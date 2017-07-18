package com.kayac.lobi.libnakamap.rec.recorder;

import android.annotation.TargetApi;
import android.media.AudioRecord;
import com.kayac.lobi.libnakamap.rec.a.b;
import com.kayac.lobi.libnakamap.rec.cocos2dx.AudioResampleHelper;
import com.kayac.lobi.libnakamap.rec.recorder.f.a;
import com.kayac.lobi.sdk.LobiCore;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@TargetApi(5)
public class MicInput implements a {
    private static final b LOG = new b(TAG);
    public static final int SAMPLE_RATE = 44100;
    private static final int STATUS_ERROR = -1;
    private static final int STATUS_PLAY = 1;
    private static final int STATUS_STOP = 0;
    private static final String TAG = MicInput.class.getSimpleName();
    private static MicInput sInstance;
    private int mBufferSizeInByte = 0;
    private Executor mExecutor = null;
    private f.b mOutputTrack = null;
    private final Object[] mOutputTrackMutex = new Object[0];
    private AudioResampleHelper mResampler;
    private int mSampleRate = 0;
    private int mStatus = 0;

    private MicInput(int i, int i2) {
        this.mSampleRate = i;
        this.mBufferSizeInByte = i2 * 2;
        if (i != com.kayac.lobi.libnakamap.rec.recorder.a.a.b) {
            this.mResampler = new AudioResampleHelper();
            this.mResampler.setInputFormat(i, 1);
            LOG.b("sample rate: " + this.mSampleRate + ", buffer size: " + this.mBufferSizeInByte + " bytes, mixing unit: " + this.mResampler.getMixingUnit());
            return;
        }
        this.mResampler = null;
    }

    private void cleanupAudioRecord(AudioRecord audioRecord) {
        if (audioRecord != null) {
            try {
                audioRecord.stop();
            } catch (Throwable e) {
                b.a(e);
            }
            audioRecord.release();
        }
    }

    public static final MicInput getInstance() {
        return sInstance;
    }

    public static void init() {
        if (sInstance == null && LobiCore.sharedInstance().getContext().getPackageManager().hasSystemFeature("android.hardware.microphone")) {
            int minBufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, 16, 2);
            if (minBufferSize != -1 && minBufferSize != -2) {
                sInstance = new MicInput(SAMPLE_RATE, minBufferSize);
            }
        }
    }

    private AudioRecord newAudioRecord() throws IllegalArgumentException {
        return new AudioRecord(1, this.mSampleRate, 16, 2, this.mBufferSizeInByte);
    }

    public boolean alreadyUsed() {
        LOG.b("check if mic is available...");
        boolean z = false;
        AudioRecord audioRecord = null;
        try {
            audioRecord = newAudioRecord();
            audioRecord.startRecording();
            if (audioRecord.getRecordingState() != 3) {
                throw new IllegalStateException("AudioRecord is stopped");
            }
            LOG.b("available");
            cleanupAudioRecord(audioRecord);
            return z;
        } catch (Throwable e) {
            b.a(e);
            z = true;
            LOG.b("unavailable");
        } catch (Throwable th) {
            cleanupAudioRecord(audioRecord);
        }
    }

    public int getBufferSizeInByte() {
        return this.mBufferSizeInByte;
    }

    public void play() {
        synchronized (this) {
            if (this.mExecutor != null) {
                return;
            }
            this.mExecutor = Executors.newSingleThreadExecutor();
            this.mExecutor.execute(new s(this));
        }
    }

    public void setAudioOutputTrack(f.b bVar) {
        synchronized (this.mOutputTrackMutex) {
            this.mOutputTrack = bVar;
            if (this.mOutputTrack == null) {
                stop();
            } else if (this.mStatus == -1) {
                this.mOutputTrack.e();
            }
        }
    }

    public void stop() {
        this.mStatus = 0;
    }
}
