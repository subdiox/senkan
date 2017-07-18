package com.kayac.lobi.libnakamap.rec.recorder.a;

import com.kayac.lobi.libnakamap.rec.a.b;
import com.kayac.lobi.libnakamap.rec.recorder.MicInput;

public class a {
    public static final int a;
    public static int b = MicInput.SAMPLE_RATE;
    public static int c = 1;
    public static int d = 65536;
    private static final String e = a.class.getSimpleName();
    private static final b f = new b(e);
    private boolean g = false;
    private double h = 1.8d;
    private double i = 0.8d;

    static {
        if (com.kayac.lobi.libnakamap.rec.a.a) {
            a = 2;
        } else {
            a = -1;
        }
    }

    public static void a(int i, int i2) {
        if (i > 0) {
            b = i;
        }
        if (i2 > 0) {
            c = i2;
        }
        f.a("audio format: sample rate:" + b + " channel count:" + c);
    }

    public double a() {
        return this.h;
    }

    public void a(double d) {
        f.b("mic volume:" + this.h + " -> " + d);
        this.h = d;
    }

    public void a(boolean z) {
        f.b("mic input:" + this.g + " -> " + z);
        this.g = z;
    }

    public double b() {
        return this.i;
    }

    public void b(double d) {
        f.b("game sound volume:" + this.i + " -> " + d);
        this.i = d;
    }

    public boolean c() {
        return this.g;
    }
}
