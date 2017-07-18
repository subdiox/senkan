package com.kayac.lobi.libnakamap.rec.recorder;

import com.kayac.lobi.libnakamap.rec.recorder.j.a.a;

class p implements Runnable {
    final /* synthetic */ n a;

    p(n nVar) {
        this.a = nVar;
    }

    public void run() {
        this.a.a.s = true;
        this.a.a.z.setLiveWipeStatus(a.Icon);
        this.a.a.q = false;
    }
}
