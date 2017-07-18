package com.kayac.lobi.libnakamap.rec.c;

import com.kayac.lobi.sdk.LobiCoreAPI.APICallback;

final class r implements Runnable {
    final /* synthetic */ APICallback a;
    final /* synthetic */ String b;

    r(APICallback aPICallback, String str) {
        this.a = aPICallback;
        this.b = str;
    }

    public void run() {
        if (a.a(this.a)) {
            f.a(new s(this));
        }
    }
}
