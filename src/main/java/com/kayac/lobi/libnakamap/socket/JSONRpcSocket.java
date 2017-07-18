package com.kayac.lobi.libnakamap.socket;

import com.kayac.lobi.libnakamap.utils.Log;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JSONRpcSocket {
    private static final int BUFFER_SIZE = 1024;
    private static final int SO_READ_TIMEOUT = 300;
    private static final String TAG = "[easyadd]";
    private final Delegate mDelegate;
    private final String mHost;
    private final int mPort;
    private final ExecutorService mReadExecutor = Executors.newSingleThreadExecutor();
    private Future<?> mReadFuture;
    private Socket mSocket;
    private final ExecutorService mWriteExecutor = Executors.newSingleThreadExecutor();
    private Future<?> mWriteFuture;
    private Writer mWriter;

    public interface Delegate {
        void connected(JSONRpcSocket jSONRpcSocket);

        void disconnected(JSONRpcSocket jSONRpcSocket, int i);

        void received(JSONRpcSocket jSONRpcSocket, JSONObject jSONObject);
    }

    public JSONRpcSocket(String host, int port, Delegate delegate) {
        Log.i(TAG, "socket: " + host + ":" + port);
        this.mHost = host;
        this.mPort = port;
        this.mDelegate = delegate;
    }

    public void connect() {
        try {
            this.mReadFuture = this.mReadExecutor.submit(new Runnable() {
                public void run() {
                    JSONRpcSocket.this.startConnection();
                }
            });
        } catch (RejectedExecutionException e) {
            Log.w(TAG, "rejected: ", e);
        }
    }

    public void destroy() {
        if (this.mReadFuture != null) {
            this.mReadFuture.cancel(true);
        }
        if (this.mWriteFuture != null) {
            this.mWriteFuture.cancel(true);
        }
        this.mReadExecutor.shutdown();
        this.mWriteExecutor.shutdown();
        closeSocket();
    }

    private synchronized void closeSocket() {
        if (!(this.mSocket == null || this.mSocket.isClosed())) {
            try {
                this.mSocket.shutdownOutput();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                this.mSocket.shutdownInput();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            try {
                this.mSocket.close();
                Log.i(TAG, "socket closed!");
            } catch (IOException e22) {
                Log.w(TAG, "io exception:", e22);
                this.mSocket = null;
            }
        }
    }

    protected void startConnection() {
        try {
            this.mSocket = new Socket();
            this.mSocket.connect(new InetSocketAddress(this.mHost, this.mPort));
            this.mSocket.setSoTimeout(SO_READ_TIMEOUT);
            Reader reader = getReader();
            this.mWriter = getWriter();
            Log.i(TAG, "connected to: " + this.mSocket.getInetAddress());
            this.mDelegate.connected(this);
            try {
                startReadLoop(reader);
            } catch (IOException e) {
                Log.w(TAG, "io exception:", e);
            } finally {
                closeSocket();
            }
        } catch (IOException e2) {
            Log.w(TAG, "io exception:", e2);
        }
    }

    private Writer getWriter() {
        try {
            return new BufferedWriter(new OutputStreamWriter(this.mSocket.getOutputStream(), "UTF-8"), 1024);
        } catch (IOException e) {
            Log.w(TAG, "io exception:", e);
            return null;
        }
    }

    private Reader getReader() {
        try {
            return new InputStreamReader(this.mSocket.getInputStream(), "UTF-8");
        } catch (IOException e) {
            Log.w(TAG, "io exception:", e);
            return null;
        }
    }

    private void startReadLoop(Reader reader) throws IOException {
        char[] buff = new char[1024];
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            try {
                int readLen = reader.read(buff, 0, 1024);
                if (readLen > 0) {
                    Log.i(TAG, "got : " + readLen);
                    stringBuffer.append(buff, 0, readLen);
                    int posLF = stringBuffer.indexOf(IOUtils.LINE_SEPARATOR_UNIX);
                    if (posLF > 0) {
                        try {
                            this.mDelegate.received(this, new JSONObject(new JSONTokener(stringBuffer.substring(0, posLF))));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } finally {
                            stringBuffer.delete(0, Math.min(stringBuffer.length(), posLF + 1));
                        }
                    } else {
                        continue;
                    }
                } else {
                    checkInterruptedState();
                }
            } catch (SocketTimeoutException e2) {
                checkInterruptedState();
            }
        }
    }

    private static void checkInterruptedState() throws InterruptedIOException {
        if (Thread.interrupted()) {
            throw new InterruptedIOException("json rpc i/o interrupted!");
        }
    }

    public void write(JSONObject jsonObject) {
        String jsonStr = jsonObject.toString();
        if (jsonStr != null) {
            wirteObject(jsonStr);
        }
    }

    private void wirteObject(final String jsonStr) {
        try {
            this.mWriteFuture = this.mWriteExecutor.submit(new Runnable() {
                public void run() {
                    try {
                        JSONRpcSocket.this.mWriter.write(jsonStr);
                        JSONRpcSocket.this.mWriter.flush();
                        Log.i(JSONRpcSocket.TAG, "* wrote successfully!");
                    } catch (IOException e) {
                        Log.i("io exception:", e.getMessage());
                        JSONRpcSocket.this.closeSocket();
                    }
                }
            });
        } catch (RejectedExecutionException e) {
            Log.w(TAG, "rejected exception:", e);
        }
    }
}
