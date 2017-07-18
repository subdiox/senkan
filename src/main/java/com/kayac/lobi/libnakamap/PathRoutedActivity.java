package com.kayac.lobi.libnakamap;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;
import com.google.android.gcm.GCMConstants;
import com.kayac.lobi.libnakamap.components.CustomProgressDialog;
import com.kayac.lobi.libnakamap.components.PathRouter;
import com.kayac.lobi.libnakamap.components.PathRouterEventReceiver;
import com.kayac.lobi.sdk.LobiCore;
import com.kayac.lobi.sdk.R;
import org.json.JSONArray;
import org.json.JSONObject;

public class PathRoutedActivity extends FragmentActivity {
    private static final String FRAGMENT_TAG_PROGRESS = "progress";
    private PathRouterEventReceiver mPathRouterEventReceiver;

    public static class ProgressDialogFragment extends DialogFragment {
        private boolean mIsForeground;
        private boolean mWillDismiss;

        public static ProgressDialogFragment newInstance() {
            return new ProgressDialogFragment();
        }

        public Dialog onCreateDialog(Bundle savedInstanceState) {
            FragmentActivity activity = getActivity();
            CustomProgressDialog dialog = new CustomProgressDialog(activity);
            dialog.setMessage(activity.getString(R.string.lobi_loading_loading));
            return dialog;
        }

        public void onPause() {
            super.onPause();
            this.mIsForeground = false;
        }

        public void onResume() {
            super.onResume();
            this.mIsForeground = true;
            if (this.mWillDismiss) {
                dismiss();
            }
        }

        public void stopLoading() {
            if (this.mIsForeground) {
                dismiss();
            } else {
                this.mWillDismiss = true;
            }
        }
    }

    protected void onCreate(Bundle savedInstance) {
        onCreate(savedInstance, null);
    }

    protected void onCreate(Bundle savedInstance, PathRouterEventReceiver pathRouterEventReceiver) {
        super.onCreate(savedInstance);
        LobiCore.setup(this);
        this.mPathRouterEventReceiver = pathRouterEventReceiver;
        if (this.mPathRouterEventReceiver == null) {
            this.mPathRouterEventReceiver = new PathRouterEventReceiver(this);
        }
        this.mPathRouterEventReceiver.onCreate(savedInstance);
    }

    protected void onDestroy() {
        this.mPathRouterEventReceiver.onDestroy();
        super.onDestroy();
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        PathRouter.saveInstanceState(outState);
    }

    protected void showAPIError(JSONObject response) {
        if (response == null) {
            showNetworkError();
        } else {
            showResponseError(response);
        }
    }

    protected void showNetworkError() {
        final Context context = this;
        final String msg = getString(R.string.lobisdk_network_disconnected);
        runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(context, msg, 0).show();
            }
        });
    }

    public void showResponseError(JSONObject response) {
        if (response == null) {
            showNetworkError();
            return;
        }
        JSONArray errors = response.optJSONArray(GCMConstants.EXTRA_ERROR);
        if (errors == null) {
            showNetworkError();
            return;
        }
        final String error = errors.optString(0, null);
        final Context context = this;
        runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(context, error != null ? error : "unknown error.", 0).show();
            }
        });
    }

    public void startLoading() {
        runOnUiThread(new Runnable() {
            public void run() {
                if (!PathRoutedActivity.this.isFinishing()) {
                    Fragment dialog = ProgressDialogFragment.newInstance();
                    FragmentTransaction transaction = PathRoutedActivity.this.getSupportFragmentManager().beginTransaction();
                    transaction.add(dialog, "progress");
                    transaction.commitAllowingStateLoss();
                }
            }
        });
    }

    public void stopLoading() {
        runOnUiThread(new Runnable() {
            public void run() {
                if (!PathRoutedActivity.this.isFinishing()) {
                    FragmentManager fm = PathRoutedActivity.this.getSupportFragmentManager();
                    fm.executePendingTransactions();
                    ProgressDialogFragment dialog = (ProgressDialogFragment) fm.findFragmentByTag("progress");
                    if (dialog != null) {
                        dialog.stopLoading();
                    }
                }
            }
        });
    }
}
