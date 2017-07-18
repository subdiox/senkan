package com.kayac.lobi.libnakamap.value;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.kayac.lobi.libnakamap.utils.JSONUtil;
import com.rekoo.libs.config.Config;
import org.json.JSONException;
import org.json.JSONObject;

public class LobiAccountContactValue implements Parcelable {
    public static final Creator<LobiAccountContactValue> CREATOR = new Creator<LobiAccountContactValue>() {
        public LobiAccountContactValue createFromParcel(Parcel source) {
            return new LobiAccountContactValue(source);
        }

        public LobiAccountContactValue[] newArray(int size) {
            return new LobiAccountContactValue[size];
        }
    };
    public static final long UNDEFINED_DATE = -1;
    private final UserValue mAppUser;
    private String mDescription;
    private long mFollowedDate;
    private final int mFollowingCount;
    private long mFollowingDate;
    private final String mIcon;
    private final int mMyGroupsCount;
    private final String mName;
    private final String mUid;

    public static final class Builder {
        private UserValue mAppUser;
        private String mDescription;
        private long mFollowedDate;
        private int mFollowingCount;
        private long mFollowingDate;
        private String mIcon;
        private int mMyGroupsCount;
        private String mName;
        private String mUid;

        public Builder(LobiAccountContactValue src) {
            this.mUid = src.getUid();
            this.mName = src.getName();
            this.mDescription = src.getDescription();
            this.mIcon = src.getIcon();
            this.mFollowingCount = src.getFollowingCount();
            this.mMyGroupsCount = src.getMyGroupsCount();
            this.mFollowingDate = src.getFollowingDate();
            this.mFollowedDate = src.getFollowedDate();
            this.mAppUser = src.getAppUser();
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

        public void setIcon(String icon) {
            this.mIcon = icon;
        }

        public void setFollowingCount(int followingCount) {
            this.mFollowingCount = followingCount;
        }

        public void setMyGroupsCount(int myGroupsCount) {
            this.mMyGroupsCount = myGroupsCount;
        }

        public void setFollowingDate(long followingDate) {
            this.mFollowingDate = followingDate;
        }

        public void setFollowedDate(long followedDate) {
            this.mFollowedDate = followedDate;
        }

        public void setAppUser(UserValue appUser) {
            this.mAppUser = appUser;
        }

        public LobiAccountContactValue build() {
            return new LobiAccountContactValue(this.mUid, this.mName, this.mDescription, this.mIcon, this.mFollowingCount, this.mMyGroupsCount, this.mFollowingDate, this.mFollowedDate, this.mAppUser);
        }
    }

    public LobiAccountContactValue(String uid, String name, String description, String icon, int followingCount, int myGroupsCount, long followingDate, long followedDate, UserValue appUser) {
        this.mUid = uid;
        this.mName = name;
        this.mDescription = description;
        this.mIcon = icon;
        this.mFollowingCount = followingCount;
        this.mMyGroupsCount = myGroupsCount;
        this.mFollowingDate = followingDate;
        this.mFollowedDate = followedDate;
        this.mAppUser = appUser;
    }

    public LobiAccountContactValue(JSONObject json) {
        this.mUid = JSONUtil.getString(json, "uid", null);
        this.mName = JSONUtil.getString(json, "name", null);
        this.mDescription = JSONUtil.getString(json, "description", null);
        this.mIcon = JSONUtil.getString(json, "icon", null);
        this.mFollowingCount = Integer.parseInt(JSONUtil.getString(json, "contacts_count", "0"));
        this.mMyGroupsCount = Integer.parseInt(JSONUtil.getString(json, "my_groups_count", "0"));
        String followingDate = JSONUtil.getString(json, "following_date", Config.INIT_FAIL_NO_NETWORK);
        this.mFollowingDate = Long.parseLong(followingDate) <= 0 ? -1 : Long.parseLong(followingDate) * 1000;
        String followedDate = JSONUtil.getString(json, "followed_date", Config.INIT_FAIL_NO_NETWORK);
        this.mFollowedDate = Long.parseLong(followedDate) <= 0 ? -1 : Long.parseLong(followedDate) * 1000;
        JSONObject appUser = null;
        try {
            appUser = json.getJSONObject("app_user");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (appUser != null) {
            this.mAppUser = new UserValue(appUser);
        } else {
            this.mAppUser = null;
        }
    }

    public String getUid() {
        return this.mUid;
    }

    public String getName() {
        return this.mName;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public String getIcon() {
        return this.mIcon;
    }

    public int getFollowingCount() {
        return this.mFollowingCount;
    }

    public int getMyGroupsCount() {
        return this.mMyGroupsCount;
    }

    public long getFollowingDate() {
        return this.mFollowingDate;
    }

    public UserValue getAppUser() {
        return this.mAppUser;
    }

    public boolean isFollowing() {
        return -1 != this.mFollowingDate;
    }

    public void setFollowingDate(long date) {
        this.mFollowingDate = date;
    }

    public long getFollowedDate() {
        return this.mFollowedDate;
    }

    public boolean equals(Object o) {
        if (!(o instanceof LobiAccountContactValue)) {
            return super.equals(o);
        }
        return TextUtils.equals(this.mUid, ((LobiAccountContactValue) o).getUid());
    }

    public int hashCode() {
        return this.mUid.hashCode();
    }

    public int describeContents() {
        return 0;
    }

    private LobiAccountContactValue(Parcel source) {
        this.mUid = source.readString();
        this.mName = source.readString();
        this.mDescription = source.readString();
        this.mIcon = source.readString();
        this.mFollowingCount = source.readInt();
        this.mMyGroupsCount = source.readInt();
        this.mFollowingDate = source.readLong();
        this.mFollowedDate = source.readLong();
        this.mAppUser = (UserValue) source.readParcelable(UserValue.class.getClassLoader());
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mUid);
        dest.writeString(this.mName);
        dest.writeString(this.mDescription);
        dest.writeString(this.mIcon);
        dest.writeInt(this.mFollowingCount);
        dest.writeInt(this.mMyGroupsCount);
        dest.writeLong(this.mFollowingDate);
        dest.writeLong(this.mFollowedDate);
        dest.writeParcelable(this.mAppUser, 0);
    }
}
