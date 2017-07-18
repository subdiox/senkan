package com.adjust.sdk;

import android.support.v4.os.EnvironmentCompat;

public enum ActivityKind {
    UNKNOWN,
    SESSION,
    EVENT,
    CLICK,
    ATTRIBUTION,
    REVENUE,
    REATTRIBUTION,
    INFO;

    public static ActivityKind fromString(String string) {
        if ("session".equals(string)) {
            return SESSION;
        }
        if ("event".equals(string)) {
            return EVENT;
        }
        if ("click".equals(string)) {
            return CLICK;
        }
        if ("attribution".equals(string)) {
            return ATTRIBUTION;
        }
        if ("info".equals(string)) {
            return INFO;
        }
        return UNKNOWN;
    }

    public String toString() {
        switch (this) {
            case SESSION:
                return "session";
            case EVENT:
                return "event";
            case CLICK:
                return "click";
            case ATTRIBUTION:
                return "attribution";
            case INFO:
                return "info";
            default:
                return EnvironmentCompat.MEDIA_UNKNOWN;
        }
    }
}
