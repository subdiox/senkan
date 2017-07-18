package com.rekoo.libs.database;

import com.kayac.lobi.libnakamap.datastore.AccountDDL.KKey.Mixi;

public class UserColumns {
    public static final String FORUMURL = "FORUMURL";
    public static final String ISRUN = "ISRUN";
    public static final String IS_FAST = "IS_FAST";
    public static final String IS_PASS = "IS_PASS";
    public static final String LAST_REFRESH_TIME = "LAST_REFRESH_TIME";
    public static final String PASSWORD = "PASSWORD";
    public static final String PHONE = "PHONE";
    public static final String TOKEN = "TOKEN";
    public static final String UID = "MID";
    public static final String USERNAME = "USERNAME";
    public static final String USERTYPE = "USERTYPE";

    public static String[] getAll() {
        return new String[]{UID, "TOKEN", USERNAME, PASSWORD, PHONE, "MOBILE", LAST_REFRESH_TIME, "SKIP_BIND_MOBILE", USERTYPE, Mixi.REFRESH_TOKEN, "LOGIN_TIME", "REGISTER_MODE"};
    }
}
