package com.yaya.sdk.core;

import android.content.Context;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.yaya.sdk.constants.Constants;
import com.yaya.sdk.http.a;

public class CoreLib {
    private static String a = CoreLib.class.getSimpleName();

    private static boolean isN() {
        if (VERSION.SDK_INT >= 24) {
            return false;
        }
        return true;
    }

    public static void init(Context context, String appId) {
        if (context != null && !TextUtils.isEmpty(appId)) {
            Constants.APP_ID = appId;
            Constants.GLOABLE_CONTEXT = context;
            a.a(context, new com.yaya.sdk.listener.a() {
                public void a(String str, int i) {
                }

                public void b(String str, int i) {
                }
            });
            a.c(context, new com.yaya.sdk.listener.a() {
                public void a(String str, int i) {
                }

                public void b(String str, int i) {
                }
            });
            new a().a(context);
        }
    }
}
