package com.rekoo.libs.push;

import android.content.Context;
import android.util.Log;
import com.google.android.gcm.GCMRegistrar;
import com.rekoo.libs.utils.ResUtils;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public final class ServerUtilities {
    private static final int BACKOFF_MILLI_SECONDS = 2000;
    private static final int MAX_ATTEMPTS = 5;
    private static final Random random = new Random();

    static boolean register(Context context, String regId) {
        Log.i("GCMDemo", "registering device (regId = " + regId + ")");
        String serverUrl = "http://sdk.jp.rekoo.net:8001//register";
        Map<String, String> params = new HashMap();
        params.put("regId", regId);
        long backoff = (long) (random.nextInt(1000) + BACKOFF_MILLI_SECONDS);
        int i = 1;
        while (i <= 5) {
            Log.d("GCMDemo", "Attempt #" + i + " to register");
            try {
                CommonUtilities.displayMessage(context, context.getString(ResUtils.getStringId("server_registering", context), new Object[]{Integer.valueOf(i), Integer.valueOf(5)}));
                post(serverUrl, params);
                GCMRegistrar.setRegisteredOnServer(context, true);
                CommonUtilities.displayMessage(context, context.getString(ResUtils.getStringId("server_registered", context)));
                return true;
            } catch (IOException e) {
                Log.e("GCMDemo", "Failed to register on attempt " + i, e);
                if (i == 5) {
                    break;
                }
                try {
                    Log.d("GCMDemo", "Sleeping for " + backoff + " ms before retry");
                    Thread.sleep(backoff);
                    backoff *= 2;
                    i++;
                } catch (InterruptedException e2) {
                    Log.d("GCMDemo", "Thread interrupted: abort remaining retries!");
                    Thread.currentThread().interrupt();
                    return false;
                }
            }
        }
        CommonUtilities.displayMessage(context, context.getString(ResUtils.getStringId("server_register_error", context), new Object[]{Integer.valueOf(5)}));
        return false;
    }

    static void unregister(Context context, String regId) {
        Log.i("GCMDemo", "unregistering device (regId = " + regId + ")");
        String serverUrl = "http://sdk.jp.rekoo.net:8001//unregister";
        Map<String, String> params = new HashMap();
        params.put("regId", regId);
        try {
            post(serverUrl, params);
            GCMRegistrar.setRegisteredOnServer(context, false);
            CommonUtilities.displayMessage(context, context.getString(ResUtils.getStringId("server_unregistered", context)));
        } catch (IOException e) {
            CommonUtilities.displayMessage(context, context.getString(ResUtils.getStringId("server_unregister_error", context), new Object[]{e.getMessage()}));
        }
    }

    private static void post(String endpoint, Map<String, String> params) throws IOException {
        try {
            URL url = new URL(endpoint);
            StringBuilder bodyBuilder = new StringBuilder();
            Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, String> param = (Entry) iterator.next();
                bodyBuilder.append((String) param.getKey()).append('=').append((String) param.getValue());
                if (iterator.hasNext()) {
                    bodyBuilder.append('&');
                }
            }
            String body = bodyBuilder.toString();
            Log.v("GCMDemo", "Posting '" + body + "' to " + url);
            byte[] bytes = body.getBytes();
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setFixedLengthStreamingMode(bytes.length);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                OutputStream out = conn.getOutputStream();
                out.write(bytes);
                out.close();
                int status = conn.getResponseCode();
                if (status != 200) {
                    throw new IOException("Post failed with error code " + status);
                }
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("invalid url: " + endpoint);
        }
    }
}
