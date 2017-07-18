package cn.sharesdk.unity3d;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import com.kayac.lobi.sdk.migration.datastore.NakamapDatastore.Key;
import com.mob.tools.utils.Hashon;
import com.rekoo.libs.net.URLCons;
import com.unity3d.player.UnityPlayer;
import java.util.ArrayList;
import java.util.HashMap;

public class Unity3dPlatformActionListener implements PlatformActionListener {
    private int reqID;
    private String u3dCallback;
    private String u3dGameObject;

    public Unity3dPlatformActionListener(String u3dGameObject, String u3dCallback) {
        this.u3dGameObject = u3dGameObject;
        this.u3dCallback = u3dCallback;
    }

    public void setReqID(int reqID) {
        this.reqID = reqID;
    }

    public void onError(Platform platform, int action, Throwable t) {
        UnityPlayer.UnitySendMessage(this.u3dGameObject, this.u3dCallback, javaActionResToCS(platform, action, t));
    }

    public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
        UnityPlayer.UnitySendMessage(this.u3dGameObject, this.u3dCallback, javaActionResToCS(platform, action, (HashMap) res));
    }

    public void onCancel(Platform platform, int action) {
        UnityPlayer.UnitySendMessage(this.u3dGameObject, this.u3dCallback, javaActionResToCS(platform, action));
    }

    private String javaActionResToCS(Platform platform, int action, Throwable t) {
        int platformId = ShareSDK.platformNameToId(platform.getName());
        HashMap<String, Object> map = new HashMap();
        map.put("reqID", Integer.valueOf(this.reqID));
        map.put("platform", Integer.valueOf(platformId));
        map.put("action", Integer.valueOf(action));
        map.put("status", Integer.valueOf(2));
        map.put("res", throwableToMap(t));
        return new Hashon().fromHashMap(map);
    }

    private String javaActionResToCS(Platform platform, int action, HashMap<String, Object> res) {
        int platformId = ShareSDK.platformNameToId(platform.getName());
        HashMap<String, Object> map = new HashMap();
        map.put("reqID", Integer.valueOf(this.reqID));
        map.put("platform", Integer.valueOf(platformId));
        map.put("action", Integer.valueOf(action));
        map.put("status", Integer.valueOf(1));
        map.put("res", res);
        return new Hashon().fromHashMap(map);
    }

    private String javaActionResToCS(Platform platform, int action) {
        int platformId = ShareSDK.platformNameToId(platform.getName());
        HashMap<String, Object> map = new HashMap();
        map.put("reqID", Integer.valueOf(this.reqID));
        map.put("platform", Integer.valueOf(platformId));
        map.put("action", Integer.valueOf(action));
        map.put("status", Integer.valueOf(3));
        return new Hashon().fromHashMap(map);
    }

    private HashMap<String, Object> throwableToMap(Throwable t) {
        HashMap<String, Object> map = new HashMap();
        map.put("msg", t.getMessage());
        ArrayList<HashMap<String, Object>> traces = new ArrayList();
        for (StackTraceElement trace : t.getStackTrace()) {
            HashMap<String, Object> element = new HashMap();
            element.put("cls", trace.getClassName());
            element.put(URLCons.METHOD, trace.getMethodName());
            element.put(Key.FILE, trace.getFileName());
            element.put("line", Integer.valueOf(trace.getLineNumber()));
            traces.add(element);
        }
        map.put("stack", traces);
        Throwable cause = t.getCause();
        if (cause != null) {
            map.put("cause", throwableToMap(cause));
        }
        return map;
    }
}
