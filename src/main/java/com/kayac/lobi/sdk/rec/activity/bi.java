package com.kayac.lobi.sdk.rec.activity;

import android.view.View;
import android.view.View.OnClickListener;

class bi implements OnClickListener {
    final /* synthetic */ RecSNSLoginActivity a;

    bi(RecSNSLoginActivity recSNSLoginActivity) {
        this.a = recSNSLoginActivity;
    }

    public void onClick(View view) {
        this.a.goBackIfHasHistory();
    }
}
