package com.kayac.lobi.sdk.rec.activity;

import android.app.AlertDialog.Builder;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import com.kayac.lobi.sdk.rec.R;

class w implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ RecPlayActivity b;

    w(RecPlayActivity recPlayActivity, String str) {
        this.b = recPlayActivity;
        this.a = str;
    }

    public void run() {
        View editText = new EditText(this.b);
        editText.setLayoutParams(new LayoutParams(-1, -2));
        editText.setMaxLines(1);
        Builder builder = new Builder(this.b);
        builder.setCancelable(true);
        builder.setTitle(this.b.getString(R.string.lobirec_accuse_title));
        builder.setView(editText);
        builder.setPositiveButton(this.b.getString(R.string.lobirec_accuse_ok), new x(this, editText));
        builder.setNegativeButton(this.b.getString(R.string.lobirec_accuse_cancel), new aa(this));
        builder.create().show();
    }
}
