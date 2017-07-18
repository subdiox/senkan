package com.kayac.lobi.libnakamap.rec.c;

import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.sdk.LobiCoreAPI.APICallback;
import java.util.Map;
import org.json.JSONObject;

class ai implements APICallback {
    final /* synthetic */ ah a;

    ai(ah ahVar) {
        this.a = ahVar;
    }

    public void onResult(int i, JSONObject jSONObject) {
        if (i != 0) {
            this.a.a.onResult(i, jSONObject);
            return;
        }
        UserValue currentUser = AccountDatastore.getCurrentUser();
        Map a = a.a();
        a.putAll(new aj(this, currentUser));
        a.a(1, al.b(), a, new ak(this));
    }
}
