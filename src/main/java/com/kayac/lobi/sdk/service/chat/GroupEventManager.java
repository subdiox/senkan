package com.kayac.lobi.sdk.service.chat;

import android.content.Context;
import android.content.Intent;
import com.kayac.lobi.libnakamap.commet.CommetEventDispatcher;
import com.kayac.lobi.libnakamap.net.ConnectivityUtil;
import com.kayac.lobi.libnakamap.utils.Log;
import com.kayac.lobi.libnakamap.value.GroupStreamValue;
import com.kayac.lobi.sdk.service.chat.GroupEventPollingTask.Callback;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import org.json.JSONObject;

public final class GroupEventManager extends CommetEventDispatcher<GroupEventListener> implements Callback {
    private static final String TAG = "[group.stream]";
    private static GroupEventManager mManager;
    private final Context mContext;
    private GroupEventThreadPoolExecutor mExecutor = new GroupEventThreadPoolExecutor();
    private String mGroupUid;
    private volatile boolean mIsPolling = false;
    private Future<?> mPollingFuture;
    private GroupEventPollingTask mPollingTask;
    private final Runnable mRetryTask = new Runnable() {
        public void run() {
            GroupEventManager.this.retry();
        }
    };
    private final Runnable mStartPollingTask = new Runnable() {
        public void run() {
            if (GroupEventManager.this.mPollingTask != null) {
                Log.d(GroupEventManager.TAG, "start with new polling task");
                GroupEventManager.this.startPollingInternal(GroupEventManager.this.mPollingTask.copy());
            }
        }
    };

    private GroupEventManager(Context context) {
        this.mContext = context;
    }

    private void retry() {
        if (!this.mIsPolling) {
            Log.w(TAG, "polling has been stopped!");
        } else if (true) {
            this.mHandler.post(this.mStartPollingTask);
        }
    }

    public void removeEventListener(GroupEventListener listener) {
        super.removeEventListener(listener);
        Log.v("chat.stream", "number of listeners: " + this.mEventListeners.size());
        if (this.mIsPolling && this.mEventListeners.size() == 0) {
            stopPolling();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void startPolling(java.lang.String r4, java.lang.String r5) {
        /*
        r3 = this;
        monitor-enter(r3);
        r0 = r3.mPollingTask;	 Catch:{ all -> 0x003a }
        if (r0 == 0) goto L_0x0029;
    L_0x0005:
        r0 = r3.mPollingTask;	 Catch:{ all -> 0x003a }
        r0 = r0.groupUid;	 Catch:{ all -> 0x003a }
        r0 = r0.equals(r5);	 Catch:{ all -> 0x003a }
        if (r0 == 0) goto L_0x0029;
    L_0x000f:
        r0 = "[group.stream]";
        r1 = new java.lang.StringBuilder;	 Catch:{ all -> 0x003a }
        r1.<init>();	 Catch:{ all -> 0x003a }
        r2 = "already polling: ";
        r1 = r1.append(r2);	 Catch:{ all -> 0x003a }
        r1 = r1.append(r5);	 Catch:{ all -> 0x003a }
        r1 = r1.toString();	 Catch:{ all -> 0x003a }
        com.kayac.lobi.libnakamap.utils.Log.i(r0, r1);	 Catch:{ all -> 0x003a }
        monitor-exit(r3);	 Catch:{ all -> 0x003a }
    L_0x0028:
        return;
    L_0x0029:
        r3.mGroupUid = r5;	 Catch:{ all -> 0x003a }
        monitor-exit(r3);	 Catch:{ all -> 0x003a }
        if (r4 == 0) goto L_0x0032;
    L_0x002e:
        r0 = r3.mGroupUid;
        if (r0 != 0) goto L_0x003d;
    L_0x0032:
        r0 = "[group.stream]";
        r1 = "you must specify stream host / group uid!";
        com.kayac.lobi.libnakamap.utils.Log.w(r0, r1);
        goto L_0x0028;
    L_0x003a:
        r0 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x003a }
        throw r0;
    L_0x003d:
        r0 = new com.kayac.lobi.sdk.service.chat.GroupEventPollingTask;
        r1 = r3.mGroupUid;
        r0.<init>(r4, r1, r3);
        r3.startPollingInternal(r0);
        goto L_0x0028;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.kayac.lobi.sdk.service.chat.GroupEventManager.startPolling(java.lang.String, java.lang.String):void");
    }

    private synchronized void startPollingInternal(GroupEventPollingTask task) {
        Log.v(TAG, "startPollingInternal");
        cancel();
        this.mPollingTask = null;
        try {
            Log.v(TAG, "submit!");
            this.mPollingFuture = this.mExecutor.submit(task);
            this.mPollingTask = task;
            setKeepAliveTimerInService(this.mContext);
            this.mIsPolling = true;
        } catch (RejectedExecutionException e) {
            Log.i(TAG, "rejected: " + e.getMessage());
            if (!this.mExecutor.isShutdown() && this.mExecutor.getActiveCount() < this.mExecutor.getMaximumPoolSize()) {
                this.mHandler.post(this.mRetryTask);
            }
        }
    }

    public boolean isPolling() {
        return this.mIsPolling;
    }

    private static void setKeepAliveTimerInService(Context context) {
        context.startService(new Intent(context, GroupEventService.class).putExtra(GroupEventService.START_POLLING, true));
    }

    void retryFromService(boolean shouldShutDownCurrentExecutor) {
        cancel();
        if (shouldShutDownCurrentExecutor) {
            shutdownCurrent(this.mExecutor);
            this.mExecutor = new GroupEventThreadPoolExecutor();
        }
        this.mHandler.post(this.mRetryTask);
    }

    private void shutdownCurrent(final ExecutorService service) {
        final ExecutorService shutdownService = Executors.newSingleThreadExecutor();
        shutdownService.execute(new Runnable() {
            public void run() {
                Log.v(GroupEventManager.TAG, "shutting down the executor...");
                service.shutdown();
                service.shutdownNow();
                GroupEventManager.this.mHandler.post(new Runnable() {
                    public void run() {
                        if (!shutdownService.isShutdown()) {
                            shutdownService.shutdown();
                        }
                    }
                });
            }
        });
    }

    private void cancel() {
        Log.i(TAG, "cancel");
        if (this.mPollingTask != null) {
            Log.i(TAG, "cancelled the task");
            this.mPollingTask.cancel();
        }
        if (this.mPollingFuture != null) {
            if (!(this.mPollingFuture.isDone() || this.mPollingFuture.isCancelled())) {
                Log.v(TAG, "cancel polling");
                this.mPollingFuture.cancel(true);
            }
            this.mPollingFuture = null;
        }
        this.mExecutor.getQueue().clear();
    }

    public synchronized void stopPolling() {
        Log.v(TAG, "stopPolling");
        cancel();
        this.mPollingTask = null;
        this.mGroupUid = null;
        this.mContext.startService(new Intent(this.mContext, GroupEventService.class).putExtra(GroupEventService.STOP, true));
        this.mHandler.removeCallbacks(this.mRetryTask);
        this.mHandler.removeCallbacks(this.mStartPollingTask);
        this.mIsPolling = false;
    }

    public static synchronized GroupEventManager getManager(Context context) {
        GroupEventManager groupEventManager;
        synchronized (GroupEventManager.class) {
            if (mManager == null) {
                mManager = new GroupEventManager(context);
            }
            groupEventManager = mManager;
        }
        return groupEventManager;
    }

    private void onMessage(final GroupStreamValue message) {
        this.mHandler.post(new Runnable() {
            public void run() {
                Iterator it = ((Vector) GroupEventManager.this.mEventListeners.clone()).iterator();
                while (it.hasNext()) {
                    ((GroupEventListener) it.next()).onMessage(message);
                }
            }
        });
    }

    public synchronized String getGroupUid() {
        return this.mGroupUid;
    }

    public void onEvent(String uid, JSONObject jsonObject) {
        Log.v(TAG, "onEvent: " + jsonObject);
        onMessage(new GroupStreamValue(jsonObject));
    }

    public void onTaskEnd(boolean isCancelled, boolean error) {
        if (!isCancelled) {
            long retryDelay = !ConnectivityUtil.isNetworkAvailable(this.mContext) ? 60000 : error ? 10000 : (long) (((Math.random() * 3.0d) + 4.0d) * 1000.0d);
            this.mHandler.postDelayed(this.mRetryTask, retryDelay);
        }
    }
}
