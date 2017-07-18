package cn.sharesdk.framework.utils;

import android.content.Context;
import cn.sharesdk.framework.ShareSDK;
import com.mob.tools.log.NLog;

public class d extends NLog {
    private d(Context context, int i, String str) {
        NLog.setCollector(ShareSDK.SDK_TAG, new e(this, context, i, str));
    }

    public static NLog a() {
        return NLog.getInstanceForSDK(ShareSDK.SDK_TAG, true);
    }

    public static NLog a(Context context, int i, String str) {
        return new d(context, i, str);
    }

    protected String getSDKTag() {
        return ShareSDK.SDK_TAG;
    }
}
