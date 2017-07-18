package com.kayac.lobi.sdk.service.chat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.os.SystemClock;
import com.kayac.lobi.libnakamap.utils.Log;
import com.kayac.lobi.libnakamap.value.GroupStreamValue;

public class GroupEventService extends Service implements GroupEventListener {
    public static final String DO_RETRY = "doRetry";
    public static final String START_POLLING = "startPolling";
    public static final String STOP = "stop";
    private static final String TAG = "[groupevent]";
    private static final long TCP_KEEP_ALIVE_TIMEOUT = 180000;
    private static final String TCP_KEEP_ALIVE_TIMEOUT_FLAG = "tcp.keep.alive.timeout";
    private PendingIntent mMXHRKeepAliveTimer = null;
    private final BroadcastReceiver mNetworkStatusReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Log.i(GroupEventService.TAG, "network connectivity has changed!");
            NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra("networkInfo");
            if (networkInfo == null || !networkInfo.isConnected()) {
                GroupEventManager.getManager(GroupEventService.this.getApplicationContext()).retryFromService(false);
            }
        }
    };

    public void onCreate() {
        super.onCreate();
        Log.v(TAG, "register connectivity service");
        registerReceiver(this.mNetworkStatusReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.v(TAG, "onStart");
        if (intent != null) {
            if (intent.hasExtra(TCP_KEEP_ALIVE_TIMEOUT_FLAG)) {
                Log.i(TAG, "TCP_KEEP_ALIVE_TIMEOUT");
                GroupEventManager.getManager(getApplicationContext()).retryFromService(true);
                return;
            } else if (intent.hasExtra(START_POLLING)) {
                setKeepAliveAlarm();
                return;
            } else if (intent.hasExtra(DO_RETRY)) {
                Log.d(TAG, "DO_RETRY");
                GroupEventManager.getManager(getApplicationContext()).retryFromService(false);
                return;
            } else if (intent.hasExtra(STOP)) {
                if (this.mMXHRKeepAliveTimer != null) {
                    ((AlarmManager) getSystemService("alarm")).cancel(this.mMXHRKeepAliveTimer);
                }
                stopSelf();
            }
        }
        GroupEventManager.getManager(getApplicationContext()).addEventListener(this);
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onMessage(GroupStreamValue value) {
        setKeepAliveAlarm();
    }

    private void setKeepAliveAlarm() {
        Log.v(TAG, "setKeepAliveAlarm");
        cancelKeepAliveAlarm();
        long triggerAtTime = SystemClock.elapsedRealtime() + TCP_KEEP_ALIVE_TIMEOUT;
        this.mMXHRKeepAliveTimer = PendingIntent.getService(this, 0, new Intent().setClass(this, getClass()).putExtra(TCP_KEEP_ALIVE_TIMEOUT_FLAG, true), 134217728);
        ((AlarmManager) getSystemService("alarm")).set(2, triggerAtTime, this.mMXHRKeepAliveTimer);
    }

    private void cancelKeepAliveAlarm() {
        Log.v(TAG, "cancelKeepAliveAlarm");
        AlarmManager alarmManager = (AlarmManager) getSystemService("alarm");
        if (this.mMXHRKeepAliveTimer != null) {
            Log.v(TAG, "alarm was cancelled!");
            alarmManager.cancel(this.mMXHRKeepAliveTimer);
        }
    }

    public void onDestroy() {
        cancelKeepAliveAlarm();
        GroupEventManager.getManager(getApplicationContext()).removeEventListener((GroupEventListener) this);
        unregisterReceiver(this.mNetworkStatusReceiver);
        super.onDestroy();
    }
}
