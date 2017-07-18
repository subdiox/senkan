package com.squareup.okhttp;

import com.squareup.okhttp.internal.Util;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.net.ssl.SSLPeerUnverifiedException;
import okio.ByteString;

public final class CertificatePinner {
    public static final CertificatePinner DEFAULT = new Builder().build();
    private final Map<String, List<ByteString>> hostnameToPins;

    public static final class Builder {
        private final Map<String, List<ByteString>> hostnameToPins = new LinkedHashMap();

        public Builder add(String hostname, String... pins) {
            if (hostname == null) {
                throw new IllegalArgumentException("hostname == null");
            }
            List<ByteString> hostPins = new ArrayList();
            List<ByteString> previousPins = (List) this.hostnameToPins.put(hostname, Collections.unmodifiableList(hostPins));
            if (previousPins != null) {
                hostPins.addAll(previousPins);
            }
            String[] arr$ = pins;
            int len$ = arr$.length;
            int i$ = 0;
            while (i$ < len$) {
                String pin = arr$[i$];
                if (pin.startsWith("sha1/")) {
                    ByteString decodedPin = ByteString.decodeBase64(pin.substring("sha1/".length()));
                    if (decodedPin == null) {
                        throw new IllegalArgumentException("pins must be base64: " + pin);
                    }
                    hostPins.add(decodedPin);
                    i$++;
                } else {
                    throw new IllegalArgumentException("pins must start with 'sha1/': " + pin);
                }
            }
            return this;
        }

        public CertificatePinner build() {
            return new CertificatePinner();
        }
    }

    private CertificatePinner(Builder builder) {
        this.hostnameToPins = Util.immutableMap(builder.hostnameToPins);
    }

    public void check(String hostname, Certificate... peerCertificates) throws SSLPeerUnverifiedException {
        List<ByteString> pins = (List) this.hostnameToPins.get(hostname);
        if (pins != null) {
            Certificate[] arr$ = peerCertificates;
            int len$ = arr$.length;
            int i$ = 0;
            while (i$ < len$) {
                if (!pins.contains(sha1((X509Certificate) arr$[i$]))) {
                    i$++;
                } else {
                    return;
                }
            }
            StringBuilder message = new StringBuilder().append("Certificate pinning failure!").append("\n  Peer certificate chain:");
            for (Certificate c : peerCertificates) {
                X509Certificate x509Certificate = (X509Certificate) c;
                message.append("\n    sha1/").append(sha1(x509Certificate).base64()).append(": ").append(x509Certificate.getSubjectDN().getName());
            }
            message.append("\n  Pinned certificates for ").append(hostname).append(":");
            for (ByteString pin : pins) {
                message.append("\n    sha1/").append(pin.base64());
            }
            throw new SSLPeerUnverifiedException(message.toString());
        }
    }

    public static String pin(Certificate certificate) {
        if (certificate instanceof X509Certificate) {
            return "sha1/" + sha1((X509Certificate) certificate).base64();
        }
        throw new IllegalArgumentException("Certificate pinning requires X509 certificates");
    }

    private static ByteString sha1(X509Certificate x509Certificate) {
        return Util.sha1(ByteString.of(x509Certificate.getPublicKey().getEncoded()));
    }
}
