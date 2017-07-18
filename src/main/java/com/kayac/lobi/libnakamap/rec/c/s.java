package com.kayac.lobi.libnakamap.rec.c;

import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.sdk.LobiCoreAPI.APICallback;
import java.util.Map;
import org.json.JSONObject;

class s implements APICallback {
    final /* synthetic */ r a;

    s(r rVar) {
        this.a = rVar;
    }

    public void onResult(int i, JSONObject jSONObject) {
        if (i != 0) {
            this.a.a.onResult(i, jSONObject);
            return;
        }
        UserValue currentUser = AccountDatastore.getCurrentUser();
        Map a = a.a();
        a.putAll(new t(this, currentUser));
        this.a.a.onResult(0, new JSONObject(new u(this, a)));
    }
}
