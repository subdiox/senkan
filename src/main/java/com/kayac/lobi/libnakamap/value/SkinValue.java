package com.kayac.lobi.libnakamap.value;

import com.kayac.lobi.libnakamap.utils.JSONUtil;
import org.json.JSONObject;

public class SkinValue {
    private final String mBarTintColor;
    private final String mCode;
    private final String mDetail;
    private final String mImage;
    private final String mName;

    public static final class Builder {
        private String mBarTintColor;
        private String mCode;
        private String mDetail;
        private String mImage;
        private String mName;

        public Builder(SkinValue src) {
            this.mName = src.getName();
            this.mDetail = src.getDetail();
            this.mBarTintColor = src.getBarTintColor();
            this.mImage = src.getImage();
            this.mCode = src.getCode();
        }

        public void setName(String name) {
            this.mName = name;
        }

        public void setDetail(String detail) {
            this.mDetail = detail;
        }

        public void setBarTintColor(String barTintColor) {
            this.mBarTintColor = barTintColor;
        }

        public void setImage(String image) {
            this.mImage = image;
        }

        public void setCode(String code) {
            this.mCode = code;
        }

        public SkinValue build() {
            return new SkinValue(this.mName, this.mDetail, this.mBarTintColor, this.mImage, this.mCode);
        }
    }

    public SkinValue(String name, String detail, String barTintColor, String image, String code) {
        this.mName = name;
        this.mDetail = detail;
        this.mBarTintColor = barTintColor;
        this.mImage = image;
        this.mCode = code;
    }

    public SkinValue(JSONObject json) {
        this.mName = JSONUtil.getString(json, "name", null);
        this.mDetail = JSONUtil.getString(json, "detail", null);
        this.mBarTintColor = JSONUtil.getString(json, "barTintColor", null);
        this.mImage = JSONUtil.getString(json, "image", null);
        this.mCode = JSONUtil.getString(json, "code", null);
    }

    public String getName() {
        return this.mName;
    }

    public String getDetail() {
        return this.mDetail;
    }

    public String getBarTintColor() {
        return this.mBarTintColor;
    }

    public String getImage() {
        return this.mImage;
    }

    public String getCode() {
        return this.mCode;
    }
}
