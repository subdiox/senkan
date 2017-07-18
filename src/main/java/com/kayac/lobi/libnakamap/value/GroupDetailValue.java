package com.kayac.lobi.libnakamap.value;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.kayac.lobi.libnakamap.utils.JSONUtil;
import com.kayac.lobi.libnakamap.value.GroupInterface.GroupType;
import org.json.JSONObject;

public class GroupDetailValue implements GroupInterface, Parcelable {
    public static final Creator<GroupDetailValue> CREATOR = new Creator<GroupDetailValue>() {
        public GroupDetailValue createFromParcel(Parcel source) {
            return new GroupDetailValue(source);
        }

        public GroupDetailValue[] newArray(int size) {
            return new GroupDetailValue[size];
        }
    };
    private final long mCreatedDate;
    private final String mDescription;
    private final String mExId;
    private final String mIcon;
    private final boolean mIsAuthorized;
    private final boolean mIsNotice;
    private final boolean mIsOfficial;
    private final boolean mIsOnline;
    private final boolean mIsPublic;
    private final long mLastChatAt;
    private final String mName;
    private final int mOnlineUsers;
    private final GroupPermissionValue mPermission;
    private final boolean mPushEnabled;
    private final String mStreamHost;
    private final int mTotalUsers;
    private final String mType;
    private final String mUid;

    public static final class Builder {
        private long mCreatedDate;
        private String mDescription;
        private String mExId;
        private String mIcon;
        private boolean mIsAuthorized;
        private boolean mIsNotice;
        private boolean mIsOfficial;
        private boolean mIsOnline;
        private boolean mIsPublic;
        private long mLastChatAt;
        private String mName;
        private int mOnlineUsers;
        private GroupPermissionValue mPermission;
        private boolean mPushEnabled;
        private String mStreamHost;
        private int mTotalUsers;
        private String mType;
        private String mUid;

        public Builder(GroupDetailValue src) {
            this.mUid = src.getUid();
            this.mName = src.getName();
            this.mDescription = src.getDescription();
            this.mCreatedDate = src.getCreatedDate();
            this.mIcon = src.getIcon();
            this.mStreamHost = src.getStreamHost();
            this.mTotalUsers = src.getTotalUsers();
            this.mOnlineUsers = src.getOnlineUsers();
            this.mIsOnline = src.isOnline();
            this.mIsPublic = src.isPublic();
            this.mIsOfficial = src.isOfficial();
            this.mIsAuthorized = src.isAuthorized();
            this.mType = src.getType();
            this.mLastChatAt = src.getLastChatAt();
            this.mPushEnabled = src.isPushEnabled();
            this.mPermission = src.getPermission();
            this.mIsNotice = src.isNotice();
            this.mExId = src.getExId();
        }

        public void setUid(String uid) {
            this.mUid = uid;
        }

        public void setName(String name) {
            this.mName = name;
        }

        public void setDescription(String description) {
            this.mDescription = description;
        }

        public void setCreatedDate(long createdDate) {
            this.mCreatedDate = createdDate;
        }

        public void setIcon(String icon) {
            this.mIcon = icon;
        }

        public void setStreamHost(String streamHost) {
            this.mStreamHost = streamHost;
        }

        public void setTotalUsers(int totalUsers) {
            this.mTotalUsers = totalUsers;
        }

        public void setOnlineUsers(int onlineUsers) {
            this.mOnlineUsers = onlineUsers;
        }

        public void setIsOnline(boolean isOnline) {
            this.mIsOnline = isOnline;
        }

        public void setIsPublic(boolean isPublic) {
            this.mIsPublic = isPublic;
        }

        public void setIsOfficial(boolean isOfficial) {
            this.mIsOfficial = isOfficial;
        }

        public void setIsAuthorized(boolean isAuthorized) {
            this.mIsAuthorized = isAuthorized;
        }

        public void setType(String type) {
            this.mType = type;
        }

        public void setLastChatAt(long lastChatAt) {
            this.mLastChatAt = lastChatAt;
        }

        public void setPushEnabled(boolean pushEnabled) {
            this.mPushEnabled = pushEnabled;
        }

        public void setIsNotice(boolean isNotice) {
            this.mIsNotice = isNotice;
        }

        public void setPermission(GroupPermissionValue permission) {
            this.mPermission = permission;
        }

        public GroupDetailValue build() {
            return new GroupDetailValue(this.mUid, this.mName, this.mDescription, this.mCreatedDate, this.mIcon, this.mStreamHost, this.mTotalUsers, this.mOnlineUsers, this.mIsOnline, this.mIsPublic, this.mIsOfficial, this.mIsAuthorized, this.mType, this.mLastChatAt, this.mPushEnabled, this.mIsNotice, this.mPermission, this.mExId);
        }
    }

    public GroupDetailValue(String uid, String name, String description, long createdDate, String icon, String streamHost, int totalUsers, int onlineUsers, boolean isOnline, boolean isPublic, boolean isOfficial, boolean isAuthorized, String type, long lastChatAt, boolean pushEnabled, boolean isNotice, GroupPermissionValue permission, String exId) {
        this.mUid = uid;
        this.mName = name;
        this.mDescription = description;
        this.mCreatedDate = createdDate;
        this.mIcon = icon;
        this.mStreamHost = streamHost;
        this.mTotalUsers = totalUsers;
        this.mOnlineUsers = onlineUsers;
        this.mIsOnline = isOnline;
        this.mIsPublic = isPublic;
        this.mIsOfficial = isOfficial;
        this.mIsAuthorized = isAuthorized;
        this.mType = type;
        this.mLastChatAt = lastChatAt;
        this.mPushEnabled = pushEnabled;
        this.mPermission = permission;
        this.mIsNotice = isNotice;
        this.mExId = exId;
    }

    public GroupDetailValue(JSONObject json) {
        this.mUid = JSONUtil.getString(json, "uid", null);
        this.mName = JSONUtil.getString(json, "name", null);
        this.mDescription = JSONUtil.getString(json, "description", null);
        this.mCreatedDate = Long.parseLong(JSONUtil.getString(json, "created_date", "0")) * 1000;
        this.mIcon = JSONUtil.getString(json, "icon", null);
        this.mStreamHost = JSONUtil.getString(json, "stream_host", null);
        this.mTotalUsers = Integer.parseInt(JSONUtil.getString(json, "total_users", "0"));
        this.mOnlineUsers = Integer.parseInt(JSONUtil.getString(json, "online_users", "0"));
        this.mIsOnline = JSONUtil.getString(json, "is_online", "0").equals("1");
        this.mIsOfficial = JSONUtil.getString(json, "is_official", "0").equals("1");
        this.mIsPublic = "1".equals(json.optString("is_public", "0"));
        this.mIsAuthorized = JSONUtil.getString(json, "is_authorized", "0").equals("1");
        this.mType = JSONUtil.getString(json, "type", null);
        this.mLastChatAt = Long.parseLong(JSONUtil.getString(json, "last_chat_at", "0")) * 1000;
        this.mPushEnabled = JSONUtil.getString(json, "push_enabled", "0").equals("1");
        this.mIsNotice = JSONUtil.getString(json, "is_notice", "0").equals("1");
        JSONObject optJSONObject = json.optJSONObject("can");
        if (optJSONObject == null) {
            optJSONObject = new JSONObject();
        }
        this.mPermission = new GroupPermissionValue(optJSONObject);
        this.mExId = JSONUtil.getString(json, "ex_id", null);
    }

    public String getUid() {
        return this.mUid;
    }

    public String getExId() {
        return this.mExId;
    }

    public String getName() {
        return this.mName;
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

    public int getTotalUsers() {
        return this.mTotalUsers;
    }

    public int getOnlineUsers() {
        return this.mOnlineUsers;
    }

    public boolean isOnline() {
        return this.mIsOnline;
    }

    public boolean isPublic() {
        return this.mIsPublic;
    }

    public boolean isOfficial() {
        return this.mIsOfficial;
    }

    public boolean isAuthorized() {
        return this.mIsAuthorized;
    }

    public String getType() {
        return this.mType;
    }

    public long getLastChatAt() {
        return this.mLastChatAt;
    }

    public boolean isPushEnabled() {
        return this.mPushEnabled;
    }

    public boolean isNotice() {
        return this.mIsNotice;
    }

    public GroupPermissionValue getPermission() {
        return this.mPermission;
    }

    public GroupType getGroupType() {
        return GroupType.GROUP;
    }

    public int describeContents() {
        return 0;
    }

    private GroupDetailValue(Parcel source) {
        boolean z;
        boolean z2 = true;
        this.mUid = source.readString();
        this.mName = source.readString();
        this.mDescription = source.readString();
        this.mCreatedDate = source.readLong();
        this.mIcon = source.readString();
        this.mStreamHost = source.readString();
        this.mTotalUsers = source.readInt();
        this.mOnlineUsers = source.readInt();
        this.mIsOnline = source.readByte() > (byte) 0;
        if (source.readByte() > (byte) 0) {
            z = true;
        } else {
            z = false;
        }
        this.mIsPublic = z;
        if (source.readByte() > (byte) 0) {
            z = true;
        } else {
            z = false;
        }
        this.mIsOfficial = z;
        if (source.readByte() > (byte) 0) {
            z = true;
        } else {
            z = false;
        }
        this.mIsAuthorized = z;
        this.mType = source.readString();
        this.mLastChatAt = source.readLong();
        if (source.readByte() > (byte) 0) {
            z = true;
        } else {
            z = false;
        }
        this.mPushEnabled = z;
        if (source.readByte() <= (byte) 0) {
            z2 = false;
        }
        this.mIsNotice = z2;
        this.mPermission = (GroupPermissionValue) source.readParcelable(GroupPermissionValue.class.getClassLoader());
        this.mExId = source.readString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        int i;
        int i2 = 1;
        dest.writeString(this.mUid);
        dest.writeString(this.mName);
        dest.writeString(this.mDescription);
        dest.writeLong(this.mCreatedDate);
        dest.writeString(this.mIcon);
        dest.writeString(this.mStreamHost);
        dest.writeInt(this.mTotalUsers);
        dest.writeInt(this.mOnlineUsers);
        dest.writeByte((byte) (this.mIsOnline ? 1 : 0));
        if (this.mIsPublic) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeByte((byte) i);
        if (this.mIsOfficial) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeByte((byte) i);
        if (this.mIsAuthorized) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeByte((byte) i);
        dest.writeString(this.mType);
        dest.writeLong(this.mLastChatAt);
        if (this.mPushEnabled) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeByte((byte) i);
        if (!this.mIsNotice) {
            i2 = 0;
        }
        dest.writeByte((byte) i2);
        dest.writeParcelable(this.mPermission, 0);
        dest.writeString(this.mExId);
    }

    public boolean equals(Object o) {
        if (o instanceof GroupDetailValue) {
            return TextUtils.equals(((GroupDetailValue) o).mUid, this.mUid);
        }
        return super.equals(o);
    }

    public int hashCode() {
        return this.mUid.hashCode();
    }
}
