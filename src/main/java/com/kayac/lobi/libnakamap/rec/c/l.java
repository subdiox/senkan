package com.kayac.lobi.libnakamap.rec.c;

import com.kayac.lobi.libnakamap.components.LoginEntranceDialog;
import com.kayac.lobi.libnakamap.net.APIDef.GetUserV3;
import com.kayac.lobi.libnakamap.net.APIDef.PostRankingScore.RequestKey;
import java.util.HashMap;

class l extends HashMap<String, String> {
    final /* synthetic */ k a;

    l(k kVar) {
        this.a = kVar;
        put("token", this.a.a);
        put("app", "0");
        put(LoginEntranceDialog.ARGUMENTS_TITLE, this.a.b);
        put("description", this.a.c);
        put(RequestKey.SCORE, String.valueOf(this.a.d));
        put("score_id", this.a.e);
        put("meta_data", this.a.f);
        put("total_length", String.valueOf(this.a.g));
        put("play_time", String.valueOf(this.a.h));
        put("has_camera", this.a.i ? "1" : "0");
        put("has_mic", this.a.j ? "1" : "0");
        put(GetUserV3.RequestKey.SECRET_MODE, this.a.k ? "1" : "0");
        put("facebook", this.a.l ? "1" : "0");
        put("twitter", this.a.m ? "1" : "0");
        put("youtube", this.a.n ? "1" : "0");
        put("nicovideo", this.a.o ? "1" : "0");
    }
}
