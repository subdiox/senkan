package com.rekoo.libs.config;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.rekoo.libs.callback.RKLibInitCallback;
import com.rekoo.libs.callback.RKLoginCallback;
import com.rekoo.libs.cons.Cons;
import com.rekoo.libs.entity.User;
import com.rekoo.libs.utils.CommonUtils;

public final class Config {
    public static int BIND = 0;
    public static final int BIND_COMMON = 2;
    public static final boolean CLICK_LOGOUT = true;
    public static final String INIT_FAIL_NO_NETWORK = "-1";
    public static boolean IS_JUST_BIND_PHONE = false;
    public static final String LOGIN_BACK_ACCOUNT_EXCEPTION = "-6";
    public static final String LOGIN_FAIL_EXCEPTION = "-8";
    public static final String LOGIN_FAIL_TIMEOUT = "-9";
    public static final String LOGIN_JSON_EXCEPTION = "-4";
    public static final String LOGIN_NOT_INIT = "-2";
    public static final String LOGIN_NULL_POINT = "-3";
    public static final String LOGIN_OLD_ACCOUNT_FAIL = "-7";
    public static final String LOGIN_RC_EXCEPTION = "-5";
    public static final boolean NOT_CLICK_LOGOUT = false;
    private static Config config;
    public static User currentLoginUser = null;
    public static String forumUrl = null;
    public static String gooleAdvertisingId = null;
    public static boolean isComeMainLoginActivity = false;
    public static boolean isFeedback = false;
    public static boolean isInit = false;
    public static boolean isLogin = false;
    public static boolean isLogout = false;
    public static boolean isMobAgent = true;
    public static boolean isRun = false;
    public static boolean isShow = false;
    public static boolean isShowbar = false;
    public static boolean isSolve = false;
    public static boolean isgother = false;
    public static RKLibInitCallback libInitCallback = null;
    public static RKLoginCallback loginCallback = null;
    public static int oldlength = 0;
    public static String packageName = null;
    public static int push_auth = 1;
    public static String regId = null;
    public static int sendno = 0;
    public static String uid = null;

    private Config() {
    }

    public static Config getConfig() {
        if (config == null) {
            synchronized (Config.class) {
                if (config == null) {
                    config = new Config();
                }
            }
        }
        return config;
    }

    public String getAppId(Context context) {
        String gameId = new StringBuilder(String.valueOf(CommonUtils.getIntMetaData(context, "RK_APPID"))).toString();
        if (!TextUtils.isEmpty(gameId)) {
            return gameId;
        }
        throw new NullPointerException("gameId is null , you don't set gameId in AndroidManifest.xml,see your AndroidManifest.xml");
    }

    public String packageName(Context context) {
        String packagename = new StringBuilder(String.valueOf(CommonUtils.getStrMetaData(context, "PACKAGENAMES"))).toString();
        if (TextUtils.isEmpty(packagename)) {
            throw new NullPointerException("packagename is null , you don't set packagename in AndroidManifest.xml,see your AndroidManifest.xml");
        }
        Log.i("TAG", "packagename" + packagename);
        return packagename;
    }

    public String getSendId(Context context) {
        String send = new StringBuilder(String.valueOf(CommonUtils.getStrMetaData(context, "SENDID"))).toString();
        Log.i("TAG", "send" + send);
        if (TextUtils.isEmpty(send)) {
            throw new NullPointerException("sendId is null, you don't set sendId in AndroidManifest.xml");
        }
        String sendId = send.substring(2);
        Log.i("TAG", "sendId=" + sendId);
        return sendId;
    }

    public void setCurrentLoginUser(User user) {
        if (user != null) {
            currentLoginUser = user;
        }
    }

    public User getCurrentLoginUser() {
        if (currentLoginUser != null) {
            return currentLoginUser;
        }
        throw new NullPointerException("current login user is null ,you did not call setCurrentLoginUser method");
    }

    public String getChannel(Context context) {
        String channel = new StringBuilder(String.valueOf(CommonUtils.getStrMetaData(context, Cons.CHANNEL))).toString();
        if (!TextUtils.isEmpty(channel)) {
            return channel;
        }
        throw new NullPointerException("REKOO_CHANNEL is null , you don't set REKOO_CHANNEL in AndroidManifest.xml,see your AndroidManifest.xml");
    }

    public static void clearAppInfo() {
        isLogin = false;
        currentLoginUser = null;
    }
}
