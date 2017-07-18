package cn.sharesdk.framework.authorize;

import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.LinearLayout.LayoutParams;

class d extends WebChromeClient {
    final /* synthetic */ int a;
    final /* synthetic */ RegisterView b;

    d(RegisterView registerView, int i) {
        this.b = registerView;
        this.a = i;
    }

    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        LayoutParams layoutParams = (LayoutParams) this.b.d.getLayoutParams();
        layoutParams.width = (this.a * newProgress) / 100;
        this.b.d.setLayoutParams(layoutParams);
        if (newProgress <= 0 || newProgress >= 100) {
            this.b.d.setVisibility(8);
        } else {
            this.b.d.setVisibility(0);
        }
    }
}
