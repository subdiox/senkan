package com.rekoo.libs.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.rekoo.libs.config.Config;
import com.rekoo.libs.encrypt_decrypt.DES;
import com.rekoo.libs.entity.User;
import com.rekoo.libs.net.URLCons;
import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private static DBHelper dbHelper;
    private static DBManager manager;

    public static DBManager getManager(Context context) {
        if (manager == null) {
            synchronized (DBManager.class) {
                if (manager == null) {
                    manager = new DBManager(context);
                }
            }
        }
        return manager;
    }

    private DBManager(Context context) {
        if (dbHelper == null) {
            dbHelper = new DBHelper(context);
        }
    }

    private SQLiteDatabase getDB() {
        return dbHelper.getReadableDatabase();
    }

    public boolean hasUser() {
        int count = -1;
        Cursor cursor = getDB().rawQuery("select count(*) from rkuser;", null);
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        if (count == 0 || count == -1) {
            return false;
        }
        return true;
    }

    public void saveUser(User user, Context context) {
        ContentValues values = new ContentValues();
        values.put(UserColumns.UID, user.getUid());
        values.put("TOKEN", user.getToken());
        values.put(UserColumns.USERNAME, user.getUserName());
        String passwordDES = user.getPassword();
        try {
            passwordDES = DES.encode(passwordDES, URLCons.getAppId(context));
        } catch (Exception e) {
            e.printStackTrace();
        }
        values.put(UserColumns.PASSWORD, passwordDES);
        values.put(UserColumns.USERTYPE, Integer.valueOf(user.getType()));
        values.put(UserColumns.LAST_REFRESH_TIME, Long.valueOf(System.currentTimeMillis()));
        if (isExsit(user)) {
            update(values, user);
        } else {
            insert(values);
        }
        Config.getConfig().setCurrentLoginUser(user);
    }

    private void update(ContentValues values, User user) {
        String[] whereArgs = new String[]{user.getUid()};
        getDB().update(DBHelper.TABLE_NAME, values, "MID = ?", whereArgs);
    }

    public void insert(ContentValues values) {
        getDB().insert(DBHelper.TABLE_NAME, null, values);
    }

    public List<User> getAllUsers(Context context) {
        List<User> users = new ArrayList();
        Cursor cursor = getDB().query(true, DBHelper.TABLE_NAME, null, null, null, null, null, "LAST_REFRESH_TIME DESC", null);
        while (cursor.moveToNext()) {
            User user = new User();
            user.setUid(cursor.getString(cursor.getColumnIndex(UserColumns.UID)));
            user.setToken(cursor.getString(cursor.getColumnIndex("TOKEN")));
            user.setType(cursor.getInt(cursor.getColumnIndex(UserColumns.USERTYPE)));
            user.setUserName(cursor.getString(cursor.getColumnIndex(UserColumns.USERNAME)));
            String password = null;
            try {
                password = DES.decode(cursor.getString(cursor.getColumnIndex(UserColumns.PASSWORD)), URLCons.getAppId(context));
            } catch (Exception e) {
                e.printStackTrace();
            }
            user.setPassword(password);
            users.add(user);
        }
        cursor.close();
        return users;
    }

    public boolean isExsit(User user) {
        String[] selectColumns = new String[]{UserColumns.UID};
        String[] whereArgs = new String[]{user.getUid()};
        return getDB().query(DBHelper.TABLE_NAME, selectColumns, "MID = ? ", whereArgs, null, null, null).moveToFirst();
    }

    public int deleteUser(User user) {
        String[] whereArgs = new String[]{user.getUid()};
        return getDB().delete(DBHelper.TABLE_NAME, "MID = ?", whereArgs);
    }

    public Cursor getCursor() {
        return getDB().query(true, DBHelper.TABLE_NAME, null, null, null, null, null, UserColumns.LAST_REFRESH_TIME, null);
    }

    public User isTouristExsit(Context context) {
        User user = null;
        String[] whereArgs = new String[]{"1"};
        Cursor cursor = getDB().query(true, DBHelper.TABLE_NAME, null, "USERTYPE = ? ", whereArgs, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            user = new User();
            user.setUid(cursor.getString(cursor.getColumnIndex(UserColumns.UID)));
            user.setToken(cursor.getString(cursor.getColumnIndex("TOKEN")));
            user.setType(cursor.getInt(cursor.getColumnIndex(UserColumns.USERTYPE)));
            user.setUserName(cursor.getString(cursor.getColumnIndex(UserColumns.USERNAME)));
            String password = null;
            try {
                password = DES.decode(cursor.getString(cursor.getColumnIndex(UserColumns.PASSWORD)), URLCons.getAppId(context));
            } catch (Exception e) {
                e.printStackTrace();
            }
            user.setPassword(password);
        }
        cursor.close();
        return user;
    }

    public void destory() {
        if (manager != null) {
            manager.onDestory();
        }
    }

    private void onDestory() {
        manager = null;
        if (dbHelper != null) {
            dbHelper.close();
            dbHelper = null;
        }
    }
}
