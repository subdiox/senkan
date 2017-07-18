package com.rekoo.libs.cons;

import android.content.Context;
import com.rekoo.libs.net.NetRequest;
import com.rekoo.libs.net.URLCons;
import com.squareup.okhttp.RequestBody;
import java.util.HashMap;
import java.util.Map;

public class FeedbackCons {
    private static FeedbackCons feedbackCons = null;

    private FeedbackCons() {
    }

    public static FeedbackCons getCons() {
        if (feedbackCons == null) {
            synchronized (FeedbackCons.class) {
                if (feedbackCons == null) {
                    feedbackCons = new FeedbackCons();
                }
            }
        }
        return feedbackCons;
    }

    public RequestBody getSubmitInfoRequestBody(Context context, String category, String content, String uid, String token) {
        return NetRequest.getRequest().getRequestBody(context, getFeedbackParams(context, category, content, uid, token));
    }

    public Map<String, String> getFeedbackParams(Context context, String category, String content, String uid, String token) {
        Map<String, String> params = new HashMap();
        params.put("token", token);
        params.put("uid", uid);
        params.put("gid", URLCons.getAppId(context));
        params.put(URLCons.VERSION, URLCons.VERSION_NUM);
        params.put("category", category);
        params.put(URLCons.CONTENT, content);
        params.put(URLCons.DEVICE_UNIQUE_KEY, URLCons.getRKId(context));
        params.put(URLCons.PHONETYPE, URLCons.FROM_DEVICE_TYPE_ANDROID);
        return params;
    }
}
