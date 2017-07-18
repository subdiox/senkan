package com.kayac.lobi.sdk.activity.invitation;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import com.kayac.lobi.libnakamap.PathRoutedActivity;
import com.kayac.lobi.libnakamap.components.ActionBar;
import com.kayac.lobi.libnakamap.components.ActionBar.BackableContent;
import com.kayac.lobi.libnakamap.net.LobiWebViewClient;
import com.kayac.lobi.libnakamap.utils.URLUtils;
import com.kayac.lobi.sdk.R;
import com.rekoo.libs.net.URLCons;
import java.util.regex.Pattern;

public class InvitationWebViewActivity extends PathRoutedActivity {
    public static final String EXTRA_TITLE = "EXTRA_TITLE";
    public static final String EXTRA_URL = "EXTRA_URL";
    public static final String PATH_INVITATION = "/invitation";
    private String mUrl;
    private LobiWebViewClient mWebViewClient = new LobiWebViewClient() {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith("nakamap:")) {
                InvitationHandler.handleInvitation(InvitationWebViewActivity.this, URLUtils.parseQuery(Uri.parse(url)));
            } else if (Pattern.matches("https?://lobi.co/invite/\\w+", url)) {
                return false;
            } else {
                ((Activity) view.getContext()).startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
            }
            return true;
        }
    };
    protected WebView mWebview;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        this.mUrl = intent.getStringExtra(EXTRA_URL);
        String contentTitle = intent.getStringExtra("EXTRA_TITLE");
        setContentView(R.layout.lobi_apps_activity);
        BackableContent content = (BackableContent) ((ActionBar) findViewById(R.id.lobi_action_bar)).getContent();
        content.setText(contentTitle);
        content.setOnBackButtonClickListener(new OnClickListener() {
            public void onClick(View v) {
                InvitationWebViewActivity.this.finish();
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
}
