package cn.sharesdk.twitter;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import cn.sharesdk.framework.authorize.g;
import com.mob.tools.utils.R;

public class b extends cn.sharesdk.framework.authorize.b {
    private boolean d;

    public b(g gVar) {
        super(gVar);
    }

    public void a(String str) {
        if (!this.d) {
            this.d = true;
            String a = e.a(this.a.a().getPlatform()).a(str);
            if (a != null && a.length() > 0) {
                String[] split = a.split("&");
                Bundle bundle = new Bundle();
                for (String str2 : split) {
                    if (str2 != null) {
                        String[] split2 = str2.split("=");
                        if (split2.length >= 2) {
                            bundle.putString(split2[0], split2[1]);
                        }
                    }
                }
                if (bundle == null || bundle.size() <= 0) {
                    if (this.c != null) {
                        this.c.onError(new Throwable());
                    }
                } else if (this.c != null) {
                    this.c.onComplete(bundle);
                }
            } else if (this.c != null) {
                this.c.onError(new Throwable());
            }
        }
    }

    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        if (this.b != null && url.startsWith(this.b)) {
            view.stopLoading();
            this.a.finish();
            new d(this, String.valueOf(R.urlToBundle(url).get("oauth_verifier"))).start();
        }
        super.onPageStarted(view, url, favicon);
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (this.b == null || !url.startsWith(this.b)) {
            return super.shouldOverrideUrlLoading(view, url);
        }
        view.stopLoading();
        this.a.finish();
        new c(this, String.valueOf(R.urlToBundle(url).get("oauth_verifier"))).start();
        return true;
    }
}
