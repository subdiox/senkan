package com.mob.commons.deviceinfo;

import android.content.Context;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import com.kayac.lobi.libnakamap.net.APIDef.PostAppData.RequestKey;
import com.mob.commons.a;
import com.mob.commons.g;
import com.mob.commons.k;
import com.mob.tools.MobHandlerThread;
import com.mob.tools.utils.Data;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.Hashon;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

public class DeviceInfoCollector implements Callback {
    private static DeviceInfoCollector a;
    private Context b;
    private DeviceHelper c;
    private Hashon d = new Hashon();
    private Handler e;
    private Random f = new Random();

    private DeviceInfoCollector(Context context) {
        this.b = context.getApplicationContext();
        this.c = DeviceHelper.getInstance(context);
    }

    private void a() {
        MobHandlerThread aVar = new a(this);
        aVar.start();
        this.e = new Handler(aVar.getLooper(), this);
        this.e.sendEmptyMessage(1);
        this.e.sendEmptyMessage(2);
        this.e.sendEmptyMessage(3);
        this.e.sendEmptyMessage(5);
    }

    private void a(Location location, int i) {
        if (location != null) {
            HashMap hashMap = new HashMap();
            hashMap.put("accuracy", Float.valueOf(location.getAccuracy()));
            hashMap.put("latitude", Double.valueOf(location.getLatitude()));
            hashMap.put("longitude", Double.valueOf(location.getLongitude()));
            hashMap.put("location_type", Integer.valueOf(i));
            HashMap hashMap2 = new HashMap();
            hashMap2.put("type", "LOCATION");
            hashMap2.put(RequestKey.DATA, hashMap);
            hashMap2.put("datetime", Long.valueOf(a.b(this.b)));
            g.a(this.b).a(hashMap2);
        }
    }

    private void b() {
        HashMap hashMap = new HashMap();
        hashMap.put("phonename", this.c.getBluetoothName());
        hashMap.put("signmd5", this.c.getSignMD5());
        if ("wifi".equals(this.c.getDetailNetworkTypeForStatic())) {
            hashMap.put("ssid", this.c.getSSID());
            hashMap.put("bssid", this.c.getBssid());
        }
        String MD5 = Data.MD5(this.d.fromHashMap(hashMap));
        String a = k.a(this.b);
        if (a == null || !a.equals(MD5)) {
            HashMap hashMap2 = new HashMap();
            hashMap2.put("type", "DEVEXT");
            hashMap2.put(RequestKey.DATA, hashMap);
            hashMap2.put("datetime", Long.valueOf(a.b(this.b)));
            g.a(this.b).a(hashMap2);
            k.a(this.b, MD5);
        }
    }

    private boolean c() {
        long b = k.b(this.b);
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(b);
        int i = instance.get(1);
        int i2 = instance.get(2);
        int i3 = instance.get(5);
        long b2 = a.b(this.b);
        Calendar instance2 = Calendar.getInstance();
        instance2.setTimeInMillis(b2);
        return (i == instance2.get(1) && i2 == instance2.get(2) && i3 == instance2.get(5)) ? false : true;
    }

    private void d() {
        synchronized (a) {
            HashMap hashMap = new HashMap();
            hashMap.put("ssid", this.c.getSSID());
            hashMap.put("bssid", this.c.getBssid());
            HashMap hashMap2 = new HashMap();
            hashMap2.put("type", "WIFI_INFO");
            hashMap2.put(RequestKey.DATA, hashMap);
            long b = a.b(this.b);
            hashMap2.put("datetime", Long.valueOf(b));
            g.a(this.b).a(hashMap2);
            k.a(this.b, b);
            k.b(this.b, Data.MD5(this.d.fromHashMap(hashMap)));
        }
    }

    private void e() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        this.b.registerReceiver(new c(this), intentFilter);
    }

    private void f() {
        HashMap hashMap = new HashMap();
        int i = -1;
        try {
            i = Integer.parseInt(this.c.getCarrier());
        } catch (Throwable th) {
        }
        hashMap.put("carrier", Integer.valueOf(i));
        hashMap.put("simopname", this.c.getCarrierName());
        hashMap.put("lac", Integer.valueOf(this.c.getCellLac()));
        hashMap.put("cell", Integer.valueOf(this.c.getCellId()));
        HashMap hashMap2 = new HashMap();
        hashMap2.put("type", "BSINFO");
        hashMap2.put(RequestKey.DATA, hashMap);
        hashMap2.put("datetime", Long.valueOf(a.b(this.b)));
        g.a(this.b).a(hashMap2);
        k.c(this.b, Data.MD5(this.d.fromHashMap(hashMap)));
        k.b(this.b, a.b(this.b) + (((long) a.j(this.b)) * 1000));
    }

    private boolean g() {
        HashMap hashMap = new HashMap();
        int i = -1;
        try {
            i = Integer.parseInt(this.c.getCarrier());
        } catch (Throwable th) {
        }
        hashMap.put("carrier", Integer.valueOf(i));
        hashMap.put("simopname", this.c.getCarrierName());
        hashMap.put("lac", Integer.valueOf(this.c.getCellLac()));
        hashMap.put("cell", Integer.valueOf(this.c.getCellId()));
        String MD5 = Data.MD5(this.d.fromHashMap(hashMap));
        String d = k.d(this.b);
        return d == null || !d.equals(MD5);
    }

    public static synchronized void startCollector(Context context) {
        synchronized (DeviceInfoCollector.class) {
            if (a == null) {
                a = new DeviceInfoCollector(context);
                a.a();
            }
        }
    }

    public boolean handleMessage(Message message) {
        switch (message.what) {
            case 1:
                if (a.h(this.b)) {
                    b();
                    break;
                }
                break;
            case 2:
                if (a.m(this.b)) {
                    if (c()) {
                        d();
                    }
                    e();
                    break;
                }
                break;
            case 3:
                if (a.i(this.b)) {
                    f();
                }
                this.e.sendEmptyMessageDelayed(4, (long) ((this.f.nextInt(120) + 180) * 1000));
                break;
            case 4:
                if (a.i(this.b) && (a.b(this.b) + (((long) a.j(this.b)) * 1000) < a.b(this.b) || g())) {
                    f();
                }
                this.e.sendEmptyMessageDelayed(4, (long) ((this.f.nextInt(120) + 180) * 1000));
                break;
            case 5:
                if (a.k(this.b)) {
                    a(this.c.getLocation(30, 0, true), 1);
                    a(this.c.getLocation(0, 15, true), 2);
                }
                this.e.sendEmptyMessageDelayed(5, (long) (a.l(this.b) * 1000));
                break;
        }
        return false;
    }
}
