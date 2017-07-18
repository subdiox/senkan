package cn.sharesdk.twitter;

import android.os.Bundle;
import cn.sharesdk.framework.authorize.AuthorizeListener;

class a implements AuthorizeListener {
    final /* synthetic */ e a;
    final /* synthetic */ Twitter b;

    a(Twitter twitter, e eVar) {
        this.b = twitter;
        this.a = eVar;
    }

    public void onCancel() {
        if (this.b.listener != null) {
            this.b.listener.onCancel(this.b, 1);
        }
    }

    public void onComplete(Bundle values) {
        String string = values.getString("oauth_token");
        String string2 = values.getString("oauth_token_secret");
        String string3 = values.getString("user_id");
        String string4 = values.getString("screen_name");
        this.b.db.putToken(string);
        this.b.db.putTokenSecret(string2);
        this.b.db.putUserId(string3);
        this.b.db.put("nickname", string4);
        this.a.a(string, string2);
        this.b.afterRegister(1, null);
    }

    public void onError(Throwable e) {
        if (this.b.listener != null) {
            this.b.listener.onError(this.b, 1, e);
        }
    }
}
