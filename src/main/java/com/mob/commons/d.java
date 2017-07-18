package com.mob.commons;

import android.content.Context;

final class d extends Thread {
    final /* synthetic */ Context a;

    d(Context context) {
        this.a = context;
    }

    public void run() {
        a.b(this.a, false, new e(this));
        a.d = false;
    }
}
