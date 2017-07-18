package com.kayac.lobi.sdk.rec.activity;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

class al implements OnClickListener {
    final /* synthetic */ RecPostVideoActivity a;

    al(RecPostVideoActivity recPostVideoActivity) {
        this.a = recPostVideoActivity;
    }

    public void onClick(View view) {
        if (!this.a.mLoadingModal) {
            this.a.mLoadingModal = true;
            this.a.startActivity(new Intent(this.a, RecFAQActivity.class));
        }
    }
}
