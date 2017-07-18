package com.kayac.lobi.libnakamap.net;

import android.text.TextUtils;
import com.adjust.sdk.Constants;
import com.google.android.gcm.GCMConstants;
import com.kayac.lobi.libnakamap.collection.Pair;
import com.kayac.lobi.libnakamap.components.LoginEntranceDialog;
import com.kayac.lobi.libnakamap.contacts.SNSInterface;
import com.kayac.lobi.libnakamap.datastore.AccountDDL.KKey.SDKBindFinish;
import com.kayac.lobi.libnakamap.net.APIDef.GetGroupChatReplies.RequestKey;
import com.kayac.lobi.libnakamap.utils.JSONUtil;
import com.kayac.lobi.libnakamap.utils.Log;
import com.kayac.lobi.libnakamap.value.AdNewAdValue;
import com.kayac.lobi.libnakamap.value.AppValue;
import com.kayac.lobi.libnakamap.value.CategoryValue;
import com.kayac.lobi.libnakamap.value.ChatValue;
import com.kayac.lobi.libnakamap.value.GroupDetailValue;
import com.kayac.lobi.libnakamap.value.GroupValue;
import com.kayac.lobi.libnakamap.value.LobiAccountContactValue;
import com.kayac.lobi.libnakamap.value.NotificationValue;
import com.kayac.lobi.libnakamap.value.ProfileValue;
import com.kayac.lobi.libnakamap.value.PublicCategoryValue;
import com.kayac.lobi.libnakamap.value.RankingDetailValue;
import com.kayac.lobi.libnakamap.value.RankingEntryValue;
import com.kayac.lobi.libnakamap.value.StampStoreValue;
import com.kayac.lobi.libnakamap.value.StampStoreValue.Builder;
import com.kayac.lobi.libnakamap.value.StampValue;
import com.kayac.lobi.libnakamap.value.StartupConfigValue;
import com.kayac.lobi.libnakamap.value.UserContactValue;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.rekoo.libs.config.Config;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class APIRes {

    public interface ConvertibleResponse {
        JSONObject convertDataForClient();
    }

    public static final class GetAdNewAd {
        public final AdNewAdValue adNewAdValue;

        public GetAdNewAd(AdNewAdValue adNewAdValue) {
            this.adNewAdValue = adNewAdValue;
        }
    }

    public interface JSON2ObjectMapper<T, K> {
        T map(K k);
    }

    public static final class GetAdNewAdMapper implements JSON2ObjectMapper<GetAdNewAd, JSONObject> {
        private static final GetAdNewAdMapper sInstance = new GetAdNewAdMapper();

        private GetAdNewAdMapper() {
        }

        public static GetAdNewAdMapper getInstance() {
            return sInstance;
        }

        public GetAdNewAd map(JSONObject json) {
            JSONObject newAd = json.optJSONObject("new_ad");
            if (newAd == null) {
                return new GetAdNewAd(null);
            }
            return new GetAdNewAd(new AdNewAdValue(newAd));
        }
    }

    public static final class GetApp {
        public final AppValue app;

        public GetApp(AppValue app) {
            this.app = app;
        }
    }

    public static final class GetAppData {
        public final JSONObject json;

        public GetAppData(JSONObject json) {
            this.json = json;
        }
    }

    public static final class GetAppDataMapper implements JSON2ObjectMapper<GetAppData, JSONObject> {
        private static final GetAppDataMapper sInstance = new GetAppDataMapper();

        private GetAppDataMapper() {
        }

        public static GetAppDataMapper getInstance() {
            return sInstance;
        }

        public GetAppData map(JSONObject json) {
            return new GetAppData(json);
        }
    }

    public static final class GetAppMapper implements JSON2ObjectMapper<GetApp, JSONObject> {
        private static final GetAppMapper sInstance = new GetAppMapper();

        private GetAppMapper() {
        }

        public static GetAppMapper getInstance() {
            return sInstance;
        }

        public GetApp map(JSONObject json) {
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new GetApp(new AppValue(json));
        }
    }

    public static final class GetDefaultUser {
        public final long contactsCount;
        public final long followersCount;
        public final long followingDate;
        public final UserValue user;

        public GetDefaultUser(UserValue user, long followingDate, long contactsCount, long followersCount) {
            this.user = user;
            this.followingDate = followingDate;
            this.contactsCount = contactsCount;
            this.followersCount = followersCount;
        }
    }

    public static final class GetDefaultUserContacts {
        public final List<UserContactValue> contacts = new ArrayList();
        public final List<LobiAccountContactValue> loginContacts = new ArrayList();

        public GetDefaultUserContacts(List<UserContactValue> contacts, List<LobiAccountContactValue> loginContacts) {
            this.contacts.addAll(contacts);
            this.loginContacts.addAll(loginContacts);
        }
    }

    public static final class GetDefaultUserContactsMapper implements JSON2ObjectMapper<GetDefaultUserContacts, JSONObject> {
        private static final GetDefaultUserContactsMapper sInstance = new GetDefaultUserContactsMapper();

        private GetDefaultUserContactsMapper() {
        }

        public static GetDefaultUserContactsMapper getInstance() {
            return sInstance;
        }

        public GetDefaultUserContacts map(JSONObject json) {
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            int len;
            int i;
            JSONObject obj;
            List<UserContactValue> contacts = new ArrayList();
            JSONArray ary = json.optJSONArray("users");
            if (ary != null) {
                len = ary.length();
                for (i = 0; i < len; i++) {
                    obj = ary.optJSONObject(i);
                    if (obj != null) {
                        contacts.add(new UserContactValue(obj));
                    }
                }
            }
            List<LobiAccountContactValue> loginContacts = new ArrayList();
            ary = json.optJSONArray("login_users");
            if (ary != null) {
                len = ary.length();
                for (i = 0; i < len; i++) {
                    obj = ary.optJSONObject(i);
                    if (obj != null) {
                        loginContacts.add(new LobiAccountContactValue(obj));
                    }
                }
            }
            return new GetDefaultUserContacts(contacts, loginContacts);
        }
    }

    public static final class GetDefaultUserFollowers {
        public final List<UserContactValue> followers = new ArrayList();
        public final boolean isPublic;
        public final List<LobiAccountContactValue> loginFollowers = new ArrayList();

        public GetDefaultUserFollowers(List<UserContactValue> followers, List<LobiAccountContactValue> loginFollowers, boolean isPublic) {
            this.followers.addAll(followers);
            this.loginFollowers.addAll(loginFollowers);
            this.isPublic = isPublic;
        }
    }

    public static final class GetDefaultUserFollowersMapper implements JSON2ObjectMapper<GetDefaultUserFollowers, JSONObject> {
        private static final GetDefaultUserFollowersMapper sInstance = new GetDefaultUserFollowersMapper();

        private GetDefaultUserFollowersMapper() {
        }

        public static GetDefaultUserFollowersMapper getInstance() {
            return sInstance;
        }

        public GetDefaultUserFollowers map(JSONObject json) {
            int i;
            JSONObject obj;
            List<UserContactValue> followers = new ArrayList();
            JSONArray array = json.optJSONArray("users");
            if (array != null) {
                for (i = 0; i < array.length(); i++) {
                    obj = array.optJSONObject(i);
                    if (obj != null) {
                        followers.add(new UserContactValue(obj));
                    }
                }
            }
            List<LobiAccountContactValue> loginFollowers = new ArrayList();
            array = json.optJSONArray("login_users");
            if (array != null) {
                for (i = 0; i < array.length(); i++) {
                    obj = array.optJSONObject(i);
                    if (obj != null) {
                        loginFollowers.add(new LobiAccountContactValue(obj));
                    }
                }
            }
            return new GetDefaultUserFollowers(followers, loginFollowers, "2".equals(json.optString("visibility")));
        }
    }

    public static final class GetDefaultUserMapper implements JSON2ObjectMapper<GetDefaultUser, JSONObject> {
        private static final GetDefaultUserMapper sInstance = new GetDefaultUserMapper();

        private GetDefaultUserMapper() {
        }

        public static GetDefaultUserMapper getInstance() {
            return sInstance;
        }

        public GetDefaultUser map(JSONObject json) {
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new GetDefaultUser(new UserValue(json), Long.parseLong(JSONUtil.getString(json, "following_date", Config.INIT_FAIL_NO_NETWORK)) * 1000, json.optLong("contacts_count", 0), json.optLong("followers_count", 0));
        }
    }

    public static final class GetGroup {
        public final GroupValue group;

        public GetGroup(GroupValue group) {
            this.group = group;
        }
    }

    public static final class GetGroupChat {
        public final List<ChatValue> chats = new ArrayList();

        public GetGroupChat(List<ChatValue> chats) {
            this.chats.addAll(chats);
        }
    }

    public static final class GetGroupChatMapper implements JSON2ObjectMapper<GetGroupChat, JSONArray> {
        private static final GetGroupChatMapper sInstance = new GetGroupChatMapper();

        private GetGroupChatMapper() {
        }

        public static GetGroupChatMapper getInstance() {
            return sInstance;
        }

        public GetGroupChat map(JSONArray ary) {
            List<ChatValue> chats = new ArrayList();
            int len = ary.length();
            for (int i = 0; i < len; i++) {
                JSONObject obj = ary.optJSONObject(i);
                if (obj != null) {
                    chats.add(new ChatValue(obj));
                }
            }
            return new GetGroupChat(chats);
        }
    }

    public static final class GetGroupChatReplies {
        public final List<ChatValue> chats = new ArrayList();
        public final ChatValue to;

        public GetGroupChatReplies(ChatValue to, List<ChatValue> chats) {
            this.chats.addAll(chats);
            this.to = to;
        }
    }

    public static final class GetGroupChatRepliesMapper implements JSON2ObjectMapper<GetGroupChatReplies, JSONObject> {
        private static final GetGroupChatRepliesMapper sInstance = new GetGroupChatRepliesMapper();

        private GetGroupChatRepliesMapper() {
        }

        public static GetGroupChatRepliesMapper getInstance() {
            return sInstance;
        }

        public GetGroupChatReplies map(JSONObject json) {
            List<ChatValue> chats = new ArrayList();
            ChatValue to = new ChatValue(json.optJSONObject(RequestKey.TO));
            JSONArray ary = json.optJSONArray("chats");
            int len = ary.length();
            for (int i = 0; i < len; i++) {
                JSONObject reply = ary.optJSONObject(i);
                if (reply != null) {
                    chats.add(new ChatValue(reply));
                }
            }
            return new GetGroupChatReplies(to, chats);
        }
    }

    public static final class GetGroupChatV2 {
        public final List<ChatValue> chats = new ArrayList();

        public GetGroupChatV2(List<ChatValue> chats) {
            this.chats.addAll(chats);
        }
    }

    public static final class GetGroupChatV2Mapper implements JSON2ObjectMapper<GetGroupChatV2, JSONArray> {
        private static final GetGroupChatV2Mapper sInstance = new GetGroupChatV2Mapper();

        private GetGroupChatV2Mapper() {
        }

        public static GetGroupChatV2Mapper getInstance() {
            return sInstance;
        }

        public GetGroupChatV2 map(JSONArray ary) {
            List<ChatValue> chats = new ArrayList();
            int len = ary.length();
            for (int i = 0; i < len; i++) {
                JSONObject obj = ary.optJSONObject(i);
                if (obj != null) {
                    chats.add(new ChatValue(obj));
                }
            }
            return new GetGroupChatV2(chats);
        }
    }

    public static final class GetGroupMapper implements JSON2ObjectMapper<GetGroup, JSONObject> {
        private static final GetGroupMapper sInstance = new GetGroupMapper();

        private GetGroupMapper() {
        }

        public static GetGroupMapper getInstance() {
            return sInstance;
        }

        public GetGroup map(JSONObject json) {
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new GetGroup(new GroupValue(json));
        }
    }

    public static final class GetGroupV2 {
        public final GroupValue group;

        public GetGroupV2(GroupValue group) {
            this.group = group;
        }
    }

    public static final class GetGroupV2Mapper implements JSON2ObjectMapper<GetGroupV2, JSONObject> {
        private static final GetGroupV2Mapper sInstance = new GetGroupV2Mapper();

        private GetGroupV2Mapper() {
        }

        public static GetGroupV2Mapper getInstance() {
            return sInstance;
        }

        public GetGroupV2 map(JSONObject json) {
            return new GetGroupV2(new GroupValue(json));
        }
    }

    public static final class GetGroups {
        public final List<CategoryValue> categories = new ArrayList();

        public GetGroups(List<CategoryValue> categories) {
            this.categories.addAll(categories);
        }
    }

    public static final class GetGroupsMapper implements JSON2ObjectMapper<GetGroups, JSONArray> {
        private static final GetGroupsMapper sInstance = new GetGroupsMapper();

        private GetGroupsMapper() {
        }

        public static GetGroupsMapper getInstance() {
            return sInstance;
        }

        public GetGroups map(JSONArray ary) {
            List<CategoryValue> categories = new ArrayList();
            int len = ary.length();
            for (int i = 0; i < len; i++) {
                JSONObject obj = ary.optJSONObject(i);
                if (obj != null) {
                    categories.add(new CategoryValue(obj, CategoryValue.TYPE_PRIVATE));
                }
            }
            return new GetGroups(categories);
        }
    }

    public static final class GetMe {
        public final UserValue me;

        public GetMe(UserValue user) {
            this.me = user;
        }
    }

    public static final class GetMeBindedMapper implements JSON2ObjectMapper<Boolean, JSONObject> {
        private static final GetMeBindedMapper sInstance = new GetMeBindedMapper();

        private GetMeBindedMapper() {
        }

        public static GetMeBindedMapper getInstance() {
            return sInstance;
        }

        public Boolean map(JSONObject json) {
            return Boolean.valueOf(TextUtils.equals("1", JSONUtil.getString(json, "binded", "0")));
        }
    }

    public static final class GetMeBlockingUsers {
        public final List<UserValue> users = new ArrayList();

        public GetMeBlockingUsers(List<UserValue> users) {
            this.users.addAll(users);
        }
    }

    public static final class GetMeBlockingUsersMapper implements JSON2ObjectMapper<GetMeBlockingUsers, JSONObject> {
        private static final GetMeBlockingUsersMapper sInstance = new GetMeBlockingUsersMapper();

        private GetMeBlockingUsersMapper() {
        }

        public static GetMeBlockingUsersMapper getInstance() {
            return sInstance;
        }

        public GetMeBlockingUsers map(JSONObject json) {
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            List<UserValue> users = new ArrayList();
            JSONArray ary = json.optJSONArray("users");
            if (ary != null) {
                int len = ary.length();
                for (int i = 0; i < len; i++) {
                    JSONObject obj = ary.optJSONObject(i);
                    if (obj != null) {
                        users.add(new UserValue(obj));
                    }
                }
            }
            return new GetMeBlockingUsers(users);
        }
    }

    public static final class GetMeChatFriends {
        public final List<UserValue> users = new ArrayList();

        public GetMeChatFriends(List<UserValue> users) {
            this.users.addAll(users);
        }
    }

    public static final class GetMeChatFriendsMapper implements JSON2ObjectMapper<GetMeChatFriends, JSONObject> {
        private static final GetMeChatFriendsMapper sInstance = new GetMeChatFriendsMapper();

        private GetMeChatFriendsMapper() {
        }

        public static GetMeChatFriendsMapper getInstance() {
            return sInstance;
        }

        public GetMeChatFriends map(JSONObject json) {
            List<UserValue> users = new ArrayList();
            if (json == null) {
                return new GetMeChatFriends(users);
            }
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            JSONArray ary = json.optJSONArray("users");
            if (ary == null) {
                return new GetMeChatFriends(users);
            }
            int len = ary.length();
            for (int i = 0; i < len; i++) {
                JSONObject obj = ary.optJSONObject(i);
                if (obj != null) {
                    users.add(new UserValue(obj));
                }
            }
            return new GetMeChatFriends(users);
        }
    }

    public static final class GetMeContacts {
        public final List<UserContactValue> contacts = new ArrayList();

        public GetMeContacts(List<UserContactValue> contacts) {
            this.contacts.addAll(contacts);
        }
    }

    public static final class GetMeContactsMapper implements JSON2ObjectMapper<GetMeContacts, JSONObject> {
        private static final GetMeContactsMapper sInstance = new GetMeContactsMapper();

        private GetMeContactsMapper() {
        }

        public static GetMeContactsMapper getInstance() {
            return sInstance;
        }

        public GetMeContacts map(JSONObject json) {
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            List<UserContactValue> contacts = new ArrayList();
            JSONArray ary = json.optJSONArray("users");
            if (ary != null) {
                int len = ary.length();
                for (int i = 0; i < len; i++) {
                    JSONObject obj = ary.optJSONObject(i);
                    if (obj != null) {
                        contacts.add(new UserContactValue(obj));
                    }
                }
            }
            return new GetMeContacts(contacts);
        }
    }

    public static final class GetMeContactsRecommended {
        public final List<Pair<String, List<Pair<UserValue, String>>>> recommended = new ArrayList();

        public GetMeContactsRecommended(List<Pair<String, List<Pair<UserValue, String>>>> recommended) {
            this.recommended.addAll(recommended);
        }
    }

    public static final class GetMeContactsRecommendedMapper implements JSON2ObjectMapper<GetMeContactsRecommended, JSONArray> {
        private static final GetMeContactsRecommendedMapper sInstance = new GetMeContactsRecommendedMapper();

        private GetMeContactsRecommendedMapper() {
        }

        public static GetMeContactsRecommendedMapper getInstance() {
            return sInstance;
        }

        public GetMeContactsRecommended map(JSONArray ary) {
            List<Pair<String, List<Pair<UserValue, String>>>> recommended = new ArrayList();
            int len = ary.length();
            for (int i = 0; i < len; i++) {
                JSONObject obj = ary.optJSONObject(i);
                if (obj != null) {
                    String title = JSONUtil.getString(obj, LoginEntranceDialog.ARGUMENTS_TITLE, null);
                    JSONArray users = obj.optJSONArray("users");
                    List<Pair<UserValue, String>> list = new ArrayList();
                    int userLen = users.length();
                    for (int j = 0; j < userLen; j++) {
                        JSONObject objContact = users.optJSONObject(j);
                        String reason = JSONUtil.getString(objContact, com.kayac.lobi.libnakamap.net.APIDef.PostAccusations.RequestKey.REASON, "");
                        if (objContact != null) {
                            list.add(new Pair(new UserValue(objContact), reason));
                        }
                    }
                    recommended.add(new Pair(title, list));
                }
            }
            return new GetMeContactsRecommended(recommended);
        }
    }

    public static final class GetMeMapper implements JSON2ObjectMapper<GetMe, JSONObject> {
        private static final GetMeMapper sInstance = new GetMeMapper();

        private GetMeMapper() {
        }

        public static GetMeMapper getInstance() {
            return sInstance;
        }

        public GetMe map(JSONObject k) {
            if (k.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new GetMe(new UserValue(k));
        }
    }

    public static final class GetMeProfileVisibleGroups {
        public final List<Pair<GroupDetailValue, String>> groupDetails;
        public final String nextCursor;

        public GetMeProfileVisibleGroups(List<Pair<GroupDetailValue, String>> groupDetails, String cursor) {
            this.groupDetails = groupDetails;
            this.nextCursor = cursor;
        }
    }

    public static final class GetMeProfileVisibleGroupsMapper implements JSON2ObjectMapper<GetMeProfileVisibleGroups, JSONObject> {
        private static final GetMeProfileVisibleGroupsMapper sInstance = new GetMeProfileVisibleGroupsMapper();

        private GetMeProfileVisibleGroupsMapper() {
        }

        public static GetMeProfileVisibleGroupsMapper getInstance() {
            return sInstance;
        }

        public GetMeProfileVisibleGroups map(JSONObject json) {
            List<Pair<GroupDetailValue, String>> publicGroups = new ArrayList();
            JSONArray jsonArray = json.optJSONArray("public_groups");
            if (jsonArray != null) {
                int len = jsonArray.length();
                for (int i = 0; i < len; i++) {
                    JSONObject publicGroup = jsonArray.optJSONObject(i);
                    if (publicGroup != null) {
                        publicGroups.add(new Pair(new GroupDetailValue(publicGroup.optJSONObject("group")), publicGroup.optString("visibility", "0")));
                    }
                }
            }
            return new GetMeProfileVisibleGroups(publicGroups, json.optString("next_cursor", "0"));
        }
    }

    public static final class GetMeSettings {
        public final boolean autoAddEmail;
        public final boolean autoAddFacebook;
        public final boolean autoAddMixi;
        public final boolean autoAddTwitter;
        public final Push push;
        public final boolean receiveFriendsNotice;
        public final boolean receiveNewsNotice;
        public final boolean searchable;

        public static final class Push {
            public final String enabledFrom;
            public final String enabledTil;
            public final int enabledType;
            public final String sound;
            public final String timeZone;

            public Push(JSONObject jsonObject) {
                if (jsonObject == null) {
                    this.enabledType = 0;
                    this.enabledFrom = null;
                    this.enabledTil = null;
                    this.timeZone = null;
                    this.sound = null;
                    return;
                }
                this.enabledType = Integer.parseInt(JSONUtil.getString(jsonObject, "enabledType", "0"));
                this.enabledFrom = jsonObject.optString("enabledFrom", null);
                this.enabledTil = jsonObject.optString("enabledTil", null);
                this.timeZone = jsonObject.optString("timeZone", null);
                this.sound = jsonObject.optString("sound", null);
            }
        }

        public GetMeSettings(JSONObject jsonObject) {
            this.searchable = "1".equals(jsonObject.optString("searchable"));
            this.push = new Push(jsonObject.optJSONObject(Constants.PUSH));
            this.autoAddFacebook = "1".equals(jsonObject.optString("auto_add_facebook"));
            this.autoAddEmail = "1".equals(jsonObject.optString("auto_add_email"));
            this.autoAddMixi = "1".equals(jsonObject.optString("auto_add_mixi"));
            this.autoAddTwitter = "1".equals(jsonObject.optString("auto_add_twitter"));
            this.receiveFriendsNotice = "1".equals(jsonObject.optString("receive_friends_notice"));
            this.receiveNewsNotice = "1".equals(jsonObject.optString("receive_news_notice"));
        }
    }

    public static final class GetMeSettingsMapper implements JSON2ObjectMapper<GetMeSettings, JSONObject> {
        private static final GetMeSettingsMapper sInstance = new GetMeSettingsMapper();

        private GetMeSettingsMapper() {
        }

        public static GetMeSettingsMapper getInstance() {
            return sInstance;
        }

        public GetMeSettings map(JSONObject k) {
            if (k.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new GetMeSettings(k);
        }
    }

    public static final class GetMeSettingsV2 {
        public final boolean autoAddEmail;
        public final boolean autoAddFacebook;
        public final boolean autoAddMixi;
        public final boolean autoAddTwitter;
        public final Push push;
        public final boolean searchable;

        public GetMeSettingsV2(JSONObject jsonObject) {
            this.searchable = "1".equals(jsonObject.optString("searchable"));
            this.push = new Push(jsonObject.optJSONObject(Constants.PUSH));
            this.autoAddFacebook = "1".equals(jsonObject.optString("auto_add_facebook"));
            this.autoAddEmail = "1".equals(jsonObject.optString("auto_add_email"));
            this.autoAddMixi = "1".equals(jsonObject.optString("auto_add_mixi"));
            this.autoAddTwitter = "1".equals(jsonObject.optString("auto_add_twitter"));
        }
    }

    public static final class GetMeSettingsV2Mapper implements JSON2ObjectMapper<GetMeSettingsV2, JSONObject> {
        private static final GetMeSettingsV2Mapper sInstance = new GetMeSettingsV2Mapper();

        private GetMeSettingsV2Mapper() {
        }

        public static GetMeSettingsV2Mapper getInstance() {
            return sInstance;
        }

        public GetMeSettingsV2 map(JSONObject k) {
            return new GetMeSettingsV2(k);
        }
    }

    public static final class GetMeUsers {
        public final boolean success;
        public final List<UserValue> users = new ArrayList();

        public GetMeUsers(boolean success, List<UserValue> users) {
            this.success = success;
            this.users.addAll(users);
        }
    }

    public static final class GetMeUsersMapper implements JSON2ObjectMapper<GetMeUsers, JSONObject> {
        private static final GetMeUsersMapper sInstance = new GetMeUsersMapper();

        private GetMeUsersMapper() {
        }

        public static GetMeUsersMapper getInstance() {
            return sInstance;
        }

        public GetMeUsers map(JSONObject json) {
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            List<UserValue> users = new ArrayList();
            if (json == null) {
                return new GetMeUsers(false, users);
            }
            String success = json.optString(SNSInterface.SUCCESS, "0");
            JSONArray ary = json.optJSONArray("users");
            if (ary == null) {
                return new GetMeUsers(false, users);
            }
            int len = ary.length();
            for (int i = 0; i < len; i++) {
                JSONObject obj = ary.optJSONObject(i);
                if (obj != null) {
                    users.add(new UserValue(obj));
                }
            }
            return new GetMeUsers("1".equals(success), users);
        }
    }

    public static final class GetNoahBanner {
        public final String url;

        public GetNoahBanner(String url) {
            this.url = url;
        }
    }

    public static final class GetNoahBannerMapper implements JSON2ObjectMapper<GetNoahBanner, JSONObject> {
        private static final GetNoahBannerMapper sInstance = new GetNoahBannerMapper();

        private GetNoahBannerMapper() {
        }

        public static GetNoahBannerMapper getInstance() {
            return sInstance;
        }

        public GetNoahBanner map(JSONObject k) {
            String url;
            if (k == null) {
                url = null;
            } else {
                url = k.optString("url");
            }
            return new GetNoahBanner(url);
        }
    }

    public static final class GetNonce {
        public final String nonce;

        public GetNonce(String nonce) {
            this.nonce = nonce;
        }
    }

    public static final class GetNonceMapper implements JSON2ObjectMapper<GetNonce, JSONObject> {
        private static final GetNonceMapper sInstance = new GetNonceMapper();

        private GetNonceMapper() {
        }

        public static GetNonceMapper getInstance() {
            return sInstance;
        }

        public GetNonce map(JSONObject json) {
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new GetNonce(json.optString("nonce", ""));
        }
    }

    public static final class GetNotifications {
        public final List<NotificationValue> notificationValue;

        public GetNotifications(List<NotificationValue> notificationValue) {
            this.notificationValue = notificationValue;
        }
    }

    public static final class GetNotificationsMapper implements JSON2ObjectMapper<GetNotifications, JSONArray> {
        private static final GetNotificationsMapper sInstance = new GetNotificationsMapper();

        private GetNotificationsMapper() {
        }

        public static GetNotificationsMapper getInstance() {
            return sInstance;
        }

        public GetNotifications map(JSONArray ary) {
            List<NotificationValue> notifications = new ArrayList();
            int len = ary.length();
            for (int i = 0; i < len; i++) {
                JSONObject obj = ary.optJSONObject(i);
                if (obj != null) {
                    Log.v("API", "notification " + obj.toString());
                    notifications.add(new NotificationValue(obj));
                }
            }
            return new GetNotifications(notifications);
        }
    }

    public static final class GetPing {
        public final String date;

        public GetPing(String date) {
            this.date = date;
        }
    }

    public static final class GetPingMapper implements JSON2ObjectMapper<GetPing, JSONObject> {
        private static final GetPingMapper sInstance = new GetPingMapper();

        private GetPingMapper() {
        }

        public static GetPingMapper getInstance() {
            return sInstance;
        }

        public GetPing map(JSONObject json) {
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new GetPing(json.optString(com.kayac.lobi.libnakamap.net.APIDef.PostRankingScore.RequestKey.DATE, ""));
        }
    }

    public static final class GetPublicCategories {
        public final PublicCategoryValue publiCategory;

        public GetPublicCategories(PublicCategoryValue publicCategoryValue) {
            this.publiCategory = publicCategoryValue;
        }
    }

    public static final class GetPublicCategoriesMapper implements JSON2ObjectMapper<GetPublicCategories, JSONObject> {
        private static final GetPublicCategoriesMapper sInstance = new GetPublicCategoriesMapper();

        private GetPublicCategoriesMapper() {
        }

        public static GetPublicCategoriesMapper getInstance() {
            return sInstance;
        }

        public GetPublicCategories map(JSONObject json) {
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new GetPublicCategories(new PublicCategoryValue(json));
        }
    }

    public static final class GetPublicGroups {
        public final List<CategoryValue> categories = new ArrayList();

        public GetPublicGroups(List<CategoryValue> categories) {
            this.categories.addAll(categories);
        }
    }

    public static final class GetPublicGroupsMapper implements JSON2ObjectMapper<GetPublicGroups, JSONArray> {
        private static final GetPublicGroupsMapper sInstance = new GetPublicGroupsMapper();

        private GetPublicGroupsMapper() {
        }

        public static GetPublicGroupsMapper getInstance() {
            return sInstance;
        }

        public GetPublicGroups map(JSONArray ary) {
            List<CategoryValue> categories = new ArrayList();
            int len = ary.length();
            for (int i = 0; i < len; i++) {
                JSONObject obj = ary.optJSONObject(i);
                if (obj != null) {
                    categories.add(new CategoryValue(obj, "public"));
                }
            }
            return new GetPublicGroups(categories);
        }
    }

    public static final class GetPublicGroupsSearch {
        public final List<GroupDetailValue> groupDetails = new ArrayList();

        public GetPublicGroupsSearch(List<GroupDetailValue> groupDetails) {
            this.groupDetails.addAll(groupDetails);
        }
    }

    public static final class GetPublicGroupsSearchMapper implements JSON2ObjectMapper<GetPublicGroupsSearch, JSONObject> {
        private static final GetPublicGroupsSearchMapper sInstance = new GetPublicGroupsSearchMapper();

        private GetPublicGroupsSearchMapper() {
        }

        public static GetPublicGroupsSearchMapper getInstance() {
            return sInstance;
        }

        public GetPublicGroupsSearch map(JSONObject json) {
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            List<GroupDetailValue> groupDetails = new ArrayList();
            JSONArray ary = json.optJSONArray("items");
            if (ary != null) {
                int len = ary.length();
                for (int i = 0; i < len; i++) {
                    JSONObject obj = ary.optJSONObject(i);
                    if (obj != null) {
                        groupDetails.add(new GroupDetailValue(obj));
                    }
                }
            }
            return new GetPublicGroupsSearch(groupDetails);
        }
    }

    public static final class GetPublicGroupsTree {
        public final PublicCategoryValue publiCategory;

        public GetPublicGroupsTree(PublicCategoryValue publicCategoryValue) {
            this.publiCategory = publicCategoryValue;
        }
    }

    public static final class GetPublicGroupsTreeMapper implements JSON2ObjectMapper<GetPublicGroupsTree, JSONObject> {
        private static final GetPublicGroupsTreeMapper sInstance = new GetPublicGroupsTreeMapper();

        private GetPublicGroupsTreeMapper() {
        }

        public static GetPublicGroupsTreeMapper getInstance() {
            return sInstance;
        }

        public GetPublicGroupsTree map(JSONObject json) {
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new GetPublicGroupsTree(new PublicCategoryValue(json));
        }
    }

    public static final class GetRanking implements ConvertibleResponse {
        public final long cursor;
        public final int limit;
        public final List<RankingEntryValue> orders;
        public final RankingDetailValue rankingDetail;
        public final int totalResults;

        public GetRanking(RankingDetailValue rankingDetail, int totalResults, long cursor, int limit, List<RankingEntryValue> orders) {
            this.rankingDetail = rankingDetail;
            this.totalResults = totalResults;
            this.cursor = cursor;
            this.limit = limit;
            this.orders = orders;
        }

        public JSONObject convertDataForClient() {
            JSONObject response = new JSONObject();
            try {
                JSONObject detail = this.rankingDetail.toJSONObject();
                if (detail == null) {
                    throw new JSONException("");
                }
                response.put("ranking", detail);
                response.put("total_results", Integer.toString(this.totalResults));
                response.put("cursor", Long.toString(this.cursor));
                response.put(com.kayac.lobi.libnakamap.net.APIDef.GetRanking.RequestKey.OPTION_LIMIT, Integer.toString(this.limit));
                JSONArray array = new JSONArray();
                for (RankingEntryValue entry : this.orders) {
                    JSONObject json = entry.toJSONObject();
                    if (json == null) {
                        throw new JSONException("");
                    }
                    array.put(json);
                }
                response.put("orders", array);
                return response;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static final class GetRankingMapper implements JSON2ObjectMapper<GetRanking, JSONObject> {
        private static final GetRankingMapper sInstance = new GetRankingMapper();

        private GetRankingMapper() {
        }

        public static GetRankingMapper getInstance() {
            return sInstance;
        }

        public GetRanking map(JSONObject json) {
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            List<RankingEntryValue> orders = new ArrayList();
            RankingDetailValue rankingDetail = new RankingDetailValue(json.optJSONObject("ranking"));
            int totalResults = Integer.parseInt(JSONUtil.getString(json, "total_results", "0"));
            long cursor = Long.parseLong(JSONUtil.getString(json, "cursor", "0"));
            int limit = Integer.parseInt(JSONUtil.getString(json, com.kayac.lobi.libnakamap.net.APIDef.GetRanking.RequestKey.OPTION_LIMIT, "0"));
            JSONArray ary = json.optJSONArray("orders");
            if (ary == null) {
                return new GetRanking(rankingDetail, totalResults, cursor, limit, orders);
            }
            int len = ary.length();
            for (int i = 0; i < len; i++) {
                JSONObject obj = ary.optJSONObject(i);
                if (obj != null) {
                    orders.add(new RankingEntryValue(obj));
                }
            }
            return new GetRanking(rankingDetail, totalResults, cursor, limit, orders);
        }
    }

    public static final class GetRankings implements ConvertibleResponse {
        public final List<Pair<RankingDetailValue, RankingEntryValue>> rankings;

        public GetRankings(List<Pair<RankingDetailValue, RankingEntryValue>> rankings) {
            this.rankings = rankings;
        }

        public JSONObject convertDataForClient() {
            JSONObject response = new JSONObject();
            try {
                JSONArray array = new JSONArray();
                for (Pair<RankingDetailValue, RankingEntryValue> pair : this.rankings) {
                    JSONObject json = new JSONObject();
                    json.put("ranking", ((RankingDetailValue) pair.first).toJSONObject());
                    json.put("order", ((RankingEntryValue) pair.second).toJSONObject());
                    array.put(json);
                }
                response.put("rankings", array);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return response;
        }
    }

    public static final class GetRankingsMapper implements JSON2ObjectMapper<GetRankings, JSONObject> {
        private static final GetRankingsMapper sInstance = new GetRankingsMapper();

        private GetRankingsMapper() {
        }

        public static GetRankingsMapper getInstance() {
            return sInstance;
        }

        public GetRankings map(JSONObject json) {
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            List<Pair<RankingDetailValue, RankingEntryValue>> rankings = new ArrayList();
            JSONArray ary = json.optJSONArray("rankings");
            if (ary == null) {
                return new GetRankings(rankings);
            }
            int len = ary.length();
            for (int i = 0; i < len; i++) {
                JSONObject obj = ary.optJSONObject(i);
                if (obj != null) {
                    rankings.add(new Pair(new RankingDetailValue(obj.optJSONObject("ranking")), new RankingEntryValue(obj.optJSONObject("order"))));
                }
            }
            return new GetRankings(rankings);
        }
    }

    public static final class GetSdkReport {
        public final boolean success;

        public GetSdkReport(boolean success) {
            this.success = success;
        }
    }

    public static final class GetSdkReportMapper implements JSON2ObjectMapper<GetSdkReport, JSONObject> {
        private static final GetSdkReportMapper sInstance = new GetSdkReportMapper();

        private GetSdkReportMapper() {
        }

        public static final GetSdkReportMapper getInstance() {
            return sInstance;
        }

        public GetSdkReport map(JSONObject k) {
            return new GetSdkReport(TextUtils.equals("1", JSONUtil.getString(k, SNSInterface.SUCCESS, "0")));
        }
    }

    public static final class GetSdkStartup {
        public final StartupConfigValue config;

        public GetSdkStartup(StartupConfigValue config) {
            this.config = config;
        }
    }

    public static final class GetSdkStartupMapper implements JSON2ObjectMapper<GetSdkStartup, JSONObject> {
        private static final GetSdkStartupMapper sInstance = new GetSdkStartupMapper();

        private GetSdkStartupMapper() {
        }

        public static GetSdkStartupMapper getInstance() {
            return sInstance;
        }

        public GetSdkStartup map(JSONObject k) {
            return new GetSdkStartup(new StartupConfigValue(k));
        }
    }

    public static final class GetSignupMessage {
        public final String labelNG;
        public final String labelOK;
        public final String message;
        public final String title;

        public GetSignupMessage(String message, String title, String labelOK, String labelNG) {
            this.message = message;
            this.title = title;
            this.labelOK = labelOK;
            this.labelNG = labelNG;
        }
    }

    public static final class GetSignupMessageMapper implements JSON2ObjectMapper<GetSignupMessage, JSONObject> {
        private static final GetSignupMessageMapper sInstance = new GetSignupMessageMapper();

        private GetSignupMessageMapper() {
        }

        public static GetSignupMessageMapper getInstance() {
            return sInstance;
        }

        public GetSignupMessage map(JSONObject k) {
            if (k.has("message")) {
                return new GetSignupMessage(k.optString("message"), k.optString(LoginEntranceDialog.ARGUMENTS_TITLE), k.optString(LoginEntranceDialog.ARGUMENTS_LABEL_OK), k.optString(LoginEntranceDialog.ARGUMENTS_LABEL_NG));
            }
            return null;
        }
    }

    public static final class GetSignupPromote {
        public final String message;

        public GetSignupPromote(String message) {
            this.message = message;
        }
    }

    public static final class GetSignupPromoteMapper implements JSON2ObjectMapper<GetSignupPromote, JSONObject> {
        private static final GetSignupPromoteMapper sInstance = new GetSignupPromoteMapper();

        private GetSignupPromoteMapper() {
        }

        public static GetSignupPromoteMapper getInstance() {
            return sInstance;
        }

        public GetSignupPromote map(JSONObject k) {
            if (k.has("message")) {
                return new GetSignupPromote(k.optString("message"));
            }
            return null;
        }
    }

    public static final class GetStampUnlocked {
        public final boolean success;

        public GetStampUnlocked(boolean success) {
            this.success = success;
        }
    }

    public static final class GetStampUnlockedMapper implements JSON2ObjectMapper<GetStampUnlocked, JSONObject> {
        private static final GetStampUnlockedMapper sInstance = new GetStampUnlockedMapper();

        private GetStampUnlockedMapper() {
        }

        public static GetStampUnlockedMapper getInstance() {
            return sInstance;
        }

        public GetStampUnlocked map(JSONObject k) {
            if (!k.has(GCMConstants.EXTRA_ERROR) && k.has(SNSInterface.SUCCESS)) {
                return new GetStampUnlocked("1".equals(k.optString(SNSInterface.SUCCESS, null)));
            }
            return null;
        }
    }

    public static final class GetStamps {
        public final List<StampValue> items = new ArrayList();

        public GetStamps(List<StampValue> items) {
            this.items.addAll(items);
        }
    }

    public static final class GetStampsMapper implements JSON2ObjectMapper<GetStamps, JSONObject> {
        private static final GetStampsMapper sInstance = new GetStampsMapper();

        private GetStampsMapper() {
        }

        public static GetStampsMapper getInstance() {
            return sInstance;
        }

        public GetStamps map(JSONObject json) {
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            List<StampValue> items = new ArrayList();
            JSONArray ary = json.optJSONArray("items");
            if (ary != null) {
                int len = ary.length();
                for (int i = 0; i < len; i++) {
                    JSONObject obj = ary.optJSONObject(i);
                    if (obj != null) {
                        items.add(new StampValue(obj));
                    }
                }
            }
            return new GetStamps(items);
        }
    }

    public static final class GetStore {
        public final StampStoreValue banner;
        public final List<StampStoreValue> items = new ArrayList();
        public final String nextCursor;

        public GetStore(List<StampStoreValue> items, String cursor, StampStoreValue bannerStampStoreValue) {
            this.items.addAll(items);
            this.nextCursor = cursor;
            this.banner = bannerStampStoreValue;
        }
    }

    public static final class GetStoreMapper implements JSON2ObjectMapper<GetStore, JSONObject> {
        private static final GetStoreMapper sInstance = new GetStoreMapper();

        private GetStoreMapper() {
        }

        public static GetStoreMapper getInstance() {
            return sInstance;
        }

        public GetStore map(JSONObject json) {
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            List<StampStoreValue> items = new ArrayList();
            JSONArray ary = json.optJSONArray("items");
            if (ary != null) {
                int len = ary.length();
                for (int i = 0; i < len; i++) {
                    JSONObject obj = ary.optJSONObject(i);
                    if (obj != null) {
                        items.add(new StampStoreValue(obj));
                    }
                }
            }
            String cursor = json.optString("next_page");
            StampStoreValue banner = null;
            JSONObject objBanner = json.optJSONObject("banner");
            if (objBanner != null) {
                StampStoreValue bannerItem = new StampStoreValue(objBanner.optJSONObject("item"));
                String bannerImage = objBanner.optString("image");
                String bannerUrl = objBanner.optString("url");
                Builder builder = new Builder(bannerItem);
                builder.setBannerImage(bannerImage);
                builder.setBannerUrl(bannerUrl);
                banner = builder.build();
            }
            return new GetStore(items, cursor, banner);
        }
    }

    public static final class GetStoreStampDetail {
        public final StampStoreValue detail;

        public GetStoreStampDetail(StampStoreValue stampStoreValue) {
            this.detail = stampStoreValue;
        }
    }

    public static final class GetStoreStampDetailMapper implements JSON2ObjectMapper<GetStoreStampDetail, JSONObject> {
        private static final GetStoreStampDetailMapper sInstance = new GetStoreStampDetailMapper();

        private GetStoreStampDetailMapper() {
        }

        public static GetStoreStampDetailMapper getInstance() {
            return sInstance;
        }

        public GetStoreStampDetail map(JSONObject json) {
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new GetStoreStampDetail(new StampStoreValue(json));
        }
    }

    public static final class GetTerms {
        public final String terms;

        public GetTerms(String terms) {
            this.terms = terms;
        }
    }

    public static final class GetTermsMapper implements JSON2ObjectMapper<GetTerms, JSONObject> {
        private static final GetTermsMapper sInstance = new GetTermsMapper();

        private GetTermsMapper() {
        }

        public static GetTermsMapper getInstance() {
            return sInstance;
        }

        public GetTerms map(JSONObject k) {
            if (k.has("terms")) {
                return new GetTerms(k.optString("terms"));
            }
            return null;
        }
    }

    public static final class GetUser {
        public final UserValue user;

        public GetUser(UserValue user) {
            this.user = user;
        }
    }

    public static final class GetUserMapper implements JSON2ObjectMapper<GetUser, JSONObject> {
        private static final GetUserMapper sInstance = new GetUserMapper();

        private GetUserMapper() {
        }

        public static GetUserMapper getInstance() {
            return sInstance;
        }

        public GetUser map(JSONObject json) {
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new GetUser(new UserValue(json));
        }
    }

    public static final class GetUserV2 {
        public final ProfileValue profileValue;

        public GetUserV2(ProfileValue profileValue) {
            this.profileValue = profileValue;
        }
    }

    public static final class GetUserV2Mapper implements JSON2ObjectMapper<GetUserV2, JSONObject> {
        private static final GetUserV2Mapper sInstance = new GetUserV2Mapper();

        private GetUserV2Mapper() {
        }

        public static GetUserV2Mapper getInstance() {
            return sInstance;
        }

        public GetUserV2 map(JSONObject json) {
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new GetUserV2(new ProfileValue(json));
        }
    }

    public static final class GetUserV3 {
        public final ProfileValue profileValue;

        public GetUserV3(ProfileValue profileValue) {
            this.profileValue = profileValue;
        }
    }

    public static final class GetUserV3Mapper implements JSON2ObjectMapper<GetUserV3, JSONObject> {
        private static final GetUserV3Mapper sInstance = new GetUserV3Mapper();

        private GetUserV3Mapper() {
        }

        public static GetUserV3Mapper getInstance() {
            return sInstance;
        }

        public GetUserV3 map(JSONObject json) {
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new GetUserV3(new ProfileValue(json));
        }
    }

    public static final class GetUserVisibleGroups {
        public final List<Pair<GroupDetailValue, String>> groupDetails;
        public final String nextCursor;

        public GetUserVisibleGroups(List<Pair<GroupDetailValue, String>> groupDetails, String cursor) {
            this.groupDetails = groupDetails;
            this.nextCursor = cursor;
        }
    }

    public static final class GetUserVisibleGroupsMapper implements JSON2ObjectMapper<GetUserVisibleGroups, JSONObject> {
        private static final GetUserVisibleGroupsMapper sInstance = new GetUserVisibleGroupsMapper();

        private GetUserVisibleGroupsMapper() {
        }

        public static GetUserVisibleGroupsMapper getInstance() {
            return sInstance;
        }

        public GetUserVisibleGroups map(JSONObject json) {
            List<Pair<GroupDetailValue, String>> list = new ArrayList();
            JSONArray ary = json.optJSONArray("public_groups");
            if (ary != null) {
                int len = ary.length();
                for (int i = 0; i < len; i++) {
                    JSONObject obj = ary.optJSONObject(i);
                    if (obj != null) {
                        list.add(new Pair(new GroupDetailValue(obj.optJSONObject("group")), obj.optString("visibility", "0")));
                    }
                }
            }
            return new GetUserVisibleGroups(list, json.optString("next_cursor", "0"));
        }
    }

    public static final class GetUsers {
        public final List<UserValue> users = new ArrayList();

        public GetUsers(List<UserValue> users) {
            this.users.addAll(users);
        }
    }

    public static final class GetUsersMapper implements JSON2ObjectMapper<GetUsers, JSONObject> {
        private static final GetUsersMapper sInstance = new GetUsersMapper();

        private GetUsersMapper() {
        }

        public static GetUsersMapper getInstance() {
            return sInstance;
        }

        public GetUsers map(JSONObject json) {
            List<UserValue> users = new ArrayList();
            if (json == null) {
                return new GetUsers(users);
            }
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            JSONArray ary = json.optJSONArray("users");
            if (ary == null) {
                return new GetUsers(users);
            }
            int len = ary.length();
            for (int i = 0; i < len; i++) {
                JSONObject obj = ary.optJSONObject(i);
                if (obj != null) {
                    users.add(new UserValue(obj));
                }
            }
            return new GetUsers(users);
        }
    }

    public static final class GetUsersSearch {
        public final List<UserValue> users = new ArrayList();

        public GetUsersSearch(List<UserValue> users) {
            this.users.addAll(users);
        }
    }

    public static final class GetUsersSearchMapper implements JSON2ObjectMapper<GetUsersSearch, JSONObject> {
        private static final GetUsersSearchMapper sInstance = new GetUsersSearchMapper();

        private GetUsersSearchMapper() {
        }

        public static GetUsersSearchMapper getInstance() {
            return sInstance;
        }

        public GetUsersSearch map(JSONObject json) {
            List<UserValue> users = new ArrayList();
            if (json == null) {
                return new GetUsersSearch(users);
            }
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            JSONArray ary = json.optJSONArray("users");
            if (ary == null) {
                return new GetUsersSearch(users);
            }
            int len = ary.length();
            for (int i = 0; i < len; i++) {
                JSONObject obj = ary.optJSONObject(i);
                if (obj != null) {
                    users.add(new UserValue(obj));
                }
            }
            return new GetUsersSearch(users);
        }
    }

    public static final class GetVersion {
        public final String message;
        public final String newest;
        public final String stampVersion;
        public final String url;

        public GetVersion(String newest, String url, String message, String stampVersion) {
            this.newest = newest;
            this.url = url;
            this.message = message;
            this.stampVersion = stampVersion;
        }
    }

    public static final class GetVersionMapper implements JSON2ObjectMapper<GetVersion, JSONObject> {
        private static final GetVersionMapper sInstance = new GetVersionMapper();

        private GetVersionMapper() {
        }

        public static GetVersionMapper getInstance() {
            return sInstance;
        }

        public GetVersion map(JSONObject json) {
            return new GetVersion(json.optString("newest", "0"), json.optString("url", "0"), json.optString("message", "0"), json.optString("stamp_version", "0"));
        }
    }

    public static final class PostAccusations {
        public final boolean success;

        public PostAccusations(boolean success) {
            this.success = success;
        }
    }

    public static final class PostAccusationsMapper implements JSON2ObjectMapper<PostAccusations, JSONObject> {
        private static final PostAccusationsMapper sInstance = new PostAccusationsMapper();

        private PostAccusationsMapper() {
        }

        public static PostAccusationsMapper getInstance() {
            return sInstance;
        }

        public PostAccusations map(JSONObject json) {
            if (json == null) {
                return new PostAccusations(false);
            }
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new PostAccusations("1".equals(json.optString(SNSInterface.SUCCESS, "0")));
        }
    }

    public static final class PostAdTrackingClick {
        public final boolean success;

        public PostAdTrackingClick(boolean success) {
            this.success = success;
        }
    }

    public static final class PostAdTrackingClickMapper implements JSON2ObjectMapper<PostAdTrackingClick, JSONObject> {
        private static final PostAdTrackingClickMapper sInstance = new PostAdTrackingClickMapper();

        private PostAdTrackingClickMapper() {
        }

        public static PostAdTrackingClickMapper getInstance() {
            return sInstance;
        }

        public PostAdTrackingClick map(JSONObject json) {
            if (json == null) {
                return new PostAdTrackingClick(false);
            }
            return new PostAdTrackingClick("1".equals(json.optString(SNSInterface.SUCCESS, "0")));
        }
    }

    public static final class PostAdTrackingConversion {
        public final boolean success;

        public PostAdTrackingConversion(boolean success) {
            this.success = success;
        }
    }

    public static final class PostAdTrackingConversionMapper implements JSON2ObjectMapper<PostAdTrackingConversion, JSONObject> {
        private static final PostAdTrackingConversionMapper sInstance = new PostAdTrackingConversionMapper();

        private PostAdTrackingConversionMapper() {
        }

        public static PostAdTrackingConversionMapper getInstance() {
            return sInstance;
        }

        public PostAdTrackingConversion map(JSONObject json) {
            if (json == null) {
                return new PostAdTrackingConversion(false);
            }
            return new PostAdTrackingConversion("1".equals(json.optString(SNSInterface.SUCCESS, "0")));
        }
    }

    public static final class PostAdTrackingImpression {
        public final boolean success;

        public PostAdTrackingImpression(boolean success) {
            this.success = success;
        }
    }

    public static final class PostAdTrackingImpressionMapper implements JSON2ObjectMapper<PostAdTrackingImpression, JSONObject> {
        private static final PostAdTrackingImpressionMapper sInstance = new PostAdTrackingImpressionMapper();

        private PostAdTrackingImpressionMapper() {
        }

        public static PostAdTrackingImpressionMapper getInstance() {
            return sInstance;
        }

        public PostAdTrackingImpression map(JSONObject json) {
            if (json == null) {
                return new PostAdTrackingImpression(false);
            }
            return new PostAdTrackingImpression("1".equals(json.optString(SNSInterface.SUCCESS, "0")));
        }
    }

    public static final class PostAppData {
        public final boolean success;

        public PostAppData(boolean success) {
            this.success = success;
        }
    }

    public static final class PostAppDataMapper implements JSON2ObjectMapper<PostAppData, JSONObject> {
        private static final PostAppDataMapper sInstance = new PostAppDataMapper();

        private PostAppDataMapper() {
        }

        public static PostAppDataMapper getInstance() {
            return sInstance;
        }

        public PostAppData map(JSONObject json) {
            return new PostAppData(TextUtils.equals("1", JSONUtil.getString(json, SNSInterface.SUCCESS, "0")));
        }
    }

    public static final class PostAppDataRemove {
        public final boolean success;

        public PostAppDataRemove(boolean success) {
            this.success = success;
        }
    }

    public static final class PostAppDataRemoveMapper implements JSON2ObjectMapper<PostAppDataRemove, JSONObject> {
        private static final PostAppDataRemoveMapper sInstance = new PostAppDataRemoveMapper();

        private PostAppDataRemoveMapper() {
        }

        public static PostAppDataRemoveMapper getInstance() {
            return sInstance;
        }

        public PostAppDataRemove map(JSONObject json) {
            return new PostAppDataRemove(TextUtils.equals("1", JSONUtil.getString(json, SNSInterface.SUCCESS, "0")));
        }
    }

    public static final class PostBindFinish {
        public final AppValue app;

        public PostBindFinish(AppValue app) {
            this.app = app;
        }
    }

    public static final class PostBindFinishMapper implements JSON2ObjectMapper<PostBindFinish, JSONObject> {
        private static final PostBindFinishMapper sInstance = new PostBindFinishMapper();

        private PostBindFinishMapper() {
        }

        public static PostBindFinishMapper getInstance() {
            return sInstance;
        }

        public PostBindFinish map(JSONObject json) {
            return new PostBindFinish(new AppValue(json));
        }
    }

    public static final class PostBindStart {
        public final String bindToken;
        public final List<String> errors;

        public PostBindStart(String bindToken, List<String> errors) {
            this.bindToken = bindToken;
            this.errors = errors;
        }
    }

    public static final class PostBindStartMapper implements JSON2ObjectMapper<PostBindStart, JSONObject> {
        private static final PostBindStartMapper sInstance = new PostBindStartMapper();

        private PostBindStartMapper() {
        }

        public static PostBindStartMapper getInstance() {
            return sInstance;
        }

        public PostBindStart map(JSONObject json) {
            List<String> errors = new ArrayList();
            if (json == null) {
                return new PostBindStart("", errors);
            }
            String bindToken = JSONUtil.getString(json, SDKBindFinish.BIND_TOKEN, "");
            JSONArray jsonArray = json.optJSONArray(GCMConstants.EXTRA_ERROR);
            if (jsonArray != null) {
                int len = jsonArray.length();
                for (int i = 0; i < len; i++) {
                    String error = jsonArray.optString(i);
                    if (error != null) {
                        errors.add(error);
                    }
                }
            }
            return new PostBindStart(bindToken, errors);
        }
    }

    public static final class PostDefaultMeContacts {
        public final boolean success;

        public PostDefaultMeContacts(boolean success) {
            this.success = success;
        }
    }

    public static final class PostDefaultMeContactsMapper implements JSON2ObjectMapper<PostDefaultMeContacts, JSONObject> {
        private static final PostDefaultMeContactsMapper sInstance = new PostDefaultMeContactsMapper();

        private PostDefaultMeContactsMapper() {
        }

        public static PostDefaultMeContactsMapper getInstance() {
            return sInstance;
        }

        public PostDefaultMeContacts map(JSONObject json) {
            if (json == null) {
                return new PostDefaultMeContacts(false);
            }
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new PostDefaultMeContacts("1".equals(json.optString(SNSInterface.SUCCESS, "0")));
        }
    }

    public static final class PostDefaultMeContactsRemove {
        public final boolean success;

        public PostDefaultMeContactsRemove(boolean success) {
            this.success = success;
        }
    }

    public static final class PostDefaultMeContactsRemoveMapper implements JSON2ObjectMapper<PostDefaultMeContactsRemove, JSONObject> {
        private static final PostDefaultMeContactsRemoveMapper sInstance = new PostDefaultMeContactsRemoveMapper();

        private PostDefaultMeContactsRemoveMapper() {
        }

        public static PostDefaultMeContactsRemoveMapper getInstance() {
            return sInstance;
        }

        public PostDefaultMeContactsRemove map(JSONObject json) {
            if (json == null || json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new PostDefaultMeContactsRemove("1".equals(json.optString(SNSInterface.SUCCESS, "0")));
        }
    }

    public static final class PostGroup {
        public final GroupDetailValue groupDetail;

        public PostGroup(GroupDetailValue groupDetail) {
            this.groupDetail = groupDetail;
        }
    }

    public static final class PostGroupChat {
        public final ChatValue chat;

        public PostGroupChat(ChatValue chat) {
            this.chat = chat;
        }
    }

    public static final class PostGroupChatMapper implements JSON2ObjectMapper<PostGroupChat, JSONObject> {
        private static final PostGroupChatMapper sInstance = new PostGroupChatMapper();

        private PostGroupChatMapper() {
        }

        public static PostGroupChatMapper getInstance() {
            return sInstance;
        }

        public PostGroupChat map(JSONObject json) {
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new PostGroupChat(new ChatValue(json));
        }
    }

    public static final class PostGroupChatRemove {
        public final boolean success;

        public PostGroupChatRemove(boolean success) {
            this.success = success;
        }
    }

    public static final class PostGroupChatRemoveMapper implements JSON2ObjectMapper<PostGroupChatRemove, JSONObject> {
        private static final PostGroupChatRemoveMapper sInstance = new PostGroupChatRemoveMapper();

        private PostGroupChatRemoveMapper() {
        }

        public static PostGroupChatRemoveMapper getInstance() {
            return sInstance;
        }

        public PostGroupChatRemove map(JSONObject json) {
            if (json == null) {
                return new PostGroupChatRemove(false);
            }
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new PostGroupChatRemove("1".equals(json.optString(SNSInterface.SUCCESS, "0")));
        }
    }

    public static final class PostGroupExId {
        public final GroupDetailValue groupDetail;

        public PostGroupExId(GroupDetailValue groupDetail) {
            this.groupDetail = groupDetail;
        }
    }

    public static final class PostGroupExIdMapper implements JSON2ObjectMapper<PostGroupExId, JSONObject> {
        private static final PostGroupExIdMapper sInstance = new PostGroupExIdMapper();

        private PostGroupExIdMapper() {
        }

        public static PostGroupExIdMapper getInstance() {
            return sInstance;
        }

        public PostGroupExId map(JSONObject json) {
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new PostGroupExId(new GroupDetailValue(json));
        }
    }

    public static final class PostGroupIcon {
        public final GroupDetailValue groupDetail;

        public PostGroupIcon(GroupDetailValue groupDetail) {
            this.groupDetail = groupDetail;
        }
    }

    public static final class PostGroupIconMapper implements JSON2ObjectMapper<PostGroupIcon, JSONObject> {
        private static final PostGroupIconMapper sInstance = new PostGroupIconMapper();

        private PostGroupIconMapper() {
        }

        public static PostGroupIconMapper getInstance() {
            return sInstance;
        }

        public PostGroupIcon map(JSONObject json) {
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new PostGroupIcon(new GroupDetailValue(json));
        }
    }

    public static final class PostGroupJoinWithExId {
        public final GroupValue group;

        public PostGroupJoinWithExId(GroupValue group) {
            this.group = group;
        }
    }

    public static final class PostGroupJoinWithExIdMapper implements JSON2ObjectMapper<PostGroupJoinWithExId, JSONObject> {
        private static final PostGroupJoinWithExIdMapper sInstance = new PostGroupJoinWithExIdMapper();

        private PostGroupJoinWithExIdMapper() {
        }

        public static PostGroupJoinWithExIdMapper getInstance() {
            return sInstance;
        }

        public PostGroupJoinWithExId map(JSONObject json) {
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new PostGroupJoinWithExId(new GroupValue(json));
        }
    }

    public static final class PostGroupJoinWithGroupUid {
        public final GroupValue group;

        public PostGroupJoinWithGroupUid(GroupValue group) {
            this.group = group;
        }
    }

    public static final class PostGroupJoinWithGroupUidMapper implements JSON2ObjectMapper<PostGroupJoinWithGroupUid, JSONObject> {
        private static final PostGroupJoinWithGroupUidMapper sInstance = new PostGroupJoinWithGroupUidMapper();

        private PostGroupJoinWithGroupUidMapper() {
        }

        public static PostGroupJoinWithGroupUidMapper getInstance() {
            return sInstance;
        }

        public PostGroupJoinWithGroupUid map(JSONObject json) {
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new PostGroupJoinWithGroupUid(new GroupValue(json));
        }
    }

    public static final class PostGroupKick {
        public final boolean success;

        public PostGroupKick(boolean success) {
            this.success = success;
        }
    }

    public static final class PostGroupKickExId {
        public final boolean success;

        public PostGroupKickExId(boolean success) {
            this.success = success;
        }
    }

    public static final class PostGroupKickExIdMapper implements JSON2ObjectMapper<PostGroupKickExId, JSONObject> {
        private static final PostGroupKickExIdMapper sInstance = new PostGroupKickExIdMapper();

        private PostGroupKickExIdMapper() {
        }

        public static PostGroupKickExIdMapper getInstance() {
            return sInstance;
        }

        public PostGroupKickExId map(JSONObject json) {
            if (json == null || json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new PostGroupKickExId("1".equals(json.optString(SNSInterface.SUCCESS, "0")));
        }
    }

    public static final class PostGroupKickMapper implements JSON2ObjectMapper<PostGroupKick, JSONObject> {
        private static final PostGroupKickMapper sInstance = new PostGroupKickMapper();

        private PostGroupKickMapper() {
        }

        public static PostGroupKickMapper getInstance() {
            return sInstance;
        }

        public PostGroupKick map(JSONObject json) {
            if (json == null) {
                return new PostGroupKick(false);
            }
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new PostGroupKick("1".equals(json.optString(SNSInterface.SUCCESS, "0")));
        }
    }

    public static final class PostGroupMapper implements JSON2ObjectMapper<PostGroup, JSONObject> {
        private static final PostGroupMapper sInstance = new PostGroupMapper();

        private PostGroupMapper() {
        }

        public static PostGroupMapper getInstance() {
            return sInstance;
        }

        public PostGroup map(JSONObject json) {
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new PostGroup(new GroupDetailValue(json));
        }
    }

    public static final class PostGroupMembers {
        public final boolean success;

        public PostGroupMembers(boolean success) {
            this.success = success;
        }
    }

    public static final class PostGroupMembersExId {
        public final boolean success;

        public PostGroupMembersExId(boolean success) {
            this.success = success;
        }
    }

    public static final class PostGroupMembersExIdMapper implements JSON2ObjectMapper<PostGroupMembersExId, JSONObject> {
        private static final PostGroupMembersExIdMapper sInstance = new PostGroupMembersExIdMapper();

        private PostGroupMembersExIdMapper() {
        }

        public static PostGroupMembersExIdMapper getInstance() {
            return sInstance;
        }

        public PostGroupMembersExId map(JSONObject json) {
            if (json == null || json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new PostGroupMembersExId("1".equals(json.optString(SNSInterface.SUCCESS, "0")));
        }
    }

    public static final class PostGroupMembersMapper implements JSON2ObjectMapper<PostGroupMembers, JSONObject> {
        private static final PostGroupMembersMapper sInstance = new PostGroupMembersMapper();

        private PostGroupMembersMapper() {
        }

        public static PostGroupMembersMapper getInstance() {
            return sInstance;
        }

        public PostGroupMembers map(JSONObject json) {
            if (json == null) {
                return new PostGroupMembers(false);
            }
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new PostGroupMembers("1".equals(json.optString(SNSInterface.SUCCESS, "0")));
        }
    }

    public static final class PostGroupPart {
        public final boolean success;

        public PostGroupPart(boolean success) {
            this.success = success;
        }
    }

    public static final class PostGroupPartExId {
        public final boolean success;

        public PostGroupPartExId(boolean success) {
            this.success = success;
        }
    }

    public static final class PostGroupPartExIdMapper implements JSON2ObjectMapper<PostGroupPartExId, JSONObject> {
        private static final PostGroupPartExIdMapper sInstance = new PostGroupPartExIdMapper();

        private PostGroupPartExIdMapper() {
        }

        public static PostGroupPartExIdMapper getInstance() {
            return sInstance;
        }

        public PostGroupPartExId map(JSONObject json) {
            if (json == null || json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new PostGroupPartExId("1".equals(json.optString(SNSInterface.SUCCESS, "0")));
        }
    }

    public static final class PostGroupPartMapper implements JSON2ObjectMapper<PostGroupPart, JSONObject> {
        private static final PostGroupPartMapper sInstance = new PostGroupPartMapper();

        private PostGroupPartMapper() {
        }

        public static PostGroupPartMapper getInstance() {
            return sInstance;
        }

        public PostGroupPart map(JSONObject json) {
            if (json == null) {
                return new PostGroupPart(false);
            }
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new PostGroupPart("1".equals(json.optString(SNSInterface.SUCCESS, "0")));
        }
    }

    public static final class PostGroupPrivacy {
        public final GroupValue group;

        public PostGroupPrivacy(GroupValue group) {
            this.group = group;
        }
    }

    public static final class PostGroupPrivacyMapper implements JSON2ObjectMapper<PostGroupPrivacy, JSONObject> {
        private static final PostGroupPrivacyMapper sInstance = new PostGroupPrivacyMapper();

        private PostGroupPrivacyMapper() {
        }

        public static PostGroupPrivacyMapper getInstance() {
            return sInstance;
        }

        public PostGroupPrivacy map(JSONObject json) {
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new PostGroupPrivacy(new GroupValue(json));
        }
    }

    public static final class PostGroupPush {
        public final boolean success;

        public PostGroupPush(boolean success) {
            this.success = success;
        }
    }

    public static final class PostGroupPushMapper implements JSON2ObjectMapper<PostGroupPush, JSONObject> {
        private static final PostGroupPushMapper sInstance = new PostGroupPushMapper();

        private PostGroupPushMapper() {
        }

        public static PostGroupPushMapper getInstance() {
            return sInstance;
        }

        public PostGroupPush map(JSONObject json) {
            if (json == null) {
                return new PostGroupPush(false);
            }
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new PostGroupPush("1".equals(json.optString(SNSInterface.SUCCESS, "0")));
        }
    }

    public static final class PostGroupRemove {
        public final boolean success;

        public PostGroupRemove(boolean success) {
            this.success = success;
        }
    }

    public static final class PostGroupRemoveExId {
        public final boolean success;

        public PostGroupRemoveExId(boolean success) {
            this.success = success;
        }
    }

    public static final class PostGroupRemoveExIdMapper implements JSON2ObjectMapper<PostGroupRemoveExId, JSONObject> {
        private static final PostGroupRemoveExIdMapper sInstance = new PostGroupRemoveExIdMapper();

        private PostGroupRemoveExIdMapper() {
        }

        public static PostGroupRemoveExIdMapper getInstance() {
            return sInstance;
        }

        public PostGroupRemoveExId map(JSONObject json) {
            if (json == null || json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new PostGroupRemoveExId("1".equals(json.optString(SNSInterface.SUCCESS, "0")));
        }
    }

    public static final class PostGroupRemoveMapper implements JSON2ObjectMapper<PostGroupRemove, JSONObject> {
        private static final PostGroupRemoveMapper sInstance = new PostGroupRemoveMapper();

        private PostGroupRemoveMapper() {
        }

        public static PostGroupRemoveMapper getInstance() {
            return sInstance;
        }

        public PostGroupRemove map(JSONObject json) {
            if (json == null) {
                return new PostGroupRemove(false);
            }
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new PostGroupRemove("1".equals(json.optString(SNSInterface.SUCCESS, "0")));
        }
    }

    public static final class PostGroupTransferExId {
        public final GroupDetailValue groupDetail;

        public PostGroupTransferExId(GroupDetailValue groupDetail) {
            this.groupDetail = groupDetail;
        }
    }

    public static final class PostGroupTransferExIdMapper implements JSON2ObjectMapper<PostGroupTransferExId, JSONObject> {
        private static final PostGroupTransferExIdMapper sInstance = new PostGroupTransferExIdMapper();

        private PostGroupTransferExIdMapper() {
        }

        public static PostGroupTransferExIdMapper getInstance() {
            return sInstance;
        }

        public PostGroupTransferExId map(JSONObject json) {
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new PostGroupTransferExId(new GroupDetailValue(json));
        }
    }

    public static final class PostGroupVisibility {
        public final boolean success;

        public PostGroupVisibility(boolean success) {
            this.success = success;
        }
    }

    public static final class PostGroupVisibilityMapper implements JSON2ObjectMapper<PostGroupVisibility, JSONObject> {
        private static final PostGroupVisibilityMapper sInstance = new PostGroupVisibilityMapper();

        private PostGroupVisibilityMapper() {
        }

        public static PostGroupVisibilityMapper getInstance() {
            return sInstance;
        }

        public PostGroupVisibility map(JSONObject json) {
            if (json == null) {
                return new PostGroupVisibility(false);
            }
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new PostGroupVisibility("1".equals(json.optString(SNSInterface.SUCCESS, "0")));
        }
    }

    public static final class PostGroupWallpaper {
        public final String url;

        public PostGroupWallpaper(String url) {
            this.url = url;
        }
    }

    public static final class PostGroupWallpaperMapper implements JSON2ObjectMapper<PostGroupWallpaper, JSONObject> {
        private static final PostGroupWallpaperMapper sInstance = new PostGroupWallpaperMapper();

        private PostGroupWallpaperMapper() {
        }

        public static PostGroupWallpaperMapper getInstance() {
            return sInstance;
        }

        public PostGroupWallpaper map(JSONObject json) {
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new PostGroupWallpaper(json.optString(com.kayac.lobi.libnakamap.net.APIDef.PostGroupWallpaper.RequestKey.WALLPAPER, ""));
        }
    }

    public static final class PostGroupWallpaperRemove {
        public final boolean success;

        public PostGroupWallpaperRemove(boolean success) {
            this.success = success;
        }
    }

    public static final class PostGroupWallpaperRemoveMapper implements JSON2ObjectMapper<PostGroupWallpaperRemove, JSONObject> {
        private static final PostGroupWallpaperRemoveMapper sInstance = new PostGroupWallpaperRemoveMapper();

        private PostGroupWallpaperRemoveMapper() {
        }

        public static PostGroupWallpaperRemoveMapper getInstance() {
            return sInstance;
        }

        public PostGroupWallpaperRemove map(JSONObject json) {
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new PostGroupWallpaperRemove("1".equals(json.optString(SNSInterface.SUCCESS, "0")));
        }
    }

    public static final class PostGroups1on1s {
        public final GroupDetailValue groupDetail;

        public PostGroups1on1s(GroupDetailValue groupDetail) {
            this.groupDetail = groupDetail;
        }
    }

    public static final class PostGroups1on1sMapper implements JSON2ObjectMapper<PostGroups1on1s, JSONObject> {
        private static final PostGroups1on1sMapper sInstance = new PostGroups1on1sMapper();

        private PostGroups1on1sMapper() {
        }

        public static PostGroups1on1sMapper getInstance() {
            return sInstance;
        }

        public PostGroups1on1s map(JSONObject json) {
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new PostGroups1on1s(new GroupDetailValue(json));
        }
    }

    public static final class PostGroups {
        public final String name;
        public final String uid;

        public PostGroups(String name, String uid) {
            this.name = name;
            this.uid = uid;
        }
    }

    public static final class PostGroupsMapper implements JSON2ObjectMapper<PostGroups, JSONObject> {
        private static final PostGroupsMapper sInstance = new PostGroupsMapper();

        private PostGroupsMapper() {
        }

        public static PostGroupsMapper getInstance() {
            return sInstance;
        }

        public PostGroups map(JSONObject json) {
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new PostGroups(JSONUtil.getString(json, "name", null), JSONUtil.getString(json, "uid", null));
        }
    }

    public static final class PostInvitationsRecipients {
        public final GroupDetailValue group;
        public final UserValue user;

        public PostInvitationsRecipients(UserValue user, GroupDetailValue group) {
            this.user = user;
            this.group = group;
        }
    }

    public static final class PostInvitationsRecipientsMapper implements JSON2ObjectMapper<PostInvitationsRecipients, JSONObject> {
        private static final PostInvitationsRecipientsMapper sInstance = new PostInvitationsRecipientsMapper();

        private PostInvitationsRecipientsMapper() {
        }

        public static PostInvitationsRecipientsMapper getInstance() {
            return sInstance;
        }

        public PostInvitationsRecipients map(JSONObject json) {
            GroupDetailValue groupDetailValue = null;
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            JSONObject user = json.optJSONObject("user");
            JSONObject group = json.optJSONObject("group");
            UserValue userValue = user == null ? null : new UserValue(user);
            if (group != null) {
                groupDetailValue = new GroupDetailValue(group);
            }
            return new PostInvitationsRecipients(userValue, groupDetailValue);
        }
    }

    public static final class PostMeAuths {
        public final boolean success;

        public PostMeAuths(boolean success) {
            this.success = success;
        }
    }

    public static final class PostMeAuthsMail {
        public final boolean success;

        public PostMeAuthsMail(boolean success) {
            this.success = success;
        }
    }

    public static final class PostMeAuthsMailMapper implements JSON2ObjectMapper<PostMeAuthsMail, JSONObject> {
        private static final PostMeAuthsMailMapper sInstance = new PostMeAuthsMailMapper();

        private PostMeAuthsMailMapper() {
        }

        public static PostMeAuthsMailMapper getInstance() {
            return sInstance;
        }

        public PostMeAuthsMail map(JSONObject k) {
            if (k.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new PostMeAuthsMail("1".equals(k.optString(SNSInterface.SUCCESS, "0")));
        }
    }

    public static final class PostMeAuthsMapper implements JSON2ObjectMapper<PostMeAuths, JSONObject> {
        private static final PostMeAuthsMapper sInstance = new PostMeAuthsMapper();

        private PostMeAuthsMapper() {
        }

        public static PostMeAuthsMapper getInstance() {
            return sInstance;
        }

        public PostMeAuths map(JSONObject json) {
            if (json == null) {
                return new PostMeAuths(false);
            }
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new PostMeAuths("1".equals(json.optString(SNSInterface.SUCCESS, "0")));
        }
    }

    public static final class PostMeBlockingUsers {
        public final boolean success;

        public PostMeBlockingUsers(boolean success) {
            this.success = success;
        }
    }

    public static final class PostMeBlockingUsersMapper implements JSON2ObjectMapper<PostMeBlockingUsers, JSONObject> {
        private static final PostMeBlockingUsersMapper sInstance = new PostMeBlockingUsersMapper();

        private PostMeBlockingUsersMapper() {
        }

        public static PostMeBlockingUsersMapper getInstance() {
            return sInstance;
        }

        public PostMeBlockingUsers map(JSONObject json) {
            if (json == null || json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new PostMeBlockingUsers("1".equals(json.optString(SNSInterface.SUCCESS, "0")));
        }
    }

    public static final class PostMeBlockingUsersRemove {
        public final boolean success;

        public PostMeBlockingUsersRemove(boolean success) {
            this.success = success;
        }
    }

    public static final class PostMeBlockingUsersRemoveMapper implements JSON2ObjectMapper<PostMeBlockingUsersRemove, JSONObject> {
        private static final PostMeBlockingUsersRemoveMapper sInstance = new PostMeBlockingUsersRemoveMapper();

        private PostMeBlockingUsersRemoveMapper() {
        }

        public static PostMeBlockingUsersRemoveMapper getInstance() {
            return sInstance;
        }

        public PostMeBlockingUsersRemove map(JSONObject json) {
            if (json == null) {
                return new PostMeBlockingUsersRemove(false);
            }
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new PostMeBlockingUsersRemove("1".equals(json.optString(SNSInterface.SUCCESS, "0")));
        }
    }

    public static final class PostMeContacts {
        public final boolean success;

        public PostMeContacts(boolean success) {
            this.success = success;
        }
    }

    public static final class PostMeContactsExId {
        public final boolean success;

        public PostMeContactsExId(boolean success) {
            this.success = success;
        }
    }

    public static final class PostMeContactsExIdMapper implements JSON2ObjectMapper<PostMeContactsExId, JSONObject> {
        private static final PostMeContactsExIdMapper sInstance = new PostMeContactsExIdMapper();

        private PostMeContactsExIdMapper() {
        }

        public static PostMeContactsExIdMapper getInstance() {
            return sInstance;
        }

        public PostMeContactsExId map(JSONObject json) {
            if (json == null || json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new PostMeContactsExId("1".equals(json.optString(SNSInterface.SUCCESS, "0")));
        }
    }

    public static final class PostMeContactsMapper implements JSON2ObjectMapper<PostMeContacts, JSONObject> {
        private static final PostMeContactsMapper sInstance = new PostMeContactsMapper();

        private PostMeContactsMapper() {
        }

        public static PostMeContactsMapper getInstance() {
            return sInstance;
        }

        public PostMeContacts map(JSONObject json) {
            if (json == null) {
                return new PostMeContacts(false);
            }
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new PostMeContacts("1".equals(json.optString(SNSInterface.SUCCESS, "0")));
        }
    }

    public static final class PostMeContactsRemove {
        public final boolean success;

        public PostMeContactsRemove(boolean success) {
            this.success = success;
        }
    }

    public static final class PostMeContactsRemoveExId {
        public final boolean success;

        public PostMeContactsRemoveExId(boolean success) {
            this.success = success;
        }
    }

    public static final class PostMeContactsRemoveExIdMapper implements JSON2ObjectMapper<PostMeContactsRemoveExId, JSONObject> {
        private static final PostMeContactsRemoveExIdMapper sInstance = new PostMeContactsRemoveExIdMapper();

        private PostMeContactsRemoveExIdMapper() {
        }

        public static PostMeContactsRemoveExIdMapper getInstance() {
            return sInstance;
        }

        public PostMeContactsRemoveExId map(JSONObject json) {
            if (json == null || json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new PostMeContactsRemoveExId("1".equals(json.optString(SNSInterface.SUCCESS, "0")));
        }
    }

    public static final class PostMeContactsRemoveMapper implements JSON2ObjectMapper<PostMeContactsRemove, JSONObject> {
        private static final PostMeContactsRemoveMapper sInstance = new PostMeContactsRemoveMapper();

        private PostMeContactsRemoveMapper() {
        }

        public static PostMeContactsRemoveMapper getInstance() {
            return sInstance;
        }

        public PostMeContactsRemove map(JSONObject json) {
            if (json == null) {
                return new PostMeContactsRemove(false);
            }
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new PostMeContactsRemove("1".equals(json.optString(SNSInterface.SUCCESS, "0")));
        }
    }

    public static final class PostMeCover {
        public final String cover;

        public PostMeCover(String cover) {
            this.cover = cover;
        }
    }

    public static final class PostMeCoverMapper implements JSON2ObjectMapper<PostMeCover, JSONObject> {
        private static final PostMeCoverMapper sInstance = new PostMeCoverMapper();

        private PostMeCoverMapper() {
        }

        public static PostMeCoverMapper getInstance() {
            return sInstance;
        }

        public PostMeCover map(JSONObject json) {
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new PostMeCover(JSONUtil.getString(json, com.kayac.lobi.libnakamap.net.APIDef.PostMeCover.RequestKey.COVER, null));
        }
    }

    public static final class PostMeExId {
        public final boolean success;

        public PostMeExId(boolean success) {
            this.success = success;
        }
    }

    public static final class PostMeExIdMapper implements JSON2ObjectMapper<PostMeExId, JSONObject> {
        private static final PostMeExIdMapper sInstance = new PostMeExIdMapper();

        private PostMeExIdMapper() {
        }

        public static PostMeExIdMapper getInstance() {
            return sInstance;
        }

        public PostMeExId map(JSONObject k) {
            if (k.has(SNSInterface.SUCCESS)) {
                return new PostMeExId("1".equals(k.optString(SNSInterface.SUCCESS)));
            }
            return null;
        }
    }

    public static final class PostMeExternalContacts {
        public static final long NOT_CONTACT = -1;
        public final long contactedDate;
        public final String email;
        public final String facebookId;
        public final String mixiId;
        public final String screenName;
        public final String uid;
        public final UserValue user;

        public PostMeExternalContacts(String uid, String email, String screenName, String facebookId, String mixiId, long contactedDate, UserValue user) {
            this.uid = uid;
            this.email = email;
            this.screenName = screenName;
            this.facebookId = facebookId;
            this.mixiId = mixiId;
            this.contactedDate = contactedDate;
            this.user = user;
        }
    }

    public static final class PostMeExternalContactsMapper implements JSON2ObjectMapper<List<PostMeExternalContacts>, JSONArray> {
        private static final PostMeExternalContactsMapper sInstance = new PostMeExternalContactsMapper();

        private PostMeExternalContactsMapper() {
        }

        public static PostMeExternalContactsMapper getInstance() {
            return sInstance;
        }

        public List<PostMeExternalContacts> map(JSONArray ary) {
            List<PostMeExternalContacts> users = new ArrayList();
            int len = ary.length();
            for (int i = 0; i < len; i++) {
                JSONObject obj = ary.optJSONObject(i);
                if (obj != null) {
                    String uid = JSONUtil.getString(obj, "uid", null);
                    String email = JSONUtil.getString(obj, "email", null);
                    String screenName = JSONUtil.getString(obj, "screen_name", null);
                    String facebookId = JSONUtil.getString(obj, "facebook_id", null);
                    String mixiId = JSONUtil.getString(obj, "mixi_id", null);
                    long date = Long.parseLong(JSONUtil.getString(obj, "contacted_date", String.valueOf(-1)));
                    JSONObject user = obj.optJSONObject("user");
                    users.add(new PostMeExternalContacts(uid, email, screenName, facebookId, mixiId, date, user != null ? new UserValue(user) : null));
                }
            }
            return users;
        }
    }

    public static final class PostMeIcon {
        public final String icon;

        public PostMeIcon(String icon) {
            this.icon = icon;
        }
    }

    public static final class PostMeIconMapper implements JSON2ObjectMapper<PostMeIcon, JSONObject> {
        private static final PostMeIconMapper sInstance = new PostMeIconMapper();

        private PostMeIconMapper() {
        }

        public static PostMeIconMapper getInstance() {
            return sInstance;
        }

        public PostMeIcon map(JSONObject json) {
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new PostMeIcon(JSONUtil.getString(json, "icon", null));
        }
    }

    public static final class PostMeLocation {
        public final boolean success;

        public PostMeLocation(boolean success) {
            this.success = success;
        }
    }

    public static final class PostMeLocationMapper implements JSON2ObjectMapper<PostMeLocation, JSONObject> {
        private static final PostMeLocationMapper sInstance = new PostMeLocationMapper();

        public static PostMeLocationMapper getInstance() {
            return sInstance;
        }

        public PostMeLocation map(JSONObject k) {
            return new PostMeLocation(TextUtils.equals(k.optString(SNSInterface.SUCCESS), "1"));
        }
    }

    public static final class PostMeProfile {
        public final boolean success;

        public PostMeProfile(boolean success) {
            this.success = success;
        }
    }

    public static final class PostMeProfileMapper implements JSON2ObjectMapper<PostMeProfile, JSONObject> {
        private static final PostMeProfileMapper sInstance = new PostMeProfileMapper();

        private PostMeProfileMapper() {
        }

        public static PostMeProfileMapper getInstance() {
            return sInstance;
        }

        public PostMeProfile map(JSONObject json) {
            if (json == null || json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new PostMeProfile("1".equals(json.optString(SNSInterface.SUCCESS, "0")));
        }
    }

    public static final class PostMeRemoveComplete {
        public final boolean success;

        public PostMeRemoveComplete(boolean success) {
            this.success = success;
        }
    }

    public static final class PostMeRemoveCompleteMapper implements JSON2ObjectMapper<PostMeRemoveComplete, JSONObject> {
        private static final PostMeRemoveCompleteMapper sInstance = new PostMeRemoveCompleteMapper();

        private PostMeRemoveCompleteMapper() {
        }

        public static PostMeRemoveCompleteMapper getInstance() {
            return sInstance;
        }

        public PostMeRemoveComplete map(JSONObject json) {
            if (json == null) {
                return new PostMeRemoveComplete(false);
            }
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new PostMeRemoveComplete("1".equals(json.optString(SNSInterface.SUCCESS, "0")));
        }
    }

    public static final class PostMeRemoveConfirm {
        public final String removeToken;

        public PostMeRemoveConfirm(String removeToken) {
            this.removeToken = removeToken;
        }
    }

    public static final class PostMeRemoveConfirmMapper implements JSON2ObjectMapper<PostMeRemoveConfirm, JSONObject> {
        private static final PostMeRemoveConfirmMapper sInstance = new PostMeRemoveConfirmMapper();

        private PostMeRemoveConfirmMapper() {
        }

        public static PostMeRemoveConfirmMapper getInstance() {
            return sInstance;
        }

        public PostMeRemoveConfirm map(JSONObject json) {
            if (json == null) {
                return new PostMeRemoveConfirm(null);
            }
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new PostMeRemoveConfirm(json.optString("remove_token", null));
        }
    }

    public static final class PostMeSettingsAutoAddContacts {
        public final boolean success;

        public PostMeSettingsAutoAddContacts(boolean success) {
            this.success = success;
        }
    }

    public static final class PostMeSettingsAutoAddContactsMapper implements JSON2ObjectMapper<PostMeSettingsAutoAddContacts, JSONObject> {
        private static final PostMeSettingsAutoAddContactsMapper sInstance = new PostMeSettingsAutoAddContactsMapper();

        private PostMeSettingsAutoAddContactsMapper() {
        }

        public static PostMeSettingsAutoAddContactsMapper getInstance() {
            return sInstance;
        }

        public PostMeSettingsAutoAddContacts map(JSONObject json) {
            if (json == null) {
                return new PostMeSettingsAutoAddContacts(false);
            }
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new PostMeSettingsAutoAddContacts("1".equals(json.optString(SNSInterface.SUCCESS, "0")));
        }
    }

    public static final class PostMeSettingsPush {
        public final boolean success;

        public PostMeSettingsPush(boolean success) {
            this.success = success;
        }
    }

    public static final class PostMeSettingsPushMapper implements JSON2ObjectMapper<PostMeSettingsPush, JSONObject> {
        private static final PostMeSettingsPushMapper sInstance = new PostMeSettingsPushMapper();

        private PostMeSettingsPushMapper() {
        }

        public static PostMeSettingsPushMapper getInstance() {
            return sInstance;
        }

        public PostMeSettingsPush map(JSONObject json) {
            if (json == null) {
                return new PostMeSettingsPush(false);
            }
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new PostMeSettingsPush("1".equals(json.optString(SNSInterface.SUCCESS, "0")));
        }
    }

    public static final class PostMeSettingsSearchable {
        public final boolean success;

        public PostMeSettingsSearchable(boolean success) {
            this.success = success;
        }
    }

    public static final class PostMeSettingsSearchableMapper implements JSON2ObjectMapper<PostMeSettingsSearchable, JSONObject> {
        private static final PostMeSettingsSearchableMapper sInstance = new PostMeSettingsSearchableMapper();

        private PostMeSettingsSearchableMapper() {
        }

        public static PostMeSettingsSearchableMapper getInstance() {
            return sInstance;
        }

        public PostMeSettingsSearchable map(JSONObject json) {
            if (json == null) {
                return new PostMeSettingsSearchable(false);
            }
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new PostMeSettingsSearchable("1".equals(json.optString(SNSInterface.SUCCESS, "0")));
        }
    }

    public static final class PostNotifyContacts {
        public final boolean success;

        public PostNotifyContacts(Boolean success) {
            this.success = success.booleanValue();
        }
    }

    public static final class PostNotifyContactsMapper implements JSON2ObjectMapper<PostNotifyContacts, JSONObject> {
        private static final PostNotifyContactsMapper sInstance = new PostNotifyContactsMapper();

        private PostNotifyContactsMapper() {
        }

        public static PostNotifyContactsMapper getInstance() {
            return sInstance;
        }

        public PostNotifyContacts map(JSONObject json) {
            if (json == null) {
                return new PostNotifyContacts(Boolean.valueOf(false));
            }
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new PostNotifyContacts(Boolean.valueOf("1".equals(json.optString(SNSInterface.SUCCESS, "0"))));
        }
    }

    public static final class PostOauthAccessToken {
        public final String accessToken;

        public PostOauthAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }
    }

    public static final class PostOauthAccessTokenMapper implements JSON2ObjectMapper<PostOauthAccessToken, JSONObject> {
        private static final PostOauthAccessTokenMapper sInstance = new PostOauthAccessTokenMapper();

        private PostOauthAccessTokenMapper() {
        }

        public static final PostOauthAccessTokenMapper getInstance() {
            return sInstance;
        }

        public PostOauthAccessToken map(JSONObject k) {
            return new PostOauthAccessToken(k.optString("access_token"));
        }
    }

    public static final class PostPublicGroups {
        public final String name;
        public final String uid;

        public PostPublicGroups(String name, String uid) {
            this.name = name;
            this.uid = uid;
        }
    }

    public static final class PostPublicGroupsMapper implements JSON2ObjectMapper<PostPublicGroups, JSONObject> {
        private static final PostPublicGroupsMapper sInstance = new PostPublicGroupsMapper();

        private PostPublicGroupsMapper() {
        }

        public static PostPublicGroupsMapper getInstance() {
            return sInstance;
        }

        public PostPublicGroups map(JSONObject json) {
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new PostPublicGroups(JSONUtil.getString(json, "name", null), JSONUtil.getString(json, "uid", null));
        }
    }

    public static final class PostRankingScore {
        public final boolean success;

        public PostRankingScore(boolean success) {
            this.success = success;
        }
    }

    public static final class PostRankingScoreMapper implements JSON2ObjectMapper<PostRankingScore, JSONObject> {
        private static final PostRankingScoreMapper sInstance = new PostRankingScoreMapper();

        private PostRankingScoreMapper() {
        }

        public static PostRankingScoreMapper getInstance() {
            return sInstance;
        }

        public PostRankingScore map(JSONObject json) {
            return new PostRankingScore(TextUtils.equals("1", JSONUtil.getString(json, SNSInterface.SUCCESS, "0")));
        }
    }

    public static final class PostResurrectSignin {
        public final GroupDetailValue groupDetail;
        public final UserValue user;

        public PostResurrectSignin(UserValue user, GroupDetailValue groupDetail) {
            this.user = user;
            this.groupDetail = groupDetail;
        }
    }

    public static final class PostResurrectSigninMapper implements JSON2ObjectMapper<PostResurrectSignin, JSONObject> {
        private static final PostResurrectSigninMapper sInstance = new PostResurrectSigninMapper();

        private PostResurrectSigninMapper() {
        }

        public static PostResurrectSigninMapper getInstance() {
            return sInstance;
        }

        public PostResurrectSignin map(JSONObject json) {
            UserValue user = new UserValue(json);
            JSONObject g = json.optJSONObject("my_group");
            GroupDetailValue groupDetail = null;
            if (g != null) {
                groupDetail = new GroupDetailValue(g);
            }
            return new PostResurrectSignin(user, groupDetail);
        }
    }

    public static final class PostResurrectStart {
        public final boolean success;

        public PostResurrectStart(boolean success) {
            this.success = success;
        }
    }

    public static final class PostResurrectStartMapper implements JSON2ObjectMapper<PostResurrectStart, JSONObject> {
        private static final PostResurrectStartMapper sInstance = new PostResurrectStartMapper();

        private PostResurrectStartMapper() {
        }

        public static final PostResurrectStartMapper getInstance() {
            return sInstance;
        }

        public PostResurrectStart map(JSONObject k) {
            return new PostResurrectStart(TextUtils.equals("1", JSONUtil.getString(k, SNSInterface.SUCCESS, "0")));
        }
    }

    public static final class PostSignup {
        public final String error;
        public final GroupDetailValue groupDetail;
        public final UserValue user;

        public PostSignup(String error, UserValue user, GroupDetailValue groupDetail) {
            this.error = error;
            this.user = user;
            this.groupDetail = groupDetail;
        }
    }

    public static final class PostSignupFree {
        public final UserValue user;

        public PostSignupFree(UserValue user) {
            this.user = user;
        }
    }

    public static final class PostSignupFreeMapper implements JSON2ObjectMapper<PostSignupFree, JSONObject> {
        private static final PostSignupFreeMapper sInstance = new PostSignupFreeMapper();

        private PostSignupFreeMapper() {
        }

        public static final PostSignupFreeMapper getInstance() {
            return sInstance;
        }

        public PostSignupFree map(JSONObject k) {
            if (k.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new PostSignupFree(new UserValue(k));
        }
    }

    public static final class PostSignupMapper implements JSON2ObjectMapper<PostSignup, JSONObject> {
        private static final PostSignupMapper sInstance = new PostSignupMapper();

        private PostSignupMapper() {
        }

        public static PostSignupMapper getInstance() {
            return sInstance;
        }

        public PostSignup map(JSONObject json) {
            String error = JSONUtil.getString(json, GCMConstants.EXTRA_ERROR, null);
            UserValue user = new UserValue(json);
            JSONObject g = json.optJSONObject("my_group");
            GroupDetailValue groupDetail = null;
            if (g != null) {
                groupDetail = new GroupDetailValue(g);
            }
            return new PostSignup(error, user, groupDetail);
        }
    }

    public static final class PostStampUnlock {
        public final boolean success;

        public PostStampUnlock(boolean success) {
            this.success = success;
        }
    }

    public static final class PostStampUnlockMapper implements JSON2ObjectMapper<PostStampUnlock, JSONObject> {
        private static final PostStampUnlockMapper sInstance = new PostStampUnlockMapper();

        private PostStampUnlockMapper() {
        }

        public static PostStampUnlockMapper getInstance() {
            return sInstance;
        }

        public PostStampUnlock map(JSONObject k) {
            if (!k.has(GCMConstants.EXTRA_ERROR) && k.has(SNSInterface.SUCCESS)) {
                return new PostStampUnlock("1".equals(k.optString(SNSInterface.SUCCESS, null)));
            }
            return null;
        }
    }

    public static final class PostStoreRestore {
        public final boolean success;

        public PostStoreRestore(boolean success) {
            this.success = success;
        }
    }

    public static final class PostStoreRestoreMapper implements JSON2ObjectMapper<PostStoreRestore, JSONObject> {
        private static final PostStoreRestoreMapper sInstance = new PostStoreRestoreMapper();

        private PostStoreRestoreMapper() {
        }

        public static PostStoreRestoreMapper getInstance() {
            return sInstance;
        }

        public PostStoreRestore map(JSONObject json) {
            if (json == null) {
                return new PostStoreRestore(false);
            }
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new PostStoreRestore("1".equals(json.optString(SNSInterface.SUCCESS, "0")));
        }
    }

    public static final class PostStoreStampUnlock {
        public final boolean success;

        public PostStoreStampUnlock(boolean success) {
            this.success = success;
        }
    }

    public static final class PostStoreStampUnlockMapper implements JSON2ObjectMapper<PostStoreStampUnlock, JSONObject> {
        private static final PostStoreStampUnlockMapper sInstance = new PostStoreStampUnlockMapper();

        private PostStoreStampUnlockMapper() {
        }

        public static PostStoreStampUnlockMapper getInstance() {
            return sInstance;
        }

        public PostStoreStampUnlock map(JSONObject json) {
            if (json == null) {
                return new PostStoreStampUnlock(false);
            }
            if (json.has(GCMConstants.EXTRA_ERROR)) {
                return null;
            }
            return new PostStoreStampUnlock("1".equals(json.optString(SNSInterface.SUCCESS, "0")));
        }
    }

    public static final class PostUserExIdDecrypt {
        public final String userExId;

        public PostUserExIdDecrypt(String userExId) {
            this.userExId = userExId;
        }
    }

    public static final class PostUserExIdDecryptMapper implements JSON2ObjectMapper<PostUserExIdDecrypt, JSONObject> {
        private static final PostUserExIdDecryptMapper sInstance = new PostUserExIdDecryptMapper();

        private PostUserExIdDecryptMapper() {
        }

        public static PostUserExIdDecryptMapper getInstance() {
            return sInstance;
        }

        public PostUserExIdDecrypt map(JSONObject k) {
            String userExId;
            if (k == null) {
                userExId = null;
            } else {
                userExId = k.optString("user_ex_id");
            }
            return new PostUserExIdDecrypt(userExId);
        }
    }

    public static final class PostUserExIdEncrypt {
        public final String encryptedExId;
        public final String iv;

        public PostUserExIdEncrypt(String encryptedExId, String iv) {
            this.encryptedExId = encryptedExId;
            this.iv = iv;
        }
    }

    public static final class PostUserExIdEncryptMapper implements JSON2ObjectMapper<PostUserExIdEncrypt, JSONObject> {
        private static final PostUserExIdEncryptMapper sInstance = new PostUserExIdEncryptMapper();

        private PostUserExIdEncryptMapper() {
        }

        public static PostUserExIdEncryptMapper getInstance() {
            return sInstance;
        }

        public PostUserExIdEncrypt map(JSONObject k) {
            String encryptedExId;
            String iv;
            if (k == null) {
                encryptedExId = null;
                iv = null;
            } else {
                encryptedExId = k.optString("encrypted_ex_id");
                iv = k.optString("iv");
            }
            return new PostUserExIdEncrypt(encryptedExId, iv);
        }
    }
}
