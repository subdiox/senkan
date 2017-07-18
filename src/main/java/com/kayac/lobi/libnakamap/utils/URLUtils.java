package com.kayac.lobi.libnakamap.utils;

import android.net.Uri;
import android.os.Bundle;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Iterator;

public class URLUtils {
    private static final String UTF8 = Charset.forName("utf-8").name();

    public static final String encodeUrlParams(Bundle params) {
        Iterator<String> iterator = params.keySet().iterator();
        StringBuilder paramStr = new StringBuilder();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            if (key != null) {
                String value = params.getString(key);
                if (value != null) {
                    try {
                        paramStr.append(URLEncoder.encode(key, UTF8)).append("=").append(URLEncoder.encode(value, UTF8)).append(iterator.hasNext() ? "&" : "");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return paramStr.toString();
    }

    public static Bundle parseQuery(Uri rawUri) {
        try {
            URI uri = new URI(rawUri.toString());
            URI uri2 = uri;
            return decodeUrlParam(uri.getRawQuery());
        } catch (URISyntaxException e) {
            return new Bundle();
        }
    }

    private static Bundle decodeUrlParam(String query) {
        Bundle bundle = new Bundle();
        if (query != null) {
            for (String strParamPair : query.replaceAll("&amp;", "&").split("&")) {
                String[] paramPair = strParamPair.split("=");
                if (paramPair.length == 2) {
                    try {
                        bundle.putString(URLDecoder.decode(paramPair[0], "UTF-8"), URLDecoder.decode(paramPair[1], "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return bundle;
    }

    public static Bundle parseFragment(Uri rawUri) {
        try {
            URI uri = new URI(rawUri.toString());
            URI uri2 = uri;
            return decodeUrlParam(uri.getRawFragment());
        } catch (URISyntaxException e) {
            return new Bundle();
        }
    }
}
