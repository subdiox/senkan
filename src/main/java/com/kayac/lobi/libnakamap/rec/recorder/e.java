package com.kayac.lobi.libnakamap.rec.recorder;

import com.kayac.lobi.libnakamap.rec.a.b;

class e implements Runnable {
    final /* synthetic */ AudioMixer a;

    e(AudioMixer audioMixer) {
        this.a = audioMixer;
    }

    public void run() {
        AudioMixer audioMixer;
        loop0:
        while (true) {
            try {
                short[] nextData = this.a.getNextData(this.a.mBufferSizeInByte / 4);
                if (nextData == null) {
                    nextData = new short[(this.a.mBufferSizeInByte / 4)];
                }
                int i = 0;
                while (i < nextData.length) {
                    int write = this.a.c.write(nextData, i + 0, nextData.length - i);
                    if (write != -3) {
                        if (write == 0) {
                            break;
                        }
                        i += write;
                    } else {
                        break loop0;
                    }
                }
            } catch (Throwable e) {
                b.a(e);
                AudioMixer.b.c("playBackgroundMusic: error state");
                audioMixer = this.a;
            } catch (Throwable th) {
                this.a.e = null;
            }
        }
        AudioMixer.b.c("write fail : AudioTrack.ERROR_INVALID_OPERATION");
        audioMixer = this.a;
        audioMixer.e = null;
    }
}
