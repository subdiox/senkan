package cn.sharesdk.framework.b.a;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import java.util.ArrayList;
import org.apache.commons.io.IOUtils;

public class d {
    public static int a = 0;
    public static int b = 1;
    public static int c = 2;

    public static synchronized long a(Context context, String str, long j) {
        long a;
        synchronized (d.class) {
            if (str != null) {
                if (str.trim() != "") {
                    b a2 = b.a(context);
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("post_time", Long.valueOf(j));
                    contentValues.put("message_data", str.toString());
                    a = a2.a("message", contentValues);
                }
            }
            a = -1;
        }
        return a;
    }

    public static synchronized long a(Context context, ArrayList<String> arrayList) {
        long j;
        synchronized (d.class) {
            if (arrayList == null) {
                j = 0;
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < arrayList.size(); i++) {
                    stringBuilder.append("'");
                    stringBuilder.append((String) arrayList.get(i));
                    stringBuilder.append("'");
                    stringBuilder.append(",");
                }
                cn.sharesdk.framework.utils.d.a().i("delete COUNT == %s", Integer.valueOf(b.a(context).a("message", "_id in ( " + stringBuilder.toString().substring(0, stringBuilder.length() - 1) + " )", null)));
                j = (long) b.a(context).a("message", "_id in ( " + stringBuilder.toString().substring(0, stringBuilder.length() - 1) + " )", null);
            }
        }
        return j;
    }

    public static synchronized ArrayList<c> a(Context context) {
        ArrayList<c> a;
        synchronized (d.class) {
            a = b.a(context).a("message") > 0 ? a(context, null, null) : new ArrayList();
        }
        return a;
    }

    private static synchronized ArrayList<c> a(Context context, String str, String[] strArr) {
        ArrayList<c> arrayList;
        synchronized (d.class) {
            arrayList = new ArrayList();
            c cVar = new c();
            StringBuilder stringBuilder = new StringBuilder();
            Cursor a = b.a(context).a("message", new String[]{"_id", "post_time", "message_data"}, str, strArr, null);
            StringBuilder stringBuilder2 = stringBuilder;
            c cVar2 = cVar;
            while (a != null && a.moveToNext()) {
                cVar2.b.add(a.getString(0));
                if (cVar2.b.size() == 100) {
                    stringBuilder2.append(a.getString(2));
                    cVar2.a = stringBuilder2.toString();
                    arrayList.add(cVar2);
                    cVar2 = new c();
                    stringBuilder2 = new StringBuilder();
                } else {
                    stringBuilder2.append(a.getString(2) + IOUtils.LINE_SEPARATOR_UNIX);
                }
            }
            a.close();
            if (cVar2.b.size() != 0) {
                cVar2.a = stringBuilder2.toString().substring(0, stringBuilder2.length() - 1);
                arrayList.add(cVar2);
            }
        }
        return arrayList;
    }
}
