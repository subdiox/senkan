package org.apache.james.mime4j.parser;

import android.support.v4.media.TransportMediator;
import java.io.IOException;
import java.util.BitSet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.james.mime4j.MimeException;
import org.apache.james.mime4j.descriptor.BodyDescriptor;
import org.apache.james.mime4j.descriptor.DefaultBodyDescriptor;
import org.apache.james.mime4j.descriptor.MaximalBodyDescriptor;
import org.apache.james.mime4j.descriptor.MutableBodyDescriptor;
import org.apache.james.mime4j.io.LineReaderInputStream;
import org.apache.james.mime4j.io.MaxHeaderLimitException;
import org.apache.james.mime4j.util.ByteArrayBuffer;

public abstract class AbstractEntity implements EntityStateMachine {
    private static final int T_IN_BODYPART = -2;
    private static final int T_IN_MESSAGE = -3;
    private static final BitSet fieldChars = new BitSet();
    protected final MutableBodyDescriptor body;
    protected final MimeEntityConfig config;
    private boolean endOfHeader;
    protected final int endState;
    private Field field;
    private int headerCount;
    private int lineCount;
    private final ByteArrayBuffer linebuf;
    protected final Log log = LogFactory.getLog(getClass());
    protected final BodyDescriptor parent;
    protected final int startState;
    protected int state;

    protected abstract LineReaderInputStream getDataStream();

    protected abstract int getLineNumber();

    static {
        int i;
        for (i = 33; i <= 57; i++) {
            fieldChars.set(i);
        }
        for (i = 59; i <= TransportMediator.KEYCODE_MEDIA_PLAY; i++) {
            fieldChars.set(i);
        }
    }

    AbstractEntity(BodyDescriptor parent, int startState, int endState, MimeEntityConfig config) {
        this.parent = parent;
        this.state = startState;
        this.startState = startState;
        this.endState = endState;
        this.config = config;
        this.body = newBodyDescriptor(parent);
        this.linebuf = new ByteArrayBuffer(64);
        this.lineCount = 0;
        this.endOfHeader = false;
        this.headerCount = 0;
    }

    public int getState() {
        return this.state;
    }

    protected MutableBodyDescriptor newBodyDescriptor(BodyDescriptor pParent) {
        if (this.config.isMaximalBodyDescriptor()) {
            return new MaximalBodyDescriptor(pParent);
        }
        return new DefaultBodyDescriptor(pParent);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private org.apache.james.mime4j.util.ByteArrayBuffer fillFieldBuffer() throws java.io.IOException, org.apache.james.mime4j.MimeException {
        /*
        r9 = this;
        r8 = 0;
        r7 = 1;
        r5 = r9.endOfHeader;
        if (r5 == 0) goto L_0x000c;
    L_0x0006:
        r5 = new java.lang.IllegalStateException;
        r5.<init>();
        throw r5;
    L_0x000c:
        r5 = r9.config;
        r4 = r5.getMaxLineLen();
        r2 = r9.getDataStream();
        r1 = new org.apache.james.mime4j.util.ByteArrayBuffer;
        r5 = 64;
        r1.<init>(r5);
    L_0x001d:
        r5 = r9.linebuf;
        r3 = r5.length();
        if (r4 <= 0) goto L_0x0034;
    L_0x0025:
        r5 = r1.length();
        r5 = r5 + r3;
        if (r5 < r4) goto L_0x0034;
    L_0x002c:
        r5 = new org.apache.james.mime4j.io.MaxLineLimitException;
        r6 = "Maximum line length limit exceeded";
        r5.<init>(r6);
        throw r5;
    L_0x0034:
        if (r3 <= 0) goto L_0x003f;
    L_0x0036:
        r5 = r9.linebuf;
        r5 = r5.buffer();
        r1.append(r5, r8, r3);
    L_0x003f:
        r5 = r9.linebuf;
        r5.clear();
        r5 = r9.linebuf;
        r5 = r2.readLine(r5);
        r6 = -1;
        if (r5 != r6) goto L_0x0055;
    L_0x004d:
        r5 = org.apache.james.mime4j.parser.Event.HEADERS_PREMATURE_END;
        r9.monitor(r5);
        r9.endOfHeader = r7;
    L_0x0054:
        return r1;
    L_0x0055:
        r5 = r9.linebuf;
        r3 = r5.length();
        if (r3 <= 0) goto L_0x006b;
    L_0x005d:
        r5 = r9.linebuf;
        r6 = r3 + -1;
        r5 = r5.byteAt(r6);
        r6 = 10;
        if (r5 != r6) goto L_0x006b;
    L_0x0069:
        r3 = r3 + -1;
    L_0x006b:
        if (r3 <= 0) goto L_0x007b;
    L_0x006d:
        r5 = r9.linebuf;
        r6 = r3 + -1;
        r5 = r5.byteAt(r6);
        r6 = 13;
        if (r5 != r6) goto L_0x007b;
    L_0x0079:
        r3 = r3 + -1;
    L_0x007b:
        if (r3 != 0) goto L_0x0080;
    L_0x007d:
        r9.endOfHeader = r7;
        goto L_0x0054;
    L_0x0080:
        r5 = r9.lineCount;
        r5 = r5 + 1;
        r9.lineCount = r5;
        r5 = r9.lineCount;
        if (r5 <= r7) goto L_0x001d;
    L_0x008a:
        r5 = r9.linebuf;
        r0 = r5.byteAt(r8);
        r5 = 32;
        if (r0 == r5) goto L_0x001d;
    L_0x0094:
        r5 = 9;
        if (r0 == r5) goto L_0x001d;
    L_0x0098:
        goto L_0x0054;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.james.mime4j.parser.AbstractEntity.fillFieldBuffer():org.apache.james.mime4j.util.ByteArrayBuffer");
    }

    protected boolean parseField() throws MimeException, IOException {
        int maxHeaderLimit = this.config.getMaxHeaderCount();
        while (!this.endOfHeader) {
            if (this.headerCount >= maxHeaderLimit) {
                throw new MaxHeaderLimitException("Maximum header limit exceeded");
            }
            ByteArrayBuffer fieldbuf = fillFieldBuffer();
            this.headerCount++;
            int len = fieldbuf.length();
            if (len > 0 && fieldbuf.byteAt(len - 1) == (byte) 10) {
                len--;
            }
            if (len > 0 && fieldbuf.byteAt(len - 1) == (byte) 13) {
                len--;
            }
            fieldbuf.setLength(len);
            boolean valid = true;
            int pos = fieldbuf.indexOf((byte) 58);
            if (pos <= 0) {
                monitor(Event.INALID_HEADER);
                valid = false;
                continue;
            } else {
                for (int i = 0; i < pos; i++) {
                    if (!fieldChars.get(fieldbuf.byteAt(i) & 255)) {
                        monitor(Event.INALID_HEADER);
                        valid = false;
                        continue;
                        break;
                    }
                }
                continue;
            }
            if (valid) {
                this.field = new RawField(fieldbuf, pos);
                this.body.addField(this.field);
                return true;
            }
        }
        return false;
    }

    public BodyDescriptor getBodyDescriptor() {
        switch (getState()) {
            case -1:
            case 6:
            case 8:
            case 9:
            case 12:
                return this.body;
            default:
                throw new IllegalStateException("Invalid state :" + stateToString(this.state));
        }
    }

    public Field getField() {
        switch (getState()) {
            case 4:
                return this.field;
            default:
                throw new IllegalStateException("Invalid state :" + stateToString(this.state));
        }
    }

    protected void monitor(Event event) throws MimeException, IOException {
        if (this.config.isStrictParsing()) {
            throw new MimeParseEventException(event);
        }
        warn(event);
    }

    protected String message(Event event) {
        String message;
        if (event == null) {
            message = "Event is unexpectedly null.";
        } else {
            message = event.toString();
        }
        int lineNumber = getLineNumber();
        return lineNumber <= 0 ? message : "Line " + lineNumber + ": " + message;
    }

    protected void warn(Event event) {
        if (this.log.isWarnEnabled()) {
            this.log.warn(message(event));
        }
    }

    protected void debug(Event event) {
        if (this.log.isDebugEnabled()) {
            this.log.debug(message(event));
        }
    }

    public String toString() {
        return getClass().getName() + " [" + stateToString(this.state) + "][" + this.body.getMimeType() + "][" + this.body.getBoundary() + "]";
    }

    public static final String stateToString(int state) {
        switch (state) {
            case -3:
                return "In message";
            case -2:
                return "Bodypart";
            case -1:
                return "End of stream";
            case 0:
                return "Start message";
            case 1:
                return "End message";
            case 2:
                return "Raw entity";
            case 3:
                return "Start header";
            case 4:
                return "Field";
            case 5:
                return "End header";
            case 6:
                return "Start multipart";
            case 7:
                return "End multipart";
            case 8:
                return "Preamble";
            case 9:
                return "Epilogue";
            case 10:
                return "Start bodypart";
            case 11:
                return "End bodypart";
            case 12:
                return "Body";
            default:
                return "Unknown";
        }
    }
}
