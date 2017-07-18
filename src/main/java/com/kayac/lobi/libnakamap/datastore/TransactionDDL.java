package com.kayac.lobi.libnakamap.datastore;

public class TransactionDDL implements CommonDDL {

    public static final class AdWaitingApp {
        public static final String CREATE_SQL = "CREATE TABLE ad_waiting_app_table (c_ad_id TEXT ,c_package TEXT ,c_client_id TEXT ,c_date INTEGER ,c_count_conversion INTEGER , PRIMARY KEY  (c_ad_id) ON CONFLICT REPLACE );";
        public static final String C_AD_ID = "c_ad_id";
        public static final String C_CLIENT_ID = "c_client_id";
        public static final String C_COUNT_CONVERSION = "c_count_conversion";
        public static final String C_DATE = "c_date";
        public static final String C_PACKAGE = "c_package";
        public static final String TABLE = "ad_waiting_app_table";
    }

    public static final class Asset {
        public static final String CREATE_SQL = "CREATE TABLE asset_table (c_id TEXT  PRIMARY KEY  ,c_upload_uid INTEGER ,c_type TEXT ,c_url TEXT );";
        public static final String C_ID = "c_id";
        public static final String C_TYPE = "c_type";
        public static final String C_UPLOAD_UID = "c_upload_uid";
        public static final String C_URL = "c_url";
        public static final String TABLE = "asset_table";
    }

    public static final class Category {
        public static final String CREATE_SQL = "CREATE TABLE category_table (c_type TEXT , c_title TEXT , c_user_uid TEXT , c_group_uid TEXT , PRIMARY KEY  (c_type,c_user_uid,c_group_uid));";
        public static final String C_GROUP_UID = "c_group_uid";
        public static final String C_TITLE = "c_title";
        public static final String C_TYPE = "c_type";
        public static final String C_USER_UID = "c_user_uid";
        public static final String TABLE = "category_table";
    }

    public static final class Chat {
        public static final String CREATE_SQL = "CREATE TABLE chat_table (c_id TEXT  PRIMARY KEY  ,c_group_uid TEXT ,c_type TEXT ,c_message TEXT ,c_created_date INTEGER ,c_image TEXT ,c_reply_to TEXT ,c_user_uid TEXT ,c_image_type TEXT ,c_image_width INTEGER ,c_image_height INTEGER ,c_stamp_uid TEXT ,c_on_store TEXT ,c_replies_count INTEGER ,c_assets TEXT ,c_likes_count INTEGER ,c_boos_count INTEGER ,c_liked INTEGER ,c_booed INTEGER );";
        public static final String C_ASSETS = "c_assets";
        public static final String C_BOOED = "c_booed";
        public static final String C_BOOS_COUNT = "c_boos_count";
        public static final String C_CREATED_DATE = "c_created_date";
        public static final String C_GROUP_UID = "c_group_uid";
        public static final String C_ID = "c_id";
        public static final String C_IMAGE = "c_image";
        public static final String C_IMAGE_HEIGHT = "c_image_height";
        public static final String C_IMAGE_TYPE = "c_image_type";
        public static final String C_IMAGE_WIDTH = "c_image_width";
        public static final String C_LIKED = "c_liked";
        public static final String C_LIKES_COUNT = "c_likes_count";
        public static final String C_MESSAGE = "c_message";
        public static final String C_ON_STORE = "c_on_store";
        public static final String C_REPLIES_COUNT = "c_replies_count";
        public static final String C_REPLY_TO = "c_reply_to";
        public static final String C_STAMP_UID = "c_stamp_uid";
        public static final String C_TYPE = "c_type";
        public static final String C_USER_UID = "c_user_uid";
        public static final String TABLE = "chat_table";
    }

    public static final class ChatRefer {
        public static final String CREATE_SQL = "CREATE TABLE chat_refer_table (c_chat_id TEXT , c_ref_id TEXT , c_type TEXT , c_title TEXT , c_image TEXT , c_action_title TEXT , c_link TEXT , PRIMARY KEY  (c_chat_id,c_ref_id));";
        public static final String C_ACTION_TITLE = "c_action_title";
        public static final String C_CHAT_ID = "c_chat_id";
        public static final String C_IMAGE = "c_image";
        public static final String C_LINK = "c_link";
        public static final String C_REF_ID = "c_ref_id";
        public static final String C_TITLE = "c_title";
        public static final String C_TYPE = "c_type";
        public static final String TABLE = "chat_refer_table";
    }

    public static final class Download {
        public static final String CREATE_SQL = "CREATE TABLE download_table (c_uid INTEGER  PRIMARY KEY   AUTOINCREMENT , c_total INTEGER );";
        public static final String C_TOTAL = "c_total";
        public static final String C_UID = "c_uid";
        public static final String TABLE = "download_table";
    }

    public static final class DownloadItem {
        public static final String CREATE_SQL = "CREATE TABLE download_item_table (c_asset_uid TEXT , c_download_uid INTEGER , c_type TEXT , c_url TEXT , c_complete INTEGER , PRIMARY KEY  (c_asset_uid,c_download_uid));";
        public static final String C_ASSET_UID = "c_asset_uid";
        public static final String C_COMPLETE = "c_complete";
        public static final String C_DOWNLOAD_UID = "c_download_uid";
        public static final String C_TYPE = "c_type";
        public static final String C_URL = "c_url";
        public static final String TABLE = "download_item_table";
    }

    public static final class FileCache {
        public static final String CREATE_SQL = "CREATE TABLE file_cache_table (c_path TEXT  PRIMARY KEY  ,c_type TEXT ,c_file_size INTEGER ,c_created_at INTEGER );";
        public static final String C_CREATED_AT = "c_created_at";
        public static final String C_FILE_SIZE = "c_file_size";
        public static final String C_PATH = "c_path";
        public static final String C_TYPE = "c_type";
        public static final String TABLE = "file_cache_table";
    }

    public static final class Group {
        public static final String CREATE_SQL = "CREATE TABLE group_table (c_uid TEXT , c_name TEXT , c_description TEXT , c_created_date INTEGER , c_icon TEXT , c_stream_host TEXT , c_push_enabled INTEGER , c_owner TEXT , c_is_online INTEGER , c_is_public INTEGER , c_is_official INTEGER , c_is_authorized INTEGER , c_type TEXT , c_members_count INTEGER , c_user_uid TEXT , c_wallpaper TEXT , c_is_notice INTEGER , c_ex_id TEXT , PRIMARY KEY  (c_uid,c_user_uid));";
        public static final String C_CREATED_DATE = "c_created_date";
        public static final String C_DESCRIPTION = "c_description";
        public static final String C_EX_ID = "c_ex_id";
        public static final String C_ICON = "c_icon";
        public static final String C_IS_AUTHORIZED = "c_is_authorized";
        public static final String C_IS_NOTICE = "c_is_notice";
        public static final String C_IS_OFFICIAL = "c_is_official";
        public static final String C_IS_ONLINE = "c_is_online";
        public static final String C_IS_PUBLIC = "c_is_public";
        public static final String C_MEMBERS_COUNT = "c_members_count";
        public static final String C_NAME = "c_name";
        public static final String C_OWNER = "c_owner";
        public static final String C_PUSH_ENABLED = "c_push_enabled";
        public static final String C_STREAM_HOST = "c_stream_host";
        public static final String C_TYPE = "c_type";
        public static final String C_UID = "c_uid";
        public static final String C_USER_UID = "c_user_uid";
        public static final String C_WALLPAPER = "c_wallpaper";
        public static final String TABLE = "group_table";
    }

    public static final class GroupDetail {
        public static final String CREATE_SQL = "CREATE TABLE group_detail_table (c_uid TEXT , c_name TEXT , c_description TEXT , c_created_date INTEGER , c_icon TEXT , c_stream_host TEXT , c_total_users INTEGER , c_online_users INTEGER , c_is_online INTEGER , c_is_public INTEGER , c_is_official INTEGER , c_is_authorized INTEGER , c_type TEXT , c_last_chat_at INTEGER , c_push_enabled INTEGER , c_user_uid TEXT  NOT NULL , c_is_notice INTEGER , c_ex_id TEXT , PRIMARY KEY  (c_uid,c_user_uid));";
        public static final String C_CREATED_DATE = "c_created_date";
        public static final String C_DESCRIPTION = "c_description";
        public static final String C_EX_ID = "c_ex_id";
        public static final String C_ICON = "c_icon";
        public static final String C_IS_AUTHORIZED = "c_is_authorized";
        public static final String C_IS_NOTICE = "c_is_notice";
        public static final String C_IS_OFFICIAL = "c_is_official";
        public static final String C_IS_ONLINE = "c_is_online";
        public static final String C_IS_PUBLIC = "c_is_public";
        public static final String C_LAST_CHAT_AT = "c_last_chat_at";
        public static final String C_NAME = "c_name";
        public static final String C_ONLINE_USERS = "c_online_users";
        public static final String C_PUSH_ENABLED = "c_push_enabled";
        public static final String C_STREAM_HOST = "c_stream_host";
        public static final String C_TOTAL_USERS = "c_total_users";
        public static final String C_TYPE = "c_type";
        public static final String C_UID = "c_uid";
        public static final String C_USER_UID = "c_user_uid";
        public static final String TABLE = "group_detail_table";

        public enum Order {
            NAME("c_name"),
            CREATED_DATE("c_created_date"),
            LAST_CHAT_AT(GroupDetail.C_LAST_CHAT_AT);
            
            private boolean caseInsensitive;
            private boolean desc;
            private final String order;

            private Order(String order) {
                this.desc = true;
                this.caseInsensitive = false;
                this.order = order;
            }

            public void setDesc(boolean b) {
                this.desc = b;
            }

            public void setCaseInsensitive(boolean b) {
                this.caseInsensitive = b;
            }

            public String getValue() {
                return this.order + (this.caseInsensitive ? " COLLATE NOCASE " : "") + (this.desc ? " DESC" : " ASC");
            }
        }
    }

    public static final class GroupMember {
        public static final String CREATE_SQL = "CREATE TABLE group_member_table (c_group_uid TEXT ,c_user_uid TEXT ,c_order INTEGER , PRIMARY KEY   (c_group_uid,c_user_uid));";
        public static final String C_GROUP_UID = "c_group_uid";
        public static final String C_ORDER = "c_order";
        public static final String C_USER_UID = "c_user_uid";
        public static final String TABLE = "group_member_table";
    }

    public static final class GroupPermission {
        public static final String ADD_MEMBERS = "add_members";
        public static final String CREATE_SQL = "CREATE TABLE group_permission_table (c_user_uid TEXT , c_group_uid TEXT , c_permission TEXT , PRIMARY KEY  (c_user_uid,c_group_uid,c_permission));";
        public static final String C_GROUP_UID = "c_group_uid";
        public static final String C_PERMISSION = "c_permission";
        public static final String C_USER_UID = "c_user_uid";
        public static final String INVITE = "invite";
        public static final String JOIN = "join";
        public static final String KICK = "kick";
        public static final String PART = "part";
        public static final String PEEK = "peek";
        public static final String REMOVE = "remove";
        public static final String SHOUT = "shout";
        public static final String TABLE = "group_permission_table";
        public static final String UPDATE_DESCRIPTION = "update_description";
        public static final String UPDATE_ICON = "update_icon";
        public static final String UPDATE_NAME = "update_name";
        public static final String UPDATE_WALLPAPER = "update_wallpaper";
    }

    public static final class KKey {
        public static final String GROUP_UPDATE_AT = "GROUP_UPDATE_AT";
        public static final String LAST_CHAT_AT = "LAST_CHAT_AT";
        public static final String LAST_CHAT_REFRESH_AT = "LAST_CHAT_REFRESH_AT";
        public static final String LAST_CHAT_REPLY_REFRESH_AT = "LAST_CHAT_REPLY_REFRESH_AT";
        public static final String LAST_GROUPS_REFRESH_AT = "LAST_GROUPS_REFRESH_AT";
        public static final String LAST_LIKE_RANKING_REFRESH_AT = "LAST_LIKE_RANKING_REFRESH_AT";
        public static final String LAST_NOTIFICATION_AT_CHAT = "LAST_NOTIFICATION_AT_CHAT";
        public static final String LAST_NOTIFICATION_AT_REC_AND_RANKING = "LAST_NOTIFICATION_AT";
        public static final String LAST_NOTIFICATION_CALL_AT = "LAST_NOTIFICATION_CALL_AT";

        public static final class AdVersion {
            public static final String KEY1 = "AD_VERSION";
            public static final String LAST_FETCHED_AT = "LAST_FETCHED_AT";
            public static final String NEW_GAME_RANKING_AVAILABLE = "NEW_GAME_RANKING_AVAILABLE";
            public static final String NEW_PERK_AVAILABLE = "NEW_PERK_AVAILABLE";
            public static final String NEW_PRIZE_GROUPS_AVAILABLE = "NEW_PRIZE_GROUPS_AVAILABLE";
            public static final String NEW_RESERVATION_AVAILABLE = "NEW_RESERVATION_AVAILABLE";
        }

        public static final class Chat {
            public static final String LATEST_CHAT_ID = "LATEST_CHAT_ID";
            public static final String LATEST_READ_CHAT_AT = "LATEST_READ_CHAT_AT";
        }

        public static final class ChatGroupInfo {
            public static final String GROUP_UID = "GROUP_UID";
            public static final String KEY1 = "CHAT_GROUP_INFO";
            public static final int PHOTO_TYPE_ICON = 0;
            public static final int PHOTO_TYPE_WALLPAPER = 1;
            public static final String TAKE_PHOTO_TYPE = "PICTURE_TYPE";
            public static final String TOKEN = "TOKEN";
            public static final String USER_UID = "USER_UID";
        }

        public static final class GroupList {
            public static final String GET_STARTUP_PRIVATE_GROUPS = "GET_STARTUP_PRIVATE_GROUPS";
            public static final String GET_STARTUP_PUBLIC_GROUPS = "GET_STARTUP_PUBLIC_GROUPS";
            public static final String KEY1 = "GROUP_LIST";
            public static final String LOAD_GROUPS = "LOAD_GROUPS";
            public static final String PUBLIC_GROUPS_DIALOG_SHOWN = "PUBLIC_GROUPS_DIALOG_SHOWN";
        }

        public static final class Hint {
            public static final String ACCOUNT_CHANGE_HINT_SHOWN = "ACCOUNT_CHANGE_HINT_SHOWN";
            public static final String COMMUNITY_BUTTON_HINT_SHOWN = "COMMUNITY_BUTTON_HINT_SHOWN";
            public static final String CONTACT_HINT_SHOW = "CONTACT_HINT_SHOW";
            public static final String CONTACT_INVITE_HINT_SHOWN = "CONTACT_INVITE_HINT_SHOWN";
            public static final String FRIEND_SEARCH_DIALOG_SHOWN = "FRIEND_SEARCH_DIALOG_SHOWN";
            public static final String JOINED_GROUP_HINT_SHOWN = "JOINED_GROUP_HINT_SHOWN";
            public static final String JOINED_PUBLIC_GROUP = "JOINED_PUBLIC_GROUP";
            public static final String JOIN_RECOMMEND_GROUPS_DIALOG_SHOWN = "JOIN_RECOMMEND_GROUPS_DIALOG_SHOWN";
            public static final String KEY1 = "HINT";
            public static final String SIGNUP_FINISH_DIALOG_SHOWN = "SIGNUP_FINISH_DIALOG_SHOWN";
            public static final String STAMP_HINT_SHOWN = "STAMP_HINT_SHOWN";
            public static final String SWIPE_HINT_SHOWN = "SWIPE_HINT_SHOWN";
            public static final String TUTORIAL_GROUP_JOIN_SHOWN = "TUTORIAL_GROUP_JOIN_SHOWN";
            public static final String WELCOME_PAGE = "WELCOME_PAGE";
        }

        public static final class ImageIntentManager {
            public static final String CHAT = "CHAT";
            public static final String GROUP_ICON = "GROUP_ICON";
            public static final String GROUP_WALLPAPER = "GROUP_WALLPAPER";
            public static final String KEY1 = "IMAGE_INTENT_MANAGER";
            public static final String LAST_PICTURE_KEY = "LAST_PICTURE_KEY";
            public static final String PICTURE_OUTPUT_PATH = "PICTURE_OUTPUT_PATH";
            public static final String PROFILE_COVER = "PROFILE_COVER";
            public static final String PROFILE_ICON = "PROFILE_ICON";
            public static final String REPLY = "REPLY";
        }

        public static final class Notification {
            public static final String BADGE_VALUE = "BADGE_VALUE";
            public static final String KEY1 = "NOTIFICATION";
        }

        public static final class Profile {
            public static final String KEY1 = "PROFILE";
            public static final int PHOTO_TYPE_COVER = 0;
            public static final int PHOTO_TYPE_ICON = 1;
            public static final String TAKE_PHOTO_TYPE = "PICTURE_TYPE";
            public static final String TOKEN = "TOKEN";
            public static final String USER_UID = "USER_UID";
        }

        public static final class Ranking {
            public static final String ACCOUNT_KEY1 = "kKLRDBAccountKey1";
            public static final String JAVASCRIPT_STORAGE_KEY1 = "kKLRDBJavaScriptStorageKey1";
            public static final String PROPERTY_KEY1 = "kKLRDBPropertyKey1";
        }

        public static final class Rec {
            public static final String LAST_CAPTURE_MOVIE = "LAST_CAPTURE_MOVIE";
            public static final String LAST_MOVIE_META_DATA = "LAST_MOVIE_META_DATA";
            public static final String LAST_UPLOAD_MOVIE = "LAST_UPLOAD_MOVIE";
            public static final String MOVIE_STATUS_END_CAPTURING = "MOVIE_STATUS_END_CAPTURING";
            public static final String MOVIE_STATUS_KEY1 = "MOVIE_STATUS";
            public static final String MOVIE_STATUS_START_CAPTURING = "MOVIE_STATUS_START_CAPTURING";
            public static final String MOVIE_STATUS_UPLOADING = "MOVIE_STATUS_UPLOADING";
            public static final String MOVIE_STATUS_UPLOAD_COMPLETE = "MOVIE_STATUS_UPLOAD_COMPLETE";
            public static final String RECORDER_SWITCH_STATE = "RECORDER_SWITCH_STATE";
            public static final String REC_CONFIG = "REC_CONFIG";
            public static final String REC_INFO = "REC_INFO";
            public static final String UPLOAD_MOVIES_KEY1 = "UPLOAD_MOVIES_KEY1";
            public static final String UPLOAD_RANKERS_MOVIES_KEY1 = "UPLOAD_RANKERS_MOVIES_KEY1";
        }

        public static final class Signup {
            public static final String KEY1 = "SIGNUP";
            public static final String LAST_SIGNUP = "LAST_SIGNUP";
        }

        public static final class StampStore {
            public static final String KEY1 = "STAMP_STORE_UNSEEN";
            public static final String LAST_SEEN_STAMPS = "LAST_SEEN_STAMPS";
            public static final String MINIMUM_STAMP_DATE_LONG = "MINIMUM_STAMP_DATE_LONG";
            public static final String STAMP_STORE_CATEGORY = "STAMP_STORE_CATEGORY";
        }

        public static final class Stamps {
            public static final String STAMP_CATEGORY = "STAMP_CATEGORY";
        }

        public static final class Tracker {
            public static final String KEY1 = "TRACKER";
            public static final String LAST_SENT_LAUNCN = "LAST_SENT_LAUNCH";
        }

        public static final class UnreadChat {
            public static final String KEY1 = "ACCOUNT_UNREAD";
            public static final String LAST_SEEN_ACCOUNT_POPUP_BADGE_AT = "LAST_SEEN_ACCOUNT_POPUP_BADGE_AT";
            public static final String LAST_SEEN_UNREAD_PRIVATE_CHATS_AT = "LAST_SEEN_UNREAD_PRIVATE_CHATS_AT";
            public static final String LAST_UNREAD_FOUND = "LAST_UNREAD_FOUND";
            public static final String UNREAD_ACCOUNT_COUNT = "UNREAD_ACCOUNT_COUNT";
            public static final String UNREAD_PRIVATE_CHATS = "UNREAD_PUBLIC_CHATS";
            public static final String UNREAD_PRIVATE_CHATS_COUNT = "UNREAD_PRIVATE_CHATS_COUNT";
            public static final String UNREAD_PUBLIC_CHATS = "UNREAD_PUBLIC_CHATS";
            public static final String UNREAD_PUBLIC_CHATS_COUNT = "UNREAD_PUBLIC_CHATS";
        }

        public static final class Version {
            public static final String KEY1 = "VERSION";
            public static final String LAST_CHECKED_AT = "LAST_CHECKED_AT";
            public static final String NEW_MARK_ON_MENU = "NEW_MARK_ON_MENU";
            public static final String NEW_MARK_ON_STAMP_TAB = "NEW_MARK_ON_STAMP_TAB";
            public static final String NEW_STAMPS_AVAILAVLE = "NEW_STAMPS_AVAILAVLE";
            public static final String STAMP_PUBLISHED_AT = "STAMP_PUBLISHED_AT";
            public static final String STAMP_VERSION = "STAMP_VERSION";
        }
    }

    public static final class Key {
        public static final String FROM_CATEGORY_ID = "FROM_CATEGORY_ID";
        public static final String LAST_CHAT_ID = "LAST_CHAT_ID";
        public static final String MISC_NOTIFICATIONS = "MISC_NOTIFICATIONS";
        public static final String SHOUT_NOTIFICATIONS = "SHOUT_NOTIFICATIONS";
        public static final String VIDEO_UPLOAD_NOTIFICATION_AVAILABLE = "VIDEO_UPLOAD_NOTIFICATION_AVAILABLE";
    }

    public static final class StampCategory {
        public static final String CREATE_SQL = "CREATE TABLE stamp_category_table (c_category TEXT , c_name TEXT , c_icon TEXT , c_uid TEXT , c_order INTEGER , PRIMARY KEY  (c_category,c_uid));";
        public static final String C_CATEGORY = "c_category";
        public static final String C_ICON = "c_icon";
        public static final String C_NAME = "c_name";
        public static final String C_ORDER = "c_order";
        public static final String C_UID = "c_uid";
        public static final String TABLE = "stamp_category_table";
    }

    public static final class StampHistory {
        public static final String CREATE_SQL = "CREATE TABLE stamp_history_table (c_uid TEXT , c_last_used_at INTEGER , PRIMARY KEY  (c_uid));";
        public static final String C_LAST_USED_AT = "c_last_used_at";
        public static final String C_UID = "c_uid";
        public static final String HISTORY_LIMIT = "32";
        public static final String TABLE = "stamp_history_table";
    }

    public static final class StampItem {
        public static final String CREATE_SQL = "CREATE TABLE stamp_item_table (c_uid TEXT  PRIMARY KEY  ,c_image TEXT ,c_thumb TEXT ,c_width INTEGER ,c_height INTEGER ,c_state INTEGER ,c_order INTEGER );";
        public static final String C_HEIGHT = "c_height";
        public static final String C_IMAGE = "c_image";
        public static final String C_ORDER = "c_order";
        public static final String C_STATE = "c_state";
        public static final String C_THUMB = "c_thumb";
        public static final String C_UID = "c_uid";
        public static final String C_WIDTH = "c_width";
        public static final String TABLE = "stamp_item_table";
    }

    public static final class Upload {
        public static final String CREATE_SQL = "CREATE TABLE upload_table (c_uid INTEGER  PRIMARY KEY   AUTOINCREMENT , c_group_uid TEXT , c_reply_to TEXT , c_total INTEGER , c_message TEXT , c_shout INTEGER );";
        public static final String C_GROUP_UID = "c_group_uid";
        public static final String C_MESSAGE = "c_message";
        public static final String C_REPLY_TO = "c_reply_to";
        public static final String C_SHOUT = "c_shout";
        public static final String C_TOTAL = "c_total";
        public static final String C_UID = "c_uid";
        public static final String TABLE = "upload_table";
    }

    public static final class UploadItem {
        public static final String CREATE_SQL = "CREATE TABLE upload_item_table (c_uid TEXT , c_upload_uid INTEGER , c_type TEXT , c_url TEXT , c_complete INTEGER , PRIMARY KEY  (c_uid));";
        public static final String C_COMPLETE = "c_complete";
        public static final String C_TYPE = "c_type";
        public static final String C_UID = "c_uid";
        public static final String C_UPLOAD_UID = "c_upload_uid";
        public static final String C_URL = "c_url";
        public static final String TABLE = "upload_item_table";
    }

    public static final class UserContact {
        public static final String CREATE_INDEX_SQL = "CREATE INDEX c_name_index ON user_contact_table ( c_name )";
        public static final String CREATE_SQL = "CREATE TABLE user_contact_table (c_uid TEXT ,c_name TEXT ,c_description TEXT ,c_icon TEXT ,c_contacts_count INTEGER ,c_my_groups_count INTEGER ,c_contacted_date INTEGER ,c_user_uid TEXT , PRIMARY KEY   (c_uid,c_user_uid));";
        public static final String C_CONTACTED_DATE = "c_contacted_date";
        public static final String C_CONTACTS_COUNT = "c_contacts_count";
        public static final String C_DESCRIPTION = "c_description";
        public static final String C_ICON = "c_icon";
        public static final String C_MY_GROUPS_COUNT = "c_my_groups_count";
        public static final String C_NAME = "c_name";
        public static final String C_NAME_INDEX = "c_name_index";
        public static final String C_UID = "c_uid";
        public static final String C_USER_UID = "c_user_uid";
        public static final String TABLE = "user_contact_table";

        public enum Order {
            NAME("c_name"),
            CONTACTED_DATE("c_contacted_date");
            
            private boolean caseInsensitive;
            private boolean desc;
            private final String order;

            private Order(String order) {
                this.desc = true;
                this.caseInsensitive = false;
                this.order = order;
            }

            public void setDesc(boolean b) {
                this.desc = b;
            }

            public void setCaseInsensitive(boolean b) {
                this.caseInsensitive = b;
            }

            public String getValue() {
                return this.order + (this.caseInsensitive ? " COLLATE NOCASE " : "") + (this.desc ? " DESC" : " ASC");
            }
        }
    }

    public static final class Widget {
        public static final String CREATE_SQL = "CREATE TABLE widget_table (c_app_widget_id INTEGER , c_token TEXT  NOT NULL  DEFAULT '', c_user_uid TEXT  NOT NULL , c_group_uid TEXT , c_updated_at INTEGER  NOT NULL , PRIMARY KEY  (c_app_widget_id));";
        public static final String C_APP_WIDGET_ID = "c_app_widget_id";
        public static final String C_GROUP_UID = "c_group_uid";
        public static final String C_TOKEN = "c_token";
        public static final String C_UPDATED_AT = "c_updated_at";
        public static final String C_USER_UID = "c_user_uid";
        public static final String TABLE = "widget_table";
    }
}
