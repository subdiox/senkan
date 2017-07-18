package com.yaya.sdk.async.http;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.message.BasicHeader;

class j implements HttpEntity {
    private static final String a = "SimpleMultipartEntity";
    private static final String b = "application/octet-stream";
    private static final byte[] c = "\r\n".getBytes();
    private static final byte[] d = "Content-Transfer-Encoding: binary\r\n".getBytes();
    private static final char[] e = "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private String f;
    private byte[] g;
    private byte[] h;
    private boolean i = false;
    private List<a> j = new ArrayList();
    private ByteArrayOutputStream k = new ByteArrayOutputStream();
    private h l;
    private int m;
    private int n;

    private class a {
        public File a;
        public byte[] b;
        final /* synthetic */ j c;

        public a(j jVar, String str, File file, String str2) {
            this.c = jVar;
            this.b = a(str, file.getName(), str2);
            this.a = file;
        }

        private byte[] a(String str, String str2, String str3) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                byteArrayOutputStream.write(this.c.g);
                byteArrayOutputStream.write(this.c.b(str, str2));
                byteArrayOutputStream.write(this.c.a(str3));
                byteArrayOutputStream.write(j.d);
                byteArrayOutputStream.write(j.c);
            } catch (IOException e) {
                boolean z = a.a;
            }
            return byteArrayOutputStream.toByteArray();
        }

        public long a() {
            return this.a.length() + ((long) this.b.length);
        }

        public void a(OutputStream outputStream) throws IOException {
            outputStream.write(this.b);
            this.c.a(this.b.length);
            FileInputStream fileInputStream = new FileInputStream(this.a);
            byte[] bArr = new byte[4096];
            while (true) {
                int read = fileInputStream.read(bArr);
                if (read == -1) {
                    outputStream.write(j.c);
                    this.c.a(j.c.length);
                    outputStream.flush();
                    try {
                        fileInputStream.close();
                        return;
                    } catch (IOException e) {
                        boolean z = a.a;
                        return;
                    }
                }
                outputStream.write(bArr, 0, read);
                this.c.a(read);
            }
        }
    }

    public j(h hVar) {
        int i = 0;
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        while (i < 30) {
            stringBuilder.append(e[random.nextInt(e.length)]);
            i++;
        }
        this.f = stringBuilder.toString();
        this.g = ("--" + this.f + "\r\n").getBytes();
        this.h = ("--" + this.f + "--\r\n").getBytes();
        this.l = hVar;
    }

    public void a(String str, String str2, String str3) {
        try {
            this.k.write(this.g);
            this.k.write(b(str));
            this.k.write(a(str3));
            this.k.write(c);
            this.k.write(str2.getBytes());
            this.k.write(c);
        } catch (IOException e) {
            boolean z = a.a;
        }
    }

    public void a(String str, String str2) {
        a(str, str2, "text/plain; charset=UTF-8");
    }

    public void a(String str, File file) {
        a(str, file, null);
    }

    public void a(String str, File file, String str2) {
        if (str2 == null) {
            str2 = b;
        }
        this.j.add(new a(this, str, file, str2));
    }

    public void a(String str, String str2, InputStream inputStream, String str3) throws IOException {
        if (str3 == null) {
            str3 = b;
        }
        this.k.write(this.g);
        this.k.write(b(str, str2));
        this.k.write(a(str3));
        this.k.write(d);
        this.k.write(c);
        byte[] bArr = new byte[4096];
        while (true) {
            int read = inputStream.read(bArr);
            if (read == -1) {
                this.k.write(c);
                this.k.flush();
                try {
                    inputStream.close();
                    return;
                } catch (IOException e) {
                    boolean z = a.a;
                    return;
                }
            }
            this.k.write(bArr, 0, read);
        }
    }

    private byte[] a(String str) {
        return ("Content-Type: " + str + "\r\n").getBytes();
    }

    private byte[] b(String str) {
        return ("Content-Disposition: form-data; name=\"" + str + "\"\r\n").getBytes();
    }

    private byte[] b(String str, String str2) {
        return ("Content-Disposition: form-data; name=\"" + str + "\"; filename=\"" + str2 + "\"\r\n").getBytes();
    }

    private void a(int i) {
        this.m += i;
        this.l.b(this.m, this.n);
    }

    public long getContentLength() {
        long size = (long) this.k.size();
        long j = size;
        for (a a : this.j) {
            size = a.a();
            if (size < 0) {
                return -1;
            }
            j = size + j;
        }
        return ((long) this.h.length) + j;
    }

    public Header getContentType() {
        return new BasicHeader("Content-Type", "multipart/form-data; boundary=" + this.f);
    }

    public boolean isChunked() {
        return false;
    }

    public void a(boolean z) {
        this.i = z;
    }

    public boolean isRepeatable() {
        return this.i;
    }

    public boolean isStreaming() {
        return false;
    }

    public void writeTo(OutputStream outstream) throws IOException {
        this.m = 0;
        this.n = (int) getContentLength();
        this.k.writeTo(outstream);
        a(this.k.size());
        for (a a : this.j) {
            a.a(outstream);
        }
        outstream.write(this.h);
        a(this.h.length);
    }

    public Header getContentEncoding() {
        return null;
    }

    public void consumeContent() throws IOException, UnsupportedOperationException {
        if (isStreaming()) {
            throw new UnsupportedOperationException("Streaming entity does not implement #consumeContent()");
        }
    }

    public InputStream getContent() throws IOException, UnsupportedOperationException {
        throw new UnsupportedOperationException("getContent() is not supported. Use writeTo() instead.");
    }
}
