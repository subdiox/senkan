package com.kayac.lobi.libnakamap.service.streaming;

import com.kayac.lobi.libnakamap.utils.Log;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class StreamingThreadPoolExecutor extends ThreadPoolExecutor {
    private static final String TAG = "[streaming]";
    private static final UncaughtExceptionHandler sHandler = new UncaughtExceptionHandler() {
        public void uncaughtException(Thread thread, Throwable ex) {
            Log.e(StreamingThreadPoolExecutor.TAG, "uncaught exception: ", ex);
        }
    };
    private static ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mThreadCount = new AtomicInteger();

        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r, "Streaming Thread #" + this.mThreadCount.getAndIncrement());
            thread.setUncaughtExceptionHandler(StreamingThreadPoolExecutor.sHandler);
            return thread;
        }
    };

    public StreamingThreadPoolExecutor() {
        super(1, 1, 0, TimeUnit.MILLISECONDS, new ArrayBlockingQueue(1), sThreadFactory);
    }

    protected void afterExecute(Runnable r, Throwable t) {
        Log.v(TAG, "task done! " + getQueue().size() + " task left in the queue " + t);
        super.afterExecute(r, t);
    }

    public void showInternalStatus() {
        Log.d(TAG, "queue size: " + getQueue().size());
        Log.i(TAG, "active count: " + getActiveCount());
    }
}
