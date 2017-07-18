package com.yaya.sdk.core;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import com.yaya.sdk.constants.Constants;
import com.yaya.sdk.down.c;
import com.yaya.sdk.http.a;
import com.yaya.sdk.utils.f;

public class b {
    private static String a = b.class.getSimpleName();
    private static final int b = 10001;
    private static final int c = 10002;
    private static Handler d = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case b.b /*10001*/:
                    msg.getData().getInt("filesize");
                    msg.getData().getInt("currentSize");
                    return;
                case b.c /*10002*/:
                    final Context context = Constants.GLOABLE_CONTEXT;
                    if (context != null) {
                        a.a(context, new com.yaya.sdk.listener.a(this) {
                            final /* synthetic */ AnonymousClass1 a;

                            public void a(String str, int i) {
                                a.a(context, f.b(str));
                            }

                            public void b(String str, int i) {
                            }
                        }, (com.yaya.sdk.bean.req.f) msg.obj);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    };

    class AnonymousClass2 implements Runnable {
        private final /* synthetic */ Context a;
        private final /* synthetic */ String b;
        private final /* synthetic */ String c;
        private final /* synthetic */ String d;

        AnonymousClass2(Context context, String str, String str2, String str3) {
            this.a = context;
            this.b = str;
            this.c = str2;
            this.d = str3;
        }

        public void run() {
            c cVar = new c(this.a, this.b, this.c, this.d, 4);
            final int b = cVar.b();
            try {
                cVar.a(new com.yaya.sdk.down.a(this) {
                    final /* synthetic */ AnonymousClass2 a;

                    public void a(int i) {
                        Message message = new Message();
                        message.what = b.b;
                        message.getData().putInt("currentSize", i);
                        message.getData().putInt("filesize", b);
                        b.d.sendMessage(message);
                    }
                });
            } catch (Exception e) {
                b.d.obtainMessage(-1).sendToTarget();
            }
        }
    }

    public static void a(int i, Context context, String str, Integer num) {
        com.yaya.sdk.bean.req.f fVar = new com.yaya.sdk.bean.req.f();
        fVar.a(num);
        fVar.b(Integer.valueOf(i));
        fVar.a(str);
        Message message = new Message();
        message.obj = fVar;
        message.what = c;
        d.sendMessageDelayed(message, 15000);
    }

    public static void a(String str, String str2, String str3) {
        Context context = Constants.GLOABLE_CONTEXT;
        if (context != null) {
            new Thread(new AnonymousClass2(context, str, str2, str3)).start();
        }
    }

    public static String a() {
        if (Environment.getExternalStorageState().equals("unmounted")) {
            return new StringBuilder(String.valueOf(Environment.getDataDirectory().toString())).append("/yaya/jb").toString();
        }
        if (Environment.getExternalStorageState().equals("mounted")) {
            return new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().toString())).append("/yaya/jb").toString();
        }
        return "";
    }
}
