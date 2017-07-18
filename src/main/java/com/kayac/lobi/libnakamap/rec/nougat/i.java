package com.kayac.lobi.libnakamap.rec.nougat;

import android.os.RemoteException;
import com.kayac.lobi.libnakamap.rec.nougat.b.a;
import com.kayac.lobi.sdk.rec.R;

class i extends a {
    final /* synthetic */ g a;

    i(g gVar) {
        this.a = gVar;
    }

    public void a() throws RemoteException {
        this.a.f = a.PREPARED;
        this.a.b.registerActivityLifecycleCallbacks(this.a.m);
    }

    public void a(int i) throws RemoteException {
        switch (i) {
            case 1:
                this.a.a(this.a.b.getString(R.string.lobirec_nougat_error_set_media_projection));
                return;
            case 2:
                this.a.a(this.a.b.getString(R.string.lobirec_nougat_error_denied_grant_permission));
                return;
            case 8:
                this.a.a(this.a.b.getString(R.string.lobirec_nougat_error_use_other_app));
                return;
            default:
                this.a.a(this.a.b.getString(R.string.lobirec_nougat_error_other));
                return;
        }
    }

    public void a(String str, boolean z) throws RemoteException {
        com.kayac.lobi.libnakamap.rec.b.a.a().a(str);
        if (z) {
            com.kayac.lobi.libnakamap.rec.b.a.a().a(null);
        }
    }
}
