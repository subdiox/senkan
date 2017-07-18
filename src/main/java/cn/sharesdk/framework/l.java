package cn.sharesdk.framework;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Handler;
import cn.sharesdk.framework.b.a;
import cn.sharesdk.framework.b.b.c;
import cn.sharesdk.framework.utils.d;
import com.mob.commons.eventrecoder.EventRecorder;
import com.mob.tools.utils.R;
import dalvik.system.DexFile;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;

public class l {
    private static boolean a = true;
    private Context b;
    private String c;

    public l(Context context, String str) {
        this.b = context;
        this.c = str;
    }

    private ArrayList<Platform> a(PackageInfo packageInfo) {
        ArrayList<Platform> arrayList = new ArrayList();
        try {
            DexFile dexFile = new DexFile(packageInfo.applicationInfo.sourceDir);
            Enumeration entries = dexFile.entries();
            dexFile.close();
        } catch (Throwable th) {
            d.a().w(th);
            return null;
        }
        while (entries != null && entries.hasMoreElements()) {
            String str = (String) entries.nextElement();
            if (str.startsWith("cn.sharesdk") && !str.contains("$")) {
                try {
                    Class cls = Class.forName(str);
                    if (!(cls == null || !Platform.class.isAssignableFrom(cls) || CustomPlatform.class.isAssignableFrom(cls))) {
                        Constructor constructor = cls.getConstructor(new Class[]{Context.class});
                        constructor.setAccessible(true);
                        arrayList.add((Platform) constructor.newInstance(new Object[]{this.b}));
                    }
                } catch (Throwable th2) {
                    d.a().w(th2);
                }
            }
        }
        return arrayList;
    }

    private PackageInfo e() {
        try {
            PackageManager packageManager = this.b.getPackageManager();
            String packageName = this.b.getPackageName();
            for (PackageInfo packageInfo : packageManager.getInstalledPackages(8192)) {
                if (packageName.equals(packageInfo.packageName)) {
                    return packageInfo;
                }
            }
            return null;
        } catch (Throwable th) {
            d.a().w(th);
            return null;
        }
    }

    private ArrayList<Platform> f() {
        String[] strArr = new String[]{"cn.sharesdk.douban.Douban", "cn.sharesdk.evernote.Evernote", "cn.sharesdk.facebook.Facebook", "cn.sharesdk.renren.Renren", "cn.sharesdk.sina.weibo.SinaWeibo", "cn.sharesdk.kaixin.KaiXin", "cn.sharesdk.linkedin.LinkedIn", "cn.sharesdk.system.email.Email", "cn.sharesdk.system.text.ShortMessage", "cn.sharesdk.tencent.qq.QQ", "cn.sharesdk.tencent.qzone.QZone", "cn.sharesdk.tencent.weibo.TencentWeibo", "cn.sharesdk.twitter.Twitter", "cn.sharesdk.wechat.friends.Wechat", "cn.sharesdk.wechat.moments.WechatMoments", "cn.sharesdk.wechat.favorite.WechatFavorite", "cn.sharesdk.youdao.YouDao", "cn.sharesdk.google.GooglePlus", "cn.sharesdk.foursquare.FourSquare", "cn.sharesdk.pinterest.Pinterest", "cn.sharesdk.flickr.Flickr", "cn.sharesdk.tumblr.Tumblr", "cn.sharesdk.dropbox.Dropbox", "cn.sharesdk.vkontakte.VKontakte", "cn.sharesdk.instagram.Instagram", "cn.sharesdk.yixin.friends.Yixin", "cn.sharesdk.yixin.moments.YixinMoments", "cn.sharesdk.mingdao.Mingdao", "cn.sharesdk.line.Line", "cn.sharesdk.kakao.story.KakaoStory", "cn.sharesdk.kakao.talk.KakaoTalk", "cn.sharesdk.whatsapp.WhatsApp", "cn.sharesdk.pocket.Pocket", "cn.sharesdk.instapaper.Instapaper", "cn.sharesdk.facebookmessenger.FacebookMessenger", "cn.sharesdk.alipay.share.Alipay"};
        ArrayList<Platform> arrayList = new ArrayList();
        for (String cls : strArr) {
            try {
                Constructor constructor = Class.forName(cls).getConstructor(new Class[]{Context.class});
                constructor.setAccessible(true);
                arrayList.add((Platform) constructor.newInstance(new Object[]{this.b}));
            } catch (Throwable th) {
                d.a().d(th);
            }
        }
        return arrayList;
    }

    public String a(int i, String str, HashMap<Integer, HashMap<String, Object>> hashMap) {
        HashMap hashMap2 = (HashMap) hashMap.get(Integer.valueOf(i));
        if (hashMap2 == null) {
            return null;
        }
        Object obj = hashMap2.get(str);
        return obj == null ? null : String.valueOf(obj);
    }

    public String a(Bitmap bitmap) {
        return a.a(this.b, this.c).a(bitmap);
    }

    public String a(String str) {
        return a.a(this.b, this.c).a(str);
    }

    public String a(String str, boolean z, int i, String str2) {
        return a.a(this.b, this.c).a(str, i, z, str2);
    }

    public ArrayList<Platform> a() {
        ArrayList f;
        if (a) {
            f = f();
        } else {
            PackageInfo e = e();
            if (e == null) {
                return null;
            }
            f = a(e);
        }
        a(f);
        return f;
    }

    public void a(int i, int i2, HashMap<Integer, HashMap<String, Object>> hashMap) {
        hashMap.put(Integer.valueOf(i2), (HashMap) hashMap.get(Integer.valueOf(i)));
    }

    public void a(int i, Platform platform) {
        c dVar = new cn.sharesdk.framework.b.b.d();
        switch (i) {
            case 1:
                dVar.a = "SHARESDK_ENTER_SHAREMENU";
                break;
            case 2:
                dVar.a = "SHARESDK_CANCEL_SHAREMENU";
                break;
            case 3:
                dVar.a = "SHARESDK_EDIT_SHARE";
                break;
            case 4:
                dVar.a = "SHARESDK_FAILED_SHARE";
                break;
            case 5:
                dVar.a = "SHARESDK_CANCEL_SHARE";
                break;
        }
        if (platform != null) {
            dVar.b = platform.getPlatformId();
        }
        cn.sharesdk.framework.b.d a = cn.sharesdk.framework.b.d.a(this.b, this.c);
        if (a != null) {
            a.a(dVar);
        }
    }

    public void a(Handler handler, boolean z, int i) {
        cn.sharesdk.framework.b.d a = cn.sharesdk.framework.b.d.a(this.b, this.c);
        if (a != null) {
            a.a(handler);
            a.a(z);
            a.a(i);
            a.startThread();
        }
    }

    public void a(n nVar) {
    }

    public void a(String str, int i) {
        cn.sharesdk.framework.b.d a = cn.sharesdk.framework.b.d.a(this.b, this.c);
        if (a != null) {
            c aVar = new cn.sharesdk.framework.b.b.a();
            aVar.b = str;
            aVar.a = i;
            a.a(aVar);
        }
    }

    public void a(ArrayList<Platform> arrayList) {
        if (arrayList != null) {
            Collections.sort(arrayList, new m(this));
        }
    }

    public boolean a(HashMap<String, Object> hashMap, HashMap<Integer, HashMap<String, Object>> hashMap2) {
        if (hashMap == null || hashMap.size() <= 0) {
            return false;
        }
        ArrayList arrayList = (ArrayList) hashMap.get("fakelist");
        if (arrayList == null) {
            return false;
        }
        EventRecorder.addBegin("ShareSDK", "parseDevInfo");
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            HashMap hashMap3 = (HashMap) it.next();
            if (hashMap3 != null) {
                int parseInt;
                try {
                    parseInt = R.parseInt(String.valueOf(hashMap3.get("snsplat")));
                } catch (Throwable th) {
                    d.a().w(th);
                    parseInt = -1;
                }
                if (parseInt != -1) {
                    hashMap2.put(Integer.valueOf(parseInt), hashMap3);
                }
            }
        }
        EventRecorder.addEnd("ShareSDK", "parseDevInfo");
        return true;
    }

    public void b() {
        cn.sharesdk.framework.b.d a = cn.sharesdk.framework.b.d.a(this.b, this.c);
        if (a != null) {
            a.stopThread();
        }
    }

    public String c() {
        return "2.7.5";
    }

    public int d() {
        return 63;
    }
}
