package com.kayac.lobi.sdk.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastore;
import com.kayac.lobi.libnakamap.utils.AdUtil;
import com.kayac.lobi.libnakamap.utils.Log;
import com.kayac.lobi.libnakamap.value.AdWaitingAppValue;
import com.kayac.lobi.sdk.LobiCore;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Executors;

public class AppInstallReceiver extends BroadcastReceiver {
    private static final String TAG = AppInstallReceiver.class.getSimpleName();

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
            Log.d(TAG, "installed: update");
            return;
        }
        LobiCore.setup(context);
        if ("android.intent.action.PACKAGE_ADDED".equals(action)) {
            String packageUri = intent.getData().toString();
            if (packageUri != null) {
                String packageName = getPackageName(packageUri);
                Log.d(TAG, "installed: " + packageName);
                Iterator it = TransactionDatastore.getAdWaitingApp(packageName).iterator();
                while (it.hasNext()) {
                    AdWaitingAppValue app = (AdWaitingAppValue) it.next();
                    finishInstallation(app.getAdId(), app.getPackage(), app.getCountConversion());
                }
            }
        }
    }

    private static String getPackageName(String packageUri) {
        return packageUri.substring("package:".length());
    }

    public static void startInstallation(String adId, String packageName, String clientId, boolean countConversion) {
        if (!AdUtil.isInstalled(packageName)) {
            TransactionDatastore.setAdWaitingApp(adId, packageName, clientId, countConversion);
            Log.d(TAG, "start waiting: " + packageName + " " + (countConversion ? "CONVERSION" : "ANALYTICS"));
        }
    }

    public static void finishInstallation(final String adId, String packageName, final boolean countConversion) {
        Log.d(TAG, "finish waiting: " + adId);
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            public void run() {
                if (countConversion) {
                    AdUtil.countUpConversion(adId);
                } else {
                    AppInstallReceiver.stopInstallation(adId);
                }
            }
        });
    }

    public static void stopInstallation(String adId) {
        TransactionDatastore.deleteAdWaitingApp(adId);
        Log.d(TAG, "stop waiting: " + adId);
    }

    public static boolean isWaitingInstallation() {
        ArrayList<AdWaitingAppValue> apps = TransactionDatastore.getAdWaitingApp(null);
        return apps != null && apps.size() > 0;
    }
}
