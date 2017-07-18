package com.kayac.lobi.libnakamap.rec.c;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import com.kayac.lobi.libnakamap.rec.c.f.a;

final class aa implements OnErrorListener {
    final /* synthetic */ a a;
    final /* synthetic */ MediaPlayer b;

    aa(a aVar, MediaPlayer mediaPlayer) {
        this.a = aVar;
        this.b = mediaPlayer;
    }

    public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
        this.a.a(-1);
        this.b.release();
        return false;
    }
}
