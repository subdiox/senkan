package com.mob.commons;

import android.content.Context;
import cn.sharesdk.framework.ShareSDK;
import com.mob.tools.MobLog;
import com.mob.tools.network.NetworkHelper;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.ReflectHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.TimeZone;

public class f extends NetworkHelper {
    private static final String[] a = new String[]{ShareSDK.SDK_TAG, "SMSSDK", "SHAREREC", "MOBAPI"};
    private static f b;
    private DeviceHelper c;
    private HashMap<String, MobProduct> d = new HashMap();

    private f(Context context) {
        this.c = DeviceHelper.getInstance(context);
    }

    public static f a(Context context) {
        if (b == null) {
            b = new f(context);
        }
        return b;
    }

    public String a(ArrayList<MobProduct> arrayList) {
        String str;
        String str2 = this.c.getPackageName() + "/" + this.c.getAppVersionName();
        int size = arrayList.size();
        int i = 0;
        String str3 = "";
        while (i < size) {
            str = i > 0 ? str3 + " " : str3;
            MobProduct mobProduct = (MobProduct) arrayList.get(i);
            i++;
            str3 = str + mobProduct.getProductTag() + "/" + mobProduct.getSdkver();
        }
        str = "Android/" + this.c.getOSVersionInt();
        return str2 + " " + str3 + (str3.length() > 0 ? " " : "") + str + " " + TimeZone.getDefault().getID() + " " + ("Lang/" + Locale.getDefault().toString().replace("-r", "-"));
    }

    public ArrayList<MobProduct> a() {
        try {
            ReflectHelper.importClass("com.mob.commons.*");
            for (String newInstance : a) {
                try {
                    MobProduct mobProduct = (MobProduct) ReflectHelper.newInstance(newInstance, new Object[0]);
                    if (mobProduct != null) {
                        this.d.put(mobProduct.getProductTag(), mobProduct);
                    }
                } catch (Throwable th) {
                }
            }
        } catch (Throwable th2) {
            MobLog.getInstance().w(th2);
        }
        ArrayList<MobProduct> arrayList = new ArrayList();
        for (Entry value : this.d.entrySet()) {
            arrayList.add(value.getValue());
        }
        return arrayList;
    }

    public void a(MobProduct mobProduct) {
        if (mobProduct != null && !this.d.containsKey(mobProduct.getProductTag())) {
            this.d.put(mobProduct.getProductTag(), mobProduct);
        }
    }
}
