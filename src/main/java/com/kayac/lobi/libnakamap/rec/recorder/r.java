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
public abstract class r {
    private static final String c = r.class.getSimpleName();
    private static final b d = new b(c);
    protected a a;
    protected long b;
    private boolean e = false;
    private MediaCodec f;
    private MediaFormat g;
    private ByteBuffer[] h;
    private ByteBuffer[] i;
    private BufferInfo j;

    public r(a aVar) {
        this.a = aVar;
        this.e = false;
    }

    private final synchronized void d() {
        if (!this.e) {
            int dequeueOutputBuffer = this.f.dequeueOutputBuffer(this.j, 0);
            while (dequeueOutputBuffer >= 0) {
                ByteBuffer byteBuffer = this.i[dequeueOutputBuffer];
                if (this.j.size > 0) {
                    byteBuffer.position(this.j.offset);
                    byteBuffer.limit(this.j.offset + this.j.size);
                    a(byteBuffer, this.j);
                }
                this.f.releaseOutputBuffer(dequeueOutputBuffer, false);
                dequeueOutputBuffer = this.f.dequeueOutputBuffer(this.j, 0);
            }
            if (dequeueOutputBuffer == -3) {
                d.c("output buffers are changed");
                this.i = this.f.getOutputBuffers();
            } else if (dequeueOutputBuffer == -2) {
                d.a("output format is changed");
                this.g = this.f.getOutputFormat();
                this.e = true;
            }
        }
    }

    protected abstract MediaCodec a() throws IOException;

    protected abstract void a(MediaFormat mediaFormat);

    protected abstract void a(ByteBuffer byteBuffer, BufferInfo bufferInfo);

    protected final void a(byte[] bArr, int i) {
        int i2 = 0;
        while (i2 == 0) {
            int dequeueInputBuffer = this.f.dequeueInputBuffer(30000);
            if (dequeueInputBuffer >= 0) {
                ByteBuffer byteBuffer = this.h[dequeueInputBuffer];
                byteBuffer.clear();
                byteBuffer.put(bArr, 0, i);
                this.f.queueInputBuffer(dequeueInputBuffer, 0, i, f(), 0);
                i2 = 1;
            } else {
                d.b("insufficient input buffers...");
            }
            d();
        }
    }

    protected abstract MediaFormat b();

    public boolean c() {
        d.a("Configuring encoder with input format " + this.g);
        if (this.g == null) {
            return false;
        }
        this.f.configure(this.g, null, null, 1);
        this.f.start();
        this.h = this.f.getInputBuffers();
        this.i = this.f.getOutputBuffers();
        this.j = new BufferInfo();
        this.b = 0;
        d.b("MediaFormat" + this.g.toString());
        if (this.a instanceof com.kayac.lobi.libnakamap.rec.recorder.muxer.b) {
            while (!this.e) {
                try {
                    e();
                } catch (Throwable e) {
                    b.a(e);
                    d.c("aborted");
                    return false;
                }
            }
        }
        this.e = false;
        a(this.g);
        d.b("MediaFormat" + this.g.toString());
        if (!(this.f == null || this.g == null)) {
            d.a("initialized");
        }
        return true;
    }

    protected abstract void e();

    protected abstract long f();

    public void g() {
        try {
            d();
            this.f.stop();
        } catch (Throwable e) {
            b.a(e);
        }
        this.f.release();
        if (this.b == 0) {
            d.a("No frames were recorded.");
        }
    }

    protected final void i() throws IOException {
        this.f = a();
        this.g = b();
    }
}
