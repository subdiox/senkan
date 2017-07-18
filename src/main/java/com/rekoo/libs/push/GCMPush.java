package com.rekoo.libs.push;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.util.Log;
import com.google.android.gcm.GCMRegistrar;
import com.rekoo.libs.config.Config;
import com.rekoo.libs.utils.ResUtils;

public class GCMPush {
    public static GCMPush myGCMPush;
    private Activity mContext;
    private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.google.android.gcm.demo.app.DISPLAY_MESSAGE")) {
                Log.d("TAG", "GCMPush接收到广告1");
            }
            Log.d("TAG", "GCMPush接收到广告2");
            String newMessage = intent.getExtras().getString("message");
        }
    };
    AsyncTask<Void, Void, Void> mRegisterTask;

    public static GCMPush getInstance() {
        if (myGCMPush == null) {
            synchronized (GCMPush.class) {
                if (myGCMPush == null) {
                    myGCMPush = new GCMPush();
                }
            }
        }
        return myGCMPush;
    }

    private GCMPush() {
    }

    public void GCMRegistrarId(Activity context) {
        this.mContext = context;
        checkNotNull("http://sdk.jp.rekoo.net:8001/", "SERVER_URL");
        Log.i("TAG", "sendId" + Config.getConfig().getSendId(this.mContext));
        checkNotNull(Config.getConfig().getSendId(this.mContext), "SENDER_ID");
        GCMRegistrar.checkDevice(this.mContext);
        GCMRegistrar.checkManifest(this.mContext);
        this.mContext.registerReceiver(this.mHandleMessageReceiver, new IntentFilter("com.google.android.gcm.demo.app.DISPLAY_MESSAGE"));
        final String regId = GCMRegistrar.getRegistrationId(this.mContext);
        if ("".equals("")) {
            GCMRegistrar.register(this.mContext, Config.getConfig().getSendId(this.mContext));
        } else if (!GCMRegistrar.isRegisteredOnServer(this.mContext)) {
            this.mRegisterTask = new AsyncTask<Void, Void, Void>() {
                protected Void doInBackground(Void... params) {
                    if (!ServerUtilities.register(GCMPush.this.mContext, regId)) {
                        GCMRegistrar.unregister(GCMPush.this.mContext);
                    }
                    return null;
                }

                protected void onPostExecute(Void result) {
                    GCMPush.this.mRegisterTask = null;
                }
            };
            this.mRegisterTask.execute(new Void[]{null, null, null});
        }
    }

    private void OnPause() {
        GCMRegistrar.unregister(this.mContext);
    }

    protected void onDestroy() {
        if (this.mRegisterTask != null) {
            this.mRegisterTask.cancel(true);
        }
        this.mContext.unregisterReceiver(this.mHandleMessageReceiver);
        GCMRegistrar.onDestroy(this.mContext);
    }

    private void checkNotNull(Object reference, String name) {
        if (reference == null) {
            throw new NullPointerException(this.mContext.getString(ResUtils.getStringId("error_config", this.mContext), new Object[]{name}));
        }
    }
}
