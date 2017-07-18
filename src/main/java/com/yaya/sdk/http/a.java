package com.yaya.sdk.http;

import android.content.Context;
import android.os.SystemClock;
import com.coremedia.iso.boxes.AuthorBox;
import com.yaya.sdk.async.http.c;
import com.yaya.sdk.constants.Constants;
import com.yaya.sdk.utils.d;
import com.yaya.sdk.utils.f;
import com.yaya.sdk.utils.h;
import com.yaya.sdk.utils.j;
import com.yaya.sdk.utils.k;
import org.apache.http.entity.StringEntity;

public class a {
    public static final int a = 0;
    public static final int b = 1;
    public static final int c = -1;
    private static final String d = a.class.getSimpleName();
    private static com.yaya.sdk.async.http.a e = new com.yaya.sdk.async.http.a();
    private static String f = "http://hs.yunva.com:9735/";

    class AnonymousClass1 extends c {
        private final /* synthetic */ com.yaya.sdk.listener.a i;

        AnonymousClass1(com.yaya.sdk.listener.a aVar) {
            this.i = aVar;
        }

        public void a(int i, String str) {
            super.a(i, str);
            try {
                if (this.i != null) {
                    this.i.a(d.b(str), i);
                }
            } catch (Exception e) {
            }
        }

        public void a(int i, Throwable th, String str) {
            super.a(i, th, str);
            try {
                if (this.i != null) {
                    this.i.b(str, i);
                }
            } catch (Exception e) {
            }
        }
    }

    private static void a(Context context, String str, String str2, com.yaya.sdk.listener.a aVar) {
        e.a(40);
        e.a(5, 30000);
        try {
            Context context2 = context;
            String str3 = str2;
            e.a(context2, str3, new StringEntity(d.a(str), "utf-8"), "application/json", new AnonymousClass1(aVar));
        } catch (Exception e) {
        }
    }

    public static void a(Context context, com.yaya.sdk.listener.a aVar) {
        long a = j.a(context, Constants.UPLOAD_INFO_TIME, Long.valueOf(0));
        long currentThreadTimeMillis = SystemClock.currentThreadTimeMillis();
        if (a == 0 || a - currentThreadTimeMillis >= 1800000) {
            try {
                j.a(context, Constants.UPLOAD_INFO_TIME, currentThreadTimeMillis);
                a(context, f.a(context).replace(":", "=").replace("{", "").replace("}", "").replace(",", "&&").replace("\"", ""), f + AuthorBox.TYPE, aVar);
            } catch (Exception e) {
            }
        }
    }

    public static synchronized void b(Context context, com.yaya.sdk.listener.a aVar) {
        synchronized (a.class) {
            com.yaya.sdk.bean.req.c cVar = new com.yaya.sdk.bean.req.c();
            cVar.b(k.a(context));
            cVar.c(Constants.APP_ID);
            cVar.a(Integer.valueOf(1));
            cVar.a(Constants.VERSION_NAME);
            cVar.d(h.a(context));
            try {
                a(context, f.a(cVar).replace(":", "=").replace("{", "").replace("}", "").replace(",", "&&").replace("\"", ""), f + "checkId", aVar);
            } catch (Exception e) {
            }
        }
    }

    public static synchronized void a(Context context, com.yaya.sdk.bean.req.h hVar, com.yaya.sdk.listener.a aVar) {
        synchronized (a.class) {
            if (hVar != null) {
                try {
                    a(context, f.a(hVar).replace(":", "=").replace("{", "").replace("}", "").replace(",", "&&").replace("\"", "").replace("userData=", "userData=" + hVar.b()), f + "loaddata", aVar);
                } catch (Exception e) {
                }
            }
        }
    }

    public static void a(Context context, com.yaya.sdk.bean.req.d dVar, com.yaya.sdk.listener.a aVar) {
        if (dVar != null) {
            try {
                a(context, f.a(dVar).replace(":", "=").replace("{", "").replace("}", "").replace(",", "&&").replace("\"", ""), f + "errReport", aVar);
            } catch (Exception e) {
            }
        }
    }

    public static void a(Context context, com.yaya.sdk.listener.a aVar, com.yaya.sdk.bean.req.f fVar) {
        try {
            a(context, f.a(fVar).replace(":", "=").replace("{", "").replace("}", "").replace(",", "&&").replace("\"", ""), f + "next", aVar);
        } catch (Exception e) {
        }
    }

    public static void c(Context context, com.yaya.sdk.listener.a aVar) {
        com.yaya.sdk.bean.req.a aVar2 = new com.yaya.sdk.bean.req.a();
        aVar2.b(k.a(context));
        aVar2.c(Constants.APP_ID);
        aVar2.a(Integer.valueOf(1));
        aVar2.a(Constants.VERSION_NAME);
        aVar2.d(h.a(context));
        aVar2.e(com.yaya.sdk.bean.req.a.a);
        try {
            a(context, f.a(aVar2).replace(":", "=").replace("{", "").replace("}", "").replace(",", "&&").replace("\"", ""), f + "action", aVar);
        } catch (Exception e) {
        }
    }

    public static void a(Context context, com.yaya.sdk.bean.resp.d dVar, com.yaya.sdk.listener.a aVar) {
        try {
            a(context, ("requestId=" + dVar.c()).replace(":", "=").replace("{", "").replace("}", "").replace(",", "&&").replace("\"", ""), f + "hello", aVar);
        } catch (Exception e) {
        }
    }
}
