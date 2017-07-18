package com.rekoo.libs.exception;

import android.app.Application;

public class ExceptionApplication extends Application {
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(this);
    }
}
