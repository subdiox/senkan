package com.kayac.lobi.sdk.rec.activity;

import android.view.View;
import android.view.View.OnClickListener;

class b implements OnClickListener {
    final /* synthetic */ RecFAQActivity a;

    b(RecFAQActivity recFAQActivity) {
        this.a = recFAQActivity;
    }

    public void onClick(View view) {
        if (this.a.canGoBack()) {
            this.a.goBack();
        } else {
            this.a.finish();
        }
    }
}
