package com.squareup.okhttp.internal.spdy;

import com.kayac.lobi.libnakamap.net.APIDef.PostRankingScore.RequestKey;
import com.kayac.lobi.libnakamap.net.APIUtil.Endpoint;
import com.kayac.lobi.libnakamap.value.GroupStreamValue;
import com.rekoo.libs.net.URLCons;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;
import okio.Source;
import org.apache.james.mime4j.util.MimeUtil;

final class HpackDraft09 {
    private static final Map<ByteString, Integer> NAME_TO_FIRST_INDEX = nameToFirstIndex();
    private static final int PREFIX_4_BITS = 15;
    private static final int PREFIX_5_BITS = 31;
    private static final int PREFIX_6_BITS = 63;
    private static final int PREFIX_7_BITS = 127;
    private static final Header[] STATIC_HEADER_TABLE = new Header[]{new Header(Header.TARGET_AUTHORITY, ""), new Header(Header.TARGET_METHOD, "GET"), new Header(Header.TARGET_METHOD, "POST"), new Header(Header.TARGET_PATH, "/"), new Header(Header.TARGET_PATH, "/index.html"), new Header(Header.TARGET_SCHEME, Endpoint.SCHEME_HTTP), new Header(Header.TARGET_SCHEME, "https"), new Header(Header.RESPONSE_STATUS, "200"), new Header(Header.RESPONSE_STATUS, "204"), new Header(Header.RESPONSE_STATUS, "206"), new Header(Header.RESPONSE_STATUS, "304"), new Header(Header.RESPONSE_STATUS, "400"), new Header(Header.RESPONSE_STATUS, "404"), new Header(Header.RESPONSE_STATUS, "500"), new Header("accept-charset", ""), new Header("accept-encoding", "gzip, deflate"), new Header("accept-language", ""), new Header("accept-ranges", ""), new Header("accept", ""), new Header("access-control-allow-origin", ""), new Header("age", ""), new Header("allow", ""), new Header("authorization", ""), new Header("cache-control", ""), new Header(MimeUtil.MIME_HEADER_CONTENT_DISPOSITION, ""), new Header("content-encoding", ""), new Header(MimeUtil.MIME_HEADER_LANGAUGE, ""), new Header("content-length", ""), new Header(MimeUtil.MIME_HEADER_LOCATION, ""), new Header("content-range", ""), new Header("content-type", ""), new Header("cookie", ""), new Header(RequestKey.DATE, ""), new Header("etag", ""), new Header("expect", ""), new Header("expires", ""), new Header(URLCons.FROM, ""), new Header("host", ""), new Header("if-match", ""), new Header("if-modified-since", ""), new Header("if-none-match", ""), new Header("if-range", ""), new Header("if-unmodified-since", ""), new Header("last-modified", ""), new Header("link", ""), new Header(GroupStreamValue.LOCATION, ""), new Header("max-forwards", ""), new Header("proxy-authenticate", ""), new Header("proxy-authorization", ""), new Header("range", ""), new Header("referer", ""), new Header("refresh", ""), new Header("retry-after", ""), new Header("server", ""), new Header("set-cookie", ""), new Header("strict-transport-security", ""), new Header("transfer-encoding", ""), new Header("user-agent", ""), new Header("vary", ""), new Header("via", ""), new Header("www-authenticate", "")};

    static final class Reader {
        int headerCount = 0;
        private final List<Header> headerList = new ArrayList();
        Header[] headerTable = new Header[8];
        int headerTableByteCount = 0;
        private int maxHeaderTableByteCount;
        private int maxHeaderTableByteCountSetting;
        int nextHeaderIndex = (this.headerTable.length - 1);
        private final BufferedSource source;

        Reader(int maxHeaderTableByteCountSetting, Source source) {
            this.maxHeaderTableByteCountSetting = maxHeaderTableByteCountSetting;
            this.maxHeaderTableByteCount = maxHeaderTableByteCountSetting;
            this.source = Okio.buffer(source);
        }

        int maxHeaderTableByteCount() {
            return this.maxHeaderTableByteCount;
        }

        void maxHeaderTableByteCountSetting(int newMaxHeaderTableByteCountSetting) {
            this.maxHeaderTableByteCountSetting = newMaxHeaderTableByteCountSetting;
            this.maxHeaderTableByteCount = this.maxHeaderTableByteCountSetting;
            adjustHeaderTableByteCount();
        }

        private void adjustHeaderTableByteCount() {
            if (this.maxHeaderTableByteCount >= this.headerTableByteCount) {
                return;
            }
            if (this.maxHeaderTableByteCount == 0) {
                clearHeaderTable();
            } else {
                evictToRecoverBytes(this.headerTableByteCount - this.maxHeaderTableByteCount);
            }
        }

        private void clearHeaderTable() {
            this.headerList.clear();
            Arrays.fill(this.headerTable, null);
            this.nextHeaderIndex = this.headerTable.length - 1;
            this.headerCount = 0;
            this.headerTableByteCount = 0;
        }

        private int evictToRecoverBytes(int bytesToRecover) {
            int entriesToEvict = 0;
            if (bytesToRecover > 0) {
                for (int j = this.headerTable.length - 1; j >= this.nextHeaderIndex && bytesToRecover > 0; j--) {
                    bytesToRecover -= this.headerTable[j].hpackSize;
                    this.headerTableByteCount -= this.headerTable[j].hpackSize;
                    this.headerCount--;
                    entriesToEvict++;
                }
                System.arraycopy(this.headerTable, this.nextHeaderIndex + 1, this.headerTable, (this.nextHeaderIndex + 1) + entriesToEvict, this.headerCount);
                this.nextHeaderIndex += entriesToEvict;
            }
            return entriesToEvict;
        }

        void readHeaders() throws IOException {
            while (!this.source.exhausted()) {
                int b = this.source.readByte() & 255;
                if (b == 128) {
                    throw new IOException("index == 0");
                } else if ((b & 128) == 128) {
                    readIndexedHeader(readInt(b, 127) - 1);
                } else if (b == 64) {
                    readLiteralHeaderWithIncrementalIndexingNewName();
                } else if ((b & 64) == 64) {
                    readLiteralHeaderWithIncrementalIndexingIndexedName(readInt(b, HpackDraft09.PREFIX_6_BITS) - 1);
                } else if ((b & 32) == 32) {
                    this.maxHeaderTableByteCount = readInt(b, 31);
                    if (this.maxHeaderTableByteCount < 0 || this.maxHeaderTableByteCount > this.maxHeaderTableByteCountSetting) {
                        throw new IOException("Invalid header table byte count " + this.maxHeaderTableByteCount);
                    }
                    adjustHeaderTableByteCount();
                } else if (b == 16 || b == 0) {
                    readLiteralHeaderWithoutIndexingNewName();
                } else {
                    readLiteralHeaderWithoutIndexingIndexedName(readInt(b, 15) - 1);
                }
            }
        }

        public List<Header> getAndResetHeaderList() {
            List<Header> result = new ArrayList(this.headerList);
            this.headerList.clear();
            return result;
        }

        private void readIndexedHeader(int index) throws IOException {
            if (isStaticHeader(index)) {
                this.headerList.add(HpackDraft09.STATIC_HEADER_TABLE[index]);
                return;
            }
            int headerTableIndex = headerTableIndex(index - HpackDraft09.STATIC_HEADER_TABLE.length);
            if (headerTableIndex < 0 || headerTableIndex > this.headerTable.length - 1) {
                throw new IOException("Header index too large " + (index + 1));
            }
            this.headerList.add(this.headerTable[headerTableIndex]);
        }

        private int headerTableIndex(int index) {
            return (this.nextHeaderIndex + 1) + index;
        }

        private void readLiteralHeaderWithoutIndexingIndexedName(int index) throws IOException {
            this.headerList.add(new Header(getName(index), readByteString()));
        }

        private void readLiteralHeaderWithoutIndexingNewName() throws IOException {
            this.headerList.add(new Header(HpackDraft09.checkLowercase(readByteString()), readByteString()));
        }

        private void readLiteralHeaderWithIncrementalIndexingIndexedName(int nameIndex) throws IOException {
            insertIntoHeaderTable(-1, new Header(getName(nameIndex), readByteString()));
        }

        private void readLiteralHeaderWithIncrementalIndexingNewName() throws IOException {
            insertIntoHeaderTable(-1, new Header(HpackDraft09.checkLowercase(readByteString()), readByteString()));
        }

        private ByteString getName(int index) {
            if (isStaticHeader(index)) {
                return HpackDraft09.STATIC_HEADER_TABLE[index].name;
            }
            return this.headerTable[headerTableIndex(index - HpackDraft09.STATIC_HEADER_TABLE.length)].name;
        }

        private boolean isStaticHeader(int index) {
            return index >= 0 && index <= HpackDraft09.STATIC_HEADER_TABLE.length - 1;
        }

        private void insertIntoHeaderTable(int index, Header entry) {
            this.headerList.add(entry);
            int delta = entry.hpackSize;
            if (index != -1) {
                delta -= this.headerTable[headerTableIndex(index)].hpackSize;
            }
            if (delta > this.maxHeaderTableByteCount) {
                clearHeaderTable();
                return;
            }
            int entriesEvicted = evictToRecoverBytes((this.headerTableByteCount + delta) - this.maxHeaderTableByteCount);
            if (index == -1) {
                if (this.headerCount + 1 > this.headerTable.length) {
                    Header[] doubled = new Header[(this.headerTable.length * 2)];
                    System.arraycopy(this.headerTable, 0, doubled, this.headerTable.length, this.headerTable.length);
                    this.nextHeaderIndex = this.headerTable.length - 1;
                    this.headerTable = doubled;
                }
                index = this.nextHeaderIndex;
                this.nextHeaderIndex = index - 1;
                this.headerTable[index] = entry;
                this.headerCount++;
            } else {
                this.headerTable[index + (headerTableIndex(index) + entriesEvicted)] = entry;
            }
            this.headerTableByteCount += delta;
        }

        private int readByte() throws IOException {
            return this.source.readByte() & 255;
        }

        int readInt(int firstByte, int prefixMask) throws IOException {
            int prefix = firstByte & prefixMask;
            if (prefix < prefixMask) {
                return prefix;
            }
            int result = prefixMask;
            int shift = 0;
            while (true) {
                int b = readByte();
                if ((b & 128) == 0) {
                    return result + (b << shift);
                }
                result += (b & 127) << shift;
                shift += 7;
            }
        }

        ByteString readByteString() throws IOException {
            int firstByte = readByte();
            boolean huffmanDecode = (firstByte & 128) == 128;
            int length = readInt(firstByte, 127);
            if (huffmanDecode) {
                return ByteString.of(Huffman.get().decode(this.source.readByteArray((long) length)));
            }
            return this.source.readByteString((long) length);
        }
    }

    static final class Writer {
        private final Buffer out;

        Writer(Buffer out) {
            this.out = out;
        }

        void writeHeaders(List<Header> headerBlock) throws IOException {
            int size = headerBlock.size();
            for (int i = 0; i < size; i++) {
                ByteString name = ((Header) headerBlock.get(i)).name.toAsciiLowercase();
                Integer staticIndex = (Integer) HpackDraft09.NAME_TO_FIRST_INDEX.get(name);
                if (staticIndex != null) {
                    writeInt(staticIndex.intValue() + 1, 15, 0);
                    writeByteString(((Header) headerBlock.get(i)).value);
                } else {
                    this.out.writeByte(0);
                    writeByteString(name);
                    writeByteString(((Header) headerBlock.get(i)).value);
                }
            }
        }

        void writeInt(int value, int prefixMask, int bits) throws IOException {
            if (value < prefixMask) {
                this.out.writeByte(bits | value);
                return;
            }
            this.out.writeByte(bits | prefixMask);
            value -= prefixMask;
            while (value >= 128) {
                this.out.writeByte((value & 127) | 128);
                value >>>= 7;
            }
            this.out.writeByte(value);
        }

        void writeByteString(ByteString data) throws IOException {
            writeInt(data.size(), 127, 0);
            this.out.write(data);
        }
    }

    private HpackDraft09() {
    }

    private static Map<ByteString, Integer> nameToFirstIndex() {
        Map<ByteString, Integer> result = new LinkedHashMap(STATIC_HEADER_TABLE.length);
        for (int i = 0; i < STATIC_HEADER_TABLE.length; i++) {
            if (!result.containsKey(STATIC_HEADER_TABLE[i].name)) {
                result.put(STATIC_HEADER_TABLE[i].name, Integer.valueOf(i));
            }
        }
        return Collections.unmodifiableMap(result);
    }

    private static ByteString checkLowercase(ByteString name) throws IOException {
        int i = 0;
        int length = name.size();
        while (i < length) {
            byte c = name.getByte(i);
            if (c < (byte) 65 || c > (byte) 90) {
                i++;
            } else {
                throw new IOException("PROTOCOL_ERROR response malformed: mixed case name: " + name.utf8());
            }
        }
        return name;
    }
}
