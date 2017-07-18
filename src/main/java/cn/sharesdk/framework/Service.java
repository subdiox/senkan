package cn.sharesdk.framework;

import android.content.Context;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.b.b.f.a;
import cn.sharesdk.framework.utils.d;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.Hashon;
import java.util.HashMap;

public abstract class Service {
    private Context a;
    private String b;

    public static abstract class ServiceEvent {
        private final int PLATFORM = 1;
        protected Service service;

        public ServiceEvent(Service service) {
            this.service = service;
        }

        protected HashMap<String, Object> filterShareContent(int platformId, ShareParams params, HashMap<String, Object> result) {
            a filterShareContent = ShareSDK.getPlatform(ShareSDK.platformIdToName(platformId)).filterShareContent(params, result);
            HashMap<String, Object> hashMap = new HashMap();
            hashMap.put("shareID", filterShareContent.a);
            hashMap.put("shareContent", new Hashon().fromJson(filterShareContent.toString()));
            d.a().i("filterShareContent ==>>%s", hashMap);
            return hashMap;
        }

        protected HashMap<String, Object> toMap() {
            HashMap<String, Object> hashMap = new HashMap();
            DeviceHelper instance = DeviceHelper.getInstance(this.service.a);
            hashMap.put("deviceid", instance.getDeviceKey());
            hashMap.put("appkey", this.service.getAppKey());
            hashMap.put("apppkg", instance.getPackageName());
            hashMap.put("appver", Integer.valueOf(instance.getAppVersion()));
            hashMap.put("sdkver", Integer.valueOf(this.service.getServiceVersionInt()));
            hashMap.put("plat", Integer.valueOf(1));
            hashMap.put("networktype", instance.getDetailNetworkTypeForStatic());
            hashMap.put("deviceData", instance.getDeviceDataNotAES());
            return hashMap;
        }

        public final String toString() {
            return new Hashon().fromHashMap(toMap());
        }
    }

    void a(Context context) {
        this.a = context;
    }

    void a(String str) {
        this.b = str;
    }

    public String getAppKey() {
        return this.b;
    }

    public Context getContext() {
        return this.a;
    }

    public String getDeviceKey() {
        return DeviceHelper.getInstance(this.a).getDeviceKey();
    }

    protected abstract int getServiceVersionInt();

    public abstract String getServiceVersionName();

    public void onBind() {
    }

    public void onUnbind() {
    }
}
