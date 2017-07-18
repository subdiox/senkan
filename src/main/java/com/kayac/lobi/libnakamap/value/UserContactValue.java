package com.kayac.lobi.libnakamap.value;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.kayac.lobi.libnakamap.utils.JSONUtil;
import com.rekoo.libs.config.Config;
import org.json.JSONObject;

public class UserContactValue implements Parcelable {
    public static final Creator<UserContactValue> CREATOR = new Creator<UserContactValue>() {
        public UserContactValue createFromParcel(Parcel source) {
            return new UserContactValue(source);
        }

        public UserContactValue[] newArray(int size) {
            return new UserContactValue[size];
        }
    };
    private final long mContactedDate;
    private final int mContactsCount;
    private final String mDescription;
    private final String mIcon;
    private final int mMyGroupsCount;
    private final String mName;
    private final String mUid;

    public static final class Builder {
        private long mContactedDate;
        private int mContactsCount;
        private String mDescription;
        private String mIcon;
        private int mMyGroupsCount;
        private String mName;
        private String mUid;

        public Builder(UserContactValue src) {
            this.mUid = src.getUid();
            this.mName = src.getName();
            this.mDescription = src.getDescription();
            this.mIcon = src.getIcon();
            this.mContactsCount = src.getContactsCount();
            this.mMyGroupsCount = src.getMyGroupsCount();
            this.mContactedDate = src.getContactedDate();
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

        public void setContactsCount(int contactsCount) {
            this.mContactsCount = contactsCount;
        }

        public void setMyGroupsCount(int myGroupsCount) {
            this.mMyGroupsCount = myGroupsCount;
        }

        public void setContactedDate(long contactedDate) {
            this.mContactedDate = contactedDate;
        }

        public UserContactValue build() {
            return new UserContactValue(this.mUid, this.mName, this.mDescription, this.mIcon, this.mContactsCount, this.mMyGroupsCount, this.mContactedDate);
        }
    }

    public UserContactValue(String uid, String name, String description, String icon, int contactsCount, int myGroupsCount, long contactedDate) {
        this.mUid = uid;
        this.mName = name;
        this.mDescription = description;
        this.mIcon = icon;
        this.mContactsCount = contactsCount;
        this.mMyGroupsCount = myGroupsCount;
        this.mContactedDate = contactedDate;
    }

    public UserContactValue(JSONObject json) {
        long j = -1;
        this.mUid = JSONUtil.getString(json, "uid", null);
        this.mName = JSONUtil.getString(json, "name", null);
        this.mDescription = JSONUtil.getString(json, "description", null);
        this.mIcon = JSONUtil.getString(json, "icon", null);
        this.mContactsCount = Integer.parseInt(JSONUtil.getString(json, "contacts_count", "0"));
        this.mMyGroupsCount = Integer.parseInt(JSONUtil.getString(json, "my_groups_count", "0"));
        String contactedDate = JSONUtil.getString(json, "contacted_date", Config.INIT_FAIL_NO_NETWORK);
        if (Long.parseLong(contactedDate) != -1) {
            j = Long.parseLong(contactedDate) * 1000;
        }
        this.mContactedDate = j;
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

    public String getIcon() {
        return this.mIcon;
    }

    public int getContactsCount() {
        return this.mContactsCount;
    }

    public int getMyGroupsCount() {
        return this.mMyGroupsCount;
    }

    public long getContactedDate() {
        return this.mContactedDate;
    }

    public boolean equals(Object o) {
        if (!(o instanceof UserContactValue)) {
            return super.equals(o);
        }
        return TextUtils.equals(this.mUid, ((UserContactValue) o).getUid());
    }

    public int hashCode() {
        return this.mUid.hashCode();
    }

    public int describeContents() {
        return 0;
    }

    private UserContactValue(Parcel source) {
        this.mUid = source.readString();
        this.mName = source.readString();
        this.mDescription = source.readString();
        this.mIcon = source.readString();
        this.mContactsCount = source.readInt();
        this.mMyGroupsCount = source.readInt();
        this.mContactedDate = source.readLong();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mUid);
        dest.writeString(this.mName);
        dest.writeString(this.mDescription);
        dest.writeString(this.mIcon);
        dest.writeInt(this.mContactsCount);
        dest.writeInt(this.mMyGroupsCount);
        dest.writeLong(this.mContactedDate);
    }
}
