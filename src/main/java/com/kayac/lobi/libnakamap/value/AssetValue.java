package com.kayac.lobi.libnakamap.value;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.kayac.lobi.libnakamap.utils.JSONUtil;
import org.json.JSONObject;

public class AssetValue implements Parcelable {
    public static final Creator<AssetValue> CREATOR = new Creator<AssetValue>() {
        public AssetValue createFromParcel(Parcel source) {
            return new AssetValue(source);
        }

        public AssetValue[] newArray(int size) {
            return new AssetValue[size];
        }
    };
    public static final String TYPE_IMAGE = "image";
    public static final String TYPE_VIDEO = "video";
    private final String mType;
    private final String mUid;
    private final String mUrl;

    public static final class Builder {
        private String mType;
        private String mUid;
        private String mUrl;

        public Builder(AssetValue src) {
            this.mUid = src.getUid();
            this.mType = src.getType();
            this.mUrl = src.getUrl();
        }

        public void setUid(String uid) {
            this.mUid = uid;
        }

        public void setType(String type) {
            this.mType = type;
        }

        public void setUrl(String url) {
            this.mUrl = url;
        }

        public AssetValue build() {
            return new AssetValue(this.mUid, this.mType, this.mUrl);
        }
    }

    public AssetValue(String uid, String type, String url) {
        this.mUid = uid;
        this.mType = type;
        this.mUrl = url;
    }

    public AssetValue(JSONObject json) {
        this.mUid = JSONUtil.getString(json, "id", null);
        this.mType = JSONUtil.getString(json, "type", null);
        this.mUrl = JSONUtil.getString(json, "url", null);
    }

    public AssetValue(String asset) {
        String[] fields = asset.split(":");
        this.mUid = fields[0];
        this.mType = fields[1];
        this.mUrl = fields[2];
    }

    public String getUid() {
        return this.mUid;
    }

    public String getType() {
        return this.mType;
    }

    public String getUrl() {
        return this.mUrl;
    }

    public int describeContents() {
        return 0;
    }

    private AssetValue(Parcel source) {
        this.mUid = source.readString();
        this.mType = source.readString();
        this.mUrl = source.readString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mUid);
        dest.writeString(this.mType);
        dest.writeString(this.mUrl);
    }

    public boolean equals(Object o) {
        if (!(o instanceof UserValue)) {
            return super.equals(o);
        }
        return TextUtils.equals(this.mUid, ((AssetValue) o).getUid());
    }

    public int hashCode() {
        return this.mUid.hashCode();
    }
}
