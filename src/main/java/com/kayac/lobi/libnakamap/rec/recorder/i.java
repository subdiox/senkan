package com.kayac.lobi.libnakamap.rec.recorder;

import com.kayac.lobi.libnakamap.rec.a.b;
import com.kayac.lobi.libnakamap.rec.recorder.f.a;

public abstract class i implements a {
    private static final b LOG = new b(TAG);
    private static final String TAG = i.class.getSimpleName();
    private static i sInstance = null;
    protected int mBufferSizeInByte;
    protected f.b mOutputTrack = null;
    protected final Object[] mOutputTrackMutex = new Object[0];
    private String mTag;

    public static i getInstance() {
        return sInstance;
    }

    protected static void register(i iVar, String str) {
        if (sInstance == null) {
            sInstance = iVar;
            sInstance.mTag = str;
            return;
        }
        LOG.c("already registered game_" + sInstance.mTag);
    }

    public int getBufferSizeInByte() {
        return this.mBufferSizeInByte;
    }

    public String getTag() {
        return this.mTag;
    }

    public abstract void onEndOfFrame();

    public void setAudioOutputTrack(f.b bVar) {
        synchronized (this.mOutputTrackMutex) {
            this.mOutputTrack = bVar;
            if (this.mOutputTrack == null) {
                stopRecording();
            }
        }
    }

    public abstract void startRecording();

    public abstract void stopRecording();
}
