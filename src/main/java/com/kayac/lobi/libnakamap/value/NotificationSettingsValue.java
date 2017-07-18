package com.kayac.lobi.libnakamap.value;

import com.adjust.sdk.Constants;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONObject;

public class NotificationSettingsValue {
    private final ArrayList<SettingValue> mSettngs;
    private final UserValue mUser;

    public static class SettingValue {
        public static final int DISABLED = 0;
        public static final int ENABLED = 1;
        public static final int FORCED_ENABLED = 2;
        private final String mKey;
        private final int mNotification;
        private final int mPush;

        private SettingValue(String key, JSONObject json) {
            this.mKey = key;
            this.mNotification = json.optInt("notification", 0);
            this.mPush = json.optInt(Constants.PUSH, 0);
        }

        public static SettingValue newInstance(JSONObject json, String key) {
            JSONObject jsonSetting = json.optJSONObject(key);
            if (jsonSetting != null) {
                return new SettingValue(key, jsonSetting);
            }
            return null;
        }

        public String getKey() {
            return this.mKey;
        }

        public int getNotification() {
            return this.mNotification;
        }

        public int getPush() {
            return this.mPush;
        }
    }

    public NotificationSettingsValue(JSONObject json) {
        Iterator<String> iter = json.keys();
        JSONObject jsonUser = json.optJSONObject("user");
        this.mUser = jsonUser != null ? new UserValue(jsonUser) : null;
        this.mSettngs = new ArrayList();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            if (!"user".equals(key)) {
                this.mSettngs.add(SettingValue.newInstance(json, key));
            }
        }
    }

    public UserValue getUser() {
        return this.mUser;
    }

    public ArrayList<SettingValue> getSettings() {
        return this.mSettngs;
    }
}
