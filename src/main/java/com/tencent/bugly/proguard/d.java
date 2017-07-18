package com.tencent.bugly.proguard;

import java.nio.ByteBuffer;
import java.util.HashMap;

/* compiled from: BUGLY */
public final class d extends c {
    private static HashMap<String, byte[]> f = null;
    private static HashMap<String, HashMap<String, byte[]>> g = null;
    private f e = new f();

    public final <T> void a(String str, T t) {
        if (str.startsWith(".")) {
            throw new IllegalArgumentException("put name can not startwith . , now is " + str);
        }
        super.a(str, t);
    }

    public final void b() {
        super.b();
        this.e.a = (short) 3;
    }

    public final byte[] a() {
        if (this.e.a != (short) 2) {
            if (this.e.c == null) {
                this.e.c = "";
            }
            if (this.e.d == null) {
                this.e.d = "";
            }
        } else if (this.e.c.equals("")) {
            throw new IllegalArgumentException("servantName can not is null");
        } else if (this.e.d.equals("")) {
            throw new IllegalArgumentException("funcName can not is null");
        }
        i iVar = new i(0);
        iVar.a(this.b);
        if (this.e.a == (short) 2) {
            iVar.a(this.a, 0);
        } else {
            iVar.a(this.d, 0);
        }
        this.e.e = k.a(iVar.a());
        iVar = new i(0);
        iVar.a(this.b);
        this.e.a(iVar);
        byte[] a = k.a(iVar.a());
        int length = a.length;
        ByteBuffer allocate = ByteBuffer.allocate(length + 4);
        allocate.putInt(length + 4).put(a).flip();
        return allocate.array();
    }

    public final void a(byte[] bArr) {
        if (bArr.length < 4) {
            throw new IllegalArgumentException("decode package must include size head");
        }
        try {
            h hVar = new h(bArr, 4);
            hVar.a(this.b);
            this.e.a(hVar);
            HashMap hashMap;
            if (this.e.a == (short) 3) {
                hVar = new h(this.e.e);
                hVar.a(this.b);
                if (f == null) {
                    hashMap = new HashMap();
                    f = hashMap;
                    hashMap.put("", new byte[0]);
                }
                this.d = hVar.a(f, 0, false);
                return;
            }
            hVar = new h(this.e.e);
            hVar.a(this.b);
            if (g == null) {
                g = new HashMap();
                hashMap = new HashMap();
                hashMap.put("", new byte[0]);
                g.put("", hashMap);
            }
            this.a = hVar.a(g, 0, false);
            HashMap hashMap2 = new HashMap();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public final void b(String str) {
        this.e.c = str;
    }

    public final void c(String str) {
        this.e.d = str;
    }

    public final void b(int i) {
        this.e.b = 1;
    }
}
