package com.kayac.lobi.libnakamap.rec.nougat;

import android.view.View;
import android.view.View.OnClickListener;
import com.kayac.lobi.libnakamap.components.CustomDialog;

class f implements OnClickListener {
    final /* synthetic */ CustomDialog a;
    final /* synthetic */ a b;

    f(a aVar, CustomDialog customDialog) {
        this.b = aVar;
        this.a = customDialog;
    }

    public void onClick(View view) {
        this.a.dismiss();
    }
}
