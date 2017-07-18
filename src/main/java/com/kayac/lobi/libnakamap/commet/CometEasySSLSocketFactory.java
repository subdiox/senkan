package com.kayac.lobi.libnakamap.commet;

import com.kayac.lobi.libnakamap.socket.InterruptibleSSLSocketFactory;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.scheme.LayeredSocketFactory;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class CometEasySSLSocketFactory implements SocketFactory, LayeredSocketFactory {
    private static final int SO_TIMEOUT = 1000;
    private final SSLContext sslcontext = createEasySSLContext();

    public static SSLContext createEasySSLContext() {
        try {
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new TrustManager[]{new EasyX509TrustManager(null)}, null);
            return context;
        } catch (Exception e) {
            return null;
        }
    }

    public SSLSocketFactory getSocketFactory() throws IOException {
        return getSocketFactory(1000);
    }

    public SSLSocketFactory getSocketFactory(int timeout) throws IOException {
        if (this.sslcontext != null) {
            return new InterruptibleSSLSocketFactory(this.sslcontext.getSocketFactory(), timeout);
        }
        throw new IOException("ssl context not available");
    }

    public Socket connectSocket(Socket sock, String host, int port, InetAddress localAddress, int localPort, HttpParams params) throws IOException, UnknownHostException, ConnectTimeoutException {
        int connTimeout = HttpConnectionParams.getConnectionTimeout(params);
        int soTimeout = HttpConnectionParams.getSoTimeout(params);
        InetSocketAddress remoteAddress = new InetSocketAddress(host, port);
        SSLSocket sslsock = (SSLSocket) (sock != null ? sock : createSocket());
        if (localAddress != null || localPort > 0) {
            if (localPort < 0) {
                localPort = 0;
            }
            sslsock.bind(new InetSocketAddress(localAddress, localPort));
        }
        sslsock.connect(remoteAddress, connTimeout);
        sslsock.setSoTimeout(soTimeout);
        return sslsock;
    }

    public Socket createSocket() throws IOException {
        return getSocketFactory().createSocket();
    }

    public boolean isSecure(Socket socket) throws IllegalArgumentException {
        return true;
    }

    public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
        return getSocketFactory().createSocket(socket, host, port, autoClose);
    }

    public boolean equals(Object obj) {
        return obj != null && obj.getClass().equals(CometEasySSLSocketFactory.class);
    }

    public int hashCode() {
        return CometEasySSLSocketFactory.class.hashCode();
    }
}
