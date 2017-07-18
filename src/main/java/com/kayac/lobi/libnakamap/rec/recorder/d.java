package com.kayac.lobi.libnakamap.rec.recorder;

import java.util.concurrent.ThreadFactory;

class d implements ThreadFactory {
    final /* synthetic */ AudioMixer a;

    d(AudioMixer audioMixer) {
        this.a = audioMixer;
    }

    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.setPriority(10);
        return thread;
    }
}
