package com.mob.commons.eventrecoder;

import android.text.TextUtils;
import com.mob.tools.MobLog;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;

final class c implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ LinkedList b;

    c(String str, LinkedList linkedList) {
        this.a = str;
        this.b = linkedList;
    }

    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(EventRecorder.b), "utf-8"));
            for (Object readLine = bufferedReader.readLine(); !TextUtils.isEmpty(readLine); readLine = bufferedReader.readLine()) {
                String[] split = readLine.split(" ");
                if (this.a.equals(split[0])) {
                    if ("0".equals(split[2])) {
                        this.b.add(split[1]);
                    } else if ("1".equals(split[2])) {
                        int indexOf = this.b.indexOf(split[1]);
                        if (indexOf != -1) {
                            this.b.remove(indexOf);
                        }
                    }
                }
            }
            bufferedReader.close();
        } catch (Throwable th) {
            MobLog.getInstance().d(th);
        }
    }
}
