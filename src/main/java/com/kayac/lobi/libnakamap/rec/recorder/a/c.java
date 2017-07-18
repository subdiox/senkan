package com.kayac.lobi.libnakamap.rec.recorder.a;

import com.kayac.lobi.libnakamap.rec.recorder.j;
import com.kayac.lobi.sdk.LobiCoreAPI.APICallback;
import java.util.Map;
import org.json.JSONObject;

class c implements APICallback {
    final /* synthetic */ Map a;
    final /* synthetic */ j b;
    final /* synthetic */ boolean c;
    final /* synthetic */ int d;
    final /* synthetic */ int e;
    final /* synthetic */ JSONObject f;
    final /* synthetic */ boolean g;
    final /* synthetic */ b h;

    c(b bVar, Map map, j jVar, boolean z, int i, int i2, JSONObject jSONObject, boolean z2) {
        this.h = bVar;
        this.a = map;
        this.b = jVar;
        this.c = z;
        this.d = i;
        this.e = i2;
        this.f = jSONObject;
        this.g = z2;
    }

    public void onResult(int i, JSONObject jSONObject) {
        if (i == 0 && jSONObject != null) {
            b.b.a("fetched and save new config: " + jSONObject.toString());
            b.a(jSONObject, this.a);
            this.h.a(this.b, this.c, this.d, this.e, jSONObject);
        } else if (i != 100) {
            b.b.a("response error: disable");
            this.h.a(this.b);
        } else if (this.f == null || !this.g) {
            b.b.a("network error: disable (no reusable saved config)");
            this.h.a(this.b);
        } else {
            b.b.a("network error: reuse saved config: " + this.f.toString());
            this.h.a(this.b, this.c, this.d, this.e, this.f);
        }
    }
}
