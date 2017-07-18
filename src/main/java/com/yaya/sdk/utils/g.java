package com.yaya.sdk.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.yaya.sdk.constants.Constants;

public class g {
    public static void a(String str, Object obj) {
        boolean z = Constants.IS_LOG_ON;
    }

    public static void b(String str, Object obj) {
        boolean z = Constants.IS_LOG_ON;
    }

    public static void c(String str, Object obj) {
        boolean z = Constants.IS_LOG_ON;
    }

    public static void d(String str, Object obj) {
        boolean z = Constants.IS_LOG_ON;
    }

    public static void e(String str, Object obj) {
        if (Constants.IS_LOG_ON) {
            Log.w(str, obj);
        }
    }

    public static void a(Context context, String str) {
        Toast.makeText(context, str, 0).show();
    }
}
