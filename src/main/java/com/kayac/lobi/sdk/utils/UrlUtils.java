package com.kayac.lobi.sdk.utils;

import android.os.Bundle;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

public final class UrlUtils {
    public static Bundle parseQuery(URI uri) {
        Bundle bundle = new Bundle();
        if (uri != null) {
            for (NameValuePair param : URLEncodedUtils.parse(uri, "UTF-8")) {
                bundle.putString(param.getName(), param.getValue());
            }
        }
        return bundle;
    }

    public static Bundle parseFragment(URI uri) {
        return decodeUrlParam(uri.getRawFragment());
    }

    private static Bundle decodeUrlParam(String query) {
        Bundle bundle = new Bundle();
        if (query != null) {
            try {
                for (String strParamPair : URLDecoder.decode(query, "UTF-8").replaceAll("&amp;", "&").split("&")) {
                    String[] paramPair = strParamPair.split("=");
                    if (paramPair.length == 2) {
                        bundle.putString(paramPair[0], paramPair[1]);
                    }
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return bundle;
    }

    public static final String encodeUrlParams(Map<String, String> params) {
        Iterator<String> iterator = params.keySet().iterator();
        StringBuilder paramStr = new StringBuilder();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            if (key != null) {
                String value = (String) params.get(key);
                if (value != null) {
                    try {
                        paramStr.append(URLEncoder.encode(key, "UTF-8")).append("=").append(URLEncoder.encode(value, "UTF-8")).append(iterator.hasNext() ? "&" : "");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return paramStr.toString();
    }

    public static final String encodeUrlParams(Bundle params) {
        Iterator<String> iterator = params.keySet().iterator();
        StringBuilder paramStr = new StringBuilder();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            if (key != null) {
                String value = params.getString(key);
                if (value != null) {
                    try {
                        paramStr.append(URLEncoder.encode(key, "UTF-8")).append("=").append(URLEncoder.encode(value, "UTF-8")).append(iterator.hasNext() ? "&" : "");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return paramStr.toString();
    }
}
