package com.kayac.lobi.sdk.rec.activity;

class t implements Runnable {
    final /* synthetic */ RecPlayActivity a;

    t(RecPlayActivity recPlayActivity) {
        this.a = recPlayActivity;
    }

    public void run() {
        if (this.a.mProgress != null && this.a.mProgress.isShowing()) {
            this.a.mProgress.dismiss();
        }
        this.a.mProgress = null;
    }
}
