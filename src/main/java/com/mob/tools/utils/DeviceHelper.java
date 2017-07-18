package com.mob.tools.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.UiModeManager;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Process;
import android.os.SystemClock;
import android.provider.Settings.Secure;
import android.support.v4.os.EnvironmentCompat;
import android.support.v4.widget.AutoScrollHelper;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.mob.tools.MobLog;
import com.rekoo.libs.config.Config;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;

public class DeviceHelper {
    private static DeviceHelper deviceHelper;
    private Context context;

    private class GSConnection implements ServiceConnection {
        boolean got;
        private final BlockingQueue<IBinder> iBinders;

        private GSConnection() {
            this.got = false;
            this.iBinders = new LinkedBlockingQueue();
        }

        public void onServiceConnected(ComponentName name, IBinder service) {
            try {
                this.iBinders.put(service);
            } catch (Throwable t) {
                MobLog.getInstance().w(t);
            }
        }

        public void onServiceDisconnected(ComponentName name) {
        }

        public IBinder takeBinder() throws InterruptedException {
            if (this.got) {
                throw new IllegalStateException();
            }
            this.got = true;
            return (IBinder) this.iBinders.poll(1500, TimeUnit.MILLISECONDS);
        }
    }

    private DeviceHelper(Context context) {
        this.context = context.getApplicationContext();
    }

    private String getCurrentNetworkHardwareAddress() throws Throwable {
        Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
        if (nis == null) {
            return null;
        }
        for (NetworkInterface intf : Collections.list(nis)) {
            Enumeration<InetAddress> ias = intf.getInetAddresses();
            if (ias != null) {
                for (InetAddress add : Collections.list(ias)) {
                    if (!add.isLoopbackAddress() && (add instanceof Inet4Address)) {
                        byte[] mac = intf.getHardwareAddress();
                        if (mac != null) {
                            StringBuilder buf = new StringBuilder();
                            int len$ = mac.length;
                            for (int i$ = 0; i$ < len$; i$++) {
                                buf.append(String.format("%02x:", new Object[]{Byte.valueOf(arr$[i$])}));
                            }
                            if (buf.length() > 0) {
                                buf.deleteCharAt(buf.length() - 1);
                            }
                            return buf.toString();
                        }
                    }
                }
                continue;
            }
        }
        return null;
    }

    private String getHardwareAddressFromShell(String networkCard) {
        Throwable t;
        Throwable th;
        String line = null;
        BufferedReader br = null;
        try {
            BufferedReader br2 = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("cat /sys/class/net/" + networkCard + "/address").getInputStream()));
            try {
                line = br2.readLine();
                if (br2 != null) {
                    try {
                        br2.close();
                        br = br2;
                    } catch (Throwable th2) {
                        br = br2;
                    }
                }
            } catch (Throwable th3) {
                th = th3;
                br = br2;
                if (br != null) {
                    br.close();
                }
                throw th;
            }
        } catch (Throwable th4) {
            t = th4;
            MobLog.getInstance().d(t);
            if (br != null) {
                br.close();
            }
            if (TextUtils.isEmpty(line)) {
            }
        }
        if (TextUtils.isEmpty(line)) {
        }
    }

    public static synchronized DeviceHelper getInstance(Context c) {
        DeviceHelper deviceHelper;
        synchronized (DeviceHelper.class) {
            if (deviceHelper == null && c != null) {
                deviceHelper = new DeviceHelper(c);
            }
            deviceHelper = deviceHelper;
        }
        return deviceHelper;
    }

    private String getLocalDeviceKey() throws Throwable {
        String str = null;
        if (getSdcardState()) {
            File keyFile;
            File cacheRoot = new File(getSdcardPath(), "ShareSDK");
            if (cacheRoot.exists()) {
                keyFile = new File(cacheRoot, ".dk");
                if (keyFile.exists() && keyFile.renameTo(new File(R.getCacheRoot(this.context), ".dk"))) {
                    keyFile.delete();
                }
            }
            keyFile = new File(R.getCacheRoot(this.context), ".dk");
            if (keyFile.exists()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(keyFile));
                Object key = ois.readObject();
                str = null;
                if (key != null && (key instanceof char[])) {
                    str = String.valueOf((char[]) key);
                }
                ois.close();
            }
        }
        return str;
    }

    private Object getSystemService(String name) {
        try {
            return this.context.getSystemService(name);
        } catch (Throwable t) {
            MobLog.getInstance().w(t);
            return null;
        }
    }

    private boolean is4GMobileNetwork() {
        TelephonyManager phone = (TelephonyManager) getSystemService("phone");
        return phone != null && phone.getNetworkType() == 13;
    }

    private boolean isFastMobileNetwork() {
        TelephonyManager phone = (TelephonyManager) getSystemService("phone");
        if (phone == null) {
            return false;
        }
        switch (phone.getNetworkType()) {
            case 0:
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
                return false;
            case 3:
                return true;
            case 5:
                return true;
            case 6:
                return true;
            case 8:
                return true;
            case 9:
                return true;
            case 10:
                return true;
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

    private boolean isSystemApp(PackageInfo pi) {
        return ((pi.applicationInfo.flags & 1) == 1) || ((pi.applicationInfo.flags & 128) == 1);
    }

    private String[] listNetworkHardwareAddress() throws Throwable {
        Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
        if (nis == null) {
            return null;
        }
        List<NetworkInterface> interfaces = Collections.list(nis);
        HashMap<String, String> macs = new HashMap();
        for (NetworkInterface intf : interfaces) {
            byte[] mac = intf.getHardwareAddress();
            if (mac != null) {
                StringBuilder buf = new StringBuilder();
                int len$ = mac.length;
                for (int i$ = 0; i$ < len$; i$++) {
                    buf.append(String.format("%02x:", new Object[]{Byte.valueOf(arr$[i$])}));
                }
                if (buf.length() > 0) {
                    buf.deleteCharAt(buf.length() - 1);
                }
                macs.put(intf.getName(), buf.toString());
            }
        }
        ArrayList<String> arrayList = new ArrayList(macs.keySet());
        ArrayList<String> wlans = new ArrayList();
        ArrayList<String> eths = new ArrayList();
        ArrayList<String> rmnets = new ArrayList();
        ArrayList<String> dummys = new ArrayList();
        ArrayList<String> usbs = new ArrayList();
        ArrayList<String> rmnetUsbs = new ArrayList();
        ArrayList<String> others = new ArrayList();
        while (arrayList.size() > 0) {
            String name = (String) arrayList.remove(0);
            if (name.startsWith("wlan")) {
                wlans.add(name);
            } else if (name.startsWith("eth")) {
                eths.add(name);
            } else if (name.startsWith("rev_rmnet")) {
                rmnets.add(name);
            } else if (name.startsWith("dummy")) {
                dummys.add(name);
            } else if (name.startsWith("usbnet")) {
                usbs.add(name);
            } else if (name.startsWith("rmnet_usb")) {
                rmnetUsbs.add(name);
            } else {
                others.add(name);
            }
        }
        Collections.sort(wlans);
        Collections.sort(eths);
        Collections.sort(rmnets);
        Collections.sort(dummys);
        Collections.sort(usbs);
        Collections.sort(rmnetUsbs);
        Collections.sort(others);
        arrayList.addAll(wlans);
        arrayList.addAll(eths);
        arrayList.addAll(rmnets);
        arrayList.addAll(dummys);
        arrayList.addAll(usbs);
        arrayList.addAll(rmnetUsbs);
        arrayList.addAll(others);
        String[] macArr = new String[arrayList.size()];
        for (int i = 0; i < macArr.length; i++) {
            macArr[i] = (String) macs.get(arrayList.get(i));
        }
        return macArr;
    }

    private void saveLocalDeviceKey(String key) throws Throwable {
        if (getSdcardState()) {
            File keyFile = new File(R.getCacheRoot(this.context), ".dk");
            if (keyFile.exists()) {
                keyFile.delete();
            }
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(keyFile));
            oos.writeObject(key.toCharArray());
            oos.flush();
            oos.close();
        }
    }

    public String Base64AES(String msg, String key) {
        String result = null;
        try {
            result = Base64.encodeToString(Data.AES128Encode(key, msg), 0);
            if (result.contains(IOUtils.LINE_SEPARATOR_UNIX)) {
                result = result.replace(IOUtils.LINE_SEPARATOR_UNIX, "");
            }
        } catch (Throwable e) {
            MobLog.getInstance().w(e);
        }
        return result;
    }

    public boolean checkPermission(String permission) throws Throwable {
        int res;
        if (VERSION.SDK_INT >= 23) {
            try {
                ReflectHelper.importClass("android.content.Context");
                Integer ret = (Integer) ReflectHelper.invokeInstanceMethod(this.context, "checkSelfPermission", permission);
                res = ret == null ? -1 : ret.intValue();
            } catch (Throwable t) {
                MobLog.getInstance().d(t);
                res = -1;
            }
        } else {
            this.context.checkPermission(permission, Process.myPid(), Process.myUid());
            res = this.context.getPackageManager().checkPermission(permission, getPackageName());
        }
        return res == 0;
    }

    public String getAdvertisingID() {
        String adsid;
        try {
            Intent intent = new Intent("com.google.android.gms.ads.identifier.service.START");
            intent.setPackage("com.google.android.gms");
            GSConnection gsc = new GSConnection();
            this.context.bindService(intent, gsc, 1);
            IBinder binder = gsc.takeBinder();
            Parcel input = Parcel.obtain();
            Parcel output = Parcel.obtain();
            input.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
            binder.transact(1, input, output, 0);
            output.readException();
            adsid = output.readString();
            output.recycle();
            input.recycle();
            MobLog.getInstance().i("getAdvertisingID === " + adsid, new Object[0]);
            this.context.unbindService(gsc);
            return adsid;
        } catch (Throwable t) {
            MobLog.getInstance().d(t);
            return null;
        }
    }

    public String getAndroidID() {
        String androidId = Secure.getString(this.context.getContentResolver(), "android_id");
        MobLog.getInstance().i("getAndroidID === " + androidId, new Object[0]);
        return androidId;
    }

    public String getAppLanguage() {
        return this.context.getResources().getConfiguration().locale.getLanguage();
    }

    public String getAppName() {
        String appName = this.context.getApplicationInfo().name;
        if (appName != null) {
            return appName;
        }
        int appLbl = this.context.getApplicationInfo().labelRes;
        return appLbl > 0 ? this.context.getString(appLbl) : String.valueOf(this.context.getApplicationInfo().nonLocalizedLabel);
    }

    public int getAppVersion() {
        int i = 0;
        try {
            return this.context.getPackageManager().getPackageInfo(this.context.getPackageName(), 0).versionCode;
        } catch (Throwable t) {
            MobLog.getInstance().d(t);
            return i;
        }
    }

    public String getAppVersionName() {
        try {
            return this.context.getPackageManager().getPackageInfo(this.context.getPackageName(), 0).versionName;
        } catch (Throwable t) {
            MobLog.getInstance().d(t);
            return "1.0";
        }
    }

    public String getBluetoothName() {
        try {
            BluetoothAdapter myDevice = BluetoothAdapter.getDefaultAdapter();
            if (myDevice != null && checkPermission("android.permission.BLUETOOTH")) {
                return myDevice.getName();
            }
        } catch (Throwable e) {
            MobLog.getInstance().d(e);
        }
        return null;
    }

    public String getBssid() {
        try {
            if (!checkPermission("android.permission.ACCESS_WIFI_STATE")) {
                return null;
            }
            WifiManager wifi = (WifiManager) getSystemService("wifi");
            if (wifi == null) {
                return null;
            }
            WifiInfo info = wifi.getConnectionInfo();
            if (info == null) {
                return null;
            }
            String bssid = info.getBSSID();
            if (bssid == null) {
                bssid = null;
            }
            return bssid;
        } catch (Throwable t) {
            MobLog.getInstance().d(t);
            return null;
        }
    }

    public String getCarrier() {
        TelephonyManager tm = (TelephonyManager) getSystemService("phone");
        if (tm == null) {
            return Config.INIT_FAIL_NO_NETWORK;
        }
        String operator = tm.getSimOperator();
        return TextUtils.isEmpty(operator) ? Config.INIT_FAIL_NO_NETWORK : operator;
    }

    public String getCarrierName() {
        TelephonyManager tm = (TelephonyManager) getSystemService("phone");
        if (tm == null) {
            return null;
        }
        try {
            if (checkPermission("android.permission.READ_PHONE_STATE")) {
                String operator = tm.getSimOperatorName();
                return TextUtils.isEmpty(operator) ? null : operator;
            }
        } catch (Throwable t) {
            MobLog.getInstance().w(t);
        }
        return null;
    }

    public int getCellId() {
        try {
            if (checkPermission("android.permission.ACCESS_COARSE_LOCATION")) {
                TelephonyManager tm = (TelephonyManager) getSystemService("phone");
                if (tm != null) {
                    return ((GsmCellLocation) tm.getCellLocation()).getCid();
                }
            }
        } catch (Throwable t) {
            MobLog.getInstance().d(t);
        }
        return -1;
    }

    public int getCellLac() {
        try {
            if (checkPermission("android.permission.ACCESS_COARSE_LOCATION")) {
                TelephonyManager tm = (TelephonyManager) getSystemService("phone");
                if (tm != null) {
                    return ((GsmCellLocation) tm.getCellLocation()).getLac();
                }
            }
        } catch (Throwable t) {
            MobLog.getInstance().d(t);
        }
        return -1;
    }

    public String getCharAndNumr(int length) {
        long realTime = System.currentTimeMillis() ^ SystemClock.elapsedRealtime();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(realTime);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            if ("char".equalsIgnoreCase(random.nextInt(2) % 2 == 0 ? "char" : "num")) {
                stringBuffer.insert(i + 1, (char) (random.nextInt(26) + 97));
            } else {
                stringBuffer.insert(stringBuffer.length(), random.nextInt(10));
            }
        }
        return stringBuffer.toString().substring(0, 40);
    }

    public String getDetailNetworkTypeForStatic() {
        String networkType = getNetworkType().toLowerCase();
        if (TextUtils.isEmpty(networkType) || "none".equals(networkType)) {
            return "none";
        }
        if (networkType.startsWith("wifi")) {
            return "wifi";
        }
        if (networkType.startsWith("4g")) {
            return "4g";
        }
        if (networkType.startsWith("3g")) {
            return "3g";
        }
        if (networkType.startsWith("2g")) {
            return "2g";
        }
        return networkType.startsWith("bluetooth") ? "bluetooth" : networkType;
    }

    public String getDeviceData() {
        return Base64AES(getModel() + "|" + getOSVersionInt() + "|" + getManufacturer() + "|" + getCarrier() + "|" + getScreenSize(), getDeviceKey().substring(0, 16));
    }

    public String getDeviceDataNotAES() {
        return getModel() + "|" + getOSVersion() + "|" + getManufacturer() + "|" + getCarrier() + "|" + getScreenSize();
    }

    public String getDeviceId() {
        String deviceId = getIMEI();
        return (!TextUtils.isEmpty(deviceId) || VERSION.SDK_INT < 9) ? deviceId : getSerialno();
    }

    public String getDeviceKey() {
        String localKey;
        try {
            localKey = getLocalDeviceKey();
        } catch (Throwable t) {
            MobLog.getInstance().w(t);
            localKey = null;
        }
        if (!TextUtils.isEmpty(localKey) && localKey.length() >= 40) {
            return localKey;
        }
        String newKey;
        try {
            String mac = getMacAddress();
            String udid = getDeviceId();
            newKey = Data.byteToHex(Data.SHA1(mac + ":" + udid + ":" + getModel()));
        } catch (Throwable t2) {
            MobLog.getInstance().d(t2);
            newKey = null;
        }
        if (TextUtils.isEmpty(newKey) || newKey.length() < 40) {
            newKey = getCharAndNumr(40);
        }
        if (newKey != null) {
            try {
                saveLocalDeviceKey(newKey);
            } catch (Throwable t22) {
                MobLog.getInstance().w(t22);
            }
        }
        return newKey;
    }

    public String getDeviceType() {
        UiModeManager um = (UiModeManager) getSystemService("uimode");
        if (um != null) {
            switch (um.getCurrentModeType()) {
                case 1:
                    return "NO_UI";
                case 2:
                    return "DESK";
                case 3:
                    return "CAR";
                case 4:
                    return "TELEVISION";
                case 5:
                    return "APPLIANCE";
                case 6:
                    return "WATCH";
            }
        }
        return "UNDEFINED";
    }

    public String getIMEI() {
        TelephonyManager phone = (TelephonyManager) getSystemService("phone");
        if (phone == null) {
            return null;
        }
        String deviceId = null;
        try {
            if (checkPermission("android.permission.READ_PHONE_STATE")) {
                deviceId = phone.getDeviceId();
            }
        } catch (Throwable t) {
            MobLog.getInstance().w(t);
        }
        return TextUtils.isEmpty(deviceId) ? null : deviceId;
    }

    public String getIMSI() {
        TelephonyManager phone = (TelephonyManager) getSystemService("phone");
        if (phone == null) {
            return null;
        }
        String imsi = null;
        try {
            if (checkPermission("android.permission.READ_PHONE_STATE")) {
                imsi = phone.getSubscriberId();
            }
        } catch (Throwable t) {
            MobLog.getInstance().w(t);
        }
        return TextUtils.isEmpty(imsi) ? null : imsi;
    }

    public String getIPAddress() {
        try {
            if (checkPermission("android.permission.INTERNET")) {
                Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
                while (en.hasMoreElements()) {
                    Enumeration<InetAddress> enumIpAddr = ((NetworkInterface) en.nextElement()).getInetAddresses();
                    while (enumIpAddr.hasMoreElements()) {
                        InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress() && (inetAddress instanceof Inet4Address)) {
                            return inetAddress.getHostAddress();
                        }
                    }
                }
            }
        } catch (Throwable e) {
            MobLog.getInstance().w(e);
        }
        return "0.0.0.0";
    }

    public ArrayList<HashMap<String, String>> getInstalledApp(boolean includeSystemApp) {
        try {
            PackageManager pm = this.context.getPackageManager();
            List<PackageInfo> pis = pm.getInstalledPackages(0);
            ArrayList<HashMap<String, String>> arrayList = new ArrayList();
            for (PackageInfo pi : pis) {
                if (includeSystemApp || !isSystemApp(pi)) {
                    HashMap<String, String> app = new HashMap();
                    app.put("pkg", pi.packageName);
                    String appName = pi.applicationInfo.name;
                    if (appName == null) {
                        int appLbl = pi.applicationInfo.labelRes;
                        if (appLbl > 0) {
                            CharSequence label = pm.getText(pi.packageName, appLbl, pi.applicationInfo);
                            if (label != null) {
                                appName = label.toString().trim();
                            }
                        }
                        if (appName == null) {
                            appName = String.valueOf(pi.applicationInfo.nonLocalizedLabel);
                        }
                    }
                    app.put("name", appName);
                    app.put("version", pi.versionName);
                    arrayList.add(app);
                }
            }
            return arrayList;
        } catch (Throwable t) {
            MobLog.getInstance().w(t);
            return new ArrayList();
        }
    }

    public String getLine1Number() {
        TelephonyManager tm = (TelephonyManager) getSystemService("phone");
        return tm == null ? Config.INIT_FAIL_NO_NETWORK : tm.getLine1Number();
    }

    public Location getLocation(int GPSTimeout, int networkTimeout, boolean useLastKnown) {
        try {
            if (checkPermission("android.permission.ACCESS_FINE_LOCATION")) {
                return new LocationHelper().getLocation(this.context, GPSTimeout, networkTimeout, useLastKnown);
            }
        } catch (Throwable t) {
            MobLog.getInstance().d(t);
        }
        return null;
    }

    public float[] getLocation(int GPSTimeout, int networkTimeout) {
        if (getLocation(GPSTimeout, networkTimeout, true) == null) {
            return null;
        }
        return new float[]{(float) getLocation(GPSTimeout, networkTimeout, true).getLatitude(), (float) getLocation(GPSTimeout, networkTimeout, true).getLongitude()};
    }

    public String getMCC() {
        if (((TelephonyManager) getSystemService("phone")) == null) {
            return null;
        }
        String imsi = getIMSI();
        return (imsi == null || imsi.length() < 3) ? null : imsi.substring(0, 3);
    }

    public String getMNC() {
        if (((TelephonyManager) getSystemService("phone")) == null) {
            return null;
        }
        String imsi = getIMSI();
        return (imsi == null || imsi.length() < 5) ? null : imsi.substring(3, 5);
    }

    public String getMacAddress() {
        if (VERSION.SDK_INT >= 23) {
            String hd;
            try {
                hd = getHardwareAddressFromShell("wlan0");
            } catch (Throwable t) {
                MobLog.getInstance().d(t);
                hd = null;
            }
            if (hd == null) {
                try {
                    hd = getCurrentNetworkHardwareAddress();
                } catch (Throwable t2) {
                    MobLog.getInstance().d(t2);
                    hd = null;
                }
            }
            if (hd == null) {
                try {
                    String[] hds = listNetworkHardwareAddress();
                    if (hds.length > 0) {
                        hd = hds[0];
                    }
                } catch (Throwable t22) {
                    MobLog.getInstance().d(t22);
                    hd = null;
                }
            }
            if (hd != null) {
                return hd;
            }
        }
        WifiManager wifi = (WifiManager) getSystemService("wifi");
        if (wifi == null) {
            return null;
        }
        WifiInfo info = wifi.getConnectionInfo();
        if (info == null) {
            return null;
        }
        String mac = info.getMacAddress();
        if (mac == null) {
            mac = null;
        }
        return mac;
    }

    public String getManufacturer() {
        return Build.MANUFACTURER;
    }

    public String getMime() {
        return getIMEI();
    }

    public String getModel() {
        return Build.MODEL;
    }

    public String getNetworkOperator() {
        TelephonyManager tm = (TelephonyManager) getSystemService("phone");
        return tm == null ? null : tm.getNetworkOperator();
    }

    public String getNetworkType() {
        ConnectivityManager conn = (ConnectivityManager) getSystemService("connectivity");
        if (conn == null) {
            return "none";
        }
        try {
            if (!checkPermission("android.permission.ACCESS_NETWORK_STATE")) {
                return "none";
            }
            NetworkInfo network = conn.getActiveNetworkInfo();
            if (network == null || !network.isAvailable()) {
                return "none";
            }
            int type = network.getType();
            switch (type) {
                case 0:
                    if (is4GMobileNetwork()) {
                        return "4G";
                    }
                    return isFastMobileNetwork() ? "3G" : "2G";
                case 1:
                    return "wifi";
                case 6:
                    return "wimax";
                case 7:
                    return "bluetooth";
                case 8:
                    return "dummy";
                case 9:
                    return "ethernet";
                default:
                    return String.valueOf(type);
            }
        } catch (Throwable t) {
            MobLog.getInstance().w(t);
            return "none";
        }
    }

    public String getNetworkTypeForStatic() {
        String networkType = getNetworkType().toLowerCase();
        if (TextUtils.isEmpty(networkType) || "none".equals(networkType)) {
            return "none";
        }
        if (networkType.startsWith("4g") || networkType.startsWith("3g") || networkType.startsWith("2g")) {
            return "cell";
        }
        return networkType.startsWith("wifi") ? "wifi" : "other";
    }

    public String getOSCountry() {
        return Locale.getDefault().getCountry();
    }

    public String getOSLanguage() {
        return Locale.getDefault().getLanguage();
    }

    public String getOSVersion() {
        return String.valueOf(getOSVersionInt());
    }

    public int getOSVersionInt() {
        return VERSION.SDK_INT;
    }

    public String getOSVersionName() {
        return VERSION.RELEASE;
    }

    public String getPackageName() {
        return this.context.getPackageName();
    }

    public int getPlatformCode() {
        return 1;
    }

    public JSONArray getRunningApp() {
        JSONArray appNmes = new JSONArray();
        ActivityManager am = (ActivityManager) getSystemService("activity");
        if (am != null) {
            List<RunningAppProcessInfo> apps = am.getRunningAppProcesses();
            if (apps != null) {
                for (RunningAppProcessInfo app : apps) {
                    appNmes.put(app.processName);
                }
            }
        }
        return appNmes;
    }

    public String getRunningAppStr() throws JSONException {
        JSONArray apps = getRunningApp();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < apps.length(); i++) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append(String.valueOf(apps.get(i)));
        }
        return sb.toString();
    }

    public String getSSID() {
        try {
            if (!checkPermission("android.permission.ACCESS_WIFI_STATE")) {
                return null;
            }
            WifiManager wifi = (WifiManager) getSystemService("wifi");
            if (wifi == null) {
                return null;
            }
            WifiInfo info = wifi.getConnectionInfo();
            if (info == null) {
                return null;
            }
            String ssid = info.getSSID().replace("\"", "");
            if (ssid == null) {
                ssid = null;
            }
            return ssid;
        } catch (Throwable t) {
            MobLog.getInstance().d(t);
            return null;
        }
    }

    public String getScreenSize() {
        int[] size = R.getScreenSize(this.context);
        return this.context.getResources().getConfiguration().orientation == 1 ? size[0] + "x" + size[1] : size[1] + "x" + size[0];
    }

    public String getSdcardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    public boolean getSdcardState() {
        try {
            return checkPermission("android.permission.WRITE_EXTERNAL_STORAGE") && "mounted".equals(Environment.getExternalStorageState());
        } catch (Throwable t) {
            MobLog.getInstance().w(t);
            return false;
        }
    }

    public String getSerialno() {
        if (VERSION.SDK_INT < 9) {
            return null;
        }
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            return (String) c.getMethod("get", new Class[]{String.class, String.class}).invoke(c, new Object[]{"ro.serialno", EnvironmentCompat.MEDIA_UNKNOWN});
        } catch (Throwable t) {
            MobLog.getInstance().d(t);
            return null;
        }
    }

    public String getSignMD5() {
        try {
            return Data.MD5(this.context.getPackageManager().getPackageInfo(getPackageName(), 64).signatures[0].toByteArray());
        } catch (Exception e) {
            MobLog.getInstance().w(e);
            return null;
        }
    }

    public String getSimSerialNumber() {
        TelephonyManager tm = (TelephonyManager) getSystemService("phone");
        return tm == null ? Config.INIT_FAIL_NO_NETWORK : tm.getSimSerialNumber();
    }

    public String getTopTaskPackageName() {
        boolean hasPer;
        try {
            hasPer = checkPermission("android.permission.GET_TASKS");
        } catch (Throwable t) {
            MobLog.getInstance().w(t);
            hasPer = false;
        }
        if (hasPer) {
            try {
                ActivityManager am = (ActivityManager) getSystemService("activity");
                if (am == null) {
                    return null;
                }
                return VERSION.SDK_INT <= 20 ? ((RunningTaskInfo) am.getRunningTasks(1).get(0)).topActivity.getPackageName() : ((RunningAppProcessInfo) am.getRunningAppProcesses().get(0)).processName.split(":")[0];
            } catch (Throwable t2) {
                MobLog.getInstance().w(t2);
            }
        }
        return null;
    }

    public void hideSoftInput(View view) {
        InputMethodManager service = getSystemService("input_method");
        if (service != null) {
            service.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public boolean isMainProcess(int pid) {
        String application = null;
        ActivityManager mActivityManager = (ActivityManager) getSystemService("activity");
        if (mActivityManager.getRunningAppProcesses() == null) {
            return pid <= 0;
        } else {
            int mPid = pid <= 0 ? Process.myPid() : pid;
            for (RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
                if (appProcess.pid == mPid) {
                    application = appProcess.processName;
                    break;
                }
            }
            return getPackageName().equals(application);
        }
    }

    public boolean isRooted() {
        return false;
    }

    public HashMap<String, String> ping(String address, int count, int packetsize) {
        int i;
        ArrayList<Float> sucRes = new ArrayList();
        int bytes = packetsize + 8;
        Process p = Runtime.getRuntime().exec("ping -c " + count + " -s " + packetsize + " " + address);
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = br.readLine();
        while (line != null) {
            if (line.startsWith(bytes + " bytes from")) {
                if (line.endsWith("ms")) {
                    line = line.substring(0, line.length() - 2).trim();
                } else if (line.endsWith("s")) {
                    line = line.substring(0, line.length() - 1).trim() + "000";
                }
                i = line.indexOf("time=");
                if (i > 0) {
                    try {
                        sucRes.add(Float.valueOf(Float.parseFloat(line.substring(i + 5).trim())));
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            }
            line = br.readLine();
        }
        p.waitFor();
        int sucCount = sucRes.size();
        int fldCount = count - sucRes.size();
        float min = 0.0f;
        float max = 0.0f;
        float average = 0.0f;
        if (sucCount > 0) {
            min = AutoScrollHelper.NO_MAX;
            for (i = 0; i < sucCount; i++) {
                float item = ((Float) sucRes.get(i)).floatValue();
                if (item < min) {
                    min = item;
                }
                if (item > max) {
                    max = item;
                }
                average += item;
            }
            average /= (float) sucCount;
        }
        HashMap<String, String> map = new HashMap();
        map.put("address", address);
        map.put("transmitted", String.valueOf(count));
        map.put("received", String.valueOf(sucCount));
        map.put("loss", String.valueOf(fldCount));
        map.put("min", String.valueOf(min));
        map.put("max", String.valueOf(max));
        map.put("avg", String.valueOf(average));
        return map;
    }

    public void showSoftInput(View view) {
        InputMethodManager service = getSystemService("input_method");
        if (service != null) {
            service.toggleSoftInputFromWindow(view.getWindowToken(), 2, 0);
        }
    }
}
