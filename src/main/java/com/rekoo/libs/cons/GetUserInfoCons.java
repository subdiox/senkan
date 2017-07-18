package com.rekoo.libs.cons;

import android.content.Context;
import com.rekoo.libs.config.Config;
import com.rekoo.libs.net.NetRequest;
import com.rekoo.libs.net.URLCons;
import com.squareup.okhttp.RequestBody;
import java.util.HashMap;
import java.util.Map;

public class GetUserInfoCons {
    private static GetUserInfoCons codeCons = null;

    private GetUserInfoCons() {
    }

    public static GetUserInfoCons getCons() {
        if (codeCons == null) {
            synchronized (GetUserInfoCons.class) {
                if (codeCons == null) {
                    codeCons = new GetUserInfoCons();
                }
            }
        }
        return codeCons;
    }

    public RequestBody getUserInfoRequestBody(Context context, String uid, String token) {
        return NetRequest.getRequest().getRequestBody(context, getUserInfoParames(context, uid, token));
    }

    private Map<String, String> getUserInfoParames(Context context, String uid, String token) {
        Map<String, String> parames = new HashMap();
        parames.put("token", token);
        parames.put("uid", uid);
        parames.put("gid", URLCons.getAppId(context));
        parames.put(URLCons.TIME, System.currentTimeMillis());
        parames.putAll(Cons.getCommonParames(context));
        parames.put(URLCons.CHANNEL, Config.getConfig().getChannel(context));
        return parames;
    }
}
