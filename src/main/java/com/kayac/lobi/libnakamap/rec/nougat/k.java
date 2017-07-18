package com.kayac.lobi.libnakamap.rec.nougat;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;

class k implements ActivityLifecycleCallbacks {
    final /* synthetic */ g a;

    k(g gVar) {
        this.a = gVar;
    }

    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    public void onActivityDestroyed(Activity activity) {
        synchronized (this) {
            if (activity.getClass() == this.a.c) {
                c.g();
            }
        }
    }

    public void onActivityPaused(Activity activity) {
        synchronized (this) {
            if (activity.getClass() == this.a.c && this.a.i) {
                this.a.n();
                if (this.a.f == a.RECORDING) {
                    if (c.n()) {
                        this.a.l();
                    } else {
                        this.a.i = false;
                        this.a.m();
                    }
                }
            }
        }
    }

    public void onActivityResumed(Activity activity) {
        synchronized (this) {
            if (activity.getClass() == this.a.c && this.a.i) {
                this.a.d.postDelayed(this.a.j, g.a.longValue());
            }
        }
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    public void onActivityStarted(Activity activity) {
    }

    public void onActivityStopped(Activity activity) {
    }
}
