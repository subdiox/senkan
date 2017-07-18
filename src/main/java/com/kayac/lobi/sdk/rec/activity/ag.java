package com.kayac.lobi.sdk.rec.activity;

import android.view.View;
import android.view.View.OnClickListener;

class ag implements OnClickListener {
    final /* synthetic */ RecPostVideoActivity a;

    ag(RecPostVideoActivity recPostVideoActivity) {
        this.a = recPostVideoActivity;
    }

    public void onClick(View view) {
        if (this.a.mShareWithNicovideo) {
            this.a.mShareWithNicovideo = false;
            this.a.updateNicovideoShareButton();
            return;
        }
        this.a.showSNSLogin("nicovideo");
    }
}
