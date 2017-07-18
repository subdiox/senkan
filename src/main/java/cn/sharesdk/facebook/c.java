package cn.sharesdk.facebook;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.PlatformActionListener;
import java.util.HashMap;

class c implements PlatformActionListener {
    final /* synthetic */ ShareParams a;
    final /* synthetic */ Facebook b;

    c(Facebook facebook, ShareParams shareParams) {
        this.b = facebook;
        this.a = shareParams;
    }

    public void onCancel(Platform platform, int action) {
        if (this.b.listener != null) {
            this.b.listener.onCancel(this.b, 9);
        }
    }

    public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
        if (this.b.listener != null) {
            res.put("ShareParams", this.a);
            this.b.listener.onComplete(this.b, 9, res);
        }
    }

    public void onError(Platform platform, int action, Throwable t) {
        if (this.b.listener != null) {
            this.b.listener.onError(this.b, 9, t);
        }
    }
}
