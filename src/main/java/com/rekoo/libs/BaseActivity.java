package com.rekoo.libs;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import com.rekoo.libs.config.Config;
import com.rekoo.libs.cons.Cons;
import com.rekoo.libs.platform.ActivityStackManager;
import com.rekoo.libs.utils.LogUtils;
import com.rekoo.libs.utils.ResUtils;

public class BaseActivity extends FragmentActivity {
    protected final int MSG_RESPONSE_NULL = 9;
    protected ImageView btnBack;
    protected Dialog loadingDialog;

    public /* bridge */ /* synthetic */ View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        return super.onCreateView(view, str, context, attributeSet);
    }

    public /* bridge */ /* synthetic */ View onCreateView(String str, Context context, AttributeSet attributeSet) {
        return super.onCreateView(str, context, attributeSet);
    }

    protected void onCreate(Bundle savedInstanceState) {
        LogUtils.i("onCreate");
        super.onCreate(savedInstanceState);
        ActivityStackManager.getAppManager().addActivity(this);
        requestWindowFeature(1);
        controlScreenOrientation();
        getDisplayMetrics();
    }

    private void controlScreenOrientation() {
    }

    private void getDisplayMetrics() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        LogUtils.i("width = " + width);
        LogUtils.i("height= " + height);
    }

    protected int getWidth() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    protected int getHeight() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    protected void onStart() {
        LogUtils.i("onStart");
        super.onStart();
    }

    protected void onRestart() {
        LogUtils.i("onRestart");
        super.onRestart();
    }

    protected void onResume() {
        LogUtils.i("onResume");
        super.onResume();
    }

    protected void onPause() {
        LogUtils.i("onPause");
        super.onPause();
    }

    protected void onStop() {
        LogUtils.i("onStop");
        super.onStop();
    }

    protected void onDestroy() {
        LogUtils.i("onDestroy");
        super.onDestroy();
    }

    protected void showLoadingDialog() {
        View view = LayoutInflater.from(this).inflate(ResUtils.getLayout("loading_dialog", this), null);
        this.loadingDialog = new Dialog(this, ResUtils.getStyle("MyDisclaimerDialogStyle", this));
        this.loadingDialog.setCancelable(false);
        this.loadingDialog.setContentView(view, new LayoutParams(-1, -1));
        this.loadingDialog.show();
    }

    protected void dismissLoadingDialog() {
        if (this.loadingDialog == null) {
            throw new NullPointerException("you did not call show showLoadingDialog()");
        }
        this.loadingDialog.dismiss();
    }

    protected void bindAccount(final Activity act) {
        if (Config.getConfig().getCurrentLoginUser().getType() == 1) {
            Builder builder = new Builder(act);
            builder.setTitle(ResUtils.getString("tips", act));
            builder.setMessage(ResUtils.getString("guest_now_bind_please", act));
            builder.setPositiveButton(ResUtils.getString("bind_immediately", act), new OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    act.finish();
                    BaseActivity.this.sendBroadcast(new Intent("closePayAct"));
                    new Bundle().putParcelable(Cons.BIND_USER, Config.getConfig().getCurrentLoginUser());
                }
            });
            builder.setNegativeButton(ResUtils.getString("cancel", act), new OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    act.finish();
                    BaseActivity.this.sendBroadcast(new Intent("closePayAct"));
                }
            });
            builder.setCancelable(false);
            builder.create().show();
        }
    }
}
