package com.rekoo.libs.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import com.rekoo.libs.cons.Cons;
import com.rekoo.libs.net.URLCons;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

public class CommonUtils {
    private static long lastClickTime;
    public static Dialog loadingDialog;

    class AnonymousClass1 implements OnClickListener {
        private final /* synthetic */ Dialog val$disclaimerDialog;

        AnonymousClass1(Dialog dialog) {
            this.val$disclaimerDialog = dialog;
        }

        public void onClick(View v) {
            this.val$disclaimerDialog.dismiss();
        }
    }

    public class CountDown extends CountDownTimer {
        private Context context;
        private Button view;

        public CountDown(Context context, long millisInFuture, long countDownInterval, Button view) {
            super(millisInFuture, countDownInterval);
            this.view = view;
            this.context = context;
            view.setClickable(false);
        }

        public void onTick(long millisUntilFinished) {
            this.view.setBackgroundDrawable(this.context.getResources().getDrawable(ResUtils.getDrawable("gray_btn_click", this.context)));
            this.view.setText((millisUntilFinished / 1000) + "秒");
        }

        public void onFinish() {
            this.view.setBackgroundDrawable(this.context.getResources().getDrawable(ResUtils.getDrawable("btn_gray_bg", this.context)));
            this.view.setText("发送验证码");
            this.view.setClickable(true);
        }
    }

    public static void openActivity(Context context, Class<?> clazz) {
        openActivity(context, clazz, null);
    }

    public static void openActivity(Context context, Class<?> activity, Bundle extras) {
        Intent intent = new Intent(context, activity);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }

    public static void hideSoftKeyboard(Context ctx) {
        try {
            ((InputMethodManager) ctx.getSystemService("input_method")).hideSoftInputFromWindow(((Activity) ctx).getCurrentFocus().getWindowToken(), 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showToast(Context context, String text) {
        Toast toast = Toast.makeText(context, text, 0);
        toast.setGravity(17, 0, 0);
        toast.show();
    }

    public static boolean checkIdentifyingCode(String code, TextView tvCodeError) {
        if (!TextUtils.isEmpty(code)) {
            return true;
        }
        tvCodeError.setText("验证码不能为空");
        tvCodeError.setVisibility(0);
        return false;
    }

    public static boolean checkPassword(String password, TextView tvPwdError) {
        if (TextUtils.isEmpty(password)) {
            tvPwdError.setText("密码不能为空");
            tvPwdError.setVisibility(0);
            return false;
        } else if (password.length() >= 6 && password.length() <= 16) {
            return true;
        } else {
            tvPwdError.setText("密码最短为6位，最长为16位");
            tvPwdError.setVisibility(0);
            return false;
        }
    }

    public static boolean checkTwoPassword(String password, String pwd2, TextView tvPwdError) {
        if (!password.equals(pwd2)) {
            return true;
        }
        tvPwdError.setText("密码最短为6位，最长为16位");
        tvPwdError.setVisibility(0);
        return false;
    }

    public static boolean checkTwoPwd(String pwd1, String pwd2) {
        if (pwd1.equals(pwd2)) {
            return true;
        }
        return false;
    }

    public static boolean checkUserName(String name, TextView tvUserNameError) {
        if (TextUtils.isEmpty(name)) {
            tvUserNameError.setText("用户名不能为空");
            tvUserNameError.setVisibility(0);
            return false;
        } else if (name.length() < 6 || name.length() > 16) {
            tvUserNameError.setText("帐号最长限16个字符，最短限6个字符");
            tvUserNameError.setVisibility(0);
            return false;
        } else if (!StringUtils.checkAccount(name)) {
            tvUserNameError.setText("帐号限小写字母、数字或者邮箱格式组成");
            tvUserNameError.setVisibility(0);
            return false;
        } else if (!StringUtils.checkZH(name)) {
            return true;
        } else {
            tvUserNameError.setText("帐号不能含有中文");
            tvUserNameError.setVisibility(0);
            return false;
        }
    }

    public static boolean checkPhoneNum(String phone, TextView tvPhoneError) {
        if (TextUtils.isEmpty(phone)) {
            tvPhoneError.setText("手机号码不能为空");
            tvPhoneError.setVisibility(0);
            return false;
        } else if (phone.length() < 11 || phone.length() > 11) {
            tvPhoneError.setText("手机号码位数不对");
            tvPhoneError.setVisibility(0);
            return false;
        } else if (StringUtils.judgeMobileNum(phone)) {
            return true;
        } else {
            tvPhoneError.setText("手机号码格式不正确");
            tvPhoneError.setVisibility(0);
            return false;
        }
    }

    public static void countDown(Context context, Button countButton) {
        CommonUtils utils = new CommonUtils();
        utils.getClass();
        new CountDown(context, 60000, 1000, countButton).start();
    }

    public static void showDisclaimerDialog(Context context) {
        Dialog disclaimerDialog = new Dialog(context, ResUtils.getStyle("MyDisclaimerDialogStyle", context));
        disclaimerDialog.show();
        disclaimerDialog.setCancelable(false);
        Window window = disclaimerDialog.getWindow();
        window.setContentView(ResUtils.getLayout("rekoo_dialog_disclaimers_view", context));
        ((Button) window.findViewById(ResUtils.getId("btnDisclaimerConfirm", context))).setOnClickListener(new AnonymousClass1(disclaimerDialog));
    }

    public static boolean hasConnectedNetwork(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivity == null || connectivity.getActiveNetworkInfo() == null) {
            return false;
        }
        return true;
    }

    public static int getIntMetaData(Context context, String key) {
        int msg = -1;
        try {
            msg = getAppInfo(context).metaData.getInt(key);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return msg;
    }

    public static String getStrMetaData(Context context, String key) {
        String msg = null;
        try {
            msg = getAppInfo(context).metaData.getString(key);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return msg;
    }

    private static ApplicationInfo getAppInfo(Context context) throws NameNotFoundException {
        return context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
    }

    public static boolean isEmail(String line) {
        return Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*").matcher(line).find();
    }

    public static boolean isFastClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 1000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    private static SharedPreferences getSP(Context context, String spName) {
        return context.getSharedPreferences(spName, 0);
    }

    public static boolean putSPString(Context context, String spName, String arg) {
        return getSP(context, spName).edit().putString(URLCons.TIME, arg).commit();
    }

    public static String getSPTime(Context context, String spName) {
        return getSP(context, spName).getString(URLCons.TIME, null);
    }

    public static String getToady() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(new Date());
    }

    public static boolean putTouristLoginTimes(Context context, String spName, int value) {
        return getSP(context, spName).edit().putInt(Cons.TOURIST_LOGIN_TIMES, value).commit();
    }

    public static int getTouristLoginTimes(Context context, String spName) {
        return getSP(context, spName).getInt(Cons.TOURIST_LOGIN_TIMES, -1);
    }

    public static boolean isSDCardMount() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    public static void showLoadingDialog(Activity act) {
        View view = LayoutInflater.from(act).inflate(ResUtils.getLayout("loading_dialog", act), null);
        loadingDialog = new Dialog(act, ResUtils.getStyle("MyDisclaimerDialogStyle", act));
        loadingDialog.setCancelable(false);
        loadingDialog.setContentView(view, new LayoutParams(-1, -1));
        loadingDialog.show();
    }

    public static void dismissLoadingDialog() {
        if (loadingDialog == null) {
            throw new NullPointerException("you did not call show CommonUtils.showLoadingDialog(Activity act)");
        }
        loadingDialog.dismiss();
        loadingDialog = null;
    }
}
