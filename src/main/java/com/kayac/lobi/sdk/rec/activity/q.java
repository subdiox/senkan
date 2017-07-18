package com.kayac.lobi.sdk.rec.activity;

class q implements Runnable {
    final /* synthetic */ RecPlayActivity a;

    q(RecPlayActivity recPlayActivity) {
        this.a = recPlayActivity;
    }

    public void run() {
        this.a.loadRequest();
    }
}
