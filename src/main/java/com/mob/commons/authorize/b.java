package com.mob.commons.authorize;

import android.content.Context;
import com.mob.commons.MobProduct;

class b implements Runnable {
    final /* synthetic */ String[] a;
    final /* synthetic */ Context b;
    final /* synthetic */ MobProduct c;
    final /* synthetic */ a d;

    b(a aVar, String[] strArr, Context context, MobProduct mobProduct) {
        this.d = aVar;
        this.a = strArr;
        this.b = context;
        this.c = mobProduct;
    }

    public void run() {
        this.a[0] = this.d.b(this.b, this.c);
    }
}
