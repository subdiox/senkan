package com.kayac.lobi.libnakamap.rec.c;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.kayac.lobi.sdk.LobiCore;
import com.kayac.lobi.sdk.rec.service.MovieUploadService;
import org.json.JSONObject;

class m extends a {
    final /* synthetic */ k a;

    m(k kVar) {
        this.a = kVar;
    }

    public void a(int i, JSONObject jSONObject) {
        this.a.q.onResult(i, jSONObject);
        if (i == 0 && !this.a.r) {
            Object optString = jSONObject.optString("upload_url");
            if (!TextUtils.isEmpty(optString)) {
                Context context = LobiCore.sharedInstance().getContext();
                Intent intent = new Intent(context, MovieUploadService.class);
                intent.putExtra("EXTRA_FILE_PATH", this.a.s);
                intent.putExtra("EXTRA_UPLOAD_URL", optString);
                context.startService(intent);
            }
        }
    }
}
