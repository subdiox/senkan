package cn.sharesdk.framework.b.b;

import android.text.TextUtils;
import android.util.Base64;
import cn.sharesdk.framework.utils.d;
import com.mob.tools.utils.Data;
import org.apache.commons.io.IOUtils;

public class b extends c {
    private static int n;
    private static long o;
    public int a;
    public String b;
    public String c;
    public String d;

    protected String a() {
        return "[AUT]";
    }

    protected void a(long j) {
        o = j;
    }

    protected int b() {
        return 5000;
    }

    protected int c() {
        return 5;
    }

    protected long d() {
        return (long) n;
    }

    protected long e() {
        return o;
    }

    protected void f() {
        n++;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(super.toString());
        stringBuilder.append('|').append(this.a);
        stringBuilder.append('|').append(this.b);
        stringBuilder.append('|');
        if (!TextUtils.isEmpty(this.d)) {
            try {
                String encodeToString = Base64.encodeToString(Data.AES128Encode(this.f.substring(0, 16), this.d), 0);
                if (!TextUtils.isEmpty(encodeToString) && encodeToString.contains(IOUtils.LINE_SEPARATOR_UNIX)) {
                    encodeToString = encodeToString.replace(IOUtils.LINE_SEPARATOR_UNIX, "");
                }
                stringBuilder.append(encodeToString);
            } catch (Throwable th) {
                d.a().d(th);
            }
        }
        stringBuilder.append('|');
        if (!TextUtils.isEmpty(this.m)) {
            stringBuilder.append(this.m);
        }
        stringBuilder.append('|');
        if (!TextUtils.isEmpty(this.c)) {
            stringBuilder.append(this.c);
        }
        return stringBuilder.toString();
    }
}
