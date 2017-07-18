package com.kayac.lobi.sdk.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.net.Uri.Builder;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.utils.Log;
import com.kayac.lobi.sdk.R;
import com.kayac.lobi.sdk.activity.RootActivity;
import com.kayac.lobi.sdk.exception.NakamapIllegalStateException;
import java.util.List;

public class ManifestUtil {
    private static final String SIGNATURE = "308201e730820150a00302010202044cd7f274300d06092a864886f70d01010505003037310e300c060355040a13054b41594143310c300a060355040b1303496e63311730150603550403130e5461726f204b6f626179617368693020170d3130313130383132353230345a180f33303130303331313132353230345a3037310e300c060355040a13054b41594143310c300a060355040b1303496e63311730150603550403130e5461726f204b6f6261796173686930819f300d06092a864886f70d010101050003818d003081890281810090209f3b882abe7f9a145ec63acbcb86dee40e15f14328b6593e98b50aa544346d918af03a401b248fd7a94f980c934636fe0f08ace7bfc9b8584bbbcf58c8056b51c17d06d77b0145674b0693a2c928e325e775cd62d5aff1eba0b908c4db0e86b99967a4238c97add47a439360889e24c01cdae2343d36d9698f634f913bad0203010001300d06092a864886f70d0101050500038181007147f2bc328634ecff20476c4cba572958b09099da4d82d6dc20aa4869bbca7fccf8231fb092c24457a2d8a16c75020984fbade9d7eea59be6a9bfdf39926e12670ecde475ec6b2cc6ac0ace84558a455a3b8f3801e1d86928bc3cb2f2b79d6d869cbe85598c8f9c11b8a63e4d9d05d93861bf780a14268c50e5629c32abe921";

    public static boolean checkIntentFilter(Context context, String clientID) {
        for (ResolveInfo info : context.getPackageManager().queryIntentActivities(new Intent("android.intent.action.VIEW", new Builder().scheme("nakamapapp-" + clientID).build()), 0)) {
            ActivityInfo activityInfo = info.activityInfo;
            if (context.getPackageName().equals(activityInfo.packageName) && RootActivity.class.getCanonicalName().equals(activityInfo.name)) {
                return true;
            }
        }
        if (false) {
            return false;
        }
        throw new NakamapIllegalStateException(context.getResources().getString(R.string.lobisdk_proper_scheme_is_not_defined, new Object[]{clientID}));
    }

    public static void checkNativeApp(Context context) {
        if (!((Boolean) AccountDatastore.getValue("ssoBound", Boolean.FALSE)).booleanValue()) {
            PackageManager pm = context.getPackageManager();
            boolean isNakamapInstalled = checkIfNakamapNativeAppInstalled(pm);
            AccountDatastore.setValue("nativeNakamapAppInstalled", Boolean.valueOf(isNakamapInstalled));
            if (isNakamapInstalled) {
                List<ResolveInfo> activities = pm.queryIntentActivities(new Intent("android.intent.action.VIEW").setData(Uri.parse("nakamap-sso://bind")), 0);
                if (activities != null) {
                    for (ResolveInfo info : activities) {
                        if ("com.kayac.lobi".equals(info.activityInfo.packageName)) {
                            AccountDatastore.setValue("nativeNakamapAppSsoSupported", Boolean.valueOf(true));
                            return;
                        }
                    }
                }
            }
        }
    }

    public static boolean checkIfNakamapNativeAppInstalled(PackageManager pm) {
        try {
            PackageInfo packageInfo = pm.getPackageInfo("com.kayac.nakamap", 64);
            if (packageInfo.signatures == null || packageInfo.signatures.length != 1) {
                return false;
            }
            String signature = packageInfo.signatures[0].toCharsString();
            Log.v("nakamap-sdk", "signature: " + signature);
            boolean signatureMatches = SIGNATURE.equals(signature);
            Log.v("nakamap-sdk", "signature matches: " + signatureMatches);
            return signatureMatches;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    public static boolean hasWriteExternalStoragePermission(Context context) {
        return hasPermission(context, "android.permission.WRITE_EXTERNAL_STORAGE");
    }

    public static boolean hasPermission(Context context, String checkPermission) {
        if (checkPermission == null || context == null) {
            return false;
        }
        String[] requestedPermissions = getPermissionList(context);
        if (requestedPermissions == null) {
            return false;
        }
        for (String str : requestedPermissions) {
            if (str.equals(checkPermission)) {
                return true;
            }
        }
        return false;
    }

    public static String[] getPermissionList(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 4096).requestedPermissions;
        } catch (NameNotFoundException e) {
            return null;
        }
    }
}
