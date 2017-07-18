package com.kayac.lobi.sdk.rec.activity;

import android.net.Uri.Builder;
import android.webkit.WebView;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.sdk.rec.R;

class j implements Runnable {
    final /* synthetic */ i a;

    j(i iVar) {
        this.a = iVar;
    }

    public void run() {
        String access$1900;
        if (this.a.a.a.mUri == null) {
            access$1900 = this.a.a.a.mVideoUid != null ? this.a.a.a.setupVideoDetailUrl() : this.a.a.a.mEventFields != null ? this.a.a.a.setupVideoTopUrl() : this.a.a.a.setupVideoListUrl();
        } else if (this.a.a.a.mUri.getHost().endsWith(".lobi.co")) {
            Builder buildUpon = this.a.a.a.mUri.buildUpon();
            buildUpon.appendQueryParameter("token", AccountDatastore.getCurrentUser().getToken());
            access$1900 = buildUpon.toString();
        } else {
            access$1900 = this.a.a.a.setupVideoListUrl();
        }
        this.a.a.a.mWebView = (WebView) this.a.a.a.findViewById(R.id.lobi_rec_play_content);
        this.a.a.a.mWebView.loadUrl(access$1900);
    }
}
