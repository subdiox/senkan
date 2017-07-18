package com.kayac.lobi.libnakamap.net;

import com.kayac.lobi.libnakamap.net.CoreAPI.DefaultAPICallback;
import com.kayac.lobi.libnakamap.utils.Log;

public abstract class PagerLoader<T> {
    private final DefaultAPICallback<T> mApiCallback;
    private final DefaultAPICallback<T> mInternalApiCallback = new DefaultAPICallback<T>(null) {
        public void onResponse(T t) {
            synchronized (PagerLoader.this.mLock) {
                PagerLoader.this.mNextCursor = PagerLoader.this.getNextCursor(t);
                PagerLoader.this.onResponse(t);
            }
            clearLoadingFlag();
            PagerLoader.this.mApiCallback.onResponse(t);
        }

        public void clearLoadingFlag() {
            PagerLoader.this.setIsLoading(false);
        }

        public void onError(int statusCode, String responseBody) {
            clearLoadingFlag();
            PagerLoader.this.mApiCallback.onError(statusCode, responseBody);
        }

        public void onError(Throwable e) {
            clearLoadingFlag();
            PagerLoader.this.mApiCallback.onError(e);
        }
    };
    protected boolean mIsLoading;
    protected String mLoadingCursor = "!";
    protected final Object mLock = new Object();
    protected String mNextCursor = null;

    protected abstract String getNextCursor(T t);

    protected abstract void load();

    protected abstract boolean shouldLoadNext();

    public PagerLoader(DefaultAPICallback<T> callback) {
        this.mApiCallback = callback;
    }

    public boolean isLoading() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mIsLoading;
        }
        return z;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean loadNextPage() {
        /*
        r6 = this;
        r2 = 1;
        r1 = 0;
        r3 = r6.mLock;
        monitor-enter(r3);
        r0 = r6.mNextCursor;	 Catch:{ all -> 0x003a }
        r4 = r6.mLoadingCursor;	 Catch:{ all -> 0x003a }
        r4 = android.text.TextUtils.equals(r4, r0);	 Catch:{ all -> 0x003a }
        if (r4 == 0) goto L_0x0029;
    L_0x000f:
        r2 = "lobi-sdk";
        r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x003a }
        r4.<init>();	 Catch:{ all -> 0x003a }
        r5 = "[pagerLoader] cursor matches: ";
        r4 = r4.append(r5);	 Catch:{ all -> 0x003a }
        r4 = r4.append(r0);	 Catch:{ all -> 0x003a }
        r4 = r4.toString();	 Catch:{ all -> 0x003a }
        com.kayac.lobi.libnakamap.utils.Log.v(r2, r4);	 Catch:{ all -> 0x003a }
        monitor-exit(r3);	 Catch:{ all -> 0x003a }
    L_0x0028:
        return r1;
    L_0x0029:
        r6.mLoadingCursor = r0;	 Catch:{ all -> 0x003a }
        monitor-exit(r3);	 Catch:{ all -> 0x003a }
        r3 = r6.shouldLoadNext();
        if (r3 != 0) goto L_0x003d;
    L_0x0032:
        r2 = "lobi-sdk";
        r3 = "[pagerLoader] !shouldLoadNext()";
        com.kayac.lobi.libnakamap.utils.Log.v(r2, r3);
        goto L_0x0028;
    L_0x003a:
        r1 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x003a }
        throw r1;
    L_0x003d:
        r6.setIsLoading(r2);
        r6.load();
        r1 = r2;
        goto L_0x0028;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.kayac.lobi.libnakamap.net.PagerLoader.loadNextPage():boolean");
    }

    private void setIsLoading(boolean isLoading) {
        synchronized (this.mLock) {
            this.mIsLoading = isLoading;
            Log.v("lobi-sdk", "[pagerLoader] setIsLoading: " + this.mIsLoading);
        }
    }

    protected final DefaultAPICallback<T> getApiCallback() {
        return this.mInternalApiCallback;
    }

    protected void onResponse(T t) {
    }
}
