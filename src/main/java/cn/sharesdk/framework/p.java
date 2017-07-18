package cn.sharesdk.framework;

import cn.sharesdk.framework.b.a;
import cn.sharesdk.framework.utils.d;
import java.util.HashMap;

class p extends Thread {
    final /* synthetic */ a a;
    final /* synthetic */ n b;

    p(n nVar, a aVar) {
        this.b = nVar;
        this.a = aVar;
    }

    public void run() {
        try {
            HashMap e = this.a.e();
            HashMap hashMap = new HashMap();
            if (this.b.a(this.a, e, hashMap)) {
                this.b.b(hashMap);
            }
            this.a.a(e);
        } catch (Throwable th) {
            d.a().w(th);
        }
    }
}
