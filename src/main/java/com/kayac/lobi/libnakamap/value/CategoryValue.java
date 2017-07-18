package com.kayac.lobi.libnakamap.value;

import com.kayac.lobi.libnakamap.components.LoginEntranceDialog;
import com.kayac.lobi.libnakamap.utils.JSONUtil;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class CategoryValue {
    public static final String TYPE_PRIVATE = "private";
    public static final String TYPE_PUBLIC = "public";
    private final List<GroupDetailValue> mGroupDetails = new ArrayList();
    private final String mTitle;
    private final String mType;

    public static final class Builder {
        private List<GroupDetailValue> mGroupDetails = new ArrayList();
        private String mTitle;
        private String mType;

        public Builder(CategoryValue src) {
            this.mType = src.getType();
            this.mTitle = src.getTitle();
            this.mGroupDetails = src.getGroupDetails();
        }

        public void setType(String type) {
            this.mType = type;
        }

        public void setTitle(String title) {
            this.mTitle = title;
        }

        public void setGroupDetails(List<GroupDetailValue> groupDetails) {
            this.mGroupDetails = groupDetails;
        }

        public CategoryValue build() {
            return new CategoryValue(this.mType, this.mTitle, this.mGroupDetails);
        }
    }

    public CategoryValue(String type, String title, List<GroupDetailValue> groupDetails) {
        this.mType = type;
        this.mTitle = title;
        this.mGroupDetails.addAll(groupDetails);
    }

    public CategoryValue(JSONObject json, String type) {
        this.mTitle = JSONUtil.getString(json, LoginEntranceDialog.ARGUMENTS_TITLE, null);
        JSONArray ary = json.optJSONArray("items");
        if (ary != null) {
            for (int i = 0; i < ary.length(); i++) {
                JSONObject obj = ary.optJSONObject(i);
                if (obj != null) {
                    this.mGroupDetails.add(new GroupDetailValue(obj));
                }
            }
        }
        this.mType = type;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public String getType() {
        return this.mType;
    }

    public List<GroupDetailValue> getGroupDetails() {
        return this.mGroupDetails;
    }
}
