package com.rekoo.libs.push;

import android.content.Context;
import android.content.Intent;

public final class CommonUtilities {
    static final String DISPLAY_MESSAGE_ACTION = "com.google.android.gcm.demo.app.DISPLAY_MESSAGE";
    static final String EXTRA_MESSAGE = "message";
    static final String SENDER_ID = "643156496295";
    static final String SERVER_URL = "http://sdk.jp.rekoo.net:8001/";
    static final String TAG = "GCMDemo";

    static void displayMessage(Context context, String message) {
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra("message", message);
        context.sendBroadcast(intent);
    }
}
