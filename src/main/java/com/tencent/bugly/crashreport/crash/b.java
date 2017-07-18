package com.tencent.bugly.crashreport.crash;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Parcelable;
import com.kayac.lobi.libnakamap.utils.NotificationUtil;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.crashreport.common.info.PlugInBean;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.crashreport.common.strategy.a;
import com.tencent.bugly.proguard.ag;
import com.tencent.bugly.proguard.ai;
import com.tencent.bugly.proguard.aj;
import com.tencent.bugly.proguard.ak;
import com.tencent.bugly.proguard.al;
import com.tencent.bugly.proguard.j;
import com.tencent.bugly.proguard.n;
import com.tencent.bugly.proguard.o;
import com.tencent.bugly.proguard.q;
import com.tencent.bugly.proguard.s;
import com.tencent.bugly.proguard.t;
import com.tencent.bugly.proguard.w;
import com.tencent.bugly.proguard.y;
import com.yaya.sdk.constants.Constants;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import org.apache.commons.io.IOUtils;

/* compiled from: BUGLY */
public final class b {
    private static int a = 0;
    private Context b;
    private t c;
    private o d;
    private a e;
    private n f;
    private BuglyStrategy.a g;

    public b(int i, Context context, t tVar, o oVar, a aVar, BuglyStrategy.a aVar2, n nVar) {
        a = i;
        this.b = context;
        this.c = tVar;
        this.d = oVar;
        this.e = aVar;
        this.g = aVar2;
        this.f = nVar;
    }

    private static List<a> a(List<a> list) {
        if (list == null || list.size() == 0) {
            return null;
        }
        long currentTimeMillis = System.currentTimeMillis();
        List<a> arrayList = new ArrayList();
        for (a aVar : list) {
            if (aVar.d && aVar.b <= currentTimeMillis - NotificationUtil.PROMOTION_NOTIFICATION_INTERVAL) {
                arrayList.add(aVar);
            }
        }
        return arrayList;
    }

    private CrashDetailBean a(List<a> list, CrashDetailBean crashDetailBean) {
        if (list == null || list.size() == 0) {
            return crashDetailBean;
        }
        CrashDetailBean crashDetailBean2;
        CrashDetailBean crashDetailBean3 = null;
        List arrayList = new ArrayList(10);
        for (a aVar : list) {
            if (aVar.e) {
                arrayList.add(aVar);
            }
        }
        if (arrayList.size() > 0) {
            List b = b(arrayList);
            if (b != null && b.size() > 0) {
                Collections.sort(b);
                int i = 0;
                while (i < b.size()) {
                    crashDetailBean2 = (CrashDetailBean) b.get(i);
                    if (i != 0) {
                        if (crashDetailBean2.s != null) {
                            String[] split = crashDetailBean2.s.split(IOUtils.LINE_SEPARATOR_UNIX);
                            if (split != null) {
                                for (String str : split) {
                                    if (!crashDetailBean3.s.contains(str)) {
                                        crashDetailBean3.t++;
                                        crashDetailBean3.s += str + IOUtils.LINE_SEPARATOR_UNIX;
                                    }
                                }
                            }
                        }
                        crashDetailBean2 = crashDetailBean3;
                    }
                    i++;
                    crashDetailBean3 = crashDetailBean2;
                }
                crashDetailBean2 = crashDetailBean3;
                if (crashDetailBean2 != null) {
                    crashDetailBean.j = true;
                    crashDetailBean.t = 0;
                    crashDetailBean.s = "";
                    crashDetailBean3 = crashDetailBean;
                } else {
                    crashDetailBean3 = crashDetailBean2;
                }
                for (a aVar2 : list) {
                    if (!(aVar2.e || aVar2.d || crashDetailBean3.s.contains(aVar2.b))) {
                        crashDetailBean3.t++;
                        crashDetailBean3.s += aVar2.b + IOUtils.LINE_SEPARATOR_UNIX;
                    }
                }
                if (crashDetailBean3.r == crashDetailBean.r && !crashDetailBean3.s.contains(crashDetailBean.r)) {
                    crashDetailBean3.t++;
                    crashDetailBean3.s += crashDetailBean.r + IOUtils.LINE_SEPARATOR_UNIX;
                    return crashDetailBean3;
                }
            }
        }
        crashDetailBean2 = null;
        if (crashDetailBean2 != null) {
            crashDetailBean3 = crashDetailBean2;
        } else {
            crashDetailBean.j = true;
            crashDetailBean.t = 0;
            crashDetailBean.s = "";
            crashDetailBean3 = crashDetailBean;
        }
        for (a aVar22 : list) {
            crashDetailBean3.t++;
            crashDetailBean3.s += aVar22.b + IOUtils.LINE_SEPARATOR_UNIX;
        }
        return crashDetailBean3.r == crashDetailBean.r ? crashDetailBean3 : crashDetailBean3;
    }

    public final boolean a(CrashDetailBean crashDetailBean) {
        return a(crashDetailBean, -123456789);
    }

    public final boolean a(CrashDetailBean crashDetailBean, int i) {
        if (crashDetailBean == null) {
            return true;
        }
        if (!(c.l == null || c.l.isEmpty())) {
            w.c("Crash filter for crash stack is: %s", c.l);
            if (crashDetailBean.q.contains(c.l)) {
                w.d("This crash contains the filter string set. It will not be record and upload.", new Object[0]);
                return true;
            }
        }
        if (!(c.m == null || c.m.isEmpty())) {
            w.c("Crash regular filter for crash stack is: %s", c.m);
            if (Pattern.compile(c.m).matcher(crashDetailBean.q).find()) {
                w.d("This crash matches the regular filter string set. It will not be record and upload.", new Object[0]);
                return true;
            }
        }
        int i2 = crashDetailBean.b;
        String str = crashDetailBean.n;
        str = crashDetailBean.p;
        str = crashDetailBean.q;
        long j = crashDetailBean.r;
        str = crashDetailBean.m;
        str = crashDetailBean.e;
        str = crashDetailBean.c;
        if (this.f != null) {
            n nVar = this.f;
            String str2 = crashDetailBean.z;
            if (!nVar.c()) {
                return true;
            }
        }
        if (crashDetailBean.b != 2) {
            q qVar = new q();
            qVar.b = 1;
            qVar.c = crashDetailBean.z;
            qVar.d = crashDetailBean.A;
            qVar.e = crashDetailBean.r;
            this.d.b(1);
            this.d.a(qVar);
            w.b("[crash] a crash occur, handling...", new Object[0]);
        } else {
            w.b("[crash] a caught exception occur, handling...", new Object[0]);
        }
        List<a> b = b();
        List list = null;
        if (b != null && b.size() > 0) {
            List arrayList = new ArrayList(10);
            List<a> arrayList2 = new ArrayList(10);
            arrayList.addAll(a((List) b));
            b.removeAll(arrayList);
            if (!com.tencent.bugly.b.c && c.c) {
                int i3 = 0;
                for (a aVar : b) {
                    if (crashDetailBean.u.equals(aVar.c)) {
                        if (aVar.e) {
                            i3 = true;
                        }
                        arrayList2.add(aVar);
                    }
                    i3 = i3;
                }
                if (i3 != 0 || arrayList2.size() >= 2) {
                    w.a("same crash occur too much do merged!", new Object[0]);
                    CrashDetailBean a = a((List) arrayList2, crashDetailBean);
                    for (a aVar2 : arrayList2) {
                        if (aVar2.a != a.a) {
                            arrayList.add(aVar2);
                        }
                    }
                    c(a);
                    c(arrayList);
                    w.b("[crash] save crash success. For this device crash many times, it will not upload crashes immediately", new Object[0]);
                    return true;
                }
            }
            list = arrayList;
        }
        c(crashDetailBean);
        if (!(list == null || list.isEmpty())) {
            c(list);
        }
        w.b("[crash] save crash success", new Object[0]);
        return false;
    }

    public final List<CrashDetailBean> a() {
        StrategyBean c = a.a().c();
        if (c == null) {
            w.d("have not synced remote!", new Object[0]);
            return null;
        } else if (c.g) {
            long currentTimeMillis = System.currentTimeMillis();
            long b = y.b();
            List b2 = b();
            if (b2 == null || b2.size() <= 0) {
                return null;
            }
            List arrayList = new ArrayList();
            Iterator it = b2.iterator();
            while (it.hasNext()) {
                a aVar = (a) it.next();
                if (aVar.b < b - c.f) {
                    it.remove();
                    arrayList.add(aVar);
                } else if (aVar.d) {
                    if (aVar.b >= currentTimeMillis - NotificationUtil.PROMOTION_NOTIFICATION_INTERVAL) {
                        it.remove();
                    } else if (!aVar.e) {
                        it.remove();
                        arrayList.add(aVar);
                    }
                } else if (((long) aVar.f) >= 3 && aVar.b < currentTimeMillis - NotificationUtil.PROMOTION_NOTIFICATION_INTERVAL) {
                    it.remove();
                    arrayList.add(aVar);
                }
            }
            if (arrayList.size() > 0) {
                c(arrayList);
            }
            List arrayList2 = new ArrayList();
            List<CrashDetailBean> b3 = b(b2);
            if (b3 != null && b3.size() > 0) {
                String str = com.tencent.bugly.crashreport.common.info.a.b().j;
                Iterator it2 = b3.iterator();
                while (it2.hasNext()) {
                    CrashDetailBean crashDetailBean = (CrashDetailBean) it2.next();
                    if (!str.equals(crashDetailBean.f)) {
                        it2.remove();
                        arrayList2.add(crashDetailBean);
                    }
                }
            }
            if (arrayList2.size() > 0) {
                d(arrayList2);
            }
            return b3;
        } else {
            w.d("Crashreport remote closed, please check your APP ID correct and Version available, then uninstall and reinstall your app.", new Object[0]);
            w.b("[init] WARNING! Crashreport closed by server, please check your APP ID correct and Version available, then uninstall and reinstall your app.", new Object[0]);
            return null;
        }
    }

    public final void a(CrashDetailBean crashDetailBean, long j, boolean z) {
        boolean z2 = false;
        if (c.k) {
            w.a("try to upload right now", new Object[0]);
            List arrayList = new ArrayList();
            arrayList.add(crashDetailBean);
            if (crashDetailBean.b == 7) {
                z2 = true;
            }
            a(arrayList, 3000, z, z2, z);
            if (this.f != null) {
                n nVar = this.f;
                int i = crashDetailBean.b;
            }
        }
    }

    public final void a(final List<CrashDetailBean> list, long j, boolean z, boolean z2, boolean z3) {
        if (!com.tencent.bugly.crashreport.common.info.a.a(this.b).e || this.c == null) {
            return;
        }
        if (z3 || this.c.b(c.a)) {
            StrategyBean c = this.e.c();
            if (!c.g) {
                w.d("remote report is disable!", new Object[0]);
                w.b("[crash] server closed bugly in this app. please check your appid if is correct, and re-install it", new Object[0]);
            } else if (list != null && list.size() != 0) {
                try {
                    j jVar;
                    String str = this.c.a ? c.s : c.t;
                    String str2 = this.c.a ? StrategyBean.c : StrategyBean.a;
                    int i = this.c.a ? 830 : 630;
                    Context context = this.b;
                    com.tencent.bugly.crashreport.common.info.a b = com.tencent.bugly.crashreport.common.info.a.b();
                    if (context == null || list == null || list.size() == 0 || b == null) {
                        w.d("enEXPPkg args == null!", new Object[0]);
                        jVar = null;
                    } else {
                        j akVar = new ak();
                        akVar.a = new ArrayList();
                        for (CrashDetailBean a : list) {
                            akVar.a.add(a(context, a, b));
                        }
                        jVar = akVar;
                    }
                    if (jVar == null) {
                        w.d("create eupPkg fail!", new Object[0]);
                        return;
                    }
                    byte[] a2 = com.tencent.bugly.proguard.a.a(jVar);
                    if (a2 == null) {
                        w.d("send encode fail!", new Object[0]);
                        return;
                    }
                    al a3 = com.tencent.bugly.proguard.a.a(this.b, i, a2);
                    if (a3 == null) {
                        w.d("request package is null.", new Object[0]);
                        return;
                    }
                    s anonymousClass1 = new s(this) {
                        private /* synthetic */ b b;

                        public final void a(boolean z) {
                            b bVar = this.b;
                            b.a(z, list);
                        }
                    };
                    if (z) {
                        this.c.a(a, a3, str, str2, anonymousClass1, j, z2);
                    } else {
                        this.c.a(a, a3, str, str2, anonymousClass1, false);
                    }
                } catch (Throwable th) {
                    w.e("req cr error %s", th.toString());
                    if (!w.b(th)) {
                        th.printStackTrace();
                    }
                }
            }
        }
    }

    public static void a(boolean z, List<CrashDetailBean> list) {
        if (list != null && list.size() > 0) {
            w.c("up finish update state %b", Boolean.valueOf(z));
            for (CrashDetailBean crashDetailBean : list) {
                w.c("pre uid:%s uc:%d re:%b me:%b", crashDetailBean.c, Integer.valueOf(crashDetailBean.l), Boolean.valueOf(crashDetailBean.d), Boolean.valueOf(crashDetailBean.j));
                crashDetailBean.l++;
                crashDetailBean.d = z;
                w.c("set uid:%s uc:%d re:%b me:%b", crashDetailBean.c, Integer.valueOf(crashDetailBean.l), Boolean.valueOf(crashDetailBean.d), Boolean.valueOf(crashDetailBean.j));
            }
            for (CrashDetailBean crashDetailBean2 : list) {
                c.a().a(crashDetailBean2);
            }
            w.c("update state size %d", Integer.valueOf(list.size()));
        }
        if (!z) {
            w.b("[crash] upload fail.", new Object[0]);
        }
    }

    public final void b(CrashDetailBean crashDetailBean) {
        if (crashDetailBean != null) {
            if (this.g != null || this.f != null) {
                try {
                    int i;
                    String b;
                    w.a("[crash callback] start user's callback:onCrashHandleStart()", new Object[0]);
                    switch (crashDetailBean.b) {
                        case 0:
                            i = 0;
                            break;
                        case 1:
                            i = 2;
                            break;
                        case 2:
                            i = 1;
                            break;
                        case 3:
                            i = 4;
                            break;
                        case 4:
                            i = 3;
                            break;
                        case 5:
                            i = 5;
                            break;
                        case 6:
                            i = 6;
                            break;
                        case 7:
                            i = 7;
                            break;
                        default:
                            return;
                    }
                    int i2 = crashDetailBean.b;
                    String str = crashDetailBean.n;
                    str = crashDetailBean.p;
                    str = crashDetailBean.q;
                    long j = crashDetailBean.r;
                    Map map = null;
                    if (this.f != null) {
                        n nVar = this.f;
                        b = this.f.b();
                        if (b != null) {
                            map = new HashMap(1);
                            map.put(Constants.LOADDATAREQ_USERDATA, b);
                        }
                    } else if (this.g != null) {
                        map = this.g.onCrashHandleStart(i, crashDetailBean.n, crashDetailBean.o, crashDetailBean.q);
                    }
                    if (map != null && map.size() > 0) {
                        crashDetailBean.N = new LinkedHashMap(map.size());
                        for (Entry entry : map.entrySet()) {
                            if (!y.a((String) entry.getKey())) {
                                b = (String) entry.getKey();
                                if (b.length() > 100) {
                                    b = b.substring(0, 100);
                                    w.d("setted key length is over limit %d substring to %s", Integer.valueOf(100), b);
                                }
                                String str2 = b;
                                if (y.a((String) entry.getValue()) || ((String) entry.getValue()).length() <= 30000) {
                                    str = ((String) entry.getValue());
                                } else {
                                    str = ((String) entry.getValue()).substring(((String) entry.getValue()).length() - 30000);
                                    w.d("setted %s value length is over limit %d substring", str2, Integer.valueOf(30000));
                                }
                                crashDetailBean.N.put(str2, str);
                                w.a("add setted key %s value size:%d", str2, Integer.valueOf(str.length()));
                            }
                        }
                    }
                    w.a("[crash callback] start user's callback:onCrashHandleStart2GetExtraDatas()", new Object[0]);
                    byte[] bArr = null;
                    if (this.f != null) {
                        bArr = this.f.a();
                    } else if (this.g != null) {
                        bArr = this.g.onCrashHandleStart2GetExtraDatas(i, crashDetailBean.n, crashDetailBean.o, crashDetailBean.q);
                    }
                    crashDetailBean.S = bArr;
                    if (crashDetailBean.S != null) {
                        if (crashDetailBean.S.length > 30000) {
                            w.d("extra bytes size %d is over limit %d will drop over part", Integer.valueOf(crashDetailBean.S.length), Integer.valueOf(30000));
                        }
                        w.a("add extra bytes %d ", Integer.valueOf(crashDetailBean.S.length));
                    }
                } catch (Throwable th) {
                    w.d("crash handle callback somthing wrong! %s", th.getClass().getName());
                    if (!w.a(th)) {
                        th.printStackTrace();
                    }
                }
            }
        }
    }

    private static ContentValues d(CrashDetailBean crashDetailBean) {
        int i = 1;
        if (crashDetailBean == null) {
            return null;
        }
        try {
            int i2;
            ContentValues contentValues = new ContentValues();
            if (crashDetailBean.a > 0) {
                contentValues.put("_id", Long.valueOf(crashDetailBean.a));
            }
            contentValues.put("_tm", Long.valueOf(crashDetailBean.r));
            contentValues.put("_s1", crashDetailBean.u);
            String str = "_up";
            if (crashDetailBean.d) {
                i2 = 1;
            } else {
                i2 = 0;
            }
            contentValues.put(str, Integer.valueOf(i2));
            String str2 = "_me";
            if (!crashDetailBean.j) {
                i = 0;
            }
            contentValues.put(str2, Integer.valueOf(i));
            contentValues.put("_uc", Integer.valueOf(crashDetailBean.l));
            contentValues.put("_dt", y.a((Parcelable) crashDetailBean));
            return contentValues;
        } catch (Throwable th) {
            if (w.a(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }

    private static CrashDetailBean a(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        try {
            byte[] blob = cursor.getBlob(cursor.getColumnIndex("_dt"));
            if (blob == null) {
                return null;
            }
            long j = cursor.getLong(cursor.getColumnIndex("_id"));
            CrashDetailBean crashDetailBean = (CrashDetailBean) y.a(blob, CrashDetailBean.CREATOR);
            if (crashDetailBean == null) {
                return crashDetailBean;
            }
            crashDetailBean.a = j;
            return crashDetailBean;
        } catch (Throwable th) {
            if (!w.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    public final void c(CrashDetailBean crashDetailBean) {
        if (crashDetailBean != null) {
            ContentValues d = d(crashDetailBean);
            if (d != null) {
                long a = o.a().a("t_cr", d, null, true);
                if (a >= 0) {
                    w.c("insert %s success!", "t_cr");
                    crashDetailBean.a = a;
                }
            }
        }
    }

    private List<CrashDetailBean> b(List<a> list) {
        Throwable th;
        Cursor cursor;
        if (list == null || list.size() == 0) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (a aVar : list) {
            stringBuilder.append(" or _id").append(" = ").append(aVar.a);
        }
        String stringBuilder2 = stringBuilder.toString();
        if (stringBuilder2.length() > 0) {
            stringBuilder2 = stringBuilder2.substring(4);
        }
        stringBuilder.setLength(0);
        Cursor a;
        try {
            a = o.a().a("t_cr", null, stringBuilder2, null, null, true);
            if (a == null) {
                if (a != null) {
                    a.close();
                }
                return null;
            }
            try {
                List<CrashDetailBean> arrayList = new ArrayList();
                while (a.moveToNext()) {
                    CrashDetailBean a2 = a(a);
                    if (a2 != null) {
                        arrayList.add(a2);
                    } else {
                        try {
                            stringBuilder.append(" or _id").append(" = ").append(a.getLong(a.getColumnIndex("_id")));
                        } catch (Throwable th2) {
                            th = th2;
                        }
                    }
                }
                String stringBuilder3 = stringBuilder.toString();
                if (stringBuilder3.length() > 0) {
                    int a3 = o.a().a("t_cr", stringBuilder3.substring(4), null, null, true);
                    w.d("deleted %s illegle data %d", "t_cr", Integer.valueOf(a3));
                }
                if (a != null) {
                    a.close();
                }
                return arrayList;
            } catch (Throwable th22) {
                th = th22;
            }
        } catch (Throwable th3) {
            th = th3;
            a = null;
            if (a != null) {
                a.close();
            }
            throw th;
        }
    }

    private static a b(Cursor cursor) {
        boolean z = true;
        if (cursor == null) {
            return null;
        }
        try {
            a aVar = new a();
            aVar.a = cursor.getLong(cursor.getColumnIndex("_id"));
            aVar.b = cursor.getLong(cursor.getColumnIndex("_tm"));
            aVar.c = cursor.getString(cursor.getColumnIndex("_s1"));
            aVar.d = cursor.getInt(cursor.getColumnIndex("_up")) == 1;
            if (cursor.getInt(cursor.getColumnIndex("_me")) != 1) {
                z = false;
            }
            aVar.e = z;
            aVar.f = cursor.getInt(cursor.getColumnIndex("_uc"));
            return aVar;
        } catch (Throwable th) {
            if (w.a(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }

    private List<a> b() {
        Cursor a;
        Throwable th;
        Cursor cursor = null;
        List<a> arrayList = new ArrayList();
        try {
            a = o.a().a("t_cr", new String[]{"_id", "_tm", "_s1", "_up", "_me", "_uc"}, null, null, null, true);
            if (a == null) {
                if (a != null) {
                    a.close();
                }
                return null;
            }
            try {
                StringBuilder stringBuilder = new StringBuilder();
                while (a.moveToNext()) {
                    a b = b(a);
                    if (b != null) {
                        arrayList.add(b);
                    } else {
                        try {
                            stringBuilder.append(" or _id").append(" = ").append(a.getLong(a.getColumnIndex("_id")));
                        } catch (Throwable th2) {
                            th = th2;
                        }
                    }
                }
                String stringBuilder2 = stringBuilder.toString();
                if (stringBuilder2.length() > 0) {
                    int a2 = o.a().a("t_cr", stringBuilder2.substring(4), null, null, true);
                    w.d("deleted %s illegle data %d", "t_cr", Integer.valueOf(a2));
                }
                if (a != null) {
                    a.close();
                }
                return arrayList;
            } catch (Throwable th22) {
                th = th22;
            }
        } catch (Throwable th3) {
            th = th3;
            a = null;
            if (a != null) {
                a.close();
            }
            throw th;
        }
    }

    private static void c(List<a> list) {
        if (list != null && list.size() != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (a aVar : list) {
                stringBuilder.append(" or _id").append(" = ").append(aVar.a);
            }
            String stringBuilder2 = stringBuilder.toString();
            if (stringBuilder2.length() > 0) {
                stringBuilder2 = stringBuilder2.substring(4);
            }
            stringBuilder.setLength(0);
            try {
                int a = o.a().a("t_cr", stringBuilder2, null, null, true);
                w.c("deleted %s data %d", "t_cr", Integer.valueOf(a));
            } catch (Throwable th) {
                if (!w.a(th)) {
                    th.printStackTrace();
                }
            }
        }
    }

    private static void d(List<CrashDetailBean> list) {
        if (list != null) {
            try {
                if (list.size() != 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (CrashDetailBean crashDetailBean : list) {
                        stringBuilder.append(" or _id").append(" = ").append(crashDetailBean.a);
                    }
                    String stringBuilder2 = stringBuilder.toString();
                    if (stringBuilder2.length() > 0) {
                        stringBuilder2 = stringBuilder2.substring(4);
                    }
                    stringBuilder.setLength(0);
                    int a = o.a().a("t_cr", stringBuilder2, null, null, true);
                    w.c("deleted %s data %d", "t_cr", Integer.valueOf(a));
                }
            } catch (Throwable th) {
                if (!w.a(th)) {
                    th.printStackTrace();
                }
            }
        }
    }

    private static aj a(Context context, CrashDetailBean crashDetailBean, com.tencent.bugly.crashreport.common.info.a aVar) {
        boolean z = true;
        if (context == null || crashDetailBean == null || aVar == null) {
            w.d("enExp args == null", new Object[0]);
            return null;
        }
        ai a;
        aj ajVar = new aj();
        switch (crashDetailBean.b) {
            case 0:
                ajVar.a = crashDetailBean.j ? "200" : "100";
                break;
            case 1:
                ajVar.a = crashDetailBean.j ? "201" : "101";
                break;
            case 2:
                ajVar.a = crashDetailBean.j ? "202" : "102";
                break;
            case 3:
                ajVar.a = crashDetailBean.j ? "203" : "103";
                break;
            case 4:
                ajVar.a = crashDetailBean.j ? "204" : "104";
                break;
            case 5:
                ajVar.a = crashDetailBean.j ? "207" : "107";
                break;
            case 6:
                ajVar.a = crashDetailBean.j ? "206" : "106";
                break;
            case 7:
                ajVar.a = crashDetailBean.j ? "208" : "108";
                break;
            default:
                w.e("crash type error! %d", Integer.valueOf(crashDetailBean.b));
                break;
        }
        ajVar.b = crashDetailBean.r;
        ajVar.c = crashDetailBean.n;
        ajVar.d = crashDetailBean.o;
        ajVar.e = crashDetailBean.p;
        ajVar.g = crashDetailBean.q;
        ajVar.h = crashDetailBean.y;
        ajVar.i = crashDetailBean.c;
        ajVar.j = null;
        ajVar.l = crashDetailBean.m;
        ajVar.m = crashDetailBean.e;
        ajVar.f = crashDetailBean.A;
        ajVar.t = com.tencent.bugly.crashreport.common.info.a.b().i();
        ajVar.n = null;
        if (crashDetailBean.i != null && crashDetailBean.i.size() > 0) {
            ajVar.o = new ArrayList();
            for (Entry entry : crashDetailBean.i.entrySet()) {
                ag agVar = new ag();
                agVar.a = ((PlugInBean) entry.getValue()).a;
                agVar.c = ((PlugInBean) entry.getValue()).c;
                agVar.d = ((PlugInBean) entry.getValue()).b;
                agVar.b = aVar.r();
                ajVar.o.add(agVar);
            }
        }
        if (crashDetailBean.h != null && crashDetailBean.h.size() > 0) {
            ajVar.p = new ArrayList();
            for (Entry entry2 : crashDetailBean.h.entrySet()) {
                agVar = new ag();
                agVar.a = ((PlugInBean) entry2.getValue()).a;
                agVar.c = ((PlugInBean) entry2.getValue()).c;
                agVar.d = ((PlugInBean) entry2.getValue()).b;
                ajVar.p.add(agVar);
            }
        }
        if (crashDetailBean.j) {
            int size;
            ajVar.k = crashDetailBean.t;
            if (crashDetailBean.s != null && crashDetailBean.s.length() > 0) {
                if (ajVar.q == null) {
                    ajVar.q = new ArrayList();
                }
                try {
                    ajVar.q.add(new ai((byte) 1, "alltimes.txt", crashDetailBean.s.getBytes("utf-8")));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    ajVar.q = null;
                }
            }
            String str = "crashcount:%d sz:%d";
            Object[] objArr = new Object[2];
            objArr[0] = Integer.valueOf(ajVar.k);
            if (ajVar.q != null) {
                size = ajVar.q.size();
            } else {
                size = 0;
            }
            objArr[1] = Integer.valueOf(size);
            w.c(str, objArr);
        }
        if (crashDetailBean.w != null) {
            if (ajVar.q == null) {
                ajVar.q = new ArrayList();
            }
            try {
                ajVar.q.add(new ai((byte) 1, "log.txt", crashDetailBean.w.getBytes("utf-8")));
            } catch (UnsupportedEncodingException e2) {
                e2.printStackTrace();
                ajVar.q = null;
            }
        }
        if (!y.a(crashDetailBean.T)) {
            Object aiVar;
            if (ajVar.q == null) {
                ajVar.q = new ArrayList();
            }
            try {
                aiVar = new ai((byte) 1, "crashInfos.txt", crashDetailBean.T.getBytes("utf-8"));
            } catch (UnsupportedEncodingException e22) {
                e22.printStackTrace();
                aiVar = null;
            }
            if (aiVar != null) {
                w.c("attach crash infos", new Object[0]);
                ajVar.q.add(aiVar);
            }
        }
        if (crashDetailBean.U != null) {
            if (ajVar.q == null) {
                ajVar.q = new ArrayList();
            }
            a = a("backupRecord.zip", context, crashDetailBean.U);
            if (a != null) {
                w.c("attach backup record", new Object[0]);
                ajVar.q.add(a);
            }
        }
        if (crashDetailBean.x != null && crashDetailBean.x.length > 0) {
            a = new ai((byte) 2, "buglylog.zip", crashDetailBean.x);
            w.c("attach user log", new Object[0]);
            if (ajVar.q == null) {
                ajVar.q = new ArrayList();
            }
            ajVar.q.add(a);
        }
        if (crashDetailBean.b == 3) {
            if (ajVar.q == null) {
                ajVar.q = new ArrayList();
            }
            if (crashDetailBean.N != null && crashDetailBean.N.containsKey("BUGLY_CR_01")) {
                try {
                    ajVar.q.add(new ai((byte) 1, "anrMessage.txt", ((String) crashDetailBean.N.get("BUGLY_CR_01")).getBytes("utf-8")));
                    w.c("attach anr message", new Object[0]);
                } catch (UnsupportedEncodingException e222) {
                    e222.printStackTrace();
                    ajVar.q = null;
                }
                crashDetailBean.N.remove("BUGLY_CR_01");
            }
            if (crashDetailBean.v != null) {
                a = a("trace.zip", context, crashDetailBean.v);
                if (a != null) {
                    w.c("attach traces", new Object[0]);
                    ajVar.q.add(a);
                }
            }
        }
        if (crashDetailBean.b == 1) {
            if (ajVar.q == null) {
                ajVar.q = new ArrayList();
            }
            if (crashDetailBean.v != null) {
                a = a("tomb.zip", context, crashDetailBean.v);
                if (a != null) {
                    w.c("attach tombs", new Object[0]);
                    ajVar.q.add(a);
                }
            }
        }
        if (!(aVar.B == null || aVar.B.isEmpty())) {
            if (ajVar.q == null) {
                ajVar.q = new ArrayList();
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (String append : aVar.B) {
                stringBuilder.append(append);
            }
            try {
                ajVar.q.add(new ai((byte) 1, "martianlog.txt", stringBuilder.toString().getBytes("utf-8")));
                w.c("attach pageTracingList", new Object[0]);
            } catch (UnsupportedEncodingException e2222) {
                e2222.printStackTrace();
            }
        }
        if (crashDetailBean.S != null && crashDetailBean.S.length > 0) {
            if (ajVar.q == null) {
                ajVar.q = new ArrayList();
            }
            ajVar.q.add(new ai((byte) 1, "userExtraByteData", crashDetailBean.S));
            w.c("attach extraData", new Object[0]);
        }
        ajVar.r = new HashMap();
        ajVar.r.put("A9", crashDetailBean.B);
        ajVar.r.put("A11", crashDetailBean.C);
        ajVar.r.put("A10", crashDetailBean.D);
        ajVar.r.put("A23", crashDetailBean.f);
        ajVar.r.put("A7", aVar.f);
        ajVar.r.put("A6", aVar.s());
        ajVar.r.put("A5", aVar.r());
        ajVar.r.put("A22", aVar.h());
        ajVar.r.put("A2", crashDetailBean.F);
        ajVar.r.put("A1", crashDetailBean.E);
        ajVar.r.put("A24", aVar.h);
        ajVar.r.put("A17", crashDetailBean.G);
        ajVar.r.put("A3", aVar.k());
        ajVar.r.put("A16", aVar.m());
        ajVar.r.put("A25", aVar.n());
        ajVar.r.put("A14", aVar.l());
        ajVar.r.put("A15", aVar.w());
        ajVar.r.put("A13", aVar.x());
        ajVar.r.put("A34", crashDetailBean.z);
        if (aVar.x != null) {
            ajVar.r.put("productIdentify", aVar.x);
        }
        try {
            ajVar.r.put("A26", URLEncoder.encode(crashDetailBean.H, "utf-8"));
        } catch (UnsupportedEncodingException e22222) {
            e22222.printStackTrace();
        }
        if (crashDetailBean.b == 1) {
            ajVar.r.put("A27", crashDetailBean.J);
            ajVar.r.put("A28", crashDetailBean.I);
            ajVar.r.put("A29", crashDetailBean.k);
        }
        ajVar.r.put("A30", crashDetailBean.K);
        ajVar.r.put("A18", crashDetailBean.L);
        ajVar.r.put("A36", (!crashDetailBean.M));
        ajVar.r.put("F02", aVar.q);
        ajVar.r.put("F03", aVar.r);
        ajVar.r.put("F04", aVar.e());
        ajVar.r.put("F05", aVar.s);
        ajVar.r.put("F06", aVar.p);
        ajVar.r.put("F08", aVar.v);
        ajVar.r.put("F09", aVar.w);
        ajVar.r.put("F10", aVar.t);
        if (crashDetailBean.O >= 0) {
            ajVar.r.put("C01", crashDetailBean.O);
        }
        if (crashDetailBean.P >= 0) {
            ajVar.r.put("C02", crashDetailBean.P);
        }
        if (crashDetailBean.Q != null && crashDetailBean.Q.size() > 0) {
            for (Entry entry22 : crashDetailBean.Q.entrySet()) {
                ajVar.r.put("C03_" + ((String) entry22.getKey()), entry22.getValue());
            }
        }
        if (crashDetailBean.R != null && crashDetailBean.R.size() > 0) {
            for (Entry entry222 : crashDetailBean.R.entrySet()) {
                ajVar.r.put("C04_" + ((String) entry222.getKey()), entry222.getValue());
            }
        }
        ajVar.s = null;
        if (crashDetailBean.N != null && crashDetailBean.N.size() > 0) {
            ajVar.s = crashDetailBean.N;
            w.a("setted message size %d", Integer.valueOf(ajVar.s.size()));
        }
        String append2 = "%s rid:%s sess:%s ls:%ds isR:%b isF:%b isM:%b isN:%b mc:%d ,%s ,isUp:%b ,vm:%d";
        Object[] objArr2 = new Object[12];
        objArr2[0] = crashDetailBean.n;
        objArr2[1] = crashDetailBean.c;
        objArr2[2] = aVar.e();
        objArr2[3] = Long.valueOf((crashDetailBean.r - crashDetailBean.L) / 1000);
        objArr2[4] = Boolean.valueOf(crashDetailBean.k);
        objArr2[5] = Boolean.valueOf(crashDetailBean.M);
        objArr2[6] = Boolean.valueOf(crashDetailBean.j);
        if (crashDetailBean.b != 1) {
            z = false;
        }
        objArr2[7] = Boolean.valueOf(z);
        objArr2[8] = Integer.valueOf(crashDetailBean.t);
        objArr2[9] = crashDetailBean.s;
        objArr2[10] = Boolean.valueOf(crashDetailBean.d);
        objArr2[11] = Integer.valueOf(ajVar.r.size());
        w.c(append2, objArr2);
        return ajVar;
    }

    private static ai a(String str, Context context, String str2) {
        Throwable e;
        Throwable th;
        if (str2 == null || context == null) {
            w.d("rqdp{  createZipAttachment sourcePath == null || context == null ,pls check}", new Object[0]);
            return null;
        }
        w.c("zip %s", str2);
        File file = new File(str2);
        File file2 = new File(context.getCacheDir(), str);
        if (y.a(file, file2, 5000)) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            FileInputStream fileInputStream;
            try {
                fileInputStream = new FileInputStream(file2);
                try {
                    byte[] bArr = new byte[1000];
                    while (true) {
                        int read = fileInputStream.read(bArr);
                        if (read <= 0) {
                            break;
                        }
                        byteArrayOutputStream.write(bArr, 0, read);
                        byteArrayOutputStream.flush();
                    }
                    w.c("read bytes :%d", Integer.valueOf(byteArrayOutputStream.toByteArray().length));
                    ai aiVar = new ai((byte) 2, file2.getName(), bArr);
                    try {
                        fileInputStream.close();
                    } catch (Throwable e2) {
                        if (!w.a(e2)) {
                            e2.printStackTrace();
                        }
                    }
                    if (file2.exists()) {
                        w.c("del tmp", new Object[0]);
                        file2.delete();
                    }
                    return aiVar;
                } catch (Throwable th2) {
                    th = th2;
                    try {
                        if (!w.a(th)) {
                            th.printStackTrace();
                        }
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (Throwable th3) {
                                if (!w.a(th3)) {
                                    th3.printStackTrace();
                                }
                            }
                        }
                        if (file2.exists()) {
                            return null;
                        }
                        w.c("del tmp", new Object[0]);
                        file2.delete();
                        return null;
                    } catch (Throwable th4) {
                        e2 = th4;
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (Throwable th32) {
                                if (!w.a(th32)) {
                                    th32.printStackTrace();
                                }
                            }
                        }
                        if (file2.exists()) {
                            w.c("del tmp", new Object[0]);
                            file2.delete();
                        }
                        throw e2;
                    }
                }
            } catch (Throwable th322) {
                fileInputStream = null;
                e2 = th322;
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (file2.exists()) {
                    w.c("del tmp", new Object[0]);
                    file2.delete();
                }
                throw e2;
            }
        }
        w.d("zip fail!", new Object[0]);
        return null;
    }

    public static void a(String str, String str2, String str3, Thread thread, String str4, CrashDetailBean crashDetailBean) {
        com.tencent.bugly.crashreport.common.info.a b = com.tencent.bugly.crashreport.common.info.a.b();
        if (b != null) {
            w.e("#++++++++++Record By Bugly++++++++++#", new Object[0]);
            w.e("# You can use Bugly(http:\\\\bugly.qq.com) to get more Crash Detail!", new Object[0]);
            w.e("# PKG NAME: %s", b.c);
            w.e("# APP VER: %s", b.j);
            w.e("# LAUNCH TIME: %s", y.a(new Date(com.tencent.bugly.crashreport.common.info.a.b().a)));
            w.e("# CRASH TYPE: %s", str);
            w.e("# CRASH TIME: %s", str2);
            w.e("# CRASH PROCESS: %s", str3);
            if (thread != null) {
                w.e("# CRASH THREAD: %s", thread.getName());
            }
            if (crashDetailBean != null) {
                w.e("# REPORT ID: %s", crashDetailBean.c);
                String str5 = "# CRASH DEVICE: %s %s";
                Object[] objArr = new Object[2];
                objArr[0] = b.g;
                objArr[1] = b.x().booleanValue() ? "ROOTED" : "UNROOT";
                w.e(str5, objArr);
                w.e("# RUNTIME AVAIL RAM:%d ROM:%d SD:%d", Long.valueOf(crashDetailBean.B), Long.valueOf(crashDetailBean.C), Long.valueOf(crashDetailBean.D));
                w.e("# RUNTIME TOTAL RAM:%d ROM:%d SD:%d", Long.valueOf(crashDetailBean.E), Long.valueOf(crashDetailBean.F), Long.valueOf(crashDetailBean.G));
                if (!y.a(crashDetailBean.J)) {
                    w.e("# EXCEPTION FIRED BY %s %s", crashDetailBean.J, crashDetailBean.I);
                } else if (crashDetailBean.b == 3) {
                    str5 = "# EXCEPTION ANR MESSAGE:\n %s";
                    objArr = new Object[1];
                    objArr[0] = crashDetailBean.N == null ? "null" : ((String) crashDetailBean.N.get("BUGLY_CR_01"));
                    w.e(str5, objArr);
                }
            }
            if (!y.a(str4)) {
                w.e("# CRASH STACK: ", new Object[0]);
                w.e(str4, new Object[0]);
            }
            w.e("#++++++++++++++++++++++++++++++++++++++++++#", new Object[0]);
        }
    }
}
