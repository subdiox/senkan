package com.mob.commons.authorize;

import android.content.Context;
import android.util.Base64;
import com.mob.commons.MobProduct;
import com.mob.commons.j;
import com.mob.tools.MobLog;
import com.mob.tools.network.KVPair;
import com.mob.tools.network.NetworkHelper;
import com.mob.tools.network.NetworkHelper.NetworkTimeOut;
import com.mob.tools.utils.Data;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.Hashon;
import com.mob.tools.utils.R;
import com.rekoo.libs.net.URLCons;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public final class a {
    private final String a(Context context, MobProduct mobProduct, HashMap<String, Object> hashMap, boolean z) {
        int parseInt;
        Throwable th;
        ObjectOutputStream objectOutputStream;
        Throwable th2;
        DeviceHelper instance = DeviceHelper.getInstance(context);
        int i = -1;
        try {
            parseInt = R.parseInt(instance.getCarrier());
        } catch (Throwable th3) {
            MobLog.getInstance().w(th3);
            return null;
        }
        HashMap hashMap2 = new HashMap();
        hashMap2.put("adsid", instance.getAdvertisingID());
        hashMap2.put("imei", instance.getIMEI());
        hashMap2.put("serialno", instance.getSerialno());
        hashMap2.put("androidid", instance.getAndroidID());
        hashMap2.put(URLCons.MAC, instance.getMacAddress());
        hashMap2.put("model", instance.getModel());
        hashMap2.put("factory", instance.getManufacturer());
        hashMap2.put("carrier", Integer.valueOf(parseInt));
        hashMap2.put("screensize", instance.getScreenSize());
        hashMap2.put("sysver", instance.getOSVersionName());
        hashMap2.put("plat", Integer.valueOf(1));
        Hashon hashon = new Hashon();
        String encodeToString = Base64.encodeToString(Data.AES128Encode("sdk.commonap.sdk", hashon.fromHashMap(hashMap2)), 2);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new KVPair("m", encodeToString));
        NetworkTimeOut networkTimeOut = new NetworkTimeOut();
        networkTimeOut.readTimout = 30000;
        networkTimeOut.connectionTimeout = 30000;
        HashMap fromJson = hashon.fromJson(new NetworkHelper().httpPost("http://devs.data.mob.com:80/dinfo", arrayList, null, null, networkTimeOut));
        if (fromJson == null) {
            return null;
        }
        encodeToString = (String) fromJson.get("duid");
        if (encodeToString == null || encodeToString.length() <= 0) {
            return null;
        }
        hashMap.put("duid", encodeToString);
        hashMap2.put("carrier", String.valueOf(parseInt));
        hashMap.put("deviceInfo", hashMap2);
        if (!z) {
            return encodeToString;
        }
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(b(context)));
            try {
                objectOutputStream.writeObject(hashMap);
                if (objectOutputStream == null) {
                    return encodeToString;
                }
                try {
                    objectOutputStream.flush();
                    objectOutputStream.close();
                    return encodeToString;
                } catch (Throwable th4) {
                    return encodeToString;
                }
            } catch (Throwable th5) {
                th2 = th5;
                try {
                    MobLog.getInstance().w(th2);
                    if (objectOutputStream != null) {
                        return encodeToString;
                    }
                    try {
                        objectOutputStream.flush();
                        objectOutputStream.close();
                        return encodeToString;
                    } catch (Throwable th6) {
                        return encodeToString;
                    }
                } catch (Throwable th7) {
                    th3 = th7;
                    if (objectOutputStream != null) {
                        try {
                            objectOutputStream.flush();
                            objectOutputStream.close();
                        } catch (Throwable th8) {
                        }
                    }
                    throw th3;
                }
            }
        } catch (Throwable th9) {
            th3 = th9;
            objectOutputStream = null;
            if (objectOutputStream != null) {
                objectOutputStream.flush();
                objectOutputStream.close();
            }
            throw th3;
        }
    }

    private final void a(Context context, MobProduct mobProduct, HashMap<String, Object> hashMap) {
        ObjectOutputStream objectOutputStream;
        Throwable th;
        ObjectOutputStream objectOutputStream2 = null;
        try {
            String str = (String) hashMap.get("duid");
            DeviceHelper instance = DeviceHelper.getInstance(context);
            ArrayList arrayList = new ArrayList();
            arrayList.add(new KVPair("product", mobProduct.getProductTag()));
            arrayList.add(new KVPair("appkey", mobProduct.getProductAppkey()));
            arrayList.add(new KVPair("duid", str));
            arrayList.add(new KVPair("apppkg", instance.getPackageName()));
            arrayList.add(new KVPair("appver", String.valueOf(instance.getAppVersion())));
            arrayList.add(new KVPair("sdkver", String.valueOf(mobProduct.getSdkver())));
            arrayList.add(new KVPair("network", instance.getDetailNetworkTypeForStatic()));
            NetworkTimeOut networkTimeOut = new NetworkTimeOut();
            networkTimeOut.readTimout = 30000;
            networkTimeOut.connectionTimeout = 30000;
            HashMap fromJson = new Hashon().fromJson(new NetworkHelper().httpPost("http://devs.data.mob.com:80/dsign", arrayList, null, null, networkTimeOut));
            if ("true".equals(String.valueOf(fromJson.get("reup"))) && a(context, mobProduct, hashMap, false) == null) {
            }
            if ("200".equals(String.valueOf(fromJson.get("status")))) {
                ((HashMap) ((HashMap) hashMap.get("appInfo")).get(instance.getPackageName())).put(mobProduct.getProductTag(), mobProduct.getProductAppkey());
                try {
                    objectOutputStream = new ObjectOutputStream(new FileOutputStream(b(context)));
                    try {
                        objectOutputStream.writeObject(hashMap);
                        if (objectOutputStream != null) {
                            try {
                                objectOutputStream.flush();
                                objectOutputStream.close();
                            } catch (Throwable th2) {
                            }
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        try {
                            MobLog.getInstance().w(th);
                            if (objectOutputStream != null) {
                                try {
                                    objectOutputStream.flush();
                                    objectOutputStream.close();
                                } catch (Throwable th4) {
                                }
                            }
                        } catch (Throwable th5) {
                            th = th5;
                            objectOutputStream2 = objectOutputStream;
                            if (objectOutputStream2 != null) {
                                try {
                                    objectOutputStream2.flush();
                                    objectOutputStream2.close();
                                } catch (Throwable th6) {
                                }
                            }
                            throw th;
                        }
                    }
                } catch (Throwable th7) {
                    th = th7;
                    if (objectOutputStream2 != null) {
                        objectOutputStream2.flush();
                        objectOutputStream2.close();
                    }
                    throw th;
                }
            }
        } catch (Throwable th8) {
            MobLog.getInstance().w(th8);
        }
    }

    private final boolean a(Context context, HashMap<String, String> hashMap) {
        if (hashMap == null) {
            return true;
        }
        DeviceHelper instance = DeviceHelper.getInstance(context);
        String advertisingID = instance.getAdvertisingID();
        Object obj = hashMap.get("adsid");
        if (advertisingID != null) {
            if (obj == null && advertisingID != null) {
                return true;
            }
            if (!(obj == null || obj.equals(advertisingID))) {
                return true;
            }
        }
        Object obj2 = hashMap.get("imei");
        if (obj2 == null || !obj2.equals(instance.getIMEI())) {
            return true;
        }
        obj2 = hashMap.get("serialno");
        if (obj2 == null || !obj2.equals(instance.getSerialno())) {
            return true;
        }
        obj2 = hashMap.get(URLCons.MAC);
        if (obj2 == null || !obj2.equals(instance.getMacAddress())) {
            return true;
        }
        obj2 = hashMap.get("model");
        if (obj2 == null || !obj2.equals(instance.getModel())) {
            return true;
        }
        obj2 = hashMap.get("factory");
        if (obj2 == null || !obj2.equals(instance.getManufacturer())) {
            return true;
        }
        obj2 = hashMap.get("androidid");
        if (obj2 == null || !obj2.equals(instance.getAndroidID())) {
            return true;
        }
        obj2 = hashMap.get("sysver");
        return obj2 == null || !obj2.equals(instance.getOSVersionName());
    }

    private final File b(Context context) {
        File file = new File(R.getCacheRoot(context), "comm/dbs/.duid");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        return file;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final java.lang.String b(android.content.Context r7, com.mob.commons.MobProduct r8) {
        /*
        r6 = this;
        r1 = 0;
        r0 = r6.b(r7);	 Catch:{ Throwable -> 0x00b2 }
        r2 = r0.exists();	 Catch:{ Throwable -> 0x00b2 }
        if (r2 == 0) goto L_0x00ba;
    L_0x000b:
        r2 = r0.isFile();	 Catch:{ Throwable -> 0x00b2 }
        if (r2 == 0) goto L_0x00ba;
    L_0x0011:
        r3 = new java.io.FileInputStream;	 Catch:{ Throwable -> 0x0096, all -> 0x00aa }
        r3.<init>(r0);	 Catch:{ Throwable -> 0x0096, all -> 0x00aa }
        r2 = new java.io.ObjectInputStream;	 Catch:{ Throwable -> 0x0096, all -> 0x00aa }
        r2.<init>(r3);	 Catch:{ Throwable -> 0x0096, all -> 0x00aa }
        r0 = r2.readObject();	 Catch:{ Throwable -> 0x00d2 }
        r0 = (java.util.HashMap) r0;	 Catch:{ Throwable -> 0x00d2 }
        if (r2 == 0) goto L_0x0026;
    L_0x0023:
        r2.close();	 Catch:{ Throwable -> 0x00c6 }
    L_0x0026:
        if (r0 != 0) goto L_0x00db;
    L_0x0028:
        r0 = r6.c(r7, r8);
        r3 = r0;
    L_0x002d:
        if (r3 == 0) goto L_0x00d9;
    L_0x002f:
        r0 = "duid";
        r0 = r3.get(r0);
        r0 = (java.lang.String) r0;
        if (r0 == 0) goto L_0x0095;
    L_0x0039:
        r1 = "deviceInfo";
        r1 = r3.get(r1);	 Catch:{ Throwable -> 0x00bd }
        r1 = (java.util.HashMap) r1;	 Catch:{ Throwable -> 0x00bd }
        r1 = r6.a(r7, r1);	 Catch:{ Throwable -> 0x00bd }
        if (r1 == 0) goto L_0x00d6;
    L_0x0047:
        r1 = 1;
        r1 = r6.a(r7, r8, r3, r1);	 Catch:{ Throwable -> 0x00bd }
        if (r1 == 0) goto L_0x00d6;
    L_0x004e:
        r0 = "appInfo";
        r0 = r3.get(r0);	 Catch:{ Throwable -> 0x00cb }
        r0 = (java.util.HashMap) r0;	 Catch:{ Throwable -> 0x00cb }
        if (r0 != 0) goto L_0x00d4;
    L_0x0058:
        r0 = new java.util.HashMap;	 Catch:{ Throwable -> 0x00cb }
        r0.<init>();	 Catch:{ Throwable -> 0x00cb }
        r2 = "appInfo";
        r3.put(r2, r0);	 Catch:{ Throwable -> 0x00cb }
        r2 = r0;
    L_0x0063:
        r0 = com.mob.tools.utils.DeviceHelper.getInstance(r7);	 Catch:{ Throwable -> 0x00cb }
        r4 = r0.getPackageName();	 Catch:{ Throwable -> 0x00cb }
        r0 = r2.get(r4);	 Catch:{ Throwable -> 0x00cb }
        r0 = (java.util.HashMap) r0;	 Catch:{ Throwable -> 0x00cb }
        if (r0 != 0) goto L_0x007b;
    L_0x0073:
        r0 = new java.util.HashMap;	 Catch:{ Throwable -> 0x00cb }
        r0.<init>();	 Catch:{ Throwable -> 0x00cb }
        r2.put(r4, r0);	 Catch:{ Throwable -> 0x00cb }
    L_0x007b:
        r2 = r8.getProductTag();	 Catch:{ Throwable -> 0x00cb }
        r0 = r0.get(r2);	 Catch:{ Throwable -> 0x00cb }
        r0 = (java.lang.String) r0;	 Catch:{ Throwable -> 0x00cb }
        if (r0 == 0) goto L_0x0091;
    L_0x0087:
        r2 = r8.getProductAppkey();	 Catch:{ Throwable -> 0x00cb }
        r0 = r0.equals(r2);	 Catch:{ Throwable -> 0x00cb }
        if (r0 != 0) goto L_0x0094;
    L_0x0091:
        r6.a(r7, r8, r3);	 Catch:{ Throwable -> 0x00cb }
    L_0x0094:
        r0 = r1;
    L_0x0095:
        return r0;
    L_0x0096:
        r0 = move-exception;
        r2 = r1;
    L_0x0098:
        r3 = com.mob.tools.MobLog.getInstance();	 Catch:{ all -> 0x00d0 }
        r3.w(r0);	 Catch:{ all -> 0x00d0 }
        if (r2 == 0) goto L_0x00ba;
    L_0x00a1:
        r2.close();	 Catch:{ Throwable -> 0x00a6 }
        r0 = r1;
        goto L_0x0026;
    L_0x00a6:
        r0 = move-exception;
        r0 = r1;
        goto L_0x0026;
    L_0x00aa:
        r0 = move-exception;
        r2 = r1;
    L_0x00ac:
        if (r2 == 0) goto L_0x00b1;
    L_0x00ae:
        r2.close();	 Catch:{ Throwable -> 0x00c9 }
    L_0x00b1:
        throw r0;	 Catch:{ Throwable -> 0x00b2 }
    L_0x00b2:
        r0 = move-exception;
        r2 = com.mob.tools.MobLog.getInstance();
        r2.w(r0);
    L_0x00ba:
        r0 = r1;
        goto L_0x0026;
    L_0x00bd:
        r1 = move-exception;
    L_0x00be:
        r2 = com.mob.tools.MobLog.getInstance();
        r2.w(r1);
        goto L_0x0095;
    L_0x00c6:
        r2 = move-exception;
        goto L_0x0026;
    L_0x00c9:
        r2 = move-exception;
        goto L_0x00b1;
    L_0x00cb:
        r0 = move-exception;
        r5 = r0;
        r0 = r1;
        r1 = r5;
        goto L_0x00be;
    L_0x00d0:
        r0 = move-exception;
        goto L_0x00ac;
    L_0x00d2:
        r0 = move-exception;
        goto L_0x0098;
    L_0x00d4:
        r2 = r0;
        goto L_0x0063;
    L_0x00d6:
        r1 = r0;
        goto L_0x004e;
    L_0x00d9:
        r0 = r1;
        goto L_0x0095;
    L_0x00db:
        r3 = r0;
        goto L_0x002d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mob.commons.authorize.a.b(android.content.Context, com.mob.commons.MobProduct):java.lang.String");
    }

    private HashMap<String, Object> c(Context context) {
        Throwable th;
        HashMap<String, Object> hashMap = new HashMap();
        ObjectOutputStream objectOutputStream = null;
        try {
            DeviceHelper instance = DeviceHelper.getInstance(context);
            String model = instance.getModel();
            String str = model == null ? "" : model;
            model = instance.getIMEI();
            String str2 = model == null ? "" : model;
            model = instance.getMacAddress();
            String str3 = model == null ? "" : model;
            model = instance.getSerialno();
            if (model == null) {
                model = "";
            }
            hashMap.put("duid", Data.byteToHex(Data.SHA1(str + ":" + str2 + ":" + str3 + ":" + model)));
            HashMap hashMap2 = new HashMap();
            hashMap2.put("adsid", instance.getAdvertisingID());
            hashMap2.put("imei", instance.getIMEI());
            hashMap2.put("serialno", instance.getSerialno());
            hashMap2.put("androidid", instance.getAndroidID());
            hashMap2.put(URLCons.MAC, instance.getMacAddress());
            hashMap2.put("model", instance.getModel());
            hashMap2.put("factory", instance.getManufacturer());
            hashMap2.put("carrier", instance.getCarrier());
            hashMap2.put("screensize", instance.getScreenSize());
            hashMap2.put("sysver", instance.getOSVersionName());
            hashMap2.put("plat", Integer.valueOf(1));
            hashMap.put("deviceInfo", hashMap2);
            ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(new FileOutputStream(b(context)));
            try {
                objectOutputStream2.writeObject(hashMap);
                if (objectOutputStream2 != null) {
                    try {
                        objectOutputStream2.flush();
                        objectOutputStream2.close();
                    } catch (Throwable th2) {
                    }
                }
            } catch (Throwable th3) {
                th = th3;
                objectOutputStream = objectOutputStream2;
                if (objectOutputStream != null) {
                    objectOutputStream.flush();
                    objectOutputStream.close();
                }
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            MobLog.getInstance().w(th);
            if (objectOutputStream != null) {
                objectOutputStream.flush();
                objectOutputStream.close();
            }
            return hashMap;
        }
        return hashMap;
    }

    private final HashMap<String, Object> c(Context context, MobProduct mobProduct) {
        int parseInt;
        Throwable th;
        ObjectOutputStream objectOutputStream;
        DeviceHelper instance = DeviceHelper.getInstance(context);
        int i = -1;
        try {
            parseInt = R.parseInt(instance.getCarrier());
        } catch (Throwable th2) {
            MobLog.getInstance().w(th2);
            return null;
        }
        HashMap hashMap = new HashMap();
        hashMap.put("adsid", instance.getAdvertisingID());
        hashMap.put("imei", instance.getIMEI());
        hashMap.put("serialno", instance.getSerialno());
        hashMap.put("androidid", instance.getAndroidID());
        hashMap.put(URLCons.MAC, instance.getMacAddress());
        hashMap.put("model", instance.getModel());
        hashMap.put("factory", instance.getManufacturer());
        hashMap.put("carrier", Integer.valueOf(parseInt));
        hashMap.put("screensize", instance.getScreenSize());
        hashMap.put("sysver", instance.getOSVersionName());
        hashMap.put("plat", Integer.valueOf(1));
        Hashon hashon = new Hashon();
        String encodeToString = Base64.encodeToString(Data.AES128Encode("sdk.commonap.sdk", hashon.fromHashMap(hashMap)), 2);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new KVPair("m", encodeToString));
        NetworkTimeOut networkTimeOut = new NetworkTimeOut();
        networkTimeOut.readTimout = 30000;
        networkTimeOut.connectionTimeout = 30000;
        HashMap fromJson = hashon.fromJson(new NetworkHelper().httpPost("http://devs.data.mob.com:80/dinfo", arrayList, null, null, networkTimeOut));
        if (fromJson == null) {
            return null;
        }
        encodeToString = (String) fromJson.get("duid");
        if (encodeToString == null || encodeToString.length() <= 0) {
            return null;
        }
        HashMap<String, Object> hashMap2 = new HashMap();
        try {
            hashMap2.put("duid", encodeToString);
            hashMap.put("carrier", String.valueOf(parseInt));
            hashMap2.put("deviceInfo", hashMap);
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(b(context)));
            try {
                objectOutputStream.writeObject(hashMap2);
                if (objectOutputStream != null) {
                    try {
                        objectOutputStream.flush();
                        objectOutputStream.close();
                    } catch (Throwable th3) {
                    }
                }
            } catch (Throwable th4) {
                th2 = th4;
                try {
                    MobLog.getInstance().w(th2);
                    if (objectOutputStream != null) {
                        try {
                            objectOutputStream.flush();
                            objectOutputStream.close();
                        } catch (Throwable th5) {
                        }
                    }
                    return hashMap2;
                } catch (Throwable th6) {
                    th2 = th6;
                    if (objectOutputStream != null) {
                        try {
                            objectOutputStream.flush();
                            objectOutputStream.close();
                        } catch (Throwable th7) {
                        }
                    }
                    throw th2;
                }
            }
        } catch (Throwable th8) {
            th2 = th8;
            objectOutputStream = null;
            if (objectOutputStream != null) {
                objectOutputStream.flush();
                objectOutputStream.close();
            }
            throw th2;
        }
        return hashMap2;
    }

    public final synchronized String a(Context context) {
        ObjectInputStream objectInputStream;
        HashMap hashMap;
        Throwable th;
        try {
            File b = b(context);
            if (b.exists() && b.isFile()) {
                try {
                    objectInputStream = new ObjectInputStream(new FileInputStream(b));
                    try {
                        hashMap = (HashMap) objectInputStream.readObject();
                        if (objectInputStream != null) {
                            try {
                                objectInputStream.close();
                            } catch (Throwable th2) {
                            }
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        try {
                            MobLog.getInstance().w(th);
                            if (objectInputStream != null) {
                                try {
                                    objectInputStream.close();
                                    hashMap = null;
                                } catch (Throwable th4) {
                                    hashMap = null;
                                }
                                if (hashMap == null) {
                                    hashMap = c(context);
                                }
                                return (String) hashMap.get("duid");
                            }
                            hashMap = null;
                            if (hashMap == null) {
                                hashMap = c(context);
                            }
                            return (String) hashMap.get("duid");
                        } catch (Throwable th5) {
                            th = th5;
                            if (objectInputStream != null) {
                                try {
                                    objectInputStream.close();
                                } catch (Throwable th6) {
                                }
                            }
                            throw th;
                        }
                    }
                } catch (Throwable th7) {
                    th = th7;
                    objectInputStream = null;
                    if (objectInputStream != null) {
                        objectInputStream.close();
                    }
                    throw th;
                }
                if (hashMap == null) {
                    hashMap = c(context);
                }
            }
        } catch (Throwable th8) {
            MobLog.getInstance().w(th8);
        }
        hashMap = null;
        if (hashMap == null) {
            hashMap = c(context);
        }
        return (String) hashMap.get("duid");
    }

    public final synchronized String a(Context context, MobProduct mobProduct) {
        String[] strArr;
        strArr = new String[1];
        j.a(new File(R.getCacheRoot(context), "comm/locks/.globalLock"), true, new b(this, strArr, context, mobProduct));
        return strArr[0];
    }
}
