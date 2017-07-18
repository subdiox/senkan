package com.kayac.lobi.sdk.activity.invitation;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import com.kayac.lobi.libnakamap.components.CustomDialog;
import com.kayac.lobi.libnakamap.components.PathRouter;
import com.kayac.lobi.libnakamap.datastore.AccountDDL.KKey.UpdateAt;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastore;
import com.kayac.lobi.libnakamap.exception.NakamapException.CurrentUserNotSet;
import com.kayac.lobi.libnakamap.net.APIRes.GetGroup;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupJoinWithGroupUid;
import com.kayac.lobi.libnakamap.net.APIRes.PostInvitationsRecipients;
import com.kayac.lobi.libnakamap.net.CoreAPI;
import com.kayac.lobi.libnakamap.net.CoreAPI.DefaultAPICallback;
import com.kayac.lobi.libnakamap.utils.GroupValueUtils;
import com.kayac.lobi.libnakamap.utils.Log;
import com.kayac.lobi.libnakamap.value.AppValue;
import com.kayac.lobi.libnakamap.value.GroupDetailValue;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.sdk.R;
import com.kayac.lobi.sdk.activity.profile.ProfileActivity;
import com.kayac.lobi.sdk.chat.activity.ChatActivity;
import com.kayac.lobi.sdk.utils.SDKBridge;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InvitationHandler {
    public static final String APP_UID = "app.uid";
    public static final String EXTRA_INVIATION_INFO = "invitation_info";
    public static final String GROUP = "group";
    public static final String GROUP_IS_PUBLIC = "group.is_public";
    public static final String GROUP_NAME = "group.name";
    public static final String GROUP_UID = "group.uid";
    public static final String INVITE_UID = "invite.uid";
    public static final String TYPE = "type";
    public static final String USER = "user";
    public static final String USER_NAME = "user.name";

    public static class GroupInvitationCallback extends DefaultAPICallback<PostInvitationsRecipients> {
        final Activity mActivity;
        final DefaultAPICallback<GetGroup> mGetGroup;
        final boolean mIsPublicGroup;
        final DefaultAPICallback<PostGroupJoinWithGroupUid> mOnJoinCallback;
        final UserValue userValue;

        GroupInvitationCallback(Activity activity, final UserValue userValue, boolean isPublicGroup) {
            super(activity);
            this.mActivity = activity;
            this.userValue = userValue;
            this.mIsPublicGroup = isPublicGroup;
            this.mOnJoinCallback = new DefaultAPICallback<PostGroupJoinWithGroupUid>(activity) {
                public void onResponse(PostGroupJoinWithGroupUid t) {
                    GroupDetailValue groupDetailValue = GroupValueUtils.convertToGroupDetailValue(t.group);
                    if (groupDetailValue != null) {
                        TransactionDatastore.setGroupDetail(groupDetailValue, userValue.getUid());
                        final Bundle extras = new Bundle();
                        extras.putString(PathRouter.PATH, ChatActivity.PATH_CHAT);
                        extras.putString("gid", groupDetailValue.getUid());
                        extras.putParcelable(SDKBridge.GROUPDETAILVALUE, groupDetailValue);
                        extras.putString(ChatActivity.EXTRAS_STREAM_HOST, groupDetailValue.getStreamHost());
                        InvitationHandler.showSwitchUserDialog(GroupInvitationCallback.this.mActivity, userValue, new OnClickListener() {
                            public void onClick(View v) {
                                GroupInvitationCallback.this.mActivity.finish();
                                PathRouter.removePathsGreaterThan("/");
                                PathRouter.startPath(extras);
                            }
                        }, new OnDismissListener() {
                            public void onDismiss(DialogInterface dialog) {
                                GroupInvitationCallback.this.mActivity.finish();
                                PathRouter.removePathsGreaterThan("/");
                                PathRouter.startPath(extras);
                            }
                        });
                    }
                }

                public void onError(int statusCode, String responseBody) {
                    super.onError(statusCode, responseBody);
                    GroupInvitationCallback.this.mActivity.finish();
                }

                public void onError(Throwable e) {
                    super.onError(e);
                    GroupInvitationCallback.this.mActivity.finish();
                }
            };
            this.mGetGroup = new DefaultAPICallback<GetGroup>(activity) {
                public void onResponse(GetGroup t) {
                    GroupDetailValue groupDetailValue = GroupValueUtils.convertToGroupDetailValue(t.group);
                    if (groupDetailValue != null) {
                        TransactionDatastore.setGroupDetail(groupDetailValue, userValue.getUid());
                        final Bundle extras = new Bundle();
                        extras.putString(PathRouter.PATH, ChatActivity.PATH_CHAT);
                        extras.putString("gid", groupDetailValue.getUid());
                        extras.putString(ChatActivity.EXTRAS_STREAM_HOST, groupDetailValue.getStreamHost());
                        extras.putParcelable(SDKBridge.GROUPDETAILVALUE, groupDetailValue);
                        InvitationHandler.showSwitchUserDialog(GroupInvitationCallback.this.mActivity, userValue, new OnClickListener() {
                            public void onClick(View v) {
                                GroupInvitationCallback.this.mActivity.finish();
                                PathRouter.removePathsGreaterThan("/");
                                PathRouter.startPath(extras);
                            }
                        }, new OnDismissListener() {
                            public void onDismiss(DialogInterface dialog) {
                                GroupInvitationCallback.this.mActivity.finish();
                                PathRouter.removePathsGreaterThan("/");
                                PathRouter.startPath(extras);
                            }
                        });
                    }
                }

                public void onError(int statusCode, String responseBody) {
                    super.onError(statusCode, responseBody);
                    GroupInvitationCallback.this.mActivity.finish();
                }

                public void onError(Throwable e) {
                    super.onError(e);
                    GroupInvitationCallback.this.mActivity.finish();
                }
            };
        }

        public void onResponse(PostInvitationsRecipients t) {
            if (t.group == null) {
                return;
            }
            if (this.mIsPublicGroup) {
                Map<String, String> params = new HashMap();
                params.put("token", this.userValue.getToken());
                params.put("uid", t.group.getUid());
                CoreAPI.getGroup(params, this.mGetGroup);
                return;
            }
            params = new HashMap();
            params.put("token", this.userValue.getToken());
            params.put("uid", t.group.getUid());
            params.put("members_count", "1");
            params.put("count", "0");
            CoreAPI.postGroupJoinWithGroupUidV2(params, this.mOnJoinCallback);
        }

        public void onError(int statusCode, String responseBody) {
            super.onError(statusCode, responseBody);
            this.mActivity.finish();
        }

        public void onError(Throwable e) {
            super.onError(e);
            this.mActivity.finish();
        }
    }

    public static void handleInvitation(Activity activity, Bundle bundle) {
        if (!activity.isFinishing()) {
            String groupName = bundle.getString(GROUP_NAME);
            String userName = bundle.getString(USER_NAME);
            final String groupUID = bundle.getString(GROUP_UID);
            final String inviteUID = bundle.getString(INVITE_UID);
            String type = bundle.getString("type");
            String appUid = bundle.getString(APP_UID);
            final boolean groupIsPublic = "1".equals(bundle.getString(GROUP_IS_PUBLIC));
            try {
                UserValue u = AccountDatastore.getCurrentUser();
                Log.v("nakamap-sdk", "default user: " + u);
                Log.v("nakamap-sdk", "appUid: " + appUid);
                final ArrayList<UserValue> matchingUsers = new ArrayList();
                if (!TextUtils.isEmpty(appUid)) {
                    boolean find = false;
                    for (UserValue user : AccountDatastore.getUsers()) {
                        Log.v("nakamap-sdk", "user->app: " + user.getApp());
                        if (user.getApp() != null) {
                            Log.v("nakamap-sdk", "user->app->uid: " + user.getApp().getUid());
                        }
                        if (user.getApp() != null) {
                            if (appUid.equals(user.getApp().getUid())) {
                                find = true;
                                u = user;
                                matchingUsers.add(u);
                            }
                        }
                    }
                    if (!find) {
                        showAlartAndFinish(activity, type);
                        return;
                    }
                }
                final UserValue targetUser = u;
                if (targetUser == null) {
                    showAlartAndFinish(activity, type);
                } else if ("user".equals(type)) {
                    dialog = CustomDialog.createTextDialog(activity, activity.getString(R.string.lobi__string__wants, new Object[]{userName}));
                    final Activity activity2 = activity;
                    dialog.setPositiveButton(activity.getString(R.string.lobi_confirm), new OnClickListener() {
                        public void onClick(View v) {
                            dialog.dismiss();
                            if (matchingUsers.size() <= 1) {
                                InvitationHandler.startUserInvitation(activity2, inviteUID, targetUser);
                            }
                        }
                    });
                    r2 = activity.getString(R.string.lobi_cancel);
                    r0 = activity;
                    dialog.setNegativeButton(r2, new OnClickListener() {
                        public void onClick(View arg0) {
                            dialog.dismiss();
                            r0.finish();
                        }
                    });
                    dialog.show();
                } else if ("group".equals(type)) {
                    String message;
                    String title = activity.getString(R.string.lobi_klm_account_main);
                    if (targetUser.getApp() != null) {
                        title = targetUser.getApp().getName();
                    }
                    if (groupIsPublic) {
                        message = activity.getString(R.string.lobi_received_an_invitationfrom__single_account_for_public_group, new Object[]{userName, groupName});
                    } else {
                        message = activity.getString(R.string.lobi_received_an_invitationfrom__single_account, new Object[]{userName, groupName});
                    }
                    dialog = CustomDialog.createTextDialog(activity, message);
                    dialog.setTitle(title);
                    final CustomDialog customDialog = dialog;
                    final ArrayList<UserValue> arrayList = matchingUsers;
                    final Activity activity3 = activity;
                    final String str = inviteUID;
                    final UserValue userValue = targetUser;
                    dialog.setPositiveButton(activity.getString(17039370), new OnClickListener() {
                        public void onClick(View v) {
                            customDialog.dismiss();
                            if (arrayList.size() <= 1) {
                                InvitationHandler.startGroupInitation(activity3, str, groupUID, userValue, groupIsPublic);
                            }
                        }
                    });
                    r2 = activity.getString(R.string.lobi_cancel);
                    r0 = activity;
                    dialog.setNegativeButton(r2, new OnClickListener() {
                        public void onClick(View arg0) {
                            dialog.dismiss();
                            r0.finish();
                        }
                    });
                    dialog.show();
                }
            } catch (CurrentUserNotSet e) {
                SDKBridge.showLoginForInvitationIfUserNotLoggedIn(activity, bundle);
            }
        }
    }

    private static void showAlartAndFinish(final Activity activity, String type) {
        final CustomDialog dialog = CustomDialog.createTextDialog(activity, activity.getString("group".equals(type) ? R.string.lobi_klm_invite_group_error_message : R.string.lobi_klm_invite_friend_error_message));
        dialog.setTitle("group".equals(type) ? R.string.lobi_klm_invite_group_error_title : R.string.lobi_klm_invite_friend_error_title);
        dialog.setPositiveButton(activity.getString(R.string.lobi_ok), new OnClickListener() {
            public void onClick(View arg0) {
                dialog.dismiss();
                activity.finish();
            }
        });
        dialog.show();
    }

    public static void showSwitchUserDialog(final Activity activity, UserValue userValue, final OnClickListener onClick, final OnDismissListener onDismiss) {
        if (AccountDatastore.getCurrentUser().equals(userValue)) {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    onClick.onClick(null);
                }
            });
            return;
        }
        String appName;
        AccountDatastore.setCurrentUser(userValue);
        AppValue app = userValue.getApp();
        if (app != null) {
            appName = app.getName();
        } else {
            appName = activity.getString(R.string.lobi_klm_account_main);
        }
        final String message = activity.getString(R.string.lobi_klm_switch_account_alert_message_format, new Object[]{userValue.getName(), appName});
        activity.runOnUiThread(new Runnable() {
            public void run() {
                CustomDialog dialog = CustomDialog.createTextDialog(activity, message);
                dialog.setTitle(R.string.lobi_klm_switch_account_alert_title);
                dialog.setPositiveButton(activity.getString(R.string.lobi_ok), onClick);
                dialog.show();
                dialog.setOnDismissListener(onDismiss);
            }
        });
    }

    public static void startUserInvitation(final Activity activity, String inviteUID, final UserValue userValue) {
        Map<String, String> params = new HashMap();
        params.put("token", userValue.getToken());
        params.put("uid", inviteUID);
        CoreAPI.postInvitationsRecipients(params, new DefaultAPICallback<PostInvitationsRecipients>(activity) {
            public void onResponse(final PostInvitationsRecipients t) {
                AccountDatastore.setKKValue(UpdateAt.KEY1, UpdateAt.GET_ME_CONTACTS, Long.valueOf(-1));
                UserValue currentUser = AccountDatastore.getCurrentUser();
                InvitationHandler.showSwitchUserDialog(activity, userValue, new OnClickListener() {
                    public void onClick(View v) {
                        activity.finish();
                        PathRouter.removeAllThePaths();
                        PathRouter.startPath("/");
                        ProfileActivity.startProfile(AccountDatastore.getCurrentUser(), t.user);
                    }
                }, new OnDismissListener() {
                    public void onDismiss(DialogInterface dialog) {
                        activity.finish();
                        PathRouter.removeAllThePaths();
                        PathRouter.startPath("/");
                        ProfileActivity.startProfile(AccountDatastore.getCurrentUser(), t.user);
                    }
                });
            }
        });
    }

    private static void startGroupInitation(Activity activity, String inviteUID, String groupUID, UserValue userValue, boolean isPublicGroup) {
        Map<String, String> params = new HashMap();
        params.put("token", userValue.getToken());
        params.put("uid", inviteUID);
        params.put("group", groupUID);
        CoreAPI.postInvitationsRecipients(params, new GroupInvitationCallback(activity, userValue, isPublicGroup));
    }
}
