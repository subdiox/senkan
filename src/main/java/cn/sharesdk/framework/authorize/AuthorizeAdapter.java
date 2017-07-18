package cn.sharesdk.framework.authorize;

import android.app.Activity;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import cn.sharesdk.framework.TitleLayout;

public class AuthorizeAdapter {
    private Activity activity;
    private boolean noTitle;
    private String platform;
    private boolean popUpAnimationDisable;
    private RelativeLayout rlBody;
    private TitleLayout title;
    private WebView webview;

    protected void disablePopUpAnimation() {
        this.popUpAnimationDisable = true;
    }

    public Activity getActivity() {
        return this.activity;
    }

    public RelativeLayout getBodyView() {
        return this.rlBody;
    }

    public String getPlatformName() {
        return this.platform;
    }

    public TitleLayout getTitleLayout() {
        return this.title;
    }

    public WebView getWebBody() {
        return this.webview;
    }

    public void hideShareSDKLogo() {
        getTitleLayout().getChildAt(getTitleLayout().getChildCount() - 1).setVisibility(8);
    }

    boolean isNotitle() {
        return this.noTitle;
    }

    boolean isPopUpAnimationDisable() {
        return this.popUpAnimationDisable;
    }

    public void onCreate() {
    }

    public void onDestroy() {
    }

    public boolean onFinish() {
        return false;
    }

    public boolean onKeyEvent(int keyCode, KeyEvent event) {
        return false;
    }

    public void onPause() {
    }

    public void onResize(int w, int h, int oldw, int oldh) {
    }

    public void onRestart() {
    }

    public void onResume() {
    }

    public void onStart() {
    }

    public void onStop() {
    }

    void setActivity(Activity activity) {
        this.activity = activity;
    }

    void setBodyView(RelativeLayout rlBody) {
        this.rlBody = rlBody;
    }

    void setNotitle(boolean noTitle) {
        this.noTitle = noTitle;
    }

    void setPlatformName(String platform) {
        this.platform = platform;
    }

    void setTitleView(TitleLayout title) {
        this.title = title;
    }

    void setWebView(WebView webview) {
        this.webview = webview;
    }
}
