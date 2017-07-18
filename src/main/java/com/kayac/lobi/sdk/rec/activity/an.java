package com.kayac.lobi.sdk.rec.activity;

import com.kayac.lobi.libnakamap.rec.c.f;

class an implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ RecPostVideoActivity b;

    an(RecPostVideoActivity recPostVideoActivity, String str) {
        this.b = recPostVideoActivity;
        this.a = str;
    }

    public void run() {
        f.a(this.a, new ao(this));
    }
}
