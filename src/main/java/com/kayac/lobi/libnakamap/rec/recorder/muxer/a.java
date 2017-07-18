package com.kayac.lobi.libnakamap.rec.recorder.muxer;

import android.media.MediaCodec.BufferInfo;
import android.media.MediaFormat;
import com.kayac.lobi.libnakamap.rec.recorder.a.d;
import java.nio.ByteBuffer;

public abstract class a {
    private static final String TAG = a.class.getSimpleName();
    protected d mConfig;
    private boolean mIsRunning = false;

    public a(d dVar) {
        this.mConfig = dVar;
    }

    public abstract void addAudioTrack(MediaFormat mediaFormat);

    public abstract void addScreenTrack(MediaFormat mediaFormat);

    public boolean isRunning() {
        return this.mIsRunning;
    }

    public void start() throws com.kayac.lobi.libnakamap.rec.b.a.a {
        this.mIsRunning = true;
    }

    public void stop() {
        this.mIsRunning = false;
    }

    public abstract void writeAudioSampleData(ByteBuffer byteBuffer, BufferInfo bufferInfo);

    public abstract void writeScreenSampleData(ByteBuffer byteBuffer, BufferInfo bufferInfo);
}
