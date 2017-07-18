package org.apache.james.mime4j.field.address.parser;

import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Vector;

public class AddressListParser implements AddressListParserTreeConstants, AddressListParserConstants {
    private static int[] jj_la1_0;
    private static int[] jj_la1_1;
    private final JJCalls[] jj_2_rtns;
    private int jj_endpos;
    private Vector<int[]> jj_expentries;
    private int[] jj_expentry;
    private int jj_gc;
    private int jj_gen;
    SimpleCharStream jj_input_stream;
    private int jj_kind;
    private int jj_la;
    private final int[] jj_la1;
    private Token jj_lastpos;
    private int[] jj_lasttokens;
    private final LookaheadSuccess jj_ls;
    public Token jj_nt;
    private int jj_ntk;
    private boolean jj_rescan;
    private Token jj_scanpos;
    private boolean jj_semLA;
    protected JJTAddressListParserState jjtree;
    public boolean lookingAhead;
    public Token token;
    public AddressListParserTokenManager token_source;

    static final class JJCalls {
        int arg;
        Token first;
        int gen;
        JJCalls next;

        JJCalls() {
        }
    }

    private static final class LookaheadSuccess extends Error {
        private LookaheadSuccess() {
        }
    }

    private final boolean jj_2_2(int r4) {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.ssa.SSATransform.placePhi(SSATransform.java:82)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:50)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
        /*
        r3 = this;
        r2 = 1;
        r3.jj_la = r4;
        r1 = r3.token;
        r3.jj_scanpos = r1;
        r3.jj_lastpos = r1;
        r1 = r3.jj_3_2();	 Catch:{ LookaheadSuccess -> 0x0016, all -> 0x001c }
        if (r1 != 0) goto L_0x0014;
    L_0x000f:
        r1 = r2;
    L_0x0010:
        r3.jj_save(r2, r4);
    L_0x0013:
        return r1;
    L_0x0014:
        r1 = 0;
        goto L_0x0010;
    L_0x0016:
        r0 = move-exception;
        r3.jj_save(r2, r4);
        r1 = r2;
        goto L_0x0013;
    L_0x001c:
        r1 = move-exception;
        r3.jj_save(r2, r4);
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.james.mime4j.field.address.parser.AddressListParser.jj_2_2(int):boolean");
    }

    public final void domain() throws org.apache.james.mime4j.field.address.parser.ParseException {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0061 in list [B:21:0x0059]
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
        /*
        r8 = this;
        r7 = 1;
        r6 = -1;
        r1 = new org.apache.james.mime4j.field.address.parser.ASTdomain;
        r3 = 11;
        r1.<init>(r3);
        r0 = 1;
        r3 = r8.jjtree;
        r3.openNodeScope(r1);
        r8.jjtreeOpenNodeScope(r1);
        r3 = r8.jj_ntk;	 Catch:{ all -> 0x002f }
        if (r3 != r6) goto L_0x003b;	 Catch:{ all -> 0x002f }
    L_0x0016:
        r3 = r8.jj_ntk();	 Catch:{ all -> 0x002f }
    L_0x001a:
        switch(r3) {
            case 14: goto L_0x003e;
            case 18: goto L_0x00a3;
            default: goto L_0x001d;
        };	 Catch:{ all -> 0x002f }
    L_0x001d:
        r3 = r8.jj_la1;	 Catch:{ all -> 0x002f }
        r4 = 21;	 Catch:{ all -> 0x002f }
        r5 = r8.jj_gen;	 Catch:{ all -> 0x002f }
        r3[r4] = r5;	 Catch:{ all -> 0x002f }
        r3 = -1;	 Catch:{ all -> 0x002f }
        r8.jj_consume_token(r3);	 Catch:{ all -> 0x002f }
        r3 = new org.apache.james.mime4j.field.address.parser.ParseException;	 Catch:{ all -> 0x002f }
        r3.<init>();	 Catch:{ all -> 0x002f }
        throw r3;	 Catch:{ all -> 0x002f }
    L_0x002f:
        r3 = move-exception;
        if (r0 == 0) goto L_0x003a;
    L_0x0032:
        r4 = r8.jjtree;
        r4.closeNodeScope(r1, r7);
        r8.jjtreeCloseNodeScope(r1);
    L_0x003a:
        throw r3;
    L_0x003b:
        r3 = r8.jj_ntk;	 Catch:{ all -> 0x002f }
        goto L_0x001a;	 Catch:{ all -> 0x002f }
    L_0x003e:
        r3 = 14;	 Catch:{ all -> 0x002f }
        r2 = r8.jj_consume_token(r3);	 Catch:{ all -> 0x002f }
    L_0x0044:
        r3 = r8.jj_ntk;	 Catch:{ all -> 0x002f }
        if (r3 != r6) goto L_0x0062;	 Catch:{ all -> 0x002f }
    L_0x0048:
        r3 = r8.jj_ntk();	 Catch:{ all -> 0x002f }
    L_0x004c:
        switch(r3) {
            case 9: goto L_0x0065;
            case 14: goto L_0x0065;
            default: goto L_0x004f;
        };	 Catch:{ all -> 0x002f }
    L_0x004f:
        r3 = r8.jj_la1;	 Catch:{ all -> 0x002f }
        r4 = 19;	 Catch:{ all -> 0x002f }
        r5 = r8.jj_gen;	 Catch:{ all -> 0x002f }
        r3[r4] = r5;	 Catch:{ all -> 0x002f }
    L_0x0057:
        if (r0 == 0) goto L_0x0061;
    L_0x0059:
        r3 = r8.jjtree;
        r3.closeNodeScope(r1, r7);
        r8.jjtreeCloseNodeScope(r1);
    L_0x0061:
        return;
    L_0x0062:
        r3 = r8.jj_ntk;	 Catch:{ all -> 0x002f }
        goto L_0x004c;	 Catch:{ all -> 0x002f }
    L_0x0065:
        r3 = r8.jj_ntk;	 Catch:{ all -> 0x002f }
        if (r3 != r6) goto L_0x0092;	 Catch:{ all -> 0x002f }
    L_0x0069:
        r3 = r8.jj_ntk();	 Catch:{ all -> 0x002f }
    L_0x006d:
        switch(r3) {
            case 9: goto L_0x0095;
            default: goto L_0x0070;
        };	 Catch:{ all -> 0x002f }
    L_0x0070:
        r3 = r8.jj_la1;	 Catch:{ all -> 0x002f }
        r4 = 20;	 Catch:{ all -> 0x002f }
        r5 = r8.jj_gen;	 Catch:{ all -> 0x002f }
        r3[r4] = r5;	 Catch:{ all -> 0x002f }
    L_0x0078:
        r3 = r2.image;	 Catch:{ all -> 0x002f }
        r4 = r2.image;	 Catch:{ all -> 0x002f }
        r4 = r4.length();	 Catch:{ all -> 0x002f }
        r4 = r4 + -1;	 Catch:{ all -> 0x002f }
        r3 = r3.charAt(r4);	 Catch:{ all -> 0x002f }
        r4 = 46;	 Catch:{ all -> 0x002f }
        if (r3 == r4) goto L_0x009c;	 Catch:{ all -> 0x002f }
    L_0x008a:
        r3 = new org.apache.james.mime4j.field.address.parser.ParseException;	 Catch:{ all -> 0x002f }
        r4 = "Atoms in domain names must be separated by '.'";	 Catch:{ all -> 0x002f }
        r3.<init>(r4);	 Catch:{ all -> 0x002f }
        throw r3;	 Catch:{ all -> 0x002f }
    L_0x0092:
        r3 = r8.jj_ntk;	 Catch:{ all -> 0x002f }
        goto L_0x006d;	 Catch:{ all -> 0x002f }
    L_0x0095:
        r3 = 9;	 Catch:{ all -> 0x002f }
        r2 = r8.jj_consume_token(r3);	 Catch:{ all -> 0x002f }
        goto L_0x0078;	 Catch:{ all -> 0x002f }
    L_0x009c:
        r3 = 14;	 Catch:{ all -> 0x002f }
        r2 = r8.jj_consume_token(r3);	 Catch:{ all -> 0x002f }
        goto L_0x0044;	 Catch:{ all -> 0x002f }
    L_0x00a3:
        r3 = 18;	 Catch:{ all -> 0x002f }
        r8.jj_consume_token(r3);	 Catch:{ all -> 0x002f }
        goto L_0x0057;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.james.mime4j.field.address.parser.AddressListParser.domain():void");
    }

    public static void main(String[] args) throws ParseException {
        while (true) {
            try {
                AddressListParser parser = new AddressListParser(System.in);
                parser.parseLine();
                ((SimpleNode) parser.jjtree.rootNode()).dump("> ");
            } catch (Exception x) {
                x.printStackTrace();
                return;
            }
        }
    }

    public ASTaddress_list parseAddressList() throws ParseException {
        try {
            parseAddressList0();
            return (ASTaddress_list) this.jjtree.rootNode();
        } catch (TokenMgrError tme) {
            throw new ParseException(tme.getMessage());
        }
    }

    public ASTaddress parseAddress() throws ParseException {
        try {
            parseAddress0();
            return (ASTaddress) this.jjtree.rootNode();
        } catch (TokenMgrError tme) {
            throw new ParseException(tme.getMessage());
        }
    }

    public ASTmailbox parseMailbox() throws ParseException {
        try {
            parseMailbox0();
            return (ASTmailbox) this.jjtree.rootNode();
        } catch (TokenMgrError tme) {
            throw new ParseException(tme.getMessage());
        }
    }

    void jjtreeOpenNodeScope(Node n) {
        ((SimpleNode) n).firstToken = getToken(1);
    }

    void jjtreeCloseNodeScope(Node n) {
        ((SimpleNode) n).lastToken = getToken(0);
    }

    public final void parseLine() throws ParseException {
        address_list();
        switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
            case 1:
                jj_consume_token(1);
                break;
            default:
                this.jj_la1[0] = this.jj_gen;
                break;
        }
        jj_consume_token(2);
    }

    public final void parseAddressList0() throws ParseException {
        address_list();
        jj_consume_token(0);
    }

    public final void parseAddress0() throws ParseException {
        address();
        jj_consume_token(0);
    }

    public final void parseMailbox0() throws ParseException {
        mailbox();
        jj_consume_token(0);
    }

    public final void address_list() throws ParseException {
        Node jjtn000 = new ASTaddress_list(1);
        this.jjtree.openNodeScope(jjtn000);
        jjtreeOpenNodeScope(jjtn000);
        try {
            switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                case 6:
                case 14:
                case AddressListParserConstants.QUOTEDSTRING /*31*/:
                    address();
                    break;
                default:
                    this.jj_la1[1] = this.jj_gen;
                    break;
            }
            while (true) {
                switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                    case 3:
                        jj_consume_token(3);
                        switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                            case 6:
                            case 14:
                            case AddressListParserConstants.QUOTEDSTRING /*31*/:
                                address();
                                break;
                            default:
                                this.jj_la1[3] = this.jj_gen;
                                break;
                        }
                    default:
                        this.jj_la1[2] = this.jj_gen;
                        if (1 != null) {
                            this.jjtree.closeNodeScope(jjtn000, true);
                            jjtreeCloseNodeScope(jjtn000);
                            return;
                        }
                        return;
                }
            }
        } catch (Throwable th) {
            if (1 != null) {
                this.jjtree.closeNodeScope(jjtn000, true);
                jjtreeCloseNodeScope(jjtn000);
            }
        }
    }

    public final void address() throws ParseException {
        Node jjtn000 = new ASTaddress(2);
        this.jjtree.openNodeScope(jjtn000);
        jjtreeOpenNodeScope(jjtn000);
        try {
            if (jj_2_1(Integer.MAX_VALUE)) {
                addr_spec();
            } else {
                int jj_ntk;
                if (this.jj_ntk == -1) {
                    jj_ntk = jj_ntk();
                } else {
                    jj_ntk = this.jj_ntk;
                }
                switch (jj_ntk) {
                    case 6:
                        angle_addr();
                        break;
                    case 14:
                    case AddressListParserConstants.QUOTEDSTRING /*31*/:
                        phrase();
                        switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                            case 4:
                                group_body();
                                break;
                            case 6:
                                angle_addr();
                                break;
                            default:
                                this.jj_la1[4] = this.jj_gen;
                                jj_consume_token(-1);
                                throw new ParseException();
                        }
                    default:
                        this.jj_la1[5] = this.jj_gen;
                        jj_consume_token(-1);
                        throw new ParseException();
                }
            }
            if (1 != null) {
                this.jjtree.closeNodeScope(jjtn000, true);
                jjtreeCloseNodeScope(jjtn000);
            }
        } catch (Throwable th) {
            if (1 != null) {
                this.jjtree.closeNodeScope(jjtn000, true);
                jjtreeCloseNodeScope(jjtn000);
            }
        }
    }

    public final void mailbox() throws ParseException {
        Node jjtn000 = new ASTmailbox(3);
        this.jjtree.openNodeScope(jjtn000);
        jjtreeOpenNodeScope(jjtn000);
        try {
            if (jj_2_2(Integer.MAX_VALUE)) {
                addr_spec();
            } else {
                switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                    case 6:
                        angle_addr();
                        break;
                    case 14:
                    case AddressListParserConstants.QUOTEDSTRING /*31*/:
                        name_addr();
                        break;
                    default:
                        this.jj_la1[6] = this.jj_gen;
                        jj_consume_token(-1);
                        throw new ParseException();
                }
            }
            if (1 != null) {
                this.jjtree.closeNodeScope(jjtn000, true);
                jjtreeCloseNodeScope(jjtn000);
            }
        } catch (Throwable th) {
            if (1 != null) {
                this.jjtree.closeNodeScope(jjtn000, true);
                jjtreeCloseNodeScope(jjtn000);
            }
        }
    }

    public final void name_addr() throws ParseException {
        Node jjtn000 = new ASTname_addr(4);
        this.jjtree.openNodeScope(jjtn000);
        jjtreeOpenNodeScope(jjtn000);
        try {
            phrase();
            angle_addr();
            if (1 != null) {
                this.jjtree.closeNodeScope(jjtn000, true);
                jjtreeCloseNodeScope(jjtn000);
            }
        } catch (Throwable th) {
            if (1 != null) {
                this.jjtree.closeNodeScope(jjtn000, true);
                jjtreeCloseNodeScope(jjtn000);
            }
        }
    }

    public final void group_body() throws ParseException {
        Node jjtn000 = new ASTgroup_body(5);
        this.jjtree.openNodeScope(jjtn000);
        jjtreeOpenNodeScope(jjtn000);
        try {
            jj_consume_token(4);
            switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                case 6:
                case 14:
                case AddressListParserConstants.QUOTEDSTRING /*31*/:
                    mailbox();
                    break;
                default:
                    this.jj_la1[7] = this.jj_gen;
                    break;
            }
            while (true) {
                switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                    case 3:
                        jj_consume_token(3);
                        switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                            case 6:
                            case 14:
                            case AddressListParserConstants.QUOTEDSTRING /*31*/:
                                mailbox();
                                break;
                            default:
                                this.jj_la1[9] = this.jj_gen;
                                break;
                        }
                    default:
                        this.jj_la1[8] = this.jj_gen;
                        jj_consume_token(5);
                        if (1 != null) {
                            this.jjtree.closeNodeScope(jjtn000, true);
                            jjtreeCloseNodeScope(jjtn000);
                            return;
                        }
                        return;
                }
            }
        } catch (Throwable th) {
            if (1 != null) {
                this.jjtree.closeNodeScope(jjtn000, true);
                jjtreeCloseNodeScope(jjtn000);
            }
        }
    }

    public final void angle_addr() throws ParseException {
        Node jjtn000 = new ASTangle_addr(6);
        this.jjtree.openNodeScope(jjtn000);
        jjtreeOpenNodeScope(jjtn000);
        try {
            jj_consume_token(6);
            switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk) {
                case 8:
                    route();
                    break;
                default:
                    this.jj_la1[10] = this.jj_gen;
                    break;
            }
            addr_spec();
            jj_consume_token(7);
            if (1 != null) {
                this.jjtree.closeNodeScope(jjtn000, true);
                jjtreeCloseNodeScope(jjtn000);
            }
        } catch (Throwable th) {
            if (1 != null) {
                this.jjtree.closeNodeScope(jjtn000, true);
                jjtreeCloseNodeScope(jjtn000);
            }
        }
    }

    public final void route() throws org.apache.james.mime4j.field.address.parser.ParseException {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:37)
	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:61)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
        /*
        r8 = this;
        r7 = 1;
        r6 = -1;
        r2 = new org.apache.james.mime4j.field.address.parser.ASTroute;
        r3 = 7;
        r2.<init>(r3);
        r0 = 1;
        r3 = r8.jjtree;
        r3.openNodeScope(r2);
        r8.jjtreeOpenNodeScope(r2);
        r3 = 8;
        r8.jj_consume_token(r3);	 Catch:{ Throwable -> 0x005e }
        r8.domain();	 Catch:{ Throwable -> 0x005e }
    L_0x0019:
        r3 = r8.jj_ntk;	 Catch:{ Throwable -> 0x005e }
        if (r3 != r6) goto L_0x003b;	 Catch:{ Throwable -> 0x005e }
    L_0x001d:
        r3 = r8.jj_ntk();	 Catch:{ Throwable -> 0x005e }
    L_0x0021:
        switch(r3) {
            case 3: goto L_0x0042;
            case 8: goto L_0x0042;
            default: goto L_0x0024;
        };	 Catch:{ Throwable -> 0x005e }
    L_0x0024:
        r3 = r8.jj_la1;	 Catch:{ Throwable -> 0x005e }
        r4 = 11;	 Catch:{ Throwable -> 0x005e }
        r5 = r8.jj_gen;	 Catch:{ Throwable -> 0x005e }
        r3[r4] = r5;	 Catch:{ Throwable -> 0x005e }
        r3 = 4;	 Catch:{ Throwable -> 0x005e }
        r8.jj_consume_token(r3);	 Catch:{ Throwable -> 0x005e }
        if (r0 == 0) goto L_0x003a;
    L_0x0032:
        r3 = r8.jjtree;
        r3.closeNodeScope(r2, r7);
        r8.jjtreeCloseNodeScope(r2);
    L_0x003a:
        return;
    L_0x003b:
        r3 = r8.jj_ntk;	 Catch:{ Throwable -> 0x005e }
        goto L_0x0021;	 Catch:{ Throwable -> 0x005e }
    L_0x003e:
        r3 = 3;	 Catch:{ Throwable -> 0x005e }
        r8.jj_consume_token(r3);	 Catch:{ Throwable -> 0x005e }
    L_0x0042:
        r3 = r8.jj_ntk;	 Catch:{ Throwable -> 0x005e }
        if (r3 != r6) goto L_0x007a;	 Catch:{ Throwable -> 0x005e }
    L_0x0046:
        r3 = r8.jj_ntk();	 Catch:{ Throwable -> 0x005e }
    L_0x004a:
        switch(r3) {
            case 3: goto L_0x003e;
            default: goto L_0x004d;
        };	 Catch:{ Throwable -> 0x005e }
    L_0x004d:
        r3 = r8.jj_la1;	 Catch:{ Throwable -> 0x005e }
        r4 = 12;	 Catch:{ Throwable -> 0x005e }
        r5 = r8.jj_gen;	 Catch:{ Throwable -> 0x005e }
        r3[r4] = r5;	 Catch:{ Throwable -> 0x005e }
        r3 = 8;	 Catch:{ Throwable -> 0x005e }
        r8.jj_consume_token(r3);	 Catch:{ Throwable -> 0x005e }
        r8.domain();	 Catch:{ Throwable -> 0x005e }
        goto L_0x0019;
    L_0x005e:
        r1 = move-exception;
        if (r0 == 0) goto L_0x007d;
    L_0x0061:
        r3 = r8.jjtree;	 Catch:{ all -> 0x006e }
        r3.clearNodeScope(r2);	 Catch:{ all -> 0x006e }
        r0 = 0;	 Catch:{ all -> 0x006e }
    L_0x0067:
        r3 = r1 instanceof java.lang.RuntimeException;	 Catch:{ all -> 0x006e }
        if (r3 == 0) goto L_0x0083;	 Catch:{ all -> 0x006e }
    L_0x006b:
        r1 = (java.lang.RuntimeException) r1;	 Catch:{ all -> 0x006e }
        throw r1;	 Catch:{ all -> 0x006e }
    L_0x006e:
        r3 = move-exception;
        if (r0 == 0) goto L_0x0079;
    L_0x0071:
        r4 = r8.jjtree;
        r4.closeNodeScope(r2, r7);
        r8.jjtreeCloseNodeScope(r2);
    L_0x0079:
        throw r3;
    L_0x007a:
        r3 = r8.jj_ntk;	 Catch:{ Throwable -> 0x005e }
        goto L_0x004a;
    L_0x007d:
        r3 = r8.jjtree;	 Catch:{ all -> 0x006e }
        r3.popNode();	 Catch:{ all -> 0x006e }
        goto L_0x0067;	 Catch:{ all -> 0x006e }
    L_0x0083:
        r3 = r1 instanceof org.apache.james.mime4j.field.address.parser.ParseException;	 Catch:{ all -> 0x006e }
        if (r3 == 0) goto L_0x008a;	 Catch:{ all -> 0x006e }
    L_0x0087:
        r1 = (org.apache.james.mime4j.field.address.parser.ParseException) r1;	 Catch:{ all -> 0x006e }
        throw r1;	 Catch:{ all -> 0x006e }
    L_0x008a:
        r1 = (java.lang.Error) r1;	 Catch:{ all -> 0x006e }
        throw r1;	 Catch:{ all -> 0x006e }
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.james.mime4j.field.address.parser.AddressListParser.route():void");
    }

    public final void phrase() throws org.apache.james.mime4j.field.address.parser.ParseException {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:37)
	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:61)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
        /*
        r6 = this;
        r5 = 1;
        r3 = -1;
        r1 = new org.apache.james.mime4j.field.address.parser.ASTphrase;
        r2 = 8;
        r1.<init>(r2);
        r0 = 1;
        r2 = r6.jjtree;
        r2.openNodeScope(r1);
        r6.jjtreeOpenNodeScope(r1);
    L_0x0012:
        r2 = r6.jj_ntk;	 Catch:{ all -> 0x002f }
        if (r2 != r3) goto L_0x003b;	 Catch:{ all -> 0x002f }
    L_0x0016:
        r2 = r6.jj_ntk();	 Catch:{ all -> 0x002f }
    L_0x001a:
        switch(r2) {
            case 14: goto L_0x003e;
            case 31: goto L_0x0061;
            default: goto L_0x001d;
        };	 Catch:{ all -> 0x002f }
    L_0x001d:
        r2 = r6.jj_la1;	 Catch:{ all -> 0x002f }
        r3 = 13;	 Catch:{ all -> 0x002f }
        r4 = r6.jj_gen;	 Catch:{ all -> 0x002f }
        r2[r3] = r4;	 Catch:{ all -> 0x002f }
        r2 = -1;	 Catch:{ all -> 0x002f }
        r6.jj_consume_token(r2);	 Catch:{ all -> 0x002f }
        r2 = new org.apache.james.mime4j.field.address.parser.ParseException;	 Catch:{ all -> 0x002f }
        r2.<init>();	 Catch:{ all -> 0x002f }
        throw r2;	 Catch:{ all -> 0x002f }
    L_0x002f:
        r2 = move-exception;
        if (r0 == 0) goto L_0x003a;
    L_0x0032:
        r3 = r6.jjtree;
        r3.closeNodeScope(r1, r5);
        r6.jjtreeCloseNodeScope(r1);
    L_0x003a:
        throw r2;
    L_0x003b:
        r2 = r6.jj_ntk;	 Catch:{ all -> 0x002f }
        goto L_0x001a;	 Catch:{ all -> 0x002f }
    L_0x003e:
        r2 = 14;	 Catch:{ all -> 0x002f }
        r6.jj_consume_token(r2);	 Catch:{ all -> 0x002f }
    L_0x0043:
        r2 = r6.jj_ntk;	 Catch:{ all -> 0x002f }
        if (r2 != r3) goto L_0x0067;	 Catch:{ all -> 0x002f }
    L_0x0047:
        r2 = r6.jj_ntk();	 Catch:{ all -> 0x002f }
    L_0x004b:
        switch(r2) {
            case 14: goto L_0x0012;
            case 31: goto L_0x0012;
            default: goto L_0x004e;
        };	 Catch:{ all -> 0x002f }
    L_0x004e:
        r2 = r6.jj_la1;	 Catch:{ all -> 0x002f }
        r3 = 14;	 Catch:{ all -> 0x002f }
        r4 = r6.jj_gen;	 Catch:{ all -> 0x002f }
        r2[r3] = r4;	 Catch:{ all -> 0x002f }
        if (r0 == 0) goto L_0x0060;
    L_0x0058:
        r2 = r6.jjtree;
        r2.closeNodeScope(r1, r5);
        r6.jjtreeCloseNodeScope(r1);
    L_0x0060:
        return;
    L_0x0061:
        r2 = 31;
        r6.jj_consume_token(r2);	 Catch:{ all -> 0x002f }
        goto L_0x0043;	 Catch:{ all -> 0x002f }
    L_0x0067:
        r2 = r6.jj_ntk;	 Catch:{ all -> 0x002f }
        goto L_0x004b;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.james.mime4j.field.address.parser.AddressListParser.phrase():void");
    }

    public final void addr_spec() throws ParseException {
        Node jjtn000 = new ASTaddr_spec(9);
        this.jjtree.openNodeScope(jjtn000);
        jjtreeOpenNodeScope(jjtn000);
        try {
            local_part();
            jj_consume_token(8);
            domain();
            if (1 != null) {
                this.jjtree.closeNodeScope(jjtn000, true);
                jjtreeCloseNodeScope(jjtn000);
            }
        } catch (Throwable th) {
            if (1 != null) {
                this.jjtree.closeNodeScope(jjtn000, true);
                jjtreeCloseNodeScope(jjtn000);
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void local_part() throws org.apache.james.mime4j.field.address.parser.ParseException {
        /*
        r9 = this;
        r8 = 1;
        r7 = 31;
        r6 = -1;
        r1 = new org.apache.james.mime4j.field.address.parser.ASTlocal_part;
        r3 = 10;
        r1.<init>(r3);
        r0 = 1;
        r3 = r9.jjtree;
        r3.openNodeScope(r1);
        r9.jjtreeOpenNodeScope(r1);
        r3 = r9.jj_ntk;	 Catch:{ all -> 0x0031 }
        if (r3 != r6) goto L_0x003d;
    L_0x0018:
        r3 = r9.jj_ntk();	 Catch:{ all -> 0x0031 }
    L_0x001c:
        switch(r3) {
            case 14: goto L_0x0040;
            case 31: goto L_0x0064;
            default: goto L_0x001f;
        };	 Catch:{ all -> 0x0031 }
    L_0x001f:
        r3 = r9.jj_la1;	 Catch:{ all -> 0x0031 }
        r4 = 15;
        r5 = r9.jj_gen;	 Catch:{ all -> 0x0031 }
        r3[r4] = r5;	 Catch:{ all -> 0x0031 }
        r3 = -1;
        r9.jj_consume_token(r3);	 Catch:{ all -> 0x0031 }
        r3 = new org.apache.james.mime4j.field.address.parser.ParseException;	 Catch:{ all -> 0x0031 }
        r3.<init>();	 Catch:{ all -> 0x0031 }
        throw r3;	 Catch:{ all -> 0x0031 }
    L_0x0031:
        r3 = move-exception;
        if (r0 == 0) goto L_0x003c;
    L_0x0034:
        r4 = r9.jjtree;
        r4.closeNodeScope(r1, r8);
        r9.jjtreeCloseNodeScope(r1);
    L_0x003c:
        throw r3;
    L_0x003d:
        r3 = r9.jj_ntk;	 Catch:{ all -> 0x0031 }
        goto L_0x001c;
    L_0x0040:
        r3 = 14;
        r2 = r9.jj_consume_token(r3);	 Catch:{ all -> 0x0031 }
    L_0x0046:
        r3 = r9.jj_ntk;	 Catch:{ all -> 0x0031 }
        if (r3 != r6) goto L_0x006b;
    L_0x004a:
        r3 = r9.jj_ntk();	 Catch:{ all -> 0x0031 }
    L_0x004e:
        switch(r3) {
            case 9: goto L_0x006e;
            case 14: goto L_0x006e;
            case 31: goto L_0x006e;
            default: goto L_0x0051;
        };	 Catch:{ all -> 0x0031 }
    L_0x0051:
        r3 = r9.jj_la1;	 Catch:{ all -> 0x0031 }
        r4 = 16;
        r5 = r9.jj_gen;	 Catch:{ all -> 0x0031 }
        r3[r4] = r5;	 Catch:{ all -> 0x0031 }
        if (r0 == 0) goto L_0x0063;
    L_0x005b:
        r3 = r9.jjtree;
        r3.closeNodeScope(r1, r8);
        r9.jjtreeCloseNodeScope(r1);
    L_0x0063:
        return;
    L_0x0064:
        r3 = 31;
        r2 = r9.jj_consume_token(r3);	 Catch:{ all -> 0x0031 }
        goto L_0x0046;
    L_0x006b:
        r3 = r9.jj_ntk;	 Catch:{ all -> 0x0031 }
        goto L_0x004e;
    L_0x006e:
        r3 = r9.jj_ntk;	 Catch:{ all -> 0x0031 }
        if (r3 != r6) goto L_0x009f;
    L_0x0072:
        r3 = r9.jj_ntk();	 Catch:{ all -> 0x0031 }
    L_0x0076:
        switch(r3) {
            case 9: goto L_0x00a2;
            default: goto L_0x0079;
        };	 Catch:{ all -> 0x0031 }
    L_0x0079:
        r3 = r9.jj_la1;	 Catch:{ all -> 0x0031 }
        r4 = 17;
        r5 = r9.jj_gen;	 Catch:{ all -> 0x0031 }
        r3[r4] = r5;	 Catch:{ all -> 0x0031 }
    L_0x0081:
        r3 = r2.kind;	 Catch:{ all -> 0x0031 }
        if (r3 == r7) goto L_0x0097;
    L_0x0085:
        r3 = r2.image;	 Catch:{ all -> 0x0031 }
        r4 = r2.image;	 Catch:{ all -> 0x0031 }
        r4 = r4.length();	 Catch:{ all -> 0x0031 }
        r4 = r4 + -1;
        r3 = r3.charAt(r4);	 Catch:{ all -> 0x0031 }
        r4 = 46;
        if (r3 == r4) goto L_0x00a9;
    L_0x0097:
        r3 = new org.apache.james.mime4j.field.address.parser.ParseException;	 Catch:{ all -> 0x0031 }
        r4 = "Words in local part must be separated by '.'";
        r3.<init>(r4);	 Catch:{ all -> 0x0031 }
        throw r3;	 Catch:{ all -> 0x0031 }
    L_0x009f:
        r3 = r9.jj_ntk;	 Catch:{ all -> 0x0031 }
        goto L_0x0076;
    L_0x00a2:
        r3 = 9;
        r2 = r9.jj_consume_token(r3);	 Catch:{ all -> 0x0031 }
        goto L_0x0081;
    L_0x00a9:
        r3 = r9.jj_ntk;	 Catch:{ all -> 0x0031 }
        if (r3 != r6) goto L_0x00c6;
    L_0x00ad:
        r3 = r9.jj_ntk();	 Catch:{ all -> 0x0031 }
    L_0x00b1:
        switch(r3) {
            case 14: goto L_0x00c9;
            case 31: goto L_0x00d1;
            default: goto L_0x00b4;
        };	 Catch:{ all -> 0x0031 }
    L_0x00b4:
        r3 = r9.jj_la1;	 Catch:{ all -> 0x0031 }
        r4 = 18;
        r5 = r9.jj_gen;	 Catch:{ all -> 0x0031 }
        r3[r4] = r5;	 Catch:{ all -> 0x0031 }
        r3 = -1;
        r9.jj_consume_token(r3);	 Catch:{ all -> 0x0031 }
        r3 = new org.apache.james.mime4j.field.address.parser.ParseException;	 Catch:{ all -> 0x0031 }
        r3.<init>();	 Catch:{ all -> 0x0031 }
        throw r3;	 Catch:{ all -> 0x0031 }
    L_0x00c6:
        r3 = r9.jj_ntk;	 Catch:{ all -> 0x0031 }
        goto L_0x00b1;
    L_0x00c9:
        r3 = 14;
        r2 = r9.jj_consume_token(r3);	 Catch:{ all -> 0x0031 }
        goto L_0x0046;
    L_0x00d1:
        r3 = 31;
        r2 = r9.jj_consume_token(r3);	 Catch:{ all -> 0x0031 }
        goto L_0x0046;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.james.mime4j.field.address.parser.AddressListParser.local_part():void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final boolean jj_2_1(int r5) {
        /*
        r4 = this;
        r1 = 1;
        r2 = 0;
        r4.jj_la = r5;
        r3 = r4.token;
        r4.jj_scanpos = r3;
        r4.jj_lastpos = r3;
        r3 = r4.jj_3_1();	 Catch:{ LookaheadSuccess -> 0x0016, all -> 0x001b }
        if (r3 != 0) goto L_0x0014;
    L_0x0010:
        r4.jj_save(r2, r5);
    L_0x0013:
        return r1;
    L_0x0014:
        r1 = r2;
        goto L_0x0010;
    L_0x0016:
        r0 = move-exception;
        r4.jj_save(r2, r5);
        goto L_0x0013;
    L_0x001b:
        r1 = move-exception;
        r4.jj_save(r2, r5);
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.james.mime4j.field.address.parser.AddressListParser.jj_2_1(int):boolean");
    }

    private final boolean jj_3R_12() {
        if (jj_scan_token(14)) {
            return true;
        }
        Token xsp;
        do {
            xsp = this.jj_scanpos;
        } while (!jj_3R_13());
        this.jj_scanpos = xsp;
        return false;
    }

    private final boolean jj_3R_10() {
        Token xsp = this.jj_scanpos;
        if (jj_3R_12()) {
            this.jj_scanpos = xsp;
            if (jj_scan_token(18)) {
                return true;
            }
        }
        return false;
    }

    private final boolean jj_3_2() {
        if (jj_3R_8()) {
            return true;
        }
        return false;
    }

    private final boolean jj_3R_9() {
        Token xsp = this.jj_scanpos;
        if (jj_scan_token(14)) {
            this.jj_scanpos = xsp;
            if (jj_scan_token(31)) {
                return true;
            }
        }
        do {
            xsp = this.jj_scanpos;
        } while (!jj_3R_11());
        this.jj_scanpos = xsp;
        return false;
    }

    private final boolean jj_3R_11() {
        Token xsp = this.jj_scanpos;
        if (jj_scan_token(9)) {
            this.jj_scanpos = xsp;
        }
        xsp = this.jj_scanpos;
        if (jj_scan_token(14)) {
            this.jj_scanpos = xsp;
            if (jj_scan_token(31)) {
                return true;
            }
        }
        return false;
    }

    private final boolean jj_3R_13() {
        Token xsp = this.jj_scanpos;
        if (jj_scan_token(9)) {
            this.jj_scanpos = xsp;
        }
        if (jj_scan_token(14)) {
            return true;
        }
        return false;
    }

    private final boolean jj_3R_8() {
        if (jj_3R_9() || jj_scan_token(8) || jj_3R_10()) {
            return true;
        }
        return false;
    }

    private final boolean jj_3_1() {
        if (jj_3R_8()) {
            return true;
        }
        return false;
    }

    static {
        jj_la1_0();
        jj_la1_1();
    }

    private static void jj_la1_0() {
        jj_la1_0 = new int[]{2, -2147467200, 8, -2147467200, 80, -2147467200, -2147467200, -2147467200, 8, -2147467200, 256, 264, 8, -2147467264, -2147467264, -2147467264, -2147466752, 512, -2147467264, 16896, 512, 278528};
    }

    private static void jj_la1_1() {
        jj_la1_1 = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    }

    public AddressListParser(InputStream stream) {
        this(stream, null);
    }

    public AddressListParser(InputStream stream, String encoding) {
        this.jjtree = new JJTAddressListParserState();
        this.lookingAhead = false;
        this.jj_la1 = new int[22];
        this.jj_2_rtns = new JJCalls[2];
        this.jj_rescan = false;
        this.jj_gc = 0;
        this.jj_ls = new LookaheadSuccess();
        this.jj_expentries = new Vector();
        this.jj_kind = -1;
        this.jj_lasttokens = new int[100];
        try {
            int i;
            this.jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1);
            this.token_source = new AddressListParserTokenManager(this.jj_input_stream);
            this.token = new Token();
            this.jj_ntk = -1;
            this.jj_gen = 0;
            for (i = 0; i < 22; i++) {
                this.jj_la1[i] = -1;
            }
            for (i = 0; i < this.jj_2_rtns.length; i++) {
                this.jj_2_rtns[i] = new JJCalls();
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public void ReInit(InputStream stream) {
        ReInit(stream, null);
    }

    public void ReInit(InputStream stream, String encoding) {
        try {
            int i;
            this.jj_input_stream.ReInit(stream, encoding, 1, 1);
            this.token_source.ReInit(this.jj_input_stream);
            this.token = new Token();
            this.jj_ntk = -1;
            this.jjtree.reset();
            this.jj_gen = 0;
            for (i = 0; i < 22; i++) {
                this.jj_la1[i] = -1;
            }
            for (i = 0; i < this.jj_2_rtns.length; i++) {
                this.jj_2_rtns[i] = new JJCalls();
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public AddressListParser(Reader stream) {
        int i;
        this.jjtree = new JJTAddressListParserState();
        this.lookingAhead = false;
        this.jj_la1 = new int[22];
        this.jj_2_rtns = new JJCalls[2];
        this.jj_rescan = false;
        this.jj_gc = 0;
        this.jj_ls = new LookaheadSuccess();
        this.jj_expentries = new Vector();
        this.jj_kind = -1;
        this.jj_lasttokens = new int[100];
        this.jj_input_stream = new SimpleCharStream(stream, 1, 1);
        this.token_source = new AddressListParserTokenManager(this.jj_input_stream);
        this.token = new Token();
        this.jj_ntk = -1;
        this.jj_gen = 0;
        for (i = 0; i < 22; i++) {
            this.jj_la1[i] = -1;
        }
        for (i = 0; i < this.jj_2_rtns.length; i++) {
            this.jj_2_rtns[i] = new JJCalls();
        }
    }

    public void ReInit(Reader stream) {
        int i;
        this.jj_input_stream.ReInit(stream, 1, 1);
        this.token_source.ReInit(this.jj_input_stream);
        this.token = new Token();
        this.jj_ntk = -1;
        this.jjtree.reset();
        this.jj_gen = 0;
        for (i = 0; i < 22; i++) {
            this.jj_la1[i] = -1;
        }
        for (i = 0; i < this.jj_2_rtns.length; i++) {
            this.jj_2_rtns[i] = new JJCalls();
        }
    }

    public AddressListParser(AddressListParserTokenManager tm) {
        int i;
        this.jjtree = new JJTAddressListParserState();
        this.lookingAhead = false;
        this.jj_la1 = new int[22];
        this.jj_2_rtns = new JJCalls[2];
        this.jj_rescan = false;
        this.jj_gc = 0;
        this.jj_ls = new LookaheadSuccess();
        this.jj_expentries = new Vector();
        this.jj_kind = -1;
        this.jj_lasttokens = new int[100];
        this.token_source = tm;
        this.token = new Token();
        this.jj_ntk = -1;
        this.jj_gen = 0;
        for (i = 0; i < 22; i++) {
            this.jj_la1[i] = -1;
        }
        for (i = 0; i < this.jj_2_rtns.length; i++) {
            this.jj_2_rtns[i] = new JJCalls();
        }
    }

    public void ReInit(AddressListParserTokenManager tm) {
        int i;
        this.token_source = tm;
        this.token = new Token();
        this.jj_ntk = -1;
        this.jjtree.reset();
        this.jj_gen = 0;
        for (i = 0; i < 22; i++) {
            this.jj_la1[i] = -1;
        }
        for (i = 0; i < this.jj_2_rtns.length; i++) {
            this.jj_2_rtns[i] = new JJCalls();
        }
    }

    private final Token jj_consume_token(int kind) throws ParseException {
        Token oldToken = this.token;
        if (oldToken.next != null) {
            this.token = this.token.next;
        } else {
            Token token = this.token;
            Token nextToken = this.token_source.getNextToken();
            token.next = nextToken;
            this.token = nextToken;
        }
        this.jj_ntk = -1;
        if (this.token.kind == kind) {
            this.jj_gen++;
            int i = this.jj_gc + 1;
            this.jj_gc = i;
            if (i > 100) {
                this.jj_gc = 0;
                for (JJCalls c : this.jj_2_rtns) {
                    for (JJCalls c2 = this.jj_2_rtns[i]; c2 != null; c2 = c2.next) {
                        if (c2.gen < this.jj_gen) {
                            c2.first = null;
                        }
                    }
                }
            }
            return this.token;
        }
        this.token = oldToken;
        this.jj_kind = kind;
        throw generateParseException();
    }

    private final boolean jj_scan_token(int kind) {
        if (this.jj_scanpos == this.jj_lastpos) {
            this.jj_la--;
            Token token;
            if (this.jj_scanpos.next == null) {
                token = this.jj_scanpos;
                Token nextToken = this.token_source.getNextToken();
                token.next = nextToken;
                this.jj_scanpos = nextToken;
                this.jj_lastpos = nextToken;
            } else {
                token = this.jj_scanpos.next;
                this.jj_scanpos = token;
                this.jj_lastpos = token;
            }
        } else {
            this.jj_scanpos = this.jj_scanpos.next;
        }
        if (this.jj_rescan) {
            int i = 0;
            Token tok = this.token;
            while (tok != null && tok != this.jj_scanpos) {
                i++;
                tok = tok.next;
            }
            if (tok != null) {
                jj_add_error_token(kind, i);
            }
        }
        if (this.jj_scanpos.kind != kind) {
            return true;
        }
        if (this.jj_la != 0 || this.jj_scanpos != this.jj_lastpos) {
            return false;
        }
        throw this.jj_ls;
    }

    public final Token getNextToken() {
        if (this.token.next != null) {
            this.token = this.token.next;
        } else {
            Token token = this.token;
            Token nextToken = this.token_source.getNextToken();
            token.next = nextToken;
            this.token = nextToken;
        }
        this.jj_ntk = -1;
        this.jj_gen++;
        return this.token;
    }

    public final Token getToken(int index) {
        int i = 0;
        Token t = this.lookingAhead ? this.jj_scanpos : this.token;
        while (i < index) {
            Token t2;
            if (t.next != null) {
                t2 = t.next;
            } else {
                t2 = this.token_source.getNextToken();
                t.next = t2;
            }
            i++;
            t = t2;
        }
        return t;
    }

    private final int jj_ntk() {
        Token token = this.token.next;
        this.jj_nt = token;
        if (token == null) {
            token = this.token;
            Token nextToken = this.token_source.getNextToken();
            token.next = nextToken;
            int i = nextToken.kind;
            this.jj_ntk = i;
            return i;
        }
        i = this.jj_nt.kind;
        this.jj_ntk = i;
        return i;
    }

    private void jj_add_error_token(int kind, int pos) {
        if (pos < 100) {
            int[] iArr;
            if (pos == this.jj_endpos + 1) {
                iArr = this.jj_lasttokens;
                int i = this.jj_endpos;
                this.jj_endpos = i + 1;
                iArr[i] = kind;
            } else if (this.jj_endpos != 0) {
                int i2;
                this.jj_expentry = new int[this.jj_endpos];
                for (i2 = 0; i2 < this.jj_endpos; i2++) {
                    this.jj_expentry[i2] = this.jj_lasttokens[i2];
                }
                boolean exists = false;
                Enumeration e = this.jj_expentries.elements();
                while (e.hasMoreElements()) {
                    int[] oldentry = (int[]) e.nextElement();
                    if (oldentry.length == this.jj_expentry.length) {
                        exists = true;
                        for (i2 = 0; i2 < this.jj_expentry.length; i2++) {
                            if (oldentry[i2] != this.jj_expentry[i2]) {
                                exists = false;
                                break;
                            }
                        }
                        if (exists) {
                            break;
                        }
                    }
                }
                if (!exists) {
                    this.jj_expentries.addElement(this.jj_expentry);
                }
                if (pos != 0) {
                    iArr = this.jj_lasttokens;
                    this.jj_endpos = pos;
                    iArr[pos - 1] = kind;
                }
            }
        }
    }

    public ParseException generateParseException() {
        int i;
        this.jj_expentries.removeAllElements();
        boolean[] la1tokens = new boolean[34];
        for (i = 0; i < 34; i++) {
            la1tokens[i] = false;
        }
        if (this.jj_kind >= 0) {
            la1tokens[this.jj_kind] = true;
            this.jj_kind = -1;
        }
        for (i = 0; i < 22; i++) {
            if (this.jj_la1[i] == this.jj_gen) {
                for (int j = 0; j < 32; j++) {
                    if ((jj_la1_0[i] & (1 << j)) != 0) {
                        la1tokens[j] = true;
                    }
                    if ((jj_la1_1[i] & (1 << j)) != 0) {
                        la1tokens[j + 32] = true;
                    }
                }
            }
        }
        for (i = 0; i < 34; i++) {
            if (la1tokens[i]) {
                this.jj_expentry = new int[1];
                this.jj_expentry[0] = i;
                this.jj_expentries.addElement(this.jj_expentry);
            }
        }
        this.jj_endpos = 0;
        jj_rescan_token();
        jj_add_error_token(0, 0);
        int[][] exptokseq = new int[this.jj_expentries.size()][];
        for (i = 0; i < this.jj_expentries.size(); i++) {
            exptokseq[i] = (int[]) this.jj_expentries.elementAt(i);
        }
        return new ParseException(this.token, exptokseq, tokenImage);
    }

    public final void enable_tracing() {
    }

    public final void disable_tracing() {
    }

    private final void jj_rescan_token() {
        this.jj_rescan = true;
        for (int i = 0; i < 2; i++) {
            try {
                JJCalls p = this.jj_2_rtns[i];
                do {
                    if (p.gen > this.jj_gen) {
                        this.jj_la = p.arg;
                        Token token = p.first;
                        this.jj_scanpos = token;
                        this.jj_lastpos = token;
                        switch (i) {
                            case 0:
                                jj_3_1();
                                break;
                            case 1:
                                jj_3_2();
                                break;
                        }
                    }
                    p = p.next;
                } while (p != null);
            } catch (LookaheadSuccess e) {
            }
        }
        this.jj_rescan = false;
    }

    private final void jj_save(int index, int xla) {
        JJCalls p = this.jj_2_rtns[index];
        while (p.gen > this.jj_gen) {
            if (p.next == null) {
                JJCalls p2 = new JJCalls();
                p.next = p2;
                p = p2;
                break;
            }
            p = p.next;
        }
        p.gen = (this.jj_gen + xla) - this.jj_la;
        p.first = this.token;
        p.arg = xla;
    }
}
