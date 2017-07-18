package com.kayac.lobi.libnakamap.rec.recorder;

import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

public class MediaCodecHelper {
    static {
        System.loadLibrary("lobimediacodechelper");
    }

    private static native void _changeVolume(short[] sArr, int i, float f);

    private static native void _convert(ShortBuffer shortBuffer, int i, ByteBuffer byteBuffer, int i2, float f);

    private static native void _mixGameAndMic(short[] sArr, int i, float f, short[] sArr2, int i2, float f2);

    public static void a(ShortBuffer shortBuffer, int i, ByteBuffer byteBuffer, int i2, float f) {
        _convert(shortBuffer, i, byteBuffer, i2, f);
    }

    public static void a(short[] sArr, int i, float f) {
        _changeVolume(sArr, i, f);
    }

    public static void a(short[] sArr, int i, float f, short[] sArr2, int i2, float f2) {
        _mixGameAndMic(sArr, i, f, sArr2, i2, f2);
    }
}
