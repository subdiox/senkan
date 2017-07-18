package com.kayac.lobi.libnakamap.rec.cocos2dx;

import android.app.Activity;
import com.kayac.lobi.libnakamap.rec.LobiRec;
import com.kayac.lobi.libnakamap.rec.LobiRecAPI;
import com.kayac.lobi.libnakamap.rec.recorder.a.b;
import com.kayac.lobi.libnakamap.rec.recorder.a.b.a;
import com.kayac.lobi.libnakamap.rec.recorder.j;
import com.kayac.lobi.sdk.LobiCore;
import com.yaya.sdk.utils.h;

public class LobiRecCocos2dx extends LobiRecAPI {
    private LobiRecCocos2dx() {
    }

    public static void initCapture(Activity activity, int i, int i2) {
        initCapture(activity, i, i2, "2");
    }

    public static void initCapture(Activity activity, int i, int i2, String str) {
        boolean init;
        LobiCore.setup(activity);
        if (sLobiRecRecorder == null) {
            init = LobiRec.init(activity);
        } else {
            init = sLobiRecRecorder.m();
            sLobiRecRecorder.r();
        }
        a aVar = "2".equals(str) ? a.COCOS2D_X_2 : h.d.equals(str) ? a.COCOS2D_X_3 : a.COCOS2D_X_3_7_OR_LATER;
        sActivity = activity;
        sLobiRecRecorder = new j(init, activity, i, i2, aVar);
        LobiAudioEngine.initSetup(activity);
    }

    public static void initOpenSLAudio(int i) {
        if (b.b()) {
            com.kayac.lobi.libnakamap.rec.recorder.a.a.a(i, 1);
            OpenSLAudio.init();
        }
    }
}
