package com.rekoo.libs.cons;

import android.content.Context;
import com.rekoo.libs.net.NetRequest;
import com.squareup.okhttp.RequestBody;
import java.util.HashMap;
import java.util.Map;

public class FeedbackListCons {
    private static FeedbackListCons feedbackListCons = null;
    private Context act;

    private FeedbackListCons(Context context) {
        this.act = context;
    }

    public static FeedbackListCons getCons(Context context) {
        if (feedbackListCons == null) {
            synchronized (FeedbackCons.class) {
                if (feedbackListCons == null) {
                    feedbackListCons = new FeedbackListCons(context);
                }
            }
        }
        return feedbackListCons;
    }

    private Map<String, String> getFeedbackListCode(Context context, String uid) {
        Map<String, String> parames = new HashMap();
        parames.put("uid", uid);
        return parames;
    }

    public RequestBody getFeedbackListBody(Context context, String uid) {
        return NetRequest.getRequest().getRequestBody(context, getFeedbackListCode(context, uid));
    }
}
