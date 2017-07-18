package com.yaya.sdk.async.http;

import android.os.SystemClock;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Iterator;
import javax.net.ssl.SSLException;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.protocol.HttpContext;

class i implements HttpRequestRetryHandler {
    private static HashSet<Class<?>> a = new HashSet();
    private static HashSet<Class<?>> b = new HashSet();
    private final int c;
    private final int d;

    static {
        a.add(NoHttpResponseException.class);
        a.add(UnknownHostException.class);
        a.add(SocketException.class);
        b.add(InterruptedIOException.class);
        b.add(SSLException.class);
    }

    public i(int i, int i2) {
        this.c = i;
        this.d = i2;
    }

    public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
        boolean z = false;
        Boolean bool = (Boolean) context.getAttribute("http.request_sent");
        boolean z2 = bool != null && bool.booleanValue();
        if (executionCount > this.c) {
            z2 = false;
        } else if (a(b, exception)) {
            z2 = false;
        } else if (a(a, exception)) {
            z2 = true;
        } else if (z2) {
            z2 = true;
        } else {
            z2 = true;
        }
        if (z2) {
            HttpUriRequest httpUriRequest = (HttpUriRequest) context.getAttribute("http.request");
            if (httpUriRequest != null) {
                if (!httpUriRequest.getMethod().equals("POST")) {
                    z = true;
                }
                if (z) {
                    SystemClock.sleep((long) this.d);
                }
            }
        } else {
            z = z2;
            if (z) {
                SystemClock.sleep((long) this.d);
            }
        }
        return z;
    }

    protected boolean a(HashSet<Class<?>> hashSet, Throwable th) {
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            if (((Class) it.next()).isInstance(th)) {
                return true;
            }
        }
        return false;
    }
}
