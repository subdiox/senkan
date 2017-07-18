package com.yaya.sdk.async.http;

import android.content.Context;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;

public class d extends c {
    static final /* synthetic */ boolean i = (!d.class.desiredAssertionStatus());
    private static final String k = "FileAsyncHttpResponseHandler";
    private File j;

    public d(File file) {
        if (i || file != null) {
            this.j = file;
            return;
        }
        throw new AssertionError();
    }

    public d(Context context) {
        if (i || context != null) {
            this.j = a(context);
            return;
        }
        throw new AssertionError();
    }

    protected File a(Context context) {
        try {
            return File.createTempFile("temp_", "_handled", context.getCacheDir());
        } catch (Throwable th) {
            if (a.a) {
                Log.e(k, "Cannot create temporary file", th);
            }
            return null;
        }
    }

    protected File k() {
        if (i || this.j != null) {
            return this.j;
        }
        throw new AssertionError();
    }

    public void onSuccess(File file) {
    }

    public void a(int i, File file) {
        onSuccess(file);
    }

    public void a(int i, Header[] headerArr, File file) {
        a(i, file);
    }

    public void a(Throwable th, File file) {
        onFailure(th);
    }

    public void a(int i, Throwable th, File file) {
        a(th, file);
    }

    public void a(int i, Header[] headerArr, Throwable th, File file) {
        a(i, th, file);
    }

    public void a(int i, Header[] headerArr, byte[] bArr, Throwable th) {
        a(i, headerArr, th, k());
    }

    public void a(int i, Header[] headerArr, byte[] bArr) {
        a(i, headerArr, k());
    }

    byte[] a(HttpEntity httpEntity) throws IOException {
        int i = 0;
        Log.d(k, "entry: " + httpEntity);
        if (httpEntity != null) {
            InputStream content = httpEntity.getContent();
            long contentLength = httpEntity.getContentLength();
            FileOutputStream fileOutputStream = new FileOutputStream(k());
            if (content != null) {
                try {
                    byte[] bArr = new byte[4096];
                    while (true) {
                        int read = content.read(bArr);
                        if (read == -1 || Thread.currentThread().isInterrupted()) {
                            content.close();
                            fileOutputStream.flush();
                            fileOutputStream.close();
                        } else {
                            i += read;
                            fileOutputStream.write(bArr, 0, read);
                            b(i, (int) contentLength);
                        }
                    }
                    content.close();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (Throwable th) {
                    content.close();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
            }
        }
        return null;
    }
}
