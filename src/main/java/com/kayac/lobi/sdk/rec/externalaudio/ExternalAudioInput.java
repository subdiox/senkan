package com.kayac.lobi.sdk.rec.externalaudio;

import com.kayac.lobi.libnakamap.rec.a.b;
import com.kayac.lobi.libnakamap.rec.recorder.MicInput;
import com.kayac.lobi.libnakamap.rec.recorder.i;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class ExternalAudioInput extends i implements Runnable {
    private static final int MAX_LOOP_MS = 500;
    private static final String TAG = ExternalAudioInput.class.getSimpleName();
    private int mChannelCount;
    private AtomicBoolean mIsRunning;
    private int mNativeContext;
    private int mSampleRate;
    private final Object[] mThraedLock;

    static {
        try {
            System.loadLibrary("lobirecexternalaudio");
            b.a(TAG, "load lobirecexternalaudio.so");
        } catch (UnsatisfiedLinkError e) {
            b.a(TAG, "failed to load lobirecexternalaudio.so");
        }
    }

    private ExternalAudioInput() {
        this.mThraedLock = new Object[0];
        this.mIsRunning = new AtomicBoolean(false);
        this.mSampleRate = MicInput.SAMPLE_RATE;
        this.mChannelCount = 1;
        commonInit(this.mSampleRate, this.mChannelCount);
    }

    private ExternalAudioInput(int i, int i2) {
        this.mThraedLock = new Object[0];
        this.mIsRunning = new AtomicBoolean(false);
        this.mSampleRate = MicInput.SAMPLE_RATE;
        this.mChannelCount = 1;
        commonInit(i, i2);
    }

    private void commonInit(int i, int i2) {
        this.mBufferSizeInByte = Math.round(((((((((float) i) * 1.0f) * 500.0f) * 2.0f) / 1000.0f) * 1.0f) * 16.0f) / 8.0f);
        this.mSampleRate = i;
        nativeInit(i, i2);
    }

    public static void init() {
        if (i.getInstance() == null) {
            i.register(new ExternalAudioInput(), "external");
        }
    }

    public static void init(int i, int i2) {
        if (i.getInstance() == null) {
            i.register(new ExternalAudioInput(i, i2), "external");
        }
    }

    private native short[] nativeGetAudioData();

    private native void nativeInit(int i, int i2);

    private native void nativeStartRecording();

    private native void nativeStopRecording();

    private native void nativeWriteAudioData(short[] sArr, int i);

    public int getChannelCount() {
        return this.mChannelCount;
    }

    public int getSampleRate() {
        return this.mSampleRate;
    }

    public void onEndOfFrame() {
        synchronized (this.mThraedLock) {
            this.mThraedLock.notifyAll();
        }
    }

    public void run() {
        while (this.mIsRunning.get()) {
            synchronized (this.mThraedLock) {
                try {
                    this.mThraedLock.wait(500);
                } catch (Throwable e) {
                    b.a(e);
                }
            }
            while (true) {
                short[] nativeGetAudioData = nativeGetAudioData();
                if (nativeGetAudioData != null) {
                    synchronized (this.mOutputTrackMutex) {
                        if (this.mOutputTrack != null) {
                            this.mOutputTrack.a(nativeGetAudioData, nativeGetAudioData.length, 0, 0);
                        }
                    }
                }
            }
        }
    }

    public void startRecording() {
        if (!this.mIsRunning.getAndSet(true)) {
            Executors.newSingleThreadExecutor().execute(this);
        }
        nativeStartRecording();
    }

    public void stopRecording() {
        this.mIsRunning.set(false);
        onEndOfFrame();
        nativeStopRecording();
    }

    public void writeAudioData(short[] sArr) {
        nativeWriteAudioData(sArr, sArr.length);
    }
}
