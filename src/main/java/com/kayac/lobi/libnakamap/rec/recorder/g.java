package com.kayac.lobi.libnakamap.rec.recorder;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaFormat;
import com.kayac.lobi.libnakamap.rec.a.b;
import com.kayac.lobi.libnakamap.rec.recorder.muxer.a;
import java.io.IOException;
import java.nio.ByteBuffer;

@TargetApi(16)
public class g extends r {
    private static final String c = g.class.getSimpleName();
    private static final b d = new b(c);
    private b e;
    private byte[] f;
    private boolean g = false;

    public g(a aVar, b bVar) throws IOException {
        super(aVar);
        this.e = bVar;
        this.f = new byte[this.e.d()];
        this.g = false;
        i();
    }

    public static long a(long j) {
        return (1000 * j) / ((long) (com.kayac.lobi.libnakamap.rec.recorder.a.a.b * com.kayac.lobi.libnakamap.rec.recorder.a.a.c));
    }

    protected MediaCodec a() {
        return MediaCodec.createEncoderByType("audio/mp4a-latm");
    }

    protected void a(MediaFormat mediaFormat) {
        this.a.addAudioTrack(mediaFormat);
    }

    protected void a(ByteBuffer byteBuffer, BufferInfo bufferInfo) {
        this.a.writeAudioSampleData(byteBuffer, bufferInfo);
    }

    protected MediaFormat b() {
        MediaFormat createAudioFormat = MediaFormat.createAudioFormat("audio/mp4a-latm", com.kayac.lobi.libnakamap.rec.recorder.a.a.b, com.kayac.lobi.libnakamap.rec.recorder.a.a.c);
        createAudioFormat.setInteger("aac-profile", com.kayac.lobi.libnakamap.rec.recorder.a.a.a);
        createAudioFormat.setInteger("bitrate", com.kayac.lobi.libnakamap.rec.recorder.a.a.d);
        createAudioFormat.setInteger("max-input-size", this.f.length);
        return createAudioFormat;
    }

    public boolean c() {
        boolean c = super.c();
        d.a("c(-_-) audio recording is started!!");
        return c;
    }

    public void d() {
        if (!this.g) {
            this.g = true;
        }
        short[] c = this.e.c();
        if (c != null && c.length != 0) {
            int i = 0;
            while (i < c.length) {
                int i2 = i;
                i = 0;
                while (i < this.f.length) {
                    int i3 = i2 + 1;
                    short s = c[i2];
                    int i4 = i + 1;
                    this.f[i] = (byte) (s & 255);
                    i = i4 + 1;
                    this.f[i4] = (byte) ((s >> 8) & 255);
                    if (c.length <= i3) {
                        i2 = i;
                        i = i3;
                        break;
                    }
                    i2 = i3;
                }
                int i5 = i;
                i = i2;
                i2 = i5;
                a(this.f, i2);
                this.b += (long) c.length;
            }
        }
    }

    protected void e() {
        a(this.f, 0);
    }

    protected long f() {
        return 1000 * a(this.b);
    }

    public void g() {
        this.e.b();
        super.g();
        d.a("DONE! Now I can hear your sounds... c(-_-)");
    }

    public b h() {
        return this.e;
    }
}
