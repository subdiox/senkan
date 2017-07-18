package cn.sharesdk.framework.authorize;

import android.content.Intent;

public class e extends a {
    protected SSOListener b;
    private f c;

    public void a(SSOListener sSOListener) {
        this.b = sSOListener;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.c.a(requestCode, resultCode, data);
    }

    public void onCreate() {
        this.c = this.a.getSSOProcessor(this);
        if (this.c == null) {
            finish();
            AuthorizeListener authorizeListener = this.a.getAuthorizeListener();
            if (authorizeListener != null) {
                authorizeListener.onError(new Throwable("Failed to start SSO for " + this.a.getPlatform().getName()));
                return;
            }
            return;
        }
        this.c.a(32973);
        this.c.a();
    }

    public void onNewIntent(Intent intent) {
        this.c.a(intent);
    }
}
