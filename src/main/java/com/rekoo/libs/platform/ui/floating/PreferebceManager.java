package com.rekoo.libs.platform.ui.floating;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferebceManager {
    private Context mContext;

    public PreferebceManager(Context context) {
        this.mContext = context;
    }

    private SharedPreferences getSharedPreferences() {
        return this.mContext.getSharedPreferences(Config.PERFERENCE_NAME, 0);
    }

    private Editor getEditer() {
        return getSharedPreferences().edit();
    }

    public float getFloatX() {
        return getSharedPreferences().getFloat(Config.PREF_KEY_FLOAT_X, 0.0f);
    }

    public void setFloatX(float x) {
        Editor editor = getEditer();
        editor.putFloat(Config.PREF_KEY_FLOAT_X, x);
        editor.commit();
    }

    public float getFloatY() {
        return getSharedPreferences().getFloat(Config.PREF_KEY_FLOAT_Y, 0.0f);
    }

    public void setFloatY(float y) {
        Editor editor = getEditer();
        editor.putFloat(Config.PREF_KEY_FLOAT_Y, y);
        editor.commit();
    }

    public boolean onlyDisplayOnHome() {
        return getSharedPreferences().getBoolean(Config.PREF_KEY_DISPLAY_ON_HOME, true);
    }

    public void setDisplayOnHome(boolean onlyDisplayOnHome) {
        Editor editor = getEditer();
        editor.putBoolean(Config.PREF_KEY_DISPLAY_ON_HOME, onlyDisplayOnHome);
        editor.commit();
    }

    public boolean isDisplayRight() {
        return getSharedPreferences().getBoolean(Config.PREF_KEY_IS_RIGHT, false);
    }

    public void setDisplayRight(boolean isRight) {
        Editor editor = getEditer();
        editor.putBoolean(Config.PREF_KEY_IS_RIGHT, isRight);
        editor.commit();
    }
}
