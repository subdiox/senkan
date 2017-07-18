package com.yaya.sdk.async.http;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.media.session.PlaybackStateCompat;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URI;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.util.ByteArrayBuffer;

public class c implements h {
    protected static final int a = 0;
    protected static final int b = 1;
    protected static final int c = 2;
    protected static final int d = 3;
    protected static final int e = 4;
    protected static final int f = 5;
    protected static final int g = 4096;
    public static final String h = "UTF-8";
    private static final String i = "AsyncHttpResponseHandler";
    private Handler j;
    private String k = "UTF-8";
    private Boolean l = Boolean.valueOf(false);
    private URI m = null;
    private Header[] n = null;

    static class a extends Handler {
        private final WeakReference<c> a;

        a(c cVar) {
            this.a = new WeakReference(cVar);
        }

        public void handleMessage(Message msg) {
            c cVar = (c) this.a.get();
            if (cVar != null) {
                cVar.a(msg);
            }
        }
    }

    public URI a() {
        return this.m;
    }

    public Header[] b() {
        return this.n;
    }

    public void a(URI uri) {
        this.m = uri;
    }

    public void a(Header[] headerArr) {
        this.n = headerArr;
    }

    public boolean c() {
        return this.l.booleanValue();
    }

    public void a(boolean z) {
        this.l = Boolean.valueOf(z);
    }

    public void a(String str) {
        this.k = str;
    }

    public String d() {
        return this.k == null ? "UTF-8" : this.k;
    }

    public c() {
        if (Looper.myLooper() != null) {
            this.j = new a(this);
        }
    }

    public void a(int i, int i2) {
    }

    public void e() {
    }

    public void f() {
    }

    @Deprecated
    public void onSuccess(String content) {
    }

    @Deprecated
    public void a(int i, Header[] headerArr, String str) {
        a(i, str);
    }

    @Deprecated
    public void a(int i, String str) {
        onSuccess(str);
    }

    public void a(int i, Header[] headerArr, byte[] bArr) {
        try {
            a(i, headerArr, bArr == null ? null : new String(bArr, d()));
        } catch (Throwable e) {
            boolean z = a.a;
            a(i, headerArr, e, null);
        }
    }

    @Deprecated
    public void onFailure(Throwable error) {
    }

    @Deprecated
    public void a(Throwable th, String str) {
        onFailure(th);
    }

    @Deprecated
    public void a(int i, Throwable th, String str) {
        a(th, str);
    }

    @Deprecated
    public void a(int i, Header[] headerArr, Throwable th, String str) {
        a(i, th, str);
    }

    public void a(int i, Header[] headerArr, byte[] bArr, Throwable th) {
        try {
            a(i, headerArr, th, bArr == null ? null : new String(bArr, d()));
        } catch (Throwable e) {
            boolean z = a.a;
            a(i, headerArr, e, null);
        }
    }

    public void g() {
    }

    public final void b(int i, int i2) {
        b(a(4, new Object[]{Integer.valueOf(i), Integer.valueOf(i2)}));
    }

    public final void b(int i, Header[] headerArr, byte[] bArr) {
        b(a(0, new Object[]{Integer.valueOf(i), headerArr, bArr}));
    }

    public final void b(int i, Header[] headerArr, byte[] bArr, Throwable th) {
        b(a(1, new Object[]{Integer.valueOf(i), headerArr, bArr, th}));
    }

    public final void h() {
        b(a(2, null));
    }

    public final void i() {
        b(a(3, null));
    }

    public final void j() {
        b(a(5, null));
    }

    protected void a(Message message) {
        boolean z;
        Object[] objArr;
        switch (message.what) {
            case 0:
                objArr = (Object[]) message.obj;
                if (objArr != null && objArr.length >= 3) {
                    a(((Integer) objArr[0]).intValue(), (Header[]) objArr[1], (byte[]) objArr[2]);
                    return;
                }
                return;
            case 1:
                objArr = (Object[]) message.obj;
                if (objArr != null && objArr.length >= 4) {
                    a(((Integer) objArr[0]).intValue(), (Header[]) objArr[1], (byte[]) objArr[2], (Throwable) objArr[3]);
                    return;
                }
                return;
            case 2:
                e();
                return;
            case 3:
                f();
                return;
            case 4:
                objArr = (Object[]) message.obj;
                if (objArr == null || objArr.length < 2) {
                    z = a.a;
                    return;
                }
                try {
                    a(((Integer) objArr[0]).intValue(), ((Integer) objArr[1]).intValue());
                    return;
                } catch (Throwable th) {
                    z = a.a;
                    return;
                }
            case 5:
                g();
                return;
            default:
                return;
        }
    }

    protected void b(Message message) {
        if (c() || this.j == null) {
            a(message);
        } else if (!Thread.currentThread().isInterrupted()) {
            this.j.sendMessage(message);
        }
    }

    protected void a(Runnable runnable) {
        if (runnable != null) {
            this.j.post(runnable);
        }
    }

    protected Message a(int i, Object obj) {
        if (this.j != null) {
            return this.j.obtainMessage(i, obj);
        }
        Message obtain = Message.obtain();
        if (obtain == null) {
            return obtain;
        }
        obtain.what = i;
        obtain.obj = obj;
        return obtain;
    }

    public void a(HttpResponse httpResponse) throws IOException {
        if (!Thread.currentThread().isInterrupted()) {
            StatusLine statusLine = httpResponse.getStatusLine();
            byte[] a = a(httpResponse.getEntity());
            if (!Thread.currentThread().isInterrupted()) {
                if (statusLine.getStatusCode() >= 300) {
                    b(statusLine.getStatusCode(), httpResponse.getAllHeaders(), a, new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase()));
                } else {
                    b(statusLine.getStatusCode(), httpResponse.getAllHeaders(), a);
                }
            }
        }
    }

    byte[] a(HttpEntity httpEntity) throws IOException {
        byte[] bArr = null;
        if (httpEntity != null) {
            InputStream content = httpEntity.getContent();
            if (content != null) {
                long contentLength = httpEntity.getContentLength();
                if (contentLength > 2147483647L) {
                    throw new IllegalArgumentException("HTTP entity too large to be buffered in memory");
                }
                if (contentLength < 0) {
                    contentLength = PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM;
                }
                try {
                    ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer((int) contentLength);
                    byte[] bArr2 = new byte[4096];
                    int i = 0;
                    while (true) {
                        int read = content.read(bArr2);
                        if (read == -1 || Thread.currentThread().isInterrupted()) {
                            break;
                        }
                        i += read;
                        byteArrayBuffer.append(bArr2, 0, read);
                        b(i, (int) contentLength);
                    }
                    content.close();
                    bArr = byteArrayBuffer.toByteArray();
                } catch (OutOfMemoryError e) {
                    System.gc();
                    throw new IOException("File too large to fit into available memory");
                } catch (Throwable th) {
                    content.close();
                }
            }
        }
        return bArr;
    }
}
