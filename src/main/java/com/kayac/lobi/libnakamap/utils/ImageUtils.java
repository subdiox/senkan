package com.kayac.lobi.libnakamap.utils;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore.Images.Media;
import com.rekoo.libs.net.URLCons;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public final class ImageUtils {
    private static final String TAG = "[image_utils]";

    public static File createThumbnailFromUri(Context context, Intent data, Uri uri, float size, int quality) {
        if (context == null) {
            return null;
        }
        File file = createThumbnailFromUri(context, uri, size, quality);
        if (file != null || data == null || data.getData() == null) {
            return file;
        }
        return createThumbnailFromUri(context, data.getData(), size, quality);
    }

    public static Bitmap scaleImage(String url, int reqHeight, int reqWidth) {
        File file = new File(url);
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            int heightRatio = Math.round(((float) height) / ((float) reqHeight));
            int widthRatio = Math.round(((float) width) / ((float) reqWidth));
            if (heightRatio < widthRatio) {
                inSampleSize = heightRatio;
            } else {
                inSampleSize = widthRatio;
            }
        }
        options.inJustDecodeBounds = false;
        options.outHeight = reqHeight;
        options.outWidth = reqHeight;
        options.inSampleSize = inSampleSize;
        return BitmapFactory.decodeFile(file.getAbsolutePath(), options);
    }

    public static File createThumbnailFromUri(Context context, Uri uri, float size, int quality) {
        if (uri == null) {
            return null;
        }
        float width;
        float height;
        File file;
        OutputStream outputStream;
        Bitmap bitmap = null;
        boolean isOutOfMemory = false;
        int i = 0;
        while (true) {
            float ratio;
            int tryCount = i + 1;
            if (i < 2 && bitmap == null) {
                try {
                    bitmap = getSmallBitmap(context, uri, (int) size, (int) size);
                    isOutOfMemory = false;
                    i = tryCount;
                } catch (OutOfMemoryError e1) {
                    isOutOfMemory = true;
                    System.gc();
                    e1.printStackTrace();
                    i = tryCount;
                } catch (IOException e12) {
                    e12.printStackTrace();
                    isOutOfMemory = false;
                    i = tryCount;
                }
            } else if (bitmap == null) {
                width = (float) bitmap.getWidth();
                height = (float) bitmap.getHeight();
                ratio = Math.max(size / width, size / height);
                if (ratio < 1.0f) {
                    bitmap = Bitmap.createScaledBitmap(bitmap, (int) (width * ratio), (int) (height * ratio), true);
                }
                bitmap = ExifUtil.rotateBitmap(getPathFromUri(context, uri), bitmap);
                file = createFileWithBaseName(context, "compress-" + UUID.randomUUID() + ".jpg");
                file.deleteOnExit();
                outputStream = null;
                try {
                    outputStream = new FileOutputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                if (outputStream == null) {
                    return null;
                }
                if (bitmap.isRecycled()) {
                    return null;
                }
                boolean compressed = bitmap.compress(CompressFormat.JPEG, quality, outputStream);
                bitmap.recycle();
                return compressed ? null : file;
            } else if (isOutOfMemory) {
                return null;
            } else {
                throw new OutOfMemoryError();
            }
        }
        if (bitmap == null) {
            width = (float) bitmap.getWidth();
            height = (float) bitmap.getHeight();
            ratio = Math.max(size / width, size / height);
            if (ratio < 1.0f) {
                bitmap = Bitmap.createScaledBitmap(bitmap, (int) (width * ratio), (int) (height * ratio), true);
            }
            bitmap = ExifUtil.rotateBitmap(getPathFromUri(context, uri), bitmap);
            file = createFileWithBaseName(context, "compress-" + UUID.randomUUID() + ".jpg");
            file.deleteOnExit();
            outputStream = null;
            outputStream = new FileOutputStream(file);
            if (outputStream == null) {
                return null;
            }
            if (bitmap.isRecycled()) {
                return null;
            }
            boolean compressed2 = bitmap.compress(CompressFormat.JPEG, quality, outputStream);
            bitmap.recycle();
            if (compressed2) {
            }
        } else if (isOutOfMemory) {
            return null;
        } else {
            throw new OutOfMemoryError();
        }
    }

    private static Bitmap getSmallBitmap(Context context, Uri uri, int width, int height) throws IOException {
        Options options = new Options();
        InputStream is = getInputStreamFromUri(context, uri);
        if (is == null) {
            return null;
        }
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, options);
        is.close();
        is = getInputStreamFromUri(context, uri);
        if (is == null) {
            return null;
        }
        options.inSampleSize = Math.max(1, Math.max(options.outWidth / width, options.outHeight / height));
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(is, null, options);
    }

    public static InputStream getInputStreamFromUri(Context context, Uri uri) throws FileNotFoundException {
        if (uri.getScheme() == null) {
            return new FileInputStream(uri.getPath());
        }
        return context.getContentResolver().openInputStream(uri);
    }

    public static String getPathFromUri(Context context, Uri uri) {
        String scheme = uri.getScheme();
        String path = uri.getPath();
        Log.v(TAG, "uri: " + uri);
        Log.v(TAG, "scheme: " + scheme);
        Log.v(TAG, "path: " + path);
        if (!URLCons.CONTENT.equals(scheme)) {
            return path;
        }
        Cursor cursor = Media.query(context.getContentResolver(), uri, new String[]{"_data"});
        Log.v(TAG, "cursor: " + cursor);
        path = null;
        if (cursor != null) {
            try {
                int fileColumnIndex = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    path = cursor.getString(fileColumnIndex);
                    Log.v(TAG, "getString: " + path);
                } else {
                    Log.v(TAG, "didn't move to first");
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Throwable th) {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        Log.v(TAG, "path: " + path);
        if (path != null) {
            return path;
        }
        path = uri.getPath();
        if (path == null) {
            return path;
        }
        int index = path.indexOf(Environment.getExternalStorageDirectory().getPath());
        if (index > -1) {
            return path.substring(index);
        }
        return path;
    }

    private static File createFileWithBaseName(Context context, String name) {
        File dir = new File(context.getCacheDir(), "nakamap");
        if (!dir.isDirectory()) {
            dir.delete();
        }
        if (!dir.exists()) {
            dir.mkdir();
        }
        return new File(dir, name);
    }
}
