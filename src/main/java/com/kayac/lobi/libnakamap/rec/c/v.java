package com.kayac.lobi.libnakamap.rec.c;

import com.kayac.lobi.sdk.LobiCoreAPI.APICallback;

final class v implements Runnable {
    final /* synthetic */ APICallback a;

    v(APICallback aPICallback) {
        this.a = aPICallback;
    }

    public void run() {
        if (a.a(this.a)) {
            f.a(new w(this));
        }
    }
}
