package com.mob.commons;

import android.text.TextUtils;

class e implements Runnable {
    final /* synthetic */ d a;

    e(d dVar) {
        this.a = dVar;
    }

    public void run() {
        String p = a.w(this.a.a);
        if (!TextUtils.isEmpty(p)) {
            a.c(p);
            a.x(this.a.a);
        }
    }
}
