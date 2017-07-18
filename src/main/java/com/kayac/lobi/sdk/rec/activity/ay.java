package com.kayac.lobi.sdk.rec.activity;

import com.kayac.lobi.sdk.LobiCoreAPI.APICallback;
import org.json.JSONArray;
import org.json.JSONObject;

class ay implements APICallback {
    final /* synthetic */ RecPostVideoActivity a;

    ay(RecPostVideoActivity recPostVideoActivity) {
        this.a = recPostVideoActivity;
    }

    public void onResult(int i, JSONObject jSONObject) {
        if (i == 0) {
            JSONArray optJSONArray = jSONObject.optJSONArray("user_auths");
            if (optJSONArray != null) {
                for (int i2 = 0; i2 < optJSONArray.length(); i2++) {
                    JSONObject optJSONObject = optJSONArray.optJSONObject(i2);
                    if (optJSONObject != null) {
                        optJSONObject.optString("service");
                    }
                }
            }
        }
    }
}
