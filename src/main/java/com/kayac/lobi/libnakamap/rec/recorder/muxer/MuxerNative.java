package com.kayac.lobi.libnakamap.rec.recorder.muxer;

import android.annotation.TargetApi;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaFormat;
import com.kayac.lobi.libnakamap.rec.a.b;
import com.kayac.lobi.libnakamap.rec.b.a.a;
import com.kayac.lobi.libnakamap.rec.recorder.a.d;
import java.nio.ByteBuffer;
import junit.framework.Assert;

@TargetApi(16)
public class MuxerNative extends a {
    private static final String TAG = MuxerNative.class.getSimpleName();
    private static final b sLog = new b(TAG);
    private boolean mIsDisposed = false;
    private int mNativeContext;

    public MuxerNative(d dVar) {
        super(dVar);
        nativeInit();
    }

    private native void nativeConfigureAudioWriter(String str, int i, int i2, int i3);

    private native void nativeConfigureScreenWriter(String str, int i, int i2, int i3, int i4, int i5, int i6, int i7);

    private native void nativeInit();

    private native void nativeStart(String str, boolean z);

    private native void nativeStop();

    private native void nativeWriteAudioSampleData(byte[] bArr, int i, long j, int i2, int i3, int i4);

    private native void nativeWriteScreenSampleData(byte[] bArr, int i, long j, int i2, int i3, int i4);

    public void addAudioTrack(MediaFormat mediaFormat) {
        nativeConfigureAudioWriter(mediaFormat.getString("mime"), mediaFormat.getInteger("sample-rate"), mediaFormat.getInteger("channel-count"), mediaFormat.getInteger("bitrate"));
    }

    public void addScreenTrack(MediaFormat mediaFormat) {
        nativeConfigureScreenWriter(mediaFormat.getString("mime"), mediaFormat.getInteger("width"), mediaFormat.getInteger("height"), mediaFormat.getInteger("bitrate"), mediaFormat.getInteger("frame-rate"), mediaFormat.getInteger("i-frame-interval"), mediaFormat.getInteger("stride"), mediaFormat.getInteger("slice-height"));
    }

    public void start() throws a {
        Assert.assertFalse(this.mIsDisposed);
        sLog.a("Start");
        nativeStart(this.mConfig.b(), this.mConfig.a());
        super.start();
    }

    public void stop() {
        this.mIsDisposed = true;
        synchronized (this) {
            if (isRunning()) {
                nativeStop();
            } else {
                sLog.b("stop() called but no muxer to stop");
            }
            super.stop();
        }
    }

    public void writeAudioSampleData(ByteBuffer byteBuffer, BufferInfo bufferInfo) {
        synchronized (this) {
            if (isRunning()) {
                byte[] bArr = new byte[bufferInfo.size];
                byteBuffer.get(bArr, 0, bufferInfo.size);
                sLog.a("write audio sample");
                nativeWriteAudioSampleData(bArr, bArr.length, bufferInfo.presentationTimeUs, bufferInfo.flags & 2, bufferInfo.flags & 1, bufferInfo.flags & 4);
                sLog.a("write audio sample done");
                return;
            }
        }
    }

    public void writeScreenSampleData(ByteBuffer byteBuffer, BufferInfo bufferInfo) {
        synchronized (this) {
            if (isRunning()) {
                byte[] bArr = new byte[bufferInfo.size];
                byteBuffer.get(bArr, 0, bufferInfo.size);
                sLog.a("write screen sample");
                nativeWriteScreenSampleData(bArr, bArr.length, bufferInfo.presentationTimeUs, bufferInfo.flags & 2, bufferInfo.flags & 1, bufferInfo.flags & 4);
                sLog.a("write screen sample done");
                return;
            }
        }
    }
}
