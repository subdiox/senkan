package com.kayac.lobi.sdk.rec.activity;

import android.view.View;
import android.view.View.OnClickListener;

class ae implements OnClickListener {
    final /* synthetic */ RecPostVideoActivity a;

    ae(RecPostVideoActivity recPostVideoActivity) {
        this.a = recPostVideoActivity;
    }

    public void onClick(View view) {
        if (this.a.mShareWithFacebook) {
            this.a.mShareWithFacebook = false;
            this.a.updateFacebookShareButton();
            return;
        }
        this.a.showSNSLogin("facebook");
    }
}
