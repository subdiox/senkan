package com.kayac.lobi.libnakamap.rec;

import android.annotation.TargetApi;
import android.os.Build.VERSION;
import com.kayac.lobi.libnakamap.rec.a.b;

@TargetApi(16)
public class a {
    public static final boolean a;
    public static final boolean b;
    private static final String c = a.class.getSimpleName();
    private static final b d = new b(c);

    static {
        boolean z = true;
        boolean z2 = 16 <= VERSION.SDK_INT && VERSION.SDK_INT <= 23;
        a = z2;
        if (18 > VERSION.SDK_INT) {
            z = false;
        }
        b = z;
    }
}
