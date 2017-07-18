package com.rekoo.libs.platform;

import android.app.Activity;
import java.util.Iterator;
import java.util.Stack;

public class ActivityStackManager {
    private static Stack<Activity> activityStack;
    private static ActivityStackManager instance;

    public void AppExit(android.content.Context r5, java.lang.Boolean r6) {
        /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextNode(HashMap.java:1439)
	at java.util.HashMap$KeyIterator.next(HashMap.java:1461)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
        /*
        r4 = this;
        r3 = 0;
        r4.finishAllActivity();	 Catch:{ Exception -> 0x001d, all -> 0x0028 }
        r1 = "activity";	 Catch:{ Exception -> 0x001d, all -> 0x0028 }
        r0 = r5.getSystemService(r1);	 Catch:{ Exception -> 0x001d, all -> 0x0028 }
        r0 = (android.app.ActivityManager) r0;	 Catch:{ Exception -> 0x001d, all -> 0x0028 }
        r1 = r5.getPackageName();	 Catch:{ Exception -> 0x001d, all -> 0x0028 }
        r0.restartPackage(r1);	 Catch:{ Exception -> 0x001d, all -> 0x0028 }
        r1 = r6.booleanValue();
        if (r1 != 0) goto L_0x001c;
    L_0x0019:
        java.lang.System.exit(r3);
    L_0x001c:
        return;
    L_0x001d:
        r1 = move-exception;
        r1 = r6.booleanValue();
        if (r1 != 0) goto L_0x001c;
    L_0x0024:
        java.lang.System.exit(r3);
        goto L_0x001c;
    L_0x0028:
        r1 = move-exception;
        r2 = r6.booleanValue();
        if (r2 != 0) goto L_0x0032;
    L_0x002f:
        java.lang.System.exit(r3);
    L_0x0032:
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.rekoo.libs.platform.ActivityStackManager.AppExit(android.content.Context, java.lang.Boolean):void");
    }

    private ActivityStackManager() {
    }

    public static ActivityStackManager getAppManager() {
        if (instance == null) {
            instance = new ActivityStackManager();
        }
        return instance;
    }

    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack();
        }
        activityStack.add(activity);
    }

    public Activity currentActivity() {
        return (Activity) activityStack.lastElement();
    }

    public void finishActivity() {
        finishActivity((Activity) activityStack.lastElement());
    }

    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    public void removeActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
        }
    }

    public void finishActivity(Class<?> cls) {
        Iterator it = activityStack.iterator();
        while (it.hasNext()) {
            Activity activity = (Activity) it.next();
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    public void finishAllActivity() {
        int size = activityStack.size();
        for (int i = 0; i < size; i++) {
            if (activityStack.get(i) != null) {
                ((Activity) activityStack.get(i)).finish();
            }
        }
        activityStack.clear();
    }
}
