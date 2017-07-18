package com.adjust.sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class AdjustReferrerReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        String rawReferrer = intent.getStringExtra("referrer");
        if (rawReferrer != null) {
            String referrer;
            try {
                referrer = URLDecoder.decode(rawReferrer, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                referrer = Constants.MALFORMED;
            }
            Adjust.getDefaultInstance().sendReferrer(referrer);
        }
    }
}
