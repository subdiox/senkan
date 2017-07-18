package com.kayac.lobi.sdk.rec.activity;

import android.view.View;
import android.view.View.OnClickListener;

class o implements OnClickListener {
    final /* synthetic */ RecPlayActivity a;

    o(RecPlayActivity recPlayActivity) {
        this.a = recPlayActivity;
    }

    public void onClick(View view) {
        if (this.a.canGoBack()) {
            this.a.goBack();
        } else if (this.a.mCanGoBackToActivity) {
            this.a.finish();
        } else {
            this.a.mDrawerLayout.openDrawer(8388611);
        }
    }
}
