package cn.sharesdk.facebook;

import android.webkit.WebView;
import cn.sharesdk.framework.i;

class l extends i {
    final /* synthetic */ i a;

    l(i iVar) {
        this.a = iVar;
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (url != null && url.startsWith("fbconnect://success")) {
            this.a.b(url);
        }
        return super.shouldOverrideUrlLoading(view, url);
    }
}
