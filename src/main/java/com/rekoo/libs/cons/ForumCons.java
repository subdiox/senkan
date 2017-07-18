package com.rekoo.libs.cons;

import android.content.Context;
import com.rekoo.libs.net.NetRequest;
import com.rekoo.libs.net.URLCons;
import com.squareup.okhttp.RequestBody;
import java.util.HashMap;
import java.util.Map;

public class ForumCons {
    private static ForumCons forumCons = null;

    private ForumCons() {
    }

    public static ForumCons getCons() {
        if (forumCons == null) {
            synchronized (ForumCons.class) {
                if (forumCons == null) {
                    forumCons = new ForumCons();
                }
            }
        }
        return forumCons;
    }

    public RequestBody getForumReqyestBody(Context context) {
        return NetRequest.getRequest().getRequestBody(context, getForumParames(context));
    }

    private Map<String, String> getForumParames(Context context) {
        Map<String, String> parames = new HashMap();
        parames.put("gid", URLCons.getAppId(context));
        return parames;
    }
}
