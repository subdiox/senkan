package com.rekoo.libs.platform.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebChromeClient.FileChooserParams;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import com.rekoo.libs.config.Config;
import com.rekoo.libs.cons.Cons;
import com.rekoo.libs.database.DBManager;
import com.rekoo.libs.entity.User;
import com.rekoo.libs.net.URLCons;
import com.rekoo.libs.utils.ResUtils;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class FloatWebView extends Activity {
    private static final int FILECHOOSER_RESULTCODE = 2;
    public static final int INPUT_FILE_REQUEST_CODE = 1;
    private static WebView webView;
    private final int TIMEOUT = 10000;
    private final int TIMEOUT_ERROR = 9527;
    private final int WEB_VIEW_FINISH = 9528;
    private Context context;
    private String mCameraPhotoPath;
    private ValueCallback<Uri[]> mFilePathCallback;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 9527:
                    Log.e("webView", "======> mWebView.getProgress()=" + FloatWebView.webView.getProgress());
                    try {
                        if (FloatWebView.this.mTimer != null) {
                            FloatWebView.this.mTimer.cancel();
                            FloatWebView.this.mTimer.purge();
                        }
                        if (FloatWebView.webView != null && FloatWebView.webView.getProgress() < 100) {
                            FloatWebView.webView.stopLoading();
                            return;
                        }
                        return;
                    } catch (Exception e) {
                        Log.e("webView", "======> webview null");
                        return;
                    }
                case 9528:
                    try {
                        if (FloatWebView.this.pd != null) {
                            FloatWebView.this.pd.setProgress(msg.arg1);
                            if (FloatWebView.this.pd.getProgress() == 100) {
                                FloatWebView.this.pd.setVisibility(8);
                                return;
                            }
                            return;
                        }
                        return;
                    } catch (Exception e2) {
                        Log.e("webView", "======> progress null");
                        return;
                    }
                default:
                    return;
            }
        }
    };
    Timer mTimer;
    TimerTask mTimerTask;
    ValueCallback<Uri> mUploadMessage;
    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            if (FloatWebView.this.mFilePathCallback != null) {
                FloatWebView.this.mFilePathCallback.onReceiveValue(null);
            }
            FloatWebView.this.mFilePathCallback = filePathCallback;
            Intent takePictureIntent = new Intent("android.media.action.IMAGE_CAPTURE");
            if (takePictureIntent.resolveActivity(FloatWebView.this.getPackageManager()) != null) {
                File file = null;
                try {
                    file = FloatWebView.this.createImageFile();
                    takePictureIntent.putExtra("PhotoPath", FloatWebView.this.mCameraPhotoPath);
                } catch (IOException ex) {
                    Log.e("WebViewSetting", "Unable to create Image File", ex);
                }
                if (file != null) {
                    FloatWebView.this.mCameraPhotoPath = "file:" + file.getAbsolutePath();
                    takePictureIntent.putExtra("output", Uri.fromFile(file));
                } else {
                    takePictureIntent = null;
                }
            }
            Intent contentSelectionIntent = new Intent("android.intent.action.GET_CONTENT");
            contentSelectionIntent.addCategory("android.intent.category.OPENABLE");
            contentSelectionIntent.setType("image/*");
            Intent[] intentArray = takePictureIntent != null ? new Intent[]{takePictureIntent} : new Intent[0];
            Intent chooserIntent = new Intent("android.intent.action.CHOOSER");
            chooserIntent.putExtra("android.intent.extra.INTENT", contentSelectionIntent);
            chooserIntent.putExtra("android.intent.extra.TITLE", "Image Chooser");
            chooserIntent.putExtra("android.intent.extra.INITIAL_INTENTS", intentArray);
            FloatWebView.this.startActivityForResult(chooserIntent, 1);
            return true;
        }

        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            FloatWebView.this.mUploadMessage = uploadMsg;
            Intent i = new Intent("android.intent.action.GET_CONTENT");
            i.addCategory("android.intent.category.OPENABLE");
            i.setType("image/*");
            FloatWebView.this.startActivityForResult(Intent.createChooser(i, "Image Chooser"), 2);
        }

        public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
            FloatWebView.this.mUploadMessage = uploadMsg;
            Intent i = new Intent("android.intent.action.GET_CONTENT");
            i.addCategory("android.intent.category.OPENABLE");
            i.setType("image/*");
            FloatWebView.this.startActivityForResult(Intent.createChooser(i, "Image Chooser"), 2);
        }

        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            FloatWebView.this.mUploadMessage = uploadMsg;
            Intent i = new Intent("android.intent.action.GET_CONTENT");
            i.addCategory("android.intent.category.OPENABLE");
            i.setType("image/*");
            FloatWebView.this.startActivityForResult(Intent.createChooser(i, "Image Chooser"), 2);
        }

        public void onProgressChanged(WebView view, int newProgress) {
            Message msg = new Message();
            msg.what = 9528;
            msg.arg1 = newProgress;
            FloatWebView.this.mHandler.sendMessage(msg);
            super.onProgressChanged(view, newProgress);
        }
    };
    private ProgressBar pd;
    private String url;
    private User user;
    List<User> users = null;

    private class MyWebViewClient extends WebViewClient {
        private Context mContext;

        public MyWebViewClient(Context context) {
            this.mContext = context;
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.d("TAG", "URL地址:" + url);
            super.onPageStarted(view, url, favicon);
        }

        public void onPageFinished(WebView view, String url) {
            Log.i("TAG", "onPageFinished");
            super.onPageFinished(view, url);
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        setContentView(ResUtils.getLayout("float_webview", this.context));
        init();
    }

    private void init() {
        this.users = DBManager.getManager(this.context).getAllUsers(this.context);
        this.user = (User) this.users.get(0);
        webView = (WebView) findViewById(ResUtils.getId("webView", this.context));
        this.pd = (ProgressBar) findViewById(ResUtils.getId("pb_float", this.context));
        this.url = Config.forumUrl;
        Log.i("TAG", "getToken" + this.user.getToken());
        synCookies(this.context, this.url, this.user.getUid(), this.user.getToken(), URLCons.getAppId(this.context), (String) URLCons.getDeviceInfo(this.context).get(Cons.DEVICE_MAC));
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(Config.forumUrl);
        Log.i("TAG", "forumUrl" + Config.forumUrl);
        webView.setWebChromeClient(this.mWebChromeClient);
        webView.setWebViewClient(new MyWebViewClient(this));
    }

    public static void synCookies(Context context, String url, String uid, String token, String appid, String deviceid) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.getCookie(url);
        cookieManager.setCookie(url, "uid=" + uid);
        cookieManager.setCookie(url, "appid=" + appid);
        cookieManager.setCookie(url, "token=" + token);
        cookieManager.setCookie(url, "device_unique_key=" + deviceid);
        CookieSyncManager.getInstance().sync();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2) {
            if (this.mUploadMessage != null) {
                Uri result = (data == null || resultCode != -1) ? null : data.getData();
                if (result != null) {
                    String imagePath = ImageFilePath.getPath(this.context, result);
                    if (!TextUtils.isEmpty(imagePath)) {
                        result = Uri.parse("file:///" + imagePath);
                    }
                }
                this.mUploadMessage.onReceiveValue(result);
                this.mUploadMessage = null;
            }
        } else if (requestCode != 1 || this.mFilePathCallback == null) {
            super.onActivityResult(requestCode, resultCode, data);
        } else {
            Uri[] results = null;
            if (resultCode == -1) {
                if (data != null) {
                    if (data.getDataString() != null) {
                        results = new Uri[]{Uri.parse(data.getDataString())};
                    }
                } else if (this.mCameraPhotoPath != null) {
                    results = new Uri[]{Uri.parse(this.mCameraPhotoPath)};
                }
            }
            this.mFilePathCallback.onReceiveValue(results);
            this.mFilePathCallback = null;
        }
    }

    private File createImageFile() throws IOException {
        return File.createTempFile("JPEG_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + "_", ".jpg", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4 && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        finish();
        return super.onKeyDown(keyCode, event);
    }
}
