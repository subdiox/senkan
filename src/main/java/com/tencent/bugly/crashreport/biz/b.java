package com.tencent.bugly.crashreport.biz;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.os.EnvironmentCompat;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.crashreport.common.info.a;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.proguard.v;
import com.tencent.bugly.proguard.w;
import com.tencent.bugly.proguard.y;
import java.util.List;
import org.apache.commons.io.IOUtils;

/* compiled from: BUGLY */
public class b {
    public static a a;
    private static boolean b;
    private static int c = 10;
    private static long d = 300000;
    private static long e = 30000;
    private static long f = 0;
    private static int g;
    private static long h;
    private static long i;
    private static long j = 0;
    private static ActivityLifecycleCallbacks k = null;
    private static Class<?> l = null;
    private static boolean m = true;

    static /* synthetic */ String a(String str, String str2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(y.a());
        stringBuilder.append("  ");
        stringBuilder.append(str);
        stringBuilder.append("  ");
        stringBuilder.append(str2);
        stringBuilder.append(IOUtils.LINE_SEPARATOR_UNIX);
        return stringBuilder.toString();
    }

    static /* synthetic */ int g() {
        int i = g;
        g = i + 1;
        return i;
    }

    private static void c(Context context, BuglyStrategy buglyStrategy) {
        boolean isEnableUserInfo;
        boolean z;
        if (buglyStrategy != null) {
            boolean recordUserInfoOnceADay = buglyStrategy.recordUserInfoOnceADay();
            isEnableUserInfo = buglyStrategy.isEnableUserInfo();
            z = recordUserInfoOnceADay;
        } else {
            isEnableUserInfo = true;
            z = false;
        }
        if (z) {
            Object obj;
            a a = a.a(context);
            List a2 = a.a(a.d);
            if (a2 != null) {
                for (int i = 0; i < a2.size(); i++) {
                    UserInfoBean userInfoBean = (UserInfoBean) a2.get(i);
                    if (userInfoBean.n.equals(a.j) && userInfoBean.b == 1) {
                        long b = y.b();
                        if (b <= 0) {
                            break;
                        } else if (userInfoBean.e >= b) {
                            if (userInfoBean.f <= 0) {
                                a aVar = a;
                                v a3 = v.a();
                                if (a3 != null) {
                                    a3.a(/* anonymous class already generated */);
                                }
                            }
                            obj = null;
                            if (obj == null) {
                                isEnableUserInfo = false;
                            } else {
                                return;
                            }
                        }
                    }
                }
            }
            obj = 1;
            if (obj == null) {
                isEnableUserInfo = false;
            } else {
                return;
            }
        }
        a b2 = a.b();
        if (b2 != null) {
            Object obj2 = null;
            String str = null;
            for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
                if (stackTraceElement.getMethodName().equals("onCreate")) {
                    str = stackTraceElement.getClassName();
                }
                if (stackTraceElement.getClassName().equals("android.app.Activity")) {
                    obj2 = 1;
                }
            }
            if (str == null) {
                str = EnvironmentCompat.MEDIA_UNKNOWN;
            } else if (obj2 != null) {
                b2.a(true);
            } else {
                str = "background";
            }
            b2.p = str;
        }
        if (isEnableUserInfo) {
            Application application = null;
            if (VERSION.SDK_INT >= 14) {
                if (context.getApplicationContext() instanceof Application) {
                    application = (Application) context.getApplicationContext();
                }
                if (application != null) {
                    try {
                        if (k == null) {
                            k = new ActivityLifecycleCallbacks() {
                                public final void onActivityStopped(Activity activity) {
                                }

                                public final void onActivityStarted(Activity activity) {
                                }

                                public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                                }

                                public final void onActivityResumed(Activity activity) {
                                    String str = EnvironmentCompat.MEDIA_UNKNOWN;
                                    if (activity != null) {
                                        str = activity.getClass().getName();
                                    }
                                    if (b.l == null || b.l.getName().equals(str)) {
                                        w.c(">>> %s onResumed <<<", str);
                                        a b = a.b();
                                        if (b != null) {
                                            b.B.add(b.a(str, "onResumed"));
                                            b.a(true);
                                            b.p = str;
                                            b.q = System.currentTimeMillis();
                                            b.t = b.q - b.i;
                                            if (b.q - b.h > (b.f > 0 ? b.f : b.e)) {
                                                b.d();
                                                b.g();
                                                w.a("[session] launch app one times (app in background %d seconds and over %d seconds)", Long.valueOf(r4 / 1000), Long.valueOf(b.e / 1000));
                                                if (b.g % b.c == 0) {
                                                    b.a.a(4, b.m, 0);
                                                    return;
                                                }
                                                b.a.a(4, false, 0);
                                                long currentTimeMillis = System.currentTimeMillis();
                                                if (currentTimeMillis - b.j > b.d) {
                                                    b.j = currentTimeMillis;
                                                    w.a("add a timer to upload hot start user info", new Object[0]);
                                                    if (b.m) {
                                                        v.a().a(new a(b.a, null, true), b.d);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                                public final void onActivityPaused(Activity activity) {
                                    String str = EnvironmentCompat.MEDIA_UNKNOWN;
                                    if (activity != null) {
                                        str = activity.getClass().getName();
                                    }
                                    if (b.l == null || b.l.getName().equals(str)) {
                                        w.c(">>> %s onPaused <<<", str);
                                        a b = a.b();
                                        if (b != null) {
                                            b.B.add(b.a(str, "onPaused"));
                                            b.a(false);
                                            b.r = System.currentTimeMillis();
                                            b.s = b.r - b.q;
                                            b.h = b.r;
                                            if (b.s < 0) {
                                                b.s = 0;
                                            }
                                            if (activity != null) {
                                                b.p = "background";
                                            } else {
                                                b.p = EnvironmentCompat.MEDIA_UNKNOWN;
                                            }
                                        }
                                    }
                                }

                                public final void onActivityDestroyed(Activity activity) {
                                }

                                public final void onActivityCreated(Activity activity, Bundle bundle) {
                                    String str = EnvironmentCompat.MEDIA_UNKNOWN;
                                    if (activity != null) {
                                        str = activity.getClass().getName();
                                    }
                                    if (b.l == null || b.l.getName().equals(str)) {
                                        w.c(">>> %s onCreated <<<", str);
                                        a b = a.b();
                                        if (b != null) {
                                            b.B.add(b.a(str, "onCreated"));
                                        }
                                    }
                                }
                            };
                        }
                        application.registerActivityLifecycleCallbacks(k);
                    } catch (Throwable e) {
                        if (!w.a(e)) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        if (m) {
            i = System.currentTimeMillis();
            a.a(1, false, 0);
            w.a("[session] launch app, new start", new Object[0]);
            a.a();
            v.a().a(new c(a, 21600000), 21600000);
        }
    }

    public static void a(final Context context, final BuglyStrategy buglyStrategy) {
        if (!b) {
            long appReportDelay;
            m = a.a(context).e;
            a = new a(context, m);
            b = true;
            if (buglyStrategy != null) {
                l = buglyStrategy.getUserInfoActivity();
                appReportDelay = buglyStrategy.getAppReportDelay();
            } else {
                appReportDelay = 0;
            }
            if (appReportDelay <= 0) {
                c(context, buglyStrategy);
            } else {
                v.a().a(new Runnable() {
                    public final void run() {
                        b.c(context, buglyStrategy);
                    }
                }, appReportDelay);
            }
        }
    }

    public static void a(long j) {
        if (j < 0) {
            j = com.tencent.bugly.crashreport.common.strategy.a.a().c().q;
        }
        f = j;
    }

    public static void a(StrategyBean strategyBean, boolean z) {
        if (!(a == null || z)) {
            a aVar = a;
            v a = v.a();
            if (a != null) {
                a.a(/* anonymous class already generated */);
            }
        }
        if (strategyBean != null) {
            if (strategyBean.q > 0) {
                e = strategyBean.q;
            }
            if (strategyBean.w > 0) {
                c = strategyBean.w;
            }
            if (strategyBean.x > 0) {
                d = strategyBean.x;
            }
        }
    }

    public static void a() {
        if (a != null) {
            a.a(2, false, 0);
        }
    }

    public static void a(Context context) {
        if (b && context != null) {
            Application application = null;
            if (VERSION.SDK_INT >= 14) {
                if (context.getApplicationContext() instanceof Application) {
                    application = (Application) context.getApplicationContext();
                }
                if (application != null) {
                    try {
                        if (k != null) {
                            application.unregisterActivityLifecycleCallbacks(k);
                        }
                    } catch (Throwable e) {
                        if (!w.a(e)) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            b = false;
        }
    }
}
