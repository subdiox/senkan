package com.kayac.lobi.sdk.rec.activity;

import com.kayac.lobi.sdk.LobiCoreAPI.APICallback;
import com.kayac.lobi.sdk.rec.R;
import org.json.JSONObject;

class y implements APICallback {
    final /* synthetic */ x a;

    y(x xVar) {
        this.a = xVar;
    }

    public void onResult(int i, JSONObject jSONObject) {
        this.a.b.b.hideIndicator();
        if (i != 0) {
            this.a.b.b.runOnUiThread(new z(this, this.a.b.b.getString(R.string.lobirec_failed)));
        }
    }
}
