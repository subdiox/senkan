package com.kayac.lobi.libnakamap.datastore;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.kayac.lobi.libnakamap.collection.Pair;
import com.kayac.lobi.libnakamap.datastore.AccountDDL.KKey;
import com.kayac.lobi.libnakamap.datastore.AccountDDL.Key;
import com.kayac.lobi.libnakamap.datastore.CommonDDL.App;
import com.kayac.lobi.libnakamap.datastore.CommonDDL.User;
import com.kayac.lobi.libnakamap.datastore.CommonDatastoreImpl.Function;
import com.kayac.lobi.libnakamap.exception.NakamapException.CurrentUserNotSet;
import com.kayac.lobi.libnakamap.value.UserValue;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;

public class AccountDatastore implements CommonDatastoreImpl {
    private static final Class<AccountDataHelper> MUTEX = AccountDataHelper.class;
    private static Context sContext = null;
    private static AccountDataHelper sHelper = null;

    private static final class AccountDataHelper extends SQLiteOpenHelper {
        private static final String FILE = "001_libnakamap_account.sqlite";
        private static final int VERSION = 8;

        public static final AccountDataHelper newInstance(Context context) {
            return newInstance(context, null);
        }

        public static final AccountDataHelper newInstance(Context context, String rootDir) {
            if (rootDir == null || rootDir.length() == 0) {
                return new AccountDataHelper(context);
            }
            return new AccountDataHelper(context, new File(rootDir, FILE).getPath());
        }

        public AccountDataHelper(Context context) {
            super(context, FILE, null, 8);
        }

        public AccountDataHelper(Context context, String filePath) {
            super(context, filePath, null, 8);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE key_value_table (c_key TEXT  PRIMARY KEY  ,c_value BLOB  NOT NULL );");
            db.execSQL("CREATE TABLE key_key_value_table (c_key_1 TEXT ,c_key_2 TEXT ,c_value BLOB  NOT NULL , PRIMARY KEY  (c_key_1,c_key_2));");
            db.execSQL(User.CREATE_SQL);
            db.execSQL(App.CREATE_SQL);
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onUpgrade(android.database.sqlite.SQLiteDatabase r5, int r6, int r7) {
            /*
            r4 = this;
            if (r6 != r7) goto L_0x0003;
        L_0x0002:
            return;
        L_0x0003:
            switch(r6) {
                case 1: goto L_0x0007;
                case 2: goto L_0x0007;
                case 3: goto L_0x0007;
                case 4: goto L_0x0011;
                case 5: goto L_0x002f;
                case 6: goto L_0x0034;
                case 7: goto L_0x0039;
                default: goto L_0x0006;
            };
        L_0x0006:
            goto L_0x0002;
        L_0x0007:
            r2 = "ALTER TABLE user_table ADD COLUMN c_cover TEXT;";
            r5.execSQL(r2);	 Catch:{ SQLException -> 0x0053 }
            r2 = "ALTER TABLE user_table ADD COLUMN c_contacted_date INTEGER;";
            r5.execSQL(r2);	 Catch:{ SQLException -> 0x0053 }
        L_0x0011:
            r2 = "DROP TABLE app_table;";
            r5.execSQL(r2);	 Catch:{ SQLException -> 0x0053 }
            r2 = "CREATE TABLE app_table (c_name TEXT, c_icon TEXT, c_appstore_uri TEXT, c_playstore_uri TEXT, c_uid TEXT, PRIMARY KEY(c_uid));";
            r5.execSQL(r2);	 Catch:{ SQLException -> 0x0053 }
            r2 = "CREATE TABLE user_table_tmp (c_uid TEXT  PRIMARY KEY  ,c_default INTEGER ,c_token TEXT ,c_name TEXT ,c_description TEXT ,c_icon TEXT ,c_cover TEXT ,c_contacts_count INTEGER ,c_contacted_date INTEGER ,c_is_nan_location INTEGER ,c_lng REAL ,c_lat REAL ,c_located_date INTEGER ,c_update_at INTEGER ,c_app_uid TEXT );";
            r5.execSQL(r2);	 Catch:{ SQLException -> 0x0053 }
            r2 = "INSERT INTO user_table_tmp SELECT c_uid, c_default, c_token, c_name, c_description, c_icon TEXT, c_cover, c_contacts_count, c_contacted_date, c_is_nan_location, c_lng, c_lat, c_located_date, c_update_at, '' FROM user_table";
            r5.execSQL(r2);	 Catch:{ SQLException -> 0x0053 }
            r2 = "DROP TABLE user_table;";
            r5.execSQL(r2);	 Catch:{ SQLException -> 0x0053 }
            r2 = "ALTER TABLE user_table_tmp RENAME TO user_table;";
            r5.execSQL(r2);	 Catch:{ SQLException -> 0x0053 }
        L_0x002f:
            r2 = "ALTER TABLE user_table ADD COLUMN c_unread_counts INTEGER;";
            r5.execSQL(r2);	 Catch:{ SQLException -> 0x0053 }
        L_0x0034:
            r2 = "ALTER TABLE app_table ADD COLUMN c_client_id TEXT;";
            r5.execSQL(r2);	 Catch:{ SQLException -> 0x0053 }
        L_0x0039:
            r2 = "ALTER TABLE user_table ADD COLUMN c_ex_id TEXT;";
            r5.execSQL(r2);	 Catch:{ SQLException -> 0x0053 }
            r2 = "CREATE TABLE user_table_tmp (c_uid TEXT, c_default INTEGER, c_token TEXT, c_name TEXT, c_description TEXT, c_icon TEXT, c_cover TEXT, c_contacts_count INTEGER, c_contacted_date INTEGER, c_is_nan_location INTEGER, c_lng REAL, c_lat REAL, c_located_date INTEGER, c_unread_counts INTEGER, c_app_uid TEXT, c_ex_id TEXT, PRIMARY KEY(c_uid));";
            r5.execSQL(r2);	 Catch:{ SQLException -> 0x0053 }
            r2 = "INSERT INTO user_table_tmp SELECT c_uid, c_default, c_token, c_name, c_description, c_icon, c_cover, c_contacts_count, c_contacted_date, c_is_nan_location, c_lng, c_lat, c_located_date, c_unread_counts, c_app_uid, c_ex_id FROM user_table;";
            r5.execSQL(r2);	 Catch:{ SQLException -> 0x0053 }
            r2 = "DROP TABLE user_table;";
            r5.execSQL(r2);	 Catch:{ SQLException -> 0x0053 }
            r2 = "ALTER TABLE user_table_tmp RENAME TO user_table;";
            r5.execSQL(r2);	 Catch:{ SQLException -> 0x0053 }
            goto L_0x0002;
        L_0x0053:
            r0 = move-exception;
            r2 = com.kayac.lobi.libnakamap.datastore.AccountDatastore.sContext;
            r3 = "001_libnakamap_account.sqlite";
            r1 = r2.getDatabasePath(r3);
            r1.delete();
            r2 = android.os.Process.myPid();
            android.os.Process.killProcess(r2);
            goto L_0x0002;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.kayac.lobi.libnakamap.datastore.AccountDatastore.AccountDataHelper.onUpgrade(android.database.sqlite.SQLiteDatabase, int, int):void");
        }
    }

    private AccountDatastore() {
    }

    public static final void init(Context context) {
        sContext = context;
        synchronized (MUTEX) {
            if (sHelper == null) {
                sHelper = AccountDataHelper.newInstance(context);
            }
        }
    }

    public static final void init(Context context, String rootDir) {
        sContext = context;
        synchronized (MUTEX) {
            if (sHelper == null) {
                sHelper = AccountDataHelper.newInstance(context, rootDir);
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
                db.execSQL("DELETE FROM user_table");
                db.execSQL("DELETE FROM app_table");
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
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
                Function.setValue(db, key, value);
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
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

    public static final void setValues(List<Pair<String, Serializable>> keyValues) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                for (Pair<String, Serializable> keyValue : keyValues) {
                    Function.setValue(db, (String) keyValue.first, (Serializable) keyValue.second);
                }
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
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
            value = null;
            try {
                db = sHelper.getReadableDatabase();
                value = Function.getValue(db, key);
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
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

    public static final <T> List<Pair<String, T>> getValues(List<String> keys, T fallback) {
        List<Pair<String, T>> values;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            values = new ArrayList();
            try {
                db = sHelper.getReadableDatabase();
                db.beginTransaction();
                for (String key : keys) {
                    T value = Function.getValue(db, key);
                    if (value == null) {
                        value = fallback;
                    }
                    values.add(new Pair(key, value));
                }
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
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
        return values;
    }

    public static final void deleteValue(String key) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                Function.deleteValue(db, key);
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
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
                Function.setKKValue(db, key1, key2, value);
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
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

    public static final void setKKValues(String key1, List<Pair<String, Serializable>> key2Values) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                for (Pair<String, Serializable> key2Value : key2Values) {
                    Function.setKKValue(db, key1, (String) key2Value.first, (Serializable) key2Value.second);
                }
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
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
            value = null;
            try {
                db = sHelper.getReadableDatabase();
                value = Function.getKKValue(db, key1, key2);
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
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

    public static final <T> List<Pair<String, T>> getKKValues(String key1, List<String> key2s, T fallback) {
        List<Pair<String, T>> values;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            values = new ArrayList();
            try {
                db = sHelper.getReadableDatabase();
                db.beginTransaction();
                for (String key2 : key2s) {
                    T value = Function.getKKValue(db, key1, key2);
                    if (value == null) {
                        value = fallback;
                    }
                    values.add(new Pair(key2, value));
                }
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
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
        return values;
    }

    public static final <T> List<T> getKKValues(String key1) {
        List<T> values;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            values = new ArrayList();
            try {
                db = sHelper.getReadableDatabase();
                values = Function.getKKValues(db, key1);
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.close();
                }
            }
        }
        return values;
    }

    public static final void deleteKKValue(String key1, String key2) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                Function.deleteKKValue(db, key1, key2);
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
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

    public static final void deleteKKValues(String key1) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                Function.deleteKKValues(db, key1);
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
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

    public static final int getNotificationId(String userUid) {
        int intValue;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            Integer id = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                id = (Integer) Function.getKKValue(db, KKey.NOTIFICATION_ID, userUid);
                if (id == null) {
                    int max = 0;
                    for (Integer i : Function.getKKValues(db, KKey.NOTIFICATION_ID)) {
                        if (max < i.intValue()) {
                            max = i.intValue();
                        }
                    }
                    id = Integer.valueOf(max + 1);
                    Function.setKKValue(db, KKey.NOTIFICATION_ID, userUid, id);
                }
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
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
            intValue = id.intValue();
        }
        return intValue;
    }

    public static final void setUser(UserValue user) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                Function.setUser(db, user);
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
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

    public static final void setUsers(List<UserValue> users) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                Function.deleteAllUser(db);
                for (UserValue user : users) {
                    Function.setUser(db, user);
                }
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
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

    public static final void setCurrentUser(UserValue user) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                Function.setValue(db, Key.CURRENT_ACCOUNT_USER_UID, user.getUid());
                Function.setUser(db, user);
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
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

    public static final UserValue getUser(String userUid) {
        UserValue user;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            user = null;
            try {
                db = sHelper.getReadableDatabase();
                user = Function.getUser(db, userUid);
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.close();
                }
            }
        }
        return user;
    }

    public static final UserValue getUserFromAppUid(String appUid) {
        UserValue user;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            user = null;
            try {
                db = sHelper.getReadableDatabase();
                user = Function.getUserFromAppUid(db, appUid);
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.close();
                }
            }
        }
        return user;
    }

    public static final UserValue getDefaultUser() {
        UserValue user;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            user = null;
            try {
                db = sHelper.getReadableDatabase();
                user = Function.getDefaultUser(db);
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.close();
                }
            }
        }
        return user;
    }

    public static final UserValue getCurrentUser() {
        UserValue user;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            user = null;
            try {
                db = sHelper.getReadableDatabase();
                String userUid = (String) Function.getValue(db, Key.CURRENT_ACCOUNT_USER_UID);
                if (userUid == null || userUid.length() <= 0) {
                    throw new CurrentUserNotSet();
                }
                user = Function.getUser(db, userUid);
                if (db != null) {
                    db.close();
                }
            } catch (CurrentUserNotSet e) {
                throw e;
            } catch (Exception e2) {
                e2.printStackTrace();
                if (db != null) {
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.close();
                }
            }
        }
        return user;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final com.kayac.lobi.libnakamap.value.UserValue optCurrentUser() {
        /*
        r4 = sHelper;
        junit.framework.Assert.assertNotNull(r4);
        r5 = MUTEX;
        monitor-enter(r5);
        r0 = 0;
        r2 = 0;
        r4 = sHelper;	 Catch:{ Exception -> 0x0034 }
        r0 = r4.getReadableDatabase();	 Catch:{ Exception -> 0x0034 }
        r4 = "CURRENT_ACCOUNT_USER_UID";
        r3 = com.kayac.lobi.libnakamap.datastore.CommonDatastoreImpl.Function.getValue(r0, r4);	 Catch:{ Exception -> 0x0034 }
        r3 = (java.lang.String) r3;	 Catch:{ Exception -> 0x0034 }
        if (r3 == 0) goto L_0x0020;
    L_0x001a:
        r4 = r3.length();	 Catch:{ Exception -> 0x0034 }
        if (r4 > 0) goto L_0x0028;
    L_0x0020:
        r4 = 0;
        if (r0 == 0) goto L_0x0026;
    L_0x0023:
        r0.close();	 Catch:{ all -> 0x003e }
    L_0x0026:
        monitor-exit(r5);	 Catch:{ all -> 0x003e }
    L_0x0027:
        return r4;
    L_0x0028:
        r2 = com.kayac.lobi.libnakamap.datastore.CommonDatastoreImpl.Function.getUser(r0, r3);	 Catch:{ Exception -> 0x0034 }
        if (r0 == 0) goto L_0x0031;
    L_0x002e:
        r0.close();	 Catch:{ all -> 0x003e }
    L_0x0031:
        monitor-exit(r5);	 Catch:{ all -> 0x003e }
        r4 = r2;
        goto L_0x0027;
    L_0x0034:
        r1 = move-exception;
        r1.printStackTrace();	 Catch:{ all -> 0x0041 }
        if (r0 == 0) goto L_0x0031;
    L_0x003a:
        r0.close();	 Catch:{ all -> 0x003e }
        goto L_0x0031;
    L_0x003e:
        r4 = move-exception;
        monitor-exit(r5);	 Catch:{ all -> 0x003e }
        throw r4;
    L_0x0041:
        r4 = move-exception;
        if (r0 == 0) goto L_0x0047;
    L_0x0044:
        r0.close();	 Catch:{ all -> 0x003e }
    L_0x0047:
        throw r4;	 Catch:{ all -> 0x003e }
        */
        throw new UnsupportedOperationException("Method not decompiled: com.kayac.lobi.libnakamap.datastore.AccountDatastore.optCurrentUser():com.kayac.lobi.libnakamap.value.UserValue");
    }

    public static final List<UserValue> getUsers() {
        List<UserValue> users;
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            users = null;
            try {
                db = sHelper.getReadableDatabase();
                users = Function.getUsers(db);
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (db != null) {
                    db.close();
                }
            } catch (Throwable th) {
                if (db != null) {
                    db.close();
                }
            }
        }
        return users;
    }

    public static final void deleteUserFromAppUid(String appUid) {
        Assert.assertNotNull(sHelper);
        synchronized (MUTEX) {
            SQLiteDatabase db = null;
            try {
                db = sHelper.getWritableDatabase();
                db.beginTransaction();
                Function.deleteUserFromAppUid(db, appUid);
                db.setTransactionSuccessful();
                if (db != null) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
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
}
