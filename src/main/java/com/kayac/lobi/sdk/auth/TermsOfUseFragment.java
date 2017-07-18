package com.kayac.lobi.sdk.auth;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import com.kayac.lobi.libnakamap.components.CustomDialog;
import com.kayac.lobi.libnakamap.components.CustomProgressDialog;
import com.kayac.lobi.libnakamap.components.LobiFragment;
import com.kayac.lobi.libnakamap.components.PathRouter;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastore;
import com.kayac.lobi.libnakamap.net.APIRes.GetGroup;
import com.kayac.lobi.libnakamap.net.APIRes.GetTerms;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupJoinWithExId;
import com.kayac.lobi.libnakamap.net.CoreAPI;
import com.kayac.lobi.libnakamap.net.CoreAPI.DefaultAPICallback;
import com.kayac.lobi.libnakamap.utils.GroupValueUtils;
import com.kayac.lobi.libnakamap.utils.Log;
import com.kayac.lobi.libnakamap.utils.NakamapSDKDatastore.Key;
import com.kayac.lobi.libnakamap.value.GroupDetailValue;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.sdk.LobiCore;
import com.kayac.lobi.sdk.R;
import com.kayac.lobi.sdk.chat.activity.ChatActivity;
import com.kayac.lobi.sdk.chat.activity.ChatEditActivity;
import java.util.HashMap;
import java.util.Map;

public class TermsOfUseFragment extends LobiFragment {
    private Callback mCallback;
    private CustomDialog mTermsOfUseDialog;

    public static class Callback {
        public void onAccept() {
        }

        public void onDismiss() {
        }
    }

    public static TermsOfUseFragment newInstance() {
        Log.v("nakamap-sdk", "TermsOfUseFragment::newInstance()");
        return new TermsOfUseFragment();
    }

    public static Boolean hasAccepted() {
        return (Boolean) AccountDatastore.getValue(Key.HAS_ACCEPTED_TERMS_OF_USE, Boolean.FALSE);
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.v("nakamap-sdk", "onAttach");
        Boolean accepted = hasAccepted();
        Log.v("nakamap-sdk", "accepted terms of use: " + accepted);
        if (!accepted.booleanValue()) {
            getTermsAndShow(activity);
        }
    }

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    private void getTermsAndShow(final Activity activity) {
        CustomProgressDialog progress = new CustomProgressDialog(activity);
        progress.setMessage(activity.getString(R.string.lobi_loading_loading));
        progress.show();
        DefaultAPICallback<GetTerms> onGetTerms = new DefaultAPICallback<GetTerms>(activity) {
            public void onResponse(final GetTerms t) {
                super.onResponse(t);
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        if (!activity.isFinishing()) {
                            TermsOfUseFragment.this.showTerms(activity, t.terms);
                        }
                    }
                });
            }

            public void onError(int statusCode, String responseBody) {
                TermsOfUseFragment.this.finishWithNetworkError(activity);
            }

            public void onError(Throwable e) {
                TermsOfUseFragment.this.finishWithNetworkError(activity);
            }
        };
        onGetTerms.setProgress(progress);
        Map<String, String> params = new HashMap();
        params.put("client_id", LobiCore.sharedInstance().getClientId());
        CoreAPI.getTerms(params, onGetTerms);
    }

    private void showTerms(Activity activity, String terms) {
        View content = LayoutInflater.from(activity).inflate(R.layout.lobisdk_terms_of_use_content, null);
        ((TextView) content.findViewById(R.id.lobi_custom_dialog_content_text)).setText(terms);
        this.mTermsOfUseDialog = new CustomDialog((Context) activity, content);
        this.mTermsOfUseDialog.setTitle(R.string.lobi_terms);
        this.mTermsOfUseDialog.setPositiveButton(activity.getString(R.string.lobi_ok), new OnClickListener() {
            public void onClick(View view) {
                AccountDatastore.setValue(Key.HAS_ACCEPTED_TERMS_OF_USE, Boolean.TRUE);
                TermsOfUseFragment.this.mTermsOfUseDialog.dismiss();
                if (TermsOfUseFragment.this.mCallback != null) {
                    TermsOfUseFragment.this.mCallback.onAccept();
                    TermsOfUseFragment.this.mCallback = null;
                }
            }
        });
        this.mTermsOfUseDialog.setNegativeButton(activity.getString(R.string.lobi_cancel), new OnClickListener() {
            public void onClick(View view) {
                TermsOfUseFragment.this.mTermsOfUseDialog.dismiss();
                if (TermsOfUseFragment.this.mCallback != null) {
                    TermsOfUseFragment.this.mCallback.onDismiss();
                    TermsOfUseFragment.this.mCallback = null;
                }
            }
        });
        this.mTermsOfUseDialog.setCancelable(false);
        this.mTermsOfUseDialog.show();
        resizeDialog(this.mTermsOfUseDialog);
    }

    private void resizeDialog(Dialog dialog) {
        DisplayMetrics displayMetrics = dialog.getContext().getResources().getDisplayMetrics();
        Window window = dialog.getWindow();
        LayoutParams params = window.getAttributes();
        params.width = (int) (((float) displayMetrics.widthPixels) * 0.9f);
        params.height = (int) (((float) displayMetrics.heightPixels) * 0.9f);
        window.setAttributes(params);
    }

    public void onConfigurationChanged(Configuration arg0) {
        super.onConfigurationChanged(arg0);
        if (this.mTermsOfUseDialog != null) {
            resizeDialog(this.mTermsOfUseDialog);
        }
    }

    private void peekGroup(String groupExId) {
        Log.v("nakamap-sdk", "peekGroup: " + groupExId);
        Map<String, String> params = new HashMap();
        final UserValue currentUser = AccountDatastore.getCurrentUser();
        params.put("token", currentUser.getToken());
        params.put("group_ex_id", groupExId);
        CoreAPI.getGroupWithExIdV2(params, new DefaultAPICallback<GetGroup>(getActivity()) {
            public void onResponse(GetGroup t) {
                TransactionDatastore.setGroupDetail(GroupValueUtils.convertToGroupDetailValue(t.group), currentUser.getUid());
                Bundle extras = new Bundle();
                extras.putString(PathRouter.PATH, ChatActivity.PATH_CHAT);
                extras.putString(ChatActivity.EXTRAS_STREAM_HOST, t.group.getStreamHost());
                extras.putString("gid", t.group.getUid());
                PathRouter.startPath(extras);
            }
        });
    }

    private void joinPendingGroup(String groupExId, String groupName, final String message) {
        Map<String, String> params = new HashMap();
        final UserValue currentUser = AccountDatastore.getCurrentUser();
        params.put("token", currentUser.getToken());
        params.put("group_ex_id", groupExId);
        if (!TextUtils.isEmpty(groupName)) {
            params.put("name", groupName);
        }
        CoreAPI.postGroupJoinWithExId(params, new DefaultAPICallback<PostGroupJoinWithExId>(getActivity()) {
            public void onResponse(PostGroupJoinWithExId t) {
                Log.v("nakamap-sdk", "joined: " + t.group.getName());
                GroupDetailValue groupDetail = GroupValueUtils.convertToGroupDetailValue(t.group);
                TransactionDatastore.setGroupDetail(groupDetail, currentUser.getUid());
                Bundle extras = new Bundle();
                if (TextUtils.isEmpty(message)) {
                    extras.putString(PathRouter.PATH, ChatActivity.PATH_CHAT);
                } else {
                    extras.putParcelable("EXTRA_GROUP_DETAIL_VALUE", groupDetail);
                    extras.putString(PathRouter.PATH, ChatEditActivity.PATH_CHAT_EDIT);
                }
                extras.putString(ChatActivity.EXTRAS_STREAM_HOST, t.group.getStreamHost());
                extras.putString("gid", t.group.getUid());
                extras.putString("EXTRA_MESSAGE", message);
                PathRouter.startPath(extras);
            }

            public void onError(int statusCode, String responseBody) {
                if (400 == statusCode) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            TermsOfUseFragment.this.showGroupWarning();
                        }
                    });
                }
            }
        });
    }

    private void showGroupWarning() {
        final CustomDialog dialog = CustomDialog.createTextDialog(getActivity(), getString(R.string.lobisdk_error_group_with_specified_exid_does_not_exist));
        dialog.setPositiveButton(getString(R.string.lobi_ok), new OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void finishWithNetworkError(final Activity activity) {
        activity.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(activity, activity.getString(R.string.lobisdk_network_disconnected), 0).show();
            }
        });
        activity.finish();
    }
}
