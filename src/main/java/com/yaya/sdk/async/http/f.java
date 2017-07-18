package com.yaya.sdk.async.http;

import java.util.concurrent.Future;

public class f {
    private final Future<?> a;

    public f(Future<?> future) {
        this.a = future;
    }

    public boolean a(boolean z) {
        return this.a != null && this.a.cancel(z);
    }

    public boolean a() {
        return this.a == null || this.a.isDone();
    }

    public boolean b() {
        return this.a != null && this.a.isCancelled();
    }
}
