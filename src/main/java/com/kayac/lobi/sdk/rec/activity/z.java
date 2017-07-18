package com.kayac.lobi.sdk.rec.activity;

import android.widget.Toast;

class z implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ y b;

    z(y yVar, String str) {
        this.b = yVar;
        this.a = str;
    }

    public void run() {
        Toast.makeText(this.b.a.b.b, this.a, 0).show();
    }
}
