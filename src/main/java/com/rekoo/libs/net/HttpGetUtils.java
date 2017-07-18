package com.rekoo.libs.net;

import android.content.Context;
import android.text.TextUtils;
import com.rekoo.libs.callback.ResultCallback;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;

public class HttpGetUtils extends Thread {
    private static InputStream inputStream;
    public final String TAG = "qh_plat";
    private ResultCallback callback;
    private HttpEntity entity;
    private HttpClient httpclient;
    private HttpGet httpget;
    private HttpResponse response;
    private String ser_url = "";
    private String uid;

    public HttpGetUtils(Context context, String exceptionUrl, ResultCallback cb) {
        this.callback = cb;
        this.ser_url = sendExcepitionLogUrl(context, exceptionUrl);
    }

    public void run() {
        super.run();
        sendToCode();
    }

    public void sendToCode() {
        try {
            if (!TextUtils.isEmpty(this.ser_url)) {
                this.httpget = new HttpGet(this.ser_url);
                this.httpclient = new DefaultHttpClient();
                HttpClientParams.setRedirecting(this.httpclient.getParams(), true);
                HttpConnectionParams.setConnectionTimeout(this.httpclient.getParams(), 10000);
                HttpConnectionParams.setSoTimeout(this.httpclient.getParams(), 10000);
                this.response = this.httpclient.execute(this.httpget);
                if (this.response.getStatusLine().getStatusCode() == 200) {
                    this.entity = this.response.getEntity();
                    inputStream = this.entity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String result = "";
                    String str = "";
                    while (true) {
                        str = reader.readLine();
                        if (str == null) {
                            break;
                        }
                        result = new StringBuilder(String.valueOf(result)).append(str).toString();
                    }
                    this.callback.onSuccess(result);
                } else {
                    this.callback.onFail();
                }
            }
            try {
                if (this.response != null) {
                    this.response.getEntity().consumeContent();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e2) {
            this.callback.onFail();
            e2.printStackTrace();
            try {
                if (this.response != null) {
                    this.response.getEntity().consumeContent();
                }
            } catch (IOException e3) {
                e3.printStackTrace();
            }
        } catch (Throwable th) {
            try {
                if (this.response != null) {
                    this.response.getEntity().consumeContent();
                }
            } catch (IOException e32) {
                e32.printStackTrace();
            }
        }
    }

    public String sendExcepitionLogUrl(Context context, String exception) {
        String appId = URLCons.getAppId(context);
        if (appId == null) {
            appId = "unkown";
        }
        if (this.uid == null) {
            this.uid = "";
        }
        try {
            return URLCons.URL_EXCEPTION.concat("?exception=").concat(URLEncoder.encode(exception, "UTF-8")).concat("&device=").concat(URLEncoder.encode(URLCons.getDeviceModel(), "UTF-8")).concat("&gid=").concat(appId).concat("&time=").concat(String.valueOf(System.currentTimeMillis())).concat("&uid=").concat(this.uid).concat("&v=").concat(URLCons.VERSION_NUM);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
