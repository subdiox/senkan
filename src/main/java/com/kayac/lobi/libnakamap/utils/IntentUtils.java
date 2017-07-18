package com.kayac.lobi.libnakamap.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import com.kayac.lobi.libnakamap.components.CustomProgressDialog;
import com.kayac.lobi.libnakamap.components.PathRouter;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastore;
import com.kayac.lobi.libnakamap.net.APIRes.GetGroup;
import com.kayac.lobi.libnakamap.net.CoreAPI;
import com.kayac.lobi.libnakamap.net.CoreAPI.DefaultAPICallback;
import com.kayac.lobi.libnakamap.value.GroupValue;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.sdk.R;
import com.kayac.lobi.sdk.activity.invitation.InvitationHandler;
import com.kayac.lobi.sdk.activity.profile.ProfileActivity;
import com.kayac.lobi.sdk.chat.activity.ChatActivity;
import com.kayac.lobi.sdk.utils.SDKBridge;
import com.kayac.lobi.sdk.utils.UrlMatcher;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IntentUtils {
    public static final String SCHEME_LOBI = "lobi";
    public static final String SCHEME_NAKAMAP = "nakamap";

    private interface Host {
        public static final String AD = "ad";
        public static final String ADD_FRIEND = "add_contacts";
        public static final String GROUP = "group";
        public static final String PUBLIC_GROUPS_TREE = "public_groups_tree";
        public static final String SEND_BUTTON = "button";
        public static final String STORE = "store";
    }

    private interface Path {

        public static class Ad {
            static final String PRE_ORDER = "/reservation";
            static final String PRIZE_GROUPS = "/communities";
        }
    }

    public static boolean checkScheme(String uriString) {
        return checkScheme(Uri.parse(uriString));
    }

    public static boolean checkScheme(Uri uri) {
        if (uri == null) {
            return false;
        }
        String scheme = uri.getScheme();
        if (SCHEME_LOBI.equals(scheme) || "nakamap".equals(scheme)) {
            return true;
        }
        return false;
    }

    public static boolean startActivity(final Context context, final Uri data) {
        if (!checkScheme(data)) {
            return false;
        }
        UrlMatcher matcher = new UrlMatcher();
        matcher.addPattern("public_groups_tree", new Runnable() {
            public void run() {
                IntentUtils.startPublicGroupsTree(context, data);
            }
        });
        matcher.addPattern("group", new Runnable() {
            public void run() {
                IntentUtils.startGroup(context, data);
            }
        });
        Runnable action = matcher.matchingAction(data);
        if (action == null) {
            return false;
        }
        action.run();
        return true;
    }

    private static UserValue changeAccount(Context context, Uri data) {
        String appUid = data.getQueryParameter(InvitationHandler.APP_UID);
        UserValue user = null;
        if (appUid != null) {
            user = AccountDatastore.getUserFromAppUid(appUid);
        }
        if (user == null) {
            user = AccountDatastore.getDefaultUser();
        }
        AccountDatastore.setCurrentUser(user);
        NakamapBroadcastManager.getInstance(context).sendBroadcast(new Intent(ProfileActivity.ON_ACCOUNT_CHANGE));
        return user;
    }

    public static void startGameCommunity(Context context, String gameUid) {
        if (context != null && !TextUtils.isEmpty(gameUid)) {
            try {
                context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(String.format("lobi://game_community?gameId=%s", new Object[]{gameUid}))));
            } catch (ActivityNotFoundException e) {
                context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(String.format("https://web.lobi.co/game/%s?from_sdk=1", new Object[]{gameUid}))));
            }
        }
    }

    public static void startPublicGroupsTree(Context context, Uri data) {
    }

    public static void startGroup(Context context, Uri data) {
        final Activity activity = (Activity) context;
        if (!checkDefaultAccount(context)) {
            List<String> segs = data.getPathSegments();
            if (segs.size() != 0) {
                final UserValue currentUser = changeAccount(context, data);
                final String groupId = (String) segs.get(0);
                final Map<String, String> params = new HashMap();
                params.put("token", currentUser.getToken());
                params.put("uid", groupId);
                final DefaultAPICallback<GetGroup> callback = new DefaultAPICallback<GetGroup>(context) {
                    public void onResponse(GetGroup t) {
                        super.onResponse(t);
                        GroupValue group = t.group;
                        if (group.isPublic()) {
                            TransactionDatastore.setGroupDetail(GroupValueUtils.convertToGroupDetailValue(group), currentUser.getUid());
                            Bundle extras = new Bundle();
                            extras.putString(PathRouter.PATH, ChatActivity.PATH_CHAT);
                            extras.putString("gid", groupId);
                            extras.putString(ChatActivity.EXTRAS_STREAM_HOST, group.getStreamHost());
                            IntentUtils.startActivity(extras);
                            return;
                        }
                        activity.runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(AnonymousClass3.this.getContext(), R.string.lobi_cant_open_a_private_group, 1).show();
                            }
                        });
                    }
                };
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        CustomProgressDialog progress = new CustomProgressDialog(activity);
                        progress.setMessage(activity.getString(R.string.lobi_loading_loading));
                        callback.setProgress(progress);
                        progress.show();
                        CoreAPI.getGroup(params, callback);
                    }
                });
            }
        }
    }

    public static boolean checkDefaultAccount(Context context) {
        return SDKBridge.intentUtilsCheckDefaultAccountImpl(context);
    }

    private static void startActivity(Bundle extras) {
        PathRouter.removePathsGreaterThan("/");
        PathRouter.startPath("/");
        PathRouter.startPath(extras);
    }
}
