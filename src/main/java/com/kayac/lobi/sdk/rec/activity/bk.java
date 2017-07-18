package com.kayac.lobi.sdk.rec.activity;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;

class bk implements OnErrorListener {
    final /* synthetic */ bj a;

    bk(bj bjVar) {
        this.a = bjVar;
    }

    public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
        return false;
    }
}
