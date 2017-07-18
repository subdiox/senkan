package com.kayac.lobi.sdk.rec.activity;

import com.kayac.lobi.sdk.LobiCoreAPI.APICallback;
import java.io.File;
import org.json.JSONObject;

class k implements APICallback {
    final /* synthetic */ boolean a;
    final /* synthetic */ File b;
    final /* synthetic */ RecPlayActivity c;

    k(RecPlayActivity recPlayActivity, boolean z, File file) {
        this.c = recPlayActivity;
        this.a = z;
        this.b = file;
    }

    public void onResult(int i, JSONObject jSONObject) {
        this.c.hideIndicator();
        if (this.a) {
            this.b.deleteOnExit();
        }
        if (i != 0) {
            this.c.runOnUiThread(new l(this));
        } else {
            this.c.runOnUiThread(new m(this));
        }
    }
}
