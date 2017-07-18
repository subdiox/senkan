package com.kayac.lobi.sdk.rec.activity;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

class am implements OnClickListener {
    final /* synthetic */ String a;
    final /* synthetic */ RecPostVideoActivity b;

    am(RecPostVideoActivity recPostVideoActivity, String str) {
        this.b = recPostVideoActivity;
        this.a = str;
    }

    public void onClick(View view) {
        if (!this.b.mLoadingModal) {
            this.b.mLoadingModal = true;
            Intent intent = new Intent(this.b, RecVideoPlayerActivity.class);
            intent.putExtra("EXTRA_URI", this.a);
            this.b.startActivity(intent);
        }
    }
}
