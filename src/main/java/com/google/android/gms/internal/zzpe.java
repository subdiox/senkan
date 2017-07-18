package com.google.android.gms.internal;

import android.util.Log;

@zzmb
public final class zzpe extends zzpy {
    public static void v(String str) {
        if (zzkh()) {
            Log.v("Ads", str);
        }
    }

    public static boolean zzkg() {
        return ((Boolean) zzfx.zzCL.get()).booleanValue();
    }

    public static boolean zzkh() {
        return zzai(2) && zzkg();
    }
}
