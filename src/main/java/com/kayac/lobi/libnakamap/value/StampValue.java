package com.kayac.lobi.libnakamap.value;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.kayac.lobi.libnakamap.utils.JSONUtil;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class StampValue {
    private final String mCategory;
    private final String mIcon;
    private final List<Item> mItems = new ArrayList();
    private final String mName;

    public static final class Builder {
        private String mCategory;
        private String mIcon;
        private List<Item> mItems = new ArrayList();
        private String mName;

        public Builder(StampValue src) {
            this.mCategory = src.getCategory();
            this.mIcon = src.getIcon();
            this.mName = src.getName();
            this.mItems = src.getItems();
        }

        public void setCategory(String category) {
            this.mCategory = category;
        }

        public void setIcon(String icon) {
            this.mIcon = icon;
        }

        public void setName(String name) {
            this.mName = name;
        }

        public void setItems(List<Item> items) {
            this.mItems = items;
        }

        public StampValue build() {
            return new StampValue(this.mCategory, this.mIcon, this.mName, this.mItems);
        }
    }

    public static final class Item implements Parcelable {
        public static final Creator<Item> CREATOR = new Creator<Item>() {
            public Item createFromParcel(Parcel source) {
                return new Item(source);
            }

            public Item[] newArray(int size) {
                return new Item[size];
            }
        };
        public static final int STATE_DISABLE = 0;
        public static final int STATE_ENABLE = 1;
        private final int mHeight;
        private final String mImage;
        private final int mState;
        private final String mThumb;
        private final String mUid;
        private final int mWidth;

        public static final class Builder {
            private int mHeight;
            private String mImage;
            private int mState;
            private String mThumb;
            private String mUid;
            private int mWidth;

            public Builder(Item src) {
                this.mUid = src.getUid();
                this.mImage = src.getImage();
                this.mThumb = src.getThumb();
                this.mWidth = src.getWidth();
                this.mHeight = src.getHeight();
                this.mState = src.getState();
            }

            public void setUid(String uid) {
                this.mUid = uid;
            }

            public void setImage(String image) {
                this.mImage = image;
            }

            public void setThumb(String thumb) {
                this.mThumb = thumb;
            }

            public void setWidth(int width) {
                this.mWidth = width;
            }

            public void setHeight(int height) {
                this.mHeight = height;
            }

            public void setState(int state) {
                this.mState = state;
            }

            public Item build() {
                return new Item(this.mUid, this.mImage, this.mThumb, this.mWidth, this.mHeight, this.mState);
            }
        }

        public Item(String uid, String image, String thumb, int width, int height, int state) {
            this.mUid = uid;
            this.mImage = image;
            this.mThumb = thumb;
            this.mWidth = width;
            this.mHeight = height;
            this.mState = state;
        }

        public Item(JSONObject json) {
            this.mUid = JSONUtil.getString(json, "uid", null);
            this.mImage = JSONUtil.getString(json, "image", null);
            this.mThumb = JSONUtil.getString(json, "thumb", null);
            this.mWidth = Integer.parseInt(JSONUtil.getString(json, "width", "0"));
            this.mHeight = Integer.parseInt(JSONUtil.getString(json, "height", "0"));
            this.mState = Integer.parseInt(JSONUtil.getString(json, "state", "0"));
        }

        public String getUid() {
            return this.mUid;
        }

        public String getImage() {
            return this.mImage;
        }

        public int getState() {
            return this.mState;
        }

        public String getThumb() {
            return this.mThumb;
        }

        public int getWidth() {
            return this.mWidth;
        }

        public int getHeight() {
            return this.mHeight;
        }

        private Item(Parcel source) {
            this.mUid = source.readString();
            this.mImage = source.readString();
            this.mThumb = source.readString();
            this.mWidth = source.readInt();
            this.mHeight = source.readInt();
            this.mState = source.readInt();
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.mUid);
            dest.writeString(this.mImage);
            dest.writeString(this.mThumb);
            dest.writeInt(this.mWidth);
            dest.writeInt(this.mHeight);
            dest.writeInt(this.mState);
        }

        public boolean equals(Object o) {
            if (o instanceof Item) {
                return TextUtils.equals(((Item) o).mUid, this.mUid);
            }
            return super.equals(o);
        }

        public int hashCode() {
            return this.mUid.hashCode();
        }

        public int describeContents() {
            return 0;
        }
    }

    public StampValue(String category, String icon, String name, List<Item> items) {
        this.mCategory = category;
        this.mIcon = icon;
        this.mName = name;
        this.mItems.addAll(items);
    }

    public StampValue(JSONObject json) {
        this.mCategory = JSONUtil.getString(json, "category", null);
        this.mIcon = JSONUtil.getString(json, "icon", null);
        this.mName = JSONUtil.getString(json, "name", null);
        JSONArray ary = json.optJSONArray("items");
        if (ary != null) {
            int len = ary.length();
            for (int i = 0; i < len; i++) {
                JSONObject obj = ary.optJSONObject(i);
                if (obj != null) {
                    this.mItems.add(new Item(obj));
                }
            }
        }
    }

    public String getCategory() {
        return this.mCategory;
    }

    public String getIcon() {
        return this.mIcon;
    }

    public String getName() {
        return this.mName;
    }

    public List<Item> getItems() {
        return this.mItems;
    }
}
