package com.kayac.lobi.sdk.migration.datastore;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import com.kayac.lobi.sdk.migration.datastore.CommonDatastore.Function;
import java.io.File;
import java.io.Serializable;
import java.util.UUID;
import junit.framework.Assert;

class NakamapDatastore implements CommonDatastore {
    private static final Class<NakamapDatastore> MUTEX = NakamapDatastore.class;
    private static NakamapDataHelper sHelper = null;

    public static final class Key {
        public static final String FILE = "file";
        public static final String INSTALL_ID = "installId";
        public static final String IS_REPORT_SENT = "isReportSent";
        public static final String LAST_REFRECHED_DATE = "lastRefreshedDate";
        public static final String LAST_SEEN_AT = "lastSeenAt";
        public static final String NATIVE_NAKAMAP_APP_INSTALLED = "nativeNakamapAppInstalled";
        public static final String NATIVE_NAKAMAP_APP_SSO_SUPPORTED = "nativeNakamapAppSsoSupported";
        public static final String QUALITY = "quality";
        public static final String SSO_BOUND = "ssoBound";
        public static final String TAKE_PICTURE_URI = "take_picture_uri";
        public static final String THUMBNAIL_SIZE = "thumbnail_size";
        public static final String TOKEN = "token";
        public static final String UPLOAD_SIZE = "upload_size";
    }

    private static final class NakamapDataHelper extends SQLiteOpenHelper {
        private static final String FILE = "com.kayac.lobi.sdk.sqlite";
        private static final int VERSION = 1;

        public static final NakamapDataHelper newInstance(Context context) {
            return newInstance(context, null);
        }

        public static final NakamapDataHelper newInstance(Context context, String rootDir) {
            if (rootDir == null || rootDir.length() == 0) {
                return new NakamapDataHelper(context);
            }
            return new NakamapDataHelper(context, new File(rootDir, FILE).getPath());
        }

        public NakamapDataHelper(Context context) {
            super(context, FILE, null, 1);
        }

        public NakamapDataHelper(Context context, String filePath) {
            super(context, filePath, null, 1);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE key_value_table (c_key TEXT  PRIMARY KEY  ,c_value BLOB  NOT NULL );");
            db.execSQL("CREATE TABLE key_key_value_table (c_key_1 TEXT ,c_key_2 TEXT ,c_value BLOB  NOT NULL , PRIMARY KEY  (c_key_1,c_key_2));");
        }

        public void onUpgrade(SQLiteDatabase db, int ordVersion, int newVersion) {
        }
    }

    private NakamapDatastore() {
    }

    public static final void init(Context context) {
        synchronized (MUTEX) {
            if (sHelper == null) {
                sHelper = NakamapDataHelper.newInstance(context);
            }
        }
    }

    public static final void init(Context context, String rootDir) {
        synchronized (MUTEX) {
            if (sHelper == null) {
                sHelper = NakamapDataHelper.newInstance(context, rootDir);
            }
        }
    }

    public static final void deleteAll() {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                db.execSQL("DELETE FROM key_value_table");
                db.execSQL("DELETE FROM key_key_value_table");
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

    public static final void setValue(String key, Serializable value) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                Function.setValueImpl(db, key, value);
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

    public static final <T> T getValue(String key) {
        T value;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getReadableDatabase();
                value = Function.getValueImpl(db, key);
                if (db != null) {
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.close();
                }
            }
        }
        return value;
    }

    public static final <T> T getValue(String key, T fallback) {
        T t = getValue(key);
        return t != null ? t : fallback;
    }

    public static final void deleteValue(String key) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                Function.deleteValueImpl(db, key);
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

    public static final void setKKValue(String key1, String key2, Serializable value) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                Function.setKKValueImpl(db, key1, key2, value);
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

    public static final <T> T getKKValue(String key1, String key2) {
        T value;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getReadableDatabase();
                value = Function.getKKValueImpl(db, key1, key2);
                if (db != null) {
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.close();
                }
            }
        }
        return value;
    }

    public static final <T> T getKKValue(String key1, String key2, T fallback) {
        T t = getKKValue(key1, key2);
        return t != null ? t : fallback;
    }

    public static final void deleteKKValue(String key1, String key2) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                Function.deleteKKValueImpl(db, key1, key2);
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

    public static final String getInstallId() {
        String installId = (String) getValue("installId", "");
        if (!TextUtils.isEmpty(installId)) {
            return installId;
        }
        installId = UUID.randomUUID().toString();
        setValue("installId", installId);
        return installId;
    }
}
