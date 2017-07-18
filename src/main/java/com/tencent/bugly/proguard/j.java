package com.tencent.bugly.proguard;

import java.io.Serializable;

/* compiled from: BUGLY */
public abstract class j implements Serializable {
    public abstract void a(h hVar);

    public abstract void a(i iVar);

    public abstract void a(StringBuilder stringBuilder, int i);

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        a(stringBuilder, 0);
        return stringBuilder.toString();
    }
}
