package com.kayac.lobi.libnakamap.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.util.LruCache;
import com.kayac.lobi.libnakamap.utils.DeviceUtil;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ImageCachingLoader {
    private static final String TAG = "[loader]";
    private static final int TIMEOUT = 30000;
    private static final Object sLock = new Object();
    private static final MemoryCache sMemoryCache = new MemoryCache();

    private static final class BitmapLruCache extends LruCache<String, BitmapDrawable> {
        public BitmapLruCache(int limit) {
            super(limit);
        }

        protected int sizeOf(String path, BitmapDrawable bitmapDrawable) {
            if (bitmapDrawable == null || (bitmapDrawable != null && bitmapDrawable.getBitmap() == null)) {
                return 0;
            }
            Bitmap bitmap = bitmapDrawable.getBitmap();
            return (bitmap.getWidth() * 4) * bitmap.getHeight();
        }

        protected BitmapDrawable create(String path) {
            return null;
        }

        protected void entryRemoved(boolean evicted, String key, BitmapDrawable oldValue, BitmapDrawable newValue) {
            if (newValue == null && oldValue != null) {
                if (RecyclingBitmapDrawable.class.isInstance(oldValue)) {
                    ((RecyclingBitmapDrawable) oldValue).setIsCached(false);
                    return;
                }
                Bitmap bitmap = oldValue.getBitmap();
                if (bitmap != null) {
                    bitmap.recycle();
                }
            }
        }
    }

    public interface Cache<T> {
        void delete(String str);

        T get(String str);

        void put(String str, T t);
    }

    public static final class MemoryCache implements Cache<BitmapDrawable> {
        private static final int LIMIT = 2097152;
        private final BitmapLruCache mLruCache = new BitmapLruCache(2097152);

        public synchronized void put(String path, BitmapDrawable t) {
            this.mLruCache.put(path, t);
        }

        public synchronized BitmapDrawable get(String path) {
            BitmapDrawable bitmapDrawable;
            bitmapDrawable = (BitmapDrawable) this.mLruCache.get(path);
            if (!(bitmapDrawable == null || bitmapDrawable.getBitmap() == null || !bitmapDrawable.getBitmap().isRecycled())) {
                this.mLruCache.remove(path);
                bitmapDrawable = null;
            }
            return bitmapDrawable;
        }

        public synchronized void delete(String path) {
            this.mLruCache.remove(path);
        }
    }

    public static final BitmapDrawable load(Context context, String url, boolean useMemoryCache, Config config) {
        return load(context, url, useMemoryCache, 0, 0, config);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final android.graphics.drawable.BitmapDrawable load(android.content.Context r24, java.lang.String r25, boolean r26, int r27, int r28, android.graphics.Bitmap.Config r29) {
        /*
        r2 = 0;
        r3 = 0;
        if (r26 == 0) goto L_0x0018;
    L_0x0004:
        r19 = sMemoryCache;
        r0 = r19;
        r1 = r25;
        r3 = r0.get(r1);
        if (r3 == 0) goto L_0x0018;
    L_0x0010:
        r19 = r3.getBitmap();
        if (r19 == 0) goto L_0x0018;
    L_0x0016:
        r4 = r3;
    L_0x0017:
        return r4;
    L_0x0018:
        r18 = android.net.Uri.parse(r25);
        r14 = 0;
        r7 = getFilename(r25);
        r19 = "nakamap";
        r20 = r18.getScheme();	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
        r19 = r19.equals(r20);	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
        if (r19 == 0) goto L_0x00c5;
    L_0x002d:
        r19 = r24.getAssets();	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
        r20 = r18.getLastPathSegment();	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
        r14 = r19.open(r20);	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
        if (r27 <= 0) goto L_0x0090;
    L_0x003b:
        if (r28 <= 0) goto L_0x0090;
    L_0x003d:
        r0 = r27;
        r1 = r28;
        r16 = sampleSizeBitmap(r14, r0, r1);	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
        r14.close();	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
        r19 = r24.getAssets();	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
        r20 = r18.getLastPathSegment();	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
        r14 = r19.open(r20);	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
    L_0x0054:
        r19 = 0;
        r0 = r19;
        r1 = r16;
        r2 = android.graphics.BitmapFactory.decodeStream(r14, r0, r1);	 Catch:{ Throwable -> 0x00c2 }
    L_0x005e:
        org.apache.commons.io.IOUtils.closeQuietly(r14);
    L_0x0061:
        if (r2 == 0) goto L_0x008e;
    L_0x0063:
        r19 = com.kayac.lobi.libnakamap.utils.DeviceUtil.hasHoneycomb();
        if (r19 != 0) goto L_0x01ca;
    L_0x0069:
        r3 = new com.kayac.lobi.libnakamap.net.RecyclingBitmapDrawable;
        r19 = r24.getResources();
        r0 = r19;
        r3.<init>(r0, r2);
    L_0x0074:
        if (r26 == 0) goto L_0x008e;
    L_0x0076:
        r19 = sMemoryCache;
        r0 = r19;
        r1 = r25;
        r0.put(r1, r3);
        r0 = r3 instanceof com.kayac.lobi.libnakamap.net.RecyclingBitmapDrawable;
        r19 = r0;
        if (r19 == 0) goto L_0x008e;
    L_0x0085:
        r19 = r3;
        r19 = (com.kayac.lobi.libnakamap.net.RecyclingBitmapDrawable) r19;
        r20 = 1;
        r19.setIsCached(r20);
    L_0x008e:
        r4 = r3;
        goto L_0x0017;
    L_0x0090:
        r16 = new android.graphics.BitmapFactory$Options;	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
        r16.<init>();	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
        r0 = r29;
        r1 = r16;
        r1.inPreferredConfig = r0;	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
        goto L_0x0054;
    L_0x009c:
        r9 = move-exception;
    L_0x009d:
        r19 = "[loader]";
        r20 = new java.lang.StringBuilder;	 Catch:{ all -> 0x01b0 }
        r20.<init>();	 Catch:{ all -> 0x01b0 }
        r21 = "io exception: ";
        r20 = r20.append(r21);	 Catch:{ all -> 0x01b0 }
        r0 = r20;
        r20 = r0.append(r7);	 Catch:{ all -> 0x01b0 }
        r20 = r20.toString();	 Catch:{ all -> 0x01b0 }
        r0 = r19;
        r1 = r20;
        com.kayac.lobi.libnakamap.utils.Log.w(r0, r1, r9);	 Catch:{ all -> 0x01b0 }
        r9.printStackTrace();	 Catch:{ all -> 0x01b0 }
        org.apache.commons.io.IOUtils.closeQuietly(r14);
        goto L_0x0061;
    L_0x00c2:
        r9 = move-exception;
        r2 = 0;
        goto L_0x005e;
    L_0x00c5:
        r5 = getCacheDirectory(r24);	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
        r6 = new java.io.File;	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
        r6.<init>(r5, r7);	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
        r10 = r6.exists();	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
        if (r10 == 0) goto L_0x012b;
    L_0x00d4:
        r15 = new java.io.FileInputStream;	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
        r15.<init>(r6);	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
    L_0x00d9:
        if (r27 <= 0) goto L_0x01b8;
    L_0x00db:
        if (r28 <= 0) goto L_0x01b8;
    L_0x00dd:
        r0 = r27;
        r1 = r28;
        r16 = sampleSizeBitmap(r15, r0, r1);	 Catch:{ IOException -> 0x01de, Exception -> 0x01da, all -> 0x01d7 }
        r15.close();	 Catch:{ IOException -> 0x01de, Exception -> 0x01da, all -> 0x01d7 }
        r14 = new java.io.FileInputStream;	 Catch:{ IOException -> 0x01de, Exception -> 0x01da, all -> 0x01d7 }
        r14.<init>(r6);	 Catch:{ IOException -> 0x01de, Exception -> 0x01da, all -> 0x01d7 }
    L_0x00ed:
        r19 = 0;
        r0 = r19;
        r1 = r16;
        r2 = android.graphics.BitmapFactory.decodeStream(r14, r0, r1);	 Catch:{ Throwable -> 0x01c6 }
    L_0x00f7:
        if (r2 != 0) goto L_0x005e;
    L_0x00f9:
        r6.delete();	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
        r19 = "[loader]";
        r20 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
        r20.<init>();	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
        r21 = "bitmap null for: ";
        r20 = r20.append(r21);	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
        r21 = r6.getAbsolutePath();	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
        r20 = r20.append(r21);	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
        r20 = r20.toString();	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
        com.kayac.lobi.libnakamap.utils.Log.w(r19, r20);	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
        goto L_0x005e;
    L_0x011a:
        r9 = move-exception;
    L_0x011b:
        r19 = "[loader]";
        r20 = "exception: ";
        r0 = r19;
        r1 = r20;
        com.kayac.lobi.libnakamap.utils.Log.w(r0, r1, r9);	 Catch:{ all -> 0x01b0 }
        org.apache.commons.io.IOUtils.closeQuietly(r14);
        goto L_0x0061;
    L_0x012b:
        r19 = "tmp_";
        r20 = java.util.UUID.randomUUID();	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
        r20 = r20.toString();	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
        r0 = r19;
        r1 = r20;
        r17 = java.io.File.createTempFile(r0, r1, r5);	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
        r17.createNewFile();	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
        r12 = 0;
        r13 = new java.io.FileOutputStream;	 Catch:{ IOException -> 0x01a0, Exception -> 0x011a }
        r0 = r17;
        r13.<init>(r0);	 Catch:{ IOException -> 0x01a0, Exception -> 0x011a }
        r12 = r13;
    L_0x0149:
        r19 = new java.net.URL;	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
        r0 = r19;
        r1 = r25;
        r0.<init>(r1);	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
        r8 = r19.openConnection();	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
        r8 = (java.net.HttpURLConnection) r8;	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
        r19 = 30000; // 0x7530 float:4.2039E-41 double:1.4822E-319;
        r0 = r19;
        r8.setConnectTimeout(r0);	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
        r19 = 30000; // 0x7530 float:4.2039E-41 double:1.4822E-319;
        r0 = r19;
        r8.setReadTimeout(r0);	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
        r14 = r8.getInputStream();	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
        if (r12 == 0) goto L_0x01e2;
    L_0x016c:
        org.apache.commons.io.IOUtils.copy(r14, r12);	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
        org.apache.commons.io.IOUtils.closeQuietly(r14);	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
        org.apache.commons.io.IOUtils.closeQuietly(r12);	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
        r20 = sLock;	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
        monitor-enter(r20);	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
        r19 = r6.exists();	 Catch:{ all -> 0x01b5 }
        if (r19 == 0) goto L_0x0181;
    L_0x017e:
        r6.delete();	 Catch:{ all -> 0x01b5 }
    L_0x0181:
        r0 = r17;
        r0.renameTo(r6);	 Catch:{ all -> 0x01b5 }
        monitor-exit(r20);	 Catch:{ all -> 0x01b5 }
        r19 = r6.getAbsolutePath();	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
        r20 = "image";
        r22 = r6.length();	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
        r0 = r22;
        r0 = (int) r0;	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
        r21 = r0;
        com.kayac.lobi.libnakamap.datastore.TransactionDatastore.setFileCache(r19, r20, r21);	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
        r15 = new java.io.FileInputStream;	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
        r15.<init>(r6);	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
        goto L_0x00d9;
    L_0x01a0:
        r11 = move-exception;
        r19 = "[loader]";
        r20 = "cannot create output stream";
        r0 = r19;
        r1 = r20;
        com.kayac.lobi.libnakamap.utils.Log.w(r0, r1, r11);	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
        r11.printStackTrace();	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
        goto L_0x0149;
    L_0x01b0:
        r19 = move-exception;
    L_0x01b1:
        org.apache.commons.io.IOUtils.closeQuietly(r14);
        throw r19;
    L_0x01b5:
        r19 = move-exception;
        monitor-exit(r20);	 Catch:{ all -> 0x01b5 }
        throw r19;	 Catch:{ IOException -> 0x009c, Exception -> 0x011a }
    L_0x01b8:
        r16 = new android.graphics.BitmapFactory$Options;	 Catch:{ IOException -> 0x01de, Exception -> 0x01da, all -> 0x01d7 }
        r16.<init>();	 Catch:{ IOException -> 0x01de, Exception -> 0x01da, all -> 0x01d7 }
        r0 = r29;
        r1 = r16;
        r1.inPreferredConfig = r0;	 Catch:{ IOException -> 0x01de, Exception -> 0x01da, all -> 0x01d7 }
        r14 = r15;
        goto L_0x00ed;
    L_0x01c6:
        r9 = move-exception;
        r2 = 0;
        goto L_0x00f7;
    L_0x01ca:
        r3 = new android.graphics.drawable.BitmapDrawable;
        r19 = r24.getResources();
        r0 = r19;
        r3.<init>(r0, r2);
        goto L_0x0074;
    L_0x01d7:
        r19 = move-exception;
        r14 = r15;
        goto L_0x01b1;
    L_0x01da:
        r9 = move-exception;
        r14 = r15;
        goto L_0x011b;
    L_0x01de:
        r9 = move-exception;
        r14 = r15;
        goto L_0x009d;
    L_0x01e2:
        r15 = r14;
        goto L_0x00d9;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.kayac.lobi.libnakamap.net.ImageCachingLoader.load(android.content.Context, java.lang.String, boolean, int, int, android.graphics.Bitmap$Config):android.graphics.drawable.BitmapDrawable");
    }

    private static Options sampleSizeBitmap(InputStream inputStream, int width, int height) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream, null, options);
        options.inSampleSize = calculateInSampleSize(options, width, height);
        if (DeviceUtil.isLowMemoryDevice()) {
            options.inPreferredConfig = Config.RGB_565;
            options.inDither = true;
        }
        options.inJustDecodeBounds = false;
        return options;
    }

    public static int calculateInSampleSize(Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if ((height > reqHeight || width > reqWidth) && reqWidth > 0 && reqHeight > 0) {
            int heightRatio = Math.round(((float) height) / ((float) reqHeight));
            int widthRatio = Math.round(((float) width) / ((float) reqWidth));
            if (heightRatio < widthRatio) {
                inSampleSize = heightRatio;
            } else {
                inSampleSize = widthRatio;
            }
            while (((float) (width * height)) / ((float) (inSampleSize * inSampleSize)) > ((float) ((reqWidth * reqHeight) * 2))) {
                inSampleSize++;
            }
        }
        return inSampleSize;
    }

    public static File getCacheDirectory(Context context) {
        File file = new File(context.getCacheDir(), "images");
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }

    public static final String getFilename(String url) {
        try {
            return URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
