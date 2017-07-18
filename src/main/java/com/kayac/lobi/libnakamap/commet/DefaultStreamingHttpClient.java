package com.kayac.lobi.libnakamap.commet;

import java.io.IOException;
import java.lang.reflect.UndeclaredThrowableException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

public class DefaultStreamingHttpClient extends DefaultHttpClient {
    public DefaultStreamingHttpClient(ClientConnectionManager conman, HttpParams params) {
        super(conman, params);
    }

    public <T> T execute(HttpHost target, HttpRequest request, ResponseHandler<? extends T> responseHandler, HttpContext context) throws IOException, ClientProtocolException {
        if (responseHandler == null) {
            throw new IllegalArgumentException("Response handler must not be null.");
        }
        try {
            return responseHandler.handleResponse(execute(target, request, context));
        } catch (Throwable t) {
            if (t instanceof Error) {
                Error t2 = (Error) t;
            } else if (t instanceof RuntimeException) {
                RuntimeException t3 = (RuntimeException) t;
            } else if (t instanceof IOException) {
                IOException t4 = (IOException) t;
            } else {
                UndeclaredThrowableException undeclaredThrowableException = new UndeclaredThrowableException(t);
            }
        }
    }
}
