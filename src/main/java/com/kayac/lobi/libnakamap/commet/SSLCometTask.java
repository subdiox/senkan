package com.kayac.lobi.libnakamap.commet;

import com.kayac.lobi.libnakamap.net.APIUtil;
import com.kayac.lobi.libnakamap.net.APIUtil.Endpoint;
import com.kayac.lobi.libnakamap.utils.Log;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ConnectionReleaseTrigger;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.json.JSONException;
import org.json.JSONObject;

public class SSLCometTask implements Runnable, ResponseHandler<Void> {
    private static final int BUFFER_SIZE = 8192;
    private static final Pattern mBoundaryMatchPattern = Pattern.compile("boundary=\"(\\w+)\"");
    private static final DefaultHttpClient sClient;
    private final String TAG = "[streaming]";
    private String mBoundary;
    private final Callback mCallback;
    private InputStream mInputStream;
    private volatile boolean mIsCanncelled;
    private final HttpRequestBase mRequest;

    public interface Callback {
        void onJsonObject(JSONObject jSONObject);

        void onTaskEnd(boolean z, boolean z2);
    }

    static {
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("https", new CometEasySSLSocketFactory(), Endpoint.HTTPS_PORT));
        HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setSoTimeout(params, 30000);
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setUserAgent(params, APIUtil.getUserAgent());
        sClient = new DefaultStreamingHttpClient(new ThreadSafeClientConnManager(params, schemeRegistry), params);
    }

    public SSLCometTask(HttpRequestBase httpRequest, Callback callback) {
        this.mRequest = httpRequest;
        this.mCallback = callback;
    }

    public void run() {
        boolean error = false;
        try {
            doRun();
        } catch (IOException e) {
            error = true;
            Log.w("[streaming]", "io exception: " + e.getMessage());
        } catch (Exception e2) {
            e2.printStackTrace();
        } finally {
            close();
            this.mCallback.onTaskEnd(this.mIsCanncelled, error);
        }
    }

    private void doRun() throws IOException {
        Log.v("[streaming]", "going to access: " + this.mRequest.getURI().toString());
        sClient.execute(this.mRequest, this);
        Log.v("[streaming]", "execute done!");
    }

    public Void handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
        Log.i("[streaming]", "handleResponse");
        int responseCode = response.getStatusLine().getStatusCode();
        Log.v("[streaming]", "responseCode: " + responseCode);
        if (200 != responseCode) {
            throw new IOException();
        }
        Header contentType = response.getFirstHeader("Content-Type");
        if (contentType != null) {
            Log.v("[streaming]", "Content-Type: " + contentType);
            Matcher matcher = mBoundaryMatchPattern.matcher(contentType.getValue());
            String boundary = null;
            if (matcher.find()) {
                boundary = "--" + matcher.group(1) + IOUtils.LINE_SEPARATOR_UNIX;
                Log.v("boundary:", boundary);
            }
            if (boundary == null) {
                Log.w("[streaming]", "no boundary found for: " + contentType);
                throw new IOException("no boundary found for: " + contentType);
            }
            synchronized (this) {
                this.mBoundary = boundary;
            }
            Log.i("[streaming]", "getting input stream...");
            InputStream inputStream = response.getEntity().getContent();
            if (this.mIsCanncelled) {
                Log.v("[streaming]", "cancelled!");
            } else {
                parseComet(inputStream);
            }
            return null;
        }
        Log.w("[streaming]", "no content type!");
        throw new IOException("no content type!");
    }

    protected void parseComet(InputStream inputStream) throws IOException {
        synchronized (this) {
            this.mInputStream = inputStream;
        }
        char[] buffer = new char[8192];
        StringWriter writer = new StringWriter(8192);
        StringBuffer stringBuffer = writer.getBuffer();
        InputStreamReader mStreamReader = new InputStreamReader(inputStream);
        while (true) {
            Log.v("[streaming]", "waiting for next event...");
            if (this.mIsCanncelled) {
                Log.w("[streaming]", "it's already cancelled!");
                return;
            }
            int read = mStreamReader.read(buffer);
            Log.v("[streaming]", "read: " + read);
            if (read >= 0) {
                writer.write(buffer, 0, read);
                writer.flush();
                while (true) {
                    int index = stringBuffer.indexOf(this.mBoundary);
                    if (index != 0) {
                        break;
                    }
                    int next = stringBuffer.indexOf(this.mBoundary, index + 1);
                    if (next <= -1) {
                        continue;
                        break;
                    }
                    try {
                        JSONObject jsonObject = new JSONObject(stringBuffer.substring(stringBuffer.indexOf("\n\n") + 2, next));
                        if (jsonObject != null) {
                            if (this.mIsCanncelled) {
                                break;
                            }
                            this.mCallback.onJsonObject(jsonObject);
                            stringBuffer.delete(0, next);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        throw new IOException(e.getMessage());
                    }
                }
            }
            return;
        }
    }

    private void close() {
        if (this.mInputStream != null) {
            try {
                if (this.mInputStream instanceof ConnectionReleaseTrigger) {
                    ((ConnectionReleaseTrigger) this.mInputStream).abortConnection();
                }
                this.mInputStream = null;
            } catch (IOException e) {
            }
        }
    }

    public void cancel() {
        this.mIsCanncelled = true;
    }

    public boolean isCancelled() {
        return this.mIsCanncelled;
    }
}
