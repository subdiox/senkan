package com.kayac.lobi.libnakamap.net;

import android.content.Context;
import android.net.Uri.Builder;
import android.os.Build;
import android.os.Build.VERSION;
import com.kayac.lobi.libnakamap.net.APIRes.JSON2ObjectMapper;
import com.kayac.lobi.libnakamap.utils.Log;
import com.kayac.lobi.libnakamap.utils.ModuleUtil;
import com.kayac.lobi.sdk.Version;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;
import junit.framework.Assert;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class APIUtil {
    public static final String ACCEPT_ENCODING = "Accept-Encoding";
    public static final int CONNECTION_TIMEOUT = 30000;
    public static final String PLATFORM = "android";
    public static final int SO_TIMEOUT = 30000;
    public static final String USER_AGENT = "User-agent";
    private static HttpClient sHttpClient = null;
    private static String sUserAgent = null;

    public static final class ByteArrayResponseHandler implements ResponseHandler<byte[]> {
        private String mResponseBody = null;
        private int mStatusCode = -1;

        public int getStatusCode() {
            return this.mStatusCode;
        }

        public String getResponseBody() {
            return this.mResponseBody;
        }

        public byte[] handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
            if (response == null || response.getStatusLine() == null) {
                return null;
            }
            this.mStatusCode = response.getStatusLine().getStatusCode();
            if (this.mStatusCode == 200) {
                return APIUtil.getEntryBytesData(response);
            }
            this.mResponseBody = APIUtil.getEntryStringData(response);
            return null;
        }
    }

    public interface Endpoint {
        public static final String GZIP = "gzip";
        public static final int HTTPS_PORT = 443;
        public static final int HTTP_PORT = 80;
        public static final String SCHEME_HTTP = "http";
        public static final String SCHEME_HTTPS = "https";

        String getAcceptEncoding();

        String getAuthority();

        int getHttpsPort();

        SocketFactory getSSLSocketFactory();

        String getScheme();
    }

    public static final class JSONArrayResponseHandler implements ResponseHandler<JSONArray> {
        private String mResponseBody = null;
        private int mStatusCode = -1;
        private Throwable mThrowable = null;

        public int getStatusCode() {
            return this.mStatusCode;
        }

        public String getResponseBody() {
            return this.mResponseBody;
        }

        public Throwable getThrowable() {
            return this.mThrowable;
        }

        public JSONArray handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
            if (response == null || response.getStatusLine() == null) {
                return null;
            }
            this.mResponseBody = APIUtil.getEntryStringData(response);
            this.mStatusCode = response.getStatusLine().getStatusCode();
            if (this.mStatusCode != 200) {
                return null;
            }
            try {
                return new JSONArray(this.mResponseBody);
            } catch (JSONException e) {
                e.printStackTrace();
                this.mThrowable = e;
                return null;
            }
        }
    }

    public static final class JSONObjectResponseHandler implements ResponseHandler<JSONObject> {
        private String mResponseBody = null;
        private int mStatusCode = -1;
        private Throwable mThrowable = null;

        public int getStatusCode() {
            return this.mStatusCode;
        }

        public String getResponseBody() {
            return this.mResponseBody;
        }

        public Throwable getThrowable() {
            return this.mThrowable;
        }

        public JSONObject handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
            if (response == null || response.getStatusLine() == null) {
                return null;
            }
            this.mResponseBody = APIUtil.getEntryStringData(response);
            this.mStatusCode = response.getStatusLine().getStatusCode();
            if (this.mStatusCode != 200) {
                return null;
            }
            try {
                return new JSONObject(this.mResponseBody);
            } catch (JSONException e) {
                e.printStackTrace();
                this.mThrowable = e;
                return null;
            }
        }
    }

    public static final class Product implements Endpoint {
        public String getAuthority() {
            return "api.lobi.co";
        }

        public String getScheme() {
            return "https";
        }

        public int getHttpsPort() {
            return Endpoint.HTTPS_PORT;
        }

        public String getAcceptEncoding() {
            return Endpoint.GZIP;
        }

        public SocketFactory getSSLSocketFactory() {
            return SSLSocketFactory.getSocketFactory();
        }
    }

    public static final class Staging implements Endpoint {
        public String getAuthority() {
            return "api-staging.lobi.co";
        }

        public String getScheme() {
            return "https";
        }

        public int getHttpsPort() {
            return Endpoint.HTTPS_PORT;
        }

        public String getAcceptEncoding() {
            return Endpoint.GZIP;
        }

        public SocketFactory getSSLSocketFactory() {
            return SSLSocketFactory.getSocketFactory();
        }
    }

    public static final class StringResponseHandler implements ResponseHandler<String> {
        private String mResponseBody = null;
        private int mStatusCode = -1;

        public int getStatusCode() {
            return this.mStatusCode;
        }

        public String getResponseBody() {
            return this.mResponseBody;
        }

        public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
            if (!(response == null || response.getStatusLine() == null)) {
                this.mResponseBody = APIUtil.getEntryStringData(response);
                this.mStatusCode = response.getStatusLine().getStatusCode();
            }
            return this.mResponseBody;
        }
    }

    public static final class Test implements Endpoint {
        public String getAuthority() {
            return "api-test.lobi.co";
        }

        public String getScheme() {
            return "https";
        }

        public int getHttpsPort() {
            return Endpoint.HTTPS_PORT;
        }

        public String getAcceptEncoding() {
            return Endpoint.GZIP;
        }

        public SocketFactory getSSLSocketFactory() {
            return SSLSocketFactory.getSocketFactory();
        }
    }

    public static final class Thanks implements Endpoint {
        public String getAuthority() {
            return "thanks.lobi.co";
        }

        public String getScheme() {
            return "https";
        }

        public int getHttpsPort() {
            return Endpoint.HTTPS_PORT;
        }

        public String getAcceptEncoding() {
            return Endpoint.GZIP;
        }

        public SocketFactory getSSLSocketFactory() {
            return SSLSocketFactory.getSocketFactory();
        }
    }

    public static final class ThanksStaging implements Endpoint {
        public String getAuthority() {
            return "thanks-staging.lobi.co";
        }

        public String getScheme() {
            return "https";
        }

        public int getHttpsPort() {
            return Endpoint.HTTPS_PORT;
        }

        public String getAcceptEncoding() {
            return Endpoint.GZIP;
        }

        public SocketFactory getSSLSocketFactory() {
            return SSLSocketFactory.getSocketFactory();
        }
    }

    private APIUtil() {
    }

    private static final String getEntryStringData(HttpResponse response) throws IllegalStateException, IOException {
        Throwable th;
        Header ceheader = response.getEntity().getContentEncoding();
        String r = null;
        if (ceheader != null) {
            HeaderElement[] codecs = ceheader.getElements();
            for (HeaderElement name : codecs) {
                if (name.getName().equalsIgnoreCase(Endpoint.GZIP)) {
                    InputStream gis = null;
                    BufferedReader br = null;
                    try {
                        InputStream gis2 = new GZIPInputStream(response.getEntity().getContent());
                        try {
                            BufferedReader br2 = new BufferedReader(new InputStreamReader(gis2, "UTF-8"));
                            try {
                                StringBuffer buf = new StringBuffer();
                                while (true) {
                                    String s = br2.readLine();
                                    if (s == null) {
                                        break;
                                    }
                                    buf.append(s);
                                }
                                r = buf.toString();
                                if (br2 != null) {
                                    br2.close();
                                }
                                if (gis2 != null) {
                                    gis2.close();
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                br = br2;
                                gis = gis2;
                                if (br != null) {
                                    br.close();
                                }
                                if (gis != null) {
                                    gis.close();
                                }
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            gis = gis2;
                            if (br != null) {
                                br.close();
                            }
                            if (gis != null) {
                                gis.close();
                            }
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        if (br != null) {
                            br.close();
                        }
                        if (gis != null) {
                            gis.close();
                        }
                        throw th;
                    }
                }
            }
        }
        if (r == null) {
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        }
        return r;
    }

    private static final byte[] getEntryBytesData(HttpResponse response) throws IllegalStateException, IOException {
        Throwable th;
        Header ceheader = response.getEntity().getContentEncoding();
        byte[] r = null;
        if (ceheader != null) {
            HeaderElement[] codecs = ceheader.getElements();
            for (HeaderElement name : codecs) {
                if (name.getName().equalsIgnoreCase(Endpoint.GZIP)) {
                    InputStream gis = null;
                    ByteArrayOutputStream bos = null;
                    BufferedOutputStream os = null;
                    try {
                        InputStream gis2 = new GZIPInputStream(response.getEntity().getContent());
                        try {
                            BufferedOutputStream os2;
                            ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
                            try {
                                os2 = new BufferedOutputStream(bos2);
                            } catch (Throwable th2) {
                                th = th2;
                                bos = bos2;
                                gis = gis2;
                                if (os != null) {
                                    os.close();
                                }
                                if (bos != null) {
                                    bos.close();
                                }
                                if (gis != null) {
                                    gis.close();
                                }
                                throw th;
                            }
                            try {
                                byte[] buff = new byte[1024];
                                int current = 0;
                                while (true) {
                                    int read = gis2.read(buff, current, buff.length);
                                    if (read == -1) {
                                        break;
                                    }
                                    os2.write(buff, 0, read);
                                    current += read;
                                }
                                os2.flush();
                                r = bos2.toByteArray();
                                if (os2 != null) {
                                    os2.close();
                                }
                                if (bos2 != null) {
                                    bos2.close();
                                }
                                if (gis2 != null) {
                                    gis2.close();
                                }
                            } catch (Throwable th3) {
                                th = th3;
                                os = os2;
                                bos = bos2;
                                gis = gis2;
                                if (os != null) {
                                    os.close();
                                }
                                if (bos != null) {
                                    bos.close();
                                }
                                if (gis != null) {
                                    gis.close();
                                }
                                throw th;
                            }
                        } catch (Throwable th4) {
                            th = th4;
                            gis = gis2;
                            if (os != null) {
                                os.close();
                            }
                            if (bos != null) {
                                bos.close();
                            }
                            if (gis != null) {
                                gis.close();
                            }
                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        if (os != null) {
                            os.close();
                        }
                        if (bos != null) {
                            bos.close();
                        }
                        if (gis != null) {
                            gis.close();
                        }
                        throw th;
                    }
                }
            }
        }
        if (r == null) {
            return EntityUtils.toByteArray(response.getEntity());
        }
        return r;
    }

    public static final Builder uriBuilderFactory(Endpoint endpoint, String path) {
        Builder uriBuilder = new Builder();
        uriBuilder.scheme(endpoint.getScheme());
        uriBuilder.authority(endpoint.getAuthority());
        uriBuilder.path(path);
        return uriBuilder;
    }

    public static final Builder uriBuilderFactory(Endpoint endpoint, String path, String scheme) {
        Builder uriBuilder = new Builder();
        uriBuilder.scheme(scheme);
        uriBuilder.authority(endpoint.getAuthority());
        uriBuilder.path(path);
        return uriBuilder;
    }

    public static final Builder uriBuilderFactory(Endpoint endpoint, String path, Map<String, String> params) {
        params.put("lang", Locale.getDefault().getLanguage());
        params.put("platform", "android");
        Builder uriBuilder = uriBuilderFactory(endpoint, path);
        for (Entry<String, String> e : params.entrySet()) {
            uriBuilder.appendQueryParameter((String) e.getKey(), (String) e.getValue());
        }
        return uriBuilder;
    }

    public static final Builder uriBuilderFactory(Endpoint endpoint, String path, Map<String, String> params, String scheme) {
        params.put("lang", Locale.getDefault().getLanguage());
        Builder uriBuilder = uriBuilderFactory(endpoint, path);
        for (Entry<String, String> e : params.entrySet()) {
            uriBuilder.appendQueryParameter((String) e.getKey(), (String) e.getValue());
        }
        return uriBuilder;
    }

    public static HttpUriRequest requestFactory(Endpoint endpoint, String method, String url) {
        HttpUriRequest request = null;
        if ("GET".equals(method)) {
            request = new HttpGet(url);
        } else if ("POST".equals(method)) {
            request = new HttpPost(url);
        } else {
            Assert.fail();
        }
        request.setHeader(USER_AGENT, getUserAgent());
        if (endpoint.getAcceptEncoding() != null) {
            request.setHeader(ACCEPT_ENCODING, endpoint.getAcceptEncoding());
        }
        return request;
    }

    public static HttpUriRequest requestFactory(Endpoint endpoint, String method, String url, Map<String, String> params) throws UnsupportedEncodingException {
        params.put("lang", Locale.getDefault().getLanguage());
        HttpUriRequest request = requestFactory(endpoint, method, url);
        if ("POST".equals(method)) {
            List<NameValuePair> p = new ArrayList();
            for (Entry<String, String> e : params.entrySet()) {
                Log.v("nakamap-sdk", ((String) e.getKey()) + "=" + ((String) e.getValue()));
                p.add(new BasicNameValuePair((String) e.getKey(), (String) e.getValue()));
            }
            ((HttpPost) request).setEntity(new UrlEncodedFormEntity(p, "UTF-8"));
        }
        return request;
    }

    public static HttpUriRequest requestFactory(Endpoint endpoint, String method, String url, Map<String, String> params, String fileName, File file) throws UnsupportedEncodingException {
        params.put("lang", Locale.getDefault().getLanguage());
        HttpUriRequest request = requestFactory(endpoint, method, url);
        if ("POST".equals(method)) {
            MultipartEntity entity = new MultipartEntity();
            for (Entry<String, String> e : params.entrySet()) {
                entity.addPart((String) e.getKey(), new StringBody((String) e.getValue(), Charset.forName("UTF-8")));
            }
            if (!(fileName == null || file == null)) {
                entity.addPart(fileName, new FileBody(file));
            }
            ((HttpPost) request).setEntity(entity);
        }
        return request;
    }

    public static HttpUriRequest requestFactory(Endpoint endpoint, String method, String url, Map<String, String> params, String fileName, File file, String mimeType) throws UnsupportedEncodingException {
        params.put("lang", Locale.getDefault().getLanguage());
        HttpUriRequest request = requestFactory(endpoint, method, url);
        if ("POST".equals(method)) {
            MultipartEntity entity = new MultipartEntity();
            for (Entry<String, String> e : params.entrySet()) {
                entity.addPart((String) e.getKey(), new StringBody((String) e.getValue(), Charset.forName("UTF-8")));
            }
            if (!(fileName == null || file == null)) {
                entity.addPart(fileName, new FileBody(file, mimeType));
            }
            ((HttpPost) request).setEntity(entity);
        }
        return request;
    }

    public static <T> T execute(Endpoint endpoint, HttpUriRequest request, ResponseHandler<T> handler) throws ClientProtocolException, IOException {
        try {
            HttpClient httpClient = httpClientFactory(endpoint);
            Log.v("nakamap-sdk", "request: " + request.getURI());
            Log.v("nakamap-sdk", "method: " + request.getMethod());
            if (request instanceof HttpPost) {
                HttpEntity entity = ((HttpPost) request).getEntity();
                if (!(entity instanceof MultipartEntity)) {
                    Writer output = new StringWriter();
                    IOUtils.copy(entity.getContent(), output);
                    Log.v("nakamap-sdk", "entity: " + output.toString());
                }
            }
            return httpClient != null ? httpClient.execute(request, handler) : httpClient.execute(request, handler);
        } catch (Throwable th) {
            if (null != null) {
            }
        }
    }

    public static <T, K> T execute(Endpoint endpoint, HttpUriRequest request, ResponseHandler<K> handler, JSON2ObjectMapper<T, K> mapper) throws ClientProtocolException, IOException {
        K k = execute(endpoint, request, handler);
        if (k == null) {
            return null;
        }
        return mapper.map(k);
    }

    public static void post(final String url, final Map<String, String> params, final APICallback<JSONObject> callback) {
        CoreAPI.getExecutorService().execute(new Runnable() {
            public void run() {
                try {
                    HttpUriRequest request = APIUtil.requestFactory(CoreAPI.getEndpoint(), "POST", url);
                    JSONObjectResponseHandler handler = new JSONObjectResponseHandler();
                    List<NameValuePair> p = new ArrayList();
                    if (params != null) {
                        for (Entry<String, String> e : params.entrySet()) {
                            p.add(new BasicNameValuePair((String) e.getKey(), (String) e.getValue()));
                        }
                        ((HttpPost) request).setEntity(new UrlEncodedFormEntity(p, "UTF-8"));
                    }
                    JSONObject json = (JSONObject) APIUtil.execute(CoreAPI.getEndpoint(), request, handler);
                    HttpEntity entity = ((HttpPost) request).getEntity();
                    if (entity instanceof UrlEncodedFormEntity) {
                        Writer writer = new StringWriter();
                        IOUtils.copy(entity.getContent(), writer);
                        Log.v("LibNakamap", "entity: " + writer);
                    }
                    if (callback == null) {
                        return;
                    }
                    if (handler.getThrowable() != null) {
                        callback.onError(handler.getThrowable());
                    } else if (json == null) {
                        callback.onError(handler.getStatusCode(), handler.getResponseBody());
                    } else {
                        callback.onResponse(json);
                    }
                } catch (UnsupportedEncodingException e2) {
                    e2.printStackTrace();
                    if (callback != null) {
                        callback.onError(e2);
                    }
                } catch (ClientProtocolException e3) {
                    e3.printStackTrace();
                    if (callback != null) {
                        callback.onError(e3);
                    }
                } catch (IOException e4) {
                    e4.printStackTrace();
                    if (callback != null) {
                        callback.onError(e4);
                    }
                }
            }
        });
    }

    public static synchronized HttpClient httpClientFactory(Endpoint endpoint) {
        HttpClient httpClient;
        synchronized (APIUtil.class) {
            if (sHttpClient != null) {
                httpClient = sHttpClient;
            } else {
                SchemeRegistry schemeRegistry = new SchemeRegistry();
                schemeRegistry.register(new Scheme(Endpoint.SCHEME_HTTP, PlainSocketFactory.getSocketFactory(), 80));
                schemeRegistry.register(new Scheme("https", endpoint.getSSLSocketFactory(), Endpoint.HTTPS_PORT));
                HttpParams params = new BasicHttpParams();
                HttpProtocolParams.setUserAgent(params, sUserAgent == null ? "" : sUserAgent);
                HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
                HttpProtocolParams.setContentCharset(params, "UTF-8");
                HttpClient httpClient2 = new DefaultHttpClient(new ThreadSafeClientConnManager(params, schemeRegistry), params);
                HttpParams connectionParams = httpClient2.getParams();
                HttpConnectionParams.setConnectionTimeout(connectionParams, 30000);
                HttpConnectionParams.setSoTimeout(connectionParams, 30000);
                sHttpClient = httpClient2;
                httpClient = sHttpClient;
            }
        }
        return httpClient;
    }

    public static final void init(Context context) {
        sUserAgent = getDefaultUserAgent(context);
    }

    private static final String getDefaultUserAgent(Context context) {
        String platform = Build.MANUFACTURER + " " + Build.MODEL + " " + Build.PRODUCT;
        String os = "Android " + VERSION.RELEASE;
        String webkitPostfix = "AppleWebKit/534.30 (KHTML, like Gecko)";
        String versionName = String.format("core:%s;", new Object[]{Version.versionName});
        if (ModuleUtil.chatIsAvailable()) {
            versionName = versionName + String.format("chat:%s;", new Object[]{ModuleUtil.chatVersionName()});
        }
        if (ModuleUtil.rankingIsAvailable()) {
            versionName = versionName + String.format("ranking:%s;", new Object[]{ModuleUtil.rankingVersionName()});
        }
        if (ModuleUtil.recIsAvailable()) {
            versionName = versionName + String.format("rec:%s;", new Object[]{ModuleUtil.recVersionName()});
        }
        return String.format("Lobi (%s) %s %s %s", new Object[]{versionName, os, platform, webkitPostfix});
    }

    public static final String getUserAgent() {
        return sUserAgent;
    }
}
