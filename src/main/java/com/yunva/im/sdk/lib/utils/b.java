package com.yunva.im.sdk.lib.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.view.Display;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class b {
    public static final byte a = (byte) 0;
    public static final byte b = (byte) 1;
    public static final byte c = (byte) 2;
    public static final byte d = (byte) 3;
    public static final byte e = (byte) 0;
    public static final byte f = (byte) 1;
    public static final byte g = (byte) 2;
    public static final byte h = (byte) 3;
    private static final String i = "PhoneInfoUtil";

    public static String a(Context context) {
        String subscriberId = ((TelephonyManager) context.getSystemService("phone")).getSubscriberId();
        if (subscriberId != null && !subscriberId.equals("")) {
            return subscriberId;
        }
        subscriberId = a(context, 0);
        if (subscriberId == null || subscriberId.equals("")) {
            return a(context, 1);
        }
        return subscriberId;
    }

    private static String a(Context context, int i) {
        if (context == null || i < 0 || i > 1) {
            return null;
        }
        String obj;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        try {
            Object invoke = Class.forName("android.telephony.TelephonyManager").getMethod("getSubscriberIdGemini", new Class[]{Integer.TYPE}).invoke(telephonyManager, new Object[]{Integer.valueOf(i)});
            if (invoke != null) {
                obj = invoke.toString();
                return obj;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        obj = null;
        return obj;
    }

    public static String b(Context context) {
        String str = "000000000000000";
        try {
            return ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
        } catch (Exception e) {
            a.c(i, "PhoneInfoUtil：001:" + e.toString());
            return str;
        }
    }

    public static String c(Context context) {
        String str = "";
        try {
            return ((TelephonyManager) context.getSystemService("phone")).getSimOperator();
        } catch (Exception e) {
            a.c(i, "PhoneInfoUtil：002:" + e.toString());
            return str;
        }
    }

    public static String d(Context context) {
        String str = "";
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (telephonyManager.getSimState() == 5) {
                return telephonyManager.getSimOperatorName();
            }
        } catch (Exception e) {
            a.c(i, "PhoneInfoUtil：003:" + e.toString());
        }
        return str;
    }

    public static String a() {
        return Build.MODEL;
    }

    public static String b() {
        return Build.MANUFACTURER;
    }

    public static int c() {
        return VERSION.SDK_INT;
    }

    public static short d() {
        return (short) Integer.parseInt(VERSION.RELEASE.replace(".", "").substring(0, 2));
    }

    public static String e(Context context) {
        String macAddress;
        WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
        WifiInfo connectionInfo = wifiManager == null ? null : wifiManager.getConnectionInfo();
        if (connectionInfo != null) {
            macAddress = connectionInfo.getMacAddress();
        } else {
            macAddress = null;
        }
        if (macAddress == null) {
            return "";
        }
        return macAddress;
    }

    public static byte e() {
        String g = g();
        if (g == null || g.length() == 0) {
            return (byte) 0;
        }
        if (g.contains("ARMv7")) {
            return (byte) 3;
        }
        if (g.contains("ARMv6")) {
            return (byte) 2;
        }
        if (g.contains("ARMv5")) {
            return (byte) 1;
        }
        return (byte) 0;
    }

    private static String g() {
        FileReader fileReader;
        IOException e;
        String[] strArr;
        FileNotFoundException e2;
        Throwable th;
        BufferedReader bufferedReader;
        try {
            fileReader = new FileReader("/proc/cpuinfo");
            try {
                bufferedReader = new BufferedReader(fileReader);
                try {
                    String[] split = bufferedReader.readLine().split(":\\s+", 2);
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    if (fileReader != null) {
                        fileReader.close();
                        strArr = split;
                        if (strArr == null && strArr.length >= 2) {
                            return strArr[1];
                        }
                    }
                    strArr = split;
                } catch (FileNotFoundException e4) {
                    e2 = e4;
                    try {
                        e2.printStackTrace();
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e32) {
                                e32.printStackTrace();
                                strArr = null;
                            }
                        }
                        if (fileReader != null) {
                            fileReader.close();
                            strArr = null;
                            return strArr == null ? null : null;
                        }
                        strArr = null;
                        if (strArr == null) {
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e322) {
                                e322.printStackTrace();
                                throw th;
                            }
                        }
                        if (fileReader != null) {
                            fileReader.close();
                        }
                        throw th;
                    }
                } catch (IOException e5) {
                    e322 = e5;
                    e322.printStackTrace();
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e3222) {
                            e3222.printStackTrace();
                            strArr = null;
                        }
                    }
                    if (fileReader != null) {
                        fileReader.close();
                        strArr = null;
                        if (strArr == null) {
                        }
                    }
                    strArr = null;
                    if (strArr == null) {
                    }
                }
            } catch (FileNotFoundException e6) {
                e2 = e6;
                bufferedReader = null;
                e2.printStackTrace();
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (fileReader != null) {
                    fileReader.close();
                    strArr = null;
                    if (strArr == null) {
                    }
                }
                strArr = null;
                if (strArr == null) {
                }
            } catch (IOException e7) {
                e3222 = e7;
                bufferedReader = null;
                e3222.printStackTrace();
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (fileReader != null) {
                    fileReader.close();
                    strArr = null;
                    if (strArr == null) {
                    }
                }
                strArr = null;
                if (strArr == null) {
                }
            } catch (Throwable th3) {
                bufferedReader = null;
                th = th3;
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (fileReader != null) {
                    fileReader.close();
                }
                throw th;
            }
        } catch (FileNotFoundException e8) {
            e2 = e8;
            bufferedReader = null;
            fileReader = null;
            e2.printStackTrace();
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (fileReader != null) {
                fileReader.close();
                strArr = null;
                if (strArr == null) {
                }
            }
            strArr = null;
            if (strArr == null) {
            }
        } catch (IOException e9) {
            e3222 = e9;
            bufferedReader = null;
            fileReader = null;
            e3222.printStackTrace();
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (fileReader != null) {
                fileReader.close();
                strArr = null;
                if (strArr == null) {
                }
            }
            strArr = null;
            if (strArr == null) {
            }
        } catch (Throwable th32) {
            bufferedReader = null;
            fileReader = null;
            th = th32;
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (fileReader != null) {
                fileReader.close();
            }
            throw th;
        }
        if (strArr == null) {
        }
    }

    public static byte f(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            return (byte) 0;
        }
        int type = activeNetworkInfo.getType();
        if (type == 1) {
            return (byte) 1;
        }
        if (type == 0) {
            return g(context) ? (byte) 2 : (byte) 3;
        } else {
            return (byte) 0;
        }
    }

    private static boolean g(Context context) {
        switch (((TelephonyManager) context.getSystemService("phone")).getNetworkType()) {
            case 0:
                return false;
            case 1:
                return false;
            case 2:
                return false;
            case 3:
                return true;
            case 4:
                return false;
            case 5:
                return true;
            case 6:
                return true;
            case 7:
                return false;
            case 8:
                return true;
            case 9:
                return true;
            case 10:
                return true;
            case 11:
                return false;
            case 12:
                return true;
            case 13:
                return true;
            case 14:
                return true;
            case 15:
                return true;
            default:
                return false;
        }
    }

    public static String a(Activity activity) {
        Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
        return defaultDisplay.getWidth() + "x" + defaultDisplay.getHeight();
    }

    public static boolean f() {
        return Environment.getExternalStorageState().equals("mounted");
    }
}
