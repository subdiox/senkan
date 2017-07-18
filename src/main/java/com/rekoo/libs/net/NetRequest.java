package com.rekoo.libs.net;

import android.content.Context;
import android.util.Log;
import com.rekoo.libs.config.Config;
import com.rekoo.libs.encrypt_decrypt.MD5;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

public class NetRequest {
    private static final String RKCHANNEL = "RKCHANNEL";
    private static final String TAG_LOGIN = "login";
    private static NetRequest netRequest = null;
    OkHttpClient client;

    private NetRequest() {
        this.client = null;
        this.client = new OkHttpClient();
        this.client.setConnectTimeout(10, TimeUnit.SECONDS);
        this.client.setWriteTimeout(10, TimeUnit.SECONDS);
        this.client.setReadTimeout(30, TimeUnit.SECONDS);
    }

    public static NetRequest getRequest() {
        if (netRequest == null) {
            synchronized (NetRequest.class) {
                if (netRequest == null) {
                    netRequest = new NetRequest();
                }
            }
        }
        return netRequest;
    }

    public void post(Context context, String url, RequestBody requestBody, Callback responseCallback) {
        try {
            this.client.newCall(new Builder().url(url).post(requestBody).tag("login").build()).enqueue(responseCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String post(Context context, String url, RequestBody requestBody) {
        try {
            Response response = this.client.newCall(new Builder().addHeader(RKCHANNEL, Config.getConfig().getChannel(context)).url(url).post(requestBody).build()).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
            throw new IOException("Unexpected code " + response);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void cancelLogin() {
        this.client.cancel("login");
    }

    public RequestBody getRequestBody(Context context, Map<String, String> parames) {
        FormEncodingBuilder builder = new FormEncodingBuilder();
        Log.i("TAG", parames.toString());
        for (Entry<String, String> entry : parames.entrySet()) {
            builder.add((String) entry.getKey(), (String) entry.getValue());
        }
        return builder.build();
    }

    public String getStringFromServer(String url) {
        try {
            Log.i("TAG", "urlurlurl" + url);
            Response response = this.client.newCall(new Builder().url(url).build()).execute();
            Log.i("TAG", "response.isSuccessful()response.isSuccessful()" + response.isSuccessful());
            if (response.isSuccessful()) {
                return response.body().string();
            }
            throw new IOException("Unexpected code " + response);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String get(String url, Map<String, String> parames) throws IOException {
        Response response = this.client.newCall(setRequest(url, parames)).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        }
        throw new IOException("Unexpected code " + response);
    }

    public void get(String url, Map<String, String> parames, Callback responseCallback) {
        this.client.newCall(setRequest(url, parames)).enqueue(responseCallback);
    }

    private String setSign(Map<String, String> parames) {
        StringBuilder builder = new StringBuilder();
        for (Entry<String, String> entry : parames.entrySet()) {
            builder.append((String) entry.getKey());
            builder.append("=");
            builder.append((String) entry.getValue());
            builder.append("&");
        }
        return MD5.getMD5(subLastAndSign(builder.toString().trim()));
    }

    private Request setRequest(String url, Map<String, String> parames) {
        return new Builder().url(setSign(parames)).build();
    }

    private String subLastAndSign(String str) {
        return str.substring(0, str.lastIndexOf("&")).trim();
    }
}
