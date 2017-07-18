package com.rekoo.libs.push;

import android.content.Context;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.rekoo.libs.BaseActivity;
import com.rekoo.libs.config.BIConfig;
import com.rekoo.libs.utils.ResUtils;

public class NotificationActivity extends BaseActivity {
    private Context context;
    private String data;
    private WebView nwv;
    String s;
    private String url;

    public /* bridge */ /* synthetic */ View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        return super.onCreateView(view, str, context, attributeSet);
    }

    public /* bridge */ /* synthetic */ View onCreateView(String str, Context context, AttributeSet attributeSet) {
        return super.onCreateView(str, context, attributeSet);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        setContentView(ResUtils.getLayout("notification", this.context));
        this.nwv = (WebView) findViewById(ResUtils.getId("notification_wv", this.context));
        this.data = getIntent().getStringExtra("url");
        this.url = new String(Base64.decode(this.data.getBytes(), 0));
        Log.i("TAG", "url" + this.url);
        this.nwv.loadUrl(this.url);
        this.nwv.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                handler.cancel();
            }
        });
        BIConfig.getBiConfig().openPushMessageCustom(this.context);
        Read.getManager().initRead(this.context);
    }

    protected void onStop() {
        super.onStop();
        finish();
    }
}
