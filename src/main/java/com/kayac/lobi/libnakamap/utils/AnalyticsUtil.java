package com.kayac.lobi.libnakamap.utils;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AnalyticsUtil {
    public static final int REFERRER_INDEX = 2;
    public static final int SESSION_INDEX = 1;
    private static Context sContext = null;
    private static Method sGetInstance = null;
    private static Method sGetTracker = null;
    private static AnalyticsHelper sHelper = null;
    private static Method sSendEvent = null;
    private static Method sSetCustomDimension = null;

    public interface AnalyticsHelper {
        String getAndroidRoot();

        String getApp();

        String getClick();

        String getTrackingId();

        String getUser();
    }

    private AnalyticsUtil() {
    }

    public static void init(Context context, AnalyticsHelper helper) {
        sContext = context;
        sHelper = helper;
        if (sHelper != null) {
            try {
                Class<?> googleAnalyticsClassRef = Class.forName("com.google.analytics.tracking.android.GoogleAnalytics");
                Class<?> trackerClassRef = Class.forName("com.google.analytics.tracking.android.Tracker");
                sGetInstance = googleAnalyticsClassRef.getMethod("getInstance", new Class[]{Context.class});
                sGetTracker = googleAnalyticsClassRef.getMethod("getTracker", new Class[]{String.class});
                sSendEvent = trackerClassRef.getMethod("sendEvent", new Class[]{String.class, String.class, String.class, Long.class});
                sSetCustomDimension = trackerClassRef.getMethod("setCustomDimension", new Class[]{Integer.TYPE, String.class});
            } catch (ClassNotFoundException e) {
            } catch (NoSuchMethodException e2) {
            }
        }
    }

    public static final String getVersion() {
        try {
            return sContext.getPackageManager().getPackageInfo(sContext.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return "nil";
        }
    }

    private static void sendGoogleAnalytics(String category, String action, String label) {
        if (sGetInstance == null || sHelper == null) {
            Log.v("lobi-sdk", "[reporting] get instance null!");
            return;
        }
        Log.v("lobi-sdk", "[reporting] sendGoogleAnalytics");
        try {
            Object googleAnalytics = sGetInstance.invoke(null, new Object[]{sContext});
            Object tracker = sGetTracker.invoke(googleAnalytics, new Object[]{sHelper.getTrackingId()});
            sSendEvent.invoke(tracker, new Object[]{category, action, label, Long.valueOf(1)});
            Log.v("lobi-sdk", String.format("[reporting] sendEvent %s %s %s", new Object[]{category, action, label}));
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e2) {
        } catch (InvocationTargetException e3) {
        }
    }

    public static void setCustomVariable(int index, String value) {
        if (sGetInstance == null || sHelper == null) {
            Log.v("lobi-sdk", "[reporting] get instance null!");
            return;
        }
        try {
            Object googleAnalytics = sGetInstance.invoke(null, new Object[]{sContext});
            Object tracker = sGetTracker.invoke(googleAnalytics, new Object[]{sHelper.getTrackingId()});
            sSetCustomDimension.invoke(tracker, new Object[]{Integer.valueOf(index), value});
            Log.v("lobi-sdk", String.format("[reporting] setCustomVariable %d %s", new Object[]{Integer.valueOf(index), value}));
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e2) {
        } catch (InvocationTargetException e3) {
        }
    }
}
