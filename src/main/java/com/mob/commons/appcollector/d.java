package com.mob.commons.appcollector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

class d extends BroadcastReceiver {
    final /* synthetic */ PackageCollector a;

    d(PackageCollector packageCollector) {
        this.a = packageCollector;
    }

    public void onReceive(Context context, Intent intent) {
        String str = null;
        if (intent != null) {
            str = intent.getAction();
        }
        if (this.a.a(str)) {
            this.a.f.removeMessages(1);
            this.a.f.sendEmptyMessageDelayed(1, 5000);
        }
    }
}
