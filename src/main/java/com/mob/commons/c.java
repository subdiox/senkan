package com.mob.commons;

import android.content.Context;

final class c implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ long b;

    c(Context context, long j) {
        this.a = context;
        this.b = j;
    }

    public void run() {
        if (a.t(this.a)) {
            a.b = this.b;
        }
    }
}
