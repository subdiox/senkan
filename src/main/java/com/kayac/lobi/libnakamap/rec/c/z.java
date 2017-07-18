package com.kayac.lobi.libnakamap.rec.c;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import com.kayac.lobi.libnakamap.rec.c.f.a;

final class z implements OnPreparedListener {
    final /* synthetic */ a a;
    final /* synthetic */ MediaPlayer b;

    z(a aVar, MediaPlayer mediaPlayer) {
        this.a = aVar;
        this.b = mediaPlayer;
    }

    public void onPrepared(MediaPlayer mediaPlayer) {
        this.a.a(this.b.getDuration());
        this.b.release();
    }
}
