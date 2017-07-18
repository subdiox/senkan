package com.kayac.lobi.libnakamap.rec.recorder;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;

class q implements Runnable {
    final /* synthetic */ Bitmap a;
    final /* synthetic */ Runnable b;
    final /* synthetic */ n c;

    q(n nVar, Bitmap bitmap, Runnable runnable) {
        this.c = nVar;
        this.a = bitmap;
        this.b = runnable;
    }

    public void run() {
        int[] iArr = new int[1];
        GLES20.glGetIntegerv(32873, iArr, 0);
        if (!this.c.a.r) {
            int[] iArr2 = new int[1];
            GLES20.glGenTextures(1, iArr2, 0);
            this.c.a.u = iArr2[0];
            this.c.a.z.setIconTextureName(this.c.a.u);
            this.c.a.r = true;
        }
        GLES20.glBindTexture(3553, this.c.a.u);
        GLUtils.texImage2D(3553, 0, this.a, 0);
        GLES20.glBindTexture(3553, iArr[0]);
        this.a.recycle();
        this.b.run();
    }
}
