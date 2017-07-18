package com.kayac.lobi.libnakamap.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import com.kayac.lobi.libnakamap.value.ProfileValue;
import com.kayac.lobi.sdk.activity.RootActivity;
import java.lang.reflect.InvocationTargetException;

public class ModuleUtil {
    private static final String METHOD_GET_PROFILE_VIEW = "getProfileView";
    private static final String METHOD_OVERRIDE_ROUTING = "overrideRouting";
    private static final String METHOD_REGISTER_PATH = "registerPath";
    private static final String METHOD_VERSION_NAME = "versionName";
    public static final String PACKAGE_CHAT_SDK = "com.kayac.lobi.sdk.chat.LobiChatModule";
    public static final String PACKAGE_RANKING_SDK = "com.kayac.lobi.sdk.ranking.LobiRankingModule";
    public static final String PACKAGE_REC_SDK = "com.kayac.lobi.sdk.rec.LobiRecModule";

    private static Class<?> classForPackage(String pkg) {
        Class<?> c = null;
        try {
            c = Class.forName(pkg);
        } catch (ClassNotFoundException e) {
        }
        return c;
    }

    private static boolean packageIsAvailable(String pkg) {
        return classForPackage(pkg) != null;
    }

    private static void runModuleMethod(String pkg, String methodName) {
        runModuleMethod(pkg, methodName, new Object[0]);
    }

    private static void runModuleMethod(String pkg, String methodName, Object[] args) {
        Class[] argTypes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            argTypes[i] = args[i].getClass();
        }
        runModuleMethod(pkg, methodName, args, argTypes);
    }

    private static Object runModuleMethod(String pkg, String methodName, Object[] args, Class[] argTypes) {
        Class<?> c = classForPackage(pkg);
        if (c == null) {
            return null;
        }
        Object res = null;
        try {
            return c.getMethod(methodName, argTypes).invoke(c, args);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return res;
        } catch (InvocationTargetException e2) {
            e2.printStackTrace();
            return res;
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
            return res;
        }
    }

    public static boolean chatIsAvailable() {
        return packageIsAvailable(PACKAGE_CHAT_SDK);
    }

    public static boolean rankingIsAvailable() {
        return packageIsAvailable(PACKAGE_RANKING_SDK);
    }

    public static boolean recIsAvailable() {
        return packageIsAvailable(PACKAGE_REC_SDK);
    }

    public static void registerModulePaths() {
        if (chatIsAvailable()) {
            runModuleMethod(PACKAGE_CHAT_SDK, METHOD_REGISTER_PATH);
        }
        if (rankingIsAvailable()) {
            runModuleMethod(PACKAGE_RANKING_SDK, METHOD_REGISTER_PATH);
        }
        if (recIsAvailable()) {
            runModuleMethod(PACKAGE_REC_SDK, METHOD_REGISTER_PATH);
        }
    }

    public static void setProfileView(String pkg, ProfileValue profile, Fragment fragment, Context context, boolean isMe, String token) {
        Class[] types = new Class[]{ProfileValue.class, Fragment.class, Context.class, Boolean.TYPE, String.class};
        Object[] args = new Object[]{profile, fragment, context, Boolean.valueOf(isMe), token};
        if (pkg.equals(PACKAGE_RANKING_SDK) && rankingIsAvailable()) {
            runModuleMethod(PACKAGE_RANKING_SDK, METHOD_GET_PROFILE_VIEW, args, types);
        }
        if (pkg.equals(PACKAGE_REC_SDK) && recIsAvailable()) {
            runModuleMethod(PACKAGE_REC_SDK, METHOD_GET_PROFILE_VIEW, args, types);
        }
    }

    public static boolean chatOverrideRouting(RootActivity activity, Intent intent) {
        return overrideRouting(PACKAGE_CHAT_SDK, activity, intent);
    }

    public static boolean rankingOverrideRouting(RootActivity activity, Intent intent) {
        return overrideRouting(PACKAGE_RANKING_SDK, activity, intent);
    }

    public static boolean recOverrideRouting(RootActivity activity, Intent intent) {
        return overrideRouting(PACKAGE_REC_SDK, activity, intent);
    }

    private static boolean overrideRouting(String pkg, RootActivity activity, Intent intent) {
        return ((Boolean) runModuleMethod(pkg, METHOD_OVERRIDE_ROUTING, new Object[]{activity, intent}, new Class[]{RootActivity.class, Intent.class})).booleanValue();
    }

    public static String chatVersionName() {
        return versionName(PACKAGE_CHAT_SDK);
    }

    public static String rankingVersionName() {
        return versionName(PACKAGE_RANKING_SDK);
    }

    public static String recVersionName() {
        return versionName(PACKAGE_REC_SDK);
    }

    private static String versionName(String pkg) {
        return (String) runModuleMethod(pkg, METHOD_VERSION_NAME, new Object[0], new Class[0]);
    }

    public static boolean recIsSecretMode() {
        String str = "com.kayac.lobi.libnakamap.rec.LobiRec";
        String str2 = "isSecretMode";
        Boolean flag = (Boolean) runModuleMethod(str, str2, new Object[0], new Class[0]);
        if (flag != null) {
            return flag.booleanValue();
        }
        return false;
    }

    public static boolean recOpenLobiPlayActivity(String videoId) {
        Object[] args = new Object[]{videoId, Boolean.valueOf(true)};
        Boolean flag = (Boolean) runModuleMethod("com.kayac.lobi.libnakamap.rec.LobiRecAPI", "openLobiPlayActivity", args, new Class[]{String.class, Boolean.TYPE});
        if (flag != null) {
            return flag.booleanValue();
        }
        return false;
    }

    public static boolean recOpenLobiPlayActivity(Uri uri) {
        Object[] args = new Object[]{uri};
        Boolean flag = (Boolean) runModuleMethod("com.kayac.lobi.libnakamap.rec.LobiRecAPI", "openLobiPlayActivity", args, new Class[]{Uri.class});
        if (flag != null) {
            return flag.booleanValue();
        }
        return false;
    }

    public static boolean recOpenLobiPlayActivityWithEventFields(String eventFields) {
        Object[] args = new Object[]{eventFields};
        Boolean flag = (Boolean) runModuleMethod("com.kayac.lobi.libnakamap.rec.LobiRecAPI", "openLobiPlayActivityWithEventFields", args, new Class[]{String.class});
        if (flag != null) {
            return flag.booleanValue();
        }
        return false;
    }
}
