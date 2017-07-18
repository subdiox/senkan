package com.tencent.bugly.proguard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* compiled from: BUGLY */
public final class aj extends j {
    private static ArrayList<ai> A = new ArrayList();
    private static Map<String, String> B = new HashMap();
    private static Map<String, String> C = new HashMap();
    private static Map<String, String> v = new HashMap();
    private static ah w = new ah();
    private static ag x = new ag();
    private static ArrayList<ag> y = new ArrayList();
    private static ArrayList<ag> z = new ArrayList();
    public String a = "";
    public long b = 0;
    public String c = "";
    public String d = "";
    public String e = "";
    public String f = "";
    public String g = "";
    public Map<String, String> h = null;
    public String i = "";
    public ah j = null;
    public int k = 0;
    public String l = "";
    public String m = "";
    public ag n = null;
    public ArrayList<ag> o = null;
    public ArrayList<ag> p = null;
    public ArrayList<ai> q = null;
    public Map<String, String> r = null;
    public Map<String, String> s = null;
    public String t = "";
    private boolean u = true;

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
        if (this.h != null) {
            iVar.a(this.h, 7);
        }
        if (this.i != null) {
            iVar.a(this.i, 8);
        }
        if (this.j != null) {
            iVar.a(this.j, 9);
        }
        iVar.a(this.k, 10);
        if (this.l != null) {
            iVar.a(this.l, 11);
        }
        if (this.m != null) {
            iVar.a(this.m, 12);
        }
        if (this.n != null) {
            iVar.a(this.n, 13);
        }
        if (this.o != null) {
            iVar.a(this.o, 14);
        }
        if (this.p != null) {
            iVar.a(this.p, 15);
        }
        if (this.q != null) {
            iVar.a(this.q, 16);
        }
        if (this.r != null) {
            iVar.a(this.r, 17);
        }
        if (this.s != null) {
            iVar.a(this.s, 18);
        }
        if (this.t != null) {
            iVar.a(this.t, 19);
        }
        iVar.a(this.u, 20);
    }

    static {
        v.put("", "");
        y.add(new ag());
        z.add(new ag());
        A.add(new ai());
        B.put("", "");
        C.put("", "");
    }

    public final void a(h hVar) {
        this.a = hVar.b(0, true);
        this.b = hVar.a(this.b, 1, true);
        this.c = hVar.b(2, true);
        this.d = hVar.b(3, false);
        this.e = hVar.b(4, false);
        this.f = hVar.b(5, false);
        this.g = hVar.b(6, false);
        this.h = (Map) hVar.a(v, 7, false);
        this.i = hVar.b(8, false);
        this.j = (ah) hVar.a(w, 9, false);
        this.k = hVar.a(this.k, 10, false);
        this.l = hVar.b(11, false);
        this.m = hVar.b(12, false);
        this.n = (ag) hVar.a(x, 13, false);
        this.o = (ArrayList) hVar.a(y, 14, false);
        this.p = (ArrayList) hVar.a(z, 15, false);
        this.q = (ArrayList) hVar.a(A, 16, false);
        this.r = (Map) hVar.a(B, 17, false);
        this.s = (Map) hVar.a(C, 18, false);
        this.t = hVar.b(19, false);
        boolean z = this.u;
        this.u = hVar.a(20, false);
    }
}
