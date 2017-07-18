package com.yunva.im.sdk.lib.config;

public class c {
    private static b a;
    private static c b;

    public b a() {
        return a;
    }

    private c() {
    }

    public static synchronized c b() {
        c cVar;
        synchronized (c.class) {
            if (b == null) {
                b = new c();
            }
            cVar = b;
        }
        return cVar;
    }

    public void a(boolean z) {
        if (z) {
            b();
            a = new d();
            return;
        }
        b();
        a = new a();
    }

    public String c() {
        return a.a();
    }

    public String d() {
        return a.c();
    }

    public String e() {
        return a.d();
    }

    public int f() {
        return a.e();
    }

    public String g() {
        return a.f();
    }

    public String h() {
        return a.g();
    }

    public String i() {
        return a.h();
    }

    public String j() {
        return a.i();
    }

    public String k() {
        return a.b();
    }

    public String l() {
        return a.j();
    }

    public String m() {
        return a.k();
    }

    public String n() {
        return a.l();
    }
}
