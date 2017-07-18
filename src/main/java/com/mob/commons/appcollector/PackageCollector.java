package com.mob.commons.appcollector;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Handler;
import android.text.TextUtils;
import com.kayac.lobi.libnakamap.value.AuthorizedAppValue;
import com.mob.commons.a;
import com.mob.commons.g;
import com.mob.tools.MobHandlerThread;
import com.mob.tools.MobLog;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.Hashon;
import com.mob.tools.utils.R;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class PackageCollector {
    private static PackageCollector b;
    private final String[] a = new String[]{"android.intent.action.PACKAGE_ADDED", "android.intent.action.PACKAGE_CHANGED", "android.intent.action.PACKAGE_REMOVED", "android.intent.action.PACKAGE_REPLACED"};
    private Context c;
    private DeviceHelper d;
    private Hashon e;
    private Handler f;

    private PackageCollector(Context context) {
        this.c = context.getApplicationContext();
        this.d = DeviceHelper.getInstance(this.c);
        this.e = new Hashon();
    }

    private ArrayList<HashMap<String, String>> a(ArrayList<HashMap<String, String>> arrayList, ArrayList<HashMap<String, String>> arrayList2) {
        ArrayList<HashMap<String, String>> arrayList3 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            HashMap hashMap = (HashMap) it.next();
            String str = (String) hashMap.get("pkg");
            if (!TextUtils.isEmpty(str)) {
                Object obj;
                Iterator it2 = arrayList2.iterator();
                while (it2.hasNext()) {
                    if (str.equals(((HashMap) it2.next()).get("pkg"))) {
                        obj = 1;
                        break;
                    }
                }
                obj = null;
                if (obj == null) {
                    arrayList3.add(hashMap);
                }
            }
        }
        return arrayList3;
    }

    private void a() {
        MobHandlerThread aVar = new a(this);
        aVar.start();
        this.f = new Handler(aVar.getLooper(), new c(this));
    }

    private void a(long j) {
        File file = new File(R.getCacheRoot(this.c), "comm/dbs/.nulal");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(file));
            dataOutputStream.writeLong(j);
            dataOutputStream.flush();
            dataOutputStream.close();
        } catch (Throwable th) {
            MobLog.getInstance().d(th);
        }
    }

    private void a(String str, ArrayList<HashMap<String, String>> arrayList) {
        HashMap hashMap = new HashMap();
        hashMap.put("type", str);
        hashMap.put("list", arrayList);
        hashMap.put("datetime", Long.valueOf(a.b(this.c)));
        g.a(this.c).a(hashMap);
    }

    private void a(ArrayList<HashMap<String, String>> arrayList) {
        File file = new File(R.getCacheRoot(this.c), "comm/dbs/.al");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new GZIPOutputStream(new FileOutputStream(file)), "utf-8");
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                outputStreamWriter.append(this.e.fromHashMap((HashMap) it.next())).append('\n');
            }
            outputStreamWriter.flush();
            outputStreamWriter.close();
        } catch (Throwable th) {
            MobLog.getInstance().d(th);
        }
    }

    private boolean a(String str) {
        for (String equals : this.a) {
            if (equals.equals(str)) {
                return true;
            }
        }
        return false;
    }

    private void b() {
        ArrayList c = c();
        if (c == null || c.isEmpty()) {
            c = this.d.getInstalledApp(false);
            a("APPS_ALL", c);
            a(c);
            a(a.b(this.c) + (a.f(this.c) * 1000));
            return;
        }
        long b = a.b(this.c);
        if (b >= d()) {
            ArrayList installedApp = this.d.getInstalledApp(false);
            a("APPS_ALL", installedApp);
            a(installedApp);
            a(b + (a.f(this.c) * 1000));
            return;
        }
        f();
    }

    private ArrayList<HashMap<String, String>> c() {
        File file = new File(R.getCacheRoot(this.c), "comm/dbs/.al");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (file.exists()) {
            try {
                ArrayList<HashMap<String, String>> arrayList = new ArrayList();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(file)), "utf-8"));
                for (String readLine = bufferedReader.readLine(); readLine != null; readLine = bufferedReader.readLine()) {
                    HashMap fromJson = this.e.fromJson(readLine);
                    if (fromJson != null) {
                        arrayList.add(fromJson);
                    }
                }
                bufferedReader.close();
                return arrayList;
            } catch (Throwable th) {
                MobLog.getInstance().d(th);
            }
        }
        return new ArrayList();
    }

    private long d() {
        File file = new File(R.getCacheRoot(this.c), "comm/dbs/.nulal");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (file.exists()) {
            try {
                DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file));
                long readLong = dataInputStream.readLong();
                dataInputStream.close();
                return readLong;
            } catch (Throwable th) {
                MobLog.getInstance().d(th);
            }
        }
        return 0;
    }

    private void e() {
        IntentFilter intentFilter = new IntentFilter();
        for (String addAction : b.a) {
            intentFilter.addAction(addAction);
        }
        intentFilter.addDataScheme(AuthorizedAppValue.JSON_KEY_PACKAGE);
        this.c.registerReceiver(new d(this), intentFilter);
    }

    private void f() {
        ArrayList c = c();
        ArrayList installedApp = this.d.getInstalledApp(false);
        if (c == null || c.isEmpty()) {
            a("APPS_ALL", installedApp);
            a(installedApp);
            a(a.b(this.c) + (a.f(this.c) * 1000));
            return;
        }
        ArrayList a = a(installedApp, c);
        if (!a.isEmpty()) {
            a("APPS_INCR", a);
        }
        c = a(c, installedApp);
        if (!c.isEmpty()) {
            a("UNINSTALL", c);
        }
        a(installedApp);
        a(a.b(this.c) + (a.f(this.c) * 1000));
    }

    public static synchronized boolean register(Context context, String str) {
        synchronized (PackageCollector.class) {
            startCollector(context);
        }
        return true;
    }

    public static synchronized void startCollector(Context context) {
        synchronized (PackageCollector.class) {
            if (b == null) {
                b = new PackageCollector(context);
                b.a();
            }
        }
    }
}
