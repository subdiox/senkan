package com.tencent.bugly.crashreport.common.strategy;

import android.content.Context;
import android.os.Parcelable;
import com.tencent.bugly.crashreport.biz.b;
import com.tencent.bugly.proguard.ao;
import com.tencent.bugly.proguard.o;
import com.tencent.bugly.proguard.q;
import com.tencent.bugly.proguard.v;
import com.tencent.bugly.proguard.w;
import com.tencent.bugly.proguard.y;
import java.util.List;
import java.util.Map;

/* compiled from: BUGLY */
public final class a {
    public static int a = 1000;
    private static a b = null;
    private final List<com.tencent.bugly.a> c;
    private final v d;
    private final StrategyBean e;
    private StrategyBean f = null;
    private Context g;

    private a(Context context, List<com.tencent.bugly.a> list) {
        this.g = context;
        this.e = new StrategyBean();
        this.c = list;
        this.d = v.a();
    }

    public static synchronized a a(Context context, List<com.tencent.bugly.a> list) {
        a aVar;
        synchronized (a.class) {
            if (b == null) {
                b = new a(context, list);
            }
            aVar = b;
        }
        return aVar;
    }

    public final void a(long j) {
        this.d.a(new Thread(this) {
            private /* synthetic */ a a;

            {
                this.a = r1;
            }

            public final void run() {
                try {
                    Map a = o.a().a(a.a, null, true);
                    if (a != null) {
                        byte[] bArr = (byte[]) a.get("key_imei");
                        byte[] bArr2 = (byte[]) a.get("key_ip");
                        if (bArr != null) {
                            com.tencent.bugly.crashreport.common.info.a.a(this.a.g).e(new String(bArr));
                        }
                        if (bArr2 != null) {
                            com.tencent.bugly.crashreport.common.info.a.a(this.a.g).d(new String(bArr2));
                        }
                    }
                    a aVar = this.a;
                    this.a.f = a.d();
                } catch (Throwable th) {
                    if (!w.a(th)) {
                        th.printStackTrace();
                    }
                }
                this.a.a(this.a.f, false);
            }
        }, j);
    }

    public static synchronized a a() {
        a aVar;
        synchronized (a.class) {
            aVar = b;
        }
        return aVar;
    }

    public final synchronized boolean b() {
        return this.f != null;
    }

    public final StrategyBean c() {
        if (this.f != null) {
            return this.f;
        }
        return this.e;
    }

    protected final void a(StrategyBean strategyBean, boolean z) {
        w.c("[Strategy] Notify %s", b.class.getName());
        b.a(strategyBean, z);
        for (com.tencent.bugly.a aVar : this.c) {
            try {
                w.c("[Strategy] Notify %s", aVar.getClass().getName());
                aVar.onServerStrategyChanged(strategyBean);
            } catch (Throwable th) {
                if (!w.a(th)) {
                    th.printStackTrace();
                }
            }
        }
    }

    public final void a(ao aoVar) {
        if (aoVar != null) {
            if (this.f == null || aoVar.h != this.f.p) {
                StrategyBean strategyBean = new StrategyBean();
                strategyBean.g = aoVar.a;
                strategyBean.i = aoVar.c;
                strategyBean.h = aoVar.b;
                if (y.c(aoVar.d)) {
                    w.c("[Strategy] Upload url changes to %s", aoVar.d);
                    strategyBean.r = aoVar.d;
                }
                if (y.c(aoVar.e)) {
                    w.c("[Strategy] Exception upload url changes to %s", aoVar.e);
                    strategyBean.s = aoVar.e;
                }
                if (!(aoVar.f == null || y.a(aoVar.f.a))) {
                    strategyBean.u = aoVar.f.a;
                }
                if (aoVar.h != 0) {
                    strategyBean.p = aoVar.h;
                }
                if (aoVar.g != null && aoVar.g.size() > 0) {
                    strategyBean.v = aoVar.g;
                    String str = (String) aoVar.g.get("B11");
                    if (str == null || !str.equals("1")) {
                        strategyBean.j = false;
                    } else {
                        strategyBean.j = true;
                    }
                    str = (String) aoVar.g.get("B3");
                    if (str != null) {
                        strategyBean.y = Long.valueOf(str).longValue();
                    }
                    strategyBean.q = (long) aoVar.i;
                    strategyBean.x = (long) aoVar.i;
                    str = (String) aoVar.g.get("B27");
                    if (str != null && str.length() > 0) {
                        try {
                            int parseInt = Integer.parseInt(str);
                            if (parseInt > 0) {
                                strategyBean.w = parseInt;
                            }
                        } catch (Throwable e) {
                            if (!w.a(e)) {
                                e.printStackTrace();
                            }
                        }
                    }
                    str = (String) aoVar.g.get("B25");
                    if (str == null || !str.equals("1")) {
                        strategyBean.l = false;
                    } else {
                        strategyBean.l = true;
                    }
                }
                w.a("[Strategy] enableCrashReport:%b, enableQuery:%b, enableUserInfo:%b, enableAnr:%b, enableBlock:%b, enableSession:%b, enableSessionTimer:%b, sessionOverTime:%d, enableCocos:%b, strategyLastUpdateTime:%d", Boolean.valueOf(strategyBean.g), Boolean.valueOf(strategyBean.i), Boolean.valueOf(strategyBean.h), Boolean.valueOf(strategyBean.j), Boolean.valueOf(strategyBean.k), Boolean.valueOf(strategyBean.n), Boolean.valueOf(strategyBean.o), Long.valueOf(strategyBean.q), Boolean.valueOf(strategyBean.l), Long.valueOf(strategyBean.p));
                this.f = strategyBean;
                o.a().b(2);
                q qVar = new q();
                qVar.b = 2;
                qVar.a = strategyBean.e;
                qVar.e = strategyBean.f;
                qVar.g = y.a((Parcelable) strategyBean);
                o.a().a(qVar);
                a(strategyBean, true);
            }
        }
    }

    public static StrategyBean d() {
        List a = o.a().a(2);
        if (a != null && a.size() > 0) {
            q qVar = (q) a.get(0);
            if (qVar.g != null) {
                return (StrategyBean) y.a(qVar.g, StrategyBean.CREATOR);
            }
        }
        return null;
    }
}
