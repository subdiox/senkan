package com.kayac.lobi.sdk;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.text.TextUtils;
import com.kayac.lobi.libnakamap.components.PathRouter;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastore;
import com.kayac.lobi.libnakamap.exception.NakamapException.CurrentUserNotSet;
import com.kayac.lobi.libnakamap.net.APIDef.GetSdkReport.RequestKey;
import com.kayac.lobi.libnakamap.net.APIRes.GetSdkReport;
import com.kayac.lobi.libnakamap.net.APIUtil;
import com.kayac.lobi.libnakamap.net.APIUtil.Thanks;
import com.kayac.lobi.libnakamap.net.CoreAPI;
import com.kayac.lobi.libnakamap.net.CoreAPI.DefaultAPICallback;
import com.kayac.lobi.libnakamap.net.CoreAPI.TokenChecker;
import com.kayac.lobi.libnakamap.utils.Log;
import com.kayac.lobi.libnakamap.utils.ModuleUtil;
import com.kayac.lobi.libnakamap.utils.NakamapSDKDatastore;
import com.kayac.lobi.libnakamap.utils.NakamapSDKDatastore.Key;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.sdk.activity.RootActivity;
import com.kayac.lobi.sdk.activity.ad.AdRecommendActivity;
import com.kayac.lobi.sdk.activity.profile.ProfileActivity;
import com.kayac.lobi.sdk.auth.AccountUtil;
import com.kayac.lobi.sdk.migration.datastore.UserMigrationHelper;
import com.kayac.lobi.sdk.net.NakamapApi;
import com.kayac.lobi.sdk.path.PathRouterConfig;
import com.kayac.lobi.sdk.receiver.AppInstallReceiver;
import com.kayac.lobi.sdk.service.AppInstallChecker;
import com.kayac.lobi.sdk.utils.ManifestMetaDataUtils;
import com.kayac.lobi.sdk.utils.ManifestUtil;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LobiCore {
    private static String sPreparedExid;
    private static String sPreparedIv;
    private static LobiCore sSharedInstance;
    private static boolean sStrictExidStatus = false;
    private static String sTmpClientId;
    private static final TokenChecker sTokenChecker = new TokenChecker() {
        private final Object mLock = new Object();

        public void checkToken() {
            synchronized (this.mLock) {
                Log.v("refreshToken", "start");
                if (AccountUtil.shouldRefreshToken()) {
                    AccountUtil.refreshToken();
                }
                Log.v("refreshToken", "end");
            }
        }
    };
    private String mClientId;
    private Context mContext;
    private String mNewAccountBaseName;

    private LobiCore(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public static synchronized LobiCore sharedInstance() {
        LobiCore lobiCore;
        synchronized (LobiCore.class) {
            assertSetup();
            lobiCore = sSharedInstance;
        }
        return lobiCore;
    }

    public static final void presentProfile() {
        Bundle extras = new Bundle();
        extras.putBoolean("EXTRA_FROM_MENU", true);
        RootActivity.startActivity(ProfileActivity.PATH_PROFILE, extras);
    }

    public static final void presentAdWall() {
        RootActivity.startActivity(AdRecommendActivity.PATH_AD_RECOMMEND, new Bundle());
    }

    public synchronized String getNewAccountBaseName() {
        return this.mNewAccountBaseName;
    }

    public synchronized void setNewAccountBaseName(String newAccountBaseName) {
        assertSetup();
        this.mNewAccountBaseName = newAccountBaseName;
    }

    public static synchronized void setup(Context context, String clientId, String newAccountBaseName) {
        synchronized (LobiCore.class) {
            context = context.getApplicationContext();
            if (sSharedInstance == null) {
                TransactionDatastore.init(context);
                AccountDatastore.init(context);
                UserMigrationHelper.init(context);
                CoreAPI.setEndpoint(new Thanks());
                CoreAPI.setTokenChecker(sTokenChecker);
                APIUtil.init(context);
                NakamapApi.init();
                APIUtil.httpClientFactory(CoreAPI.getEndpoint());
                PathRouter.init(context, PathRouterConfig.getConfig());
                ManifestUtil.checkNativeApp(context);
                LobiCore lobiCore = new LobiCore(context);
                ModuleUtil.registerModulePaths();
                TransactionDatastore.setValue(Key.GAME_PROFILE_CALLBACK_ENABLED, Boolean.valueOf(false));
                sSharedInstance = lobiCore;
            }
            String metaClientId = ManifestMetaDataUtils.getString(context, ManifestMetaDataUtils.CLIENT_ID);
            if (clientId != null) {
                setClientId(clientId);
            } else if (sTmpClientId != null) {
                setClientId(sTmpClientId);
                sTmpClientId = null;
            } else if (sSharedInstance.getClientId() == null && metaClientId != null) {
                setClientId(metaClientId);
            }
            String metaAccountBaseName = ManifestMetaDataUtils.getString(context, ManifestMetaDataUtils.ACCOUNT_BASE_NAME);
            if (newAccountBaseName != null) {
                sSharedInstance.setNewAccountBaseName(newAccountBaseName);
            } else if (sSharedInstance.getNewAccountBaseName() == null && metaAccountBaseName != null) {
                sSharedInstance.setNewAccountBaseName(metaAccountBaseName);
            }
            sendReport(context, clientId);
            if (AppInstallReceiver.isWaitingInstallation()) {
                doInstallCheck(context);
            }
            boolean hasOldUserAccount = UserMigrationHelper.hasOldUserAccount();
            Log.v("nakamap-sdk", "hasOldUserAccount: " + hasOldUserAccount);
            if (hasOldUserAccount) {
                UserMigrationHelper.migrateDatabase();
            }
        }
    }

    public static synchronized void setup(Context context, String clientId) {
        synchronized (LobiCore.class) {
            setup(context, clientId, null);
        }
    }

    public static synchronized void setup(Context context) {
        synchronized (LobiCore.class) {
            setup(context, null, null);
        }
    }

    public static boolean isSetup() {
        return sSharedInstance != null;
    }

    public static void assertSetup() {
        if (!isSetup()) {
            throw new RuntimeException("Lobi SDK has not been setup. Call LobiCore.setup(Context context) before using any Lobi SDK methods.");
        }
    }

    private static void doInstallCheck(Context context) {
        android.util.Log.v("lobi-sdk", "[inline] doInstallCheck");
        PendingIntent service = PendingIntent.getService(context, 0, new Intent(context, AppInstallChecker.class).setAction(AppInstallChecker.INSTALL_CHECK), 134217728);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService("alarm");
        long triggerAtMillis = ((long) ((Math.random() * 19.0d) + 1.0d)) * 60000;
        android.util.Log.v("lobi-sdk", "[inline] triggerAt: " + triggerAtMillis);
        alarmManager.set(0, System.currentTimeMillis() + triggerAtMillis, service);
    }

    public static void setClientId(String clientId) {
        if (clientId != null) {
            if (!isSetup()) {
                sTmpClientId = clientId;
            } else if (!clientId.equals(sSharedInstance.mClientId)) {
                if (sSharedInstance.mClientId == null) {
                    ManifestUtil.checkIntentFilter(sSharedInstance.mContext, clientId);
                } else {
                    debugReset();
                }
                sSharedInstance.mClientId = clientId;
            }
        }
    }

    public String getClientId() {
        return this.mClientId;
    }

    public static final boolean isSignedIn() {
        assertSetup();
        try {
            UserValue currentUser = AccountDatastore.getCurrentUser();
            return (currentUser == null || currentUser.getToken() == null) ? false : true;
        } catch (CurrentUserNotSet e) {
            return false;
        }
    }

    public static final void debugReset() {
        AccountDatastore.deleteAll();
        TransactionDatastore.deleteAll();
    }

    public static final void userReset() {
        assertSetup();
        String installId = (String) AccountDatastore.getValue("installId", "");
        AccountDatastore.deleteAll();
        TransactionDatastore.deleteAll();
        if (!TextUtils.isEmpty(installId)) {
            AccountDatastore.setValue("installId", installId);
        }
    }

    public static final String getInstallId() {
        assertSetup();
        return (String) AccountDatastore.getValue("installId", "");
    }

    public Context getContext() {
        assertSetup();
        return this.mContext;
    }

    private static void sendReport(Context context, String clientId) {
        Boolean reportSent = (Boolean) AccountDatastore.getValue("isReportSent");
        if (reportSent == null || !reportSent.booleanValue()) {
            String applicationName = (String) context.getPackageManager().getApplicationLabel(context.getApplicationInfo());
            Map<String, String> params = new HashMap();
            params.put(RequestKey.OS, "android");
            params.put("lang", Locale.getDefault().getLanguage());
            params.put("client_id", clientId);
            params.put(RequestKey.BUNDLE_ID, context.getPackageName());
            params.put("name", applicationName);
            CoreAPI.getSdkReport(params, new DefaultAPICallback<GetSdkReport>(null) {
                public void onResponse(GetSdkReport t) {
                    if (t.success) {
                        AccountDatastore.setValue("isReportSent", Boolean.valueOf(true));
                    }
                }

                public void onError(int statusCode, String responseBody) {
                    Log.e("nakamap-sdk", String.format("error : %d, %s", new Object[]{Integer.valueOf(statusCode), responseBody}));
                }

                public void onError(Throwable e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public static final void clear() {
        assertSetup();
        String installId = NakamapSDKDatastore.getInstallId();
        TransactionDatastore.deleteAll();
        AccountDatastore.deleteAll();
        AccountDatastore.setValue("installId", installId);
    }

    public String hostApplicationVersionName() {
        assertSetup();
        String versionName = null;
        try {
            return this.mContext.getPackageManager().getPackageInfo(this.mContext.getPackageName(), 1).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return versionName;
        }
    }

    public static final void prepareExternalId(String encryptedExternalId, String iv, String accountBaseName) {
        assertSetup();
        if (!(TextUtils.isEmpty(encryptedExternalId) || TextUtils.isEmpty(iv))) {
            sPreparedExid = encryptedExternalId;
            sPreparedIv = iv;
        }
        sharedInstance().setNewAccountBaseName(accountBaseName);
        enableStrictExid();
    }

    public static String getPreparedExternalId() {
        return sPreparedExid;
    }

    public static String getPreparedIv() {
        return sPreparedIv;
    }

    public static void enableStrictExid() {
        sStrictExidStatus = true;
    }

    public static boolean isStrictExidEnabled() {
        return sStrictExidStatus;
    }

    public static boolean hasExidUser() {
        String encryptedExid = (String) AccountDatastore.getValue(Key.ENCRYPTED_EX_ID);
        if (AccountDatastore.optCurrentUser() == null || encryptedExid == null) {
            return false;
        }
        return true;
    }

    public static void bindToLobiAccount() {
        AccountUtil.bindToLobiAccount(12);
    }
}
