package com.yaya.sdk.utils;

import android.content.Context;
import android.os.Build.VERSION;
import com.yaya.sdk.bean.req.a;
import com.yaya.sdk.bean.req.b;
import com.yaya.sdk.bean.req.c;
import com.yaya.sdk.bean.req.d;
import com.yaya.sdk.bean.req.g;
import com.yaya.sdk.bean.req.h;
import com.yaya.sdk.constants.Constants;
import org.json.JSONException;
import org.json.JSONObject;

public class f {
    private static String a = JSONObject.class.getSimpleName();

    public static String a(b bVar) {
        String str = "";
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(Constants.AUTHREQ_RESQUEST_TYPE, bVar.c());
            jSONObject.put(Constants.AUTHREQ_UUID, bVar.d());
            jSONObject.put(Constants.AUTHREQ_OSTYPE, bVar.e());
            jSONObject.put(Constants.AUTHREQ_MOBILE_TYPE, bVar.g());
            jSONObject.put(Constants.AUTHREQ_MAC, bVar.h());
            jSONObject.put(Constants.AUTHREQ_APP_ID, bVar.i());
            jSONObject.put(Constants.AUTHREQ_APP_VERSION, bVar.j());
            jSONObject.put(Constants.AUTHREQ_CHANNERL_ID, bVar.k());
            jSONObject.put(Constants.AUTHREQ_SOFT_OWNER, bVar.l());
            jSONObject.put(Constants.AUTHREQ_SDK_VERSION, bVar.b());
            jSONObject.put(Constants.AUTHREQ_DOUBLECARD, bVar.m());
            jSONObject.put(Constants.AUTHREQ_PHONENUM, bVar.n());
            jSONObject.put(Constants.AUTHREQ_SECOND_PHONUM, bVar.o());
            jSONObject.put(Constants.AUTHREQ_IMEI, bVar.p());
            jSONObject.put(Constants.AUTHREQ_IMSI, bVar.q());
            jSONObject.put(Constants.AUTHREQ_ISP, bVar.r());
            jSONObject.put(Constants.AUTHREQ_ANDROID_ID, bVar.s());
            jSONObject.put(Constants.AUTHREQ_IDFA, bVar.t());
            jSONObject.put(Constants.AUTHREQ_IP, bVar.v());
            jSONObject.put(Constants.AUTHREQ_NETWORKTYPE, bVar.u());
            jSONObject.put(Constants.AUTHREQ_OSVERSION, bVar.a());
            str = jSONObject.toString();
        } catch (JSONException e) {
            g.a(a, e.getMessage());
        }
        return str;
    }

    public static String a(h hVar) {
        String str = "";
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("requestId", hVar.a());
            jSONObject.put(Constants.LOADDATAREQ_USERDATA, "");
            jSONObject.put(Constants.ERRREPORTREQ_PARMS, hVar.c());
            str = jSONObject.toString();
        } catch (Exception e) {
            g.a(a, e.getMessage());
        }
        return str;
    }

    public static String a(a aVar) {
        String str = "";
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(Constants.ACTIONREQ_ACTIONTYPE, aVar.f());
            jSONObject.put(Constants.ACTIONREQ_APPID, aVar.d());
            jSONObject.put(Constants.ACTIONREQ_OSTYPE, aVar.c());
            jSONObject.put(Constants.ACTIONREQ_SDKVERSION, aVar.a());
            jSONObject.put(Constants.ACTIONREQ_UUID, aVar.b());
            jSONObject.put(Constants.ACTIONREQ_VERSION, aVar.e());
            str = jSONObject.toString();
        } catch (Exception e) {
            g.a(a, e.getMessage());
        }
        return str;
    }

    public static String a(Context context) {
        b bVar = new b();
        bVar.p(h.h(context));
        bVar.h(Constants.APP_ID);
        bVar.i(h.a(context));
        bVar.j("");
        bVar.b(Integer.valueOf(0));
        bVar.q("");
        bVar.n(h.g(context));
        bVar.o(h.f(context));
        bVar.r(h.j(context));
        bVar.c(Integer.valueOf(h.i(context)));
        bVar.g(h.e(context));
        bVar.e(h.e());
        bVar.f(h.d());
        bVar.d(Integer.valueOf(h.k(context)));
        bVar.d(h.a());
        bVar.l("");
        bVar.a(new StringBuilder(String.valueOf(VERSION.SDK_INT)).toString());
        int b = j.b(context, Constants.REGISTER_OR_LOAD, 1);
        bVar.a(Integer.valueOf(b));
        if (b == 1) {
            j.a(context, Constants.REGISTER_OR_LOAD, 2);
        }
        bVar.b("");
        bVar.m("");
        bVar.k("");
        bVar.c(k.a(context));
        return a(bVar);
    }

    public static String a(d dVar) {
        String str = "";
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(Constants.ERRREPORTREQ_ERRMSG, dVar.b());
            jSONObject.put("requestId", dVar.a());
            jSONObject.put(Constants.ERRREPORTREQ_PARMS, dVar.c());
            str = jSONObject.toString();
        } catch (Exception e) {
            g.a(a, e.getMessage());
        }
        return str;
    }

    public static String a(c cVar) {
        String str = "";
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(Constants.CHECKREQ_APPID, cVar.c());
            jSONObject.put(Constants.CHECKREQ_OSTYPE, cVar.d());
            jSONObject.put(Constants.CHECKREQ_SDKVERSION, cVar.a());
            jSONObject.put(Constants.CHECKREQ_UUID, cVar.b());
            jSONObject.put(Constants.CHECKREQ_VERSION, cVar.e());
            str = jSONObject.toString();
        } catch (Exception e) {
            g.a(a, e.getMessage());
        }
        return str;
    }

    public static com.yaya.sdk.bean.resp.d a(String str) {
        com.yaya.sdk.bean.resp.d dVar = new com.yaya.sdk.bean.resp.d();
        try {
            JSONObject jSONObject = new JSONObject(str);
            String string = jSONObject.getString("requestId");
            Integer valueOf = Integer.valueOf(jSONObject.getInt(Constants.CHECKRESP_HELLOID));
            dVar.a(string);
            dVar.a(valueOf);
            return dVar;
        } catch (JSONException e) {
            return null;
        }
    }

    public static com.yaya.sdk.bean.resp.f b(String str) {
        com.yaya.sdk.bean.resp.f fVar = new com.yaya.sdk.bean.resp.f();
        g gVar = null;
        try {
            JSONObject jSONObject = new JSONObject(str);
            String string = jSONObject.getString("requestId");
            if (!jSONObject.isNull("info")) {
                gVar = new g();
                jSONObject = jSONObject.getJSONObject("info");
                if (!jSONObject.isNull("id")) {
                    gVar.a(Integer.valueOf(jSONObject.getInt("id")));
                }
                if (!jSONObject.isNull(Constants.HELLOINFO_MD5VAL)) {
                    gVar.a(jSONObject.getString(Constants.HELLOINFO_MD5VAL));
                }
                if (!jSONObject.isNull("name")) {
                    gVar.b(jSONObject.getString("name"));
                }
                if (!jSONObject.isNull(Constants.HELLOINFO_OSTYPE)) {
                    gVar.b(Integer.valueOf(jSONObject.getInt(Constants.HELLOINFO_OSTYPE)));
                }
                if (!jSONObject.isNull(Constants.HELLOINFO_HELLO)) {
                    gVar.c(jSONObject.getString(Constants.HELLOINFO_HELLO));
                }
            }
            fVar.a(gVar);
            fVar.a(string);
        } catch (JSONException e) {
        }
        return fVar;
    }

    public static com.yaya.sdk.bean.resp.d c(String str) {
        com.yaya.sdk.bean.resp.d dVar = new com.yaya.sdk.bean.resp.d();
        try {
            JSONObject jSONObject = new JSONObject(str);
            String string = jSONObject.getString("requestId");
            if (!jSONObject.isNull(Constants.CHECKRESP_HELLOID)) {
                dVar.a(Integer.valueOf(jSONObject.getInt(Constants.CHECKRESP_HELLOID)));
            }
            dVar.a(string);
        } catch (JSONException e) {
        }
        return dVar;
    }

    public static String a(com.yaya.sdk.bean.req.f fVar) {
        String str = "";
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(Constants.GETNEXTSCPREQ_LASTID, fVar.a());
            jSONObject.put(Constants.GETNEXTSCPREQ_NEXTID, fVar.b());
            jSONObject.put(Constants.GETNEXTSCPREQ_REQUESTID, fVar.c());
            str = jSONObject.toString();
        } catch (Exception e) {
            g.a(a, e.getMessage());
        }
        return str;
    }
}
