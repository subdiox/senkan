package com.kayac.lobi.libnakamap.rec.c;

import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.sdk.LobiCoreAPI.APICallback;
import java.io.File;
import org.json.JSONObject;

class i implements APICallback {
    final /* synthetic */ h a;

    i(h hVar) {
        this.a = hVar;
    }

    public void onResult(int i, JSONObject jSONObject) {
        if (i != 0) {
            this.a.a.onResult(i, jSONObject);
            return;
        }
        UserValue currentUser = AccountDatastore.getCurrentUser();
        File file = new File(this.a.b);
        if (file.exists()) {
            f.a(this.a.b, new j(this, currentUser, file));
        }
    }
}
