package org.apache.james.mime4j.field.contenttype.parser;

import android.support.v4.widget.ExploreByTouchHelper;
import com.kayac.lobi.libnakamap.rec.LobiRec;
import java.io.IOException;
import java.io.PrintStream;
import org.apache.commons.io.IOUtils;

public class ContentTypeParserTokenManager implements ContentTypeParserConstants {
    static int commentNest;
    static final long[] jjbitVec0 = new long[]{0, 0, -1, -1};
    public static final int[] jjnewLexState = new int[]{-1, -1, -1, -1, -1, -1, -1, 1, 0, -1, 2, -1, -1, -1, -1, -1, 3, -1, -1, 0, -1, -1, -1, -1};
    static final int[] jjnextStates = new int[0];
    public static final String[] jjstrLiteralImages = new String[]{"", "\r", IOUtils.LINE_SEPARATOR_UNIX, "/", ";", "=", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null};
    static final long[] jjtoMore = new long[]{523904};
    static final long[] jjtoSkip = new long[]{320};
    static final long[] jjtoSpecial = new long[]{64};
    static final long[] jjtoToken = new long[]{3670079};
    public static final String[] lexStateNames = new String[]{"DEFAULT", "INCOMMENT", "NESTED_COMMENT", "INQUOTEDSTRING"};
    protected char curChar;
    int curLexState;
    public PrintStream debugStream;
    int defaultLexState;
    StringBuffer image;
    protected SimpleCharStream input_stream;
    int jjimageLen;
    int jjmatchedKind;
    int jjmatchedPos;
    int jjnewStateCnt;
    int jjround;
    private final int[] jjrounds;
    private final int[] jjstateSet;
    int lengthOfMatch;

    public void setDebugStream(PrintStream ds) {
        this.debugStream = ds;
    }

    private final int jjStopStringLiteralDfa_0(int pos, long active0) {
        return -1;
    }

    private final int jjStartNfa_0(int pos, long active0) {
        return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
    }

    private final int jjStopAtPos(int pos, int kind) {
        this.jjmatchedKind = kind;
        this.jjmatchedPos = pos;
        return pos + 1;
    }

    private final int jjStartNfaWithStates_0(int pos, int kind, int state) {
        this.jjmatchedKind = kind;
        this.jjmatchedPos = pos;
        try {
            this.curChar = this.input_stream.readChar();
            return jjMoveNfa_0(state, pos + 1);
        } catch (IOException e) {
            return pos + 1;
        }
    }

    private final int jjMoveStringLiteralDfa0_0() {
        switch (this.curChar) {
            case '\n':
                return jjStartNfaWithStates_0(0, 2, 2);
            case '\r':
                return jjStartNfaWithStates_0(0, 1, 2);
            case '\"':
                return jjStopAtPos(0, 16);
            case '(':
                return jjStopAtPos(0, 7);
            case '/':
                return jjStopAtPos(0, 3);
            case ';':
                return jjStopAtPos(0, 4);
            case '=':
                return jjStopAtPos(0, 5);
            default:
                return jjMoveNfa_0(3, 0);
        }
    }

    private final void jjCheckNAdd(int state) {
        if (this.jjrounds[state] != this.jjround) {
            int[] iArr = this.jjstateSet;
            int i = this.jjnewStateCnt;
            this.jjnewStateCnt = i + 1;
            iArr[i] = state;
            this.jjrounds[state] = this.jjround;
        }
    }

    private final void jjAddStates(int start, int end) {
        while (true) {
            int[] iArr = this.jjstateSet;
            int i = this.jjnewStateCnt;
            this.jjnewStateCnt = i + 1;
            iArr[i] = jjnextStates[start];
            int start2 = start + 1;
            if (start != end) {
                start = start2;
            } else {
                return;
            }
        }
    }

    private final void jjCheckNAddTwoStates(int state1, int state2) {
        jjCheckNAdd(state1);
        jjCheckNAdd(state2);
    }

    private final void jjCheckNAddStates(int start, int end) {
        while (true) {
            jjCheckNAdd(jjnextStates[start]);
            int start2 = start + 1;
            if (start != end) {
                start = start2;
            } else {
                return;
            }
        }
    }

    private final void jjCheckNAddStates(int start) {
        jjCheckNAdd(jjnextStates[start]);
        jjCheckNAdd(jjnextStates[start + 1]);
    }

    private final int jjMoveNfa_0(int startState, int curPos) {
        int startsAt = 0;
        this.jjnewStateCnt = 3;
        int i = 1;
        this.jjstateSet[0] = startState;
        int kind = Integer.MAX_VALUE;
        while (true) {
            int i2 = this.jjround + 1;
            this.jjround = i2;
            if (i2 == Integer.MAX_VALUE) {
                ReInitRounds();
            }
            long l;
            if (this.curChar >= '@') {
                if (this.curChar >= '') {
                    int i22 = (this.curChar & 255) >> 6;
                    long l2 = 1 << (this.curChar & 63);
                    do {
                        i--;
                        switch (this.jjstateSet[i]) {
                            case 2:
                            case 3:
                                if ((jjbitVec0[i22] & l2) == 0) {
                                    continue;
                                } else {
                                    if (kind > 21) {
                                        kind = 21;
                                    }
                                    jjCheckNAdd(2);
                                    continue;
                                }
                            default:
                                break;
                        }
                    } while (i != startsAt);
                }
                l = 1 << (this.curChar & 63);
                do {
                    i--;
                    switch (this.jjstateSet[i]) {
                        case 2:
                        case 3:
                            if ((-939524098 & l) != 0) {
                                kind = 21;
                                jjCheckNAdd(2);
                                continue;
                            } else {
                                continue;
                            }
                        default:
                            break;
                    }
                } while (i != startsAt);
            }
            l = 1 << this.curChar;
            do {
                i--;
                switch (this.jjstateSet[i]) {
                    case 0:
                        if ((4294967808L & l) != 0) {
                            kind = 6;
                            jjCheckNAdd(0);
                            continue;
                        } else {
                            continue;
                        }
                    case 1:
                        if ((287948901175001088L & l) == 0) {
                            continue;
                        } else {
                            if (kind > 20) {
                                kind = 20;
                            }
                            jjCheckNAdd(1);
                            continue;
                        }
                    case 2:
                        if ((288068726467591679L & l) == 0) {
                            continue;
                        } else {
                            if (kind > 21) {
                                kind = 21;
                            }
                            jjCheckNAdd(2);
                            continue;
                        }
                    case 3:
                        if ((288068726467591679L & l) != 0) {
                            if (kind > 21) {
                                kind = 21;
                            }
                            jjCheckNAdd(2);
                        } else if ((4294967808L & l) != 0) {
                            if (kind > 6) {
                                kind = 6;
                            }
                            jjCheckNAdd(0);
                        }
                        if ((287948901175001088L & l) == 0) {
                            continue;
                        } else {
                            if (kind > 20) {
                                kind = 20;
                            }
                            jjCheckNAdd(1);
                            continue;
                        }
                    default:
                        break;
                }
            } while (i != startsAt);
            if (kind != Integer.MAX_VALUE) {
                this.jjmatchedKind = kind;
                this.jjmatchedPos = curPos;
                kind = Integer.MAX_VALUE;
            }
            curPos++;
            i = this.jjnewStateCnt;
            this.jjnewStateCnt = startsAt;
            startsAt = 3 - startsAt;
            if (i != startsAt) {
                try {
                    this.curChar = this.input_stream.readChar();
                } catch (IOException e) {
                }
            }
            return curPos;
        }
    }

    private final int jjStopStringLiteralDfa_1(int pos, long active0) {
        return -1;
    }

    private final int jjStartNfa_1(int pos, long active0) {
        return jjMoveNfa_1(jjStopStringLiteralDfa_1(pos, active0), pos + 1);
    }

    private final int jjStartNfaWithStates_1(int pos, int kind, int state) {
        this.jjmatchedKind = kind;
        this.jjmatchedPos = pos;
        try {
            this.curChar = this.input_stream.readChar();
            return jjMoveNfa_1(state, pos + 1);
        } catch (IOException e) {
            return pos + 1;
        }
    }

    private final int jjMoveStringLiteralDfa0_1() {
        switch (this.curChar) {
            case '(':
                return jjStopAtPos(0, 10);
            case ')':
                return jjStopAtPos(0, 8);
            default:
                return jjMoveNfa_1(0, 0);
        }
    }

    private final int jjMoveNfa_1(int startState, int curPos) {
        int startsAt = 0;
        this.jjnewStateCnt = 3;
        int i = 1;
        this.jjstateSet[0] = startState;
        int kind = Integer.MAX_VALUE;
        while (true) {
            int i2 = this.jjround + 1;
            this.jjround = i2;
            if (i2 == Integer.MAX_VALUE) {
                ReInitRounds();
            }
            if (this.curChar >= '@') {
                if (this.curChar >= '') {
                    int i22 = (this.curChar & 255) >> 6;
                    long l2 = 1 << (this.curChar & 63);
                    do {
                        i--;
                        switch (this.jjstateSet[i]) {
                            case 0:
                                if ((jjbitVec0[i22] & l2) != 0 && kind > 11) {
                                    kind = 11;
                                    continue;
                                }
                            case 1:
                                if ((jjbitVec0[i22] & l2) != 0 && kind > 9) {
                                    kind = 9;
                                    continue;
                                }
                            default:
                                break;
                        }
                    } while (i != startsAt);
                }
                long j = 1 << (this.curChar & 63);
                do {
                    i--;
                    switch (this.jjstateSet[i]) {
                        case 0:
                            if (kind > 11) {
                                kind = 11;
                            }
                            if (this.curChar == IOUtils.DIR_SEPARATOR_WINDOWS) {
                                int[] iArr = this.jjstateSet;
                                int i3 = this.jjnewStateCnt;
                                this.jjnewStateCnt = i3 + 1;
                                iArr[i3] = 1;
                                continue;
                            } else {
                                continue;
                            }
                        case 1:
                            if (kind > 9) {
                                kind = 9;
                                continue;
                            } else {
                                continue;
                            }
                        case 2:
                            if (kind > 11) {
                                kind = 11;
                                continue;
                            } else {
                                continue;
                            }
                        default:
                            break;
                    }
                } while (i != startsAt);
            }
            j = 1 << this.curChar;
            do {
                i--;
                switch (this.jjstateSet[i]) {
                    case 0:
                        if (kind > 11) {
                            kind = 11;
                            continue;
                        } else {
                            continue;
                        }
                    case 1:
                        if (kind > 9) {
                            kind = 9;
                            continue;
                        } else {
                            continue;
                        }
                    default:
                        break;
                }
            } while (i != startsAt);
            if (kind != Integer.MAX_VALUE) {
                this.jjmatchedKind = kind;
                this.jjmatchedPos = curPos;
                kind = Integer.MAX_VALUE;
            }
            curPos++;
            i = this.jjnewStateCnt;
            this.jjnewStateCnt = startsAt;
            startsAt = 3 - startsAt;
            if (i != startsAt) {
                try {
                    this.curChar = this.input_stream.readChar();
                } catch (IOException e) {
                }
            }
            return curPos;
        }
    }

    private final int jjStopStringLiteralDfa_3(int pos, long active0) {
        return -1;
    }

    private final int jjStartNfa_3(int pos, long active0) {
        return jjMoveNfa_3(jjStopStringLiteralDfa_3(pos, active0), pos + 1);
    }

    private final int jjStartNfaWithStates_3(int pos, int kind, int state) {
        this.jjmatchedKind = kind;
        this.jjmatchedPos = pos;
        try {
            this.curChar = this.input_stream.readChar();
            return jjMoveNfa_3(state, pos + 1);
        } catch (IOException e) {
            return pos + 1;
        }
    }

    private final int jjMoveStringLiteralDfa0_3() {
        switch (this.curChar) {
            case '\"':
                return jjStopAtPos(0, 19);
            default:
                return jjMoveNfa_3(0, 0);
        }
    }

    private final int jjMoveNfa_3(int startState, int curPos) {
        int startsAt = 0;
        this.jjnewStateCnt = 3;
        int i = 1;
        this.jjstateSet[0] = startState;
        int kind = Integer.MAX_VALUE;
        while (true) {
            int i2 = this.jjround + 1;
            this.jjround = i2;
            if (i2 == Integer.MAX_VALUE) {
                ReInitRounds();
            }
            long l;
            if (this.curChar >= '@') {
                if (this.curChar >= '') {
                    int i22 = (this.curChar & 255) >> 6;
                    long l2 = 1 << (this.curChar & 63);
                    do {
                        i--;
                        switch (this.jjstateSet[i]) {
                            case 0:
                            case 2:
                                if ((jjbitVec0[i22] & l2) == 0) {
                                    continue;
                                } else {
                                    if (kind > 18) {
                                        kind = 18;
                                    }
                                    jjCheckNAdd(2);
                                    continue;
                                }
                            case 1:
                                if ((jjbitVec0[i22] & l2) != 0 && kind > 17) {
                                    kind = 17;
                                    continue;
                                }
                            default:
                                break;
                        }
                    } while (i != startsAt);
                }
                l = 1 << (this.curChar & 63);
                do {
                    i--;
                    switch (this.jjstateSet[i]) {
                        case 0:
                            if ((-268435457 & l) != 0) {
                                if (kind > 18) {
                                    kind = 18;
                                }
                                jjCheckNAdd(2);
                                continue;
                            } else if (this.curChar == IOUtils.DIR_SEPARATOR_WINDOWS) {
                                int[] iArr = this.jjstateSet;
                                int i3 = this.jjnewStateCnt;
                                this.jjnewStateCnt = i3 + 1;
                                iArr[i3] = 1;
                                continue;
                            } else {
                                continue;
                            }
                        case 1:
                            if (kind > 17) {
                                kind = 17;
                                continue;
                            } else {
                                continue;
                            }
                        case 2:
                            if ((-268435457 & l) == 0) {
                                continue;
                            } else {
                                if (kind > 18) {
                                    kind = 18;
                                }
                                jjCheckNAdd(2);
                                continue;
                            }
                        default:
                            break;
                    }
                } while (i != startsAt);
            }
            l = 1 << this.curChar;
            do {
                i--;
                switch (this.jjstateSet[i]) {
                    case 0:
                    case 2:
                        if ((-17179869185L & l) == 0) {
                            continue;
                        } else {
                            if (kind > 18) {
                                kind = 18;
                            }
                            jjCheckNAdd(2);
                            continue;
                        }
                    case 1:
                        if (kind > 17) {
                            kind = 17;
                            continue;
                        } else {
                            continue;
                        }
                    default:
                        break;
                }
            } while (i != startsAt);
            if (kind != Integer.MAX_VALUE) {
                this.jjmatchedKind = kind;
                this.jjmatchedPos = curPos;
                kind = Integer.MAX_VALUE;
            }
            curPos++;
            i = this.jjnewStateCnt;
            this.jjnewStateCnt = startsAt;
            startsAt = 3 - startsAt;
            if (i != startsAt) {
                try {
                    this.curChar = this.input_stream.readChar();
                } catch (IOException e) {
                }
            }
            return curPos;
        }
    }

    private final int jjStopStringLiteralDfa_2(int pos, long active0) {
        return -1;
    }

    private final int jjStartNfa_2(int pos, long active0) {
        return jjMoveNfa_2(jjStopStringLiteralDfa_2(pos, active0), pos + 1);
    }

    private final int jjStartNfaWithStates_2(int pos, int kind, int state) {
        this.jjmatchedKind = kind;
        this.jjmatchedPos = pos;
        try {
            this.curChar = this.input_stream.readChar();
            return jjMoveNfa_2(state, pos + 1);
        } catch (IOException e) {
            return pos + 1;
        }
    }

    private final int jjMoveStringLiteralDfa0_2() {
        switch (this.curChar) {
            case '(':
                return jjStopAtPos(0, 13);
            case ')':
                return jjStopAtPos(0, 14);
            default:
                return jjMoveNfa_2(0, 0);
        }
    }

    private final int jjMoveNfa_2(int startState, int curPos) {
        int startsAt = 0;
        this.jjnewStateCnt = 3;
        int i = 1;
        this.jjstateSet[0] = startState;
        int kind = Integer.MAX_VALUE;
        while (true) {
            int i2 = this.jjround + 1;
            this.jjround = i2;
            if (i2 == Integer.MAX_VALUE) {
                ReInitRounds();
            }
            if (this.curChar >= '@') {
                if (this.curChar >= '') {
                    int i22 = (this.curChar & 255) >> 6;
                    long l2 = 1 << (this.curChar & 63);
                    do {
                        i--;
                        switch (this.jjstateSet[i]) {
                            case 0:
                                if ((jjbitVec0[i22] & l2) != 0 && kind > 15) {
                                    kind = 15;
                                    continue;
                                }
                            case 1:
                                if ((jjbitVec0[i22] & l2) != 0 && kind > 12) {
                                    kind = 12;
                                    continue;
                                }
                            default:
                                break;
                        }
                    } while (i != startsAt);
                }
                long j = 1 << (this.curChar & 63);
                do {
                    i--;
                    switch (this.jjstateSet[i]) {
                        case 0:
                            if (kind > 15) {
                                kind = 15;
                            }
                            if (this.curChar == IOUtils.DIR_SEPARATOR_WINDOWS) {
                                int[] iArr = this.jjstateSet;
                                int i3 = this.jjnewStateCnt;
                                this.jjnewStateCnt = i3 + 1;
                                iArr[i3] = 1;
                                continue;
                            } else {
                                continue;
                            }
                        case 1:
                            if (kind > 12) {
                                kind = 12;
                                continue;
                            } else {
                                continue;
                            }
                        case 2:
                            if (kind > 15) {
                                kind = 15;
                                continue;
                            } else {
                                continue;
                            }
                        default:
                            break;
                    }
                } while (i != startsAt);
            }
            j = 1 << this.curChar;
            do {
                i--;
                switch (this.jjstateSet[i]) {
                    case 0:
                        if (kind > 15) {
                            kind = 15;
                            continue;
                        } else {
                            continue;
                        }
                    case 1:
                        if (kind > 12) {
                            kind = 12;
                            continue;
                        } else {
                            continue;
                        }
                    default:
                        break;
                }
            } while (i != startsAt);
            if (kind != Integer.MAX_VALUE) {
                this.jjmatchedKind = kind;
                this.jjmatchedPos = curPos;
                kind = Integer.MAX_VALUE;
            }
            curPos++;
            i = this.jjnewStateCnt;
            this.jjnewStateCnt = startsAt;
            startsAt = 3 - startsAt;
            if (i != startsAt) {
                try {
                    this.curChar = this.input_stream.readChar();
                } catch (IOException e) {
                }
            }
            return curPos;
        }
    }

    public ContentTypeParserTokenManager(SimpleCharStream stream) {
        this.debugStream = System.out;
        this.jjrounds = new int[3];
        this.jjstateSet = new int[6];
        this.curLexState = 0;
        this.defaultLexState = 0;
        this.input_stream = stream;
    }

    public ContentTypeParserTokenManager(SimpleCharStream stream, int lexState) {
        this(stream);
        SwitchTo(lexState);
    }

    public void ReInit(SimpleCharStream stream) {
        this.jjnewStateCnt = 0;
        this.jjmatchedPos = 0;
        this.curLexState = this.defaultLexState;
        this.input_stream = stream;
        ReInitRounds();
    }

    private final void ReInitRounds() {
        this.jjround = LobiRec.ERROR_BAD_ENCODER_CONNECTION;
        int i = 3;
        while (true) {
            int i2 = i - 1;
            if (i > 0) {
                this.jjrounds[i2] = ExploreByTouchHelper.INVALID_ID;
                i = i2;
            } else {
                return;
            }
        }
    }

    public void ReInit(SimpleCharStream stream, int lexState) {
        ReInit(stream);
        SwitchTo(lexState);
    }

    public void SwitchTo(int lexState) {
        if (lexState >= 4 || lexState < 0) {
            throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", 2);
        }
        this.curLexState = lexState;
    }

    protected Token jjFillToken() {
        Token t = Token.newToken(this.jjmatchedKind);
        t.kind = this.jjmatchedKind;
        String im = jjstrLiteralImages[this.jjmatchedKind];
        if (im == null) {
            im = this.input_stream.GetImage();
        }
        t.image = im;
        t.beginLine = this.input_stream.getBeginLine();
        t.beginColumn = this.input_stream.getBeginColumn();
        t.endLine = this.input_stream.getEndLine();
        t.endColumn = this.input_stream.getEndColumn();
        return t;
    }

    public Token getNextToken() {
        Token specialToken = null;
        int curPos = 0;
        while (true) {
            Token matchedToken;
            try {
                this.curChar = this.input_stream.BeginToken();
                this.image = null;
                this.jjimageLen = 0;
                while (true) {
                    switch (this.curLexState) {
                        case 0:
                            this.jjmatchedKind = Integer.MAX_VALUE;
                            this.jjmatchedPos = 0;
                            curPos = jjMoveStringLiteralDfa0_0();
                            break;
                        case 1:
                            this.jjmatchedKind = Integer.MAX_VALUE;
                            this.jjmatchedPos = 0;
                            curPos = jjMoveStringLiteralDfa0_1();
                            break;
                        case 2:
                            this.jjmatchedKind = Integer.MAX_VALUE;
                            this.jjmatchedPos = 0;
                            curPos = jjMoveStringLiteralDfa0_2();
                            break;
                        case 3:
                            this.jjmatchedKind = Integer.MAX_VALUE;
                            this.jjmatchedPos = 0;
                            curPos = jjMoveStringLiteralDfa0_3();
                            break;
                    }
                    if (this.jjmatchedKind != Integer.MAX_VALUE) {
                        if (this.jjmatchedPos + 1 < curPos) {
                            this.input_stream.backup((curPos - this.jjmatchedPos) - 1);
                        }
                        if ((jjtoToken[this.jjmatchedKind >> 6] & (1 << (this.jjmatchedKind & 63))) != 0) {
                            matchedToken = jjFillToken();
                            matchedToken.specialToken = specialToken;
                            TokenLexicalActions(matchedToken);
                            if (jjnewLexState[this.jjmatchedKind] != -1) {
                                this.curLexState = jjnewLexState[this.jjmatchedKind];
                            }
                            return matchedToken;
                        } else if ((jjtoSkip[this.jjmatchedKind >> 6] & (1 << (this.jjmatchedKind & 63))) != 0) {
                            if ((jjtoSpecial[this.jjmatchedKind >> 6] & (1 << (this.jjmatchedKind & 63))) != 0) {
                                matchedToken = jjFillToken();
                                if (specialToken == null) {
                                    specialToken = matchedToken;
                                } else {
                                    matchedToken.specialToken = specialToken;
                                    specialToken.next = matchedToken;
                                    specialToken = matchedToken;
                                }
                            }
                            if (jjnewLexState[this.jjmatchedKind] != -1) {
                                this.curLexState = jjnewLexState[this.jjmatchedKind];
                            }
                        } else {
                            MoreLexicalActions();
                            if (jjnewLexState[this.jjmatchedKind] != -1) {
                                this.curLexState = jjnewLexState[this.jjmatchedKind];
                            }
                            curPos = 0;
                            this.jjmatchedKind = Integer.MAX_VALUE;
                            try {
                                this.curChar = this.input_stream.readChar();
                            } catch (IOException e) {
                            }
                        }
                    } else {
                        int error_line = this.input_stream.getEndLine();
                        int error_column = this.input_stream.getEndColumn();
                        String error_after = null;
                        boolean EOFSeen = false;
                        try {
                            this.input_stream.readChar();
                            this.input_stream.backup(1);
                        } catch (IOException e2) {
                            EOFSeen = true;
                            error_after = curPos <= 1 ? "" : this.input_stream.GetImage();
                            if (this.curChar == '\n' || this.curChar == '\r') {
                                error_line++;
                                error_column = 0;
                            } else {
                                error_column++;
                            }
                        }
                        if (!EOFSeen) {
                            this.input_stream.backup(1);
                            if (curPos <= 1) {
                                error_after = "";
                            } else {
                                error_after = this.input_stream.GetImage();
                            }
                        }
                        throw new TokenMgrError(EOFSeen, this.curLexState, error_line, error_column, error_after, this.curChar, 0);
                    }
                }
            } catch (IOException e3) {
                this.jjmatchedKind = 0;
                matchedToken = jjFillToken();
                matchedToken.specialToken = specialToken;
                return matchedToken;
            }
        }
    }

    void MoreLexicalActions() {
        int i = this.jjimageLen;
        int i2 = this.jjmatchedPos + 1;
        this.lengthOfMatch = i2;
        this.jjimageLen = i + i2;
        switch (this.jjmatchedKind) {
            case 9:
                if (this.image == null) {
                    this.image = new StringBuffer();
                }
                this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
                this.jjimageLen = 0;
                this.image.deleteCharAt(this.image.length() - 2);
                return;
            case 10:
                if (this.image == null) {
                    this.image = new StringBuffer();
                }
                this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
                this.jjimageLen = 0;
                commentNest = 1;
                return;
            case 12:
                if (this.image == null) {
                    this.image = new StringBuffer();
                }
                this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
                this.jjimageLen = 0;
                this.image.deleteCharAt(this.image.length() - 2);
                return;
            case 13:
                if (this.image == null) {
                    this.image = new StringBuffer();
                }
                this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
                this.jjimageLen = 0;
                commentNest++;
                return;
            case 14:
                if (this.image == null) {
                    this.image = new StringBuffer();
                }
                this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
                this.jjimageLen = 0;
                commentNest--;
                if (commentNest == 0) {
                    SwitchTo(1);
                    return;
                }
                return;
            case 16:
                if (this.image == null) {
                    this.image = new StringBuffer();
                }
                this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
                this.jjimageLen = 0;
                this.image.deleteCharAt(this.image.length() - 1);
                return;
            case 17:
                if (this.image == null) {
                    this.image = new StringBuffer();
                }
                this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
                this.jjimageLen = 0;
                this.image.deleteCharAt(this.image.length() - 2);
                return;
            default:
                return;
        }
    }

    void TokenLexicalActions(Token matchedToken) {
        switch (this.jjmatchedKind) {
            case 19:
                if (this.image == null) {
                    this.image = new StringBuffer();
                }
                StringBuffer stringBuffer = this.image;
                SimpleCharStream simpleCharStream = this.input_stream;
                int i = this.jjimageLen;
                int i2 = this.jjmatchedPos + 1;
                this.lengthOfMatch = i2;
                stringBuffer.append(simpleCharStream.GetSuffix(i + i2));
                matchedToken.image = this.image.substring(0, this.image.length() - 1);
                return;
            default:
                return;
        }
    }
}
