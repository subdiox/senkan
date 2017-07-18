package com.kayac.lobi.sdk.rec.activity;

import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import com.google.android.gcm.GCMConstants;
import com.kayac.lobi.libnakamap.net.LobiWebViewClient;
import com.kayac.lobi.libnakamap.rec.c.am;
import com.kayac.lobi.sdk.LobiCore;
import java.util.Map;

class bf extends LobiWebViewClient {
    final /* synthetic */ RecSNSLoginActivity a;

    bf(RecSNSLoginActivity recSNSLoginActivity) {
        this.a = recSNSLoginActivity;
    }

    public void onPageFinished(WebView webView, String str) {
        super.onPageFinished(webView, str);
        if (this.a.mActionBar != null) {
            this.a.mActionBar.setVisibility(0);
        }
    }

    public boolean shouldOverrideUrlLoading(WebView webView, String str) {
        String str2 = "nakamapapp-" + LobiCore.sharedInstance().getClientId();
        String str3 = "external_bind";
        Uri parse = Uri.parse(str);
        if (str2.equals(parse.getScheme()) && "external_bind".equals(parse.getHost())) {
            Map a = am.a(parse);
            if (a.containsKey(GCMConstants.EXTRA_ERROR)) {
                RecSNSLoginActivity.sLog.c("SNSLogin error : " + ((String) a.get(GCMConstants.EXTRA_ERROR)));
                this.a.finish();
                return true;
            } else if (a.containsKey("service")) {
                str2 = (String) a.get("service");
                Intent intent = new Intent();
                intent.putExtra(RecPostVideoActivity.RESULT_EXTRA_SERVICE, str2);
                this.a.setResult(-1, intent);
                this.a.finish();
                return true;
            }
        }
        return false;
    }
}
