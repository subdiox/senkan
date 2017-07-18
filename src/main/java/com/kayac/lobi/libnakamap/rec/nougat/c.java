package com.kayac.lobi.libnakamap.rec.nougat;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;
import android.os.Bundle;
import com.kayac.lobi.libnakamap.components.CustomDialog;
import com.kayac.lobi.libnakamap.components.PathRouter;
import com.kayac.lobi.libnakamap.rec.LobiRecAPI;
import com.kayac.lobi.libnakamap.rec.a.b;
import com.kayac.lobi.sdk.LobiCore;
import com.kayac.lobi.sdk.activity.RootActivity;
import com.kayac.lobi.sdk.path.PathRouterConfig;
import com.kayac.lobi.sdk.rec.LobiRecModule;
import com.kayac.lobi.sdk.rec.R;
import com.kayac.lobi.sdk.rec.activity.RecPlayActivity;
import com.kayac.lobi.sdk.rec.activity.RecPostVideoActivity;

public class c {
    private static Activity a;
    private static g b;
    private static boolean c = false;

    private static class a extends DialogFragment {
        private a() {
        }

        public Dialog onCreateDialog(Bundle bundle) {
            Dialog createTextDialog = CustomDialog.createTextDialog(getActivity(), getString(R.string.lobirec_nougat_note_enable_rec));
            createTextDialog.setPositiveButton(getString(R.string.lobi_download), new e(this, createTextDialog));
            createTextDialog.setNegativeButton(getString(R.string.lobirec_close), new f(this, createTextDialog));
            return createTextDialog;
        }
    }

    public static void a(Activity activity) {
        if (a == null) {
            LobiCore.setup(activity);
            a = activity;
            b = new g(a);
        }
    }

    public static void a(boolean z) {
        if (!j()) {
            c = z;
        }
    }

    public static boolean a() {
        return VERSION.SDK_INT >= 24;
    }

    public static boolean a(String str) {
        if (j()) {
            return false;
        }
        LobiCore.assertSetup();
        Bundle bundle = new Bundle();
        bundle.putString(RecPlayActivity.EXTRA_EVENT_FIELDS, str);
        if (PathRouter.getLastPath() == null) {
            RootActivity.startActivity(PathRouterConfig.PATH_REC_SDK_DEFAULT, bundle);
        } else {
            bundle.putString(PathRouter.PATH, PathRouterConfig.PATH_REC_SDK_DEFAULT);
            PathRouter.startPath(bundle, 65536);
        }
        return true;
    }

    public static boolean a(String str, String str2, long j, String str3) {
        return a(str, str2, j, str3, "");
    }

    public static boolean a(String str, String str2, long j, String str3, String str4) {
        if (!l()) {
            b.a("LobiRecSDK", "There are no videos to post.");
            RecPostVideoActivity.sendFinishBroadcast(LobiCore.sharedInstance().getContext());
            return false;
        } else if (j()) {
            return false;
        } else {
            Intent intent = new Intent(a, RecPostVideoActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("EXTRA_TITLE", str);
            bundle.putString(RecPostVideoActivity.EXTRA_DESCRIPTION, str2);
            bundle.putLong(RecPostVideoActivity.EXTRA_SCORE, j);
            bundle.putString(RecPostVideoActivity.EXTRA_CATEGORY, str3);
            bundle.putString(RecPostVideoActivity.EXTRA_METADATA, str4);
            bundle.putBoolean(RecPostVideoActivity.EXTRA_MIC, true);
            bundle.putBoolean(RecPostVideoActivity.EXTRA_CAMERA, true);
            intent.putExtras(bundle);
            RootActivity.startActivity(LobiRecModule.PATH_POSTVIDEO, bundle);
            return true;
        }
    }

    public static boolean a(String str, String str2, boolean z, String str3) {
        if (j()) {
            return false;
        }
        LobiCore.assertSetup();
        Bundle bundle = new Bundle();
        bundle.putString(RecPlayActivity.EXTRA_USER_EXID, str);
        bundle.putString(RecPlayActivity.EXTRA_CATEGORY, str2);
        bundle.putBoolean(RecPlayActivity.EXTRA_LETSPLAY, z);
        bundle.putString(RecPlayActivity.EXTRA_META_JSON, str3);
        bundle.putString(RecPlayActivity.EXTRA_EVENT_FIELDS, null);
        RootActivity.startActivity(PathRouterConfig.PATH_REC_SDK_DEFAULT, bundle);
        return true;
    }

    public static boolean a(String str, boolean z) {
        if (j()) {
            return false;
        }
        LobiCore.assertSetup();
        Bundle bundle = new Bundle();
        bundle.putString(RecPlayActivity.EXTRA_VIDEO_UID, str);
        if (z) {
            bundle.putBoolean(RecPlayActivity.EXTRA_CAN_GO_BACK_TO_ACTIVITY, z);
            bundle.putString(PathRouter.PATH, PathRouterConfig.PATH_REC_SDK_DEFAULT.equals(PathRouter.getLastPath()) ? RecPlayActivity.PATH_PLAY_LIST_VIDEO : PathRouterConfig.PATH_REC_SDK_DEFAULT);
            PathRouter.startPath(bundle);
        } else {
            bundle.putString(RecPlayActivity.EXTRA_EVENT_FIELDS, null);
            RootActivity.startActivity(PathRouterConfig.PATH_REC_SDK_DEFAULT, bundle);
        }
        return true;
    }

    public static void b() {
        if (b != null) {
            b.a(a);
        }
    }

    public static boolean b(String str) {
        return a(str, false);
    }

    public static void c() {
        if (b != null) {
            b.a();
        }
    }

    public static void d() {
        if (b != null) {
            b.b();
        }
    }

    public static void e() {
        if (b != null) {
            b.c();
        }
    }

    public static void f() {
        if (b != null) {
            b.e();
        }
    }

    public static void g() {
        if (b != null) {
            b.f();
        }
    }

    public static boolean h() {
        return b != null && b.i();
    }

    public static boolean i() {
        return b != null && b.h();
    }

    public static boolean j() {
        return b != null && b.j();
    }

    public static boolean k() {
        try {
            return a.getPackageManager().getPackageInfo("com.kayac.nakamap", 128).versionCode >= 247;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    public static boolean l() {
        return LobiRecAPI.hasMovie();
    }

    public static boolean m() {
        boolean f;
        com.kayac.lobi.libnakamap.rec.b.a a = com.kayac.lobi.libnakamap.rec.b.a.a();
        boolean c = a.c();
        try {
            f = a.f();
        } catch (Throwable e) {
            b.a(e);
            f = false;
        }
        return c || f;
    }

    public static boolean n() {
        return c;
    }

    public static boolean o() {
        return a(null, null, false, null);
    }

    public static void p() {
        if (a != null) {
            new a().show(a.getFragmentManager(), "DownloadLobiDialogFragment");
        }
    }
}
