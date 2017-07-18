package com.kayac.lobi.sdk.rec.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import com.kayac.lobi.libnakamap.PathRoutedActivity;
import com.kayac.lobi.libnakamap.components.ActionBar;
import com.kayac.lobi.libnakamap.components.ActionBar.BackableContent;
import com.kayac.lobi.libnakamap.net.LobiWebViewClient;
import com.kayac.lobi.libnakamap.rec.a.b;
import com.kayac.lobi.libnakamap.rec.c.f;
import com.kayac.lobi.sdk.rec.R;

public class RecSNSLoginActivity extends PathRoutedActivity {
    public static final String EXTRA_SERVICE = "EXTRA_SERVICE";
    private static final String TAG = RecSNSLoginActivity.class.getSimpleName();
    private static final b sLog = new b(TAG);
    private ActionBar mActionBar;
    private LobiWebViewClient mWebViewClient = new bf(this);

    private void goBackIfHasHistory() {
        WebView webView = (WebView) findViewById(R.id.lobi_rec_sns_login_content);
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }

    private void setupActionBar() {
        this.mActionBar = (ActionBar) findViewById(R.id.lobi_action_bar);
        ((BackableContent) this.mActionBar.getContent()).setOnBackButtonClickListener(new bi(this));
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        if (intent == null) {
            finish();
        } else if (intent.hasExtra(EXTRA_SERVICE)) {
            String stringExtra = intent.getStringExtra(EXTRA_SERVICE);
            if (TextUtils.isEmpty(stringExtra)) {
                finish();
                return;
            }
            setContentView(R.layout.lobi_rec_activity_sns_login);
            setupActionBar();
            WebView webView = (WebView) findViewById(R.id.lobi_rec_sns_login_content);
            webView.setWebViewClient(this.mWebViewClient);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebChromeClient(new WebChromeClient());
            webView.setScrollBarStyle(0);
            webView.getSettings().setAppCachePath(getCacheDir().getAbsolutePath());
            webView.getSettings().setAppCacheEnabled(true);
            webView.getSettings().setCacheMode(2);
            webView.setVisibility(4);
            this.mActionBar.setVisibility(4);
            f.a(stringExtra, new bg(this, webView));
        } else {
            finish();
        }
    }
}
