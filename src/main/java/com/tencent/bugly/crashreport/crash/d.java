package com.tencent.bugly.crashreport.crash;

import android.content.Context;
import com.tencent.bugly.crashreport.common.strategy.a;
import com.tencent.bugly.proguard.v;
import com.tencent.bugly.proguard.w;
import com.tencent.bugly.proguard.y;
import java.util.Map;

/* compiled from: BUGLY */
public final class d {
    private static d a = null;
    private a b;
    private com.tencent.bugly.crashreport.common.info.a c;
    private b d;
    private Context e;

    static /* synthetic */ void a(d dVar) {
        w.c("[ExtraCrashManager] Trying to notify Bugly agents.", new Object[0]);
        try {
            Class cls = Class.forName("com.tencent.bugly.agent.GameAgent");
            Object obj = "com.tencent.bugly";
            dVar.c.getClass();
            String str = "";
            if (!"".equals(str)) {
                obj = obj + "." + str;
            }
            y.a(cls, "sdkPackageName", obj, null);
            w.c("[ExtraCrashManager] Bugly game agent has been notified.", new Object[0]);
        } catch (Throwable th) {
            w.a("[ExtraCrashManager] no game agent", new Object[0]);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void a(com.tencent.bugly.crashreport.crash.d r8, java.lang.Thread r9, int r10, java.lang.String r11, java.lang.String r12, java.lang.String r13, java.util.Map r14) {
        /*
        r2 = 1;
        r6 = 0;
        switch(r10) {
            case 4: goto L_0x00a3;
            case 5: goto L_0x0013;
            case 6: goto L_0x0013;
            case 7: goto L_0x0005;
            case 8: goto L_0x00a7;
            default: goto L_0x0005;
        };
    L_0x0005:
        r0 = "[ExtraCrashManager] Unknown extra crash type: %d";
        r1 = new java.lang.Object[r2];
        r2 = java.lang.Integer.valueOf(r10);
        r1[r6] = r2;
        com.tencent.bugly.proguard.w.d(r0, r1);
    L_0x0012:
        return;
    L_0x0013:
        r0 = "Cocos";
    L_0x0015:
        r1 = "[ExtraCrashManager] %s Crash Happen";
        r2 = new java.lang.Object[r2];
        r2[r6] = r0;
        com.tencent.bugly.proguard.w.e(r1, r2);
        r1 = r8.b;	 Catch:{ Throwable -> 0x028d }
        r1 = r1.b();	 Catch:{ Throwable -> 0x028d }
        if (r1 != 0) goto L_0x0042;
    L_0x0026:
        r1 = "waiting for remote sync";
        r2 = 0;
        r2 = new java.lang.Object[r2];	 Catch:{ Throwable -> 0x028d }
        com.tencent.bugly.proguard.w.e(r1, r2);	 Catch:{ Throwable -> 0x028d }
        r1 = r6;
    L_0x002f:
        r2 = r8.b;	 Catch:{ Throwable -> 0x028d }
        r2 = r2.b();	 Catch:{ Throwable -> 0x028d }
        if (r2 != 0) goto L_0x0042;
    L_0x0037:
        r2 = 500; // 0x1f4 float:7.0E-43 double:2.47E-321;
        com.tencent.bugly.proguard.y.b(r2);	 Catch:{ Throwable -> 0x028d }
        r1 = r1 + 500;
        r2 = 3000; // 0xbb8 float:4.204E-42 double:1.482E-320;
        if (r1 < r2) goto L_0x002f;
    L_0x0042:
        r1 = r8.b;	 Catch:{ Throwable -> 0x028d }
        r1 = r1.b();	 Catch:{ Throwable -> 0x028d }
        if (r1 != 0) goto L_0x0052;
    L_0x004a:
        r1 = "[ExtraCrashManager] There is no remote strategy, but still store it.";
        r2 = 0;
        r2 = new java.lang.Object[r2];	 Catch:{ Throwable -> 0x028d }
        com.tencent.bugly.proguard.w.d(r1, r2);	 Catch:{ Throwable -> 0x028d }
    L_0x0052:
        r1 = r8.b;	 Catch:{ Throwable -> 0x028d }
        r1 = r1.c();	 Catch:{ Throwable -> 0x028d }
        r2 = r1.g;	 Catch:{ Throwable -> 0x028d }
        if (r2 != 0) goto L_0x00ab;
    L_0x005c:
        r2 = r8.b;	 Catch:{ Throwable -> 0x028d }
        r2 = r2.b();	 Catch:{ Throwable -> 0x028d }
        if (r2 == 0) goto L_0x00ab;
    L_0x0064:
        r1 = "[ExtraCrashManager] Crash report was closed by remote , will not upload to Bugly , print local for helpful!";
        r2 = 0;
        r2 = new java.lang.Object[r2];	 Catch:{ Throwable -> 0x028d }
        com.tencent.bugly.proguard.w.e(r1, r2);	 Catch:{ Throwable -> 0x028d }
        r1 = com.tencent.bugly.proguard.y.a();	 Catch:{ Throwable -> 0x028d }
        r2 = r8.c;	 Catch:{ Throwable -> 0x028d }
        r2 = r2.d;	 Catch:{ Throwable -> 0x028d }
        r3 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x028d }
        r3.<init>();	 Catch:{ Throwable -> 0x028d }
        r3 = r3.append(r11);	 Catch:{ Throwable -> 0x028d }
        r4 = "\n";
        r3 = r3.append(r4);	 Catch:{ Throwable -> 0x028d }
        r3 = r3.append(r12);	 Catch:{ Throwable -> 0x028d }
        r4 = "\n";
        r3 = r3.append(r4);	 Catch:{ Throwable -> 0x028d }
        r3 = r3.append(r13);	 Catch:{ Throwable -> 0x028d }
        r4 = r3.toString();	 Catch:{ Throwable -> 0x028d }
        r5 = 0;
        r3 = r9;
        com.tencent.bugly.crashreport.crash.b.a(r0, r1, r2, r3, r4, r5);	 Catch:{ Throwable -> 0x028d }
        r0 = "[ExtraCrashManager] Successfully handled.";
        r1 = new java.lang.Object[r6];
        com.tencent.bugly.proguard.w.e(r0, r1);
        goto L_0x0012;
    L_0x00a3:
        r0 = "Unity";
        goto L_0x0015;
    L_0x00a7:
        r0 = "H5";
        goto L_0x0015;
    L_0x00ab:
        switch(r10) {
            case 4: goto L_0x00f6;
            case 5: goto L_0x00c6;
            case 6: goto L_0x00c6;
            case 7: goto L_0x00ae;
            case 8: goto L_0x00de;
            default: goto L_0x00ae;
        };
    L_0x00ae:
        r0 = "[ExtraCrashManager] Unknown extra crash type: %d";
        r1 = 1;
        r1 = new java.lang.Object[r1];	 Catch:{ Throwable -> 0x028d }
        r2 = 0;
        r3 = java.lang.Integer.valueOf(r10);	 Catch:{ Throwable -> 0x028d }
        r1[r2] = r3;	 Catch:{ Throwable -> 0x028d }
        com.tencent.bugly.proguard.w.d(r0, r1);	 Catch:{ Throwable -> 0x028d }
        r0 = "[ExtraCrashManager] Successfully handled.";
        r1 = new java.lang.Object[r6];
        com.tencent.bugly.proguard.w.e(r0, r1);
        goto L_0x0012;
    L_0x00c6:
        r1 = r1.l;	 Catch:{ Throwable -> 0x028d }
        if (r1 != 0) goto L_0x00f6;
    L_0x00ca:
        r1 = "[ExtraCrashManager] %s report is disabled.";
        r2 = 1;
        r2 = new java.lang.Object[r2];	 Catch:{ Throwable -> 0x028d }
        r3 = 0;
        r2[r3] = r0;	 Catch:{ Throwable -> 0x028d }
        com.tencent.bugly.proguard.w.e(r1, r2);	 Catch:{ Throwable -> 0x028d }
        r0 = "[ExtraCrashManager] Successfully handled.";
        r1 = new java.lang.Object[r6];
        com.tencent.bugly.proguard.w.e(r0, r1);
        goto L_0x0012;
    L_0x00de:
        r1 = r1.m;	 Catch:{ Throwable -> 0x028d }
        if (r1 != 0) goto L_0x00f6;
    L_0x00e2:
        r1 = "[ExtraCrashManager] %s report is disabled.";
        r2 = 1;
        r2 = new java.lang.Object[r2];	 Catch:{ Throwable -> 0x028d }
        r3 = 0;
        r2[r3] = r0;	 Catch:{ Throwable -> 0x028d }
        com.tencent.bugly.proguard.w.e(r1, r2);	 Catch:{ Throwable -> 0x028d }
        r0 = "[ExtraCrashManager] Successfully handled.";
        r1 = new java.lang.Object[r6];
        com.tencent.bugly.proguard.w.e(r0, r1);
        goto L_0x0012;
    L_0x00f6:
        r1 = 8;
        if (r10 != r1) goto L_0x00fb;
    L_0x00fa:
        r10 = 5;
    L_0x00fb:
        r5 = new com.tencent.bugly.crashreport.crash.CrashDetailBean;	 Catch:{ Throwable -> 0x028d }
        r5.<init>();	 Catch:{ Throwable -> 0x028d }
        r2 = com.tencent.bugly.crashreport.common.info.b.g();	 Catch:{ Throwable -> 0x028d }
        r5.B = r2;	 Catch:{ Throwable -> 0x028d }
        r2 = com.tencent.bugly.crashreport.common.info.b.e();	 Catch:{ Throwable -> 0x028d }
        r5.C = r2;	 Catch:{ Throwable -> 0x028d }
        r2 = com.tencent.bugly.crashreport.common.info.b.i();	 Catch:{ Throwable -> 0x028d }
        r5.D = r2;	 Catch:{ Throwable -> 0x028d }
        r1 = r8.c;	 Catch:{ Throwable -> 0x028d }
        r2 = r1.p();	 Catch:{ Throwable -> 0x028d }
        r5.E = r2;	 Catch:{ Throwable -> 0x028d }
        r1 = r8.c;	 Catch:{ Throwable -> 0x028d }
        r2 = r1.o();	 Catch:{ Throwable -> 0x028d }
        r5.F = r2;	 Catch:{ Throwable -> 0x028d }
        r1 = r8.c;	 Catch:{ Throwable -> 0x028d }
        r2 = r1.q();	 Catch:{ Throwable -> 0x028d }
        r5.G = r2;	 Catch:{ Throwable -> 0x028d }
        r1 = r8.e;	 Catch:{ Throwable -> 0x028d }
        r2 = com.tencent.bugly.crashreport.crash.c.d;	 Catch:{ Throwable -> 0x028d }
        r3 = 0;
        r1 = com.tencent.bugly.proguard.y.a(r1, r2, r3);	 Catch:{ Throwable -> 0x028d }
        r5.w = r1;	 Catch:{ Throwable -> 0x028d }
        r5.b = r10;	 Catch:{ Throwable -> 0x028d }
        r1 = r8.c;	 Catch:{ Throwable -> 0x028d }
        r1 = r1.h();	 Catch:{ Throwable -> 0x028d }
        r5.e = r1;	 Catch:{ Throwable -> 0x028d }
        r1 = r8.c;	 Catch:{ Throwable -> 0x028d }
        r1 = r1.j;	 Catch:{ Throwable -> 0x028d }
        r5.f = r1;	 Catch:{ Throwable -> 0x028d }
        r1 = r8.c;	 Catch:{ Throwable -> 0x028d }
        r1 = r1.w();	 Catch:{ Throwable -> 0x028d }
        r5.g = r1;	 Catch:{ Throwable -> 0x028d }
        r1 = r8.c;	 Catch:{ Throwable -> 0x028d }
        r1 = r1.g();	 Catch:{ Throwable -> 0x028d }
        r5.m = r1;	 Catch:{ Throwable -> 0x028d }
        r1 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x028d }
        r1.<init>();	 Catch:{ Throwable -> 0x028d }
        r1 = r1.append(r11);	 Catch:{ Throwable -> 0x028d }
        r1 = r1.toString();	 Catch:{ Throwable -> 0x028d }
        r5.n = r1;	 Catch:{ Throwable -> 0x028d }
        r1 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x028d }
        r1.<init>();	 Catch:{ Throwable -> 0x028d }
        r1 = r1.append(r12);	 Catch:{ Throwable -> 0x028d }
        r1 = r1.toString();	 Catch:{ Throwable -> 0x028d }
        r5.o = r1;	 Catch:{ Throwable -> 0x028d }
        r1 = "";
        if (r13 == 0) goto L_0x0240;
    L_0x0177:
        r2 = "\n";
        r2 = r13.split(r2);	 Catch:{ Throwable -> 0x028d }
        r3 = r2.length;	 Catch:{ Throwable -> 0x028d }
        if (r3 <= 0) goto L_0x0183;
    L_0x0180:
        r1 = 0;
        r1 = r2[r1];	 Catch:{ Throwable -> 0x028d }
    L_0x0183:
        r2 = r1;
        r1 = r13;
    L_0x0185:
        r5.p = r2;	 Catch:{ Throwable -> 0x028d }
        r5.q = r1;	 Catch:{ Throwable -> 0x028d }
        r2 = java.lang.System.currentTimeMillis();	 Catch:{ Throwable -> 0x028d }
        r5.r = r2;	 Catch:{ Throwable -> 0x028d }
        r1 = r5.q;	 Catch:{ Throwable -> 0x028d }
        r1 = r1.getBytes();	 Catch:{ Throwable -> 0x028d }
        r1 = com.tencent.bugly.proguard.y.b(r1);	 Catch:{ Throwable -> 0x028d }
        r5.u = r1;	 Catch:{ Throwable -> 0x028d }
        r1 = com.tencent.bugly.crashreport.crash.c.e;	 Catch:{ Throwable -> 0x028d }
        r2 = 0;
        r1 = com.tencent.bugly.proguard.y.a(r1, r2);	 Catch:{ Throwable -> 0x028d }
        r5.y = r1;	 Catch:{ Throwable -> 0x028d }
        r1 = r8.c;	 Catch:{ Throwable -> 0x028d }
        r1 = r1.d;	 Catch:{ Throwable -> 0x028d }
        r5.z = r1;	 Catch:{ Throwable -> 0x028d }
        r1 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x028d }
        r1.<init>();	 Catch:{ Throwable -> 0x028d }
        r2 = r9.getName();	 Catch:{ Throwable -> 0x028d }
        r1 = r1.append(r2);	 Catch:{ Throwable -> 0x028d }
        r2 = "(";
        r1 = r1.append(r2);	 Catch:{ Throwable -> 0x028d }
        r2 = r9.getId();	 Catch:{ Throwable -> 0x028d }
        r1 = r1.append(r2);	 Catch:{ Throwable -> 0x028d }
        r2 = ")";
        r1 = r1.append(r2);	 Catch:{ Throwable -> 0x028d }
        r1 = r1.toString();	 Catch:{ Throwable -> 0x028d }
        r5.A = r1;	 Catch:{ Throwable -> 0x028d }
        r1 = r8.c;	 Catch:{ Throwable -> 0x028d }
        r1 = r1.y();	 Catch:{ Throwable -> 0x028d }
        r5.H = r1;	 Catch:{ Throwable -> 0x028d }
        r1 = r8.c;	 Catch:{ Throwable -> 0x028d }
        r1 = r1.v();	 Catch:{ Throwable -> 0x028d }
        r5.h = r1;	 Catch:{ Throwable -> 0x028d }
        r1 = r8.c;	 Catch:{ Throwable -> 0x028d }
        r2 = r1.a;	 Catch:{ Throwable -> 0x028d }
        r5.L = r2;	 Catch:{ Throwable -> 0x028d }
        r1 = r8.c;	 Catch:{ Throwable -> 0x028d }
        r1 = r1.a();	 Catch:{ Throwable -> 0x028d }
        r5.M = r1;	 Catch:{ Throwable -> 0x028d }
        r1 = r8.c;	 Catch:{ Throwable -> 0x028d }
        r1 = r1.F();	 Catch:{ Throwable -> 0x028d }
        r5.O = r1;	 Catch:{ Throwable -> 0x028d }
        r1 = r8.c;	 Catch:{ Throwable -> 0x028d }
        r1 = r1.G();	 Catch:{ Throwable -> 0x028d }
        r5.P = r1;	 Catch:{ Throwable -> 0x028d }
        r1 = r8.c;	 Catch:{ Throwable -> 0x028d }
        r1 = r1.z();	 Catch:{ Throwable -> 0x028d }
        r5.Q = r1;	 Catch:{ Throwable -> 0x028d }
        r1 = r8.c;	 Catch:{ Throwable -> 0x028d }
        r1 = r1.E();	 Catch:{ Throwable -> 0x028d }
        r5.R = r1;	 Catch:{ Throwable -> 0x028d }
        r1 = r8.d;	 Catch:{ Throwable -> 0x028d }
        r1.b(r5);	 Catch:{ Throwable -> 0x028d }
        r1 = 0;
        r1 = com.tencent.bugly.proguard.x.a(r1);	 Catch:{ Throwable -> 0x028d }
        r5.x = r1;	 Catch:{ Throwable -> 0x028d }
        r1 = r5.N;	 Catch:{ Throwable -> 0x028d }
        if (r1 != 0) goto L_0x0226;
    L_0x021f:
        r1 = new java.util.LinkedHashMap;	 Catch:{ Throwable -> 0x028d }
        r1.<init>();	 Catch:{ Throwable -> 0x028d }
        r5.N = r1;	 Catch:{ Throwable -> 0x028d }
    L_0x0226:
        if (r14 == 0) goto L_0x022d;
    L_0x0228:
        r1 = r5.N;	 Catch:{ Throwable -> 0x028d }
        r1.putAll(r14);	 Catch:{ Throwable -> 0x028d }
    L_0x022d:
        if (r5 != 0) goto L_0x0247;
    L_0x022f:
        r0 = "[ExtraCrashManager] Failed to package crash data.";
        r1 = 0;
        r1 = new java.lang.Object[r1];	 Catch:{ Throwable -> 0x028d }
        com.tencent.bugly.proguard.w.e(r0, r1);	 Catch:{ Throwable -> 0x028d }
        r0 = "[ExtraCrashManager] Successfully handled.";
        r1 = new java.lang.Object[r6];
        com.tencent.bugly.proguard.w.e(r0, r1);
        goto L_0x0012;
    L_0x0240:
        r2 = "";
        r7 = r2;
        r2 = r1;
        r1 = r7;
        goto L_0x0185;
    L_0x0247:
        r1 = com.tencent.bugly.proguard.y.a();	 Catch:{ Throwable -> 0x028d }
        r2 = r8.c;	 Catch:{ Throwable -> 0x028d }
        r2 = r2.d;	 Catch:{ Throwable -> 0x028d }
        r3 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x028d }
        r3.<init>();	 Catch:{ Throwable -> 0x028d }
        r3 = r3.append(r11);	 Catch:{ Throwable -> 0x028d }
        r4 = "\n";
        r3 = r3.append(r4);	 Catch:{ Throwable -> 0x028d }
        r3 = r3.append(r12);	 Catch:{ Throwable -> 0x028d }
        r4 = "\n";
        r3 = r3.append(r4);	 Catch:{ Throwable -> 0x028d }
        r3 = r3.append(r13);	 Catch:{ Throwable -> 0x028d }
        r4 = r3.toString();	 Catch:{ Throwable -> 0x028d }
        r3 = r9;
        com.tencent.bugly.crashreport.crash.b.a(r0, r1, r2, r3, r4, r5);	 Catch:{ Throwable -> 0x028d }
        r0 = r8.d;	 Catch:{ Throwable -> 0x028d }
        r0 = r0.a(r5);	 Catch:{ Throwable -> 0x028d }
        if (r0 != 0) goto L_0x0284;
    L_0x027c:
        r0 = r8.d;	 Catch:{ Throwable -> 0x028d }
        r2 = 3000; // 0xbb8 float:4.204E-42 double:1.482E-320;
        r1 = 0;
        r0.a(r5, r2, r1);	 Catch:{ Throwable -> 0x028d }
    L_0x0284:
        r0 = "[ExtraCrashManager] Successfully handled.";
        r1 = new java.lang.Object[r6];
        com.tencent.bugly.proguard.w.e(r0, r1);
        goto L_0x0012;
    L_0x028d:
        r0 = move-exception;
        r1 = com.tencent.bugly.proguard.w.a(r0);	 Catch:{ all -> 0x02a0 }
        if (r1 != 0) goto L_0x0297;
    L_0x0294:
        r0.printStackTrace();	 Catch:{ all -> 0x02a0 }
    L_0x0297:
        r0 = "[ExtraCrashManager] Successfully handled.";
        r1 = new java.lang.Object[r6];
        com.tencent.bugly.proguard.w.e(r0, r1);
        goto L_0x0012;
    L_0x02a0:
        r0 = move-exception;
        r1 = "[ExtraCrashManager] Successfully handled.";
        r2 = new java.lang.Object[r6];
        com.tencent.bugly.proguard.w.e(r1, r2);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.crash.d.a(com.tencent.bugly.crashreport.crash.d, java.lang.Thread, int, java.lang.String, java.lang.String, java.lang.String, java.util.Map):void");
    }

    private d(Context context) {
        c a = c.a();
        if (a != null) {
            this.b = a.a();
            this.c = com.tencent.bugly.crashreport.common.info.a.a(context);
            this.d = a.n;
            this.e = context;
            v.a().a(new Runnable(this) {
                private /* synthetic */ d a;

                {
                    this.a = r1;
                }

                public final void run() {
                    d.a(this.a);
                }
            });
        }
    }

    public static d a(Context context) {
        if (a == null) {
            a = new d(context);
        }
        return a;
    }

    public static void a(Thread thread, int i, String str, String str2, String str3, Map<String, String> map) {
        final Thread thread2 = thread;
        final int i2 = i;
        final String str4 = str;
        final String str5 = str2;
        final String str6 = str3;
        final Map<String, String> map2 = map;
        v.a().a(new Runnable() {
            public final void run() {
                try {
                    if (d.a == null) {
                        w.e("[ExtraCrashManager] Extra crash manager has not been initialized.", new Object[0]);
                    } else {
                        d.a(d.a, thread2, i2, str4, str5, str6, map2);
                    }
                } catch (Throwable th) {
                    if (!w.b(th)) {
                        th.printStackTrace();
                    }
                    w.e("[ExtraCrashManager] Crash error %s %s %s", str4, str5, str6);
                }
            }
        });
    }
}
