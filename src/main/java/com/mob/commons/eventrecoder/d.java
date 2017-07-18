package com.mob.commons.eventrecoder;

import com.mob.tools.MobLog;
import java.io.File;
import java.io.FileOutputStream;

final class d implements Runnable {
    d() {
    }

    public void run() {
        try {
            EventRecorder.c.close();
            EventRecorder.b.delete();
            EventRecorder.b = new File(EventRecorder.a.getFilesDir(), ".mrecord");
            EventRecorder.b.createNewFile();
            EventRecorder.c = new FileOutputStream(EventRecorder.b, true);
        } catch (Throwable th) {
            MobLog.getInstance().w(th);
        }
    }
}
