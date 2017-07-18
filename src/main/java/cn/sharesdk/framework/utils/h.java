package cn.sharesdk.framework.utils;

final class h extends ThreadLocal<char[]> {
    h() {
    }

    protected char[] a() {
        return new char[1024];
    }

    protected /* synthetic */ Object initialValue() {
        return a();
    }
}
