package com.kayac.lobi.libnakamap.rec.c;

import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.sdk.LobiCoreAPI.APICallback;
import java.io.File;
import java.util.Map;
import org.json.JSONObject;

final class n implements APICallback {
    final /* synthetic */ APICallback a;
    final /* synthetic */ long b;
    final /* synthetic */ long c;
    final /* synthetic */ long d;
    final /* synthetic */ File e;
    final /* synthetic */ String f;

    n(APICallback aPICallback, long j, long j2, long j3, File file, String str) {
        this.a = aPICallback;
        this.b = j;
        this.c = j2;
        this.d = j3;
        this.e = file;
        this.f = str;
    }

    public void onResult(int i, JSONObject jSONObject) {
        if (i != 0) {
            this.a.onResult(i, jSONObject);
            return;
        }
        UserValue currentUser = AccountDatastore.getCurrentUser();
        Map a = a.a();
        a.putAll(new o(this, currentUser));
        a.a(1, this.f, a, new p(this), this.b, this.c, this.d, new q(this));
    }
}
