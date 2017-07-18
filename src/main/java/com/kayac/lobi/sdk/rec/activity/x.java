package com.kayac.lobi.sdk.rec.activity;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.EditText;
import com.kayac.lobi.libnakamap.rec.c.f;

class x implements OnClickListener {
    final /* synthetic */ EditText a;
    final /* synthetic */ w b;

    x(w wVar, EditText editText) {
        this.b = wVar;
        this.a = editText;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        String obj = this.a.getText().toString();
        this.b.b.showIndicator();
        f.a(this.b.a, obj, new y(this));
    }
}
