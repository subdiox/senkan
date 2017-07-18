package com.tencent.bugly.proguard;

import java.io.Serializable;

/* compiled from: BUGLY */
public final class l implements Serializable, Comparable<l> {
    public long a;
    public String b;
    public long c;
    public int d;
    public String e;
    public String f;
    public long g;

    public final /* bridge */ /* synthetic */ int compareTo(Object obj) {
        return (int) (this.c - ((l) obj).c);
    }
}
