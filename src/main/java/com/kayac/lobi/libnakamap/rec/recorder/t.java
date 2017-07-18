package com.kayac.lobi.libnakamap.rec.recorder;

import android.annotation.TargetApi;
import android.app.Activity;
import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import com.kayac.lobi.libnakamap.rec.a.b;
import com.kayac.lobi.libnakamap.rec.a.c;
import com.kayac.lobi.libnakamap.rec.recorder.a.e;
import com.kayac.lobi.libnakamap.rec.recorder.muxer.a;
import java.io.IOException;
import java.nio.ByteBuffer;

@TargetApi(16)
public class t extends r {
    private static final String c = t.class.getSimpleName();
    private static final b d = new b(c);
    private byte[] e;
    private CameraInput f;
    private boolean g = false;
    private long h = 0;
    private long i = 0;
    private final e j;
    private final OffScreenManager k;

    public t(Activity activity, a aVar, e eVar, OffScreenManager offScreenManager) throws IOException {
        super(aVar);
        this.j = eVar;
        this.k = offScreenManager;
        offScreenManager.setWipeParameters(j.a.a, j.a.b, j.a.c, j.a.d);
        i();
        if (j.a.e == j.a.a.InCamera) {
            this.f = new CameraInput(activity, this.k);
            if (!this.f.isEnabled()) {
                d.c("No camera is found");
                j.a.e = j.a.a.Icon;
                this.f = null;
            }
        }
    }

    protected MediaCodec a() {
        MediaCodecInfo c = this.j.c();
        if (c == null) {
            d.c("Codec info is null");
            return null;
        }
        d.b("codec name: " + c.getName() + " (" + "video/avc" + ")");
        return MediaCodec.createByCodecName(c.getName());
    }

    protected void a(MediaFormat mediaFormat) {
        this.a.addScreenTrack(mediaFormat);
    }

    protected void a(ByteBuffer byteBuffer, BufferInfo bufferInfo) {
        this.a.writeScreenSampleData(byteBuffer, bufferInfo);
    }

    protected MediaFormat b() {
        MediaCodecInfo c = this.j.c();
        if (c == null) {
            return null;
        }
        int d = this.j.d();
        d.b("color format: " + d);
        int[] iArr = new int[5];
        this.k.getScreenInformation(e.a(this.j.b()), c.getName(), d, this.j.e(), iArr);
        int i = iArr[0];
        int i2 = iArr[1];
        int i3 = iArr[2];
        int i4 = iArr[0];
        int i5 = iArr[1];
        c.a(c, "nativeGetScreenInformation");
        MediaFormat createVideoFormat = MediaFormat.createVideoFormat("video/avc", i, i2);
        createVideoFormat.setInteger("bitrate", 1500000);
        createVideoFormat.setInteger("frame-rate", this.j.f());
        createVideoFormat.setInteger("color-format", d);
        createVideoFormat.setInteger("i-frame-interval", 5);
        createVideoFormat.setInteger("stride", i4);
        createVideoFormat.setInteger("slice-height", i5);
        this.e = new byte[i3];
        c.a(c, "createVideoFormat");
        return createVideoFormat;
    }

    public boolean c() {
        boolean c = super.c();
        d.a("(-_o) start screen recording");
        if (j.a.e == j.a.a.InCamera && this.f != null) {
            this.f.start();
        }
        return c;
    }

    public synchronized void d() {
        long[] jArr = new long[1];
        if (this.k.captureScreen(this.e, jArr) != 0) {
            if (this.h < 0) {
                this.i = this.h;
            }
            this.h = jArr[0];
            d.a("capturedtime " + this.h + "(" + jArr[0] + ")");
            this.i = this.h;
            a(this.e, this.e.length);
            this.b++;
        }
    }

    protected void e() {
        d();
    }

    protected long f() {
        return this.h;
    }

    public void g() {
        this.k.stopCapture();
        super.g();
        if (this.f != null) {
            this.f.stop();
            this.g = false;
        }
        d.a("DONE! Now I can see your display... (o_o)");
    }

    public void h() {
        if (j.a.e != j.a.a.InCamera) {
            return;
        }
        if (this.g) {
            this.f.update();
        } else if (this.f.update()) {
            this.k.startFaceCaptureRender();
            this.g = true;
        }
    }

    public void j() {
        if (this.f != null) {
            this.f.pause();
            this.g = false;
        }
    }

    public void k() {
        if (this.f != null) {
            this.f.start();
        }
    }
}
