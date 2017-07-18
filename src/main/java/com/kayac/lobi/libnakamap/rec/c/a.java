package com.kayac.lobi.libnakamap.rec.c;

import android.os.Build.VERSION;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import com.google.android.gcm.GCMConstants;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.sdk.LobiCore;
import com.kayac.lobi.sdk.LobiCoreAPI;
import com.kayac.lobi.sdk.LobiCoreAPI.APICallback;
import com.tencent.bugly.Bugly;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONArray;
import org.json.JSONObject;

public final class a {
    static final String[] a = new String[]{"GET", "POST"};
    static ExecutorService b = Executors.newFixedThreadPool(4);

    private interface b {
        void a(int i);

        void a(int i, JSONObject jSONObject);

        void a(JSONObject jSONObject);

        void b(JSONObject jSONObject);
    }

    static abstract class a implements b {
        a() {
        }

        public void a(int i) {
        }

        public void a(JSONObject jSONObject) {
        }

        public void b(JSONObject jSONObject) {
            UserValue currentUser = AccountDatastore.getCurrentUser();
            String newAccountBaseName = (currentUser == null || TextUtils.isEmpty(currentUser.getName())) ? LobiCore.sharedInstance().getNewAccountBaseName() : currentUser.getName();
            LobiCoreAPI.signupWithBaseName(newAccountBaseName, new e(this));
        }
    }

    static {
        if (VERSION.SDK_INT < 8) {
            System.setProperty("http.keepAlive", Bugly.SDK_IS_DEV);
        }
    }

    static Map<String, String> a() {
        return new b();
    }

    static void a(int i, String str, Map<String, String> map, b bVar) {
        a(i, str, map, null, bVar);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void a(int r16, java.lang.String r17, java.util.Map<java.lang.String, java.lang.String> r18, java.util.Map<java.lang.String, java.io.File> r19, long r20, long r22, long r24, com.kayac.lobi.libnakamap.rec.c.a.b r26) {
        /*
        r3 = 0;
        r14 = 0;
        r4 = 0;
        r13 = 0;
        r2 = new java.net.URL;	 Catch:{ MalformedURLException -> 0x032b, IOException -> 0x0308, JSONException -> 0x02df, all -> 0x02b0 }
        r0 = r17;
        r2.<init>(r0);	 Catch:{ MalformedURLException -> 0x032b, IOException -> 0x0308, JSONException -> 0x02df, all -> 0x02b0 }
        r2 = r2.openConnection();	 Catch:{ MalformedURLException -> 0x032b, IOException -> 0x0308, JSONException -> 0x02df, all -> 0x02b0 }
        r2 = (java.net.HttpURLConnection) r2;	 Catch:{ MalformedURLException -> 0x032b, IOException -> 0x0308, JSONException -> 0x02df, all -> 0x02b0 }
        r3 = a;	 Catch:{ MalformedURLException -> 0x0331, IOException -> 0x030d, JSONException -> 0x02e4, all -> 0x02b4 }
        r3 = r3[r16];	 Catch:{ MalformedURLException -> 0x0331, IOException -> 0x030d, JSONException -> 0x02e4, all -> 0x02b4 }
        r2.setRequestMethod(r3);	 Catch:{ MalformedURLException -> 0x0331, IOException -> 0x030d, JSONException -> 0x02e4, all -> 0x02b4 }
        r3 = "Accept-Encoding";
        r5 = "gzip";
        r2.setRequestProperty(r3, r5);	 Catch:{ MalformedURLException -> 0x0331, IOException -> 0x030d, JSONException -> 0x02e4, all -> 0x02b4 }
        r3 = 1;
        r2.setDoInput(r3);	 Catch:{ MalformedURLException -> 0x0331, IOException -> 0x030d, JSONException -> 0x02e4, all -> 0x02b4 }
        r3 = 1;
        r0 = r16;
        if (r0 != r3) goto L_0x0346;
    L_0x0028:
        r3 = 1;
        r2.setDoOutput(r3);	 Catch:{ MalformedURLException -> 0x0331, IOException -> 0x030d, JSONException -> 0x02e4, all -> 0x02b4 }
        if (r19 != 0) goto L_0x00bc;
    L_0x002e:
        r5 = new java.io.BufferedWriter;	 Catch:{ MalformedURLException -> 0x0331, IOException -> 0x030d, JSONException -> 0x02e4, all -> 0x02b4 }
        r3 = new java.io.OutputStreamWriter;	 Catch:{ MalformedURLException -> 0x0331, IOException -> 0x030d, JSONException -> 0x02e4, all -> 0x02b4 }
        r6 = new java.io.BufferedOutputStream;	 Catch:{ MalformedURLException -> 0x0331, IOException -> 0x030d, JSONException -> 0x02e4, all -> 0x02b4 }
        r7 = r2.getOutputStream();	 Catch:{ MalformedURLException -> 0x0331, IOException -> 0x030d, JSONException -> 0x02e4, all -> 0x02b4 }
        r6.<init>(r7);	 Catch:{ MalformedURLException -> 0x0331, IOException -> 0x030d, JSONException -> 0x02e4, all -> 0x02b4 }
        r7 = "UTF-8";
        r3.<init>(r6, r7);	 Catch:{ MalformedURLException -> 0x0331, IOException -> 0x030d, JSONException -> 0x02e4, all -> 0x02b4 }
        r5.<init>(r3);	 Catch:{ MalformedURLException -> 0x0331, IOException -> 0x030d, JSONException -> 0x02e4, all -> 0x02b4 }
        r3 = com.kayac.lobi.libnakamap.rec.c.am.a(r18);	 Catch:{ MalformedURLException -> 0x0338, IOException -> 0x0315, JSONException -> 0x02ec, all -> 0x02bb }
        r5.write(r3);	 Catch:{ MalformedURLException -> 0x0338, IOException -> 0x0315, JSONException -> 0x02ec, all -> 0x02bb }
        r5.close();	 Catch:{ MalformedURLException -> 0x0338, IOException -> 0x0315, JSONException -> 0x02ec, all -> 0x02bb }
        r14 = 0;
        r5 = r4;
        r6 = r14;
    L_0x0050:
        r2.connect();	 Catch:{ MalformedURLException -> 0x010e, IOException -> 0x0159, JSONException -> 0x02fb, all -> 0x02c8 }
        r7 = r2.getResponseCode();	 Catch:{ MalformedURLException -> 0x010e, IOException -> 0x0159, JSONException -> 0x02fb, all -> 0x02c8 }
        r3 = 0;
        r4 = r2.getContentEncoding();	 Catch:{ MalformedURLException -> 0x010e, IOException -> 0x0159, JSONException -> 0x02fb, all -> 0x02c8 }
        if (r4 == 0) goto L_0x0064;
    L_0x005e:
        r3 = "gzip";
        r3 = r4.contains(r3);	 Catch:{ MalformedURLException -> 0x010e, IOException -> 0x0159, JSONException -> 0x02fb, all -> 0x02c8 }
    L_0x0064:
        if (r3 == 0) goto L_0x0134;
    L_0x0066:
        r4 = new java.io.BufferedReader;	 Catch:{ IOException -> 0x00f6, MalformedURLException -> 0x010e, JSONException -> 0x02fb, all -> 0x02c8 }
        r3 = new java.io.InputStreamReader;	 Catch:{ IOException -> 0x00f6, MalformedURLException -> 0x010e, JSONException -> 0x02fb, all -> 0x02c8 }
        r8 = new java.util.zip.GZIPInputStream;	 Catch:{ IOException -> 0x00f6, MalformedURLException -> 0x010e, JSONException -> 0x02fb, all -> 0x02c8 }
        r9 = r2.getInputStream();	 Catch:{ IOException -> 0x00f6, MalformedURLException -> 0x010e, JSONException -> 0x02fb, all -> 0x02c8 }
        r8.<init>(r9);	 Catch:{ IOException -> 0x00f6, MalformedURLException -> 0x010e, JSONException -> 0x02fb, all -> 0x02c8 }
        r9 = "UTF-8";
        r3.<init>(r8, r9);	 Catch:{ IOException -> 0x00f6, MalformedURLException -> 0x010e, JSONException -> 0x02fb, all -> 0x02c8 }
        r4.<init>(r3);	 Catch:{ IOException -> 0x00f6, MalformedURLException -> 0x010e, JSONException -> 0x02fb, all -> 0x02c8 }
    L_0x007b:
        r3 = new java.lang.StringBuffer;	 Catch:{ MalformedURLException -> 0x0183, IOException -> 0x0324, JSONException -> 0x0301, all -> 0x02ce }
        r3.<init>();	 Catch:{ MalformedURLException -> 0x0183, IOException -> 0x0324, JSONException -> 0x0301, all -> 0x02ce }
    L_0x0080:
        r8 = r4.readLine();	 Catch:{ MalformedURLException -> 0x0183, IOException -> 0x0324, JSONException -> 0x0301, all -> 0x02ce }
        if (r8 != 0) goto L_0x017e;
    L_0x0086:
        r3 = r3.toString();	 Catch:{ MalformedURLException -> 0x0183, IOException -> 0x0324, JSONException -> 0x0301, all -> 0x02ce }
        r8 = new org.json.JSONObject;	 Catch:{ MalformedURLException -> 0x0183, IOException -> 0x0324, JSONException -> 0x0301, all -> 0x02ce }
        r8.<init>(r3);	 Catch:{ MalformedURLException -> 0x0183, IOException -> 0x0324, JSONException -> 0x0301, all -> 0x02ce }
        r4.close();	 Catch:{ MalformedURLException -> 0x0183, IOException -> 0x0324, JSONException -> 0x0301, all -> 0x02ce }
        r4 = 0;
        r0 = r26;
        r0.a(r7);	 Catch:{ MalformedURLException -> 0x01b1, IOException -> 0x01c8, JSONException -> 0x020b, all -> 0x0242 }
        r3 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r7 != r3) goto L_0x018b;
    L_0x009c:
        r0 = r26;
        r0.a(r8);	 Catch:{ MalformedURLException -> 0x01b1, IOException -> 0x01c8, JSONException -> 0x020b, all -> 0x0242 }
        r3 = 0;
        r0 = r26;
        r0.a(r3, r8);	 Catch:{ MalformedURLException -> 0x01b1, IOException -> 0x01c8, JSONException -> 0x020b, all -> 0x0242 }
    L_0x00a7:
        if (r4 == 0) goto L_0x00ac;
    L_0x00a9:
        r4.close();	 Catch:{ IOException -> 0x029e }
    L_0x00ac:
        if (r5 == 0) goto L_0x00b1;
    L_0x00ae:
        r5.close();	 Catch:{ IOException -> 0x02a4 }
    L_0x00b1:
        if (r6 == 0) goto L_0x00b6;
    L_0x00b3:
        r6.close();	 Catch:{ IOException -> 0x02aa }
    L_0x00b6:
        if (r2 == 0) goto L_0x00bb;
    L_0x00b8:
        r2.disconnect();
    L_0x00bb:
        return;
    L_0x00bc:
        r12 = b();	 Catch:{ MalformedURLException -> 0x0331, IOException -> 0x030d, JSONException -> 0x02e4, all -> 0x02b4 }
        r3 = "Content-Type";
        r5 = new java.lang.StringBuilder;	 Catch:{ MalformedURLException -> 0x0331, IOException -> 0x030d, JSONException -> 0x02e4, all -> 0x02b4 }
        r5.<init>();	 Catch:{ MalformedURLException -> 0x0331, IOException -> 0x030d, JSONException -> 0x02e4, all -> 0x02b4 }
        r6 = "multipart/form-data;boundary=";
        r5 = r5.append(r6);	 Catch:{ MalformedURLException -> 0x0331, IOException -> 0x030d, JSONException -> 0x02e4, all -> 0x02b4 }
        r5 = r5.append(r12);	 Catch:{ MalformedURLException -> 0x0331, IOException -> 0x030d, JSONException -> 0x02e4, all -> 0x02b4 }
        r5 = r5.toString();	 Catch:{ MalformedURLException -> 0x0331, IOException -> 0x030d, JSONException -> 0x02e4, all -> 0x02b4 }
        r2.setRequestProperty(r3, r5);	 Catch:{ MalformedURLException -> 0x0331, IOException -> 0x030d, JSONException -> 0x02e4, all -> 0x02b4 }
        r3 = new java.io.BufferedOutputStream;	 Catch:{ MalformedURLException -> 0x0331, IOException -> 0x030d, JSONException -> 0x02e4, all -> 0x02b4 }
        r5 = r2.getOutputStream();	 Catch:{ MalformedURLException -> 0x0331, IOException -> 0x030d, JSONException -> 0x02e4, all -> 0x02b4 }
        r3.<init>(r5);	 Catch:{ MalformedURLException -> 0x0331, IOException -> 0x030d, JSONException -> 0x02e4, all -> 0x02b4 }
        r4 = r18;
        r5 = r19;
        r6 = r20;
        r8 = r22;
        r10 = r24;
        a(r3, r4, r5, r6, r8, r10, r12);	 Catch:{ MalformedURLException -> 0x033e, IOException -> 0x031d, JSONException -> 0x02f4, all -> 0x02c2 }
        r3.close();	 Catch:{ MalformedURLException -> 0x033e, IOException -> 0x031d, JSONException -> 0x02f4, all -> 0x02c2 }
        r4 = 0;
        r5 = r4;
        r6 = r14;
        goto L_0x0050;
    L_0x00f6:
        r3 = move-exception;
        r4 = new java.io.BufferedReader;	 Catch:{ MalformedURLException -> 0x010e, IOException -> 0x0159, JSONException -> 0x02fb, all -> 0x02c8 }
        r3 = new java.io.InputStreamReader;	 Catch:{ MalformedURLException -> 0x010e, IOException -> 0x0159, JSONException -> 0x02fb, all -> 0x02c8 }
        r8 = new java.util.zip.GZIPInputStream;	 Catch:{ MalformedURLException -> 0x010e, IOException -> 0x0159, JSONException -> 0x02fb, all -> 0x02c8 }
        r9 = r2.getErrorStream();	 Catch:{ MalformedURLException -> 0x010e, IOException -> 0x0159, JSONException -> 0x02fb, all -> 0x02c8 }
        r8.<init>(r9);	 Catch:{ MalformedURLException -> 0x010e, IOException -> 0x0159, JSONException -> 0x02fb, all -> 0x02c8 }
        r9 = "UTF-8";
        r3.<init>(r8, r9);	 Catch:{ MalformedURLException -> 0x010e, IOException -> 0x0159, JSONException -> 0x02fb, all -> 0x02c8 }
        r4.<init>(r3);	 Catch:{ MalformedURLException -> 0x010e, IOException -> 0x0159, JSONException -> 0x02fb, all -> 0x02c8 }
        goto L_0x007b;
    L_0x010e:
        r3 = move-exception;
        r4 = r5;
        r5 = r6;
        r6 = r2;
        r2 = r3;
        r3 = r13;
    L_0x0114:
        com.kayac.lobi.libnakamap.rec.a.b.a(r2);	 Catch:{ all -> 0x02d5 }
        r2 = 100;
        r7 = 0;
        r0 = r26;
        r0.a(r2, r7);	 Catch:{ all -> 0x02d5 }
        if (r3 == 0) goto L_0x0124;
    L_0x0121:
        r3.close();	 Catch:{ IOException -> 0x026b }
    L_0x0124:
        if (r4 == 0) goto L_0x0129;
    L_0x0126:
        r4.close();	 Catch:{ IOException -> 0x0271 }
    L_0x0129:
        if (r5 == 0) goto L_0x012e;
    L_0x012b:
        r5.close();	 Catch:{ IOException -> 0x0277 }
    L_0x012e:
        if (r6 == 0) goto L_0x00bb;
    L_0x0130:
        r6.disconnect();
        goto L_0x00bb;
    L_0x0134:
        r4 = new java.io.BufferedReader;	 Catch:{ IOException -> 0x0146, MalformedURLException -> 0x010e, JSONException -> 0x02fb, all -> 0x02c8 }
        r3 = new java.io.InputStreamReader;	 Catch:{ IOException -> 0x0146, MalformedURLException -> 0x010e, JSONException -> 0x02fb, all -> 0x02c8 }
        r8 = r2.getInputStream();	 Catch:{ IOException -> 0x0146, MalformedURLException -> 0x010e, JSONException -> 0x02fb, all -> 0x02c8 }
        r9 = "UTF-8";
        r3.<init>(r8, r9);	 Catch:{ IOException -> 0x0146, MalformedURLException -> 0x010e, JSONException -> 0x02fb, all -> 0x02c8 }
        r4.<init>(r3);	 Catch:{ IOException -> 0x0146, MalformedURLException -> 0x010e, JSONException -> 0x02fb, all -> 0x02c8 }
        goto L_0x007b;
    L_0x0146:
        r3 = move-exception;
        r4 = new java.io.BufferedReader;	 Catch:{ MalformedURLException -> 0x010e, IOException -> 0x0159, JSONException -> 0x02fb, all -> 0x02c8 }
        r3 = new java.io.InputStreamReader;	 Catch:{ MalformedURLException -> 0x010e, IOException -> 0x0159, JSONException -> 0x02fb, all -> 0x02c8 }
        r8 = r2.getErrorStream();	 Catch:{ MalformedURLException -> 0x010e, IOException -> 0x0159, JSONException -> 0x02fb, all -> 0x02c8 }
        r9 = "UTF-8";
        r3.<init>(r8, r9);	 Catch:{ MalformedURLException -> 0x010e, IOException -> 0x0159, JSONException -> 0x02fb, all -> 0x02c8 }
        r4.<init>(r3);	 Catch:{ MalformedURLException -> 0x010e, IOException -> 0x0159, JSONException -> 0x02fb, all -> 0x02c8 }
        goto L_0x007b;
    L_0x0159:
        r3 = move-exception;
        r15 = r3;
        r3 = r2;
        r2 = r15;
    L_0x015d:
        com.kayac.lobi.libnakamap.rec.a.b.a(r2);	 Catch:{ all -> 0x02dc }
        r2 = 100;
        r4 = 0;
        r0 = r26;
        r0.a(r2, r4);	 Catch:{ all -> 0x02dc }
        if (r13 == 0) goto L_0x016d;
    L_0x016a:
        r13.close();	 Catch:{ IOException -> 0x027d }
    L_0x016d:
        if (r5 == 0) goto L_0x0172;
    L_0x016f:
        r5.close();	 Catch:{ IOException -> 0x0283 }
    L_0x0172:
        if (r6 == 0) goto L_0x0177;
    L_0x0174:
        r6.close();	 Catch:{ IOException -> 0x0289 }
    L_0x0177:
        if (r3 == 0) goto L_0x00bb;
    L_0x0179:
        r3.disconnect();
        goto L_0x00bb;
    L_0x017e:
        r3.append(r8);	 Catch:{ MalformedURLException -> 0x0183, IOException -> 0x0324, JSONException -> 0x0301, all -> 0x02ce }
        goto L_0x0080;
    L_0x0183:
        r3 = move-exception;
        r15 = r3;
        r3 = r4;
        r4 = r5;
        r5 = r6;
        r6 = r2;
        r2 = r15;
        goto L_0x0114;
    L_0x018b:
        r3 = 401; // 0x191 float:5.62E-43 double:1.98E-321;
        if (r7 != r3) goto L_0x01ba;
    L_0x018f:
        r0 = r26;
        r0.b(r8);	 Catch:{ MalformedURLException -> 0x01b1, IOException -> 0x01c8, JSONException -> 0x020b, all -> 0x0242 }
        r3 = new org.json.JSONObject;	 Catch:{ MalformedURLException -> 0x01b1, IOException -> 0x01c8, JSONException -> 0x020b, all -> 0x0242 }
        r3.<init>();	 Catch:{ MalformedURLException -> 0x01b1, IOException -> 0x01c8, JSONException -> 0x020b, all -> 0x0242 }
        r7 = "error";
        r8 = new org.json.JSONArray;	 Catch:{ MalformedURLException -> 0x01b1, IOException -> 0x01c8, JSONException -> 0x020b, all -> 0x0242 }
        r9 = new com.kayac.lobi.libnakamap.rec.c.c;	 Catch:{ MalformedURLException -> 0x01b1, IOException -> 0x01c8, JSONException -> 0x020b, all -> 0x0242 }
        r9.<init>();	 Catch:{ MalformedURLException -> 0x01b1, IOException -> 0x01c8, JSONException -> 0x020b, all -> 0x0242 }
        r8.<init>(r9);	 Catch:{ MalformedURLException -> 0x01b1, IOException -> 0x01c8, JSONException -> 0x020b, all -> 0x0242 }
        r3.put(r7, r8);	 Catch:{ MalformedURLException -> 0x01b1, IOException -> 0x01c8, JSONException -> 0x020b, all -> 0x0242 }
        r7 = 102; // 0x66 float:1.43E-43 double:5.04E-322;
        r0 = r26;
        r0.a(r7, r3);	 Catch:{ MalformedURLException -> 0x01b1, IOException -> 0x01c8, JSONException -> 0x020b, all -> 0x0242 }
        goto L_0x00a7;
    L_0x01b1:
        r3 = move-exception;
        r15 = r3;
        r3 = r4;
        r4 = r5;
        r5 = r6;
        r6 = r2;
        r2 = r15;
        goto L_0x0114;
    L_0x01ba:
        r3 = 500; // 0x1f4 float:7.0E-43 double:2.47E-321;
        if (r7 < r3) goto L_0x01ce;
    L_0x01be:
        r3 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r7 = 0;
        r0 = r26;
        r0.a(r3, r7);	 Catch:{ MalformedURLException -> 0x01b1, IOException -> 0x01c8, JSONException -> 0x020b, all -> 0x0242 }
        goto L_0x00a7;
    L_0x01c8:
        r3 = move-exception;
        r13 = r4;
        r15 = r3;
        r3 = r2;
        r2 = r15;
        goto L_0x015d;
    L_0x01ce:
        r3 = "error";
        r3 = r8.has(r3);	 Catch:{ MalformedURLException -> 0x01b1, IOException -> 0x01c8, JSONException -> 0x020b, all -> 0x0242 }
        if (r3 == 0) goto L_0x022e;
    L_0x01d6:
        r3 = "error";
        r3 = r8.optJSONObject(r3);	 Catch:{ MalformedURLException -> 0x01b1, IOException -> 0x01c8, JSONException -> 0x020b, all -> 0x0242 }
        r7 = "message";
        r3 = r3.has(r7);	 Catch:{ MalformedURLException -> 0x01b1, IOException -> 0x01c8, JSONException -> 0x020b, all -> 0x0242 }
        if (r3 == 0) goto L_0x022e;
    L_0x01e4:
        r3 = new org.json.JSONObject;	 Catch:{ MalformedURLException -> 0x01b1, IOException -> 0x01c8, JSONException -> 0x020b, all -> 0x0242 }
        r3.<init>();	 Catch:{ MalformedURLException -> 0x01b1, IOException -> 0x01c8, JSONException -> 0x020b, all -> 0x0242 }
        r7 = new org.json.JSONArray;	 Catch:{ MalformedURLException -> 0x01b1, IOException -> 0x01c8, JSONException -> 0x020b, all -> 0x0242 }
        r7.<init>();	 Catch:{ MalformedURLException -> 0x01b1, IOException -> 0x01c8, JSONException -> 0x020b, all -> 0x0242 }
        r9 = "error";
        r8 = r8.optJSONObject(r9);	 Catch:{ MalformedURLException -> 0x01b1, IOException -> 0x01c8, JSONException -> 0x020b, all -> 0x0242 }
        r9 = "message";
        r8 = r8.optString(r9);	 Catch:{ MalformedURLException -> 0x01b1, IOException -> 0x01c8, JSONException -> 0x020b, all -> 0x0242 }
        r7.put(r8);	 Catch:{ MalformedURLException -> 0x01b1, IOException -> 0x01c8, JSONException -> 0x020b, all -> 0x0242 }
        r8 = "error";
        r3.put(r8, r7);	 Catch:{ MalformedURLException -> 0x01b1, IOException -> 0x01c8, JSONException -> 0x020b, all -> 0x0242 }
        r7 = 102; // 0x66 float:1.43E-43 double:5.04E-322;
        r0 = r26;
        r0.a(r7, r3);	 Catch:{ MalformedURLException -> 0x01b1, IOException -> 0x01c8, JSONException -> 0x020b, all -> 0x0242 }
        goto L_0x00a7;
    L_0x020b:
        r3 = move-exception;
        r13 = r4;
        r15 = r3;
        r3 = r2;
        r2 = r15;
    L_0x0210:
        com.kayac.lobi.libnakamap.rec.a.b.a(r2);	 Catch:{ all -> 0x02dc }
        r2 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r4 = 0;
        r0 = r26;
        r0.a(r2, r4);	 Catch:{ all -> 0x02dc }
        if (r13 == 0) goto L_0x0220;
    L_0x021d:
        r13.close();	 Catch:{ IOException -> 0x028f }
    L_0x0220:
        if (r5 == 0) goto L_0x0225;
    L_0x0222:
        r5.close();	 Catch:{ IOException -> 0x0294 }
    L_0x0225:
        if (r6 == 0) goto L_0x022a;
    L_0x0227:
        r6.close();	 Catch:{ IOException -> 0x0299 }
    L_0x022a:
        if (r3 == 0) goto L_0x00bb;
    L_0x022c:
        goto L_0x0179;
    L_0x022e:
        r3 = new org.json.JSONObject;	 Catch:{ MalformedURLException -> 0x01b1, IOException -> 0x01c8, JSONException -> 0x020b, all -> 0x0242 }
        r3.<init>();	 Catch:{ MalformedURLException -> 0x01b1, IOException -> 0x01c8, JSONException -> 0x020b, all -> 0x0242 }
        r7 = "error";
        r8 = 0;
        r3.put(r7, r8);	 Catch:{ MalformedURLException -> 0x01b1, IOException -> 0x01c8, JSONException -> 0x020b, all -> 0x0242 }
        r7 = 102; // 0x66 float:1.43E-43 double:5.04E-322;
        r0 = r26;
        r0.a(r7, r3);	 Catch:{ MalformedURLException -> 0x01b1, IOException -> 0x01c8, JSONException -> 0x020b, all -> 0x0242 }
        goto L_0x00a7;
    L_0x0242:
        r3 = move-exception;
        r13 = r4;
        r15 = r3;
        r3 = r2;
        r2 = r15;
    L_0x0247:
        if (r13 == 0) goto L_0x024c;
    L_0x0249:
        r13.close();	 Catch:{ IOException -> 0x025c }
    L_0x024c:
        if (r5 == 0) goto L_0x0251;
    L_0x024e:
        r5.close();	 Catch:{ IOException -> 0x0261 }
    L_0x0251:
        if (r6 == 0) goto L_0x0256;
    L_0x0253:
        r6.close();	 Catch:{ IOException -> 0x0266 }
    L_0x0256:
        if (r3 == 0) goto L_0x025b;
    L_0x0258:
        r3.disconnect();
    L_0x025b:
        throw r2;
    L_0x025c:
        r4 = move-exception;
        com.kayac.lobi.libnakamap.rec.a.b.a(r4);
        goto L_0x024c;
    L_0x0261:
        r4 = move-exception;
        com.kayac.lobi.libnakamap.rec.a.b.a(r4);
        goto L_0x0251;
    L_0x0266:
        r4 = move-exception;
        com.kayac.lobi.libnakamap.rec.a.b.a(r4);
        goto L_0x0256;
    L_0x026b:
        r2 = move-exception;
        com.kayac.lobi.libnakamap.rec.a.b.a(r2);
        goto L_0x0124;
    L_0x0271:
        r2 = move-exception;
        com.kayac.lobi.libnakamap.rec.a.b.a(r2);
        goto L_0x0129;
    L_0x0277:
        r2 = move-exception;
        com.kayac.lobi.libnakamap.rec.a.b.a(r2);
        goto L_0x012e;
    L_0x027d:
        r2 = move-exception;
        com.kayac.lobi.libnakamap.rec.a.b.a(r2);
        goto L_0x016d;
    L_0x0283:
        r2 = move-exception;
        com.kayac.lobi.libnakamap.rec.a.b.a(r2);
        goto L_0x0172;
    L_0x0289:
        r2 = move-exception;
        com.kayac.lobi.libnakamap.rec.a.b.a(r2);
        goto L_0x0177;
    L_0x028f:
        r2 = move-exception;
        com.kayac.lobi.libnakamap.rec.a.b.a(r2);
        goto L_0x0220;
    L_0x0294:
        r2 = move-exception;
        com.kayac.lobi.libnakamap.rec.a.b.a(r2);
        goto L_0x0225;
    L_0x0299:
        r2 = move-exception;
        com.kayac.lobi.libnakamap.rec.a.b.a(r2);
        goto L_0x022a;
    L_0x029e:
        r3 = move-exception;
        com.kayac.lobi.libnakamap.rec.a.b.a(r3);
        goto L_0x00ac;
    L_0x02a4:
        r3 = move-exception;
        com.kayac.lobi.libnakamap.rec.a.b.a(r3);
        goto L_0x00b1;
    L_0x02aa:
        r3 = move-exception;
        com.kayac.lobi.libnakamap.rec.a.b.a(r3);
        goto L_0x00b6;
    L_0x02b0:
        r2 = move-exception;
        r5 = r4;
        r6 = r14;
        goto L_0x0247;
    L_0x02b4:
        r3 = move-exception;
        r5 = r4;
        r6 = r14;
        r15 = r3;
        r3 = r2;
        r2 = r15;
        goto L_0x0247;
    L_0x02bb:
        r3 = move-exception;
        r6 = r5;
        r5 = r4;
        r15 = r3;
        r3 = r2;
        r2 = r15;
        goto L_0x0247;
    L_0x02c2:
        r4 = move-exception;
        r5 = r3;
        r6 = r14;
        r3 = r2;
        r2 = r4;
        goto L_0x0247;
    L_0x02c8:
        r3 = move-exception;
        r15 = r3;
        r3 = r2;
        r2 = r15;
        goto L_0x0247;
    L_0x02ce:
        r3 = move-exception;
        r13 = r4;
        r15 = r3;
        r3 = r2;
        r2 = r15;
        goto L_0x0247;
    L_0x02d5:
        r2 = move-exception;
        r13 = r3;
        r3 = r6;
        r6 = r5;
        r5 = r4;
        goto L_0x0247;
    L_0x02dc:
        r2 = move-exception;
        goto L_0x0247;
    L_0x02df:
        r2 = move-exception;
        r5 = r4;
        r6 = r14;
        goto L_0x0210;
    L_0x02e4:
        r3 = move-exception;
        r5 = r4;
        r6 = r14;
        r15 = r3;
        r3 = r2;
        r2 = r15;
        goto L_0x0210;
    L_0x02ec:
        r3 = move-exception;
        r6 = r5;
        r5 = r4;
        r15 = r3;
        r3 = r2;
        r2 = r15;
        goto L_0x0210;
    L_0x02f4:
        r4 = move-exception;
        r5 = r3;
        r6 = r14;
        r3 = r2;
        r2 = r4;
        goto L_0x0210;
    L_0x02fb:
        r3 = move-exception;
        r15 = r3;
        r3 = r2;
        r2 = r15;
        goto L_0x0210;
    L_0x0301:
        r3 = move-exception;
        r13 = r4;
        r15 = r3;
        r3 = r2;
        r2 = r15;
        goto L_0x0210;
    L_0x0308:
        r2 = move-exception;
        r5 = r4;
        r6 = r14;
        goto L_0x015d;
    L_0x030d:
        r3 = move-exception;
        r5 = r4;
        r6 = r14;
        r15 = r3;
        r3 = r2;
        r2 = r15;
        goto L_0x015d;
    L_0x0315:
        r3 = move-exception;
        r6 = r5;
        r5 = r4;
        r15 = r3;
        r3 = r2;
        r2 = r15;
        goto L_0x015d;
    L_0x031d:
        r4 = move-exception;
        r5 = r3;
        r6 = r14;
        r3 = r2;
        r2 = r4;
        goto L_0x015d;
    L_0x0324:
        r3 = move-exception;
        r13 = r4;
        r15 = r3;
        r3 = r2;
        r2 = r15;
        goto L_0x015d;
    L_0x032b:
        r2 = move-exception;
        r5 = r14;
        r6 = r3;
        r3 = r13;
        goto L_0x0114;
    L_0x0331:
        r3 = move-exception;
        r5 = r14;
        r6 = r2;
        r2 = r3;
        r3 = r13;
        goto L_0x0114;
    L_0x0338:
        r3 = move-exception;
        r6 = r2;
        r2 = r3;
        r3 = r13;
        goto L_0x0114;
    L_0x033e:
        r4 = move-exception;
        r5 = r14;
        r6 = r2;
        r2 = r4;
        r4 = r3;
        r3 = r13;
        goto L_0x0114;
    L_0x0346:
        r5 = r4;
        r6 = r14;
        goto L_0x0050;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.kayac.lobi.libnakamap.rec.c.a.a(int, java.lang.String, java.util.Map, java.util.Map, long, long, long, com.kayac.lobi.libnakamap.rec.c.a$b):void");
    }

    static void a(int i, String str, Map<String, String> map, Map<String, File> map2, b bVar) {
        a(i, str, map, map2, -1, -1, -1, bVar);
    }

    private static void a(OutputStream outputStream, Map<String, String> map, Map<String, File> map2, long j, long j2, long j3, String str) throws IOException {
        Throwable th;
        for (Entry entry : map.entrySet()) {
            outputStream.write(String.format("--%s", new Object[]{str}).getBytes());
            outputStream.write("\r\n".getBytes());
            outputStream.write(String.format("Content-Disposition: form-data; name=\"%s\"", new Object[]{entry.getKey()}).getBytes());
            outputStream.write("\r\n".getBytes());
            outputStream.write("\r\n".getBytes());
            outputStream.write(((String) entry.getValue()).getBytes());
            outputStream.write("\r\n".getBytes());
        }
        for (Entry entry2 : map2.entrySet()) {
            BufferedInputStream bufferedInputStream;
            String fileExtensionFromUrl = MimeTypeMap.getFileExtensionFromUrl(((File) entry2.getValue()).getAbsolutePath());
            String str2 = "";
            if (fileExtensionFromUrl != null) {
                str2 = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtensionFromUrl);
            }
            fileExtensionFromUrl = "Content-Type: " + str2;
            outputStream.write(String.format("--%s", new Object[]{str}).getBytes());
            outputStream.write("\r\n".getBytes());
            outputStream.write(String.format("Content-Disposition: form-data; name=\"%s\"; filename=\"%s\"", new Object[]{entry2.getKey(), ((File) entry2.getValue()).getName()}).getBytes());
            outputStream.write("\r\n".getBytes());
            outputStream.write(fileExtensionFromUrl.getBytes());
            outputStream.write("\r\n".getBytes());
            outputStream.write("\r\n".getBytes());
            byte[] bArr = new byte[1024];
            try {
                bufferedInputStream = new BufferedInputStream(new FileInputStream((File) entry2.getValue()));
                int i;
                if (j >= 0 || j2 >= 0 || j3 >= 0) {
                    i = bufferedInputStream.skip(j3) != j3 ? 0 : 0;
                    while (((long) i) < j2) {
                        int read = bufferedInputStream.read(bArr, 0, (int) (j2 - ((long) i) < ((long) bArr.length) ? j2 - ((long) i) : (long) bArr.length));
                        if (read == -1) {
                            break;
                        }
                        outputStream.write(bArr, 0, read);
                        i += read;
                    }
                } else {
                    while (true) {
                        i = bufferedInputStream.read(bArr, 0, bArr.length);
                        if (i == -1) {
                            break;
                        }
                        try {
                            outputStream.write(bArr, 0, i);
                        } catch (Throwable th2) {
                            th = th2;
                        }
                    }
                }
                if (bufferedInputStream != null) {
                    try {
                        bufferedInputStream.close();
                    } catch (IOException e) {
                    }
                }
                outputStream.write("\r\n".getBytes());
            } catch (Throwable th3) {
                th = th3;
                bufferedInputStream = null;
            }
        }
        outputStream.write(String.format("--%s--", new Object[]{str}).getBytes());
        outputStream.write("\r\n".getBytes());
        return;
        if (bufferedInputStream != null) {
            try {
                bufferedInputStream.close();
            } catch (IOException e2) {
            }
        }
        throw th;
        throw th;
    }

    static boolean a(APICallback aPICallback) {
        UserValue currentUser = AccountDatastore.getCurrentUser();
        if (currentUser != null && currentUser.getToken().length() != 0) {
            return true;
        }
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(GCMConstants.EXTRA_ERROR, new JSONArray(new d()));
            aPICallback.onResult(APICallback.FATAL_ERROR, jSONObject);
        } catch (Throwable e) {
            aPICallback.onResult(APICallback.FATAL_ERROR, null);
            com.kayac.lobi.libnakamap.rec.a.b.a(e);
        }
        return false;
    }

    private static String b() {
        StringBuffer stringBuffer = new StringBuffer(32);
        SecureRandom secureRandom = new SecureRandom(SecureRandom.getSeed(32));
        for (int i = 0; i < 32; i++) {
            stringBuffer.append("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".charAt(secureRandom.nextInt(32)));
        }
        return stringBuffer.toString();
    }
}
