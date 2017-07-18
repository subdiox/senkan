package com.kayac.lobi.libnakamap.rec.c;

import android.media.MediaPlayer;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.rec.a.b;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.sdk.LobiCoreAPI.APICallback;
import com.kayac.lobi.sdk.auth.AccountUtil;
import java.io.File;
import java.util.Map;

public class f {

    public interface a {
        void a(int i);
    }

    public static void a(APICallback aPICallback) {
        if (AccountUtil.shouldRefreshToken()) {
            c(aPICallback);
        } else {
            aPICallback.onResult(0, null);
        }
    }

    public static void a(File file, String str, long j, long j2, long j3, APICallback aPICallback) {
        b(new n(aPICallback, j, j2, j3, file, str));
    }

    public static void a(String str, a aVar) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setOnPreparedListener(new z(aVar, mediaPlayer));
            mediaPlayer.setOnErrorListener(new aa(aVar, mediaPlayer));
            mediaPlayer.setDataSource(str);
            mediaPlayer.prepare();
        } catch (Throwable e) {
            b.a(e);
            aVar.a(-1);
            mediaPlayer.release();
        } catch (Throwable e2) {
            b.a(e2);
            aVar.a(-1);
            mediaPlayer.release();
        } catch (Throwable e22) {
            b.a(e22);
            aVar.a(-1);
            mediaPlayer.release();
        } catch (Throwable e222) {
            b.a(e222);
            aVar.a(-1);
            mediaPlayer.release();
        }
    }

    public static void a(String str, APICallback aPICallback) {
        a.b.execute(new r(aPICallback, str));
    }

    public static void a(String str, String str2, APICallback aPICallback) {
        a.b.execute(new ah(aPICallback, str, str2));
    }

    public static void a(String str, String str2, String str3, long j, String str4, String str5, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, String str6, boolean z7, boolean z8, APICallback aPICallback) {
        a.b.execute(new h(aPICallback, str, str2, str3, j, str4, str5, z, z2, z3, z4, z5, z6, str6, z7, z8));
    }

    public static void a(Map<String, String> map, APICallback aPICallback) {
        a.b.execute(new ab(map, aPICallback));
    }

    public static void b(APICallback aPICallback) {
        if (AccountUtil.shouldRefreshToken()) {
            d(aPICallback);
        } else {
            aPICallback.onResult(0, null);
        }
    }

    private static void b(String str, String str2, String str3, long j, String str4, String str5, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, String str6, boolean z7, String str7, long j2, int i, boolean z8, APICallback aPICallback) {
        a.b.execute(new k(str7, str2, str3, j, str4, str5, j2, i, z5, z6, z7, z, z2, z3, z4, str6, aPICallback, z8, str));
    }

    public static final void c(APICallback aPICallback) {
        a.b.execute(new g(aPICallback));
    }

    public static final void d(APICallback aPICallback) {
        UserValue currentUser = AccountDatastore.getCurrentUser();
        Map a = a.a();
        a.putAll(new af(currentUser));
        a.a(1, al.a(), a, new ag(aPICallback));
    }

    public static void e(APICallback aPICallback) {
        a.b.execute(new v(aPICallback));
    }

    public static void f(APICallback aPICallback) {
        a.b.execute(new ad(aPICallback));
    }
}
