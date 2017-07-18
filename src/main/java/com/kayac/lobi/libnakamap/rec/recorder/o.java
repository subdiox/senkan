package com.kayac.lobi.libnakamap.rec.recorder;

import com.kayac.lobi.libnakamap.rec.recorder.j.a.a;

class o implements Runnable {
    final /* synthetic */ n a;

    o(n nVar) {
        this.a = nVar;
    }

    public void run() {
        this.a.a.t = true;
        this.a.a.z.setLiveWipeStatus(a.Icon);
        this.a.a.q = false;
    }
}
