package com.kayac.lobi.sdk.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.Toast;
import com.kayac.lobi.libnakamap.components.CustomDialog;
import com.kayac.lobi.libnakamap.components.PathRouter;
import com.kayac.lobi.libnakamap.components.SelectPictureMenuDialog;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.KKey.ImageIntentManager;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastore;
import com.kayac.lobi.libnakamap.net.APIDef.PostBindStart.RequestKey;
import com.kayac.lobi.libnakamap.net.APIRes.PostBindStart;
import com.kayac.lobi.libnakamap.net.CoreAPI;
import com.kayac.lobi.libnakamap.net.CoreAPI.DefaultAPICallback;
import com.kayac.lobi.libnakamap.utils.ChatListUtil.ChatListItemData;
import com.kayac.lobi.libnakamap.utils.NakamapBroadcastManager;
import com.kayac.lobi.libnakamap.utils.NakamapSDKDatastore.Key;
import com.kayac.lobi.libnakamap.utils.URLUtils;
import com.kayac.lobi.libnakamap.value.ChatValue;
import com.kayac.lobi.libnakamap.value.GroupDetailValue;
import com.kayac.lobi.libnakamap.value.NotificationValue;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.sdk.LobiCore;
import com.kayac.lobi.sdk.R;
import com.kayac.lobi.sdk.activity.NakamapActivity;
import com.kayac.lobi.sdk.activity.ad.AdBaseActivity;
import com.kayac.lobi.sdk.activity.ad.AdRecommendActivity;
import com.kayac.lobi.sdk.activity.invitation.InvitationHandler;
import com.kayac.lobi.sdk.data.LobiGroupDataFactory;
import java.util.HashMap;
import java.util.Map;

public class SDKBridge {
    public static final String GROUPDETAILVALUE = "GroupDetailValue";
    public static final String ON_GROUP_LIST_CLOSE = "on_group_list_close";
    public static final String ON_POST_GROUP_CHAT = "on_post_group_chat";
    public static final String ON_PURCHASE_SUCCESS = "com.kayac.lobi.activity.stamp.ON_PURCHASE_SUCCESS";
    public static final String PLATFORM = "android-sdk";
    public static final boolean SHOW_PRIVATE_GROUP_ON_START = false;
    private static GroupListDecorator sDefaultDecorator = new GroupListDecorator() {
        public void decorate(ListView listView, Context context, boolean isPublic) {
        }
    };

    public interface GroupListDecorator {
        void decorate(ListView listView, Context context, boolean z);
    }

    public static void showLoginForInvitationIfUserNotLoggedIn(Activity activity) {
        Uri data = activity.getIntent().getData();
        if ("invited".equals(data.getAuthority())) {
            showLoginForInvitationIfUserNotLoggedIn(activity, URLUtils.parseQuery(data));
        }
    }

    public static boolean shouldShowInvitationDialog() {
        return ((Boolean) AccountDatastore.getValue(Key.HAS_ACCEPTED_TERMS_OF_USE, Boolean.FALSE)).booleanValue();
    }

    public static boolean groupListFragmentShouldLoadFromNetwork(Fragment fragment) {
        return true;
    }

    private static void openUrl(Activity activity, String url) {
        startActivitySafe(activity, new Intent("android.intent.action.VIEW", Uri.parse(url)));
    }

    private static void startActivitySafe(Activity activity, Intent intent) {
        try {
            activity.startActivity(intent);
        } catch (ActivityNotFoundException e) {
        }
    }

    public static void setGroupListDecorator(GroupListDecorator decorator) {
        sDefaultDecorator = decorator;
    }

    public static void decorateGroupList(ListView listView, Context context, boolean isPublic) {
        sDefaultDecorator.decorate(listView, context, isPublic);
    }

    public static void startChatPickPhotoFromChatActivity(FragmentActivity activity, String gid, boolean isSet) {
        SelectPictureMenuDialog.newInstance(ImageIntentManager.CHAT, R.string.lobi_attach, isSet, R.string.lobi_unselect).show(activity.getSupportFragmentManager(), SelectPictureMenuDialog.class.getCanonicalName());
    }

    public static void startGalleryActivityFromReply(ChatValue reply) {
        throw new UnsupportedOperationException();
    }

    public static void storeSelectedPictures(Context context, GroupDetailValue groupDetail, String replyTo, String message) {
        throw new UnsupportedOperationException();
    }

    public static Bundle getGalleryBundleForStandardView(ChatListItemData data) {
        throw new UnsupportedOperationException();
    }

    public static Bundle getGalleryForReplyView(ChatListItemData data) {
        throw new UnsupportedOperationException();
    }

    public static void startSelectPhotoFromChatEditActivity(FragmentActivity activity, boolean isSet) {
        SelectPictureMenuDialog.newInstance(ImageIntentManager.CHAT, R.string.lobi_attach, isSet, R.string.lobi_unselect).show(activity.getSupportFragmentManager(), SelectPictureMenuDialog.class.getCanonicalName());
    }

    public static void startSelectPhotoFromChatReplyActivity(FragmentActivity activity, boolean isSet) {
        SelectPictureMenuDialog.newInstance(ImageIntentManager.REPLY, R.string.lobi_attach, isSet, R.string.lobi_unselect).show(activity.getSupportFragmentManager(), SelectPictureMenuDialog.class.getCanonicalName());
    }

    public static void showLoginForInvitationIfUserNotLoggedIn(Activity activity, Bundle bundle) {
        PathRouter.removeAllThePaths();
        activity.startActivity(new Intent(activity, NakamapActivity.class).putExtra(InvitationHandler.EXTRA_INVIATION_INFO, bundle));
        activity.finish();
    }

    public static void updateLastSeenAt() {
        TransactionDatastore.setValue("lastSeenAt", Long.valueOf(System.currentTimeMillis()));
    }

    public static void onGroupListActivityDestroy() {
        NakamapBroadcastManager.getInstance(LobiCore.sharedInstance().getContext()).sendBroadcast(new Intent(ON_GROUP_LIST_CLOSE));
    }

    public static CustomDialog showShoutDescriptionDialog(Activity activity) {
        return showDownloadDialogImpl(activity, activity.getString(R.string.lobi_shout_exp_title), activity.getString(R.string.lobi_sdk_shout_exp_desc));
    }

    private static CustomDialog showDownloadDialogImpl(final Activity activity, String title, String description) {
        final CustomDialog dialog = CustomDialog.createTextDialog(activity, description);
        dialog.setTitle(title);
        dialog.setPositiveButton(activity.getString(R.string.lobi_ok), new OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
                SDKBridge.openUrl(activity, ReferrerUtil.createMarketUri());
            }
        });
        dialog.setNegativeButton(activity.getString(R.string.lobi_cancel), new OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
        return dialog;
    }

    public static final String createMarketUriWithReferer() {
        return ReferrerUtil.createMarketUri();
    }

    public static void startBindingToNativeApp(final Context context, final int trackingId) {
        String accessToken = AccountDatastore.getCurrentUser().getToken();
        final boolean nativeAppInstalled = checkIfNakamapNativeAppInstalled(context.getPackageManager());
        Map<String, String> params = new HashMap();
        params.put("token", accessToken);
        params.put("client_id", LobiCore.sharedInstance().getClientId());
        params.put(RequestKey.TID, (nativeAppInstalled ? "" : "web") + Integer.toString(trackingId));
        CoreAPI.postBindStart(params, new DefaultAPICallback<PostBindStart>(context) {
            public void onResponse(PostBindStart t) {
                if (!TextUtils.isEmpty(t.bindToken)) {
                    String uri;
                    if (nativeAppInstalled) {
                        uri = String.format("nakamap-sso://bind?bind_token=%s&client_id=%s", new Object[]{t.bindToken, LobiCore.sharedInstance().getClientId()});
                    } else {
                        uri = String.format("http://lobi.co/bind?bind_token=%s&tid=%s", new Object[]{t.bindToken, Integer.valueOf(trackingId)});
                    }
                    Intent intent = new Intent("android.intent.action.VIEW");
                    intent.setData(Uri.parse(uri));
                    intent.setFlags(268435456);
                    try {
                        context.startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                    }
                } else if (t.errors.size() > 0) {
                    for (String error : t.errors) {
                        if ("Bind already done".equals(error)) {
                            showToast(context.getString(R.string.lobisdk_error_already_sso_bound));
                            return;
                        }
                    }
                }
            }

            private void showToast(final String message) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(context, message, 0).show();
                    }
                });
            }
        });
    }

    public static void openMainLobi(Context context) {
        String uri;
        if (checkIfNakamapNativeAppInstalled(context.getPackageManager())) {
            uri = "nakamap://";
        } else {
            uri = "https://web.lobi.co/";
        }
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(uri));
        intent.setFlags(268435456);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
        }
    }

    public static boolean checkIfNakamapNativeAppInstalled(PackageManager pm) {
        return ManifestUtil.checkIfNakamapNativeAppInstalled(pm);
    }

    public static void onPostGroupChat(GroupDetailValue groupDetailValue) {
        TransactionDatastore.setValue("lastSeenAt", Long.valueOf(System.currentTimeMillis()));
        NakamapBroadcastManager.getInstance(LobiCore.sharedInstance().getContext()).sendBroadcast(new Intent(ON_POST_GROUP_CHAT).putExtra(LobiGroupDataFactory.EXTRA_GROUP_DATA, LobiGroupDataFactory.create(groupDetailValue)));
    }

    public static void startAlbumFromChatEditActivity(FragmentActivity chatEditActivity, String gid, boolean isSet) {
        startChatPickPhotoFromChatActivity(chatEditActivity, gid, isSet);
    }

    public static boolean isChatTutorialFragmentForceVisible(FragmentActivity activity) {
        return false;
    }

    public static Context getContext() {
        return LobiCore.sharedInstance().getContext();
    }

    public static boolean shouldMoveToAnotherActivity(Activity activity, Uri url) {
        if ((activity instanceof AdRecommendActivity) || !url.getPath().equals(NotificationValue.SHEME_PATH_AD_RECOMMENDS)) {
            return false;
        }
        AdBaseActivity.startFromMenu(AdRecommendActivity.PATH_AD_RECOMMEND);
        return true;
    }

    public static boolean intentUtilsCheckDefaultAccountImpl(Context context) {
        if (AccountDatastore.getDefaultUser() != null) {
            return false;
        }
        Toast.makeText(context, R.string.lobi_to_use, 0).show();
        return true;
    }

    public static View communityGetTopics(Fragment fragment, LayoutInflater inflater) {
        return null;
    }

    public static CharSequence getEmoticonSpannedText(Context context, String text) {
        return EmoticonUtil.getEmoticonSpannedText(context, text);
    }

    public static void setupTransferLeaderButton(View content, UserValue currentUser, String uid, String string, boolean canTransferToThisUser) {
    }
}
