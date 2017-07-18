package com.kayac.lobi.sdk.rec.activity;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.widget.ImageView;
import com.kayac.lobi.libnakamap.rec.a.b;
import com.kayac.lobi.sdk.rec.R;

class ar implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ long b;
    final /* synthetic */ RecPostVideoActivity c;

    ar(RecPostVideoActivity recPostVideoActivity, String str, long j) {
        this.c = recPostVideoActivity;
        this.a = str;
        this.b = j;
    }

    public void run() {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            mediaMetadataRetriever.setDataSource(this.a);
            Bitmap frameAtTime = mediaMetadataRetriever.getFrameAtTime((this.b * 1000) / 2, 0);
            ImageView imageView = (ImageView) this.c.findViewById(R.id.lobi_rec_activity_post_video_thumbnail);
            if (imageView != null) {
                this.c.runOnUiThread(new as(this, imageView, frameAtTime));
            }
        } catch (Throwable e) {
            b.a(e);
            b.c("LobiRecSDK", "failed to create a thumbnail");
            this.c.finishWithError(R.string.lobirec_cant_open_video);
        }
    }
}
