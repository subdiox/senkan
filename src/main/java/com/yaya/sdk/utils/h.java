package com.yaya.sdk.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings.System;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class h {
    public static final String a = "0";
    public static final String b = "1";
    public static final String c = "2";
    public static final String d = "3";
    private static final String e = "TelephonyUtil";
    private static final String f = "/uuinfo";
    private static final String g = "phone_uuid.tmp";

    public static String a() {
        return "1";
    }

    public static String a(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception e) {
            return null;
        }
    }

    public static int b(Context context) {
        int i = 0;
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (Exception e) {
            return i;
        }
    }

    public static String c(Context context) {
        try {
            return context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.getString("environment");
        } catch (Exception e) {
            return null;
        }
    }

    public static String b() {
        String c = c();
        if (c == null) {
            return "0";
        }
        if (c.contains("ARMv7")) {
            return d;
        }
        if (c.contains("ARMv6")) {
            return "2";
        }
        if (c.contains("ARMv5")) {
            return "1";
        }
        return "0";
    }

    public static String c() {
        FileReader fileReader;
        BufferedReader bufferedReader;
        String[] strArr;
        Throwable th;
        try {
            fileReader = new FileReader("/proc/cpuinfo");
            try {
                bufferedReader = new BufferedReader(fileReader);
                try {
                    String[] split = bufferedReader.readLine().split(":\\s+", 2);
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e) {
                            strArr = split;
                        }
                    }
                    if (fileReader != null) {
                        fileReader.close();
                        strArr = split;
                    } else {
                        strArr = split;
                    }
                } catch (FileNotFoundException e2) {
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e3) {
                            strArr = null;
                        }
                    }
                    if (fileReader != null) {
                        fileReader.close();
                        strArr = null;
                        return strArr != null ? null : null;
                    }
                    strArr = null;
                    if (strArr != null) {
                    }
                } catch (IOException e4) {
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e5) {
                            strArr = null;
                        }
                    }
                    if (fileReader != null) {
                        fileReader.close();
                        strArr = null;
                        if (strArr != null) {
                        }
                    }
                    strArr = null;
                    if (strArr != null) {
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e6) {
                            throw th;
                        }
                    }
                    if (fileReader != null) {
                        fileReader.close();
                    }
                    throw th;
                }
            } catch (FileNotFoundException e7) {
                bufferedReader = null;
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (fileReader != null) {
                    fileReader.close();
                    strArr = null;
                    if (strArr != null) {
                    }
                }
                strArr = null;
                if (strArr != null) {
                }
            } catch (IOException e8) {
                bufferedReader = null;
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (fileReader != null) {
                    fileReader.close();
                    strArr = null;
                    if (strArr != null) {
                    }
                }
                strArr = null;
                if (strArr != null) {
                }
            } catch (Throwable th3) {
                Throwable th4 = th3;
                bufferedReader = null;
                th = th4;
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (fileReader != null) {
                    fileReader.close();
                }
                throw th;
            }
        } catch (FileNotFoundException e9) {
            bufferedReader = null;
            fileReader = null;
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (fileReader != null) {
                fileReader.close();
                strArr = null;
                if (strArr != null) {
                }
            }
            strArr = null;
            if (strArr != null) {
            }
        } catch (IOException e10) {
            bufferedReader = null;
            fileReader = null;
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (fileReader != null) {
                fileReader.close();
                strArr = null;
                if (strArr != null) {
                }
            }
            strArr = null;
            if (strArr != null) {
            }
        } catch (Throwable th32) {
            fileReader = null;
            th = th32;
            bufferedReader = null;
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (fileReader != null) {
                fileReader.close();
            }
            throw th;
        }
        if (strArr != null && strArr.length >= 2) {
            return strArr[1];
        }
    }

    public static String d(Context context) {
        return null;
    }

    public static String d() {
        return Build.MODEL;
    }

    public static String e() {
        return Build.MANUFACTURER;
    }

    public static int f() {
        return VERSION.SDK_INT;
    }

    public static String g() {
        return VERSION.RELEASE;
    }

    public static String e(Context context) {
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
            WifiInfo connectionInfo = wifiManager == null ? null : wifiManager.getConnectionInfo();
            if (connectionInfo != null) {
                return connectionInfo.getMacAddress();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static String f(Context context) {
        try {
            return ((TelephonyManager) context.getSystemService("phone")).getSubscriberId();
        } catch (Exception e) {
            return null;
        }
    }

    public static String g(Context context) {
        try {
            return ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
        } catch (Exception e) {
            return null;
        }
    }

    public static String h(Context context) {
        return System.getString(context.getContentResolver(), "android_id");
    }

    public static int i(Context context) {
        try {
            if (((TelephonyManager) context.getSystemService("phone")).getSimState() != 5) {
                return 4;
            }
            String f = f(context);
            if (f.startsWith("46000") || f.startsWith("46002")) {
                return 1;
            }
            if (f.startsWith("46001")) {
                return 2;
            }
            if (f.startsWith("46003")) {
                return 3;
            }
            return 4;
        } catch (Exception e) {
            return 4;
        }
    }

    public static String j(Context context) {
        CharSequence charSequence = "";
        if (((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo() == null) {
            return "";
        }
        try {
            charSequence = Formatter.formatIpAddress(((WifiManager) context.getSystemService("wifi")).getConnectionInfo().getIpAddress());
            if (!TextUtils.isEmpty(charSequence)) {
                return "0.0.0.0".equals(charSequence) ? "" : charSequence;
            } else {
                Enumeration networkInterfaces = NetworkInterface.getNetworkInterfaces();
                while (networkInterfaces.hasMoreElements()) {
                    Enumeration inetAddresses = ((NetworkInterface) networkInterfaces.nextElement()).getInetAddresses();
                    while (inetAddresses.hasMoreElements()) {
                        InetAddress inetAddress = (InetAddress) inetAddresses.nextElement();
                        if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress() && (inetAddress instanceof Inet4Address)) {
                            return inetAddress.getHostAddress();
                        }
                    }
                }
                return charSequence;
            }
        } catch (SocketException e) {
        } catch (Exception e2) {
        }
    }

    public static int k(Context context) {
        int i;
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            i = 0;
        } else {
            i = activeNetworkInfo.getType();
            if (i == 1) {
                i = 1;
            } else if (i == 0) {
                switch (l(context)) {
                    case 2:
                        i = 2;
                        break;
                    case 3:
                        i = 3;
                        break;
                    case 4:
                        i = 4;
                        break;
                    default:
                        i = 0;
                        break;
                }
            } else {
                i = 0;
            }
        }
        if (i == 0) {
            return 4;
        }
        return i;
    }

    private static int l(Context context) {
        switch (((TelephonyManager) context.getSystemService("phone")).getNetworkType()) {
            case 0:
                return 0;
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
                return 2;
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
                return 3;
            case 13:
                return 4;
            default:
                return 0;
        }
    }
}
