package com.kayac.lobi.sdk.rec.activity;

import com.kayac.lobi.sdk.LobiCoreAPI.APICallback;
import org.json.JSONObject;

class r implements APICallback {
    final /* synthetic */ RecPlayActivity a;

    r(RecPlayActivity recPlayActivity) {
        this.a = recPlayActivity;
    }

    public void onResult(int i, JSONObject jSONObject) {
        if (i != 0) {
            RecPlayActivity.showErrorAlert(this.a);
        } else {
            this.a.runOnUiThread(new s(this));
        }
    }
}
