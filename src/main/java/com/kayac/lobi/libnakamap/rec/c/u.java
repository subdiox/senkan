package com.kayac.lobi.libnakamap.rec.c;

import java.util.HashMap;
import java.util.Map;

class u extends HashMap<String, String> {
    final /* synthetic */ Map a;
    final /* synthetic */ s b;

    u(s sVar, Map map) {
        this.b = sVar;
        this.a = map;
        put("url", al.d() + "?" + am.a(this.a));
    }
}
