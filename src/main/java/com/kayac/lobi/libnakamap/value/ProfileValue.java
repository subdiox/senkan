package com.kayac.lobi.libnakamap.value;

import com.kayac.lobi.libnakamap.utils.JSONUtil;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class ProfileValue {
    private final List<Banner> mBanners;
    private final String mCursor;
    private final List<GroupDetailValue> mGroupDetails;
    private final int mPostedVideosCount;
    private final JSONArray mRankings;
    private final UserValue mUserValue;
    private final JSONArray mVideos;

    public static class Banner {
        public final String appUrl;
        public final String background;
        public final String description;
        public final String icon;
        public final String webUrl;

        public Banner(JSONObject json) {
            if (json == null) {
                this.description = null;
                this.icon = null;
                this.background = null;
                this.webUrl = null;
                this.appUrl = null;
                return;
            }
            this.description = json.optString("description");
            this.icon = json.optString("icon");
            this.background = json.optString("background");
            this.webUrl = json.optString("web_url");
            this.appUrl = json.optString("app_url");
        }
    }

    public static final class Builder {
        private List<Banner> mBanners;
        private String mCursor;
        private List<GroupDetailValue> mGroupDetails;
        private int mPostedVideosCount;
        private JSONArray mRankings;
        private UserValue mUserValue;
        private JSONArray mVideos;

        public Builder(ProfileValue src) {
            this.mUserValue = src.getUserValue();
            this.mGroupDetails = src.getPublicGroups();
            this.mRankings = src.getRankings();
            this.mVideos = src.getVideos();
            this.mPostedVideosCount = src.getPostedVideosCount();
            this.mBanners = src.getBanners();
            this.mCursor = src.getCursor();
        }

        public Builder setUserValue(UserValue userValue) {
            this.mUserValue = userValue;
            return this;
        }

        public Builder setGroupDetails(List<GroupDetailValue> groupDetails) {
            this.mGroupDetails = groupDetails;
            return this;
        }

        public Builder setRankings(JSONArray rankings) {
            this.mRankings = rankings;
            return this;
        }

        public Builder setVideos(JSONArray videos) {
            this.mVideos = videos;
            return this;
        }

        public Builder setPostedVideosCount(int postedVideosCount) {
            this.mPostedVideosCount = postedVideosCount;
            return this;
        }

        public Builder setBanners(List<Banner> banners) {
            this.mBanners = banners;
            return this;
        }

        public Builder setCursor(String cursor) {
            this.mCursor = cursor;
            return this;
        }

        public ProfileValue build() {
            return new ProfileValue(this.mUserValue, this.mGroupDetails, this.mRankings, this.mVideos, this.mPostedVideosCount, this.mBanners, this.mCursor);
        }
    }

    public ProfileValue(UserValue user, List<GroupDetailValue> groupDetails, JSONArray rankings, JSONArray videos, int postedVideosCount, List<Banner> banners, String cursor) {
        this.mUserValue = user;
        this.mGroupDetails = groupDetails;
        this.mRankings = rankings;
        this.mVideos = videos;
        this.mPostedVideosCount = postedVideosCount;
        this.mBanners = banners;
        this.mCursor = cursor;
    }

    public ProfileValue(JSONObject json) {
        int i;
        this.mUserValue = new UserValue(json);
        List<GroupDetailValue> groupDetails = new ArrayList();
        JSONArray groupList = json.optJSONArray("public_groups");
        if (groupList != null) {
            int len = groupList.length();
            for (i = 0; i < len; i++) {
                JSONObject obj = groupList.optJSONObject(i);
                if (obj != null) {
                    JSONObject group = obj.optJSONObject("group");
                    if (group != null) {
                        groupDetails.add(new GroupDetailValue(group));
                    }
                }
            }
        }
        this.mGroupDetails = groupDetails;
        this.mRankings = json.optJSONArray("rankings");
        this.mVideos = json.optJSONArray("videos");
        this.mPostedVideosCount = json.optInt("posted_videos_count");
        this.mBanners = new ArrayList();
        JSONArray banners = json.optJSONArray("banner");
        if (banners != null) {
            for (i = 0; i < banners.length(); i++) {
                this.mBanners.add(new Banner(banners.optJSONObject(i)));
            }
        }
        this.mCursor = JSONUtil.getString(json, "public_groups_next_cursor", "0");
    }

    public UserValue getUserValue() {
        return this.mUserValue;
    }

    public List<GroupDetailValue> getPublicGroups() {
        return this.mGroupDetails;
    }

    public JSONArray getRankings() {
        return this.mRankings;
    }

    public JSONArray getVideos() {
        return this.mVideos;
    }

    public int getPostedVideosCount() {
        return this.mPostedVideosCount;
    }

    public List<Banner> getBanners() {
        return this.mBanners;
    }

    public String getCursor() {
        return this.mCursor;
    }
}
