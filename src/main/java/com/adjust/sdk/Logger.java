package com.adjust.sdk;

import android.util.Log;
import java.util.Arrays;
import java.util.Locale;

public class Logger implements ILogger {
    private static String formatErrorMessage = "Error formating log message: %s, with params: %s";
    private LogLevel logLevel;
    private boolean logLevelLocked = false;

    public Logger() {
        setLogLevel(LogLevel.INFO);
    }

    public void setLogLevel(LogLevel logLevel) {
        if (!this.logLevelLocked) {
            this.logLevel = logLevel;
        }
    }

    public void setLogLevelString(String logLevelString) {
        if (logLevelString != null) {
            try {
                setLogLevel(LogLevel.valueOf(logLevelString.toUpperCase(Locale.US)));
            } catch (IllegalArgumentException e) {
                error("Malformed logLevel '%s', falling back to 'info'", logLevelString);
            }
        }
    }

    public void verbose(String message, Object... parameters) {
        if (this.logLevel.androidLogLevel <= 2) {
            try {
                Log.v(Constants.LOGTAG, String.format(Locale.US, message, parameters));
            } catch (Exception e) {
                Log.e(Constants.LOGTAG, String.format(Locale.US, formatErrorMessage, new Object[]{message, Arrays.toString(parameters)}));
            }
        }
    }

    public void debug(String message, Object... parameters) {
        if (this.logLevel.androidLogLevel <= 3) {
            try {
                Log.d(Constants.LOGTAG, String.format(Locale.US, message, parameters));
            } catch (Exception e) {
                Log.e(Constants.LOGTAG, String.format(Locale.US, formatErrorMessage, new Object[]{message, Arrays.toString(parameters)}));
            }
        }
    }

    public void info(String message, Object... parameters) {
        if (this.logLevel.androidLogLevel <= 4) {
            try {
                Log.i(Constants.LOGTAG, String.format(Locale.US, message, parameters));
            } catch (Exception e) {
                Log.e(Constants.LOGTAG, String.format(Locale.US, formatErrorMessage, new Object[]{message, Arrays.toString(parameters)}));
            }
        }
    }

    public void warn(String message, Object... parameters) {
        if (this.logLevel.androidLogLevel <= 5) {
            try {
                Log.w(Constants.LOGTAG, String.format(Locale.US, message, parameters));
            } catch (Exception e) {
                Log.e(Constants.LOGTAG, String.format(Locale.US, formatErrorMessage, new Object[]{message, Arrays.toString(parameters)}));
            }
        }
    }

    public void error(String message, Object... parameters) {
        if (this.logLevel.androidLogLevel <= 6) {
            try {
                Log.e(Constants.LOGTAG, String.format(Locale.US, message, parameters));
            } catch (Exception e) {
                Log.e(Constants.LOGTAG, String.format(Locale.US, formatErrorMessage, new Object[]{message, Arrays.toString(parameters)}));
            }
        }
    }

    public void Assert(String message, Object... parameters) {
        if (this.logLevel.androidLogLevel <= 7) {
            try {
                Log.println(7, Constants.LOGTAG, String.format(Locale.US, message, parameters));
            } catch (Exception e) {
                Log.e(Constants.LOGTAG, String.format(Locale.US, formatErrorMessage, new Object[]{message, Arrays.toString(parameters)}));
            }
        }
    }

    public void lockLogLevel() {
        this.logLevelLocked = true;
    }
}
