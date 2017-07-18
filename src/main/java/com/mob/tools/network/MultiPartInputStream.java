package com.mob.tools.network;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class MultiPartInputStream extends InputStream {
    private int curIs;
    private ArrayList<InputStream> isList = new ArrayList();

    MultiPartInputStream() {
    }

    private boolean isEmpty() {
        return this.isList == null || this.isList.size() <= 0;
    }

    public void addInputStream(InputStream is) throws Throwable {
        this.isList.add(is);
    }

    public int available() throws IOException {
        return isEmpty() ? 0 : ((InputStream) this.isList.get(this.curIs)).available();
    }

    public void close() throws IOException {
        Iterator i$ = this.isList.iterator();
        while (i$.hasNext()) {
            ((InputStream) i$.next()).close();
        }
    }

    public int read() throws IOException {
        if (isEmpty()) {
            return -1;
        }
        int data = ((InputStream) this.isList.get(this.curIs)).read();
        while (data < 0) {
            this.curIs++;
            if (this.curIs >= this.isList.size()) {
                return data;
            }
            data = ((InputStream) this.isList.get(this.curIs)).read();
        }
        return data;
    }

    public int read(byte[] b, int offset, int length) throws IOException {
        if (isEmpty()) {
            return -1;
        }
        int len = ((InputStream) this.isList.get(this.curIs)).read(b, offset, length);
        while (len < 0) {
            this.curIs++;
            if (this.curIs >= this.isList.size()) {
                return len;
            }
            len = ((InputStream) this.isList.get(this.curIs)).read(b, offset, length);
        }
        return len;
    }

    public long skip(long n) throws IOException {
        throw new IOException();
    }
}
