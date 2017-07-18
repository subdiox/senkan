package com.kayac.lobi.libnakamap.rec.c;

import com.kayac.lobi.libnakamap.value.UserValue;
import java.util.HashMap;

class x extends HashMap<String, String> {
    final /* synthetic */ UserValue a;
    final /* synthetic */ w b;

    x(w wVar, UserValue userValue) {
        this.b = wVar;
        this.a = userValue;
        put("token", this.a.getToken());
    }
}
