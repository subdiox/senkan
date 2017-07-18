package com.kayac.lobi.libnakamap.rec.a;

public class c {
    private static long a = 0;
    private static b b = new b(c.class.getSimpleName());

    public static void a() {
        a = System.nanoTime();
    }

    public static void a(String str, String str2) {
        long nanoTime = System.nanoTime();
        long j = nanoTime - a;
        a = nanoTime;
        b.b(str + "| " + str2 + " " + j + "ns");
    }
}
