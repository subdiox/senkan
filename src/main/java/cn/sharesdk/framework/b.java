package cn.sharesdk.framework;

import cn.sharesdk.framework.b.b.c;
import cn.sharesdk.framework.b.d;
import com.mob.tools.utils.Hashon;
import java.util.HashMap;

class b implements PlatformActionListener {
    final /* synthetic */ PlatformActionListener a;
    final /* synthetic */ int b;
    final /* synthetic */ HashMap c;
    final /* synthetic */ a d;

    b(a aVar, PlatformActionListener platformActionListener, int i, HashMap hashMap) {
        this.d = aVar;
        this.a = platformActionListener;
        this.b = i;
        this.c = hashMap;
    }

    public void onCancel(Platform platform, int actionInner) {
        this.d.a = this.a;
        if (this.d.a != null) {
            this.d.a.onComplete(platform, this.b, this.c);
            this.d.a = null;
        }
    }

    public void onComplete(Platform platform, int actionInner, HashMap<String, Object> resInner) {
        this.d.a = this.a;
        if (this.d.a != null) {
            this.d.a.onComplete(platform, this.b, this.c);
            if (!"Wechat".equals(platform.getName())) {
                this.d.a = null;
            }
        }
        c bVar = new cn.sharesdk.framework.b.b.b();
        bVar.a = platform.getPlatformId();
        bVar.b = "TencentWeibo".equals(platform.getName()) ? platform.getDb().get("name") : platform.getDb().getUserId();
        bVar.c = new Hashon().fromHashMap(resInner);
        bVar.d = this.d.a(platform);
        d.a(platform.getContext(), bVar.g).a(bVar);
        this.d.a(1, platform);
    }

    public void onError(Platform platform, int actionInner, Throwable t) {
        cn.sharesdk.framework.utils.d.a().w(t);
        this.d.a = this.a;
        if (this.d.a != null) {
            this.d.a.onComplete(platform, this.b, this.c);
            this.d.a = null;
        }
    }
}
