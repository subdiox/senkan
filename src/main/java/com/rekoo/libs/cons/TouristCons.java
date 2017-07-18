package com.rekoo.libs.cons;

import android.content.Context;
import android.text.TextUtils;
import com.rekoo.libs.config.BIConfig;
import com.rekoo.libs.config.Config;
import com.rekoo.libs.net.NetRequest;
import com.rekoo.libs.net.URLCons;
import com.rekoo.libs.utils.MacAddressUtil;
import com.squareup.okhttp.RequestBody;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TouristCons {
    private static TouristCons touristCons = null;

    private TouristCons() {
    }

    public static TouristCons getCons() {
        if (touristCons == null) {
            synchronized (TouristCons.class) {
                if (touristCons == null) {
                    touristCons = new TouristCons();
                }
            }
        }
        return touristCons;
    }

    public RequestBody getTouristLoginRequestBody(Context context) {
        return NetRequest.getRequest().getRequestBody(context, getTouristParames(context));
    }

    private Map<String, String> getTouristParames(Context context) {
        Map<String, String> parames = new HashMap();
        parames.put(URLCons.TIME, System.currentTimeMillis());
        parames.put(URLCons.FROM, URLCons.FROM_DEVICE_TYPE_ANDROID);
        parames.put(URLCons.DEVICE, URLCons.getDeviceModel());
        parames.put(URLCons.VERSION, URLCons.VERSION_NUM);
        parames.put(URLCons.SIGN, getTouristSign(context));
        parames.put(URLCons.DEVICE_UNIQUE_KEY, URLCons.getRKId(context));
        parames.put(URLCons.DEVICE_ID, Config.regId);
        parames.put(URLCons.PUSH_AUTH, Config.push_auth);
        parames.put(URLCons.CHANNEL, Config.getConfig().getChannel(context));
        parames.put(URLCons.SYSVERSION, URLCons.VERSION_NUM);
        parames.put(URLCons.MAC, MacAddressUtil.getMacAddress(context));
        if (!TextUtils.isEmpty(BIConfig.getBiConfig().getDeviceId(context))) {
            parames.put(URLCons.IMEI, BIConfig.getBiConfig().getDeviceId(context));
        }
        if (!TextUtils.isEmpty(Config.gooleAdvertisingId)) {
            parames.put(URLCons.ADID, Config.gooleAdvertisingId);
        }
        return parames;
    }

    private String getTouristSign(Context context) {
        ArrayList<String> olds = new ArrayList();
        olds.add(URLCons.getAppId(context) + "_" + URLCons.SRC_CONTENT);
        Collections.sort(olds);
        String sig = "";
        for (int i = 0; i < olds.size() - 1; i++) {
            sig = new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(sig)).append((String) olds.get(i)).toString())).append("&").toString();
        }
        return new StringBuilder(String.valueOf(sig)).append((String) olds.get(olds.size() - 1)).toString();
    }
}
