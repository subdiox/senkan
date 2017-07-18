package com.kayac.lobi.sdk.activity;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import com.kayac.lobi.libnakamap.PathRoutedActivity;
import com.kayac.lobi.libnakamap.components.CustomDialog;
import com.kayac.lobi.sdk.R;
import com.kayac.lobi.sdk.utils.ReferrerUtil;

public class AppDownloadActivity extends PathRoutedActivity {

    public static class AppDownloadDialogFragment extends DialogFragment {
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            setCancelable(false);
            final FragmentActivity activity = getActivity();
            final CustomDialog dialog = CustomDialog.createTextDialog(activity, getString(R.string.lobisdk_download_lobi));
            dialog.setPositiveButton(getString(R.string.lobi_ok), new OnClickListener() {
                public void onClick(View v) {
                    dialog.dismiss();
                    AppDownloadActivity.openNativeAppStoreUrl(activity);
                    activity.finish();
                }
            });
            dialog.setNegativeButton(getString(R.string.lobi_cancel), new OnClickListener() {
                public void onClick(View v) {
                    dialog.dismiss();
                    activity.finish();
                }
            });
            dialog.setCancelable(false);
            return dialog;
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomDialog.appendDialogFragment(this, new AppDownloadDialogFragment(), "download");
    }

    private static void openNativeAppStoreUrl(Context context) {
        try {
            context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(ReferrerUtil.createMarketUri())));
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }
}
