package com.yunva.im.sdk.lib;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import com.unity3d.player.UnityPlayer;
import com.yaya.sdk.core.CoreLib;
import com.yunva.im.sdk.lib.config.c;
import com.yunva.im.sdk.lib.location.b;
import com.yunva.im.sdk.lib.service.VioceService;
import org.cocos2dx.lib.Cocos2dxActivity;

public class YvLoginInit {
    public static Context context;
    private static String mAppId;
    private static Application mApplication;
    public static YvLoginInit yvLoginInit;
    private com.yunva.im.sdk.lib.location.a lbsUtil;
    private a mGetLbsInfoReturnListener = new a(this);
    private Handler mHandler = new Handler(this, Looper.getMainLooper()) {
        final /* synthetic */ YvLoginInit a;

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Toast.makeText(YvLoginInit.context, "警告: 当前yunva-IMSDK 运行环境为测试环境，请在应用上线时更改为正式环境！！.", 1).show();
                    return;
                case 2:
                    YvLoginInit.YvImDoCallBack();
                    return;
                case 3:
                    this.a.lbsUtil.a();
                    return;
                case 4:
                    this.a.lbsUtil.e();
                    return;
                case 5:
                    this.a.lbsUtil.f();
                    return;
                case 8:
                    this.a.lbsUtil.b();
                    return;
                case 16:
                    this.a.lbsUtil.g();
                    return;
                case 32:
                    this.a.lbsUtil.c();
                    return;
                case 64:
                    this.a.lbsUtil.d();
                    return;
                default:
                    return;
            }
        }
    };
    private boolean orTest = false;

    class AnonymousClass2 implements Runnable {
        private final /* synthetic */ Application a;
        private final /* synthetic */ String b;

        AnonymousClass2(Application application, String str) {
            this.a = application;
            this.b = str;
        }

        public void run() {
            CoreLib.init(this.a, this.b);
        }
    }

    class a implements b {
        final /* synthetic */ YvLoginInit a;

        a(YvLoginInit yvLoginInit) {
            this.a = yvLoginInit;
        }

        public void a(int i, String str) {
            this.a.YvImUpdateGps(0, i, str);
        }

        public void a(int i, int i2) {
            this.a.YvImUpdateGps(i, i2, "");
        }
    }

    public static native void YvImDoCallBack();

    public native void YvImUpdateGps(int i, int i2, String str);

    public static YvLoginInit getInstance() {
        if (yvLoginInit == null) {
            yvLoginInit = new YvLoginInit();
        }
        return yvLoginInit;
    }

    public static boolean initApplicationOnCreate(Application application, String appId) {
        c.b().a(false);
        Intent intent = new Intent(application, VioceService.class);
        intent.putExtra("appId", appId);
        intent.addFlags(2);
        application.startService(intent);
        if (Looper.myLooper() == Looper.getMainLooper()) {
            CoreLib.init(application, appId);
        } else {
            new Handler(Looper.getMainLooper()).post(new AnonymousClass2(application, appId));
        }
        mAppId = appId;
        mApplication = application;
        context = application;
        return true;
    }

    public static void onDestory() {
        mApplication.stopService(new Intent(mApplication, VioceService.class));
    }

    public int YvLoginCallBack(long appid, long yunvaid) {
        if (mApplication == null) {
            throw new RuntimeException("the application is null. Please create the Application class, and call the method for initApplicationOnCreate(Application application, String appId) ");
        } else if (mAppId == null || !mAppId.equals(new StringBuilder(String.valueOf(appid)).toString())) {
            throw new RuntimeException("the initApplicationOnCreate appId is null, or it and the init sdk appId is not the same appId.");
        } else {
            int initc = initc();
            if (initc == 0) {
                throw new RuntimeException(" com.yunva.im.sdk.lib.YvLoginInit.context  is null .Please initialize  ");
            }
            readMetaDataFromService(context);
            if (!VioceService.a) {
                onCreateVoiceService(Long.valueOf(appid), Long.valueOf(yunvaid), this.orTest);
            }
            if (this.orTest) {
                this.mHandler.sendEmptyMessage(1);
            }
            return initc;
        }
    }

    public void YvInitCallBack(long appid, boolean test) {
        this.orTest = test;
        if (test) {
            Log.e("System.err", "警告: 当前yunva-IMSDK 运行环境为测试环境，请在应用上线时更改为正式环境！！.");
            Log.e("System.err", "警告: 当前yunva-IMSDK 运行环境为测试环境，请在应用上线时更改为正式环境！！.");
            Log.e("System.err", "警告: 当前yunva-IMSDK 运行环境为测试环境，请在应用上线时更改为正式环境！！.");
            Log.e("System.err", "警告: 当前yunva-IMSDK 运行环境为测试环境，请在应用上线时更改为正式环境！！.");
            Log.e("System.err", "警告: 当前yunva-IMSDK 运行环境为测试环境，请在应用上线时更改为正式环境！！.");
            Log.e("System.err", "警告: 当前yunva-IMSDK 运行环境为测试环境，请在应用上线时更改为正式环境！！.");
            Log.e("System.err", "警告: 当前yunva-IMSDK 运行环境为测试环境，请在应用上线时更改为正式环境！！.");
            Log.e("System.err", "警告: 当前yunva-IMSDK 运行环境为测试环境，请在应用上线时更改为正式环境！！.");
            Log.e("System.err", "警告: 当前yunva-IMSDK 运行环境为测试环境，请在应用上线时更改为正式环境！！.");
            Log.e("System.err", "警告: 当前yunva-IMSDK 运行环境为测试环境，请在应用上线时更改为正式环境！！.");
        }
    }

    private int initc() {
        if (context != null) {
            return 1;
        }
        try {
            if (UnityPlayer.currentActivity != null) {
                context = UnityPlayer.currentActivity;
                Log.v("dalvikvm", " This is Unity ...");
                return 1;
            } else if (context == null) {
                return 0;
            } else {
                return 1;
            }
        } catch (NoClassDefFoundError e) {
            try {
                if (Cocos2dxActivity.getContext() != null) {
                    context = Cocos2dxActivity.getContext();
                    Log.v("dalvikvm", " This is Cocos2dx ...");
                    return 1;
                } else if (context == null) {
                    return 0;
                } else {
                    return 1;
                }
            } catch (NoClassDefFoundError e2) {
                try {
                    if (context == null) {
                        return 0;
                    }
                    Log.v("dalvikvm", " This is Android  ...");
                    return 1;
                } catch (Error e3) {
                    if (context == null) {
                        return 0;
                    }
                    Log.v("dalvikvm", " This is unknown engine ...");
                    return 1;
                }
            }
        }
    }

    private void onCreateVoiceService(Long appId, Long userId, boolean isTest) {
        try {
            c.b().a(isTest);
            Intent intent = new Intent(context, VioceService.class);
            intent.putExtra("appId", appId);
            context.startService(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readMetaDataFromService(Context context) {
        try {
            context.getPackageManager().getServiceInfo(new ComponentName(context, VioceService.class), 128);
        } catch (NameNotFoundException e) {
            throw new RuntimeException("not found services 确认AndroidManifest.xml 注册 services ---> com.yunva.im.sdk.lib.service.VioceService");
        }
    }

    public void YvImDispatchAsync() {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            YvImDoCallBack();
        } else if (this.mHandler != null) {
            this.mHandler.sendEmptyMessage(2);
        }
    }

    public int YvImGetGpsCallBack(int locate_gps, int locate_wifi, int locate_cell, int locate_network, int locate_bluetooth) {
        this.lbsUtil = com.yunva.im.sdk.lib.location.a.a(context, this.mGetLbsInfoReturnListener);
        if ((locate_gps & 1) == 1) {
            if (Looper.myLooper() != Looper.getMainLooper()) {
                this.mHandler.sendEmptyMessage(3);
            } else {
                this.lbsUtil.a();
            }
        }
        if ((locate_gps & 2) == 2) {
            if (Looper.myLooper() != Looper.getMainLooper()) {
                this.mHandler.sendEmptyMessage(4);
            } else {
                this.lbsUtil.e();
            }
        }
        if ((locate_gps & 4) == 4) {
            if (Looper.myLooper() != Looper.getMainLooper()) {
                this.mHandler.sendEmptyMessage(5);
            } else {
                this.lbsUtil.f();
            }
        }
        if ((locate_gps & 8) == 8) {
            if (Looper.myLooper() != Looper.getMainLooper()) {
                this.mHandler.sendEmptyMessage(8);
            } else {
                this.lbsUtil.b();
            }
        }
        if ((locate_gps & 16) == 16) {
            if (Looper.myLooper() != Looper.getMainLooper()) {
                this.mHandler.sendEmptyMessage(16);
            } else {
                this.lbsUtil.g();
            }
        }
        if ((locate_gps & 32) == 32) {
            if (Looper.myLooper() != Looper.getMainLooper()) {
                this.mHandler.sendEmptyMessage(32);
            } else {
                this.lbsUtil.c();
            }
        }
        if ((locate_gps & 64) == 64) {
            if (Looper.myLooper() != Looper.getMainLooper()) {
                this.mHandler.sendEmptyMessage(64);
            } else {
                this.lbsUtil.d();
            }
        }
        return 0;
    }
}
