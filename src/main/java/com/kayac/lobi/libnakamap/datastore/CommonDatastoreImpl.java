package com.kayac.lobi.libnakamap.datastore;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import com.kayac.lobi.libnakamap.datastore.CommonDDL.App;
import com.kayac.lobi.libnakamap.datastore.CommonDDL.User;
import com.kayac.lobi.libnakamap.exception.NakamapException.Fatal;
import com.kayac.lobi.libnakamap.value.AppValue;
import com.kayac.lobi.libnakamap.value.UserValue;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public interface CommonDatastoreImpl {
    public static final String ASC = " ASC ";
    public static final String DESC = " DESC ";

    public static final class Function {
        public static final void setValue(SQLiteDatabase db, String key, Serializable value) {
            Throwable e;
            Throwable th;
            ByteArrayOutputStream byteArrayOutputStream = null;
            ObjectOutputStream oos = null;
            try {
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                try {
                    ObjectOutputStream oos2 = new ObjectOutputStream(os);
                    try {
                        oos2.writeObject(value);
                        ContentValues values = new ContentValues();
                        values.put("c_key", key);
                        values.put("c_value", os.toByteArray());
                        if (db.replaceOrThrow("key_value_table", null, values) == -1) {
                            throw new Fatal();
                        }
                        if (oos2 != null) {
                            try {
                                oos2.close();
                            } catch (IOException e2) {
                            }
                        }
                        if (os != null) {
                            try {
                                os.close();
                            } catch (IOException e3) {
                            }
                        }
                    } catch (IOException e4) {
                        e = e4;
                        oos = oos2;
                        byteArrayOutputStream = os;
                        try {
                            e.printStackTrace();
                            throw new Fatal(e);
                        } catch (Throwable th2) {
                            th = th2;
                            if (oos != null) {
                                try {
                                    oos.close();
                                } catch (IOException e5) {
                                }
                            }
                            if (byteArrayOutputStream != null) {
                                try {
                                    byteArrayOutputStream.close();
                                } catch (IOException e6) {
                                }
                            }
                            throw th;
                        }
                    } catch (SQLException e7) {
                        e = e7;
                        oos = oos2;
                        byteArrayOutputStream = os;
                        e.printStackTrace();
                        throw new Fatal(e);
                    } catch (Throwable th3) {
                        th = th3;
                        oos = oos2;
                        byteArrayOutputStream = os;
                        if (oos != null) {
                            oos.close();
                        }
                        if (byteArrayOutputStream != null) {
                            byteArrayOutputStream.close();
                        }
                        throw th;
                    }
                } catch (IOException e8) {
                    e = e8;
                    byteArrayOutputStream = os;
                    e.printStackTrace();
                    throw new Fatal(e);
                } catch (SQLException e9) {
                    e = e9;
                    byteArrayOutputStream = os;
                    e.printStackTrace();
                    throw new Fatal(e);
                } catch (Throwable th4) {
                    th = th4;
                    byteArrayOutputStream = os;
                    if (oos != null) {
                        oos.close();
                    }
                    if (byteArrayOutputStream != null) {
                        byteArrayOutputStream.close();
                    }
                    throw th;
                }
            } catch (IOException e10) {
                e = e10;
                e.printStackTrace();
                throw new Fatal(e);
            } catch (SQLException e11) {
                e = e11;
                e.printStackTrace();
                throw new Fatal(e);
            }
        }

        public static final <T> T getValue(SQLiteDatabase db, String key) {
            Cursor c = null;
            T value = null;
            try {
                SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
                queryBuilder.setTables("key_value_table");
                SQLiteDatabase sQLiteDatabase = db;
                c = queryBuilder.query(sQLiteDatabase, new String[]{"c_value"}, "c_key = ?", new String[]{key}, null, null, null);
                if (c.moveToFirst()) {
                    value = new ObjectInputStream(new ByteArrayInputStream(c.getBlob(c.getColumnIndex("c_value")))).readObject();
                }
                if (c != null) {
                    c.close();
                }
                return value;
            } catch (Throwable e) {
                e.printStackTrace();
                throw new Fatal(e);
            } catch (Throwable e2) {
                e2.printStackTrace();
                throw new Fatal(e2);
            } catch (Throwable e22) {
                e22.printStackTrace();
                throw new Fatal(e22);
            } catch (Throwable th) {
                if (c != null) {
                    c.close();
                }
            }
        }

        public static final void deleteValue(SQLiteDatabase db, String key) {
            try {
                db.delete("key_value_table", "c_key = ? ", new String[]{key});
            } catch (Throwable e) {
                e.printStackTrace();
                throw new Fatal(e);
            }
        }

        public static final void setKKValue(SQLiteDatabase db, String key1, String key2, Serializable value) {
            Throwable e;
            Throwable th;
            ByteArrayOutputStream byteArrayOutputStream = null;
            ObjectOutputStream oos = null;
            try {
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                try {
                    ObjectOutputStream oos2 = new ObjectOutputStream(os);
                    try {
                        oos2.writeObject(value);
                        ContentValues values = new ContentValues();
                        values.put("c_key_1", key1);
                        values.put("c_key_2", key2);
                        values.put("c_value", os.toByteArray());
                        if (db.replaceOrThrow("key_key_value_table", null, values) == -1) {
                            throw new Fatal();
                        }
                        if (oos2 != null) {
                            try {
                                oos2.close();
                            } catch (IOException e2) {
                            }
                        }
                        if (os != null) {
                            try {
                                os.close();
                            } catch (IOException e3) {
                            }
                        }
                    } catch (IOException e4) {
                        e = e4;
                        oos = oos2;
                        byteArrayOutputStream = os;
                        try {
                            e.printStackTrace();
                            throw new Fatal(e);
                        } catch (Throwable th2) {
                            th = th2;
                            if (oos != null) {
                                try {
                                    oos.close();
                                } catch (IOException e5) {
                                }
                            }
                            if (byteArrayOutputStream != null) {
                                try {
                                    byteArrayOutputStream.close();
                                } catch (IOException e6) {
                                }
                            }
                            throw th;
                        }
                    } catch (SQLException e7) {
                        e = e7;
                        oos = oos2;
                        byteArrayOutputStream = os;
                        e.printStackTrace();
                        throw new Fatal(e);
                    } catch (Throwable th3) {
                        th = th3;
                        oos = oos2;
                        byteArrayOutputStream = os;
                        if (oos != null) {
                            oos.close();
                        }
                        if (byteArrayOutputStream != null) {
                            byteArrayOutputStream.close();
                        }
                        throw th;
                    }
                } catch (IOException e8) {
                    e = e8;
                    byteArrayOutputStream = os;
                    e.printStackTrace();
                    throw new Fatal(e);
                } catch (SQLException e9) {
                    e = e9;
                    byteArrayOutputStream = os;
                    e.printStackTrace();
                    throw new Fatal(e);
                } catch (Throwable th4) {
                    th = th4;
                    byteArrayOutputStream = os;
                    if (oos != null) {
                        oos.close();
                    }
                    if (byteArrayOutputStream != null) {
                        byteArrayOutputStream.close();
                    }
                    throw th;
                }
            } catch (IOException e10) {
                e = e10;
                e.printStackTrace();
                throw new Fatal(e);
            } catch (SQLException e11) {
                e = e11;
                e.printStackTrace();
                throw new Fatal(e);
            }
        }

        public static final <T> T getKKValue(SQLiteDatabase db, String key1, String key2) {
            Cursor c = null;
            T value = null;
            try {
                SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
                queryBuilder.setTables("key_key_value_table");
                SQLiteDatabase sQLiteDatabase = db;
                c = queryBuilder.query(sQLiteDatabase, new String[]{"c_value"}, "c_key_1 = ? AND c_key_2 = ?", new String[]{key1, key2}, null, null, null);
                if (c.moveToFirst()) {
                    value = new ObjectInputStream(new ByteArrayInputStream(c.getBlob(c.getColumnIndex("c_value")))).readObject();
                }
                if (c != null) {
                    c.close();
                }
                return value;
            } catch (Throwable e) {
                e.printStackTrace();
                throw new Fatal(e);
            } catch (Throwable e2) {
                e2.printStackTrace();
                throw new Fatal(e2);
            } catch (Throwable e22) {
                e22.printStackTrace();
                throw new Fatal(e22);
            } catch (Throwable th) {
                if (c != null) {
                    c.close();
                }
            }
        }

        public static final <T> List<T> getKKValues(SQLiteDatabase db, String key1) {
            Cursor c = null;
            List<T> values = new ArrayList();
            try {
                SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
                queryBuilder.setTables("key_key_value_table");
                SQLiteDatabase sQLiteDatabase = db;
                c = queryBuilder.query(sQLiteDatabase, new String[]{"c_value"}, "c_key_1 = ? ", new String[]{key1}, null, null, null);
                if (c.moveToFirst()) {
                    do {
                        values.add(new ObjectInputStream(new ByteArrayInputStream(c.getBlob(c.getColumnIndex("c_value")))).readObject());
                    } while (c.moveToNext());
                }
                if (c != null) {
                    c.close();
                }
                return values;
            } catch (Throwable e) {
                e.printStackTrace();
                throw new Fatal(e);
            } catch (Throwable e2) {
                e2.printStackTrace();
                throw new Fatal(e2);
            } catch (Throwable e22) {
                e22.printStackTrace();
                throw new Fatal(e22);
            } catch (Throwable th) {
                if (c != null) {
                    c.close();
                }
            }
        }

        public static final void deleteKKValue(SQLiteDatabase db, String key1, String key2) {
            try {
                db.delete("key_key_value_table", "c_key_1 = ? AND c_key_2 = ? ", new String[]{key1, key2});
            } catch (Throwable e) {
                e.printStackTrace();
                throw new Fatal(e);
            }
        }

        public static final void deleteKKValues(SQLiteDatabase db, String key1) {
            try {
                db.delete("key_key_value_table", "c_key_1 = ?", new String[]{key1});
            } catch (Throwable e) {
                e.printStackTrace();
                throw new Fatal(e);
            }
        }

        public static final void setUser(SQLiteDatabase db, UserValue user) {
            int i = 1;
            try {
                ContentValues values = new ContentValues();
                values.put("c_uid", user.getUid());
                String str = User.C_DEFAULT;
                if (!user.isDefault()) {
                    i = 0;
                }
                values.put(str, Integer.valueOf(i));
                values.put("c_token", user.getToken());
                values.put("c_name", user.getName());
                values.put("c_description", user.getDescription());
                values.put("c_icon", user.getIcon());
                values.put(User.C_COVER, user.getCover());
                values.put("c_contacts_count", Integer.valueOf(user.getContactsCount()));
                values.put("c_contacted_date", Long.valueOf(user.getContactedDate()));
                if (Float.isNaN(user.getLat()) || Float.isNaN(user.getLng())) {
                    values.put(User.C_IS_NAN_LOCATION, Integer.valueOf(1));
                    values.put(User.C_LNG, Float.valueOf(0.0f));
                    values.put(User.C_LAT, Float.valueOf(0.0f));
                } else {
                    values.put(User.C_IS_NAN_LOCATION, Integer.valueOf(0));
                    values.put(User.C_LNG, Float.valueOf(user.getLng()));
                    values.put(User.C_LAT, Float.valueOf(user.getLat()));
                }
                values.put(User.C_LOCATED_DATE, Long.valueOf(user.getLocatedDate()));
                values.put(User.C_UNREAD_COUNTS, Integer.valueOf(user.getUnreadCount()));
                if (user.getApp() != null) {
                    values.put(User.C_APP_UID, user.getApp().getUid());
                }
                values.put("c_ex_id", user.getExId());
                if (db.replaceOrThrow(User.TABLE, null, values) == -1) {
                    throw new Fatal();
                } else if (user.getApp() != null) {
                    setApp(db, user.getApp());
                }
            } catch (Throwable e) {
                e.printStackTrace();
                throw new Fatal(e);
            }
        }

        public static final UserValue getUser(SQLiteDatabase db, String tragetUser) {
            Cursor c = null;
            UserValue user = null;
            try {
                SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
                queryBuilder.setTables(User.TABLE);
                SQLiteDatabase sQLiteDatabase = db;
                c = queryBuilder.query(sQLiteDatabase, new String[]{"c_uid", User.C_DEFAULT, "c_token", "c_name", "c_description", "c_icon", User.C_COVER, "c_contacts_count", "c_contacted_date", User.C_IS_NAN_LOCATION, User.C_LNG, User.C_LAT, User.C_LOCATED_DATE, User.C_UNREAD_COUNTS, User.C_APP_UID, "c_ex_id"}, "c_uid = ?", new String[]{tragetUser}, null, null, null);
                if (c.moveToFirst()) {
                    user = toUserValue(db, c);
                }
                if (c != null) {
                    c.close();
                }
                return user;
            } catch (Throwable th) {
                if (c != null) {
                    c.close();
                }
            }
        }

        public static final UserValue getUserFromAppUid(SQLiteDatabase db, String appUid) {
            Cursor c = null;
            UserValue user = null;
            try {
                SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
                queryBuilder.setTables(User.TABLE);
                SQLiteDatabase sQLiteDatabase = db;
                c = queryBuilder.query(sQLiteDatabase, new String[]{"c_uid", User.C_DEFAULT, "c_token", "c_name", "c_description", "c_icon", User.C_COVER, "c_contacts_count", "c_contacted_date", User.C_IS_NAN_LOCATION, User.C_LNG, User.C_LAT, User.C_LOCATED_DATE, User.C_UNREAD_COUNTS, User.C_APP_UID, "c_ex_id"}, "c_app_uid = ?", new String[]{appUid}, null, null, null);
                if (c.moveToFirst()) {
                    user = toUserValue(db, c);
                }
                if (c != null) {
                    c.close();
                }
                return user;
            } catch (Throwable th) {
                if (c != null) {
                    c.close();
                }
            }
        }

        public static final UserValue getDefaultUser(SQLiteDatabase db) {
            Cursor c = null;
            UserValue user = null;
            try {
                SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
                queryBuilder.setTables(User.TABLE);
                SQLiteDatabase sQLiteDatabase = db;
                c = queryBuilder.query(sQLiteDatabase, new String[]{"c_uid", User.C_DEFAULT, "c_token", "c_name", "c_description", "c_icon", User.C_COVER, "c_contacts_count", "c_contacted_date", User.C_IS_NAN_LOCATION, User.C_LNG, User.C_LAT, User.C_LOCATED_DATE, User.C_UNREAD_COUNTS, User.C_APP_UID, "c_ex_id"}, "c_default = ?", new String[]{"1"}, null, null, null);
                if (c.moveToFirst()) {
                    user = toUserValue(db, c);
                }
                if (c != null) {
                    c.close();
                }
                return user;
            } catch (Throwable th) {
                if (c != null) {
                    c.close();
                }
            }
        }

        public static final List<UserValue> getUsers(SQLiteDatabase db) {
            Cursor c = null;
            List<UserValue> users = new ArrayList();
            try {
                SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
                queryBuilder.setTables(User.TABLE);
                SQLiteDatabase sQLiteDatabase = db;
                c = queryBuilder.query(sQLiteDatabase, new String[]{"c_uid", User.C_DEFAULT, "c_token", "c_name", "c_description", "c_icon", User.C_COVER, "c_contacts_count", "c_contacted_date", User.C_IS_NAN_LOCATION, User.C_LNG, User.C_LAT, User.C_LOCATED_DATE, User.C_UNREAD_COUNTS, User.C_APP_UID, "c_ex_id"}, null, null, null, null, "c_default DESC , c_app_uid ASC ");
                if (c.moveToFirst()) {
                    do {
                        users.add(toUserValue(db, c));
                    } while (c.moveToNext());
                }
                if (c != null) {
                    c.close();
                }
                return users;
            } catch (Throwable th) {
                if (c != null) {
                    c.close();
                }
            }
        }

        private static final UserValue toUserValue(SQLiteDatabase db, Cursor c) {
            String uid = c.getString(c.getColumnIndex("c_uid"));
            boolean isDefault = c.getInt(c.getColumnIndex(User.C_DEFAULT)) == 1;
            String token = c.getString(c.getColumnIndex("c_token"));
            String name = c.getString(c.getColumnIndex("c_name"));
            String description = c.getString(c.getColumnIndex("c_description"));
            String icon = c.getString(c.getColumnIndex("c_icon"));
            String cover = c.getString(c.getColumnIndex(User.C_COVER));
            int contactsCount = c.getInt(c.getColumnIndex("c_contacts_count"));
            long contactedDate = c.getLong(c.getColumnIndex("c_contacted_date"));
            boolean isNanLocation = c.getInt(c.getColumnIndex(User.C_IS_NAN_LOCATION)) == 1;
            int unreadCount = c.getInt(c.getColumnIndex(User.C_UNREAD_COUNTS));
            float lng = Float.NaN;
            float lat = Float.NaN;
            if (!isNanLocation) {
                lng = c.getFloat(c.getColumnIndex(User.C_LNG));
                lat = c.getFloat(c.getColumnIndex(User.C_LAT));
            }
            long locatedDate = c.getLong(c.getColumnIndex(User.C_LOCATED_DATE));
            String clientId = c.getString(c.getColumnIndex(User.C_APP_UID));
            AppValue appValue = null;
            if (clientId != null) {
                appValue = getApp(db, clientId);
            }
            return new UserValue(uid, isDefault, token, name, description, icon, cover, contactsCount, contactedDate, lng, lat, locatedDate, unreadCount, appValue, c.getString(c.getColumnIndex("c_ex_id")));
        }

        public static final void deleteUser(SQLiteDatabase db, String userUid) {
            try {
                UserValue user = getUser(db, userUid);
                db.delete(User.TABLE, "c_uid = ? ", new String[]{userUid});
                if (user.getApp() != null) {
                    deleteApp(db, user.getApp().getName());
                }
            } catch (Throwable e) {
                e.printStackTrace();
                throw new Fatal(e);
            }
        }

        public static final void deleteUserFromAppUid(SQLiteDatabase db, String appUid) {
            try {
                UserValue user = getUserFromAppUid(db, appUid);
                db.delete(User.TABLE, "c_app_uid = ? ", new String[]{appUid});
                if (user.getApp() != null) {
                    deleteApp(db, user.getApp().getName());
                }
            } catch (Throwable e) {
                e.printStackTrace();
                throw new Fatal(e);
            }
        }

        public static final void deleteAllUser(SQLiteDatabase db) {
            try {
                db.delete(User.TABLE, null, null);
                db.delete(App.TABLE, null, null);
            } catch (Throwable e) {
                e.printStackTrace();
                throw new Fatal(e);
            }
        }

        public static final void setApp(SQLiteDatabase db, AppValue app) {
            try {
                ContentValues values = new ContentValues();
                values.put("c_name", app.getName());
                values.put("c_icon", app.getIcon());
                values.put(App.C_APPSTORE_URI, app.getAppstoreUri());
                values.put(App.C_PLAYSTORE_URI, app.getPlaystoreUri());
                values.put("c_uid", app.getUid());
                values.put("c_client_id", app.getClientId());
                if (db.replaceOrThrow(App.TABLE, null, values) == -1) {
                    throw new Fatal();
                }
            } catch (Throwable e) {
                e.printStackTrace();
                throw new Fatal(e);
            }
        }

        public static final AppValue getApp(SQLiteDatabase db, String appUid) {
            Cursor c = null;
            try {
                AppValue app;
                SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
                queryBuilder.setTables(App.TABLE);
                SQLiteDatabase sQLiteDatabase = db;
                c = queryBuilder.query(sQLiteDatabase, new String[]{"c_name", "c_icon", App.C_APPSTORE_URI, App.C_PLAYSTORE_URI, "c_client_id"}, "c_uid = ?", new String[]{appUid}, null, null, null);
                if (c.moveToFirst()) {
                    app = new AppValue(c.getString(c.getColumnIndex("c_name")), c.getString(c.getColumnIndex("c_icon")), c.getString(c.getColumnIndex(App.C_APPSTORE_URI)), c.getString(c.getColumnIndex(App.C_PLAYSTORE_URI)), appUid, c.getString(c.getColumnIndex("c_client_id")));
                } else {
                    app = null;
                }
                if (c != null) {
                    c.close();
                }
                return app;
            } catch (Throwable th) {
                if (c != null) {
                    c.close();
                }
            }
        }

        public static final void deleteApp(SQLiteDatabase db, String appName) {
            try {
                db.delete(App.TABLE, "c_name = ? ", new String[]{appName});
            } catch (Throwable e) {
                e.printStackTrace();
                throw new Fatal(e);
            }
        }
    }
}
