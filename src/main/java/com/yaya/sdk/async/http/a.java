package com.yaya.sdk.async.http;

import android.content.Context;
import com.kayac.lobi.libnakamap.net.APIUtil.Endpoint;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.zip.GZIPInputStream;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultRedirectHandler;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.SyncBasicHttpContext;

public class a {
    public static boolean a = false;
    private static final String b = "1.4.4";
    private static final int c = 10;
    private static final int d = 10000;
    private static final int e = 5;
    private static final int f = 1500;
    private static final int g = 8192;
    private static final String h = "Accept-Encoding";
    private static final String i = "gzip";
    private static final String j = "AsyncHttpClient";
    private int k;
    private int l;
    private final DefaultHttpClient m;
    private final HttpContext n;
    private ThreadPoolExecutor o;
    private final Map<Context, List<WeakReference<Future<?>>>> p;
    private final Map<String, String> q;
    private boolean r;

    private static class a extends HttpEntityWrapper {
        public a(HttpEntity httpEntity) {
            super(httpEntity);
        }

        public InputStream getContent() throws IOException {
            return new GZIPInputStream(this.wrappedEntity.getContent());
        }

        public long getContentLength() {
            return -1;
        }
    }

    public a() {
        this(false, 80, Endpoint.HTTPS_PORT);
    }

    public a(int i) {
        this(false, i, Endpoint.HTTPS_PORT);
    }

    public a(int i, int i2) {
        this(false, i, i2);
    }

    public a(boolean z, int i, int i2) {
        this(a(z, i, i2));
    }

    private static SchemeRegistry a(boolean z, int i, int i2) {
        SocketFactory b;
        if (i < 1) {
            i = 80;
        }
        if (i2 < 1) {
            i2 = Endpoint.HTTPS_PORT;
        }
        if (z) {
            b = e.b();
        } else {
            b = SSLSocketFactory.getSocketFactory();
        }
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme(Endpoint.SCHEME_HTTP, PlainSocketFactory.getSocketFactory(), i));
        schemeRegistry.register(new Scheme("https", b, i2));
        return schemeRegistry;
    }

    public a(SchemeRegistry schemeRegistry) {
        this.k = 10;
        this.l = d;
        this.r = true;
        HttpParams basicHttpParams = new BasicHttpParams();
        ConnManagerParams.setTimeout(basicHttpParams, (long) this.l);
        ConnManagerParams.setMaxConnectionsPerRoute(basicHttpParams, new ConnPerRouteBean(this.k));
        ConnManagerParams.setMaxTotalConnections(basicHttpParams, 10);
        HttpConnectionParams.setSoTimeout(basicHttpParams, this.l);
        HttpConnectionParams.setConnectionTimeout(basicHttpParams, this.l);
        HttpConnectionParams.setTcpNoDelay(basicHttpParams, true);
        HttpConnectionParams.setSocketBufferSize(basicHttpParams, 8192);
        HttpProtocolParams.setVersion(basicHttpParams, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setUserAgent(basicHttpParams, String.format("android-async-http/%s (http://loopj.com/android-async-http)", new Object[]{b}));
        ClientConnectionManager threadSafeClientConnManager = new ThreadSafeClientConnManager(basicHttpParams, schemeRegistry);
        this.o = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        this.p = new WeakHashMap();
        this.q = new HashMap();
        this.n = new SyncBasicHttpContext(new BasicHttpContext());
        this.m = new DefaultHttpClient(threadSafeClientConnManager, basicHttpParams);
        this.m.addRequestInterceptor(new HttpRequestInterceptor(this) {
            final /* synthetic */ a a;

            {
                this.a = r1;
            }

            public void process(HttpRequest request, HttpContext context) {
                if (!request.containsHeader("Accept-Encoding")) {
                    request.addHeader("Accept-Encoding", "gzip");
                }
                for (String str : this.a.q.keySet()) {
                    request.addHeader(str, (String) this.a.q.get(str));
                }
            }
        });
        this.m.addResponseInterceptor(new HttpResponseInterceptor(this) {
            final /* synthetic */ a a;

            {
                this.a = r1;
            }

            public void process(HttpResponse response, HttpContext context) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    Header contentEncoding = entity.getContentEncoding();
                    if (contentEncoding != null) {
                        for (HeaderElement name : contentEncoding.getElements()) {
                            if (name.getName().equalsIgnoreCase("gzip")) {
                                response.setEntity(new a(entity));
                                return;
                            }
                        }
                    }
                }
            }
        });
        this.m.setHttpRequestRetryHandler(new i(5, 1500));
    }

    public HttpClient a() {
        return this.m;
    }

    public HttpContext b() {
        return this.n;
    }

    public void a(CookieStore cookieStore) {
        this.n.setAttribute("http.cookie-store", cookieStore);
    }

    public void a(ThreadPoolExecutor threadPoolExecutor) {
        this.o = threadPoolExecutor;
    }

    public void a(final boolean z) {
        this.m.setRedirectHandler(new DefaultRedirectHandler(this) {
            final /* synthetic */ a a;

            public boolean isRedirectRequested(HttpResponse response, HttpContext context) {
                return z;
            }
        });
    }

    public void a(String str) {
        HttpProtocolParams.setUserAgent(this.m.getParams(), str);
    }

    public int c() {
        return this.k;
    }

    public void a(int i) {
        if (i < 1) {
            i = 10;
        }
        this.k = i;
        ConnManagerParams.setMaxConnectionsPerRoute(this.m.getParams(), new ConnPerRouteBean(this.k));
    }

    public int d() {
        return this.l;
    }

    public void b(int i) {
        if (i < 1000) {
            i = d;
        }
        this.l = i;
        HttpParams params = this.m.getParams();
        ConnManagerParams.setTimeout(params, (long) this.l);
        HttpConnectionParams.setSoTimeout(params, this.l);
        HttpConnectionParams.setConnectionTimeout(params, this.l);
    }

    public void a(String str, int i) {
        this.m.getParams().setParameter("http.route.default-proxy", new HttpHost(str, i));
    }

    public void a(String str, int i, String str2, String str3) {
        this.m.getCredentialsProvider().setCredentials(new AuthScope(str, i), new UsernamePasswordCredentials(str2, str3));
        this.m.getParams().setParameter("http.route.default-proxy", new HttpHost(str, i));
    }

    public void a(SSLSocketFactory sSLSocketFactory) {
        this.m.getConnectionManager().getSchemeRegistry().register(new Scheme("https", sSLSocketFactory, Endpoint.HTTPS_PORT));
    }

    public void a(int i, int i2) {
        this.m.setHttpRequestRetryHandler(new i(i, i2));
    }

    public void a(String str, String str2) {
        this.q.put(str, str2);
    }

    public void b(String str) {
        this.q.remove(str);
    }

    public void b(String str, String str2) {
        a(str, str2, AuthScope.ANY);
    }

    public void a(String str, String str2, AuthScope authScope) {
        this.m.getCredentialsProvider().setCredentials(authScope, new UsernamePasswordCredentials(str, str2));
    }

    public void e() {
        this.m.getCredentialsProvider().clear();
    }

    public void a(Context context, boolean z) {
        List<WeakReference> list = (List) this.p.get(context);
        if (list != null) {
            for (WeakReference weakReference : list) {
                Future future = (Future) weakReference.get();
                if (future != null) {
                    future.cancel(z);
                }
            }
        }
        this.p.remove(context);
    }

    public f a(String str, h hVar) {
        return a(null, str, null, hVar);
    }

    public f a(String str, g gVar, h hVar) {
        return a(null, str, gVar, hVar);
    }

    public f a(Context context, String str, h hVar) {
        return a(context, str, null, hVar);
    }

    public f a(Context context, String str, g gVar, h hVar) {
        return a(this.m, this.n, new HttpHead(a(this.r, str, gVar)), null, hVar, context);
    }

    public f a(Context context, String str, Header[] headerArr, g gVar, h hVar) {
        HttpUriRequest httpHead = new HttpHead(a(this.r, str, gVar));
        if (headerArr != null) {
            httpHead.setHeaders(headerArr);
        }
        return a(this.m, this.n, httpHead, null, hVar, context);
    }

    public f b(String str, h hVar) {
        return b(null, str, null, hVar);
    }

    public f b(String str, g gVar, h hVar) {
        return b(null, str, gVar, hVar);
    }

    public f b(Context context, String str, h hVar) {
        return b(context, str, null, hVar);
    }

    public f b(Context context, String str, g gVar, h hVar) {
        return a(this.m, this.n, new HttpGet(a(this.r, str, gVar)), null, hVar, context);
    }

    public f b(Context context, String str, Header[] headerArr, g gVar, h hVar) {
        HttpUriRequest httpGet = new HttpGet(a(this.r, str, gVar));
        if (headerArr != null) {
            httpGet.setHeaders(headerArr);
        }
        return a(this.m, this.n, httpGet, null, hVar, context);
    }

    public f c(String str, h hVar) {
        return c(null, str, null, hVar);
    }

    public f c(String str, g gVar, h hVar) {
        return c(null, str, gVar, hVar);
    }

    public f c(Context context, String str, g gVar, h hVar) {
        return a(context, str, a(gVar, hVar), null, hVar);
    }

    public f a(Context context, String str, HttpEntity httpEntity, String str2, h hVar) {
        return a(this.m, this.n, a(new HttpPost(str), httpEntity), str2, hVar, context);
    }

    public f a(Context context, String str, Header[] headerArr, g gVar, String str2, h hVar) {
        HttpUriRequest httpPost = new HttpPost(str);
        if (gVar != null) {
            httpPost.setEntity(a(gVar, hVar));
        }
        if (headerArr != null) {
            httpPost.setHeaders(headerArr);
        }
        return a(this.m, this.n, httpPost, str2, hVar, context);
    }

    public f a(Context context, String str, Header[] headerArr, HttpEntity httpEntity, String str2, h hVar) {
        HttpUriRequest a = a(new HttpPost(str), httpEntity);
        if (headerArr != null) {
            a.setHeaders(headerArr);
        }
        return a(this.m, this.n, a, str2, hVar, context);
    }

    public f d(String str, h hVar) {
        return d(null, str, null, hVar);
    }

    public f d(String str, g gVar, h hVar) {
        return d(null, str, gVar, hVar);
    }

    public f d(Context context, String str, g gVar, h hVar) {
        return b(context, str, a(gVar, hVar), null, hVar);
    }

    public f b(Context context, String str, HttpEntity httpEntity, String str2, h hVar) {
        return a(this.m, this.n, a(new HttpPut(str), httpEntity), str2, hVar, context);
    }

    public f b(Context context, String str, Header[] headerArr, HttpEntity httpEntity, String str2, h hVar) {
        HttpUriRequest a = a(new HttpPut(str), httpEntity);
        if (headerArr != null) {
            a.setHeaders(headerArr);
        }
        return a(this.m, this.n, a, str2, hVar, context);
    }

    public f e(String str, h hVar) {
        return c(null, str, hVar);
    }

    public f c(Context context, String str, h hVar) {
        return a(this.m, this.n, new HttpDelete(str), null, hVar, context);
    }

    public f a(Context context, String str, Header[] headerArr, h hVar) {
        HttpUriRequest httpDelete = new HttpDelete(str);
        if (headerArr != null) {
            httpDelete.setHeaders(headerArr);
        }
        return a(this.m, this.n, httpDelete, null, hVar, context);
    }

    public f c(Context context, String str, Header[] headerArr, g gVar, h hVar) {
        HttpUriRequest httpDelete = new HttpDelete(a(this.r, str, gVar));
        if (headerArr != null) {
            httpDelete.setHeaders(headerArr);
        }
        return a(this.m, this.n, httpDelete, null, hVar, context);
    }

    protected f a(DefaultHttpClient defaultHttpClient, HttpContext httpContext, HttpUriRequest httpUriRequest, String str, h hVar, Context context) {
        if (str != null) {
            httpUriRequest.addHeader("Content-Type", str);
        }
        hVar.a(httpUriRequest.getAllHeaders());
        hVar.a(httpUriRequest.getURI());
        Future submit = this.o.submit(new b(defaultHttpClient, httpContext, httpUriRequest, hVar));
        if (context != null) {
            List list = (List) this.p.get(context);
            if (list == null) {
                list = new LinkedList();
                this.p.put(context, list);
            }
            list.add(new WeakReference(submit));
        }
        return new f(submit);
    }

    public void b(boolean z) {
        this.r = z;
    }

    public static String a(boolean z, String str, g gVar) {
        String replace;
        if (z) {
            replace = str.replace(" ", "%20");
        } else {
            replace = str;
        }
        if (gVar == null) {
            return replace;
        }
        String b = gVar.b();
        if (replace.contains("?")) {
            return new StringBuilder(String.valueOf(replace)).append("&").append(b).toString();
        }
        return new StringBuilder(String.valueOf(replace)).append("?").append(b).toString();
    }

    private HttpEntity a(g gVar, h hVar) {
        HttpEntity httpEntity = null;
        if (gVar != null) {
            try {
                httpEntity = gVar.a(hVar);
            } catch (Throwable th) {
                if (hVar != null) {
                    hVar.b(0, httpEntity, httpEntity, th);
                } else if (a) {
                    th.printStackTrace();
                }
            }
        }
        return httpEntity;
    }

    public boolean f() {
        return this.r;
    }

    private HttpEntityEnclosingRequestBase a(HttpEntityEnclosingRequestBase httpEntityEnclosingRequestBase, HttpEntity httpEntity) {
        if (httpEntity != null) {
            httpEntityEnclosingRequestBase.setEntity(httpEntity);
        }
        return httpEntityEnclosingRequestBase;
    }
}
