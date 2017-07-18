package cn.sharesdk.facebook;

import android.app.Instrumentation;
import cn.sharesdk.framework.utils.d;

class k extends Thread {
    final /* synthetic */ j a;

    k(j jVar) {
        this.a = jVar;
    }

    public void run() {
        try {
            new Instrumentation().sendKeyDownUpSync(4);
        } catch (Throwable th) {
            d.a().d(th);
            this.a.a.finish();
            this.a.a.b.onCancel(null, 0);
        }
    }
}
