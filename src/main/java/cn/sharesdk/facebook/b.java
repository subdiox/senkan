package cn.sharesdk.facebook;

import android.os.Bundle;
import android.text.TextUtils;
import cn.sharesdk.framework.authorize.AuthorizeListener;
import cn.sharesdk.framework.utils.d;
import com.mob.tools.utils.R;

class b implements AuthorizeListener {
    final /* synthetic */ g a;
    final /* synthetic */ Facebook b;

    b(Facebook facebook, g gVar) {
        this.b = facebook;
        this.a = gVar;
    }

    public void onCancel() {
        if (this.b.listener != null) {
            this.b.listener.onCancel(this.b, 1);
        }
    }

    public void onComplete(Bundle values) {
        String string = values.getString("oauth_token");
        int i = values.getInt("oauth_token_expires");
        if (i == 0) {
            try {
                i = R.parseInt(String.valueOf(values.get("expires_in")));
            } catch (Throwable th) {
                d.a().d(th);
                i = 0;
            }
        }
        if (TextUtils.isEmpty(string)) {
            string = values.getString("access_token");
        }
        this.b.db.putToken(string);
        this.b.db.putExpiresIn((long) i);
        this.a.a(string, String.valueOf(i));
        this.b.afterRegister(1, null);
    }

    public void onError(Throwable e) {
        if (this.b.listener != null) {
            this.b.listener.onError(this.b, 1, e);
        }
    }
}
