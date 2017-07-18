package com.rekoo.libs.cons;

import android.content.Context;
import com.rekoo.libs.config.Config;
import com.rekoo.libs.encrypt_decrypt.MD5;
import com.rekoo.libs.net.NetRequest;
import com.rekoo.libs.net.URLCons;
import com.squareup.okhttp.RequestBody;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FindUserCons {
    private static FindUserCons findUserCons = null;
    private String currentTime = null;

    private FindUserCons() {
    }

    public static FindUserCons getCons() {
        if (findUserCons == null) {
            synchronized (TouristCons.class) {
                if (findUserCons == null) {
                    findUserCons = new FindUserCons();
                }
            }
        }
        return findUserCons;
    }

    public String getFindUserSign(Context context) {
        ArrayList<String> olds = new ArrayList();
        olds.add("channel=" + URLCons.getChannel(context));
        olds.add("device=" + URLCons.getDeviceModel());
        olds.add("device_id=" + Config.regId);
        olds.add("device_unique_key=" + URLCons.getRKId(context));
        olds.add("from=Android");
        olds.add("gid=" + URLCons.getAppId(context));
        olds.add("push_auth=" + Config.push_auth);
        olds.add("src=rksgame_phone");
        olds.add("time=" + this.currentTime);
        Collections.sort(olds);
        String sig = "";
        for (int i = 0; i < olds.size() - 1; i++) {
            sig = new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(sig)).append((String) olds.get(i)).toString())).append("&").toString();
        }
        return MD5.getMD5(new StringBuilder(String.valueOf(sig)).append((String) olds.get(olds.size() - 1)).toString());
    }

    public RequestBody getFindUserRequestBody(Context context) {
        this.currentTime = new StringBuilder(String.valueOf(System.currentTimeMillis())).toString();
        return NetRequest.getRequest().getRequestBody(context, getFindUserParames(context));
    }

    private Map<String, String> getFindUserParames(Context context) {
        Map<String, String> parames = new HashMap();
        parames.put("gid", URLCons.getAppId(context));
        parames.put(URLCons.TIME, this.currentTime);
        parames.put(URLCons.FROM, URLCons.FROM_DEVICE_TYPE_ANDROID);
        parames.put(URLCons.DEVICE, URLCons.getDeviceModel());
        parames.put(URLCons.SIGN, getFindUserSign(context));
        parames.put(URLCons.DEVICE_UNIQUE_KEY, URLCons.getRKId(context));
        parames.put(URLCons.PUSH_AUTH, Config.push_auth);
        parames.put(URLCons.DEVICE_ID, Config.regId);
        parames.put(URLCons.CHANNEL, Config.getConfig().getChannel(context));
        return parames;
    }
}
