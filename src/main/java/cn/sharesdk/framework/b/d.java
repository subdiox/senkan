package cn.sharesdk.framework.b;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import cn.sharesdk.framework.b.b.c;
import cn.sharesdk.framework.b.b.e;
import cn.sharesdk.framework.b.b.g;
import com.kayac.lobi.libnakamap.datastore.AccountDDL.KKey.UpdateAt;
import com.mob.commons.SHARESDK;
import com.mob.commons.appcollector.PackageCollector;
import com.mob.commons.appcollector.RuntimeCollector;
import com.mob.commons.deviceinfo.DeviceInfoCollector;
import com.mob.commons.iosbridge.UDPServer;
import com.mob.tools.SSDKHandlerThread;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.FileLocker;
import java.io.File;
import java.util.Calendar;

public class d extends SSDKHandlerThread {
    private static d a;
    private Context b;
    private DeviceHelper c;
    private a d;
    private String e;
    private Handler f;
    private boolean g;
    private int h;
    private boolean i;
    private long j;
    private boolean k;
    private File l;
    private FileLocker m = new FileLocker();

    private d(Context context, String str) {
        this.b = context;
        this.e = str;
        this.c = DeviceHelper.getInstance(context);
        this.d = a.a(context, str);
        this.l = new File(context.getFilesDir(), ".statistics");
        if (!this.l.exists()) {
            try {
                this.l.createNewFile();
            } catch (Throwable e) {
                cn.sharesdk.framework.utils.d.a().d(e);
            }
        }
    }

    public static synchronized d a(Context context, String str) {
        d dVar = null;
        synchronized (d.class) {
            if (a == null) {
                if (context != null) {
                    if (!TextUtils.isEmpty(str)) {
                        a = new d(context.getApplicationContext(), str);
                    }
                }
            }
            dVar = a;
        }
        return dVar;
    }

    private void a() {
        boolean b = b();
        if (b) {
            if (!this.k) {
                this.k = b;
                this.j = System.currentTimeMillis();
                a(new g());
            }
        } else if (this.k) {
            this.k = b;
            long currentTimeMillis = System.currentTimeMillis() - this.j;
            c eVar = new e();
            eVar.a = currentTimeMillis;
            a(eVar);
        }
    }

    private void b(c cVar) {
        cVar.f = this.c.getDeviceKey();
        cVar.g = this.e;
        cVar.h = this.c.getPackageName();
        cVar.i = this.c.getAppVersion();
        cVar.j = String.valueOf(60000 + this.h);
        cVar.k = this.c.getPlatformCode();
        cVar.l = this.c.getDetailNetworkTypeForStatic();
        if (TextUtils.isEmpty(this.e)) {
            System.err.println("Your appKey of ShareSDK is null , this will cause its data won't be count!");
        } else if (!"cn.sharesdk.demo".equals(cVar.h) && ("api20".equals(this.e) || "androidv1101".equals(this.e))) {
            System.err.println("Your app is using the appkey of ShareSDK Demo, this will cause its data won't be count!");
        }
        cVar.m = this.c.getDeviceData();
    }

    private boolean b() {
        DeviceHelper instance = DeviceHelper.getInstance(this.b);
        String topTaskPackageName = instance.getTopTaskPackageName();
        String packageName = instance.getPackageName();
        return packageName != null && packageName.equals(topTaskPackageName);
    }

    private void c() {
        try {
            this.d.c();
        } catch (Throwable th) {
            cn.sharesdk.framework.utils.d.a().d(th);
        }
    }

    private void c(c cVar) {
        try {
            this.d.a(cVar);
            cVar.b(this.b);
        } catch (Throwable th) {
            cn.sharesdk.framework.utils.d.a().d(th);
            cn.sharesdk.framework.utils.d.a().d(cVar.toString(), new Object[0]);
        }
    }

    public void a(int i) {
        this.h = i;
    }

    public void a(Handler handler) {
        this.f = handler;
    }

    public void a(c cVar) {
        if (this.i) {
            b(cVar);
            if (cVar.a(this.b)) {
                Message message = new Message();
                message.what = 3;
                message.obj = cVar;
                try {
                    this.handler.sendMessage(message);
                    return;
                } catch (Throwable th) {
                    cn.sharesdk.framework.utils.d.a().d(th);
                    return;
                }
            }
            cn.sharesdk.framework.utils.d.a().d("Drop event: " + cVar.toString(), new Object[0]);
        }
    }

    public void a(boolean z) {
        this.g = z;
    }

    protected void onMessage(Message msg) {
        switch (msg.what) {
            case 1:
                a();
                try {
                    this.handler.sendEmptyMessageDelayed(1, 5000);
                    return;
                } catch (Throwable th) {
                    cn.sharesdk.framework.utils.d.a().d(th);
                    return;
                }
            case 2:
                c();
                try {
                    this.handler.sendEmptyMessageDelayed(2, 10000);
                    return;
                } catch (Throwable th2) {
                    cn.sharesdk.framework.utils.d.a().d(th2);
                    return;
                }
            case 3:
                if (msg.obj != null) {
                    c((c) msg.obj);
                    return;
                }
                return;
            case 4:
                long longValue = cn.sharesdk.framework.b.a.e.a(this.b).f().longValue();
                Calendar instance = Calendar.getInstance();
                instance.setTimeInMillis(longValue);
                int i = instance.get(1);
                int i2 = instance.get(2);
                int i3 = instance.get(5);
                instance.setTimeInMillis(System.currentTimeMillis());
                int i4 = instance.get(1);
                int i5 = instance.get(2);
                int i6 = instance.get(5);
                if (!(i == i4 && i2 == i5 && i3 == i6)) {
                    this.d.b();
                }
                this.handler.sendEmptyMessageDelayed(4, UpdateAt.GET_REC_INFO_CACHE);
                return;
            default:
                return;
        }
    }

    protected void onStart(Message msg) {
        if (!this.i) {
            this.i = true;
            try {
                this.m.setLockFile(this.l.getAbsolutePath());
                if (this.m.lock(false)) {
                    this.d.a();
                    this.d.b();
                    SHARESDK.setAppKey(this.e);
                    this.d.c(new SHARESDK().getDuid(this.b));
                    DeviceInfoCollector.startCollector(this.b);
                    PackageCollector.startCollector(this.b);
                    RuntimeCollector.startCollector(this.b);
                    UDPServer.start(this.b);
                    this.handler.sendEmptyMessageDelayed(4, UpdateAt.GET_REC_INFO_CACHE);
                    this.d.a(this.g);
                    this.handler.sendEmptyMessage(1);
                    this.handler.sendEmptyMessage(2);
                }
            } catch (Throwable th) {
                cn.sharesdk.framework.utils.d.a().d(th);
            }
        }
    }

    protected void onStop(Message msg) {
        if (this.i) {
            long currentTimeMillis = System.currentTimeMillis() - this.j;
            c eVar = new e();
            eVar.a = currentTimeMillis;
            a(eVar);
            this.i = false;
            try {
                this.f.sendEmptyMessage(1);
            } catch (Throwable th) {
                cn.sharesdk.framework.utils.d.a().d(th);
            }
            a = null;
            this.handler.getLooper().quit();
        }
    }
}
