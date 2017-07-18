package com.kayac.lobi.libnakamap.utils;

import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;

public class SingleLineInputFilter implements InputFilter {
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        for (int i = start; i < end; i++) {
            if (source.charAt(i) == '\n') {
                char[] v = new char[(end - start)];
                TextUtils.getChars(source, start, end, v, 0);
                String s = new String(v).replaceAll("\\n", "");
                if (!(source instanceof Spanned)) {
                    return s;
                }
                SpannableString sp = new SpannableString(s);
                TextUtils.copySpansFrom((Spanned) source, start, end, null, sp, 0);
                return sp;
            }
        }
        return null;
    }
}
