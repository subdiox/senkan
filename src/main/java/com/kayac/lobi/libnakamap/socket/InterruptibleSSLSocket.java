package com.kayac.lobi.libnakamap.socket;

import com.kayac.lobi.libnakamap.utils.Log;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.channels.SocketChannel;
import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

public class InterruptibleSSLSocket extends SSLSocket {
    private static final String TAG = InterruptibleSSLSocket.class.getSimpleName();
    private final SSLSocket mSocket;

    public InterruptibleSSLSocket(SSLSocket socket, final int timeout) {
        this.mSocket = socket;
        addHandshakeCompletedListener(new HandshakeCompletedListener() {
            public void handshakeCompleted(HandshakeCompletedEvent event) {
                Log.d(InterruptibleSSLSocket.TAG, "handshakeCompleted!");
                SSLSocket socket = event.getSocket();
                try {
                    socket.setSoTimeout(timeout);
                } catch (SocketException e) {
                    Log.w(InterruptibleSSLSocket.TAG, e.getMessage());
                } finally {
                    socket.removeHandshakeCompletedListener(this);
                }
            }
        });
    }

    public InputStream getInputStream() throws IOException {
        return new InterruptibleSocketInputStream(this.mSocket.getInputStream());
    }

    public OutputStream getOutputStream() throws IOException {
        return new InterruptibleSocketOutputStream(this.mSocket.getOutputStream());
    }

    public void addHandshakeCompletedListener(HandshakeCompletedListener listener) {
        this.mSocket.addHandshakeCompletedListener(listener);
    }

    public boolean getEnableSessionCreation() {
        return this.mSocket.getEnableSessionCreation();
    }

    public String[] getEnabledCipherSuites() {
        return this.mSocket.getEnabledCipherSuites();
    }

    public String[] getEnabledProtocols() {
        return this.mSocket.getEnabledProtocols();
    }

    public boolean getNeedClientAuth() {
        return this.mSocket.getNeedClientAuth();
    }

    public SSLSession getSession() {
        return this.mSocket.getSession();
    }

    public String[] getSupportedCipherSuites() {
        return this.mSocket.getSupportedCipherSuites();
    }

    public String[] getSupportedProtocols() {
        return this.mSocket.getSupportedProtocols();
    }

    public boolean getUseClientMode() {
        return this.mSocket.getUseClientMode();
    }

    public boolean getWantClientAuth() {
        return this.mSocket.getWantClientAuth();
    }

    public void removeHandshakeCompletedListener(HandshakeCompletedListener listener) {
        this.mSocket.removeHandshakeCompletedListener(listener);
    }

    public void setEnableSessionCreation(boolean flag) {
        this.mSocket.setEnableSessionCreation(flag);
    }

    public void setEnabledCipherSuites(String[] suites) {
        this.mSocket.setEnabledCipherSuites(suites);
    }

    public void setEnabledProtocols(String[] protocols) {
        this.mSocket.setEnabledProtocols(protocols);
    }

    public void setNeedClientAuth(boolean need) {
        this.mSocket.setNeedClientAuth(need);
    }

    public void setUseClientMode(boolean mode) {
        this.mSocket.setUseClientMode(mode);
    }

    public void setWantClientAuth(boolean want) {
        this.mSocket.setWantClientAuth(want);
    }

    public void startHandshake() throws IOException {
        this.mSocket.startHandshake();
    }

    public void bind(SocketAddress localAddr) throws IOException {
        this.mSocket.bind(localAddr);
    }

    public synchronized void close() throws IOException {
        this.mSocket.close();
    }

    public void connect(SocketAddress remoteAddr) throws IOException {
        this.mSocket.connect(remoteAddr);
    }

    public void connect(SocketAddress remoteAddr, int timeout) throws IOException {
        this.mSocket.connect(remoteAddr, timeout);
    }

    public SocketChannel getChannel() {
        return this.mSocket.getChannel();
    }

    public InetAddress getInetAddress() {
        return this.mSocket.getInetAddress();
    }

    public boolean getKeepAlive() throws SocketException {
        return this.mSocket.getKeepAlive();
    }

    public InetAddress getLocalAddress() {
        return this.mSocket.getLocalAddress();
    }

    public int getLocalPort() {
        return this.mSocket.getLocalPort();
    }

    public SocketAddress getLocalSocketAddress() {
        return this.mSocket.getLocalSocketAddress();
    }

    public boolean getOOBInline() throws SocketException {
        return this.mSocket.getOOBInline();
    }

    public int getPort() {
        return this.mSocket.getPort();
    }

    public synchronized int getReceiveBufferSize() throws SocketException {
        return this.mSocket.getReceiveBufferSize();
    }

    public SocketAddress getRemoteSocketAddress() {
        return this.mSocket.getRemoteSocketAddress();
    }

    public boolean getReuseAddress() throws SocketException {
        return this.mSocket.getReuseAddress();
    }

    public synchronized int getSendBufferSize() throws SocketException {
        return this.mSocket.getSendBufferSize();
    }

    public int getSoLinger() throws SocketException {
        return this.mSocket.getSoLinger();
    }

    public synchronized int getSoTimeout() throws SocketException {
        return this.mSocket.getSoTimeout();
    }

    public boolean getTcpNoDelay() throws SocketException {
        return this.mSocket.getTcpNoDelay();
    }

    public int getTrafficClass() throws SocketException {
        return this.mSocket.getTrafficClass();
    }

    public boolean isBound() {
        return this.mSocket.isBound();
    }

    public boolean isClosed() {
        return this.mSocket.isClosed();
    }

    public boolean isConnected() {
        return this.mSocket.isConnected();
    }

    public boolean isInputShutdown() {
        return this.mSocket.isInputShutdown();
    }

    public boolean isOutputShutdown() {
        return this.mSocket.isOutputShutdown();
    }

    public void sendUrgentData(int value) throws IOException {
        this.mSocket.sendUrgentData(value);
    }

    public void setKeepAlive(boolean keepAlive) throws SocketException {
        this.mSocket.setKeepAlive(keepAlive);
    }

    public void setOOBInline(boolean oobinline) throws SocketException {
        this.mSocket.setOOBInline(oobinline);
    }

    public void setPerformancePreferences(int connectionTime, int latency, int bandwidth) {
        this.mSocket.setPerformancePreferences(connectionTime, latency, bandwidth);
    }

    public synchronized void setReceiveBufferSize(int size) throws SocketException {
        this.mSocket.setReceiveBufferSize(size);
    }

    public void setReuseAddress(boolean reuse) throws SocketException {
        this.mSocket.setReuseAddress(reuse);
    }

    public synchronized void setSendBufferSize(int size) throws SocketException {
        this.mSocket.setSendBufferSize(size);
    }

    public void setSoLinger(boolean on, int timeout) throws SocketException {
        this.mSocket.setSoLinger(on, timeout);
    }

    public synchronized void setSoTimeout(int timeout) throws SocketException {
        this.mSocket.setSoTimeout(timeout);
    }

    public void setTcpNoDelay(boolean on) throws SocketException {
        this.mSocket.setTcpNoDelay(on);
    }

    public void setTrafficClass(int value) throws SocketException {
        this.mSocket.setTrafficClass(value);
    }

    public void shutdownInput() throws IOException {
        this.mSocket.shutdownInput();
    }

    public void shutdownOutput() throws IOException {
        this.mSocket.shutdownOutput();
    }
}
