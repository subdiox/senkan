package com.mob.commons;

import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Base64;
import com.kayac.lobi.libnakamap.net.APIDef.PostAppData.RequestKey;
import com.mob.commons.authorize.DeviceAuthorizer;
import com.mob.tools.MobHandlerThread;
import com.mob.tools.MobLog;
import com.mob.tools.network.KVPair;
import com.mob.tools.network.NetworkHelper.NetworkTimeOut;
import com.mob.tools.utils.Data;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.Hashon;
import com.mob.tools.utils.R;
import com.mob.tools.utils.SQLiteHelper;
import com.mob.tools.utils.SQLiteHelper.SingleTableDB;
import com.rekoo.libs.net.URLCons;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.zip.GZIPOutputStream;

public class g implements Callback {
    private static g a;
    private Context b;
    private Handler c;
    private SingleTableDB d;
    private Hashon e = new Hashon();
    private DeviceHelper f;

    private g(Context context) {
        this.b = context.getApplicationContext();
        this.f = DeviceHelper.getInstance(context);
        MobHandlerThread mobHandlerThread = new MobHandlerThread();
        mobHandlerThread.start();
        this.c = new Handler(mobHandlerThread.getLooper(), this);
        File file = new File(R.getCacheRoot(context), "comm/dbs/.dh");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        this.d = SQLiteHelper.getDatabase(file.getAbsolutePath(), "DataHeap_1");
        this.d.addField(URLCons.TIME, "text", true);
        this.d.addField(RequestKey.DATA, "text", true);
        this.c.sendEmptyMessage(1);
    }

    public static synchronized g a(Context context) {
        g gVar;
        synchronized (g.class) {
            if (a == null) {
                a = new g(context);
            }
            gVar = a;
        }
        return gVar;
    }

    private String a(String str) {
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
        gZIPOutputStream.write(str.getBytes("utf-8"));
        gZIPOutputStream.flush();
        gZIPOutputStream.close();
        return Base64.encodeToString(Data.AES128Encode("sdk.commonca.sdk".getBytes("UTF-8"), byteArrayOutputStream.toByteArray()), 2);
    }

    private void a() {
        String networkType = this.f.getNetworkType();
        if (networkType != null && !"none".equals(networkType)) {
            j.a(new File(R.getCacheRoot(this.b), "comm/locks/.dhlock"), true, new i(this));
        }
    }

    private boolean a(ArrayList<String[]> arrayList) {
        try {
            f a = f.a(this.b);
            ArrayList a2 = a.a();
            if (a2.isEmpty()) {
                return false;
            }
            HashMap hashMap = new HashMap();
            hashMap.put("plat", Integer.valueOf(this.f.getPlatformCode()));
            hashMap.put(URLCons.DEVICE, this.f.getDeviceKey());
            hashMap.put(URLCons.MAC, this.f.getMacAddress());
            hashMap.put("model", this.f.getModel());
            hashMap.put("duid", DeviceAuthorizer.authorize(this.b, null));
            hashMap.put("imei", this.f.getIMEI());
            hashMap.put("serialno", this.f.getSerialno());
            hashMap.put("networktype", this.f.getDetailNetworkTypeForStatic());
            ArrayList arrayList2 = new ArrayList();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                arrayList2.add(this.e.fromJson(((String[]) it.next())[1]));
            }
            hashMap.put("datas", arrayList2);
            arrayList2 = new ArrayList();
            arrayList2.add(new KVPair("appkey", ((MobProduct) a2.get(0)).getProductAppkey()));
            arrayList2.add(new KVPair("m", a(this.e.fromHashMap(hashMap))));
            ArrayList arrayList3 = new ArrayList();
            arrayList3.add(new KVPair("User-Identity", a.a(a2)));
            NetworkTimeOut networkTimeOut = new NetworkTimeOut();
            networkTimeOut.readTimout = 30000;
            networkTimeOut.connectionTimeout = 30000;
            return "200".equals(String.valueOf(this.e.fromJson(a.httpPost("http://c.data.mob.com/cdata", arrayList2, null, arrayList3, networkTimeOut)).get("status")));
        } catch (Throwable th) {
            MobLog.getInstance().w(th);
            return false;
        }
    }

    private ArrayList<String[]> b() {
        ArrayList<String[]> arrayList = new ArrayList();
        try {
            Cursor query = SQLiteHelper.query(this.d, new String[]{URLCons.TIME, RequestKey.DATA}, null, null, null);
            if (query != null) {
                if (query.moveToFirst()) {
                    arrayList.add(new String[]{query.getString(0), query.getString(1)});
                    while (query.moveToNext()) {
                        arrayList.add(new String[]{query.getString(0), query.getString(1)});
                    }
                }
                query.close();
            }
        } catch (Throwable th) {
            MobLog.getInstance().w(th);
        }
        return arrayList;
    }

    private void b(ArrayList<String[]> arrayList) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                String[] strArr = (String[]) it.next();
                if (stringBuilder.length() > 0) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append('\'').append(strArr[0]).append('\'');
            }
            SQLiteHelper.delete(this.d, "time in (" + stringBuilder.toString() + ")", null);
        } catch (Throwable th) {
            MobLog.getInstance().w(th);
        }
    }

    private void b(HashMap<String, Object> hashMap) {
        j.a(new File(R.getCacheRoot(this.b), "comm/locks/.dhlock"), true, new h(this, hashMap));
    }

    public synchronized void a(HashMap<String, Object> hashMap) {
        Message message = new Message();
        message.what = 2;
        message.obj = hashMap;
        this.c.sendMessage(message);
    }

    public boolean handleMessage(Message message) {
        switch (message.what) {
            case 1:
                a();
                this.c.sendEmptyMessageDelayed(1, 10000);
                break;
            case 2:
                b((HashMap) message.obj);
                break;
        }
        return false;
    }
}
