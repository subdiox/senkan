package com.kayac.lobi.libnakamap.rec.c;

import com.kayac.lobi.libnakamap.net.APIDef.PostOauthAccessToken.RequestKey;
import com.kayac.lobi.libnakamap.value.UserValue;
import java.util.HashMap;

final class af extends HashMap<String, String> {
    final /* synthetic */ UserValue a;

    af(UserValue userValue) {
        this.a = userValue;
        put("refresh_token", this.a.getToken());
        put(RequestKey.GRANT_TYPE, "refresh_token");
    }
}
