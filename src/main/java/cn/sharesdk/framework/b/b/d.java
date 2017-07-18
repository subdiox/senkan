package cn.sharesdk.framework.b.b;

public class d extends c {
    private static int d;
    private static long n;
    public String a;
    public int b;
    public String c = "";

    protected String a() {
        return "[EVT]";
    }

    protected void a(long j) {
        n = j;
    }

    protected int b() {
        return 5000;
    }

    protected int c() {
        return 30;
    }

    protected long d() {
        return (long) d;
    }

    protected long e() {
        return n;
    }

    protected void f() {
        d++;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(super.toString());
        stringBuilder.append('|').append(this.a);
        stringBuilder.append('|').append(this.b);
        stringBuilder.append('|').append(this.c);
        return stringBuilder.toString();
    }
}
