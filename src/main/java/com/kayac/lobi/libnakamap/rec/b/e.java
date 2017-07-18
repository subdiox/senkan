package com.kayac.lobi.libnakamap.rec.b;

import com.kayac.lobi.libnakamap.value.NotificationValue;
import java.util.HashMap;

final class e extends HashMap<String, Integer> {
    e() {
        put("accuse_video", Integer.valueOf(1));
        put("share_video", Integer.valueOf(2));
        put(NotificationValue.SHEME_AUTHORITY_LOGIN, Integer.valueOf(3));
        put("open_lobi", Integer.valueOf(4));
    }
}
