package com.kayac.lobi.libnakamap.components;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.kayac.lobi.libnakamap.PathRoutedActivity;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.net.APIRes.GetSignupMessage;
import com.kayac.lobi.libnakamap.net.CoreAPI;
import com.kayac.lobi.libnakamap.net.CoreAPI.DefaultAPICallback;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.sdk.R;
import com.kayac.lobi.sdk.auth.AccountUtil;
import java.util.HashMap;
import java.util.Map;

public class LoginEntranceDialog extends DialogFragment {
    public static final String ARGUMENTS_LABEL_NG = "label_ng";
    public static final String ARGUMENTS_LABEL_OK = "label_ok";
    public static final String ARGUMENTS_MESSAGE = "message";
    public static final String ARGUMENTS_TITLE = "title";
    public static final String ARGUMENTS_TRACKING_ID = "tracking_id";

    public static class Type {
        public static final int ADD_COMMENT = 3;
        public static final int DEFAULT = 0;
        public static final int FOLLOW_BUTTON = 1;
        public static final int FOLLOW_LIST_MENU = 2;
    }

    public static LoginEntranceDialog newInstance(String message, String title, String labelOK, String labelNG, int trackingId) {
        LoginEntranceDialog dialog = new LoginEntranceDialog();
        Bundle arguments = new Bundle();
        arguments.putString("message", message);
        arguments.putString(ARGUMENTS_TITLE, title);
        arguments.putString(ARGUMENTS_LABEL_OK, labelOK);
        arguments.putString(ARGUMENTS_LABEL_NG, labelNG);
        arguments.putInt(ARGUMENTS_TRACKING_ID, trackingId);
        dialog.setArguments(arguments);
        return dialog;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context activity = getActivity();
        if (activity == null) {
            return super.onCreateDialog(savedInstanceState);
        }
        String message = getArguments().getString("message");
        String title = getArguments().getString(ARGUMENTS_TITLE);
        String labelOK = getArguments().getString(ARGUMENTS_LABEL_OK);
        String labelNG = getArguments().getString(ARGUMENTS_LABEL_NG);
        final int trackingId = getArguments().getInt(ARGUMENTS_TRACKING_ID);
        if (TextUtils.isEmpty(message)) {
            message = getString(R.string.lobisdk_default_login_entrance_message);
        }
        if (TextUtils.isEmpty(title)) {
            title = getString(R.string.lobisdk_default_login_entrance_title);
        }
        if (TextUtils.isEmpty(labelOK)) {
            labelOK = getString(R.string.lobi_ok);
        }
        if (TextUtils.isEmpty(labelNG)) {
            labelNG = getString(R.string.lobi_later);
        }
        View content = LayoutInflater.from(activity).inflate(R.layout.lobisdk_terms_of_use_content, null);
        ((TextView) content.findViewById(R.id.lobi_custom_dialog_content_text)).setText(message);
        Dialog dialog = new CustomDialog(activity, content);
        dialog.setTitle(title);
        dialog.setPositiveButton(labelOK, new OnClickListener() {
            public void onClick(View v) {
                LoginEntranceDialog.this.dismiss();
                AccountUtil.bindToLobiAccount(trackingId);
            }
        });
        dialog.setCancelButton(labelNG, new OnClickListener() {
            public void onClick(View v) {
                LoginEntranceDialog.this.dismiss();
            }
        });
        return dialog;
    }

    public static void start(final PathRoutedActivity activity, final String tag, int type, final int trackingId) {
        activity.startLoading();
        loadSignupMessage(type, new DefaultAPICallback<GetSignupMessage>(null) {
            public void onResponse(GetSignupMessage t) {
                super.onResponse(t);
                activity.stopLoading();
                LoginEntranceDialog.show(activity, tag, t.message, t.title, t.labelOK, t.labelNG, trackingId);
            }

            public void onError(int statusCode, String responseBody) {
                activity.stopLoading();
                LoginEntranceDialog.show(activity, tag, trackingId);
            }

            public void onError(Throwable e) {
                activity.stopLoading();
                LoginEntranceDialog.show(activity, tag, trackingId);
            }
        });
    }

    private static void show(FragmentActivity activity, String tag, int trackingId) {
        show(activity, tag, null, null, null, null, trackingId);
    }

    private static void show(FragmentActivity activity, String tag, String message, String title, String labelOK, String labelNG, int trackingId) {
        final FragmentActivity fragmentActivity = activity;
        final String str = message;
        final String str2 = title;
        final String str3 = labelOK;
        final String str4 = labelNG;
        final int i = trackingId;
        final String str5 = tag;
        activity.runOnUiThread(new Runnable() {
            public void run() {
                if (!fragmentActivity.isFinishing()) {
                    FragmentTransaction transaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
                    transaction.add(LoginEntranceDialog.newInstance(str, str2, str3, str4, i), str5);
                    transaction.commitAllowingStateLoss();
                }
            }
        });
    }

    private static void loadSignupMessage(int type, DefaultAPICallback<GetSignupMessage> listener) {
        UserValue user = AccountDatastore.optCurrentUser();
        if (user == null) {
            listener.onError(new Throwable("current user not set."));
            return;
        }
        Map<String, String> params = new HashMap();
        params.put("token", user.getToken());
        params.put("type", Integer.toString(type));
        CoreAPI.getSignupMessage(params, listener);
    }
}
