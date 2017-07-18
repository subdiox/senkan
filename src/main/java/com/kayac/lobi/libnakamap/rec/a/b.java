package com.kayac.lobi.libnakamap.rec.a;

import android.util.Log;
import com.kayac.lobi.libnakamap.rec.LobiRec;

public class b {
    public b(String str) {
    }

    public static void a(String str, String str2) {
        if (LobiRec.sLoggingEnabled) {
            Log.d(str, str2);
        }
    }

    public static void a(Throwable th) {
        if (LobiRec.sLoggingEnabled) {
            th.printStackTrace();
        }
    }

    public static void b(String str, String str2) {
        if (LobiRec.sLoggingEnabled) {
            Log.i(str, str2);
        }
    }

    public static void c(String str, String str2) {
        if (LobiRec.sLoggingEnabled) {
            Log.e(str, str2);
        }
    }

    public void a(String str) {
    }

    public void b(String str) {
    }

    public void c(String str) {
        if (LobiRec.sLoggingEnabled) {
            Log.w("LobiRecSDK", str);
        }
    }
}
