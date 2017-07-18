package com.kayac.lobi.libnakamap.net;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import cn.sharesdk.framework.i;
import com.kayac.lobi.libnakamap.PathRoutedActivity;
import com.kayac.lobi.libnakamap.components.CustomDialog;
import com.kayac.lobi.sdk.R;

public class LobiWebViewClient extends WebViewClient {
    private static final String FRAGMENT_TAG_ERROR = "error";
    private boolean mHasError;
    private boolean mLoading = false;

    public static class ErrorDialogFragment extends DialogFragment {
        private static final String ARGUMENTS_MESSAGE = "MESSAGE";
        private static final String ARGUMENTS_VIEW_ID = "VIEW_ID";
        private String mMessage;
        private int mViewId;

        public static ErrorDialogFragment newInstance(int viewId, String message) {
            ErrorDialogFragment dialog = new ErrorDialogFragment();
            Bundle arguments = new Bundle();
            arguments.putInt(ARGUMENTS_VIEW_ID, viewId);
            arguments.putString(ARGUMENTS_MESSAGE, message);
            dialog.setArguments(arguments);
            return dialog;
        }

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Bundle arguments = getArguments();
            this.mViewId = arguments.getInt(ARGUMENTS_VIEW_ID);
            this.mMessage = arguments.getString(ARGUMENTS_MESSAGE);
        }

        public Dialog onCreateDialog(Bundle savedInstanceState) {
            setCancelable(false);
            final Activity activity = getActivity();
            final CustomDialog dialog = CustomDialog.createTextDialog(activity, this.mMessage);
            dialog.setCancelable(false);
            dialog.setPositiveButton(activity.getString(R.string.lobisdk_retry), new OnClickListener() {
                public void onClick(View v) {
                    dialog.dismiss();
                    WebView webView = (WebView) activity.findViewById(ErrorDialogFragment.this.mViewId);
                    if (webView != null) {
                        webView.reload();
                    }
                }
            });
            dialog.setNegativeButton(activity.getString(R.string.lobi_ok), new OnClickListener() {
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            return dialog;
        }
    }

    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if (!this.mLoading) {
            this.mLoading = true;
            this.mHasError = false;
            ((PathRoutedActivity) view.getContext()).startLoading();
        }
    }

    public void onPageFinished(WebView view, String url) {
        int i = 0;
        super.onPageFinished(view, url);
        if (this.mLoading) {
            this.mLoading = false;
            PathRoutedActivity activity = (PathRoutedActivity) view.getContext();
            if (this.mHasError) {
                i = 4;
            }
            view.setVisibility(i);
            activity.stopLoading();
        }
    }

    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        String msg;
        if (this.mLoading) {
            this.mHasError = true;
        }
        PathRoutedActivity activity = (PathRoutedActivity) view.getContext();
        int resId = 0;
        switch (errorCode) {
            case i.ERROR_TIMEOUT /*-8*/:
            case i.ERROR_CONNECT /*-6*/:
            case -2:
                resId = R.string.lobisdk_network_disconnected;
                break;
        }
        if (resId > 0) {
            msg = activity.getResources().getString(resId);
        } else {
            msg = description;
        }
        showErrorDialog(activity, msg, view);
    }

    public void showErrorDialog(PathRoutedActivity activity, String msg, WebView view) {
        if (!activity.isFinishing()) {
            Fragment dialog = ErrorDialogFragment.newInstance(view.getId(), msg);
            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
            transaction.add(dialog, "error");
            transaction.commitAllowingStateLoss();
        }
    }
}
