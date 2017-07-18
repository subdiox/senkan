package com.kayac.lobi.sdk.rec.unity;

import android.app.Activity;
import android.content.Context;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import com.kayac.lobi.libnakamap.rec.LobiRec;
import com.kayac.lobi.libnakamap.rec.LobiRecAPI;
import com.kayac.lobi.libnakamap.rec.a.b;
import com.kayac.lobi.libnakamap.rec.cocos2dx.LobiAudio;
import com.kayac.lobi.libnakamap.rec.recorder.AudioMixer;
import com.kayac.lobi.libnakamap.rec.recorder.a.b.a;
import com.kayac.lobi.libnakamap.rec.recorder.i;
import com.kayac.lobi.libnakamap.rec.recorder.j;
import com.kayac.lobi.sdk.LobiCore;
import com.kayac.lobi.sdk.rec.externalaudio.ExternalAudioInput;
import com.kayac.lobi.sdk.utils.UnityUtils;
import junit.framework.Assert;

public class LobiRecUnity extends LobiRecAPI {
    private static final int CHANNEL_COUNT = 1;
    private static int sSampleRate = 24000;
    private static int sUnityHeight = -1;
    private static String sUnityVersion = "";
    private static int sUnityWidth = -1;

    private LobiRecUnity() {
    }

    public static void disableAudioRecording() {
        AudioMixer.a();
    }

    private static SurfaceView getSurfaceView(ViewGroup viewGroup) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt instanceof SurfaceView) {
                return (SurfaceView) childAt;
            }
            if (childAt instanceof ViewGroup) {
                SurfaceView surfaceView = getSurfaceView((ViewGroup) childAt);
                if (surfaceView != null) {
                    return surfaceView;
                }
            }
        }
        return null;
    }

    public static void initCapture() {
        if (sLobiRecRecorder == null) {
            Context unityCurrentActivity = UnityUtils.getUnityCurrentActivity();
            int i = sUnityWidth;
            int i2 = sUnityHeight;
            LobiCore.setup(unityCurrentActivity);
            boolean init = LobiRec.init(unityCurrentActivity);
            a aVar = ("4.2".equals(sUnityVersion) || "4.3".equals(sUnityVersion)) ? a.UNITY_4_2_OR_4_3 : ("4.5".equals(sUnityVersion) || "4.6".equals(sUnityVersion)) ? a.UNITY_4_5_OR_4_6 : "5.0".equals(sUnityVersion) ? a.UNITY_5_0 : "5.1".equals(sUnityVersion) ? a.UNITY_5_1 : a.UNITY_5_2_OR_LATER;
            if (i <= 0 || i2 <= 0) {
                SurfaceView surfaceView = getSurfaceView((ViewGroup) unityCurrentActivity.findViewById(16908290));
                Assert.assertNotNull("SurfaceView is not found.", surfaceView);
                i = surfaceView.getWidth();
                i2 = surfaceView.getHeight();
            }
            if (i.getInstance() instanceof ExternalAudioInput) {
                ExternalAudioInput externalAudioInput = (ExternalAudioInput) i.getInstance();
                com.kayac.lobi.libnakamap.rec.recorder.a.a.a(externalAudioInput.getSampleRate(), externalAudioInput.getChannelCount());
            } else {
                com.kayac.lobi.libnakamap.rec.recorder.a.a.a(sSampleRate, 1);
            }
            sActivity = unityCurrentActivity;
            sLobiRecRecorder = new j(init, unityCurrentActivity, i, i2, aVar);
            if (init) {
                RecBroadcastReceiver.start(sActivity);
            }
        }
    }

    public static void initCapture(Activity activity) {
        throw new RuntimeException("Please update Assets/LobiSDK/Scripts");
    }

    public static boolean loadNativeLibrary() {
        try {
            System.loadLibrary("lobirecunity");
            if ("armeabi-v7a".equals(nativeGetCpuArch())) {
                return true;
            }
        } catch (Throwable e) {
            b.a(e);
        } catch (Throwable e2) {
            b.a(e2);
        }
        return false;
    }

    private static native String nativeGetCpuArch();

    public static void registerDismissingPostVideoViewNotification(String str, String str2) {
        RecBroadcastReceiver.registerObserver(LobiRec.ACTION_FINISH_POST_VIDEO_ACTIVITY, str, str2);
    }

    public static void registerDryingUpInStorageObserver(String str, String str2) {
        RecBroadcastReceiver.registerObserver(LobiRec.ACTION_DRYING_UP_INSTORAGE, str, str2);
    }

    public static void registerMicEnableErrorObserver(String str, String str2) {
    }

    public static void registerMovieCreatedErrorNotification(String str, String str2) {
        RecBroadcastReceiver.registerObserver(LobiRec.ACTION_MOVIE_CREATED_ERROR, str, str2);
    }

    public static void registerMovieCreatedNotification(String str, String str2) {
        RecBroadcastReceiver.registerObserver(LobiRec.ACTION_MOVIE_CREATED, str, str2);
    }

    public static void registerMovieUploadedErrorNotification(String str, String str2) {
        RecBroadcastReceiver.registerObserver(LobiRec.ACTION_MOVIE_UPLOADED_ERROR, str, str2);
    }

    public static void registerMovieUploadedNotification(String str, String str2) {
        RecBroadcastReceiver.registerObserver(LobiRec.ACTION_MOVIE_UPLOADED, str, str2);
    }

    public static void setAudioFormat(int i, int i2) {
        sSampleRate = i;
    }

    public static void setResolution(int i, int i2) {
        sUnityWidth = i;
        sUnityHeight = i2;
    }

    public static void setUnityVersion(String str) {
        sUnityVersion = str;
    }

    public static void startRecording() {
        if (sLobiRecRecorder != null && sLobiRecRecorder.m()) {
            if (i.getInstance() == null) {
                FmodAudio.init();
            }
            if (LobiAudio.isSupport()) {
                sLobiRecRecorder.f();
            }
        }
    }

    public static void unregisterDismissingPostVideoViewNotification() {
        RecBroadcastReceiver.unregisterObserver(LobiRec.ACTION_FINISH_POST_VIDEO_ACTIVITY);
    }

    public static void unregisterDryingUpInStorageObserver() {
        RecBroadcastReceiver.unregisterObserver(LobiRec.ACTION_DRYING_UP_INSTORAGE);
    }

    public static void unregisterMicEnableErrorObserver() {
    }

    public static void unregisterMovieCreatedErrorNotification() {
        RecBroadcastReceiver.unregisterObserver(LobiRec.ACTION_MOVIE_CREATED_ERROR);
    }

    public static void unregisterMovieCreatedNotification() {
        RecBroadcastReceiver.unregisterObserver(LobiRec.ACTION_MOVIE_CREATED);
    }

    public static void unregisterMovieUploadedErrorNotification() {
        RecBroadcastReceiver.unregisterObserver(LobiRec.ACTION_MOVIE_UPLOADED_ERROR);
    }

    public static void unregisterMovieUploadedNotification() {
        RecBroadcastReceiver.unregisterObserver(LobiRec.ACTION_MOVIE_UPLOADED);
    }
}
