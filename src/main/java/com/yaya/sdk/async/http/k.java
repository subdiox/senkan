package com.yaya.sdk.async.http;

import android.content.Context;
import com.kayac.lobi.libnakamap.net.APIUtil.Endpoint;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;

public class k extends a {
    public k() {
        super(false, 80, Endpoint.HTTPS_PORT);
    }

    public k(int i) {
        super(false, i, Endpoint.HTTPS_PORT);
    }

    public k(int i, int i2) {
        super(false, i, i2);
    }

    public k(boolean z, int i, int i2) {
        super(z, i, i2);
    }

    public k(SchemeRegistry schemeRegistry) {
        super(schemeRegistry);
    }

    protected f a(DefaultHttpClient defaultHttpClient, HttpContext httpContext, HttpUriRequest httpUriRequest, String str, h hVar, Context context) {
        if (str != null) {
            httpUriRequest.addHeader("Content-Type", str);
        }
        hVar.a(true);
        new b(defaultHttpClient, httpContext, httpUriRequest, hVar).run();
        return new f(null);
    }
}
