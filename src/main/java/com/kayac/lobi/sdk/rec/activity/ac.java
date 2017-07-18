package com.kayac.lobi.sdk.rec.activity;

class ac implements Runnable {
    final /* synthetic */ RecPostVideoActivity a;

    ac(RecPostVideoActivity recPostVideoActivity) {
        this.a = recPostVideoActivity;
    }

    public void run() {
        this.a.setupSNSShare();
    }
}
