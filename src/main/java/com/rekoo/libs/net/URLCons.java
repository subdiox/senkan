package com.rekoo.libs.net;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.support.v4.os.EnvironmentCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.rekoo.libs.config.Config;
import com.rekoo.libs.cons.Cons;
import java.util.HashMap;
import java.util.UUID;

public class URLCons {
    public static final String ACCOUNTINFO = "http://sdk.jp.rekoo.net/exchange/accounts";
    public static final String ADID = "adid";
    public static final String BASE = "http://sdk.jp.rekoo.net";
    public static final String BASE_EXCEPTION = "http://jrlog.rekoo.net";
    public static final String CATEGORY = "category";
    public static final String CHANGE_PAY_PWD = "http://sdk.jp.rekoo.net/exchange/pwd/change";
    public static final String CHANNEL = "channel";
    public static final String CHANNEL_NAME = "rekoo";
    public static final String CODE = "code";
    public static final String CONTENT = "content";
    public static final String DEVICE = "device";
    public static final String DEVICE_ID = "device_id";
    public static final String DEVICE_UNIQUE_KEY = "device_unique_key";
    public static final String FROM = "from";
    public static final String FROM_DEVICE_TYPE_ANDROID = "Android";
    public static final String GETORDERMESSAGE_URL = "http://sdk.jp.rekoo.net/sdk2/pay/now/sign";
    public static final String GID = "gid";
    public static final String HTTP = "http://";
    public static final String IDENTIFYING_CODE = "smsvalue";
    public static final String IMEI = "IMEI";
    public static final String INFO = "info";
    public static final String MAC = "mac";
    public static String MAKE_ORDER_URL = "http://sdk.jp.rekoo.net/sdk2/pay/request";
    public static final String METHOD = "method";
    public static final String MOBILE = "mobile";
    public static final String NEWPAYPWD = "newpwd";
    public static final String OLDPAYPWD = "oldpwd";
    public static final String PASSWORD = "pwd";
    public static final String PHONETYPE = "phoneType";
    public static final String PUSH_AUTH = "push_auth";
    public static final String RECHARGE_REKOOCOIN_CONFIRM_RECHARGE = "http://sdk.jp.rekoo.net/exchange/orders";
    public static final String REKOOCOIN_LIST = "http://sdk.jp.rekoo.net/exchange/coins";
    public static final String RESET_REKOOPAYPWD = "http://sdk.jp.rekoo.net/exchange/pwd/find";
    public static final String SENDNO = "sendno";
    public static final String SEND_METHOD = "mobile";
    public static final String SIGN = "sign";
    public static final String SRC = "src";
    public static final String SRC_CONTENT = "rksgame_phone";
    public static final String SYSVERSION = "sysversion";
    public static final String TIME = "time";
    public static final String TOKEN = "token";
    public static final String TRANSID = "transid";
    public static final String UID = "uid";
    public static final String URL_BBS_REKOO = "http://bbs.jp.rekoo.net";
    public static final String URL_BBS_STATUS = "http://sdk.jp.rekoo.net/config/bbs";
    public static final String URL_BIND_PHONENUM = "http://sdk.jp.rekoo.net/sdk3/mobile/bind";
    public static final String URL_BIND_PHONE_SEND_CODE = "http://sdk.jp.rekoo.net/mobile/send/code";
    public static final String URL_CHANGE_ACCOUNT = "http://sdk.jp.rekoo.net/sdkv2/change/account";
    public static final String URL_CHECK_CODE_BIND = "http://sdk.jp.rekoo.net/sdk3/bind/code/check";
    public static final String URL_CHECK_CODE_REGISTER = "http://sdk.jp.rekoo.net/sdk3/reg/code/check";
    public static final String URL_CHECK_CODE_RESET_PWD = "http://sdk.jp.rekoo.net/sdk2/check/pwd/code";
    public static final String URL_COMMON_LOGIN = "http://sdk.jp.rekoo.net/sdkv2/login/account";
    public static final String URL_EXCEPTION = "http://jrlog.rekoo.net/error/content";
    public static final String URL_FEEDBACK_LIST = "http://sdk.jp.rekoo.net/feedback/list";
    public static final String URL_FIND_LOGIN = "http://sdk.jp.rekoo.net/oauth/login/key";
    public static final String URL_FORGET_PWD_SEND_CODE = "http://sdk.jp.rekoo.net/sdk2/send/pwd/code";
    public static final String URL_GET_USER_INFO = "http://sdk.jp.rekoo.net/oauth2/get/user";
    public static final String URL_GUEST_LOGIN = "http://sdk.jp.rekoo.net/sdkv2/generate";
    public static String URL_INIT_PAY = "http://sdk.jp.rekoo.net/sdk2/ipay/init";
    public static final String URL_JUST_BIND_PHONE = "http://sdk.jp.rekoo.net/exchange/mobile/bind";
    public static final String URL_MESSAGE_READ = "http://sdk.jp.rekoo.net/push/message/read";
    public static final String URL_PHONE_NUM_LOGIN = "http://sdk.jp.rekoo.net/sdk2/guest/mobile/login";
    public static final String URL_PHONE_REGISTER = "http://sdk.jp.rekoo.net/sdk3/guest/phreg";
    public static final String URL_PHONE_REGISTER_SEND_CODE = "http://sdk.jp.rekoo.net/sdk2/guest/reg/send";
    public static final String URL_PUSH_CHECK = "http://sdk.jp.rekoo.net/push/check/type";
    public static final String URL_RESET_NEW_PASSWORD = "http://sdk.jp.rekoo.net/sdk2/find/pwd";
    public static final String URL_TRANSFORM = "http://sdk.jp.rekoo.net/sdkv2/get/transcode";
    public static final String URL_USER_BIND = "http://sdk.jp.rekoo.net/sdkv2/bind";
    public static final String URL_USER_NAME_BIND = "http://sdk.jp.rekoo.net/sdk3/guest/bind";
    public static final String URL_USER_NAME_REGISTER = "http://sdk.jp.rekoo.net/oauth2/fastlogin";
    public static final String USER_NAME = "account";
    public static final String VERSION = "v";
    public static final String VERSION_NUM = "2.0.2";

    public static String getDeviceModel() {
        return (Build.MODEL == null ? EnvironmentCompat.MEDIA_UNKNOWN : Build.MODEL).replace("(", "").replace(")", "").replace("+", "").replace("-", "").replace("*", "").replace("&", "").replace(" ", "");
    }

    public static String getAppId(Context context) {
        return Config.getConfig().getAppId(context);
    }

    public static String getMacAddress(Context context) {
        try {
            String macSha1 = (String) invokeStaticMethod("com.rekoo.libs.utils.MacAddressUtil", "getMacAddress", new Class[]{Context.class}, context);
            Log.i("TAG", "mac地址是" + macSha1);
            return macSha1.toUpperCase();
        } catch (Exception e) {
            return null;
        }
    }

    public static Object invokeStaticMethod(String className, String methodName, Class[] cArgs, Object... args) throws Exception {
        return invokeMethod(Class.forName(className), methodName, null, cArgs, args);
    }

    public static Object invokeMethod(Class classObject, String methodName, Object instance, Class[] cArgs, Object... args) throws Exception {
        return classObject.getMethod(methodName, cArgs).invoke(instance, args);
    }

    public static String getAndroidId(Context context) {
        try {
            return Secure.getString(context.getContentResolver(), "android_id");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getChannel(Context context) {
        return Config.getConfig().getChannel(context);
    }

    public static String getPackageName(Context context) {
        return Config.getConfig().packageName(context);
    }

    public static String getRKId(Context context) {
        if (!TextUtils.isEmpty(Config.gooleAdvertisingId)) {
            return Config.gooleAdvertisingId;
        }
        if (!TextUtils.isEmpty(getMacAddress(context))) {
            return getMacAddress(context);
        }
        if (TextUtils.isEmpty(getAndroidId(context))) {
            return UUID.randomUUID().toString();
        }
        return getAndroidId(context);
    }

    public static HashMap<String, String> getDeviceInfo(Context context) {
        TelephonyManager telManager = (TelephonyManager) context.getSystemService("phone");
        WifiInfo wifiInfo = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo();
        HashMap<String, String> hmp = new HashMap();
        hmp.put(Cons.DEVICE_MODEL, getDeviceModel());
        hmp.put(Cons.DEVICE_ID, telManager.getDeviceId());
        hmp.put(Cons.DEVICE_MAC, wifiInfo.getMacAddress());
        hmp.put(Cons.DEVICE_BRAND, Build.BRAND);
        hmp.put(Cons.DEVICE_VERSION, VERSION.RELEASE);
        return hmp;
    }
}
