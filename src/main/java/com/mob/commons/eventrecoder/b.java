package com.mob.commons.eventrecoder;

import com.mob.tools.MobLog;

final class b implements Runnable {
    final /* synthetic */ String a;

    b(String str) {
        this.a = str;
    }

    public void run() {
        try {
            EventRecorder.c.write(this.a.getBytes("utf-8"));
            EventRecorder.c.flush();
        } catch (Throwable th) {
            MobLog.getInstance().w(th);
        }
    }
}
