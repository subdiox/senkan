package cn.sharesdk.framework;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Message;
import android.view.KeyEvent;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.mob.tools.utils.DeviceHelper;

public class i extends WebViewClient {
    public static final int ERROR_AUTHENTICATION = -4;
    public static final int ERROR_BAD_URL = -12;
    public static final int ERROR_CONNECT = -6;
    public static final int ERROR_FAILED_SSL_HANDSHAKE = -11;
    public static final int ERROR_FILE = -13;
    public static final int ERROR_FILE_NOT_FOUND = -14;
    public static final int ERROR_HOST_LOOKUP = -2;
    public static final int ERROR_IO = -7;
    public static final int ERROR_PROXY_AUTHENTICATION = -5;
    public static final int ERROR_REDIRECT_LOOP = -9;
    public static final int ERROR_TIMEOUT = -8;
    public static final int ERROR_TOO_MANY_REQUESTS = -15;
    public static final int ERROR_UNKNOWN = -1;
    public static final int ERROR_UNSUPPORTED_AUTH_SCHEME = -3;
    public static final int ERROR_UNSUPPORTED_SCHEME = -10;

    public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
    }

    public void onFormResubmission(WebView view, Message dontResend, Message resend) {
        dontResend.sendToTarget();
    }

    public void onLoadResource(WebView view, String url) {
    }

    public void onPageFinished(WebView view, String url) {
    }

    public void onPageStarted(WebView view, String url, Bitmap favicon) {
    }

    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
    }

    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
        handler.cancel();
    }

    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        if (view.getContext() instanceof Activity) {
            String[] strArr;
            CharSequence valueOf;
            CharSequence valueOf2;
            CharSequence valueOf3;
            Activity activity = (Activity) view.getContext();
            if ("zh".equals(DeviceHelper.getInstance(activity).getOSLanguage())) {
                strArr = new String[]{String.valueOf(new char[]{'不', '受', '信', '任', '的', '证', '书', '。', '你', '要', '继', '续', '吗', '？'}), String.valueOf(new char[]{'证', '书', '已', '过', '期', '。', '你', '要', '继', '续', '吗', '？'}), String.valueOf(new char[]{'证', '书', 'I', 'D', '不', '匹', '配', '。', '你', '要', '继', '续', '吗', '？'}), String.valueOf(new char[]{'证', '书', '尚', '未', '生', '效', '。', '你', '要', '继', '续', '吗', '？'}), String.valueOf(new char[]{'证', '书', '错', '误', '。', '你', '要', '继', '续', '吗', '？'})};
                valueOf = String.valueOf(new char[]{'证', '书', '错', '误'});
                valueOf2 = String.valueOf(new char[]{'继', '续'});
                valueOf3 = String.valueOf(new char[]{'停', '止'});
            } else {
                strArr = new String[]{"Certificate is untrusted. Do you want to continue anyway?", "Certificate has expired. Do you want to continue anyway?", "Certificate ID is mismatched. Do you want to continue anyway?", "Certificate is not yet valid. Do you want to continue anyway?", "Certificate error. Do you want to continue anyway?"};
                valueOf = "SSL Certificate Error";
                valueOf2 = "Yes";
                valueOf3 = "No";
            }
            Builder builder = new Builder(activity);
            builder.setTitle(valueOf);
            switch (error.getPrimaryError()) {
                case 0:
                    builder.setMessage(strArr[3]);
                    break;
                case 1:
                    builder.setMessage(strArr[1]);
                    break;
                case 2:
                    builder.setMessage(strArr[2]);
                    break;
                case 3:
                    builder.setMessage(strArr[0]);
                    break;
                default:
                    builder.setMessage(strArr[4]);
                    break;
            }
            builder.setCancelable(false);
            builder.setPositiveButton(valueOf2, new j(this, handler));
            builder.setNegativeButton(valueOf3, new k(this, handler));
            builder.create().show();
            return;
        }
        handler.cancel();
    }

    public void onScaleChanged(WebView view, float oldScale, float newScale) {
    }

    public void onTooManyRedirects(WebView view, Message cancelMsg, Message continueMsg) {
        cancelMsg.sendToTarget();
    }

    public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
    }

    public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
        return false;
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return false;
    }
}
