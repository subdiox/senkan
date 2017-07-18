package com.kayac.lobi.libnakamap.rec.c;

import com.kayac.lobi.sdk.LobiCoreAPI.APICallback;

final class ah implements Runnable {
    final /* synthetic */ APICallback a;
    final /* synthetic */ String b;
    final /* synthetic */ String c;

    ah(APICallback aPICallback, String str, String str2) {
        this.a = aPICallback;
        this.b = str;
        this.c = str2;
    }

    public void run() {
        if (a.a(this.a)) {
            f.a(new ai(this));
        }
    }
}
