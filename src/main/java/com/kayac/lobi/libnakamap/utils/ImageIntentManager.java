package com.kayac.lobi.libnakamap.utils;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import com.kayac.lobi.libnakamap.components.SelectPictureMenuDialog;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastore;
import com.kayac.lobi.libnakamap.net.CoreAPI;
import com.kayac.lobi.libnakamap.utils.PictureUtil.Res;
import com.kayac.lobi.sdk.LobiCore;
import com.kayac.lobi.sdk.R;
import com.kayac.lobi.sdk.utils.ManifestUtil;
import java.io.File;

public class ImageIntentManager extends BroadcastReceiver {
    public static final int PICK_PICTURE = 20002;
    public static final int TAKE_PHOTO = 20001;
    private Activity mActivity;
    private File mFile = null;

    public ImageIntentManager(Activity activity) {
        this.mActivity = activity;
    }

    public void onReceive(Context context, Intent intent) {
        Log.w("lobi-sdk", "onReceive: " + this.mActivity, new RuntimeException());
        String action = intent.getAction();
        Bundle bundle = intent.getExtras();
        Log.v("nakamap", "[picture] onReceive: " + action + " " + bundle);
        if (SelectPictureMenuDialog.ACTION_SELECTED.equals(action)) {
            int type = bundle.getInt(SelectPictureMenuDialog.EXTRA_TYPE, -1);
            DebugAssert.assertTrue(type != -1);
            if (type == R.id.lobi_select_picture_menu_take_photo) {
                if (isCameraAvailable() && !requestCameraPermissionIfNeeded(this.mActivity)) {
                    final File output = PictureUtil.getPreferablePlaceForSavingCameraPictures(context);
                    output.deleteOnExit();
                    final String pictureKey = bundle.getString(SelectPictureMenuDialog.EXTRA_PICTURE_KEY);
                    CoreAPI.getExecutorService().execute(new Runnable() {
                        public void run() {
                            TransactionDatastore.setKKValue(com.kayac.lobi.libnakamap.datastore.TransactionDDL.KKey.ImageIntentManager.KEY1, com.kayac.lobi.libnakamap.datastore.TransactionDDL.KKey.ImageIntentManager.LAST_PICTURE_KEY, pictureKey);
                            TransactionDatastore.setKKValue(com.kayac.lobi.libnakamap.datastore.TransactionDDL.KKey.ImageIntentManager.PICTURE_OUTPUT_PATH, pictureKey, output.getAbsolutePath());
                            Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
                            i.putExtra("output", Uri.fromFile(output));
                            i.putExtra("android.intent.extra.videoQuality", 1);
                            ImageIntentManager.this.mActivity.startActivityForResult(i, 20001);
                        }
                    });
                }
            } else if (type == R.id.lobi_select_picture_menu_select_photo) {
                if (isGalleryAvailable()) {
                    Intent i = new Intent("android.intent.action.GET_CONTENT");
                    i.setType("image/*");
                    this.mActivity.startActivityForResult(i, 20002);
                }
            } else if (type == R.id.lobi_select_picture_menu_detach_photo) {
                setFile(null);
            } else {
                DebugAssert.fail();
            }
        }
    }

    public Res onActivityResult(int requestCode, int resultCode, Intent data) {
        Res res;
        if (requestCode == 20001) {
            if (resultCode == -1) {
                res = PictureUtil.onActivityResultPictureTakenHelper(this.mActivity, data, retreivePictureOutputPath(), 640, 0, 640);
                if (res.getRaw() == null || res.getThumb() == null) {
                    return res;
                }
                setFile(res.getRaw());
                return res;
            }
        } else if (requestCode == 20002 && resultCode == -1) {
            res = PictureUtil.onActivityResultPictureSelectedHelper(this.mActivity, data, 640, 0, 640);
            if (res.getRaw() == null || res.getThumb() == null) {
                return res;
            }
            setFile(res.getRaw());
            return res;
        }
        return null;
    }

    public static final Uri retreivePictureOutputPath() {
        String pictureKey = (String) TransactionDatastore.getKKValue(com.kayac.lobi.libnakamap.datastore.TransactionDDL.KKey.ImageIntentManager.KEY1, com.kayac.lobi.libnakamap.datastore.TransactionDDL.KKey.ImageIntentManager.LAST_PICTURE_KEY, null);
        DebugAssert.assertNotNull(pictureKey);
        String output = (String) TransactionDatastore.getKKValue(com.kayac.lobi.libnakamap.datastore.TransactionDDL.KKey.ImageIntentManager.PICTURE_OUTPUT_PATH, pictureKey, null);
        DebugAssert.assertNotNull(output);
        return Uri.parse(output);
    }

    public void onDestroy() {
        this.mActivity = null;
    }

    public synchronized void setFile(File file) {
        this.mFile = file;
    }

    public synchronized File getFile() {
        return this.mFile;
    }

    private static boolean requestCameraPermissionIfNeeded(Activity activity) {
        String permission = "android.permission.CAMERA";
        if (activity.getApplicationInfo().targetSdkVersion < 23 || VERSION.SDK_INT < 23 || !ManifestUtil.hasPermission(activity, "android.permission.CAMERA") || PermissionChecker.checkSelfPermission(activity, "android.permission.CAMERA") == 0) {
            return false;
        }
        ActivityCompat.requestPermissions(activity, new String[]{"android.permission.CAMERA"}, 0);
        return true;
    }

    public static boolean isCameraAvailable() {
        if (LobiCore.sharedInstance().getContext().getPackageManager().queryIntentActivities(new Intent("android.media.action.IMAGE_CAPTURE", null), 0).size() > 0) {
            return true;
        }
        return false;
    }

    public static boolean isGalleryAvailable() {
        PackageManager pm = LobiCore.sharedInstance().getContext().getPackageManager();
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        if (pm.queryIntentActivities(intent, 0).size() > 0) {
            return true;
        }
        return false;
    }
}
