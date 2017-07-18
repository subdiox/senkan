package com.kayac.lobi.libnakamap.datastore;

public class AccountDDL implements CommonDDL {

    public static final class KKey {
        public static final String NOTIFICATION_ID = "NOTIFICATION_ID";
        public static final String NOTIFICATION_PUSH_ENABLED = "NOTIFICATION_PUSH_ENABLED";
        public static final String RECEIVE_INVITATION_NOTICE_ENABLED = "RECEIVE_INVITATION_NOTICE_ENABLED";
        public static final String SETTING_SHOUT_FROM = "SETTING_SHOUT_FROM";
        public static final String SETTING_SHOUT_TIL = "SETTING_SHOUT_TIL";
        public static final String SETTING_SHOUT_TYPE = "SETTING_SHOUT_TYPE";

        public static final class Facebook {
            public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
            public static final String EXPIRES = "EXPIRES_IN";
            public static final String ID = "ID";
            public static final String KEY1 = "FACEBOOK";
            public static final String NAME = "NAME";
        }

        public static final class GeoLocation {
            public static final String ACCURACY = "ACCURACY";
            public static final String DATE = "DATE";
            public static final String KEY1 = "GEO_LOCATION";
            public static final String LAT = "LAT";
            public static final String LNG = "LNG";
            public static final String PROVIDER = "PROVIDER";
        }

        public static final class GlobalSettings {
            public static final String AUTO_ADD_EMAIL_ENABLED = "AUTO_ADD_EMAIL_ENABLED";
            public static final String AUTO_ADD_FACEBOOK_ENABLED = "AUTO_ADD_FACEBOOK_ENABLED";
            public static final String AUTO_ADD_MIXI_ENABLED = "AUTO_ADD_MIXI_ENABLED";
            public static final String AUTO_ADD_TWITTER_ENABLED = "AUTO_ADD_TWITTER_ENABLED";
            public static final String GPS_ENABLED = "GPS_ENABLED";
            public static final String KEY1 = "GLOBAL_SEGGINGS";
            public static final String LOCATION_ENABLED = "LOCATION_ENABLED";
            public static final String SEARCH_ENABLED = "SEARCH_ENABLED";
        }

        public static final class LaunchHook {
            public static final String KEY1 = "LAUNCH_HOOK";
            public static final String URI = "uri";
        }

        public static final class LoginAccount {
            public static final String CONTACT_FACEBOOK_LOGIN = "CONTACT_FACEBOOK_LOGIN";
            public static final String CONTACT_TWITTER_LOGIN = "CONTACT_TWITTER_LOGIN";
            public static final String KEY1 = "ACCOUNT";
            public static final String MAIL_CONFIRM_CHECK_COUNT = "MAIL_CONFIRM_CHECK_COUNT";
            public static final String NEED_MAIL_CONFIRMATION = "NEED_MAIL_CONFIRMATION";
            public static final String SERVICE = "SERVICE";
            public static final String SIGNUP_KIND_EMAIL = "mail";
            public static final String SIGNUP_KIND_FACEBOOK = "facebook";
            public static final String SIGNUP_KIND_TWITTER = "twitter";
        }

        public static final class Mail {
            public static final String KEY1 = "MAIL";
            public static final String MAIL_ADDRESS = "MAIL_ADDRESS";
        }

        public static final class Mixi {
            public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
            public static final String KEY1 = "MIXI";
            public static final String REFRESH_TOKEN = "REFRESH_TOKEN";
        }

        public static final class NotificationSound {
            public static final String KEY1 = "NOTIFICATION_SOUND";
            public static final String SOUND = "SOUND";
            public static final String SOUND_DEFAULT = "0";
        }

        public static final class SDKBindFinish {
            public static final String BIND_TOKEN = "bind_token";
            public static final String KEY1 = "SDK_BIND_FINISH";
            public static final String SDK_CLIENT = "sdk_client";
            public static final String UID = "uid";
        }

        public static final class SDKBindStart {
            public static final String KEY1 = "SDK_BIND_START";
            public static final String SDK_CLIENT = "sdk_client";
            public static final String SDK_REPORTING_SESSION = "sdk_reporting_session";
            public static final String SDK_SESSION = "sdk_session";
            public static final String SDK_VERSION = "sdk_version";
        }

        public static final class ServerSettings {
            public static final String KEY1 = "SERVER_SETTINGS";
            public static final String SERVER_ENABLED = "SERVER_ENABLED";
        }

        public static final class Twitter {
            public static final String KEY1 = "TWITTER";
            public static final String NAME = "NAME";
            public static final String TOKEN = "ACCESS_TOKEN";
            public static final String TOKEN_SECRET = "ACCESS_TOKEN_SECRET";
        }

        public static final class UpdateAt {
            public static final String GET_ME_CONTACTS = "GET_ME_CONTACTS";
            public static final long GET_ME_CONTACTS_CACHE = 600000;
            public static final String GET_ME_USERS = "GET_ME_USERS";
            public static final String GET_REC_INFO = "GET_REC_INFO";
            public static final long GET_REC_INFO_CACHE = 3600000;
            public static final String GET_STAMPS = "GET_STAMPS";
            public static final long GET_STAMPS_CACHE = 600000;
            public static final String KEY1 = "UPDATE_AT";
        }

        public static final class UsageReporting {
            public static final String KEY1 = "USER_REPORTING";
            public static final String REFERRER = "referrer";
            public static final String SESSION_ID = "session_id";
        }
    }

    public static final class Key {
        public static final String CURRENT_ACCOUNT_USER_UID = "CURRENT_ACCOUNT_USER_UID";
        public static final String LAST_CHECKED_VERSION = "LAST_CHECKED_VERSION";
        public static final String MY_GROUP_NAME = "MY_GROUP_NAME";
        public static final String MY_GROUP_UID = "MY_GROUP_UID";
        public static final String PREF_IS_LOGIN_BY_MAIL = "PREF_IS_LOGIN_BY_MAIL";
    }
}
