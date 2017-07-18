package com.kayac.lobi.sdk.rec.nativeinterface;

import android.app.Activity;
import com.kayac.lobi.libnakamap.rec.LobiRec;
import com.kayac.lobi.libnakamap.rec.LobiRecAPI;
import com.kayac.lobi.libnakamap.rec.cocos2dx.OpenSLAudio;
import com.kayac.lobi.libnakamap.rec.recorder.a.b;
import com.kayac.lobi.libnakamap.rec.recorder.a.b.a;
import com.kayac.lobi.libnakamap.rec.recorder.j;
import com.kayac.lobi.sdk.LobiCore;

public class LobiRecNative extends LobiRecAPI {
    private LobiRecNative() {
    }

    public static void initCapture(int i, int i2) {
        boolean init;
        if (sLobiRecRecorder == null) {
            init = LobiRec.init(sActivity);
        } else {
            init = sLobiRecRecorder.m();
            sLobiRecRecorder.r();
        }
        sLobiRecRecorder = new j(init, sActivity, i, i2, a.COCOS2D_X_2);
    }

    public static void initOpenSLAudio(int i) {
        if (b.b()) {
            com.kayac.lobi.libnakamap.rec.recorder.a.a.a(i, 1);
            OpenSLAudio.init();
        }
    }

    public static void setup(Activity activity) {
        sActivity = activity;
        LobiCore.setup(activity);
    }
}
