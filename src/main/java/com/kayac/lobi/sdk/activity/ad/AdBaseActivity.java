package com.kayac.lobi.sdk.activity.ad;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.kayac.lobi.libnakamap.PathRoutedActivity;
import com.kayac.lobi.libnakamap.components.ActionBar;
import com.kayac.lobi.libnakamap.components.ActionBar.BackableContent;
import com.kayac.lobi.libnakamap.components.ActionBar.Button;
import com.kayac.lobi.libnakamap.components.ActionBar.MenuContent;
import com.kayac.lobi.libnakamap.components.MenuDrawer;
import com.kayac.lobi.libnakamap.components.PathRouter;
import com.kayac.lobi.libnakamap.net.APIUtil;
import com.kayac.lobi.libnakamap.net.CoreAPI;
import com.kayac.lobi.libnakamap.net.LobiWebViewClient;
import com.kayac.lobi.libnakamap.utils.AdUtil;
import com.kayac.lobi.libnakamap.utils.DeviceUUID;
import com.kayac.lobi.libnakamap.utils.DeviceUUID.OnGetUUID;
import com.kayac.lobi.libnakamap.utils.Log;
import com.kayac.lobi.sdk.R;
import com.rekoo.libs.net.URLCons;
import java.util.Map;

public class AdBaseActivity extends PathRoutedActivity {
    public static final String EXTRA_FROM_MENU = "EXTRA_FROM_MENU";
    DrawerLayout mDrawerLayout;
    private boolean mEnableVirtualBack;
    private boolean mFromMenu;
    private int mMenuId;
    private String mTitle;
    private LobiWebViewClient mWebViewClient = new LobiWebViewClient() {
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            AdBaseActivity.this.mEnableVirtualBack = true;
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (shouldOverrideUrlLoading(view, url)) {
                view.stopLoading();
            } else {
                super.onPageStarted(view, url, favicon);
            }
        }

        public boolean shouldOverrideUrlLoading(WebView view, String urlString) {
            Log.i(AdBaseActivity.class.getSimpleName(), "sh:" + urlString);
            if (AdUtil.isClosed(urlString)) {
                if (view.canGoBack()) {
                    view.goBack();
                    return true;
                }
                AdBaseActivity.this.finish();
                return true;
            } else if (AdUtil.shouldOverrideUrlLoading(AdBaseActivity.this, view, urlString)) {
                return true;
            } else {
                return false;
            }
        }
    };
    protected WebView mWebview;

    public void setBackableState(boolean isBackable, boolean forceToSet) {
        ActionBar actionBar = (ActionBar) findViewById(R.id.lobi_action_bar);
        if (isBackable && (this.mFromMenu || forceToSet)) {
            MenuDrawer.disableMenuDrawer(this.mDrawerLayout);
            BackableContent content = new BackableContent(this);
            actionBar.setContent(content);
            content.setText(this.mTitle);
            content.setOnBackButtonClickListener(new OnClickListener() {
                public void onClick(View v) {
                    AdBaseActivity.this.goBackOrFinish();
                }
            });
            this.mFromMenu = false;
            onResume();
        } else if (!isBackable) {
            if (!this.mFromMenu || forceToSet) {
                MenuDrawer.enableMenuDrawer(this.mDrawerLayout);
                MenuContent content2 = new MenuContent(this);
                actionBar.setContent(content2);
                content2.setText(this.mTitle);
                content2.setOnMenuButtonClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        AdBaseActivity.this.mDrawerLayout.openDrawer(8388611);
                    }
                });
                Button actionBarButton = new Button(this);
                actionBarButton.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        AdBaseActivity.this.finish();
                    }
                });
                actionBarButton.setIconImage(R.drawable.lobi_action_bar_close_selector);
                actionBar.addActionBarButtonWithSeparator(actionBarButton);
                this.mFromMenu = true;
                onResume();
            }
        }
    }

    protected void init(String title, final String path, final Map<String, String> params, int menuId) {
        if (menuId == 0) {
            this.mFromMenu = false;
        }
        this.mDrawerLayout = MenuDrawer.setMenuDrawer(this, (DrawerLayout) findViewById(R.id.drawer_layout), (ViewGroup) findViewById(R.id.content_frame));
        this.mMenuId = menuId;
        this.mTitle = title;
        setBackableState(!this.mFromMenu, true);
        if (params.containsKey("install_id")) {
            DeviceUUID.getUUID(getApplicationContext(), new OnGetUUID() {
                public void onGetUUID(String uuid) {
                    params.put("install_id", uuid);
                    AdBaseActivity.this.setUrl(path, params);
                }
            });
        } else {
            setUrl(path, params);
        }
    }

    private void setUrl(String path, Map<String, String> params) {
        String url = APIUtil.uriBuilderFactory(CoreAPI.getEndpoint(), path, (Map) params).build().toString();
        Log.i(AdBaseActivity.class.getSimpleName(), url);
        if (TextUtils.isEmpty(url)) {
            String body = getIntent().getStringExtra(URLCons.CONTENT);
            if (TextUtils.isEmpty(body)) {
                finish();
                return;
            } else {
                this.mWebview.loadDataWithBaseURL("fake://not/needed", body, "text/html", "UTF-8", "");
                return;
            }
        }
        this.mWebview.loadUrl(url);
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getData() == null) {
            setContentView(R.layout.lobi_ad_activity);
            this.mWebview = (WebView) findViewById(R.id.lobi_apps_webview);
            this.mWebview.setVerticalScrollbarOverlay(true);
            this.mWebview.setWebViewClient(this.mWebViewClient);
            WebSettings settings = this.mWebview.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setAppCachePath(getCacheDir().getAbsolutePath());
            settings.setAppCacheEnabled(true);
            settings.setCacheMode(2);
            settings.setUserAgentString(APIUtil.getUserAgent());
            this.mFromMenu = getIntent().getBooleanExtra("EXTRA_FROM_MENU", false);
            this.mEnableVirtualBack = false;
        }
    }

    protected void onResume() {
        super.onResume();
        if (this.mFromMenu) {
            MenuDrawer.setMenuItems(this.mDrawerLayout);
        }
    }

    protected void onStop() {
        super.onStop();
        if (this.mFromMenu) {
            this.mDrawerLayout.closeDrawers();
        }
    }

    protected void onDestroy() {
        if (this.mWebview != null) {
            ((ViewGroup) this.mWebview.getParent()).removeView(this.mWebview);
            this.mWebview.removeAllViews();
            this.mWebview.setWebViewClient(null);
            this.mWebview.setWebChromeClient(null);
            this.mWebview.destroy();
        }
        super.onDestroy();
    }

    public void finish() {
        super.finish();
        if (this.mFromMenu) {
            overridePendingTransition(0, 0);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4) {
            return false;
        }
        goBackOrFinish();
        return true;
    }

    protected void goBackOrFinish() {
        if (this.mEnableVirtualBack) {
            if (this.mWebview != null) {
                this.mWebview.loadUrl(AdUtil.buildJavaScriptUrl(AdUtil.METHOD_HISTORY_BACK, new Object[0]));
            } else {
                finish();
            }
        } else if (this.mWebview == null || !this.mWebview.canGoBack()) {
            finish();
        } else {
            this.mWebview.goBack();
        }
    }

    public static void startFromMenu(String path) {
        startFromMenu(path, new Bundle());
    }

    public static void startFromMenu(String path, Bundle extra) {
        extra.putBoolean("EXTRA_FROM_MENU", true);
        extra.putString(PathRouter.PATH, path);
        PathRouter.removePathsGreaterThan("/");
        PathRouter.startPath(extra, 65536);
    }
}
