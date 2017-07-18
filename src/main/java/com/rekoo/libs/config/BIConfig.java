package com.rekoo.libs.config;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import com.rekoo.libs.cons.Cons;
import com.rekoo.libs.net.URLCons;
import com.rekoo.libs.utils.MacAddressUtil;
import java.util.HashMap;
import java.util.Map;

public class BIConfig {
    private static BIConfig config = null;
    public Map<String, String> biParames = null;
    private final boolean isMobAgent = true;

    private BIConfig() {
    }

    public static BIConfig getBiConfig() {
        if (config == null) {
            synchronized (BIConfig.class) {
                if (config == null) {
                    config = new BIConfig();
                }
            }
        }
        return config;
    }

    public void BISend(Context context, String eventId, Map<String, String> actionMessage) {
        this.biParames = getBIParames(context);
        this.biParames.putAll(actionMessage);
    }

    public String getDeviceId(Context context) {
        if (VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(context, "android.permission.READ_PHONE_STATE") != 0) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{"android.permission.READ_PHONE_STATE"}, 3081);
        }
        try {
            return ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String getMobileBrand() {
        return Build.BRAND;
    }

    public String getOSVersion() {
        return VERSION.RELEASE;
    }

    public Map<String, String> getBIParames(Context context) {
        Map<String, String> biParam = new HashMap();
        biParam.put(Cons.DEVICE_MODEL, URLCons.getDeviceModel());
        biParam.put(Cons.DEVICE_ID, getDeviceId(context));
        biParam.put(Cons.DEVICE_MAC, MacAddressUtil.getMacAddress(context));
        biParam.put(Cons.DEVICE_BRAND, getMobileBrand());
        biParam.put(Cons.DEVICE_VERSION, getOSVersion());
        return biParam;
    }

    public void loginFailUninstalReinstall(Context context) {
        this.biParames = getBIParames(context);
        this.biParames.put(Cons.LOGINFAIL_UNINSTALREINSTALL, Cons.SUCCESS);
    }

    public void loginFailOldAccount(Context context) {
        this.biParames = getBIParames(context);
        this.biParames.put(Cons.LOGINFAIL_OLDACCOUNT, Cons.SUCCESS);
    }

    public void loginFailNewAccount(Context context) {
        this.biParames = getBIParames(context);
        this.biParames.put(Cons.LOGINFAIL_NEWACCOUNT, Cons.SUCCESS);
    }

    public void applyNewAccountFail(Context context) {
        this.biParames = getBIParames(context);
        this.biParames.put(Cons.APPLYNEWACCOUNT_FAIL, Cons.SUCCESS);
    }

    public void initFail(Context context) {
        this.biParames = getBIParames(context);
        this.biParames.put(Cons.INIT_FAIL, Cons.SUCCESS);
    }

    public void gamesForumClick(Context context) {
        this.biParames = getBIParames(context);
        this.biParames.put(Cons.GAMESFORUM_CLICK, Cons.SUCCESS);
    }

    public void backlistShow(Context context) {
        this.biParames = getBIParames(context);
        this.biParames.put(Cons.BACKLIST_SHOW, Cons.SUCCESS);
    }

    public void feedbackInterfaceAbandon(Context context) {
        this.biParames = getBIParames(context);
        this.biParames.put(Cons.FEEDBACKINTERFACE_ABANDON, Cons.SUCCESS);
    }

    public void feedbackInterfaceSubmit(Context context) {
        this.biParames = getBIParames(context);
        this.biParames.put(Cons.FEEDBACKINTERFACE_SUBMIT, Cons.SUCCESS);
    }

    public void feedbackInterfaceSubmitSuccess(Context context) {
        this.biParames = getBIParames(context);
        this.biParames.put(Cons.FEEDBACKINTERFACE_SUBMIT_SUCCESS, Cons.SUCCESS);
    }

    public void feedbackInterfaceShow(Context context) {
        this.biParames = getBIParames(context);
        this.biParames.put(Cons.FEEDBACKINTERFACE_SHOW, Cons.SUCCESS);
    }

    public void dropzoneClick(Context context) {
        this.biParames = getBIParames(context);
        this.biParames.put(Cons.DROPZONE_CLICK, Cons.SUCCESS);
    }

    public void loginSuccessUninstalReinstall(Context context) {
        this.biParames = getBIParames(context);
        this.biParames.put(Cons.LOGINSUCCESS_UNINSTALREINSTALL, Cons.SUCCESS);
    }

    public void loginSuccessOldaAccount(Context context) {
        this.biParames = getBIParames(context);
        this.biParames.put(Cons.LOGINSUCCESS_OLDACCOUNT, Cons.SUCCESS);
    }

    public void loginSuccessNewAccount(Context context) {
        this.biParames = getBIParames(context);
        this.biParames.put(Cons.LOGINSUCCESS_NEWACCOUNT, Cons.SUCCESS);
    }

    public void initSuccess(Context context) {
        this.biParames = getBIParames(context);
        this.biParames.put(Cons.INIT_SUCCESS, Cons.SUCCESS);
    }

    public void openPushMessageCustom(Context context) {
        this.biParames = getBIParames(context);
        this.biParames.put(Cons.OPENPUSHMESSAGE_CUSTOM, Cons.SUCCESS);
    }

    public void openPushMessageGame(Context context) {
        this.biParames = getBIParames(context);
        this.biParames.put(Cons.OPENPUSHMESSAGE_GAME, Cons.SUCCESS);
    }

    public void pushAccessClose(Context context) {
        this.biParames = getBIParames(context);
        this.biParames.put(Cons.PUSHACCESS_CLOSE, Cons.SUCCESS);
    }

    public void pushAccessOpen(Context context) {
        this.biParames = getBIParames(context);
        this.biParames.put(Cons.PUSHACCESS_OPEN, Cons.SUCCESS);
    }

    public void getPushTokenFail(Context context) {
        this.biParames = getBIParames(context);
        this.biParames.put(Cons.GETPUSHTOKEN_FAIL, Cons.SUCCESS);
    }

    public void getPushTokenSuccess(Context context) {
        this.biParames = getBIParames(context);
        this.biParames.put(Cons.GETPUSHTOKEN_SUCCESS, Cons.SUCCESS);
    }

    public void getLogoutSuccess(Context context) {
        this.biParames = getBIParames(context);
        this.biParames.put(Cons.LOGOUT_SUCCESS, Cons.SUCCESS);
    }

    public void getAccountUninstalreinatall(Context context) {
        this.biParames = getBIParames(context);
        this.biParames.put(Cons.ACCOUNT_UNINSTALREINSTALL, Cons.SUCCESS);
    }

    public void getFeedbackSubmitSuccess(Context context) {
        this.biParames = getBIParames(context);
        this.biParames.put(Cons.FEEDBACKINTERFACE_SUBMIT_SUCCESS, Cons.SUCCESS);
    }

    public void getFeedbackSubmitFail(Context context) {
        this.biParames = getBIParames(context);
        this.biParames.put(Cons.FEEDBACKINTERFACE_SUBMIT_FAIL, Cons.SUCCESS);
    }

    public void init(Context context) {
        this.biParames = getBIParames(context);
        this.biParames.put(Cons.INIT, Cons.SUCCESS);
    }

    public void logout(Context context) {
        this.biParames = getBIParames(context);
        this.biParames.put(Cons.LOGOUT_SUCCESS, Cons.SUCCESS);
    }
}
