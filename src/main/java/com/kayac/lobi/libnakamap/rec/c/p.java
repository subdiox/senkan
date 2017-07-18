package com.kayac.lobi.libnakamap.rec.c;

import com.kayac.lobi.libnakamap.net.APIDef.PostAppData.RequestKey;
import java.io.File;
import java.util.HashMap;

class p extends HashMap<String, File> {
    final /* synthetic */ n a;

    p(n nVar) {
        this.a = nVar;
        put(RequestKey.DATA, this.a.e);
    }
}
