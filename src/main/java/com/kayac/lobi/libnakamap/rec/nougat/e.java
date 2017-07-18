package com.kayac.lobi.libnakamap.rec.nougat;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import com.kayac.lobi.libnakamap.components.CustomDialog;

class e implements OnClickListener {
    final /* synthetic */ CustomDialog a;
    final /* synthetic */ a b;

    e(a aVar, CustomDialog customDialog) {
        this.b = aVar;
        this.a = customDialog;
    }

    public void onClick(View view) {
        this.a.dismiss();
        try {
            this.b.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.kayac.nakamap")));
        } catch (ActivityNotFoundException e) {
            this.b.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.kayac.nakamap")));
        }
    }
}
