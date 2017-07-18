package com.kayac.lobi.libnakamap.rec.cocos2dx;

import android.os.Build.VERSION;
import com.kayac.lobi.libnakamap.rec.LobiRecAPI;
import com.kayac.lobi.libnakamap.rec.a.b;

public class LobiAudio {
    private static LoadingStatus sStatusNativeLibrary = LoadingStatus.NONE;

    enum LoadingStatus {
        NONE,
        FAIL,
        SUCCESS
    }

    public static final boolean isSupport() {
        return VERSION.SDK_INT >= 16 && LobiRecAPI.isInitialized() && loadNativeLibrary();
    }

    public static boolean loadNativeLibrary() {
        if (sStatusNativeLibrary == LoadingStatus.NONE) {
            try {
                System.loadLibrary("lobirecaudio");
                sStatusNativeLibrary = LoadingStatus.SUCCESS;
                b.a(LobiAudio.class.getSimpleName(), "load liblobirecaudio.so");
            } catch (UnsatisfiedLinkError e) {
                sStatusNativeLibrary = LoadingStatus.FAIL;
                b.a(LobiAudio.class.getSimpleName(), "failed to load liblobirecaudio.so");
            }
        }
        return sStatusNativeLibrary == LoadingStatus.SUCCESS;
    }
}
