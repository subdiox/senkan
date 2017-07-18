package com.kayac.lobi.sdk.rec.a;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.kayac.lobi.libnakamap.components.PathRouter;
import com.kayac.lobi.sdk.path.PathRouterConfig;
import com.kayac.lobi.sdk.rec.activity.RecPlayActivity;

class c implements OnClickListener {
    final /* synthetic */ String a;
    final /* synthetic */ a b;

    c(a aVar, String str) {
        this.b = aVar;
        this.a = str;
    }

    public void onClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putString(PathRouter.PATH, PathRouterConfig.PATH_REC_SDK_DEFAULT);
        bundle.putString(RecPlayActivity.EXTRA_USER_UID, this.a);
        bundle.putBoolean(RecPlayActivity.EXTRA_CAN_GO_BACK_TO_ACTIVITY, true);
        PathRouter.startPath(bundle);
    }
}
