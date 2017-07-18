package cn.sharesdk.framework.b.b;

public class a extends c {
    private static int c;
    private static long d;
    public int a;
    public String b;

    protected String a() {
        return "[API]";
    }

    protected void a(long j) {
        d = j;
    }

    protected int b() {
        return 5000;
    }

    protected int c() {
        return 50;
    }

    protected long d() {
        return (long) c;
    }

    protected long e() {
        return d;
    }

    protected void f() {
        c++;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(super.toString());
        stringBuilder.append('|').append(this.a);
        stringBuilder.append('|').append(this.b);
        return stringBuilder.toString();
    }
}
