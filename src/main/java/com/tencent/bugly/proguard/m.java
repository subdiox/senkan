package com.tencent.bugly.proguard;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.kayac.lobi.libnakamap.utils.NotificationUtil;
import com.tencent.bugly.CrashModule;
import com.tencent.bugly.crashreport.common.info.a;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: BUGLY */
public final class m {
    public static final long a = System.currentTimeMillis();
    private static m b = null;
    private Context c;
    private String d = a.b().d;
    private Map<Integer, Map<String, l>> e = new HashMap();
    private SharedPreferences f;

    private m(Context context) {
        this.c = context;
        this.f = context.getSharedPreferences("crashrecord", 0);
    }

    public static synchronized m a(Context context) {
        m mVar;
        synchronized (m.class) {
            if (b == null) {
                b = new m(context);
            }
            mVar = b;
        }
        return mVar;
    }

    public static synchronized m a() {
        m mVar;
        synchronized (m.class) {
            mVar = b;
        }
        return mVar;
    }

    private synchronized boolean b(int i) {
        boolean z;
        try {
            List<l> c = c(i);
            if (c == null) {
                z = false;
            } else {
                long currentTimeMillis = System.currentTimeMillis();
                List arrayList = new ArrayList();
                Collection arrayList2 = new ArrayList();
                for (l lVar : c) {
                    if (lVar.b != null && lVar.b.equalsIgnoreCase(this.d) && lVar.d > 0) {
                        arrayList.add(lVar);
                    }
                    if (lVar.c + NotificationUtil.PROMOTION_NOTIFICATION_INTERVAL < currentTimeMillis) {
                        arrayList2.add(lVar);
                    }
                }
                Collections.sort(arrayList);
                if (arrayList.size() < 2) {
                    c.removeAll(arrayList2);
                    a(i, (List) c);
                    z = false;
                } else if (arrayList.size() <= 0 || ((l) arrayList.get(arrayList.size() - 1)).c + NotificationUtil.PROMOTION_NOTIFICATION_INTERVAL >= currentTimeMillis) {
                    z = true;
                } else {
                    c.clear();
                    a(i, (List) c);
                    z = false;
                }
            }
        } catch (Exception e) {
            w.e("isFrequentCrash failed", new Object[0]);
            z = false;
        }
        return z;
    }

    public final synchronized void a(int i, final int i2) {
        v.a().a(new Runnable(this, CrashModule.MODULE_ID) {
            private /* synthetic */ m c;

            public final void run() {
                try {
                    if (!TextUtils.isEmpty(this.c.d)) {
                        l lVar;
                        l lVar2;
                        List a = this.c.c(CrashModule.MODULE_ID);
                        List arrayList;
                        if (a == null) {
                            arrayList = new ArrayList();
                        } else {
                            arrayList = a;
                        }
                        if (this.c.e.get(Integer.valueOf(CrashModule.MODULE_ID)) == null) {
                            this.c.e.put(Integer.valueOf(CrashModule.MODULE_ID), new HashMap());
                        }
                        if (((Map) this.c.e.get(Integer.valueOf(CrashModule.MODULE_ID))).get(this.c.d) == null) {
                            l lVar3 = new l();
                            lVar3.a = (long) CrashModule.MODULE_ID;
                            lVar3.g = m.a;
                            lVar3.b = this.c.d;
                            lVar3.f = a.b().j;
                            a.b().getClass();
                            lVar3.e = "2.4.1";
                            lVar3.c = System.currentTimeMillis();
                            lVar3.d = i2;
                            ((Map) this.c.e.get(Integer.valueOf(CrashModule.MODULE_ID))).put(this.c.d, lVar3);
                            lVar = lVar3;
                        } else {
                            lVar2 = (l) ((Map) this.c.e.get(Integer.valueOf(CrashModule.MODULE_ID))).get(this.c.d);
                            lVar2.d = i2;
                            lVar = lVar2;
                        }
                        Collection arrayList2 = new ArrayList();
                        int i = 0;
                        for (l lVar22 : r4) {
                            if (lVar22.g == lVar.g && lVar22.b != null && lVar22.b.equalsIgnoreCase(lVar.b)) {
                                i = 1;
                                lVar22.d = lVar.d;
                            }
                            if ((lVar22.e != null && !lVar22.e.equalsIgnoreCase(lVar.e)) || ((lVar22.f != null && !lVar22.f.equalsIgnoreCase(lVar.f)) || lVar22.d <= 0)) {
                                arrayList2.add(lVar22);
                            }
                        }
                        r4.removeAll(arrayList2);
                        if (i == 0) {
                            r4.add(lVar);
                        }
                        this.c.a(CrashModule.MODULE_ID, (List) r4);
                    }
                } catch (Exception e) {
                    w.e("saveCrashRecord failed", new Object[0]);
                }
            }
        });
    }

    private synchronized <T extends List<?>> T c(int i) {
        ObjectInputStream objectInputStream;
        T t;
        Throwable th;
        try {
            File file = new File(this.c.getDir("crashrecord", 0), i);
            if (file.exists()) {
                ObjectInputStream objectInputStream2;
                try {
                    objectInputStream2 = new ObjectInputStream(new FileInputStream(file));
                } catch (IOException e) {
                    objectInputStream = null;
                    try {
                        w.a("open record file error", new Object[0]);
                        if (objectInputStream != null) {
                            objectInputStream.close();
                        }
                        t = null;
                        return t;
                    } catch (Throwable th2) {
                        Throwable th3 = th2;
                        objectInputStream2 = objectInputStream;
                        th = th3;
                        if (objectInputStream2 != null) {
                            objectInputStream2.close();
                        }
                        throw th;
                    }
                } catch (ClassNotFoundException e2) {
                    objectInputStream2 = null;
                    try {
                        w.a("get object error", new Object[0]);
                        if (objectInputStream2 != null) {
                            objectInputStream2.close();
                        }
                        t = null;
                        return t;
                    } catch (Throwable th4) {
                        th = th4;
                        if (objectInputStream2 != null) {
                            objectInputStream2.close();
                        }
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    objectInputStream2 = null;
                    if (objectInputStream2 != null) {
                        objectInputStream2.close();
                    }
                    throw th;
                }
                try {
                    List list = (List) objectInputStream2.readObject();
                    objectInputStream2.close();
                } catch (IOException e3) {
                    objectInputStream = objectInputStream2;
                    w.a("open record file error", new Object[0]);
                    if (objectInputStream != null) {
                        objectInputStream.close();
                    }
                    t = null;
                    return t;
                } catch (ClassNotFoundException e4) {
                    w.a("get object error", new Object[0]);
                    if (objectInputStream2 != null) {
                        objectInputStream2.close();
                    }
                    t = null;
                    return t;
                }
            }
            t = null;
        } catch (Exception e5) {
            w.e("readCrashRecord error", new Object[0]);
        }
        return t;
    }

    private synchronized <T extends List<?>> void a(int i, T t) {
        ObjectOutputStream objectOutputStream;
        IOException e;
        Throwable th;
        if (t != null) {
            try {
                try {
                    objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File(this.c.getDir("crashrecord", 0), i)));
                    try {
                        objectOutputStream.writeObject(t);
                        objectOutputStream.close();
                    } catch (IOException e2) {
                        e = e2;
                        try {
                            e.printStackTrace();
                            w.a("open record file error", new Object[0]);
                            if (objectOutputStream != null) {
                                objectOutputStream.close();
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            if (objectOutputStream != null) {
                                objectOutputStream.close();
                            }
                            throw th;
                        }
                    }
                } catch (IOException e3) {
                    e = e3;
                    objectOutputStream = null;
                    e.printStackTrace();
                    w.a("open record file error", new Object[0]);
                    if (objectOutputStream != null) {
                        objectOutputStream.close();
                    }
                } catch (Throwable th3) {
                    th = th3;
                    objectOutputStream = null;
                    if (objectOutputStream != null) {
                        objectOutputStream.close();
                    }
                    throw th;
                }
            } catch (Exception e4) {
                w.e("writeCrashRecord error", new Object[0]);
            }
        }
    }

    public final synchronized boolean a(final int i) {
        boolean z = true;
        synchronized (this) {
            try {
                z = this.f.getBoolean(i + "_" + this.d, true);
                v.a().a(new Runnable(this) {
                    private /* synthetic */ m b;

                    public final void run() {
                        this.b.f.edit().putBoolean(i + "_" + this.b.d, !this.b.b(i)).commit();
                    }
                });
            } catch (Exception e) {
                w.e("canInit error", new Object[0]);
            }
        }
        return z;
    }
}
