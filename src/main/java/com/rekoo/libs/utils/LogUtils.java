package com.rekoo.libs.utils;

import android.text.TextUtils;
import android.util.Log;

public class LogUtils {
    public static String customTagPrefix = "";
    public static boolean debug = false;

    private LogUtils() {
    }

    public static void d(String content) {
        if (debug) {
            Log.d(generateTag(getCallerStackTraceElement()), content);
        }
    }

    public static void d(String content, Throwable tr) {
        if (debug) {
            Log.d(generateTag(getCallerStackTraceElement()), content, tr);
        }
    }

    public static void e(String content) {
        if (debug) {
            Log.e(generateTag(getCallerStackTraceElement()), content);
        }
    }

    public static void e(String content, Throwable tr) {
        if (debug) {
            Log.e(generateTag(getCallerStackTraceElement()), content, tr);
        }
    }

    public static void i(String content) {
        if (debug) {
            Log.i(generateTag(getCallerStackTraceElement()), content);
        }
    }

    public static void i(String content, Throwable tr) {
        if (debug) {
            Log.i(generateTag(getCallerStackTraceElement()), content, tr);
        }
    }

    public static void v(String content) {
        if (debug) {
            Log.v(generateTag(getCallerStackTraceElement()), content);
        }
    }

    public static void v(String content, Throwable tr) {
        if (debug) {
            Log.v(generateTag(getCallerStackTraceElement()), content, tr);
        }
    }

    public static void w(String content) {
        if (debug) {
            Log.w(generateTag(getCallerStackTraceElement()), content);
        }
    }

    public static void w(String content, Throwable tr) {
        if (debug) {
            Log.w(generateTag(getCallerStackTraceElement()), content, tr);
        }
    }

    public static void w(Throwable tr) {
        if (debug) {
            Log.w(generateTag(getCallerStackTraceElement()), tr);
        }
    }

    public static void wtf(String content) {
        if (debug) {
            Log.wtf(generateTag(getCallerStackTraceElement()), content);
        }
    }

    public static void wtf(String content, Throwable tr) {
        if (debug) {
            Log.wtf(generateTag(getCallerStackTraceElement()), content, tr);
        }
    }

    public static void wtf(Throwable tr) {
        if (debug) {
            Log.wtf(generateTag(getCallerStackTraceElement()), tr);
        }
    }

    private static String generateTag(StackTraceElement caller) {
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        String tag = String.format("%s.%s(L:%d)", new Object[]{callerClazzName, caller.getMethodName(), Integer.valueOf(caller.getLineNumber())});
        if (TextUtils.isEmpty(customTagPrefix)) {
            return tag;
        }
        return customTagPrefix + ":" + tag;
    }

    public static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[4];
    }
}
