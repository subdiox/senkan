package com.kayac.lobi.libnakamap.rec.recorder.a;

import com.kayac.lobi.libnakamap.rec.LobiRec;
import com.kayac.lobi.libnakamap.rec.a.b;
import com.kayac.lobi.libnakamap.rec.a.c;

class f implements Runnable {
    final /* synthetic */ e a;

    f(e eVar) {
        this.a = eVar;
    }

    public void run() {
        c.a();
        try {
            this.a.d = e.b(this.a.e, "video/avc");
        } catch (Throwable e) {
            e.b.c("no color format found for video/avc");
            LobiRec.setLastErrorCode(LobiRec.ERROR_BAD_ENCODER_CONNECTION);
            b.a(e);
        }
        c.a(e.a, "getColorFormat");
    }
}
