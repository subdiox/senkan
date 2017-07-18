package com.mob.commons.appcollector;

import com.mob.commons.j;
import com.mob.tools.utils.R;
import java.io.File;

class e extends Thread {
    final /* synthetic */ RuntimeCollector a;

    e(RuntimeCollector runtimeCollector) {
        this.a = runtimeCollector;
    }

    public void run() {
        j.a(new File(R.getCacheRoot(this.a.c), "comm/locks/.rc_lock"), new f(this));
    }
}
