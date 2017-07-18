package cn.sharesdk.twitter;

import android.text.TextUtils;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.authorize.AuthorizeListener;
import cn.sharesdk.framework.authorize.b;
import cn.sharesdk.framework.authorize.g;
import cn.sharesdk.framework.utils.a;
import cn.sharesdk.framework.utils.d;
import com.kayac.lobi.libnakamap.net.APIDef.PostOauthAccessToken;
import com.kayac.lobi.libnakamap.net.APIUtil.Endpoint;
import com.mob.tools.network.KVPair;
import com.mob.tools.utils.BitmapHelper;
import com.mob.tools.utils.Hashon;
import com.mob.tools.utils.R;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import org.apache.commons.io.IOUtils;

public class e extends cn.sharesdk.framework.e {
    private static e b;
    private a c = new a();
    private cn.sharesdk.framework.a.a d = cn.sharesdk.framework.a.a.a();

    private e(Platform platform) {
        super(platform);
    }

    public static e a(Platform platform) {
        if (b == null) {
            b = new e(platform);
        }
        return b;
    }

    public String a(String str) {
        try {
            String str2 = "https://api.twitter.com/oauth/access_token";
            ArrayList arrayList = new ArrayList();
            arrayList.add(new KVPair("oauth_verifier", str));
            return this.d.a(str2, arrayList, null, this.c.a(this.c.a(str2, arrayList)), PostOauthAccessToken.PATH, c());
        } catch (Throwable th) {
            d.a().d(th);
            return null;
        }
    }

    public HashMap<String, Object> a(String str, String str2, HashMap<String, Object> hashMap, HashMap<String, String> hashMap2) {
        if (str2 == null) {
            return null;
        }
        KVPair kVPair;
        String httpGet;
        ArrayList arrayList = new ArrayList();
        if (hashMap != null && hashMap.size() > 0) {
            for (Entry entry : hashMap.entrySet()) {
                arrayList.add(new KVPair((String) entry.getKey(), String.valueOf(entry.getValue())));
            }
        }
        if (hashMap2 == null || hashMap2.size() <= 0) {
            kVPair = null;
        } else {
            HashMap<String, Object> hashMap3 = null;
            for (Entry entry2 : hashMap2.entrySet()) {
                Object kVPair2 = new KVPair((String) entry2.getKey(), entry2.getValue());
            }
            kVPair = hashMap3;
        }
        if ("GET".equals(str2.toUpperCase())) {
            httpGet = this.d.httpGet(str, arrayList, this.c.a(this.c.b(str, arrayList)), null);
        } else if ("POST".equals(str2.toUpperCase())) {
            ArrayList a;
            if (hashMap2 == null || hashMap2.size() <= 0) {
                a = this.c.a(this.c.a(str, arrayList));
            } else {
                a = this.c.a(this.c.a(str, new ArrayList()));
                a.remove(1);
            }
            httpGet = this.d.httpPost(str, arrayList, kVPair, a, null);
        } else {
            httpGet = null;
        }
        return (httpGet == null || httpGet.length() <= 0) ? null : new Hashon().fromJson(httpGet);
    }

    public HashMap<String, Object> a(String str, String[] strArr) {
        int i = 0;
        String str2 = "https://upload.twitter.com/1.1/media/upload.json";
        ArrayList arrayList = new ArrayList();
        ArrayList a = this.c.a(this.c.a(str2, arrayList));
        a.remove(1);
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList arrayList2 = new ArrayList();
        for (int i2 = 0; i2 < strArr.length && (arrayList2 == null || arrayList2.size() <= 3); i2++) {
            try {
                Object obj = strArr[i2];
                if (obj.startsWith(Endpoint.SCHEME_HTTP)) {
                    obj = BitmapHelper.downloadBitmap(this.a.getContext(), obj);
                } else {
                    if (!TextUtils.isEmpty(obj)) {
                        if (!new File(obj).exists()) {
                        }
                    }
                }
                String a2 = this.d.a(str2, arrayList, new KVPair("media", obj), a, "/1.1/media/upload.json", c());
                stringBuilder.append(strArr[i2]).append(": ").append(a2).append(IOUtils.LINE_SEPARATOR_UNIX);
                if (a2 != null && a2.length() > 0) {
                    arrayList2.add(new Hashon().fromJson(a2));
                }
            } catch (Exception e) {
                d.a().d(stringBuilder.toString(), new Object[0]);
                e.printStackTrace();
            }
        }
        stringBuilder.setLength(0);
        while (i < arrayList2.size()) {
            if (((HashMap) arrayList2.get(i)).containsKey("image")) {
                if (stringBuilder.length() > 0) {
                    stringBuilder.append(',');
                }
                stringBuilder.append(String.valueOf(((HashMap) arrayList2.get(i)).get("media_id")));
            }
            i++;
        }
        return d(str, stringBuilder.toString());
    }

    public void a(AuthorizeListener authorizeListener) {
        b(authorizeListener);
    }

    public void a(String str, String str2) {
        this.c.a(str, str2);
    }

    public void a(String str, String str2, String str3) {
        this.c.a(str, str2, str3);
    }

    public HashMap<String, Object> b(String str) {
        String str2 = "https://api.twitter.com/1.1/users/show.json";
        ArrayList arrayList = new ArrayList();
        long j = 0;
        try {
            j = R.parseLong(str);
        } catch (Throwable th) {
            str = null;
        }
        arrayList.add(new KVPair("user_id", str == null ? this.a.getDb().getUserId() : String.valueOf(j)));
        String a = this.d.a(str2, arrayList, this.c.a(this.c.b(str2, arrayList)), null, "/1.1/users/show.json", c());
        return (a == null || a.length() <= 0) ? null : new Hashon().fromJson(a);
    }

    public HashMap<String, Object> b(String str, String str2) {
        String str3 = "https://api.twitter.com/1.1/friends/list.json";
        ArrayList arrayList = new ArrayList();
        arrayList.add(new KVPair("nextCursor", str2));
        Object obj = 1;
        try {
            R.parseLong(str);
        } catch (Throwable th) {
            obj = null;
        }
        if (obj != null) {
            arrayList.add(new KVPair("user_id", str));
        } else {
            arrayList.add(new KVPair("screen_name", str));
        }
        String a = this.d.a(str3, arrayList, this.c.a(this.c.b(str3, arrayList)), null, "/1.1/friends/list.json", c());
        return (a == null || a.length() <= 0) ? null : new Hashon().fromJson(a);
    }

    public HashMap<String, Object> c(String str) {
        return d(str, null);
    }

    public HashMap<String, Object> c(String str, String str2) {
        String str3 = "https://api.twitter.com/1.1/followers/list.json";
        ArrayList arrayList = new ArrayList();
        arrayList.add(new KVPair("nextCursor", str2));
        Object obj = 1;
        try {
            R.parseLong(str);
        } catch (Throwable th) {
            obj = null;
        }
        if (obj != null) {
            arrayList.add(new KVPair("user_id", str));
        } else {
            arrayList.add(new KVPair("screen_name", str));
        }
        String a = this.d.a(str3, arrayList, this.c.a(this.c.b(str3, arrayList)), null, "/1.1/followers/list.json", c());
        return (a == null || a.length() <= 0) ? null : new Hashon().fromJson(a);
    }

    public HashMap<String, Object> d(String str, String str2) {
        String str3 = "https://api.twitter.com/1.1/statuses/update.json";
        ArrayList arrayList = new ArrayList();
        arrayList.add(new KVPair("status", str));
        if (!TextUtils.isEmpty(str2)) {
            arrayList.add(new KVPair("media_ids", str2));
        }
        String a = this.d.a(str3, arrayList, null, this.c.a(this.c.a(str3, arrayList)), "/1.1/statuses/update.json", c());
        return (a == null || a.length() <= 0) ? null : new Hashon().fromJson(a);
    }

    public HashMap<String, Object> e(String str, String str2) {
        String str3 = "https://api.twitter.com/1.1/statuses/update_with_media.json";
        ArrayList arrayList = new ArrayList();
        ArrayList a = this.c.a(this.c.a(str3, arrayList));
        a.remove(1);
        arrayList.add(new KVPair("status", str));
        String a2 = this.d.a(str3, arrayList, new KVPair("media[]", str2), a, "/1.1/statuses/update_with_media.json", c());
        return (a2 == null || a2.length() <= 0) ? null : new Hashon().fromJson(a2);
    }

    public String getAuthorizeUrl() {
        try {
            String str = "https://api.twitter.com/oauth/request_token";
            ArrayList arrayList = new ArrayList();
            arrayList.add(new KVPair("oauth_callback", getRedirectUri()));
            a(null, null);
            String a = this.d.a(str, arrayList, null, this.c.a(this.c.a(str, arrayList)), "/oauth/request_token", c());
            if (a == null) {
                return null;
            }
            String[] split = a.split("&");
            HashMap hashMap = new HashMap();
            for (String str2 : split) {
                if (str2 != null) {
                    String[] split2 = str2.split("=");
                    if (split2.length >= 2) {
                        hashMap.put(split2[0], split2[1]);
                    }
                }
            }
            if (hashMap.containsKey("oauth_token")) {
                a = (String) hashMap.get("oauth_token");
                a(a, (String) hashMap.get("oauth_token_secret"));
                ShareSDK.logApiEvent("/oauth/authorize", c());
                return "https://api.twitter.com/oauth/authorize?oauth_token=" + a;
            }
            return null;
        } catch (Throwable th) {
            d.a().d(th);
        }
    }

    public b getAuthorizeWebviewClient(g webAct) {
        return new b(webAct);
    }

    public String getRedirectUri() {
        return this.c.a().e;
    }
}
