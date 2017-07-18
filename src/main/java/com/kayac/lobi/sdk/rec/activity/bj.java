package com.kayac.lobi.sdk.rec.activity;

import android.net.Uri;
import android.widget.MediaController;

class bj implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ RecVideoPlayerActivity b;

    bj(RecVideoPlayerActivity recVideoPlayerActivity, String str) {
        this.b = recVideoPlayerActivity;
        this.a = str;
    }

    public void run() {
        this.b.mVideoView.setMediaController(new MediaController(this.b.mVideoView.getContext()));
        this.b.mVideoView.setOnErrorListener(new bk(this));
        this.b.mVideoView.setOnPreparedListener(new bl(this));
        this.b.mVideoView.setOnCompletionListener(new bn(this));
        this.b.mVideoView.setVideoURI(Uri.parse(this.a));
    }
}
