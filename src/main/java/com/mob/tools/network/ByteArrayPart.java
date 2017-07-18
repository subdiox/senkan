package com.mob.tools.network;

import com.mob.tools.utils.Data;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ByteArrayPart extends HTTPPart {
    private BufferedByteArrayOutputStream buffer;

    public ByteArrayPart append(byte[] array) throws Throwable {
        if (this.buffer == null) {
            this.buffer = new BufferedByteArrayOutputStream(array.length);
        }
        this.buffer.write(array);
        this.buffer.flush();
        return this;
    }

    protected InputStream getInputStream() throws Throwable {
        if (this.buffer == null) {
            return new ByteArrayInputStream(new byte[0]);
        }
        byte[] body = this.buffer.getBuffer();
        return (body == null || this.buffer.size() <= 0) ? new ByteArrayInputStream(new byte[0]) : new ByteArrayInputStream(body, 0, this.buffer.size());
    }

    protected long length() throws Throwable {
        return this.buffer == null ? 0 : (long) this.buffer.size();
    }

    public String toString() {
        if (this.buffer == null) {
            return null;
        }
        byte[] body = this.buffer.getBuffer();
        return body != null ? Data.byteToHex(body, 0, this.buffer.size()) : null;
    }
}
