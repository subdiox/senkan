package com.kayac.lobi.sdk.rec.activity;

class s implements Runnable {
    final /* synthetic */ r a;

    s(r rVar) {
        this.a = rVar;
    }

    public void run() {
        this.a.a.loadRequest();
    }
}
