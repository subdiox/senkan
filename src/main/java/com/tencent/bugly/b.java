package com.tencent.bugly;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.bugly.crashreport.common.strategy.a;
import com.tencent.bugly.proguard.m;
import com.tencent.bugly.proguard.o;
import com.tencent.bugly.proguard.t;
import com.tencent.bugly.proguard.w;
import com.tencent.bugly.proguard.x;
import com.tencent.bugly.proguard.y;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: BUGLY */
public final class b {
    public static boolean a = true;
    public static List<a> b = new ArrayList();
    public static boolean c;
    private static o d;
    private static a e;
    private static m f;
    private static boolean g;

    private static boolean a(com.tencent.bugly.crashreport.common.info.a aVar) {
        List list = aVar.o;
        aVar.getClass();
        String str = "bugly";
        if (list == null || !list.contains(str)) {
            return false;
        }
        return true;
    }

    public static synchronized void a(Context context) {
        synchronized (b.class) {
            a(context, null);
        }
    }

    public static synchronized void a(Context context, BuglyStrategy buglyStrategy) {
        synchronized (b.class) {
            if (g) {
                w.d("[init] initial Multi-times, ignore this.", new Object[0]);
            } else if (context == null) {
                Log.w(w.a, "[init] context of init() is null, check it.");
            } else {
                com.tencent.bugly.crashreport.common.info.a a = com.tencent.bugly.crashreport.common.info.a.a(context);
                if (a(a)) {
                    a = false;
                } else {
                    String f = a.f();
                    if (f == null) {
                        Log.e(w.a, "[init] meta data of BUGLY_APPID in AndroidManifest.xml should be set.");
                    } else {
                        a(context, f, a.u, buglyStrategy);
                    }
                }
            }
        }
    }

    public static synchronized void a(Context context, String str, boolean z, BuglyStrategy buglyStrategy) {
        synchronized (b.class) {
            if (g) {
                w.d("[init] initial Multi-times, ignore this.", new Object[0]);
            } else if (context == null) {
                Log.w(w.a, "[init] context is null, check it.");
            } else if (str == null) {
                Log.e(w.a, "init arg 'crashReportAppID' should not be null!");
            } else {
                g = true;
                if (z) {
                    c = true;
                    w.b = true;
                    w.d("Bugly debug模式开启，请在发布时把isDebug关闭。 -- Running in debug model for 'isDebug' is enabled. Please disable it when you release.", new Object[0]);
                    w.e("--------------------------------------------------------------------------------------------", new Object[0]);
                    w.d("Bugly debug模式将有以下行为特性 -- The following list shows the behaviour of debug model: ", new Object[0]);
                    w.d("[1] 输出详细的Bugly SDK的Log -- More detailed log of Bugly SDK will be output to logcat;", new Object[0]);
                    w.d("[2] 每一条Crash都会被立即上报 -- Every crash caught by Bugly will be uploaded immediately.", new Object[0]);
                    w.d("[3] 自定义日志将会在Logcat中输出 -- Custom log will be output to logcat.", new Object[0]);
                    w.e("--------------------------------------------------------------------------------------------", new Object[0]);
                    w.b("[init] Open debug mode of Bugly.", new Object[0]);
                }
                w.a("[init] Bugly version: v%s", "2.4.1");
                w.a(" crash report start initializing...", new Object[0]);
                w.b("[init] Bugly start initializing...", new Object[0]);
                w.a("[init] Bugly complete version: v%s", "2.4.1");
                Context a = y.a(context);
                com.tencent.bugly.crashreport.common.info.a a2 = com.tencent.bugly.crashreport.common.info.a.a(a);
                a2.t();
                x.a(a);
                d = o.a(a, b);
                t.a(a);
                e = a.a(a, b);
                f = m.a(a);
                if (a(a2)) {
                    a = false;
                } else {
                    a2.a(str);
                    w.a("[param] Set APP ID:%s", str);
                    if (buglyStrategy != null) {
                        String substring;
                        String appVersion = buglyStrategy.getAppVersion();
                        if (!TextUtils.isEmpty(appVersion)) {
                            if (appVersion.length() > 100) {
                                substring = appVersion.substring(0, 100);
                                w.d("appVersion %s length is over limit %d substring to %s", appVersion, Integer.valueOf(100), substring);
                            } else {
                                substring = appVersion;
                            }
                            a2.j = substring;
                            w.a("[param] Set App version: %s", buglyStrategy.getAppVersion());
                        }
                        try {
                            if (buglyStrategy.isReplaceOldChannel()) {
                                appVersion = buglyStrategy.getAppChannel();
                                if (!TextUtils.isEmpty(appVersion)) {
                                    String str2;
                                    if (appVersion.length() > 100) {
                                        w.d("appChannel %s length is over limit %d substring to %s", appVersion, Integer.valueOf(100), appVersion.substring(0, 100));
                                        str2 = substring;
                                    } else {
                                        str2 = appVersion;
                                    }
                                    d.a(556, "app_channel", str2.getBytes(), null, false);
                                    a2.l = str2;
                                }
                            } else {
                                Map a3 = d.a(556, null, true);
                                if (a3 != null) {
                                    byte[] bArr = (byte[]) a3.get("app_channel");
                                    if (bArr != null) {
                                        a2.l = new String(bArr);
                                    }
                                }
                            }
                            w.a("[param] Set App channel: %s", a2.l);
                        } catch (Exception e) {
                            if (c) {
                                e.printStackTrace();
                            }
                        }
                        appVersion = buglyStrategy.getAppPackageName();
                        if (!TextUtils.isEmpty(appVersion)) {
                            if (appVersion.length() > 100) {
                                substring = appVersion.substring(0, 100);
                                w.d("appPackageName %s length is over limit %d substring to %s", appVersion, Integer.valueOf(100), substring);
                            } else {
                                substring = appVersion;
                            }
                            a2.c = substring;
                            w.a("[param] Set App package: %s", buglyStrategy.getAppPackageName());
                        }
                        appVersion = buglyStrategy.getDeviceID();
                        if (appVersion != null) {
                            if (appVersion.length() > 100) {
                                substring = appVersion.substring(0, 100);
                                w.d("deviceId %s length is over limit %d substring to %s", appVersion, Integer.valueOf(100), substring);
                            } else {
                                substring = appVersion;
                            }
                            a2.c(substring);
                            w.a("s[param] Set device ID: %s", substring);
                        }
                        a2.e = buglyStrategy.isUploadProcess();
                        x.a = buglyStrategy.isBuglyLogUpload();
                    }
                    com.tencent.bugly.crashreport.biz.b.a(a, buglyStrategy);
                    for (int i = 0; i < b.size(); i++) {
                        try {
                            if (f.a(((a) b.get(i)).id)) {
                                ((a) b.get(i)).init(a, z, buglyStrategy);
                            }
                        } catch (Throwable th) {
                            if (!w.a(th)) {
                                th.printStackTrace();
                            }
                        }
                    }
                    e.a(buglyStrategy != null ? buglyStrategy.getAppReportDelay() : 0);
                    w.b("[init] Bugly initialization finished.", new Object[0]);
                }
            }
        }
    }

    public static synchronized void a(a aVar) {
        synchronized (b.class) {
            if (!b.contains(aVar)) {
                b.add(aVar);
            }
        }
    }
}
