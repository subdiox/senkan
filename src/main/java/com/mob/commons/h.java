package com.mob.commons;

import android.content.ContentValues;
import com.kayac.lobi.libnakamap.net.APIDef.PostAppData.RequestKey;
import com.mob.tools.MobLog;
import com.mob.tools.utils.SQLiteHelper;
import com.rekoo.libs.net.URLCons;
import java.util.HashMap;

class h implements Runnable {
    final /* synthetic */ HashMap a;
    final /* synthetic */ g b;

    h(g gVar, HashMap hashMap) {
        this.b = gVar;
        this.a = hashMap;
    }

    public void run() {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(URLCons.TIME, String.valueOf(a.b(this.b.b)));
            contentValues.put(RequestKey.DATA, this.b.e.fromHashMap(this.a));
            SQLiteHelper.insert(this.b.d, contentValues);
        } catch (Throwable th) {
            MobLog.getInstance().w(th);
        }
    }
}
