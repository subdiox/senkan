package com.yaya.sdk.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import com.adjust.sdk.Constants;

public class i {
    private static String a = i.class.getSimpleName();

    public static void a(Context context, int i, Class cls) {
        ((AlarmManager) context.getSystemService("alarm")).setRepeating(3, SystemClock.elapsedRealtime() + 5000, (long) (Constants.ONE_HOUR * i), PendingIntent.getService(context, 0, new Intent(context, cls), 134217728));
    }

    public static void a(Context context, Class cls) {
        ((AlarmManager) context.getSystemService("alarm")).cancel(PendingIntent.getService(context, 0, new Intent(context, cls), 134217728));
    }
}
