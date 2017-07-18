package com.kayac.lobi.sdk.rec.activity;

import android.widget.Toast;

class f implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ e b;

    f(e eVar, String str) {
        this.b = eVar;
        this.a = str;
    }

    public void run() {
        Toast.makeText(this.b.a.b, this.a, 0).show();
    }
}
