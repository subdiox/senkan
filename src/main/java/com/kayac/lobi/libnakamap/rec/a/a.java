package com.kayac.lobi.libnakamap.rec.a;

import android.opengl.GLES20;
import android.os.Build;
import android.os.Build.VERSION;

public class a {
    private static final b a = new b("build");

    public static void a() {
        a.a("BOARD:" + Build.BOARD);
        a.a("BOOTLOADER:" + Build.BOOTLOADER);
        a.a("BRAND:" + Build.BRAND);
        a.a("CPU_ABI:" + Build.CPU_ABI);
        a.a("CPU_ABI2:" + Build.CPU_ABI2);
        a.a("DEVICE:" + Build.DEVICE);
        a.a("DISPLAY:" + Build.DISPLAY);
        a.a("FINGERPRINT:" + Build.FINGERPRINT);
        a.a("HARDWARE:" + Build.HARDWARE);
        a.a("HOST:" + Build.HOST);
        a.a("ID:" + Build.ID);
        a.a("MANUFACTURER:" + Build.MANUFACTURER);
        a.a("MODEL:" + Build.MODEL);
        a.a("PRODUCT:" + Build.PRODUCT);
        a.a("RADIO:" + Build.RADIO);
        a.a("TAGS:" + Build.TAGS);
        a.a("TIME:" + Build.TIME);
        a.a("TYPE:" + Build.TYPE);
        a.a("UNKNOWN:unknown");
        a.a("USER:" + Build.USER);
        a.a("VERSION.CODENAME:" + VERSION.CODENAME);
        a.a("VERSION.INCREMENTAL:" + VERSION.INCREMENTAL);
        a.a("VERSION.RELEASE:" + VERSION.RELEASE);
        a.a("VERSION.SDK:" + VERSION.SDK);
        a.a("VERSION.SDK_INT:" + VERSION.SDK_INT);
    }

    public static void b() {
        a.a("*** OPEN GL ***");
        a.a("GL_VENDOR:" + GLES20.glGetString(7936));
        a.a("GL_RENDERER:" + GLES20.glGetString(7937));
        a.a("GL_VERSION:" + GLES20.glGetString(7938));
        int[] iArr = new int[]{0};
        GLES20.glGetIntegerv(3379, iArr, 0);
        a.a("GL_MAX_TEXTURE_SIZE:" + iArr[0]);
        a.a("GL_EXTENSIONS" + GLES20.glGetString(7939));
    }
}
