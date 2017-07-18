package com.kayac.lobi.sdk.rec.activity;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import com.kayac.lobi.libnakamap.PathRoutedActivity;
import com.kayac.lobi.libnakamap.components.ActionBar;
import com.kayac.lobi.libnakamap.components.ActionBar.BackableContent;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.net.LobiWebViewClient;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.sdk.rec.R;

public class RecFAQActivity extends PathRoutedActivity {
    private WebView mWebView;
    private LobiWebViewClient mWebViewClient = new a(this);

    private boolean canGoBack() {
        this.mWebView = (WebView) findViewById(R.id.lobi_rec_faq_content);
        return this.mWebView.canGoBack();
    }

    private void goBack() {
        this.mWebView = (WebView) findViewById(R.id.lobi_rec_faq_content);
        this.mWebView.goBack();
    }

    private void setupActionBar() {
        ((BackableContent) ((ActionBar) findViewById(R.id.lobi_action_bar)).getContent()).setOnBackButtonClickListener(new b(this));
    }

    private static Uri setupUrl() {
        UserValue currentUser = AccountDatastore.getCurrentUser();
        Builder builder = new Builder();
        builder.scheme("https");
        builder.authority("thanks.lobi.co");
        builder.path("/faq");
        builder.appendQueryParameter("token", currentUser.getToken());
        return builder.build();
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getIntent() == null) {
            finish();
            return;
        }
        String uri = setupUrl().toString();
        setContentView(R.layout.lobi_rec_activity_faq);
        setupActionBar();
        this.mWebView = (WebView) findViewById(R.id.lobi_rec_faq_content);
        this.mWebView.setWebViewClient(this.mWebViewClient);
        this.mWebView.getSettings().setJavaScriptEnabled(true);
        this.mWebView.setWebChromeClient(new WebChromeClient());
        this.mWebView.setScrollBarStyle(0);
        this.mWebView.getSettings().setAppCachePath(getCacheDir().getAbsolutePath());
        this.mWebView.getSettings().setAppCacheEnabled(true);
        this.mWebView.getSettings().setCacheMode(2);
        this.mWebView.loadUrl(uri);
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4) {
            return super.onKeyDown(i, keyEvent);
        }
        if (canGoBack()) {
            goBack();
        } else {
            finish();
        }
        return false;
    }
}
