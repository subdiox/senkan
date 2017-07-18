package cn.sharesdk.framework.b.a;

import android.content.Context;
import android.text.TextUtils;
import com.mob.tools.utils.SharePrefrenceHelper;
import com.tencent.bugly.Bugly;

public class e {
    private static e c;
    private Context a;
    private SharePrefrenceHelper b = new SharePrefrenceHelper(this.a);

    private e(Context context) {
        this.a = context.getApplicationContext();
        this.b.open("share_sdk", 1);
    }

    public static e a(Context context) {
        if (c == null) {
            c = new e(context.getApplicationContext());
        }
        return c;
    }

    public long a() {
        return this.b.getLong("service_time");
    }

    public void a(long j) {
        this.b.putLong("device_time", Long.valueOf(j));
    }

    public void a(String str) {
        this.b.putString("trans_short_link", str);
    }

    public void a(String str, int i) {
        this.b.putInt(str, Integer.valueOf(i));
    }

    public void a(String str, Long l) {
        this.b.putLong(str, l);
    }

    public void a(String str, Object obj) {
        this.b.put(str, obj);
    }

    public void a(String str, String str2) {
        this.b.putString("buffered_snsconf_" + str, str2);
    }

    public void a(boolean z) {
        this.b.putBoolean("connect_server", Boolean.valueOf(z));
    }

    public void b(long j) {
        this.b.putLong("connect_server_time", Long.valueOf(j));
    }

    public void b(String str) {
        this.b.putString("upload_device_info", str);
    }

    public void b(boolean z) {
        this.b.putBoolean("upload_device_duid", Boolean.valueOf(z));
    }

    public boolean b() {
        Object string = this.b.getString("upload_device_info");
        return TextUtils.isEmpty(string) ? true : Boolean.parseBoolean(string);
    }

    public void c(String str) {
        this.b.putString("upload_user_info", str);
    }

    public boolean c() {
        Object string = this.b.getString("upload_user_info");
        return TextUtils.isEmpty(string) ? true : Boolean.parseBoolean(string);
    }

    public void d(String str) {
        this.b.putString("upload_share_content", str);
    }

    public boolean d() {
        Object string = this.b.getString("trans_short_link");
        return TextUtils.isEmpty(string) ? false : Boolean.parseBoolean(string);
    }

    public int e() {
        String string = this.b.getString("upload_share_content");
        return "true".equals(string) ? 1 : Bugly.SDK_IS_DEV.equals(string) ? -1 : 0;
    }

    public String e(String str) {
        return this.b.getString("buffered_snsconf_" + str);
    }

    public long f(String str) {
        return this.b.getLong(str);
    }

    public Long f() {
        return Long.valueOf(this.b.getLong("device_time"));
    }

    public int g(String str) {
        return this.b.getInt(str);
    }

    public boolean g() {
        return this.b.getBoolean("connect_server");
    }

    public Long h() {
        return Long.valueOf(this.b.getLong("connect_server_time"));
    }

    public Object h(String str) {
        return this.b.get(str);
    }

    public boolean i() {
        return this.b.getBoolean("upload_device_duid");
    }
}
