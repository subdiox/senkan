package com.rekoo.libs.utils;

import android.content.Context;
import android.support.v4.view.InputDeviceCompat;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.URLSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Pattern;

public class StringUtils {
    static String TAG = "StringUtils";

    public static void setLink(Context context, TextView textView, int contentId, int linkId, int start, int end) {
        SpannableString sp = new SpannableString(context.getResources().getString(contentId));
        sp.setSpan(new URLSpan(context.getResources().getString(linkId)), start, end, 33);
        textView.setText(sp);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public static String setTextColor(String color, String content) {
        return "<font color='" + color + "'><b>" + content + "</b></font>";
    }

    public static void hideSoftInputMode(Context context, View windowToken) {
        ((InputMethodManager) context.getSystemService("input_method")).hideSoftInputFromWindow(windowToken.getWindowToken(), 2);
    }

    public static boolean isCharAndNumber(String str) {
        if (str == null || str.length() < 1 || str.equals("")) {
            return false;
        }
        return Pattern.compile("^[0-9a-zA-Z_]+$").matcher(str).find();
    }

    public static boolean checkPassword(String str) {
        if (str == null || str.length() < 1 || str.equals("")) {
            return false;
        }
        return Pattern.compile("^[a-z0-9]{6,70}$").matcher(str).find();
    }

    public static boolean checkAccount(String str) {
        return Pattern.compile("^[a-z0-9@.]{6,70}$").matcher(str).find();
    }

    public static boolean checkZH(String str) {
        return Pattern.compile("^[一-龥]").matcher(str).find();
    }

    public static boolean isEmptyString(String str) {
        if (str == null || str.length() < 1 || str.equals("")) {
            return true;
        }
        return false;
    }

    public static void strcmp(String firstStr, String otherStr) {
        if (!TextUtils.isEmpty(firstStr) && !TextUtils.isEmpty(otherStr)) {
            int firstLen = firstStr.length();
            int otherLen = otherStr.length();
            if (!firstStr.equals(otherStr)) {
                int tempLen = 0;
                if (firstLen >= otherLen) {
                    tempLen = otherLen;
                }
                if (firstLen < otherLen) {
                    tempLen = firstLen;
                }
                for (int i = 0; i < tempLen; i++) {
                    char firstChar = firstStr.charAt(i);
                    char otherChar = otherStr.charAt(i);
                    if (firstChar != otherChar) {
                        LogUtils.d("firstChar:" + firstChar + ", otherChar:" + otherChar);
                    }
                }
            }
        }
    }

    public static SpannableString getSpanableText(String wholeText, String spanableText) {
        if (TextUtils.isEmpty(wholeText)) {
            wholeText = "";
        }
        SpannableString spannableString = new SpannableString(wholeText);
        if (spanableText.equals("")) {
            return spannableString;
        }
        wholeText = wholeText.toLowerCase();
        spanableText = spanableText.toLowerCase();
        int startPos = wholeText.indexOf(spanableText);
        if (startPos == -1) {
            int tmpLength = spanableText.length();
            String tmpResult = "";
            for (int i = 1; i <= tmpLength; i++) {
                tmpResult = spanableText.substring(0, tmpLength - i);
                int tmpPos = wholeText.indexOf(tmpResult);
                if (tmpPos == -1) {
                    tmpResult = spanableText.substring(i, tmpLength);
                    tmpPos = wholeText.indexOf(tmpResult);
                }
                if (tmpPos != -1) {
                    break;
                }
                tmpResult = "";
            }
            if (tmpResult.length() != 0) {
                return getSpanableText(wholeText, tmpResult);
            }
            return spannableString;
        }
        int endPos = startPos + spanableText.length();
        do {
            endPos = startPos + spanableText.length();
            spannableString.setSpan(new BackgroundColorSpan(InputDeviceCompat.SOURCE_ANY), startPos, endPos, 33);
            startPos = wholeText.indexOf(spanableText, endPos);
        } while (startPos != -1);
        return spannableString;
    }

    public static boolean verivyCharAndNumberAndUnderLine(String str) {
        return Pattern.compile("^\\w*(?=\\w*\\d)(?=\\w*[a-zA-Z])\\w*$").matcher(str).matches();
    }

    public static boolean judgeMobileNum(String mobiles) {
        return Pattern.compile("^((1[0-9]))\\d{9}$").matcher(mobiles).matches();
    }

    public static boolean isEmail(String strEmail) {
        return Pattern.compile("^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]*@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$").matcher(strEmail).matches();
    }

    public static String parseString(String s, int screenWidth) {
        String s1 = "";
        int length = 0;
        int maxCharacters = (screenWidth / 13) + 1;
        if (s.getBytes().length < maxCharacters) {
            return s;
        }
        while (s1.getBytes().length < maxCharacters) {
            int length2 = length + 1;
            s1 = new StringBuilder(String.valueOf(s1)).append(s.toCharArray()[length]).toString();
            length = length2;
        }
        if (s1.getBytes().length >= maxCharacters) {
            return new StringBuilder(String.valueOf(s1)).append("...").toString();
        }
        return new StringBuilder(String.valueOf(s1)).append("...").toString();
    }

    public static String processString(String str, int length, Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        int gapLength = ((screenWidth > screenHeight ? screenWidth - screenHeight : screenHeight - screenWidth) / 13) + 1;
        String temp = str.trim();
        int cnt = 0;
        int ent = 0;
        int resultCount = 0;
        int charAt = 0;
        int i;
        if (screenWidth >= screenHeight) {
            length += gapLength / 2;
            if (getStringLength(temp) <= length) {
                return temp;
            }
            for (i = 0; i < temp.length(); i++) {
                if (resultCount == length) {
                    return temp.substring(0, charAt) + "...";
                }
                if (isChinese(temp.charAt(i))) {
                    cnt++;
                } else {
                    ent++;
                }
                resultCount = cnt + (ent / 2);
                charAt = i;
            }
        } else if (getStringLength(temp) <= length) {
            return temp;
        } else {
            for (i = 0; i < temp.length(); i++) {
                if (resultCount == length) {
                    return temp.substring(0, charAt) + "...";
                }
                if (isChinese(temp.charAt(i))) {
                    cnt++;
                } else {
                    ent++;
                }
                resultCount = cnt + (ent / 2);
                charAt = i;
            }
        }
        return "";
    }

    private static boolean isChinese(char a) {
        return a >= '一' && a <= '龥';
    }

    private static int getStringLength(String str) {
        int cnt = 0;
        int ent = 0;
        for (int i = 0; i < str.length(); i++) {
            if (isChinese(str.charAt(i))) {
                cnt++;
            } else {
                ent++;
            }
        }
        return (ent / 2) + cnt;
    }

    public static String trim(String resource) {
        return resource.trim().replaceAll(" ", "");
    }

    public static String getSplitString(String s) {
        StringBuffer sb = new StringBuffer();
        if (s.getBytes().length > 16) {
            for (char a : s.toCharArray()) {
                if (sb.length() < 8) {
                    sb.append(a);
                }
            }
            sb.append("...");
        }
        return sb.toString();
    }

    public static String parseChineseWord(String words) {
        StringBuffer sb = new StringBuffer();
        for (char c : words.toCharArray()) {
            if (c < 'Α' || c > '￥') {
                sb.append(c);
            } else {
                try {
                    sb.append(URLEncoder.encode(new StringBuilder(String.valueOf(c)).toString(), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    public static String splitImageName(String url, boolean isRemovePrefix_l) {
        if (isEmptyString(url)) {
            return "";
        }
        int lastindex = url.lastIndexOf("/");
        if (lastindex <= 0) {
            return "";
        }
        return url.substring(0, lastindex + 1) + url.substring(isRemovePrefix_l ? lastindex + 3 : lastindex + 1);
    }

    public static String upToLowerCase(String str) {
        char[] ch = str.toCharArray();
        StringBuffer buffer = new StringBuffer();
        int i = 0;
        while (i < str.length()) {
            if (ch[i] < 'A' || ch[i] > 'Z') {
                buffer.append(String.valueOf(ch[i]));
            } else {
                buffer.append(String.valueOf(ch[i]).toLowerCase());
            }
            i++;
        }
        return buffer.toString();
    }
}
