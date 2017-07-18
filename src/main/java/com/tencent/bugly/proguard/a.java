package com.tencent.bugly.proguard;

import android.content.Context;
import com.adjust.sdk.Constants;
import com.mp4parser.iso14496.part15.SyncSampleEntry;
import com.tencent.bugly.crashreport.biz.UserInfoBean;
import com.tencent.bugly.crashreport.common.info.b;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/* compiled from: BUGLY */
public class a {
    protected HashMap<String, HashMap<String, byte[]>> a = new HashMap();
    protected String b;
    h c;
    private HashMap<String, Object> d;

    public static af a(int i) {
        if (i == 1) {
            return new ae();
        }
        if (i == 3) {
            return new ad();
        }
        return null;
    }

    a() {
        HashMap hashMap = new HashMap();
        this.d = new HashMap();
        this.b = "GBK";
        this.c = new h();
    }

    public static ap a(UserInfoBean userInfoBean) {
        if (userInfoBean == null) {
            return null;
        }
        ap apVar = new ap();
        apVar.a = userInfoBean.e;
        apVar.e = userInfoBean.j;
        apVar.d = userInfoBean.c;
        apVar.c = userInfoBean.d;
        apVar.g = com.tencent.bugly.crashreport.common.info.a.b().i();
        apVar.h = userInfoBean.o == 1;
        switch (userInfoBean.b) {
            case 1:
                apVar.b = (byte) 1;
                break;
            case 2:
                apVar.b = (byte) 4;
                break;
            case 3:
                apVar.b = (byte) 2;
                break;
            case 4:
                apVar.b = (byte) 3;
                break;
            default:
                if (userInfoBean.b >= 10 && userInfoBean.b < 20) {
                    apVar.b = (byte) userInfoBean.b;
                    break;
                }
                w.e("unknown uinfo type %d ", Integer.valueOf(userInfoBean.b));
                return null;
                break;
        }
        apVar.f = new HashMap();
        if (userInfoBean.p >= 0) {
            apVar.f.put("C01", userInfoBean.p);
        }
        if (userInfoBean.q >= 0) {
            apVar.f.put("C02", userInfoBean.q);
        }
        if (userInfoBean.r != null && userInfoBean.r.size() > 0) {
            for (Entry entry : userInfoBean.r.entrySet()) {
                apVar.f.put("C03_" + ((String) entry.getKey()), entry.getValue());
            }
        }
        if (userInfoBean.s != null && userInfoBean.s.size() > 0) {
            for (Entry entry2 : userInfoBean.s.entrySet()) {
                apVar.f.put("C04_" + ((String) entry2.getKey()), entry2.getValue());
            }
        }
        apVar.f.put("A36", (!userInfoBean.l));
        apVar.f.put("F02", userInfoBean.g);
        apVar.f.put("F03", userInfoBean.h);
        apVar.f.put("F04", userInfoBean.j);
        apVar.f.put("F05", userInfoBean.i);
        apVar.f.put("F06", userInfoBean.m);
        apVar.f.put("F10", userInfoBean.k);
        w.c("summary type %d vm:%d", Byte.valueOf(apVar.b), Integer.valueOf(apVar.f.size()));
        return apVar;
    }

    public void a(String str) {
        this.b = str;
    }

    public static String a(ArrayList<String> arrayList) {
        int i;
        StringBuffer stringBuffer = new StringBuffer();
        for (i = 0; i < arrayList.size(); i++) {
            Object obj = (String) arrayList.get(i);
            if (obj.equals("java.lang.Integer") || obj.equals("int")) {
                obj = "int32";
            } else if (obj.equals("java.lang.Boolean") || obj.equals("boolean")) {
                obj = "bool";
            } else if (obj.equals("java.lang.Byte") || obj.equals("byte")) {
                obj = "char";
            } else if (obj.equals("java.lang.Double") || obj.equals("double")) {
                obj = "double";
            } else if (obj.equals("java.lang.Float") || obj.equals("float")) {
                obj = "float";
            } else if (obj.equals("java.lang.Long") || obj.equals(Constants.LONG)) {
                obj = "int64";
            } else if (obj.equals("java.lang.Short") || obj.equals("short")) {
                obj = "short";
            } else if (obj.equals("java.lang.Character")) {
                throw new IllegalArgumentException("can not support java.lang.Character");
            } else if (obj.equals("java.lang.String")) {
                obj = "string";
            } else if (obj.equals("java.util.List")) {
                obj = "list";
            } else if (obj.equals("java.util.Map")) {
                obj = "map";
            }
            arrayList.set(i, obj);
        }
        Collections.reverse(arrayList);
        for (i = 0; i < arrayList.size(); i++) {
            String str = (String) arrayList.get(i);
            if (str.equals("list")) {
                arrayList.set(i - 1, "<" + ((String) arrayList.get(i - 1)));
                arrayList.set(0, ((String) arrayList.get(0)) + ">");
            } else if (str.equals("map")) {
                arrayList.set(i - 1, "<" + ((String) arrayList.get(i - 1)) + ",");
                arrayList.set(0, ((String) arrayList.get(0)) + ">");
            } else if (str.equals("Array")) {
                arrayList.set(i - 1, "<" + ((String) arrayList.get(i - 1)));
                arrayList.set(0, ((String) arrayList.get(0)) + ">");
            }
        }
        Collections.reverse(arrayList);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            stringBuffer.append((String) it.next());
        }
        return stringBuffer.toString();
    }

    public <T> void a(String str, T t) {
        if (str == null) {
            throw new IllegalArgumentException("put key can not is null");
        } else if (t == null) {
            throw new IllegalArgumentException("put value can not is null");
        } else if (t instanceof Set) {
            throw new IllegalArgumentException("can not support Set");
        } else {
            i iVar = new i();
            iVar.a(this.b);
            iVar.a((Object) t, 0);
            Object a = k.a(iVar.a());
            HashMap hashMap = new HashMap(1);
            ArrayList arrayList = new ArrayList(1);
            a(arrayList, (Object) t);
            hashMap.put(a(arrayList), a);
            this.d.remove(str);
            this.a.put(str, hashMap);
        }
    }

    public static aq a(List<UserInfoBean> list, int i) {
        if (list == null || list.size() == 0) {
            return null;
        }
        com.tencent.bugly.crashreport.common.info.a b = com.tencent.bugly.crashreport.common.info.a.b();
        if (b == null) {
            return null;
        }
        b.t();
        aq aqVar = new aq();
        aqVar.b = b.d;
        aqVar.c = b.h();
        ArrayList arrayList = new ArrayList();
        for (UserInfoBean a : list) {
            ap a2 = a(a);
            if (a2 != null) {
                arrayList.add(a2);
            }
        }
        aqVar.d = arrayList;
        aqVar.e = new HashMap();
        aqVar.e.put("A7", b.f);
        aqVar.e.put("A6", b.s());
        aqVar.e.put("A5", b.r());
        aqVar.e.put("A2", b.p());
        aqVar.e.put("A1", b.p());
        aqVar.e.put("A24", b.h);
        aqVar.e.put("A17", b.q());
        aqVar.e.put("A15", b.w());
        aqVar.e.put("A13", b.x());
        aqVar.e.put("F08", b.v);
        aqVar.e.put("F09", b.w);
        Map E = b.E();
        if (E != null && E.size() > 0) {
            for (Entry entry : E.entrySet()) {
                aqVar.e.put("C04_" + ((String) entry.getKey()), entry.getValue());
            }
        }
        switch (i) {
            case 1:
                aqVar.a = (byte) 1;
                break;
            case 2:
                aqVar.a = (byte) 2;
                break;
            default:
                w.e("unknown up type %d ", Integer.valueOf(i));
                return null;
        }
        return aqVar;
    }

    public static <T extends j> T a(byte[] bArr, Class<T> cls) {
        if (bArr == null || bArr.length <= 0) {
            return null;
        }
        try {
            j jVar = (j) cls.newInstance();
            h hVar = new h(bArr);
            hVar.a("utf-8");
            jVar.a(hVar);
            return jVar;
        } catch (Throwable th) {
            if (!w.b(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    public static al a(Context context, int i, byte[] bArr) {
        com.tencent.bugly.crashreport.common.info.a b = com.tencent.bugly.crashreport.common.info.a.b();
        StrategyBean c = com.tencent.bugly.crashreport.common.strategy.a.a().c();
        if (b == null || c == null) {
            w.e("Can not create request pkg for parameters is invalid.", new Object[0]);
            return null;
        }
        try {
            al alVar = new al();
            synchronized (b) {
                alVar.a = 1;
                alVar.b = b.f();
                alVar.c = b.c;
                alVar.d = b.j;
                alVar.e = b.l;
                b.getClass();
                alVar.f = "2.4.1";
                alVar.g = i;
                alVar.h = bArr == null ? "".getBytes() : bArr;
                alVar.i = b.g;
                alVar.j = b.h;
                alVar.k = new HashMap();
                alVar.l = b.e();
                alVar.m = c.p;
                alVar.o = b.h();
                alVar.p = b.e(context);
                alVar.q = System.currentTimeMillis();
                alVar.r = b.k();
                alVar.s = b.j();
                alVar.t = b.m();
                alVar.u = b.l();
                alVar.v = b.n();
                alVar.w = alVar.p;
                b.getClass();
                alVar.n = "com.tencent.bugly";
                alVar.k.put("A26", b.y());
                alVar.k.put("F11", b.z);
                alVar.k.put("F12", b.y);
                alVar.k.put("G1", b.u());
                alVar.k.put("G2", b.K());
                alVar.k.put("G3", b.L());
                alVar.k.put("G4", b.M());
                alVar.k.put("G5", b.N());
                alVar.k.put("G6", b.O());
                alVar.k.put("G7", Long.toString(b.P()));
                alVar.k.put("D3", b.k);
                if (com.tencent.bugly.b.b != null) {
                    for (com.tencent.bugly.a aVar : com.tencent.bugly.b.b) {
                        if (!(aVar.versionKey == null || aVar.version == null)) {
                            alVar.k.put(aVar.versionKey, aVar.version);
                        }
                    }
                }
            }
            t a = t.a();
            if (!(a == null || a.a || bArr == null)) {
                alVar.h = y.a(alVar.h, 2, 1, c.u);
                if (alVar.h == null) {
                    w.e("reqPkg sbuffer error!", new Object[0]);
                    return null;
                }
            }
            Map D = b.D();
            if (D != null) {
                for (Entry entry : D.entrySet()) {
                    alVar.k.put(entry.getKey(), entry.getValue());
                }
            }
            return alVar;
        } catch (Throwable th) {
            if (!w.b(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    private void a(ArrayList<String> arrayList, Object obj) {
        if (obj.getClass().isArray()) {
            if (!obj.getClass().getComponentType().toString().equals("byte")) {
                throw new IllegalArgumentException("only byte[] is supported");
            } else if (Array.getLength(obj) > 0) {
                arrayList.add("java.util.List");
                a((ArrayList) arrayList, Array.get(obj, 0));
            } else {
                arrayList.add("Array");
                arrayList.add("?");
            }
        } else if (obj instanceof Array) {
            throw new IllegalArgumentException("can not support Array, please use List");
        } else if (obj instanceof List) {
            arrayList.add("java.util.List");
            List list = (List) obj;
            if (list.size() > 0) {
                a((ArrayList) arrayList, list.get(0));
            } else {
                arrayList.add("?");
            }
        } else if (obj instanceof Map) {
            arrayList.add("java.util.Map");
            Map map = (Map) obj;
            if (map.size() > 0) {
                Object next = map.keySet().iterator().next();
                Object obj2 = map.get(next);
                arrayList.add(next.getClass().getName());
                a((ArrayList) arrayList, obj2);
                return;
            }
            arrayList.add("?");
            arrayList.add("?");
        } else {
            arrayList.add(obj.getClass().getName());
        }
    }

    public byte[] a() {
        i iVar = new i(0);
        iVar.a(this.b);
        iVar.a(this.a, 0);
        return k.a(iVar.a());
    }

    public void a(byte[] bArr) {
        this.c.a(bArr);
        this.c.a(this.b);
        Map hashMap = new HashMap(1);
        HashMap hashMap2 = new HashMap(1);
        hashMap2.put("", new byte[0]);
        hashMap.put("", hashMap2);
        this.a = this.c.a(hashMap, 0, false);
    }

    public static byte[] a(al alVar) {
        try {
            d dVar = new d();
            dVar.b();
            dVar.a("utf-8");
            dVar.b(1);
            dVar.b("RqdServer");
            dVar.c(SyncSampleEntry.TYPE);
            dVar.a("detail", alVar);
            return dVar.a();
        } catch (Throwable th) {
            if (!w.b(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    public static am a(byte[] bArr, boolean z) {
        if (bArr != null) {
            try {
                am amVar;
                d dVar = new d();
                dVar.b();
                dVar.a("utf-8");
                dVar.a(bArr);
                Object b = dVar.b("detail", new am());
                if (am.class.isInstance(b)) {
                    amVar = (am) am.class.cast(b);
                } else {
                    amVar = null;
                }
                if (z || amVar == null || amVar.c == null || amVar.c.length <= 0) {
                    return amVar;
                }
                w.c("resp buf %d", Integer.valueOf(amVar.c.length));
                amVar.c = y.b(amVar.c, 2, 1, StrategyBean.d);
                if (amVar.c != null) {
                    return amVar;
                }
                w.e("resp sbuffer error!", new Object[0]);
                return null;
            } catch (Throwable th) {
                if (!w.b(th)) {
                    th.printStackTrace();
                }
            }
        }
        return null;
    }

    public static byte[] a(j jVar) {
        try {
            i iVar = new i();
            iVar.a("utf-8");
            jVar.a(iVar);
            return iVar.b();
        } catch (Throwable th) {
            if (!w.b(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }
}
