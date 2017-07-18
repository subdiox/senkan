package com.kayac.lobi.sdk.rec;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import com.kayac.lobi.libnakamap.components.PathRouter;
import com.kayac.lobi.libnakamap.value.ProfileValue;
import com.kayac.lobi.sdk.activity.RootActivity;
import com.kayac.lobi.sdk.path.PathRouterConfig;
import com.kayac.lobi.sdk.rec.a.a;
import com.kayac.lobi.sdk.rec.activity.RecPlayActivity;
import com.kayac.lobi.sdk.rec.activity.RecPostVideoActivity;

public class LobiRecModule {
    public static final String PATH_POSTVIDEO = "/postvideo";

    public static void getProfileView(ProfileValue profileValue, Fragment fragment, Context context, boolean z, String str) {
        a aVar = new a(profileValue, fragment, context, z, str);
    }

    public static boolean overrideRouting(RootActivity rootActivity, Intent intent) {
        return false;
    }

    public static void registerPath() {
        PathRouter.registerPath(PathRouterConfig.PATH_REC_SDK_DEFAULT, RecPlayActivity.class);
        PathRouter.registerPath(RecPlayActivity.PATH_PLAY_LIST_VIDEO, RecPlayActivity.class);
        PathRouter.registerPath(PATH_POSTVIDEO, RecPostVideoActivity.class);
    }

    public static String versionName() {
        return "5.2.39";
    }
}
