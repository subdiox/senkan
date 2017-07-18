package com.kayac.lobi.sdk.rec.activity;

import android.view.View;
import android.view.View.OnClickListener;

class ad implements OnClickListener {
    final /* synthetic */ RecPostVideoActivity a;

    ad(RecPostVideoActivity recPostVideoActivity) {
        this.a = recPostVideoActivity;
    }

    public void onClick(View view) {
        if (this.a.mShareWithTwitter) {
            this.a.mShareWithTwitter = false;
            this.a.updateTwitterShareButton();
            return;
        }
        this.a.showSNSLogin("twitter");
    }
}
