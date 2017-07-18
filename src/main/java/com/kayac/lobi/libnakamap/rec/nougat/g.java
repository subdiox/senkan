package com.kayac.lobi.libnakamap.rec.nougat;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;
import com.kayac.lobi.sdk.rec.R;

public class g {
    private static final Long a = Long.valueOf(300);
    private Application b;
    private Class<? extends Activity> c;
    private Handler d = new Handler(Looper.getMainLooper());
    private a e;
    private a f = a.INITIAL;
    private boolean g;
    private boolean h;
    private boolean i;
    private Runnable j = new h(this);
    private b k = new i(this);
    private ServiceConnection l = new j(this);
    private ActivityLifecycleCallbacks m = new k(this);

    private enum a {
        INITIAL,
        PREPARED,
        RECORDING
    }

    public g(Activity activity) {
        this.b = activity.getApplication();
    }

    private void a(RemoteException remoteException) {
        remoteException.printStackTrace();
        a(this.b.getString(R.string.lobirec_nougat_error_other));
    }

    private void a(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.d.post(new l(this, str));
        }
        c.g();
    }

    private void l() {
        synchronized (this) {
            this.f = a.PREPARED;
            try {
                this.e.b();
            } catch (RemoteException e) {
                a(e);
            }
        }
    }

    private void m() {
        synchronized (this) {
            this.f = a.PREPARED;
            this.g = false;
            try {
                this.e.c();
            } catch (RemoteException e) {
                a(e);
            }
        }
    }

    private void n() {
        this.d.removeCallbacks(this.j);
    }

    public void a() {
        if (i() && !j()) {
            this.f = a.RECORDING;
            c.m();
            this.g = true;
            try {
                this.e.a();
                this.i = true;
            } catch (RemoteException e) {
                a(e);
            }
        }
    }

    public void a(Activity activity) {
        if (this.f == a.INITIAL && !this.h && c.k()) {
            this.b.bindService(new Intent("com.kayac.lobi.recorder").setPackage("com.kayac.nakamap"), this.l, 1);
            this.c = activity.getClass();
            this.h = true;
        }
    }

    public void b() {
        synchronized (this) {
            if (g()) {
                n();
                this.i = false;
                l();
            }
        }
    }

    public void c() {
        synchronized (this) {
            if (h()) {
                d();
                this.i = true;
            }
        }
    }

    public void d() {
        synchronized (this) {
            this.f = a.RECORDING;
            try {
                this.e.a();
            } catch (RemoteException e) {
                a(e);
            }
        }
    }

    public void e() {
        synchronized (this) {
            if (j()) {
                n();
                this.i = false;
                m();
            }
        }
    }

    public void f() {
        synchronized (this) {
            if (this.e != null) {
                this.b.unbindService(this.l);
                this.e = null;
            }
            this.b.unregisterActivityLifecycleCallbacks(this.m);
            this.f = a.INITIAL;
            this.g = false;
            this.i = false;
        }
    }

    public boolean g() {
        return this.f == a.RECORDING;
    }

    public boolean h() {
        return this.g && this.f == a.PREPARED;
    }

    public boolean i() {
        return this.f == a.PREPARED || this.f == a.RECORDING;
    }

    public boolean j() {
        return this.g;
    }
}
