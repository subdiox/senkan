package com.squareup.okhttp.internal.http;

import com.mob.tools.network.HttpPatch;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public final class HttpMethod {
    public static final Set<String> METHODS = new LinkedHashSet(Arrays.asList(new String[]{"OPTIONS", "GET", "HEAD", "POST", "PUT", "DELETE", "TRACE", HttpPatch.METHOD_NAME}));

    public static boolean invalidatesCache(String method) {
        return method.equals("POST") || method.equals(HttpPatch.METHOD_NAME) || method.equals("PUT") || method.equals("DELETE");
    }

    public static boolean requiresRequestBody(String method) {
        return method.equals("POST") || method.equals("PUT") || method.equals(HttpPatch.METHOD_NAME);
    }

    public static boolean permitsRequestBody(String method) {
        return requiresRequestBody(method) || method.equals("DELETE");
    }

    private HttpMethod() {
    }
}
