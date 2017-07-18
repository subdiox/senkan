package com.rekoo.libs.cons;

import android.content.Context;
import com.rekoo.libs.net.NetRequest;
import com.rekoo.libs.net.URLCons;
import com.squareup.okhttp.RequestBody;
import java.util.HashMap;
import java.util.Map;

public class ReadCons {
    private static ReadCons readCons = null;

    private ReadCons() {
    }

    public static ReadCons getCons() {
        if (readCons == null) {
            synchronized (LoginCons.class) {
                if (readCons == null) {
                    readCons = new ReadCons();
                }
            }
        }
        return readCons;
    }

    public RequestBody getReadRequestBody(Context context, String uid, String sendno) {
        return NetRequest.getRequest().getRequestBody(context, getReadParames(context, uid, sendno));
    }

    public Map<String, String> getReadParames(Context context, String uid, String sendno) {
        Map<String, String> parames = new HashMap();
        parames.put("gid", URLCons.getAppId(context));
        parames.put("uid", uid);
        parames.put(URLCons.SENDNO, sendno);
        return parames;
    }
}
