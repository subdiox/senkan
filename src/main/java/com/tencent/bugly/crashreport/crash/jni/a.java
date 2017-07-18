package com.tencent.bugly.crashreport.crash.jni;

import android.content.Context;
import com.tencent.bugly.crashreport.common.info.AppInfo;
import com.tencent.bugly.crashreport.crash.CrashDetailBean;
import com.tencent.bugly.crashreport.crash.b;
import com.tencent.bugly.crashreport.crash.c;
import com.tencent.bugly.proguard.w;
import com.tencent.bugly.proguard.x;
import com.tencent.bugly.proguard.y;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.IOUtils;

/* compiled from: BUGLY */
public final class a implements NativeExceptionHandler {
    private final Context a;
    private final b b;
    private final com.tencent.bugly.crashreport.common.info.a c;
    private final com.tencent.bugly.crashreport.common.strategy.a d;
    private final String e;

    public a(Context context, com.tencent.bugly.crashreport.common.info.a aVar, b bVar, com.tencent.bugly.crashreport.common.strategy.a aVar2, String str) {
        this.a = context;
        this.b = bVar;
        this.c = aVar;
        this.d = aVar2;
        this.e = str;
    }

    public final CrashDetailBean packageCrashDatas(String str, String str2, long j, String str3, String str4, String str5, String str6, String str7, String str8, String str9, byte[] bArr, Map<String, String> map, boolean z) {
        boolean l = c.a().l();
        if (l) {
            w.e("This Crash Caused By ANR , PLS To Fix ANR , This Trace May Be Not Useful!", new Object[0]);
        }
        CrashDetailBean crashDetailBean = new CrashDetailBean();
        crashDetailBean.b = 1;
        crashDetailBean.e = this.c.h();
        crashDetailBean.f = this.c.j;
        crashDetailBean.g = this.c.w();
        crashDetailBean.m = this.c.g();
        crashDetailBean.n = str3;
        crashDetailBean.o = l ? " This Crash Caused By ANR , PLS To Fix ANR , This Trace May Be Not Useful![Bugly]" : "";
        crashDetailBean.p = str4;
        if (str5 == null) {
            str5 = "";
        }
        crashDetailBean.q = str5;
        crashDetailBean.r = j;
        crashDetailBean.u = y.b(crashDetailBean.q.getBytes());
        crashDetailBean.z = str;
        crashDetailBean.A = str2;
        crashDetailBean.H = this.c.y();
        crashDetailBean.h = this.c.v();
        crashDetailBean.i = this.c.I();
        crashDetailBean.v = str8;
        String a = b.a(this.e, str8);
        if (!y.a(a)) {
            crashDetailBean.T = a;
        }
        File file = new File(this.e, "backup_record.txt");
        crashDetailBean.U = file.exists() ? file.getAbsolutePath() : null;
        crashDetailBean.I = str7;
        crashDetailBean.J = str6;
        crashDetailBean.K = str9;
        crashDetailBean.E = this.c.p();
        crashDetailBean.F = this.c.o();
        crashDetailBean.G = this.c.q();
        if (z) {
            crashDetailBean.B = com.tencent.bugly.crashreport.common.info.b.g();
            crashDetailBean.C = com.tencent.bugly.crashreport.common.info.b.e();
            crashDetailBean.D = com.tencent.bugly.crashreport.common.info.b.i();
            crashDetailBean.w = y.a(this.a, c.d, null);
            crashDetailBean.x = x.a(true);
            crashDetailBean.L = this.c.a;
            crashDetailBean.M = this.c.a();
            crashDetailBean.O = this.c.F();
            crashDetailBean.P = this.c.G();
            crashDetailBean.Q = this.c.z();
            crashDetailBean.R = this.c.E();
            crashDetailBean.y = y.a(c.e, false);
            a = "java:\n";
            int indexOf = crashDetailBean.q.indexOf(a);
            if (indexOf > 0) {
                indexOf += a.length();
                String substring = crashDetailBean.q.substring(indexOf, crashDetailBean.q.length() - 1);
                if (substring.length() > 0 && crashDetailBean.y.containsKey(crashDetailBean.A)) {
                    a = (String) crashDetailBean.y.get(crashDetailBean.A);
                    int indexOf2 = a.indexOf(substring);
                    if (indexOf2 > 0) {
                        a = a.substring(indexOf2);
                        crashDetailBean.y.put(crashDetailBean.A, a);
                        crashDetailBean.q = crashDetailBean.q.substring(0, indexOf);
                        crashDetailBean.q += a;
                    }
                }
            }
            if (str == null) {
                crashDetailBean.z = this.c.d;
            }
            this.b.b(crashDetailBean);
        } else {
            crashDetailBean.B = -1;
            crashDetailBean.C = -1;
            crashDetailBean.D = -1;
            crashDetailBean.w = "this crash is occurred at last process! Log is miss, when get an terrible ABRT Native Exception etc.";
            crashDetailBean.L = -1;
            crashDetailBean.O = -1;
            crashDetailBean.P = -1;
            crashDetailBean.Q = map;
            crashDetailBean.R = this.c.E();
            crashDetailBean.y = null;
            if (str == null) {
                crashDetailBean.z = "unknown(record)";
            }
            if (bArr == null) {
                crashDetailBean.x = "this crash is occurred at last process! Log is miss, when get an terrible ABRT Native Exception etc.".getBytes();
            } else {
                crashDetailBean.x = bArr;
            }
        }
        return crashDetailBean;
    }

    public final void handleNativeException(int i, int i2, long j, long j2, String str, String str2, String str3, String str4, int i3, String str5, int i4, int i5, int i6, String str6, String str7) {
        w.a("Native Crash Happen v1", new Object[0]);
        handleNativeException2(i, i2, j, j2, str, str2, str3, str4, i3, str5, i4, i5, i6, str6, str7, null);
    }

    public final void handleNativeException2(int i, int i2, long j, long j2, String str, String str2, String str3, String str4, int i3, String str5, int i4, int i5, int i6, String str6, String str7, String[] strArr) {
        w.a("Native Crash Happen v2", new Object[0]);
        try {
            int i7;
            String str8;
            String str9;
            if (!this.d.b()) {
                w.e("waiting for remote sync", new Object[0]);
                i7 = 0;
                while (!this.d.b()) {
                    y.b(500);
                    i7 += 500;
                    if (i7 >= 3000) {
                        break;
                    }
                }
            }
            String a = b.a(str3);
            String str10 = "UNKNOWN";
            if (i3 > 0) {
                str8 = "KERNEL";
                str9 = str + "(" + str5 + ")";
            } else {
                if (i4 > 0) {
                    Context context = this.a;
                    str10 = AppInfo.a(i4);
                }
                if (str10.equals(String.valueOf(i4))) {
                    str8 = str5;
                    str9 = str;
                } else {
                    str10 = str10 + "(" + i4 + ")";
                    str8 = str5;
                    str9 = str;
                }
            }
            if (!this.d.b()) {
                w.d("no remote but still store!", new Object[0]);
            }
            if (this.d.c().g || !this.d.b()) {
                String str11 = null;
                String str12 = null;
                if (strArr != null) {
                    Map hashMap = new HashMap();
                    for (String str122 : strArr) {
                        String[] split = str122.split("=");
                        if (split.length == 2) {
                            hashMap.put(split[0], split[1]);
                        } else {
                            w.d("bad extraMsg %s", str122);
                        }
                    }
                    str122 = (String) hashMap.get("ExceptionThreadName");
                    str11 = (String) hashMap.get("ExceptionProcessName");
                } else {
                    w.c("not found extraMsg", new Object[0]);
                }
                if (str11 == null || str11.length() == 0) {
                    str11 = this.c.d;
                } else {
                    w.c("crash process name change to %s", str11);
                }
                Thread currentThread;
                if (str122 != null && str122.length() != 0) {
                    w.c("crash thread name change to %s", str122);
                    for (Thread currentThread2 : Thread.getAllStackTraces().keySet()) {
                        if (currentThread2.getName().equals(str122)) {
                            str122 = str122 + "(" + currentThread2.getId() + ")";
                            break;
                        }
                    }
                }
                currentThread2 = Thread.currentThread();
                str122 = currentThread2.getName() + "(" + currentThread2.getId() + ")";
                CrashDetailBean packageCrashDatas = packageCrashDatas(str11, str122, (j2 / 1000) + (1000 * j), str9, str2, a, str8, str10, str4, str7, null, null, true);
                if (packageCrashDatas == null) {
                    w.e("pkg crash datas fail!", new Object[0]);
                    return;
                }
                b.a("NATIVE_CRASH", y.a(), this.c.d, Thread.currentThread(), str9 + IOUtils.LINE_SEPARATOR_UNIX + str2 + IOUtils.LINE_SEPARATOR_UNIX + a, packageCrashDatas);
                if (!this.b.a(packageCrashDatas, i3)) {
                    this.b.a(packageCrashDatas, 3000, true);
                }
                b.b(this.e);
                return;
            }
            w.e("crash report was closed by remote , will not upload to Bugly , print local for helpful!", new Object[0]);
            b.a("NATIVE_CRASH", y.a(), this.c.d, Thread.currentThread(), str9 + IOUtils.LINE_SEPARATOR_UNIX + str2 + IOUtils.LINE_SEPARATOR_UNIX + a, null);
            y.b(str4);
        } catch (Throwable th) {
            if (!w.a(th)) {
                th.printStackTrace();
            }
        }
    }
}
