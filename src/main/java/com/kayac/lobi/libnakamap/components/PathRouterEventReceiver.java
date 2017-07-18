package com.kayac.lobi.libnakamap.components;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import com.kayac.lobi.libnakamap.utils.Log;
import com.kayac.lobi.libnakamap.utils.NakamapBroadcastManager;
import com.kayac.lobi.sdk.utils.SDKBridge;
import java.lang.ref.WeakReference;

public class PathRouterEventReceiver extends BroadcastReceiver {
    protected final WeakReference<Activity> mActivityRef;
    protected boolean mIsRemovedByRouter;
    protected String mPath;

    protected void addActions(IntentFilter filter) {
        filter.addAction(PathRouter.START_PATH);
        filter.addAction(PathRouter.REMOVE_PATH);
    }

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Bundle bundle = intent.getExtras();
        if (PathRouter.REMOVE_PATH.equals(action)) {
            String path = bundle.getString(PathRouter.PATH);
            if (TextUtils.equals(this.mPath, path)) {
                this.mIsRemovedByRouter = true;
                Log.v(PathRouter.TAG, "path: " + path + " committing suicide");
                Activity activity = (Activity) this.mActivityRef.get();
                if (activity != null) {
                    activity.finish();
                }
            }
        }
    }

    public PathRouterEventReceiver(Activity activity) {
        this.mActivityRef = new WeakReference(activity);
    }

    public final void onCreate(Bundle savedInstance) {
        Activity activity = (Activity) this.mActivityRef.get();
        if (activity != null) {
            this.mPath = activity.getIntent().getStringExtra(PathRouter.PATH);
            if (!TextUtils.isEmpty(this.mPath)) {
                NakamapBroadcastManager manager = getBroadcastManagerInstance();
                IntentFilter filter = new IntentFilter();
                addActions(filter);
                manager.registerReceiver(this, filter);
                manager.sendBroadcastSync(new Intent(PathRouter.ACTIVITY_ONCREATE).putExtra(PathRouter.PATH, this.mPath));
                PathRouter.restoreInstanceState(this.mPath, savedInstance);
                if (!PathRouter.hasPath(this.mPath)) {
                    manager.sendBroadcastSync(new Intent(PathRouter.REMOVE_PATH).putExtra(PathRouter.PATH, this.mPath));
                }
            }
        }
    }

    public final void onDestroy() {
        NakamapBroadcastManager manager = getBroadcastManagerInstance();
        manager.unregisterReceiver(this);
        Activity activity = (Activity) this.mActivityRef.get();
        if (activity != null && activity.isFinishing()) {
            Log.v(PathRouter.TAG, "onDestroy: is suicide " + this.mIsRemovedByRouter + " " + this.mPath + " " + activity);
            if (!this.mIsRemovedByRouter) {
                manager.sendBroadcast(new Intent(PathRouter.ACTIVITY_ONDESTROY).putExtra(PathRouter.PATH, this.mPath));
            }
        }
    }

    private static NakamapBroadcastManager getBroadcastManagerInstance() {
        return NakamapBroadcastManager.getInstance(SDKBridge.getContext());
    }
}
