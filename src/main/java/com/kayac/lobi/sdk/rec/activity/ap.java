package com.kayac.lobi.sdk.rec.activity;

class ap implements Runnable {
    final /* synthetic */ RecPostVideoActivity a;

    ap(RecPostVideoActivity recPostVideoActivity) {
        this.a = recPostVideoActivity;
    }

    public void run() {
        this.a.updateStatusMe();
    }
}
