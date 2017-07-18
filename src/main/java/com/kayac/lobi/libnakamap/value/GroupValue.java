package com.kayac.lobi.libnakamap.value;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.kayac.lobi.libnakamap.net.APIDef.PostGroupWallpaper.RequestKey;
import com.kayac.lobi.libnakamap.utils.JSONUtil;
import com.kayac.lobi.libnakamap.value.GroupInterface.GroupType;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class GroupValue implements GroupInterface, Parcelable {
    public static final Creator<GroupValue> CREATOR = new Creator<GroupValue>() {
        public GroupValue createFromParcel(Parcel source) {
            return new GroupValue(source);
        }

        public GroupValue[] newArray(int size) {
            return new GroupValue[size];
        }
    };
    public static final String INVITED = "invited";
    public static final String MINE = "mine";
    public static final String NOT_JOINED = "not_joined";
    private final List<ChatValue> mChats;
    private final long mCreatedDate;
    private final String mDescription;
    private final String mExId;
    private final GroupButtonHooksValue mGroupButtonHooksValue;
    private final String mIcon;
    private final boolean mIsAuthorized;
    private final boolean mIsNotice;
    private final boolean mIsOfficial;
    private final boolean mIsOnline;
    private final boolean mIsPublic;
    private final List<JoinCondition> mJoinConditions;
    private final List<UserValue> mMembers;
    private final int mMembersCount;
    private final String mMembersNextCursor;
    private final String mName;
    private final UserValue mOwner;
    private final GroupPermissionValue mPermission;
    private final boolean mPushEnabled;
    private final String mStreamHost;
    private final String mType;
    private final String mUid;
    private final String mWallpaper;

    public static final class Builder {
        private List<ChatValue> mChats;
        private long mCreatedDate;
        private String mDescription;
        private String mExId;
        private GroupButtonHooksValue mGroupButtonHooksValue;
        private String mIcon;
        private boolean mIsAuthorized;
        private boolean mIsNotice;
        private boolean mIsOfficial;
        private boolean mIsOnline;
        private boolean mIsPublic;
        private List<JoinCondition> mJoinConditions;
        private List<UserValue> mMembers;
        private int mMembersCount;
        private String mMembersNextCursor;
        private String mName;
        private UserValue mOwner;
        private GroupPermissionValue mPermission;
        private boolean mPushEnabled;
        private String mStreamHost;
        private String mType;
        private String mUid;
        private String mWallpaper;

        public Builder(GroupValue src) {
            this.mUid = src.getUid();
            this.mName = src.getName();
            this.mDescription = src.getDescription();
            this.mCreatedDate = src.getCreatedDate();
            this.mIcon = src.getIcon();
            this.mStreamHost = src.getStreamHost();
            this.mPushEnabled = src.isPushEnabled();
            this.mMembers = src.getMembers();
            this.mChats = src.getChats();
            this.mOwner = src.getOwner();
            this.mIsOnline = src.isOnline();
            this.mIsOfficial = src.isOfficial();
            this.mIsAuthorized = src.isAuthorized();
            this.mMembersCount = src.getMembersCount();
            this.mMembersNextCursor = src.getMembersNextCursor();
            this.mPermission = src.getPermission();
            this.mType = src.getType();
            this.mWallpaper = src.getWallpaper();
            this.mIsNotice = src.isNotice();
            this.mExId = src.getExId();
            this.mJoinConditions = src.getJoinConditions();
            this.mGroupButtonHooksValue = src.getGroupButtonHooksValue();
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

        public void setPushEnabled(boolean pushEnabled) {
            this.mPushEnabled = pushEnabled;
        }

        public void setMembers(List<UserValue> members) {
            this.mMembers = members;
        }

        public void setChats(List<ChatValue> chats) {
            this.mChats = chats;
        }

        public void setOwner(UserValue owner) {
            this.mOwner = owner;
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

        public void setMembersCount(int membersCount) {
            this.mMembersCount = membersCount;
        }

        public void setMembersNextCursor(String membersNextCursor) {
            this.mMembersNextCursor = membersNextCursor;
        }

        public void setPermission(GroupPermissionValue permission) {
            this.mPermission = permission;
        }

        public void setType(String type) {
            this.mType = type;
        }

        public void setWallpaper(String wallpaper) {
            this.mWallpaper = wallpaper;
        }

        public void setIsNotice(boolean isNotice) {
            this.mIsNotice = isNotice;
        }

        public void setExId(String exId) {
            this.mExId = exId;
        }

        public void setJoinConditions(List<JoinCondition> joinConditions) {
            this.mJoinConditions = joinConditions;
        }

        public void setGroupButtonHooksValue(GroupButtonHooksValue groupButtonHooksValue) {
            this.mGroupButtonHooksValue = groupButtonHooksValue;
        }

        public GroupValue build() {
            return new GroupValue(this.mUid, this.mName, this.mDescription, this.mCreatedDate, this.mIcon, this.mStreamHost, this.mPushEnabled, this.mMembers, this.mChats, this.mOwner, this.mIsOnline, this.mIsPublic, this.mIsOfficial, this.mIsAuthorized, this.mMembersCount, this.mMembersNextCursor, this.mPermission, this.mType, this.mWallpaper, this.mIsNotice, this.mExId, this.mJoinConditions, this.mGroupButtonHooksValue);
        }
    }

    public static class JoinCondition implements Parcelable {
        public static final Creator<JoinCondition> CREATOR = new Creator<JoinCondition>() {
            public JoinCondition createFromParcel(Parcel in) {
                return new JoinCondition(in);
            }

            public JoinCondition[] newArray(int size) {
                return new JoinCondition[size];
            }
        };
        private final Params mParams;
        private final String mType;

        public static abstract class Params implements Parcelable {
        }

        public static class InstalledParams extends Params {
            public static final Creator<InstalledParams> CREATOR = new Creator<InstalledParams>() {
                public InstalledParams createFromParcel(Parcel in) {
                    return new InstalledParams(in);
                }

                public InstalledParams[] newArray(int size) {
                    return new InstalledParams[size];
                }
            };
            public static final String TYPE = "app_installed";
            private final String mAppUid;
            private final String mPackageName;

            public InstalledParams(Parcel source) {
                this.mAppUid = source.readString();
                this.mPackageName = source.readString();
            }

            public InstalledParams(JSONObject json) {
                this.mAppUid = json.optString("app_uid");
                this.mPackageName = json.optString(AuthorizedAppValue.JSON_KEY_PACKAGE);
            }

            public String getAppUid() {
                return this.mAppUid;
            }

            public String getPackageName() {
                return this.mPackageName;
            }

            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.mAppUid);
                dest.writeString(this.mPackageName);
            }

            public int describeContents() {
                return 0;
            }
        }

        public JoinCondition(Parcel source) {
            this.mType = source.readString();
            if (this.mType.equals(InstalledParams.TYPE)) {
                this.mParams = (Params) source.readParcelable(InstalledParams.class.getClassLoader());
            } else {
                this.mParams = null;
            }
        }

        public JoinCondition(JSONObject json) {
            this.mType = json.optString("type");
            JSONObject params = json.optJSONObject("params");
            if (this.mType.equals(InstalledParams.TYPE)) {
                this.mParams = new InstalledParams(params);
            } else {
                this.mParams = null;
            }
        }

        public String getType() {
            return this.mType;
        }

        public Params getParams() {
            return this.mParams;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.mType);
            dest.writeParcelable(this.mParams, 0);
        }

        public int describeContents() {
            return 0;
        }
    }

    public GroupValue(String uid, String name, String description, long createdDate, String icon, String streamHost, boolean pushEnabled, List<UserValue> members, List<ChatValue> chats, UserValue owner, boolean isOnline, boolean isPublic, boolean isOfficial, boolean isAuthorized, int membersCount, String membersNextCursor, GroupPermissionValue permission, String type, String wallpaper, boolean isNotice, String exId, List<JoinCondition> joinConditions, GroupButtonHooksValue groupButtonHooksValue) {
        this.mUid = uid;
        this.mName = name;
        this.mDescription = description;
        this.mCreatedDate = createdDate;
        this.mIcon = icon;
        this.mStreamHost = streamHost;
        this.mPushEnabled = pushEnabled;
        this.mMembers = members;
        this.mChats = chats;
        this.mOwner = owner;
        this.mIsOnline = isOnline;
        this.mIsPublic = isPublic;
        this.mIsOfficial = isOfficial;
        this.mIsAuthorized = isAuthorized;
        this.mMembersCount = membersCount;
        this.mMembersNextCursor = membersNextCursor;
        this.mPermission = permission;
        this.mType = type;
        this.mWallpaper = wallpaper;
        this.mIsNotice = isNotice;
        this.mExId = exId;
        this.mJoinConditions = joinConditions;
        this.mGroupButtonHooksValue = groupButtonHooksValue;
    }

    public GroupValue(JSONObject json) {
        int length;
        int i;
        JSONObject obj;
        this.mUid = JSONUtil.getString(json, "uid", null);
        this.mName = JSONUtil.getString(json, "name", null);
        this.mDescription = JSONUtil.getString(json, "description", null);
        this.mCreatedDate = Long.parseLong(JSONUtil.getString(json, "created_date", "0")) * 1000;
        this.mIcon = JSONUtil.getString(json, "icon", null);
        this.mStreamHost = JSONUtil.getString(json, "stream_host", null);
        this.mPushEnabled = JSONUtil.getString(json, "push_enabled", "0").equals("1");
        this.mMembers = new ArrayList();
        JSONArray ary = json.optJSONArray("members");
        if (ary != null) {
            length = ary.length();
            for (i = 0; i < length; i++) {
                obj = ary.optJSONObject(i);
                if (obj != null) {
                    this.mMembers.add(new UserValue(obj));
                }
            }
        }
        this.mChats = new ArrayList();
        ary = json.optJSONArray("chats");
        if (ary != null) {
            length = ary.length();
            for (i = 0; i < length; i++) {
                obj = ary.optJSONObject(i);
                if (obj != null) {
                    this.mChats.add(new ChatValue(obj));
                }
            }
        }
        JSONObject owner = json.optJSONObject("owner");
        if (owner != null) {
            this.mOwner = new UserValue(owner);
        } else {
            this.mOwner = null;
        }
        JSONObject me = json.optJSONObject("me");
        if (me != null) {
            this.mIsOnline = JSONUtil.getString(me, "is_online", "0").equals("1");
        } else {
            this.mIsOnline = false;
        }
        this.mIsPublic = "1".equals(json.optString("is_public", "0"));
        this.mIsOfficial = "1".equals(json.optString("is_official", "0"));
        this.mIsAuthorized = "1".equals(json.optString("is_authorized", "0"));
        this.mMembersCount = Integer.parseInt(JSONUtil.getString(json, "members_count", "0"));
        this.mMembersNextCursor = JSONUtil.getString(json, "members_next_cursor", "0");
        JSONObject optJSONObject = json.optJSONObject("can");
        if (optJSONObject == null) {
            optJSONObject = new JSONObject();
        }
        this.mPermission = new GroupPermissionValue(optJSONObject);
        this.mType = JSONUtil.getString(json, "type", null);
        this.mWallpaper = JSONUtil.getString(json, RequestKey.WALLPAPER, null);
        this.mIsNotice = JSONUtil.getString(json, "is_notice", "0").equals("1");
        this.mExId = JSONUtil.getString(json, "ex_id", null);
        this.mJoinConditions = new ArrayList();
        JSONArray array = json.optJSONArray("needs_to_join");
        if (array != null) {
            for (i = 0; i < array.length(); i++) {
                JSONObject item = array.optJSONObject(i);
                if (item != null) {
                    this.mJoinConditions.add(new JoinCondition(item));
                }
            }
        }
        this.mGroupButtonHooksValue = new GroupButtonHooksValue(json);
    }

    public String getUid() {
        return this.mUid;
    }

    public String getExId() {
        return this.mExId;
    }

    public List<JoinCondition> getJoinConditions() {
        return this.mJoinConditions;
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

    public boolean isPushEnabled() {
        return this.mPushEnabled;
    }

    public List<UserValue> getMembers() {
        return this.mMembers;
    }

    public List<ChatValue> getChats() {
        return this.mChats;
    }

    public UserValue getOwner() {
        return this.mOwner;
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

    public int getMembersCount() {
        return this.mMembersCount;
    }

    public String getMembersNextCursor() {
        return this.mMembersNextCursor;
    }

    public GroupPermissionValue getPermission() {
        return this.mPermission;
    }

    public String getType() {
        return this.mType;
    }

    public GroupType getGroupType() {
        return GroupType.GROUP;
    }

    public String getWallpaper() {
        return this.mWallpaper;
    }

    public boolean isNotice() {
        return this.mIsNotice;
    }

    public GroupButtonHooksValue getGroupButtonHooksValue() {
        return this.mGroupButtonHooksValue;
    }

    public int describeContents() {
        return 0;
    }

    private GroupValue(Parcel source) {
        boolean z;
        boolean z2 = true;
        this.mUid = source.readString();
        this.mName = source.readString();
        this.mDescription = source.readString();
        this.mCreatedDate = source.readLong();
        this.mIcon = source.readString();
        this.mStreamHost = source.readString();
        this.mPushEnabled = source.readByte() > (byte) 0;
        this.mMembers = new ArrayList();
        this.mChats = new ArrayList();
        source.readList(this.mMembers, UserValue.class.getClassLoader());
        source.readList(this.mChats, ChatValue.class.getClassLoader());
        this.mOwner = (UserValue) source.readParcelable(UserValue.class.getClassLoader());
        if (source.readByte() > (byte) 0) {
            z = true;
        } else {
            z = false;
        }
        this.mIsOnline = z;
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
        this.mMembersCount = source.readInt();
        this.mMembersNextCursor = source.readString();
        this.mPermission = (GroupPermissionValue) source.readParcelable(GroupPermissionValue.class.getClassLoader());
        this.mType = source.readString();
        this.mWallpaper = source.readString();
        if (source.readByte() <= (byte) 0) {
            z2 = false;
        }
        this.mIsNotice = z2;
        this.mExId = source.readString();
        this.mJoinConditions = new ArrayList();
        source.readList(this.mJoinConditions, JoinCondition.class.getClassLoader());
        this.mGroupButtonHooksValue = (GroupButtonHooksValue) source.readParcelable(GroupButtonHooksValue.class.getClassLoader());
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
        dest.writeByte((byte) (this.mPushEnabled ? 1 : 0));
        dest.writeList(this.mMembers);
        dest.writeList(this.mChats);
        dest.writeParcelable(this.mOwner, 0);
        if (this.mIsOnline) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeByte((byte) i);
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
        dest.writeInt(this.mMembersCount);
        dest.writeString(this.mMembersNextCursor);
        dest.writeParcelable(this.mPermission, 0);
        dest.writeString(this.mType);
        dest.writeString(this.mWallpaper);
        if (!this.mIsNotice) {
            i2 = 0;
        }
        dest.writeByte((byte) i2);
        dest.writeString(this.mExId);
        dest.writeList(this.mJoinConditions);
        dest.writeParcelable(this.mGroupButtonHooksValue, 0);
    }
}
