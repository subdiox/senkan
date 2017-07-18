package cn.sharesdk.framework.b;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.text.TextUtils;
import android.util.Base64;
import cn.sharesdk.framework.b.a.e;
import cn.sharesdk.framework.b.b.b;
import cn.sharesdk.framework.b.b.c;
import cn.sharesdk.framework.b.b.f;
import cn.sharesdk.framework.utils.d;
import com.coremedia.iso.boxes.AuthorBox;
import com.google.android.gcm.GCMConstants;
import com.kayac.lobi.libnakamap.net.APIDef.PostAppData.RequestKey;
import com.kayac.lobi.libnakamap.utils.NotificationUtil;
import com.mob.tools.utils.BitmapHelper;
import com.mob.tools.utils.Data;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.R;
import com.rekoo.libs.net.URLCons;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPOutputStream;

public class a {
    private static a a;
    private c b;
    private DeviceHelper c;
    private e d;
    private boolean e;

    private a(Context context, String str) {
        this.b = new c(context, str);
        this.d = e.a(context);
        this.c = DeviceHelper.getInstance(context);
    }

    public static a a(Context context, String str) {
        if (a == null) {
            a = new a(context, str);
        }
        return a;
    }

    private String a(Bitmap bitmap, b bVar) {
        File createTempFile = File.createTempFile("bm_tmp", ".png");
        OutputStream fileOutputStream = new FileOutputStream(createTempFile);
        bitmap.compress(CompressFormat.PNG, 100, fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
        return a(createTempFile.getAbsolutePath(), bVar);
    }

    private String a(String str, b bVar) {
        if (TextUtils.isEmpty(str) || !new File(str).exists()) {
            return null;
        }
        CharSequence networkType = this.c.getNetworkType();
        if ("none".equals(networkType) || TextUtils.isEmpty(networkType)) {
            return null;
        }
        int ceil;
        CompressFormat bmpFormat = BitmapHelper.getBmpFormat(str);
        float f = 200.0f;
        if (bVar == b.BEFORE_SHARE) {
            f = 600.0f;
        }
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        options.inJustDecodeBounds = false;
        int i = options.outWidth;
        int i2 = options.outHeight;
        if (i >= i2 && ((float) i2) > f) {
            ceil = (int) Math.ceil((double) (((float) options.outHeight) / f));
        } else if (i >= i2 || ((float) i) <= f) {
            return d(str);
        } else {
            ceil = (int) Math.ceil((double) (((float) options.outWidth) / f));
        }
        if (ceil <= 0) {
            ceil = 1;
        }
        options = new Options();
        options.inSampleSize = ceil;
        options.inPurgeable = true;
        options.inInputShareable = true;
        Bitmap decodeFile = BitmapFactory.decodeFile(str, options);
        decodeFile.getHeight();
        decodeFile.getWidth();
        File createTempFile = File.createTempFile("bm_tmp2", "." + bmpFormat.name().toLowerCase());
        OutputStream fileOutputStream = new FileOutputStream(createTempFile);
        decodeFile.compress(bmpFormat, 80, fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
        return d(createTempFile.getAbsolutePath());
    }

    private String a(String str, String str2, int i, String str3) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        ArrayList arrayList = new ArrayList();
        Pattern compile = Pattern.compile(str2);
        Matcher matcher = compile.matcher(str);
        while (matcher.find()) {
            String group = matcher.group();
            if (group != null && group.length() > 0) {
                arrayList.add(group);
            }
        }
        if (arrayList.size() == 0) {
            return str;
        }
        HashMap a = this.b.a(str, arrayList, i, str3);
        if (a == null || a.size() <= 0 || !a.containsKey(RequestKey.DATA)) {
            return str;
        }
        arrayList = (ArrayList) a.get(RequestKey.DATA);
        HashMap hashMap = new HashMap();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            a = (HashMap) it.next();
            hashMap.put(String.valueOf(a.get("source")), String.valueOf(a.get("surl")));
        }
        Matcher matcher2 = compile.matcher(str);
        StringBuilder stringBuilder = new StringBuilder();
        int i2 = 0;
        while (matcher2.find()) {
            stringBuilder.append(str.substring(i2, matcher2.start()));
            stringBuilder.append((String) hashMap.get(matcher2.group()));
            i2 = matcher2.end();
        }
        stringBuilder.append(str.substring(i2, str.length()));
        d.a().i("> SERVER_SHORT_LINK_URL content after replace link ===  %s", stringBuilder.toString());
        return stringBuilder.toString();
    }

    private void a(b bVar) {
        boolean c = this.d.c();
        Object obj = bVar.c;
        if (!c || TextUtils.isEmpty(obj)) {
            bVar.d = null;
            bVar.c = null;
            return;
        }
        bVar.c = Data.Base64AES(obj, bVar.f.substring(0, 16));
    }

    private void a(f fVar) {
        int i = 0;
        int e = this.d.e();
        boolean c = this.d.c();
        cn.sharesdk.framework.b.b.f.a aVar = fVar.d;
        if (e == 1 || (e == 0 && this.e)) {
            CharSequence a;
            int size = (aVar == null || aVar.e == null) ? 0 : aVar.e.size();
            for (int i2 = 0; i2 < size; i2++) {
                a = a((String) aVar.e.get(i2), b.FINISH_SHARE);
                if (!TextUtils.isEmpty(a)) {
                    aVar.d.add(a);
                }
            }
            size = (aVar == null || aVar.f == null) ? 0 : aVar.f.size();
            while (i < size) {
                a = a((Bitmap) aVar.f.get(i), b.FINISH_SHARE);
                if (!TextUtils.isEmpty(a)) {
                    aVar.d.add(a);
                }
                i++;
            }
        } else {
            fVar.d = null;
        }
        if (!c) {
            fVar.n = null;
        }
    }

    private boolean a(String str, boolean z) {
        return this.b.a(str, z);
    }

    private String d(String str) {
        HashMap b = this.b.b(str);
        return (b != null && b.size() > 0 && b.containsKey("status") && R.parseInt(String.valueOf(b.get("status"))) == 200 && b.containsKey("url")) ? (String) b.get("url") : null;
    }

    private String e(String str) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(str.getBytes());
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
        byte[] bArr = new byte[1024];
        while (true) {
            int read = byteArrayInputStream.read(bArr, 0, 1024);
            if (read != -1) {
                gZIPOutputStream.write(bArr, 0, read);
            } else {
                gZIPOutputStream.flush();
                gZIPOutputStream.close();
                byte[] toByteArray = byteArrayOutputStream.toByteArray();
                byteArrayOutputStream.flush();
                byteArrayOutputStream.close();
                byteArrayInputStream.close();
                return Base64.encodeToString(toByteArray, 2);
            }
        }
    }

    public String a(Bitmap bitmap) {
        String str = null;
        if (this.d.g()) {
            try {
                str = a(bitmap, b.BEFORE_SHARE);
            } catch (Throwable th) {
                d.a().d(th);
            }
        }
        return str;
    }

    public String a(String str) {
        String str2 = null;
        if (this.d.g()) {
            try {
                str2 = a(str, b.BEFORE_SHARE);
            } catch (Throwable th) {
                d.a().d(th);
            }
        }
        return str2;
    }

    public String a(String str, int i, boolean z, String str2) {
        try {
            if (!this.d.g() || !this.d.d()) {
                return str;
            }
            CharSequence networkType = this.c.getNetworkType();
            if ("none".equals(networkType) || TextUtils.isEmpty(networkType)) {
                return str;
            }
            String a;
            if (z) {
                a = a(str, "<a[^>]*?href[\\s]*=[\\s]*[\"']?([^'\">]+?)['\"]?>", i, str2);
                if (!(a == null || a.equals(str))) {
                    return a;
                }
            }
            a = a(str, "(http://|https://){1}[\\w\\.\\-/:\\?&%=,;\\[\\]\\{\\}`~!@#\\$\\^\\*\\(\\)_\\+\\\\\\|]+", i, str2);
            return (a == null || a.equals(str)) ? str : a;
        } catch (Throwable th) {
            d.a().d(th);
            return str;
        }
    }

    public void a() {
        try {
            CharSequence networkType = this.c.getNetworkType();
            if (!"none".equals(networkType) && !TextUtils.isEmpty(networkType)) {
                long longValue = this.d.h().longValue();
                long currentTimeMillis = System.currentTimeMillis();
                Calendar instance = Calendar.getInstance();
                instance.setTimeInMillis(longValue);
                int i = instance.get(5);
                instance.setTimeInMillis(currentTimeMillis);
                int i2 = instance.get(5);
                if (currentTimeMillis - longValue >= NotificationUtil.PROMOTION_NOTIFICATION_INTERVAL || i != i2) {
                    HashMap a = this.b.a();
                    this.d.a(a.containsKey("res") ? "true".equals(String.valueOf(a.get("res"))) : true);
                    if (a != null && a.size() > 0) {
                        this.d.b(System.currentTimeMillis());
                    }
                }
            }
        } catch (Throwable th) {
            d.a().d(th);
        }
    }

    public void a(c cVar) {
        try {
            if (this.d.g()) {
                if (cVar instanceof b) {
                    a((b) cVar);
                } else if (cVar instanceof f) {
                    a((f) cVar);
                }
                if (!this.d.b()) {
                    cVar.m = null;
                }
                long a = this.d.a();
                if (a == 0) {
                    a = this.b.b();
                }
                cVar.e = System.currentTimeMillis() - a;
                this.b.a(cVar);
            }
        } catch (Throwable th) {
            d.a().d(th);
        }
    }

    public void a(HashMap<String, Object> hashMap) {
        try {
            this.b.b((HashMap) hashMap);
        } catch (Throwable th) {
            d.a().d(th);
        }
    }

    public void a(boolean z) {
        this.e = z;
    }

    public HashMap<String, Object> b(String str) {
        try {
            return this.b.c(str);
        } catch (Throwable th) {
            d.a().d(th);
            return new HashMap();
        }
    }

    public void b() {
        try {
            CharSequence networkType = this.c.getNetworkType();
            if (!"none".equals(networkType) && !TextUtils.isEmpty(networkType) && this.d.g()) {
                this.d.a(System.currentTimeMillis());
                HashMap c = this.b.c();
                if (c.containsKey("status") && R.parseInt(String.valueOf(c.get("status"))) == -200) {
                    d.a().d((String) c.get(GCMConstants.EXTRA_ERROR), new Object[0]);
                    return;
                }
                HashMap hashMap;
                if (c.containsKey("timestamp")) {
                    this.d.a("service_time", Long.valueOf(System.currentTimeMillis() - R.parseLong(String.valueOf(c.get("timestamp")))));
                }
                if (c.containsKey("switchs")) {
                    hashMap = (HashMap) c.get("switchs");
                    if (hashMap != null) {
                        String valueOf = String.valueOf(hashMap.get(URLCons.DEVICE));
                        String valueOf2 = String.valueOf(hashMap.get("share"));
                        String valueOf3 = String.valueOf(hashMap.get(AuthorBox.TYPE));
                        String valueOf4 = String.valueOf(hashMap.get("backflow"));
                        this.d.b(valueOf);
                        this.d.d(valueOf2);
                        this.d.c(valueOf3);
                        this.d.a(valueOf4);
                    }
                }
                if (c.containsKey("serpaths")) {
                    hashMap = (HashMap) c.get("serpaths");
                    if (hashMap != null) {
                        Object valueOf5 = String.valueOf(hashMap.get("defhost"));
                        Object valueOf6 = String.valueOf(hashMap.get("defport"));
                        if (!(TextUtils.isEmpty(valueOf5) || TextUtils.isEmpty(valueOf6))) {
                            this.b.a(URLCons.HTTP + valueOf5 + ":" + valueOf6);
                        }
                        HashMap hashMap2 = new HashMap();
                        if (hashMap.containsKey("assigns")) {
                            hashMap = (HashMap) hashMap.get("assigns");
                            if (hashMap == null || hashMap.size() == 0) {
                                this.b.a(null);
                                return;
                            }
                            for (String str : hashMap.keySet()) {
                                HashMap hashMap3 = (HashMap) hashMap.get(str);
                                Object valueOf7 = String.valueOf(hashMap3.get("host"));
                                valueOf6 = String.valueOf(hashMap3.get("port"));
                                if (!(TextUtils.isEmpty(str) || TextUtils.isEmpty(valueOf7) || TextUtils.isEmpty(valueOf6))) {
                                    hashMap2.put(str, URLCons.HTTP + valueOf7 + ":" + valueOf6);
                                }
                            }
                            this.b.a(hashMap2);
                        }
                    }
                }
            }
        } catch (Throwable th) {
            d.a().d(th);
        }
    }

    public void c() {
        try {
            CharSequence networkType = this.c.getNetworkType();
            if (!"none".equals(networkType) && !TextUtils.isEmpty(networkType) && this.d.g()) {
                ArrayList e = this.b.e();
                for (int i = 0; i < e.size(); i++) {
                    cn.sharesdk.framework.b.a.c cVar = (cn.sharesdk.framework.b.a.c) e.get(i);
                    if (cVar.b.size() == 1 ? a(cVar.a, false) : a(e(cVar.a), true)) {
                        this.b.a(cVar.b);
                    }
                }
            }
        } catch (Throwable th) {
            d.a().d(th);
        }
    }

    public void c(String str) {
        CharSequence networkType = this.c.getNetworkType();
        if (!"none".equals(networkType) && !TextUtils.isEmpty(networkType) && this.d.g() && !this.d.i()) {
            try {
                this.b.d(str);
                this.d.b(true);
            } catch (Throwable th) {
                d.a().d(th);
            }
        }
    }

    public HashMap<String, Object> d() {
        try {
            return this.b.f();
        } catch (Throwable th) {
            d.a().d(th);
            return new HashMap();
        }
    }

    public HashMap<String, Object> e() {
        if (!this.d.g()) {
            return new HashMap();
        }
        try {
            return this.b.d();
        } catch (Throwable th) {
            d.a().d(th);
            return new HashMap();
        }
    }
}
