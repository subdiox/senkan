package cn.sharesdk.framework.utils;

import android.content.Context;
import android.text.TextUtils;

public class ShareSDKR {
    public static int getAnimRes(Context context, String resName) {
        return getResId(context, "anim", resName);
    }

    public static int getBitmapRes(Context context, String resName) {
        return getResId(context, "drawable", resName);
    }

    public static int getColorRes(Context context, String resName) {
        return getResId(context, "color", resName);
    }

    public static int getIdRes(Context context, String resName) {
        return getResId(context, "id", resName);
    }

    public static int getLayoutRes(Context context, String resName) {
        return getResId(context, "layout", resName);
    }

    public static int getPluralsRes(Context context, String resName) {
        return getResId(context, "plurals", resName);
    }

    public static int getRawRes(Context context, String resName) {
        return getResId(context, "raw", resName);
    }

    public static int getResId(Context context, String resType, String resName) {
        int i = 0;
        if (!(context == null || TextUtils.isEmpty(resType) || TextUtils.isEmpty(resName))) {
            String packageName = context.getPackageName();
            if (!TextUtils.isEmpty(packageName)) {
                i = context.getResources().getIdentifier(resName, resType, packageName);
                if (i <= 0) {
                    i = context.getResources().getIdentifier(resName.toLowerCase(), resType, packageName);
                }
                if (i <= 0) {
                    i = context.getResources().getIdentifier("ssdk_" + resName, resType, packageName);
                    if (i <= 0) {
                        i = context.getResources().getIdentifier("ssdk_" + resName.toLowerCase(), resType, packageName);
                    }
                }
                if (i <= 0) {
                    i = context.getResources().getIdentifier("ssdk_oks_" + resName, resType, packageName);
                    if (i <= 0) {
                        i = context.getResources().getIdentifier("ssdk_oks_" + resName.toLowerCase(), resType, packageName);
                    }
                }
                if (i <= 0) {
                    System.err.println("failed to parse " + resType + " resource \"" + resName + "\"");
                }
            }
        }
        return i;
    }

    public static int getStringArrayRes(Context context, String resName) {
        return getResId(context, "array", resName);
    }

    public static int getStringRes(Context context, String resName) {
        return getResId(context, "string", resName);
    }

    public static int getStyleRes(Context context, String resName) {
        return getResId(context, "style", resName);
    }
}
