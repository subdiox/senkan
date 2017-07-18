package cn.sharesdk.facebook;

import android.content.Context;
import android.text.TextUtils;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.b.b.f.a;
import cn.sharesdk.framework.utils.d;
import com.google.android.gcm.GCMConstants;
import com.kayac.lobi.libnakamap.net.APIDef.PostAppData.RequestKey;
import com.kayac.lobi.libnakamap.value.AuthorizedAppValue;
import com.mob.tools.utils.BitmapHelper;
import com.mob.tools.utils.Hashon;
import com.mob.tools.utils.R;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

public class Facebook extends Platform {
    public static final String NAME = Facebook.class.getSimpleName();
    private String a;
    private String b;

    public static class ShareParams extends cn.sharesdk.framework.Platform.ShareParams {
    }

    public Facebook(Context context) {
        super(context);
    }

    protected boolean checkAuthorize(int action, Object extra) {
        if (isAuthValid()) {
            g a = g.a((Platform) this);
            a.a(this.a);
            String token = this.db.getToken();
            String valueOf = String.valueOf(this.db.getExpiresIn());
            if (!(token == null || valueOf == null)) {
                a.a(token, valueOf);
                if (a.a()) {
                    return true;
                }
            }
        } else if ((extra instanceof cn.sharesdk.framework.Platform.ShareParams) && ((cn.sharesdk.framework.Platform.ShareParams) extra).getShareType() == 4) {
            return true;
        }
        innerAuthorize(action, extra);
        return false;
    }

    protected void doAuthorize(String[] permission) {
        g a = g.a((Platform) this);
        a.a(this.a);
        a.d(this.b);
        a.a(permission);
        a.a(new b(this, a), isSSODisable());
    }

    protected void doCustomerProtocol(String url, String method, int customerAction, HashMap<String, Object> values, HashMap<String, String> filePathes) {
        try {
            HashMap a = g.a((Platform) this).a(url, method, values, filePathes);
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
        g a = g.a((Platform) this);
        a.a(this.a);
        try {
            String shortLintk = getShortLintk(params.getText(), false);
            String imagePath = params.getImagePath();
            Object imageUrl = params.getImageUrl();
            CharSequence titleUrl = params.getTitleUrl();
            CharSequence url = params.getUrl();
            if (params.getShareType() != 4 || (TextUtils.isEmpty(titleUrl) && TextUtils.isEmpty(url))) {
                HashMap b;
                if (!TextUtils.isEmpty(imagePath) && new File(imagePath).exists()) {
                    b = a.b(shortLintk, imagePath);
                } else if (TextUtils.isEmpty(imageUrl)) {
                    b = a.b(shortLintk);
                } else {
                    File file = new File(BitmapHelper.downloadBitmap(this.context, imageUrl));
                    b = file.exists() ? a.b(shortLintk, file.getAbsolutePath()) : a.b(shortLintk);
                }
                if (b == null || b.size() <= 0) {
                    if (this.listener != null) {
                        this.listener.onError(this, 9, new Throwable("response is null"));
                        return;
                    }
                    return;
                } else if (b.containsKey("error_code") || b.containsKey(GCMConstants.EXTRA_ERROR)) {
                    if (this.listener != null) {
                        this.listener.onError(this, 9, new Throwable(new Hashon().fromHashMap(b)));
                        return;
                    }
                    return;
                } else if (this.listener != null) {
                    b.put("ShareParams", params);
                    this.listener.onComplete(this, 9, b);
                    return;
                } else {
                    return;
                }
            }
            if (TextUtils.isEmpty(imageUrl) && !TextUtils.isEmpty(imagePath) && new File(imagePath).exists()) {
                params.setImageUrl(uploadImageToFileServer(imagePath));
            }
            a.a(params, new c(this, params));
        } catch (Throwable th) {
            if (this.listener != null) {
                this.listener.onError(this, 9, th);
            }
        }
    }

    protected HashMap<String, Object> filterFriendshipInfo(int action, HashMap<String, Object> res) {
        Object obj = res.get(RequestKey.DATA);
        if (obj == null) {
            return null;
        }
        HashMap<String, Object> hashMap = new HashMap();
        hashMap.put("type", "FOLLOWING");
        hashMap.put("snsplat", Integer.valueOf(getPlatformId()));
        hashMap.put("snsuid", this.db.getUserId());
        int intValue = ((Integer) res.get("current_cursor")).intValue();
        int intValue2 = ((Integer) res.get("current_limit")).intValue();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = (ArrayList) obj;
        if (arrayList2.size() <= 0) {
            return null;
        }
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            HashMap hashMap2 = (HashMap) it.next();
            if (hashMap2 != null) {
                String[] split;
                HashMap hashMap3 = new HashMap();
                hashMap3.put("snsuid", String.valueOf(hashMap2.get("id")));
                hashMap3.put("nickname", String.valueOf(hashMap2.get("name")));
                hashMap3.put("gender", "male".equals(String.valueOf(hashMap2.get("gender"))) ? "0" : "1");
                hashMap3.put("secretType", "true".equals(String.valueOf(hashMap2.get("verified"))) ? "1" : "0");
                hashMap3.put("snsUserUrl", String.valueOf(hashMap2.get("link")));
                hashMap3.put("resume", String.valueOf(hashMap2.get("link")));
                HashMap hashMap4 = hashMap2.containsKey("picture") ? (HashMap) hashMap2.get("picture") : null;
                if (hashMap4 != null) {
                    hashMap4 = hashMap4.containsKey(RequestKey.DATA) ? (HashMap) hashMap4.get(RequestKey.DATA) : null;
                    if (hashMap4 != null) {
                        hashMap3.put("icon", String.valueOf(hashMap4.get("url")));
                    }
                }
                try {
                    if (hashMap2.containsKey("birthday")) {
                        split = String.valueOf(hashMap2.get("birthday")).split("/");
                        Calendar instance = Calendar.getInstance();
                        instance.set(1, R.parseInt(split[2]));
                        instance.set(2, R.parseInt(split[0]) - 1);
                        instance.set(5, R.parseInt(split[1]));
                        hashMap3.put("birthday", String.valueOf(instance.getTimeInMillis()));
                    }
                } catch (Throwable th) {
                    d.a().d(th);
                }
                ArrayList arrayList3 = hashMap2.containsKey("education") ? (ArrayList) hashMap2.get("education") : null;
                if (arrayList3 != null) {
                    ArrayList arrayList4 = new ArrayList();
                    Iterator it2 = arrayList3.iterator();
                    while (it2.hasNext()) {
                        hashMap4 = (HashMap) it2.next();
                        HashMap hashMap5 = new HashMap();
                        hashMap5.put("school_type", Integer.valueOf(0));
                        HashMap hashMap6 = (HashMap) hashMap4.get("school");
                        if (hashMap6 != null) {
                            hashMap5.put("school", String.valueOf(hashMap6.get("name")));
                        }
                        try {
                            hashMap5.put("year", Integer.valueOf(R.parseInt(String.valueOf(((HashMap) hashMap4.get("year")).get("name")))));
                        } catch (Throwable th2) {
                            d.a().d(th2);
                        }
                        hashMap5.put("background", Integer.valueOf(0));
                        arrayList4.add(hashMap5);
                    }
                    hashMap4 = new HashMap();
                    hashMap4.put("list", arrayList4);
                    String fromHashMap = new Hashon().fromHashMap(hashMap4);
                    hashMap3.put("educationJSONArrayStr", fromHashMap.substring(8, fromHashMap.length() - 1));
                }
                arrayList2 = hashMap2.containsKey("work") ? (ArrayList) hashMap2.get("work") : null;
                if (arrayList2 != null) {
                    ArrayList arrayList5 = new ArrayList();
                    Iterator it3 = arrayList2.iterator();
                    while (it3.hasNext()) {
                        hashMap2 = (HashMap) it3.next();
                        HashMap hashMap7 = new HashMap();
                        hashMap4 = (HashMap) hashMap2.get("employer");
                        if (hashMap4 != null) {
                            hashMap7.put(AuthorizedAppValue.JSON_KEY_COMPANY, String.valueOf(hashMap4.get("name")));
                        }
                        hashMap4 = (HashMap) hashMap2.get("position");
                        if (hashMap4 != null) {
                            hashMap7.put("position", String.valueOf(hashMap4.get("name")));
                        }
                        try {
                            split = String.valueOf(hashMap2.get("start_date")).split("-");
                            hashMap7.put("start_date", Integer.valueOf(R.parseInt(split[1]) + (R.parseInt(split[0]) * 100)));
                        } catch (Throwable th22) {
                            d.a().d(th22);
                        }
                        try {
                            String[] split2 = String.valueOf(hashMap2.get("end_date")).split("-");
                            hashMap7.put("end_date", Integer.valueOf(R.parseInt(split2[1]) + (R.parseInt(split2[0]) * 100)));
                        } catch (Throwable th3) {
                            d.a().d(th3);
                            hashMap7.put("end_date", Integer.valueOf(0));
                        }
                        arrayList5.add(hashMap7);
                    }
                    hashMap2 = new HashMap();
                    hashMap2.put("list", arrayList5);
                    String fromHashMap2 = new Hashon().fromHashMap(hashMap2);
                    hashMap3.put("workJSONArrayStr", fromHashMap2.substring(8, fromHashMap2.length() - 1));
                }
                arrayList.add(hashMap3);
            }
        }
        if (arrayList == null || arrayList.size() <= 0) {
            return null;
        }
        fromHashMap2 = "_false";
        if (intValue2 >= arrayList.size()) {
            fromHashMap2 = "_true";
        }
        hashMap.put("nextCursor", (arrayList.size() + intValue) + fromHashMap2);
        hashMap.put("list", arrayList);
        return hashMap;
    }

    protected a filterShareContent(cn.sharesdk.framework.Platform.ShareParams params, HashMap<String, Object> res) {
        a aVar = new a();
        aVar.b = params.getText();
        if (res != null) {
            Object titleUrl;
            if (res != null && res.containsKey("source")) {
                aVar.d.add(String.valueOf(res.get("source")));
            } else if (4 == params.getShareType()) {
                aVar.d.add(params.getImageUrl());
                titleUrl = params.getTitleUrl();
                if (TextUtils.isEmpty(titleUrl)) {
                    titleUrl = params.getUrl();
                }
                aVar.c.add(titleUrl);
            }
            titleUrl = res.get("post_id");
            aVar.a = titleUrl == null ? null : String.valueOf(titleUrl);
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

    protected HashMap<String, Object> getFollowers(int count, int cursor, String account) {
        return null;
    }

    protected HashMap<String, Object> getFollowings(int limit, int offset, String account) {
        HashMap<String, Object> hashMap = null;
        try {
            HashMap a = g.a((Platform) this).a(limit, offset, account);
            if (!(a == null || a.size() <= 0 || a.containsKey("error_code") || a.containsKey(GCMConstants.EXTRA_ERROR))) {
                a.put("current_limit", Integer.valueOf(limit));
                a.put("current_cursor", Integer.valueOf(offset));
                hashMap = filterFriendshipInfo(2, a);
            }
        } catch (Throwable th) {
            d.a().d(th);
        }
        return hashMap;
    }

    protected void getFriendList(int count, int page, String account) {
        try {
            HashMap a = g.a((Platform) this).a(count, page * count, account);
            if (a == null || a.size() <= 0) {
                if (this.listener != null) {
                    this.listener.onError(this, 2, new Throwable("response is null"));
                }
            } else if (a.containsKey("error_code") || a.containsKey(GCMConstants.EXTRA_ERROR)) {
                if (this.listener != null) {
                    this.listener.onError(this, 2, new Throwable(new Hashon().fromHashMap(a)));
                }
            } else if (this.listener != null) {
                this.listener.onComplete(this, 2, a);
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
        return 10;
    }

    public int getVersion() {
        return 2;
    }

    public boolean hasShareCallback() {
        return true;
    }

    protected void initDevInfo(String name) {
        this.a = getDevinfo("ConsumerKey");
        this.b = getDevinfo("RedirectUrl");
    }

    public boolean isClientValid() {
        g a = g.a((Platform) this);
        a.a(this.a);
        return a.b();
    }

    protected void setNetworkDevinfo() {
        this.a = getNetworkDevinfo("api_key", "ConsumerKey");
        this.b = getNetworkDevinfo("redirect_uri", "RedirectUrl");
    }

    protected void timeline(int count, int page, String account) {
        if (this.listener != null) {
            this.listener.onCancel(this, 7);
        }
    }

    protected void userInfor(String account) {
        HashMap c = g.a((Platform) this).c(account);
        if (c == null || c.size() <= 0) {
            if (this.listener != null) {
                this.listener.onError(this, 8, new Throwable("response is null"));
            }
        } else if (!c.containsKey("error_code") && !c.containsKey(GCMConstants.EXTRA_ERROR)) {
            if (account == null) {
                String[] split;
                HashMap hashMap;
                String fromHashMap;
                this.db.putUserId(String.valueOf(c.get("id")));
                this.db.put("nickname", String.valueOf(c.get("name")));
                this.db.put("gender", "male".equals(String.valueOf(c.get("gender"))) ? "0" : "1");
                this.db.put("token_for_business", (String) c.get("token_for_business"));
                HashMap hashMap2 = c.containsKey("picture") ? (HashMap) c.get("picture") : null;
                if (hashMap2 != null) {
                    hashMap2 = (HashMap) hashMap2.get(RequestKey.DATA);
                    if (hashMap2 != null) {
                        this.db.put("icon", String.valueOf(hashMap2.get("url")));
                    }
                }
                try {
                    if (c.containsKey("birthday")) {
                        split = String.valueOf(c.get("birthday")).split("/");
                        Calendar instance = Calendar.getInstance();
                        instance.set(1, R.parseInt(split[2]));
                        instance.set(2, R.parseInt(split[0]) - 1);
                        instance.set(5, R.parseInt(split[1]));
                        this.db.put("birthday", String.valueOf(instance.getTimeInMillis()));
                    }
                } catch (Throwable th) {
                    if (this.listener != null) {
                        this.listener.onError(this, 8, th);
                        return;
                    }
                    return;
                }
                this.db.put("secretType", "true".equals(String.valueOf(c.get("verified"))) ? "1" : "0");
                this.db.put("snsUserUrl", String.valueOf(c.get("link")));
                this.db.put("resume", String.valueOf(c.get("link")));
                ArrayList arrayList = c.containsKey("education") ? (ArrayList) c.get("education") : null;
                if (arrayList != null) {
                    ArrayList arrayList2 = new ArrayList();
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        hashMap2 = (HashMap) it.next();
                        HashMap hashMap3 = new HashMap();
                        hashMap3.put("school_type", Integer.valueOf(0));
                        hashMap = hashMap2.containsKey("school") ? (HashMap) hashMap2.get("school") : null;
                        if (hashMap != null) {
                            hashMap3.put("school", String.valueOf(hashMap.get("name")));
                        }
                        try {
                            hashMap3.put("year", Integer.valueOf(R.parseInt(String.valueOf((hashMap2.containsKey("year") ? (HashMap) hashMap2.get("year") : null).get("name")))));
                        } catch (Throwable th2) {
                            d.a().d(th2);
                        }
                        hashMap3.put("background", Integer.valueOf(0));
                        arrayList2.add(hashMap3);
                    }
                    hashMap2 = new HashMap();
                    hashMap2.put("list", arrayList2);
                    fromHashMap = new Hashon().fromHashMap(hashMap2);
                    this.db.put("educationJSONArrayStr", fromHashMap.substring(8, fromHashMap.length() - 1));
                }
                arrayList = c.containsKey("work") ? (ArrayList) c.get("work") : null;
                if (arrayList != null) {
                    ArrayList arrayList3 = new ArrayList();
                    Iterator it2 = arrayList.iterator();
                    while (it2.hasNext()) {
                        hashMap2 = (HashMap) it2.next();
                        HashMap hashMap4 = new HashMap();
                        hashMap = (HashMap) hashMap2.get("employer");
                        if (hashMap != null) {
                            hashMap4.put(AuthorizedAppValue.JSON_KEY_COMPANY, String.valueOf(hashMap.get("name")));
                        }
                        hashMap = (HashMap) hashMap2.get("position");
                        if (hashMap != null) {
                            hashMap4.put("position", String.valueOf(hashMap.get("name")));
                        }
                        try {
                            String[] split2 = String.valueOf(hashMap2.get("start_date")).split("-");
                            hashMap4.put("start_date", Integer.valueOf(R.parseInt(split2[1]) + (R.parseInt(split2[0]) * 100)));
                        } catch (Throwable th3) {
                            d.a().d(th3);
                        }
                        try {
                            split = String.valueOf(hashMap2.get("end_date")).split("-");
                            hashMap4.put("end_date", Integer.valueOf(R.parseInt(split[1]) + (R.parseInt(split[0]) * 100)));
                        } catch (Throwable th22) {
                            d.a().d(th22);
                            hashMap4.put("end_date", Integer.valueOf(0));
                        }
                        arrayList3.add(hashMap4);
                    }
                    hashMap2 = new HashMap();
                    hashMap2.put("list", arrayList3);
                    fromHashMap = new Hashon().fromHashMap(hashMap2);
                    this.db.put("workJSONArrayStr", fromHashMap.substring(8, fromHashMap.length() - 1));
                }
            }
            if (this.listener != null) {
                this.listener.onComplete(this, 8, c);
            }
        } else if (this.listener != null) {
            this.listener.onError(this, 8, new Throwable(new Hashon().fromHashMap(c)));
        }
    }
}
