package com.kayac.lobi.libnakamap.rec.cocos2dx;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import com.kayac.lobi.libnakamap.rec.LobiRecAPI;
import com.kayac.lobi.libnakamap.rec.a.b;
import com.kayac.lobi.libnakamap.rec.recorder.AudioMixer;
import com.kayac.lobi.libnakamap.rec.recorder.AudioMixer.a;
import com.kayac.lobi.libnakamap.rec.recorder.MediaCodecHelper;
import com.kayac.lobi.libnakamap.rec.recorder.MicInput;
import com.kayac.lobi.libnakamap.rec.recorder.h;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.List;

public class LobiAudioEngine {
    private static final String TAG = LobiAudioEngine.class.getSimpleName();
    private static Context sContext;
    private static Boolean sIsSupport;
    private static List<MusicPlayer> sMusicPlayerList;

    public static class MusicPlayer implements a {
        private static final int STATUS_PAUSE = 2;
        private static final int STATUS_PLAY = 1;
        private static final int STATUS_STOP = 0;
        private int mChannelCount = 1;
        private MediaCodec mCodec = null;
        private ByteBuffer[] mCodecInputBuffers = null;
        private ByteBuffer[] mCodecOutputBuffers = null;
        private Context mContext = null;
        private float mDuration;
        private MediaExtractor mExtractor = null;
        private int mId;
        private boolean mIsInputEOS = false;
        private boolean mIsLooping = false;
        private boolean mIsOutputEOS = false;
        private boolean mIsRewind = false;
        private long mLobiAudioEnginePlayer;
        private String mPath = null;
        private short[] mRest = new short[88200];
        private int mRestPosition = 0;
        private int mSampleRate = MicInput.SAMPLE_RATE;
        private int mStatus = 0;
        private short[] mUpdataOutputBufferResult = null;
        private ByteBuffer mUpdateOutputBufferConvertByteBuffer = null;
        private ShortBuffer mUpdateOutputBufferConvertShortBuffer = null;
        private float mVolume = 0.5f;

        private void updateInputBuffer(long j) {
            long j2 = 0;
            int dequeueInputBuffer = this.mCodec.dequeueInputBuffer(j);
            if (dequeueInputBuffer >= 0) {
                int readSampleData = this.mExtractor.readSampleData(this.mCodecInputBuffers[dequeueInputBuffer], 0);
                if (readSampleData < 0) {
                    b.a(LobiAudioEngine.TAG, "saw input EOS.");
                    if (this.mIsLooping) {
                        this.mExtractor.seekTo(0, 0);
                        this.mIsInputEOS = false;
                        this.mIsOutputEOS = false;
                    } else {
                        this.mIsInputEOS = true;
                    }
                    LobiAudioEngine.nativePlayOver(this.mLobiAudioEnginePlayer);
                    readSampleData = 0;
                } else {
                    j2 = this.mExtractor.getSampleTime();
                }
                this.mCodec.queueInputBuffer(dequeueInputBuffer, 0, readSampleData, j2, this.mIsInputEOS ? 4 : 0);
                if (!this.mIsInputEOS) {
                    this.mExtractor.advance();
                }
            }
        }

        private short[] updateOutputBuffer(long j, BufferInfo bufferInfo) {
            int dequeueOutputBuffer = this.mCodec.dequeueOutputBuffer(bufferInfo, j);
            if (dequeueOutputBuffer >= 0) {
                ByteBuffer byteBuffer = this.mCodecOutputBuffers[dequeueOutputBuffer];
                if (this.mUpdateOutputBufferConvertByteBuffer == null || this.mUpdateOutputBufferConvertByteBuffer.capacity() != bufferInfo.size) {
                    this.mUpdateOutputBufferConvertByteBuffer = ByteBuffer.allocateDirect(bufferInfo.size);
                    this.mUpdateOutputBufferConvertByteBuffer.order(ByteOrder.nativeOrder());
                    this.mUpdateOutputBufferConvertShortBuffer = this.mUpdateOutputBufferConvertByteBuffer.asShortBuffer();
                }
                MediaCodecHelper.a(this.mUpdateOutputBufferConvertShortBuffer, bufferInfo.size / 2, byteBuffer, bufferInfo.size, this.mVolume);
                if (this.mUpdataOutputBufferResult == null || this.mUpdataOutputBufferResult.length != bufferInfo.size / 2) {
                    this.mUpdataOutputBufferResult = new short[(bufferInfo.size / 2)];
                }
                this.mUpdateOutputBufferConvertShortBuffer.get(this.mUpdataOutputBufferResult);
                byteBuffer.clear();
                this.mUpdateOutputBufferConvertShortBuffer.clear();
                this.mCodec.releaseOutputBuffer(dequeueOutputBuffer, false);
                if ((bufferInfo.flags & 4) != 0) {
                    b.a(LobiAudioEngine.TAG, "saw output EOS.");
                    this.mIsOutputEOS = true;
                }
            } else if (dequeueOutputBuffer == -3) {
                this.mCodecOutputBuffers = this.mCodec.getOutputBuffers();
                b.a(LobiAudioEngine.TAG, "output buffers have changed.");
            } else if (dequeueOutputBuffer == -2) {
                MediaFormat outputFormat = this.mCodec.getOutputFormat();
                this.mChannelCount = outputFormat.getInteger("channel-count");
                this.mSampleRate = outputFormat.getInteger("sample-rate");
                b.a(LobiAudioEngine.TAG, "output format has changed to " + outputFormat);
            }
            return this.mUpdataOutputBufferResult;
        }

        public float getCurrentTime() {
            return ((float) this.mExtractor.getSampleTime()) / 1000000.0f;
        }

        public float getDuration() {
            return this.mDuration;
        }

        public int getId() {
            return this.mId;
        }

        public short[] getNextData(int i) {
            int i2 = 0;
            synchronized (this) {
                short[] sArr;
                if (this.mStatus == 0) {
                    sArr = new short[i];
                    return sArr;
                } else if (this.mStatus == 2) {
                    sArr = new short[i];
                    return sArr;
                } else {
                    Object obj = new short[i];
                    if (this.mRestPosition > 0) {
                        if (this.mRestPosition < i) {
                            System.arraycopy(this.mRest, 0, obj, 0, this.mRestPosition);
                            i2 = 0 + this.mRestPosition;
                            this.mRestPosition = 0;
                        } else {
                            System.arraycopy(this.mRest, 0, obj, 0, obj.length);
                            System.arraycopy(this.mRest, obj.length, this.mRest, 0, this.mRestPosition - obj.length);
                            this.mRestPosition -= obj.length;
                            return obj;
                        }
                    }
                    while (i > i2) {
                        if (this.mIsOutputEOS) {
                            this.mStatus = 0;
                            return obj;
                        }
                        if (this.mIsRewind) {
                            this.mExtractor.seekTo(0, 0);
                            this.mIsInputEOS = false;
                            this.mIsOutputEOS = false;
                            this.mIsRewind = false;
                        }
                        BufferInfo bufferInfo = new BufferInfo();
                        if (!this.mIsInputEOS) {
                            updateInputBuffer(5000);
                        }
                        short[] updateOutputBuffer = updateOutputBuffer(5000, bufferInfo);
                        if (!(updateOutputBuffer == null || updateOutputBuffer.length == 0)) {
                            Object a = h.a(h.b.BGM, updateOutputBuffer, this.mSampleRate, this.mChannelCount);
                            if (a.length < obj.length - i2) {
                                System.arraycopy(a, 0, obj, i2, a.length);
                            } else {
                                int length = obj.length - i2;
                                System.arraycopy(a, 0, obj, i2, length);
                                System.arraycopy(a, length, this.mRest, this.mRestPosition, a.length - length);
                                this.mRestPosition = (a.length - length) + this.mRestPosition;
                            }
                            i2 += a.length;
                        }
                    }
                    return obj;
                }
            }
        }

        public boolean isEnd() {
            switch (this.mStatus) {
                case 0:
                    return true;
                default:
                    return false;
            }
        }

        public boolean isPlaying() {
            return this.mStatus == 1;
        }

        public void pause() {
            this.mStatus = 2;
        }

        public void play() {
            this.mStatus = 1;
            AudioMixer.b().a((a) this);
        }

        public void prepare() throws IOException {
            if (this.mPath == null) {
                b.c(LobiAudioEngine.TAG, "mPath must be not null");
            } else if (this.mContext == null) {
                b.c(LobiAudioEngine.TAG, "mContext must be not null");
            } else {
                if (this.mPath.startsWith("/")) {
                    this.mExtractor = new MediaExtractor();
                    this.mExtractor.setDataSource(this.mPath);
                } else {
                    this.mExtractor = new MediaExtractor();
                    AssetFileDescriptor openFd = this.mContext.getAssets().openFd(this.mPath);
                    this.mExtractor.setDataSource(openFd.getFileDescriptor(), openFd.getStartOffset(), openFd.getLength());
                }
                if (this.mExtractor.getTrackCount() != 1) {
                    b.c(LobiAudioEngine.TAG, "invalid extractor.getTrackCount() : " + this.mExtractor.getTrackCount());
                }
                MediaFormat trackFormat = this.mExtractor.getTrackFormat(0);
                this.mChannelCount = trackFormat.getInteger("channel-count");
                this.mSampleRate = trackFormat.getInteger("sample-rate");
                this.mDuration = ((float) trackFormat.getLong("durationUs")) / 1000000.0f;
                String string = trackFormat.getString("mime");
                if (!string.startsWith("audio/")) {
                    b.c(LobiAudioEngine.TAG, "invalid mime : " + string);
                }
                this.mCodec = MediaCodec.createDecoderByType(string);
                this.mCodec.configure(trackFormat, null, null, 0);
                this.mCodec.start();
                this.mCodecInputBuffers = this.mCodec.getInputBuffers();
                this.mCodecOutputBuffers = this.mCodec.getOutputBuffers();
                this.mExtractor.selectTrack(0);
            }
        }

        public void resume() {
            this.mStatus = 1;
        }

        public void rewind() {
            this.mIsRewind = true;
            this.mStatus = 1;
        }

        public void seekTo(int i) {
            synchronized (this) {
                this.mExtractor.seekTo(((long) i) * 1000000, 0);
            }
        }

        public void setContext(Context context) {
            this.mContext = context;
        }

        public void setId(int i) {
            this.mId = i;
        }

        public void setLobiAudioEnginePlayer(long j) {
            this.mLobiAudioEnginePlayer = j;
        }

        public void setLooping(boolean z) {
            this.mIsLooping = z;
        }

        public void setPath(String str) {
            this.mPath = str;
        }

        public void setVolume(float f) {
            this.mVolume = f;
        }

        public void stop() {
            this.mStatus = 0;
            LobiAudioEngine.nativePlayOver(this.mLobiAudioEnginePlayer);
        }
    }

    public static float getCurrentTime(int i) {
        MusicPlayer musicPlayer = getMusicPlayer(i);
        return musicPlayer != null ? musicPlayer.getCurrentTime() : 0.0f;
    }

    public static float getDuration(int i) {
        MusicPlayer musicPlayer = getMusicPlayer(i);
        return musicPlayer != null ? musicPlayer.getDuration() : -1.0f;
    }

    private static MusicPlayer getMusicPlayer(int i) {
        for (MusicPlayer musicPlayer : sMusicPlayerList) {
            if (musicPlayer.getId() == i) {
                return musicPlayer;
            }
        }
        return null;
    }

    public static void initSetup(Context context) {
        sContext = context;
        sMusicPlayerList = new ArrayList();
    }

    public static boolean isEnable() {
        if (sIsSupport == null) {
            sIsSupport = Boolean.valueOf(LobiAudio.isSupport());
        }
        return LobiRecAPI.isCapturing() && sIsSupport.booleanValue();
    }

    private static native void nativePlayOver(long j);

    public static void pause(int i) {
        MusicPlayer musicPlayer = getMusicPlayer(i);
        if (musicPlayer != null && musicPlayer.isPlaying()) {
            musicPlayer.pause();
        }
    }

    public static int play2d(long j, String str, boolean z, float f) {
        MusicPlayer musicPlayer = new MusicPlayer();
        int size = sMusicPlayerList.size();
        musicPlayer.setContext(sContext);
        musicPlayer.setId(size);
        musicPlayer.setLobiAudioEnginePlayer(j);
        musicPlayer.setPath(str);
        musicPlayer.setLooping(z);
        musicPlayer.setVolume(f);
        try {
            musicPlayer.prepare();
            musicPlayer.play();
            sMusicPlayerList.add(musicPlayer);
            return size;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static void resume(int i) {
        MusicPlayer musicPlayer = getMusicPlayer(i);
        if (musicPlayer != null) {
            musicPlayer.resume();
        }
    }

    public static boolean setCurrentTime(int i, float f) {
        MusicPlayer musicPlayer = getMusicPlayer(i);
        if (musicPlayer == null) {
            return false;
        }
        musicPlayer.seekTo((int) f);
        return true;
    }

    public static void setLoop(int i, boolean z) {
        MusicPlayer musicPlayer = getMusicPlayer(i);
        if (musicPlayer != null) {
            musicPlayer.setLooping(z);
        }
    }

    public static void setVolume(int i, float f) {
        MusicPlayer musicPlayer = getMusicPlayer(i);
        if (musicPlayer != null) {
            musicPlayer.setVolume(f);
        }
    }

    public static void stop(int i) {
        MusicPlayer musicPlayer = getMusicPlayer(i);
        if (musicPlayer != null) {
            musicPlayer.stop();
        }
    }
}
