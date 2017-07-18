package cn.sharesdk.framework.utils;

class g implements Appendable {
    int a = -1;
    char[] b = new char[2];
    final /* synthetic */ Appendable c;
    final /* synthetic */ f d;

    g(f fVar, Appendable appendable) {
        this.d = fVar;
        this.c = appendable;
    }

    private void a(char[] cArr, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            this.c.append(cArr[i2]);
        }
    }

    public Appendable append(char c) {
        char[] a;
        if (this.a != -1) {
            if (Character.isLowSurrogate(c)) {
                a = this.d.a(Character.toCodePoint((char) this.a, c));
                if (a != null) {
                    a(a, a.length);
                } else {
                    this.c.append((char) this.a);
                    this.c.append(c);
                }
                this.a = -1;
            } else {
                throw new IllegalArgumentException("Expected low surrogate character but got '" + c + "' with value " + c);
            }
        } else if (Character.isHighSurrogate(c)) {
            this.a = c;
        } else if (Character.isLowSurrogate(c)) {
            throw new IllegalArgumentException("Unexpected low surrogate character '" + c + "' with value " + c);
        } else {
            a = this.d.a(c);
            if (a != null) {
                a(a, a.length);
            } else {
                this.c.append(c);
            }
        }
        return this;
    }

    public Appendable append(CharSequence csq) {
        return append(csq, 0, csq.length());
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Appendable append(java.lang.CharSequence r6, int r7, int r8) {
        /*
        r5 = this;
        r4 = -1;
        if (r7 >= r8) goto L_0x0052;
    L_0x0003:
        r0 = r5.a;
        if (r0 == r4) goto L_0x008c;
    L_0x0007:
        r0 = r7 + 1;
        r1 = r6.charAt(r7);
        r2 = java.lang.Character.isLowSurrogate(r1);
        if (r2 != 0) goto L_0x002c;
    L_0x0013:
        r0 = new java.lang.IllegalArgumentException;
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "Expected low surrogate character but got ";
        r2 = r2.append(r3);
        r1 = r2.append(r1);
        r1 = r1.toString();
        r0.<init>(r1);
        throw r0;
    L_0x002c:
        r2 = r5.d;
        r3 = r5.a;
        r3 = (char) r3;
        r1 = java.lang.Character.toCodePoint(r3, r1);
        r1 = r2.a(r1);
        if (r1 == 0) goto L_0x0053;
    L_0x003b:
        r2 = r1.length;
        r5.a(r1, r2);
        r7 = r7 + 1;
    L_0x0041:
        r5.a = r4;
    L_0x0043:
        r1 = r5.d;
        r1 = r1.a(r6, r0, r8);
        if (r1 <= r7) goto L_0x0050;
    L_0x004b:
        r0 = r5.c;
        r0.append(r6, r7, r1);
    L_0x0050:
        if (r1 != r8) goto L_0x005c;
    L_0x0052:
        return r5;
    L_0x0053:
        r1 = r5.c;
        r2 = r5.a;
        r2 = (char) r2;
        r1.append(r2);
        goto L_0x0041;
    L_0x005c:
        r0 = cn.sharesdk.framework.utils.f.b(r6, r1, r8);
        if (r0 >= 0) goto L_0x0066;
    L_0x0062:
        r0 = -r0;
        r5.a = r0;
        goto L_0x0052;
    L_0x0066:
        r2 = r5.d;
        r2 = r2.a(r0);
        if (r2 == 0) goto L_0x007d;
    L_0x006e:
        r3 = r2.length;
        r5.a(r2, r3);
    L_0x0072:
        r0 = java.lang.Character.isSupplementaryCodePoint(r0);
        if (r0 == 0) goto L_0x008a;
    L_0x0078:
        r0 = 2;
    L_0x0079:
        r7 = r1 + r0;
        r0 = r7;
        goto L_0x0043;
    L_0x007d:
        r2 = r5.b;
        r3 = 0;
        r2 = java.lang.Character.toChars(r0, r2, r3);
        r3 = r5.b;
        r5.a(r3, r2);
        goto L_0x0072;
    L_0x008a:
        r0 = 1;
        goto L_0x0079;
    L_0x008c:
        r0 = r7;
        goto L_0x0043;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.sharesdk.framework.utils.g.append(java.lang.CharSequence, int, int):java.lang.Appendable");
    }
}
