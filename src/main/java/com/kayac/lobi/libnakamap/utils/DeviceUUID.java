package com.kayac.lobi.libnakamap.utils;

import android.content.Context;
import android.text.TextUtils;

public class DeviceUUID {
    static final String UUID = "uuid";
    static final String UUID_PREFS = "uuid_prefs";

    public interface OnGetUUID {
        void onGetUUID(String str);
    }

    public static void getUUID(final Context context, final OnGetUUID onGetUUID) {
        String uuid = context.getSharedPreferences(UUID_PREFS, 0).getString("uuid", null);
        if (TextUtils.isEmpty(uuid)) {
            getImplForApiLevelFrom5(context, new OnGetUUID() {
                public void onGetUUID(String uuid) {
                    DeviceUUID.saveToSharedPrefs(context, uuid);
                    onGetUUID.onGetUUID(uuid);
                }
            });
            return;
        }
        Log.v("hash", "uuid: " + uuid);
        onGetUUID.onGetUUID(uuid);
    }

    private static void getImplForApiLevelFrom5(Context context, OnGetUUID onGetUUID) {
        new DeviceUUIDIml(context, onGetUUID).getUUID();
    }

    private static void saveToSharedPrefs(Context context, String uuid) {
        context.getSharedPreferences(UUID_PREFS, 0).edit().putString("uuid", uuid).commit();
    }
}
