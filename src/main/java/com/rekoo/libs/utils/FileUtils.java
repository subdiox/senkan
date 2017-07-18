package com.rekoo.libs.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.text.TextUtils;
import android.util.Log;
import com.kayac.lobi.sdk.migration.datastore.NakamapDatastore.Key;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {
    public static File compressFile(String oldpath, String newPath) {
        Bitmap compressBitmap = decodeFile(oldpath);
        Bitmap newBitmap = ratingImage(oldpath, compressBitmap);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        newBitmap.compress(CompressFormat.PNG, 100, os);
        File file = null;
        try {
            file = getFileFromBytes(os.toByteArray(), newPath);
            if (newBitmap != null) {
                if (!newBitmap.isRecycled()) {
                    newBitmap.recycle();
                }
            }
            if (compressBitmap != null) {
                if (!compressBitmap.isRecycled()) {
                    compressBitmap.recycle();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (newBitmap != null) {
                if (!newBitmap.isRecycled()) {
                    newBitmap.recycle();
                }
            }
            if (compressBitmap != null) {
                if (!compressBitmap.isRecycled()) {
                    compressBitmap.recycle();
                }
            }
        } catch (Throwable th) {
            if (newBitmap != null) {
                if (!newBitmap.isRecycled()) {
                    newBitmap.recycle();
                }
            }
            if (compressBitmap != null) {
                if (!compressBitmap.isRecycled()) {
                    compressBitmap.recycle();
                }
            }
        }
        return file;
    }

    private static Bitmap ratingImage(String filePath, Bitmap bitmap) {
        return rotaingImageView(readPictureDegree(filePath), bitmap);
    }

    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate((float) angle);
        System.out.println("angle2=" + angle);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static int readPictureDegree(String path) {
        try {
            switch (new ExifInterface(path).getAttributeInt("Orientation", 1)) {
                case 3:
                    return 180;
                case 6:
                    return 90;
                case 8:
                    return 270;
                default:
                    return 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static File getFileFromBytes(byte[] b, String outputFile) {
        BufferedOutputStream stream;
        Exception e;
        Throwable th;
        File ret = null;
        BufferedOutputStream stream2 = null;
        try {
            File ret2 = new File(outputFile);
            try {
                stream = new BufferedOutputStream(new FileOutputStream(ret2));
            } catch (Exception e2) {
                e = e2;
                ret = ret2;
                try {
                    e.printStackTrace();
                    if (stream2 != null) {
                        return ret;
                    }
                    try {
                        stream2.close();
                        return ret;
                    } catch (IOException e3) {
                        e3.printStackTrace();
                        return ret;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (stream2 != null) {
                        try {
                            stream2.close();
                        } catch (IOException e32) {
                            e32.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                ret = ret2;
                if (stream2 != null) {
                    stream2.close();
                }
                throw th;
            }
            try {
                stream.write(b);
                if (stream != null) {
                    try {
                        stream.close();
                        stream2 = stream;
                        return ret2;
                    } catch (IOException e322) {
                        e322.printStackTrace();
                    }
                }
                stream2 = stream;
                return ret2;
            } catch (Exception e4) {
                e = e4;
                stream2 = stream;
                ret = ret2;
                e.printStackTrace();
                if (stream2 != null) {
                    return ret;
                }
                stream2.close();
                return ret;
            } catch (Throwable th4) {
                th = th4;
                stream2 = stream;
                ret = ret2;
                if (stream2 != null) {
                    stream2.close();
                }
                throw th;
            }
        } catch (Exception e5) {
            e = e5;
            e.printStackTrace();
            if (stream2 != null) {
                return ret;
            }
            stream2.close();
            return ret;
        }
    }

    public static Bitmap decodeFile(String fPath) {
        Options opts = new Options();
        opts.inJustDecodeBounds = true;
        opts.inDither = false;
        opts.inPurgeable = true;
        opts.inInputShareable = true;
        int scale = 1;
        if (opts.outHeight > 200 || opts.outWidth > 200) {
            int heightRatio = Math.round(((float) opts.outHeight) / 200.0f);
            int widthRatio = Math.round(((float) opts.outWidth) / 200.0f);
            if (heightRatio < widthRatio) {
                scale = heightRatio;
            } else {
                scale = widthRatio;
            }
        }
        Log.i("scale", "scal =" + scale);
        opts.inJustDecodeBounds = false;
        opts.inSampleSize = scale;
        byte[] data = new byte[10000];
        try {
            FileInputStream stream = new FileInputStream(new File(fPath));
            ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            while (true) {
                int n = stream.read(b);
                if (n == -1) {
                    break;
                }
                out.write(b, 0, n);
            }
            stream.close();
            out.close();
            data = out.toByteArray();
        } catch (IOException e) {
        }
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data, 0, data.length, options);
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(data, 0, data.length, options);
    }

    public static void setMkdir(String path) {
        File file = new File(path);
        if (file.exists()) {
            Log.e(Key.FILE, "目录存在");
            return;
        }
        file.mkdirs();
        Log.e(Key.FILE, "目录不存在  创建目录    ");
    }

    public static String getFileName(String url) {
        int lastIndexStart = url.lastIndexOf("/");
        if (lastIndexStart != -1) {
            return url.substring(lastIndexStart + 1, url.length());
        }
        return null;
    }

    public static void delFile(String path) {
        if (!TextUtils.isEmpty(path)) {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
        }
    }
}
