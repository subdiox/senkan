package com.kayac.lobi.sdk.rec.activity;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

class ax implements OnClickListener {
    final /* synthetic */ aw a;

    ax(aw awVar) {
        this.a = awVar;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        dialogInterface.dismiss();
        this.a.a.finish();
    }
}
