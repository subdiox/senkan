package com.kayac.lobi.sdk.rec.activity;

import android.widget.Toast;
import com.kayac.lobi.sdk.rec.R;

class l implements Runnable {
    final /* synthetic */ k a;

    l(k kVar) {
        this.a = kVar;
    }

    public void run() {
        Toast.makeText(this.a.c, this.a.c.getString(R.string.lobirec_failed), 0).show();
    }
}
