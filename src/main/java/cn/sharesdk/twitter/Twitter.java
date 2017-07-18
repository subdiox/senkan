package cn.sharesdk.twitter;

import android.content.Context;
import android.text.TextUtils;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.b.b.f.a;
import cn.sharesdk.framework.utils.d;
import com.google.android.gcm.GCMConstants;
import com.mob.tools.utils.BitmapHelper;
import com.mob.tools.utils.Hashon;
import com.mob.tools.utils.R;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Twitter extends Platform {
    public static final String NAME = Twitter.class.getSimpleName();
    private String a;
    private String b;
    private String c;

    public static class ShareParams extends cn.sharesdk.framework.Platform.ShareParams {
    }

    public Twitter(Context context) {
        super(context);
    }

    protected boolean checkAuthorize(int action, Object extra) {
        if (isAuthValid()) {
            e a = e.a((Platform) this);
            a.a(this.a, this.b, this.c);
            String token = this.db.getToken();
            String tokenSecret = this.db.getTokenSecret();
            if (!(token == null || tokenSecret == null)) {
                a.a(token, tokenSecret);
                return true;
            }
        }
        innerAuthorize(action, extra);
        return false;
    }

    protected void doAuthorize(String[] permission) {
        e a = e.a((Platform) this);
        a.a(this.a, this.b, this.c);
        a.a(new a(this, a));
    }

    protected void doCustomerProtocol(String url, String method, int customerAction, HashMap<String, Object> values, HashMap<String, String> filePathes) {
        try {
            HashMap a = e.a((Platform) this).a(url, method, values, filePathes);
            if (a == null || a.size() <= 0) {
                if (this.listener != null) {
                    this.listener.onError(this, customerAction, new Throwable("response is null"));
                }
            } else if (a.containsKey("error_code") || a.containsKey(GCMConstants.EXTRA_ERROR)) {
                if (this.listener != null) {
                    this.listener.onError(this, customerAction, new Throwable(new Hashon().fromHashMap(a)));
                }
            } else if (this.listener != null) {
                this.listener.onComplete(this, customerAction, a);
            }
        } catch (Throwable th) {
            if (this.listener != null) {
                this.listener.onError(this, customerAction, th);
            }
        }
    }

    protected void doShare(cn.sharesdk.framework.Platform.ShareParams params) {
        e a = e.a((Platform) this);
        HashMap hashMap = null;
        String shortLintk = getShortLintk(params.getText(), false);
        try {
            String[] imageArray = params.getImageArray();
            String imagePath = params.getImagePath();
            Object imageUrl = params.getImageUrl();
            if (imageArray != null && imageArray.length > 0) {
                hashMap = a.a(shortLintk, imageArray);
            } else if (!TextUtils.isEmpty(imagePath) && new File(imagePath).exists()) {
                hashMap = a.e(shortLintk, imagePath);
            } else if (TextUtils.isEmpty(imageUrl)) {
                hashMap = a.c(shortLintk);
            } else {
                String downloadBitmap = BitmapHelper.downloadBitmap(getContext(), imageUrl);
                if (new File(downloadBitmap).exists()) {
                    hashMap = a.e(shortLintk, downloadBitmap);
                }
            }
            if (hashMap == null) {
                if (this.listener != null) {
                    this.listener.onError(this, 8, new Throwable("response is null"));
                }
            } else if (!hashMap.containsKey("error_code") && !hashMap.containsKey(GCMConstants.EXTRA_ERROR)) {
                hashMap.put("ShareParams", params);
                if (this.listener != null) {
                    this.listener.onComplete(this, 9, hashMap);
                }
            } else if (this.listener != null) {
                this.listener.onError(this, 8, new Throwable(new Hashon().fromHashMap(hashMap)));
            }
        } catch (Throwable th) {
            if (this.listener != null) {
                this.listener.onError(this, 9, th);
            }
        }
    }

    protected HashMap<String, Object> filterFriendshipInfo(int action, HashMap<String, Object> res) {
        HashMap<String, Object> hashMap = new HashMap();
        switch (action) {
            case 2:
                hashMap.put("type", "FOLLOWING");
                break;
            case 10:
                hashMap.put("type", "FRIENDS");
                break;
            case 11:
                hashMap.put("type", "FOLLOWERS");
                break;
            default:
                return null;
        }
        hashMap.put("snsplat", Integer.valueOf(getPlatformId()));
        hashMap.put("snsuid", this.db.getUserId());
        CharSequence valueOf = res.containsKey("next_cursor") ? String.valueOf(res.get("next_cursor")) : null;
        Object obj = res.get("users");
        if (obj == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = (ArrayList) obj;
        if (arrayList2.size() <= 0) {
            return null;
        }
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            HashMap hashMap2 = (HashMap) it.next();
            if (hashMap2 != null) {
                HashMap hashMap3 = new HashMap();
                hashMap3.put("snsuid", String.valueOf(hashMap2.get("id")));
                hashMap3.put("nickname", String.valueOf(hashMap2.get("screen_name")));
                hashMap3.put("icon", String.valueOf(hashMap2.get("profile_image_url")));
                hashMap3.put("gender", "2");
                hashMap3.put("resume", String.valueOf(hashMap2.get("description")));
                hashMap3.put("secretType", "true".equals(String.valueOf(hashMap2.get("verified"))) ? "1" : "0");
                hashMap3.put("followerCount", String.valueOf(hashMap2.get("followers_count")));
                hashMap3.put("favouriteCount", String.valueOf(hashMap2.get("friends_count")));
                hashMap3.put("shareCount", String.valueOf(hashMap2.get("statuses_count")));
                hashMap3.put("snsregat", String.valueOf(R.dateToLong(String.valueOf(hashMap2.get("created_at")))));
                hashMap3.put("snsUserUrl", "https://twitter.com/" + hashMap2.get("screen_name"));
                arrayList.add(hashMap3);
            }
        }
        if (arrayList == null || arrayList.size() <= 0) {
            return null;
        }
        obj = valueOf + "_false";
        if (TextUtils.isEmpty(valueOf) || "0".equals(valueOf)) {
            obj = "0_true";
        }
        hashMap.put("nextCursor", obj);
        hashMap.put("list", arrayList);
        return hashMap;
    }

    protected a filterShareContent(cn.sharesdk.framework.Platform.ShareParams params, HashMap<String, Object> res) {
        a aVar = new a();
        aVar.b = params.getText();
        if (res != null) {
            HashMap hashMap = (HashMap) res.get("entities");
            if (hashMap != null) {
                ArrayList arrayList = (ArrayList) hashMap.get("media");
                if (!(arrayList == null || arrayList.size() <= 0 || ((HashMap) arrayList.get(0)) == null)) {
                    aVar.d.add(String.valueOf(res.get("media_url")));
                }
            }
            aVar.a = String.valueOf(res.get("id"));
            aVar.g = res;
        }
        return aVar;
    }

    protected void follow(String account) {
        if (this.listener != null) {
            this.listener.onCancel(this, 6);
        }
    }

    protected HashMap<String, Object> getBilaterals(int count, int cursor, String account) {
        return null;
    }

    protected HashMap<String, Object> getFollowers(int count, int page, String nextCursor) {
        HashMap<String, Object> hashMap = null;
        Object userId = TextUtils.isEmpty(null) ? this.db.getUserId() : null;
        if (TextUtils.isEmpty(userId)) {
            userId = this.db.getUserName();
        }
        if (!TextUtils.isEmpty(userId)) {
            e a = e.a((Platform) this);
            try {
                if (TextUtils.isEmpty(nextCursor)) {
                    nextCursor = "0";
                }
                HashMap c = a.c(userId, nextCursor);
                if (!(c == null || c.size() <= 0 || c.containsKey("error_code") || c.containsKey(GCMConstants.EXTRA_ERROR))) {
                    hashMap = filterFriendshipInfo(11, c);
                }
            } catch (Throwable th) {
                d.a().d(th);
            }
        }
        return hashMap;
    }

    protected HashMap<String, Object> getFollowings(int count, int page, String nextCursor) {
        HashMap<String, Object> hashMap = null;
        Object userId = TextUtils.isEmpty(null) ? this.db.getUserId() : null;
        if (TextUtils.isEmpty(userId)) {
            userId = this.db.getUserName();
        }
        if (!TextUtils.isEmpty(userId)) {
            e a = e.a((Platform) this);
            try {
                if (TextUtils.isEmpty(nextCursor)) {
                    nextCursor = "0";
                }
                HashMap b = a.b(userId, nextCursor);
                if (!(b == null || b.size() <= 0 || b.containsKey("error_code") || b.containsKey(GCMConstants.EXTRA_ERROR))) {
                    hashMap = filterFriendshipInfo(2, b);
                }
            } catch (Throwable th) {
                d.a().d(th);
            }
        }
        return hashMap;
    }

    protected void getFriendList(int count, int page, String nextCursor) {
        Object obj = null;
        if (TextUtils.isEmpty(null)) {
            obj = this.db.getUserId();
        }
        if (TextUtils.isEmpty(obj)) {
            obj = this.db.getUserName();
        }
        if (TextUtils.isEmpty(obj) && this.listener != null) {
            this.listener.onError(this, 2, new Throwable("The account do not authorize!"));
        }
        e a = e.a((Platform) this);
        try {
            if (TextUtils.isEmpty(nextCursor)) {
                nextCursor = "0";
            }
            HashMap b = a.b(obj, nextCursor);
            if (b == null || b.size() <= 0) {
                if (this.listener != null) {
                    this.listener.onError(this, 2, new Throwable("response is null"));
                }
            } else if (b.containsKey("error_code") || b.containsKey(GCMConstants.EXTRA_ERROR)) {
                if (this.listener != null) {
                    this.listener.onError(this, 2, new Throwable(new Hashon().fromHashMap(b)));
                }
            } else if (this.listener != null) {
                this.listener.onComplete(this, 2, b);
            }
        } catch (Throwable th) {
            if (this.listener != null) {
                this.listener.onError(this, 2, th);
            }
        }
    }

    public String getName() {
        return NAME;
    }

    public int getPlatformId() {
        return 11;
    }

    public int getVersion() {
        return 2;
    }

    public boolean hasShareCallback() {
        return true;
    }

    protected void initDevInfo(String name) {
        this.a = getDevinfo("ConsumerKey");
        this.b = getDevinfo("ConsumerSecret");
        this.c = getDevinfo("CallbackUrl");
    }

    protected void setNetworkDevinfo() {
        this.a = getNetworkDevinfo("consumer_key", "ConsumerKey");
        this.b = getNetworkDevinfo("consumer_secret", "ConsumerSecret");
        this.c = getNetworkDevinfo("redirect_uri", "CallbackUrl");
    }

    protected void timeline(int count, int page, String account) {
        if (this.listener != null) {
            this.listener.onCancel(this, 7);
        }
    }

    protected void userInfor(String account) {
        try {
            HashMap b = e.a((Platform) this).b(account);
            if (b == null || b.size() <= 0) {
                if (this.listener != null) {
                    this.listener.onError(this, 8, new Throwable("response is null"));
                }
            } else if (!b.containsKey("error_code") && !b.containsKey(GCMConstants.EXTRA_ERROR)) {
                if (account == null) {
                    this.db.put("nickname", String.valueOf(b.get("screen_name")));
                    this.db.put("icon", String.valueOf(b.get("profile_image_url")));
                    this.db.put("gender", "2");
                    this.db.put("resume", String.valueOf(b.get("description")));
                    this.db.put("secretType", "true".equals(String.valueOf(b.get("verified"))) ? "1" : "0");
                    this.db.put("followerCount", String.valueOf(b.get("followers_count")));
                    this.db.put("favouriteCount", String.valueOf(b.get("friends_count")));
                    this.db.put("shareCount", String.valueOf(b.get("statuses_count")));
                    this.db.put("snsregat", String.valueOf(R.dateToLong(String.valueOf(b.get("created_at")))));
                    this.db.put("snsUserUrl", "https://twitter.com/" + b.get("screen_name"));
                }
                if (this.listener != null) {
                    this.listener.onComplete(this, 8, b);
                }
            } else if (this.listener != null) {
                this.listener.onError(this, 8, new Throwable(new Hashon().fromHashMap(b)));
            }
        } catch (Throwable th) {
            if (this.listener != null) {
                this.listener.onError(this, 8, th);
            }
        }
    }
}
