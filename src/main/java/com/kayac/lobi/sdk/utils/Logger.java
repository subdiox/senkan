package com.kayac.lobi.sdk.utils;

import com.kayac.lobi.libnakamap.utils.Log;

public final class Logger {
    private static final int DEBUG = 1;
    private static final int ERROR = 4;
    private static final int INFO = 2;
    private static final int LOG_LEVEL = 4;
    private static final String SPACE = " ";
    private static final int VERBOSE = 0;
    private static final int WARN = 3;
    final String TAG;
    public boolean isEnabled = false;

    public Logger(Class<?> classRef, boolean isEnabled) {
        this.TAG = classRef.getSimpleName();
        this.isEnabled = isEnabled;
    }

    public Logger(Class<?> classRef) {
        this.TAG = classRef.getSimpleName();
    }

    public Logger(String tag) {
        this.TAG = tag;
    }

    public void debug(Object... messages) {
        if (!this.isEnabled) {
        }
    }

    public void debug(String message, Throwable t) {
        if (!this.isEnabled) {
        }
    }

    public void verbose(Object... messages) {
        if (!this.isEnabled) {
        }
    }

    public void verbose(String message, Throwable t) {
        if (!this.isEnabled) {
        }
    }

    public void info(Object... messages) {
        if (!this.isEnabled) {
        }
    }

    public void info(String message, Throwable t) {
        if (!this.isEnabled) {
        }
    }

    public void warn(Object... messages) {
        if (!this.isEnabled) {
        }
    }

    public void warn(String message, Throwable t) {
        if (!this.isEnabled) {
        }
    }

    public void error(Object... messages) {
        if (this.isEnabled) {
            Log.e(this.TAG, buildMessage(messages));
        }
    }

    public void error(String message, Throwable t) {
        if (this.isEnabled) {
            Log.e(this.TAG, message, t);
        }
    }

    String buildMessage(Object[] messages) {
        StringBuilder builder = new StringBuilder();
        if (messages != null) {
            int len = messages.length;
            for (int i = 0; i < len; i++) {
                builder.append(messages[i]);
                if (i < len - 1) {
                    builder.append(SPACE);
                }
            }
        }
        return builder.toString();
    }
}
