package cn.sharesdk.onekeyshare;

import cn.sharesdk.onekeyshare.themes.classic.ClassicTheme;

public enum OnekeyShareTheme {
    CLASSIC(0, new ClassicTheme());
    
    private final OnekeyShareThemeImpl impl;
    private final int value;

    private OnekeyShareTheme(int value, OnekeyShareThemeImpl impl) {
        this.value = value;
        this.impl = impl;
    }

    public int getValue() {
        return this.value;
    }

    public OnekeyShareThemeImpl getImpl() {
        return this.impl;
    }

    public static OnekeyShareTheme fromValue(int value) {
        for (OnekeyShareTheme theme : values()) {
            if (theme.value == value) {
                return theme;
            }
        }
        return CLASSIC;
    }
}
