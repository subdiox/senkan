package com.kayac.lobi.libnakamap.rec.c;

import com.kayac.lobi.libnakamap.net.APIDef.PostAccusations.RequestKey;
import com.kayac.lobi.libnakamap.value.UserValue;
import java.util.HashMap;

class aj extends HashMap<String, String> {
    final /* synthetic */ UserValue a;
    final /* synthetic */ ai b;

    aj(ai aiVar, UserValue userValue) {
        this.b = aiVar;
        this.a = userValue;
        put("token", this.a.getToken());
        put("video", this.b.a.b);
        put(RequestKey.REASON, this.b.a.c);
    }
}
