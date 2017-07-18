package com.mob.commons.appcollector;

import android.os.Handler.Callback;
import android.os.Message;

class c implements Callback {
    final /* synthetic */ PackageCollector a;

    c(PackageCollector packageCollector) {
        this.a = packageCollector;
    }

    public boolean handleMessage(Message message) {
        this.a.f();
        return false;
    }
}
