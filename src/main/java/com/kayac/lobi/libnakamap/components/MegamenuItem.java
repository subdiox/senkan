package com.kayac.lobi.libnakamap.components;

import android.graphics.drawable.Drawable;

/* compiled from: MegamenuAdapter */
class MegamenuItem {
    private Drawable mIcon1;
    private Drawable mIcon2;
    private boolean mIsEnable = true;
    private boolean mIsFirstIcon = true;
    private String mTitle1;
    private String mTitle2;
    private MenuType mType;

    /* compiled from: MegamenuAdapter */
    public enum MenuType {
        GROUP_DETAIL,
        LIKES_CHAT,
        INVITE_FRIEND,
        MEMBER_LOCATION,
        SHOUT_SETTING,
        GROUP_SETTING
    }

    public MegamenuItem(MenuType type, Drawable icon1, Drawable icon2, String title1, String title2) {
        if (type == null) {
            throw new IllegalArgumentException("type is required param.");
        } else if (icon1 == null) {
            throw new IllegalArgumentException("icon1 is required param.");
        } else {
            this.mIcon1 = icon1;
            this.mIcon2 = icon2;
            this.mTitle1 = title1;
            this.mTitle2 = title2;
            this.mType = type;
        }
    }

    public Drawable getIcon1() {
        return this.mIcon1;
    }

    public Drawable getIcon2() {
        return this.mIcon2;
    }

    public String getTitle1() {
        return this.mTitle1;
    }

    public MenuType getType() {
        return this.mType;
    }

    public boolean isSwitchIconEnable() {
        return (this.mIcon1 == null || this.mIcon2 == null) ? false : true;
    }

    public boolean isSwitchTextEnable() {
        return (this.mTitle1 == null || this.mTitle2 == null) ? false : true;
    }

    public String getCurrentTitle() {
        if (isSwitchTextEnable()) {
            return this.mIsFirstIcon ? this.mTitle1 : this.mTitle2;
        } else {
            return this.mTitle1;
        }
    }

    public Drawable getCurrentIcon() {
        if (isSwitchIconEnable()) {
            return this.mIsFirstIcon ? this.mIcon1 : this.mIcon2;
        } else {
            return this.mIcon1;
        }
    }

    public void changeIconState() {
        if (this.mIcon2 != null) {
            this.mIsFirstIcon = !this.mIsFirstIcon;
        }
    }

    public boolean isEnable() {
        return this.mIsEnable;
    }

    public void setEnable(boolean enable) {
        this.mIsEnable = enable;
    }
}
