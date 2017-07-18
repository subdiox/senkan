package com.kayac.lobi.libnakamap.value;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.kayac.lobi.libnakamap.utils.JSONUtil;
import org.json.JSONObject;

public class AppValue implements Parcelable {
    public static final Creator<AppValue> CREATOR = new Creator<AppValue>() {
        public AppValue createFromParcel(Parcel in) {
            return new AppValue(in);
        }

        public AppValue[] newArray(int size) {
            return new AppValue[size];
        }
    };
    private final String mAppstoreUri;
    private final String mClientId;
    private final String mIcon;
    private final String mName;
    private final String mPlaystoreUri;
    private final String mUid;

    public static final class Builder {
        private String mAppstoreUri;
        private String mClientId;
        private String mIcon;
        private String mName;
        private String mPlaystoreUri;
        private String mUid;

        public Builder(AppValue src) {
            this.mName = src.getName();
            this.mIcon = src.getIcon();
            this.mAppstoreUri = src.getAppstoreUri();
            this.mPlaystoreUri = src.getPlaystoreUri();
            this.mUid = src.getUid();
        }

        public void setName(String name) {
            this.mName = name;
        }

        public void setIcon(String icon) {
            this.mIcon = icon;
        }

        public void setAppstoreUri(String appstoreUri) {
            this.mAppstoreUri = appstoreUri;
        }

        public void setPlaystoreUri(String playstoreUri) {
            this.mPlaystoreUri = playstoreUri;
        }

        public void setUid(String uid) {
            this.mUid = uid;
        }

        public void setClientId(String clientId) {
            this.mClientId = clientId;
        }

        public AppValue build() {
            return new AppValue(this.mName, this.mIcon, this.mAppstoreUri, this.mPlaystoreUri, this.mUid, this.mClientId);
        }
    }

    public AppValue(String name, String icon, String appstoreUri, String playstoreUri, String uid, String clientId) {
        this.mName = name;
        this.mIcon = icon;
        this.mAppstoreUri = appstoreUri;
        this.mPlaystoreUri = playstoreUri;
        this.mUid = uid;
        this.mClientId = clientId;
    }

    public AppValue(JSONObject json) {
        this.mName = JSONUtil.getString(json, "name", null);
        this.mIcon = JSONUtil.getString(json, "icon", null);
        this.mAppstoreUri = JSONUtil.getString(json, "appstore_uri", null);
        this.mPlaystoreUri = JSONUtil.getString(json, "playstore_uri", null);
        this.mUid = JSONUtil.getString(json, "uid", null);
        this.mClientId = JSONUtil.getString(json, "client_id", null);
    }

    public String getName() {
        return this.mName;
    }

    public String getIcon() {
        return this.mIcon;
    }

    public String getAppstoreUri() {
        return this.mAppstoreUri;
    }

    public String getPlaystoreUri() {
        return this.mPlaystoreUri;
    }

    public String getUid() {
        return this.mUid;
    }

    public String getClientId() {
        return this.mClientId;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.mName);
        out.writeString(this.mIcon);
        out.writeString(this.mAppstoreUri);
        out.writeString(this.mPlaystoreUri);
        out.writeString(this.mUid);
        out.writeString(this.mClientId);
    }

    private AppValue(Parcel in) {
        this.mName = in.readString();
        this.mIcon = in.readString();
        this.mAppstoreUri = in.readString();
        this.mPlaystoreUri = in.readString();
        this.mUid = in.readString();
        this.mClientId = in.readString();
    }
}
