package com.mob.commons.logcollector;

import android.content.Context;
import com.mob.tools.utils.SharePrefrenceHelper;

public class d {
    private static d a;
    private SharePrefrenceHelper b;

    private d(Context context) {
        this.b = new SharePrefrenceHelper(context.getApplicationContext());
        this.b.open("mob_sdk_exception", 1);
    }

    public static d a(Context context) {
        if (a == null) {
            a = new d(context);
        }
        return a;
    }

    public long a() {
        return this.b.getLong("service_time");
    }

    public void a(int i) {
        this.b.putInt("is_upload_crash", Integer.valueOf(i));
    }

    public void a(long j) {
        this.b.putLong("service_time", Long.valueOf(j));
    }

    public void a(String str) {
        this.b.putString("err_log_filter", str);
    }

    public void a(boolean z) {
        this.b.putInt("is_upload_err_log", Integer.valueOf(z ? 0 : 1));
    }

    public void b(int i) {
        this.b.putInt("is_upload_sdkerr", Integer.valueOf(i));
    }

    public boolean b() {
        return this.b.getInt("is_upload_err_log") == 0;
    }

    public int c() {
        return this.b.getInt("is_upload_crash");
    }

    public void c(int i) {
        this.b.putInt("is_upload_apperr", Integer.valueOf(i));
    }

    public int d() {
        return this.b.getInt("is_upload_sdkerr");
    }

    public int e() {
        return this.b.getInt("is_upload_apperr");
    }

    public String f() {
        return this.b.getString("err_log_filter");
    }
}
