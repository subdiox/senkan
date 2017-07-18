package com.kayac.lobi.libnakamap.rec.c;

import com.kayac.lobi.libnakamap.value.UserValue;
import java.util.HashMap;

class o extends HashMap<String, String> {
    final /* synthetic */ UserValue a;
    final /* synthetic */ n b;

    o(n nVar, UserValue userValue) {
        this.b = nVar;
        this.a = userValue;
        put("token", this.a.getToken());
        put("app", "0");
        put("total_length", String.valueOf(this.b.b));
        put("byte_length", String.valueOf(this.b.c));
        put("byte_offset", String.valueOf(this.b.d));
    }
}
