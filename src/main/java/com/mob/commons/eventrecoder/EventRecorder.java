package com.mob.commons.eventrecoder;

import android.content.Context;
import com.mob.commons.j;
import java.io.File;
import java.io.FileOutputStream;
import java.util.LinkedList;

public final class EventRecorder {
    private static Context a;
    private static File b;
    private static FileOutputStream c;

    private static final void a(Runnable runnable) {
        j.a(new File(a.getFilesDir(), "comm/locks/.mrlock"), runnable);
    }

    private static final void a(String str) {
        a(new b(str));
    }

    public static final synchronized void addBegin(String str, String str2) {
        synchronized (EventRecorder.class) {
            a(str + " " + str2 + " 0\n");
        }
    }

    public static final synchronized void addEnd(String str, String str2) {
        synchronized (EventRecorder.class) {
            a(str + " " + str2 + " 1\n");
        }
    }

    public static final synchronized String checkRecord(String str) {
        String str2;
        synchronized (EventRecorder.class) {
            LinkedList linkedList = new LinkedList();
            a(new c(str, linkedList));
            str2 = linkedList.size() > 0 ? (String) linkedList.get(0) : null;
        }
        return str2;
    }

    public static final synchronized void clear() {
        synchronized (EventRecorder.class) {
            a(new d());
        }
    }

    public static final synchronized void prepare(Context context) {
        synchronized (EventRecorder.class) {
            a = context.getApplicationContext();
            a(new a());
        }
    }
}
