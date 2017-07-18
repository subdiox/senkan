package com.kayac.lobi.sdk.activity.setting;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import com.kayac.lobi.libnakamap.PathRoutedActivity;
import com.kayac.lobi.libnakamap.components.ActionBar;
import com.kayac.lobi.libnakamap.components.ActionBar.BackableContent;
import com.kayac.lobi.libnakamap.net.LobiWebViewClient;
import com.kayac.lobi.sdk.R;
import com.rekoo.libs.net.URLCons;

public class WebViewSetting extends PathRoutedActivity {
    public static final String PATH_COMMUNITY_WEBVIEW = "/community/webview";
    public static final String PATH_WEBVIEW = "/webview";
    public static final String PATH_WEBVIEW_SETTINGS = "/settings/webview";
    private String mUrl;
    private LobiWebViewClient mWebViewClient = new LobiWebViewClient();
    protected WebView mWebview;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        this.mUrl = intent.getStringExtra("url");
        String contentTitle = intent.getStringExtra("actionBarTitle");
        setContentView(R.layout.lobi_apps_activity);
        BackableContent content = (BackableContent) ((ActionBar) findViewById(R.id.lobi_action_bar)).getContent();
        content.setText(contentTitle);
        content.setOnBackButtonClickListener(new OnClickListener() {
            public void onClick(View v) {
                WebViewSetting.this.finish();
            }
        });
        this.mWebview = (WebView) findViewById(R.id.lobi_apps_webview);
        this.mWebview.setVerticalScrollbarOverlay(true);
        this.mWebview.getSettings().setJavaScriptEnabled(true);
        this.mWebview.setWebViewClient(this.mWebViewClient);
        if (TextUtils.isEmpty(this.mUrl)) {
            String body = intent.getStringExtra(URLCons.CONTENT);
            if (TextUtils.isEmpty(body)) {
                finish();
                return;
            } else {
                this.mWebview.loadDataWithBaseURL("fake://not/needed", body, "text/html", "UTF-8", "");
                return;
            }
        }
        this.mWebview.loadUrl(this.mUrl);
    }

    protected void onDestroy() {
        this.mWebview.destroy();
        super.onDestroy();
    }

    public void finish() {
        super.finish();
    }

    public void onBackPressed() {
        if (this.mWebview == null || !this.mWebview.canGoBack()) {
            super.onBackPressed();
        } else {
            this.mWebview.goBack();
        }
    }
}
