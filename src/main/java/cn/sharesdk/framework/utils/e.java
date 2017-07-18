package cn.sharesdk.framework.utils;

import android.content.Context;
import cn.sharesdk.framework.ShareSDK;
import com.mob.commons.logcollector.LogsCollector;

class e extends LogsCollector {
    final /* synthetic */ int a;
    final /* synthetic */ String b;
    final /* synthetic */ d c;

    e(d dVar, Context context, int i, String str) {
        this.c = dVar;
        this.a = i;
        this.b = str;
        super(context);
    }

    protected String getAppkey() {
        return this.b;
    }

    protected String getSDKTag() {
        return ShareSDK.SDK_TAG;
    }

    protected int getSDKVersion() {
        return this.a;
    }
}
