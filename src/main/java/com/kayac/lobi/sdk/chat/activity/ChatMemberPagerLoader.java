package com.kayac.lobi.sdk.chat.activity;

import android.text.TextUtils;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.net.APIRes.GetGroup;
import com.kayac.lobi.libnakamap.net.CoreAPI;
import com.kayac.lobi.libnakamap.net.CoreAPI.DefaultAPICallback;
import com.kayac.lobi.libnakamap.net.PagerLoader;
import java.util.HashMap;
import java.util.Map;

class ChatMemberPagerLoader extends PagerLoader<GetGroup> {
    private String mGid = null;

    public ChatMemberPagerLoader(DefaultAPICallback<GetGroup> callback) {
        super(callback);
    }

    void setGid(String gid) {
        synchronized (this.mLock) {
            if (!TextUtils.equals(this.mGid, gid)) {
                this.mGid = gid;
                this.mNextCursor = null;
                this.mLoadingCursor = "!";
                this.mIsLoading = false;
            }
        }
    }

    protected String getNextCursor(GetGroup t) {
        return t.group.getMembersNextCursor();
    }

    protected boolean shouldLoadNext() {
        boolean z;
        synchronized (this.mLock) {
            z = !TextUtils.equals(this.mNextCursor, "0");
        }
        return z;
    }

    protected void load() {
        synchronized (this.mLock) {
            Map<String, String> params = new HashMap();
            params.put("token", AccountDatastore.getCurrentUser().getToken());
            params.put("uid", this.mGid);
            params.put("count", "1");
            params.put("members_count", "100");
            if (this.mNextCursor != null) {
                params.put("members_cursor", this.mNextCursor);
            }
            CoreAPI.getGroup(params, getApiCallback());
        }
    }
}
