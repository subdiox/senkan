package com.kayac.lobi.libnakamap.value;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.kayac.lobi.libnakamap.utils.JSONUtil;
import org.json.JSONException;
import org.json.JSONObject;

public class RankingDetailValue implements Parcelable {
    public static final Creator<RankingDetailValue> CREATOR = new Creator<RankingDetailValue>() {
        public RankingDetailValue createFromParcel(Parcel source) {
            return new RankingDetailValue(source);
        }

        public RankingDetailValue[] newArray(int size) {
            return new RankingDetailValue[size];
        }
    };
    private final String mIcon;
    private final String mId;
    private final String mName;

    public static final class Builder {
        private String mIcon;
        private String mId;
        private String mName;

        public Builder(RankingDetailValue src) {
            this.mId = src.getId();
            this.mName = src.getName();
            this.mIcon = src.getIcon();
        }

        public void setId(String id) {
            this.mId = id;
        }

        public void setName(String name) {
            this.mName = name;
        }

        public void setIcon(String icon) {
            this.mIcon = icon;
        }

        public RankingDetailValue build() {
            return new RankingDetailValue(this.mId, this.mName, this.mIcon);
        }
    }

    public RankingDetailValue(String id, String name, String icon) {
        this.mId = id;
        this.mName = name;
        this.mIcon = icon;
    }

    public RankingDetailValue(JSONObject json) {
        if (json != null) {
            this.mId = JSONUtil.getString(json, "id", null);
            this.mName = JSONUtil.getString(json, "name", null);
            this.mIcon = JSONUtil.getString(json, "icon", null);
            return;
        }
        this.mId = null;
        this.mName = null;
        this.mIcon = null;
    }

    public JSONObject toJSONObject() {
        JSONObject json = new JSONObject();
        try {
            json.put("id", this.mId);
            json.put("name", this.mName);
            json.put("icon", this.mIcon);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public String getId() {
        return this.mId;
    }

    public String getName() {
        return this.mName;
    }

    public String getIcon() {
        return this.mIcon;
    }

    public int describeContents() {
        return 0;
    }

    private RankingDetailValue(Parcel source) {
        this.mId = source.readString();
        this.mName = source.readString();
        this.mIcon = source.readString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mId);
        dest.writeString(this.mName);
        dest.writeString(this.mIcon);
    }

    public boolean equals(Object o) {
        if (o instanceof RankingDetailValue) {
            return TextUtils.equals(((RankingDetailValue) o).mId, this.mId);
        }
        return super.equals(o);
    }

    public int hashCode() {
        return this.mId.hashCode();
    }
}
