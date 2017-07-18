package com.kayac.lobi.sdk.migration.datastore;

import android.content.Context;
import android.text.TextUtils;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.utils.Log;
import com.kayac.lobi.libnakamap.utils.NakamapSDKDatastore;

public class UserMigrationHelper {
    public static final void init(Context context) {
        NakamapDatastore.init(context);
    }

    public static boolean hasOldUserAccount() {
        String installId = (String) NakamapDatastore.getValue("installId");
        Log.v("nakamap-sdk", "hasOldUserAccount");
        Log.v("nakamap-sdk", "  installId: " + installId);
        return !TextUtils.isEmpty(installId);
    }

    public static void migrateDatabase() {
        AccountDatastore.deleteAll();
        Boolean ssoBound = (Boolean) NakamapDatastore.getValue("ssoBound", Boolean.FALSE);
        AccountDatastore.setValue("installId", (String) NakamapDatastore.getValue("installId"));
        AccountDatastore.setValue("ssoBound", ssoBound);
    }

    public static final void deleteOldDatabase() {
        NakamapDatastore.deleteAll();
    }

    public static final String getInstallId() {
        String installId = (String) NakamapDatastore.getValue("installId");
        if (TextUtils.isEmpty(installId)) {
            return NakamapSDKDatastore.getInstallId();
        }
        return installId;
    }
}
