package cn.sharesdk.framework.b;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import cn.sharesdk.framework.b.a.e;
import cn.sharesdk.framework.utils.d;
import com.kayac.lobi.sdk.migration.datastore.NakamapDatastore.Key;
import com.mob.tools.network.KVPair;
import com.mob.tools.network.NetworkHelper;
import com.mob.tools.network.NetworkHelper.NetworkTimeOut;
import com.mob.tools.utils.Data;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.Hashon;
import com.mob.tools.utils.R;
import com.rekoo.libs.net.URLCons;
import java.util.ArrayList;
import java.util.HashMap;

public class c {
    private String a;
    private Context b;
    private e c = e.a(this.b);
    private DeviceHelper d = DeviceHelper.getInstance(this.b);
    private NetworkHelper e = new NetworkHelper();
    private Hashon f = new Hashon();
    private String g;
    private String h;
    private boolean i;
    private HashMap<String, String> j;

    public c(Context context, String str) {
        this.a = str;
        this.b = context.getApplicationContext();
        try {
            this.j = (HashMap) this.c.h("buffered_server_paths");
        } catch (Throwable th) {
            this.j = new HashMap();
        }
        g();
    }

    private String e(String str) {
        boolean b = this.c.b();
        boolean c = this.c.c();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Data.urlEncode(this.d.getPackageName(), "utf-8")).append("|");
        stringBuilder.append(Data.urlEncode(this.d.getAppVersionName(), "utf-8")).append("|");
        stringBuilder.append(Data.urlEncode(String.valueOf(60063), "utf-8")).append("|");
        stringBuilder.append(Data.urlEncode(String.valueOf(this.d.getPlatformCode()), "utf-8")).append("|");
        stringBuilder.append(Data.urlEncode(this.d.getDetailNetworkTypeForStatic(), "utf-8")).append("|");
        if (b) {
            stringBuilder.append(Data.urlEncode(String.valueOf(this.d.getOSVersionInt()), "utf-8")).append("|");
            stringBuilder.append(Data.urlEncode(this.d.getScreenSize(), "utf-8")).append("|");
            stringBuilder.append(Data.urlEncode(this.d.getManufacturer(), "utf-8")).append("|");
            stringBuilder.append(Data.urlEncode(this.d.getModel(), "utf-8")).append("|");
            stringBuilder.append(Data.urlEncode(this.d.getCarrier(), "utf-8")).append("|");
        } else {
            stringBuilder.append("|||||");
        }
        if (c) {
            stringBuilder.append(str);
        } else {
            stringBuilder.append(str.split("\\|")[0]);
            stringBuilder.append("|||||");
        }
        d.a().i("shorLinkMsg ===>>>>", stringBuilder.toString());
        return Base64.encodeToString(Data.AES128Encode(Data.rawMD5(String.format("%s:%s", new Object[]{this.d.getDeviceKey(), this.a})), stringBuilder.toString()), 2);
    }

    private void g() {
        String str = this.d.getPackageName() + "/" + this.d.getAppVersionName();
        this.g = str + " " + "ShareSDK/2.7.5" + " " + ("Android/" + this.d.getOSVersionInt());
        this.h = "http://api.share.mob.com:80";
        this.i = true;
    }

    private String h() {
        return this.h + "/conn";
    }

    private String i() {
        return (this.j == null || !this.j.containsKey("/date")) ? this.h + "/date" : ((String) this.j.get("/date")) + "/date";
    }

    private String j() {
        return this.h + "/conf5";
    }

    private String k() {
        return (this.j == null || !this.j.containsKey("/data2")) ? this.h + "/data2" : ((String) this.j.get("/data2")) + "/data2";
    }

    private String l() {
        return "http://up.sharesdk.cn/upload/image";
    }

    private String m() {
        return (this.j == null || !this.j.containsKey("/log4")) ? this.h + "/log4" : ((String) this.j.get("/log4")) + "/log4";
    }

    private String n() {
        return "http://l.mob.com/url/ShareSdkMapping.do";
    }

    private String o() {
        return (this.j == null || !this.j.containsKey("/snsconf")) ? this.h + "/snsconf" : ((String) this.j.get("/snsconf")) + "/snsconf";
    }

    public HashMap<String, Object> a() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new KVPair("appkey", this.a));
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(new KVPair("User-Agent", this.g));
        NetworkTimeOut networkTimeOut = new NetworkTimeOut();
        networkTimeOut.readTimout = 30000;
        networkTimeOut.connectionTimeout = 30000;
        d.a().i(" isConnectToServer response == %s", this.e.httpPost(h(), arrayList, null, arrayList2, networkTimeOut));
        return this.f.fromJson(this.e.httpPost(h(), arrayList, null, arrayList2, networkTimeOut));
    }

    public HashMap<String, Object> a(String str, ArrayList<String> arrayList, int i, String str2) {
        if (!this.i) {
            return null;
        }
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(new KVPair("key", this.a));
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            arrayList2.add(new KVPair("urls", ((String) arrayList.get(i2)).toString()));
        }
        arrayList2.add(new KVPair("deviceid", this.d.getDeviceKey()));
        arrayList2.add(new KVPair("snsplat", String.valueOf(i)));
        CharSequence e = e(str2);
        if (TextUtils.isEmpty(e)) {
            return null;
        }
        arrayList2.add(new KVPair("m", e));
        ArrayList arrayList3 = new ArrayList();
        arrayList3.add(new KVPair("User-Agent", this.g));
        NetworkTimeOut networkTimeOut = new NetworkTimeOut();
        networkTimeOut.readTimout = 5000;
        networkTimeOut.connectionTimeout = 5000;
        String httpPost = this.e.httpPost(n(), arrayList2, null, arrayList3, networkTimeOut);
        d.a().i("> SERVER_SHORT_LINK_URL  resp: %s", httpPost);
        if (TextUtils.isEmpty(httpPost)) {
            this.i = false;
            return null;
        }
        HashMap<String, Object> fromJson = this.f.fromJson(httpPost);
        return ((Integer) fromJson.get("status")).intValue() == 200 ? fromJson : null;
    }

    public void a(cn.sharesdk.framework.b.b.c cVar) {
        cn.sharesdk.framework.b.a.d.a(this.b, cVar.toString(), cVar.e);
    }

    public void a(String str) {
        this.h = str;
    }

    public void a(ArrayList<String> arrayList) {
        cn.sharesdk.framework.b.a.d.a(this.b, arrayList);
    }

    public void a(HashMap<String, String> hashMap) {
        this.j = hashMap;
        this.c.a("buffered_server_paths", this.j);
    }

    public boolean a(String str, boolean z) {
        try {
            if ("none".equals(this.d.getDetailNetworkTypeForStatic())) {
                throw new IllegalStateException("network is disconnected!");
            }
            ArrayList arrayList = new ArrayList();
            arrayList.add(new KVPair("m", str));
            arrayList.add(new KVPair("t", z ? "1" : "0"));
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(new KVPair("User-Agent", this.g));
            NetworkTimeOut networkTimeOut = new NetworkTimeOut();
            networkTimeOut.readTimout = 30000;
            networkTimeOut.connectionTimeout = 30000;
            String httpPost = this.e.httpPost(m(), arrayList, null, arrayList2, networkTimeOut);
            d.a().i("> Upload All Log  resp: %s", httpPost);
            return TextUtils.isEmpty(httpPost) || ((Integer) this.f.fromJson(httpPost).get("status")).intValue() == 200;
        } catch (Throwable th) {
            d.a().d(th);
            return false;
        }
    }

    public long b() {
        if (!this.c.g()) {
            return 0;
        }
        String str = "{}";
        try {
            str = this.e.httpGet(i(), null, null, null);
        } catch (Throwable th) {
            d.a().d(th);
        }
        HashMap fromJson = this.f.fromJson(str);
        if (!fromJson.containsKey("timestamp")) {
            return this.c.a();
        }
        try {
            long currentTimeMillis = System.currentTimeMillis() - R.parseLong(String.valueOf(fromJson.get("timestamp")));
            this.c.a("service_time", Long.valueOf(currentTimeMillis));
            return currentTimeMillis;
        } catch (Throwable th2) {
            d.a().d(th2);
            return this.c.a();
        }
    }

    public HashMap<String, Object> b(String str) {
        KVPair kVPair = new KVPair(Key.FILE, str);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new KVPair("User-Agent", this.g));
        d.a().i("upload file response == %s", this.e.httpPost(l(), null, kVPair, arrayList, null));
        return this.f.fromJson(this.e.httpPost(l(), null, kVPair, arrayList, null));
    }

    public void b(HashMap<String, Object> hashMap) {
        this.c.a(this.a, this.f.fromHashMap(hashMap));
    }

    public HashMap<String, Object> c() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new KVPair("appkey", this.a));
        arrayList.add(new KVPair(URLCons.DEVICE, this.d.getDeviceKey()));
        arrayList.add(new KVPair("plat", String.valueOf(this.d.getPlatformCode())));
        arrayList.add(new KVPair("apppkg", this.d.getPackageName()));
        arrayList.add(new KVPair("appver", String.valueOf(this.d.getAppVersion())));
        arrayList.add(new KVPair("sdkver", String.valueOf(60063)));
        arrayList.add(new KVPair("networktype", this.d.getDetailNetworkTypeForStatic()));
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(new KVPair("User-Agent", this.g));
        NetworkTimeOut networkTimeOut = new NetworkTimeOut();
        networkTimeOut.readTimout = 10000;
        networkTimeOut.connectionTimeout = 10000;
        d.a().i(" get server config response == %s", this.e.httpPost(j(), arrayList, null, arrayList2, networkTimeOut));
        return this.f.fromJson(this.e.httpPost(j(), arrayList, null, arrayList2, networkTimeOut));
    }

    public HashMap<String, Object> c(String str) {
        return this.f.fromJson(new String(Data.AES128Decode(Data.rawMD5(this.a + ":" + this.d.getDeviceKey()), Base64.decode(str, 2)), "UTF-8").trim());
    }

    public HashMap<String, Object> d() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new KVPair("appkey", this.a));
        arrayList.add(new KVPair(URLCons.DEVICE, this.d.getDeviceKey()));
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(new KVPair("User-Agent", this.g));
        NetworkTimeOut networkTimeOut = new NetworkTimeOut();
        networkTimeOut.readTimout = 10000;
        networkTimeOut.connectionTimeout = 10000;
        return this.f.fromJson(this.e.httpPost(o(), arrayList, null, arrayList2, networkTimeOut));
    }

    public void d(String str) {
        HashMap hashMap = new HashMap();
        hashMap.put("type", "DUID");
        hashMap.put("plat", Integer.valueOf(this.d.getPlatformCode()));
        hashMap.put(URLCons.DEVICE, this.d.getDeviceKey());
        hashMap.put("duid", str);
        hashMap.put(URLCons.MAC, this.d.getMacAddress());
        hashMap.put("udid", this.d.getDeviceId());
        hashMap.put("model", this.d.getModel());
        String fromHashMap = this.f.fromHashMap(hashMap);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new KVPair("m", Data.Base64AES(fromHashMap, "sdk.sharesdk.sdk")));
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(new KVPair("User-Agent", this.g));
        NetworkTimeOut networkTimeOut = new NetworkTimeOut();
        networkTimeOut.readTimout = 30000;
        networkTimeOut.connectionTimeout = 30000;
        fromHashMap = this.e.httpPost(k(), arrayList, null, arrayList2, networkTimeOut);
        d.a().i("> uploadDuid  resp: %s", fromHashMap);
    }

    public ArrayList<cn.sharesdk.framework.b.a.c> e() {
        ArrayList<cn.sharesdk.framework.b.a.c> a = cn.sharesdk.framework.b.a.d.a(this.b);
        return a == null ? new ArrayList() : a;
    }

    public HashMap<String, Object> f() {
        return this.f.fromJson(this.c.e(this.a));
    }
}
