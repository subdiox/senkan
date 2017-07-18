package com.squareup.okhttp;

import com.squareup.okhttp.internal.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import okio.Buffer;
import okio.BufferedSink;
import okio.ByteString;
import org.apache.james.mime4j.field.ContentTypeField;

public final class MultipartBuilder {
    public static final MediaType ALTERNATIVE = MediaType.parse("multipart/alternative");
    private static final byte[] COLONSPACE = new byte[]{(byte) 58, (byte) 32};
    private static final byte[] CRLF = new byte[]{(byte) 13, (byte) 10};
    private static final byte[] DASHDASH = new byte[]{(byte) 45, (byte) 45};
    public static final MediaType DIGEST = MediaType.parse(ContentTypeField.TYPE_MULTIPART_DIGEST);
    public static final MediaType FORM = MediaType.parse("multipart/form-data");
    public static final MediaType MIXED = MediaType.parse("multipart/mixed");
    public static final MediaType PARALLEL = MediaType.parse("multipart/parallel");
    private final ByteString boundary;
    private long length;
    private final List<RequestBody> partBodies;
    private final List<Buffer> partHeadings;
    private MediaType type;

    private static final class MultipartRequestBody extends RequestBody {
        private final ByteString boundary;
        private final MediaType contentType;
        private final long length;
        private final List<RequestBody> partBodies;
        private final List<Buffer> partHeadings;

        public MultipartRequestBody(MediaType type, ByteString boundary, List<Buffer> partHeadings, List<RequestBody> partBodies, long length) {
            if (type == null) {
                throw new NullPointerException("type == null");
            }
            this.boundary = boundary;
            this.contentType = MediaType.parse(type + "; boundary=" + boundary.utf8());
            this.partHeadings = Util.immutableList((List) partHeadings);
            this.partBodies = Util.immutableList((List) partBodies);
            if (length != -1) {
                length += (long) ((((MultipartBuilder.CRLF.length + MultipartBuilder.DASHDASH.length) + boundary.size()) + MultipartBuilder.DASHDASH.length) + MultipartBuilder.CRLF.length);
            }
            this.length = length;
        }

        public long contentLength() {
            return this.length;
        }

        public MediaType contentType() {
            return this.contentType;
        }

        public void writeTo(BufferedSink sink) throws IOException {
            int size = this.partHeadings.size();
            for (int i = 0; i < size; i++) {
                sink.writeAll(((Buffer) this.partHeadings.get(i)).clone());
                ((RequestBody) this.partBodies.get(i)).writeTo(sink);
            }
            sink.write(MultipartBuilder.CRLF);
            sink.write(MultipartBuilder.DASHDASH);
            sink.write(this.boundary);
            sink.write(MultipartBuilder.DASHDASH);
            sink.write(MultipartBuilder.CRLF);
        }
    }

    public MultipartBuilder() {
        this(UUID.randomUUID().toString());
    }

    public MultipartBuilder(String boundary) {
        this.type = MIXED;
        this.length = 0;
        this.partHeadings = new ArrayList();
        this.partBodies = new ArrayList();
        this.boundary = ByteString.encodeUtf8(boundary);
    }

    public MultipartBuilder type(MediaType type) {
        if (type == null) {
            throw new NullPointerException("type == null");
        } else if (type.type().equals("multipart")) {
            this.type = type;
            return this;
        } else {
            throw new IllegalArgumentException("multipart != " + type);
        }
    }

    public MultipartBuilder addPart(RequestBody body) {
        return addPart(null, body);
    }

    public MultipartBuilder addPart(Headers headers, RequestBody body) {
        if (body == null) {
            throw new NullPointerException("body == null");
        } else if (headers != null && headers.get("Content-Type") != null) {
            throw new IllegalArgumentException("Unexpected header: Content-Type");
        } else if (headers == null || headers.get("Content-Length") == null) {
            Buffer heading = createPartHeading(headers, body, this.partHeadings.isEmpty());
            this.partHeadings.add(heading);
            this.partBodies.add(body);
            long bodyContentLength = body.contentLength();
            if (bodyContentLength == -1) {
                this.length = -1;
            } else if (this.length != -1) {
                this.length += heading.size() + bodyContentLength;
            }
            return this;
        } else {
            throw new IllegalArgumentException("Unexpected header: Content-Length");
        }
    }

    private static StringBuilder appendQuotedString(StringBuilder target, String key) {
        target.append('\"');
        int len = key.length();
        for (int i = 0; i < len; i++) {
            char ch = key.charAt(i);
            switch (ch) {
                case '\n':
                    target.append("%0A");
                    break;
                case '\r':
                    target.append("%0D");
                    break;
                case '\"':
                    target.append("%22");
                    break;
                default:
                    target.append(ch);
                    break;
            }
        }
        target.append('\"');
        return target;
    }

    public MultipartBuilder addFormDataPart(String name, String value) {
        return addFormDataPart(name, null, RequestBody.create(null, value));
    }

    public MultipartBuilder addFormDataPart(String name, String filename, RequestBody value) {
        if (name == null) {
            throw new NullPointerException("name == null");
        }
        StringBuilder disposition = new StringBuilder("form-data; name=");
        appendQuotedString(disposition, name);
        if (filename != null) {
            disposition.append("; filename=");
            appendQuotedString(disposition, filename);
        }
        return addPart(Headers.of("Content-Disposition", disposition.toString()), value);
    }

    private Buffer createPartHeading(Headers headers, RequestBody body, boolean isFirst) {
        Buffer sink = new Buffer();
        if (!isFirst) {
            sink.write(CRLF);
        }
        sink.write(DASHDASH);
        sink.write(this.boundary);
        sink.write(CRLF);
        if (headers != null) {
            for (int i = 0; i < headers.size(); i++) {
                sink.writeUtf8(headers.name(i)).write(COLONSPACE).writeUtf8(headers.value(i)).write(CRLF);
            }
        }
        MediaType contentType = body.contentType();
        if (contentType != null) {
            sink.writeUtf8("Content-Type: ").writeUtf8(contentType.toString()).write(CRLF);
        }
        long contentLength = body.contentLength();
        if (contentLength != -1) {
            sink.writeUtf8("Content-Length: ").writeUtf8(Long.toString(contentLength)).write(CRLF);
        }
        sink.write(CRLF);
        return sink;
    }

    public RequestBody build() {
        if (!this.partHeadings.isEmpty()) {
            return new MultipartRequestBody(this.type, this.boundary, this.partHeadings, this.partBodies, this.length);
        }
        throw new IllegalStateException("Multipart body must have at least one part.");
    }
}
