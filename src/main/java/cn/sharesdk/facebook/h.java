package cn.sharesdk.facebook;

import android.os.Bundle;
import cn.sharesdk.framework.authorize.AuthorizeListener;
import cn.sharesdk.framework.authorize.SSOListener;
import cn.sharesdk.framework.utils.d;

class h implements SSOListener {
    final /* synthetic */ AuthorizeListener a;
    final /* synthetic */ g b;

    h(g gVar, AuthorizeListener authorizeListener) {
        this.b = gVar;
        this.a = authorizeListener;
    }

    public void onCancel() {
        this.a.onCancel();
    }

    public void onComplete(Bundle values) {
        this.a.onComplete(values);
    }

    public void onFailed(Throwable t) {
        d.a().d(t);
        this.b.b(this.a);
    }
}
