package com.kayac.lobi.sdk.rec.activity;

import android.view.View;
import android.view.View.OnClickListener;

class ab implements OnClickListener {
    final /* synthetic */ RecPostVideoActivity a;

    ab(RecPostVideoActivity recPostVideoActivity) {
        this.a = recPostVideoActivity;
    }

    public void onClick(View view) {
        this.a.finish();
    }
}
