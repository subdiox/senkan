package com.kayac.lobi.sdk.rec.activity;

import android.widget.VideoView;

class bm implements Runnable {
    final /* synthetic */ VideoView a;
    final /* synthetic */ bl b;

    bm(bl blVar, VideoView videoView) {
        this.b = blVar;
        this.a = videoView;
    }

    public void run() {
        this.a.start();
    }
}
