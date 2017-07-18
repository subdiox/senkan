package com.squareup.okhttp;

import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.NamedRunnable;
import com.squareup.okhttp.internal.http.HttpEngine;
import com.squareup.okhttp.internal.http.OkHeaders;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.logging.Level;
import okio.BufferedSource;

public class Call {
    volatile boolean canceled;
    private final OkHttpClient client;
    HttpEngine engine;
    private boolean executed;
    private int redirectionCount;
    private Request request;

    final class AsyncCall extends NamedRunnable {
        private final Callback responseCallback;

        private AsyncCall(Callback responseCallback) {
            super("OkHttp %s", r5.request.urlString());
            this.responseCallback = responseCallback;
        }

        String host() {
            return Call.this.request.url().getHost();
        }

        Request request() {
            return Call.this.request;
        }

        Object tag() {
            return Call.this.request.tag();
        }

        void cancel() {
            Call.this.cancel();
        }

        Call get() {
            return Call.this;
        }

        protected void execute() {
            boolean signalledCallback = false;
            try {
                Response response = Call.this.getResponse();
                if (Call.this.canceled) {
                    this.responseCallback.onFailure(Call.this.request, new IOException("Canceled"));
                } else {
                    signalledCallback = true;
                    Call.this.engine.releaseConnection();
                    this.responseCallback.onResponse(response);
                }
                Call.this.client.getDispatcher().finished(this);
            } catch (IOException e) {
                if (signalledCallback) {
                    Internal.logger.log(Level.INFO, "Callback failure for " + Call.this.toLoggableString(), e);
                } else {
                    this.responseCallback.onFailure(Call.this.request, e);
                }
                Call.this.client.getDispatcher().finished(this);
            } catch (Throwable th) {
                Call.this.client.getDispatcher().finished(this);
            }
        }
    }

    private static class RealResponseBody extends ResponseBody {
        private final Response response;
        private final BufferedSource source;

        RealResponseBody(Response response, BufferedSource source) {
            this.response = response;
            this.source = source;
        }

        public MediaType contentType() {
            String contentType = this.response.header("Content-Type");
            return contentType != null ? MediaType.parse(contentType) : null;
        }

        public long contentLength() {
            return OkHeaders.contentLength(this.response);
        }

        public BufferedSource source() {
            return this.source;
        }
    }

    protected Call(OkHttpClient client, Request request) {
        this.client = client.copyWithDefaults();
        this.request = request;
    }

    public Response execute() throws IOException {
        synchronized (this) {
            if (this.executed) {
                throw new IllegalStateException("Already Executed");
            }
            this.executed = true;
        }
        try {
            this.client.getDispatcher().executed(this);
            Response result = getResponse();
            this.engine.releaseConnection();
            if (result != null) {
                return result;
            }
            throw new IOException("Canceled");
        } finally {
            this.client.getDispatcher().finished(this);
        }
    }

    Object tag() {
        return this.request.tag();
    }

    public void enqueue(Callback responseCallback) {
        synchronized (this) {
            if (this.executed) {
                throw new IllegalStateException("Already Executed");
            }
            this.executed = true;
        }
        this.client.getDispatcher().enqueue(new AsyncCall(responseCallback));
    }

    public void cancel() {
        this.canceled = true;
        if (this.engine != null) {
            this.engine.disconnect();
        }
    }

    public boolean isCanceled() {
        return this.canceled;
    }

    private String toLoggableString() {
        String string = this.canceled ? "canceled call" : "call";
        try {
            string = string + " to " + new URL(this.request.url(), "/...").toString();
        } catch (MalformedURLException e) {
        }
        return string;
    }

    private Response getResponse() throws IOException {
        RequestBody body = this.request.body();
        if (body != null) {
            Builder requestBuilder = this.request.newBuilder();
            MediaType contentType = body.contentType();
            if (contentType != null) {
                requestBuilder.header("Content-Type", contentType.toString());
            }
            long contentLength = body.contentLength();
            if (contentLength != -1) {
                requestBuilder.header("Content-Length", Long.toString(contentLength));
                requestBuilder.removeHeader("Transfer-Encoding");
            } else {
                requestBuilder.header("Transfer-Encoding", "chunked");
                requestBuilder.removeHeader("Content-Length");
            }
            this.request = requestBuilder.build();
        }
        this.engine = new HttpEngine(this.client, this.request, false, null, null, null, null);
        while (!this.canceled) {
            try {
                this.engine.sendRequest();
                if (this.request.body() != null) {
                    this.request.body().writeTo(this.engine.getBufferedRequestBody());
                }
                this.engine.readResponse();
                Response response = this.engine.getResponse();
                Request followUp = this.engine.followUpRequest();
                if (followUp == null) {
                    this.engine.releaseConnection();
                    return response.newBuilder().body(new RealResponseBody(response, this.engine.getResponseBody())).build();
                }
                if (this.engine.getResponse().isRedirect()) {
                    int i = this.redirectionCount + 1;
                    this.redirectionCount = i;
                    if (i > 20) {
                        throw new ProtocolException("Too many redirects: " + this.redirectionCount);
                    }
                }
                if (!this.engine.sameConnection(followUp.url())) {
                    this.engine.releaseConnection();
                }
                Connection connection = this.engine.close();
                this.request = followUp;
                this.engine = new HttpEngine(this.client, this.request, false, connection, null, null, response);
            } catch (IOException e) {
                HttpEngine retryEngine = this.engine.recover(e, null);
                if (retryEngine != null) {
                    this.engine = retryEngine;
                } else {
                    throw e;
                }
            }
        }
        return null;
    }
}
