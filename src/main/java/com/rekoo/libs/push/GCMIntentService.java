package com.rekoo.libs.push;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMConstants;
import com.google.android.gcm.GCMRegistrar;
import com.rekoo.libs.config.Config;
import com.rekoo.libs.net.URLCons;
import com.rekoo.libs.utils.ResUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class GCMIntentService extends GCMBaseIntentService {
    private String message_type;
    private int sendno;
    private String url;

    protected void onRegistered(Context context, String registrationId) {
        Log.i(GCMBaseIntentService.TAG, "Device registered: regId = " + registrationId);
        displayMessage(context, getString(ResUtils.getStringId("gcm_registered", context)));
        ServerUtilities.register(context, registrationId);
    }

    protected void onUnregistered(Context context, String registrationId) {
        Log.i(GCMBaseIntentService.TAG, "Device unregistered");
        displayMessage(context, getString(ResUtils.getStringId("gcm_unregistered", context)));
        if (GCMRegistrar.isRegisteredOnServer(context)) {
            ServerUtilities.unregister(context, registrationId);
        } else {
            Log.i(GCMBaseIntentService.TAG, "Ignoring unregister callback");
        }
    }

    protected void onMessage(Context context, Intent intent) {
        Log.i("GCMIntentService", "收到新消息：" + intent.getStringExtra("alert"));
        String message = intent.getStringExtra("alert");
        String extra = intent.getStringExtra("extra");
        try {
            JSONObject json = new JSONObject(extra);
            this.url = json.getString("url");
            this.message_type = json.getString(GCMConstants.EXTRA_SPECIAL_MESSAGE);
            this.sendno = json.getInt(URLCons.SENDNO);
            Config.sendno = this.sendno;
            Log.i("TAG", "onMessage" + this.url + null + this.message_type);
            if (this.message_type.equals("CustomMsg")) {
                Log.i("TAG", "url" + this.url);
                if (TextUtils.isEmpty(this.url)) {
                    gameMsgNotification(context, message, extra, this.sendno);
                } else {
                    generateNotification(context, message, this.url, this.sendno);
                }
            } else if (this.message_type.equals("GameMsg")) {
                gameMsgNotification(context, message, extra, this.sendno);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void onDeletedMessages(Context context, int total) {
        Log.i(GCMBaseIntentService.TAG, "Received deleted messages notification");
        String message = getString(ResUtils.getStringId("gcm_deleted", context), new Object[]{Integer.valueOf(total)});
        displayMessage(context, message);
        generateNotification(context, message, this.url, this.sendno);
    }

    public void onError(Context context, String errorId) {
        Log.i(GCMBaseIntentService.TAG, "Received error: " + errorId);
        displayMessage(context, getString(ResUtils.getStringId("gcm_error", context), new Object[]{errorId}));
    }

    protected boolean onRecoverableError(Context context, String errorId) {
        Log.i(GCMBaseIntentService.TAG, "Received recoverable error: " + errorId);
        displayMessage(context, getString(ResUtils.getStringId("gcm_recoverable_error", context), new Object[]{errorId}));
        return super.onRecoverableError(context, errorId);
    }

    private static void generateNotification(Context context, String message, String url, int sendno) {
        int icon = ResUtils.getDrawable("push_icon", context);
        long when = System.currentTimeMillis();
        String title = context.getString(ResUtils.getStringId("app_name", context));
        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        if (VERSION.SDK_INT < 21) {
            Notification notification = new Notification(icon, message, when);
            Intent notificationIntent = new Intent(context, NotificationActivity.class);
            Log.i("RKSDK", "pushURL:" + url);
            notificationIntent.putExtra("url", url);
            notificationIntent.addFlags(268435456);
            notification.setLatestEventInfo(context, title, message, PendingIntent.getActivity(context, 0, notificationIntent, 134217728));
            notification.flags |= 16;
            notification.defaults |= 2;
            notificationManager.notify(sendno, notification);
            return;
        }
        Builder builder = new Builder(context);
        builder.setContentText(message);
        builder.setContentTitle(title);
        builder.setSmallIcon(icon);
        builder.setTicker("新消息");
        builder.setAutoCancel(true);
        builder.setWhen(System.currentTimeMillis());
        notificationIntent = new Intent(context, NotificationActivity.class);
        Log.i("RKSDK", "pushURL:" + url);
        notificationIntent.putExtra("url", url);
        notificationIntent.addFlags(268435456);
        builder.setContentIntent(PendingIntent.getActivity(context, 0, notificationIntent, 134217728));
        notification = builder.build();
        notification.flags |= 16;
        notification.defaults |= 2;
        notificationManager.notify(sendno, notification);
    }

    private static void gameMsgNotification(Context context, String message, String extra, int sendno) {
        int icon = ResUtils.getDrawable("push_icon", context);
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        String title = context.getString(ResUtils.getStringId("app_name", context));
        if (VERSION.SDK_INT < 21) {
            Notification notification = new Notification(icon, message, when);
            Intent notificationIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("extra", extra);
            notificationIntent.setClassName(context, URLCons.getPackageName(context));
            notificationIntent.addCategory("android.intent.category.INFO");
            notificationIntent.addCategory("android.intent.category.DEFAULT");
            notificationIntent.putExtras(bundle);
            notificationIntent.addFlags(536870912);
            notification.setLatestEventInfo(context, title, message, PendingIntent.getActivity(context, 0, notificationIntent, 134217728));
            notification.flags |= 16;
            notification.defaults |= 2;
            notificationManager.notify(sendno, notification);
            return;
        }
        Builder builder = new Builder(context);
        builder.setContentText(message);
        builder.setContentTitle(title);
        builder.setSmallIcon(icon);
        builder.setTicker("新消息");
        builder.setAutoCancel(true);
        builder.setWhen(System.currentTimeMillis());
        notificationIntent = new Intent();
        bundle = new Bundle();
        bundle.putString("extra", extra);
        notificationIntent.setClassName(context, URLCons.getPackageName(context));
        notificationIntent.addCategory("android.intent.category.INFO");
        notificationIntent.addCategory("android.intent.category.DEFAULT");
        notificationIntent.putExtras(bundle);
        notificationIntent.addFlags(536870912);
        builder.setContentIntent(PendingIntent.getActivity(context, 0, notificationIntent, 134217728));
        notification = builder.build();
        notification.flags |= 16;
        notification.defaults |= 2;
        notificationManager.notify(sendno, notification);
    }

    static void displayMessage(Context context, String message) {
        Intent intent = new Intent("com.google.android.gcm.demo.app.DISPLAY_MESSAGE");
        intent.putExtra("message", message);
        context.sendBroadcast(intent);
    }
}
