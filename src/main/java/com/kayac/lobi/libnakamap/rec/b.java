package com.kayac.lobi.libnakamap.rec;

import com.kayac.lobi.libnakamap.rec.b.a;
import com.kayac.lobi.sdk.LobiCore;

final class b implements Runnable {
    final /* synthetic */ boolean a;

    b(boolean z) {
        this.a = z;
    }

    public void run() {
        if (this.a) {
            LobiRec.uploadMovies(LobiCore.sharedInstance().getContext());
        }
        try {
            a.a().f();
        } catch (Throwable e) {
            com.kayac.lobi.libnakamap.rec.a.b.a(e);
        }
    }
}
