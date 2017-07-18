package com.rekoo.libs.utils;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import com.rekoo.libs.config.BIConfig;
import com.rekoo.libs.config.Config;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;

public class MacAddressUtil {
    public static String getMacAddress(Context context) {
        String rawAddress = getRawMacAddress(context);
        if (rawAddress != null) {
            return removeSpaceString(rawAddress.toUpperCase(Locale.US));
        }
        if (!TextUtils.isEmpty(Config.gooleAdvertisingId)) {
            return Config.gooleAdvertisingId;
        }
        if (TextUtils.isEmpty(BIConfig.getBiConfig().getDeviceId(context))) {
            return null;
        }
        return BIConfig.getBiConfig().getDeviceId(context);
    }

    private static String getRawMacAddress(Context context) {
        if (VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(context, "android.permission.ACCESS_WIFI_STATE") != 0) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{"android.permission.ACCESS_WIFI_STATE"}, 3080);
        }
        String wlanAddress = loadAddress("wlan0");
        if (wlanAddress != null) {
            return wlanAddress;
        }
        String ethAddress = loadAddress("eth0");
        if (ethAddress != null) {
            return ethAddress;
        }
        try {
            String wifiAddress = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo().getMacAddress();
            if (wifiAddress != null) {
                return wifiAddress;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String loadAddress(String interfaceName) {
        try {
            String filePath = "/sys/class/net/" + interfaceName + "/address";
            StringBuilder fileData = new StringBuilder(1000);
            BufferedReader reader = new BufferedReader(new FileReader(filePath), 1024);
            char[] buf = new char[1024];
            while (true) {
                int numRead = reader.read(buf);
                if (numRead == -1) {
                    reader.close();
                    return fileData.toString();
                }
                fileData.append(String.valueOf(buf, 0, numRead));
            }
        } catch (IOException e) {
            return null;
        }
    }

    private static String removeSpaceString(String inputString) {
        if (inputString == null) {
            return null;
        }
        String outputString = inputString.replaceAll("\\s", "");
        if (TextUtils.isEmpty(outputString)) {
            return null;
        }
        return outputString;
    }
}
