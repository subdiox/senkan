package com.kayac.lobi.sdk.rec.activity;

import com.kayac.lobi.sdk.auth.AccountUtil;

class h implements Runnable {
    final /* synthetic */ RecPlayActivity a;

    h(RecPlayActivity recPlayActivity) {
        this.a = recPlayActivity;
    }

    public void run() {
        AccountUtil.refreshTokenIfNecessary(new i(this));
    }
}
