package com.kayac.lobi.libnakamap.rec.recorder;

import android.app.Activity;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class b {
    private static final String a = b.class.getSimpleName();
    private static final com.kayac.lobi.libnakamap.rec.a.b b = new com.kayac.lobi.libnakamap.rec.a.b(a);
    private final com.kayac.lobi.libnakamap.rec.recorder.a.a c;
    private List<a> d = new ArrayList();
    private long e;
    private long f;
    private long g;
    private boolean h;

    private static class a implements com.kayac.lobi.libnakamap.rec.recorder.f.b {
        private boolean a = true;
        private String b;
        private ShortBuffer c;
        private ShortBuffer d;
        private com.kayac.lobi.libnakamap.rec.recorder.f.a e;
        private j f;

        public a(String str, com.kayac.lobi.libnakamap.rec.recorder.f.a aVar, j jVar) {
            this.b = str;
            this.e = aVar;
            this.f = jVar;
            while (aVar.getBufferSizeInByte() == 0) {
                try {
                    Thread.sleep(0, 300);
                } catch (InterruptedException e) {
                }
            }
            this.c = ByteBuffer.allocateDirect(aVar.getBufferSizeInByte() * 4).order(ByteOrder.nativeOrder()).asShortBuffer();
            this.d = ByteBuffer.allocateDirect(aVar.getBufferSizeInByte() * 6).order(ByteOrder.nativeOrder()).asShortBuffer();
        }

        public void a() {
            this.e.setAudioOutputTrack(this);
        }

        public void a(short[] sArr, int i, int i2, int i3) {
            if (sArr != null && sArr.length != 0 && i > 0) {
                synchronized (this.c) {
                    if (i <= this.c.remaining()) {
                        this.c.put(sArr, 0, i);
                    } else {
                        b.b.c("Overflow input " + this.b);
                    }
                    this.f.e();
                }
            }
        }

        public ShortBuffer b() {
            return this.d;
        }

        public void c() {
            synchronized (this.c) {
                if (this.d.remaining() <= this.c.position()) {
                    b.b.c("Overflow update buffer " + this.b);
                    this.c.flip();
                    this.c.limit(this.d.remaining());
                    this.d.put(this.c);
                    this.c.compact();
                } else {
                    this.c.flip();
                    this.d.put(this.c);
                    this.c.clear();
                }
            }
        }

        public void d() {
            this.e.setAudioOutputTrack(null);
        }

        public void e() {
            this.a = false;
        }
    }

    public b(j jVar, com.kayac.lobi.libnakamap.rec.recorder.a.a aVar) {
        this.c = aVar;
        if (i.getInstance() == null) {
            AudioMixer.a();
        }
        this.d.add(new a("game_" + i.getInstance().getTag(), i.getInstance(), jVar));
        if (this.c.c()) {
            if (MicInput.getInstance() == null) {
                MicInput.init();
            }
            if (MicInput.getInstance() != null) {
                if (MicInput.getInstance().alreadyUsed()) {
                    b.c("MicInput.getInstance().alreadyUsed()");
                    jVar.o().c(false);
                    Activity s = jVar.s();
                    if (s != null) {
                        s.runOnUiThread(new c(this, s));
                    }
                } else {
                    MicInput.getInstance().play();
                    this.d.add(new a("mic", MicInput.getInstance(), jVar));
                }
            }
        }
        this.e = System.currentTimeMillis();
        this.f = -1;
        this.g = 0;
        this.h = true;
    }

    public void a() {
        for (a a : this.d) {
            a.a();
        }
    }

    public void a(long j) {
        this.e = j;
        this.f = -1;
    }

    public void b() {
        for (a d : this.d) {
            d.d();
        }
    }

    public void b(long j) {
        this.f = j - this.e;
    }

    public synchronized short[] c() {
        short[] sArr;
        int position;
        int i;
        long j;
        int i2 = 0;
        Object obj = 1;
        int i3 = 0;
        if (!this.h && this.f < 0) {
            this.h = true;
        }
        Iterator it = this.d.iterator();
        while (it.hasNext()) {
            if (!((a) it.next()).a) {
                it.remove();
            }
        }
        for (a aVar : this.d) {
            int i4;
            Object obj2;
            aVar.c();
            ShortBuffer b = aVar.b();
            position = b.position();
            if (position > 0) {
                if (position == b.limit()) {
                    Object obj3;
                    for (a aVar2 : this.d) {
                        if (aVar2 != aVar && position < aVar2.b().capacity()) {
                            obj3 = null;
                            break;
                        }
                    }
                    i4 = 1;
                    if (obj3 != null) {
                        b.b("force to read");
                        i4 = position;
                        if (i3 != 0 || position < i3) {
                            obj2 = obj;
                            i3 = i4;
                            i4 = position;
                        } else {
                            obj2 = obj;
                            int i5 = i3;
                            i3 = i4;
                            i4 = i5;
                        }
                    } else {
                        b.b("force to read: aborted. waiting for other listeners");
                    }
                }
                i4 = i2;
                if (i3 != 0) {
                }
                obj2 = obj;
                i3 = i4;
                i4 = position;
            } else {
                obj2 = null;
                i4 = i3;
                i3 = i2;
            }
            obj = obj2;
            i2 = i3;
            i3 = i4;
        }
        if (i2 > 0) {
            position = i2;
        } else if (i3 == 0 || obj == null) {
            sArr = null;
        } else {
            position = i3;
        }
        long a = g.a((long) position);
        if (!this.h || 0 > this.f || this.f >= this.g + a) {
            obj = null;
            i = position;
            j = a;
        } else {
            i3 = (int) Math.floor((((double) ((this.g + a) - this.f)) / 1000.0d) * ((double) (com.kayac.lobi.libnakamap.rec.recorder.a.a.b * com.kayac.lobi.libnakamap.rec.recorder.a.a.c)));
            b.b("overflow audio " + i3 + " minLength:" + position + " deadline:" + this.f + "ms current:" + (this.g + a) + "ms");
            position = position < i3 ? 0 : position - i3;
            obj = 1;
            i = position;
            j = a - g.a((long) position);
        }
        List arrayList = new ArrayList();
        for (a aVar3 : this.d) {
            ShortBuffer b2 = aVar3.b();
            Object obj4 = new short[i];
            if (i <= b2.position()) {
                b2.flip();
                b2.get(obj4, 0, i);
                b2.compact();
            } else if (b2.position() > 0) {
                b2.flip();
                b2.get(obj4, 0, b2.limit());
                b2.clear();
            }
            arrayList.add(obj4);
        }
        if (this.h) {
            if (obj != null) {
                this.h = false;
            }
            this.g = j + this.g;
            switch (arrayList.size()) {
                case 0:
                    sArr = null;
                    break;
                case 1:
                    sArr = (short[]) arrayList.get(0);
                    break;
                case 2:
                    MediaCodecHelper.a((short[]) arrayList.get(0), i, (float) this.c.b(), (short[]) arrayList.get(1), i, (float) this.c.a());
                    sArr = (short[]) arrayList.get(0);
                    break;
                default:
                    sArr = null;
                    break;
            }
        }
        sArr = null;
        return sArr;
    }

    public int d() {
        int i = 0;
        for (a b : this.d) {
            int capacity = b.b().capacity();
            if (i >= capacity) {
                capacity = i;
            }
            i = capacity;
        }
        return i * 2;
    }
}
