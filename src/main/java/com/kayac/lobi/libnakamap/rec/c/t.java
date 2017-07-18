package com.kayac.lobi.libnakamap.rec.c;

import com.kayac.lobi.libnakamap.value.UserValue;
import java.util.HashMap;

class t extends HashMap<String, String> {
    final /* synthetic */ UserValue a;
    final /* synthetic */ s b;

    t(s sVar, UserValue userValue) {
        this.b = sVar;
        this.a = userValue;
        put("token", this.a.getToken());
        put("service", this.b.a.b);
    }
}
