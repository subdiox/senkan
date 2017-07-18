package com.kayac.lobi.sdk.rec.activity;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.EditText;
import com.kayac.lobi.sdk.LobiCoreAPI;

class d implements OnClickListener {
    final /* synthetic */ EditText a;
    final /* synthetic */ RecPlayActivity b;

    d(RecPlayActivity recPlayActivity, EditText editText) {
        this.b = recPlayActivity;
        this.a = editText;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        String obj = this.a.getText().toString();
        this.b.showIndicator();
        LobiCoreAPI.updateUserName(obj, new e(this));
    }
}
