package com.googlecode.mp4parser;

import com.googlecode.mp4parser.util.CastUtils;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;

public class MemoryDataSourceImpl implements DataSource {
    ByteBuffer data;

    public MemoryDataSourceImpl(byte[] data) {
        this.data = ByteBuffer.wrap(data);
    }

    public MemoryDataSourceImpl(ByteBuffer buffer) {
        this.data = buffer;
    }

    public int read(ByteBuffer byteBuffer) throws IOException {
        byte[] buf = new byte[Math.min(byteBuffer.remaining(), this.data.remaining())];
        this.data.get(buf);
        byteBuffer.put(buf);
        return buf.length;
    }

    public long size() throws IOException {
        return (long) this.data.capacity();
    }

    public long position() throws IOException {
        return (long) this.data.position();
    }

    public void position(long nuPos) throws IOException {
        this.data.position(CastUtils.l2i(nuPos));
    }

    public long transferTo(long position, long count, WritableByteChannel target) throws IOException {
        return (long) target.write((ByteBuffer) ((ByteBuffer) this.data.position(CastUtils.l2i(position))).slice().limit(CastUtils.l2i(count)));
    }

    public ByteBuffer map(long startPosition, long size) throws IOException {
        return (ByteBuffer) ((ByteBuffer) this.data.position(CastUtils.l2i(startPosition))).slice().limit(CastUtils.l2i(size));
    }

    public void close() throws IOException {
    }
}
