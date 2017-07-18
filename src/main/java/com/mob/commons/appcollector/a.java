package com.mob.commons.appcollector;

import com.mob.commons.j;
import com.mob.tools.MobHandlerThread;
import com.mob.tools.utils.R;
import java.io.File;

class a extends MobHandlerThread {
    final /* synthetic */ PackageCollector a;

    a(PackageCollector packageCollector) {
        this.a = packageCollector;
    }

    private void a() {
        super.run();
    }

    public void run() {
        j.a(new File(R.getCacheRoot(this.a.c), "comm/locks/.pkg_lock"), new b(this));
    }
}
