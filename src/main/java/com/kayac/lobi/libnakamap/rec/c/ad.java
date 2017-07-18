package com.kayac.lobi.libnakamap.rec.c;

import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.sdk.LobiCoreAPI.APICallback;
import java.util.Map;

final class ad implements Runnable {
    final /* synthetic */ APICallback a;

    ad(APICallback aPICallback) {
        this.a = aPICallback;
    }

    public void run() {
        UserValue currentUser = AccountDatastore.getCurrentUser();
        Map a = a.a();
        a.put("token", currentUser.getToken());
        a.a(0, al.g() + "?" + am.a(a), a, new ae(this));
    }
}
