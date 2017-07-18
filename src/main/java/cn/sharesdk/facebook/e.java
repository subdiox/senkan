package cn.sharesdk.facebook;

import android.os.Bundle;
import android.webkit.WebView;
import cn.sharesdk.framework.authorize.b;
import cn.sharesdk.framework.authorize.g;
import cn.sharesdk.framework.utils.d;
import com.mob.tools.utils.R;
import com.rekoo.libs.config.Config;
import org.apache.commons.io.IOUtils;

public class e extends b {
    public e(g gVar) {
        super(gVar);
    }

    protected void a(String str) {
        Bundle urlToBundle = R.urlToBundle(str);
        String string = urlToBundle.getString("error_message");
        if (!(string == null || this.c == null)) {
            string = "error_message ==>>" + string + IOUtils.LINE_SEPARATOR_UNIX + "error_code ==>>" + urlToBundle.getString("error_code");
            this.c.onError(new Throwable(str));
        }
        if (string == null) {
            String string2 = urlToBundle.getString("access_token");
            string = urlToBundle.containsKey("expires_in") ? urlToBundle.getString("expires_in") : Config.INIT_FAIL_NO_NETWORK;
            if (this.c != null) {
                int parseInt;
                urlToBundle = new Bundle();
                urlToBundle.putString("oauth_token", string2);
                urlToBundle.putString("oauth_token_secret", "");
                try {
                    parseInt = R.parseInt(string);
                } catch (Throwable th) {
                    d.a().d(th);
                    parseInt = -1;
                }
                urlToBundle.putInt("oauth_token_expires", parseInt);
                this.c.onComplete(urlToBundle);
            }
        }
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (!url.startsWith(this.b)) {
            return super.shouldOverrideUrlLoading(view, url);
        }
        view.stopLoading();
        view.postDelayed(new f(this), 500);
        a(url);
        return true;
    }
}
