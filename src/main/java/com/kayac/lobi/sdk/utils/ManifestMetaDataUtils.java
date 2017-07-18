package com.kayac.lobi.sdk.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;

public class ManifestMetaDataUtils {
    public static final String ACCOUNT_BASE_NAME = "com.kayac.lobi.sdk.accountBaseName";
    public static final String BIND_AFTER_POSTING_VIDEO = "com.kayac.lobi.sdk.bindAfterPostingVideo";
    public static final String CLIENT_ID = "com.kayac.lobi.sdk.clientId";
    public static final String USE_LOBI_RECORDER_SWITCH = "com.kayac.lobi.sdk.useLobiRecorderSwitch";

    private static ApplicationInfo fetchApplicationInfo(Context context) {
        ApplicationInfo ai = null;
        try {
            ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return ai;
    }

    public static String getString(Context context, String name) {
        return getString(context, name, null);
    }

    public static String getString(Context context, String name, String opt) {
        ApplicationInfo ai = fetchApplicationInfo(context);
        if (ai == null) {
            return null;
        }
        String value = (String) ai.metaData.get(name);
        return value != null ? value : opt;
    }

    public static boolean getBoolean(Context context, String name, boolean opt) {
        ApplicationInfo ai = fetchApplicationInfo(context);
        return ai == null ? opt : ai.metaData.getBoolean(name, opt);
    }
}
