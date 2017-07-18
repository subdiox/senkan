package com.rekoo.libs.cons;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.rekoo.libs.config.BIConfig;
import com.rekoo.libs.config.Config;
import com.rekoo.libs.encrypt_decrypt.MD5;
import com.rekoo.libs.entity.User;
import com.rekoo.libs.net.NetRequest;
import com.rekoo.libs.net.URLCons;
import com.rekoo.libs.utils.MacAddressUtil;
import com.squareup.okhttp.RequestBody;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LoginCons {
    private static LoginCons loginCons = null;
    private String currentTime = null;

    private LoginCons() {
    }

    public static LoginCons getCons() {
        if (loginCons == null) {
            synchronized (LoginCons.class) {
                if (loginCons == null) {
                    loginCons = new LoginCons();
                }
            }
        }
        return loginCons;
    }

    public String getLoginSign(Context context, User user) {
        Log.i("TAG", "currentTime" + this.currentTime);
        ArrayList<String> olds = new ArrayList();
        Log.i("dongnan", URLCons.getRKId(context));
        olds.add("channel=" + URLCons.getChannel(context));
        olds.add("device=" + URLCons.getDeviceModel());
        olds.add("device_id=" + Config.regId);
        olds.add("device_unique_key=" + URLCons.getRKId(context));
        olds.add("from=Android");
        olds.add("gid=" + URLCons.getAppId(context));
        olds.add("push_auth=" + Config.push_auth);
        olds.add("account=" + user.getUserName());
        olds.add("pwd=" + user.getPassword());
        olds.add("src=rksgame_phone");
        olds.add("time=" + this.currentTime);
        olds.add("v=2.0.2");
        olds.add("mac=" + MacAddressUtil.getMacAddress(context));
        if (!TextUtils.isEmpty(BIConfig.getBiConfig().getDeviceId(context))) {
            olds.add("IMEI=" + BIConfig.getBiConfig().getDeviceId(context));
        }
        if (!TextUtils.isEmpty(Config.gooleAdvertisingId)) {
            olds.add("adid=" + Config.gooleAdvertisingId);
        }
        Collections.sort(olds);
        String sig = "";
        for (int i = 0; i < olds.size() - 1; i++) {
            sig = new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(sig)).append((String) olds.get(i)).toString())).append("&").toString();
        }
        sig = new StringBuilder(String.valueOf(sig)).append((String) olds.get(olds.size() - 1)).toString();
        String sign = MD5.getMD5(sig);
        Log.i("TAG", new StringBuilder(URLCons.SIGN).append(sig).toString());
        return sign;
    }

    public RequestBody getLoginRequestBody(Context context, User user) {
        this.currentTime = new StringBuilder(String.valueOf(System.currentTimeMillis())).toString();
        return NetRequest.getRequest().getRequestBody(context, getLoginParames(context, user));
    }

    public Map<String, String> getLoginParames(Context context, User user) {
        Map<String, String> parames = new HashMap();
        parames.put("gid", URLCons.getAppId(context));
        parames.put(URLCons.TIME, this.currentTime);
        Log.i("TAG", "currentTime" + this.currentTime);
        parames.put(URLCons.DEVICE, URLCons.getDeviceModel());
        parames.put(URLCons.SIGN, getLoginSign(context, user));
        parames.put(URLCons.FROM, URLCons.FROM_DEVICE_TYPE_ANDROID);
        parames.put(URLCons.DEVICE_ID, Config.regId);
        parames.put(URLCons.USER_NAME, user.getUserName());
        parames.put(URLCons.PUSH_AUTH, Config.push_auth);
        parames.put(URLCons.DEVICE_UNIQUE_KEY, URLCons.getRKId(context));
        parames.put(URLCons.CHANNEL, Config.getConfig().getChannel(context));
        parames.putAll(Cons.getCommonParames(context));
        parames.put(URLCons.MAC, MacAddressUtil.getMacAddress(context));
        if (!TextUtils.isEmpty(BIConfig.getBiConfig().getDeviceId(context))) {
            parames.put(URLCons.IMEI, BIConfig.getBiConfig().getDeviceId(context));
        }
        if (!TextUtils.isEmpty(Config.gooleAdvertisingId)) {
            parames.put(URLCons.ADID, Config.gooleAdvertisingId);
        }
        return parames;
    }
}
