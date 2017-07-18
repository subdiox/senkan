package com.kayac.lobi.sdk.rec.activity;

import com.kayac.lobi.sdk.rec.R;

class bh implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ bg b;

    bh(bg bgVar, String str) {
        this.b = bgVar;
        this.a = str;
    }

    public void run() {
        this.b.b.findViewById(R.id.lobi_rec_sns_login_loading).setVisibility(8);
        this.b.a.loadUrl(this.a);
    }
}
