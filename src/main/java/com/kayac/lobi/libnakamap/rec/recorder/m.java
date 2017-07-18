package com.kayac.lobi.libnakamap.rec.recorder;

import android.widget.Toast;
import com.kayac.lobi.sdk.rec.R;

class m implements Runnable {
    final /* synthetic */ j a;

    m(j jVar) {
        this.a = jVar;
    }

    public void run() {
        Toast.makeText(this.a.c, this.a.c.getString(R.string.lobirec_recording_failed), 1).show();
    }
}
