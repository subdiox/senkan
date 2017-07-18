package com.kayac.lobi.sdk.rec.activity;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import com.kayac.lobi.sdk.rec.R;

final class u implements Runnable {
    final /* synthetic */ Activity a;

    u(Activity activity) {
        this.a = activity;
    }

    public void run() {
        CharSequence string = this.a.getString(R.string.lobirec_connection_error);
        Builder builder = new Builder(this.a);
        builder.setMessage(string);
        builder.setCancelable(false);
        builder.setNegativeButton(17039370, new v(this));
    }
}
