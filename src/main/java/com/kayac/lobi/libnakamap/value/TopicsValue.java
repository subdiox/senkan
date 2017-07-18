package com.kayac.lobi.libnakamap.value;

import com.kayac.lobi.libnakamap.components.LoginEntranceDialog;
import com.kayac.lobi.libnakamap.utils.JSONUtil;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class TopicsValue {
    public static final String AD_COMMUNITIES = "ad_communities";
    public static final String GROUP = "group";
    public static final String GROUPS = "groups";
    private final String mDescription;
    private final List<GroupDetailValue> mGroups = new ArrayList();
    private final List<String> mIcons = new ArrayList();
    private final String mId;
    private final String mTitle;
    private final String mType;

    public static final class Builder {
        private String mDescription;
        private List<GroupDetailValue> mGroups = new ArrayList();
        private List<String> mIcons = new ArrayList();
        private String mId;
        private String mTitle;
        private String mType;

        public Builder(TopicsValue src) {
            this.mType = src.getType();
            this.mId = src.getId();
            this.mTitle = src.getTtile();
            this.mIcons = src.getIcons();
            this.mDescription = src.getDescription();
            this.mGroups = src.getGroups();
        }

        public void setType(String type) {
            this.mType = type;
        }

        public void setId(String id) {
            this.mId = id;
        }

        public void setTitlee(String title) {
            this.mTitle = title;
        }

        public void setIcons(List<String> icons) {
            this.mIcons = icons;
        }

        public void setDescription(String desc) {
            this.mDescription = desc;
        }

        public void setGroups(List<GroupDetailValue> groups) {
            this.mGroups = groups;
        }
    }

    public TopicsValue(String type, String id, String title, List<String> icons, String desc, List<GroupDetailValue> groups) {
        this.mType = type;
        this.mId = id;
        this.mTitle = title;
        this.mIcons.addAll(icons);
        this.mDescription = desc;
        this.mGroups.addAll(groups);
    }

    public TopicsValue(JSONObject json) {
        int len;
        int i;
        this.mType = JSONUtil.getString(json, "type", null);
        this.mId = JSONUtil.getString(json, "id", null);
        this.mTitle = JSONUtil.getString(json, LoginEntranceDialog.ARGUMENTS_TITLE, null);
        JSONArray ary = json.optJSONArray("icons");
        if (ary != null) {
            len = ary.length();
            for (i = 0; i < len; i++) {
                this.mIcons.add(ary.optString(i));
            }
        }
        this.mDescription = JSONUtil.getString(json, "description", null);
        JSONArray aryItems = json.optJSONArray("items");
        if (aryItems != null) {
            len = aryItems.length();
            for (i = 0; i < len; i++) {
                JSONObject obj = aryItems.optJSONObject(i);
                if (obj != null) {
                    this.mGroups.add(new GroupDetailValue(obj));
                }
            }
        }
    }

    public String getType() {
        return this.mType;
    }

    public String getId() {
        return this.mId;
    }

    public String getTtile() {
        return this.mTitle;
    }

    public List<String> getIcons() {
        return this.mIcons;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public List<GroupDetailValue> getGroups() {
        return this.mGroups;
    }
}
