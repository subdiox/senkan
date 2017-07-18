package net.agasper.unitynotification;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Build.VERSION;
import com.kayac.lobi.libnakamap.components.LoginEntranceDialog;
import com.unity3d.player.UnityPlayer;

public class UnityNotificationManager extends BroadcastReceiver {
    public static void SetNotification(int id, long delayMs, String title, String message, String ticker, int sound, int vibrate, int lights, String largeIconResource, String smallIconResource, int bgColor, int executeMode, String unityClass) {
        Activity currentActivity = UnityPlayer.currentActivity;
        AlarmManager am = (AlarmManager) currentActivity.getSystemService("alarm");
        Intent intent = new Intent(currentActivity, UnityNotificationManager.class);
        intent.putExtra("ticker", ticker);
        intent.putExtra(LoginEntranceDialog.ARGUMENTS_TITLE, title);
        intent.putExtra("message", message);
        intent.putExtra("id", id);
        intent.putExtra("color", bgColor);
        intent.putExtra("sound", sound == 1);
        intent.putExtra("vibrate", vibrate == 1);
        intent.putExtra("lights", lights == 1);
        intent.putExtra("l_icon", largeIconResource);
        intent.putExtra("s_icon", smallIconResource);
        intent.putExtra("activity", unityClass);
        if (VERSION.SDK_INT < 23) {
            am.set(0, System.currentTimeMillis() + delayMs, PendingIntent.getBroadcast(currentActivity, id, intent, 0));
        } else if (executeMode == 2) {
            am.setExactAndAllowWhileIdle(0, System.currentTimeMillis() + delayMs, PendingIntent.getBroadcast(currentActivity, id, intent, 0));
        } else if (executeMode == 1) {
            am.setExact(0, System.currentTimeMillis() + delayMs, PendingIntent.getBroadcast(currentActivity, id, intent, 0));
        } else {
            am.set(0, System.currentTimeMillis() + delayMs, PendingIntent.getBroadcast(currentActivity, id, intent, 0));
        }
    }

    public static void SetRepeatingNotification(int id, long delay, String title, String message, String ticker, long rep, int sound, int vibrate, int lights, String largeIconResource, String smallIconResource, int bgColor, String unityClass) {
        Activity currentActivity = UnityPlayer.currentActivity;
        AlarmManager am = (AlarmManager) currentActivity.getSystemService("alarm");
        Intent intent = new Intent(currentActivity, UnityNotificationManager.class);
        intent.putExtra("ticker", ticker);
        intent.putExtra(LoginEntranceDialog.ARGUMENTS_TITLE, title);
        intent.putExtra("message", message);
        intent.putExtra("id", id);
        intent.putExtra("color", bgColor);
        intent.putExtra("sound", sound == 1);
        intent.putExtra("vibrate", vibrate == 1);
        intent.putExtra("lights", lights == 1);
        intent.putExtra("l_icon", largeIconResource);
        intent.putExtra("s_icon", smallIconResource);
        intent.putExtra("activity", unityClass);
        am.setRepeating(0, System.currentTimeMillis() + delay, rep, PendingIntent.getBroadcast(currentActivity, id, intent, 0));
    }

    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        String ticker = intent.getStringExtra("ticker");
        String title = intent.getStringExtra(LoginEntranceDialog.ARGUMENTS_TITLE);
        String message = intent.getStringExtra("message");
        String s_icon = intent.getStringExtra("s_icon");
        String l_icon = intent.getStringExtra("l_icon");
        int color = intent.getIntExtra("color", 0);
        String unityClass = intent.getStringExtra("activity");
        Boolean sound = Boolean.valueOf(intent.getBooleanExtra("sound", false));
        Boolean vibrate = Boolean.valueOf(intent.getBooleanExtra("vibrate", false));
        Boolean lights = Boolean.valueOf(intent.getBooleanExtra("lights", false));
        int id = intent.getIntExtra("id", 0);
        Resources res = context.getResources();
        Class<?> unityClassActivity = null;
        try {
            unityClassActivity = Class.forName(unityClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(context, unityClassActivity), 0);
        Builder builder = new Builder(context);
        builder.setContentIntent(contentIntent).setWhen(System.currentTimeMillis()).setAutoCancel(true).setContentTitle(title).setContentText(message);
        if (VERSION.SDK_INT >= 21) {
            builder.setColor(color);
        }
        if (ticker != null && ticker.length() > 0) {
            builder.setTicker(ticker);
        }
        if (s_icon != null && s_icon.length() > 0) {
            builder.setSmallIcon(res.getIdentifier(s_icon, "drawable", context.getPackageName()));
        }
        if (l_icon != null && l_icon.length() > 0) {
            builder.setLargeIcon(BitmapFactory.decodeResource(res, res.getIdentifier(l_icon, "drawable", context.getPackageName())));
        }
        if (sound.booleanValue()) {
            builder.setSound(RingtoneManager.getDefaultUri(2));
        }
        if (vibrate.booleanValue()) {
            long[] jArr = new long[2];
            builder.setVibrate(new long[]{1000, 1000});
        }
        if (lights.booleanValue()) {
            builder.setLights(-16711936, 3000, 3000);
        }
        notificationManager.notify(id, builder.build());
    }

    public static void CancelNotification(int id) {
        Activity currentActivity = UnityPlayer.currentActivity;
        AlarmManager am = (AlarmManager) currentActivity.getSystemService("alarm");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(currentActivity, id, new Intent(currentActivity, UnityNotificationManager.class), 536870912);
        if (pendingIntent != null) {
            am.cancel(pendingIntent);
            pendingIntent.cancel();
        }
    }

    public static void CancelAll() {
        ((NotificationManager) UnityPlayer.currentActivity.getApplicationContext().getSystemService("notification")).cancelAll();
    }
}
