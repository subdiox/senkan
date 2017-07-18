package com.kayac.lobi.sdk.migration.datastore;

interface CommonDDL {
    public static final String CREATE_TABLE = "CREATE TABLE ";
    public static final String T_BLOB = " BLOB ";
    public static final String T_DEFAULT = " DEFAULT ";
    public static final String T_INTEGER = " INTEGER ";
    public static final String T_NOT_NULL = " NOT NULL ";
    public static final String T_PRIMARY_KEY = " PRIMARY KEY  ";
    public static final String T_REAL = " REAL ";
    public static final String T_TEXT = " TEXT ";

    public static final class KKVS {
        public static final String CREATE_SQL = "CREATE TABLE key_key_value_table (c_key_1 TEXT ,c_key_2 TEXT ,c_value BLOB  NOT NULL , PRIMARY KEY  (c_key_1,c_key_2));";
        public static final String C_KEY_1 = "c_key_1";
        public static final String C_KEY_2 = "c_key_2";
        public static final String C_VALUE = "c_value";
        public static final String TABLE = "key_key_value_table";
        public static final String UPDATE_SQL = null;
    }

    public static final class KVS {
        public static final String CREATE_SQL = "CREATE TABLE key_value_table (c_key TEXT  PRIMARY KEY  ,c_value BLOB  NOT NULL );";
        public static final String C_KEY = "c_key";
        public static final String C_VALUE = "c_value";
        public static final String TABLE = "key_value_table";
        public static final String UPDATE_SQL = null;
    }
}
