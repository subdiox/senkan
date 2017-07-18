package com.kayac.lobi.libnakamap.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class InterruptibleSSLSocketFactory extends SSLSocketFactory {
    private final SSLSocketFactory mSocketFactory;
    private final int mTimeout;

    public InterruptibleSSLSocketFactory(SSLSocketFactory wrapedFactory, int timeout) {
        this.mSocketFactory = wrapedFactory;
        this.mTimeout = timeout;
    }

    private Socket wrapSocket(Socket socket) {
        return new InterruptibleSSLSocket((SSLSocket) socket, this.mTimeout);
    }

    public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
        return wrapSocket(this.mSocketFactory.createSocket(s, host, port, autoClose));
    }

    public String[] getDefaultCipherSuites() {
        return this.mSocketFactory.getDefaultCipherSuites();
    }

    public String[] getSupportedCipherSuites() {
        return this.mSocketFactory.getSupportedCipherSuites();
    }

    public Socket createSocket() throws IOException {
        return wrapSocket(this.mSocketFactory.createSocket());
    }

    public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
        return wrapSocket(this.mSocketFactory.createSocket(address, port, localAddress, localPort));
    }

    public Socket createSocket(InetAddress host, int port) throws IOException {
        return wrapSocket(this.mSocketFactory.createSocket(host, port));
    }

    public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException, UnknownHostException {
        return wrapSocket(this.mSocketFactory.createSocket(host, port, localHost, localPort));
    }

    public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
        return wrapSocket(this.mSocketFactory.createSocket(host, port));
    }
}
