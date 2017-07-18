package com.yunva.im.sdk.lib.utils;

import android.util.Log;

public class a {
    public static boolean a = true;

    public static void a(String str, String str2) {
        if (a) {
            Log.i(str, str2);
        }
    }

    public static void b(String str, String str2) {
        if (a) {
            Log.e(str, str2);
        }
    }

    public static void c(String str, String str2) {
        if (a) {
            Log.i(str, str2);
        }
    }

    public static void d(String str, String str2) {
        if (a) {
            Log.i(str, str2);
        }
    }

    public static void e(String str, String str2) {
        if (a) {
            Log.i(str, str2);
        }
    }

    public static void a(boolean z) {
        a = z;
    }

    public static void a(String str) {
        if (a) {
            Log.i("system", str);
        }
    }
}
