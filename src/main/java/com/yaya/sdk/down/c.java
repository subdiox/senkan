package com.yaya.sdk.down;

import android.content.Context;
import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.james.mime4j.util.MimeUtil;

public class c {
    private static final String a = "FileDownloader";
    private Context b;
    private int c = 0;
    private int d = 0;
    private b[] e;
    private File f;
    private Map<Integer, Integer> g = new ConcurrentHashMap();
    private int h;
    private String i;

    public int a() {
        return this.e.length;
    }

    public int b() {
        return this.d;
    }

    protected synchronized void a(int i) {
        this.c += i;
    }

    protected synchronized void a(int i, int i2) {
        this.g.put(Integer.valueOf(i), Integer.valueOf(i2));
    }

    public c(Context context, String str, String str2, String str3, int i) {
        int i2 = 0;
        try {
            this.b = context;
            this.i = str;
            URL url = new URL(this.i);
            File file = new File(str3);
            if (!file.exists()) {
                file.mkdirs();
            }
            this.e = new b[i];
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Accept", "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
            httpURLConnection.setRequestProperty("Accept-Language", "zh-CN");
            httpURLConnection.setRequestProperty("Referer", str);
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == 200) {
                this.d = httpURLConnection.getContentLength();
                if (this.d <= 0) {
                    throw new RuntimeException("Unkown file size ");
                }
                this.f = new File(file.getAbsolutePath().toString(), str2);
                if (this.g.size() == this.e.length) {
                    while (i2 < this.e.length) {
                        this.c = ((Integer) this.g.get(Integer.valueOf(i2 + 1))).intValue() + this.c;
                        i2++;
                    }
                    a("已经下载的长度" + this.c);
                }
                this.h = this.d % this.e.length == 0 ? this.d / this.e.length : (this.d / this.e.length) + 1;
                return;
            }
            throw new RuntimeException("server no response ");
        } catch (Exception e) {
            a(e.toString());
            throw new RuntimeException("don't connection this url");
        }
    }

    private String c(HttpURLConnection httpURLConnection) {
        String substring = this.i.substring(this.i.lastIndexOf(47) + 1);
        if (substring != null && !"".equals(substring.trim())) {
            return substring;
        }
        int i = 0;
        while (true) {
            String headerField = httpURLConnection.getHeaderField(i);
            if (headerField == null) {
                return UUID.randomUUID() + ".tmp";
            }
            if (MimeUtil.MIME_HEADER_CONTENT_DISPOSITION.equals(httpURLConnection.getHeaderFieldKey(i).toLowerCase())) {
                Matcher matcher = Pattern.compile(".*filename=(.*)").matcher(headerField.toLowerCase());
                if (matcher.find()) {
                    return matcher.group(1);
                }
            }
            i++;
        }
    }

    public int a(a aVar) throws Exception {
        int i;
        RandomAccessFile randomAccessFile = new RandomAccessFile(this.f, "rw");
        if (this.d > 0) {
            randomAccessFile.setLength((long) this.d);
        }
        randomAccessFile.close();
        URL url = new URL(this.i);
        if (this.g.size() != this.e.length) {
            this.g.clear();
            int i2 = 0;
            while (i2 < this.e.length) {
                try {
                    this.g.put(Integer.valueOf(i2 + 1), Integer.valueOf(0));
                    i2++;
                } catch (Exception e) {
                    a(e.toString());
                    throw new Exception("file download fail");
                }
            }
        }
        for (i = 0; i < this.e.length; i++) {
            if (((Integer) this.g.get(Integer.valueOf(i + 1))).intValue() >= this.h || this.c >= this.d) {
                this.e[i] = null;
            } else {
                this.e[i] = new b(this, url, this.f, this.h, ((Integer) this.g.get(Integer.valueOf(i + 1))).intValue(), i + 1);
                this.e[i].setPriority(7);
                this.e[i].start();
            }
        }
        Object obj = 1;
        while (obj != null) {
            Thread.sleep(900);
            i = 0;
            obj = null;
            while (i < this.e.length) {
                if (!(this.e[i] == null || this.e[i].a())) {
                    if (this.e[i].b() == -1) {
                        this.e[i] = new b(this, url, this.f, this.h, ((Integer) this.g.get(Integer.valueOf(i + 1))).intValue(), i + 1);
                        this.e[i].setPriority(7);
                        this.e[i].start();
                    }
                    obj = 1;
                }
                i++;
            }
            if (aVar != null) {
                aVar.a(this.c);
            }
        }
        return this.c;
    }

    public static Map<String, String> a(HttpURLConnection httpURLConnection) {
        Map<String, String> linkedHashMap = new LinkedHashMap();
        int i = 0;
        while (true) {
            String headerField = httpURLConnection.getHeaderField(i);
            if (headerField == null) {
                return linkedHashMap;
            }
            linkedHashMap.put(httpURLConnection.getHeaderFieldKey(i), headerField);
            i++;
        }
    }

    public static void b(HttpURLConnection httpURLConnection) {
        for (Entry entry : a(httpURLConnection).entrySet()) {
            a("HttpHead:" + (entry.getKey() != null ? new StringBuilder(String.valueOf((String) entry.getKey())).append(":").toString() : "") + ((String) entry.getValue()));
        }
    }

    private static void a(String str) {
    }
}
