package com.kayac.lobi.sdk.rec.activity;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.widget.VideoView;

class bl implements OnPreparedListener {
    final /* synthetic */ bj a;

    bl(bj bjVar) {
        this.a = bjVar;
    }

    public void onPrepared(MediaPlayer mediaPlayer) {
        VideoView access$000 = this.a.b.mVideoView;
        if (access$000 != null) {
            access$000.post(new bm(this, access$000));
        }
    }
}
