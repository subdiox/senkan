package com.kayac.lobi.sdk.rec.unity;

import com.kayac.lobi.libnakamap.rec.LobiRec;
import com.kayac.lobi.libnakamap.rec.a.b;
import com.kayac.lobi.libnakamap.rec.recorder.a.a;
import com.kayac.lobi.libnakamap.rec.recorder.i;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class FmodAudio extends i implements Runnable {
    private static final b LOG = new b(TAG);
    private static final int MAX_LOOP_MS = 500;
    private static final String TAG = FmodAudio.class.getSimpleName();
    private AtomicBoolean mIsRunning;
    private int mNativeContext;
    private final Object[] mThraedLock;

    static {
        try {
            System.loadLibrary("lobirecaudiounity");
        } catch (Throwable e) {
            b.a(e);
        }
    }

    private FmodAudio() {
        this.mThraedLock = new Object[0];
        this.mIsRunning = new AtomicBoolean(false);
        this.mBufferSizeInByte = Math.round(((((((1.0f * ((float) a.b)) * 500.0f) * 2.0f) / 1000.0f) * ((float) a.c)) * 16.0f) / 8.0f);
        if (!nativeInit()) {
            LobiRec.setLastErrorCode(LobiRec.ERROR_FAILED_TO_LOAD_NATIVE_LIBRARY);
        }
    }

    public static void init() {
        if (i.getInstance() == null) {
            i.register(new FmodAudio(), "unity");
        }
    }

    private native short[] nativeGetAudioData();

    private native boolean nativeInit();

    private native void nativeStartRecording();

    private native void nativeStopRecording();

    public void onEndOfFrame() {
        synchronized (this.mThraedLock) {
            this.mThraedLock.notifyAll();
        }
    }

    public void run() {
        LOG.b("enter thread");
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
        LOG.b("exit thread");
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
}
