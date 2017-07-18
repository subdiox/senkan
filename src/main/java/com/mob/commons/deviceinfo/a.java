package com.mob.commons.deviceinfo;

import com.mob.commons.j;
import com.mob.tools.MobHandlerThread;
import com.mob.tools.utils.R;
import java.io.File;

class a extends MobHandlerThread {
    final /* synthetic */ DeviceInfoCollector a;

    a(DeviceInfoCollector deviceInfoCollector) {
        this.a = deviceInfoCollector;
    }

    private void a() {
        super.run();
    }

    public void run() {
        j.a(new File(R.getCacheRoot(this.a.b), "comm/locks/.dic_lock"), new b(this));
    }
}
