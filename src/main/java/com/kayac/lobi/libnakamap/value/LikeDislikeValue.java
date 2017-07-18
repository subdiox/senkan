package com.kayac.lobi.libnakamap.value;

import com.kayac.lobi.libnakamap.utils.JSONUtil;
import org.json.JSONObject;

public class LikeDislikeValue {
    public static final String BOO = "boo";
    public static final String LIKE = "like";
    private boolean mIsCanceled;
    private final String mType;
    private final UserValue mUserValue;

    public static final class Builder {
        private String mType;
        private UserValue mUserValue;

        public Builder(LikeDislikeValue src) {
            this.mType = src.getType();
            this.mUserValue = src.getUserValue();
        }

        public Builder setType(String type) {
            this.mType = type;
            return this;
        }

        public Builder setUserValue(UserValue userValue) {
            this.mUserValue = userValue;
            return this;
        }

        public LikeDislikeValue build() {
            return new LikeDislikeValue(this.mType, this.mUserValue);
        }
    }

    public LikeDislikeValue(String type, UserValue userValue) {
        this.mType = type;
        this.mUserValue = userValue;
        this.mIsCanceled = false;
    }

    public LikeDislikeValue(JSONObject json) {
        this.mType = JSONUtil.getString(json, "type", LIKE);
        this.mUserValue = new UserValue(json.optJSONObject("user"));
    }

    public void setCanceled(boolean canceled) {
        this.mIsCanceled = canceled;
    }

    public boolean getIsCanceled() {
        return this.mIsCanceled;
    }

    public String getType() {
        return this.mType;
    }

    public UserValue getUserValue() {
        return this.mUserValue;
    }
}
