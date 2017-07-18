package com.kayac.lobi.libnakamap.value;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.kayac.lobi.libnakamap.utils.JSONUtil;
import org.json.JSONObject;

public class StampStoreValue implements Parcelable {
    public static final Creator<StampStoreValue> CREATOR = new Creator<StampStoreValue>() {
        public StampStoreValue[] newArray(int size) {
            return new StampStoreValue[size];
        }

        public StampStoreValue createFromParcel(Parcel in) {
            return new StampStoreValue(in);
        }
    };
    public static final String NEW = "new";
    public static final int PURCHASE_TYPE_APPLICATION = 3;
    public static final int PURCHASE_TYPE_FREE = 2;
    public static final int PURCHASE_TYPE_PAID = 1;
    public static final String RECOMMENDED = "recommended";
    public static final int STATE_LOCKED = 0;
    public static final int STATE_UNLOCKED = 1;
    public static final String TYPE_BALOON = "baloon";
    public static final String TYPE_STAMP = "stamp";
    public static final String TYPE_STAMP_SET = "stamp_set";
    private final String mAndroidPrice;
    private final AppValue mApp;
    private final String mBannerImage;
    private final String mBannerUrl;
    private final String mCopyright;
    private final String mDescription;
    private final String mLargeIcon;
    private final String mName;
    private final String mProductId;
    private final long mPublisehdAt;
    private final String mPublisher;
    private final int mPurchaseType;
    private final String mSmallIcon;
    private final String mStampId;
    private final int mStampNum;
    private final int mState;
    private final String mThumb;
    private final String mType;
    private final String mUid;

    public static final class Builder {
        private String mAndroidPrice;
        private AppValue mApp;
        private String mBannerImage;
        private String mBannerUrl;
        private String mCopyright;
        private String mDescription;
        private String mLargeIcon;
        private String mName;
        private String mProductId;
        private long mPublishedAt;
        private String mPublisher;
        private int mPurchaseType;
        private String mSmallIcon;
        private String mStampId;
        private int mStampNum;
        private int mState;
        private String mThumb;
        private String mType;
        private String mUid;

        public Builder(StampStoreValue src) {
            this.mType = src.getType();
            this.mUid = src.getUid();
            this.mProductId = src.getProductId();
            this.mStampId = src.getStampId();
            this.mPurchaseType = src.getPurchaseType();
            this.mAndroidPrice = src.getAndroidPrice();
            this.mName = src.getName();
            this.mPublisher = src.getPublisher();
            this.mDescription = src.getDescription();
            this.mCopyright = src.getCopyright();
            this.mSmallIcon = src.getSmallIcon();
            this.mLargeIcon = src.getLargeIcon();
            this.mState = src.getState();
            this.mStampNum = src.getStampNum();
            this.mThumb = src.getThumb();
            this.mBannerImage = src.getBannerImage();
            this.mBannerUrl = src.getBannerUrl();
            this.mPublishedAt = src.getPublishedAt();
            this.mApp = src.getApp();
        }

        public void setType(String type) {
            this.mType = type;
        }

        public void setUid(String uid) {
            this.mUid = uid;
        }

        public void setProductId(String id) {
            this.mProductId = id;
        }

        public void setStampId(String id) {
            this.mStampId = id;
        }

        public void setPurchaseType(int purchaseType) {
            this.mPurchaseType = purchaseType;
        }

        public void setAndroidPrice(String androidPrice) {
            this.mAndroidPrice = androidPrice;
        }

        public void setName(String name) {
            this.mName = name;
        }

        public void setPublisher(String publisher) {
            this.mPublisher = publisher;
        }

        public void setDescription(String description) {
            this.mDescription = description;
        }

        public void setCopyright(String copyright) {
            this.mCopyright = copyright;
        }

        public void setSmallIcon(String smallIcon) {
            this.mSmallIcon = smallIcon;
        }

        public void setLargeIcon(String largeIcon) {
            this.mLargeIcon = largeIcon;
        }

        public void setState(int state) {
            this.mState = state;
        }

        public void setStampNum(int stampNum) {
            this.mStampNum = stampNum;
        }

        public void setThumb(String thumb) {
            this.mThumb = thumb;
        }

        public void setBannerImage(String image) {
            this.mBannerImage = image;
        }

        public void setBannerUrl(String url) {
            this.mBannerUrl = url;
        }

        public void setPublishedAt(long publishedAt) {
            this.mPublishedAt = publishedAt;
        }

        public void setApp(AppValue app) {
            this.mApp = app;
        }

        public StampStoreValue build() {
            return new StampStoreValue(this.mType, this.mUid, this.mProductId, this.mStampId, this.mPurchaseType, this.mAndroidPrice, this.mName, this.mPublisher, this.mDescription, this.mCopyright, this.mSmallIcon, this.mLargeIcon, this.mState, this.mStampNum, this.mThumb, this.mBannerImage, this.mBannerUrl, this.mPublishedAt, this.mApp);
        }
    }

    public StampStoreValue(String type, String uid, String productId, String stampId, int purchaseType, String androidPrice, String name, String publisher, String description, String copyright, String smallIcon, String largeIcon, int state, int stampNum, String thumb, String bannerImage, String bannerUrl, long publishedAt, AppValue app) {
        this.mType = type;
        this.mUid = uid;
        this.mProductId = productId;
        this.mStampId = stampId;
        this.mPurchaseType = purchaseType;
        this.mAndroidPrice = androidPrice;
        this.mName = name;
        this.mPublisher = publisher;
        this.mDescription = description;
        this.mCopyright = copyright;
        this.mSmallIcon = smallIcon;
        this.mLargeIcon = largeIcon;
        this.mState = state;
        this.mStampNum = stampNum;
        this.mThumb = thumb;
        this.mBannerImage = bannerImage;
        this.mBannerUrl = bannerUrl;
        this.mPublisehdAt = publishedAt;
        this.mApp = app;
    }

    public StampStoreValue(JSONObject json) {
        this.mType = JSONUtil.getString(json, "type", null);
        this.mUid = JSONUtil.getString(json, "uid", null);
        this.mProductId = JSONUtil.getString(json, "product_id", null);
        this.mStampId = JSONUtil.getString(json, "id", null);
        this.mPurchaseType = Integer.parseInt(JSONUtil.getString(json, "purchase_type", null));
        JSONObject obj = json.optJSONObject("price");
        if (obj != null) {
            this.mAndroidPrice = JSONUtil.getString(obj, "android", null);
        } else {
            this.mAndroidPrice = null;
        }
        this.mName = JSONUtil.getString(json, "name", null);
        this.mPublisher = JSONUtil.getString(json, "publisher", null);
        this.mDescription = JSONUtil.getString(json, "description", null);
        this.mCopyright = JSONUtil.getString(json, "copyright", null);
        this.mSmallIcon = JSONUtil.getString(json, "small_icon", null);
        this.mLargeIcon = JSONUtil.getString(json, "large_icon", null);
        this.mState = Integer.parseInt(JSONUtil.getString(json, "state", null));
        this.mStampNum = Integer.parseInt(JSONUtil.getString(json, "num", null));
        this.mThumb = JSONUtil.getString(json, "all_thumb", null);
        this.mBannerImage = null;
        this.mBannerUrl = null;
        this.mPublisehdAt = Long.parseLong(JSONUtil.getString(json, "published_at", "0")) * 1000;
        JSONObject app = json.optJSONObject("app");
        if (app != null) {
            this.mApp = new AppValue(app);
        } else {
            this.mApp = null;
        }
    }

    public String getType() {
        return this.mType;
    }

    public String getUid() {
        return this.mUid;
    }

    public String getProductId() {
        return this.mProductId;
    }

    public String getStampId() {
        return this.mStampId;
    }

    public int getPurchaseType() {
        return this.mPurchaseType;
    }

    public String getAndroidPrice() {
        return this.mAndroidPrice;
    }

    public String getName() {
        return this.mName;
    }

    public String getPublisher() {
        return this.mPublisher;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public String getCopyright() {
        return this.mCopyright;
    }

    public String getSmallIcon() {
        return this.mSmallIcon;
    }

    public String getLargeIcon() {
        return this.mLargeIcon;
    }

    public int getState() {
        return this.mState;
    }

    public int getStampNum() {
        return this.mStampNum;
    }

    public String getThumb() {
        return this.mThumb;
    }

    public String getBannerImage() {
        return this.mBannerImage;
    }

    public String getBannerUrl() {
        return this.mBannerUrl;
    }

    public long getPublishedAt() {
        return this.mPublisehdAt;
    }

    public AppValue getApp() {
        return this.mApp;
    }

    public boolean equals(Object o) {
        if (!(o instanceof StampStoreValue)) {
            return super.equals(o);
        }
        return TextUtils.equals(this.mUid, ((StampStoreValue) o).getUid());
    }

    public int hashCode() {
        return this.mUid.hashCode();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.mType);
        out.writeString(this.mUid);
        out.writeString(this.mProductId);
        out.writeString(this.mStampId);
        out.writeInt(this.mPurchaseType);
        out.writeString(this.mAndroidPrice);
        out.writeString(this.mName);
        out.writeString(this.mPublisher);
        out.writeString(this.mDescription);
        out.writeString(this.mCopyright);
        out.writeString(this.mSmallIcon);
        out.writeString(this.mLargeIcon);
        out.writeInt(this.mState);
        out.writeInt(this.mStampNum);
        out.writeString(this.mThumb);
        out.writeString(this.mBannerImage);
        out.writeString(this.mBannerUrl);
        out.writeLong(this.mPublisehdAt);
        out.writeParcelable(this.mApp, flags);
    }

    private StampStoreValue(Parcel in) {
        this.mType = in.readString();
        this.mUid = in.readString();
        this.mProductId = in.readString();
        this.mStampId = in.readString();
        this.mPurchaseType = in.readInt();
        this.mAndroidPrice = in.readString();
        this.mName = in.readString();
        this.mPublisher = in.readString();
        this.mDescription = in.readString();
        this.mCopyright = in.readString();
        this.mSmallIcon = in.readString();
        this.mLargeIcon = in.readString();
        this.mState = in.readInt();
        this.mStampNum = in.readInt();
        this.mThumb = in.readString();
        this.mBannerImage = in.readString();
        this.mBannerUrl = in.readString();
        this.mPublisehdAt = in.readLong();
        this.mApp = (AppValue) in.readParcelable(AppValue.class.getClassLoader());
    }
}
