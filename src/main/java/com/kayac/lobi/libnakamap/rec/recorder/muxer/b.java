package com.kayac.lobi.libnakamap.rec.recorder.muxer;

import android.annotation.TargetApi;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaFormat;
import com.kayac.lobi.libnakamap.rec.a.c;
import com.kayac.lobi.libnakamap.rec.recorder.a.d;
import com.kayac.lobi.sdk.service.chat.GroupEventService;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import net.agasper.unitynotification.BuildConfig;

@TargetApi(16)
public class b extends a {
    private static final String a = b.class.getSimpleName();
    private static final com.kayac.lobi.libnakamap.rec.a.b b = new com.kayac.lobi.libnakamap.rec.a.b(a);
    private a c;
    private int d;
    private int e;

    private static class a {
        private Object a;
        private Method b;
        private Method c;
        private Method d;
        private Method e;
        private Method f;

        public a(String str, int i) throws IOException {
            try {
                Class cls = Class.forName("android.media.MediaMuxer");
                this.a = cls.getConstructor(new Class[]{String.class, Integer.TYPE}).newInstance(new Object[]{str, Integer.valueOf(i)});
                this.e = cls.getMethod("addTrack", new Class[]{MediaFormat.class});
                this.b = cls.getMethod("start", new Class[0]);
                this.c = cls.getMethod(GroupEventService.STOP, new Class[0]);
                this.d = cls.getMethod(BuildConfig.BUILD_TYPE, new Class[0]);
                this.f = cls.getMethod("writeSampleData", new Class[]{Integer.TYPE, ByteBuffer.class, BufferInfo.class});
            } catch (Throwable e) {
                com.kayac.lobi.libnakamap.rec.a.b.a(e);
            } catch (Throwable e2) {
                com.kayac.lobi.libnakamap.rec.a.b.a(e2);
            } catch (Throwable e22) {
                com.kayac.lobi.libnakamap.rec.a.b.a(e22);
            } catch (Throwable e222) {
                com.kayac.lobi.libnakamap.rec.a.b.a(e222);
            } catch (Throwable e2222) {
                com.kayac.lobi.libnakamap.rec.a.b.a(e2222);
            } catch (Throwable e22222) {
                com.kayac.lobi.libnakamap.rec.a.b.a(e22222);
            }
        }

        public int a(MediaFormat mediaFormat) {
            try {
                return ((Integer) this.e.invoke(this.a, new Object[]{mediaFormat})).intValue();
            } catch (Throwable e) {
                com.kayac.lobi.libnakamap.rec.a.b.a(e);
                return -1;
            } catch (Throwable e2) {
                com.kayac.lobi.libnakamap.rec.a.b.a(e2);
                return -1;
            } catch (Throwable e22) {
                com.kayac.lobi.libnakamap.rec.a.b.a(e22);
                return -1;
            }
        }

        public void a() {
            try {
                this.b.invoke(this.a, new Object[0]);
            } catch (Throwable e) {
                com.kayac.lobi.libnakamap.rec.a.b.a(e);
            } catch (Throwable e2) {
                com.kayac.lobi.libnakamap.rec.a.b.a(e2);
            } catch (Throwable e22) {
                com.kayac.lobi.libnakamap.rec.a.b.a(e22);
            }
        }

        public void a(int i, ByteBuffer byteBuffer, BufferInfo bufferInfo) {
            try {
                this.f.invoke(this.a, new Object[]{Integer.valueOf(i), byteBuffer, bufferInfo});
            } catch (Throwable e) {
                com.kayac.lobi.libnakamap.rec.a.b.a(e);
            } catch (Throwable e2) {
                com.kayac.lobi.libnakamap.rec.a.b.a(e2);
            } catch (Throwable e22) {
                com.kayac.lobi.libnakamap.rec.a.b.a(e22);
            }
        }

        public void b() {
            try {
                this.c.invoke(this.a, new Object[0]);
            } catch (Throwable e) {
                com.kayac.lobi.libnakamap.rec.a.b.a(e);
            } catch (Throwable e2) {
                com.kayac.lobi.libnakamap.rec.a.b.a(e2);
            } catch (Throwable e22) {
                com.kayac.lobi.libnakamap.rec.a.b.a(e22);
            }
        }

        public void c() {
            try {
                this.d.invoke(this.a, new Object[0]);
            } catch (Throwable e) {
                com.kayac.lobi.libnakamap.rec.a.b.a(e);
            } catch (Throwable e2) {
                com.kayac.lobi.libnakamap.rec.a.b.a(e2);
            } catch (Throwable e22) {
                com.kayac.lobi.libnakamap.rec.a.b.a(e22);
            }
        }
    }

    public b(d dVar) {
        super(dVar);
        try {
            this.c = new a(this.mConfig.b(), 0);
        } catch (Throwable e) {
            com.kayac.lobi.libnakamap.rec.a.b.a(e);
        } catch (Throwable e2) {
            com.kayac.lobi.libnakamap.rec.a.b.a(e2);
        }
        if (this.c == null) {
            b.c("failed to create MediaMuxer");
        }
    }

    public void addAudioTrack(MediaFormat mediaFormat) {
        this.e = this.c.a(mediaFormat);
    }

    public void addScreenTrack(MediaFormat mediaFormat) {
        this.d = this.c.a(mediaFormat);
    }

    public void start() throws com.kayac.lobi.libnakamap.rec.b.a.a {
        this.c.a();
        super.start();
    }

    public void stop() {
        synchronized (this) {
            b.b("stopping");
            super.stop();
            c.a();
            this.c.b();
            b.b("stopping 2");
            c.a(a, GroupEventService.STOP);
            this.c.c();
            c.a(a, BuildConfig.BUILD_TYPE);
            b.b("stopped");
        }
    }

    public void writeAudioSampleData(ByteBuffer byteBuffer, BufferInfo bufferInfo) {
        synchronized (this) {
            if (isRunning()) {
                this.c.a(this.e, byteBuffer, bufferInfo);
                return;
            }
            b.a("audio overflow");
        }
    }

    public void writeScreenSampleData(ByteBuffer byteBuffer, BufferInfo bufferInfo) {
        synchronized (this) {
            if (isRunning()) {
                this.c.a(this.d, byteBuffer, bufferInfo);
                return;
            }
            b.a("screen overflow");
        }
    }
}
