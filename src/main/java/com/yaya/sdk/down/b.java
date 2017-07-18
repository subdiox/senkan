package com.yaya.sdk.down;

import android.util.Log;
import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class b extends Thread {
    private static final String a = "DownloadThread";
    private File b;
    private URL c;
    private int d;
    private int e = -1;
    private int f;
    private boolean g = false;
    private c h;

    public b(c cVar, URL url, File file, int i, int i2, int i3) {
        this.c = url;
        this.b = file;
        this.d = i;
        this.h = cVar;
        this.e = i3;
        this.f = i2;
    }

    public void run() {
        if (this.f < this.d) {
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) this.c.openConnection();
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setRequestProperty("Accept", "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
                httpURLConnection.setRequestProperty("Accept-Language", "zh-CN");
                httpURLConnection.setRequestProperty("Referer", this.c.toString());
                httpURLConnection.setRequestProperty("Charset", "UTF-8");
                int i = (this.d * (this.e - 1)) + this.f;
                httpURLConnection.setRequestProperty("Range", "bytes=" + i + "-" + ((this.d * this.e) - 1));
                httpURLConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
                httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
                InputStream inputStream = httpURLConnection.getInputStream();
                byte[] bArr = new byte[512000];
                a("Thread " + this.e + " start download from position " + i);
                RandomAccessFile randomAccessFile = new RandomAccessFile(this.b, "rwd");
                randomAccessFile.seek((long) i);
                while (true) {
                    i = inputStream.read(bArr, 0, 1024);
                    if (i == -1) {
                        randomAccessFile.close();
                        inputStream.close();
                        a("Thread " + this.e + " download finish");
                        this.g = true;
                        return;
                    }
                    randomAccessFile.write(bArr, 0, i);
                    this.f += i;
                    this.h.a(this.e, this.f);
                    this.h.a(i);
                }
            } catch (Exception e) {
                this.f = -1;
                a("Thread " + this.e + ":" + e);
            }
        }
    }

    private static void a(String str) {
        Log.i(a, str);
    }

    public boolean a() {
        return this.g;
    }

    public long b() {
        return (long) this.f;
    }
}
