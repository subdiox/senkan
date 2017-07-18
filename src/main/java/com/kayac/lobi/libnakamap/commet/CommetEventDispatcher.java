package com.kayac.lobi.libnakamap.commet;

import android.os.Handler;
import android.os.Looper;
import java.util.EventListener;
import java.util.Vector;

public class CommetEventDispatcher<Listener extends EventListener> {
    protected final Vector<Listener> mEventListeners = new Vector();
    protected final Handler mHandler = new Handler(Looper.getMainLooper());

    public void addEventListener(Listener listener) {
        if (!this.mEventListeners.contains(listener)) {
            this.mEventListeners.add(listener);
        }
    }

    public void removeEventListener(Listener listener) {
        this.mEventListeners.remove(listener);
    }

    public void removeAllListeners() {
        this.mEventListeners.removeAllElements();
    }
}
