package com.kayac.nakamap.sdk.auth;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import com.kayac.libnakamap.auth.INakamapAuthService.Stub;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.net.APIDef.PostBindStart.RequestKey;
import com.kayac.lobi.libnakamap.net.APIRes.PostBindStart;
import com.kayac.lobi.libnakamap.net.CoreAPI;
import com.kayac.lobi.libnakamap.net.CoreAPI.DefaultAPICallback;
import com.kayac.lobi.libnakamap.utils.Log;
import com.kayac.lobi.libnakamap.utils.NakamapSDKDatastore.Key;
import com.kayac.lobi.sdk.LobiCore;
import java.util.HashMap;
import java.util.Map;

public class NakamapAuthCallbackService extends Service {
    private static final String AUTH_SERVICE_CLASS = "com.kayac.libnakamap.auth.RemoteNakamapAuthService";
    private static final String NAKAMAP_AUTH_SERVICE = "com.kayac.nakamap.SUB_ACCOUNT_AUTH";
    private static final String NAKAMAP_PACKAGE = "com.kayac.nakamap";
    private static final String SDK_SESSION = "sdk_session";
    private final ServiceConnection mAuthServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder binder) {
            Log.v("nakamap-sdk", "[auth] onServiceConnected");
            try {
                Log.v("nakamap-sdk", "[auth] interface descriptor: " + binder.getInterfaceDescriptor());
            } catch (RemoteException e) {
            }
            String packageName = componentName.getPackageName();
            Log.v("nakamap-sdk", "[auth] packageName=" + packageName);
            String className = componentName.getClassName();
            Log.v("nakamap-sdk", "[auth] className=" + className);
            if (!NakamapAuthCallbackService.NAKAMAP_PACKAGE.equals(packageName)) {
                Log.v("nakamap-sdk", "[auth] packageName differs");
            } else if (NakamapAuthCallbackService.AUTH_SERVICE_CLASS.equals(className)) {
                try {
                    Stub.asInterface(binder).bind(LobiCore.sharedInstance().getClientId(), AccountDatastore.getCurrentUser().getUid(), NakamapAuthCallbackService.this.getBindToken());
                } catch (RemoteException e2) {
                    Log.w("nakamap-sdk", "[auth] remote exception", e2);
                }
                NakamapAuthCallbackService.this.unbindService(this);
                Log.v("nakamap-sdk", "[auth] unbindService");
            } else {
                Log.v("nakamap-sdk", "[auth] className differs");
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
            Log.v("nakamap-sdk", "[auth] onServiceDisconnected: " + componentName);
            NakamapAuthCallbackService.this.stopSelf();
        }
    };
    private String mBindToken = null;

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("nakamap-sdk", "[auth] NakamapAuthCallbackService::onCreate");
        if (!LobiCore.isSetup()) {
            Log.w("nakamap-sdk", "[auth] LobiCore is not setup. so service stop");
            stopSelf();
        } else if (intent == null) {
            Log.w("nakamap-sdk", "[auth] intent is null. so service stop");
            stopSelf();
        } else if (LobiCore.isSignedIn()) {
            String sdkSessionExtra = intent.getStringExtra("sdk_session");
            String sdkSession = (String) AccountDatastore.getValue(Key.NAKAMAP_AUTH_SESSION, "");
            if (TextUtils.isEmpty(sdkSessionExtra) || !TextUtils.equals(sdkSession, sdkSessionExtra)) {
                Log.w("nakamap-sdk", "[auth] sdkSession doesn't match: " + sdkSessionExtra);
                stopSelf();
            } else {
                Log.v("nakamap-sdk", "[auth] sdkSession=" + sdkSessionExtra);
                String accessToken = AccountDatastore.getCurrentUser().getToken();
                Map<String, String> params = new HashMap();
                params.put("token", accessToken);
                params.put("client_id", LobiCore.sharedInstance().getClientId());
                params.put(RequestKey.TID, Integer.toString(0));
                CoreAPI.postBindStart(params, new DefaultAPICallback<PostBindStart>(this) {
                    public void onResponse(PostBindStart t) {
                        Log.v("nakamap-sdk", "[auth] postBindStart success: " + t.bindToken);
                        NakamapAuthCallbackService.this.setBindToken(t.bindToken);
                        NakamapAuthCallbackService.this.bindService(new Intent(NakamapAuthCallbackService.NAKAMAP_AUTH_SERVICE), NakamapAuthCallbackService.this.mAuthServiceConnection, 1);
                    }

                    public void onError(int statusCode, String responseBody) {
                    }

                    public void onError(Throwable e) {
                    }
                });
            }
        } else {
            Log.w("nakamap-sdk", "[auth] not signed in stop self");
            stopSelf();
        }
        return 0;
    }

    private synchronized void setBindToken(String bindToken) {
        this.mBindToken = bindToken;
    }

    private synchronized String getBindToken() {
        return this.mBindToken;
    }

    public IBinder onBind(Intent intent) {
        return null;
    }
}
