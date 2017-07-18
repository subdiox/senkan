package com.kayac.lobi.sdk.rec.activity;

import com.google.android.gms.common.Scopes;
import com.kayac.lobi.libnakamap.net.APIDef.GetRanking;
import com.kayac.lobi.libnakamap.net.APIUtil.Thanks;
import java.util.HashMap;

final class c extends HashMap<String, String> {
    c() {
        put(Scopes.PROFILE, String.format("https://%s/video/profile", new Object[]{new Thanks().getAuthority()}));
        put("list", String.format("https://%s/videos", new Object[]{new Thanks().getAuthority()}));
        put("video", String.format("https://%s/video/", new Object[]{new Thanks().getAuthority()}));
        put(GetRanking.ORIGIN_TOP, String.format("https://%s/videos/top", new Object[]{new Thanks().getAuthority()}));
        put("help", String.format("https://%s/video/faq.html", new Object[]{new Thanks().getAuthority()}));
    }
}
