package com.kayac.lobi.sdk.service.chat;

import com.kayac.lobi.libnakamap.utils.Log;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class GroupEventThreadPoolExecutor extends ThreadPoolExecutor {
    protected static final String TAG = "[streaming]";
    private static final BlockingQueue<Runnable> mQueue = new LinkedBlockingQueue(10);
    private static final UncaughtExceptionHandler sHandler = new UncaughtExceptionHandler() {
        public void uncaughtException(Thread thread, Throwable ex) {
            Log.e(GroupEventThreadPoolExecutor.TAG, "uncaught exception: ", ex);
        }
    };
    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mThreadCount = new AtomicInteger();

        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r, "GroupEventPollingThread #" + this.mThreadCount.getAndIncrement());
            thread.setUncaughtExceptionHandler(GroupEventThreadPoolExecutor.sHandler);
            return thread;
        }
    };

    public GroupEventThreadPoolExecutor() {
        super(1, 5, 8, TimeUnit.SECONDS, mQueue, sThreadFactory);
    }
}
