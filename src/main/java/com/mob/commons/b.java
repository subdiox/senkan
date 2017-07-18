package com.mob.commons;

import android.content.Context;

final class b implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ long b;

    b(Context context, long j) {
        this.a = context;
        this.b = j;
    }

    public void run() {
        if (a.s(this.a)) {
            a.b = this.b;
        }
    }
}
