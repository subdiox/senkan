package com.kayac.lobi.sdk.rec.activity;

import android.webkit.WebView;
import com.kayac.lobi.sdk.LobiCoreAPI.APICallback;
import com.kayac.lobi.sdk.rec.R;
import org.json.JSONObject;

class e implements APICallback {
    final /* synthetic */ d a;

    e(d dVar) {
        this.a = dVar;
    }

    public void onResult(int i, JSONObject jSONObject) {
        this.a.b.hideIndicator();
        if (i != 0) {
            this.a.b.runOnUiThread(new f(this, this.a.b.getString(R.string.lobirec_failed)));
            return;
        }
        this.a.b.mWebView = (WebView) this.a.b.findViewById(R.id.lobi_rec_play_content);
        this.a.b.mWebView.reload();
    }
}
