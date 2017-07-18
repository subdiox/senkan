package com.rekoo.libs.cons;

import android.content.Context;
import com.rekoo.libs.config.Config;
import com.rekoo.libs.net.NetRequest;
import com.rekoo.libs.net.URLCons;
import com.squareup.okhttp.RequestBody;
import java.util.HashMap;
import java.util.Map;

public class TransformCons {
    private static TransformCons transformCons = null;

    private TransformCons() {
    }

    public static TransformCons getCons() {
        if (transformCons == null) {
            synchronized (TransformCons.class) {
                if (transformCons == null) {
                    transformCons = new TransformCons();
                }
            }
        }
        return transformCons;
    }

    public RequestBody getTransformRequestBody(Context context, String uid) {
        return NetRequest.getRequest().getRequestBody(context, getTransformParams(uid));
    }

    public Map<String, String> getTransformParams(String uid) {
        Map<String, String> params = new HashMap();
        params.put("uid", uid);
        return params;
    }

    public RequestBody setTransformRequestBody(Context context, String transform) {
        return NetRequest.getRequest().getRequestBody(context, setTransformParams(transform));
    }

    public Map<String, String> setTransformParams(String transform) {
        Map<String, String> params = new HashMap();
        params.put("uid", Config.uid);
        params.put(URLCons.TRANSID, transform);
        return params;
    }
}
