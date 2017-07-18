package com.kayac.lobi.libnakamap.rec.c;

import com.kayac.lobi.sdk.LobiCoreAPI.APICallback;

final class g implements Runnable {
    final /* synthetic */ APICallback a;

    g(APICallback aPICallback) {
        this.a = aPICallback;
    }

    public void run() {
        f.d(this.a);
    }
}
