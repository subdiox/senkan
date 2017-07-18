package com.kayac.lobi.libnakamap.rec.b;

import android.net.Uri;
import com.kayac.lobi.libnakamap.rec.c.am;
import com.kayac.lobi.libnakamap.utils.AdUtil;
import com.kayac.lobi.libnakamap.utils.DebugAssert;
import java.util.Map;
import java.util.Map.Entry;

public class d {
    private static final Map<String, Integer> a = new e();
    private int b;
    private Map<String, String> c;

    public static final d a(String str) {
        Uri parse = Uri.parse(str);
        d dVar = new d();
        dVar.b = 0;
        String scheme = parse.getScheme();
        String host = parse.getHost();
        if (AdUtil.SCHEMA.equals(scheme)) {
            for (Entry entry : a.entrySet()) {
                if (((String) entry.getKey()).equals(host)) {
                    dVar.b = ((Integer) entry.getValue()).intValue();
                    break;
                }
            }
            switch (dVar.b) {
                case 1:
                case 2:
                case 3:
                case 4:
                    dVar.c = am.a(parse);
                    break;
            }
        }
        return dVar;
    }

    public int a() {
        return this.b;
    }

    public Map<String, String> b() {
        DebugAssert.assertNotNull(this.c);
        return this.c;
    }
}
