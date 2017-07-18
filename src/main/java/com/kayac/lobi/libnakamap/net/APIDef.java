package com.kayac.lobi.libnakamap.net;

public class APIDef {

    public interface APICommonRequestKey {
        public static final String LANG = "lang";
        public static final String PLATFORM = "platform";
        public static final String TOKEN = "token";
    }

    public static final class GetAdNewAd {
        public static final String PATH = "/1/ad/new_ad";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String LAST_ACCESSED_EPOCH = "last_accessed_epoch";
        }
    }

    public static final class GetAdRecommends {
        public static final String PATH = "/1/ad/recommends";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String INLINE = "inline";
            public static final String OPTION_PAGE = "page";
        }
    }

    public static final class GetApp {
        public static final String PATH = "/1/app/%s";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String MORE_FIELD_CLIENT_ID = "client_id";
            public static final String OPTION_MORE_FIELDS = "more_fields";
            public static final String UID = "uid";
        }
    }

    public static final class GetAppData {
        public static final String PATH = "/1/appdata/";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String OPTION_FIELDS = "fields";
        }
    }

    public static final class GetDefaultUser {
        public static final String PATH = "/1/user/%s/default_user";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String UID = "uid";
        }
    }

    public static final class GetDefaultUserContacts {
        public static final String PATH = "/1/default/user/%s/contacts";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String LOGIN_FILTER = "login_filter";
            public static final String UID = "uid";
        }
    }

    public static final class GetDefaultUserFollowers {
        public static final String PATH = "/1/default/user/%s/followers";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String LOGIN_FILTER = "login_filter";
            public static final String UID = "uid";
        }
    }

    public static final class GetGroup {
        public static final String PATH = "/1/group/%s";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String OPTION_COUNT = "count";
            public static final String OPTION_INSTALL_ID = "install_id";
            public static final String OPTION_MEMBERS_COUNT = "members_count";
            public static final String OPTION_MEMBERS_CURSOR = "members_cursor";
            public static final String OPTION_NEWER_THAN = "newer_than";
            public static final String OPTION_OLDER_THAN = "older_than";
            public static final String UID = "uid";
        }
    }

    public static final class GetGroupChatReplies {
        public static final String PATH = "/1/group/%s/chats/replies";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String TO = "to";
            public static final String UID = "uid";
        }
    }

    public static final class GetGroupChatV2 {
        public static final String PATH = "/2/group/%s/chats";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String OPTION_CHATS_COUNT = "count";
            public static final String OPTION_NEWER_THAN = "newer_than";
            public static final String OPTION_OLDER_THAN = "older_than";
            public static final String UID = "uid";
        }
    }

    public static final class GetGroupChatWithExIdV2 {
        public static final String PATH = "/2/group/chats";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String GROUP_EX_ID = "group_ex_id";
            public static final String OPTION_CHATS_COUNT = "count";
            public static final String OPTION_NEWER_THAN = "newer_than";
            public static final String OPTION_OLDER_THAN = "older_than";
        }
    }

    public static final class GetGroupV2 {
        public static final String PATH = "/2/group/%s";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String OPTION_COUNT = "count";
            public static final String OPTION_INSTALL_ID = "install_id";
            public static final String OPTION_MEMBERS_COUNT = "members_count";
            public static final String OPTION_MEMBERS_CURSOR = "members_cursor";
            public static final String OPTION_NEWER_THAN = "newer_than";
            public static final String OPTION_OLDER_THAN = "older_than";
            public static final String UID = "uid";
        }
    }

    public static final class GetGroupWithExIdV2 {
        public static final String PATH = "/2/group";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String GROUP_EX_ID = "group_ex_id";
            public static final String OPTION_COUNT = "count";
            public static final String OPTION_MEMBERS_COUNT = "members_count";
            public static final String OPTION_MEMBERS_CURSOR = "members_cursor";
            public static final String OPTION_NEWER_THAN = "newer_than";
            public static final String OPTION_OLDER_THAN = "older_than";
        }
    }

    public static final class GetGroupsV2 {
        public static final String PATH = "/2/groups";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String OPTION_COUNT = "count";
            public static final String OPTION_PAGE = "page";
        }
    }

    public static final class GetGroupsV3 {
        public static final String PATH = "/3/groups";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String OPTION_COUNT = "count";
            public static final String OPTION_PAGE = "page";
        }
    }

    public static final class GetMe {
        public static final String PATH = "/1/me";

        public static final class RequestKey implements APICommonRequestKey {
        }
    }

    public static final class GetMeBinded {
        public static final String PATH = "/1/me/binded";

        public static final class RequestKey implements APICommonRequestKey {
        }
    }

    public static final class GetMeBlockingUsers {
        public static final String PATH = "/1/me/blocking_users";

        public static final class RequestKey implements APICommonRequestKey {
        }
    }

    public static final class GetMeChatFriends {
        public static final String PATH = "/1/me/chat_friends";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String USER_EX_IDS = "user_ex_ids";
        }
    }

    public static final class GetMeContacts {
        public static final String PATH = "/1/me/contacts";

        public static final class RequestKey implements APICommonRequestKey {
        }
    }

    public static final class GetMeContactsRecommended {
        public static final String PATH = "/1/me/contacts/recommended";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String OPTION_COUNT = "count";
        }
    }

    public static final class GetMeProfileVisibleGroups {
        public static final String PATH = "/1/me/profile/visible_groups";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String OPTION_COUNT = "count";
            public static final String OPTION_CURSOR = "cursor";
        }
    }

    public static final class GetMeSettings {
        public static final String PATH = "/1/me/settings";

        public static final class RequestKey implements APICommonRequestKey {
        }
    }

    public static final class GetMeSettingsV2 {
        public static final String PATH = "/2/me/settings";

        public static final class RequestKey implements APICommonRequestKey {
        }
    }

    public static final class GetNoahBanner {
        public static final String PATH = "/1/noah/banner";

        public static final class RequestKey implements APICommonRequestKey {
        }
    }

    public static final class GetNonce {
        public static final String PATH = "/1/nonce/";

        public static final class RequestKey implements APICommonRequestKey {
        }
    }

    public static final class GetNotifications {
        public static final String PATH = "/3/notifications";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String OPTION_COUNT = "count";
            public static final String OPTION_OLDER_THAN = "older_than";
            public static final String OPTION_SERVICE = "service";
            public static final String SERVICE_CHAT = "chat";
            public static final String SERVICE_REC = "rec";
        }
    }

    public static final class GetPing {
        public static final String PATH = "/1/ping/";

        public static final class RequestKey implements APICommonRequestKey {
        }
    }

    public static final class GetPublicGroups {
        public static final String PATH = "/1/public_groups";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String OPTION_COUNT = "count";
            public static final String OPTION_PAGE = "page";
        }
    }

    public static final class GetPublicGroupsSearch {
        public static final String PATH = "/1/public_groups/search";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String OPTION_CATEGORY = "category";
            public static final String OPTION_COUNT = "count";
            public static final String OPTION_CURSOR = "cursor";
            public static final String Q = "q";
        }
    }

    public static final class GetPublicGroupsTree {
        public static final String PATH = "/1/public_groups/tree";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String OPTION_CATEGORY = "category";
            public static final String OPTION_COUNT = "count";
            public static final String OPTION_PAGE = "page";
        }
    }

    public static final class GetRanking {
        public static final String ORIGIN_SELF = "self";
        public static final String ORIGIN_TOP = "top";
        public static final String PATH = "/1/ranking/%s/";
        public static final String TYPE_ALL = "all";
        public static final String TYPE_LAST_WEEK = "last_week";
        public static final String TYPE_TODAY = "today";
        public static final String TYPE_WEEK = "week";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String OPTION_CURSOR = "cursor";
            public static final String OPTION_LIMIT = "limit";
            public static final String OPTION_ORIGIN = "origin";
            public static final String OPTION_TYPE = "type";
            public static final String RANKING_ID = "ranking_id";
        }
    }

    public static final class GetRankings {
        public static final String PATH = "/1/rankings/";
        public static final String TYPE_ALL = "all";
        public static final String TYPE_LAST_WEEK = "last_week";
        public static final String TYPE_TODAY = "today";
        public static final String TYPE_WEEK = "week";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String OPTION_TYPE = "type";
            public static final String OPTION_UID = "uid";
        }
    }

    public static final class GetSdkReport {
        public static final String PATH = "/1/sdk/report";

        public static final class RequestKey {
            public static final String BUNDLE_ID = "bundle_id";
            public static final String CLIENT_ID = "client_id";
            public static final String LANG = "lang";
            public static final String NAME = "name";
            public static final String OS = "os";
        }
    }

    public static final class GetSdkStartup {
        public static final String PATH = "/1/sdk/startup";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String OPTION_FIELDS = "fields";
        }
    }

    public static final class GetSignupMessage {
        public static final String PATH = "/1/signup/message";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String TYPE = "type";
        }
    }

    public static final class GetSignupPromote {
        public static final String PATH = "/1/signup/promote";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String SEED = "seed";
            public static final String TYPE = "type";
        }
    }

    public static final class GetStampUnlocked {
        public static final String PATH = "/1/stamp/%s/unlocked";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String UID = "uid";
        }
    }

    public static final class GetStamps {
        public static final String PATH = "/1/stamps";

        public static final class RequestKey implements APICommonRequestKey {
        }
    }

    public static final class GetTerms {
        public static final String PATH = "/1/terms";

        public static final class RequestKey {
            public static final String CLIENT_ID = "client_id";
        }
    }

    public static final class GetUser {
        public static final String PATH = "/1/user/%s";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String OPTION_GROUP = "group";
            public static final String UID = "uid";
        }
    }

    public static final class GetUserV2 {
        public static final String PATH = "/2/user/%s/";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String OPTION_GROUP = "group";
            public static final String UID = "uid";
        }
    }

    public static final class GetUserV3 {
        public static final String PATH = "/3/user/%s/";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String OPTION_FIELDS = "fields";
            public static final String OPTION_GROUP = "group";
            public static final String SECRET_MODE = "secret_mode";
            public static final String UID = "uid";
        }
    }

    public static final class GetUserVisibleGroups {
        public static final String PATH = "/1/user/%s/visible_groups";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String OPTION_COUNT = "count";
            public static final String OPTION_CURSOR = "cursor";
            public static final String UID = "uid";
        }
    }

    public static final class GetUsers {
        public static final String PATH = "/1/users";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String TARGET_USER_EX_IDS = "target_user_ex_ids";
        }
    }

    public static final class GetUsersSearch {
        public static final String PATH = "/1/users/search";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String Q = "q";
        }
    }

    public static final class PostAccusations {
        public static final String PATH = "/1/accusations";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String OPTION_CHAT = "chat";
            public static final String OPTION_GROUP = "group";
            public static final String OPTION_USER = "user";
            public static final String REASON = "reason";
        }
    }

    public static final class PostAdTrackingClick {
        public static final String PATH = "/1/ad/tracking/click";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String AD_ID = "ad_id";
        }
    }

    public static final class PostAdTrackingConversion {
        public static final String PATH = "/1/ad/tracking/conversion";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String AD_ID = "ad_id";
            public static final String INSTALL_ID = "install_id";
        }
    }

    public static final class PostAdTrackingImpression {
        public static final String PATH = "/1/ad/tracking/imp";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String AD_IDS = "ad_ids";
        }
    }

    public static final class PostAppData {
        public static final String PATH = "/1/appdata/";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String DATA = "data";
            public static final String HASH = "hash";
            public static final String NONCE = "nonce";
            public static final String VERSION = "version";
        }
    }

    public static final class PostAppDataRemove {
        public static final String PATH = "/1/appdata/remove";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String OPTION_FIELDS = "fields";
            public static final String OPTION_HASH = "hash";
            public static final String OPTION_NONCE = "nonce";
            public static final String OPTION_VERSION = "version";
        }
    }

    public static final class PostBindStart {
        public static final String PATH = "/1/bind/start";

        public static final class RequestKey {
            public static final String CLIENT_ID = "client_id";
            public static final String TID = "tid";
            public static final String TOKEN = "token";
        }
    }

    public static final class PostDefaultMeContacts {
        public static final String PATH = "/1/default/me/contacts";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String USERS = "users";
        }
    }

    public static final class PostDefaultMeContactsRemove {
        public static final String PATH = "/1/default/me/contacts/remove";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String USER = "user";
        }
    }

    public static final class PostGroup {
        public static final String PATH = "/1/group/%s";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String DESCRIPTION = "description";
            public static final String NAME = "name";
            public static final String UID = "uid";
        }
    }

    public static final class PostGroupChat {
        public static final String PATH = "/1/group/%s/chats";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String OPTION_ASSETS = "assets";
            public static final String OPTION_IMAGE = "image";
            public static final String OPTION_IMAGE_TYPE = "image_type";
            public static final String OPTION_MESSAGE = "message";
            public static final String OPTION_REPLY_TO = "reply_to";
            public static final String OPTION_TYPE = "type";
            public static final String UID = "uid";
        }
    }

    public static final class PostGroupChatRemove {
        public static final String PATH = "/1/group/%s/chats/remove";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String ID = "id";
            public static final String UID = "uid";
        }
    }

    public static final class PostGroupExId {
        public static final String PATH = "/1/group";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String DESCRIPTION = "description";
            public static final String GROUP_EX_ID = "group_ex_id";
            public static final String NAME = "name";
        }
    }

    public static final class PostGroupIcon {
        public static final String PATH = "/1/group/%s/icon";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String ICON = "icon";
            public static final String UID = "uid";
        }
    }

    public static final class PostGroupJoinWithExId {
        public static final String PATH = "/1/group/join";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String GROUP_EX_ID = "group_ex_id";
            public static final String NAME = "name";
        }
    }

    public static final class PostGroupJoinWithExIdV2 {
        public static final String PATH = "/2/group/join";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String GROUP_EX_ID = "group_ex_id";
            public static final String NAME = "name";
        }
    }

    public static final class PostGroupJoinWithGroupUid {
        public static final String PATH = "/1/group/%s/join";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String OPTION_CHATS_COUNT = "count";
            public static final String OPTION_INSTALL_ID = "install_id";
            public static final String OPTION_LAT = "lat";
            public static final String OPTION_LNG = "lng";
            public static final String OPTION_MEMBERS_COUNT = "members_count";
            public static final String OPTION_MEMBERS_CURSOR = "members_cursor";
            public static final String OPTION_NEWER_THAN = "newer_than";
            public static final String OPTION_OLDER_THAN = "older_than";
            public static final String UID = "uid";
        }
    }

    public static final class PostGroupJoinWithGroupUidV2 {
        public static final String PATH = "/2/group/%s/join";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String FROM_CATEGORY_ID = "from_category_id";
            public static final String OPTION_CHATS_COUNT = "count";
            public static final String OPTION_INSTALL_ID = "install_id";
            public static final String OPTION_LAT = "lat";
            public static final String OPTION_LNG = "lng";
            public static final String OPTION_MEMBERS_COUNT = "members_count";
            public static final String OPTION_MEMBERS_CURSOR = "members_cursor";
            public static final String OPTION_NEWER_THAN = "newer_than";
            public static final String OPTION_OLDER_THAN = "older_than";
            public static final String UID = "uid";
        }
    }

    public static final class PostGroupKick {
        public static final String PATH = "/1/group/%s/kick";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String TARGET_USER = "target_user";
            public static final String UID = "uid";
        }
    }

    public static final class PostGroupKickExId {
        public static final String PATH = "/1/group/kick";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String GROUP_EX_ID = "group_ex_id";
            public static final String USER_EX_ID = "user_ex_id";
        }
    }

    public static final class PostGroupMembers {
        public static final String PATH = "/1/group/%s/members";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String UID = "uid";
            public static final String USERS = "users";
        }
    }

    public static final class PostGroupMembersExId {
        public static final String PATH = "/1/group/members";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String GROUP_EX_ID = "group_ex_id";
            public static final String USER_EX_IDS = "user_ex_ids";
        }
    }

    public static final class PostGroupPart {
        public static final String PATH = "/1/group/%s/part";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String UID = "uid";
        }
    }

    public static final class PostGroupPartExId {
        public static final String PATH = "/1/group/part";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String GROUP_EX_ID = "group_ex_id";
        }
    }

    public static final class PostGroupPrivacy {
        public static final String PATH = "/1/group/%s/privacy";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String ON = "on";
            public static final String OPTION_MEMBERS_COUNT = "members_count";
            public static final String OPTION_MEMBERS_CURSOR = "members_cursor";
            public static final String UID = "uid";
        }
    }

    public static final class PostGroupPrivacyV2 {
        public static final String PATH = "/2/group/%s/privacy";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String ON = "on";
            public static final String OPTION_MEMBERS_COUNT = "members_count";
            public static final String OPTION_MEMBERS_CURSOR = "members_cursor";
            public static final String UID = "uid";
        }
    }

    public static final class PostGroupPush {
        public static final String PATH = "/1/group/%s/push";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String ON = "on";
            public static final String UID = "uid";
        }
    }

    public static final class PostGroupRemove {
        public static final String PATH = "/1/group/%s/remove";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String UID = "uid";
        }
    }

    public static final class PostGroupRemoveExId {
        public static final String PATH = "/1/group/remove";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String GROUP_EX_ID = "group_ex_id";
        }
    }

    public static final class PostGroupTransferExId {
        public static final String PATH = "/1/group/transfer";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String GROUP_EX_ID = "group_ex_id";
            public static final String USER_EX_ID = "user_ex_id";
        }
    }

    public static final class PostGroupTransferExIdV2 {
        public static final String PATH = "/2/group/transfer";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String GROUP_EX_ID = "group_ex_id";
            public static final String USER_EX_ID = "user_ex_id";
        }
    }

    public static final class PostGroupVisibility {
        public static final String LEVEL_FRIENDS = "1";
        public static final String LEVEL_PRIVATE = "0";
        public static final String LEVEL_PUBLIC = "2";
        public static final String PATH = "/1/group/%s/visibility";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String LEVEL = "level";
            public static final String UID = "uid";
        }
    }

    public static final class PostGroupWallpaper {
        public static final String PATH = "/1/group/%s/wallpaper";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String UID = "uid";
            public static final String WALLPAPER = "wallpaper";
        }
    }

    public static final class PostGroupWallpaperRemove {
        public static final String PATH = "/1/group/%s/wallpaper/remove";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String UID = "uid";
        }
    }

    public static final class PostGroups1on1s {
        public static final String PATH = "/1/groups/1on1s";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String USER = "user";
            public static final String USER_EX_ID = "user_ex_id";
        }
    }

    public static final class PostGroups {
        public static final String PATH = "/1/groups";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String DESCRIPTION = "description";
            public static final String NAME = "name";
            public static final String OPTION_CATEGORY = "category";
            public static final String OPTION_GROUP_EX_ID = "group_ex_id";
            public static final String OPTION_MEMBER_USER_EX_IDS = "member_user_ex_ids";
            public static final String OPTION_PUBLIC = "public";
        }
    }

    public static final class PostInvitations {
        public static final String PATH = "/1/invitations";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String OPTION_GROUP = "group";
        }
    }

    public static final class PostInvitationsRecipients {
        public static final String PATH = "/1/invitation/%s/recipients";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String OPTION_GROUP = "group";
            public static final String UID = "uid";
        }
    }

    public static final class PostMeBlockingUsers {
        public static final String PATH = "/1/me/blocking_users";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String USERS = "users";
        }
    }

    public static final class PostMeBlockingUsersRemove {
        public static final String PATH = "/1/me/blocking_users/remove";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String USERS = "users";
        }
    }

    public static final class PostMeContacts {
        public static final String PATH = "/1/me/contacts";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String USERS = "users";
        }
    }

    public static final class PostMeContactsExId {
        public static final String PATH = "/1/me/contacts";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String USER_EX_IDS = "user_ex_ids";
        }
    }

    public static final class PostMeContactsRemove {
        public static final String PATH = "/1/me/contacts/remove";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String USER = "user";
        }
    }

    public static final class PostMeContactsRemoveExId {
        public static final String PATH = "/1/me/contacts/remove";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String USER_EX_ID = "user_ex_id";
        }
    }

    public static final class PostMeCover {
        public static final String PATH = "/1/me/cover";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String COVER = "cover";
            public static final String OPTION_FORCE = "force";
        }
    }

    public static final class PostMeExId {
        public static final String PATH = "/1/me/ex_id";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String ENCRYPTED_EX_ID = "encrypted_ex_id";
            public static final String IV = "iv";
        }
    }

    public static final class PostMeExternalContacts {
        public static final String PATH = "/2/me/external_contacts";

        public static final class Email {

            public static final class RequestKey {
                public static final String EMAILS = "emails";
            }
        }

        public static final class Facebook {

            public static final class RequestKey {
                public static final String IDS = "ids";
            }
        }

        public static final class Mixi {

            public static final class RequestKey {
                public static final String IDS = "ids";
            }
        }

        public static final class RequestKey implements APICommonRequestKey {
            public static final String SERVICE = "service";
        }

        public static final class Twitter {

            public static final class RequestKey {
                public static final String SCREEN_NAMES = "screen_names";
            }
        }
    }

    public static final class PostMeIcon {
        public static final String PATH = "/1/me/icon";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String ICON = "icon";
            public static final String OPTION_FORCE = "force";
        }
    }

    public static final class PostMeProfile {
        public static final String PATH = "/1/me/profile";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String OPTION_DESCRIPTION = "description";
            public static final String OPTION_NAME = "name";
        }
    }

    public static final class PostMeRemoveConfirm {
        public static final String PATH = "/1/me/remove/confirm";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String WHY = "why";
        }
    }

    public static final class PostMeSettingsSearchable {
        public static final String PATH = "/1/me/settings/searchable";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String ON = "on";
        }
    }

    public static final class PostNotifyContacts {
        public static final String EVENT_JOINED_GROUP = "joined_group";
        public static final String EVENT_STARTED_APP = "started_app";
        public static final String PATH = "/1/notify_contacts";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String EVENT = "event";
            public static final String OPTION_APP = "app";
            public static final String OPTION_GROUP = "group";
        }
    }

    public static final class PostOauthAccessToken {
        public static final String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";
        public static final String PATH = "/oauth/access_token";

        public static final class RequestKey {
            public static final String GRANT_TYPE = "grant_type";
            public static final String REFRESH_TOKEN = "refresh_token";
        }
    }

    public static final class PostPublicGroups {
        public static final String PATH = "/1/public_groups";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String CATEGORY = "category";
            public static final String NAME = "name";
            public static final String OPTION_DESCRIPTION = "description";
        }
    }

    public static final class PostRankingScore {
        public static final String PATH = "/1/ranking/%s/score";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String DATE = "date";
            public static final String HASH = "hash";
            public static final String NONCE = "nonce";
            public static final String RANKING_ID = "ranking_id";
            public static final String SCORE = "score";
            public static final String VERSION = "version";
        }
    }

    public static final class PostSignupFree {
        public static final String ANDROID = "android";
        public static final String PATH = "/2/signup/free";

        public static final class RequestKey {
            public static final String CLIENT_ID = "client_id";
            public static final String INSTALL_ID = "install_id";
            public static final String NAME = "name";
            public static final String OPTION_ENCRYPTED_EX_ID = "encrypted_ex_id";
            public static final String OPTION_IV = "iv";
            public static final String PLATFORM = "platform";
            public static final String SIGNATURE = "signature";
            public static final String VERSION = "version";
        }
    }

    public static final class PostStampUnlock {
        public static final String PATH = "/1/stamp/%s/unlock";

        public static final class RequestKey implements APICommonRequestKey {
            public static final String UID = "uid";
        }
    }

    public static final class PostUserExIdDecrypt {
        public static final String PATH = "/1/tool/user_ex_id/decrypt";

        public static final class RequestKey {
            public static final String CLIENT_SECRET = "client_secret";
            public static final String ENCRYPTED_EX_ID = "encrypted_ex_id";
            public static final String IV = "iv";
        }
    }

    public static final class PostUserExIdEncrypt {
        public static final String PATH = "/1/tool/user_ex_id/encrypt";

        public static final class RequestKey {
            public static final String CLIENT_SECRET = "client_secret";
            public static final String USER_EX_ID = "user_ex_id";
        }
    }
}
