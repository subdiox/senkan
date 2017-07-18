package com.kayac.lobi.libnakamap.net.security;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import junit.framework.Assert;

public class HashUtils {
    private HashUtils() {
    }

    public static String hmacSha1(String salt, String value) {
        String r = "";
        try {
            SecretKeySpec secret = new SecretKeySpec(salt.getBytes(), "HmacSHA1");
            Mac mac = Mac.getInstance(secret.getAlgorithm());
            mac.init(secret);
            for (byte b : mac.doFinal(value.getBytes())) {
                String strB = Integer.toHexString(b & 255);
                if (strB.length() == 1) {
                    strB = "0" + strB;
                }
                r = r + strB;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (InvalidKeyException e2) {
            e2.printStackTrace();
            Assert.fail();
        }
        return r;
    }
}
