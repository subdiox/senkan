package com.kayac.lobi.libnakamap.value;

import com.kayac.lobi.libnakamap.net.APIDef.PostAppData.RequestKey;
import com.kayac.lobi.libnakamap.utils.JSONUtil;
import com.kayac.lobi.libnakamap.value.GroupInterface.GroupType;
import org.json.JSONObject;

@Deprecated
public class PublicGroupValue implements GroupInterface {
    private final long mCreatedDate;
    private final String mDescription;
    private final String mIcon;
    private final String mStreamHost;
    private final String mTitle;
    private final String mUid;

    public PublicGroupValue(String uid, String title, String description, long createdDate, String icon, String streamHost) {
        this.mUid = uid;
        this.mTitle = title;
        this.mDescription = description;
        this.mCreatedDate = createdDate;
        this.mIcon = icon;
        this.mStreamHost = streamHost;
    }

    public PublicGroupValue(JSONObject json) {
        JSONObject obj = json.optJSONObject(RequestKey.DATA);
        if (obj != null) {
            this.mUid = JSONUtil.getString(obj, "uid", "");
            this.mTitle = JSONUtil.getString(obj, "name", "");
            this.mDescription = JSONUtil.getString(obj, "description", "");
            this.mCreatedDate = Long.parseLong(JSONUtil.getString(obj, "created_date", "0"));
            this.mIcon = JSONUtil.getString(obj, "icon", "");
            this.mStreamHost = JSONUtil.getString(obj, "stream_host", "");
            return;
        }
        this.mUid = null;
        this.mTitle = null;
        this.mDescription = null;
        this.mCreatedDate = 0;
        this.mIcon = null;
        this.mStreamHost = null;
    }

    public String getUid() {
        return this.mUid;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public long getCreatedDate() {
        return this.mCreatedDate;
    }

    public String getIcon() {
        return this.mIcon;
    }

    public String getStreamHost() {
        return this.mStreamHost;
    }

    public String getName() {
        return this.mTitle;
    }

    public GroupType getGroupType() {
        return GroupType.GROUP;
    }
}
