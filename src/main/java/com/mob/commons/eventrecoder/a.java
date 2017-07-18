package com.mob.commons.eventrecoder;

import com.mob.tools.MobLog;
import java.io.File;
import java.io.FileOutputStream;

final class a implements Runnable {
    a() {
    }

    public void run() {
        try {
            EventRecorder.b = new File(EventRecorder.a.getFilesDir(), ".mrecord");
            if (!EventRecorder.b.exists()) {
                EventRecorder.b.createNewFile();
            }
            EventRecorder.c = new FileOutputStream(EventRecorder.b, true);
        } catch (Throwable th) {
            MobLog.getInstance().w(th);
        }
    }
}
