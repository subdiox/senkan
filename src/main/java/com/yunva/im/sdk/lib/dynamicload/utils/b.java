package com.yunva.im.sdk.lib.dynamicload.utils;

import android.content.Context;
import com.yunva.im.sdk.lib.utils.a;
import dalvik.system.DexClassLoader;
import java.io.File;

public class b {
    private static DexClassLoader a = null;
    private static final boolean b = false;
    private static final String c = b.class.getSimpleName();

    public static DexClassLoader a(Context context, String str, String str2) {
        if (a == null) {
            b(context, str, str2);
            if (com.yunva.im.sdk.lib.utils.b.c() >= 14) {
                a = a(new StringBuilder(String.valueOf(str2)).append(File.separator).append(str).toString(), context.getDir("dex", 0).getAbsolutePath());
            } else {
                a = a(new StringBuilder(String.valueOf(str2)).append(File.separator).append(str).toString(), str2);
            }
        }
        return a;
    }

    public static void b(Context context, String str, String str2) {
        a.a(context, str, str2, false);
    }

    public static synchronized DexClassLoader a(String str, String str2) {
        DexClassLoader dexClassLoader = null;
        synchronized (b.class) {
            File file = new File(str);
            if (file.exists()) {
                dexClassLoader = new DexClassLoader(file.toString(), str2, null, ClassLoader.getSystemClassLoader().getParent());
            }
        }
        return dexClassLoader;
    }

    public static Boolean b(String str, String str2) {
        File file = new File(str2);
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            if (new File(file, str).exists()) {
                return Boolean.valueOf(true);
            }
        } catch (Exception e) {
            a.b(c, e.getStackTrace().toString());
        }
        return Boolean.valueOf(false);
    }
}
