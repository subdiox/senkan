package org.apache.james.mime4j.storage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.james.mime4j.util.ByteArrayBuffer;

public class MemoryStorageProvider extends AbstractStorageProvider {

    static final class MemoryStorage implements Storage {
        private final int count;
        private byte[] data;

        public MemoryStorage(byte[] data, int count) {
            this.data = data;
            this.count = count;
        }

        public InputStream getInputStream() throws IOException {
            if (this.data != null) {
                return new ByteArrayInputStream(this.data, 0, this.count);
            }
            throw new IllegalStateException("storage has been deleted");
        }

        public void delete() {
            this.data = null;
        }
    }

    private static final class MemoryStorageOutputStream extends StorageOutputStream {
        ByteArrayBuffer bab;

        private MemoryStorageOutputStream() {
            this.bab = new ByteArrayBuffer(1024);
        }

        protected void write0(byte[] buffer, int offset, int length) throws IOException {
            this.bab.append(buffer, offset, length);
        }

        protected Storage toStorage0() throws IOException {
            return new MemoryStorage(this.bab.buffer(), this.bab.length());
        }
    }

    public StorageOutputStream createStorageOutputStream() {
        return new MemoryStorageOutputStream();
    }
}
