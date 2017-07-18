package com.kayac.lobi.sdk.rec.activity;

import android.view.View;
import android.view.View.OnClickListener;

class p implements OnClickListener {
    final /* synthetic */ RecPlayActivity a;

    p(RecPlayActivity recPlayActivity) {
        this.a = recPlayActivity;
    }

    public void onClick(View view) {
        this.a.finish();
    }
}
