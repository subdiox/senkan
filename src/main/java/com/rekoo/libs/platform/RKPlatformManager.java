package com.rekoo.libs.platform;

import android.app.Activity;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import com.rekoo.libs.callback.ProduceTransformCallback;
import com.rekoo.libs.callback.RKLibInitCallback;
import com.rekoo.libs.callback.RKLoginCallback;
import com.rekoo.libs.callback.RKLogoutCallback;
import com.rekoo.libs.callback.TransformCallback;
import com.rekoo.libs.config.BIConfig;
import com.rekoo.libs.config.Config;
import com.rekoo.libs.entity.RKUser;
import com.rekoo.libs.net.NetUtils;
import com.rekoo.libs.platform.ui.FloatWebView;
import com.rekoo.libs.platform.ui.LoginManager;
import com.rekoo.libs.platform.ui.TransformManager;
import com.rekoo.libs.platform.ui.floating.FloatManager;
import com.rekoo.libs.push.GCMPush;
import com.rekoo.libs.utils.AdvertisingIdClient;
import com.rekoo.libs.utils.CommonUtils;
import com.rekoo.libs.utils.LogUtils;
import com.rekoo.libs.utils.ResUtils;

public class RKPlatformManager {
    private static Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 2) {
                Config.gooleAdvertisingId = (String) msg.obj;
            }
        }
    };
    public static RKPlatformManager manager;
    Activity act;
    RKLibInitCallback rkLibInitCallback;

    private RKPlatformManager() {
    }

    public static RKPlatformManager getManager() {
        if (manager == null) {
            synchronized (RKPlatformManager.class) {
                if (manager == null) {
                    manager = new RKPlatformManager();
                }
            }
        }
        return manager;
    }

    public Context getContext() {
        return this.act.getWindow().getContext();
    }

    public void rkFloat(Context context) {
        int sysVersion = Integer.parseInt(VERSION.SDK);
    }

    public void rkInit(final Activity context, RKLibInitCallback initCallback) {
        this.act = context;
        this.rkLibInitCallback = initCallback;
        Config.libInitCallback = initCallback;
        Config.isInit = false;
        if (NetUtils.checkConnected(context)) {
            new Thread(new Runnable() {
                public void run() {
                    RKPlatformManager.this.rkPush(context);
                }
            }).start();
            new Thread(new Runnable() {
                public void run() {
                    try {
                        String advertisingId = AdvertisingIdClient.getAdvertisingIdInfo(context).getId();
                        Config.gooleAdvertisingId = advertisingId;
                        Log.i("ABC", "advertisingId" + advertisingId);
                        Log.i("ABC", "advertisingId" + Config.gooleAdvertisingId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            return;
        }
        if (this.rkLibInitCallback != null) {
            initCallback.onInitFailed(Config.INIT_FAIL_NO_NETWORK);
        }
        Log.i("RKSDKInit", "初始化失败");
        CommonUtils.showToast(context, ResUtils.getString("no_network", context));
        BIConfig.getBiConfig().initFail(context);
    }

    public void rkLogin(Context context, RKLoginCallback loginCallback) {
        Config.loginCallback = loginCallback;
        if (TextUtils.isEmpty(Config.regId)) {
            Config.regId = "0";
        }
        Log.e("RKSDK", "登录：pushToken-Config.regId===" + Config.regId);
        Log.e("RKSDK", "登录：Config.isInit===" + Config.isInit);
        if (Config.regId == null || !Config.isInit) {
            Log.i("RKSDK", "LoginAction2");
            loginCallback.onFail(Config.LOGIN_NOT_INIT);
            ResUtils.getString("not_init", context);
            return;
        }
        Log.i("RKSDK", "LoginAction1");
        LoginManager.getManager().login(context);
    }

    public void rkLogout(Context context, RKLogoutCallback logoutCallback) {
        if (Config.isLogin) {
            Config.isLogout = true;
            logoutCallback.onSuccess(ResUtils.getString("logout_success", context));
            Config.isLogin = false;
            BIConfig.getBiConfig().logout(context);
            return;
        }
        logoutCallback.onFail(ResUtils.getString("logout_failed_not_login", context));
    }

    public void rkGetTransform(final Context context, final ProduceTransformCallback callback) {
        if (Config.uid == null) {
            rkLogin(context, new RKLoginCallback() {
                public void onSuccess(RKUser user) {
                    TransformManager.getInstentce().getTransform(context, callback);
                }

                public void onFail(String failMsg) {
                }

                public void onCancel() {
                }
            });
        } else {
            TransformManager.getInstentce().getTransform(context, callback);
        }
    }

    public void rkSetTransform(final Context context, final String transform, final TransformCallback transformCallback) {
        if (Config.uid == null) {
            rkLogin(context, new RKLoginCallback() {
                public void onSuccess(RKUser user) {
                    TransformManager.getInstentce().setTransform(context, transform, transformCallback);
                }

                public void onFail(String failMsg) {
                }

                public void onCancel() {
                }
            });
        } else {
            TransformManager.getInstentce().setTransform(context, transform, transformCallback);
        }
    }

    public void rkShowCenter(Context act, boolean isShow) {
        if (!Config.isLogin) {
            LogUtils.e("请先登录");
        } else if (isShow) {
            FloatManager.getManager(act).createView();
            Config.isShow = true;
        } else {
            FloatManager.getManager(act).removeView();
            Log.i("TAG", "removeView+removeView");
            Config.isShow = false;
        }
    }

    public void rkPush(Activity context) {
        try {
            GCMPush.getInstance().GCMRegistrarId(context);
        } catch (Exception e) {
            Log.d("TAG", "exception of register google push id");
            Config.regId = "0";
        }
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            public void run() {
                Config.isInit = true;
                if (RKPlatformManager.this.rkLibInitCallback != null) {
                    RKPlatformManager.this.rkLibInitCallback.onInitSuccess();
                }
            }
        }, 1500);
    }

    public void rkOnResume(Activity context) {
        Log.i("TAG", "rkOnResume====");
        if (!Config.isLogin) {
            FloatManager.getManager(context).removeView();
        }
    }

    public void rkOnPause(Context context) {
        Log.i("TAG", "rkOnPause====");
        if (Config.isLogin) {
            FloatManager.getManager(context).removeView();
        }
    }

    public void rkOnDestroy(Context context) {
        Log.i("RKSDK", "rkOnDestroy");
        Config.isInit = false;
        Config.isLogout = false;
        Config.isLogin = false;
        FloatManager.getManager(context).removeView();
    }

    public void rkOpenForum(Context context) {
        BIConfig.getBiConfig().gamesForumClick(context);
        CommonUtils.openActivity(context, FloatWebView.class);
    }
}
