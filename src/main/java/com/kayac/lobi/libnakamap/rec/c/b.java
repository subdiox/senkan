package com.kayac.lobi.libnakamap.rec.c;

import java.util.HashMap;
import java.util.Locale;

final class b extends HashMap<String, String> {
    b() {
        put("lang", Locale.getDefault().getLanguage());
        put("error_flavor", "json2");
    }
}
