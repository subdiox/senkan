package com.kayac.lobi.sdk.rec.activity;

import android.view.View;
import android.view.View.OnClickListener;

class aj implements OnClickListener {
    final /* synthetic */ RecPostVideoActivity a;

    aj(RecPostVideoActivity recPostVideoActivity) {
        this.a = recPostVideoActivity;
    }

    public void onClick(View view) {
        if (!this.a.mLoadingModal) {
            this.a.doIfTermsOfUseIsAccepted(new ak(this));
        }
    }
}
