package com.kayac.lobi.sdk.rec.activity;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import com.kayac.lobi.sdk.rec.R;

class as implements Runnable {
    final /* synthetic */ ImageView a;
    final /* synthetic */ Bitmap b;
    final /* synthetic */ ar c;

    as(ar arVar, ImageView imageView, Bitmap bitmap) {
        this.c = arVar;
        this.a = imageView;
        this.b = bitmap;
    }

    public void run() {
        View findViewById = this.c.c.findViewById(R.id.lobi_rec_activity_post_video_progress);
        if (findViewById != null) {
            findViewById.setVisibility(8);
        }
        findViewById = this.c.c.findViewById(R.id.lobi_rec_activity_post_video_play_btn);
        if (findViewById != null) {
            findViewById.setVisibility(0);
        }
        this.a.setImageBitmap(this.b);
    }
}
