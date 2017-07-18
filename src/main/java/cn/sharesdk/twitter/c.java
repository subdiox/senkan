package cn.sharesdk.twitter;

import cn.sharesdk.framework.utils.d;

class c extends Thread {
    final /* synthetic */ String a;
    final /* synthetic */ b b;

    c(b bVar, String str) {
        this.b = bVar;
        this.a = str;
    }

    public void run() {
        try {
            this.b.a(this.a);
        } catch (Throwable th) {
            d.a().d(th);
        }
    }
}
