package com.kayac.lobi.libnakamap.rec.recorder.a;

import android.opengl.GLES20;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.kayac.lobi.libnakamap.datastore.AccountDDL.KKey.UpdateAt;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.KKey.Rec;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastore;
import com.kayac.lobi.libnakamap.rec.c.f;
import com.kayac.lobi.libnakamap.rec.recorder.j;
import com.rekoo.libs.net.URLCons;
import java.util.HashMap;
import java.util.Map;
import junit.framework.Assert;
import org.json.JSONObject;

public class b {
    private static final String a = b.class.getSimpleName();
    private static final com.kayac.lobi.libnakamap.rec.a.b b = new com.kayac.lobi.libnakamap.rec.a.b(a);
    private final a c;
    private final String d;
    private final e e;
    private final a f;
    private d g;
    private boolean h = false;
    private boolean i;
    private boolean j;

    public enum a {
        UNDEFINED,
        UNITY_4_2_OR_4_3,
        UNITY_4_5_OR_4_6,
        UNITY_5_0,
        UNITY_5_1,
        UNITY_5_2_OR_LATER,
        COCOS2D_X_2,
        COCOS2D_X_3,
        COCOS2D_X_3_7_OR_LATER
    }

    public b(j jVar, boolean z, int i, int i2, a aVar, boolean z2) {
        com.kayac.lobi.libnakamap.rec.a.a.a();
        com.kayac.lobi.libnakamap.rec.a.a.b();
        this.c = aVar;
        this.d = GLES20.glGetString(7937);
        boolean z3 = true;
        if (z2) {
            b.c("\n\n RecSDK is forced to enable \n\n");
        } else {
            z3 = b();
        }
        if (z3) {
            this.e = new e(this.d);
            this.f = new a();
            Map a = a(i, i2);
            JSONObject a2 = a();
            boolean b = b(a2, a);
            if (a2 == null) {
                b.a("no saved config.");
            } else if (!b) {
                b.a("request params are changed. we can't use saved one.");
            } else if (a(a2)) {
                a(jVar, z, i, i2, a2);
                return;
            } else {
                b.a("the saved config is expired. we can't use saved one.");
            }
            if (z2) {
                a(jVar, z, i, i2, ((32 + (i / 2)) - 1) & -32, (((i2 / 2) + 32) - 1) & -32, VERSION.SDK_INT <= 16, "orig_glBindFramebuffer");
                return;
            }
            b.a("connect");
            f.a(a, new c(this, a, jVar, z, i, i2, a2, b));
            return;
        }
        a(jVar);
        this.e = null;
        this.f = null;
    }

    private Map<String, String> a(int i, int i2) {
        Map<String, String> hashMap = new HashMap();
        hashMap.put("api_level", String.valueOf(VERSION.SDK_INT));
        hashMap.put(URLCons.DEVICE, Build.MODEL);
        hashMap.put("sdk", "5.2.39");
        hashMap.put("renderer", this.d);
        hashMap.put("screen_width", String.valueOf(i));
        hashMap.put("screen_height", String.valueOf(i2));
        Object obj = "undefined";
        if (this.c == a.UNITY_4_2_OR_4_3) {
            obj = "unity";
        } else if (this.c == a.UNITY_4_5_OR_4_6) {
            obj = "unity4.5";
        } else if (this.c == a.UNITY_5_0) {
            obj = "unity5.0";
        } else if (this.c == a.UNITY_5_1) {
            obj = "unity5.1";
        } else if (this.c == a.UNITY_5_2_OR_LATER) {
            obj = "unity5.2";
        } else if (this.c == a.COCOS2D_X_2) {
            obj = "cocos2d-x-2";
        } else if (this.c == a.COCOS2D_X_3) {
            obj = "cocos2d-x-3";
        } else if (this.c == a.COCOS2D_X_3_7_OR_LATER) {
            obj = "cocos2d-x-3.7";
        }
        hashMap.put("engine", obj);
        return hashMap;
    }

    protected static JSONObject a() {
        String str = (String) TransactionDatastore.getValue(Rec.REC_CONFIG);
        if (!TextUtils.isEmpty(str)) {
            JSONObject jSONObject;
            try {
                jSONObject = new JSONObject(str);
            } catch (Throwable e) {
                com.kayac.lobi.libnakamap.rec.a.b.a(e);
                jSONObject = null;
            }
            if (jSONObject != null) {
                b.a("load LobiRecSDK Config: " + jSONObject.toString());
                return jSONObject;
            }
        }
        return null;
    }

    private void a(j jVar, boolean z, int i, int i2, int i3, int i4, boolean z2, String str) {
        if (z) {
            try {
                System.loadLibrary("lobirec");
                if (!com.kayac.lobi.libnakamap.rec.a.b) {
                    System.loadLibrary("lobirecmuxer");
                }
                b.a("Recorder is supported");
                if (jVar != null) {
                    jVar.a(i, i2, i3, i4, str);
                }
            } catch (Throwable e) {
                com.kayac.lobi.libnakamap.rec.a.b.a(e);
                b.a("not supported HW");
                a(jVar);
                return;
            }
        }
        a(jVar);
        this.g = new d(z2);
        this.i = true;
        this.j = true;
    }

    private void a(j jVar, boolean z, int i, int i2, JSONObject jSONObject) {
        if (a(jSONObject, "is_supported")) {
            boolean a = a(jSONObject.optJSONObject("writer"), "is_replace_mpeg4_writer");
            JSONObject optJSONObject = jSONObject.optJSONObject("avc_encoder");
            a(jVar, z, i, i2, optJSONObject.optInt("recording_width"), optJSONObject.optInt("recording_height"), a, jSONObject.optJSONObject("open_gl").optString("method_name", ""));
            return;
        }
        a(jVar);
    }

    protected static void a(JSONObject jSONObject, Map<String, String> map) {
        b.a("saving into our database...");
        try {
            for (String str : map.keySet()) {
                String str2 = (String) map.get(str);
                b.a("additional information: " + str + " = " + str2);
                jSONObject.put(str, str2);
            }
            long currentTimeMillis = System.currentTimeMillis();
            b.a("additional information: timestamp = " + currentTimeMillis);
            jSONObject.put("timestamp", currentTimeMillis);
            TransactionDatastore.setValue(Rec.REC_CONFIG, jSONObject.toString());
            b.a("finished saving");
        } catch (Throwable e) {
            com.kayac.lobi.libnakamap.rec.a.b.a(e);
        }
    }

    private boolean a(JSONObject jSONObject) {
        boolean z = false;
        long optLong = jSONObject.optLong("timestamp");
        if (optLong >= 0) {
            boolean a = a(jSONObject, "is_supported");
            long j = a ? UpdateAt.GET_REC_INFO_CACHE : 43200000;
            optLong = System.currentTimeMillis() - optLong;
            if (optLong < j) {
                z = true;
            }
            b.a("check validity period: " + (z ? "VALID" : "INVALID") + " elapsed " + optLong + "ms / " + j + "ms(" + (a ? "supported" : "unsupported") + ")");
        }
        return z;
    }

    private static boolean a(JSONObject jSONObject, String str) {
        return "1".equals(jSONObject.optString(str));
    }

    public static boolean b() {
        if (Build.CPU_ABI != null && Build.CPU_ABI.contains("x86") && Build.CPU_ABI2 != null && Build.CPU_ABI2.contains("arm")) {
            b.a("not supported CPU (x86)");
            return false;
        } else if (!com.kayac.lobi.libnakamap.rec.a.a) {
            b.a("not supported HW");
            return false;
        } else if (VERSION.SDK_INT == 23 && !"REL".equals(VERSION.CODENAME)) {
            b.a("android api level " + VERSION.SDK_INT + " (" + VERSION.CODENAME + ")");
            return false;
        } else if (com.kayac.lobi.libnakamap.rec.d.a.a()) {
            return true;
        } else {
            b.a("not supported CPU (no neon)");
            return false;
        }
    }

    private static boolean b(JSONObject jSONObject, Map<String, String> map) {
        if (jSONObject == null) {
            return false;
        }
        Object obj;
        for (String str : map.keySet()) {
            String str2 = (String) map.get(str);
            String optString = jSONObject.optString(str);
            b.a("comparing request params: " + str + " = " + optString + "(saved) <--> " + str2 + "(current)");
            Assert.assertNotNull(str2);
            if (!str2.equals(optString)) {
                obj = null;
                break;
            }
        }
        int i = 1;
        return obj != null;
    }

    public void a(j jVar) {
        this.i = false;
        this.j = false;
        if (jVar != null) {
            jVar.a();
        }
    }

    public void a(boolean z) {
        this.h = z;
    }

    public a c() {
        return this.c;
    }

    public e d() {
        return this.e;
    }

    public a e() {
        return this.f;
    }

    public d f() {
        return this.g;
    }

    public boolean g() {
        return this.i && d() != null;
    }

    public boolean h() {
        return this.j;
    }

    public com.kayac.lobi.libnakamap.rec.b.f i() {
        boolean z = true;
        com.kayac.lobi.libnakamap.rec.b.f fVar = new com.kayac.lobi.libnakamap.rec.b.f();
        fVar.a(g());
        fVar.b(h());
        boolean z2 = g() && com.kayac.lobi.libnakamap.rec.recorder.j.a.f;
        fVar.c(z2);
        if (!(h() && e().c())) {
            z = false;
        }
        fVar.c(z);
        com.kayac.lobi.libnakamap.rec.a.b.a("LobiRecSDK", "------ LobiRecSDK for Android ------\nStart recording: \n" + fVar + "\n------------------------------------");
        return fVar;
    }

    public boolean j() {
        return this.h;
    }
}
