package cn.sharesdk.framework;

import android.content.Context;
import android.graphics.Bitmap;
import cn.sharesdk.framework.b.b.f.a;
import java.util.HashMap;

public abstract class Platform {
    public static final int ACTION_AUTHORIZING = 1;
    protected static final int ACTION_CUSTOMER = 655360;
    public static final int ACTION_FOLLOWING_USER = 6;
    protected static final int ACTION_GETTING_BILATERAL_LIST = 10;
    protected static final int ACTION_GETTING_FOLLOWER_LIST = 11;
    public static final int ACTION_GETTING_FRIEND_LIST = 2;
    public static final int ACTION_SENDING_DIRECT_MESSAGE = 5;
    public static final int ACTION_SHARE = 9;
    public static final int ACTION_TIMELINE = 7;
    public static final int ACTION_USER_INFOR = 8;
    public static final int CUSTOMER_ACTION_MASK = 65535;
    public static final int SHARE_APPS = 7;
    public static final int SHARE_EMOJI = 9;
    public static final int SHARE_FILE = 8;
    public static final int SHARE_IMAGE = 2;
    public static final int SHARE_MUSIC = 5;
    public static final int SHARE_TEXT = 1;
    public static final int SHARE_VIDEO = 6;
    public static final int SHARE_WEBPAGE = 4;
    private f a;
    protected final Context context;
    protected final PlatformDb db = this.a.g();
    protected PlatformActionListener listener = this.a.i();

    public static class ShareParams extends d {
        @Deprecated
        public String imagePath;
        @Deprecated
        public String text;

        public ShareParams(String jsonParams) {
            super(jsonParams);
        }

        public ShareParams(HashMap<String, Object> params) {
            super((HashMap) params);
        }
    }

    public Platform(Context context) {
        this.context = context;
        this.a = new f(this, context);
    }

    public void SSOSetting(boolean disable) {
        this.a.a(disable);
    }

    void a() {
        this.a.a(false);
        this.a.a(getName());
    }

    protected void afterRegister(int action, Object extra) {
        this.a.b(action, extra);
    }

    public void authorize() {
        authorize(null);
    }

    public void authorize(String[] permissions) {
        this.a.a(permissions);
    }

    boolean b() {
        return this.a.f();
    }

    protected abstract boolean checkAuthorize(int i, Object obj);

    protected void copyDevinfo(String src, String dst) {
        ShareSDK.a(src, dst);
    }

    protected void copyNetworkDevinfo(int src, int dst) {
        ShareSDK.a(src, dst);
    }

    public void customerProtocol(String url, String method, short customerAction, HashMap<String, Object> values, HashMap<String, String> filePathes) {
        this.a.a(url, method, customerAction, values, filePathes);
    }

    protected abstract void doAuthorize(String[] strArr);

    protected abstract void doCustomerProtocol(String str, String str2, int i, HashMap<String, Object> hashMap, HashMap<String, String> hashMap2);

    protected abstract void doShare(ShareParams shareParams);

    protected abstract HashMap<String, Object> filterFriendshipInfo(int i, HashMap<String, Object> hashMap);

    protected abstract a filterShareContent(ShareParams shareParams, HashMap<String, Object> hashMap);

    protected abstract void follow(String str);

    public void followFriend(String account) {
        this.a.b(account);
    }

    protected abstract HashMap<String, Object> getBilaterals(int i, int i2, String str);

    public Context getContext() {
        return this.context;
    }

    public PlatformDb getDb() {
        return this.db;
    }

    public String getDevinfo(String field) {
        return getDevinfo(getName(), field);
    }

    public String getDevinfo(String name, String field) {
        return ShareSDK.b(name, field);
    }

    protected abstract HashMap<String, Object> getFollowers(int i, int i2, String str);

    protected abstract HashMap<String, Object> getFollowings(int i, int i2, String str);

    protected abstract void getFriendList(int i, int i2, String str);

    public int getId() {
        return this.a.a();
    }

    public abstract String getName();

    protected String getNetworkDevinfo(int platformId, String onlineField, String offlineField) {
        return this.a.a(platformId, onlineField, offlineField);
    }

    protected String getNetworkDevinfo(String onlineField, String offlineField) {
        return getNetworkDevinfo(getPlatformId(), onlineField, offlineField);
    }

    public PlatformActionListener getPlatformActionListener() {
        return this.a.c();
    }

    protected abstract int getPlatformId();

    public String getShortLintk(String text, boolean checkHref) {
        return this.a.a(text, checkHref);
    }

    public int getSortId() {
        return this.a.b();
    }

    public void getTimeLine(String account, int count, int page) {
        this.a.a(account, count, page);
    }

    public abstract int getVersion();

    public abstract boolean hasShareCallback();

    protected abstract void initDevInfo(String str);

    protected void innerAuthorize(int action, Object extra) {
        this.a.a(action, extra);
    }

    public boolean isAuthValid() {
        return this.a.d();
    }

    public boolean isClientValid() {
        return false;
    }

    public boolean isSSODisable() {
        return this.a.e();
    }

    @Deprecated
    public boolean isValid() {
        return this.a.d();
    }

    public void listFriend(int count, int page, String account) {
        this.a.a(count, page, account);
    }

    @Deprecated
    public void removeAccount() {
        this.a.h();
    }

    public void removeAccount(boolean removeCookie) {
        this.a.h();
        ShareSDK.removeCookieOnAuthorize(removeCookie);
    }

    protected abstract void setNetworkDevinfo();

    public void setPlatformActionListener(PlatformActionListener l) {
        this.a.a(l);
    }

    public void share(ShareParams params) {
        this.a.a(params);
    }

    public void showUser(String account) {
        this.a.c(account);
    }

    protected abstract void timeline(int i, int i2, String str);

    protected String uploadImageToFileServer(Bitmap imageData) {
        return this.a.a(imageData);
    }

    protected String uploadImageToFileServer(String imagePath) {
        return this.a.d(imagePath);
    }

    protected abstract void userInfor(String str);
}
