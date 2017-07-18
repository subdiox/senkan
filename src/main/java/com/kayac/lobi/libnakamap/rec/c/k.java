package com.kayac.lobi.libnakamap.rec.c;

import android.text.TextUtils;
import com.kayac.lobi.sdk.LobiCoreAPI.APICallback;
import java.util.Map;

final class k implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ String b;
    final /* synthetic */ String c;
    final /* synthetic */ long d;
    final /* synthetic */ String e;
    final /* synthetic */ String f;
    final /* synthetic */ long g;
    final /* synthetic */ int h;
    final /* synthetic */ boolean i;
    final /* synthetic */ boolean j;
    final /* synthetic */ boolean k;
    final /* synthetic */ boolean l;
    final /* synthetic */ boolean m;
    final /* synthetic */ boolean n;
    final /* synthetic */ boolean o;
    final /* synthetic */ String p;
    final /* synthetic */ APICallback q;
    final /* synthetic */ boolean r;
    final /* synthetic */ String s;

    k(String str, String str2, String str3, long j, String str4, String str5, long j2, int i, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, String str6, APICallback aPICallback, boolean z8, String str7) {
        this.a = str;
        this.b = str2;
        this.c = str3;
        this.d = j;
        this.e = str4;
        this.f = str5;
        this.g = j2;
        this.h = i;
        this.i = z;
        this.j = z2;
        this.k = z3;
        this.l = z4;
        this.m = z5;
        this.n = z6;
        this.o = z7;
        this.p = str6;
        this.q = aPICallback;
        this.r = z8;
        this.s = str7;
    }

    public void run() {
        Map a = a.a();
        a.putAll(new l(this));
        if (!TextUtils.isEmpty(this.p)) {
            a.put("upload_callback_url", this.p);
        }
        a.a(1, al.c(), a, new m(this));
    }
}
