package com.tencent.bugly.proguard;

import java.util.HashMap;
import java.util.Map;

/* compiled from: BUGLY */
public final class am extends j {
    private static byte[] i;
    private static Map<String, String> j = new HashMap();
    public byte a = (byte) 0;
    public int b = 0;
    public byte[] c = null;
    public String d = "";
    public long e = 0;
    public String f = "";
    public Map<String, String> g = null;
    private String h = "";

    public final void a(i iVar) {
        iVar.a(this.a, 0);
        iVar.a(this.b, 1);
        if (this.c != null) {
            iVar.a(this.c, 2);
        }
        if (this.d != null) {
            iVar.a(this.d, 3);
        }
        iVar.a(this.e, 4);
        if (this.h != null) {
            iVar.a(this.h, 5);
        }
        if (this.f != null) {
            iVar.a(this.f, 6);
        }
        if (this.g != null) {
            iVar.a(this.g, 7);
        }
    }

    static {
        byte[] bArr = new byte[1];
        i = bArr;
        bArr[0] = (byte) 0;
        j.put("", "");
    }

    public final void a(h hVar) {
        this.a = hVar.a(this.a, 0, true);
        this.b = hVar.a(this.b, 1, true);
        byte[] bArr = i;
        this.c = hVar.c(2, false);
        this.d = hVar.b(3, false);
        this.e = hVar.a(this.e, 4, false);
        this.h = hVar.b(5, false);
        this.f = hVar.b(6, false);
        this.g = (Map) hVar.a(j, 7, false);
    }
}
