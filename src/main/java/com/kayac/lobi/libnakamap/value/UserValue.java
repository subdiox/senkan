package com.kayac.lobi.libnakamap.value;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.kayac.lobi.libnakamap.net.APIDef.PostMeCover.RequestKey;
import com.kayac.lobi.libnakamap.utils.JSONUtil;
import com.rekoo.libs.config.Config;
import org.json.JSONObject;

public class UserValue implements Parcelable {
    public static final Creator<UserValue> CREATOR = new Creator<UserValue>() {
        public UserValue createFromParcel(Parcel src) {
            return new UserValue(src);
        }

        public UserValue[] newArray(int size) {
            return new UserValue[size];
        }
    };
    private final AppValue mApp;
    private final long mContactedDate;
    private final int mContactsCount;
    private final String mCover;
    private final boolean mDefault;
    private final String mDescription;
    private final String mExId;
    private final String mIcon;
    private final float mLat;
    private final float mLng;
    private final long mLocatedDate;
    private final String mName;
    private final String mToken;
    private final String mUid;
    private final int mUnreadCount;

    public static final class Builder {
        private AppValue mApp;
        private long mContactedDate;
        private int mContactsCount;
        private String mCover;
        private boolean mDefault;
        private String mDescription;
        private String mExId;
        private String mIcon;
        private float mLat;
        private float mLng;
        private long mLocatedDate;
        private String mName;
        private String mToken;
        private String mUid;
        private int mUnreadCount;

        public Builder(UserValue src) {
            this.mUid = src.getUid();
            this.mDefault = src.isDefault();
            this.mToken = src.getToken();
            this.mName = src.getName();
            this.mDescription = src.getDescription();
            this.mIcon = src.getIcon();
            this.mCover = src.getCover();
            this.mContactsCount = src.getContactsCount();
            this.mContactedDate = src.getContactedDate();
            this.mLng = src.getLng();
            this.mLat = src.getLat();
            this.mLocatedDate = src.getLocatedDate();
            this.mUnreadCount = src.getUnreadCount();
            this.mApp = src.getApp();
            this.mExId = src.getExId();
        }

        public void setUid(String uid) {
            this.mUid = uid;
        }

        public void setDefault(boolean default1) {
            this.mDefault = default1;
        }

        public void setToken(String token) {
            this.mToken = token;
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

        public void setCover(String cover) {
            this.mCover = cover;
        }

        public void setContactsCount(int contactsCount) {
            this.mContactsCount = contactsCount;
        }

        public void setContactedDate(long contactedDate) {
            this.mContactedDate = contactedDate;
        }

        public void setLng(float lng) {
            this.mLng = lng;
        }

        public void setLat(float lat) {
            this.mLat = lat;
        }

        public void setLocatedDate(long locatedDate) {
            this.mLocatedDate = locatedDate;
        }

        public void setUnreadCount(int unreadCount) {
            this.mUnreadCount = unreadCount;
        }

        public void setApp(AppValue app) {
            this.mApp = app;
        }

        public void setExId(String exId) {
            this.mExId = exId;
        }

        public UserValue build() {
            return new UserValue(this.mUid, this.mDefault, this.mToken, this.mName, this.mDescription, this.mIcon, this.mCover, this.mContactsCount, this.mContactedDate, this.mLng, this.mLat, this.mLocatedDate, this.mUnreadCount, this.mApp, this.mExId);
        }
    }

    public UserValue(String uid, boolean default1, String token, String name, String description, String icon, String cover, int contactsCount, long contactedDate, float lng, float lat, long locatedDate, int unreadCount, AppValue app, String exId) {
        this.mUid = uid;
        this.mDefault = default1;
        this.mToken = token;
        this.mName = name;
        this.mDescription = description;
        this.mIcon = icon;
        this.mCover = cover;
        this.mContactsCount = contactsCount;
        this.mContactedDate = contactedDate;
        this.mLng = lng;
        this.mLat = lat;
        this.mLocatedDate = locatedDate;
        this.mUnreadCount = unreadCount;
        this.mApp = app;
        this.mExId = exId;
    }

    public UserValue(JSONObject json) {
        long j = -1;
        this.mUid = JSONUtil.getString(json, "uid", null);
        this.mDefault = JSONUtil.getString(json, "default", "0").equals("1");
        this.mToken = JSONUtil.getString(json, "token", null);
        this.mName = JSONUtil.getString(json, "name", null);
        this.mDescription = JSONUtil.getString(json, "description", null);
        this.mIcon = JSONUtil.getString(json, "icon", null);
        this.mCover = JSONUtil.getString(json, RequestKey.COVER, null);
        this.mContactsCount = Integer.parseInt(JSONUtil.getString(json, "contacts_count", "0"));
        String contactedDate = JSONUtil.getString(json, "contacted_date", Config.INIT_FAIL_NO_NETWORK);
        if (Long.parseLong(contactedDate) != -1) {
            j = Long.parseLong(contactedDate) * 1000;
        }
        this.mContactedDate = j;
        this.mUnreadCount = Integer.parseInt(JSONUtil.getString(json, "unread_count", "0"));
        if (!json.has("lng") || json.isNull("lng")) {
            this.mLng = Float.NaN;
        } else {
            this.mLng = Float.parseFloat(json.optString("lng", "0.0"));
        }
        if (!json.has("lat") || json.isNull("lat")) {
            this.mLat = Float.NaN;
        } else {
            this.mLat = Float.parseFloat(json.optString("lat", "0.0"));
        }
        this.mLocatedDate = Long.parseLong(JSONUtil.getString(json, "located_date", "0")) * 1000;
        JSONObject app = json.optJSONObject("app");
        if (app != null) {
            this.mApp = new AppValue(app);
        } else {
            this.mApp = null;
        }
        this.mExId = json.optString("ex_id", null);
    }

    public String getUid() {
        return this.mUid;
    }

    public String getExId() {
        return this.mExId;
    }

    public boolean isDefault() {
        return this.mDefault;
    }

    public String getToken() {
        return this.mToken;
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

    public String getCover() {
        return this.mCover;
    }

    public int getContactsCount() {
        return this.mContactsCount;
    }

    public float getLng() {
        return this.mLng;
    }

    public float getLat() {
        return this.mLat;
    }

    public long getLocatedDate() {
        return this.mLocatedDate;
    }

    public long getContactedDate() {
        return this.mContactedDate;
    }

    public int getUnreadCount() {
        return this.mUnreadCount;
    }

    public AppValue getApp() {
        return this.mApp;
    }

    public boolean equals(Object o) {
        if (!(o instanceof UserValue)) {
            return super.equals(o);
        }
        return TextUtils.equals(this.mUid, ((UserValue) o).getUid());
    }

    public int hashCode() {
        return this.mUid.hashCode();
    }

    public int describeContents() {
        return 0;
    }

    private UserValue(Parcel src) {
        this.mUid = src.readString();
        this.mDefault = src.readByte() > (byte) 0;
        this.mToken = src.readString();
        this.mName = src.readString();
        this.mDescription = src.readString();
        this.mIcon = src.readString();
        this.mCover = src.readString();
        this.mContactsCount = src.readInt();
        this.mContactedDate = src.readLong();
        this.mLng = src.readFloat();
        this.mLat = src.readFloat();
        this.mLocatedDate = src.readLong();
        this.mApp = (AppValue) src.readParcelable(AppValue.class.getClassLoader());
        this.mUnreadCount = src.readInt();
        this.mExId = src.readString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mUid);
        dest.writeByte((byte) (this.mDefault ? 1 : 0));
        dest.writeString(this.mToken);
        dest.writeString(this.mName);
        dest.writeString(this.mDescription);
        dest.writeString(this.mIcon);
        dest.writeString(this.mCover);
        dest.writeInt(this.mContactsCount);
        dest.writeLong(this.mContactedDate);
        dest.writeFloat(this.mLng);
        dest.writeFloat(this.mLat);
        dest.writeLong(this.mLocatedDate);
        dest.writeParcelable(this.mApp, 0);
        dest.writeInt(this.mUnreadCount);
        dest.writeString(this.mExId);
    }
}
