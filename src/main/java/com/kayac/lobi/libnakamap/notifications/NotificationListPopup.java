package com.kayac.lobi.libnakamap.notifications;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.kayac.lobi.libnakamap.components.CustomDialog;
import com.kayac.lobi.libnakamap.components.CustomProgressDialog;
import com.kayac.lobi.libnakamap.components.ImageLoaderView;
import com.kayac.lobi.libnakamap.components.PathRouter;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastore;
import com.kayac.lobi.libnakamap.net.APIDef.GetNotifications.RequestKey;
import com.kayac.lobi.libnakamap.net.APIRes.GetApp;
import com.kayac.lobi.libnakamap.net.APIRes.GetGroup;
import com.kayac.lobi.libnakamap.net.APIRes.GetGroupV2;
import com.kayac.lobi.libnakamap.net.APIRes.GetNotifications;
import com.kayac.lobi.libnakamap.net.APIRes.GetSignupPromote;
import com.kayac.lobi.libnakamap.net.APIRes.GetUser;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupJoinWithGroupUid;
import com.kayac.lobi.libnakamap.net.APIUtil;
import com.kayac.lobi.libnakamap.net.CoreAPI;
import com.kayac.lobi.libnakamap.net.CoreAPI.DefaultAPICallback;
import com.kayac.lobi.libnakamap.net.PagerLoader;
import com.kayac.lobi.libnakamap.utils.AdUtil;
import com.kayac.lobi.libnakamap.utils.GroupValueUtils;
import com.kayac.lobi.libnakamap.utils.IntentUtils;
import com.kayac.lobi.libnakamap.utils.Log;
import com.kayac.lobi.libnakamap.utils.ModuleUtil;
import com.kayac.lobi.libnakamap.utils.NotificationUtil;
import com.kayac.lobi.libnakamap.utils.NotificationUtil.NotificationSDKType;
import com.kayac.lobi.libnakamap.utils.NotificationUtil.PromoteType;
import com.kayac.lobi.libnakamap.utils.TimeUtil;
import com.kayac.lobi.libnakamap.utils.URLUtils;
import com.kayac.lobi.libnakamap.utils.ViewUtils;
import com.kayac.lobi.libnakamap.value.GroupDetailValue;
import com.kayac.lobi.libnakamap.value.GroupValue;
import com.kayac.lobi.libnakamap.value.GroupValue.JoinCondition;
import com.kayac.lobi.libnakamap.value.HookActionValue;
import com.kayac.lobi.libnakamap.value.HookActionValue.APIRequestParams;
import com.kayac.lobi.libnakamap.value.HookActionValue.OpenAppStoreParams;
import com.kayac.lobi.libnakamap.value.HookActionValue.Params;
import com.kayac.lobi.libnakamap.value.NotificationValue;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.sdk.R;
import com.kayac.lobi.sdk.activity.ad.AdBaseActivity;
import com.kayac.lobi.sdk.activity.ad.AdRecommendActivity;
import com.kayac.lobi.sdk.activity.invitation.InvitationHandler;
import com.kayac.lobi.sdk.activity.invitation.InvitationWebViewActivity;
import com.kayac.lobi.sdk.activity.profile.ProfileActivity;
import com.kayac.lobi.sdk.auth.AccountUtil;
import com.kayac.lobi.sdk.chat.activity.ChatActivity;
import com.kayac.lobi.sdk.chat.activity.ChatReplyActivity;
import com.kayac.lobi.sdk.chat.activity.ChatSDKModuleBridge;
import com.kayac.lobi.sdk.utils.SDKBridge;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class NotificationListPopup extends PopupWindow {
    private static final int NOTIFICATION_BG_RES_NORMAL = R.drawable.lobi_notification_background_selector;
    private static final int NOTIFICATION_BG_RES_UNREAD = R.drawable.lobi_notification_background_yellow_selector;
    private static ArrayList<String> sAdIdList = new ArrayList();
    private WeakReference<Adapter> mAdapterRef;
    private Context mContext;
    private int mFooterHeight;
    private ListView mListView;
    private WeakReference<View> mLoadingFooterViewRef;
    private View mNoListItemsView;
    private final PagerLoader<GetNotifications> mOlderNotificationLoader = new PagerLoader<GetNotifications>(this.mOnGetNotifications) {
        private boolean mShouldLoadNext = true;

        protected boolean shouldLoadNext() {
            boolean z;
            synchronized (this.mLock) {
                Log.v("lobi-sdk", "[pagerLoader] shouldLoadNext: " + this.mShouldLoadNext);
                z = this.mShouldLoadNext;
            }
            return z;
        }

        protected void load() {
            String service;
            Log.v("lobi-sdk", "[pagerLoader] load");
            Map<String, String> params = new HashMap();
            params.put("token", AccountDatastore.getCurrentUser().getToken());
            params.put("count", "30");
            synchronized (this.mLock) {
                if (this.mNextCursor != null) {
                    params.put("older_than", this.mNextCursor);
                }
            }
            if (NotificationSDKType.ChatSDK == NotificationListPopup.this.mSdkType) {
                service = "chat";
            } else {
                service = RequestKey.SERVICE_REC;
            }
            params.put("service", service);
            CoreAPI.getNotifications(params, getApiCallback());
        }

        protected String getNextCursor(GetNotifications t) {
            int size = t.notificationValue.size();
            if (size <= 0) {
                return null;
            }
            NotificationValue notification = (NotificationValue) t.notificationValue.get(size - 1);
            Log.v("lobi-sdk", "[pagerLoader] getNextCursor: " + notification.getId());
            return notification.getId();
        }

        protected void onResponse(GetNotifications t) {
            Log.v("lobi-sdk", "[pagerLoader] onResponse ");
            synchronized (this.mLock) {
                this.mShouldLoadNext = t.notificationValue.size() > 0;
            }
            super.onResponse(t);
        }
    };
    private final DefaultAPICallback<GetNotifications> mOnGetNotifications = new DefaultAPICallback<GetNotifications>(this.mContext) {
        public void onResponse(final GetNotifications t) {
            runOnUiThread(new Runnable() {
                public void run() {
                    NotificationListPopup.this.onFetchNotifications(t.notificationValue);
                }
            });
        }

        public void onError(int statusCode, String responseBody) {
            super.onError(statusCode, responseBody);
        }

        public void onError(Throwable e) {
            Log.e(NotificationListPopup.class.getSimpleName(), "OnGetNotifications", e);
        }
    };
    private final OnScrollListener mOnScrollListener = new OnScrollListener() {
        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }

        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            View loadingFooterView = (View) NotificationListPopup.this.mLoadingFooterViewRef.get();
            if (((Adapter) NotificationListPopup.this.mAdapterRef.get()) != null && loadingFooterView != null) {
                int position = firstVisibleItem + visibleItemCount;
                Log.v("lobi-sdk", "[pagerLoader] position: " + position);
                Log.v("lobi-sdk", "[pagerLoader] firstVisibleItem: " + firstVisibleItem);
                Log.v("lobi-sdk", "[pagerLoader] visibleItemCount: " + visibleItemCount);
                if (position == totalItemCount) {
                    LayoutParams params = (LayoutParams) loadingFooterView.getLayoutParams();
                    if (params.height == 1) {
                        params.height = NotificationListPopup.this.mFooterHeight;
                        loadingFooterView.setLayoutParams(params);
                    } else {
                        NotificationListPopup.this.mFooterHeight = params.height;
                    }
                    boolean isLoading = NotificationListPopup.this.mOlderNotificationLoader.isLoading();
                    Log.v("lobi-sdk", "[pagerLoader] isLoading: " + isLoading);
                    if (!isLoading) {
                        if (NotificationListPopup.this.mOlderNotificationLoader.loadNextPage()) {
                            Log.v("lobi-sdk", "[pagerLoader] load next");
                            loadingFooterView.setVisibility(0);
                            return;
                        }
                        Log.v("lobi-sdk", "[pagerLoader] didn't load!");
                        loadingFooterView.setVisibility(8);
                        params.height = 1;
                        loadingFooterView.setLayoutParams(params);
                    }
                }
            }
        }
    };
    private NotificationSDKType mSdkType = NotificationSDKType.Unknown;

    private static class Adapter extends BaseAdapter {
        private final Comparator<NotificationItemData> mComparator = new Comparator<NotificationItemData>() {
            public int compare(NotificationItemData lhs, NotificationItemData rhs) {
                if (lhs.getTime() < rhs.getTime()) {
                    return 1;
                }
                if (lhs.getTime() > rhs.getTime()) {
                    return -1;
                }
                return 0;
            }
        };
        private Context mContext;
        private final List<NotificationData> mData = new ArrayList();
        private final int mFirstItemPadding;
        private Long mLastNoticeAt;
        private final LayoutInflater mLayoutInflater;
        private Resources mResources;
        private NotificationSDKType mSdkType = NotificationSDKType.Unknown;

        public Adapter(Context context, NotificationSDKType sdkType) {
            this.mContext = context;
            Resources res = context.getResources();
            this.mLayoutInflater = LayoutInflater.from(context);
            this.mFirstItemPadding = res.getDimensionPixelSize(R.dimen.lobi_padding_low);
            this.mResources = context.getResources();
            this.mLastNoticeAt = Long.valueOf(NotificationUtil.getLastNotificationAt(sdkType, AccountDatastore.getCurrentUser().getUid()));
            this.mSdkType = sdkType;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = this.mLayoutInflater.inflate(R.layout.lobi_notification_list_item, null);
                view.setTag(new ItemHolder(view));
            } else {
                view = convertView;
            }
            final NotificationData notificationData = getItem(position);
            final NotificationValue data = notificationData.value;
            ItemHolder holder = (ItemHolder) view.getTag();
            UserValue user = data.getUser();
            if (user == null || user.getUid() == null) {
                holder.userIcon.setImageResource(R.drawable.lobi_icn_notification_user_default);
            } else {
                holder.userIcon.loadImage(data.getUser().getIcon());
            }
            String title = data.getTitle();
            if (TextUtils.isEmpty(title) || " ".equals(title)) {
                holder.activity.setVisibility(8);
            } else {
                holder.activity.setText(title);
                holder.activity.setVisibility(0);
            }
            holder.date.setText(TimeUtil.getTimeSpan(this.mContext, data.getCreatedDate()));
            if (notificationData.isClicked || data.getCreatedDate() <= this.mLastNoticeAt.longValue()) {
                holder.container.setBackgroundResource(NotificationListPopup.NOTIFICATION_BG_RES_NORMAL);
            } else {
                holder.container.setBackgroundResource(NotificationListPopup.NOTIFICATION_BG_RES_UNREAD);
            }
            holder.container.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    notificationData.isClicked = true;
                    v.setBackgroundResource(NotificationListPopup.NOTIFICATION_BG_RES_NORMAL);
                    Adapter.this.doAction(data);
                }
            });
            if (TextUtils.isEmpty(data.getMessage())) {
                holder.description.setVisibility(8);
            } else {
                holder.description.setVisibility(0);
                holder.description.setText(data.getMessage());
            }
            if (TextUtils.isEmpty(data.getIcon())) {
                holder.icon.setImageResource(R.drawable.lobi_icn_notification_type_default);
            } else {
                holder.icon.loadImage(data.getIcon());
            }
            ArrayList<HookActionValue> hookActions = data.getDisplayHook();
            if (hookActions.size() > 0) {
                Iterator<HookActionValue> iter = hookActions.iterator();
                while (iter.hasNext()) {
                    HookActionValue hookAction = (HookActionValue) iter.next();
                    Params actionParams = hookAction.getParams();
                    String type = hookAction.getType();
                    Log.i("[notification]", "display hook: type " + type);
                    if (APIRequestParams.TYPE.equals(type)) {
                        String uriString = ((APIRequestParams) actionParams).getApiUrl();
                        Log.i("[notification]", "display hook: POST " + uriString);
                        APIUtil.post(uriString, null, null);
                    }
                    iter.remove();
                }
                if (NotificationListPopup.sAdIdList != null) {
                    NotificationListPopup.sAdIdList.add(data.getId());
                    Log.i("[notification]", NotificationListPopup.sAdIdList.toString());
                }
            }
            return view;
        }

        public void putNotifications(List<NotificationValue> notificaions) {
            for (NotificationValue value : notificaions) {
                this.mData.add(new NotificationData(value));
            }
            notifyDataSetChanged();
        }

        public int getCount() {
            return this.mData.size();
        }

        public NotificationData getItem(int position) {
            if (position < 0) {
                return null;
            }
            return (NotificationData) this.mData.get(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        private void doAction(NotificationValue refer) {
            ArrayList<HookActionValue> actions = refer.getClickHook();
            Log.i(NotificationListPopup.class.getSimpleName(), "acts: " + actions.size());
            Iterator it = actions.iterator();
            while (it.hasNext()) {
                HookActionValue action = (HookActionValue) it.next();
                Log.i(NotificationListPopup.class.getSimpleName(), "act: " + action.getType());
                String type = action.getType();
                Params hookParams = action.getParams();
                if (APIRequestParams.TYPE.equals(type)) {
                    String uriString = ((APIRequestParams) hookParams).getApiUrl();
                    Log.i("[notification]", "click hook: POST " + uriString);
                    APIUtil.post(uriString, null, null);
                } else if (OpenAppStoreParams.TYPE.equals(type)) {
                    OpenAppStoreParams convertedParams = (OpenAppStoreParams) hookParams;
                    String adId = convertedParams.getAdId();
                    String packageName = convertedParams.getPackageName();
                    Log.i("[notification]", "click hook: start conversion check ad_id=" + adId + " package=" + packageName);
                    AdUtil.startInstallation(adId, packageName, true);
                }
            }
            final UserValue currentUser = AccountDatastore.getCurrentUser();
            String link = refer.getLink();
            if (!TextUtils.isEmpty(link)) {
                Uri uri = Uri.parse(link);
                String lastPath = uri.getLastPathSegment();
                Map<String, String> params;
                CustomProgressDialog progress;
                if ("group".equals(uri.getAuthority()) && uri.getPathSegments().size() == 1) {
                    final String gid = lastPath;
                    params = new HashMap();
                    params.put("token", currentUser.getToken());
                    params.put("uid", gid);
                    progress = createCustomProgress();
                    DefaultAPICallback<GetGroup> callback = new DefaultAPICallback<GetGroup>(this.mContext) {
                        public void onResponse(GetGroup t) {
                            super.onResponse(t);
                            GroupValue group = t.group;
                            Adapter.this.saveGroupDetail(currentUser, group);
                            Bundle extras = new Bundle();
                            extras.putString(PathRouter.PATH, ChatActivity.PATH_CHAT);
                            extras.putParcelable(SDKBridge.GROUPDETAILVALUE, GroupValueUtils.convertToGroupDetailValue(group));
                            extras.putString("gid", gid);
                            extras.putString(ChatActivity.EXTRAS_STREAM_HOST, group.getStreamHost());
                            PathRouter.removePathsGreaterThan("/");
                            PathRouter.startPath(extras);
                        }
                    };
                    callback.setProgress(progress);
                    progress.show();
                    CoreAPI.getGroup(params, callback);
                } else if ("user".equals(uri.getAuthority())) {
                    params = new HashMap();
                    params.put("token", currentUser.getToken());
                    params.put("uid", lastPath);
                    progress = createCustomProgress();
                    DefaultAPICallback<GetUser> callback2 = new DefaultAPICallback<GetUser>(this.mContext) {
                        public void onResponse(GetUser t) {
                            super.onResponse(t);
                            if (NotificationSDKType.ChatSDK == Adapter.this.mSdkType) {
                                ChatSDKModuleBridge.startChatProfile(t.user);
                            } else {
                                ProfileActivity.startProfile(currentUser, t.user);
                            }
                        }
                    };
                    callback2.setProgress(progress);
                    progress.show();
                    CoreAPI.getUser(params, callback2);
                } else if ("app".equals(uri.getAuthority())) {
                    List<String> pathSegments = uri.getPathSegments();
                    Log.v("lobi-sdk", "notification_link: " + uri);
                    Log.v("lobi-sdk", "segments: " + pathSegments.size());
                    String appUid = (String) pathSegments.get(0);
                    if (pathSegments.size() >= 1) {
                        getAppAndCheck(appUid, pathSegments);
                    }
                } else if ("group".equals(uri.getAuthority()) && uri.getPathSegments().size() == 3 && link.indexOf("/chat/") > -1) {
                    Log.v("lobi-sdk", "refer link: " + link);
                    if (!TextUtils.isEmpty(link)) {
                        openReplyActivity(link);
                    }
                } else if ("invited".equals(uri.getAuthority())) {
                    Log.v("lobi-sdk", "refer link: " + link);
                    if (!TextUtils.isEmpty(link)) {
                        groupInvitation(link);
                    }
                } else if ("public_groups_tree".equals(uri.getAuthority())) {
                    Log.v("lobi-sdk", "refer link: " + uri);
                    IntentUtils.startPublicGroupsTree(this.mContext, uri);
                } else if (NotificationValue.SHEME_AUTHORITY_LOGIN.equals(uri.getAuthority())) {
                    Log.v("lobi-sdk", "refer link: " + uri);
                    AccountUtil.bindToLobiAccount(3);
                } else if (uri.getPath().startsWith(NotificationValue.SHEME_PATH_AD_RECOMMENDS)) {
                    Log.v("lobi-sdk", "refer link: " + uri);
                    AdBaseActivity.startFromMenu(AdRecommendActivity.PATH_AD_RECOMMEND, URLUtils.parseQuery(uri));
                } else if (uri.getPath().startsWith(NotificationValue.SHEME_PATH_VIDEO) && uri.getPathSegments().size() == 2 && ModuleUtil.recIsAvailable()) {
                    Log.v("lobi-sdk", "refer link: " + uri);
                    ModuleUtil.recOpenLobiPlayActivity(lastPath);
                } else if (link != null) {
                    openUrlSafely(link);
                }
            }
        }

        private CustomProgressDialog createCustomProgress() {
            CustomProgressDialog progress = new CustomProgressDialog(this.mContext);
            progress.setMessage(this.mContext.getString(R.string.lobi_loading_loading));
            return progress;
        }

        private void saveGroupDetail(UserValue currentUser, GroupValue group) {
            TransactionDatastore.setGroupDetail(GroupValueUtils.convertToGroupDetailValue(group), currentUser.getUid());
        }

        private void openInvitedGroup(String link) {
            String lobiInvitation = "https?://lobi.co/invite/\\w+";
            if (Pattern.matches("https?://lobi.co/invite/\\w+", link)) {
                String string = link.toString() + "?platfrom=android&is_sdk=1";
                Bundle bundle = new Bundle();
                bundle.putString(PathRouter.PATH, InvitationWebViewActivity.PATH_INVITATION);
                bundle.putString(InvitationWebViewActivity.EXTRA_URL, string);
                bundle.putString("EXTRA_TITLE", "");
                PathRouter.startPath(bundle);
            }
        }

        private void groupInvitation(String link) {
            Uri uri = Uri.parse(link);
            String userUid = uri.getQueryParameter("user.uid");
            final String groupUid = uri.getQueryParameter(InvitationHandler.GROUP_UID);
            String userName = uri.getQueryParameter(InvitationHandler.USER_NAME);
            String groupName = uri.getQueryParameter(InvitationHandler.GROUP_NAME);
            if ("invited".equals(uri.getHost()) && userUid != null) {
                final CustomDialog dialog = CustomDialog.createTextDialog(this.mContext, this.mContext.getString(R.string.lobi_received_an_invitationfrom__single_account, new Object[]{userName, groupName}));
                final UserValue currentUser = AccountDatastore.getCurrentUser();
                dialog.setPositiveButton(this.mContext.getString(R.string.lobi_join_short), new OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                        Map<String, String> params = new HashMap();
                        params.put("token", currentUser.getToken());
                        params.put("uid", groupUid);
                        final CustomProgressDialog progress = new CustomProgressDialog(Adapter.this.mContext);
                        progress.setMessage(Adapter.this.mContext.getString(R.string.lobi_loading_loading));
                        DefaultAPICallback<PostGroupJoinWithGroupUid> callback = new DefaultAPICallback<PostGroupJoinWithGroupUid>(Adapter.this.mContext) {
                            public void onResponse(PostGroupJoinWithGroupUid t) {
                                super.onResponse(t);
                                progress.dismiss();
                                if (t.group != null) {
                                    Map<String, String> params = new HashMap();
                                    params.put("token", currentUser.getToken());
                                    params.put("uid", t.group.getUid());
                                    CoreAPI.getGroup(params, new DefaultAPICallback<GetGroup>(Adapter.this.mContext) {
                                        public void onResponse(GetGroup t) {
                                            super.onResponse(t);
                                            GroupValue group = t.group;
                                            TransactionDatastore.setGroupDetail(GroupValueUtils.convertToGroupDetailValue(group), currentUser.getUid());
                                            Bundle extras = new Bundle();
                                            extras.putString(PathRouter.PATH, ChatActivity.PATH_CHAT);
                                            extras.putParcelable(SDKBridge.GROUPDETAILVALUE, GroupValueUtils.convertToGroupDetailValue(group));
                                            extras.putString("gid", group.getUid());
                                            extras.putString(ChatActivity.EXTRAS_STREAM_HOST, group.getStreamHost());
                                            PathRouter.removePathsGreaterThan("/");
                                            PathRouter.startPath(extras);
                                        }
                                    });
                                }
                            }
                        };
                        progress.show();
                        CoreAPI.postGroupJoinWithGroupUidV2(params, callback);
                    }
                });
                dialog.setNegativeButton(this.mContext.getString(R.string.lobi_refuse_group_invitation), new OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.setCancelButton(this.mContext.getString(17039360), new OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        }

        private void openReplyActivity(String link) {
            Uri uri = Uri.parse(link);
            List<String> pathSegments = uri.getPathSegments();
            int size = pathSegments.size();
            Log.v("lobi-sdk", "size: " + size);
            if ("group".equals(uri.getHost()) && size >= 3) {
                final String groupUid = (String) pathSegments.get(0);
                final String chatUid = (String) pathSegments.get(2);
                if ("chat".equals((String) pathSegments.get(1))) {
                    Map<String, String> params = new HashMap();
                    final UserValue currentUser = AccountDatastore.getCurrentUser();
                    params.put("token", currentUser.getToken());
                    params.put("uid", groupUid);
                    params.put("count", "0");
                    params.put("members_count", "1");
                    CustomProgressDialog progress = createCustomProgress();
                    DefaultAPICallback<GetGroupV2> callback = new DefaultAPICallback<GetGroupV2>(this.mContext) {
                        public void onResponse(GetGroupV2 t) {
                            super.onResponse(t);
                            Adapter.this.saveGroupDetail(currentUser, t.group);
                            Adapter.this.openReplyActivity(groupUid, chatUid, GroupValueUtils.convertToGroupDetailValue(t.group), (ArrayList) t.group.getJoinConditions());
                        }
                    };
                    callback.setProgress(progress);
                    progress.show();
                    CoreAPI.getGroupV2(params, callback);
                }
            }
        }

        private void openReplyActivity(String groupUid, String chatUid, GroupDetailValue groupDetail, ArrayList<JoinCondition> joinConditions) {
            Bundle extras = new Bundle();
            extras.putString(PathRouter.PATH, ChatReplyActivity.PATH_CHAT_REPLY);
            extras.putString(ChatReplyActivity.EXTRA_CHAT_REPLY_TO, chatUid);
            extras.putString("gid", groupUid);
            extras.putParcelable(ChatReplyActivity.EXTRA_CHAT_REPLY_GROUPDETAIL, groupDetail);
            extras.putParcelableArrayList(ChatReplyActivity.EXTRA_CHAT_REPLY_GROUP_JOIN_CONDITIONS, joinConditions);
            PathRouter.removePathsGreaterThan("/");
            PathRouter.startPath(extras);
        }

        private void getAppAndCheck(String appUid, final List<String> pathSegments) {
            UserValue currentUser = AccountDatastore.getCurrentUser();
            Map<String, String> params = new HashMap();
            params.put("token", currentUser.getToken());
            params.put("uid", appUid);
            CoreAPI.getApp(params, new DefaultAPICallback<GetApp>(this.mContext) {
                public void onResponse(GetApp t) {
                    Log.v("nakamap-sdk", "clientId: " + t.app.getClientId());
                    if (AdUtil.canRespondToImplicitIntent(String.format("nakamapapp-%s://", new Object[]{clientId}))) {
                        String data = null;
                        Log.v(IntentUtils.SCHEME_LOBI, "segments: " + pathSegments.size());
                        if (pathSegments.size() > 2 && "app".equals(pathSegments.get(1))) {
                            data = (String) pathSegments.get(2);
                        }
                        String url = String.format("nakamapapp-%s://", new Object[]{clientId});
                        if (!TextUtils.isEmpty(data)) {
                            url = url + "app/" + data;
                        }
                        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
                        intent.addFlags(67108864);
                        Adapter.this.openUrlSafely(intent);
                        return;
                    }
                    Adapter.this.openUrlSafely(t.app.getPlaystoreUri());
                }
            });
        }

        private void openUrlSafely(String url) {
            openUrlSafely(new Intent("android.intent.action.VIEW", Uri.parse(url)));
        }

        private void openUrlSafely(Intent intent) {
            Activity activity = this.mContext;
            if (activity != null) {
                try {
                    activity.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                }
            }
        }
    }

    static final class ItemHolder {
        final TextView activity;
        final LinearLayout container;
        final TextView date;
        final TextView description;
        final ImageLoaderView icon;
        final ImageLoaderView userIcon;

        ItemHolder(View view) {
            this.userIcon = (ImageLoaderView) ViewUtils.findViewById(view, R.id.lobi_notification_list_item_user_icon);
            this.date = (TextView) view.findViewById(R.id.lobi_notification_list_item_date);
            this.description = (TextView) ViewUtils.findViewById(view, R.id.lobi_notification_list_item_message);
            this.activity = (TextView) ViewUtils.findViewById(view, R.id.lobi_notification_list_item_activity);
            this.icon = (ImageLoaderView) ViewUtils.findViewById(view, R.id.lobi_notification_list_item_icon);
            this.container = (LinearLayout) ViewUtils.findViewById(view, R.id.lobi_notification_list_item_container);
        }
    }

    private static class NotificationData {
        public boolean isClicked = false;
        public NotificationValue value;

        public NotificationData(NotificationValue value) {
            this.value = value;
        }
    }

    public static abstract class NotificationItemData {
        public long getTime() {
            return 0;
        }
    }

    public NotificationListPopup(Context context, PromoteType promotionNotificationType, boolean hasLobiAccount, NotificationSDKType sdkType) {
        super(context);
        this.mSdkType = sdkType;
        this.mContext = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.lobi_notifications_activity, null);
        this.mListView = (ListView) view.findViewById(R.id.lobi_notification_list);
        ViewUtils.hideOverscrollEdge(this.mListView);
        Adapter adapter = new Adapter(context, this.mSdkType);
        this.mAdapterRef = new WeakReference(adapter);
        View loadingFooterView = inflater.inflate(R.layout.lobi_loading_footer, null);
        this.mLoadingFooterViewRef = new WeakReference(loadingFooterView);
        this.mListView.addFooterView(loadingFooterView);
        this.mListView.setAdapter(adapter);
        this.mListView.setOnScrollListener(this.mOnScrollListener);
        this.mNoListItemsView = view.findViewById(R.id.lobi_notification_no_items);
        setContentView(view);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(0));
        View loginSection = view.findViewById(R.id.lobi_notification_login_section);
        if (this.mSdkType == NotificationSDKType.ChatSDK) {
            loginSection.setVisibility(8);
            this.mOlderNotificationLoader.loadNextPage();
            return;
        }
        loginSection.setVisibility(0);
        setupLoginSection(hasLobiAccount);
        if (promotionNotificationType != null) {
            loadLoginPromotion(promotionNotificationType);
        } else {
            this.mOlderNotificationLoader.loadNextPage();
        }
    }

    private void loadLoginPromotion(PromoteType type) {
        final String defaultMessage = this.mContext.getString(R.string.lobisdk_default_login_entrance_message);
        Map<String, String> params = new HashMap();
        params.put("type", type.getValue());
        CoreAPI.getSignupPromote(params, new DefaultAPICallback<GetSignupPromote>(null) {
            public void onResponse(GetSignupPromote res) {
                NotificationListPopup.this.setLoginPromotionNotification(res.message);
                NotificationListPopup.this.mOlderNotificationLoader.loadNextPage();
            }

            public void onError(int statusCode, String responseBody) {
                NotificationListPopup.this.setLoginPromotionNotification(defaultMessage);
                NotificationListPopup.this.mOlderNotificationLoader.loadNextPage();
            }

            public void onError(Throwable e) {
                NotificationListPopup.this.setLoginPromotionNotification(defaultMessage);
                NotificationListPopup.this.mOlderNotificationLoader.loadNextPage();
            }
        });
    }

    private void setLoginPromotionNotification(String content) {
        if (!TextUtils.isEmpty(content)) {
            Builder builder = new Builder();
            builder.scheme("nakamap");
            builder.authority(NotificationValue.SHEME_AUTHORITY_LOGIN);
            NotificationValue notification = new NotificationValue(null, null, content, null, null, null, builder.build().toString(), System.currentTimeMillis(), new ArrayList(), new ArrayList(), new ArrayList());
            final List<NotificationValue> list = new ArrayList();
            list.add(notification);
            this.mListView.post(new Runnable() {
                public void run() {
                    Adapter adapter = (Adapter) NotificationListPopup.this.mAdapterRef.get();
                    if (adapter != null) {
                        adapter.putNotifications(list);
                    }
                }
            });
        }
    }

    private void onFetchNotifications(List<NotificationValue> notifications) {
        NotificationUtil.filter(notifications);
        for (NotificationValue notification : notifications) {
            if (sAdIdList != null && sAdIdList.contains(notification.getId())) {
                notification.getDisplayHook().clear();
                Log.i("[notification]", "already displayed: id " + notification.getId());
            }
        }
        Adapter adapter = (Adapter) this.mAdapterRef.get();
        if (adapter != null) {
            adapter.putNotifications(notifications);
        }
        if (adapter.getCount() == 0) {
            this.mListView.setVisibility(4);
            this.mNoListItemsView.setVisibility(0);
            return;
        }
        this.mListView.setVisibility(0);
        this.mNoListItemsView.setVisibility(4);
    }

    public static void clearAdList() {
        if (sAdIdList != null) {
            sAdIdList.clear();
        }
    }

    private void setupLoginSection(final boolean bind) {
        final View contentView = getContentView();
        contentView.post(new Runnable() {
            public void run() {
                LinearLayout loginButton = (LinearLayout) contentView.findViewById(R.id.lobi_notification_login_button);
                if (bind) {
                    TextView message = (TextView) contentView.findViewById(R.id.lobi_notification_login_message);
                    TextView description = (TextView) contentView.findViewById(R.id.lobi_notification_login_description);
                    TextView label = (TextView) contentView.findViewById(R.id.lobi_notification_login_button_label);
                    loginButton.setBackgroundResource(R.drawable.lobi_btn_s_orange);
                    message.setText(NotificationListPopup.this.mContext.getString(R.string.lobisdk_check_activity));
                    description.setText(NotificationListPopup.this.mContext.getString(R.string.lobisdk_lobi_app_features));
                    label.setText(NotificationListPopup.this.mContext.getString(R.string.lobisdk_open_main_lobi));
                    loginButton.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            SDKBridge.openMainLobi(NotificationListPopup.this.mContext);
                        }
                    });
                    return;
                }
                loginButton.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        AccountUtil.bindToLobiAccount(4);
                    }
                });
            }
        });
    }
}
