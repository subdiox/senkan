package com.kayac.lobi.libnakamap.rec.c;

import com.kayac.lobi.sdk.LobiCoreAPI.APICallback;
import org.json.JSONObject;

final class ag extends a {
    final /* synthetic */ APICallback a;

    ag(APICallback aPICallback) {
        this.a = aPICallback;
    }

    public void a(int i, JSONObject jSONObject) {
        if (i == 0 && jSONObject.optString("access_token") != null) {
            String.valueOf(System.currentTimeMillis());
        }
        this.a.onResult(i, jSONObject);
    }
}
