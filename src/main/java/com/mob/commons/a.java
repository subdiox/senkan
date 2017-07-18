package com.mob.commons;

import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;
import com.kayac.lobi.libnakamap.utils.NotificationUtil;
import com.mob.tools.MobLog;
import com.mob.tools.network.KVPair;
import com.mob.tools.network.NetworkHelper.NetworkTimeOut;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.Hashon;
import com.mob.tools.utils.R;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class a {
    private static HashMap<String, Object> a;
    private static long b;
    private static long c;
    private static boolean d;

    private static void a() {
        a = new HashMap();
        a.put("in", Integer.valueOf(1));
        a.put("all", Integer.valueOf(1));
        a.put("agap", "m");
        a.put("un", Integer.valueOf(1));
        a.put("rt", Integer.valueOf(1));
        a.put("rtsr", Integer.valueOf(300000));
        a.put("mi", Integer.valueOf(1));
        a.put("ext", Integer.valueOf(1));
        a.put("bs", Integer.valueOf(1));
        a.put("bsgap", Integer.valueOf(86400));
        a.put("di", Integer.valueOf(1));
        a.put("l", Integer.valueOf(0));
        a.put("lgap", Integer.valueOf(86400));
        a.put("wi", Integer.valueOf(1));
    }

    public static boolean a(Context context) {
        r(context);
        return 1 == ((Integer) R.forceCast(a.get("mi"), Integer.valueOf(1))).intValue();
    }

    public static long b(Context context) {
        long longValue;
        r(context);
        long elapsedRealtime = SystemClock.elapsedRealtime();
        try {
            longValue = Long.valueOf(String.valueOf(a.get("deviceTime"))).longValue();
        } catch (Throwable th) {
            longValue = 0;
        }
        return ((Long) R.forceCast(a.get("serverTime"), Long.valueOf(0))).longValue() + (elapsedRealtime - longValue);
    }

    private static long b(String str) {
        if (!TextUtils.isEmpty(str)) {
            switch (str.charAt(0)) {
                case 'd':
                    return 86400;
                case 'h':
                    return 3600;
                case 'm':
                    return 2592000;
                case 'w':
                    return 604800;
            }
        }
        return 0;
    }

    private static void b(Context context, boolean z, Runnable runnable) {
        j.a(new File(R.getCacheRoot(context), "comm/locks/.ccLock"), z, runnable);
    }

    private static void c(String str) {
        try {
            a = new Hashon().fromJson(str);
        } catch (Throwable th) {
            MobLog.getInstance().w(th);
        }
    }

    public static boolean c(Context context) {
        r(context);
        return 1 == ((Integer) R.forceCast(a.get("rt"), Integer.valueOf(1))).intValue();
    }

    public static int d(Context context) {
        r(context);
        return ((Integer) R.forceCast(a.get("rtsr"), Integer.valueOf(300000))).intValue();
    }

    public static boolean e(Context context) {
        r(context);
        return 1 == ((Integer) R.forceCast(a.get("all"), Integer.valueOf(1))).intValue();
    }

    public static long f(Context context) {
        r(context);
        return b((String) R.forceCast(a.get("agap"), "m"));
    }

    public static boolean g(Context context) {
        r(context);
        return 1 == ((Integer) R.forceCast(a.get("di"), Integer.valueOf(1))).intValue();
    }

    public static boolean h(Context context) {
        r(context);
        return 1 == ((Integer) R.forceCast(a.get("ext"), Integer.valueOf(1))).intValue();
    }

    public static boolean i(Context context) {
        r(context);
        return 1 == ((Integer) R.forceCast(a.get("bs"), Integer.valueOf(1))).intValue();
    }

    public static int j(Context context) {
        r(context);
        return ((Integer) R.forceCast(a.get("bsgap"), Integer.valueOf(86400))).intValue();
    }

    public static boolean k(Context context) {
        r(context);
        return 1 == ((Integer) R.forceCast(a.get("l"), Integer.valueOf(0))).intValue();
    }

    public static int l(Context context) {
        r(context);
        return ((Integer) R.forceCast(a.get("lgap"), Integer.valueOf(86400))).intValue();
    }

    public static boolean m(Context context) {
        r(context);
        return 1 == ((Integer) R.forceCast(a.get("wi"), Integer.valueOf(1))).intValue();
    }

    private static synchronized void r(Context context) {
        synchronized (a.class) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            if (a == null) {
                b(context, true, new b(context, elapsedRealtime));
            } else if (elapsedRealtime - b >= 60000) {
                b(context, true, new c(context, elapsedRealtime));
            }
        }
    }

    private static boolean s(Context context) {
        String w = w(context);
        if (TextUtils.isEmpty(w)) {
            a();
            return false;
        }
        c(w);
        x(context);
        return true;
    }

    private static boolean t(Context context) {
        String v = v(context);
        if (TextUtils.isEmpty(v)) {
            return s(context);
        }
        c(v);
        if (((Long) R.forceCast(a.get("timestamp"), Long.valueOf(0))).longValue() - c >= NotificationUtil.PROMOTION_NOTIFICATION_INTERVAL) {
            y(context);
        }
        return true;
    }

    private static File u(Context context) {
        File file = new File(R.getCacheRoot(context), "comm/dbs/.ccc");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        return file;
    }

    private static String v(Context context) {
        String str;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            File u = u(context);
            if (!u.getParentFile().exists()) {
                u.getParentFile().mkdirs();
            }
            if (!u.exists()) {
                return null;
            }
            FileInputStream fileInputStream = new FileInputStream(u);
            byte[] bArr = new byte[1024];
            for (int read = fileInputStream.read(bArr); read > 0; read = fileInputStream.read(bArr)) {
                byteArrayOutputStream.write(bArr, 0, read);
            }
            byteArrayOutputStream.close();
            fileInputStream.close();
            str = new String(byteArrayOutputStream.toByteArray(), "utf-8");
            return str;
        } catch (Throwable th) {
            MobLog.getInstance().w(th);
            str = null;
        }
    }

    private static String w(Context context) {
        try {
            f a = f.a(context);
            ArrayList a2 = a.a();
            if (a2.isEmpty()) {
                return null;
            }
            DeviceHelper instance = DeviceHelper.getInstance(context);
            ArrayList arrayList = new ArrayList();
            arrayList.add(new KVPair("appkey", ((MobProduct) a2.get(0)).getProductAppkey()));
            arrayList.add(new KVPair("plat", String.valueOf(instance.getPlatformCode())));
            arrayList.add(new KVPair("apppkg", instance.getPackageName()));
            arrayList.add(new KVPair("appver", instance.getAppVersionName()));
            arrayList.add(new KVPair("networktype", instance.getDetailNetworkTypeForStatic()));
            NetworkTimeOut networkTimeOut = new NetworkTimeOut();
            networkTimeOut.readTimout = 30000;
            networkTimeOut.connectionTimeout = 30000;
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(new KVPair("User-Identity", a.a(a2)));
            String httpGet = a.httpGet("http://m.data.mob.com/cconf", arrayList, arrayList2, networkTimeOut);
            Hashon hashon = new Hashon();
            HashMap fromJson = hashon.fromJson(httpGet);
            if (fromJson == null) {
                return null;
            }
            if ("200".equals(String.valueOf(fromJson.get("status")))) {
                HashMap hashMap = (HashMap) R.forceCast(fromJson.get("switchs"));
                if (hashMap == null) {
                    throw new Throwable("response is illegal: " + httpGet);
                }
                long longValue = ((Long) R.forceCast(fromJson.get("timestamp"), Long.valueOf(0))).longValue();
                hashMap.put("deviceTime", Long.valueOf(SystemClock.elapsedRealtime()));
                hashMap.put("serverTime", Long.valueOf(longValue));
                return hashon.fromHashMap(hashMap);
            }
            throw new Throwable("response is illegal: " + httpGet);
        } catch (Throwable th) {
            MobLog.getInstance().w(th);
            return null;
        }
    }

    private static void x(Context context) {
        try {
            String fromHashMap = new Hashon().fromHashMap(a);
            FileOutputStream fileOutputStream = new FileOutputStream(u(context));
            fileOutputStream.write(fromHashMap.getBytes("utf-8"));
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Throwable th) {
            MobLog.getInstance().w(th);
        }
    }

    private static void y(Context context) {
        if (!d) {
            d = true;
            new d(context).start();
        }
    }
}
