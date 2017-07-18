package com.kayac.lobi.libnakamap.utils;

import java.lang.ref.WeakReference;

public class WeakRefUtil {
    public static <T> T get(WeakReference<T> ref) {
        if (ref == null) {
            return null;
        }
        return ref.get();
    }
}
