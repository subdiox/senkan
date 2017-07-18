package com.mob.tools;

import android.os.Looper;
import android.os.Process;

public class MobHandlerThread extends Thread {
    private Looper mLooper;
    private int mPriority;
    private int mTid;

    public MobHandlerThread() {
        this.mTid = -1;
        this.mPriority = 0;
    }

    public MobHandlerThread(int priority) {
        this.mTid = -1;
        this.mPriority = priority;
    }

    public Looper getLooper() {
        if (!isAlive()) {
            return null;
        }
        synchronized (this) {
            while (isAlive() && this.mLooper == null) {
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }
        }
        return this.mLooper;
    }

    public int getThreadId() {
        return this.mTid;
    }

    protected void onLooperPrepared() {
    }

    public boolean quit() {
        Looper looper = getLooper();
        if (looper == null) {
            return false;
        }
        looper.quit();
        return true;
    }

    public void run() {
        this.mTid = Process.myTid();
        Looper.prepare();
        synchronized (this) {
            this.mLooper = Looper.myLooper();
            notifyAll();
        }
        Process.setThreadPriority(this.mPriority);
        onLooperPrepared();
        Looper.loop();
        this.mTid = -1;
    }
}
