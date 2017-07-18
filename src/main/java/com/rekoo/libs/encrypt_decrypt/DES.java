package com.rekoo.libs.encrypt_decrypt;

import android.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class DES {
    private static final String merchantId = "rekoo&sg";
    private static final char[] suppChar = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};

    private static String getKey(String appId) {
        String key = new StringBuilder(merchantId).append(appId).toString();
        if (key.length() > 8) {
            return key.substring(0, 8);
        }
        if (key.length() >= 8) {
            return key;
        }
        int i = 0;
        do {
            key = new StringBuilder(String.valueOf(key)).append(suppChar[i]).toString();
            i++;
        } while (key.length() < 8);
        return key;
    }

    public static String encode(String str, String appId) throws Exception {
        byte[] rawKey = getKey(appId).getBytes("UTF-8");
        IvParameterSpec sr = new IvParameterSpec(rawKey);
        SecretKey secretKey = SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(rawKey));
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(1, secretKey, sr);
        return Base64.encodeToString(cipher.doFinal(str.getBytes("UTF8")), 0);
    }

    public static String decode(String str, String appId) throws Exception {
        byte[] rawKey = getKey(appId).getBytes("UTF-8");
        IvParameterSpec sr = new IvParameterSpec(rawKey);
        SecretKey secretKey = SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(rawKey));
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(2, secretKey, sr);
        return new String(cipher.doFinal(Base64.decode(str, 0)), "UTF8");
    }
}
