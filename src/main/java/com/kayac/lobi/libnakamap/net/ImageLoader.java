package com.kayac.lobi.libnakamap.net;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import com.kayac.lobi.libnakamap.utils.Log;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import junit.framework.Assert;

public class ImageLoader {
    private static final int REQUEST_QUEUE_MAX = 8;
    private static final String TAG = "[loader]";
    private static final ThreadPoolExecutor sExecutor = new ThreadPoolExecutor(4, 8, 10, TimeUnit.SECONDS, sWorkQueue);
    private static final BlockingQueue<Runnable> sWorkQueue = new LinkedBlockingQueue();

    public interface OnImageLoaded {
        void onImageLoaded(String str, BitmapDrawable bitmapDrawable);
    }

    public static class LoaderTask implements Runnable {
        public final OnImageLoaded callback;
        public final Config config;
        public final Context context;
        public final int height;
        public final String url;
        public final boolean useMemoryCache;
        public final int width;

        public LoaderTask(Context context, String url, boolean useMemoryCache, OnImageLoaded callback, int width, int height, Config config) {
            this.context = context;
            this.url = url;
            this.useMemoryCache = useMemoryCache;
            this.callback = callback;
            this.width = width;
            this.height = height;
            this.config = config;
        }

        public void run() {
            final BitmapDrawable bitmapDrawable = ImageCachingLoader.load(this.context, this.url, this.useMemoryCache, this.width, this.height, this.config);
            if (bitmapDrawable != null) {
                ((Activity) this.context).runOnUiThread(new Runnable() {
                    public void run() {
                        LoaderTask.this.callback.onImageLoaded(LoaderTask.this.url, bitmapDrawable);
                    }
                });
            } else {
                Log.w("[icon]", "bitmap null!");
            }
        }
    }

    public static final void cancel(LoaderTask task) {
        sExecutor.remove(task);
    }

    public static final void loadImage(LoaderTask task) {
        load(task);
    }

    public static final void loadImage(Context context, String url, boolean useMemoryCache, OnImageLoaded callback, Config config) {
        Assert.assertNotNull(url);
        Assert.assertNotNull(callback);
        load(new LoaderTask(context, url, useMemoryCache, callback, 0, 0, config));
    }

    private static void load(LoaderTask task) {
        if (task != null && !TextUtils.isEmpty(task.url)) {
            try {
                sExecutor.execute(task);
            } catch (RejectedExecutionException e) {
                e.printStackTrace();
                sWorkQueue.clear();
                try {
                    sExecutor.execute(task);
                } catch (RejectedExecutionException e2) {
                }
            }
        }
    }

    private static void removeTaskFromQueue(LoaderTask task) {
    }
}
