package com.tencent.bugly.crashreport.crash;

import android.content.Context;
import com.google.android.gcm.GCMRegistrar;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.CrashModule;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.crashreport.common.strategy.a;
import com.tencent.bugly.crashreport.crash.anr.b;
import com.tencent.bugly.crashreport.crash.jni.NativeCrashHandler;
import com.tencent.bugly.proguard.n;
import com.tencent.bugly.proguard.o;
import com.tencent.bugly.proguard.q;
import com.tencent.bugly.proguard.t;
import com.tencent.bugly.proguard.v;
import com.tencent.bugly.proguard.w;
import com.tencent.bugly.proguard.y;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: BUGLY */
public final class c {
    public static int a = 0;
    public static boolean b = false;
    public static boolean c = true;
    public static int d = 20000;
    public static int e = 20000;
    public static long f = GCMRegistrar.DEFAULT_ON_SERVER_LIFESPAN_MS;
    public static String g = null;
    public static boolean h = false;
    public static String i = null;
    public static int j = 5000;
    public static boolean k = true;
    public static String l = null;
    public static String m = null;
    private static c p;
    public final b n;
    private final Context o;
    private final e q;
    private final NativeCrashHandler r;
    private a s = a.a();
    private v t;
    private final b u;
    private Boolean v;

    private c(int i, Context context, v vVar, boolean z, BuglyStrategy.a aVar, n nVar, String str) {
        a = i;
        Context a = y.a(context);
        this.o = a;
        this.t = vVar;
        this.n = new b(i, a, t.a(), o.a(), this.s, aVar, nVar);
        com.tencent.bugly.crashreport.common.info.a a2 = com.tencent.bugly.crashreport.common.info.a.a(a);
        this.q = new e(a, this.n, this.s, a2);
        this.r = NativeCrashHandler.getInstance(a, a2, this.n, this.s, vVar, z, str);
        a2.C = this.r;
        this.u = new b(a, this.s, a2, vVar, this.n);
    }

    public static synchronized void a(int i, Context context, boolean z, BuglyStrategy.a aVar, n nVar, String str) {
        synchronized (c.class) {
            if (p == null) {
                p = new c(CrashModule.MODULE_ID, context, v.a(), z, aVar, null, null);
            }
        }
    }

    public static synchronized c a() {
        c cVar;
        synchronized (c.class) {
            cVar = p;
        }
        return cVar;
    }

    public final void a(StrategyBean strategyBean) {
        this.q.a(strategyBean);
        this.r.onStrategyChanged(strategyBean);
        this.u.a(strategyBean);
        v.a().a(new Thread(this) {
            private /* synthetic */ c a;

            {
                this.a = r1;
            }

            public final void run() {
                if (y.a(this.a.o, "local_crash_lock", 10000)) {
                    List a = this.a.n.a();
                    if (a != null && a.size() > 0) {
                        List arrayList;
                        int size = a.size();
                        if (((long) size) > 100) {
                            arrayList = new ArrayList();
                            Collections.sort(a);
                            for (int i = 0; ((long) i) < 100; i++) {
                                arrayList.add(a.get((size - 1) - i));
                            }
                        } else {
                            arrayList = a;
                        }
                        this.a.n.a(arrayList, 0, false, false, false);
                    }
                    y.b(this.a.o, "local_crash_lock");
                }
            }
        }, 0);
    }

    public final boolean b() {
        Boolean bool = this.v;
        if (bool != null) {
            return bool.booleanValue();
        }
        String str = com.tencent.bugly.crashreport.common.info.a.b().d;
        List<q> a = o.a().a(1);
        List arrayList = new ArrayList();
        if (a == null || a.size() <= 0) {
            this.v = Boolean.valueOf(false);
            return false;
        }
        for (q qVar : a) {
            if (str.equals(qVar.c)) {
                this.v = Boolean.valueOf(true);
                arrayList.add(qVar);
            }
        }
        if (arrayList.size() > 0) {
            o.a().a(arrayList);
        }
        return true;
    }

    public final synchronized void c() {
        this.q.a();
        this.r.setUserOpened(true);
        this.u.a(true);
    }

    public final synchronized void d() {
        this.q.b();
        this.r.setUserOpened(false);
        this.u.a(false);
    }

    public final void e() {
        this.q.a();
    }

    public final void f() {
        this.r.setUserOpened(false);
    }

    public final void g() {
        this.r.setUserOpened(true);
    }

    public final void h() {
        this.u.a(true);
    }

    public final void i() {
        this.u.a(false);
    }

    public final synchronized void j() {
        this.r.testNativeCrash();
    }

    public final synchronized void k() {
        int i = 0;
        synchronized (this) {
            b bVar = this.u;
            while (true) {
                int i2 = i + 1;
                if (i >= 30) {
                    break;
                }
                try {
                    w.a("try main sleep for make a test anr! try:%d/30 , kill it if you don't want to wait!", Integer.valueOf(i2));
                    y.b(5000);
                    i = i2;
                } catch (Throwable th) {
                    if (!w.a(th)) {
                        th.printStackTrace();
                    }
                }
            }
        }
    }

    public final boolean l() {
        return this.u.a();
    }

    public final void a(Thread thread, Throwable th, boolean z, String str, byte[] bArr, boolean z2) {
        final Thread thread2 = thread;
        final Throwable th2 = th;
        final byte[] bArr2 = null;
        final boolean z3 = z2;
        this.t.a(new Runnable(this, false, null) {
            private /* synthetic */ c g;

            public final void run() {
                try {
                    w.c("post a throwable %b", Boolean.valueOf(false));
                    this.g.q.a(thread2, th2, false, null, bArr2);
                    if (z3) {
                        w.a("clear user datas", new Object[0]);
                        com.tencent.bugly.crashreport.common.info.a.a(this.g.o).A();
                    }
                } catch (Throwable th) {
                    if (!w.b(th)) {
                        th.printStackTrace();
                    }
                    w.e("java catch error: %s", th2.toString());
                }
            }
        });
    }

    public final void a(CrashDetailBean crashDetailBean) {
        this.n.c(crashDetailBean);
    }

    public final void a(long j) {
        v.a().a(/* anonymous class already generated */, 0);
    }
}
