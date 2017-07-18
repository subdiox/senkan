package com.kayac.lobi.libnakamap.rec.cocos2dx;

import android.os.Build.VERSION;
import com.kayac.lobi.libnakamap.rec.LobiRec;
import com.kayac.lobi.libnakamap.rec.a.b;
import com.kayac.lobi.libnakamap.rec.recorder.a.a;
import com.kayac.lobi.libnakamap.rec.recorder.i;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class OpenSLAudio extends i implements Runnable {
    private static final b LOG = new b(TAG);
    private static final int MAX_LOOP_MS = 500;
    private static final String TAG = OpenSLAudio.class.getSimpleName();
    private static AudioWaveView sAudioWaveView;
    private AtomicBoolean mIsRunning;
    private int mNativeContext;
    private final Object[] mThraedLock;

    private OpenSLAudio() {
        this.mThraedLock = new Object[0];
        this.mIsRunning = new AtomicBoolean(false);
        this.mBufferSizeInByte = Math.round(((((((((float) a.b) * 1.0f) * 500.0f) * 2.0f) / 1000.0f) * 1.0f) * 16.0f) / 8.0f);
        nativeInit(VERSION.SDK_INT, a.b);
    }

    public static void init() {
        if (i.getInstance() != null) {
            return;
        }
        if (LobiAudio.loadNativeLibrary()) {
            i.register(new OpenSLAudio(), "opensles");
        } else {
            LobiRec.setLastErrorCode(LobiRec.ERROR_FAILED_TO_LOAD_NATIVE_LIBRARY);
        }
    }

    public static void setPreviewView(AudioWaveView audioWaveView) {
        sAudioWaveView = audioWaveView;
    }

    public native short[] nativeGetAudioData();

    public native void nativeInit(int i, int i2);

    public native void nativeStartRecording();

    public native void nativeStopRecording();

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
            short[] nativeGetAudioData = nativeGetAudioData();
            if (!(nativeGetAudioData == null || nativeGetAudioData.length == 0)) {
                if (sAudioWaveView != null) {
                    sAudioWaveView.add(nativeGetAudioData);
                }
                synchronized (this.mOutputTrackMutex) {
                    if (this.mOutputTrack != null) {
                        this.mOutputTrack.a(nativeGetAudioData, nativeGetAudioData.length, 0, 0);
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
