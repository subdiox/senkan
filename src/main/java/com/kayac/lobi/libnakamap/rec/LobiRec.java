package com.kayac.lobi.libnakamap.rec;

import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.KKey.Rec;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastore;
import com.kayac.lobi.libnakamap.rec.a.b;
import com.kayac.lobi.libnakamap.rec.b.a;
import com.kayac.lobi.libnakamap.rec.recorder.i;
import com.kayac.lobi.sdk.rec.externalaudio.ExternalAudioInput;
import com.kayac.lobi.sdk.rec.service.MovieUploadService;
import com.kayac.lobi.sdk.utils.ManifestMetaDataUtils;
import java.io.File;
import java.util.concurrent.Executors;

public final class LobiRec {
    public static final String ACTION_DRYING_UP_INSTORAGE = "com.kayac.lobi.libnakamap.rec.ACTION_DRYING_UP_INSTORAGE";
    public static final String ACTION_FINISH_POST_VIDEO_ACTIVITY = "com.kayac.lobi.libnakamap.rec.ACTION_FINISH_POST_VIDEO_ACTIVITY";
    public static final String ACTION_MOVIE_CREATED = "com.kayac.lobi.libnakamap.rec.ACTION_MOVIE_CREATED";
    public static final String ACTION_MOVIE_CREATED_ERROR = "com.kayac.lobi.libnakamap.rec.ACTION_MOVIE_CREATED_ERROR";
    public static final String ACTION_MOVIE_UPLOADED = "com.kayac.lobi.libnakamap.rec.ACTION_MOVIE_UPLOADED";
    public static final String ACTION_MOVIE_UPLOADED_ERROR = "com.kayac.lobi.libnakamap.rec.ACTION_MOVIE_UPLOADED_ERROR";
    public static final String ACTION_RANKERS_MOVIE_UPLOADED = "com.kayac.lobi.libnakamap.rec.ACTION_RANKERS_MOVIE_UPLOADED";
    public static final String ACTION_RANKERS_MOVIE_UPLOADED_ERROR = "com.kayac.lobi.libnakamap.rec.ACTION_RANKERS_MOVIE_UPLOADED_ERROR";
    public static final String ACTION_RANKERS_MOVIE_UPLOAD_PROGRESS = "com.kayac.lobi.libnakamap.rec.ACTION_RANKERS_MOVIE_UPLOAD_PROGRESS";
    public static final int ERROR_BAD_ENCODER_CONNECTION = -2147483647;
    public static final int ERROR_FAILED_TO_LOAD_NATIVE_LIBRARY = -2147483646;
    public static final String EXTRA_FINISH_POST_VIDEO_ACTIVITY_FACEBOOK_SHARE = "EXTRA_FINISH_POST_VIDEO_ACTIVITY_FACEBOOK_SHARE";
    public static final String EXTRA_FINISH_POST_VIDEO_ACTIVITY_NICOVIDEO_SHARE = "EXTRA_FINISH_POST_VIDEO_ACTIVITY_NICOVIDEO_SHARE";
    public static final String EXTRA_FINISH_POST_VIDEO_ACTIVITY_TRY_POST = "EXTRA_FINISH_POST_VIDEO_ACTIVITY_TRY_POST";
    public static final String EXTRA_FINISH_POST_VIDEO_ACTIVITY_TWITTER_SHARE = "EXTRA_FINISH_POST_VIDEO_ACTIVITY_TWITTER_SHARE";
    public static final String EXTRA_FINISH_POST_VIDEO_ACTIVITY_YOUTUBE_SHARE = "EXTRA_FINISH_POST_VIDEO_ACTIVITY_YOUTUBE_SHARE";
    public static final String EXTRA_MOVIE_CREATED_URL = "EXTRA_MOVIE_CREATED_URL";
    public static final String EXTRA_MOVIE_CREATED_VIDEO_ID = "EXTRA_MOVIE_CREATED_VIDEO_ID";
    private static final b LOG = new b(LobiRec.class.getSimpleName());
    public static final int NO_ERROR = 0;
    private static int sLastErrorCode = 0;
    public static boolean sLoggingEnabled = true;
    private static boolean sSecretMode;
    public static boolean sUseLobiRecorderSwitch = false;
    public static boolean sUseLobiRecorderSwitchState = false;

    private LobiRec() {
    }

    public static int getLastErrorCode() {
        return sLastErrorCode;
    }

    public static boolean init(Context context) {
        boolean z;
        sUseLobiRecorderSwitch = ManifestMetaDataUtils.getBoolean(context, ManifestMetaDataUtils.USE_LOBI_RECORDER_SWITCH, false);
        if (sUseLobiRecorderSwitch) {
            LOG.a("sUseLobiRecorderSwitch: true");
            boolean booleanValue = ((Boolean) TransactionDatastore.getValue(Rec.RECORDER_SWITCH_STATE, Boolean.FALSE)).booleanValue();
            sUseLobiRecorderSwitchState = booleanValue;
            b.a("LobiRecSDK", "Recorder is " + (booleanValue ? "enabled" : "disabled") + ". (useLobiRecorderSwitch: true)");
            z = booleanValue;
        } else {
            LOG.a("sUseLobiRecorderSwitch: false");
            z = true;
        }
        Executors.newSingleThreadExecutor().execute(new b(z));
        return z;
    }

    public static boolean isSecretMode() {
        return sSecretMode;
    }

    public static void setLastErrorCode(int i) {
        sLastErrorCode = i;
    }

    public static void setLoggingEnable(boolean z) {
        sLoggingEnabled = z;
    }

    public static void setSecretMode(boolean z) {
        sSecretMode = z;
    }

    public static void setupInputAudioFormat(int i, int i2) {
        ExternalAudioInput.init(i, i2);
    }

    private static void uploadMovies(Context context) {
        try {
            for (Pair pair : a.a().g()) {
                File file = (File) pair.first;
                String str = (String) pair.second;
                Intent intent = new Intent(context, MovieUploadService.class);
                intent.putExtra("EXTRA_FILE_PATH", file.getAbsolutePath());
                intent.putExtra("EXTRA_UPLOAD_URL", str);
                context.startService(intent);
            }
        } catch (Throwable e) {
            b.a(e);
        }
    }

    public static void writeInputAudioData(short[] sArr) {
        if (i.getInstance() == null) {
            LOG.c("LobiRec.writeInputAudioData must be call LobiRec.setupInputAudioFormat before.");
        } else if (!(i.getInstance() instanceof ExternalAudioInput)) {
            LOG.c("LobiRec.writeInputAudioData expect :`external`, but actual : `" + i.getInstance().getTag() + "`");
        }
        if (i.getInstance() instanceof ExternalAudioInput) {
            ((ExternalAudioInput) i.getInstance()).writeAudioData(sArr);
        }
    }
}
