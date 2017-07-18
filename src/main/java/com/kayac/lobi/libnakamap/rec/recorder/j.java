package com.kayac.lobi.libnakamap.rec.recorder;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Process;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.KKey.Rec;
import com.kayac.lobi.libnakamap.rec.LobiRec;
import com.kayac.lobi.libnakamap.rec.a.b;
import com.kayac.lobi.libnakamap.rec.b.f;
import com.kayac.lobi.libnakamap.rec.recorder.a.d;
import com.kayac.lobi.libnakamap.rec.recorder.muxer.MuxerNative;
import com.kayac.lobi.sdk.LobiCore;
import junit.framework.Assert;

@TargetApi(9)
public class j implements Runnable {
    private static final String a = j.class.getSimpleName();
    private static final b b = new b(a);
    private Runnable A = new l(this);
    private Activity c;
    private t d = null;
    private g e = null;
    private boolean f = false;
    private boolean g = false;
    private boolean h = false;
    private boolean i = false;
    private boolean j = false;
    private boolean k = false;
    private f l;
    private Object m = new Object();
    private Object n = new Object();
    private boolean o = false;
    private com.kayac.lobi.libnakamap.rec.recorder.a.b p;
    private boolean q = false;
    private boolean r = false;
    private boolean s = false;
    private boolean t = false;
    private int u;
    private Runnable v;
    private Runnable w;
    private long x = 0;
    private long y = 0;
    private OffScreenManager z;

    public static class a {
        static int a = 0;
        static int b = 0;
        static int c = 200;
        static int d = 10;
        static a e = a.None;
        public static boolean f = false;
        private static float g = 0.0f;

        public enum a {
            None,
            InCamera,
            Icon
        }

        public static int a() {
            return (int) ((((float) a) / g) + 0.5f);
        }

        public static void a(int i) {
            a = (int) ((g * ((float) i)) + 0.5f);
        }

        public static int b() {
            return (int) ((((float) b) / g) + 0.5f);
        }

        public static void b(int i) {
            b = (int) ((g * ((float) i)) + 0.5f);
        }

        public static int c() {
            return (int) ((((float) c) / g) + 0.5f);
        }

        public static void c(int i) {
            c = i < 0 ? 0 : (int) ((g * ((float) i)) + 0.5f);
        }
    }

    public j(boolean z, Activity activity, int i, int i2, com.kayac.lobi.libnakamap.rec.recorder.a.b.a aVar) {
        b.a("LobiRecSDK", String.format("Version: %s GameEngine: %s ScreenSize: %dx%d", new Object[]{"5.2.39", aVar.name(), Integer.valueOf(i), Integer.valueOf(i2)}));
        if (z) {
            try {
                this.c = activity;
            } catch (Throwable e) {
                b.a(e);
                return;
            }
        }
        Context context = LobiCore.sharedInstance().getContext();
        a.g = context.getResources().getDisplayMetrics().density;
        a.f = context.getPackageManager().hasSystemFeature("android.hardware.camera.front");
        this.p = new com.kayac.lobi.libnakamap.rec.recorder.a.b(this, z, i, i2, aVar, false);
    }

    private void A() {
        if (z()) {
            synchronized (this.m) {
                this.m.notifyAll();
                this.j = true;
            }
        }
    }

    private void B() {
        if (z()) {
            synchronized (this.n) {
                this.n.notifyAll();
                this.k = true;
            }
        }
    }

    private void C() {
        g();
        if (this.c != null) {
            this.c.runOnUiThread(new m(this));
        }
    }

    private void d(int i) {
        LobiRec.setLastErrorCode(i);
        C();
    }

    private boolean z() {
        if (this.o) {
            return true;
        }
        b.a("not initialized yet");
        return false;
    }

    public void a() {
        Assert.assertFalse(this.o);
        b.a("init on the unsupported device (or disabled by RecorderSwitch)");
        b.a("LobiRecSDK", "unsupported device or disabled");
        this.o = true;
    }

    public void a(int i) {
        a.a(i);
        if (this.z != null) {
            this.z.setWipePositionX(a.a);
        }
    }

    public void a(int i, int i2, int i3, int i4, String str) {
        Assert.assertFalse(this.o);
        this.v = new k(this, i, i2, i3, i4, str);
        b.a("init on the supported device");
        b.a("LobiRecSDK", "supported device");
        this.o = true;
    }

    public void a(a aVar) {
        if (aVar != a.InCamera || a.f) {
            a.e = aVar;
            return;
        }
        b.c("Face Capture is not supported on this device");
        a.e = a.None;
    }

    public void a(boolean z) {
        if (z()) {
            b.b("supported: " + l() + " initialized:" + m());
            if (m() && !this.f) {
                if (i.getInstance() != null) {
                    i.getInstance().startRecording();
                }
                this.g = false;
                this.j = false;
                this.k = false;
                this.f = true;
                if (z) {
                    this.h = false;
                    this.l = this.p.i();
                    this.l.e();
                    com.kayac.lobi.libnakamap.rec.b.a.a().c();
                } else {
                    this.l = o();
                }
                b.a("start video capturing");
                new Thread(this).start();
            }
        }
    }

    public void b() {
        if (z() && this.z != null) {
            if (this.d != null) {
                this.d.h();
            }
            this.z.prepareFrame();
        }
    }

    public void b(int i) {
        a.b(i);
        if (this.z != null) {
            this.z.setWipePositionY(a.b);
        }
    }

    public void c() {
        if (z() && this.z != null) {
            this.z.appendFrame();
        }
    }

    public void c(int i) {
        a.c(i);
        if (this.z != null) {
            this.z.setWipeSquareSize(a.c);
        }
    }

    public void d() {
        if (!z()) {
            return;
        }
        if (this.z != null) {
            if (this.w != null) {
                this.w.run();
                this.w = null;
            }
            if (i.getInstance() != null) {
                i.getInstance().onEndOfFrame();
            }
            if (this.z.onEndOfFrame()) {
                A();
            }
        } else if (this.v != null) {
            this.v.run();
            this.v = null;
        }
    }

    public void e() {
        if (z()) {
            B();
        }
    }

    public void f() {
        a(true);
    }

    public void g() {
        if (z() && this.f) {
            b.a("stop video capturing");
            if (i.getInstance() != null) {
                i.getInstance().stopRecording();
            }
            this.g = true;
            this.h = false;
            A();
            B();
            com.kayac.lobi.libnakamap.rec.b.a.a().b(Rec.MOVIE_STATUS_END_CAPTURING);
        }
    }

    public void h() {
        if (!z()) {
            return;
        }
        if (this.f || this.h) {
            b.a("resume video capturing");
            this.h = false;
            if (this.d != null) {
                this.d.k();
            }
            A();
            B();
        }
    }

    public void i() {
        if (!z()) {
            return;
        }
        if (this.f || !this.h) {
            b.a("pause video capturing");
            this.h = true;
            A();
            B();
        }
    }

    public boolean j() {
        return this.f;
    }

    public boolean k() {
        return this.f && this.h;
    }

    public boolean l() {
        return n().g();
    }

    public boolean m() {
        return l() && this.z != null && LobiRec.getLastErrorCode() == 0;
    }

    public com.kayac.lobi.libnakamap.rec.recorder.a.b n() {
        return this.p;
    }

    public f o() {
        if (this.l == null) {
            this.l = new f();
            this.l.f();
        }
        return this.l;
    }

    public void p() {
        boolean j = n().j();
        b.a("onPauseActivity capturing: " + j() + " sticky: " + j);
        if (j()) {
            boolean z = this.h;
            g();
            if (j) {
                this.i = true;
                this.h = z;
            }
        }
    }

    public void q() {
        b.a("onResumeActivity " + this.i);
        if (this.i) {
            a(false);
            this.i = false;
        }
    }

    public void r() {
        if (this.z != null) {
            this.z.release();
        }
    }

    public void run() {
        if (z()) {
            com.kayac.lobi.libnakamap.rec.recorder.muxer.a bVar;
            d f = this.p.f();
            if (com.kayac.lobi.libnakamap.rec.a.b) {
                b.b("Muxer Java");
                bVar = new com.kayac.lobi.libnakamap.rec.recorder.muxer.b(f);
            } else {
                b.b("Muxer Native");
                bVar = new MuxerNative(f);
            }
            t tVar;
            g gVar;
            b bVar2;
            String str;
            try {
                if (this.p.g()) {
                    b.b("instanciate ScreenRecorder");
                    this.d = new t(this.c, bVar, this.p.d(), this.z);
                }
                if (this.p.h()) {
                    b.b("instanciate AudioRecorder");
                    this.e = new g(bVar, new b(this, this.p.e()));
                }
                if (this.e == null && this.d == null) {
                    b.a("recording thread was aborted");
                    b.a("recording is finishing...");
                    tVar = this.d;
                    gVar = this.e;
                    this.d = null;
                    synchronized (this.A) {
                        this.e = null;
                    }
                    b.a("AudioRecordTask request stop");
                    B();
                    bVar.stop();
                    if (tVar != null) {
                        tVar.g();
                    }
                    if (gVar != null) {
                        gVar.g();
                    }
                    this.f = false;
                    bVar2 = b;
                    str = "Video capturing thread is finished.";
                    bVar2.a(str);
                }
                if (this.d != null) {
                    b.b("start ScreenRecorder");
                    if (a.e != a.Icon || this.t) {
                        this.z.setLiveWipeStatus(a.e);
                    } else {
                        this.z.setLiveWipeStatus(a.None);
                        t();
                    }
                    this.z.startCapture();
                    this.y = System.currentTimeMillis();
                    if (!this.d.c()) {
                        throw new RuntimeException("failed to start screen recorder");
                    }
                }
                if (this.e != null) {
                    b.b("start AudioRecorder");
                    if (!this.e.c()) {
                        throw new RuntimeException("failed to start audio recorder");
                    }
                }
                b.a("recorders are started");
                try {
                    bVar.start();
                } catch (Throwable e) {
                    b.a(e);
                }
                if (this.e != null) {
                    new Thread(this.A).start();
                }
                b.a("loop start");
                do {
                    Process.setThreadPriority(-4);
                    b.a("loop front");
                    if (this.d != null) {
                        this.d.d();
                    }
                    if (this.h) {
                        this.z.stopCapture();
                        if (this.d != null) {
                            this.d.j();
                        }
                        this.x = System.currentTimeMillis();
                    } else {
                        this.z.startCapture();
                        this.y = System.currentTimeMillis();
                    }
                    b.a("loop wait");
                    synchronized (this.m) {
                        if (!this.j) {
                            try {
                                this.m.wait();
                            } catch (Throwable e2) {
                                b.a(e2);
                            }
                        }
                        this.j = false;
                    }
                    b.a("loop again");
                } while (!this.g);
                b.a("stop recorders");
                b.a("recording is finishing...");
                tVar = this.d;
                gVar = this.e;
                this.d = null;
                synchronized (this.A) {
                    this.e = null;
                }
                b.a("AudioRecordTask request stop");
                B();
                bVar.stop();
                if (tVar != null) {
                    tVar.g();
                }
                if (gVar != null) {
                    gVar.g();
                }
                this.f = false;
                bVar2 = b;
                str = "Video capturing thread is finished.";
                bVar2.a(str);
            } catch (Throwable e22) {
                b.a(e22);
                b.a("recording is aborted. failed to create MediaCodec.");
                d((int) LobiRec.ERROR_BAD_ENCODER_CONNECTION);
                b.a("recording is finishing...");
                tVar = this.d;
                gVar = this.e;
                this.d = null;
                synchronized (this.A) {
                    this.e = null;
                    b.a("AudioRecordTask request stop");
                    B();
                    bVar.stop();
                    if (tVar != null) {
                        tVar.g();
                    }
                    if (gVar != null) {
                        gVar.g();
                    }
                    this.f = false;
                    bVar2 = b;
                    str = "Video capturing thread is finished.";
                }
            } catch (Throwable e222) {
                b.a(e222);
                b.a("recording is aborted");
                C();
                b.a("recording is finishing...");
                tVar = this.d;
                gVar = this.e;
                this.d = null;
                synchronized (this.A) {
                    this.e = null;
                    b.a("AudioRecordTask request stop");
                    B();
                    bVar.stop();
                    if (tVar != null) {
                        tVar.g();
                    }
                    if (gVar != null) {
                        gVar.g();
                    }
                    this.f = false;
                    bVar2 = b;
                    str = "Video capturing thread is finished.";
                }
            } catch (Throwable th) {
                b.a("recording is finishing...");
                t tVar2 = this.d;
                g gVar2 = this.e;
                this.d = null;
                synchronized (this.A) {
                    this.e = null;
                    b.a("AudioRecordTask request stop");
                    B();
                    bVar.stop();
                    if (tVar2 != null) {
                        tVar2.g();
                    }
                    if (gVar2 != null) {
                        gVar2.g();
                    }
                    this.f = false;
                    b.a("Video capturing thread is finished.");
                }
            }
        }
    }

    public Activity s() {
        return this.c;
    }

    @SuppressLint({"InlinedApi"})
    public void t() {
        if (!this.q) {
            this.q = true;
            new Thread(new n(this)).start();
        }
    }

    public int u() {
        return a.a();
    }

    public int v() {
        return a.b();
    }

    public int w() {
        return a.c();
    }

    public a x() {
        return a.e;
    }
}
