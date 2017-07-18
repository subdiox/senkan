package com.mob.tools.network;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

public class HttpConnectionImpl23 implements HttpConnection {
    private HttpURLConnection conn;

    public HttpConnectionImpl23(HttpURLConnection conn) {
        this.conn = conn;
    }

    public InputStream getErrorStream() throws IOException {
        return this.conn.getErrorStream();
    }

    public Map<String, List<String>> getHeaderFields() throws IOException {
        return this.conn.getHeaderFields();
    }

    public InputStream getInputStream() throws IOException {
        return this.conn.getInputStream();
    }

    public int getResponseCode() throws IOException {
        return this.conn.getResponseCode();
    }
}
