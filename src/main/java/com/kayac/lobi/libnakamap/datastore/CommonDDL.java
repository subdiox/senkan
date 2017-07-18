package com.kayac.lobi.libnakamap.datastore;

public interface CommonDDL {
    public static final String CREATE_INDEX = "CREATE INDEX ";
    public static final String CREATE_TABLE = "CREATE TABLE ";
    public static final String T_AUTO_INCREMENT = " AUTOINCREMENT ";
    public static final String T_BLOB = " BLOB ";
    public static final String T_DEFAULT = " DEFAULT ";
    public static final String T_ID = " ID ";
    public static final String T_INTEGER = " INTEGER ";
    public static final String T_NOT_NULL = " NOT NULL ";
    public static final String T_ON_CONFLICT_REPLACE = " ON CONFLICT REPLACE ";
    public static final String T_PRIMARY_KEY = " PRIMARY KEY  ";
    public static final String T_REAL = " REAL ";
    public static final String T_TEXT = " TEXT ";
    public static final String T_UNIQUE = " UNIQUE ";

    public static final class App {
        public static final String CREATE_SQL = "CREATE TABLE app_table (c_name TEXT , c_icon TEXT , c_appstore_uri TEXT , c_playstore_uri TEXT , c_uid TEXT , c_client_id TEXT , PRIMARY KEY  (c_uid));";
        public static final String C_APPSTORE_URI = "c_appstore_uri";
        public static final String C_CLIENT_ID = "c_client_id";
        public static final String C_ICON = "c_icon";
        public static final String C_NAME = "c_name";
        public static final String C_PLAYSTORE_URI = "c_playstore_uri";
        public static final String C_UID = "c_uid";
        public static final String TABLE = "app_table";
    }

    public static final class KKVS {
        public static final String CREATE_SQL = "CREATE TABLE key_key_value_table (c_key_1 TEXT ,c_key_2 TEXT ,c_value BLOB  NOT NULL , PRIMARY KEY  (c_key_1,c_key_2));";
        public static final String C_KEY_1 = "c_key_1";
        public static final String C_KEY_2 = "c_key_2";
        public static final String C_VALUE = "c_value";
        public static final String TABLE = "key_key_value_table";
    }

    public static final class KVS {
        public static final String CREATE_SQL = "CREATE TABLE key_value_table (c_key TEXT  PRIMARY KEY  ,c_value BLOB  NOT NULL );";
        public static final String C_KEY = "c_key";
        public static final String C_VALUE = "c_value";
        public static final String TABLE = "key_value_table";
    }

    public static final class User {
        public static final String CREATE_SQL = "CREATE TABLE user_table (c_uid TEXT , c_default INTEGER , c_token TEXT , c_name TEXT , c_description TEXT , c_icon TEXT , c_cover TEXT , c_contacts_count INTEGER , c_contacted_date INTEGER , c_is_nan_location INTEGER , c_lng REAL , c_lat REAL , c_located_date INTEGER , c_unread_counts INTEGER , c_app_uid TEXT , c_ex_id TEXT , PRIMARY KEY  (c_uid));";
        public static final String C_APP_UID = "c_app_uid";
        public static final String C_CONTACTED_DATE = "c_contacted_date";
        public static final String C_CONTACTS_COUNT = "c_contacts_count";
        public static final String C_COVER = "c_cover";
        public static final String C_DEFAULT = "c_default";
        public static final String C_DESCRIPTION = "c_description";
        public static final String C_EX_ID = "c_ex_id";
        public static final String C_ICON = "c_icon";
        public static final String C_IS_NAN_LOCATION = "c_is_nan_location";
        public static final String C_LAT = "c_lat";
        public static final String C_LNG = "c_lng";
        public static final String C_LOCATED_DATE = "c_located_date";
        public static final String C_NAME = "c_name";
        public static final String C_TOKEN = "c_token";
        public static final String C_UID = "c_uid";
        public static final String C_UNREAD_COUNTS = "c_unread_counts";
        public static final String TABLE = "user_table";
    }
}
