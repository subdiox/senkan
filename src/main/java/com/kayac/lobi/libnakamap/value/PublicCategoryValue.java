package com.kayac.lobi.libnakamap.value;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.kayac.lobi.libnakamap.collection.Pair;
import com.kayac.lobi.libnakamap.components.LoginEntranceDialog;
import com.kayac.lobi.libnakamap.net.APIDef.PostAppData.RequestKey;
import com.kayac.lobi.libnakamap.utils.JSONUtil;
import com.kayac.lobi.libnakamap.value.AdNewAdValue.AdItemList;
import com.kayac.lobi.libnakamap.value.GroupInterface.GroupType;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class PublicCategoryValue implements GroupInterface, Parcelable {
    public static final Creator<PublicCategoryValue> CREATOR = new Creator<PublicCategoryValue>() {
        public PublicCategoryValue createFromParcel(Parcel source) {
            return new PublicCategoryValue(source);
        }

        public PublicCategoryValue[] newArray(int size) {
            return new PublicCategoryValue[size];
        }
    };
    public static final String TYPE_CATEGORY = "category";
    public static final String TYPE_GROUP = "group";
    public static final String TYPE_ROOT = "root";
    private static final byte _T_CATEGORY = (byte) 0;
    private static final byte _T_GROUP = (byte) 1;
    private final boolean mAuthorized;
    private final boolean mAuthorizedRoot;
    private final String mBackgroundImg;
    private final String mDescription;
    private String mIcon = null;
    private final String mId;
    private final List<Pair<String, GroupInterface>> mItems = new ArrayList();
    private final String mNextPage;
    private final String mParent;
    private final PermissionValue mPermission;
    private final RelatedAd mRelatedAd;
    private final String mTitle;
    private final String mType;

    public static class PermissionValue implements Parcelable {
        public static final Creator<PermissionValue> CREATOR = new Creator<PermissionValue>() {
            public PermissionValue createFromParcel(Parcel source) {
                return new PermissionValue(source);
            }

            public PermissionValue[] newArray(int size) {
                return new PermissionValue[size];
            }
        };
        public final boolean addGroup;

        public static final class Builder {
            private boolean mAddGroup;

            public Builder(GroupPermissionValue value) {
                this.mAddGroup = value.canUpdateIcon;
            }

            public void setAddGroup(boolean addGroup) {
                this.mAddGroup = addGroup;
            }

            public PermissionValue build() {
                return new PermissionValue(this.mAddGroup);
            }
        }

        public PermissionValue(JSONObject jsonObject) {
            this.addGroup = parseFlag(jsonObject, "add_group");
        }

        public PermissionValue(boolean addGroup) {
            this.addGroup = addGroup;
        }

        boolean parseFlag(JSONObject jsonObject, String key) {
            return "1".equals(jsonObject.optString(key, ""));
        }

        public int describeContents() {
            return 0;
        }

        private PermissionValue(Parcel source) {
            boolean[] permission = new boolean[1];
            source.readBooleanArray(permission);
            this.addGroup = permission[0];
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeBooleanArray(new boolean[]{this.addGroup});
        }
    }

    public static class RelatedAd implements Parcelable {
        public static final Creator<RelatedAd> CREATOR = new Creator<RelatedAd>() {
            public RelatedAd createFromParcel(Parcel source) {
                return new RelatedAd(source);
            }

            public RelatedAd[] newArray(int size) {
                return new RelatedAd[size];
            }
        };
        private String mCommunityAdId;

        public RelatedAd(JSONObject json) {
            if (!json.isNull(AdItemList.TYPE_PRIZE_GROUPS)) {
                this.mCommunityAdId = JSONUtil.getString(json.optJSONObject(AdItemList.TYPE_PRIZE_GROUPS), "ad_id", "");
            }
        }

        private RelatedAd(Parcel source) {
            this.mCommunityAdId = source.readString();
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.mCommunityAdId);
        }

        public int describeContents() {
            return 0;
        }

        public boolean hasCommunityAd() {
            return !TextUtils.isEmpty(this.mCommunityAdId);
        }
    }

    public PublicCategoryValue(String id, String title, String description, String type, String cursor, String parent, String backgroundImg, boolean authorizedRoot, boolean authorized, PermissionValue permission, RelatedAd ad, List<Pair<String, GroupInterface>> items) {
        this.mId = id;
        this.mTitle = title;
        this.mDescription = description;
        this.mType = type;
        this.mNextPage = cursor;
        this.mParent = parent;
        this.mBackgroundImg = backgroundImg;
        this.mAuthorizedRoot = authorizedRoot;
        this.mAuthorized = authorized;
        this.mPermission = permission;
        this.mRelatedAd = ad;
        this.mItems.addAll(items);
    }

    public PublicCategoryValue(JSONObject json) {
        JSONObject obj = json.optJSONObject(RequestKey.DATA);
        this.mType = JSONUtil.getString(json, "type", null);
        if (obj != null) {
            this.mId = JSONUtil.getString(obj, "id", "");
            this.mTitle = JSONUtil.getString(obj, LoginEntranceDialog.ARGUMENTS_TITLE, "");
            this.mDescription = JSONUtil.getString(obj, "description", "");
            this.mNextPage = JSONUtil.getString(obj, "next_page", "");
            this.mParent = JSONUtil.getString(obj, "parent", "");
            this.mBackgroundImg = JSONUtil.getString(obj, "background_img", "");
            this.mAuthorizedRoot = JSONUtil.getString(obj, "authorized_root", "0").equals("1");
            this.mAuthorized = JSONUtil.getString(obj, "authorized", "0").equals("1");
            JSONObject optJSONObject = obj.optJSONObject("can");
            if (optJSONObject == null) {
                optJSONObject = new JSONObject();
            }
            this.mPermission = new PermissionValue(optJSONObject);
            JSONObject optJSONObject2 = obj.optJSONObject(Host.AD);
            if (optJSONObject2 == null) {
                optJSONObject2 = new JSONObject();
            }
            this.mRelatedAd = new RelatedAd(optJSONObject2);
            JSONArray ary = obj.optJSONArray("items");
            if (ary != null) {
                int len = ary.length();
                for (int i = 0; i < len; i++) {
                    JSONObject o = ary.optJSONObject(i);
                    if (o != null) {
                        String t = JSONUtil.getString(o, "type", null);
                        if ("category".equals(t)) {
                            this.mItems.add(new Pair("category", new PublicCategoryValue(o)));
                        } else if ("group".equals(t)) {
                            JSONObject data = o.optJSONObject(RequestKey.DATA);
                            if (data != null) {
                                this.mItems.add(new Pair("group", new GroupDetailValue(data)));
                            }
                        }
                    }
                }
                return;
            }
            return;
        }
        this.mId = null;
        this.mTitle = null;
        this.mDescription = null;
        this.mNextPage = null;
        this.mParent = null;
        this.mBackgroundImg = null;
        this.mAuthorizedRoot = false;
        this.mAuthorized = false;
        this.mPermission = new PermissionValue(new JSONObject());
        this.mRelatedAd = new RelatedAd(new JSONObject());
    }

    public String getId() {
        return this.mId;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public String getType() {
        return this.mType;
    }

    public String getNextPage() {
        return this.mNextPage;
    }

    public String getParent() {
        return this.mParent;
    }

    public String getBackgroundImg() {
        return this.mBackgroundImg;
    }

    public boolean isAuthorizedRoot() {
        return this.mAuthorizedRoot;
    }

    public boolean isAuthorized() {
        return this.mAuthorized;
    }

    public PermissionValue getPermissions() {
        return this.mPermission;
    }

    public boolean hasPrize() {
        return this.mRelatedAd.hasCommunityAd();
    }

    public List<Pair<String, GroupInterface>> getItems() {
        return this.mItems;
    }

    public String getUid() {
        return this.mId;
    }

    public String getName() {
        return this.mTitle;
    }

    public GroupType getGroupType() {
        return GroupType.CATEGORY;
    }

    public PublicCategoryValue(Parcel source) {
        boolean z;
        boolean z2 = false;
        this.mId = source.readString();
        this.mTitle = source.readString();
        this.mDescription = source.readString();
        this.mType = source.readString();
        this.mNextPage = source.readString();
        this.mParent = source.readString();
        this.mBackgroundImg = source.readString();
        if (source.readByte() > (byte) 0) {
            z = true;
        } else {
            z = false;
        }
        this.mAuthorizedRoot = z;
        if (source.readByte() > (byte) 0) {
            z2 = true;
        }
        this.mAuthorized = z2;
        this.mPermission = (PermissionValue) source.readParcelable(PermissionValue.class.getClassLoader());
        this.mRelatedAd = (RelatedAd) source.readParcelable(RelatedAd.class.getClassLoader());
        int size = source.readInt();
        for (int i = 0; i < size; i++) {
            byte b = source.readByte();
            if (b == (byte) 0) {
                this.mItems.add(i, new Pair("category", (PublicCategoryValue) source.readParcelable(PublicCategoryValue.class.getClassLoader())));
            } else if (b == (byte) 1) {
                this.mItems.add(i, new Pair("group", (GroupDetailValue) source.readParcelable(GroupDetailValue.class.getClassLoader())));
            }
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        int i;
        dest.writeString(this.mId);
        dest.writeString(this.mTitle);
        dest.writeString(this.mDescription);
        dest.writeString(this.mType);
        dest.writeString(this.mNextPage);
        dest.writeString(this.mParent);
        dest.writeString(this.mBackgroundImg);
        if (this.mAuthorizedRoot) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeByte((byte) i);
        if (this.mAuthorized) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeByte((byte) i);
        dest.writeParcelable(this.mPermission, 0);
        dest.writeParcelable(this.mRelatedAd, 0);
        dest.writeInt(this.mItems.size());
        for (Pair<String, GroupInterface> item : this.mItems) {
            if ("category".equals(item.first)) {
                dest.writeByte((byte) 0);
                dest.writeParcelable((PublicCategoryValue) item.second, 0);
            } else if ("group".equals(item.first)) {
                dest.writeByte((byte) 1);
                dest.writeParcelable((GroupDetailValue) item.second, 0);
            }
        }
    }

    public String getIcon() {
        return this.mIcon;
    }
}
