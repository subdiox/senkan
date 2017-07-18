package com.kayac.lobi.libnakamap.rec.c;

import android.net.Uri;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class am {
    public static final String a(Map<String, String> map) {
        StringBuffer stringBuffer = new StringBuffer();
        for (Entry entry : map.entrySet()) {
            stringBuffer.append(String.format("%s=%s&", new Object[]{entry.getKey(), Uri.encode((String) entry.getValue(), "UTF-8")}));
        }
        if (stringBuffer.length() >= 1) {
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        }
        return stringBuffer.toString();
    }

    public static final Map<String, String> a(Uri uri) {
        return a(uri, true);
    }

    public static final Map<String, String> a(Uri uri, boolean z) {
        return a(uri.getEncodedQuery(), z);
    }

    public static final Map<String, String> a(String str, boolean z) {
        Map<String, String> hashMap = new HashMap();
        for (String split : str.split("&")) {
            String[] split2 = split.split("=", 2);
            if (split2.length == 2) {
                Object decode;
                if (z) {
                    decode = Uri.decode(split2[0]);
                } else {
                    String str2 = split2[0];
                }
                hashMap.put(decode, z ? Uri.decode(split2[1]) : split2[1]);
            }
        }
        return hashMap;
    }
}
