package com.kayac.lobi.libnakamap.rec.recorder;

import android.media.AudioTrack;
import com.kayac.lobi.libnakamap.rec.a.b;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AudioMixer extends i implements a {
    private static final String a = AudioMixer.class.getSimpleName();
    private static final b b = new b(a);
    private AudioTrack c = null;
    private List<a> d = new ArrayList();
    private Executor e = null;
    private List<ShortBuffer> f = new ArrayList();
    private ShortBuffer g = null;
    private short[] h = null;

    public interface a extends a {
        boolean isEnd();
    }

    static {
        System.loadLibrary("lobimixer");
    }

    private AudioMixer() {
        int i = 4;
        this.mBufferSizeInByte = AudioTrack.getMinBufferSize(com.kayac.lobi.libnakamap.rec.recorder.a.a.b, com.kayac.lobi.libnakamap.rec.recorder.a.a.c == 1 ? 4 : 12, 2);
        int i2 = com.kayac.lobi.libnakamap.rec.recorder.a.a.b;
        if (com.kayac.lobi.libnakamap.rec.recorder.a.a.c != 1) {
            i = 12;
        }
        this.c = new AudioTrack(3, i2, i, 2, this.mBufferSizeInByte, 1);
        this.c.play();
        c();
    }

    public static void a() {
        if (i.getInstance() == null) {
            i.register(new AudioMixer(), "cocos2dx");
        }
    }

    public static AudioMixer b() {
        a();
        return (AudioMixer) i.getInstance();
    }

    private static native void getMix(ShortBuffer shortBuffer, int i);

    private static native void setTrack(ShortBuffer shortBuffer);

    public void a(a aVar) {
        synchronized (this.d) {
            if (this.d.size() < 31) {
                this.d.add(aVar);
            }
        }
    }

    public void c() {
        if (this.e == null) {
            this.e = Executors.newSingleThreadExecutor(new d(this));
            this.e.execute(new e(this));
        }
    }

    public short[] getNextData(int i) {
        int i2 = 0;
        synchronized (this.d) {
            int i3 = 0;
            int i4 = 0;
            while (i3 < this.d.size()) {
                ShortBuffer shortBuffer;
                int i5 = i4 + 1;
                ShortBuffer asShortBuffer;
                if (this.f.size() <= i3) {
                    ByteBuffer allocateDirect = ByteBuffer.allocateDirect(i * 2);
                    allocateDirect.order(ByteOrder.nativeOrder());
                    asShortBuffer = allocateDirect.asShortBuffer();
                    this.f.add(asShortBuffer);
                    shortBuffer = asShortBuffer;
                } else {
                    asShortBuffer = (ShortBuffer) this.f.get(i3);
                    asShortBuffer.clear();
                    shortBuffer = asShortBuffer;
                }
                shortBuffer.put(((a) this.d.get(i3)).getNextData(i));
                i3++;
                i4 = i5;
            }
            Collection arrayList = new ArrayList();
            for (a aVar : this.d) {
                if (aVar.isEnd()) {
                    arrayList.add(aVar);
                }
            }
            if (!arrayList.isEmpty()) {
                this.d.removeAll(arrayList);
            }
        }
        while (i2 < i4) {
            setTrack((ShortBuffer) this.f.get(i2));
            i2++;
        }
        if (this.g == null) {
            allocateDirect = ByteBuffer.allocateDirect(i * 2);
            allocateDirect.order(ByteOrder.nativeOrder());
            this.g = allocateDirect.asShortBuffer();
        }
        getMix(this.g, i);
        if (this.h == null) {
            this.h = new short[i];
        }
        this.g.get(this.h);
        this.g.clear();
        synchronized (this.mOutputTrackMutex) {
            if (this.mOutputTrack != null) {
                this.mOutputTrack.a(this.h, this.h.length, com.kayac.lobi.libnakamap.rec.recorder.a.a.b, com.kayac.lobi.libnakamap.rec.recorder.a.a.c);
            }
        }
        return this.h;
    }

    public void onEndOfFrame() {
    }

    public void startRecording() {
    }

    public void stopRecording() {
    }
}
