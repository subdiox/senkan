package com.tencent.bugly.proguard;

import java.util.ArrayList;

/* compiled from: BUGLY */
public final class ak extends j implements Cloneable {
    private static ArrayList<aj> b;
    public ArrayList<aj> a = null;

    public final void a(i iVar) {
        iVar.a(this.a, 0);
    }

    public final void a(h hVar) {
        if (b == null) {
            b = new ArrayList();
            b.add(new aj());
        }
        this.a = (ArrayList) hVar.a(b, 0, true);
    }

    public final void a(StringBuilder stringBuilder, int i) {
    }
}
