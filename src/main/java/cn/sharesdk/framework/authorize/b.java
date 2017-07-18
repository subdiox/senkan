package cn.sharesdk.framework.authorize;

import android.webkit.WebView;
import cn.sharesdk.framework.i;

public abstract class b extends i {
    protected g a;
    protected String b;
    protected AuthorizeListener c;

    public b(g gVar) {
        this.a = gVar;
        AuthorizeHelper a = gVar.a();
        this.b = a.getRedirectUri();
        this.c = a.getAuthorizeListener();
    }

    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        view.stopLoading();
        AuthorizeListener authorizeListener = this.a.a().getAuthorizeListener();
        this.a.finish();
        if (authorizeListener != null) {
            authorizeListener.onError(new Throwable(description + " (" + errorCode + "): " + failingUrl));
        }
    }
}
