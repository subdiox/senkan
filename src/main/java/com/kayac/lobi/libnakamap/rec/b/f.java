package com.kayac.lobi.libnakamap.rec.b;

import android.text.TextUtils;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.KKey.Rec;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastore;
import com.kayac.lobi.libnakamap.rec.a.b;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

public class f {
    private boolean a;
    private boolean b;
    private boolean c;
    private boolean d;

    public void a(boolean z) {
        if (this.a != z) {
            this.a = z;
            e();
        }
    }

    public boolean a() {
        return this.a;
    }

    public void b(boolean z) {
        if (this.b != z) {
            this.b = z;
            e();
        }
    }

    public boolean b() {
        return this.b;
    }

    public void c(boolean z) {
        if (this.c != z) {
            this.c = z;
            e();
        }
    }

    public boolean c() {
        return this.c;
    }

    public void d(boolean z) {
        if (this.d != z) {
            this.d = z;
            e();
        }
    }

    public boolean d() {
        return this.d;
    }

    public void e() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("screen_rec", a());
            jSONObject.put("audio_rec", b());
            jSONObject.put("camera_input", d());
            jSONObject.put("mic_input", c());
            TransactionDatastore.setValue(Rec.LAST_MOVIE_META_DATA, jSONObject.toString());
        } catch (Throwable e) {
            b.a(e);
        }
    }

    public void f() {
        String str = (String) TransactionDatastore.getValue(Rec.LAST_MOVIE_META_DATA);
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                a(jSONObject.optBoolean("screen_rec"));
                b(jSONObject.optBoolean("audio_rec"));
                d(jSONObject.optBoolean("camera_input"));
                c(jSONObject.optBoolean("mic_input"));
            } catch (Throwable e) {
                b.a(e);
            }
        }
    }

    public String toString() {
        return "  Screen Rec: " + a() + "\n   Audio Rec: " + b() + "\n --- \n" + (a() ? "  Camera Input: " + d() : "") + IOUtils.LINE_SEPARATOR_UNIX + (b() ? "     Mic Input: " + c() : "");
    }
}
