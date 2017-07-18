package com.kayac.lobi.libnakamap.rec.recorder;

import com.kayac.lobi.libnakamap.rec.cocos2dx.AudioResampleHelper;
import java.util.ArrayList;
import java.util.List;

public class h {

    public static class a {
        private static final List<a> a = new ArrayList();

        private static class a {
            AudioResampleHelper a = new AudioResampleHelper();
            b b;

            public a(b bVar, int i, int i2) {
                this.b = bVar;
                this.a.setInputFormat(i, i2);
            }

            public String toString() {
                return "[" + this.a.toString() + "] " + this.b.toString() + ", sr:" + this.a.getInputSampleRate() + ", ch:" + this.a.getInputChannelCount();
            }
        }

        public static AudioResampleHelper a(b bVar, int i, int i2) {
            for (a aVar : a) {
                if (aVar.b == bVar && aVar.a.getInputSampleRate() == i && aVar.a.getInputChannelCount() == i2) {
                    return aVar.a;
                }
            }
            a aVar2 = new a(bVar, i, i2);
            a.add(aVar2);
            return aVar2.a;
        }
    }

    public enum b {
        BGM,
        SE
    }

    private static void a(short[] sArr, int i, short[] sArr2, int i2) {
        for (int i3 = 0; i3 < i; i3++) {
            sArr[i3] = sArr2[i3 * 2];
        }
    }

    public static short[] a(b bVar, short[] sArr, int i, int i2) {
        if (com.kayac.lobi.libnakamap.rec.recorder.a.a.b == i && com.kayac.lobi.libnakamap.rec.recorder.a.a.c == i2) {
            return (short[]) sArr.clone();
        }
        if (com.kayac.lobi.libnakamap.rec.recorder.a.a.b != i || com.kayac.lobi.libnakamap.rec.recorder.a.a.c == i2) {
            return a.a(bVar, i, i2).resampleMixingUnit(sArr, sArr.length);
        }
        short[] sArr2 = new short[(sArr.length / 2)];
        a(sArr2, sArr2.length, sArr, sArr.length);
        return sArr2;
    }
}
