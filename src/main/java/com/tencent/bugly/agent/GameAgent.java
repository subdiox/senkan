package com.tencent.bugly.agent;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import com.kayac.lobi.libnakamap.value.PushNotificationValue;
import com.rekoo.libs.net.URLCons;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;
import org.apache.commons.io.IOUtils;

public class GameAgent {
    private static final String CLASS_COCOS_ACTIVITY = "org.cocos2dx.lib.Cocos2dxActivity";
    private static final String CLASS_UNITY_PLAYER = "com.unity3d.player.UnityPlayer";
    private static final String CRASH_REPORT_CLASS_SUFFIX = "crashreport.CrashReport";
    private static final int GAME_TYPE_COCOS = 1;
    private static final int GAME_TYPE_UNITY = 2;
    private static final int LOG_LEVEL_DEBUG = 1;
    private static final int LOG_LEVEL_ERROR = 4;
    private static final int LOG_LEVEL_INFO = 2;
    private static final int LOG_LEVEL_VERBOSE = 0;
    private static final int LOG_LEVEL_WARN = 3;
    private static final String LOG_TAG = "CrashReport-GameAgent";
    private static final String OLD_STRATEGY_CLASS_SUFFIX = "crashreport.CrashReport$UserStrategy";
    private static final String STRATEGY_CLASS_SUFFIX = "BuglyStrategy";
    private static final int TYPE_COCOS2DX_JS_CRASH = 5;
    private static final int TYPE_COCOS2DX_LUA_CRASH = 6;
    private static final int TYPE_U3D_CRASH = 4;
    private static final String VERSION = "2.0";
    private static String sAppChannel = null;
    private static String sAppVersion = null;
    private static WeakReference<Activity> sContext = null;
    private static int sGameType = 0;
    private static Handler sHandler = null;
    private static boolean sIsDebug = false;
    private static String sUserId = null;
    private static String sdkPackageName = "com.tencent.bugly";

    private static class Reflection {
        private Reflection() {
        }

        private static Object getStaticField(String str, String str2, Object obj) {
            try {
                Field declaredField = Class.forName(str).getDeclaredField(str2);
                declaredField.setAccessible(true);
                return declaredField.get(obj);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            } catch (NoSuchFieldException e2) {
                e2.printStackTrace();
                return null;
            } catch (IllegalAccessException e3) {
                e3.printStackTrace();
                return null;
            }
        }

        private static Object invokeStaticMethod(String str, String str2, Object[] objArr, Class<?>... clsArr) {
            Object obj = null;
            try {
                Method declaredMethod = Class.forName(str).getDeclaredMethod(str2, clsArr);
                declaredMethod.setAccessible(true);
                obj = declaredMethod.invoke(null, objArr);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e2) {
                e2.printStackTrace();
            } catch (InvocationTargetException e3) {
                e3.printStackTrace();
            } catch (IllegalAccessException e4) {
                e4.printStackTrace();
            } catch (Exception e5) {
                e5.printStackTrace();
            }
            return obj;
        }

        private static Object newInstance(String str, Object[] objArr, Class<?>... clsArr) {
            try {
                Class cls = Class.forName(str);
                if (objArr == null) {
                    return cls.newInstance();
                }
                return cls.getConstructor(clsArr).newInstance(objArr);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            } catch (NoSuchMethodException e2) {
                e2.printStackTrace();
                return null;
            } catch (InstantiationException e3) {
                e3.printStackTrace();
                return null;
            } catch (IllegalAccessException e4) {
                e4.printStackTrace();
                return null;
            } catch (InvocationTargetException e5) {
                e5.printStackTrace();
                return null;
            } catch (Exception e6) {
                e6.printStackTrace();
                return null;
            }
        }
    }

    public static String getVersion() {
        return VERSION;
    }

    public static void printLog(String str) {
        if (!TextUtils.isEmpty(str)) {
            if (str.startsWith("<Log>")) {
                printLog(2, str);
            } else if (str.startsWith("<LogDebug>")) {
                printLog(1, str);
            } else if (str.startsWith("<LogInfo>")) {
                printLog(2, str);
            } else if (str.startsWith("<LogWarning>")) {
                printLog(3, str);
            } else if (str.startsWith("<LogAssert>")) {
                printLog(3, str);
            } else if (str.startsWith("<LogError>")) {
                printLog(4, str);
            } else if (str.startsWith("<LogException>")) {
                printLog(4, str);
            } else {
                printLog(0, str);
            }
        }
    }

    private static void printLog(int i, String str) {
        setLog(i, LOG_TAG, str);
    }

    public static Activity getUnityActivity() {
        try {
            Object access$000 = Reflection.getStaticField(CLASS_UNITY_PLAYER, "currentActivity", null);
            if (access$000 != null && (access$000 instanceof Activity)) {
                return (Activity) access$000;
            }
        } catch (Exception e) {
            Log.w(LOG_TAG, "Failed to get the current activity from UnityPlayer");
            e.printStackTrace();
        }
        return null;
    }

    public static Activity getCocosActivity() {
        try {
            Object access$100 = Reflection.invokeStaticMethod(CLASS_COCOS_ACTIVITY, "getContext", null, new Class[0]);
            if (access$100 != null && (access$100 instanceof Activity)) {
                return (Activity) access$100;
            }
        } catch (Exception e) {
            Log.w(LOG_TAG, "Failed to get the current activity from UnityPlayer");
            e.printStackTrace();
        }
        return null;
    }

    private static Activity getActivity() {
        if (sContext != null && sContext.get() != null) {
            return (Activity) sContext.get();
        }
        switch (sGameType) {
            case 1:
                return getCocosActivity();
            case 2:
                return getUnityActivity();
            default:
                Log.w(LOG_TAG, "Game type has not been set.");
                return null;
        }
    }

    private static Context getApplicationContext() {
        Activity activity = getActivity();
        if (activity != null) {
            return activity.getApplicationContext();
        }
        return null;
    }

    private static void runTaskInUiThread(Runnable runnable) {
        Activity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(runnable);
        } else {
            new Thread(runnable).start();
        }
    }

    private static void exitApplication() {
        printLog(3, String.format(Locale.US, "Exit application by kill process[%d]", new Object[]{Integer.valueOf(Process.myPid())}));
        Process.killProcess(r0);
    }

    private static void delayExit(long j) {
        long max = Math.max(0, j);
        if (sHandler != null) {
            sHandler.postDelayed(new Runnable() {
                public void run() {
                    GameAgent.exitApplication();
                }
            }, max);
            return;
        }
        try {
            Thread.sleep(max);
            exitApplication();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static String convertToCanonicalName(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        if (sdkPackageName == null) {
            sdkPackageName = "com.tencent.bugly";
        }
        stringBuilder.append(sdkPackageName);
        stringBuilder.append(".");
        stringBuilder.append(str);
        return stringBuilder.toString();
    }

    public static void setSdkPackageName(String str) {
        if (!TextUtils.isEmpty(str)) {
            sdkPackageName = str;
        }
    }

    public static void setGameType(int i) {
        sGameType = i;
    }

    public static void setLogEnable(boolean z) {
        sIsDebug = z;
    }

    private static Object newStrategy(Context context, String str, String str2, long j) {
        if (context == null || (TextUtils.isEmpty(str) && TextUtils.isEmpty(str2))) {
            return null;
        }
        Object access$300 = Reflection.newInstance(convertToCanonicalName(OLD_STRATEGY_CLASS_SUFFIX), new Object[]{context}, Context.class);
        if (access$300 != null) {
            try {
                Class cls = Class.forName(convertToCanonicalName(STRATEGY_CLASS_SUFFIX));
                cls.getDeclaredMethod("setAppChannel", new Class[]{String.class}).invoke(access$300, new Object[]{str});
                cls.getDeclaredMethod("setAppVersion", new Class[]{String.class}).invoke(access$300, new Object[]{str2});
                cls.getDeclaredMethod("setAppReportDelay", new Class[]{Long.TYPE}).invoke(access$300, new Object[]{Long.valueOf(j)});
                return access$300;
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e2) {
                e2.printStackTrace();
            } catch (IllegalArgumentException e3) {
                e3.printStackTrace();
            } catch (InvocationTargetException e4) {
                e4.printStackTrace();
            } catch (Exception e5) {
                e5.printStackTrace();
            }
        }
        return null;
    }

    public static void initCrashReport(String str, boolean z) {
        setLogEnable(z);
        initCrashReport(str, sAppChannel, sAppVersion, sUserId, 0);
    }

    private static void initCrashReport(final String str, String str2, String str3, final String str4, long j) {
        final Context applicationContext = getApplicationContext();
        if (applicationContext == null) {
            printLog(4, "Context is null. bugly initialize terminated.");
        } else if (TextUtils.isEmpty(str)) {
            printLog(4, "Please input appid when initCrashReport.");
        } else {
            sHandler = new Handler(Looper.getMainLooper());
            final Object newStrategy = newStrategy(applicationContext, str2, str3, j);
            runTaskInUiThread(new Runnable() {
                public void run() {
                    int i;
                    boolean access$400 = GameAgent.sIsDebug;
                    if (newStrategy != null) {
                        Class cls;
                        try {
                            cls = Class.forName(GameAgent.convertToCanonicalName(GameAgent.OLD_STRATEGY_CLASS_SUFFIX));
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                            cls = null;
                        } catch (Exception e2) {
                            e2.printStackTrace();
                            cls = null;
                        }
                        if (cls != null) {
                            Reflection.invokeStaticMethod(GameAgent.convertToCanonicalName(GameAgent.CRASH_REPORT_CLASS_SUFFIX), "initCrashReport", new Object[]{applicationContext, str, Boolean.valueOf(access$400), newStrategy}, Context.class, String.class, Boolean.TYPE, cls);
                            i = 1;
                            if (i == 0) {
                                Reflection.invokeStaticMethod(GameAgent.convertToCanonicalName(GameAgent.CRASH_REPORT_CLASS_SUFFIX), "initCrashReport", new Object[]{applicationContext, str, Boolean.valueOf(access$400)}, Context.class, String.class, Boolean.TYPE);
                            }
                            GameAgent.setUserId(str4);
                        }
                    }
                    i = 0;
                    if (i == 0) {
                        Reflection.invokeStaticMethod(GameAgent.convertToCanonicalName(GameAgent.CRASH_REPORT_CLASS_SUFFIX), "initCrashReport", new Object[]{applicationContext, str, Boolean.valueOf(access$400)}, Context.class, String.class, Boolean.TYPE);
                    }
                    GameAgent.setUserId(str4);
                }
            });
        }
    }

    public static void setAppVersion(final String str) {
        if (!TextUtils.isEmpty(str)) {
            sAppVersion = str;
            runTaskInUiThread(new Runnable() {
                public void run() {
                    Reflection.invokeStaticMethod(GameAgent.convertToCanonicalName(GameAgent.CRASH_REPORT_CLASS_SUFFIX), "setAppVersion", new Object[]{GameAgent.getApplicationContext(), str}, Context.class, String.class);
                }
            });
        }
    }

    public static void setAppChannel(final String str) {
        if (!TextUtils.isEmpty(str)) {
            sAppChannel = str;
            runTaskInUiThread(new Runnable() {
                public void run() {
                    Reflection.invokeStaticMethod(GameAgent.convertToCanonicalName(GameAgent.CRASH_REPORT_CLASS_SUFFIX), "setAppChannel", new Object[]{GameAgent.getApplicationContext(), str}, Context.class, String.class);
                }
            });
        }
    }

    public static void setUserId(final String str) {
        if (!TextUtils.isEmpty(str)) {
            sUserId = str;
            runTaskInUiThread(new Runnable() {
                public void run() {
                    Reflection.invokeStaticMethod(GameAgent.convertToCanonicalName(GameAgent.CRASH_REPORT_CLASS_SUFFIX), "setUserId", new Object[]{GameAgent.getApplicationContext(), str}, Context.class, String.class);
                }
            });
        }
    }

    public static void setUserSceneTag(final int i) {
        runTaskInUiThread(new Runnable() {
            public void run() {
                Reflection.invokeStaticMethod(GameAgent.convertToCanonicalName(GameAgent.CRASH_REPORT_CLASS_SUFFIX), "setUserSceneTag", new Object[]{GameAgent.getApplicationContext(), Integer.valueOf(i)}, Context.class, Integer.TYPE);
            }
        });
    }

    public static void putUserData(final String str, final String str2) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            runTaskInUiThread(new Runnable() {
                public void run() {
                    Reflection.invokeStaticMethod(GameAgent.convertToCanonicalName(GameAgent.CRASH_REPORT_CLASS_SUFFIX), "putUserData", new Object[]{GameAgent.getApplicationContext(), str, str2}, Context.class, String.class, String.class);
                }
            });
        }
    }

    public static void removeUserData(final String str) {
        if (!TextUtils.isEmpty(str)) {
            runTaskInUiThread(new Runnable() {
                public void run() {
                    Reflection.invokeStaticMethod(GameAgent.convertToCanonicalName(GameAgent.CRASH_REPORT_CLASS_SUFFIX), "removeUserData", new Object[]{GameAgent.getApplicationContext(), str}, Context.class, String.class);
                }
            });
        }
    }

    public static void setSdkConfig(final String str, final String str2) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            runTaskInUiThread(new Runnable() {
                public void run() {
                    Reflection.invokeStaticMethod(GameAgent.convertToCanonicalName(GameAgent.CRASH_REPORT_CLASS_SUFFIX), "putSdkData", new Object[]{GameAgent.getApplicationContext(), "SDK_" + str, str2}, Context.class, String.class, String.class);
                }
            });
        }
    }

    public static void setLog(int i, final String str, final String str2) {
        if (!TextUtils.isEmpty(str)) {
            String str3;
            switch (i) {
                case 0:
                    str3 = URLCons.VERSION;
                    break;
                case 1:
                    str3 = "d";
                    break;
                case 2:
                    str3 = PushNotificationValue.INVITED;
                    break;
                case 3:
                    str3 = "w";
                    break;
                case 4:
                    str3 = "e";
                    break;
                default:
                    str3 = null;
                    break;
            }
            if (str3 != null) {
                runTaskInUiThread(new Runnable() {
                    public void run() {
                        Reflection.invokeStaticMethod(GameAgent.convertToCanonicalName("crashreport.BuglyLog"), str3, new Object[]{str, str2}, String.class, String.class);
                    }
                });
            }
        }
    }

    private static void postCocosException(int i, String str, String str2, String str3, boolean z) {
        String trim;
        int indexOf;
        String str4;
        final String str5;
        final boolean z2;
        try {
            if (str3.startsWith("stack traceback")) {
                trim = str3.substring(str3.indexOf(IOUtils.LINE_SEPARATOR_UNIX) + 1, str3.length()).trim();
            } else {
                trim = str3;
            }
            try {
                String substring;
                int indexOf2 = trim.indexOf(IOUtils.LINE_SEPARATOR_UNIX);
                if (indexOf2 > 0) {
                    trim = trim.substring(indexOf2 + 1, trim.length());
                }
                indexOf2 = trim.indexOf(IOUtils.LINE_SEPARATOR_UNIX);
                if (indexOf2 > 0) {
                    substring = trim.substring(0, indexOf2);
                } else {
                    substring = trim;
                }
                indexOf = substring.indexOf("]:");
                if (str == null || str.length() == 0) {
                    if (indexOf != -1) {
                        str = substring.substring(0, indexOf + 1);
                    } else {
                        str = str2;
                    }
                }
                str4 = str;
            } catch (Throwable th) {
                if (str != null || str.length() == 0) {
                    str4 = str2;
                } else {
                    str4 = str;
                }
                indexOf = i;
                str5 = str2;
                z2 = z;
                runTaskInUiThread(new Runnable() {
                    public void run() {
                        Reflection.invokeStaticMethod(GameAgent.convertToCanonicalName("crashreport.inner.InnerApi"), "postCocos2dxCrashAsync", new Object[]{Integer.valueOf(indexOf), str4, str5, trim}, Integer.TYPE, String.class, String.class, String.class);
                        if (z2) {
                            GameAgent.delayExit(3000);
                        }
                    }
                });
            }
        } catch (Throwable th2) {
            trim = str3;
            if (str != null) {
            }
            str4 = str2;
            indexOf = i;
            str5 = str2;
            z2 = z;
            runTaskInUiThread(/* anonymous class already generated */);
        }
        indexOf = i;
        str5 = str2;
        z2 = z;
        runTaskInUiThread(/* anonymous class already generated */);
    }

    private static void postUnityException(final String str, final String str2, final String str3, final boolean z) {
        runTaskInUiThread(new Runnable() {
            public void run() {
                Reflection.invokeStaticMethod(GameAgent.convertToCanonicalName("crashreport.inner.InnerApi"), "postU3dCrashAsync", new Object[]{str, str2, str3}, String.class, String.class, String.class);
                if (z) {
                    GameAgent.delayExit(3000);
                }
            }
        });
    }

    public static void postException(int i, String str, String str2, String str3, boolean z) {
        switch (i) {
            case 4:
                postUnityException(str, str2, str3, z);
                return;
            case 5:
            case 6:
                postCocosException(i, str, str2, str3, z);
                return;
            default:
                printLog(4, "The category of exception posted is unknown: " + String.valueOf(i));
                return;
        }
    }
}
