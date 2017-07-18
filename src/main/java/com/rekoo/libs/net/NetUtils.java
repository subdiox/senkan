package com.rekoo.libs.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetUtils {
    public int checkNetworkType(Context context) {
        NetworkInfo activeInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeInfo == null || !activeInfo.isConnected()) {
            return 0;
        }
        boolean mobileConnected;
        boolean wifiConnected = activeInfo.getType() == 1;
        if (activeInfo.getType() == 0) {
            mobileConnected = true;
        } else {
            mobileConnected = false;
        }
        if (wifiConnected) {
            return (short) 1;
        }
        if (mobileConnected) {
            return (short) 2;
        }
        return 0;
    }

    public static boolean checkConnected(Context context) {
        NetworkInfo activeInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeInfo == null || !activeInfo.isConnected()) {
            return false;
        }
        int type = activeInfo.getType();
        if (type == 1) {
            return true;
        }
        if (type == 0) {
            return true;
        }
        return false;
    }

    public static boolean isWifi(Context mContext) {
        NetworkInfo activeNetInfo = ((ConnectivityManager) mContext.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetInfo == null || activeNetInfo.getType() != 1) {
            return false;
        }
        return true;
    }
}
