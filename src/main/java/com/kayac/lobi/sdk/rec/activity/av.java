package com.kayac.lobi.sdk.rec.activity;

import com.kayac.lobi.sdk.LobiCoreAPI.APICallback;
import org.json.JSONObject;

class av implements APICallback {
    final /* synthetic */ RecPostVideoActivity a;

    av(RecPostVideoActivity recPostVideoActivity) {
        this.a = recPostVideoActivity;
    }

    public void onResult(int i, JSONObject jSONObject) {
        if (i != 0) {
            RecPostVideoActivity.showErrorAlert(this.a);
        } else {
            this.a.updateStatusMe();
        }
    }
}
