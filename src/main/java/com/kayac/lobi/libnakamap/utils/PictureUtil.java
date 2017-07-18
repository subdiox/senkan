package com.kayac.lobi.libnakamap.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

public class PictureUtil {
    public static final String BASENAME = "nakamap";
    public static final int CHAT_FORMAT = 1;
    public static final int CHAT_SIZE = 960;
    public static final int COVER_FORMAT = 0;
    public static final int COVER_SIZE = 640;
    private static final int FORMAT_JPEG = 1;
    private static final int FORMAT_JPEG_QUALITY = 80;
    private static final int FORMAT_PNG = 0;
    public static final int ICON_FORMAT = 0;
    public static final int ICON_SIZE = 640;
    public static final int THUMBNAIL_NOT_NEED = -1;

    public static final class Res {
        private File raw = null;
        private File thumb = null;

        public File getRaw() {
            return this.raw;
        }

        public void setRaw(File raw) {
            this.raw = raw;
        }

        public File getThumb() {
            return this.thumb;
        }

        public void setThumb(File thumb) {
            this.thumb = thumb;
        }
    }

    private PictureUtil() {
    }

    public static final Res onActivityResultPictureTakenHelper(Context context, Intent data, Uri path, int uploadSize, int uploadFormat, int thumbnailSize) {
        Res res = new Res();
        if (thumbnailSize != -1) {
            res.thumb = ImageUtils.createThumbnailFromUri(context, data, path, (float) thumbnailSize, 80);
        }
        res.raw = ImageUtils.createThumbnailFromUri(context, data, path, (float) uploadSize, 80);
        if (res.raw == null) {
            try {
                context.getContentResolver().delete(path, null, null);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        } else {
            context.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(res.raw)));
        }
        return res;
    }

    public static final Res onActivityResultPictureSelectedHelper(Context context, Intent data, int uploadSize, int uploadFormat, int thumbnailSize) {
        Res res = new Res();
        if (thumbnailSize != -1) {
            res.thumb = ImageUtils.createThumbnailFromUri(context, data, null, (float) thumbnailSize, 80);
        }
        res.raw = ImageUtils.createThumbnailFromUri(context, data, null, (float) uploadSize, 80);
        return res;
    }

    public static File getPreferablePlaceForSavingPictures(Context context) {
        File file = null;
        if ("mounted".equals(Environment.getExternalStorageState())) {
            if (VERSION.SDK_INT < 8) {
                file = getExternalDirectory(context);
            } else {
                file = getPicturesDirectory(context);
            }
        }
        if (file == null) {
            file = context.getCacheDir();
        }
        file.mkdirs();
        return file;
    }

    public static File getPreferablePlaceForSavingCameraPictures(Context context) {
        File file = null;
        if ("mounted".equals(Environment.getExternalStorageState())) {
            if (VERSION.SDK_INT < 8) {
                file = getExternalDirectory(context);
            } else if (VERSION.SDK_INT < 19) {
                file = getPicturesDirectory(context);
            } else {
                file = getExternalDirectoryWithoutPermission(context);
            }
        }
        if (file == null) {
            file = context.getCacheDir();
        }
        file.mkdirs();
        return new File(file, "nakamap" + UUID.randomUUID().toString());
    }

    private static File getExternalDirectory(Context context) {
        return new File(new File(Environment.getExternalStorageDirectory(), "Pictures"), getDirectoryNameInPictureDirectory(context));
    }

    @TargetApi(8)
    private static File getPicturesDirectory(Context context) {
        String directoryPictures = null;
        Class<?> c = Environment.class;
        try {
            directoryPictures = (String) c.getField("DIRECTORY_PICTURES").get(null);
        } catch (NoSuchFieldException e1) {
            e1.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        }
        try {
            File ret = null;
            try {
                ret = (File) c.getMethod("getExternalStoragePublicDirectory", new Class[]{String.class}).invoke(null, new Object[]{directoryPictures});
            } catch (IllegalArgumentException e3) {
                throw new RuntimeException(e3);
            } catch (IllegalAccessException e22) {
                throw new RuntimeException(e22);
            } catch (InvocationTargetException e4) {
                e4.printStackTrace();
            }
            return new File(ret, getDirectoryNameInPictureDirectory(context));
        } catch (SecurityException e5) {
            throw new RuntimeException(e5);
        } catch (NoSuchMethodException e6) {
            throw new RuntimeException(e6);
        }
    }

    @TargetApi(19)
    public static File getExternalDirectoryWithoutPermission(Context context) {
        File file = null;
        try {
            file = context.getExternalFilesDir(null);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static String getDirectoryNameInPictureDirectory(Context context) {
        String packageLabel = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            packageLabel = packageManager.getApplicationLabel(packageManager.getApplicationInfo(context.getPackageName(), 128)).toString();
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageLabel == null) {
            return context.getPackageName();
        }
        return packageLabel;
    }

    public static int getUploadSize(int photoType) {
        switch (photoType) {
            case 0:
                return 640;
            case 1:
                return 640;
            default:
                throw new IllegalArgumentException("photo type invalid.");
        }
    }

    public static int getUploadFormat(int photoType) {
        switch (photoType) {
            case 0:
                return 0;
            case 1:
                return 0;
            default:
                throw new IllegalArgumentException("photo type invalid.");
        }
    }

    @TargetApi(4)
    public static boolean requestWritePermissionIfNotGranted(Activity activity) {
        String permission = "android.permission.WRITE_EXTERNAL_STORAGE";
        if (PermissionChecker.checkSelfPermission(activity, "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
            return false;
        }
        ActivityCompat.requestPermissions(activity, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 0);
        return true;
    }
}
