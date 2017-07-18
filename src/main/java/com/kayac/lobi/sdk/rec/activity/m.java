package com.kayac.lobi.sdk.rec.activity;

import android.webkit.WebView;
import com.kayac.lobi.sdk.rec.R;

class m implements Runnable {
    final /* synthetic */ k a;

    m(k kVar) {
        this.a = kVar;
    }

    public void run() {
        this.a.c.mWebView = (WebView) this.a.c.findViewById(R.id.lobi_rec_play_content);
        this.a.c.mWebView.reload();
    }
}
