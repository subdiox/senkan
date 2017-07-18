package com.kayac.lobi.libnakamap.components;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import com.kayac.lobi.libnakamap.utils.DebugAssert;
import com.kayac.lobi.libnakamap.utils.Log;
import com.kayac.lobi.libnakamap.utils.NakamapBroadcastManager;
import com.kayac.lobi.sdk.LobiCore;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PathRouter {
    public static final String ACTIVITY_ONCREATE = (PathRouter.class.getCanonicalName() + ".ACTIVITY_ONCREATE");
    public static final String ACTIVITY_ONDESTROY = (PathRouter.class.getCanonicalName() + ".ACTIVITY_ONDESTROY");
    private static final String CURRENT_PATHS = (PathRouter.class.getCanonicalName() + "current_paths");
    public static final String PATH = "path";
    public static final String REMOVE_PATH = (PathRouter.class.getCanonicalName() + ".REMOVE_PATH");
    public static final String REQUEST_START_PATH = (PathRouter.class.getCanonicalName() + ".REQUEST_START_PATH");
    public static final String ROOT = "/";
    public static final String START_PATH = (PathRouter.class.getCanonicalName() + ".START_PATH");
    public static final String TAG = "[router]";
    private static final BroadcastReceiver sBroadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.v(PathRouter.TAG, "action: " + intent.getAction());
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                for (String key : bundle.keySet()) {
                    Log.v(PathRouter.TAG, key + " = " + bundle.get(key));
                }
            }
            if (PathRouter.REQUEST_START_PATH.equals(action)) {
                PathRouter.handleRouteChange(action, bundle);
            } else if (PathRouter.ACTIVITY_ONCREATE.equals(action)) {
                path = bundle.getString(PathRouter.PATH);
                Log.v(PathRouter.TAG, "onCreate:" + path);
                PathRouter.dumpCurrentPath();
                DebugAssert.assertNotNull(path);
            } else if (PathRouter.ACTIVITY_ONDESTROY.equals(action)) {
                path = bundle.getString(PathRouter.PATH);
                Log.v(PathRouter.TAG, "onDestroy:" + path);
                if (path != null) {
                    synchronized (PathRouter.sPaths) {
                        Iterator<String> iterator = PathRouter.sPaths.iterator();
                        while (iterator.hasNext()) {
                            String p = (String) iterator.next();
                            if (path.equals(p) || PathRouter.isLessThan(path, p)) {
                                Log.v(PathRouter.TAG, "removed path: " + p);
                                iterator.remove();
                            }
                        }
                    }
                    PathRouter.dumpCurrentPath();
                }
            }
        }
    };
    private static Config sConfig;
    private static Context sContext;
    protected static final ArrayList<String> sPaths = new ArrayList();

    public interface Config {
        Class<? extends Activity> getClassForPath(String str);

        void updateClassForPath(String str, Class<? extends Activity> cls);
    }

    private static void dumpCurrentPath() {
        Log.v(TAG, "--Current Path--------------------------");
        Iterator it = sPaths.iterator();
        while (it.hasNext()) {
            Log.v(TAG, "- " + ((String) it.next()));
        }
        Log.v(TAG, "----------------------------------------");
    }

    public static boolean hasActivePaths() {
        boolean z;
        synchronized (sPaths) {
            z = sPaths.size() > 0;
        }
        return z;
    }

    public static boolean hasPath(String path) {
        boolean contains;
        synchronized (sPaths) {
            contains = sPaths.contains(path);
        }
        return contains;
    }

    public static void init(Context context, Config config) {
        sContext = context;
        sConfig = config;
        Log.v(TAG, "init!");
        IntentFilter filter = new IntentFilter();
        filter.addAction(REQUEST_START_PATH);
        filter.addAction(ACTIVITY_ONCREATE);
        filter.addAction(ACTIVITY_ONDESTROY);
        NakamapBroadcastManager.getInstance(context).registerReceiver(sBroadcastReceiver, filter);
    }

    private static void handleRouteChange(String action, Bundle extras) {
        if (extras == null) {
            Log.v(TAG, "no extras");
            return;
        }
        String path = extras.getString(PATH);
        if (path == null) {
            Log.v(TAG, "no path!");
        } else if (path.split("/").length >= 1) {
            startPath(extras);
            Iterator it = sPaths.iterator();
            while (it.hasNext()) {
                Log.v(TAG, "<route> " + ((String) it.next()));
            }
        }
    }

    public static void startPath(String path) {
        startPath(path, 0);
    }

    public static void startPath(String path, int flags) {
        Bundle extras = new Bundle();
        extras.putString(PATH, path);
        startPath(extras, flags);
    }

    public static void startPath(Bundle extras) {
        startPath(extras, 0);
    }

    public static void startPath(Bundle extras, int flags) {
        LobiCore.assertSetup();
        synchronized (sPaths) {
            String basePath;
            String path;
            List<String> registry = sPaths;
            String newPath = extras.getString(PATH);
            List<String> paths = pathsBetween("/", newPath);
            if (paths.size() == 0) {
                basePath = newPath;
            } else {
                basePath = (String) paths.get(0);
            }
            Log.v(TAG, "addPathRegistry: " + newPath);
            if (!registry.isEmpty()) {
                String lastPath = (String) registry.get(registry.size() - 1);
                Log.v(TAG, "last: " + lastPath);
                if (TextUtils.equals(newPath, lastPath)) {
                    Log.v(TAG, "startedPath");
                    dumpCurrentPath();
                    return;
                } else if (isLessThan(newPath, lastPath)) {
                    Log.v(TAG, "is less than: " + newPath + " < " + lastPath);
                    Log.v(TAG, "remove the last");
                    removePath(registry, lastPath);
                    for (String path2 : pathsBetween(newPath, lastPath)) {
                        Log.v(TAG, "remove path between");
                        removePath(registry, path2);
                    }
                    Log.v(TAG, "startedPath");
                    dumpCurrentPath();
                    return;
                } else if (isLessThan(lastPath, newPath)) {
                    Log.v(TAG, "is less than: " + lastPath + " < " + newPath);
                    for (String path22 : pathsBetween(lastPath, newPath)) {
                        startAndAddToRegistry(registry, path22, extras, flags);
                    }
                    startAndAddToRegistry(registry, newPath, extras, flags);
                    Log.v(TAG, "startedPath");
                    dumpCurrentPath();
                    return;
                } else if (TextUtils.equals(getBasePath(lastPath), basePath)) {
                    String union = union(lastPath, newPath);
                    Log.v(TAG, "union: " + union);
                    for (String path222 : pathsBetween(union, lastPath)) {
                        removePath(registry, path222);
                    }
                    removePath(registry, lastPath);
                    for (String path2222 : pathsBetween(union, newPath)) {
                        startAndAddToRegistry(registry, path2222, extras, flags);
                    }
                    startAndAddToRegistry(registry, newPath, extras, flags);
                    return;
                }
            }
            int index = registry.indexOf(basePath);
            if (index > -1) {
                removePath(registry, basePath);
                while (index != registry.size()) {
                    path2222 = (String) registry.get(index);
                    if (!isLessThan(basePath, path2222)) {
                        break;
                    }
                    removePath(registry, path2222);
                }
            }
            for (String path22222 : paths) {
                startAndAddToRegistry(registry, path22222, extras, flags);
            }
            startAndAddToRegistry(registry, newPath, extras, flags);
            Log.v(TAG, "startedPath");
            dumpCurrentPath();
        }
    }

    public static final String getLastPath() {
        String str;
        synchronized (sPaths) {
            int size = sPaths.size();
            if (size == 0) {
                str = null;
            } else {
                str = (String) sPaths.get(size - 1);
            }
        }
        return str;
    }

    private static void startAndAddToRegistry(List<String> registry, String path, Bundle extras, int flags) {
        Log.v(TAG, "startPath: " + path);
        Class<? extends Activity> classRef = sConfig.getClassForPath(path);
        extras = (Bundle) extras.clone();
        extras.remove(PATH);
        extras.putString(PATH, path);
        if (classRef != null) {
            registry.add(path);
            Log.v(TAG, "start activity: " + classRef.getCanonicalName());
            sContext.startActivity(new Intent(sContext, classRef).addFlags(268435456 | flags).putExtras(extras));
            NakamapBroadcastManager.getInstance(sContext).sendBroadcast(new Intent(START_PATH).putExtras(extras));
        }
    }

    private static String getBasePath(String path) {
        return "/";
    }

    public static void removePathsGreaterThan(String path) {
        Log.v(TAG, "removePathsGreaterThan: " + path);
        synchronized (sPaths) {
            Iterator it = new ArrayList(sPaths).iterator();
            while (it.hasNext()) {
                String p = (String) it.next();
                if (isLessThan(path, p)) {
                    Log.v(TAG, "removing: " + path + " < " + p);
                    removePath(sPaths, p);
                }
            }
        }
    }

    public static boolean removePathsGreaterThanOrEqualTo(String path) {
        Log.v(TAG, "removePathsGreaterThanOrEqualTo: " + path);
        if (path == null) {
            return false;
        }
        boolean isRemove = false;
        Iterator it = new ArrayList(sPaths).iterator();
        while (it.hasNext()) {
            String p = (String) it.next();
            if (isLessThan(path, p) || path.equals(p)) {
                Log.v(TAG, "removing: " + path + " < " + p);
                removePath(sPaths, p);
                isRemove = true;
            }
        }
        return isRemove;
    }

    public static void removeAllThePaths() {
        synchronized (sPaths) {
            Iterator it = new ArrayList(sPaths).iterator();
            while (it.hasNext()) {
                removePath(sPaths, (String) it.next());
            }
        }
    }

    private static void removePath(List<String> registry, String path) {
        Log.v(TAG, "removePath: " + path);
        registry.remove(path);
        NakamapBroadcastManager.getInstance(sContext).sendBroadcast(new Intent(REMOVE_PATH).putExtra(PATH, path));
        Log.v(TAG, "removed path");
        dumpCurrentPath();
    }

    public static final String union(String a, String b) {
        String[] pathsA = a.split("/");
        String[] pathsB = b.split("/");
        int minLength = Math.min(pathsA.length, pathsB.length);
        String union = "";
        for (int i = 0; i < minLength; i++) {
            String pathA = pathsA[i];
            if (!TextUtils.equals(pathA, pathsB[i])) {
                break;
            }
            if (!union.equals("/")) {
                union = union + "/";
            }
            union = union + pathA;
        }
        return union;
    }

    public static final boolean isLessThan(String a, String b) {
        if (TextUtils.equals(a, b)) {
            return false;
        }
        String[] pathsA = a.split("/");
        String[] pathsB = b.split("/");
        int minLength = Math.min(pathsA.length, pathsB.length);
        for (int i = 0; i < minLength; i++) {
            if (!TextUtils.equals(pathsA[i], pathsB[i])) {
                return false;
            }
        }
        if (pathsA.length == minLength) {
            return true;
        }
        return false;
    }

    public static final boolean isGreaterThan(String a, String b) {
        if (TextUtils.equals(a, b)) {
            return false;
        }
        String[] pathsA = a.split("/");
        String[] pathsB = b.split("/");
        int minLength = Math.min(pathsA.length, pathsB.length);
        for (int i = 0; i < minLength; i++) {
            if (!TextUtils.equals(pathsA[i], pathsB[i])) {
                return false;
            }
        }
        if (pathsB.length == minLength) {
            return true;
        }
        return false;
    }

    public static final boolean isIncomparable(String a, String b) {
        String[] pathsA = a.split("/");
        String[] pathsB = b.split("/");
        int minLength = Math.min(pathsA.length, pathsB.length);
        for (int i = 0; i < minLength; i++) {
            if (!TextUtils.equals(pathsA[i], pathsB[i])) {
                return true;
            }
        }
        return false;
    }

    public static final List<String> pathsBetween(String a, String b) {
        List<String> paths = new ArrayList();
        if (!(isIncomparable(a, b) || TextUtils.equals(a, b))) {
            String min;
            String max;
            boolean isALessThanB = isLessThan(a, b);
            if (isALessThanB) {
                min = a;
            } else {
                min = b;
            }
            if (isALessThanB) {
                max = b;
            } else {
                max = a;
            }
            String[] pathsMin = TextUtils.split(min, "/");
            String[] pathsMax = TextUtils.split(max, "/");
            if (pathsMin.length > 0 && TextUtils.equals(pathsMin[pathsMin.length - 1], "")) {
                int length = pathsMin.length - 1;
                String[] arr = new String[length];
                System.arraycopy(pathsMin, 0, arr, 0, length);
                pathsMin = arr;
            }
            int count = (pathsMax.length - pathsMin.length) - 1;
            if (count >= 0) {
                String p;
                if ("/".equals(min)) {
                    p = "";
                } else {
                    p = min;
                }
                for (int i = 0; i < count; i++) {
                    p = p + "/" + pathsMax[pathsMin.length + i];
                    paths.add(p + "");
                }
            }
        }
        return paths;
    }

    public static void saveInstanceState(Bundle outState) {
        outState.putStringArrayList(CURRENT_PATHS, sPaths);
    }

    public static void restoreInstanceState(String lastPath, Bundle savedInstanceState) {
        synchronized (sPaths) {
            if (sPaths.size() == 0) {
                ArrayList<String> savedPath = null;
                if (savedInstanceState != null) {
                    savedPath = savedInstanceState.getStringArrayList(CURRENT_PATHS);
                    if (savedPath != null) {
                        Log.v(TAG, "restored path: " + TextUtils.join(", ", savedPath));
                    } else {
                        Log.v(TAG, "no paths are saved");
                    }
                }
                if ("/".equals(lastPath) && savedPath == null) {
                    Log.v(TAG, "add ROOT path");
                    savedPath = new ArrayList();
                    savedPath.add("/");
                }
                if (savedPath == null) {
                    Log.v(TAG, "nothing to do");
                    return;
                }
                while (savedPath.size() > 0) {
                    int index = savedPath.size() - 1;
                    if (((String) savedPath.get(index)).equals(lastPath)) {
                        break;
                    }
                    savedPath.remove(index);
                }
                sPaths.clear();
                sPaths.addAll(savedPath);
                dumpCurrentPath();
                return;
            }
        }
    }

    public static void registerPath(String path, Class<? extends Activity> classRef) {
        sConfig.updateClassForPath(path, classRef);
    }
}
