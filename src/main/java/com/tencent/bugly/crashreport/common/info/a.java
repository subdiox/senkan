package com.tencent.bugly.crashreport.common.info;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Process;
import android.support.v4.os.EnvironmentCompat;
import com.tencent.bugly.b;
import com.tencent.bugly.proguard.w;
import com.tencent.bugly.proguard.y;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

/* compiled from: BUGLY */
public final class a {
    private static a Z = null;
    public HashMap<String, String> A = new HashMap();
    public List<String> B = new ArrayList();
    public com.tencent.bugly.crashreport.a C = null;
    private final Context D;
    private String E;
    private String F;
    private String G = EnvironmentCompat.MEDIA_UNKNOWN;
    private String H = EnvironmentCompat.MEDIA_UNKNOWN;
    private String I = "";
    private String J = null;
    private String K = null;
    private String L = null;
    private String M = null;
    private long N = -1;
    private long O = -1;
    private long P = -1;
    private String Q = null;
    private String R = null;
    private Map<String, PlugInBean> S = null;
    private boolean T = true;
    private String U = null;
    private String V = null;
    private Boolean W = null;
    private String X = null;
    private Map<String, PlugInBean> Y = null;
    public final long a = System.currentTimeMillis();
    private int aa = -1;
    private int ab = -1;
    private Map<String, String> ac = new HashMap();
    private Map<String, String> ad = new HashMap();
    private Map<String, String> ae = new HashMap();
    private boolean af;
    private String ag = null;
    private String ah = null;
    private String ai = null;
    private String aj = null;
    private String ak = null;
    private final Object al = new Object();
    private final Object am = new Object();
    private final Object an = new Object();
    private final Object ao = new Object();
    private final Object ap = new Object();
    private final Object aq = new Object();
    private final Object ar = new Object();
    public final byte b;
    public String c;
    public final String d;
    public boolean e = true;
    public final String f;
    public final String g;
    public final String h;
    public long i;
    public String j = null;
    public String k = null;
    public String l = null;
    public String m = null;
    public String n = null;
    public List<String> o = null;
    public String p = EnvironmentCompat.MEDIA_UNKNOWN;
    public long q = 0;
    public long r = 0;
    public long s = 0;
    public long t = 0;
    public boolean u = false;
    public String v = null;
    public String w = null;
    public String x = null;
    public boolean y = false;
    public boolean z = false;

    private a(Context context) {
        this.D = y.a(context);
        this.b = (byte) 1;
        PackageInfo b = AppInfo.b(context);
        if (b != null) {
            try {
                this.j = b.versionName;
                this.v = this.j;
                this.w = Integer.toString(b.versionCode);
            } catch (Throwable th) {
                if (!w.a(th)) {
                    th.printStackTrace();
                }
            }
        }
        this.c = AppInfo.a(context);
        this.d = AppInfo.a(Process.myPid());
        this.f = b.k();
        this.g = b.a();
        this.k = AppInfo.c(context);
        this.h = "Android " + b.b() + ",level " + b.c();
        this.g + ";" + this.h;
        Map d = AppInfo.d(context);
        if (d != null) {
            try {
                this.o = AppInfo.a(d);
                String str = (String) d.get("BUGLY_APPID");
                if (str != null) {
                    this.V = str;
                }
                str = (String) d.get("BUGLY_APP_VERSION");
                if (str != null) {
                    this.j = str;
                }
                str = (String) d.get("BUGLY_APP_CHANNEL");
                if (str != null) {
                    this.l = str;
                }
                str = (String) d.get("BUGLY_ENABLE_DEBUG");
                if (str != null) {
                    this.u = str.equalsIgnoreCase("true");
                }
                str = (String) d.get("com.tencent.rdm.uuid");
                if (str != null) {
                    this.x = str;
                }
            } catch (Throwable th2) {
                if (!w.a(th2)) {
                    th2.printStackTrace();
                }
            }
        }
        try {
            if (!context.getDatabasePath("bugly_db_").exists()) {
                this.z = true;
                w.c("App is first time to be installed on the device.", new Object[0]);
            }
        } catch (Throwable th22) {
            if (b.c) {
                th22.printStackTrace();
            }
        }
        w.c("com info create end", new Object[0]);
    }

    public final boolean a() {
        return this.af;
    }

    public final void a(boolean z) {
        this.af = z;
        if (this.C != null) {
            this.C.setNativeIsAppForeground(z);
        }
    }

    public static synchronized a a(Context context) {
        a aVar;
        synchronized (a.class) {
            if (Z == null) {
                Z = new a(context);
            }
            aVar = Z;
        }
        return aVar;
    }

    public static synchronized a b() {
        a aVar;
        synchronized (a.class) {
            aVar = Z;
        }
        return aVar;
    }

    public static String c() {
        return "2.4.1";
    }

    public final void d() {
        synchronized (this.al) {
            this.E = UUID.randomUUID().toString();
        }
    }

    public final String e() {
        if (this.E == null) {
            synchronized (this.al) {
                if (this.E == null) {
                    this.E = UUID.randomUUID().toString();
                }
            }
        }
        return this.E;
    }

    public final String f() {
        if (y.a(null)) {
            return this.V;
        }
        return null;
    }

    public final void a(String str) {
        this.V = str;
    }

    public final String g() {
        String str;
        synchronized (this.aq) {
            str = this.G;
        }
        return str;
    }

    public final void b(String str) {
        synchronized (this.aq) {
            if (str == null) {
                str = "10000";
            }
            this.G = str;
        }
    }

    public final String h() {
        if (this.F != null) {
            return this.F;
        }
        this.F = k() + "|" + m() + "|" + n();
        return this.F;
    }

    public final void c(String str) {
        this.F = str;
    }

    public final synchronized String i() {
        return this.H;
    }

    public final synchronized void d(String str) {
        this.H = str;
    }

    public final synchronized String j() {
        return this.I;
    }

    public final synchronized void e(String str) {
        this.I = str;
    }

    public final String k() {
        if (!this.T) {
            return "";
        }
        if (this.J == null) {
            this.J = b.a(this.D);
        }
        return this.J;
    }

    public final String l() {
        if (!this.T) {
            return "";
        }
        if (this.K == null) {
            this.K = b.d(this.D);
        }
        return this.K;
    }

    public final String m() {
        if (!this.T) {
            return "";
        }
        if (this.L == null) {
            this.L = b.b(this.D);
        }
        return this.L;
    }

    public final String n() {
        if (!this.T) {
            return "";
        }
        if (this.M == null) {
            this.M = b.c(this.D);
        }
        return this.M;
    }

    public final long o() {
        if (this.N <= 0) {
            this.N = b.d();
        }
        return this.N;
    }

    public final long p() {
        if (this.O <= 0) {
            this.O = b.f();
        }
        return this.O;
    }

    public final long q() {
        if (this.P <= 0) {
            this.P = b.h();
        }
        return this.P;
    }

    public final String r() {
        if (this.Q == null) {
            this.Q = b.a(true);
        }
        return this.Q;
    }

    public final String s() {
        if (this.R == null) {
            this.R = b.g(this.D);
        }
        return this.R;
    }

    public final void a(String str, String str2) {
        if (str != null && str2 != null) {
            synchronized (this.am) {
                this.A.put(str, str2);
            }
        }
    }

    public final String t() {
        try {
            Map all = this.D.getSharedPreferences("BuglySdkInfos", 0).getAll();
            if (!all.isEmpty()) {
                synchronized (this.am) {
                    for (Entry entry : all.entrySet()) {
                        try {
                            this.A.put(entry.getKey(), entry.getValue().toString());
                        } catch (Throwable th) {
                            w.a(th);
                        }
                    }
                }
            }
        } catch (Throwable th2) {
            w.a(th2);
        }
        if (this.A.isEmpty()) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Entry entry2 : this.A.entrySet()) {
            stringBuilder.append("[");
            stringBuilder.append((String) entry2.getKey());
            stringBuilder.append(",");
            stringBuilder.append((String) entry2.getValue());
            stringBuilder.append("] ");
        }
        c("SDK_INFO", stringBuilder.toString());
        return stringBuilder.toString();
    }

    public final String u() {
        if (this.ak == null) {
            this.ak = AppInfo.e(this.D);
        }
        return this.ak;
    }

    public final synchronized Map<String, PlugInBean> v() {
        return null;
    }

    public final String w() {
        if (this.U == null) {
            this.U = b.j();
        }
        return this.U;
    }

    public final Boolean x() {
        if (this.W == null) {
            this.W = Boolean.valueOf(b.h(this.D));
        }
        return this.W;
    }

    public final String y() {
        if (this.X == null) {
            this.X = b.f(this.D);
            w.a("rom:%s", this.X);
        }
        return this.X;
    }

    public final Map<String, String> z() {
        Map<String, String> map;
        synchronized (this.an) {
            if (this.ac.size() <= 0) {
                map = null;
            } else {
                map = new HashMap(this.ac);
            }
        }
        return map;
    }

    public final String f(String str) {
        if (y.a(str)) {
            w.d("key should not be empty %s", str);
            return null;
        }
        String str2;
        synchronized (this.an) {
            str2 = (String) this.ac.remove(str);
        }
        return str2;
    }

    public final void A() {
        synchronized (this.an) {
            this.ac.clear();
        }
    }

    public final String g(String str) {
        if (y.a(str)) {
            w.d("key should not be empty %s", str);
            return null;
        }
        String str2;
        synchronized (this.an) {
            str2 = (String) this.ac.get(str);
        }
        return str2;
    }

    public final void b(String str, String str2) {
        if (y.a(str) || y.a(str2)) {
            w.d("key&value should not be empty %s %s", str, str2);
            return;
        }
        synchronized (this.an) {
            this.ac.put(str, str2);
        }
    }

    public final int B() {
        int size;
        synchronized (this.an) {
            size = this.ac.size();
        }
        return size;
    }

    public final Set<String> C() {
        Set<String> keySet;
        synchronized (this.an) {
            keySet = this.ac.keySet();
        }
        return keySet;
    }

    public final Map<String, String> D() {
        Map<String, String> map;
        synchronized (this.ar) {
            if (this.ad.size() <= 0) {
                map = null;
            } else {
                map = new HashMap(this.ad);
            }
        }
        return map;
    }

    public final void c(String str, String str2) {
        if (y.a(str) || y.a(str2)) {
            w.d("server key&value should not be empty %s %s", str, str2);
            return;
        }
        synchronized (this.ao) {
            this.ae.put(str, str2);
        }
    }

    public final Map<String, String> E() {
        Map<String, String> map;
        synchronized (this.ao) {
            if (this.ae.size() <= 0) {
                map = null;
            } else {
                map = new HashMap(this.ae);
            }
        }
        return map;
    }

    public final void a(int i) {
        synchronized (this.ap) {
            if (this.aa != i) {
                this.aa = i;
                w.a("user scene tag %d changed to tag %d", Integer.valueOf(r0), Integer.valueOf(this.aa));
            }
        }
    }

    public final int F() {
        int i;
        synchronized (this.ap) {
            i = this.aa;
        }
        return i;
    }

    public final void b(int i) {
        if (this.ab != 24096) {
            this.ab = 24096;
            w.a("server scene tag %d changed to tag %d", Integer.valueOf(r0), Integer.valueOf(this.ab));
        }
    }

    public final int G() {
        return this.ab;
    }

    public final boolean H() {
        return AppInfo.f(this.D);
    }

    public final synchronized Map<String, PlugInBean> I() {
        return null;
    }

    public static int J() {
        return b.c();
    }

    public final String K() {
        if (this.ag == null) {
            this.ag = b.l();
        }
        return this.ag;
    }

    public final String L() {
        if (this.ah == null) {
            this.ah = b.i(this.D);
        }
        return this.ah;
    }

    public final String M() {
        if (this.ai == null) {
            this.ai = b.j(this.D);
        }
        return this.ai;
    }

    public final String N() {
        Context context = this.D;
        return b.m();
    }

    public final String O() {
        if (this.aj == null) {
            this.aj = b.k(this.D);
        }
        return this.aj;
    }

    public final long P() {
        Context context = this.D;
        return b.n();
    }
}
