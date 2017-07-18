package cn.sharesdk.framework;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.webkit.SslErrorHandler;

class k implements OnClickListener {
    final /* synthetic */ SslErrorHandler a;
    final /* synthetic */ i b;

    k(i iVar, SslErrorHandler sslErrorHandler) {
        this.b = iVar;
        this.a = sslErrorHandler;
    }

    public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
        this.a.cancel();
    }
}
