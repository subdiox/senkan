package com.kayac.lobi.libnakamap.rec.b;

import com.kayac.lobi.libnakamap.rec.LobiRecAPI;
import com.kayac.lobi.libnakamap.rec.b.a.c;
import java.io.File;
import java.util.concurrent.TimeUnit;

class b implements Runnable {
    final /* synthetic */ a a;

    b(a aVar) {
        this.a = aVar;
    }

    public void run() {
        File a;
        a.b.b("ConcatTask BEGIN");
        this.a.g = true;
        while (LobiRecAPI.isCapturing() && this.a.e != null) {
            a.b.b("ConcatTask waiting... (Muxer is working)");
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (Throwable e) {
                com.kayac.lobi.libnakamap.rec.a.b.a(e);
            }
        }
        if (this.a.e != null) {
            a.b.b("ConcatTask working... concat");
            try {
                a = this.a.d.a(this.a.f);
            } catch (Throwable e2) {
                a.b.c("failed to concat movie files");
                com.kayac.lobi.libnakamap.rec.a.b.a(e2);
                a = null;
            }
            a.b.b("ConcatTask working... finished");
        } else {
            a = null;
        }
        c a2 = this.a.e;
        if (a2 != null) {
            a2.onLoadVideo(a);
            this.a.e = null;
        }
        this.a.g = false;
        a.b.b("ConcatTask END");
    }
}
