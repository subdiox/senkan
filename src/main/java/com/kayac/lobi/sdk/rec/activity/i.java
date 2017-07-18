package com.kayac.lobi.sdk.rec.activity;

import com.kayac.lobi.sdk.LobiCoreAPI.APICallback;
import org.json.JSONObject;

class i implements APICallback {
    final /* synthetic */ h a;

    i(h hVar) {
        this.a = hVar;
    }

    public void onResult(int i, JSONObject jSONObject) {
        if (i != 0) {
            RecPlayActivity.showErrorAlert(this.a.a);
        } else {
            this.a.a.runOnUiThread(new j(this));
        }
    }
}
