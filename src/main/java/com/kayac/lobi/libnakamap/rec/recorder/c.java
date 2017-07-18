package com.kayac.lobi.libnakamap.rec.recorder;

import android.app.Activity;
import android.widget.Toast;
import com.kayac.lobi.sdk.rec.R;

class c implements Runnable {
    final /* synthetic */ Activity a;
    final /* synthetic */ b b;

    c(b bVar, Activity activity) {
        this.b = bVar;
        this.a = activity;
    }

    public void run() {
        Toast.makeText(this.a, R.string.lobirec_already_use_mic, 0).show();
    }
}
