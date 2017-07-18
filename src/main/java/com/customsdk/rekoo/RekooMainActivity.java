package com.customsdk.rekoo;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Process;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import com.customsdk.rekoo.util.IabHelper;
import com.customsdk.rekoo.util.IabHelper.OnConsumeFinishedListener;
import com.customsdk.rekoo.util.IabHelper.OnIabPurchaseFinishedListener;
import com.customsdk.rekoo.util.IabHelper.OnIabSetupFinishedListener;
import com.customsdk.rekoo.util.IabHelper.QueryInventoryFinishedListener;
import com.customsdk.rekoo.util.IabResult;
import com.customsdk.rekoo.util.Inventory;
import com.customsdk.rekoo.util.Purchase;
import com.demo.yunva.MainActivity;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.rekoo.libs.callback.ProduceTransformCallback;
import com.rekoo.libs.callback.RKLibInitCallback;
import com.rekoo.libs.callback.RKLoginCallback;
import com.rekoo.libs.callback.RKLogoutCallback;
import com.rekoo.libs.callback.TransformCallback;
import com.rekoo.libs.entity.ProduceTransform;
import com.rekoo.libs.entity.RKUser;
import com.rekoo.libs.entity.Transform;
import com.rekoo.libs.net.URLCons;
import com.rekoo.libs.platform.RKPlatformManager;
import com.rekoo.libs.utils.AdvertisingIdClient;
import com.unity3d.player.UnityPlayer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class RekooMainActivity extends MainActivity {
    private static final String INIT_CALLBACK_MESSAGE = "OnPlatformSdk_Init";
    private static final String LOGIN_CALLBACK_MESSAGE = "OnPlatformSdk_UserLogin";
    private static final String LOGOUT_CALLBACK_MESSAGE = "OnPlatformSdk_UserLogout";
    static final int RC_REQUEST = 10001;
    private static final String TAG = "REKOO_SDK";
    private static final String TRANSLATE_CALLBACK_MESSAGE = "OnPlatformSdk_AccountTranslateResult";
    private static final String TRANSLATE_CODE_CALLBACK_MESSAGE = "OnPlatformSdk_GotAccountTranslateCode";
    private static final String UNITY_OBJECT_NAME = "ICustomSdk";
    private static final String fileAddressMac = "/sys/class/net/wlan0/address";
    private static final String marshmallowMacAddress = "02:00:00:00:00:00";
    private List<Purchase> consumeList = new ArrayList();
    private String mAndroidAdvertiseID = null;
    private String mAndroidID = null;
    OnConsumeFinishedListener mConsumeFinishedListener = new OnConsumeFinishedListener() {
        public void onConsumeFinished(Purchase purchase, IabResult result) {
            Log.d(RekooMainActivity.TAG, "Consumption finished. Purchase: " + purchase + ", result: " + result);
            if (RekooMainActivity.this.mHelper != null) {
                if (result.isSuccess()) {
                    Log.d(RekooMainActivity.TAG, "queryPurchases consume count: " + RekooMainActivity.this.queryPurchases.size());
                    RekooMainActivity.this.queryPurchases.remove(purchase);
                    Log.d(RekooMainActivity.TAG, "queryPurchases consume after count: " + RekooMainActivity.this.queryPurchases.size());
                    if (RekooMainActivity.this.consumeList.size() > 0) {
                        RekooMainActivity.this.mHelper.consumeAsync((Purchase) RekooMainActivity.this.consumeList.get(0), RekooMainActivity.this.mConsumeFinishedListener);
                        RekooMainActivity.this.consumeList.remove(0);
                        return;
                    }
                    return;
                }
                Log.d(RekooMainActivity.TAG, "Error while consuming: " + result);
            }
        }
    };
    private Context mContext;
    QueryInventoryFinishedListener mGotInventoryListener = new QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            Log.d(RekooMainActivity.TAG, "Query inventory finished.");
            if (RekooMainActivity.this.mHelper != null) {
                if (result.isFailure()) {
                    Log.d(RekooMainActivity.TAG, "Failed to query inventory: " + result);
                    return;
                }
                RekooMainActivity.this.queryPurchases.clear();
                List<Purchase> purs = inventory.getAllPurchases();
                Log.d(RekooMainActivity.TAG, "purchase size: " + purs.size());
                for (int i = 0; i < purs.size(); i++) {
                    RekooMainActivity.this.queryPurchases.add((Purchase) purs.get(i));
                }
                Log.d(RekooMainActivity.TAG, "query purchase size: " + RekooMainActivity.this.queryPurchases.size());
                Log.d(RekooMainActivity.TAG, "Query inventory was successful.");
            }
        }
    };
    IabHelper mHelper;
    OnIabPurchaseFinishedListener mPurchaseFinishedListener = new OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            Log.d(RekooMainActivity.TAG, "Purchase finished: " + result + ", purchase: " + purchase);
            if (RekooMainActivity.this.mHelper != null) {
                if (result.isFailure()) {
                    Log.d(RekooMainActivity.TAG, "Error purchasing: " + result);
                } else if (RekooMainActivity.this.verifyDeveloperPayload(purchase)) {
                    String purchase_data = new String(Base64.encode(purchase.getOriginalJson().getBytes(), 0));
                    Map<String, String> unitymsg = new HashMap();
                    purchase.getOrderId();
                    unitymsg.put("token", purchase_data);
                    unitymsg.put(URLCons.SIGN, purchase.getSignature());
                    unitymsg.put("sku", purchase.getSku());
                    Log.d(RekooMainActivity.TAG, "token: " + purchase_data);
                    Log.d(RekooMainActivity.TAG, "sign: " + purchase.getSignature());
                    RekooMainActivity.sendU3DMessage("OnPlatformSdk_GooglePayFinish", unitymsg);
                    RekooMainActivity.this.queryPurchases.add(purchase);
                    Log.d(RekooMainActivity.TAG, "Purchase successful.");
                } else {
                    Log.d(RekooMainActivity.TAG, "Error purchasing. Authenticity verification failed.");
                }
            }
        }
    };
    private RKUser mRkUser;
    private String mToken;
    private String mUid;
    private List<Purchase> queryPurchases = new ArrayList();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        this.mAndroidID = Secure.getString(getContentResolver(), "android_id");
        new AsyncTask<Void, Void, String>() {
            protected String doInBackground(Void... params) {
                try {
                    return AdvertisingIdClient.getAdvertisingIdInfo(RekooMainActivity.this.getApplicationContext()).getId();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            protected void onPostExecute(String advertId) {
                RekooMainActivity.this.mAndroidAdvertiseID = advertId;
            }
        }.execute(new Void[0]);
        String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzPnYbW0D/8e7qNtxV1iLCA3x9XhzFXDs3nJOJzbLO84E7qgM8HhX1eGVyKxBafdvEsFoUvhENNqfrEYVTgpDg/MaxieDMy7E5PEmQKEcC5YOcr9Js5Ji1ndkoEvF/uFzVar9P2BQZ+hThV1JK7lJ833Av8MlM9IqFMdycgCNlrh4nhHCfPDAqXf5uTOTTIkhlzCrurhBHOZebuNfmULLURvcznx7/0nyaopdOmLLE2nI0oI29Knzf89uRlLKWkRELjiupf3QAmHMLtvrk/54imahP6r/jL1fKEsubkHM54I4Q7A72oukh+llcUldKYqYvL7VaNs0G5ytV50QBXGgeQIDAQAB";
        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(this) == 0) {
            Log.d(TAG, "Creating IAB helper.");
            this.mHelper = new IabHelper(this, base64EncodedPublicKey);
            this.mHelper.enableDebugLogging(true);
            Log.d(TAG, "Starting setup.");
            this.mHelper.startSetup(new OnIabSetupFinishedListener() {
                public void onIabSetupFinished(IabResult result) {
                    Log.d(RekooMainActivity.TAG, "Setup finished.");
                    if (!result.isSuccess()) {
                        Log.e(RekooMainActivity.TAG, "Problem setting up in-app billing: " + result);
                    } else if (RekooMainActivity.this.mHelper != null) {
                        Log.d(RekooMainActivity.TAG, "Setup successful. Querying inventory.");
                        RekooMainActivity.this.mHelper.queryInventoryAsync(RekooMainActivity.this.mGotInventoryListener);
                    }
                }
            });
        }
    }

    public void googlePay(String productID) {
        Log.d(TAG, "GooglePay " + productID);
        String str = productID;
        this.mHelper.launchPurchaseFlow(this, str, RC_REQUEST, this.mPurchaseFinishedListener, "");
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);
        if (this.mHelper != null) {
            if (this.mHelper.handleActivityResult(requestCode, resultCode, data)) {
                Log.d(TAG, "onActivityResult handled by IABUtil.");
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    void ConsumePurchase(String signature) {
        Log.d(TAG, "try consume queryPurchases: " + this.queryPurchases.size());
        Log.d(TAG, "try consume signature: " + signature);
        for (int i = 0; i < this.queryPurchases.size(); i++) {
            Purchase purchase = (Purchase) this.queryPurchases.get(i);
            Log.d(TAG, "exist purchase token: " + purchase.getToken());
            Log.d(TAG, "exist purchase sig: " + purchase.getSignature());
            if (purchase.getSignature().equals(signature)) {
                Log.d(TAG, "consume sig: " + signature);
                if (this.mHelper.getAsyncInProgress().booleanValue()) {
                    this.consumeList.add(purchase);
                } else {
                    this.mHelper.consumeAsync(purchase, this.mConsumeFinishedListener);
                }
            }
        }
    }

    boolean verifyDeveloperPayload(Purchase p) {
        String payload = p.getDeveloperPayload();
        return true;
    }

    public void Init() {
        Log.w(TAG, "INIT START! ");
        RKPlatformManager.getManager().rkInit(this, new RKLibInitCallback() {
            public void onInitSuccess() {
                Log.w(RekooMainActivity.TAG, "INIT Successed! ");
                UnityPlayer.UnitySendMessage(RekooMainActivity.UNITY_OBJECT_NAME, RekooMainActivity.INIT_CALLBACK_MESSAGE, "");
            }

            public void onInitFailed(String msg) {
                Log.e(RekooMainActivity.TAG, "INIT FAILED! " + msg);
                UnityPlayer.UnitySendMessage(RekooMainActivity.UNITY_OBJECT_NAME, RekooMainActivity.INIT_CALLBACK_MESSAGE, msg);
            }
        });
    }

    public void Login() {
        Log.w(TAG, "LOGIN START! ");
        RKPlatformManager.getManager().rkLogin(this.mContext, new RKLoginCallback() {
            public void onSuccess(RKUser user) {
                Log.w(RekooMainActivity.TAG, "LOGIN SUCCESS! ");
                RekooMainActivity.this.mRkUser = user;
                RekooMainActivity.this.mUid = user.getUid();
                RekooMainActivity.this.mToken = user.getToken();
                Map<String, String> msg = new HashMap();
                msg.put("uid", RekooMainActivity.this.mUid);
                msg.put("token", RekooMainActivity.this.mToken);
                RekooMainActivity.sendU3DMessage(RekooMainActivity.LOGIN_CALLBACK_MESSAGE, msg);
            }

            public void onFail(String failMsg) {
                Log.w(RekooMainActivity.TAG, "LOGIN FAILED! " + failMsg);
                RekooMainActivity.sendU3DMessage(RekooMainActivity.LOGIN_CALLBACK_MESSAGE, new HashMap());
            }

            public void onCancel() {
                Log.w(RekooMainActivity.TAG, "LOGIN CANCEL!");
                RekooMainActivity.sendU3DMessage(RekooMainActivity.LOGIN_CALLBACK_MESSAGE, new HashMap());
            }
        });
    }

    public void Logout() {
        RKPlatformManager.getManager().rkLogout(this.mContext, new RKLogoutCallback() {
            public void onSuccess(String msg) {
                UnityPlayer.UnitySendMessage(RekooMainActivity.UNITY_OBJECT_NAME, RekooMainActivity.LOGOUT_CALLBACK_MESSAGE, "");
            }

            public void onFail(String failMsg) {
            }
        });
    }

    public void GetTransform() {
        RKPlatformManager.getManager().rkGetTransform(this, new ProduceTransformCallback() {
            public void onSuccess(ProduceTransform ptf) {
                String transid = ptf.getRktransid();
                String uid = ptf.getRkuid();
                String lasttime = ptf.getLasttime();
                Map<String, String> msg = new HashMap();
                msg.put("uid", uid);
                msg.put(URLCons.TRANSID, transid);
                msg.put("lasttime", lasttime);
                RekooMainActivity.sendU3DMessage(RekooMainActivity.TRANSLATE_CODE_CALLBACK_MESSAGE, msg);
            }

            public void onFailed() {
                UnityPlayer.UnitySendMessage(RekooMainActivity.UNITY_OBJECT_NAME, RekooMainActivity.TRANSLATE_CODE_CALLBACK_MESSAGE, "");
            }
        });
    }

    public void SetTransform(String trans) {
        RKPlatformManager.getManager().rkSetTransform(this, trans, new TransformCallback() {
            public void onSuccess(Transform transform) {
                String transid = transform.getRkTrans();
                String newuid = transform.getNewUid();
                String olduid = transform.getOldUid();
                Map<String, String> msg = new HashMap();
                msg.put("newuid", newuid);
                msg.put(URLCons.TRANSID, transid);
                msg.put("olduid", olduid);
                RekooMainActivity.sendU3DMessage(RekooMainActivity.TRANSLATE_CALLBACK_MESSAGE, msg);
            }

            public void onFail(int failed) {
                UnityPlayer.UnitySendMessage(RekooMainActivity.UNITY_OBJECT_NAME, RekooMainActivity.TRANSLATE_CALLBACK_MESSAGE, "");
            }
        });
    }

    public void ConsumeAllQuery() {
        Log.d(TAG, "consume query purchase size: " + this.queryPurchases.size());
        for (int i = 0; i < this.queryPurchases.size(); i++) {
            Purchase pur = (Purchase) this.queryPurchases.get(i);
            Log.d(TAG, "consume query purchase sku: " + pur.getSku());
            if (verifyDeveloperPayload(pur)) {
                String purchase_data = new String(Base64.encode(pur.getOriginalJson().getBytes(), 0));
                Log.d(TAG, "Consumption successful. Provisioning.");
                Map<String, String> unitymsg = new HashMap();
                pur.getOrderId();
                unitymsg.put("token", purchase_data);
                unitymsg.put(URLCons.SIGN, pur.getSignature());
                unitymsg.put("sku", pur.getSku());
                Log.d(TAG, "token: " + purchase_data);
                Log.d(TAG, "sign: " + pur.getSignature());
                sendU3DMessage("OnPlatformSdk_GooglePayFinish", unitymsg);
            }
        }
    }

    public void CloseFloat() {
        RKPlatformManager.getManager().rkShowCenter(this, false);
    }

    public void OpenFloat() {
        RKPlatformManager.getManager().rkShowCenter(this, true);
    }

    public void CopyToClipboard(final String str) {
        runOnUiThread(new Runnable() {
            public void run() {
                ((ClipboardManager) RekooMainActivity.this.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText(URLCons.TRANSID, str));
            }
        });
    }

    protected void onDestroy() {
        super.onDestroy();
        RKPlatformManager.getManager().rkOnDestroy(this);
        finish();
        System.exit(0);
        Process.killProcess(0);
    }

    protected void onPause() {
        super.onPause();
        RKPlatformManager.getManager().rkOnPause(this);
    }

    protected void onResume() {
        super.onResume();
        RKPlatformManager.getManager().rkOnResume(this);
    }

    private static void sendU3DMessage(String methodName, Map<String, String> hashMap) {
        String param = "";
        if (hashMap != null) {
            for (Entry<String, String> entry : hashMap.entrySet()) {
                String key = (String) entry.getKey();
                String val = (String) entry.getValue();
                if (param.length() == 0) {
                    param = new StringBuilder(String.valueOf(param)).append(String.format("%s{%s}", new Object[]{key, val})).toString();
                } else {
                    param = new StringBuilder(String.valueOf(param)).append(String.format("&%s{%s}", new Object[]{key, val})).toString();
                }
            }
        }
        UnityPlayer.UnitySendMessage(UNITY_OBJECT_NAME, methodName, param);
    }

    public String GetAndroidID() {
        if (this.mAndroidID != null) {
            return this.mAndroidID;
        }
        return "";
    }

    public String GetAndroidAdvID() {
        if (this.mAndroidAdvertiseID != null) {
            return this.mAndroidAdvertiseID;
        }
        return "";
    }

    public String GetModel() {
        try {
            return Build.MODEL;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String GetVersion() {
        try {
            return VERSION.RELEASE;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String GetImei() {
        return "";
    }

    public String recupAdresseMAC() {
        try {
            WifiManager wifiMan = (WifiManager) getSystemService("wifi");
            WifiInfo wifiInf = wifiMan.getConnectionInfo();
            if (!wifiInf.getMacAddress().equals(marshmallowMacAddress)) {
                return wifiInf.getMacAddress();
            }
            try {
                String ret = getAdressMacByInterface();
                if (ret != null) {
                    return ret;
                }
                return getAddressMacByFile(wifiMan);
            } catch (IOException e) {
                Log.e("MobileAccess", "Erreur lecture propriete Adresse MAC1");
                return marshmallowMacAddress;
            } catch (Exception e2) {
                Log.e("MobileAcces", "Erreur lecture propriete Adresse MAC2 ");
                return marshmallowMacAddress;
            }
        } catch (Exception e3) {
            Log.e("MobileAcces", "Erreur lecture propriete Adresse MAC 3");
            return marshmallowMacAddress;
        }
    }

    private static String getAdressMacByInterface() {
        try {
            for (NetworkInterface nif : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if (nif.getName().equalsIgnoreCase("wlan0")) {
                    byte[] macBytes = nif.getHardwareAddress();
                    if (macBytes == null) {
                        return "";
                    }
                    StringBuilder res1 = new StringBuilder();
                    int length = macBytes.length;
                    for (int i = 0; i < length; i++) {
                        res1.append(String.format("%02X:", new Object[]{Byte.valueOf(macBytes[i])}));
                    }
                    if (res1.length() > 0) {
                        res1.deleteCharAt(res1.length() - 1);
                    }
                    return res1.toString();
                }
            }
        } catch (Exception e) {
            Log.e("MobileAcces", "Erreur lecture propriete Adresse MAC4 ");
        }
        return null;
    }

    private static String getAddressMacByFile(WifiManager wifiMan) throws Exception {
        boolean enabled = true;
        int wifiState = wifiMan.getWifiState();
        wifiMan.setWifiEnabled(true);
        FileInputStream fin = new FileInputStream(new File(fileAddressMac));
        String ret = crunchifyGetStringFromStream(fin);
        fin.close();
        if (3 != wifiState) {
            enabled = false;
        }
        wifiMan.setWifiEnabled(enabled);
        return ret;
    }

    private static String crunchifyGetStringFromStream(InputStream crunchifyStream) throws IOException {
        if (crunchifyStream == null) {
            return "No Contents";
        }
        Writer crunchifyWriter = new StringWriter();
        char[] crunchifyBuffer = new char[2048];
        try {
            Reader crunchifyReader = new BufferedReader(new InputStreamReader(crunchifyStream, "UTF-8"));
            while (true) {
                int counter = crunchifyReader.read(crunchifyBuffer);
                if (counter == -1) {
                    break;
                }
                crunchifyWriter.write(crunchifyBuffer, 0, counter);
            }
            return crunchifyWriter.toString();
        } finally {
            crunchifyStream.close();
        }
    }

    public String GetAndroidMac() {
        try {
            return recupAdresseMAC();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String GetLocalIp() {
        try {
            for (NetworkInterface intf : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                for (InetAddress addr : Collections.list(intf.getInetAddresses())) {
                    if (!addr.isLoopbackAddress()) {
                        return addr.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
        }
        return "";
    }

    public String GetCarrier() {
        try {
            return ((TelephonyManager) getSystemService("phone")).getSimOperatorName();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String GetNetworkType() {
        String strNetworkType = "";
        try {
            NetworkInfo networkInfo = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
            if (networkInfo == null || !networkInfo.isConnected()) {
                return strNetworkType;
            }
            if (networkInfo.getType() == 1) {
                return "WIFI";
            }
            if (networkInfo.getType() != 0) {
                return strNetworkType;
            }
            String _strSubTypeName = networkInfo.getSubtypeName();
            Log.e("cocos2d-x", "Network getSubtypeName : " + _strSubTypeName);
            switch (networkInfo.getSubtype()) {
                case 1:
                case 2:
                case 4:
                case 7:
                case 11:
                    return "2G";
                case 3:
                case 5:
                case 6:
                case 8:
                case 9:
                case 10:
                case 12:
                case 14:
                case 15:
                    return "3G";
                case 13:
                    return "4G";
                default:
                    if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") || _strSubTypeName.equalsIgnoreCase("WCDMA") || _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                        return "3G";
                    }
                    return _strSubTypeName;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return strNetworkType;
        }
    }
}
