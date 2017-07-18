package com.kayac.lobi.sdk.rec.activity;

import com.kayac.lobi.sdk.auth.TermsOfUseFragment.Callback;

class aq extends Callback {
    final /* synthetic */ Runnable a;
    final /* synthetic */ RecPostVideoActivity b;

    aq(RecPostVideoActivity recPostVideoActivity, Runnable runnable) {
        this.b = recPostVideoActivity;
        this.a = runnable;
    }

    public void onAccept() {
        this.a.run();
    }

    public void onDismiss() {
        this.b.mLoadingModal = false;
    }
}
