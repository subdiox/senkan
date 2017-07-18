package com.tencent.bugly.proguard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: BUGLY */
public final class o {
    private static o a = null;
    private static p b = null;
    private static boolean c = false;

    /* compiled from: BUGLY */
    class a extends Thread {
        private int a;
        private n b;
        private String c;
        private ContentValues d;
        private boolean e;
        private String[] f;
        private String g;
        private String[] h;
        private String i;
        private String j;
        private String k;
        private String l;
        private String m;
        private String[] n;
        private int o;
        private String p;
        private byte[] q;
        private /* synthetic */ o r;

        public a(o oVar, int i, n nVar) {
            this.r = oVar;
            this.a = i;
            this.b = nVar;
        }

        public final void a(boolean z, String str, String[] strArr, String str2, String[] strArr2, String str3, String str4, String str5, String str6) {
            this.e = z;
            this.c = str;
            this.f = strArr;
            this.g = str2;
            this.h = strArr2;
            this.i = str3;
            this.j = str4;
            this.k = str5;
            this.l = str6;
        }

        public final void a(int i, String str, byte[] bArr) {
            this.o = i;
            this.p = str;
            this.q = bArr;
        }

        public final void run() {
            switch (this.a) {
                case 1:
                    this.r.a(this.c, this.d, this.b);
                    return;
                case 2:
                    this.r.a(this.c, this.m, this.n, this.b);
                    return;
                case 3:
                    this.r.a(this.e, this.c, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.b);
                    return;
                case 4:
                    this.r.a(this.o, this.p, this.q, this.b);
                    return;
                case 5:
                    this.r.a(this.o, this.b);
                    return;
                case 6:
                    this.r.a(this.o, this.p, this.b);
                    return;
                default:
                    return;
            }
        }
    }

    private o(Context context, List<com.tencent.bugly.a> list) {
        b = new p(context, list);
    }

    public static synchronized o a(Context context, List<com.tencent.bugly.a> list) {
        o oVar;
        synchronized (o.class) {
            if (a == null) {
                a = new o(context, list);
            }
            oVar = a;
        }
        return oVar;
    }

    public static synchronized o a() {
        o oVar;
        synchronized (o.class) {
            oVar = a;
        }
        return oVar;
    }

    public final long a(String str, ContentValues contentValues, n nVar, boolean z) {
        return a(str, contentValues, null);
    }

    public final Cursor a(String str, String[] strArr, String str2, String[] strArr2, n nVar, boolean z) {
        return a(false, str, strArr, str2, null, null, null, null, null, null);
    }

    public final int a(String str, String str2, String[] strArr, n nVar, boolean z) {
        return a(str, str2, null, null);
    }

    private synchronized long a(String str, ContentValues contentValues, n nVar) {
        long j = 0;
        synchronized (this) {
            try {
                SQLiteDatabase writableDatabase = b.getWritableDatabase();
                if (!(writableDatabase == null || contentValues == null)) {
                    long replace = writableDatabase.replace(str, "_id", contentValues);
                    if (replace >= 0) {
                        w.c("[Database] insert %s success.", str);
                    } else {
                        w.d("[Database] replace %s error.", str);
                    }
                    j = replace;
                }
                if (nVar != null) {
                    Long.valueOf(j);
                }
            } catch (Throwable th) {
                if (nVar != null) {
                    Long.valueOf(0);
                }
            }
        }
        return j;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized android.database.Cursor a(boolean r12, java.lang.String r13, java.lang.String[] r14, java.lang.String r15, java.lang.String[] r16, java.lang.String r17, java.lang.String r18, java.lang.String r19, java.lang.String r20, com.tencent.bugly.proguard.n r21) {
        /*
        r11 = this;
        monitor-enter(r11);
        r10 = 0;
        r0 = b;	 Catch:{ Throwable -> 0x0020 }
        r0 = r0.getWritableDatabase();	 Catch:{ Throwable -> 0x0020 }
        if (r0 == 0) goto L_0x0035;
    L_0x000a:
        r1 = r12;
        r2 = r13;
        r3 = r14;
        r4 = r15;
        r5 = r16;
        r6 = r17;
        r7 = r18;
        r8 = r19;
        r9 = r20;
        r0 = r0.query(r1, r2, r3, r4, r5, r6, r7, r8, r9);	 Catch:{ Throwable -> 0x0020 }
    L_0x001c:
        if (r21 == 0) goto L_0x001e;
    L_0x001e:
        monitor-exit(r11);
        return r0;
    L_0x0020:
        r0 = move-exception;
        r1 = com.tencent.bugly.proguard.w.a(r0);	 Catch:{ all -> 0x002e }
        if (r1 != 0) goto L_0x002a;
    L_0x0027:
        r0.printStackTrace();	 Catch:{ all -> 0x002e }
    L_0x002a:
        if (r21 == 0) goto L_0x0033;
    L_0x002c:
        r0 = r10;
        goto L_0x001e;
    L_0x002e:
        r0 = move-exception;
        throw r0;	 Catch:{ all -> 0x0030 }
    L_0x0030:
        r0 = move-exception;
        monitor-exit(r11);
        throw r0;
    L_0x0033:
        r0 = r10;
        goto L_0x001e;
    L_0x0035:
        r0 = r10;
        goto L_0x001c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.o.a(boolean, java.lang.String, java.lang.String[], java.lang.String, java.lang.String[], java.lang.String, java.lang.String, java.lang.String, java.lang.String, com.tencent.bugly.proguard.n):android.database.Cursor");
    }

    private synchronized int a(String str, String str2, String[] strArr, n nVar) {
        int i = 0;
        synchronized (this) {
            try {
                SQLiteDatabase writableDatabase = b.getWritableDatabase();
                if (writableDatabase != null) {
                    i = writableDatabase.delete(str, str2, strArr);
                }
                if (nVar != null) {
                    Integer.valueOf(i);
                }
            } catch (Throwable th) {
                if (nVar != null) {
                    Integer.valueOf(0);
                }
            }
        }
        return i;
    }

    public final boolean a(int i, String str, byte[] bArr, n nVar, boolean z) {
        if (z) {
            return a(i, str, bArr, null);
        }
        Runnable aVar = new a(this, 4, null);
        aVar.a(i, str, bArr);
        v.a().a(aVar);
        return true;
    }

    public final Map<String, byte[]> a(int i, n nVar, boolean z) {
        return a(i, null);
    }

    public final boolean a(int i, String str, n nVar, boolean z) {
        return a(555, str, null);
    }

    private boolean a(int i, String str, byte[] bArr, n nVar) {
        boolean z = false;
        try {
            q qVar = new q();
            qVar.a = (long) i;
            qVar.f = str;
            qVar.e = System.currentTimeMillis();
            qVar.g = bArr;
            z = b(qVar);
            if (nVar != null) {
                Boolean.valueOf(z);
            }
        } catch (Throwable th) {
            if (nVar != null) {
                Boolean.valueOf(z);
            }
        }
        return z;
    }

    private Map<String, byte[]> a(int i, n nVar) {
        Map<String, byte[]> map;
        Throwable th;
        try {
            List<q> c = c(i);
            Map<String, byte[]> hashMap = new HashMap();
            try {
                for (q qVar : c) {
                    Object obj = qVar.g;
                    if (obj != null) {
                        hashMap.put(qVar.f, obj);
                    }
                }
                if (nVar != null) {
                    return hashMap;
                }
                return hashMap;
            } catch (Throwable th2) {
                Throwable th3 = th2;
                map = hashMap;
                th = th3;
                if (!w.a(th)) {
                    th.printStackTrace();
                }
                return nVar == null ? map : map;
            }
        } catch (Throwable th22) {
            th = th22;
            map = null;
            if (w.a(th)) {
                th.printStackTrace();
            }
            if (nVar == null) {
            }
        }
    }

    public final synchronized boolean a(q qVar) {
        boolean z = false;
        synchronized (this) {
            if (qVar != null) {
                try {
                    SQLiteDatabase writableDatabase = b.getWritableDatabase();
                    if (writableDatabase != null) {
                        ContentValues c = c(qVar);
                        if (c != null) {
                            long replace = writableDatabase.replace("t_lr", "_id", c);
                            if (replace >= 0) {
                                w.c("[Database] insert %s success.", "t_lr");
                                qVar.a = replace;
                                z = true;
                            }
                        }
                    }
                } catch (Throwable th) {
                    if (!w.a(th)) {
                        th.printStackTrace();
                    }
                }
            }
        }
        return z;
    }

    private synchronized boolean b(q qVar) {
        boolean z = false;
        synchronized (this) {
            if (qVar != null) {
                try {
                    SQLiteDatabase writableDatabase = b.getWritableDatabase();
                    if (writableDatabase != null) {
                        ContentValues d = d(qVar);
                        if (d != null) {
                            long replace = writableDatabase.replace("t_pf", "_id", d);
                            if (replace >= 0) {
                                w.c("[Database] insert %s success.", "t_pf");
                                qVar.a = replace;
                                z = true;
                            }
                        }
                    }
                } catch (Throwable th) {
                    if (!w.a(th)) {
                        th.printStackTrace();
                    }
                }
            }
        }
        return z;
    }

    public final synchronized List<q> a(int i) {
        Throwable th;
        Cursor cursor;
        List<q> list;
        SQLiteDatabase writableDatabase = b.getWritableDatabase();
        if (writableDatabase != null) {
            String str;
            Cursor cursor2;
            if (i >= 0) {
                try {
                    str = "_tp = " + i;
                } catch (Throwable th2) {
                    th = th2;
                    cursor2 = null;
                    if (cursor2 != null) {
                        cursor2.close();
                    }
                    throw th;
                }
            }
            str = null;
            cursor2 = writableDatabase.query("t_lr", null, str, null, null, null, null);
            if (cursor2 == null) {
                if (cursor2 != null) {
                    cursor2.close();
                }
                list = null;
            } else {
                try {
                    StringBuilder stringBuilder = new StringBuilder();
                    List<q> arrayList = new ArrayList();
                    while (cursor2.moveToNext()) {
                        q a = a(cursor2);
                        if (a != null) {
                            arrayList.add(a);
                        } else {
                            try {
                                stringBuilder.append(" or _id").append(" = ").append(cursor2.getLong(cursor2.getColumnIndex("_id")));
                            } catch (Throwable th3) {
                                th = th3;
                            }
                        }
                    }
                    str = stringBuilder.toString();
                    if (str.length() > 0) {
                        int delete = writableDatabase.delete("t_lr", str.substring(4), null);
                        w.d("[Database] deleted %s illegal data %d", "t_lr", Integer.valueOf(delete));
                    }
                    if (cursor2 != null) {
                        cursor2.close();
                    }
                    list = arrayList;
                } catch (Throwable th32) {
                    th = th32;
                }
            }
        }
        list = null;
        return list;
    }

    public final synchronized void a(List<q> list) {
        if (list != null) {
            if (list.size() != 0) {
                SQLiteDatabase writableDatabase = b.getWritableDatabase();
                if (writableDatabase != null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (q qVar : list) {
                        stringBuilder.append(" or _id").append(" = ").append(qVar.a);
                    }
                    String stringBuilder2 = stringBuilder.toString();
                    if (stringBuilder2.length() > 0) {
                        stringBuilder2 = stringBuilder2.substring(4);
                    }
                    stringBuilder.setLength(0);
                    try {
                        int delete = writableDatabase.delete("t_lr", stringBuilder2, null);
                        w.c("[Database] deleted %s data %d", "t_lr", Integer.valueOf(delete));
                    } catch (Throwable th) {
                        if (!w.a(th)) {
                            th.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public final synchronized void b(int i) {
        String str = null;
        synchronized (this) {
            SQLiteDatabase writableDatabase = b.getWritableDatabase();
            if (writableDatabase != null) {
                if (i >= 0) {
                    try {
                        str = "_tp = " + i;
                    } catch (Throwable th) {
                        if (!w.a(th)) {
                            th.printStackTrace();
                        }
                    }
                }
                int delete = writableDatabase.delete("t_lr", str, null);
                w.c("[Database] deleted %s data %d", "t_lr", Integer.valueOf(delete));
            }
        }
    }

    private static ContentValues c(q qVar) {
        if (qVar == null) {
            return null;
        }
        try {
            ContentValues contentValues = new ContentValues();
            if (qVar.a > 0) {
                contentValues.put("_id", Long.valueOf(qVar.a));
            }
            contentValues.put("_tp", Integer.valueOf(qVar.b));
            contentValues.put("_pc", qVar.c);
            contentValues.put("_th", qVar.d);
            contentValues.put("_tm", Long.valueOf(qVar.e));
            if (qVar.g != null) {
                contentValues.put("_dt", qVar.g);
            }
            return contentValues;
        } catch (Throwable th) {
            if (w.a(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }

    private static q a(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        try {
            q qVar = new q();
            qVar.a = cursor.getLong(cursor.getColumnIndex("_id"));
            qVar.b = cursor.getInt(cursor.getColumnIndex("_tp"));
            qVar.c = cursor.getString(cursor.getColumnIndex("_pc"));
            qVar.d = cursor.getString(cursor.getColumnIndex("_th"));
            qVar.e = cursor.getLong(cursor.getColumnIndex("_tm"));
            qVar.g = cursor.getBlob(cursor.getColumnIndex("_dt"));
            return qVar;
        } catch (Throwable th) {
            if (w.a(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }

    private synchronized List<q> c(int i) {
        List<q> list;
        Throwable th;
        Cursor cursor;
        Cursor query;
        try {
            SQLiteDatabase writableDatabase = b.getWritableDatabase();
            if (writableDatabase != null) {
                String str = "_id = " + i;
                query = writableDatabase.query("t_pf", null, str, null, null, null, null);
                if (query == null) {
                    if (query != null) {
                        query.close();
                    }
                    list = null;
                } else {
                    try {
                        StringBuilder stringBuilder = new StringBuilder();
                        List<q> arrayList = new ArrayList();
                        while (query.moveToNext()) {
                            q b = b(query);
                            if (b != null) {
                                arrayList.add(b);
                            } else {
                                try {
                                    stringBuilder.append(" or _tp").append(" = ").append(query.getString(query.getColumnIndex("_tp")));
                                } catch (Throwable th2) {
                                    th = th2;
                                }
                            }
                        }
                        if (stringBuilder.length() > 0) {
                            stringBuilder.append(" and _id").append(" = ").append(i);
                            int delete = writableDatabase.delete("t_pf", str.substring(4), null);
                            w.d("[Database] deleted %s illegal data %d.", "t_pf", Integer.valueOf(delete));
                        }
                        if (query != null) {
                            query.close();
                        }
                        list = arrayList;
                    } catch (Throwable th22) {
                        th = th22;
                    }
                }
            }
        } catch (Throwable th3) {
            th = th3;
            query = null;
            if (query != null) {
                query.close();
            }
            throw th;
        }
        list = null;
        return list;
    }

    private synchronized boolean a(int i, String str, n nVar) {
        boolean z = true;
        boolean z2 = false;
        synchronized (this) {
            try {
                SQLiteDatabase writableDatabase = b.getWritableDatabase();
                if (writableDatabase != null) {
                    String str2;
                    if (y.a(str)) {
                        str2 = "_id = " + i;
                    } else {
                        str2 = "_id = " + i + " and _tp" + " = \"" + str + "\"";
                    }
                    w.c("[Database] deleted %s data %d", "t_pf", Integer.valueOf(writableDatabase.delete("t_pf", str2, null)));
                    if (writableDatabase.delete("t_pf", str2, null) <= 0) {
                        z = false;
                    }
                    z2 = z;
                }
                if (nVar != null) {
                    Boolean.valueOf(z2);
                }
            } catch (Throwable th) {
                if (nVar != null) {
                    Boolean.valueOf(false);
                }
            }
        }
        return z2;
    }

    private static ContentValues d(q qVar) {
        if (qVar == null || y.a(qVar.f)) {
            return null;
        }
        try {
            ContentValues contentValues = new ContentValues();
            if (qVar.a > 0) {
                contentValues.put("_id", Long.valueOf(qVar.a));
            }
            contentValues.put("_tp", qVar.f);
            contentValues.put("_tm", Long.valueOf(qVar.e));
            if (qVar.g == null) {
                return contentValues;
            }
            contentValues.put("_dt", qVar.g);
            return contentValues;
        } catch (Throwable th) {
            if (!w.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    private static q b(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        try {
            q qVar = new q();
            qVar.a = cursor.getLong(cursor.getColumnIndex("_id"));
            qVar.e = cursor.getLong(cursor.getColumnIndex("_tm"));
            qVar.f = cursor.getString(cursor.getColumnIndex("_tp"));
            qVar.g = cursor.getBlob(cursor.getColumnIndex("_dt"));
            return qVar;
        } catch (Throwable th) {
            if (w.a(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }
}
