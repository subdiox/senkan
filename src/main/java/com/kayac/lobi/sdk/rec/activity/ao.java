package com.kayac.lobi.sdk.rec.activity;

import com.kayac.lobi.libnakamap.rec.a.b;
import com.kayac.lobi.libnakamap.rec.c.f.a;
import com.kayac.lobi.sdk.rec.R;

class ao implements a {
    final /* synthetic */ an a;

    ao(an anVar) {
        this.a = anVar;
    }

    public void a(int i) {
        if (i <= 0) {
            b.c("LobiRecSDK", "zero length movie");
            this.a.b.finishWithError(R.string.lobirec_cant_open_video);
            return;
        }
        this.a.b.updateThumbnail(this.a.a, (long) i);
    }
}
