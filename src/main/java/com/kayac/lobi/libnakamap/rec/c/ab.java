package com.kayac.lobi.libnakamap.rec.c;

import com.kayac.lobi.sdk.LobiCoreAPI.APICallback;
import java.util.Map;

final class ab implements Runnable {
    final /* synthetic */ Map a;
    final /* synthetic */ APICallback b;

    ab(Map map, APICallback aPICallback) {
        this.a = map;
        this.b = aPICallback;
    }

    public void run() {
        Map a = a.a();
        a.putAll(this.a);
        a.a(0, al.f() + "?" + am.a(a), a, new ac(this));
    }
}
