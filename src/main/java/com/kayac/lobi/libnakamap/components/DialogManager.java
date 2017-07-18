package com.kayac.lobi.libnakamap.components;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.kayac.lobi.sdk.R;
import java.util.HashMap;
import java.util.Map;

public class DialogManager {
    private static final String DIALOG_KEY = "PROGRESS_DIALOG";
    private final Map<String, Dialog> mDialogs = new HashMap();
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    public void dismiss(String key) {
        Dialog dialog = (Dialog) this.mDialogs.remove(key);
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public void show(String key, Dialog dialog) {
        this.mDialogs.put(key, dialog);
        dialog.show();
    }

    public void onDestroy() {
        for (Dialog d : this.mDialogs.values()) {
            d.dismiss();
        }
    }

    public void showLoadingDialog(Activity activity) {
        if (activity == null) {
            throw new IllegalArgumentException("showProgressDialog() param activity null.");
        }
        dismiss(DIALOG_KEY);
        CustomProgressDialog dialog = new CustomProgressDialog(activity);
        dialog.setCancelable(true);
        dialog.setMessage(activity.getString(R.string.lobi_loading_loading));
        show(DIALOG_KEY, dialog);
    }

    public final void cancelLoadingDialog() {
        this.mHandler.post(new Runnable() {
            public void run() {
                DialogManager.this.dismiss(DialogManager.DIALOG_KEY);
            }
        });
    }

    public static CustomProgressDialog showProgressDialog(Context context) {
        CustomProgressDialog progressDialog = new CustomProgressDialog(context);
        progressDialog.setMessage(context.getString(R.string.lobi_loading_loading));
        progressDialog.show();
        return progressDialog;
    }
}
