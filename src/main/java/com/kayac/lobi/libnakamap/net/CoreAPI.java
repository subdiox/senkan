package com.kayac.lobi.libnakamap.net;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri.Builder;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;
import com.google.android.gcm.GCMConstants;
import com.kayac.lobi.libnakamap.net.APIDef.GetGroupChatWithExIdV2;
import com.kayac.lobi.libnakamap.net.APIDef.GetGroupWithExIdV2;
import com.kayac.lobi.libnakamap.net.APIDef.GetGroupsV2;
import com.kayac.lobi.libnakamap.net.APIDef.GetGroupsV3;
import com.kayac.lobi.libnakamap.net.APIDef.GetMeBinded;
import com.kayac.lobi.libnakamap.net.APIDef.PostGroupChat.RequestKey;
import com.kayac.lobi.libnakamap.net.APIDef.PostGroupJoinWithExIdV2;
import com.kayac.lobi.libnakamap.net.APIDef.PostGroupJoinWithGroupUidV2;
import com.kayac.lobi.libnakamap.net.APIDef.PostGroupPrivacyV2;
import com.kayac.lobi.libnakamap.net.APIDef.PostGroupTransferExIdV2;
import com.kayac.lobi.libnakamap.net.APIRes.GetAdNewAd;
import com.kayac.lobi.libnakamap.net.APIRes.GetAdNewAdMapper;
import com.kayac.lobi.libnakamap.net.APIRes.GetApp;
import com.kayac.lobi.libnakamap.net.APIRes.GetAppData;
import com.kayac.lobi.libnakamap.net.APIRes.GetAppDataMapper;
import com.kayac.lobi.libnakamap.net.APIRes.GetAppMapper;
import com.kayac.lobi.libnakamap.net.APIRes.GetDefaultUser;
import com.kayac.lobi.libnakamap.net.APIRes.GetDefaultUserContacts;
import com.kayac.lobi.libnakamap.net.APIRes.GetDefaultUserContactsMapper;
import com.kayac.lobi.libnakamap.net.APIRes.GetDefaultUserFollowers;
import com.kayac.lobi.libnakamap.net.APIRes.GetDefaultUserFollowersMapper;
import com.kayac.lobi.libnakamap.net.APIRes.GetDefaultUserMapper;
import com.kayac.lobi.libnakamap.net.APIRes.GetGroup;
import com.kayac.lobi.libnakamap.net.APIRes.GetGroupChat;
import com.kayac.lobi.libnakamap.net.APIRes.GetGroupChatMapper;
import com.kayac.lobi.libnakamap.net.APIRes.GetGroupChatReplies;
import com.kayac.lobi.libnakamap.net.APIRes.GetGroupChatRepliesMapper;
import com.kayac.lobi.libnakamap.net.APIRes.GetGroupChatV2;
import com.kayac.lobi.libnakamap.net.APIRes.GetGroupChatV2Mapper;
import com.kayac.lobi.libnakamap.net.APIRes.GetGroupMapper;
import com.kayac.lobi.libnakamap.net.APIRes.GetGroupV2;
import com.kayac.lobi.libnakamap.net.APIRes.GetGroupV2Mapper;
import com.kayac.lobi.libnakamap.net.APIRes.GetGroups;
import com.kayac.lobi.libnakamap.net.APIRes.GetGroupsMapper;
import com.kayac.lobi.libnakamap.net.APIRes.GetMe;
import com.kayac.lobi.libnakamap.net.APIRes.GetMeBindedMapper;
import com.kayac.lobi.libnakamap.net.APIRes.GetMeBlockingUsers;
import com.kayac.lobi.libnakamap.net.APIRes.GetMeBlockingUsersMapper;
import com.kayac.lobi.libnakamap.net.APIRes.GetMeChatFriends;
import com.kayac.lobi.libnakamap.net.APIRes.GetMeChatFriendsMapper;
import com.kayac.lobi.libnakamap.net.APIRes.GetMeContacts;
import com.kayac.lobi.libnakamap.net.APIRes.GetMeContactsMapper;
import com.kayac.lobi.libnakamap.net.APIRes.GetMeContactsRecommended;
import com.kayac.lobi.libnakamap.net.APIRes.GetMeContactsRecommendedMapper;
import com.kayac.lobi.libnakamap.net.APIRes.GetMeMapper;
import com.kayac.lobi.libnakamap.net.APIRes.GetMeProfileVisibleGroups;
import com.kayac.lobi.libnakamap.net.APIRes.GetMeProfileVisibleGroupsMapper;
import com.kayac.lobi.libnakamap.net.APIRes.GetMeSettings;
import com.kayac.lobi.libnakamap.net.APIRes.GetMeSettingsMapper;
import com.kayac.lobi.libnakamap.net.APIRes.GetMeSettingsV2;
import com.kayac.lobi.libnakamap.net.APIRes.GetMeSettingsV2Mapper;
import com.kayac.lobi.libnakamap.net.APIRes.GetNoahBanner;
import com.kayac.lobi.libnakamap.net.APIRes.GetNoahBannerMapper;
import com.kayac.lobi.libnakamap.net.APIRes.GetNonce;
import com.kayac.lobi.libnakamap.net.APIRes.GetNonceMapper;
import com.kayac.lobi.libnakamap.net.APIRes.GetNotifications;
import com.kayac.lobi.libnakamap.net.APIRes.GetNotificationsMapper;
import com.kayac.lobi.libnakamap.net.APIRes.GetPing;
import com.kayac.lobi.libnakamap.net.APIRes.GetPingMapper;
import com.kayac.lobi.libnakamap.net.APIRes.GetPublicGroups;
import com.kayac.lobi.libnakamap.net.APIRes.GetPublicGroupsMapper;
import com.kayac.lobi.libnakamap.net.APIRes.GetPublicGroupsSearch;
import com.kayac.lobi.libnakamap.net.APIRes.GetPublicGroupsSearchMapper;
import com.kayac.lobi.libnakamap.net.APIRes.GetPublicGroupsTree;
import com.kayac.lobi.libnakamap.net.APIRes.GetPublicGroupsTreeMapper;
import com.kayac.lobi.libnakamap.net.APIRes.GetRanking;
import com.kayac.lobi.libnakamap.net.APIRes.GetRankingMapper;
import com.kayac.lobi.libnakamap.net.APIRes.GetRankings;
import com.kayac.lobi.libnakamap.net.APIRes.GetRankingsMapper;
import com.kayac.lobi.libnakamap.net.APIRes.GetSdkReport;
import com.kayac.lobi.libnakamap.net.APIRes.GetSdkReportMapper;
import com.kayac.lobi.libnakamap.net.APIRes.GetSdkStartup;
import com.kayac.lobi.libnakamap.net.APIRes.GetSdkStartupMapper;
import com.kayac.lobi.libnakamap.net.APIRes.GetSignupMessage;
import com.kayac.lobi.libnakamap.net.APIRes.GetSignupMessageMapper;
import com.kayac.lobi.libnakamap.net.APIRes.GetSignupPromote;
import com.kayac.lobi.libnakamap.net.APIRes.GetSignupPromoteMapper;
import com.kayac.lobi.libnakamap.net.APIRes.GetStampUnlocked;
import com.kayac.lobi.libnakamap.net.APIRes.GetStampUnlockedMapper;
import com.kayac.lobi.libnakamap.net.APIRes.GetStamps;
import com.kayac.lobi.libnakamap.net.APIRes.GetStampsMapper;
import com.kayac.lobi.libnakamap.net.APIRes.GetTerms;
import com.kayac.lobi.libnakamap.net.APIRes.GetTermsMapper;
import com.kayac.lobi.libnakamap.net.APIRes.GetUser;
import com.kayac.lobi.libnakamap.net.APIRes.GetUserMapper;
import com.kayac.lobi.libnakamap.net.APIRes.GetUserV2;
import com.kayac.lobi.libnakamap.net.APIRes.GetUserV2Mapper;
import com.kayac.lobi.libnakamap.net.APIRes.GetUserV3;
import com.kayac.lobi.libnakamap.net.APIRes.GetUserV3Mapper;
import com.kayac.lobi.libnakamap.net.APIRes.GetUserVisibleGroups;
import com.kayac.lobi.libnakamap.net.APIRes.GetUserVisibleGroupsMapper;
import com.kayac.lobi.libnakamap.net.APIRes.GetUsers;
import com.kayac.lobi.libnakamap.net.APIRes.GetUsersMapper;
import com.kayac.lobi.libnakamap.net.APIRes.GetUsersSearch;
import com.kayac.lobi.libnakamap.net.APIRes.GetUsersSearchMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostAccusations;
import com.kayac.lobi.libnakamap.net.APIRes.PostAccusationsMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostAdTrackingClick;
import com.kayac.lobi.libnakamap.net.APIRes.PostAdTrackingClickMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostAdTrackingConversion;
import com.kayac.lobi.libnakamap.net.APIRes.PostAdTrackingConversionMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostAdTrackingImpression;
import com.kayac.lobi.libnakamap.net.APIRes.PostAdTrackingImpressionMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostAppData;
import com.kayac.lobi.libnakamap.net.APIRes.PostAppDataMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostAppDataRemove;
import com.kayac.lobi.libnakamap.net.APIRes.PostAppDataRemoveMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostBindStart;
import com.kayac.lobi.libnakamap.net.APIRes.PostBindStartMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostDefaultMeContacts;
import com.kayac.lobi.libnakamap.net.APIRes.PostDefaultMeContactsMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostDefaultMeContactsRemove;
import com.kayac.lobi.libnakamap.net.APIRes.PostDefaultMeContactsRemoveMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroup;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupChat;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupChatMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupChatRemove;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupChatRemoveMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupExId;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupExIdMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupIcon;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupIconMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupJoinWithExId;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupJoinWithExIdMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupJoinWithGroupUid;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupJoinWithGroupUidMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupKick;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupKickExId;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupKickExIdMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupKickMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupMembers;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupMembersExId;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupMembersExIdMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupMembersMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupPart;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupPartExId;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupPartExIdMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupPartMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupPrivacy;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupPrivacyMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupPush;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupPushMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupRemove;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupRemoveExId;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupRemoveExIdMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupRemoveMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupTransferExId;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupTransferExIdMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupVisibility;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupVisibilityMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupWallpaper;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupWallpaperMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupWallpaperRemove;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupWallpaperRemoveMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroups;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroups1on1s;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroups1on1sMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupsMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostInvitationsRecipients;
import com.kayac.lobi.libnakamap.net.APIRes.PostInvitationsRecipientsMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostMeBlockingUsers;
import com.kayac.lobi.libnakamap.net.APIRes.PostMeBlockingUsersMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostMeBlockingUsersRemove;
import com.kayac.lobi.libnakamap.net.APIRes.PostMeBlockingUsersRemoveMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostMeContacts;
import com.kayac.lobi.libnakamap.net.APIRes.PostMeContactsExId;
import com.kayac.lobi.libnakamap.net.APIRes.PostMeContactsExIdMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostMeContactsMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostMeContactsRemove;
import com.kayac.lobi.libnakamap.net.APIRes.PostMeContactsRemoveExId;
import com.kayac.lobi.libnakamap.net.APIRes.PostMeContactsRemoveExIdMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostMeContactsRemoveMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostMeCover;
import com.kayac.lobi.libnakamap.net.APIRes.PostMeCoverMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostMeExId;
import com.kayac.lobi.libnakamap.net.APIRes.PostMeExIdMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostMeExternalContacts;
import com.kayac.lobi.libnakamap.net.APIRes.PostMeExternalContactsMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostMeIcon;
import com.kayac.lobi.libnakamap.net.APIRes.PostMeIconMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostMeProfile;
import com.kayac.lobi.libnakamap.net.APIRes.PostMeProfileMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostMeRemoveConfirm;
import com.kayac.lobi.libnakamap.net.APIRes.PostMeRemoveConfirmMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostMeSettingsSearchable;
import com.kayac.lobi.libnakamap.net.APIRes.PostMeSettingsSearchableMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostNotifyContacts;
import com.kayac.lobi.libnakamap.net.APIRes.PostNotifyContactsMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostOauthAccessToken;
import com.kayac.lobi.libnakamap.net.APIRes.PostOauthAccessTokenMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostPublicGroups;
import com.kayac.lobi.libnakamap.net.APIRes.PostPublicGroupsMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostRankingScore;
import com.kayac.lobi.libnakamap.net.APIRes.PostRankingScoreMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostSignupFree;
import com.kayac.lobi.libnakamap.net.APIRes.PostSignupFreeMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostStampUnlock;
import com.kayac.lobi.libnakamap.net.APIRes.PostStampUnlockMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostUserExIdDecrypt;
import com.kayac.lobi.libnakamap.net.APIRes.PostUserExIdDecryptMapper;
import com.kayac.lobi.libnakamap.net.APIRes.PostUserExIdEncrypt;
import com.kayac.lobi.libnakamap.net.APIRes.PostUserExIdEncryptMapper;
import com.kayac.lobi.libnakamap.net.APIUtil.ByteArrayResponseHandler;
import com.kayac.lobi.libnakamap.net.APIUtil.Endpoint;
import com.kayac.lobi.libnakamap.net.APIUtil.JSONArrayResponseHandler;
import com.kayac.lobi.libnakamap.net.APIUtil.JSONObjectResponseHandler;
import com.kayac.lobi.libnakamap.net.APIUtil.Product;
import com.kayac.lobi.libnakamap.utils.ApiUtils;
import com.kayac.lobi.libnakamap.utils.Log;
import com.kayac.lobi.libnakamap.utils.ModuleUtil;
import com.kayac.lobi.sdk.R;
import com.kayac.lobi.sdk.auth.AccountUtil;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CoreAPI {
    public static final String INVALID_TOKEN = "Unauthorized, Token Invalid";
    private static Endpoint sEndpoint = new Product();
    private static final ExecutorService sExecutorService = Executors.newCachedThreadPool();
    private static final Handler sHandler = new Handler(Looper.getMainLooper());
    private static TokenChecker sTokenChecker;

    interface APICallback<T> {
        void onError(int i, String str);

        void onError(Throwable th);

        void onResponse(T t);
    }

    public static abstract class DefaultAPICallback<T> implements APICallback<T> {
        private final Context mContext;
        private DialogInterface mProgress;

        public DefaultAPICallback(Context context) {
            this.mContext = context;
        }

        public void setProgress(DialogInterface progress) {
            this.mProgress = progress;
        }

        public DialogInterface getProgress() {
            return this.mProgress;
        }

        public void onResponse(T t) {
            if (this.mProgress != null) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        DefaultAPICallback.this.mProgress.dismiss();
                        DefaultAPICallback.this.mProgress = null;
                    }
                });
            }
        }

        public void onError(final int statusCode, String responseBody) {
            if (statusCode < 500) {
                AccountUtil.clearTokenRefreshedDate();
            }
            final StringBuilder messageBuilder = new StringBuilder();
            Log.v("lobi-sdk", "[api] onError: (" + statusCode + ") " + responseBody);
            try {
                JSONArray jsonArray = new JSONObject(responseBody).optJSONArray(GCMConstants.EXTRA_ERROR);
                if (jsonArray != null) {
                    Log.v("lobi-sdk", "[api] has error: " + jsonArray);
                    int len = jsonArray.length();
                    for (int i = 0; i < len; i++) {
                        String msg = jsonArray.optString(i);
                        if (!TextUtils.isEmpty(msg)) {
                            if (messageBuilder.length() > 0) {
                                messageBuilder.append('\n');
                            }
                            messageBuilder.append(msg);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            runOnUiThread(new Runnable() {
                public void run() {
                    if (DefaultAPICallback.this.mProgress != null) {
                        DefaultAPICallback.this.mProgress.dismiss();
                        DefaultAPICallback.this.mProgress = null;
                    }
                    if (DefaultAPICallback.this.mContext == null) {
                        return;
                    }
                    if (statusCode < 500) {
                        String message = messageBuilder.toString();
                        if (TextUtils.isEmpty(messageBuilder)) {
                            message = DefaultAPICallback.this.mContext.getString(R.string.lobi_something_wrong);
                        }
                        Toast.makeText(DefaultAPICallback.this.mContext, message, 0).show();
                        return;
                    }
                    Toast.makeText(DefaultAPICallback.this.mContext, R.string.lobi_something_wrong, 0).show();
                }
            });
        }

        public void onError(Throwable e) {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (DefaultAPICallback.this.mProgress != null) {
                        DefaultAPICallback.this.mProgress.dismiss();
                        DefaultAPICallback.this.mProgress = null;
                    }
                    if (DefaultAPICallback.this.mContext != null) {
                        Toast.makeText(DefaultAPICallback.this.mContext, R.string.lobi_something_wrong, 0).show();
                    }
                }
            });
        }

        protected Context getContext() {
            return this.mContext;
        }

        public void runOnUiThread(Runnable runnable) {
            CoreAPI.sHandler.post(runnable);
        }
    }

    public interface TokenChecker {
        void checkToken();
    }

    private CoreAPI() {
    }

    public static Endpoint getEndpoint() {
        return sEndpoint;
    }

    public static void setEndpoint(Endpoint endpoint) {
        sEndpoint = endpoint;
    }

    public static final ExecutorService getExecutorService() {
        return sExecutorService;
    }

    public static synchronized void setTokenChecker(TokenChecker checker) {
        synchronized (CoreAPI.class) {
            sTokenChecker = checker;
        }
    }

    public static synchronized TokenChecker getTokenChecker() {
        TokenChecker tokenChecker;
        synchronized (CoreAPI.class) {
            tokenChecker = sTokenChecker;
        }
        return tokenChecker;
    }

    public static final boolean containsError(String responseBody, String error) {
        try {
            JSONArray jsonArray = new JSONObject(responseBody).optJSONArray(GCMConstants.EXTRA_ERROR);
            int len = jsonArray.length();
            for (int i = 0; i < len; i++) {
                if (error.equals(jsonArray.optString(i))) {
                    return true;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static final void getBytes(final String url, final APICallback<byte[]> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.sEndpoint, "GET", url);
                    ByteArrayResponseHandler handler = new ByteArrayResponseHandler();
                    byte[] bytes = (byte[]) APIUtil.execute(CoreAPI.sEndpoint, request, handler);
                    if (bytes == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(bytes);
                    }
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (IOException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IllegalStateException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postSignupFree(final Map<String, String> p, final APICallback<PostSignupFree> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                Map<String, String> params = ApiUtils.completeParamsWithPreparedExIdIfNecessary(p);
                try {
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostSignupFree.PATH).build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostSignupFree r = (PostSignupFree) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostSignupFreeMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void getGroupsV2(final Map<String, String> params, final APICallback<GetGroups> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), GetGroupsV2.PATH, params).build().toString());
                    JSONArrayResponseHandler handler = new JSONArrayResponseHandler();
                    GetGroups r = (GetGroups) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetGroupsMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void getGroupsV3(final Map<String, String> params, final APICallback<GetGroups> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), GetGroupsV3.PATH, params).build().toString());
                    JSONArrayResponseHandler handler = new JSONArrayResponseHandler();
                    GetGroups r = (GetGroups) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetGroupsMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postGroups(final Map<String, String> params, final APICallback<PostGroups> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostGroups.PATH).build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostGroups r = (PostGroups) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroupsMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postGroups1on1s(final Map<String, String> params, final APICallback<PostGroups1on1s> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostGroups1on1s.PATH).build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostGroups1on1s r = (PostGroups1on1s) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroups1on1sMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postGroupJoinWithGroupUid(final Map<String, String> params, final APICallback<PostGroupJoinWithGroupUid> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    String path = String.format(APIDef.PostGroupJoinWithGroupUid.PATH, new Object[]{params.get("uid")});
                    params.remove("uid");
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path).build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostGroupJoinWithGroupUid r = (PostGroupJoinWithGroupUid) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroupJoinWithGroupUidMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postGroupJoinWithExId(final Map<String, String> params, final APICallback<PostGroupJoinWithExId> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostGroupJoinWithExId.PATH).build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostGroupJoinWithExId r = (PostGroupJoinWithExId) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroupJoinWithExIdMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postGroupJoinWithGroupUidV2(final Map<String, String> params, final APICallback<PostGroupJoinWithGroupUid> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    String path = String.format(PostGroupJoinWithGroupUidV2.PATH, new Object[]{params.get("uid")});
                    params.remove("uid");
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path).build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostGroupJoinWithGroupUid r = (PostGroupJoinWithGroupUid) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroupJoinWithGroupUidMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postGroupJoinWithExIdV2(final Map<String, String> params, final APICallback<PostGroupJoinWithGroupUid> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), PostGroupJoinWithExIdV2.PATH).build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostGroupJoinWithGroupUid r = (PostGroupJoinWithGroupUid) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroupJoinWithGroupUidMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void getGroup(final Map<String, String> params, final APICallback<GetGroup> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    String path = String.format("/1/group/%s", new Object[]{params.get("uid")});
                    params.remove("uid");
                    HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path, params).build().toString());
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    GetGroup r = (GetGroup) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetGroupMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void getGroupV2(final Map<String, String> params, final APICallback<GetGroupV2> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    String path = String.format(APIDef.GetGroupV2.PATH, new Object[]{params.get("uid")});
                    params.remove("uid");
                    HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path, params).build().toString());
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    GetGroupV2 r = (GetGroupV2) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetGroupV2Mapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void getGroupWithExIdV2(final Map<String, String> params, final APICallback<GetGroup> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), GetGroupWithExIdV2.PATH, params).build().toString());
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    GetGroup r = (GetGroup) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetGroupMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postGroupPrivacy(final Map<String, String> params, final APICallback<PostGroupPrivacy> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    String path = String.format(APIDef.PostGroupPrivacy.PATH, new Object[]{params.get("uid")});
                    params.remove("uid");
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path).build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostGroupPrivacy r = (PostGroupPrivacy) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroupPrivacyMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postGroupPrivacyV2(final Map<String, String> params, final APICallback<PostGroupPrivacy> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    String path = String.format(PostGroupPrivacyV2.PATH, new Object[]{params.get("uid")});
                    params.remove("uid");
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path).build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostGroupPrivacy r = (PostGroupPrivacy) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroupPrivacyMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postGroupChat(final Map<String, String> params, final APICallback<PostGroupChat> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    HttpPost request;
                    CoreAPI.getTokenChecker().checkToken();
                    String path = String.format(APIDef.PostGroupChat.PATH, new Object[]{params.get("uid")});
                    params.remove("uid");
                    Builder builder = APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path);
                    String filePath = (String) params.get("image");
                    if (filePath == null || "stamp".equals(params.get(RequestKey.OPTION_IMAGE_TYPE))) {
                        request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", builder.build().toString(), params);
                    } else {
                        File file = new File(filePath);
                        params.remove("image");
                        request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", builder.build().toString(), params, "image", file);
                    }
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostGroupChat r = (PostGroupChat) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroupChatMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void getGroupChatV2(final Map<String, String> params, final APICallback<GetGroupChatV2> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    String path = String.format(APIDef.GetGroupChatV2.PATH, new Object[]{params.get("uid")});
                    params.remove("uid");
                    HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path, params).build().toString());
                    JSONArrayResponseHandler handler = new JSONArrayResponseHandler();
                    GetGroupChatV2 r = (GetGroupChatV2) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetGroupChatV2Mapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void getGroupChatWithExIdV2(final Map<String, String> params, final APICallback<GetGroupChat> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), GetGroupChatWithExIdV2.PATH, params).build().toString());
                    JSONArrayResponseHandler handler = new JSONArrayResponseHandler();
                    GetGroupChat r = (GetGroupChat) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetGroupChatMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void getGroupChatReplies(final Map<String, String> params, final APICallback<GetGroupChatReplies> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    String path = String.format(APIDef.GetGroupChatReplies.PATH, new Object[]{params.get("uid")});
                    params.remove("uid");
                    HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path, params).build().toString());
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    GetGroupChatReplies r = (GetGroupChatReplies) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetGroupChatRepliesMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postGroupMembers(final Map<String, String> params, final APICallback<PostGroupMembers> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    String path = String.format(APIDef.PostGroupMembers.PATH, new Object[]{params.get("uid")});
                    params.remove("uid");
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path).build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostGroupMembers r = (PostGroupMembers) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroupMembersMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postGroupMembersExId(final Map<String, String> params, final APICallback<PostGroupMembersExId> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostGroupMembersExId.PATH).build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostGroupMembersExId r = (PostGroupMembersExId) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroupMembersExIdMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postGroupChatRemove(final Map<String, String> params, final APICallback<PostGroupChatRemove> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    String path = String.format(APIDef.PostGroupChatRemove.PATH, new Object[]{params.get("uid")});
                    params.remove("uid");
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path).build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostGroupChatRemove r = (PostGroupChatRemove) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroupChatRemoveMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postGroupPart(final Map<String, String> params, final APICallback<PostGroupPart> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    String path = String.format(APIDef.PostGroupPart.PATH, new Object[]{params.get("uid")});
                    params.remove("uid");
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path).build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostGroupPart r = (PostGroupPart) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroupPartMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postGroupPartExId(final Map<String, String> params, final APICallback<PostGroupPartExId> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostGroupPartExId.PATH).build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostGroupPartExId r = (PostGroupPartExId) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroupPartExIdMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postGroupKick(final Map<String, String> params, final APICallback<PostGroupKick> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    String path = String.format(APIDef.PostGroupKick.PATH, new Object[]{params.get("uid")});
                    params.remove("uid");
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path).build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostGroupKick r = (PostGroupKick) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroupKickMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postGroupKickExId(final Map<String, String> params, final APICallback<PostGroupKickExId> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostGroupKickExId.PATH).build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostGroupKickExId r = (PostGroupKickExId) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroupKickExIdMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postGroupPush(final Map<String, String> params, final APICallback<PostGroupPush> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    String path = String.format(APIDef.PostGroupPush.PATH, new Object[]{params.get("uid")});
                    params.remove("uid");
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path).build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostGroupPush r = (PostGroupPush) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroupPushMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postGroupRemove(final Map<String, String> params, final APICallback<PostGroupRemove> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    String path = String.format(APIDef.PostGroupRemove.PATH, new Object[]{params.get("uid")});
                    params.remove("uid");
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path).build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostGroupRemove r = (PostGroupRemove) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroupRemoveMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postGroupRemoveExId(final Map<String, String> params, final APICallback<PostGroupRemoveExId> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostGroupRemoveExId.PATH).build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostGroupRemoveExId r = (PostGroupRemoveExId) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroupRemoveExIdMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postGroup(final Map<String, String> params, final APICallback<PostGroup> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    String path = String.format("/1/group/%s", new Object[]{params.get("uid")});
                    params.remove("uid");
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path).build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostGroup r = (PostGroup) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroupMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postGroupExId(final Map<String, String> params, final APICallback<PostGroupExId> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostGroupExId.PATH).build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostGroupExId r = (PostGroupExId) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroupExIdMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postGroupIcon(final Map<String, String> params, final APICallback<PostGroupIcon> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    HttpPost request;
                    CoreAPI.getTokenChecker().checkToken();
                    String path = String.format(APIDef.PostGroupIcon.PATH, new Object[]{params.get("uid")});
                    params.remove("uid");
                    Builder builder = APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path);
                    String filePath = (String) params.get("icon");
                    if (filePath != null) {
                        File file = new File(filePath);
                        params.remove("icon");
                        request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", builder.build().toString(), params, "icon", file);
                    } else {
                        request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", builder.build().toString(), params);
                    }
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostGroupIcon r = (PostGroupIcon) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroupIconMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postGroupWallpaper(final Map<String, String> params, final APICallback<PostGroupWallpaper> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    HttpPost request;
                    CoreAPI.getTokenChecker().checkToken();
                    String path = String.format(APIDef.PostGroupWallpaper.PATH, new Object[]{params.get("uid")});
                    params.remove("uid");
                    Builder builder = APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path);
                    String filePath = (String) params.get(APIDef.PostGroupWallpaper.RequestKey.WALLPAPER);
                    if (filePath != null) {
                        File file = new File(filePath);
                        params.remove(APIDef.PostGroupWallpaper.RequestKey.WALLPAPER);
                        request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", builder.build().toString(), params, APIDef.PostGroupWallpaper.RequestKey.WALLPAPER, file);
                    } else {
                        request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", builder.build().toString(), params);
                    }
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostGroupWallpaper r = (PostGroupWallpaper) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroupWallpaperMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postGroupWallpaperRemove(final Map<String, String> params, final APICallback<PostGroupWallpaperRemove> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    String path = String.format(APIDef.PostGroupWallpaperRemove.PATH, new Object[]{params.get("uid")});
                    params.remove("uid");
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path).build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostGroupWallpaperRemove r = (PostGroupWallpaperRemove) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroupWallpaperRemoveMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postGroupTransferExId(final Map<String, String> params, final APICallback<PostGroupTransferExId> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostGroupTransferExId.PATH).build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostGroupTransferExId r = (PostGroupTransferExId) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroupTransferExIdMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postGroupTransferExIdV2(final Map<String, String> params, final APICallback<PostGroupTransferExId> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), PostGroupTransferExIdV2.PATH).build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostGroupTransferExId r = (PostGroupTransferExId) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroupTransferExIdMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postMeContacts(final Map<String, String> params, final APICallback<PostMeContacts> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), "/1/me/contacts").build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostMeContacts r = (PostMeContacts) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostMeContactsMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postMeContactsExId(final Map<String, String> params, final APICallback<PostMeContactsExId> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), "/1/me/contacts").build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostMeContactsExId r = (PostMeContactsExId) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostMeContactsExIdMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void getMeContacts(final Map<String, String> params, final APICallback<GetMeContacts> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), "/1/me/contacts", params).build().toString());
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    GetMeContacts r = (GetMeContacts) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetMeContactsMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void getMeContactsRecommended(final Map<String, String> params, final APICallback<GetMeContactsRecommended> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.GetMeContactsRecommended.PATH, params).build().toString());
                    JSONArrayResponseHandler handler = new JSONArrayResponseHandler();
                    GetMeContactsRecommended r = (GetMeContactsRecommended) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetMeContactsRecommendedMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postMeContactsRemove(final Map<String, String> params, final APICallback<PostMeContactsRemove> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), "/1/me/contacts/remove").build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostMeContactsRemove r = (PostMeContactsRemove) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostMeContactsRemoveMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postMeContactsRemoveExId(final Map<String, String> params, final APICallback<PostMeContactsRemoveExId> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), "/1/me/contacts/remove").build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostMeContactsRemoveExId r = (PostMeContactsRemoveExId) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostMeContactsRemoveExIdMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postMeExId(final Map<String, String> params, final APICallback<PostMeExId> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostMeExId.PATH).build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostMeExId r = (PostMeExId) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostMeExIdMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postMeProfile(final Map<String, String> params, final APICallback<PostMeProfile> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostMeProfile.PATH).build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostMeProfile r = (PostMeProfile) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostMeProfileMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postMeBlockingUsers(final Map<String, String> params, final APICallback<PostMeBlockingUsers> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), "/1/me/blocking_users").build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostMeBlockingUsers r = (PostMeBlockingUsers) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostMeBlockingUsersMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postMeBlockingUsersRemove(final Map<String, String> params, final APICallback<PostMeBlockingUsersRemove> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostMeBlockingUsersRemove.PATH).build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostMeBlockingUsersRemove r = (PostMeBlockingUsersRemove) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostMeBlockingUsersRemoveMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void getMeBlockingUsers(final Map<String, String> params, final APICallback<GetMeBlockingUsers> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), "/1/me/blocking_users", params).build().toString());
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    GetMeBlockingUsers r = (GetMeBlockingUsers) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetMeBlockingUsersMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postMeExternalContacts(final Map<String, String> params, final APICallback<List<PostMeExternalContacts>> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostMeExternalContacts.PATH).build().toString(), params);
                    JSONArrayResponseHandler handler = new JSONArrayResponseHandler();
                    List<PostMeExternalContacts> r = (List) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostMeExternalContactsMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postMeIcon(final Map<String, String> params, final APICallback<PostMeIcon> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    HttpPost request;
                    CoreAPI.getTokenChecker().checkToken();
                    Builder builder = APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostMeIcon.PATH);
                    String filePath = (String) params.get("icon");
                    if (filePath != null) {
                        File file = new File(filePath);
                        params.remove("icon");
                        request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", builder.build().toString(), params, "icon", file);
                    } else {
                        request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", builder.build().toString(), params);
                    }
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostMeIcon r = (PostMeIcon) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostMeIconMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postMeCover(final Map<String, String> params, final APICallback<PostMeCover> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    HttpPost request;
                    CoreAPI.getTokenChecker().checkToken();
                    Builder builder = APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostMeCover.PATH);
                    String filePath = (String) params.get(APIDef.PostMeCover.RequestKey.COVER);
                    if (filePath != null) {
                        File file = new File(filePath);
                        params.remove(APIDef.PostMeCover.RequestKey.COVER);
                        request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", builder.build().toString(), params, APIDef.PostMeCover.RequestKey.COVER, file);
                    } else {
                        request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", builder.build().toString(), params);
                    }
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostMeCover r = (PostMeCover) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostMeCoverMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postMeRemoveConfirm(final Map<String, String> params, final APICallback<PostMeRemoveConfirm> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostMeRemoveConfirm.PATH).build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostMeRemoveConfirm r = (PostMeRemoveConfirm) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostMeRemoveConfirmMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void getMe(final Map<String, String> params, final APICallback<GetMe> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.GetMe.PATH, params).build().toString());
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    GetMe r = (GetMe) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetMeMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void getMeSettings(final Map<String, String> params, final APICallback<GetMeSettings> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.GetMeSettings.PATH, params).build().toString());
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    GetMeSettings r = (GetMeSettings) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetMeSettingsMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void getMeSettingsV2(final Map<String, String> params, final APICallback<GetMeSettingsV2> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.GetMeSettingsV2.PATH, params).build().toString());
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    GetMeSettingsV2 r = (GetMeSettingsV2) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetMeSettingsV2Mapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postMeSettingsSearchable(final Map<String, String> params, final APICallback<PostMeSettingsSearchable> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostMeSettingsSearchable.PATH).build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostMeSettingsSearchable r = (PostMeSettingsSearchable) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostMeSettingsSearchableMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void getUser(final Map<String, String> params, final APICallback<GetUser> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    String path = String.format(APIDef.GetUser.PATH, new Object[]{params.get("uid")});
                    params.remove("uid");
                    HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path, params).build().toString());
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    GetUser r = (GetUser) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetUserMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void getUserV2(final Map<String, String> params, final APICallback<GetUserV2> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    String path = String.format(APIDef.GetUserV2.PATH, new Object[]{params.get("uid")});
                    params.remove("uid");
                    HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path, params).build().toString());
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    GetUserV2 r = (GetUserV2) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetUserV2Mapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void getUserV3(final Map<String, String> params, final APICallback<GetUserV3> callback) {
        if (ModuleUtil.recIsAvailable()) {
            params.put(APIDef.GetUserV3.RequestKey.SECRET_MODE, ModuleUtil.recIsSecretMode() ? "1" : "0");
        }
        params.put("fields", "posted_videos_count,banner");
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    String path = String.format(APIDef.GetUserV3.PATH, new Object[]{params.get("uid")});
                    params.remove("uid");
                    HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path, params).build().toString());
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    GetUserV3 r = (GetUserV3) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetUserV3Mapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void getUsers(final Map<String, String> params, final APICallback<GetUsers> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.GetUsers.PATH, params).build().toString());
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    GetUsers r = (GetUsers) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetUsersMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void getUsersSearch(final Map<String, String> params, final APICallback<GetUsersSearch> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.GetUsersSearch.PATH, params).build().toString());
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    GetUsersSearch r = (GetUsersSearch) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetUsersSearchMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postInvitationsRecipients(final Map<String, String> params, final APICallback<PostInvitationsRecipients> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    String path = String.format(APIDef.PostInvitationsRecipients.PATH, new Object[]{params.get("uid")});
                    params.remove("uid");
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path).build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostInvitationsRecipients r = (PostInvitationsRecipients) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostInvitationsRecipientsMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void getStamps(final Map<String, String> params, final APICallback<GetStamps> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.GetStamps.PATH, params).build().toString());
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    GetStamps r = (GetStamps) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetStampsMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postStampUnlock(final Map<String, String> params, final APICallback<PostStampUnlock> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    String path = String.format(APIDef.PostStampUnlock.PATH, new Object[]{params.get("uid")});
                    params.remove("uid");
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path).build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostStampUnlock r = (PostStampUnlock) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostStampUnlockMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void getStampUnlocked(final Map<String, String> params, final APICallback<GetStampUnlocked> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    String path = String.format(APIDef.GetStampUnlocked.PATH, new Object[]{params.get("uid")});
                    params.remove("uid");
                    HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path, params).build().toString());
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    GetStampUnlocked r = (GetStampUnlocked) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetStampUnlockedMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void getApp(final Map<String, String> params, final APICallback<GetApp> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    String path = String.format(APIDef.GetApp.PATH, new Object[]{params.get("uid")});
                    params.remove("uid");
                    HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path, params).build().toString());
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    GetApp r = (GetApp) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetAppMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postAccusations(final Map<String, String> params, final APICallback<PostAccusations> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostAccusations.PATH).build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostAccusations r = (PostAccusations) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostAccusationsMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void getPublicGroups(final Map<String, String> params, final APICallback<GetPublicGroups> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), "/1/public_groups", params).build().toString());
                    JSONArrayResponseHandler handler = new JSONArrayResponseHandler();
                    GetPublicGroups r = (GetPublicGroups) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetPublicGroupsMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postPublicGroups(final Map<String, String> params, final APICallback<PostPublicGroups> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), "/1/public_groups").build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostPublicGroups r = (PostPublicGroups) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostPublicGroupsMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void getPublicGroupsTree(final Map<String, String> params, final APICallback<GetPublicGroupsTree> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.GetPublicGroupsTree.PATH, params).build().toString());
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    GetPublicGroupsTree r = (GetPublicGroupsTree) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetPublicGroupsTreeMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void getPublicGroupsSearch(final Map<String, String> params, final APICallback<GetPublicGroupsSearch> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.GetPublicGroupsSearch.PATH, params).build().toString());
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    GetPublicGroupsSearch r = (GetPublicGroupsSearch) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetPublicGroupsSearchMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postGroupVisibility(final Map<String, String> params, final APICallback<PostGroupVisibility> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    String path = String.format(APIDef.PostGroupVisibility.PATH, new Object[]{params.get("uid")});
                    params.remove("uid");
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path).build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostGroupVisibility r = (PostGroupVisibility) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroupVisibilityMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void getUserVisibleGroups(final Map<String, String> params, final APICallback<GetUserVisibleGroups> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    String path = String.format(APIDef.GetUserVisibleGroups.PATH, new Object[]{params.get("uid")});
                    params.remove("uid");
                    HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path, params).build().toString());
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    GetUserVisibleGroups r = (GetUserVisibleGroups) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetUserVisibleGroupsMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void getMeProfileVisibleGroups(final Map<String, String> params, final APICallback<GetMeProfileVisibleGroups> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.GetMeProfileVisibleGroups.PATH, params).build().toString());
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    GetMeProfileVisibleGroups r = (GetMeProfileVisibleGroups) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetMeProfileVisibleGroupsMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postNotifyContacts(final Map<String, String> params, final APICallback<PostNotifyContacts> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostNotifyContacts.PATH).build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostNotifyContacts r = (PostNotifyContacts) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostNotifyContactsMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postBindStart(final Map<String, String> params, final APICallback<PostBindStart> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    Builder builder = APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostBindStart.PATH);
                    builder.appendQueryParameter(APIDef.PostBindStart.RequestKey.TID, (String) params.get(APIDef.PostBindStart.RequestKey.TID));
                    params.remove(APIDef.PostBindStart.RequestKey.TID);
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", builder.build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostBindStart r = (PostBindStart) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostBindStartMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postOauthAccessToken(final Map<String, String> params, final APICallback<PostOauthAccessToken> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostOauthAccessToken.PATH).build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostOauthAccessToken r = (PostOauthAccessToken) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostOauthAccessTokenMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void getSdkReport(final Map<String, String> params, final APICallback<GetSdkReport> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.GetSdkReport.PATH, params).build().toString());
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    GetSdkReport r = (GetSdkReport) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetSdkReportMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void getTerms(final Map<String, String> params, final APICallback<GetTerms> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.GetTerms.PATH, params).build().toString());
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    GetTerms r = (GetTerms) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetTermsMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postUserExIdEncrypt(final Map<String, String> params, final APICallback<PostUserExIdEncrypt> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostUserExIdEncrypt.PATH).build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostUserExIdEncrypt r = (PostUserExIdEncrypt) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostUserExIdEncryptMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postUserExIdDecrypt(final Map<String, String> params, final APICallback<PostUserExIdDecrypt> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostUserExIdDecrypt.PATH).build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostUserExIdDecrypt r = (PostUserExIdDecrypt) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostUserExIdDecryptMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void getNotifications(final Map<String, String> params, final APICallback<GetNotifications> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.GetNotifications.PATH, params).build().toString());
                    JSONArrayResponseHandler handler = new JSONArrayResponseHandler();
                    GetNotifications r = (GetNotifications) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetNotificationsMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void getNoahBanner(final Map<String, String> params, final APICallback<GetNoahBanner> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.GetNoahBanner.PATH, params).build().toString());
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    GetNoahBanner r = (GetNoahBanner) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetNoahBannerMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postRankingScore(final Map<String, String> params, final APICallback<PostRankingScore> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    String path = String.format(APIDef.PostRankingScore.PATH, new Object[]{params.get("ranking_id")});
                    params.remove("ranking_id");
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path).build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostRankingScore r = (PostRankingScore) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostRankingScoreMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void getRanking(final Map<String, String> params, final APICallback<GetRanking> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    String path = String.format(APIDef.GetRanking.PATH, new Object[]{params.get("ranking_id")});
                    params.remove("ranking_id");
                    HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path, params).build().toString());
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    GetRanking r = (GetRanking) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetRankingMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void getRankings(final Map<String, String> params, final APICallback<GetRankings> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.GetRankings.PATH, params).build().toString());
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    GetRankings r = (GetRankings) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetRankingsMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void getNonce(final Map<String, String> params, final APICallback<GetNonce> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.GetNonce.PATH, params).build().toString());
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    GetNonce r = (GetNonce) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetNonceMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void getPing(final Map<String, String> params, final APICallback<GetPing> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.GetPing.PATH, params).build().toString());
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    GetPing r = (GetPing) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetPingMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postAppData(final Map<String, String> params, final APICallback<PostAppData> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), "/1/appdata/").build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostAppData r = (PostAppData) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostAppDataMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void getAppData(final Map<String, String> params, final APICallback<GetAppData> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), "/1/appdata/", params).build().toString());
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    GetAppData r = (GetAppData) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetAppDataMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postAppDataRemove(final Map<String, String> params, final APICallback<PostAppDataRemove> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostAppDataRemove.PATH).build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostAppDataRemove r = (PostAppDataRemove) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostAppDataRemoveMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postAdTrackingImpression(final Map<String, String> params, final APICallback<PostAdTrackingImpression> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostAdTrackingImpression.PATH).build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostAdTrackingImpression r = (PostAdTrackingImpression) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostAdTrackingImpressionMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postAdTrackingClick(final Map<String, String> params, final APICallback<PostAdTrackingClick> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostAdTrackingClick.PATH).build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostAdTrackingClick r = (PostAdTrackingClick) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostAdTrackingClickMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postAdTrackingConversion(final Map<String, String> params, final APICallback<PostAdTrackingConversion> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostAdTrackingConversion.PATH).build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostAdTrackingConversion r = (PostAdTrackingConversion) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostAdTrackingConversionMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void getAdNewAd(final Map<String, String> params, final APICallback<GetAdNewAd> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.GetAdNewAd.PATH, params).build().toString());
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    GetAdNewAd r = (GetAdNewAd) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetAdNewAdMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void getSdkStartup(final Map<String, String> params, final APICallback<GetSdkStartup> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.GetSdkStartup.PATH, params).build().toString());
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    GetSdkStartup r = (GetSdkStartup) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetSdkStartupMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void getMeBinded(final Map<String, String> params, final APICallback<Boolean> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), GetMeBinded.PATH, params).build().toString());
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    Boolean r = (Boolean) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetMeBindedMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void getDefaultUser(final Map<String, String> params, final APICallback<GetDefaultUser> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    String path = String.format(APIDef.GetDefaultUser.PATH, new Object[]{params.get("uid")});
                    params.remove("uid");
                    HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path, params).build().toString());
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    GetDefaultUser r = (GetDefaultUser) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetDefaultUserMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postDefaultMeContacts(final Map<String, String> params, final APICallback<PostDefaultMeContacts> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostDefaultMeContacts.PATH).build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostDefaultMeContacts r = (PostDefaultMeContacts) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostDefaultMeContactsMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void postDefaultMeContactsRemove(final Map<String, String> params, final APICallback<PostDefaultMeContactsRemove> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostDefaultMeContactsRemove.PATH).build().toString(), params);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    PostDefaultMeContactsRemove r = (PostDefaultMeContactsRemove) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostDefaultMeContactsRemoveMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void getDefaultUserContacts(final Map<String, String> params, final APICallback<GetDefaultUserContacts> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    String path = String.format(APIDef.GetDefaultUserContacts.PATH, new Object[]{params.get("uid")});
                    params.remove("uid");
                    HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path, params).build().toString());
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    GetDefaultUserContacts r = (GetDefaultUserContacts) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetDefaultUserContactsMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void getDefaultUserFollowers(final Map<String, String> params, final APICallback<GetDefaultUserFollowers> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    String path = String.format(APIDef.GetDefaultUserFollowers.PATH, new Object[]{params.get("uid")});
                    params.remove("uid");
                    HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path, params).build().toString());
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    GetDefaultUserFollowers r = (GetDefaultUserFollowers) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetDefaultUserFollowersMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void getSignupMessage(final Map<String, String> params, final APICallback<GetSignupMessage> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.GetSignupMessage.PATH, params).build().toString());
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    GetSignupMessage r = (GetSignupMessage) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetSignupMessageMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void getSignupPromote(final Map<String, String> params, final APICallback<GetSignupPromote> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.GetSignupPromote.PATH, params).build().toString());
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    GetSignupPromote r = (GetSignupPromote) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetSignupPromoteMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }

    public static final void getMeChatFriends(final Map<String, String> params, final APICallback<GetMeChatFriends> callback) {
        sExecutorService.execute(new Runnable() {
            public void run() {
                try {
                    CoreAPI.getTokenChecker().checkToken();
                    HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.GetMeChatFriends.PATH, params).build().toString());
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    GetMeChatFriends r = (GetMeChatFriends) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetMeChatFriendsMapper.getInstance());
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (r == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(r);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    callback.onError(e);
                } catch (ClientProtocolException e2) {
                    e2.printStackTrace();
                    callback.onError(e2);
                } catch (IOException e3) {
                    e3.printStackTrace();
                    callback.onError(e3);
                }
            }
        });
    }
}
