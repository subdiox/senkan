package cn.sharesdk.framework;

import cn.sharesdk.framework.utils.d;
import java.util.HashMap;

class o extends Thread {
    final /* synthetic */ n a;

    o(n nVar) {
        this.a = nVar;
    }

    public void run() {
        try {
            HashMap hashMap = new HashMap();
            if (!this.a.e() && this.a.a(hashMap)) {
                this.a.b(hashMap);
            }
        } catch (Throwable th) {
            d.a().w(th);
        }
    }
}
