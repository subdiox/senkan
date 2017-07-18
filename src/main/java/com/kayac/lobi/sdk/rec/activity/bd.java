package com.kayac.lobi.sdk.rec.activity;

import com.kayac.lobi.libnakamap.datastore.AccountDDL.KKey.UpdateAt;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.KKey.Rec;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastore;
import com.kayac.lobi.sdk.LobiCoreAPI.APICallback;
import com.kayac.lobi.sdk.rec.R;
import org.json.JSONObject;

class bd implements APICallback {
    final /* synthetic */ RecPostVideoActivity a;

    bd(RecPostVideoActivity recPostVideoActivity) {
        this.a = recPostVideoActivity;
    }

    public void onResult(int i, JSONObject jSONObject) {
        if (i != 0 || jSONObject == null) {
            this.a.finishWithError(R.string.lobisdk_network_disconnected);
            return;
        }
        TransactionDatastore.setValue(Rec.REC_INFO, jSONObject.toString());
        AccountDatastore.setKKValue(UpdateAt.KEY1, UpdateAt.GET_REC_INFO, Long.valueOf(System.currentTimeMillis()));
        this.a.handleRecInfoResponse(jSONObject);
    }
}
