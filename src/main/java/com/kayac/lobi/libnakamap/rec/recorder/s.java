package com.kayac.lobi.libnakamap.rec.recorder;

import android.media.AudioRecord;
import com.kayac.lobi.libnakamap.rec.a.b;
import com.kayac.lobi.libnakamap.rec.recorder.a.a;

class s implements Runnable {
    final /* synthetic */ MicInput a;

    s(MicInput micInput) {
        this.a = micInput;
    }

    public void run() {
        Throwable e;
        AudioRecord access$000;
        MicInput micInput;
        try {
            access$000 = this.a.newAudioRecord();
            try {
                access$000.startRecording();
                this.a.mStatus = 1;
                short[] sArr = new short[this.a.mBufferSizeInByte];
                int i = 0;
                while (this.a.mStatus != 0) {
                    int read = access$000.read(sArr, i, Math.min(this.a.mBufferSizeInByte / 2, sArr.length - i));
                    if (read > 0) {
                        short[] resample;
                        int length;
                        read += i;
                        if (this.a.mResampler != null) {
                            int[] iArr = new int[]{0};
                            resample = this.a.mResampler.resample(sArr, read, iArr);
                            length = resample.length;
                            i = iArr[0];
                        } else {
                            length = read;
                            i = 0;
                            resample = sArr;
                        }
                        synchronized (this.a.mOutputTrackMutex) {
                            if (this.a.mOutputTrack != null) {
                                this.a.mOutputTrack.a(resample, length, a.b, 1);
                            }
                        }
                    }
                }
                this.a.cleanupAudioRecord(access$000);
                this.a.mExecutor = null;
                if (this.a.mStatus != -1) {
                    micInput = this.a;
                    micInput.mStatus = 0;
                }
            } catch (RuntimeException e2) {
                e = e2;
            }
        } catch (RuntimeException e3) {
            e = e3;
            access$000 = null;
            try {
                b.a(e);
                this.a.mStatus = -1;
                synchronized (this.a.mOutputTrackMutex) {
                    if (this.a.mOutputTrack != null) {
                        this.a.mOutputTrack.e();
                    }
                }
                this.a.cleanupAudioRecord(access$000);
                this.a.mExecutor = null;
                if (this.a.mStatus != -1) {
                    micInput = this.a;
                    micInput.mStatus = 0;
                }
            } catch (Throwable th) {
                e = th;
            }
        } catch (Throwable th2) {
            e = th2;
            access$000 = null;
            this.a.cleanupAudioRecord(access$000);
            this.a.mExecutor = null;
            if (this.a.mStatus != -1) {
                this.a.mStatus = 0;
            }
            throw e;
        }
    }
}
