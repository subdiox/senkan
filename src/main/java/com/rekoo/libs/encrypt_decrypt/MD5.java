package com.rekoo.libs.encrypt_decrypt;

import com.adjust.sdk.Constants;
import java.security.MessageDigest;

public class MD5 {
    public static String getMD5(String val) {
        try {
            return getString(MessageDigest.getInstance(Constants.MD5).digest(val.getBytes("UTF-8")));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String getString(byte[] b) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            if ((b[i] & 255) < 16) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(b[i] & 255));
        }
        return sb.toString();
    }
}
