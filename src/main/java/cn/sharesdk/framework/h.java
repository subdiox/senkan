package cn.sharesdk.framework;

import cn.sharesdk.framework.utils.d;

class h extends Thread {
    final /* synthetic */ String[] a;
    final /* synthetic */ f b;

    h(f fVar, String[] strArr) {
        this.b = fVar;
        this.a = strArr;
    }

    public void run() {
        try {
            this.b.j();
            this.b.a.doAuthorize(this.a);
        } catch (Throwable th) {
            d.a().w(th);
        }
    }
}
