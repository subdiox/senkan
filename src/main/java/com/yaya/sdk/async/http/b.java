package com.yaya.sdk.async.http;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.protocol.HttpContext;

class b implements Runnable {
    private final AbstractHttpClient a;
    private final HttpContext b;
    private final HttpUriRequest c;
    private final h d;
    private int e;

    public b(AbstractHttpClient abstractHttpClient, HttpContext httpContext, HttpUriRequest httpUriRequest, h hVar) {
        this.a = abstractHttpClient;
        this.b = httpContext;
        this.c = httpUriRequest;
        this.d = hVar;
    }

    public void run() {
        if (this.d != null) {
            this.d.h();
        }
        try {
            b();
        } catch (Throwable e) {
            if (this.d != null) {
                this.d.b(0, null, null, e);
            }
        }
        if (this.d != null) {
            this.d.i();
        }
    }

    private void a() throws IOException {
        if (!Thread.currentThread().isInterrupted()) {
            if (this.c.getURI().getScheme() == null) {
                throw new MalformedURLException("No valid URI scheme was provided");
            }
            HttpResponse execute = this.a.execute(this.c, this.b);
            if (!Thread.currentThread().isInterrupted() && this.d != null) {
                this.d.a(execute);
            }
        }
    }

    private void b() throws IOException {
        int i;
        IOException iOException = null;
        HttpRequestRetryHandler httpRequestRetryHandler = this.a.getHttpRequestRetryHandler();
        boolean z = true;
        while (z) {
            try {
                a();
                return;
            } catch (UnknownHostException e) {
                boolean z2;
                try {
                    IOException iOException2;
                    IOException iOException3 = new IOException("UnknownHostException exception: " + e.getMessage());
                    if (this.e > 0) {
                        int i2 = this.e + 1;
                        this.e = i2;
                        if (httpRequestRetryHandler.retryRequest(iOException3, i2, this.b)) {
                            z2 = true;
                            iOException2 = iOException3;
                            z = z2;
                            iOException = iOException2;
                        }
                    }
                    z2 = false;
                    iOException2 = iOException3;
                    z = z2;
                    iOException = iOException2;
                } catch (Exception e2) {
                    Exception exception = e2;
                    z2 = a.a;
                    iOException = new IOException("Unhandled exception: " + exception.getMessage());
                }
            } catch (NullPointerException e3) {
                iOException = new IOException("NPE in HttpClient: " + e3.getMessage());
                i = this.e + 1;
                this.e = i;
                z = httpRequestRetryHandler.retryRequest(iOException, i, this.b);
            } catch (IOException e4) {
                iOException = e4;
                i = this.e + 1;
                this.e = i;
                z = httpRequestRetryHandler.retryRequest(iOException, i, this.b);
            }
        }
        throw iOException;
        if (z && this.d != null) {
            this.d.j();
        }
    }
}
