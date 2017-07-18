package cn.sharesdk.line;

import android.content.Intent;
import android.net.Uri;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.authorize.b;
import cn.sharesdk.framework.authorize.g;
import cn.sharesdk.framework.e;
import cn.sharesdk.framework.utils.d;
import com.mob.tools.utils.Data;

public class a extends e {
    private static a b;

    private a(Platform platform) {
        super(platform);
    }

    public static a a(Platform platform) {
        if (b == null) {
            b = new a(platform);
        }
        return b;
    }

    private void a(String str, String str2) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse("line://msg/" + str + "/" + str2));
        intent.addFlags(268435456);
        this.a.getContext().startActivity(intent);
    }

    public void a(String str) {
        a("text", Data.urlEncode(str, "utf-8"));
    }

    public boolean a() {
        try {
            return this.a.getContext().getPackageManager().getPackageInfo("jp.naver.line.android", 0) != null;
        } catch (Throwable th) {
            d.a().d(th);
            return false;
        }
    }

    public void b(String str) {
        a("image", str);
    }

    public String getAuthorizeUrl() {
        return null;
    }

    public b getAuthorizeWebviewClient(g webAct) {
        return null;
    }

    public String getRedirectUri() {
        return null;
    }
}
