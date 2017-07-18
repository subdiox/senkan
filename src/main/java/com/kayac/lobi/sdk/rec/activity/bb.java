package com.kayac.lobi.sdk.rec.activity;

import android.content.Intent;
import android.text.TextUtils;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.KKey.Rec;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.Key;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastore;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastoreAsync;
import com.kayac.lobi.libnakamap.rec.LobiRec;
import com.kayac.lobi.libnakamap.rec.e.a;
import com.kayac.lobi.sdk.LobiCoreAPI.APICallback;
import com.kayac.lobi.sdk.auth.AccountUtil;
import org.json.JSONObject;

class bb implements APICallback {
    final /* synthetic */ ba a;

    bb(ba baVar) {
        this.a = baVar;
    }

    public void onResult(int i, JSONObject jSONObject) {
        Object optString = jSONObject != null ? jSONObject.optString("upload_url") : null;
        if (i != 0 || TextUtils.isEmpty(optString)) {
            this.a.a.showAPIError(jSONObject);
            this.a.a.sendBroadcast(new Intent(LobiRec.ACTION_MOVIE_CREATED_ERROR));
        } else {
            TransactionDatastore.setKKValue(Rec.MOVIE_STATUS_KEY1, this.a.a.mMovieFile.getAbsolutePath(), Rec.MOVIE_STATUS_UPLOADING);
            TransactionDatastore.setKKValue(Rec.UPLOAD_MOVIES_KEY1, this.a.a.mMovieFile.getAbsolutePath(), optString);
            TransactionDatastore.setValue(Rec.LAST_UPLOAD_MOVIE, this.a.a.mMovieFile.getAbsolutePath());
            String optString2 = jSONObject.optString("url");
            Object optString3 = jSONObject.optString("uid");
            if (!(TextUtils.isEmpty(optString2) || TextUtils.isEmpty(optString3))) {
                Intent intent = new Intent(LobiRec.ACTION_MOVIE_CREATED);
                intent.putExtra(LobiRec.EXTRA_MOVIE_CREATED_URL, optString2);
                intent.putExtra(LobiRec.EXTRA_MOVIE_CREATED_VIDEO_ID, optString3);
                this.a.a.sendBroadcast(intent);
                TransactionDatastoreAsync.setValue(Key.VIDEO_UPLOAD_NOTIFICATION_AVAILABLE, Boolean.valueOf(true), null);
                this.a.a.showDialogFragmentAsync(a.a(optString2), "share");
                if (this.a.a.mBindAfterPostingVideo) {
                    AccountUtil.bindWithInstalledAppIfNecessary(this.a.a, 10);
                }
            }
        }
        this.a.a.mSending = false;
    }
}
