package com.kayac.lobi.libnakamap.rec.recorder;

import com.kayac.lobi.libnakamap.rec.recorder.a.b.a;

class k implements Runnable {
    final /* synthetic */ int a;
    final /* synthetic */ int b;
    final /* synthetic */ int c;
    final /* synthetic */ int d;
    final /* synthetic */ String e;
    final /* synthetic */ j f;

    k(j jVar, int i, int i2, int i3, int i4, String str) {
        this.f = jVar;
        this.a = i;
        this.b = i2;
        this.c = i3;
        this.d = i4;
        this.e = str;
    }

    public void run() {
        j.b.a("init GL");
        a c = this.f.n().c();
        boolean z = c == a.UNITY_4_5_OR_4_6 || c == a.UNITY_5_0 || c == a.UNITY_5_1 || c == a.UNITY_5_2_OR_LATER || c == a.COCOS2D_X_3_7_OR_LATER;
        OffScreenManager offScreenManager = new OffScreenManager(this.a, this.b, this.c, this.d, z, this.e);
        int checkError = offScreenManager.checkError();
        if (checkError != 0) {
            j.b.c("failed to init LobiRecSDK (" + checkError + ")");
            this.f.n().a(null);
            return;
        }
        this.f.z = offScreenManager;
    }
}
