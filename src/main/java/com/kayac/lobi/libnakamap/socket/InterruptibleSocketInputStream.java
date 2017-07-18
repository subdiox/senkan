package com.kayac.lobi.libnakamap.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

public class InterruptibleSocketInputStream extends InputStream {
    private final InputStream mInputStream;
    private volatile boolean mIsClosed;

    public InterruptibleSocketInputStream(InputStream inputStream) {
        this.mInputStream = inputStream;
    }

    public int read(byte[] buffer, int offset, int length) throws IOException {
        int read = -1;
        while (!checkIfClosed()) {
            try {
                read = this.mInputStream.read(buffer, offset, length);
                break;
            } catch (SocketTimeoutException e) {
                sleep();
            }
        }
        return read;
    }

    public int read() throws IOException {
        int read = -1;
        while (!checkIfClosed()) {
            try {
                read = this.mInputStream.read();
                break;
            } catch (SocketTimeoutException e) {
                sleep();
            }
        }
        return read;
    }

    public int read(byte[] b) throws IOException {
        int read = -1;
        while (!checkIfClosed()) {
            try {
                read = this.mInputStream.read(b);
                break;
            } catch (SocketTimeoutException e) {
                sleep();
            }
        }
        return read;
    }

    public int available() throws IOException {
        return 0;
    }

    private boolean checkIfClosed() throws IOException {
        if (!this.mIsClosed) {
            return this.mIsClosed;
        }
        throw new IOException("the socket is closed!");
    }

    private void sleep() throws IOException {
        try {
            checkIfClosed();
            TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new InterruptedIOException("read was interrupted!");
        }
    }

    public void close() throws IOException {
        this.mIsClosed = true;
        this.mInputStream.close();
    }

    public void mark(int readlimit) {
        this.mInputStream.mark(readlimit);
    }

    public boolean markSupported() {
        return this.mInputStream.markSupported();
    }

    public synchronized void reset() throws IOException {
        this.mInputStream.reset();
    }

    public long skip(long byteCount) throws IOException {
        return this.mInputStream.skip(byteCount);
    }
}
