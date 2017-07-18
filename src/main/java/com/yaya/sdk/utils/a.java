package com.yaya.sdk.utils;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class a {
    private static final String a = "T~rYw2^3%'_9#iGc";

    public static String a(String str) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        String str2 = "";
        byte[] bytes = str.getBytes();
        Key secretKeySpec = new SecretKeySpec(a.getBytes(), "AES");
        Cipher instance = Cipher.getInstance("AES");
        instance.init(1, secretKeySpec);
        return a(instance.doFinal(bytes));
    }

    public static String b(String str) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        String str2 = "";
        byte[] c = c(str);
        Key secretKeySpec = new SecretKeySpec(a.getBytes(), "AES");
        Cipher instance = Cipher.getInstance("AES");
        instance.init(2, secretKeySpec);
        return new String(instance.doFinal(c));
    }

    private static byte[] c(String str) {
        if (str.length() < 1) {
            return null;
        }
        byte[] bArr = new byte[(str.length() / 2)];
        for (int i = 0; i < str.length() / 2; i++) {
            bArr[i] = (byte) ((Integer.parseInt(str.substring(i * 2, (i * 2) + 1), 16) * 16) + Integer.parseInt(str.substring((i * 2) + 1, (i * 2) + 2), 16));
        }
        return bArr;
    }

    private static String a(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer(bArr.length * 2);
        for (int i = 0; i < bArr.length; i++) {
            stringBuffer.append(new StringBuilder(String.valueOf(Integer.toString((bArr[i] >> 4) & 15, 16))).append(Integer.toString(bArr[i] & 15, 16)).toString());
        }
        return stringBuffer.toString();
    }
}
