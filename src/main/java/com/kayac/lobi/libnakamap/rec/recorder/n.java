package com.kayac.lobi.libnakamap.rec.recorder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.rec.recorder.j.a.a;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.sdk.rec.R;
import java.io.InputStream;
import java.net.URL;

class n implements Runnable {
    final /* synthetic */ j a;

    n(j jVar) {
        this.a = jVar;
    }

    private void a(Bitmap bitmap, Runnable runnable) {
        this.a.w = new q(this, bitmap, runnable);
    }

    public void run() {
        Bitmap bitmap = null;
        try {
            UserValue currentUser = AccountDatastore.getCurrentUser();
            if (currentUser != null) {
                InputStream openStream = new URL(currentUser.getIcon()).openStream();
                bitmap = BitmapFactory.decodeStream(openStream);
                openStream.close();
                if (bitmap != null) {
                    a(bitmap, new o(this));
                    return;
                } else if (this.a.s) {
                    this.a.z.setLiveWipeStatus(a.Icon);
                    this.a.q = false;
                    return;
                } else {
                    a(BitmapFactory.decodeResource(this.a.s().getResources(), R.drawable.default_user_icon), new p(this));
                    return;
                }
            }
            throw new Exception();
        } catch (Exception e) {
            j.b.c("Failed to get user icon.");
        }
    }
}
