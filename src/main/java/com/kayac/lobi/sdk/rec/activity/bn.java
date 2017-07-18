package com.kayac.lobi.sdk.rec.activity;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

class bn implements OnCompletionListener {
    final /* synthetic */ bj a;

    bn(bj bjVar) {
        this.a = bjVar;
    }

    public void onCompletion(MediaPlayer mediaPlayer) {
        this.a.b.finish();
    }
}
