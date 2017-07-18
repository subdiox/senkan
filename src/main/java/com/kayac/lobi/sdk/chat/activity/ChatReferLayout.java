package com.kayac.lobi.sdk.chat.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.kayac.lobi.libnakamap.components.ImageLoaderView;
import com.kayac.lobi.libnakamap.components.PathRouter;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.net.APIRes.GetApp;
import com.kayac.lobi.libnakamap.net.APIRes.GetGroup;
import com.kayac.lobi.libnakamap.net.APIRes.GetGroupV2;
import com.kayac.lobi.libnakamap.net.APIRes.GetUser;
import com.kayac.lobi.libnakamap.net.CoreAPI;
import com.kayac.lobi.libnakamap.net.CoreAPI.DefaultAPICallback;
import com.kayac.lobi.libnakamap.utils.AdUtil;
import com.kayac.lobi.libnakamap.utils.GroupValueUtils;
import com.kayac.lobi.libnakamap.utils.Log;
import com.kayac.lobi.libnakamap.value.ChatReferValue;
import com.kayac.lobi.libnakamap.value.GroupDetailValue;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.sdk.R;
import com.kayac.lobi.sdk.activity.profile.ProfileActivity;
import com.kayac.lobi.sdk.utils.SDKBridge;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatReferLayout extends LinearLayout implements OnClickListener {
    private static final String TAG = "[refer]";
    private final Button mActionButton = ((Button) findViewById(R.id.lobi_chat_refer_action));
    private final ImageLoaderView mIcon = ((ImageLoaderView) findViewById(R.id.lobi_chat_refer_icon));
    private ChatReferValue mRefer;
    private final TextView mTitle = ((TextView) findViewById(R.id.lobi_chat_refer_title));

    public ChatReferLayout(Context context) {
        super(context);
        inflate(context, R.layout.lobi_chat_refer_layout, this);
    }

    public void setChatRefer(ChatReferValue refer) {
        setOnClickListener(this);
        this.mActionButton.setOnClickListener(this);
        this.mActionButton.setText(refer.actionTitle);
        this.mTitle.setText(refer.title);
        int size = -1;
        if ("public_group".equals(refer.type)) {
            size = 128;
        } else if ("user".equals(refer.type)) {
            size = 144;
        } else if ("app".equals(refer.type)) {
            size = 120;
        }
        if (size > 0) {
            this.mIcon.loadImage(refer.image, size);
        } else {
            this.mIcon.loadImage(refer.image);
        }
        this.mRefer = refer;
    }

    public void onClick(View v) {
        doAction(this.mRefer);
    }

    void doAction(ChatReferValue refer) {
        Uri uri = Uri.parse(refer.link);
        String lastPath = uri.getLastPathSegment();
        Log.v(TAG, "type:" + refer.type);
        Log.v(TAG, "lastPath:" + lastPath);
        final UserValue currentUser = AccountDatastore.getCurrentUser();
        Map<String, String> params;
        if ("public_group".equals(refer.type)) {
            final String gid = lastPath;
            params = new HashMap();
            params.put("token", currentUser.getToken());
            params.put("uid", gid);
            CoreAPI.getGroup(params, new DefaultAPICallback<GetGroup>(getContext()) {
                public void onResponse(GetGroup t) {
                    Bundle extras = new Bundle();
                    extras.putString(PathRouter.PATH, ChatActivity.PATH_CHAT);
                    extras.putParcelable(SDKBridge.GROUPDETAILVALUE, GroupValueUtils.convertToGroupDetailValue(t.group));
                    extras.putString("gid", gid);
                    extras.putString(ChatActivity.EXTRAS_STREAM_HOST, t.group.getStreamHost());
                    PathRouter.removePathsGreaterThan("/");
                    PathRouter.startPath(extras);
                }
            });
        } else if ("user".equals(refer.type)) {
            params = new HashMap();
            params.put("token", currentUser.getToken());
            params.put("uid", lastPath);
            CoreAPI.getUser(params, new DefaultAPICallback<GetUser>(getContext()) {
                public void onResponse(GetUser t) {
                    ProfileActivity.startProfile(currentUser, t.user);
                }
            });
        } else if ("app".equals(refer.type)) {
            List<String> pathSegments = uri.getPathSegments();
            getAppAndCheck((String) pathSegments.get(0), pathSegments);
        } else if ("type".equals(refer.type)) {
            if (refer.link != null) {
                openUrlSafely(refer.link);
            }
        } else if ("reply".equals(refer.type) && !TextUtils.isEmpty(refer.link)) {
            openReplyActivity(refer.link);
        }
    }

    private void openReplyActivity(String link) {
        Uri uri = Uri.parse(link);
        if ("nakamap".equals(uri.getScheme())) {
            List<String> pathSegments = uri.getPathSegments();
            if ("group".equals(uri.getHost()) && pathSegments.size() == 3) {
                final String groupUid = (String) pathSegments.get(0);
                final String chatUid = (String) pathSegments.get(2);
                if ("chat".equals((String) pathSegments.get(1))) {
                    Map<String, String> params = new HashMap();
                    params.put("token", AccountDatastore.getCurrentUser().getToken());
                    params.put("uid", groupUid);
                    params.put("count", "0");
                    params.put("members_count", "1");
                    CoreAPI.getGroupV2(params, new DefaultAPICallback<GetGroupV2>(getContext()) {
                        public void onResponse(GetGroupV2 t) {
                            ChatReferLayout.this.openReplyActivity(groupUid, chatUid, GroupValueUtils.convertToGroupDetailValue(t.group));
                        }
                    });
                }
            }
        }
    }

    private void openReplyActivity(String groupUid, String chatUid, GroupDetailValue groupDetail) {
        Bundle extras = new Bundle();
        extras.putString(PathRouter.PATH, ChatReplyActivity.PATH_CHAT_REPLY);
        extras.putString(ChatReplyActivity.EXTRA_CHAT_REPLY_TO, chatUid);
        extras.putString("gid", groupUid);
        extras.putParcelable(ChatReplyActivity.EXTRA_CHAT_REPLY_GROUPDETAIL, groupDetail);
        PathRouter.removePathsGreaterThan("/");
        PathRouter.startPath(extras);
    }

    private void getAppAndCheck(String appUid, final List<String> pathSegments) {
        UserValue currentUser = AccountDatastore.getCurrentUser();
        Map<String, String> params = new HashMap();
        params.put("token", currentUser.getToken());
        params.put("uid", appUid);
        CoreAPI.getApp(params, new DefaultAPICallback<GetApp>(getContext()) {
            public void onResponse(GetApp t) {
                Log.v(ChatReferLayout.TAG, "clientId: " + t.app.getClientId());
                if (AdUtil.canRespondToImplicitIntent(String.format("nakamapapp-%s://", new Object[]{clientId}))) {
                    String data = null;
                    if (pathSegments.size() > 2 && "app".equals(pathSegments.get(1))) {
                        data = (String) pathSegments.get(2);
                    }
                    String url = String.format("nakamapapp-%s://", new Object[]{clientId});
                    if (!TextUtils.isEmpty(data)) {
                        url = url + "app/" + data;
                    }
                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
                    intent.addFlags(67108864);
                    ChatReferLayout.this.openUrlSafely(intent);
                    return;
                }
                ChatReferLayout.this.openUrlSafely(t.app.getPlaystoreUri());
            }
        });
    }

    private void openUrlSafely(String url) {
        openUrlSafely(new Intent("android.intent.action.VIEW", Uri.parse(url)));
    }

    private void openUrlSafely(Intent intent) {
        Activity activity = (Activity) getContext();
        if (activity != null) {
            try {
                activity.startActivity(intent);
            } catch (ActivityNotFoundException e) {
            }
        }
    }
}
