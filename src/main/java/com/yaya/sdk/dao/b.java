package com.yaya.sdk.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.yaya.sdk.constants.Constants;

public class b extends SQLiteOpenHelper {
    private static final int a = 1;

    public b(Context context) {
        super(context, Constants.DB_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS  table_name(id integer primary key autoincrement, helloid varchar(20))");
    }

    private void a(SQLiteDatabase sQLiteDatabase) {
        ContentValues contentValues = new ContentValues();
        ContentValues contentValues2 = new ContentValues();
        contentValues.put("name", "ober");
        contentValues2.put("name", "ober2016");
        sQLiteDatabase.insert("test_user", null, contentValues);
        sQLiteDatabase.insert("test_user", null, contentValues2);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS table_name");
        onCreate(db);
    }
}
