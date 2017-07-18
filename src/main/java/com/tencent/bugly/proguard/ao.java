package com.tencent.bugly.proguard;

import com.tencent.bugly.crashreport.crash.jni.b;
import java.util.HashMap;
import java.util.Map;

/* compiled from: BUGLY */
public final class ao extends j implements Cloneable {
    private static an m = new an();
    private static Map<String, String> n = new HashMap();
    private static /* synthetic */ boolean o;
    public boolean a = true;
    public boolean b = true;
    public boolean c = true;
    public String d = "";
    public String e = "";
    public an f = null;
    public Map<String, String> g = null;
    public long h = 0;
    public int i = 0;
    private String j = "";
    private String k = "";
    private int l = 0;

    static {
        boolean z;
        if (ao.class.desiredAssertionStatus()) {
            z = false;
        } else {
            z = true;
        }
        o = z;
        n.put("", "");
    }

    public final boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        ao aoVar = (ao) o;
        if (k.a(this.a, aoVar.a) && k.a(this.b, aoVar.b) && k.a(this.c, aoVar.c) && k.a(this.d, aoVar.d) && k.a(this.e, aoVar.e) && k.a(this.f, aoVar.f) && k.a(this.g, aoVar.g) && k.a(this.h, aoVar.h) && k.a(this.j, aoVar.j) && k.a(this.k, aoVar.k) && k.a(this.l, aoVar.l) && k.a(this.i, aoVar.i)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        try {
            throw new Exception("Need define key first!");
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public final Object clone() {
        Object obj = null;
        try {
            obj = super.clone();
        } catch (CloneNotSupportedException e) {
            if (!o) {
                throw new AssertionError();
            }
        }
        return obj;
    }

    public final void a(i iVar) {
        iVar.a(this.a, 0);
        iVar.a(this.b, 1);
        iVar.a(this.c, 2);
        if (this.d != null) {
            iVar.a(this.d, 3);
        }
        if (this.e != null) {
            iVar.a(this.e, 4);
        }
        if (this.f != null) {
            iVar.a(this.f, 5);
        }
        if (this.g != null) {
            iVar.a(this.g, 6);
        }
        iVar.a(this.h, 7);
        if (this.j != null) {
            iVar.a(this.j, 8);
        }
        if (this.k != null) {
            iVar.a(this.k, 9);
        }
        iVar.a(this.l, 10);
        iVar.a(this.i, 11);
    }

    public final void a(h hVar) {
        boolean z = this.a;
        this.a = hVar.a(0, true);
        z = this.b;
        this.b = hVar.a(1, true);
        z = this.c;
        this.c = hVar.a(2, true);
        this.d = hVar.b(3, false);
        this.e = hVar.b(4, false);
        this.f = (an) hVar.a(m, 5, false);
        this.g = (Map) hVar.a(n, 6, false);
        this.h = hVar.a(this.h, 7, false);
        this.j = hVar.b(8, false);
        this.k = hVar.b(9, false);
        this.l = hVar.a(this.l, 10, false);
        this.i = hVar.a(this.i, 11, false);
    }

    public final void a(StringBuilder stringBuilder, int i) {
        b bVar = new b(stringBuilder, i);
        bVar.a(this.a, "enable");
        bVar.a(this.b, "enableUserInfo");
        bVar.a(this.c, "enableQuery");
        bVar.b(this.d, "url");
        bVar.b(this.e, "expUrl");
        bVar.a(this.f, "security");
        bVar.a(this.g, "valueMap");
        bVar.a(this.h, "strategylastUpdateTime");
        bVar.b(this.j, "httpsUrl");
        bVar.b(this.k, "httpsExpUrl");
        bVar.a(this.l, "eventRecordCount");
        bVar.a(this.i, "eventTimeInterval");
    }
}
