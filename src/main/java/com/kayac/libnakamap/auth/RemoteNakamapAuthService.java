package com.kayac.libnakamap.auth;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import com.kayac.libnakamap.auth.INakamapAuthService.Stub;
import com.kayac.lobi.libnakamap.datastore.AccountDDL.KKey.SDKBindFinish;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.utils.Log;

public class RemoteNakamapAuthService extends Service {
    private final Stub mBinder = new Stub() {
        public void bind(String clientId, String uid, String bindToken) throws RemoteException {
            RemoteNakamapAuthService.this.bindImpl(clientId, uid, bindToken);
        }
    };

    public IBinder onBind(Intent intent) {
        return this.mBinder;
    }

    private void bindImpl(String clientId, String uid, String bindToken) {
        Log.v("nakamap-sdk", "[auth] RemoteNakamapAuthService::bindImpl");
        Log.v("nakamap-sdk", "[auth] clientId=" + clientId);
        Log.v("nakamap-sdk", "[auth] bindToken=" + bindToken);
        AccountDatastore.setKKValue(SDKBindFinish.KEY1, "sdk_client", clientId);
        AccountDatastore.setKKValue(SDKBindFinish.KEY1, "uid", uid);
        AccountDatastore.setKKValue(SDKBindFinish.KEY1, SDKBindFinish.BIND_TOKEN, bindToken);
    }

    public boolean onUnbind(Intent intent) {
        Log.v("nakamap-sdk", "[auth] RemoteNakamapAuthService::onUnbind");
        stopSelf();
        return super.onUnbind(intent);
    }
}
