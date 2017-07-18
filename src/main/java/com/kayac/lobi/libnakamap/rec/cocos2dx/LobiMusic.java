package com.kayac.lobi.libnakamap.rec.cocos2dx;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaExtractor;
import android.media.MediaFormat;
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

public class LobiMusic {
    private static final String TAG = LobiMusic.class.getSimpleName();
    private final Context mContext;
    private String mCurrentPath;
    private MusicPlayer mMusicPlayer;
    private boolean mResumeOnEnterForeground = false;
    private float mVolume;

    public static class MusicPlayer implements a {
        private static final int STATUS_PLAY = 1;
        private static final int STATUS_PUASE = 2;
        private static final int STATUS_STOP = 0;
        private int mChannelCount = 1;
        private MediaCodec mCodec = null;
        private ByteBuffer[] mCodecInputBuffers = null;
        private ByteBuffer[] mCodecOutputBuffers = null;
        private Context mContext = null;
        private MediaExtractor mExtractor = null;
        private boolean mIsInputEOS = false;
        private boolean mIsLooping = false;
        private boolean mIsOutputEOS = false;
        private boolean mIsRewind = false;
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
                    b.a(LobiMusic.TAG, "saw input EOS.");
                    if (this.mIsLooping) {
                        this.mExtractor.seekTo(0, 0);
                        this.mIsInputEOS = false;
                        this.mIsOutputEOS = false;
                        readSampleData = 0;
                    } else {
                        this.mIsInputEOS = true;
                        readSampleData = 0;
                    }
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
                    b.a(LobiMusic.TAG, "saw output EOS.");
                    this.mIsOutputEOS = true;
                }
            } else if (dequeueOutputBuffer == -3) {
                this.mCodecOutputBuffers = this.mCodec.getOutputBuffers();
                b.a(LobiMusic.TAG, "output buffers have changed.");
            } else if (dequeueOutputBuffer == -2) {
                MediaFormat outputFormat = this.mCodec.getOutputFormat();
                this.mChannelCount = outputFormat.getInteger("channel-count");
                this.mSampleRate = outputFormat.getInteger("sample-rate");
                b.a(LobiMusic.TAG, "output format has changed to " + outputFormat);
            }
            return this.mUpdataOutputBufferResult;
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

        public void play() {
            this.mStatus = 1;
            AudioMixer.b().a((a) this);
        }

        public void prepare() throws IOException {
            if (this.mPath == null) {
                b.c(LobiMusic.TAG, "mPath must be not null");
            } else if (this.mContext == null) {
                b.c(LobiMusic.TAG, "mContext must be not null");
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
                    b.c(LobiMusic.TAG, "invalid extractor.getTrackCount() : " + this.mExtractor.getTrackCount());
                }
                MediaFormat trackFormat = this.mExtractor.getTrackFormat(0);
                this.mChannelCount = trackFormat.getInteger("channel-count");
                this.mSampleRate = trackFormat.getInteger("sample-rate");
                String string = trackFormat.getString("mime");
                if (!string.startsWith("audio/")) {
                    b.c(LobiMusic.TAG, "invalid mime : " + string);
                }
                this.mCodec = MediaCodec.createDecoderByType(string);
                this.mCodec.configure(trackFormat, null, null, 0);
                this.mCodec.start();
                this.mCodecInputBuffers = this.mCodec.getInputBuffers();
                this.mCodecOutputBuffers = this.mCodec.getOutputBuffers();
                this.mExtractor.selectTrack(0);
            }
        }

        public void puase() {
            this.mStatus = 2;
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
                this.mExtractor.seekTo((long) (i * 1000), 0);
            }
        }

        public void setContext(Context context) {
            this.mContext = context;
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
        }
    }

    public LobiMusic(Context context) {
        this.mContext = context;
        initData();
    }

    private void _preloadBackgroundMusic(String str) {
        if (this.mCurrentPath == null || !this.mCurrentPath.equals(str)) {
            if (this.mMusicPlayer != null) {
                this.mMusicPlayer.stop();
            }
            this.mCurrentPath = str;
        }
        this.mMusicPlayer = new MusicPlayer();
        this.mMusicPlayer.setContext(this.mContext);
        this.mMusicPlayer.setPath(this.mCurrentPath);
        this.mMusicPlayer.setVolume(this.mVolume);
    }

    private void initData() {
        this.mVolume = 0.5f;
        this.mCurrentPath = null;
    }

    public void end() {
        if (this.mMusicPlayer != null) {
            this.mMusicPlayer.stop();
            this.mMusicPlayer = null;
        }
        initData();
    }

    public float getBackgroundVolume() {
        return this.mMusicPlayer != null ? this.mVolume : 0.0f;
    }

    public boolean isBackgroundMusicPlaying() {
        return this.mMusicPlayer == null ? false : this.mMusicPlayer.isPlaying();
    }

    public void onEnterBackground() {
        if (this.mMusicPlayer == null) {
            this.mResumeOnEnterForeground = false;
        } else if (this.mMusicPlayer.isPlaying()) {
            this.mResumeOnEnterForeground = true;
            this.mMusicPlayer.puase();
        }
    }

    public void onEnterForeground() {
        if (this.mMusicPlayer != null && this.mResumeOnEnterForeground) {
            this.mMusicPlayer.resume();
        }
        this.mResumeOnEnterForeground = false;
    }

    public void pauseBackgroundMusic() {
        if (this.mMusicPlayer != null && this.mMusicPlayer.isPlaying()) {
            this.mMusicPlayer.puase();
        }
    }

    public void playBackgroundMusic(String str, boolean z) {
        synchronized (this) {
            if (this.mMusicPlayer != null) {
                this.mMusicPlayer.stop();
                this.mMusicPlayer = null;
            }
            _preloadBackgroundMusic(str);
            if (this.mMusicPlayer == null) {
                b.c(TAG, "playBackgroundMusic: background data source is null");
            } else {
                try {
                    this.mMusicPlayer.prepare();
                    this.mMusicPlayer.seekTo(0);
                    this.mMusicPlayer.setLooping(z);
                    this.mMusicPlayer.play();
                } catch (Throwable e) {
                    b.c(TAG, "playBackgroundMusic: fail");
                    b.a(e);
                }
            }
        }
    }

    public void preloadBackgroundMusic(String str) {
        synchronized (this) {
            _preloadBackgroundMusic(str);
        }
    }

    public void resumeBackgroundMusic() {
        if (this.mMusicPlayer != null) {
            this.mMusicPlayer.resume();
        }
    }

    public void rewindBackgroundMusic() {
        if (this.mMusicPlayer != null) {
            this.mMusicPlayer.rewind();
        }
    }

    public void setBackgroundVolume(float f) {
        this.mVolume = f;
        if (this.mMusicPlayer != null) {
            this.mMusicPlayer.setVolume(f);
        }
    }

    public void stopBackgroundMusic() {
        if (this.mMusicPlayer != null) {
            this.mMusicPlayer.stop();
        }
    }
}
