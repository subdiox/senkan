package com.rekoo.libs.exception;

import android.content.Context;
import android.os.Looper;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;
import com.rekoo.libs.callback.ResultCallback;
import com.rekoo.libs.net.HttpGetUtils;
import com.rekoo.libs.utils.ResUtils;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;

public class CrashHandler implements UncaughtExceptionHandler {
    private static CrashHandler CRASHHANDLER = null;
    public static final String TAG = "RK_CrashHandler";
    private Context context;
    private UncaughtExceptionHandler defaultHandler;

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        if (CRASHHANDLER == null) {
            CRASHHANDLER = new CrashHandler();
        }
        return CRASHHANDLER;
    }

    public void init(Context context) {
        this.context = context;
        this.defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public void uncaughtException(Thread thread, Throwable ex) {
        if (handleException(thread, ex) || this.defaultHandler == null) {
            try {
                Thread.sleep(5000);
            } catch (Exception e) {
                Log.e("crash", "error:", e);
            }
            Process.killProcess(Process.myPid());
            System.exit(10);
            return;
        }
        this.defaultHandler.uncaughtException(thread, ex);
    }

    private boolean handleException(Thread thread, Throwable ex) {
        if (ex == null) {
            Log.w(TAG, "handleException --- ex==null");
        } else {
            new Thread() {
                public void run() {
                    Looper.prepare();
                    Toast.makeText(CrashHandler.this.context, ResUtils.getString("error_crash", CrashHandler.this.context), 1).show();
                    Looper.loop();
                }
            }.start();
            ex.printStackTrace(System.err);
            dealException(ex);
        }
        return true;
    }

    private void dealException(Throwable ex) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        printWriter.close();
        new HttpGetUtils(this.context, transStackTraceToLog(writer.toString()), new ResultCallback() {
            public void onSuccess(String result) {
                Log.i(CrashHandler.TAG, "exception response , result = " + result);
            }

            public void onFail() {
            }
        }).start();
    }

    private String transStackTraceToLog(String s) {
        String org = s;
        try {
            org = s.replace("at ", "\r\nat ");
        } catch (Exception e) {
        }
        return org;
    }
}
