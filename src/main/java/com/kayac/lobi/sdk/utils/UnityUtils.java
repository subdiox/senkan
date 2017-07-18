package com.kayac.lobi.sdk.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import com.google.android.gcm.GCMConstants;
import com.kayac.lobi.sdk.LobiCore;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class UnityUtils {
    private static Class<?> getUnityPlayerClass() {
        Class<?> c = null;
        try {
            c = Class.forName("com.unity3d.player.UnityPlayer");
        } catch (ClassNotFoundException e) {
        }
        return c;
    }

    public static Activity getUnityCurrentActivity() {
        Activity activity = null;
        Class<?> c = getUnityPlayerClass();
        if (c != null) {
            Field field = null;
            try {
                field = c.getField("currentActivity");
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            if (field != null) {
                activity = null;
                try {
                    activity = (Activity) field.get(null);
                } catch (IllegalAccessException e2) {
                }
            }
        }
        return activity;
    }

    public static void checkSetup() {
        Activity currentActivity = getUnityCurrentActivity();
        if (currentActivity != null) {
            LobiCore.setup(currentActivity);
        }
    }

    public static final String errorMessage(String callbackId, int statusCode, JSONObject response) {
        String message = "";
        if (response != null) {
            JSONArray ary = response.optJSONArray(GCMConstants.EXTRA_ERROR);
            if (ary != null) {
                StringBuffer sb = new StringBuffer();
                int len = ary.length();
                for (int i = 0; i < len; i++) {
                    sb.append(ary.optString(i, "") + IOUtils.LINE_SEPARATOR_UNIX);
                }
                message = sb.toString();
            }
        }
        return String.format("{\"status_code\" : \"%d\", \"error\" : \"%s\", \"id\" : \"%s\"}", new Object[]{Integer.valueOf(statusCode), message, callbackId});
    }

    public static final String defaultSuccessMessage(String callbackId, int statusCode) {
        return String.format("{\"status_code\" : \"%d\", \"result\" : {\"success\" : \"1\"}, \"id\" : \"%s\"}", new Object[]{Integer.valueOf(statusCode), callbackId});
    }

    public static final String successMessage(String callbackId, int statusCode, JSONObject json) {
        return String.format("{\"status_code\" : \"%d\", \"result\" : %s, \"id\" : \"%s\"}", new Object[]{Integer.valueOf(statusCode), json.toString(), callbackId});
    }

    public static void unitySendMessage(String gameObjectName, String callbackMethodName, String message) {
        if (gameObjectName != null && callbackMethodName != null) {
            Class<?> unity = getUnityPlayerClass();
            if (unity != null) {
                try {
                    Method method = unity.getMethod("UnitySendMessage", new Class[]{String.class, String.class, String.class});
                    String s = message != null ? message : "";
                    try {
                        method.invoke(null, new Object[]{gameObjectName, callbackMethodName, s});
                    } catch (IllegalArgumentException e) {
                    } catch (IllegalAccessException e2) {
                    } catch (InvocationTargetException e3) {
                    }
                } catch (SecurityException e4) {
                } catch (NoSuchMethodException e5) {
                }
            }
        }
    }

    public static Class<?> getPlayerActivityClass() {
        Activity currentActivity = getUnityCurrentActivity();
        if (currentActivity != null) {
            return currentActivity.getClass();
        }
        PackageManager pm = LobiCore.sharedInstance().getContext().getPackageManager();
        Intent intent = new Intent("android.intent.action.MAIN", null);
        intent.setPackage(LobiCore.sharedInstance().getContext().getPackageName());
        List<ResolveInfo> activityInfoList = pm.queryIntentActivities(intent, 0);
        if (activityInfoList.size() == 0) {
            return null;
        }
        ResolveInfo ri = (ResolveInfo) activityInfoList.get(0);
        if (ri.activityInfo == null) {
            return null;
        }
        Class<?> cls = null;
        try {
            return Class.forName(ri.activityInfo.name);
        } catch (ClassNotFoundException e) {
            return cls;
        }
    }

    public static void startUnityPlayerActivity() {
        Context context = LobiCore.sharedInstance().getContext();
        Class<?> playerActivity = getPlayerActivityClass();
        if (playerActivity != null) {
            Intent i = new Intent(context.getApplicationContext(), playerActivity);
            i.addFlags(268435456);
            i.addFlags(67108864);
            i.addFlags(536870912);
            context.startActivity(i);
        }
    }
}
