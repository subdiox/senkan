package com.kayac.lobi.sdk.service.chat;

import com.kayac.lobi.libnakamap.commet.SSLCometTask;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.utils.Log;
import com.kayac.lobi.sdk.service.chat.sdk.GroupEventPollingTaskSetting;
import junit.framework.Assert;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONObject;

public class GroupEventPollingTask implements Runnable, com.kayac.lobi.libnakamap.commet.SSLCometTask.Callback {
    private static final String TAG = GroupEventPollingTask.class.getSimpleName();
    public final String groupUid;
    private final Callback mCallback;
    private final String mStreamHost;
    private final SSLCometTask mTask;

    public interface Callback {
        void onEvent(String str, JSONObject jSONObject);

        void onTaskEnd(boolean z, boolean z2);
    }

    public GroupEventPollingTask(String streamHost, String group_uid, Callback callback) {
        this.mStreamHost = streamHost;
        Assert.assertNotNull("streaming host should not be null!", streamHost);
        Assert.assertNotNull("group uid should not be null!", group_uid);
        this.groupUid = group_uid;
        this.mCallback = callback;
        Log.d(TAG, "created task!");
        String token = AccountDatastore.getCurrentUser().getToken();
        String endpoint = String.format("https://%s%s/%s?token=%s", new Object[]{GroupEventPollingTaskSetting.getStreamHost(streamHost), GroupEventPollingTaskSetting.getApiPath(), group_uid, token});
        Log.v("libnakamp", "endpoint: " + endpoint);
        this.mTask = new SSLCometTask(new HttpGet(endpoint), this);
    }

    public void run() {
        this.mTask.run();
    }

    public void onJsonObject(JSONObject jsonObject) {
        if (!this.mTask.isCancelled()) {
            this.mCallback.onEvent(this.groupUid, jsonObject);
        }
    }

    public void onTaskEnd(boolean isCancelled, boolean error) {
        this.mCallback.onTaskEnd(isCancelled, error);
    }

    public void cancel() {
        this.mTask.cancel();
    }

    public GroupEventPollingTask copy() {
        return new GroupEventPollingTask(this.mStreamHost, this.groupUid, this.mCallback);
    }
}
