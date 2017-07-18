package com.yunva.im.sdk.lib.config;

import android.os.Environment;
import com.yunva.im.sdk.lib.YvLoginInit;
import com.yunva.im.sdk.lib.utils.c;

public class d extends b {
    public String a() {
        return "http://plugin.yunva.com/yunva?m=UPDATE";
    }

    public String b() {
        if (Environment.getExternalStorageState().equals("mounted")) {
            return new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().toString())).append("/im_sdk_test").toString();
        }
        return new StringBuilder(String.valueOf(YvLoginInit.context.getFilesDir().getAbsolutePath())).append("/im_sdk_test").toString();
    }

    public String c() {
        return b() + "/jar";
    }

    public String d() {
        return c.b;
    }

    public int e() {
        return c.a;
    }

    public String f() {
        return b() + "/sig";
    }

    public String g() {
        return b() + "/updateinfo";
    }

    public String h() {
        return "yunva_dynamic_live_sdk_test_version_info.txt";
    }

    public String i() {
        return "http://plugin.yunva.com/";
    }

    public String j() {
        return b() + "/pic";
    }

    public String k() {
        return b() + "/voice";
    }

    public String l() {
        return b() + "/bdvoice";
    }
}
