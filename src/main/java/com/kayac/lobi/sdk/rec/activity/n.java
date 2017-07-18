package com.kayac.lobi.sdk.rec.activity;

import android.webkit.WebView;
import com.kayac.lobi.libnakamap.net.LobiWebViewClient;

class n extends LobiWebViewClient {
    final /* synthetic */ RecPlayActivity a;

    n(RecPlayActivity recPlayActivity) {
        this.a = recPlayActivity;
    }

    public void onPageFinished(WebView webView, String str) {
        super.onPageFinished(webView, str);
        this.a.updateActionbarContent(str);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean shouldOverrideUrlLoading(android.webkit.WebView r9, java.lang.String r10) {
        /*
        r8 = this;
        r2 = 0;
        r3 = 1;
        r0 = android.net.Uri.parse(r10);
        r1 = "user";
        r1 = r0.getQueryParameter(r1);
        r4 = ".m3u8";
        r4 = r10.endsWith(r4);
        if (r4 == 0) goto L_0x0036;
    L_0x0014:
        r0 = r8.a;
        r0 = r0.mLoadingModal;
        if (r0 == 0) goto L_0x001d;
    L_0x001c:
        return r3;
    L_0x001d:
        r0 = r8.a;
        r0.mLoadingModal = r3;
        r0 = new android.content.Intent;
        r1 = r8.a;
        r2 = com.kayac.lobi.sdk.rec.activity.RecVideoLoadingActivity.class;
        r0.<init>(r1, r2);
        r1 = "EXTRA_URI";
        r0.putExtra(r1, r10);
        r1 = r8.a;
        r1.startActivity(r0);
        goto L_0x001c;
    L_0x0036:
        r4 = r0.getHost();
        r5 = "web.lobi.co";
        r4 = r4.equals(r5);
        if (r4 == 0) goto L_0x005c;
    L_0x0042:
        r1 = r8.a;
        r1 = r1.mLoadingModal;
        if (r1 != 0) goto L_0x001c;
    L_0x004a:
        r1 = r8.a;
        r1.mLoadingModal = r3;
        r1 = new android.content.Intent;
        r2 = "android.intent.action.VIEW";
        r1.<init>(r2, r0);
        r0 = r8.a;
        r0.startActivity(r1);
        goto L_0x001c;
    L_0x005c:
        r4 = r0.getHost();
        r5 = "thanks.lobi.co";
        r4 = r4.equals(r5);
        if (r4 == 0) goto L_0x007e;
    L_0x0068:
        r4 = r0.getPath();
        r5 = "/videos";
        r4 = r4.equals(r5);
        if (r4 == 0) goto L_0x007e;
    L_0x0074:
        if (r1 == 0) goto L_0x007e;
    L_0x0076:
        r0 = r9.getContext();
        com.kayac.lobi.sdk.activity.profile.ProfileActivity.startProfileWithUserUid(r0, r1);
        goto L_0x001c;
    L_0x007e:
        r4 = r0.getHost();
        r5 = "thanks.lobi.co";
        r4 = r4.equals(r5);
        if (r4 == 0) goto L_0x00ae;
    L_0x008a:
        r0 = com.kayac.lobi.sdk.rec.activity.RecPlayActivity.isListPageUri(r0);
        if (r0 == 0) goto L_0x00ae;
    L_0x0090:
        if (r1 != 0) goto L_0x00ae;
    L_0x0092:
        r0 = "/rec_playlist/video";
        r1 = com.kayac.lobi.libnakamap.components.PathRouter.getLastPath();
        r0 = r0.equals(r1);
        if (r0 == 0) goto L_0x00ae;
    L_0x009e:
        r0 = com.kayac.lobi.sdk.rec.activity.RecPlayActivity.sLog;
        r1 = "Back to the previous Activity. The path /rec_playlist/video doesn't load the video list page.";
        r0.b(r1);
        r0 = r8.a;
        r0.finish();
        goto L_0x001c;
    L_0x00ae:
        r4 = com.kayac.lobi.libnakamap.rec.b.d.a(r10);
        r0 = r4.a();
        switch(r0) {
            case 0: goto L_0x00ba;
            case 1: goto L_0x00bd;
            case 2: goto L_0x00d6;
            case 3: goto L_0x012e;
            case 4: goto L_0x0179;
            default: goto L_0x00b9;
        };
    L_0x00b9:
        r2 = r3;
    L_0x00ba:
        r3 = r2;
        goto L_0x001c;
    L_0x00bd:
        r0 = r4.b();
        r1 = "videoId";
        r0 = r0.get(r1);
        r0 = (java.lang.String) r0;
        r1 = android.text.TextUtils.isEmpty(r0);
        if (r1 != 0) goto L_0x00b9;
    L_0x00cf:
        r1 = r8.a;
        r1.showAccuseVideoAlert(r0);
        r2 = r3;
        goto L_0x00ba;
    L_0x00d6:
        r0 = r8.a;
        r0 = r0.mLoadingModal;
        if (r0 == 0) goto L_0x00e0;
    L_0x00de:
        r2 = r3;
        goto L_0x00ba;
    L_0x00e0:
        r0 = r8.a;
        r0.mLoadingModal = r3;
        r0 = r4.b();
        r1 = "videoTitle";
        r0 = r0.get(r1);
        r0 = (java.lang.String) r0;
        r1 = r4.b();
        r4 = "videoURL";
        r1 = r1.get(r4);
        r1 = (java.lang.String) r1;
        r4 = new android.content.Intent;
        r5 = "android.intent.action.SEND";
        r4.<init>(r5);
        r5 = "text/plain";
        r4.setType(r5);
        r5 = "android.intent.extra.TEXT";
        r6 = "%s %s";
        r7 = 2;
        r7 = new java.lang.Object[r7];
        r7[r2] = r0;
        r7[r3] = r1;
        r0 = java.lang.String.format(r6, r7);
        r4.putExtra(r5, r0);
        r0 = r8.a;
        r1 = r8.a;
        r2 = com.kayac.lobi.sdk.rec.R.string.lobirec_share;
        r1 = r1.getString(r2);
        r1 = android.content.Intent.createChooser(r4, r1);
        r0.startActivity(r1);
        r2 = r3;
        goto L_0x00ba;
    L_0x012e:
        r0 = r8.a;
        r0.mShouldRefresh = r3;
        r0 = r4.b();
        r1 = "type";
        r0 = r0.get(r1);
        r0 = (java.lang.String) r0;
        r1 = android.text.TextUtils.isEmpty(r0);
        if (r1 != 0) goto L_0x0172;
    L_0x0145:
        r0 = java.lang.Integer.parseInt(r0);	 Catch:{ NumberFormatException -> 0x016e }
        r1 = r0;
    L_0x014a:
        r0 = r4.b();
        r4 = "tid";
        r0 = r0.get(r4);
        r0 = (java.lang.String) r0;
        r4 = android.text.TextUtils.isEmpty(r0);
        if (r4 != 0) goto L_0x0160;
    L_0x015c:
        r2 = java.lang.Integer.parseInt(r0);	 Catch:{ NumberFormatException -> 0x0174 }
    L_0x0160:
        r0 = r9.getContext();
        r0 = (com.kayac.lobi.sdk.rec.activity.RecPlayActivity) r0;
        r4 = "login";
        com.kayac.lobi.libnakamap.components.LoginEntranceDialog.start(r0, r4, r1, r2);
        r2 = r3;
        goto L_0x00ba;
    L_0x016e:
        r0 = move-exception;
        com.kayac.lobi.libnakamap.rec.a.b.a(r0);
    L_0x0172:
        r1 = r2;
        goto L_0x014a;
    L_0x0174:
        r0 = move-exception;
        com.kayac.lobi.libnakamap.rec.a.b.a(r0);
        goto L_0x0160;
    L_0x0179:
        r0 = r8.a;
        r0 = r0.mLoadingModal;
        if (r0 == 0) goto L_0x0184;
    L_0x0181:
        r2 = r3;
        goto L_0x00ba;
    L_0x0184:
        r0 = r8.a;
        r0.mLoadingModal = r3;
        r0 = r4.b();
        r1 = "app_url";
        r0 = r0.get(r1);
        r0 = (java.lang.String) r0;
        r1 = r4.b();
        r2 = "web_url";
        r1 = r1.get(r2);
        r1 = (java.lang.String) r1;
        r2 = new android.content.Intent;
        r4 = "android.intent.action.VIEW";
        r2.<init>(r4);
        r0 = android.net.Uri.parse(r0);
        r2.setData(r0);
        r0 = new android.content.Intent;
        r4 = "android.intent.action.VIEW";
        r0.<init>(r4);
        r1 = android.net.Uri.parse(r1);
        r0.setData(r1);
        r1 = r8.a;	 Catch:{ ActivityNotFoundException -> 0x01c5 }
        r1.startActivity(r2);	 Catch:{ ActivityNotFoundException -> 0x01c5 }
        r2 = r3;
        goto L_0x00ba;
    L_0x01c5:
        r1 = move-exception;
        r1 = r8.a;
        r1.startActivity(r0);
        r2 = r3;
        goto L_0x00ba;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.kayac.lobi.sdk.rec.activity.n.shouldOverrideUrlLoading(android.webkit.WebView, java.lang.String):boolean");
    }
}
