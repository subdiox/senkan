package cn.sharesdk.twitter;

class d extends Thread {
    final /* synthetic */ String a;
    final /* synthetic */ b b;

    d(b bVar, String str) {
        this.b = bVar;
        this.a = str;
    }

    public void run() {
        try {
            this.b.a(this.a);
        } catch (Throwable th) {
            cn.sharesdk.framework.utils.d.a().d(th);
        }
    }
}
