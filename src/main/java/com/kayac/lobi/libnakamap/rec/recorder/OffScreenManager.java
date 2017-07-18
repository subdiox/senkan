package com.kayac.lobi.libnakamap.rec.recorder;

import com.kayac.lobi.libnakamap.rec.recorder.j.a.a;

public class OffScreenManager {
    private Object[] mLockCpu = new Object[0];
    private int mNativeContext;

    public OffScreenManager(int i, int i2, int i3, int i4, boolean z, String str) {
        nativeInit(i, i2, i3, i4, z, str);
    }

    private native void nativeAppendFrame();

    private native int nativeCaptureScreen(byte[] bArr, long[] jArr);

    private native int nativeCheckError();

    private native void nativeGetScreenInformation(int i, String str, int i2, int i3, int[] iArr);

    private native void nativeInit(int i, int i2, int i3, int i4, boolean z, String str);

    private native boolean nativeOnEndOfFrame();

    private native void nativePrepareFrame();

    private native void nativeRelease();

    private native void nativeRemoveLastCapturedData();

    private native void nativeSetIconTextureName(int i);

    private native void nativeSetLiveWipeStatus(int i);

    private native void nativeSetWipeParameters(int i, int i2, int i3, int i4);

    private native void nativeSetWipePositionX(int i);

    private native void nativeSetWipePositionY(int i);

    private native void nativeSetWipeSquareSize(int i);

    private native int nativeSetupCamera(int i, int i2, int i3);

    private native void nativeStartCapture();

    private native void nativeStartFaceCaptureRender();

    private native void nativeStopCapture();

    public synchronized void appendFrame() {
        nativeAppendFrame();
    }

    public int captureScreen(byte[] bArr, long[] jArr) {
        int nativeCaptureScreen;
        synchronized (this.mLockCpu) {
            nativeCaptureScreen = nativeCaptureScreen(bArr, jArr);
            if (nativeCaptureScreen != 0) {
                nativeRemoveLastCapturedData();
            }
        }
        return nativeCaptureScreen;
    }

    public synchronized int checkError() {
        return nativeCheckError();
    }

    public synchronized void getScreenInformation(int i, String str, int i2, int i3, int[] iArr) {
        nativeGetScreenInformation(i, str, i2, i3, iArr);
    }

    public synchronized boolean onEndOfFrame() {
        return nativeOnEndOfFrame();
    }

    public synchronized void prepareFrame() {
        nativePrepareFrame();
    }

    public synchronized void release() {
        synchronized (this.mLockCpu) {
            nativeRelease();
        }
    }

    public synchronized void setIconTextureName(int i) {
        nativeSetIconTextureName(i);
    }

    public synchronized void setLiveWipeStatus(a aVar) {
        nativeSetLiveWipeStatus(aVar.ordinal());
    }

    public synchronized void setWipeParameters(int i, int i2, int i3, int i4) {
        nativeSetWipeParameters(i, i2, i3, i4);
    }

    public synchronized void setWipePositionX(int i) {
        nativeSetWipePositionX(i);
    }

    public synchronized void setWipePositionY(int i) {
        nativeSetWipePositionY(i);
    }

    public synchronized void setWipeSquareSize(int i) {
        nativeSetWipeSquareSize(i);
    }

    public int setupCamera(int i, int i2, int i3) {
        return nativeSetupCamera(i, i2, i3);
    }

    public synchronized void startCapture() {
        nativeStartCapture();
    }

    public synchronized void startFaceCaptureRender() {
        nativeStartFaceCaptureRender();
    }

    public synchronized void stopCapture() {
        nativeStopCapture();
    }
}
