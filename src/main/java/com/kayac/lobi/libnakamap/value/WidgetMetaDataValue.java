package com.kayac.lobi.libnakamap.value;

public class WidgetMetaDataValue {
    private final int mAppWidgetId;
    private final String mGroupUid;
    private final String mToken;
    private final long mUpdateAt;
    private final String mUserUid;

    public static final class Builder {
        private int mAppWidgetId;
        private String mGroupUid;
        private String mToken;
        private long mUpdateAt;
        private String mUserUid;

        public Builder(WidgetMetaDataValue src) {
            this.mAppWidgetId = src.getAppWidgetId();
            this.mToken = src.getToken();
            this.mUserUid = src.getUserUid();
            this.mGroupUid = src.getGroupUid();
            this.mUpdateAt = src.getUpdateAt();
        }

        public void setAppWidgetId(int appWidgetId) {
            this.mAppWidgetId = appWidgetId;
        }

        public void setToken(String token) {
            this.mToken = token;
        }

        public void setUserUid(String userUid) {
            this.mUserUid = userUid;
        }

        public void setGroupUid(String groupUid) {
            this.mGroupUid = groupUid;
        }

        public void setUpdateAt(long updateAt) {
            this.mUpdateAt = updateAt;
        }

        public WidgetMetaDataValue build() {
            return new WidgetMetaDataValue(this.mAppWidgetId, this.mToken, this.mUserUid, this.mGroupUid, this.mUpdateAt);
        }
    }

    public WidgetMetaDataValue(int appWidgetId, String token, String userUid, String groupUid, long updateAt) {
        this.mAppWidgetId = appWidgetId;
        this.mToken = token;
        this.mGroupUid = groupUid;
        this.mUpdateAt = updateAt;
        this.mUserUid = userUid;
    }

    public int getAppWidgetId() {
        return this.mAppWidgetId;
    }

    public String getToken() {
        return this.mToken;
    }

    public String getGroupUid() {
        return this.mGroupUid;
    }

    public long getUpdateAt() {
        return this.mUpdateAt;
    }

    public String getUserUid() {
        return this.mUserUid;
    }
}
