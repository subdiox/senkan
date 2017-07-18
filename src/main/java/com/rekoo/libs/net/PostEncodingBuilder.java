package com.rekoo.libs.net;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class PostEncodingBuilder {
    private static final MediaType CONTENT_TYPE = MediaType.parse("application/x-www-form-urlencoded");
    private final StringBuilder content = new StringBuilder();

    public PostEncodingBuilder add(String name, String value) {
        if (this.content.length() > 0) {
            this.content.append('&');
        }
        try {
            this.content.append(URLEncoder.encode(name, "UTF-8")).append('=').append(URLEncoder.encode(value, "UTF-8"));
            return this;
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    public RequestBody build() {
        if (this.content.length() == 0) {
            throw new IllegalStateException("Form encoded body must have at least one part.");
        }
        byte[] contentBytes = null;
        try {
            contentBytes = this.content.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return RequestBody.create(CONTENT_TYPE, contentBytes);
    }
}
