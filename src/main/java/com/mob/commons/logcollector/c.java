package com.mob.commons.logcollector;

import android.content.Context;
import android.os.Message;
import android.text.TextUtils;
import android.util.Base64;
import com.kayac.lobi.sdk.LobiCoreAPI.APICallback;
import com.mob.tools.MobLog;
import com.mob.tools.SSDKHandlerThread;
import com.mob.tools.log.NLog;
import com.mob.tools.network.KVPair;
import com.mob.tools.network.NetworkHelper;
import com.mob.tools.network.NetworkHelper.NetworkTimeOut;
import com.mob.tools.utils.Data;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.FileLocker;
import com.mob.tools.utils.Hashon;
import com.mob.tools.utils.R;
import com.rekoo.libs.net.URLCons;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.zip.GZIPOutputStream;

public class c extends SSDKHandlerThread {
    private static c a;
    private static String b = "http://api.exc.mob.com:80";
    private HashMap<String, Integer> c;
    private Context d;
    private DeviceHelper e;
    private NetworkHelper f = new NetworkHelper();
    private d g;
    private File h;
    private FileLocker i;

    private c(Context context) {
        this.d = context.getApplicationContext();
        this.e = DeviceHelper.getInstance(context);
        this.g = d.a(context);
        this.c = new HashMap();
        this.i = new FileLocker();
        this.h = new File(context.getFilesDir(), ".lock");
        if (!this.h.exists()) {
            try {
                this.h.createNewFile();
            } catch (Throwable e) {
                MobLog.getInstance().w(e);
            }
        }
        NLog.setContext(context);
        startThread();
    }

    public static synchronized c a(Context context) {
        c cVar;
        synchronized (c.class) {
            if (a == null) {
                a = new c(context);
            }
            cVar = a;
        }
        return cVar;
    }

    private String a(String str) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(str.getBytes());
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
        byte[] bArr = new byte[1024];
        while (true) {
            int read = byteArrayInputStream.read(bArr, 0, 1024);
            if (read != -1) {
                gZIPOutputStream.write(bArr, 0, read);
            } else {
                gZIPOutputStream.flush();
                gZIPOutputStream.close();
                byte[] toByteArray = byteArrayOutputStream.toByteArray();
                byteArrayOutputStream.flush();
                byteArrayOutputStream.close();
                byteArrayInputStream.close();
                return Base64.encodeToString(toByteArray, 2);
            }
        }
    }

    private void a(int i, String str, String str2, String[] strArr) {
        try {
            if (this.g.b()) {
                if ("none".equals(this.e.getDetailNetworkTypeForStatic())) {
                    throw new IllegalStateException("network is disconnected!");
                }
                ArrayList a = f.a(this.d, strArr);
                for (int i2 = 0; i2 < a.size(); i2++) {
                    e eVar = (e) a.get(i2);
                    HashMap c = c(i, str, str2);
                    c.put("errmsg", eVar.a);
                    if (a(a(new Hashon().fromHashMap(c)), true)) {
                        f.a(this.d, eVar.b);
                    }
                }
            }
        } catch (Throwable th) {
            MobLog.getInstance().i(th);
        }
    }

    private void a(Message message) {
        this.handler.sendMessageDelayed(message, 1000);
    }

    private boolean a(String str, boolean z) {
        try {
            if ("none".equals(this.e.getDetailNetworkTypeForStatic())) {
                throw new IllegalStateException("network is disconnected!");
            }
            ArrayList arrayList = new ArrayList();
            arrayList.add(new KVPair("m", str));
            NetworkTimeOut networkTimeOut = new NetworkTimeOut();
            networkTimeOut.readTimout = 10000;
            networkTimeOut.connectionTimeout = 10000;
            this.f.httpPost(c(), arrayList, null, null, networkTimeOut);
            return true;
        } catch (Throwable th) {
            MobLog.getInstance().i(th);
            return false;
        }
    }

    private String b() {
        return b + "/errconf";
    }

    private void b(int i, String str, String str2) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new KVPair("key", str2));
        arrayList.add(new KVPair("sdk", str));
        arrayList.add(new KVPair("apppkg", this.e.getPackageName()));
        arrayList.add(new KVPair("appver", String.valueOf(this.e.getAppVersion())));
        arrayList.add(new KVPair("sdkver", String.valueOf(i)));
        arrayList.add(new KVPair("plat", String.valueOf(this.e.getPlatformCode())));
        try {
            NetworkTimeOut networkTimeOut = new NetworkTimeOut();
            networkTimeOut.readTimout = 10000;
            networkTimeOut.connectionTimeout = 10000;
            MobLog.getInstance().i("get logs server config response == %s", this.f.httpPost(b(), arrayList, null, null, networkTimeOut));
            HashMap fromJson = new Hashon().fromJson(r0);
            if ("-200".equals(String.valueOf(fromJson.get("status")))) {
                MobLog.getInstance().i("error log server config response fail !!", new Object[0]);
                return;
            }
            Object obj = fromJson.get("result");
            if (obj != null && (obj instanceof HashMap)) {
                HashMap hashMap;
                fromJson = (HashMap) obj;
                if (fromJson.containsKey("timestamp")) {
                    this.g.a(System.currentTimeMillis() - R.parseLong(String.valueOf(fromJson.get("timestamp"))));
                }
                if ("1".equals(String.valueOf(fromJson.get("enable")))) {
                    this.g.a(true);
                } else {
                    this.g.a(false);
                }
                Object obj2 = fromJson.get("upconf");
                if (obj2 != null && (obj2 instanceof HashMap)) {
                    hashMap = (HashMap) obj2;
                    String valueOf = String.valueOf(hashMap.get("crash"));
                    String valueOf2 = String.valueOf(hashMap.get("sdkerr"));
                    String valueOf3 = String.valueOf(hashMap.get("apperr"));
                    this.g.a(Integer.parseInt(valueOf));
                    this.g.b(Integer.parseInt(valueOf2));
                    this.g.c(Integer.parseInt(valueOf3));
                }
                if (fromJson.containsKey("requesthost") && fromJson.containsKey("requestport")) {
                    obj2 = String.valueOf(fromJson.get("requesthost"));
                    Object valueOf4 = String.valueOf(fromJson.get("requestport"));
                    if (!(TextUtils.isEmpty(obj2) || TextUtils.isEmpty(valueOf4))) {
                        b = URLCons.HTTP + obj2 + ":" + valueOf4;
                    }
                }
                obj = fromJson.get("filter");
                if (obj != null && (obj instanceof ArrayList)) {
                    ArrayList arrayList2 = (ArrayList) obj;
                    if (arrayList2.size() > 0) {
                        hashMap = new HashMap();
                        hashMap.put("fakelist", arrayList2);
                        this.g.a(new Hashon().fromHashMap(hashMap));
                    }
                }
            }
        } catch (Throwable th) {
            MobLog.getInstance().d(th);
        }
    }

    private void b(Message message) {
        try {
            int i = message.arg1;
            Object[] objArr = (Object[]) message.obj;
            String str = (String) objArr[0];
            String str2 = (String) objArr[1];
            b(i, str, str2);
            a(i, str, str2, null);
        } catch (Throwable th) {
            MobLog.getInstance().w(th);
        }
    }

    private String c() {
        return b + "/errlog";
    }

    private HashMap<String, Object> c(int i, String str, String str2) {
        HashMap<String, Object> hashMap = new HashMap();
        hashMap.put("key", str2);
        hashMap.put("plat", Integer.valueOf(this.e.getPlatformCode()));
        hashMap.put("sdk", str);
        hashMap.put("sdkver", Integer.valueOf(i));
        hashMap.put("appname", this.e.getAppName());
        hashMap.put("apppkg", this.e.getPackageName());
        hashMap.put("appver", String.valueOf(this.e.getAppVersion()));
        hashMap.put("deviceid", this.e.getDeviceKey());
        hashMap.put("model", this.e.getModel());
        hashMap.put(URLCons.MAC, this.e.getMacAddress());
        hashMap.put("udid", this.e.getDeviceId());
        hashMap.put("sysver", String.valueOf(this.e.getOSVersionInt()));
        hashMap.put("networktype", this.e.getDetailNetworkTypeForStatic());
        return hashMap;
    }

    private void c(Message message) {
        int c;
        String MD5;
        try {
            int i = message.arg1;
            Object[] objArr = (Object[]) message.obj;
            String str = (String) objArr[0];
            String str2 = (String) objArr[1];
            String str3 = (String) objArr[2];
            int i2 = 1;
            if (message.arg2 == 0) {
                i2 = 2;
            } else if (message.arg2 == 2) {
                i2 = 1;
            }
            String f = this.g.f();
            if (!TextUtils.isEmpty(f)) {
                ArrayList arrayList = (ArrayList) new Hashon().fromJson(f).get("fakelist");
                if (arrayList != null && arrayList.size() > 0) {
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        f = (String) it.next();
                        if (!TextUtils.isEmpty(f) && str3.contains(f)) {
                            return;
                        }
                    }
                }
            }
            c = this.g.c();
            int d = this.g.d();
            int e = this.g.e();
            if (3 != i2 || -1 != e) {
                if (1 != i2 || -1 != c) {
                    if (2 != i2 || -1 != d) {
                        MD5 = Data.MD5(str3);
                        this.i.setLockFile(this.h.getAbsolutePath());
                        if (this.i.lock(false)) {
                            f.a(this.d, System.currentTimeMillis() - this.g.a(), str3, i2, MD5);
                        }
                        this.i.release();
                        this.c.remove(MD5);
                        if (3 == i2 && 1 == e) {
                            a(i, str, str2, new String[]{String.valueOf(3)});
                        } else if (1 == i2 && 1 == c) {
                            a(i, str, str2, new String[]{String.valueOf(1)});
                        } else if (2 == i2 && 1 == d) {
                            a(i, str, str2, new String[]{String.valueOf(2)});
                        }
                    }
                }
            }
        } catch (Throwable th) {
            MobLog.getInstance().w(th);
        }
    }

    public Context a() {
        return this.d;
    }

    public void a(int i, int i2, String str, String str2, String str3) {
        Message message = new Message();
        message.what = APICallback.RESPONSE_ERROR;
        message.arg1 = i;
        message.arg2 = i2;
        message.obj = new Object[]{str, str2, str3};
        this.handler.sendMessage(message);
    }

    public void a(int i, String str, String str2) {
        Message message = new Message();
        message.what = 100;
        message.arg1 = i;
        message.obj = new Object[]{str, str2};
        this.handler.sendMessage(message);
    }

    public void b(int i, int i2, String str, String str2, String str3) {
        a(i, i2, str, str2, str3);
        try {
            this.handler.wait();
        } catch (Throwable th) {
        }
    }

    protected void onMessage(Message message) {
        switch (message.what) {
            case 100:
                b(message);
                return;
            case APICallback.RESPONSE_ERROR /*101*/:
                c(message);
                return;
            default:
                return;
        }
    }
}
