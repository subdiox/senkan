package com.kayac.lobi.libnakamap.net;

import android.net.Uri.Builder;
import com.kayac.lobi.libnakamap.net.APIDef.GetGroupChatWithExIdV2;
import com.kayac.lobi.libnakamap.net.APIDef.GetGroupWithExIdV2;
import com.kayac.lobi.libnakamap.net.APIDef.GetGroupsV2;
import com.kayac.lobi.libnakamap.net.APIDef.GetGroupsV3;
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
import com.kayac.lobi.libnakamap.net.APIRes.GetMeBlockingUsers;
import com.kayac.lobi.libnakamap.net.APIRes.GetMeBlockingUsersMapper;
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
import com.kayac.lobi.libnakamap.net.APIRes.GetUserVisibleGroups;
import com.kayac.lobi.libnakamap.net.APIRes.GetUserVisibleGroupsMapper;
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
import com.kayac.lobi.libnakamap.net.APIUtil.JSONArrayResponseHandler;
import com.kayac.lobi.libnakamap.net.APIUtil.JSONObjectResponseHandler;
import java.io.File;
import java.util.List;
import java.util.Map;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

public class APISync {

    public static final class APISyncException extends Exception {
        private String mResponseBody = "";
        private int mStatusCode = 0;

        public APISyncException(String detailMessage, Throwable throwable) {
            super(detailMessage, throwable);
        }

        public APISyncException(String detailMessage) {
            super(detailMessage);
        }

        public APISyncException(Throwable throwable) {
            super(throwable);
        }

        public APISyncException(int statusCode, String responseBody) {
            this.mStatusCode = statusCode;
            this.mResponseBody = responseBody;
        }

        public int getStatusCode() {
            return this.mStatusCode;
        }

        public String getResponseBody() {
            return this.mResponseBody;
        }
    }

    private APISync() {
    }

    public static final byte[] getBytes(String url) throws APISyncException {
        try {
            byte[] bytes = (byte[]) APIUtil.execute(CoreAPI.getEndpoint(), (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", url), new ByteArrayResponseHandler());
            if (bytes != null) {
                return bytes;
            }
            throw new APISyncException();
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostSignupFree postSignupFree(Map<String, String> params) throws APISyncException {
        try {
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostSignupFree.PATH).build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostSignupFree r = (PostSignupFree) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostSignupFreeMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final GetGroups getGroupsV2(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), GetGroupsV2.PATH, (Map) params).build().toString());
            JSONArrayResponseHandler handler = new JSONArrayResponseHandler();
            GetGroups r = (GetGroups) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetGroupsMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final GetGroups getGroupsV3(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), GetGroupsV3.PATH, (Map) params).build().toString());
            JSONArrayResponseHandler handler = new JSONArrayResponseHandler();
            GetGroups r = (GetGroups) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetGroupsMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostGroups postGroups(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostGroups.PATH).build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostGroups r = (PostGroups) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroupsMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostGroups1on1s postGroups1on1s(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostGroups1on1s.PATH).build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostGroups1on1s r = (PostGroups1on1s) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroups1on1sMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostGroupJoinWithGroupUid postGroupJoinWithGroupUid(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            String path = String.format(APIDef.PostGroupJoinWithGroupUid.PATH, new Object[]{params.get("uid")});
            params.remove("uid");
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path).build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostGroupJoinWithGroupUid r = (PostGroupJoinWithGroupUid) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroupJoinWithGroupUidMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostGroupJoinWithExId postGroupJoinWithExId(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostGroupJoinWithExId.PATH).build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostGroupJoinWithExId r = (PostGroupJoinWithExId) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroupJoinWithExIdMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostGroupJoinWithGroupUid postGroupJoinWithGroupUidV2(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            String path = String.format(PostGroupJoinWithGroupUidV2.PATH, new Object[]{params.get("uid")});
            params.remove("uid");
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path).build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostGroupJoinWithGroupUid r = (PostGroupJoinWithGroupUid) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroupJoinWithGroupUidMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostGroupJoinWithGroupUid postGroupJoinWithExIdV2(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), PostGroupJoinWithExIdV2.PATH).build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostGroupJoinWithGroupUid r = (PostGroupJoinWithGroupUid) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroupJoinWithGroupUidMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final GetGroup getGroup(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            String path = String.format("/1/group/%s", new Object[]{params.get("uid")});
            params.remove("uid");
            HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path, (Map) params).build().toString());
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            GetGroup r = (GetGroup) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetGroupMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final GetGroupV2 getGroupV2(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            String path = String.format(APIDef.GetGroupV2.PATH, new Object[]{params.get("uid")});
            params.remove("uid");
            HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path, (Map) params).build().toString());
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            GetGroupV2 r = (GetGroupV2) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetGroupV2Mapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final GetGroup getGroupWithExIdV2(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), GetGroupWithExIdV2.PATH, (Map) params).build().toString());
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            GetGroup r = (GetGroup) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetGroupMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostGroupPrivacy postGroupPrivacy(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            String path = String.format(APIDef.PostGroupPrivacy.PATH, new Object[]{params.get("uid")});
            params.remove("uid");
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path).build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostGroupPrivacy r = (PostGroupPrivacy) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroupPrivacyMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostGroupPrivacy postGroupPrivacyV2(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            String path = String.format(PostGroupPrivacyV2.PATH, new Object[]{params.get("uid")});
            params.remove("uid");
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path).build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostGroupPrivacy r = (PostGroupPrivacy) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroupPrivacyMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostGroupChat postGroupChat(Map<String, String> params) throws APISyncException {
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
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final GetGroupChatV2 getGroupChatV2(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            String path = String.format(APIDef.GetGroupChatV2.PATH, new Object[]{params.get("uid")});
            params.remove("uid");
            HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path, (Map) params).build().toString());
            JSONArrayResponseHandler handler = new JSONArrayResponseHandler();
            GetGroupChatV2 r = (GetGroupChatV2) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetGroupChatV2Mapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final GetGroupChat getGroupChatWithExIdV2(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), GetGroupChatWithExIdV2.PATH, (Map) params).build().toString());
            JSONArrayResponseHandler handler = new JSONArrayResponseHandler();
            GetGroupChat r = (GetGroupChat) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetGroupChatMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final GetGroupChatReplies getGroupChatReplies(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            String path = String.format(APIDef.GetGroupChatReplies.PATH, new Object[]{params.get("uid")});
            params.remove("uid");
            HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path, (Map) params).build().toString());
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            GetGroupChatReplies r = (GetGroupChatReplies) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetGroupChatRepliesMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostGroupMembers postGroupMembers(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            String path = String.format(APIDef.PostGroupMembers.PATH, new Object[]{params.get("uid")});
            params.remove("uid");
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path).build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostGroupMembers r = (PostGroupMembers) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroupMembersMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostGroupMembersExId postGroupMembersExId(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostGroupMembersExId.PATH).build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostGroupMembersExId r = (PostGroupMembersExId) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroupMembersExIdMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostGroupChatRemove postGroupChatRemove(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            String path = String.format(APIDef.PostGroupChatRemove.PATH, new Object[]{params.get("uid")});
            params.remove("uid");
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path).build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostGroupChatRemove r = (PostGroupChatRemove) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroupChatRemoveMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostGroupPart postGroupPart(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            String path = String.format(APIDef.PostGroupPart.PATH, new Object[]{params.get("uid")});
            params.remove("uid");
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path).build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostGroupPart r = (PostGroupPart) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroupPartMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostGroupPartExId postGroupPartExId(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostGroupPartExId.PATH).build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostGroupPartExId r = (PostGroupPartExId) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroupPartExIdMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostGroupKick postGroupKick(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            String path = String.format(APIDef.PostGroupKick.PATH, new Object[]{params.get("uid")});
            params.remove("uid");
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path).build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostGroupKick r = (PostGroupKick) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroupKickMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostGroupKickExId postGroupKickExId(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostGroupKickExId.PATH).build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostGroupKickExId r = (PostGroupKickExId) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroupKickExIdMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostGroupPush postGroupPush(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            String path = String.format(APIDef.PostGroupPush.PATH, new Object[]{params.get("uid")});
            params.remove("uid");
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path).build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostGroupPush r = (PostGroupPush) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroupPushMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostGroupRemove postGroupRemove(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            String path = String.format(APIDef.PostGroupRemove.PATH, new Object[]{params.get("uid")});
            params.remove("uid");
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path).build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostGroupRemove r = (PostGroupRemove) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroupRemoveMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostGroupRemoveExId postGroupRemoveExId(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostGroupRemoveExId.PATH).build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostGroupRemoveExId r = (PostGroupRemoveExId) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroupRemoveExIdMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostGroup postGroup(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            String path = String.format("/1/group/%s", new Object[]{params.get("uid")});
            params.remove("uid");
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path).build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostGroup r = (PostGroup) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroupMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostGroupExId postGroupExId(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostGroupExId.PATH).build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostGroupExId r = (PostGroupExId) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroupExIdMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostGroupIcon postGroupIcon(Map<String, String> params) throws APISyncException {
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
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostGroupWallpaper postGroupWallpaper(Map<String, String> params) throws APISyncException {
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
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostGroupWallpaperRemove postGroupWallpaperRemove(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            String path = String.format(APIDef.PostGroupWallpaperRemove.PATH, new Object[]{params.get("uid")});
            params.remove("uid");
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path).build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostGroupWallpaperRemove r = (PostGroupWallpaperRemove) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroupWallpaperRemoveMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostGroupTransferExId postGroupTransferExId(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostGroupTransferExId.PATH).build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostGroupTransferExId r = (PostGroupTransferExId) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroupTransferExIdMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostGroupTransferExId postGroupTransferExIdV2(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), PostGroupTransferExIdV2.PATH).build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostGroupTransferExId r = (PostGroupTransferExId) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroupTransferExIdMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostMeContacts postMeContacts(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), "/1/me/contacts").build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostMeContacts r = (PostMeContacts) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostMeContactsMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostMeContactsExId postMeContactsExId(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), "/1/me/contacts").build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostMeContactsExId r = (PostMeContactsExId) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostMeContactsExIdMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final GetMeContacts getMeContacts(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), "/1/me/contacts", (Map) params).build().toString());
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            GetMeContacts r = (GetMeContacts) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetMeContactsMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final GetMeContactsRecommended getMeContactsRecommended(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.GetMeContactsRecommended.PATH, (Map) params).build().toString());
            JSONArrayResponseHandler handler = new JSONArrayResponseHandler();
            GetMeContactsRecommended r = (GetMeContactsRecommended) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetMeContactsRecommendedMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostMeContactsRemove postMeContactsRemove(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), "/1/me/contacts/remove").build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostMeContactsRemove r = (PostMeContactsRemove) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostMeContactsRemoveMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostMeContactsRemoveExId postMeContactsRemoveExId(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), "/1/me/contacts/remove").build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostMeContactsRemoveExId r = (PostMeContactsRemoveExId) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostMeContactsRemoveExIdMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostMeExId postMeExId(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostMeExId.PATH).build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostMeExId r = (PostMeExId) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostMeExIdMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostMeProfile postMeProfile(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostMeProfile.PATH).build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostMeProfile r = (PostMeProfile) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostMeProfileMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostMeBlockingUsers postMeBlockingUsers(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), "/1/me/blocking_users").build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostMeBlockingUsers r = (PostMeBlockingUsers) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostMeBlockingUsersMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostMeBlockingUsersRemove postMeBlockingUsersRemove(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostMeBlockingUsersRemove.PATH).build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostMeBlockingUsersRemove r = (PostMeBlockingUsersRemove) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostMeBlockingUsersRemoveMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final GetMeBlockingUsers getMeBlockingUsers(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), "/1/me/blocking_users", (Map) params).build().toString());
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            GetMeBlockingUsers r = (GetMeBlockingUsers) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetMeBlockingUsersMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final List<PostMeExternalContacts> postMeExternalContacts(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostMeExternalContacts.PATH).build().toString(), params);
            JSONArrayResponseHandler handler = new JSONArrayResponseHandler();
            List<PostMeExternalContacts> r = (List) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostMeExternalContactsMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostMeIcon postMeIcon(Map<String, String> params) throws APISyncException {
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
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostMeCover postMeCover(Map<String, String> params) throws APISyncException {
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
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostMeRemoveConfirm postMeRemoveConfirm(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostMeRemoveConfirm.PATH).build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostMeRemoveConfirm r = (PostMeRemoveConfirm) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostMeRemoveConfirmMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final GetMe getMe(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.GetMe.PATH, (Map) params).build().toString());
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            GetMe r = (GetMe) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetMeMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final GetMeSettings getMeSettings(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.GetMeSettings.PATH, (Map) params).build().toString());
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            GetMeSettings r = (GetMeSettings) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetMeSettingsMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final GetMeSettingsV2 getMeSettingsV2(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.GetMeSettingsV2.PATH, (Map) params).build().toString());
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            GetMeSettingsV2 r = (GetMeSettingsV2) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetMeSettingsV2Mapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostMeSettingsSearchable postMeSettingsSearchable(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostMeSettingsSearchable.PATH).build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostMeSettingsSearchable r = (PostMeSettingsSearchable) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostMeSettingsSearchableMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final GetUser getUser(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            String path = String.format(APIDef.GetUser.PATH, new Object[]{params.get("uid")});
            params.remove("uid");
            HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path, (Map) params).build().toString());
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            GetUser r = (GetUser) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetUserMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final GetUserV2 getUserV2(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            String path = String.format(APIDef.GetUserV2.PATH, new Object[]{params.get("uid")});
            params.remove("uid");
            HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path, (Map) params).build().toString());
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            GetUserV2 r = (GetUserV2) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetUserV2Mapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final GetUsersSearch getUsersSearch(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.GetUsersSearch.PATH, (Map) params).build().toString());
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            GetUsersSearch r = (GetUsersSearch) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetUsersSearchMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostInvitationsRecipients postInvitationsRecipients(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            String path = String.format(APIDef.PostInvitationsRecipients.PATH, new Object[]{params.get("uid")});
            params.remove("uid");
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path).build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostInvitationsRecipients r = (PostInvitationsRecipients) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostInvitationsRecipientsMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final GetStamps getStamps(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.GetStamps.PATH, (Map) params).build().toString());
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            GetStamps r = (GetStamps) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetStampsMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostStampUnlock postStampUnlock(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            String path = String.format(APIDef.PostStampUnlock.PATH, new Object[]{params.get("uid")});
            params.remove("uid");
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path).build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostStampUnlock r = (PostStampUnlock) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostStampUnlockMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final GetStampUnlocked getStampUnlocked(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            String path = String.format(APIDef.GetStampUnlocked.PATH, new Object[]{params.get("uid")});
            params.remove("uid");
            HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path, (Map) params).build().toString());
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            GetStampUnlocked r = (GetStampUnlocked) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetStampUnlockedMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final GetApp getApp(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            String path = String.format(APIDef.GetApp.PATH, new Object[]{params.get("uid")});
            params.remove("uid");
            HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path, (Map) params).build().toString());
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            GetApp r = (GetApp) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetAppMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostAccusations postAccusations(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostAccusations.PATH).build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostAccusations r = (PostAccusations) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostAccusationsMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final GetPublicGroups getPublicGroups(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), "/1/public_groups", (Map) params).build().toString());
            JSONArrayResponseHandler handler = new JSONArrayResponseHandler();
            GetPublicGroups r = (GetPublicGroups) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetPublicGroupsMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostPublicGroups postPublicGroups(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), "/1/public_groups").build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostPublicGroups r = (PostPublicGroups) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostPublicGroupsMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final GetPublicGroupsTree getPublicGroupsTree(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.GetPublicGroupsTree.PATH, (Map) params).build().toString());
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            GetPublicGroupsTree r = (GetPublicGroupsTree) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetPublicGroupsTreeMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final GetPublicGroupsSearch getPublicGroupsSearch(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.GetPublicGroupsSearch.PATH, (Map) params).build().toString());
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            GetPublicGroupsSearch r = (GetPublicGroupsSearch) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetPublicGroupsSearchMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostGroupVisibility postGroupVisibility(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            String path = String.format(APIDef.PostGroupVisibility.PATH, new Object[]{params.get("uid")});
            params.remove("uid");
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path).build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostGroupVisibility r = (PostGroupVisibility) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostGroupVisibilityMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final GetUserVisibleGroups getUserVisibleGroups(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            String path = String.format(APIDef.GetUserVisibleGroups.PATH, new Object[]{params.get("uid")});
            params.remove("uid");
            HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path, (Map) params).build().toString());
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            GetUserVisibleGroups r = (GetUserVisibleGroups) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetUserVisibleGroupsMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final GetMeProfileVisibleGroups getMeProfileVisibleGroups(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.GetMeProfileVisibleGroups.PATH, (Map) params).build().toString());
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            GetMeProfileVisibleGroups r = (GetMeProfileVisibleGroups) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetMeProfileVisibleGroupsMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostNotifyContacts postNotifyContacts(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostNotifyContacts.PATH).build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostNotifyContacts r = (PostNotifyContacts) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostNotifyContactsMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostBindStart postBindStart(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostBindStart.PATH).build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostBindStart r = (PostBindStart) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostBindStartMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostOauthAccessToken postOauthAccessToken(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostOauthAccessToken.PATH).build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostOauthAccessToken r = (PostOauthAccessToken) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostOauthAccessTokenMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final GetSdkReport getSdkReport(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.GetSdkReport.PATH, (Map) params).build().toString());
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            GetSdkReport r = (GetSdkReport) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetSdkReportMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final GetTerms getTerms(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.GetTerms.PATH, (Map) params).build().toString());
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            GetTerms r = (GetTerms) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetTermsMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostUserExIdEncrypt postUserExIdEncrypt(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostUserExIdEncrypt.PATH).build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostUserExIdEncrypt r = (PostUserExIdEncrypt) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostUserExIdEncryptMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostUserExIdDecrypt postUserExIdDecrypt(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostUserExIdDecrypt.PATH).build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostUserExIdDecrypt r = (PostUserExIdDecrypt) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostUserExIdDecryptMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final GetNotifications getNotifications(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.GetNotifications.PATH, (Map) params).build().toString());
            JSONArrayResponseHandler handler = new JSONArrayResponseHandler();
            GetNotifications r = (GetNotifications) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetNotificationsMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final GetNoahBanner getNoahBanner(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.GetNoahBanner.PATH, (Map) params).build().toString());
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            GetNoahBanner r = (GetNoahBanner) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetNoahBannerMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostRankingScore postRankingScore(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            String path = String.format(APIDef.PostRankingScore.PATH, new Object[]{params.get("ranking_id")});
            params.remove("ranking_id");
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path).build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostRankingScore r = (PostRankingScore) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostRankingScoreMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final GetRanking getRanking(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            String path = String.format(APIDef.GetRanking.PATH, new Object[]{params.get("ranking_id")});
            params.remove("ranking_id");
            HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path, (Map) params).build().toString());
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            GetRanking r = (GetRanking) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetRankingMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final GetRankings getRankings(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.GetRankings.PATH, (Map) params).build().toString());
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            GetRankings r = (GetRankings) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetRankingsMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final GetNonce getNonce(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.GetNonce.PATH, (Map) params).build().toString());
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            GetNonce r = (GetNonce) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetNonceMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final GetPing getPing(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.GetPing.PATH, (Map) params).build().toString());
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            GetPing r = (GetPing) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetPingMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostAppData postAppData(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), "/1/appdata/").build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostAppData r = (PostAppData) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostAppDataMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final GetAppData getAppData(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), "/1/appdata/", (Map) params).build().toString());
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            GetAppData r = (GetAppData) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetAppDataMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostAppDataRemove postAppDataRemove(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostAppDataRemove.PATH).build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostAppDataRemove r = (PostAppDataRemove) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostAppDataRemoveMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostAdTrackingImpression postAdTrackingImpression(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostAdTrackingImpression.PATH).build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostAdTrackingImpression r = (PostAdTrackingImpression) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostAdTrackingImpressionMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostAdTrackingClick postAdTrackingClick(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostAdTrackingClick.PATH).build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostAdTrackingClick r = (PostAdTrackingClick) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostAdTrackingClickMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final PostAdTrackingConversion postAdTrackingConversion(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpPost request = (HttpPost) APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.PostAdTrackingConversion.PATH).build().toString(), params);
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            PostAdTrackingConversion r = (PostAdTrackingConversion) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, PostAdTrackingConversionMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }

    public static final GetAdNewAd getAdNewAd(Map<String, String> params) throws APISyncException {
        try {
            CoreAPI.getTokenChecker().checkToken();
            HttpGet request = (HttpGet) APIUtil.requestFactory(CoreAPI.getEndpoint(), "GET", APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), APIDef.GetAdNewAd.PATH, (Map) params).build().toString());
            JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
            GetAdNewAd r = (GetAdNewAd) APIUtil.execute(CoreAPI.getEndpoint(), request, handler, GetAdNewAdMapper.getInstance());
            if (handler.getThrowable() != null) {
                throw new APISyncException(handler.getThrowable());
            } else if (r != null) {
                return r;
            } else {
                throw new APISyncException(handler.getStatusCode(), handler.getResponseBody());
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new APISyncException(e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new APISyncException(e2);
        }
    }
}
