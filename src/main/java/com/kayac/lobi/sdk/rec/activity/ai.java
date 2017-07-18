package com.kayac.lobi.sdk.rec.activity;

class ai implements Runnable {
    final /* synthetic */ ah a;

    ai(ah ahVar) {
        this.a = ahVar;
    }

    public void run() {
        this.a.a.mLoadingModal = false;
        this.a.a.uploadMovie();
    }
}
