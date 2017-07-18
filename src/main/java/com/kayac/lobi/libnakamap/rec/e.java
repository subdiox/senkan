package com.kayac.lobi.libnakamap.rec;

import android.text.TextUtils;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.KKey.Rec;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastore;
import com.kayac.lobi.sdk.LobiCoreAPI.APICallback;
import org.json.JSONObject;

class e implements APICallback {
    final /* synthetic */ String a;
    final /* synthetic */ d b;

    e(d dVar, String str) {
        this.b = dVar;
        this.a = str;
    }

    public void onResult(int i, JSONObject jSONObject) {
        String optString = jSONObject != null ? jSONObject.optString("upload_url") : null;
        if (i != 0 || TextUtils.isEmpty(optString)) {
            this.b.a.onResult(1, null, null, null);
            return;
        }
        TransactionDatastore.setKKValue(Rec.MOVIE_STATUS_KEY1, this.a, Rec.MOVIE_STATUS_UPLOADING);
        TransactionDatastore.setKKValue(Rec.UPLOAD_MOVIES_KEY1, this.a, optString);
        TransactionDatastore.setKKValue(Rec.UPLOAD_RANKERS_MOVIES_KEY1, this.a, "1");
        TransactionDatastore.setValue(Rec.LAST_UPLOAD_MOVIE, this.a);
        this.b.a.onResult(0, jSONObject.optString("uid"), this.a, optString);
    }
}
