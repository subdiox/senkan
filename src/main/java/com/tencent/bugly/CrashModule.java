package com.tencent.bugly;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.bugly.BuglyStrategy.a;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.crashreport.crash.BuglyBroadcastRecevier;
import com.tencent.bugly.crashreport.crash.c;
import com.tencent.bugly.crashreport.crash.d;
import com.tencent.bugly.proguard.m;
import com.tencent.bugly.proguard.w;

/* compiled from: BUGLY */
public class CrashModule extends a {
    public static final int MODULE_ID = 1004;
    private static int c = 0;
    private static boolean d = false;
    private static CrashModule e = new CrashModule();
    private long a;
    private a b;

    public static CrashModule getInstance() {
        e.id = MODULE_ID;
        return e;
    }

    public static boolean hasInitialized() {
        return d;
    }

    public synchronized void init(Context context, boolean z, BuglyStrategy buglyStrategy) {
        if (context != null) {
            if (!d) {
                w.a("Initializing crash module.", new Object[0]);
                m a = m.a();
                int i = c + 1;
                c = i;
                a.a((int) MODULE_ID, i);
                d = true;
                CrashReport.setContext(context);
                a(context, buglyStrategy);
                c.a((int) MODULE_ID, context, z, this.b, null, null);
                c a2 = c.a();
                a2.e();
                if (buglyStrategy == null || buglyStrategy.isEnableNativeCrashMonitor()) {
                    a2.g();
                } else {
                    w.a("[crash] Closed native crash monitor!", new Object[0]);
                    a2.f();
                }
                if (buglyStrategy == null || buglyStrategy.isEnableANRCrashMonitor()) {
                    a2.h();
                } else {
                    w.a("[crash] Closed ANR monitor!", new Object[0]);
                    a2.i();
                }
                d.a(context);
                BuglyBroadcastRecevier instance = BuglyBroadcastRecevier.getInstance();
                instance.addFilter("android.net.conn.CONNECTIVITY_CHANGE");
                instance.regist(context);
                a = m.a();
                i = c - 1;
                c = i;
                a.a((int) MODULE_ID, i);
            }
        }
    }

    private synchronized void a(Context context, BuglyStrategy buglyStrategy) {
        if (buglyStrategy != null) {
            Object libBuglySOFilePath = buglyStrategy.getLibBuglySOFilePath();
            if (!TextUtils.isEmpty(libBuglySOFilePath)) {
                com.tencent.bugly.crashreport.common.info.a.a(context).m = libBuglySOFilePath;
                w.a("setted libBugly.so file path :%s", libBuglySOFilePath);
            }
            if (buglyStrategy.getCrashHandleCallback() != null) {
                this.b = buglyStrategy.getCrashHandleCallback();
                w.a("setted CrashHanldeCallback", new Object[0]);
            }
            if (buglyStrategy.getAppReportDelay() > 0) {
                this.a = buglyStrategy.getAppReportDelay();
                w.a("setted delay: %d", Long.valueOf(this.a));
            }
        }
    }

    public void onServerStrategyChanged(StrategyBean strategyBean) {
        if (strategyBean != null) {
            c a = c.a();
            if (a != null) {
                a.a(strategyBean);
            }
        }
    }

    public String[] getTables() {
        return new String[]{"t_cr"};
    }
}
