package com.mob.commons;

import java.util.ArrayList;

class i implements Runnable {
    final /* synthetic */ g a;

    i(g gVar) {
        this.a = gVar;
    }

    public void run() {
        ArrayList d = this.a.b();
        if (d.size() > 0 && this.a.a(d)) {
            this.a.b(d);
        }
    }
}
