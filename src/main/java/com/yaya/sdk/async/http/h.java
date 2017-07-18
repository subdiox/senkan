package com.yaya.sdk.async.http;

import java.io.IOException;
import java.net.URI;
import org.apache.http.Header;
import org.apache.http.HttpResponse;

public interface h {
    URI a();

    void a(URI uri);

    void a(HttpResponse httpResponse) throws IOException;

    void a(boolean z);

    void a(Header[] headerArr);

    void b(int i, int i2);

    void b(int i, Header[] headerArr, byte[] bArr);

    void b(int i, Header[] headerArr, byte[] bArr, Throwable th);

    Header[] b();

    void h();

    void i();

    void j();
}
