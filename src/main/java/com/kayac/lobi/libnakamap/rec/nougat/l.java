package com.kayac.lobi.libnakamap.rec.nougat;

import android.widget.Toast;

class l implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ g b;

    l(g gVar, String str) {
        this.b = gVar;
        this.a = str;
    }

    public void run() {
        Toast.makeText(this.b.b, this.a, 0).show();
    }
}
