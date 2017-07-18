package com.mob.commons;

import android.content.Context;
import com.mob.tools.utils.SharePrefrenceHelper;

public class k {
    private static SharePrefrenceHelper a;

    public static String a(Context context) {
        e(context);
        return a.getString("key_ext_info");
    }

    public static void a(Context context, long j) {
        e(context);
        a.putLong("wifi_last_time", Long.valueOf(j));
    }

    public static void a(Context context, String str) {
        e(context);
        a.putString("key_ext_info", str);
    }

    public static long b(Context context) {
        e(context);
        return a.getLong("wifi_last_time");
    }

    public static void b(Context context, long j) {
        e(context);
        a.putLong("key_cellinfo_next_total", Long.valueOf(j));
    }

    public static void b(Context context, String str) {
        e(context);
        a.putString("wifi_last_info", str);
    }

    public static String c(Context context) {
        e(context);
        return a.getString("wifi_last_info");
    }

    public static void c(Context context, String str) {
        e(context);
        a.putString("key_cellinfo", str);
    }

    public static String d(Context context) {
        e(context);
        return a.getString("key_cellinfo");
    }

    private static synchronized void e(Context context) {
        synchronized (k.class) {
            if (a == null) {
                a = new SharePrefrenceHelper(context.getApplicationContext());
                a.open("mob_commons", 1);
            }
        }
    }
}
