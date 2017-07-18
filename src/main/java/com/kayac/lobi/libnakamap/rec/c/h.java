package com.kayac.lobi.libnakamap.rec.c;

import com.kayac.lobi.sdk.LobiCoreAPI.APICallback;

final class h implements Runnable {
    final /* synthetic */ APICallback a;
    final /* synthetic */ String b;
    final /* synthetic */ String c;
    final /* synthetic */ String d;
    final /* synthetic */ long e;
    final /* synthetic */ String f;
    final /* synthetic */ String g;
    final /* synthetic */ boolean h;
    final /* synthetic */ boolean i;
    final /* synthetic */ boolean j;
    final /* synthetic */ boolean k;
    final /* synthetic */ boolean l;
    final /* synthetic */ boolean m;
    final /* synthetic */ String n;
    final /* synthetic */ boolean o;
    final /* synthetic */ boolean p;

    h(APICallback aPICallback, String str, String str2, String str3, long j, String str4, String str5, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, String str6, boolean z7, boolean z8) {
        this.a = aPICallback;
        this.b = str;
        this.c = str2;
        this.d = str3;
        this.e = j;
        this.f = str4;
        this.g = str5;
        this.h = z;
        this.i = z2;
        this.j = z3;
        this.k = z4;
        this.l = z5;
        this.m = z6;
        this.n = str6;
        this.o = z7;
        this.p = z8;
    }

    public void run() {
        if (a.a(this.a)) {
            f.a(new i(this));
        }
    }
}
