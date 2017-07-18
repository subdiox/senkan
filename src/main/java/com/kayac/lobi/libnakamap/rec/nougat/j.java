package com.kayac.lobi.libnakamap.rec.nougat;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import com.kayac.lobi.libnakamap.rec.nougat.a.a;
import com.kayac.lobi.sdk.rec.R;

class j implements ServiceConnection {
    final /* synthetic */ g a;

    j(g gVar) {
        this.a = gVar;
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.a.h = false;
        this.a.e = a.a(iBinder);
        try {
            this.a.e.a(this.a.b.getPackageName(), this.a.k);
        } catch (RemoteException e) {
            this.a.a(e);
        }
    }

    public void onServiceDisconnected(ComponentName componentName) {
        this.a.a(this.a.b.getString(R.string.lobirec_nougat_error_other));
    }
}
