package com.yunva.im.sdk.lib.service;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.yunva.im.sdk.lib.YvLoginInit;
import com.yunva.im.sdk.lib.config.c;
import com.yunva.im.sdk.lib.dynamicload.utils.b;
import com.yunva.im.sdk.lib.utils.a;

public class VioceService extends Service {
    public static boolean a = false;
    private static final byte[] c = new byte[0];
    private final String b = "LibVioceService";
    private String d;
    private Object e;

    public IBinder onBind(Intent intent) {
        a.d("LibVioceService", "onBind...");
        try {
            Object a = a();
            return (IBinder) a.getClass().getDeclaredMethod("onBind", new Class[]{Service.class, Intent.class}).invoke(a, new Object[]{this, intent});
        } catch (Exception e) {
            a.b("LibVioceService", "dynamic load onBind failure.exception:" + e.getMessage());
            return null;
        }
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        a.d("LibVioceService", "onStartCommand..." + flags + ",stick= " + 1);
        a = true;
        if (YvLoginInit.context == null) {
            YvLoginInit.context = getApplicationContext();
        }
        Object a = a();
        try {
            a.getClass().getDeclaredMethod("onStartCommand", new Class[]{Service.class, Intent.class, Integer.TYPE, Integer.TYPE}).invoke(a, new Object[]{this, intent, Integer.valueOf(flags), Integer.valueOf(startId)});
        } catch (Exception e) {
            e.printStackTrace();
            a.b("LibVioceService", "dynamic load onStartCommand failure.exception:" + e.getMessage());
        }
        try {
            Intent intent2 = new Intent(this, VioceService.class);
            intent2.putExtra("isAlarm", true);
            ((AlarmManager) getSystemService("alarm")).set(0, System.currentTimeMillis() + 1800000, PendingIntent.getService(this, 0, intent2, 0));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public void onCreate() {
        super.onCreate();
        a.d("LibVioceService", "onCreate...");
    }

    public void onDestroy() {
        super.onDestroy();
        a = false;
        a.d("LibVioceService", "onDestroy...");
        try {
            Object a = a();
            a.getClass().getDeclaredMethod("onDestroy", new Class[]{Service.class}).invoke(a, new Object[]{this});
        } catch (Exception e) {
            e.printStackTrace();
            a.b("LibVioceService", "dynamic load onDestroy failure.exception:" + e.getMessage());
        }
    }

    @SuppressLint({"NewApi"})
    public Object a() {
        synchronized (c) {
            if (this.e == null) {
                if (c.b().a() == null) {
                    c.b().a(false);
                }
                if (c.b().a() != null) {
                    try {
                        this.e = b.a(getApplicationContext(), com.yunva.im.sdk.lib.utils.c.b, c.b().d()).loadClass("com.yunva.atp.service.VioceService").newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                        a.b("LibVioceService", " getService   failure.exception:" + e.getMessage());
                    }
                }
            }
        }
        return this.e;
    }
}
