package com.kayac.lobi.libnakamap.value;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.kayac.lobi.libnakamap.utils.JSONUtil;
import org.json.JSONObject;

public class AuthorizedAppValue implements Parcelable {
    public static final Creator<AuthorizedAppValue> CREATOR = new Creator<AuthorizedAppValue>() {
        public AuthorizedAppValue createFromParcel(Parcel in) {
            return new AuthorizedAppValue(in);
        }

        public AuthorizedAppValue[] newArray(int size) {
            return new AuthorizedAppValue[size];
        }
    };
    public static final String JSON_KEY_ADID = "ad_id";
    public static final String JSON_KEY_COMPANY = "company";
    public static final String JSON_KEY_DESCRIPTION = "description";
    public static final String JSON_KEY_ICON = "icon";
    public static final String JSON_KEY_LINKURL = "link_url";
    public static final String JSON_KEY_NAME = "name";
    public static final String JSON_KEY_PACKAGE = "package";
    private final String mAdId;
    private final String mCompany;
    private final String mDescription;
    private final String mIcon;
    private final String mLinkUri;
    private final String mName;
    private final String mPackage;

    public static final class Builder {
        private String mAdId;
        private String mCompany;
        private String mDescription;
        private String mIcon;
        private String mLinkUri;
        private String mName;
        private String mPackage;

        public Builder(AuthorizedAppValue src) {
            this.mName = src.getName();
            this.mIcon = src.getIcon();
            this.mPackage = src.getPackage();
            this.mLinkUri = src.getLinkUri();
            this.mCompany = src.getCompany();
            this.mDescription = src.getDescription();
            this.mAdId = src.getAdId();
        }

        public void setName(String name) {
            this.mName = name;
        }

        public void setIcon(String icon) {
            this.mIcon = icon;
        }

        public void setPackage(String _packaeg) {
            this.mPackage = _packaeg;
        }

        public void setLinkUri(String linkUri) {
            this.mLinkUri = linkUri;
        }

        public void setCompany(String company) {
            this.mCompany = company;
        }

        public void setDescription(String description) {
            this.mDescription = description;
        }

        public void setAdId(String adId) {
            this.mAdId = adId;
        }

        public AuthorizedAppValue build() {
            return new AuthorizedAppValue(this.mName, this.mIcon, this.mPackage, this.mLinkUri, this.mCompany, this.mDescription, this.mAdId);
        }
    }

    public AuthorizedAppValue(String name, String icon, String packageName, String linkUri, String company, String description, String adId) {
        this.mName = name;
        this.mIcon = icon;
        this.mPackage = packageName;
        this.mLinkUri = linkUri;
        this.mCompany = company;
        this.mDescription = description;
        this.mAdId = adId;
    }

    public AuthorizedAppValue(JSONObject json) {
        this.mName = JSONUtil.getString(json, "name", null);
        this.mIcon = JSONUtil.getString(json, "icon", null);
        this.mPackage = JSONUtil.getString(json, JSON_KEY_PACKAGE, null);
        this.mLinkUri = JSONUtil.getString(json, JSON_KEY_LINKURL, null);
        this.mCompany = JSONUtil.getString(json, JSON_KEY_COMPANY, null);
        this.mDescription = JSONUtil.getString(json, "description", null);
        this.mAdId = JSONUtil.getString(json, "ad_id", null);
    }

    public String getName() {
        return this.mName;
    }

    public String getIcon() {
        return this.mIcon;
    }

    public String getPackage() {
        return this.mPackage;
    }

    public String getLinkUri() {
        return this.mLinkUri;
    }

    public String getCompany() {
        return this.mCompany;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public String getAdId() {
        return this.mAdId;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.mName);
        out.writeString(this.mIcon);
        out.writeString(this.mPackage);
        out.writeString(this.mLinkUri);
        out.writeString(this.mCompany);
        out.writeString(this.mDescription);
        out.writeString(this.mAdId);
    }

    private AuthorizedAppValue(Parcel in) {
        this.mName = in.readString();
        this.mIcon = in.readString();
        this.mPackage = in.readString();
        this.mLinkUri = in.readString();
        this.mCompany = in.readString();
        this.mDescription = in.readString();
        this.mAdId = in.readString();
    }
}
