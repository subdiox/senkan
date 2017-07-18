package com.yaya.sdk.async.http;

import java.io.IOException;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.http.conn.ssl.SSLSocketFactory;

public class e extends SSLSocketFactory {
    SSLContext a = SSLContext.getInstance("TLS");

    public e(KeyStore keyStore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        super(keyStore);
        AnonymousClass1 anonymousClass1 = new X509TrustManager(this) {
            final /* synthetic */ e a;

            {
                this.a = r1;
            }

            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }
        };
        this.a.init(null, new TrustManager[]{anonymousClass1}, null);
    }

    public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException {
        return this.a.getSocketFactory().createSocket(socket, host, port, autoClose);
    }

    public Socket createSocket() throws IOException {
        return this.a.getSocketFactory().createSocket();
    }

    public static KeyStore a() {
        KeyStore instance;
        Throwable th;
        try {
            instance = KeyStore.getInstance(KeyStore.getDefaultType());
            try {
                instance.load(null, null);
            } catch (Throwable th2) {
                th = th2;
                if (a.a) {
                    th.printStackTrace();
                }
                return instance;
            }
        } catch (Throwable th3) {
            Throwable th4 = th3;
            instance = null;
            th = th4;
            if (a.a) {
                th.printStackTrace();
            }
            return instance;
        }
        return instance;
    }

    public static SSLSocketFactory b() {
        try {
            SSLSocketFactory eVar = new e(a());
            eVar.setHostnameVerifier(SSLSocketFactory.STRICT_HOSTNAME_VERIFIER);
            return eVar;
        } catch (Throwable th) {
            if (a.a) {
                th.printStackTrace();
            }
            return SSLSocketFactory.getSocketFactory();
        }
    }
}
