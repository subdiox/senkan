package cn.sharesdk.framework;

import android.content.Context;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.b.b.f.a;
import java.util.HashMap;

public abstract class CustomPlatform extends Platform {
    public CustomPlatform(Context context) {
        super(context);
    }

    protected abstract boolean checkAuthorize(int i, Object obj);

    protected void doAuthorize(String[] permissions) {
    }

    protected void doCustomerProtocol(String url, String method, int customerAction, HashMap<String, Object> hashMap, HashMap<String, String> hashMap2) {
    }

    protected void doShare(ShareParams params) {
    }

    protected HashMap<String, Object> filterFriendshipInfo(int action, HashMap<String, Object> hashMap) {
        return null;
    }

    protected final a filterShareContent(ShareParams params, HashMap<String, Object> hashMap) {
        return null;
    }

    protected void follow(String account) {
    }

    protected HashMap<String, Object> getBilaterals(int count, int cursor, String account) {
        return null;
    }

    protected int getCustomPlatformId() {
        return 1;
    }

    protected HashMap<String, Object> getFollowers(int count, int cursor, String account) {
        return null;
    }

    protected HashMap<String, Object> getFollowings(int count, int page, String account) {
        return null;
    }

    protected void getFriendList(int count, int cursor, String account) {
    }

    public abstract String getName();

    protected final int getPlatformId() {
        return -Math.abs(getCustomPlatformId());
    }

    public int getVersion() {
        return 0;
    }

    public boolean hasShareCallback() {
        return false;
    }

    protected final void initDevInfo(String name) {
    }

    protected final void setNetworkDevinfo() {
    }

    protected void timeline(int count, int page, String account) {
    }

    protected void userInfor(String account) {
    }
}
