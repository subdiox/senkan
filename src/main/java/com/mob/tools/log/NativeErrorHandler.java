package com.mob.tools.log;

import android.content.Context;
import com.mob.tools.MobLog;
import com.mob.tools.utils.R;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class NativeErrorHandler {
    private static final int MAX_LOG_SIZE = 100;
    private static final boolean enable;

    private static class NativeCrashInfo {
        public int signal;
        public long time;

        private NativeCrashInfo() {
        }
    }

    static {
        boolean loaded = false;
        try {
            System.loadLibrary("neh");
            loaded = true;
        } catch (Throwable th) {
        }
        enable = loaded;
    }

    private static String getCachePath(Context context) {
        File file = new File(R.getCacheRoot(context), "NativeCrashLogs");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    private static native void nativePrepare(String str);

    private static ArrayList<NativeCrashInfo> parseIndex(String path) throws Throwable {
        File indexFile = new File(path, ".ncl");
        if (!indexFile.exists()) {
            return new ArrayList();
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(indexFile)));
        ArrayList<NativeCrashInfo> infos = new ArrayList();
        String line = br.readLine();
        while (line != null) {
            String[] i = line.split(",");
            if (i.length >= 2) {
                NativeCrashInfo info = new NativeCrashInfo();
                info.time = R.parseLong(i[0]);
                info.signal = R.parseInt(i[1]);
                infos.add(info);
                line = br.readLine();
            }
        }
        br.close();
        return infos;
    }

    private static String parseLog(String path, NativeCrashInfo info) throws Throwable {
        File logFile = new File(path, "." + info.time);
        if (!logFile.exists()) {
            return "";
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(logFile)));
        LinkedList<String> logs = new LinkedList();
        for (String line = br.readLine(); line != null; line = br.readLine()) {
            logs.add(line);
            if (logs.size() > 100) {
                logs.remove(0);
            }
        }
        br.close();
        StringBuffer sb = new StringBuffer();
        Iterator i$ = logs.iterator();
        while (i$.hasNext()) {
            sb.append((String) i$.next()).append('\n');
        }
        return sb.length() > 0 ? sb.substring(0, sb.length() - 1) : "";
    }

    public static boolean prepare(Context context) {
        if (enable) {
            String path = getCachePath(context);
            uploadCreashLog(path);
            nativePrepare(path);
        }
        return enable;
    }

    private static void uploadCreashLog(String path) {
        try {
            Iterator i$ = parseIndex(path).iterator();
            while (i$.hasNext()) {
                MobLog.getInstance().nativeCrashLog(parseLog(path, (NativeCrashInfo) i$.next()));
            }
            R.deleteFileAndFolder(new File(path));
        } catch (Throwable t) {
            MobLog.getInstance().w(t);
        }
    }
}
