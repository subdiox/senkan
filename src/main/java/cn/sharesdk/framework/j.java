package cn.sharesdk.framework;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.webkit.SslErrorHandler;
import java.lang.reflect.Method;

class j implements OnClickListener {
    final /* synthetic */ SslErrorHandler a;
    final /* synthetic */ i b;

    j(i iVar, SslErrorHandler sslErrorHandler) {
        this.b = iVar;
        this.a = sslErrorHandler;
    }

    public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
        try {
            Method method = this.a.getClass().getMethod("proceed", new Class[0]);
            method.setAccessible(true);
            method.invoke(this.a, new Object[0]);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
