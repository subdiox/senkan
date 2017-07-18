package com.kayac.lobi.libnakamap.rec.c;

import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.sdk.LobiCoreAPI.APICallback;
import java.util.Map;
import org.json.JSONObject;

class w implements APICallback {
    final /* synthetic */ v a;

    w(v vVar) {
        this.a = vVar;
    }

    public void onResult(int i, JSONObject jSONObject) {
        if (i != 0) {
            this.a.a.onResult(i, jSONObject);
            return;
        }
        UserValue currentUser = AccountDatastore.getCurrentUser();
        Map a = a.a();
        a.putAll(new x(this, currentUser));
        a.a(0, al.e() + "?" + am.a(a), a, new y(this));
    }
}
