package com.kayac.lobi.sdk.rec.activity;

import android.text.TextUtils;
import android.webkit.WebView;
import com.kayac.lobi.sdk.LobiCoreAPI.APICallback;
import org.json.JSONObject;

class bg implements APICallback {
    final /* synthetic */ WebView a;
    final /* synthetic */ RecSNSLoginActivity b;

    bg(RecSNSLoginActivity recSNSLoginActivity, WebView webView) {
        this.b = recSNSLoginActivity;
        this.a = webView;
    }

    public void onResult(int i, JSONObject jSONObject) {
        if (i != 0) {
            this.b.finish();
            return;
        }
        Object optString = jSONObject.optString("url");
        if (TextUtils.isEmpty(optString)) {
            this.b.finish();
        } else {
            this.a.post(new bh(this, optString));
        }
    }
}
