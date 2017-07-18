package com.kayac.lobi.libnakamap.rec;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import com.kayac.lobi.libnakamap.components.PathRouter;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.KKey.Rec;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastore;
import com.kayac.lobi.libnakamap.rec.a.b;
import com.kayac.lobi.libnakamap.rec.b.a;
import com.kayac.lobi.libnakamap.rec.b.f;
import com.kayac.lobi.libnakamap.rec.recorder.a.e;
import com.kayac.lobi.libnakamap.rec.recorder.j;
import com.kayac.lobi.sdk.LobiCore;
import com.kayac.lobi.sdk.activity.RootActivity;
import com.kayac.lobi.sdk.path.PathRouterConfig;
import com.kayac.lobi.sdk.rec.LobiRecModule;
import com.kayac.lobi.sdk.rec.activity.RecPlayActivity;
import com.kayac.lobi.sdk.rec.activity.RecPostVideoActivity;
import com.kayac.lobi.sdk.rec.service.MovieUploadService;
import java.io.File;
import java.util.Map;

public class LobiRecAPI {
    private static final int POST_LAST_MOVIE_FILE_NOT_FOUND_ERROR = 2;
    private static final int POST_LAST_MOVIE_SUCCESS = 0;
    private static final int POST_LAST_MOVIE_UPLOAD_ERROR = 1;
    private static final String TAG = LobiRecAPI.class.getSimpleName();
    protected static Activity sActivity;
    protected static j sLobiRecRecorder;
    private static final b sLog = new b(TAG);

    public interface FilePathCallback {
        void onResult(String str);
    }

    public interface PostCallback {
        void onResult(int i, String str, String str2, String str3);
    }

    protected LobiRecAPI() {
    }

    private static void abortGetLastMovieFilePathCallback() {
        a.a().h();
    }

    public static void cameraCaptureAndRender() {
        if (sLobiRecRecorder != null) {
            sLobiRecRecorder.c();
        }
    }

    public static void cameraPreRender() {
        if (sLobiRecRecorder != null) {
            sLobiRecRecorder.b();
        }
    }

    public static int checkError() {
        return LobiRec.getLastErrorCode();
    }

    private static com.kayac.lobi.libnakamap.rec.recorder.a.a getAudioEncoderConfig() {
        return sLobiRecRecorder != null ? sLobiRecRecorder.n().e() : null;
    }

    public static int getCapturePerFrame() {
        e screenEncoderConfig = getScreenEncoderConfig();
        return screenEncoderConfig == null ? 0 : screenEncoderConfig.a();
    }

    private static com.kayac.lobi.libnakamap.rec.recorder.a.b getConfig() {
        return sLobiRecRecorder != null ? sLobiRecRecorder.n() : null;
    }

    public static float getGameSoundVolume() {
        com.kayac.lobi.libnakamap.rec.recorder.a.a audioEncoderConfig = getAudioEncoderConfig();
        return audioEncoderConfig == null ? 0.0f : (float) audioEncoderConfig.b();
    }

    private static void getLastMovieFilePath(FilePathCallback filePathCallback) {
        if (!a.a().a(new c(filePathCallback))) {
            filePathCallback.onResult(null);
        }
    }

    public static int getLiveWipeStatus() {
        return sLobiRecRecorder == null ? j.a.a.None.ordinal() : sLobiRecRecorder.x().ordinal();
    }

    public static boolean getMicEnable() {
        com.kayac.lobi.libnakamap.rec.recorder.a.a audioEncoderConfig = getAudioEncoderConfig();
        return audioEncoderConfig == null ? false : audioEncoderConfig.c();
    }

    public static float getMicVolume() {
        com.kayac.lobi.libnakamap.rec.recorder.a.a audioEncoderConfig = getAudioEncoderConfig();
        return audioEncoderConfig == null ? 0.0f : (float) audioEncoderConfig.a();
    }

    public static boolean getRecorderSwitch() {
        return LobiRec.sUseLobiRecorderSwitchState;
    }

    private static e getScreenEncoderConfig() {
        return sLobiRecRecorder != null ? sLobiRecRecorder.n().d() : null;
    }

    public static boolean getStickyRecording() {
        com.kayac.lobi.libnakamap.rec.recorder.a.b config = getConfig();
        return config == null ? false : config.j();
    }

    public static int getWipePositionX() {
        return sLobiRecRecorder == null ? 0 : sLobiRecRecorder.u();
    }

    public static int getWipePositionY() {
        return sLobiRecRecorder == null ? 0 : sLobiRecRecorder.v();
    }

    public static int getWipeSquareSize() {
        return sLobiRecRecorder == null ? 0 : sLobiRecRecorder.w();
    }

    public static boolean hasMovie() {
        return a.a().d();
    }

    public static boolean isCapturing() {
        return sLobiRecRecorder == null ? false : sLobiRecRecorder.j();
    }

    public static boolean isFaceCaptureSupported() {
        return j.a.f;
    }

    public static boolean isInitialized() {
        return sLobiRecRecorder == null ? false : sLobiRecRecorder.m();
    }

    public static boolean isPaused() {
        return sLobiRecRecorder == null ? false : sLobiRecRecorder.k();
    }

    public static boolean isSecretMode() {
        return LobiRec.isSecretMode();
    }

    public static boolean isSupported() {
        return sLobiRecRecorder == null ? false : sLobiRecRecorder.l();
    }

    public static void onEndOfFrame() {
        if (sLobiRecRecorder != null) {
            sLobiRecRecorder.d();
        }
    }

    public static void onPauseActivity() {
        if (sLobiRecRecorder != null) {
            sLobiRecRecorder.p();
        }
    }

    public static void onResumeActivity() {
        if (sLobiRecRecorder != null) {
            sLobiRecRecorder.q();
        }
    }

    public static boolean openLobiPlayActivity() {
        return openLobiPlayActivity(null, null, false, null);
    }

    public static boolean openLobiPlayActivity(Uri uri) {
        if (isCapturing()) {
            return false;
        }
        LobiCore.assertSetup();
        if (PathRouter.getLastPath() == null) {
            return false;
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable(RecPlayActivity.EXTRA_URI, uri);
        bundle.putString(PathRouter.PATH, PathRouterConfig.PATH_REC_SDK_DEFAULT);
        PathRouter.removeAllThePaths();
        PathRouter.startPath(bundle);
        return true;
    }

    public static boolean openLobiPlayActivity(String str) {
        return openLobiPlayActivity(str, false);
    }

    public static boolean openLobiPlayActivity(String str, String str2, boolean z, String str3) {
        if (isCapturing()) {
            return false;
        }
        LobiCore.assertSetup();
        Bundle bundle = new Bundle();
        bundle.putString(RecPlayActivity.EXTRA_USER_EXID, str);
        bundle.putString(RecPlayActivity.EXTRA_CATEGORY, str2);
        bundle.putBoolean(RecPlayActivity.EXTRA_LETSPLAY, z);
        bundle.putString(RecPlayActivity.EXTRA_META_JSON, str3);
        bundle.putString(RecPlayActivity.EXTRA_EVENT_FIELDS, null);
        RootActivity.startActivity(PathRouterConfig.PATH_REC_SDK_DEFAULT, bundle);
        return true;
    }

    public static boolean openLobiPlayActivity(String str, boolean z) {
        if (isCapturing()) {
            return false;
        }
        LobiCore.assertSetup();
        Bundle bundle = new Bundle();
        bundle.putString(RecPlayActivity.EXTRA_VIDEO_UID, str);
        if (z) {
            bundle.putBoolean(RecPlayActivity.EXTRA_CAN_GO_BACK_TO_ACTIVITY, z);
            bundle.putString(PathRouter.PATH, PathRouterConfig.PATH_REC_SDK_DEFAULT.equals(PathRouter.getLastPath()) ? RecPlayActivity.PATH_PLAY_LIST_VIDEO : PathRouterConfig.PATH_REC_SDK_DEFAULT);
            PathRouter.startPath(bundle);
        } else {
            bundle.putString(RecPlayActivity.EXTRA_EVENT_FIELDS, null);
            RootActivity.startActivity(PathRouterConfig.PATH_REC_SDK_DEFAULT, bundle);
        }
        return true;
    }

    public static boolean openLobiPlayActivityWithEventFields(String str) {
        if (isCapturing()) {
            return false;
        }
        LobiCore.assertSetup();
        Bundle bundle = new Bundle();
        bundle.putString(RecPlayActivity.EXTRA_EVENT_FIELDS, str);
        if (PathRouter.getLastPath() == null) {
            RootActivity.startActivity(PathRouterConfig.PATH_REC_SDK_DEFAULT, bundle);
        } else {
            bundle.putString(PathRouter.PATH, PathRouterConfig.PATH_REC_SDK_DEFAULT);
            PathRouter.startPath(bundle, 65536);
        }
        return true;
    }

    public static boolean openPostVideoActivity(String str, String str2, long j, String str3) {
        return openPostVideoActivity(str, str2, j, str3, "");
    }

    public static boolean openPostVideoActivity(String str, String str2, long j, String str3, String str4) {
        boolean z = false;
        if (sLobiRecRecorder == null) {
            sLog.a("Not initialized yet");
            return false;
        } else if (hasMovie()) {
            f o = sLobiRecRecorder.o();
            if (o == null) {
                sLog.a("openPostVideoActivity was aborted: No video was recorded");
                RecPostVideoActivity.sendFinishBroadcast(LobiCore.sharedInstance().getContext());
                return false;
            }
            Intent intent = new Intent(sActivity, RecPostVideoActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("EXTRA_TITLE", str);
            bundle.putString(RecPostVideoActivity.EXTRA_DESCRIPTION, str2);
            bundle.putLong(RecPostVideoActivity.EXTRA_SCORE, j);
            bundle.putString(RecPostVideoActivity.EXTRA_CATEGORY, str3);
            bundle.putString(RecPostVideoActivity.EXTRA_METADATA, str4);
            String str5 = RecPostVideoActivity.EXTRA_MIC;
            boolean z2 = o.a() && o.c();
            bundle.putBoolean(str5, z2);
            String str6 = RecPostVideoActivity.EXTRA_CAMERA;
            if (o.b() && o.d()) {
                z = true;
            }
            bundle.putBoolean(str6, z);
            intent.putExtras(bundle);
            RootActivity.startActivity(LobiRecModule.PATH_POSTVIDEO, bundle);
            return true;
        } else {
            b.a("LobiRecSDK", "There are no videos to post.");
            RecPostVideoActivity.sendFinishBroadcast(LobiCore.sharedInstance().getContext());
            return false;
        }
    }

    public static void pauseRecording() {
        if (sLobiRecRecorder != null) {
            sLobiRecRecorder.i();
        }
    }

    private static boolean postLastMovie(String str, String str2) {
        if (!new File(str).exists()) {
            return false;
        }
        if (!TextUtils.isEmpty(str2)) {
            Context context = LobiCore.sharedInstance().getContext();
            Intent intent = new Intent(context, MovieUploadService.class);
            intent.putExtra("EXTRA_FILE_PATH", str);
            intent.putExtra("EXTRA_UPLOAD_URL", str2);
            context.startService(intent);
        }
        return true;
    }

    private static void postLastMovieMeta(Map<String, String> map, PostCallback postCallback) {
        getLastMovieFilePath(new d(postCallback, map));
    }

    public static void removeAllUploadingVideos() {
        try {
            for (Pair pair : a.a().g()) {
                a.a().a((File) pair.first);
            }
        } catch (Throwable e) {
            b.a(e);
        }
    }

    public static boolean removeUnretainedVideo() {
        if (sLobiRecRecorder == null) {
            sLog.a("Not initialized yet");
            return false;
        }
        boolean f;
        a a = a.a();
        boolean c = a.c();
        try {
            f = a.f();
        } catch (Throwable e) {
            b.a(e);
            f = false;
        }
        return c || f;
    }

    public static void resumeRecording() {
        if (sLobiRecRecorder != null) {
            sLobiRecRecorder.h();
        }
    }

    public static void setCapturePerFrame(int i) {
        e screenEncoderConfig = getScreenEncoderConfig();
        if (screenEncoderConfig != null) {
            screenEncoderConfig.a(i);
        }
    }

    public static void setGameSoundVolume(double d) {
        com.kayac.lobi.libnakamap.rec.recorder.a.a audioEncoderConfig = getAudioEncoderConfig();
        if (audioEncoderConfig != null) {
            audioEncoderConfig.b(d);
        }
    }

    public static void setHideFaceOnPreview(boolean z) {
    }

    public static void setLiveWipeStatus(int i) {
        if (sLobiRecRecorder != null && i >= 0 && i < j.a.a.values().length) {
            sLobiRecRecorder.a(j.a.a.values()[i]);
        }
    }

    public static void setLoggingEnable(boolean z) {
        LobiRec.setLoggingEnable(z);
    }

    public static void setMicEnable(boolean z) {
        if (isCapturing()) {
            sLog.b("Can't set mic state while recording");
            return;
        }
        com.kayac.lobi.libnakamap.rec.recorder.a.a audioEncoderConfig = getAudioEncoderConfig();
        if (audioEncoderConfig != null) {
            audioEncoderConfig.a(z);
        }
    }

    public static void setMicVolume(double d) {
        com.kayac.lobi.libnakamap.rec.recorder.a.a audioEncoderConfig = getAudioEncoderConfig();
        if (audioEncoderConfig != null) {
            audioEncoderConfig.a(d);
        }
    }

    public static void setPreventSpoiler(boolean z) {
    }

    public static void setRecorderSwitch(boolean z) {
        if (LobiRec.sUseLobiRecorderSwitch) {
            sLog.a("setRecorderSwitch(" + z + ")");
            TransactionDatastore.setValue(Rec.RECORDER_SWITCH_STATE, z ? Boolean.TRUE : Boolean.FALSE);
            LobiRec.sUseLobiRecorderSwitchState = z;
        }
    }

    public static void setSecretMode(boolean z) {
        LobiRec.setSecretMode(z);
    }

    public static void setStickyRecording(boolean z) {
        com.kayac.lobi.libnakamap.rec.recorder.a.b config = getConfig();
        if (config != null) {
            config.a(z);
        }
    }

    public static void setWipePosition(int i, int i2) {
        setWipePositionX(i);
        setWipePositionY(i2);
    }

    public static void setWipePositionX(int i) {
        if (sLobiRecRecorder != null) {
            sLobiRecRecorder.a(i);
        }
    }

    public static void setWipePositionY(int i) {
        if (sLobiRecRecorder != null) {
            sLobiRecRecorder.b(i);
        }
    }

    public static void setWipeSquareSize(int i) {
        if (sLobiRecRecorder != null) {
            sLobiRecRecorder.c(i);
        }
    }

    public static void startRecording() {
        if (sLobiRecRecorder != null) {
            sLobiRecRecorder.f();
        }
    }

    public static void stopRecording() {
        if (sLobiRecRecorder != null) {
            sLobiRecRecorder.g();
        }
    }

    public static int uploadQueueCount() {
        int i = 0;
        try {
            i = a.a().g().size();
        } catch (Throwable e) {
            b.a(e);
        }
        return i;
    }
}
