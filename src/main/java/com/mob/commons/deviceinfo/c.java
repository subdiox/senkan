package com.mob.commons.deviceinfo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.os.Parcelable;
import com.mob.commons.a;
import com.mob.commons.k;
import com.mob.tools.utils.Data;
import java.util.HashMap;

class c extends BroadcastReceiver {
    final /* synthetic */ DeviceInfoCollector a;

    c(DeviceInfoCollector deviceInfoCollector) {
        this.a = deviceInfoCollector;
    }

    public void onReceive(Context context, Intent intent) {
        if ("android.net.wifi.STATE_CHANGE".equals(intent.getAction())) {
            Parcelable parcelableExtra = intent.getParcelableExtra("networkInfo");
            if (parcelableExtra != null && ((NetworkInfo) parcelableExtra).isAvailable()) {
                HashMap hashMap = new HashMap();
                hashMap.put("ssid", this.a.c.getSSID());
                hashMap.put("bssid", this.a.c.getBssid());
                String MD5 = Data.MD5(this.a.d.fromHashMap(hashMap));
                String c = k.c(context);
                if ((c == null || !c.equals(MD5)) && a.m(context)) {
                    this.a.d();
                }
            }
        }
    }
}
