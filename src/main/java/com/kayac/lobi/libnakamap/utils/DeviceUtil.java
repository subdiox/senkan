package com.kayac.lobi.libnakamap.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Build.VERSION;

public class DeviceUtil {
    public static final int LOW_MEM_DEFAULT = 5242880;
    public static final int MAX_MEM_DEFAULT = 20971520;

    private DeviceUtil() {
    }

    public static boolean hasFroyo() {
        return VERSION.SDK_INT >= 8;
    }

    public static boolean hasGingerbread() {
        return VERSION.SDK_INT >= 9;
    }

    public static boolean hasHoneycomb() {
        return VERSION.SDK_INT >= 11;
    }

    public static boolean hasHoneycombMR1() {
        return VERSION.SDK_INT >= 12;
    }

    public static boolean hasJellyBean() {
        return VERSION.SDK_INT >= 16;
    }

    public static boolean isLowMemoryDevice() {
        if (Runtime.getRuntime() == null || Runtime.getRuntime().maxMemory() >= 20971520) {
            return false;
        }
        return true;
    }

    public static boolean isLowMemoryNow() {
        if (Runtime.getRuntime() == null || Runtime.getRuntime().freeMemory() >= 5242880) {
            return false;
        }
        return true;
    }

    public static int getStatusBarHeight(Activity activity) {
        if (activity == null) {
            return 0;
        }
        Rect rectgle = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rectgle);
        return rectgle.top;
    }
}
