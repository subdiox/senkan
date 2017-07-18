package com.kayac.lobi.sdk.rec.activity;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

class v implements OnClickListener {
    final /* synthetic */ u a;

    v(u uVar) {
        this.a = uVar;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        dialogInterface.dismiss();
        this.a.a.finish();
    }
}
