package com.kayac.lobi.libnakamap.utils;

public interface Logger {
    void debug(String str, String str2, Throwable th);

    void debug(String str, Object... objArr);

    void error(String str, String str2, Throwable th);

    void error(String str, Object... objArr);

    void info(String str, String str2, Throwable th);

    void info(String str, Object... objArr);

    void verbose(String str, String str2, Throwable th);

    void verbose(String str, Object... objArr);

    void warn(String str, String str2, Throwable th);

    void warn(String str, Object... objArr);
}
