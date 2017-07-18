package com.rekoo.libs.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DB_VERSION = 3;
    public static final String RK_DB_NAME = "rk_db_sdk";
    public static final String TABLE_NAME = "rkuser";

    public DBHelper(Context context) {
        super(context, RK_DB_NAME, null, 3);
    }

    public void onCreate(SQLiteDatabase db) {
        createUserTable(db);
    }

    private void createUserTable(SQLiteDatabase db) {
        StringBuilder sql = new StringBuilder();
        sql.append("create table ").append(TABLE_NAME);
        sql.append(" ( ");
        sql.append(UserColumns.UID).append(" varchar(30) primary key,");
        sql.append("TOKEN").append(" varchar(200),");
        sql.append(UserColumns.USERNAME).append(" varchar(100),");
        sql.append(UserColumns.PHONE).append(" varchar(100),");
        sql.append(UserColumns.LAST_REFRESH_TIME).append(" Long,");
        sql.append(UserColumns.IS_FAST).append(" integer,");
        sql.append(UserColumns.USERTYPE).append(" varchar(200),");
        sql.append(UserColumns.PASSWORD).append(" varchar(100),");
        sql.append(UserColumns.IS_PASS).append(" varchar(30)");
        sql.append(");");
        db.execSQL(sql.toString());
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("---onUpgrade-----");
    }
}
