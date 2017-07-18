package com.kayac.lobi.libnakamap.rec.cocos2dx;

import com.kayac.lobi.libnakamap.rec.a.b;
import com.kayac.lobi.libnakamap.rec.recorder.a.a;

public class AudioResampleHelper {
    private static final b LOG = new b(TAG);
    private static final String TAG = AudioResampleHelper.class.getSimpleName();
    private int mInputChannelCount;
    private int mInputSampleRate;
    private int mMixingUnit = 0;
    private int mNativeContext;

    public AudioResampleHelper() {
        nativeInit(a.b);
    }

    private native void nativeInit(int i);

    private native short[] nativeResample(short[] sArr, int i);

    private native int nativeSetInputFormat(int i, int i2);

    public int getInputChannelCount() {
        return this.mInputChannelCount;
    }

    public int getInputSampleRate() {
        return this.mInputSampleRate;
    }

    public int getMixingUnit() {
        return this.mMixingUnit;
    }

    public short[] resample(short[] sArr, int i, int[] iArr) {
        int i2 = 0;
        if (this.mMixingUnit == 0) {
            LOG.c("undefined input format");
            return null;
        }
        iArr[0] = i % this.mMixingUnit;
        int i3 = iArr[0];
        short[] resampleMixingUnit = resampleMixingUnit(sArr, i - i3);
        LOG.b("input length: " + i + "(used:" + (i - i3) + " remain:" + i3 + ")  -> out length: " + resampleMixingUnit.length);
        if (i3 <= 0) {
            return resampleMixingUnit;
        }
        while (i2 < i3) {
            sArr[i2] = sArr[(i - i3) + i2];
            i2++;
        }
        return resampleMixingUnit;
    }

    public short[] resampleMixingUnit(short[] sArr, int i) {
        if (this.mMixingUnit == 0) {
            LOG.c("undefined input format");
            return null;
        }
        if (i % this.mMixingUnit != 0) {
            LOG.c("invalid lenght: " + i + "(mixing unit: " + this.mMixingUnit + ")");
        }
        return nativeResample(sArr, i);
    }

    public void setInputFormat(int i, int i2) {
        this.mInputSampleRate = i;
        this.mInputChannelCount = i2;
        this.mMixingUnit = nativeSetInputFormat(i, i2);
    }
}
