package com.yaya.sdk.utils;

import org.apache.commons.io.IOUtils;

public final class l {
    private static final char[] a = new char[64];
    private static final byte[] b = new byte[256];

    static {
        int i;
        for (i = 0; i < 26; i++) {
            a[i] = (char) (i + 65);
        }
        i = 26;
        int i2 = 0;
        while (i < 52) {
            a[i] = (char) (i2 + 97);
            i++;
            i2++;
        }
        i = 52;
        i2 = 0;
        while (i < 62) {
            a[i] = (char) (i2 + 48);
            i++;
            i2++;
        }
        a[62] = '+';
        a[63] = IOUtils.DIR_SEPARATOR_UNIX;
        for (i2 = 0; i2 < 256; i2++) {
            b[i2] = (byte) -1;
        }
        for (i2 = 90; i2 >= 65; i2--) {
            b[i2] = (byte) (i2 - 65);
        }
        for (i2 = 122; i2 >= 97; i2--) {
            b[i2] = (byte) ((i2 - 97) + 26);
        }
        for (i2 = 57; i2 >= 48; i2--) {
            b[i2] = (byte) ((i2 - 48) + 52);
        }
        b[43] = (byte) 62;
        b[47] = (byte) 63;
        b[61] = (byte) 0;
    }

    private l() {
    }

    public static String a(byte[] bArr) {
        StringBuilder stringBuilder = new StringBuilder(((bArr.length + 2) / 3) * 4);
        int i = 0;
        int length = bArr.length - 2;
        while (i < length) {
            int i2 = i + 1;
            int i3 = i2 + 1;
            i2 = ((bArr[i2] & 255) << 8) | ((bArr[i] & 255) << 16);
            i = i3 + 1;
            i2 |= bArr[i3] & 255;
            stringBuilder.append(a[i2 >> 18]);
            stringBuilder.append(a[(i2 >> 12) & 63]);
            stringBuilder.append(a[(i2 >> 6) & 63]);
            stringBuilder.append(a[i2 & 63]);
        }
        length = bArr.length;
        if (i < length) {
            i2 = i + 1;
            i = (bArr[i] & 255) << 16;
            stringBuilder.append(a[i >> 18]);
            if (i2 < length) {
                i |= (bArr[i2] & 255) << 8;
                stringBuilder.append(a[(i >> 12) & 63]);
                stringBuilder.append(a[(i >> 6) & 63]);
            } else {
                stringBuilder.append(a[(i >> 12) & 63]);
                stringBuilder.append('=');
            }
            stringBuilder.append('=');
        }
        return stringBuilder.toString();
    }

    public static byte[] a(String str) {
        int length;
        int i = 0;
        for (length = str.length() - 1; str.charAt(length) == '='; length--) {
            i++;
        }
        int length2 = ((str.length() * 6) / 8) - i;
        byte[] bArr = new byte[length2];
        int length3 = str.length();
        i = 0;
        int i2 = 0;
        while (i2 < length3) {
            int i3 = b[str.charAt(i2 + 3)] + (((b[str.charAt(i2)] << 18) + (b[str.charAt(i2 + 1)] << 12)) + (b[str.charAt(i2 + 2)] << 6));
            length = 0;
            while (length < 3 && i + length < length2) {
                bArr[i + length] = (byte) (i3 >> ((2 - length) * 8));
                length++;
            }
            i2 += 4;
            i += 3;
        }
        return bArr;
    }
}
