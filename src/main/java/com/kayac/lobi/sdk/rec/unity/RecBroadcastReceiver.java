package com.kayac.lobi.sdk.rec.unity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.kayac.lobi.libnakamap.rec.LobiRec;
import com.kayac.lobi.libnakamap.rec.a.b;
import com.kayac.lobi.sdk.utils.UnityUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONObject;

public class RecBroadcastReceiver extends BroadcastReceiver {
    private static final ArrayList<String> ACTIONS = new ArrayList(Arrays.asList(new String[]{LobiRec.ACTION_DRYING_UP_INSTORAGE, LobiRec.ACTION_FINISH_POST_VIDEO_ACTIVITY, LobiRec.ACTION_MOVIE_CREATED, LobiRec.ACTION_MOVIE_CREATED_ERROR, LobiRec.ACTION_MOVIE_UPLOADED, LobiRec.ACTION_MOVIE_UPLOADED_ERROR}));
    public static Map<String, UnityActionObserver> sObservers = new HashMap();

    private static class UnityActionObserver {
        public String callbackMethodName;
        public String gameObjectName;

        public UnityActionObserver(String str, String str2) {
            this.gameObjectName = str;
            this.callbackMethodName = str2;
        }
    }

    private static boolean canReceive(String str) {
        return ACTIONS.contains(str);
    }

    public static void registerObserver(String str, String str2, String str3) {
        if (canReceive(str)) {
            sObservers.put(str, new UnityActionObserver(str2, str3));
        }
    }

    public static void start(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        Iterator it = ACTIONS.iterator();
        while (it.hasNext()) {
            intentFilter.addAction((String) it.next());
        }
        context.registerReceiver(new RecBroadcastReceiver(), intentFilter);
    }

    public static void unregisterObserver(String str) {
        if (canReceive(str)) {
            sObservers.remove(str);
        }
    }

    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            UnityActionObserver unityActionObserver = (UnityActionObserver) sObservers.get(action);
            if (unityActionObserver != null) {
                String str = "";
                if (LobiRec.ACTION_MOVIE_CREATED.equals(action)) {
                    str = intent.getStringExtra(LobiRec.EXTRA_MOVIE_CREATED_URL);
                    action = intent.getStringExtra(LobiRec.EXTRA_MOVIE_CREATED_VIDEO_ID);
                    JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.put("url", str);
                        jSONObject.put("videoId", action);
                    } catch (Throwable e) {
                        b.a(e);
                    }
                    str = jSONObject.toString();
                } else if (LobiRec.ACTION_FINISH_POST_VIDEO_ACTIVITY.equals(action)) {
                    boolean booleanExtra = intent.getBooleanExtra(LobiRec.EXTRA_FINISH_POST_VIDEO_ACTIVITY_TRY_POST, false);
                    boolean booleanExtra2 = intent.getBooleanExtra(LobiRec.EXTRA_FINISH_POST_VIDEO_ACTIVITY_TWITTER_SHARE, false);
                    boolean booleanExtra3 = intent.getBooleanExtra(LobiRec.EXTRA_FINISH_POST_VIDEO_ACTIVITY_FACEBOOK_SHARE, false);
                    boolean booleanExtra4 = intent.getBooleanExtra(LobiRec.EXTRA_FINISH_POST_VIDEO_ACTIVITY_YOUTUBE_SHARE, false);
                    boolean booleanExtra5 = intent.getBooleanExtra(LobiRec.EXTRA_FINISH_POST_VIDEO_ACTIVITY_NICOVIDEO_SHARE, false);
                    JSONObject jSONObject2 = new JSONObject();
                    try {
                        jSONObject2.put("try_post", booleanExtra ? "1" : "0");
                        jSONObject2.put("twitter_share", booleanExtra2 ? "1" : "0");
                        jSONObject2.put("facebook_share", booleanExtra3 ? "1" : "0");
                        jSONObject2.put("youtube_share", booleanExtra4 ? "1" : "0");
                        jSONObject2.put("nicovideo_share", booleanExtra5 ? "1" : "0");
                    } catch (Throwable e2) {
                        b.a(e2);
                    }
                    str = jSONObject2.toString();
                }
                UnityUtils.unitySendMessage(unityActionObserver.gameObjectName, unityActionObserver.callbackMethodName, str);
            }
        }
    }
}
