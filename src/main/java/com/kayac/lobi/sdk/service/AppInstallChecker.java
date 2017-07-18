package com.kayac.lobi.sdk.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import com.kayac.lobi.libnakamap.utils.AdUtil;
import com.kayac.lobi.sdk.LobiCore;
import java.util.concurrent.Executors;

public class AppInstallChecker extends Service {
    public static final String INSTALL_CHECK = (AppInstallChecker.class.getCanonicalName() + ".INSTALL_CHECK");

    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }
        String action = intent.getAction();
        Log.v("lobi-sdk", "[inline] install check " + action);
        if (LobiCore.isSetup()) {
            if (INSTALL_CHECK.equals(action)) {
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    public void run() {
                        AdUtil.checkAllInstallations(2592000000L);
                    }
                });
            }
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }
}
