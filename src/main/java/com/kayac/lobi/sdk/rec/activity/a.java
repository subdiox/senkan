package com.kayac.lobi.sdk.rec.activity;

import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import com.kayac.lobi.libnakamap.net.LobiWebViewClient;

class a extends LobiWebViewClient {
    final /* synthetic */ RecFAQActivity a;

    a(RecFAQActivity recFAQActivity) {
        this.a = recFAQActivity;
    }

    public boolean shouldOverrideUrlLoading(WebView webView, String str) {
        Uri parse = Uri.parse(str);
        if (!parse.getHost().equals("web.lobi.co")) {
            return false;
        }
        this.a.startActivity(new Intent("android.intent.action.VIEW", parse));
        return true;
    }
}
