package com.kayac.lobi.libnakamap.socket;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

public class InterruptibleSocketOutputStream extends OutputStream {
    private boolean mIsClosed = false;
    private final OutputStream mOutputStream;

    public InterruptibleSocketOutputStream(OutputStream outputStream) {
        this.mOutputStream = outputStream;
    }

    public void close() throws IOException {
        this.mIsClosed = true;
        this.mOutputStream.close();
    }

    public void flush() throws IOException {
        while (!checkIfClosed()) {
            try {
                this.mOutputStream.flush();
                return;
            } catch (SocketTimeoutException e) {
                sleep();
            }
        }
    }

    public void write(byte[] buffer, int offset, int count) throws IOException {
        while (!checkIfClosed()) {
            try {
                this.mOutputStream.write(buffer, offset, count);
                return;
            } catch (SocketTimeoutException e) {
                sleep();
            }
        }
    }

    public void write(byte[] buffer) throws IOException {
        while (!checkIfClosed()) {
            try {
                this.mOutputStream.write(buffer);
                return;
            } catch (SocketTimeoutException e) {
                sleep();
            }
        }
    }

    public void write(int oneByte) throws IOException {
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
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new InterruptedIOException("read was interrupted!");
        }
    }
}
