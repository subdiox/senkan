package com.tencent.bugly.crashreport.crash.jni;

import android.annotation.SuppressLint;
import android.content.Context;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.a;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.crashreport.crash.CrashDetailBean;
import com.tencent.bugly.crashreport.crash.b;
import com.tencent.bugly.crashreport.crash.c;
import com.tencent.bugly.proguard.v;
import com.tencent.bugly.proguard.w;
import com.tencent.bugly.proguard.y;
import java.io.File;

/* compiled from: BUGLY */
public class NativeCrashHandler implements a {
    private static NativeCrashHandler a;
    private static boolean l = false;
    private static boolean m = false;
    private final Context b;
    private final com.tencent.bugly.crashreport.common.info.a c;
    private final v d;
    private NativeExceptionHandler e;
    private String f;
    private final boolean g;
    private boolean h = false;
    private boolean i = false;
    private boolean j = false;
    private boolean k = false;
    private b n;

    protected native boolean appendNativeLog(String str, String str2, String str3);

    protected native boolean appendWholeNativeLog(String str);

    protected native String getNativeKeyValueList();

    protected native String getNativeLog();

    protected native boolean putNativeKeyValue(String str, String str2);

    protected native String regist(String str, boolean z, int i);

    protected native String removeNativeKeyValue(String str);

    protected native void setNativeInfo(int i, String str);

    protected native void testCrash();

    protected native String unregist();

    @SuppressLint({"SdCardPath"})
    private NativeCrashHandler(Context context, com.tencent.bugly.crashreport.common.info.a aVar, b bVar, v vVar, boolean z, String str) {
        this.b = y.a(context);
        try {
            if (y.a(str)) {
                str = context.getDir("bugly", 0).getAbsolutePath();
            }
        } catch (Throwable th) {
            str = "/data/data/" + com.tencent.bugly.crashreport.common.info.a.a(context).c + "/app_bugly";
        }
        this.n = bVar;
        this.f = str;
        this.c = aVar;
        this.d = vVar;
        this.g = z;
    }

    public static synchronized NativeCrashHandler getInstance(Context context, com.tencent.bugly.crashreport.common.info.a aVar, b bVar, com.tencent.bugly.crashreport.common.strategy.a aVar2, v vVar, boolean z, String str) {
        NativeCrashHandler nativeCrashHandler;
        synchronized (NativeCrashHandler.class) {
            if (a == null) {
                a = new NativeCrashHandler(context, aVar, bVar, vVar, z, str);
            }
            nativeCrashHandler = a;
        }
        return nativeCrashHandler;
    }

    public static synchronized NativeCrashHandler getInstance() {
        NativeCrashHandler nativeCrashHandler;
        synchronized (NativeCrashHandler.class) {
            nativeCrashHandler = a;
        }
        return nativeCrashHandler;
    }

    public synchronized String getDumpFilePath() {
        return this.f;
    }

    public synchronized void setDumpFilePath(String str) {
        this.f = str;
    }

    private synchronized void a(boolean z) {
        if (this.j) {
            w.d("[Native] Native crash report has already registered.", new Object[0]);
        } else {
            this.e = new a(this.b, this.c, this.n, com.tencent.bugly.crashreport.common.strategy.a.a(), this.f);
            String replace;
            if (this.i) {
                try {
                    String regist = regist(this.f, z, 1);
                    if (regist != null) {
                        w.a("[Native] Native Crash Report enable.", new Object[0]);
                        w.c("[Native] Check extra jni for Bugly NDK v%s", regist);
                        String replace2 = "2.1.1".replace(".", "");
                        String replace3 = "2.3.0".replace(".", "");
                        replace = regist.replace(".", "");
                        if (replace.length() == 2) {
                            replace = replace + "0";
                        } else if (replace.length() == 1) {
                            replace = replace + "00";
                        }
                        try {
                            if (Integer.parseInt(replace) >= Integer.parseInt(replace2)) {
                                l = true;
                            }
                            if (Integer.parseInt(replace) >= Integer.parseInt(replace3)) {
                                m = true;
                            }
                        } catch (Throwable th) {
                        }
                        if (m) {
                            w.a("[Native] Info setting jni can be accessed.", new Object[0]);
                        } else {
                            w.d("[Native] Info setting jni can not be accessed.", new Object[0]);
                        }
                        if (l) {
                            w.a("[Native] Extra jni can be accessed.", new Object[0]);
                        } else {
                            w.d("[Native] Extra jni can not be accessed.", new Object[0]);
                        }
                        this.c.n = regist;
                        this.j = true;
                    }
                } catch (Throwable th2) {
                    w.c("[Native] Failed to load Bugly SO file.", new Object[0]);
                }
            } else if (this.h) {
                try {
                    Class[] clsArr = new Class[]{String.class, String.class, Integer.TYPE, Integer.TYPE};
                    r4 = new Object[4];
                    com.tencent.bugly.crashreport.common.info.a.b();
                    r4[2] = Integer.valueOf(com.tencent.bugly.crashreport.common.info.a.J());
                    r4[3] = Integer.valueOf(1);
                    replace = (String) y.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "registNativeExceptionHandler2", null, clsArr, r4);
                    if (replace == null) {
                        clsArr = new Class[]{String.class, String.class, Integer.TYPE};
                        r4 = new Object[3];
                        r4[0] = this.f;
                        r4[1] = com.tencent.bugly.crashreport.common.info.b.a(false);
                        com.tencent.bugly.crashreport.common.info.a.b();
                        r4[2] = Integer.valueOf(com.tencent.bugly.crashreport.common.info.a.J());
                        replace = (String) y.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "registNativeExceptionHandler", null, clsArr, r4);
                    }
                    if (replace != null) {
                        this.j = true;
                        com.tencent.bugly.crashreport.common.info.a.b().n = replace;
                        y.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "enableHandler", null, new Class[]{Boolean.TYPE}, new Object[]{Boolean.valueOf(true)});
                        int i = com.tencent.bugly.b.c ? 3 : 5;
                        y.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "setLogMode", null, new Class[]{Integer.TYPE}, new Object[]{Integer.valueOf(i)});
                    }
                } catch (Throwable th3) {
                }
            }
            this.i = false;
            this.h = false;
        }
    }

    public synchronized void startNativeMonitor() {
        if (this.i || this.h) {
            a(this.g);
        } else {
            String str;
            if (!y.a(this.c.m)) {
                str = this.c.m;
            }
            str = "Bugly";
            this.c.getClass();
            this.i = a(y.a(this.c.m) ? str : this.c.m, !y.a(this.c.m));
            if (this.i || this.h) {
                a(this.g);
                this.d.a(new Runnable(this) {
                    private /* synthetic */ NativeCrashHandler a;

                    {
                        this.a = r1;
                    }

                    public final void run() {
                        if (y.a(this.a.b, "native_record_lock", 10000)) {
                            try {
                                this.a.setNativeAppVersion(this.a.c.j);
                                this.a.setNativeAppChannel(this.a.c.l);
                                this.a.setNativeAppPackage(this.a.c.c);
                                this.a.setNativeUserId(this.a.c.g());
                                this.a.setNativeIsAppForeground(this.a.c.a());
                                this.a.setNativeLaunchTime(this.a.c.a);
                            } catch (Throwable th) {
                                if (!w.a(th)) {
                                    th.printStackTrace();
                                }
                            }
                            CrashDetailBean a = b.a(this.a.b, this.a.f, this.a.e);
                            if (a != null) {
                                w.a("[Native] Get crash from native record.", new Object[0]);
                                if (!this.a.n.a(a)) {
                                    this.a.n.a(a, 3000, false);
                                }
                                b.b(this.a.f);
                            }
                            this.a.a();
                            y.b(this.a.b, "native_record_lock");
                            return;
                        }
                        w.a("[Native] Failed to lock file for handling native crash record.", new Object[0]);
                    }
                });
            }
        }
    }

    private static boolean a(String str, boolean z) {
        Throwable th;
        boolean z2;
        try {
            w.a("[Native] Trying to load so: %s", str);
            if (z) {
                System.load(str);
            } else {
                System.loadLibrary(str);
            }
            try {
                w.a("[Native] Successfully loaded SO: %s", str);
                return true;
            } catch (Throwable th2) {
                th = th2;
                z2 = true;
            }
        } catch (Throwable th22) {
            th = th22;
            z2 = false;
            w.d(th.getMessage(), new Object[0]);
            w.d("[Native] Failed to load so: %s", str);
            return z2;
        }
    }

    private synchronized void b() {
        if (this.j) {
            try {
                if (unregist() != null) {
                    w.a("[Native] Successfully closed native crash report.", new Object[0]);
                    this.j = false;
                }
            } catch (Throwable th) {
                w.c("[Native] Failed to close native crash report.", new Object[0]);
            }
            try {
                y.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "enableHandler", null, new Class[]{Boolean.TYPE}, new Object[]{Boolean.valueOf(false)});
                this.j = false;
                w.a("[Native] Successfully closed native crash report.", new Object[0]);
            } catch (Throwable th2) {
                w.c("[Native] Failed to close native crash report.", new Object[0]);
                this.i = false;
                this.h = false;
            }
        } else {
            w.d("[Native] Native crash report has already unregistered.", new Object[0]);
        }
        return;
    }

    public void testNativeCrash() {
        if (this.i) {
            testCrash();
        } else {
            w.d("[Native] Bugly SO file has not been load.", new Object[0]);
        }
    }

    public NativeExceptionHandler getNativeExceptionHandler() {
        return this.e;
    }

    protected final void a() {
        long b = y.b() - c.f;
        File file = new File(this.f);
        if (file.exists() && file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null && listFiles.length != 0) {
                String str = "tomb_";
                String str2 = ".txt";
                int length = str.length();
                int i = 0;
                for (File file2 : listFiles) {
                    String name = file2.getName();
                    if (name.startsWith(str)) {
                        try {
                            int indexOf = name.indexOf(str2);
                            if (indexOf > 0 && Long.parseLong(name.substring(length, indexOf)) >= b) {
                            }
                        } catch (Throwable th) {
                            w.e("[Native] Tomb file format error, delete %s", name);
                        }
                        if (file2.delete()) {
                            i++;
                        }
                    }
                }
                w.c("[Native] Clean tombs %d", Integer.valueOf(i));
            }
        }
    }

    private synchronized void b(boolean z) {
        if (z) {
            startNativeMonitor();
        } else {
            b();
        }
    }

    public synchronized boolean isUserOpened() {
        return this.k;
    }

    private synchronized void c(boolean z) {
        if (this.k != z) {
            w.a("user change native %b", Boolean.valueOf(z));
            this.k = z;
        }
    }

    public synchronized void setUserOpened(boolean z) {
        boolean z2 = true;
        synchronized (this) {
            c(z);
            boolean isUserOpened = isUserOpened();
            com.tencent.bugly.crashreport.common.strategy.a a = com.tencent.bugly.crashreport.common.strategy.a.a();
            if (a == null) {
                z2 = isUserOpened;
            } else if (!(isUserOpened && a.c().g)) {
                z2 = false;
            }
            if (z2 != this.j) {
                w.a("native changed to %b", Boolean.valueOf(z2));
                b(z2);
            }
        }
    }

    public synchronized void onStrategyChanged(StrategyBean strategyBean) {
        boolean z = true;
        synchronized (this) {
            if (strategyBean != null) {
                if (strategyBean.g != this.j) {
                    w.d("server native changed to %b", Boolean.valueOf(strategyBean.g));
                }
            }
            if (!(com.tencent.bugly.crashreport.common.strategy.a.a().c().g && this.k)) {
                z = false;
            }
            if (z != this.j) {
                w.a("native changed to %b", Boolean.valueOf(z));
                b(z);
            }
        }
    }

    public boolean appendLogToNative(String str, String str2, String str3) {
        boolean z = false;
        if (!(!this.i || !l || str == null || str2 == null || str3 == null)) {
            try {
                z = appendNativeLog(str, str2, str3);
            } catch (UnsatisfiedLinkError e) {
                l = z;
            } catch (Throwable th) {
                if (!w.a(th)) {
                    th.printStackTrace();
                }
            }
        }
        return z;
    }

    public boolean putKeyValueToNative(String str, String str2) {
        boolean z = false;
        if (this.i && l && str != null && str2 != null) {
            try {
                z = putNativeKeyValue(str, str2);
            } catch (UnsatisfiedLinkError e) {
                l = z;
            } catch (Throwable th) {
                if (!w.a(th)) {
                    th.printStackTrace();
                }
            }
        }
        return z;
    }

    private boolean a(int i, String str) {
        if (!this.i || !m) {
            return false;
        }
        try {
            setNativeInfo(i, str);
            return true;
        } catch (UnsatisfiedLinkError e) {
            m = false;
            return false;
        } catch (Throwable th) {
            if (w.a(th)) {
                return false;
            }
            th.printStackTrace();
            return false;
        }
    }

    public boolean setNativeAppVersion(String str) {
        return a(10, str);
    }

    public boolean setNativeAppChannel(String str) {
        return a(12, str);
    }

    public boolean setNativeAppPackage(String str) {
        return a(13, str);
    }

    public boolean setNativeUserId(String str) {
        return a(11, str);
    }

    public boolean setNativeIsAppForeground(boolean z) {
        return a(14, z ? "true" : Bugly.SDK_IS_DEV);
    }

    public boolean setNativeLaunchTime(long j) {
        try {
            return a(15, String.valueOf(j));
        } catch (Throwable e) {
            if (!w.a(e)) {
                e.printStackTrace();
            }
            return false;
        }
    }
}
