package com.yaya.sdk.utils;

import android.content.Context;
import com.yaya.sdk.constants.Constants;

public class j {
    public static void a(Context context, String str, boolean z) {
        context.getSharedPreferences(Constants.SP_NAME, 0).edit().putBoolean(str, z).commit();
    }

    public static boolean b(Context context, String str, boolean z) {
        return context.getSharedPreferences(Constants.SP_NAME, 0).getBoolean(str, z);
    }

    public static void a(Context context, String str, String str2) {
        context.getSharedPreferences(Constants.SP_NAME, 0).edit().putString(str, str2).commit();
    }

    public static String b(Context context, String str, String str2) {
        return context.getSharedPreferences(Constants.SP_NAME, 0).getString(str, str2);
    }

    public static void a(Context context, String str, int i) {
        context.getSharedPreferences(Constants.SP_NAME, 0).edit().putInt(str, i).commit();
    }

    public static int b(Context context, String str, int i) {
        return context.getSharedPreferences(Constants.SP_NAME, 0).getInt(str, i);
    }

    public static void a(Context context, String str, long j) {
        context.getSharedPreferences(Constants.SP_NAME, 0).edit().putLong(str, j).commit();
    }

    public static long a(Context context, String str, Long l) {
        return context.getSharedPreferences(Constants.SP_NAME, 0).getLong(str, l.longValue());
    }
}
