package com.yaya.sdk.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.yaya.sdk.constants.Constants;

public class a {
    private static String b = a.class.getSimpleName();
    private static a c;
    private b a;

    public a(Context context) {
        this.a = new b(context);
    }

    public static synchronized a a(Context context) {
        a aVar;
        synchronized (a.class) {
            if (c == null) {
                c = new a(context);
            }
            aVar = c;
        }
        return aVar;
    }

    public void a(int i) {
        String.valueOf(i);
        SQLiteDatabase writableDatabase = this.a.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.TABLE_HELLO_ID, Integer.valueOf(i));
        writableDatabase.insert(Constants.TABLE_NAME, null, contentValues);
        writableDatabase.close();
    }

    public boolean b(int i) {
        boolean z = true;
        String valueOf = String.valueOf(i);
        SQLiteDatabase readableDatabase = this.a.getReadableDatabase();
        Cursor rawQuery = readableDatabase.rawQuery("select helloid from table_name where helloid =?", new String[]{valueOf});
        if (!rawQuery.moveToNext()) {
            z = false;
        }
        rawQuery.close();
        readableDatabase.close();
        return z;
    }

    public boolean a(String str) {
        boolean z = true;
        SQLiteDatabase writableDatabase = this.a.getWritableDatabase();
        if (writableDatabase.delete(Constants.TABLE_NAME, "helloid=?", new String[]{str}) == 0) {
            z = false;
        }
        writableDatabase.close();
        return z;
    }
}
