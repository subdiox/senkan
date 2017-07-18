package com.yaya.sdk.async.http;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

public class g {
    protected boolean a;
    protected ConcurrentHashMap<String, String> b;
    protected ConcurrentHashMap<String, b> c;
    protected ConcurrentHashMap<String, a> d;
    protected ConcurrentHashMap<String, Object> e;

    class AnonymousClass1 extends HashMap<String, String> {
        AnonymousClass1(String str, String str2) {
            put(str, str2);
        }
    }

    private static class a {
        public File a;
        public String b;

        public a(File file, String str) {
            this.a = file;
            this.b = str;
        }
    }

    private static class b {
        public InputStream a;
        public String b;
        public String c;

        public b(InputStream inputStream, String str, String str2) {
            this.a = inputStream;
            this.b = str;
            this.c = str2;
        }
    }

    public g() {
        this(null);
    }

    public g(Map<String, String> map) {
        this.a = false;
        d();
        if (map != null) {
            for (Entry entry : map.entrySet()) {
                a((String) entry.getKey(), (String) entry.getValue());
            }
        }
    }

    public g(String str, String str2) {
        this(new AnonymousClass1(str, str2));
    }

    public g(Object... objArr) {
        int i = 0;
        this.a = false;
        d();
        int length = objArr.length;
        if (length % 2 != 0) {
            throw new IllegalArgumentException("Supplied arguments must be even");
        }
        while (i < length) {
            a(String.valueOf(objArr[i]), String.valueOf(objArr[i + 1]));
            i += 2;
        }
    }

    public void a(String str, String str2) {
        if (str != null && str2 != null) {
            this.b.put(str, str2);
        }
    }

    public void a(String str, File file) throws FileNotFoundException {
        a(str, file, null);
    }

    public void a(String str, File file, String str2) throws FileNotFoundException {
        if (str != null && file != null) {
            this.d.put(str, new a(file, str2));
        }
    }

    public void a(String str, InputStream inputStream) {
        a(str, inputStream, null);
    }

    public void a(String str, InputStream inputStream, String str2) {
        a(str, inputStream, str2, null);
    }

    public void a(String str, InputStream inputStream, String str2, String str3) {
        if (str != null && inputStream != null) {
            this.c.put(str, new b(inputStream, str2, str3));
        }
    }

    public void a(String str, Object obj) {
        if (str != null && obj != null) {
            this.e.put(str, obj);
        }
    }

    public void b(String str, String str2) {
        if (str != null && str2 != null) {
            Object obj = this.e.get(str);
            if (obj == null) {
                obj = new HashSet();
                a(str, obj);
            }
            if (obj instanceof List) {
                ((List) obj).add(str2);
            } else if (obj instanceof Set) {
                ((Set) obj).add(str2);
            }
        }
    }

    public void a(String str) {
        this.b.remove(str);
        this.c.remove(str);
        this.d.remove(str);
        this.e.remove(str);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Entry entry : this.b.entrySet()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append("&");
            }
            stringBuilder.append((String) entry.getKey());
            stringBuilder.append("=");
            stringBuilder.append((String) entry.getValue());
        }
        for (Entry entry2 : this.c.entrySet()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append("&");
            }
            stringBuilder.append((String) entry2.getKey());
            stringBuilder.append("=");
            stringBuilder.append("STREAM");
        }
        for (Entry entry22 : this.d.entrySet()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append("&");
            }
            stringBuilder.append((String) entry22.getKey());
            stringBuilder.append("=");
            stringBuilder.append("FILE");
        }
        for (BasicNameValuePair basicNameValuePair : b(null, this.e)) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append("&");
            }
            stringBuilder.append(basicNameValuePair.getName());
            stringBuilder.append("=");
            stringBuilder.append(basicNameValuePair.getValue());
        }
        return stringBuilder.toString();
    }

    public void a(boolean z) {
        this.a = z;
    }

    public HttpEntity a(h hVar) throws IOException {
        if (this.c.isEmpty() && this.d.isEmpty()) {
            return c();
        }
        return b(hVar);
    }

    private HttpEntity c() {
        try {
            return new UrlEncodedFormEntity(a(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            boolean z = a.a;
            return null;
        }
    }

    private HttpEntity b(h hVar) throws IOException {
        HttpEntity jVar = new j(hVar);
        jVar.a(this.a);
        for (Entry entry : this.b.entrySet()) {
            jVar.a((String) entry.getKey(), (String) entry.getValue());
        }
        for (BasicNameValuePair basicNameValuePair : b(null, this.e)) {
            jVar.a(basicNameValuePair.getName(), basicNameValuePair.getValue());
        }
        for (Entry entry2 : this.c.entrySet()) {
            b bVar = (b) entry2.getValue();
            if (bVar.a != null) {
                jVar.a((String) entry2.getKey(), bVar.b, bVar.a, bVar.c);
            }
        }
        for (Entry entry22 : this.d.entrySet()) {
            a aVar = (a) entry22.getValue();
            jVar.a((String) entry22.getKey(), aVar.a, aVar.b);
        }
        return jVar;
    }

    private void d() {
        this.b = new ConcurrentHashMap();
        this.c = new ConcurrentHashMap();
        this.d = new ConcurrentHashMap();
        this.e = new ConcurrentHashMap();
    }

    protected List<BasicNameValuePair> a() {
        List<BasicNameValuePair> linkedList = new LinkedList();
        for (Entry entry : this.b.entrySet()) {
            linkedList.add(new BasicNameValuePair((String) entry.getKey(), (String) entry.getValue()));
        }
        linkedList.addAll(b(null, this.e));
        return linkedList;
    }

    private List<BasicNameValuePair> b(String str, Object obj) {
        List<BasicNameValuePair> linkedList = new LinkedList();
        Object obj2;
        if (obj instanceof Map) {
            Map map = (Map) obj;
            List<String> arrayList = new ArrayList(map.keySet());
            Collections.sort(arrayList);
            for (String str2 : arrayList) {
                String str22;
                obj2 = map.get(str22);
                if (obj2 != null) {
                    if (str != null) {
                        str22 = String.format("%s[%s]", new Object[]{str, str22});
                    }
                    linkedList.addAll(b(str22, obj2));
                }
            }
        } else if (obj instanceof List) {
            for (Object b : (List) obj) {
                linkedList.addAll(b(String.format("%s[]", new Object[]{str}), b));
            }
        } else if (obj instanceof Object[]) {
            for (Object obj22 : (Object[]) obj) {
                linkedList.addAll(b(String.format("%s[]", new Object[]{str}), obj22));
            }
        } else if (obj instanceof Set) {
            for (Object b2 : (Set) obj) {
                linkedList.addAll(b(str, b2));
            }
        } else if (obj instanceof String) {
            linkedList.add(new BasicNameValuePair(str, (String) obj));
        }
        return linkedList;
    }

    protected String b() {
        return URLEncodedUtils.format(a(), "UTF-8");
    }
}
