package com.kayac.lobi.libnakamap.rec.cocos2dx;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioTrack;
import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import com.kayac.lobi.libnakamap.rec.a.b;
import com.kayac.lobi.libnakamap.rec.recorder.AudioMixer;
import com.kayac.lobi.libnakamap.rec.recorder.MediaCodecHelper;
import com.kayac.lobi.libnakamap.rec.recorder.MicInput;
import com.kayac.lobi.libnakamap.rec.recorder.h;
import com.kayac.lobi.libnakamap.rec.recorder.h.a;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class LobiSound {
    private static final int INVALID_SOUND_ID = -1;
    private static final int INVALID_STREAM_ID = -1;
    private static final String TAG = LobiSound.class.getSimpleName();
    private final Context mContext;
    private final AtomicInteger mCurrentSoundID = new AtomicInteger(65534);
    private final AtomicInteger mCurrentStreamID = new AtomicInteger(65534);
    private final List<SoundPlayer> mSoundPlayers = new ArrayList();
    private float mVolume = 0.5f;

    public static class SoundPlayer {
        private List<short[]> mAudioData = new ArrayList();
        private int mChannelCount = 1;
        private Context mContext = null;
        private String mPath = null;
        private int mSampleRate = MicInput.SAMPLE_RATE;
        private int mSoundID = -1;
        private List<SoundTrack> mSoundTracks = new ArrayList();
        private float mVolume = 0.5f;

        public short[] getAudioData(int i) {
            return this.mAudioData.size() < i ? null : (short[]) this.mAudioData.get(i);
        }

        public int getAudioDataLength() {
            return this.mAudioData.size();
        }

        public String getPath() {
            return this.mPath;
        }

        public int getSoundID() {
            return this.mSoundID;
        }

        public SoundTrack getSoundTrack(int i) {
            SoundTrack soundTrack;
            synchronized (this.mSoundTracks) {
                for (SoundTrack soundTrack2 : this.mSoundTracks) {
                    if (soundTrack2.getStreamID() == i) {
                        break;
                    }
                }
                soundTrack2 = null;
            }
            return soundTrack2;
        }

        public void onEnterBackground() {
            synchronized (this.mSoundTracks) {
                for (SoundTrack onEnterBackground : this.mSoundTracks) {
                    onEnterBackground.onEnterBackground();
                }
            }
        }

        public void onEnterForeground() {
            synchronized (this.mSoundTracks) {
                for (SoundTrack onEnterForeground : this.mSoundTracks) {
                    onEnterForeground.onEnterForeground();
                }
            }
        }

        public void pause() {
            synchronized (this.mSoundTracks) {
                for (SoundTrack pause : this.mSoundTracks) {
                    pause.pause();
                }
            }
        }

        public int play(int i, boolean z) {
            SoundTrack soundTrack = new SoundTrack();
            soundTrack.setSoundPlayer(this);
            soundTrack.setStreamID(i);
            soundTrack.setAudioDataSize(this.mAudioData.size());
            soundTrack.setChannelCount(this.mChannelCount);
            soundTrack.setSampleRate(this.mSampleRate);
            float minVolume = AudioTrack.getMinVolume();
            soundTrack.setVolume(minVolume + ((AudioTrack.getMaxVolume() - minVolume) * this.mVolume));
            soundTrack.play(z);
            synchronized (this.mSoundTracks) {
                this.mSoundTracks.add(soundTrack);
            }
            return i;
        }

        public void prepare() throws IOException {
            Throwable th;
            MediaCodec mediaCodec;
            MediaCodec mediaCodec2 = null;
            MediaExtractor mediaExtractor = null;
            try {
                if (this.mPath == null) {
                    b.c(LobiSound.TAG, "mPath must be not null");
                    if (mediaCodec2 != null) {
                        mediaCodec2.release();
                    }
                    if (mediaExtractor == null) {
                        return;
                    }
                } else if (this.mContext == null) {
                    b.c(LobiSound.TAG, "mContext must be not null");
                    if (mediaCodec2 != null) {
                        mediaCodec2.release();
                    }
                    if (mediaExtractor == null) {
                        return;
                    }
                } else {
                    MediaExtractor mediaExtractor2;
                    if (this.mPath.startsWith("/")) {
                        MediaExtractor mediaExtractor3 = new MediaExtractor();
                        try {
                            mediaExtractor3.setDataSource(this.mPath);
                            mediaExtractor2 = mediaExtractor3;
                        } catch (Throwable th2) {
                            th = th2;
                            mediaExtractor = mediaExtractor3;
                            mediaCodec = mediaCodec2;
                            if (mediaCodec != null) {
                                mediaCodec.release();
                            }
                            if (mediaExtractor != null) {
                                mediaExtractor.release();
                            }
                            throw th;
                        }
                    }
                    MediaExtractor mediaExtractor4 = new MediaExtractor();
                    try {
                        AssetFileDescriptor openFd = this.mContext.getAssets().openFd(this.mPath);
                        mediaExtractor4.setDataSource(openFd.getFileDescriptor(), openFd.getStartOffset(), openFd.getLength());
                        mediaExtractor2 = mediaExtractor4;
                    } catch (Throwable th3) {
                        mediaCodec = mediaCodec2;
                        MediaExtractor mediaExtractor5 = mediaExtractor4;
                        th = th3;
                        mediaExtractor = mediaExtractor5;
                        if (mediaCodec != null) {
                            mediaCodec.release();
                        }
                        if (mediaExtractor != null) {
                            mediaExtractor.release();
                        }
                        throw th;
                    }
                    try {
                        if (mediaExtractor2.getTrackCount() != 1) {
                            b.c(LobiSound.TAG, "invalid extractor.getTrackCount() : " + mediaExtractor2.getTrackCount());
                        }
                        MediaFormat trackFormat = mediaExtractor2.getTrackFormat(0);
                        this.mChannelCount = trackFormat.getInteger("channel-count");
                        this.mSampleRate = trackFormat.getInteger("sample-rate");
                        String string = trackFormat.getString("mime");
                        if (!string.startsWith("audio/")) {
                            b.c(LobiSound.TAG, "invalid mime : " + string);
                        }
                        MediaCodec createDecoderByType = MediaCodec.createDecoderByType(string);
                        createDecoderByType.configure(trackFormat, null, null, 0);
                        createDecoderByType.start();
                        ByteBuffer[] inputBuffers = createDecoderByType.getInputBuffers();
                        ByteBuffer[] outputBuffers = createDecoderByType.getOutputBuffers();
                        mediaExtractor2.selectTrack(0);
                        AudioResampleHelper a = a.a(h.b.SE, this.mSampleRate, this.mChannelCount);
                        Object obj = null;
                        Object obj2 = null;
                        Object obj3 = null;
                        int[] iArr = new int[]{0};
                        while (obj2 == null) {
                            Object obj4;
                            int dequeueOutputBuffer;
                            ByteBuffer byteBuffer;
                            byte[] bArr;
                            int length;
                            Object obj5;
                            int i;
                            ByteBuffer[] byteBufferArr;
                            MediaFormat outputFormat;
                            BufferInfo bufferInfo = new BufferInfo();
                            if (obj == null) {
                                int dequeueInputBuffer = createDecoderByType.dequeueInputBuffer(5000);
                                if (dequeueInputBuffer >= 0) {
                                    Object obj6;
                                    int readSampleData = mediaExtractor2.readSampleData(inputBuffers[dequeueInputBuffer], 0);
                                    long j = 0;
                                    if (readSampleData < 0) {
                                        b.a(LobiSound.TAG, "saw input EOS.");
                                        readSampleData = 0;
                                        obj6 = 1;
                                    } else {
                                        j = mediaExtractor2.getSampleTime();
                                        obj6 = obj;
                                    }
                                    createDecoderByType.queueInputBuffer(dequeueInputBuffer, 0, readSampleData, j, obj6 != null ? 4 : 0);
                                    if (obj6 == null) {
                                        mediaExtractor2.advance();
                                    }
                                    obj4 = obj6;
                                    dequeueOutputBuffer = createDecoderByType.dequeueOutputBuffer(bufferInfo, 5000);
                                    if (dequeueOutputBuffer >= 0) {
                                        byteBuffer = outputBuffers[dequeueOutputBuffer];
                                        bArr = new byte[bufferInfo.size];
                                        byteBuffer.get(bArr);
                                        length = (bArr.length / 2) + iArr[0];
                                        if (obj3 != null || obj3.length < length) {
                                            obj5 = new short[length];
                                            if (obj3 != null && iArr[0] > 0) {
                                                System.arraycopy(obj3, 0, obj5, 0, iArr[0]);
                                            }
                                        } else {
                                            obj5 = obj3;
                                        }
                                        for (i = 0; i < bArr.length / 2; i++) {
                                            obj5[iArr[0] + i] = (short) ((bArr[(i * 2) + 1] << 8) + bArr[i * 2]);
                                        }
                                        this.mAudioData.add(a.resample(obj5, length, iArr));
                                        byteBuffer.clear();
                                        createDecoderByType.releaseOutputBuffer(dequeueOutputBuffer, false);
                                        if ((bufferInfo.flags & 4) == 0) {
                                            b.a(LobiSound.TAG, "saw output EOS.");
                                            obj = 1;
                                            this.mAudioData.add(a.resampleMixingUnit(obj5, iArr[0]));
                                        } else {
                                            obj = obj2;
                                        }
                                        obj3 = obj5;
                                        obj2 = obj;
                                        byteBufferArr = outputBuffers;
                                    } else if (dequeueOutputBuffer != -3) {
                                        byteBufferArr = createDecoderByType.getOutputBuffers();
                                        b.a(LobiSound.TAG, "output buffers have changed.");
                                    } else {
                                        if (dequeueOutputBuffer == -2) {
                                            try {
                                                outputFormat = createDecoderByType.getOutputFormat();
                                                this.mChannelCount = trackFormat.getInteger("channel-count");
                                                this.mSampleRate = trackFormat.getInteger("sample-rate");
                                                b.a(LobiSound.TAG, "output format has changed to " + outputFormat);
                                            } catch (Throwable th32) {
                                                mediaCodec = createDecoderByType;
                                                th = th32;
                                                mediaExtractor = mediaExtractor2;
                                            }
                                        }
                                        byteBufferArr = outputBuffers;
                                    }
                                    obj = obj4;
                                    outputBuffers = byteBufferArr;
                                }
                            }
                            obj4 = obj;
                            dequeueOutputBuffer = createDecoderByType.dequeueOutputBuffer(bufferInfo, 5000);
                            if (dequeueOutputBuffer >= 0) {
                                byteBuffer = outputBuffers[dequeueOutputBuffer];
                                bArr = new byte[bufferInfo.size];
                                byteBuffer.get(bArr);
                                length = (bArr.length / 2) + iArr[0];
                                if (obj3 != null) {
                                }
                                obj5 = new short[length];
                                System.arraycopy(obj3, 0, obj5, 0, iArr[0]);
                                for (i = 0; i < bArr.length / 2; i++) {
                                    obj5[iArr[0] + i] = (short) ((bArr[(i * 2) + 1] << 8) + bArr[i * 2]);
                                }
                                this.mAudioData.add(a.resample(obj5, length, iArr));
                                byteBuffer.clear();
                                createDecoderByType.releaseOutputBuffer(dequeueOutputBuffer, false);
                                if ((bufferInfo.flags & 4) == 0) {
                                    obj = obj2;
                                } else {
                                    b.a(LobiSound.TAG, "saw output EOS.");
                                    obj = 1;
                                    this.mAudioData.add(a.resampleMixingUnit(obj5, iArr[0]));
                                }
                                obj3 = obj5;
                                obj2 = obj;
                                byteBufferArr = outputBuffers;
                            } else if (dequeueOutputBuffer != -3) {
                                if (dequeueOutputBuffer == -2) {
                                    outputFormat = createDecoderByType.getOutputFormat();
                                    this.mChannelCount = trackFormat.getInteger("channel-count");
                                    this.mSampleRate = trackFormat.getInteger("sample-rate");
                                    b.a(LobiSound.TAG, "output format has changed to " + outputFormat);
                                }
                                byteBufferArr = outputBuffers;
                            } else {
                                byteBufferArr = createDecoderByType.getOutputBuffers();
                                b.a(LobiSound.TAG, "output buffers have changed.");
                            }
                            obj = obj4;
                            outputBuffers = byteBufferArr;
                        }
                        if (createDecoderByType != null) {
                            createDecoderByType.release();
                        }
                        if (mediaExtractor2 != null) {
                            mediaExtractor2.release();
                            return;
                        }
                        return;
                    } catch (Throwable th4) {
                        th = th4;
                        mediaExtractor = mediaExtractor2;
                        mediaCodec = mediaCodec2;
                        if (mediaCodec != null) {
                            mediaCodec.release();
                        }
                        if (mediaExtractor != null) {
                            mediaExtractor.release();
                        }
                        throw th;
                    }
                }
                mediaExtractor.release();
            } catch (Throwable th5) {
                th = th5;
                mediaCodec = mediaCodec2;
                if (mediaCodec != null) {
                    mediaCodec.release();
                }
                if (mediaExtractor != null) {
                    mediaExtractor.release();
                }
                throw th;
            }
        }

        public void removeSoundTrack(SoundTrack soundTrack) {
            synchronized (this.mSoundTracks) {
                this.mSoundTracks.remove(soundTrack);
            }
        }

        public void resume() {
            synchronized (this.mSoundTracks) {
                for (SoundTrack resume : this.mSoundTracks) {
                    resume.resume();
                }
            }
        }

        public void setContext(Context context) {
            this.mContext = context;
        }

        public void setPath(String str) {
            this.mPath = str;
        }

        public void setSoundID(int i) {
            this.mSoundID = i;
        }

        public void setVolume(float f) {
            this.mVolume = f;
            synchronized (this.mSoundTracks) {
                for (SoundTrack volume : this.mSoundTracks) {
                    volume.setVolume(f);
                }
            }
        }

        public void stop() {
            synchronized (this.mSoundTracks) {
                for (SoundTrack stop : this.mSoundTracks) {
                    stop.stop();
                }
            }
        }
    }

    private static class SoundTrack implements AudioMixer.a {
        private static final int STATUS_PLAY = 1;
        private static final int STATUS_PUASE = 2;
        private static final int STATUS_STOP = 0;
        private int mAudioDataPosition;
        private int mAudioDateSize;
        private int mChannelCount;
        private boolean mIsLooping;
        private short[] mRest;
        private int mRestPosition;
        private boolean mResumeOnEnterForeground;
        private int mSampleRate;
        private WeakReference<SoundPlayer> mSoundPlayer;
        private int mStatus;
        private int mStreamID;
        private float mVolume;

        private SoundTrack() {
            this.mSoundPlayer = null;
            this.mVolume = 0.5f;
            this.mIsLooping = false;
            this.mChannelCount = 1;
            this.mSampleRate = MicInput.SAMPLE_RATE;
            this.mStreamID = 0;
            this.mAudioDataPosition = 0;
            this.mAudioDateSize = 0;
            this.mStatus = 0;
            this.mResumeOnEnterForeground = false;
            this.mRest = new short[88200];
            this.mRestPosition = 0;
        }

        public short[] getNextData(int i) {
            if (this.mStatus == 2) {
                return new short[i];
            }
            int i2;
            Object obj = new short[i];
            if (this.mRestPosition <= 0) {
                i2 = 0;
            } else if (this.mRestPosition < i) {
                System.arraycopy(this.mRest, 0, obj, 0, this.mRestPosition);
                int i3 = this.mRestPosition + 0;
                this.mRestPosition = 0;
                i2 = i3;
            } else {
                System.arraycopy(this.mRest, 0, obj, 0, obj.length);
                System.arraycopy(this.mRest, obj.length, this.mRest, 0, this.mRestPosition - obj.length);
                this.mRestPosition -= obj.length;
                return obj;
            }
            while (i > i2) {
                SoundPlayer soundPlayer = (SoundPlayer) this.mSoundPlayer.get();
                if (soundPlayer == null) {
                    return new short[i];
                }
                if (this.mAudioDateSize <= this.mAudioDataPosition && this.mIsLooping) {
                    this.mAudioDataPosition = 0;
                }
                if (this.mAudioDataPosition < this.mAudioDateSize) {
                    Object audioData = soundPlayer.getAudioData(this.mAudioDataPosition);
                    if (audioData == null) {
                        return obj;
                    }
                    this.mAudioDataPosition++;
                    short[] sArr = (short[]) audioData.clone();
                    MediaCodecHelper.a(sArr, sArr.length, this.mVolume);
                    if (sArr.length < obj.length - i2) {
                        System.arraycopy(sArr, 0, obj, i2, sArr.length);
                    } else {
                        int length = obj.length - i2;
                        System.arraycopy(sArr, 0, obj, i2, length);
                        System.arraycopy(sArr, length, this.mRest, this.mRestPosition, sArr.length - length);
                        this.mRestPosition = (sArr.length - length) + this.mRestPosition;
                    }
                    i2 = sArr.length + i2;
                } else {
                    this.mStatus = 0;
                    return obj;
                }
            }
            return obj;
        }

        public int getStreamID() {
            return this.mStreamID;
        }

        public boolean isEnd() {
            switch (this.mStatus) {
                case 0:
                    SoundPlayer soundPlayer = (SoundPlayer) this.mSoundPlayer.get();
                    if (soundPlayer != null) {
                        soundPlayer.removeSoundTrack(this);
                    }
                    this.mSoundPlayer = null;
                    return true;
                default:
                    return false;
            }
        }

        public void onEnterBackground() {
            if (this.mStatus == 1) {
                this.mResumeOnEnterForeground = true;
                pause();
            }
        }

        public void onEnterForeground() {
            if (this.mResumeOnEnterForeground) {
                resume();
            }
            this.mResumeOnEnterForeground = false;
        }

        public void pause() {
            this.mStatus = 2;
        }

        public void play(boolean z) {
            this.mStatus = 1;
            AudioMixer.b().a((AudioMixer.a) this);
        }

        public void resume() {
            this.mStatus = 1;
        }

        public void setAudioDataSize(int i) {
            this.mAudioDateSize = i;
        }

        public void setChannelCount(int i) {
            this.mChannelCount = i;
        }

        public void setSampleRate(int i) {
            this.mSampleRate = i;
        }

        public void setSoundPlayer(SoundPlayer soundPlayer) {
            this.mSoundPlayer = new WeakReference(soundPlayer);
        }

        public void setStreamID(int i) {
            this.mStreamID = i;
        }

        public void setVolume(float f) {
            this.mVolume = f;
        }

        public void stop() {
            this.mStatus = 0;
        }
    }

    public LobiSound(Context context) {
        this.mContext = context;
    }

    public int createSoundIDFromAsset(String str) {
        return -1;
    }

    public void end() {
        for (SoundPlayer stop : this.mSoundPlayers) {
            stop.stop();
        }
        this.mVolume = 0.5f;
        this.mSoundPlayers.clear();
    }

    public float getEffectsVolume() {
        return this.mVolume;
    }

    public void onEnterBackground() {
        for (SoundPlayer onEnterBackground : this.mSoundPlayers) {
            onEnterBackground.onEnterBackground();
        }
    }

    public void onEnterForeground() {
        for (SoundPlayer onEnterForeground : this.mSoundPlayers) {
            onEnterForeground.onEnterForeground();
        }
    }

    public void pauseAllEffects() {
        for (SoundPlayer pause : this.mSoundPlayers) {
            pause.pause();
        }
    }

    public void pauseEffect(int i) {
        for (SoundPlayer soundTrack : this.mSoundPlayers) {
            SoundTrack soundTrack2 = soundTrack.getSoundTrack(i);
            if (soundTrack2 != null) {
                soundTrack2.pause();
                return;
            }
        }
    }

    public int playEffect(String str, boolean z) {
        for (SoundPlayer soundPlayer : this.mSoundPlayers) {
            if (str.equals(soundPlayer.getPath())) {
                return soundPlayer.play(this.mCurrentStreamID.addAndGet(-1), z);
            }
        }
        if (preloadEffect(str) != -1) {
            playEffect(str, z);
        }
        return -1;
    }

    public int preloadEffect(String str) {
        int addAndGet;
        for (SoundPlayer soundPlayer : this.mSoundPlayers) {
            if (str.equals(soundPlayer.getPath())) {
                return soundPlayer.getSoundID();
            }
        }
        SoundPlayer soundPlayer2 = new SoundPlayer();
        soundPlayer2.setContext(this.mContext);
        soundPlayer2.setPath(str);
        soundPlayer2.setVolume(this.mVolume);
        try {
            soundPlayer2.prepare();
            addAndGet = this.mCurrentSoundID.addAndGet(-1);
        } catch (Throwable e) {
            b.a(e);
            addAndGet = -1;
        }
        if (addAndGet == -1) {
            return addAndGet;
        }
        this.mSoundPlayers.add(soundPlayer2);
        return addAndGet;
    }

    public void resumeAllEffects() {
        for (SoundPlayer resume : this.mSoundPlayers) {
            resume.resume();
        }
    }

    public void resumeEffect(int i) {
        for (SoundPlayer soundTrack : this.mSoundPlayers) {
            SoundTrack soundTrack2 = soundTrack.getSoundTrack(i);
            if (soundTrack2 != null) {
                soundTrack2.resume();
                return;
            }
        }
    }

    public void setEffectsVolume(float f) {
        this.mVolume = f;
        for (SoundPlayer volume : this.mSoundPlayers) {
            volume.setVolume(f);
        }
    }

    public void stopAllEffects() {
        for (SoundPlayer stop : this.mSoundPlayers) {
            stop.stop();
        }
    }

    public void stopEffect(int i) {
        for (SoundPlayer soundTrack : this.mSoundPlayers) {
            SoundTrack soundTrack2 = soundTrack.getSoundTrack(i);
            if (soundTrack2 != null) {
                soundTrack2.stop();
                return;
            }
        }
    }

    public void unloadEffect(String str) {
        for (SoundPlayer soundPlayer : this.mSoundPlayers) {
            if (str.equals(soundPlayer.getPath())) {
                break;
            }
        }
        SoundPlayer soundPlayer2 = null;
        if (soundPlayer2 != null) {
            soundPlayer2.stop();
            this.mSoundPlayers.remove(soundPlayer2);
        }
    }
}
