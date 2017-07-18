package com.kayac.lobi.libnakamap.utils;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.provider.MediaStore.Images.Media;
import android.text.TextUtils;
import com.kayac.lobi.libnakamap.gallery.AlbumData;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.OutputStream;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;

public class GalleryUtil {
    public static final int COLUMN_DATA = 0;
    public static final int COLUMN_TYPE = 1;
    public static final int TAKE_PHOTO = 20001;
    public static final String TYPE_IMAGE = "image";
    public static final String TYPE_VIDEO = "video";
    static final String[] mExtensions = new String[]{"jpg", "png", "gif", "jpeg"};

    public static class ImageData {
        public static final String IMAGE = "image";
        public static final boolean SELECTED = true;
        public static final boolean UNSELECTED = false;
        public static final String VIDEO = "video";
        private final File mAlbum;
        private SoftReference<Bitmap> mBitmap = new SoftReference(null);
        private long mDateTaken;
        private int mImageId;
        private String mImageUrl;
        private boolean mSelected;
        private String mType;
        private String mUid;

        public ImageData(int id, String url, String type, long date, File album) {
            this.mImageId = id;
            this.mImageUrl = url;
            this.mType = type;
            if (album != null) {
                this.mUid = album.getName() + "_" + type + "_" + id;
            } else {
                this.mUid = "noalbum_" + type + "_" + id;
            }
            this.mDateTaken = date;
            this.mAlbum = album;
        }

        public int getId() {
            return this.mImageId;
        }

        public File getAlbum() {
            return this.mAlbum;
        }

        public String getUrl() {
            return this.mImageUrl;
        }

        public String getType() {
            return this.mType;
        }

        public long getDateTaken() {
            return this.mDateTaken;
        }

        public String getUid() {
            return this.mUid;
        }

        public void setBitmap(Bitmap bitmap) {
            this.mBitmap = new SoftReference(bitmap);
        }

        public SoftReference<Bitmap> getBitmap() {
            return this.mBitmap;
        }

        public void setSelected(boolean flag) {
            this.mSelected = flag;
        }

        public boolean isSelected() {
            return this.mSelected;
        }

        public boolean equals(Object o) {
            if (o instanceof ImageData) {
                return TextUtils.equals(((ImageData) o).mImageUrl, this.mImageUrl);
            }
            return super.equals(o);
        }

        public int hashCode() {
            return this.mUid.hashCode();
        }
    }

    public interface OnFinish {
        void onFinish();
    }

    public interface OnImageFound {
        void onImageFound(ImageData imageData);
    }

    public static Map<String, AlbumData> getAlbums(Context context) {
        MergeCursor mergeCursor = new MergeCursor(getCursors(context, null));
        Map<String, AlbumData> albums = new HashMap();
        mergeCursor.moveToFirst();
        for (int i = 0; i < mergeCursor.getCount(); i++) {
            String url = mergeCursor.getString(0);
            List<String> segments = Uri.parse(mergeCursor.getString(0)).getPathSegments();
            if (segments.size() > 1) {
                String folderName = (String) segments.get(segments.size() - 2);
                String folderPath = "";
                for (int dirs = 0; dirs < segments.size() - 1; dirs++) {
                    folderPath = folderPath + "/" + ((String) segments.get(dirs));
                }
                if (!albums.containsKey(folderPath)) {
                    String mimeType = mergeCursor.getString(1);
                    if (mimeType != null) {
                        if ("image".equals("image".equals(mimeType.split("/")[0]) ? "image" : "video")) {
                            albums.put(folderPath, new AlbumData(folderName, folderPath, url, getImagesCount(new File(folderPath))));
                        }
                    }
                }
            }
            mergeCursor.moveToNext();
        }
        return albums;
    }

    public static final void getImages(File folder, OnImageFound onImageFound, OnFinish onFinish) {
        if (folder.exists()) {
            Options opts = new Options();
            opts.inJustDecodeBounds = true;
            int position = 0;
            List<File> files = new ArrayList();
            Collections.addAll(files, folder.listFiles());
            List<File> myFiles = new ArrayList();
            for (File file : files) {
                int index = Collections.binarySearch(myFiles, file, new Comparator<File>() {
                    public int compare(File arg0, File arg1) {
                        return Double.compare((double) arg1.lastModified(), (double) arg0.lastModified());
                    }
                });
                if (index < 0) {
                    myFiles.add((-index) - 1, file);
                } else {
                    myFiles.add(index, file);
                }
            }
            for (File file2 : myFiles) {
                if (file2.isFile() && checkExtension(file2)) {
                    String path = file2.getAbsolutePath();
                    BitmapFactory.decodeFile(path, opts);
                    if (opts.outWidth > 0) {
                        Log.v("gallery", String.format("%s: %d x %d", new Object[]{file2.getAbsolutePath(), Integer.valueOf(opts.outWidth), Integer.valueOf(opts.outHeight)}));
                        ImageData imageData = new ImageData(position, path, "image", file2.lastModified(), new File(path).getParentFile());
                        position++;
                        onImageFound.onImageFound(imageData);
                    }
                }
            }
            if (onFinish != null) {
                onFinish.onFinish();
            }
        }
    }

    public static final void getImagesLight(File folder, OnImageFound onImageFound, OnFinish onFinish) {
        if (folder.exists()) {
            int position = 0;
            List<File> files = new ArrayList();
            Collections.addAll(files, folder.listFiles());
            List<File> myFiles = new ArrayList();
            for (File file : files) {
                if (file.length() > 0) {
                    int index = Collections.binarySearch(myFiles, file, new Comparator<File>() {
                        public int compare(File arg0, File arg1) {
                            return Double.compare((double) arg1.lastModified(), (double) arg0.lastModified());
                        }
                    });
                    if (index < 0) {
                        myFiles.add((-index) - 1, file);
                    } else {
                        myFiles.add(index, file);
                    }
                }
            }
            for (File file2 : myFiles) {
                if (file2.isFile() && checkExtension(file2)) {
                    String path = file2.getAbsolutePath();
                    ImageData imageData = new ImageData(position, path, "image", file2.lastModified(), new File(path).getParentFile());
                    position++;
                    onImageFound.onImageFound(imageData);
                }
            }
            if (onFinish != null) {
                onFinish.onFinish();
            }
        }
    }

    public static int getNumberFiles(File folder) {
        if (!folder.exists()) {
            return 0;
        }
        List<File> files = new ArrayList();
        Collections.addAll(files, folder.listFiles());
        return files.size();
    }

    private static int getImagesCount(File folder) {
        int imagesCount = 0;
        if (folder.exists()) {
            File[] files = folder.listFiles(new FilenameFilter() {
                public boolean accept(File arg0, String arg1) {
                    return !arg1.toLowerCase().startsWith(".");
                }
            });
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.length() > 0 && checkExtension(file)) {
                        imagesCount++;
                    }
                }
            }
        }
        return imagesCount;
    }

    private static boolean checkExtension(File file) {
        if (!file.isFile()) {
            return false;
        }
        for (String extension : mExtensions) {
            if (file.getName().toLowerCase().endsWith(extension)) {
                return true;
            }
        }
        return false;
    }

    public static final List<ImageData> convertToImageData(List<String> images, boolean isFile) {
        List<ImageData> data = new ArrayList();
        int i = 0;
        for (String image : images) {
            data.add(new ImageData(i, image, "image", (long) i, isFile ? new File(image).getParentFile() : null));
            i++;
        }
        return data;
    }

    public static final void deleteLastImage(Context context) {
        Cursor cursor = ((Activity) context).managedQuery(Media.EXTERNAL_CONTENT_URI, new String[]{"_data", "_id", "datetaken", "mime_type"}, null, null, "datetaken DESC LIMIT 1");
        cursor.moveToPosition(0);
        ((Activity) context).getContentResolver().delete(Media.EXTERNAL_CONTENT_URI, "_id=" + cursor.getInt(1), null);
    }

    private static Cursor[] getCursors(Context context, String folder) {
        Cursor[] cursors = new Cursor[4];
        cursors[0] = imageQuery(context, Media.EXTERNAL_CONTENT_URI, folder);
        cursors[1] = imageQuery(context, Media.INTERNAL_CONTENT_URI, folder);
        return cursors;
    }

    private static Cursor imageQuery(Context context, Uri uri, String folder) {
        String condition;
        String[] projection = new String[]{"_data", "mime_type"};
        if (folder != null) {
            condition = "_data like '%" + folder + "%'";
        } else {
            condition = null;
        }
        Log.v("TEST", "CONDITION " + condition);
        return ((Activity) context).managedQuery(uri, projection, condition, null, "datetaken desc");
    }

    public static void saveImageToLocal(Context context, Bitmap bitmap) {
        FileNotFoundException e;
        Throwable th;
        File file = new File(PictureUtil.getPreferablePlaceForSavingPictures(context), "nakamap-" + new Date().getTime() + ".jpg");
        OutputStream outputStream = null;
        try {
            OutputStream outputStream2 = new FileOutputStream(file);
            try {
                if (bitmap.isRecycled()) {
                    IOUtils.closeQuietly(outputStream2);
                    outputStream = outputStream2;
                    return;
                }
                boolean compressed = bitmap.compress(CompressFormat.JPEG, 80, outputStream2);
                Log.d("nakamap", "bitmap compressed: " + compressed);
                if (compressed) {
                    ContentValues values = new ContentValues();
                    values.put("_data", file.getAbsolutePath());
                    values.put("datetaken", String.valueOf(System.currentTimeMillis()));
                    values.put("mime_type", "image/jpeg");
                    context.getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, values);
                }
                IOUtils.closeQuietly(outputStream2);
                outputStream = outputStream2;
            } catch (FileNotFoundException e2) {
                e = e2;
                outputStream = outputStream2;
                try {
                    e.printStackTrace();
                    IOUtils.closeQuietly(outputStream);
                } catch (Throwable th2) {
                    th = th2;
                    IOUtils.closeQuietly(outputStream);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                outputStream = outputStream2;
                IOUtils.closeQuietly(outputStream);
                throw th;
            }
        } catch (FileNotFoundException e3) {
            e = e3;
            e.printStackTrace();
            IOUtils.closeQuietly(outputStream);
        }
    }
}
