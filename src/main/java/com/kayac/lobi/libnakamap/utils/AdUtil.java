package com.kayac.lobi.libnakamap.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.WebView;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.KKey.AdVersion;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.KKey.Version;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastore;
import com.kayac.lobi.libnakamap.net.APIRes.PostAdTrackingClick;
import com.kayac.lobi.libnakamap.net.APIRes.PostAdTrackingConversion;
import com.kayac.lobi.libnakamap.net.CoreAPI;
import com.kayac.lobi.libnakamap.net.CoreAPI.DefaultAPICallback;
import com.kayac.lobi.libnakamap.utils.DeviceUUID.OnGetUUID;
import com.kayac.lobi.libnakamap.value.AdNewAdValue;
import com.kayac.lobi.libnakamap.value.AdNewAdValue.AdItemList;
import com.kayac.lobi.libnakamap.value.AdNewAdValue.AdItemList.AdItem;
import com.kayac.lobi.libnakamap.value.AdWaitingAppValue;
import com.kayac.lobi.libnakamap.value.AuthorizedAppValue;
import com.kayac.lobi.sdk.activity.ad.AdBaseActivity;
import com.kayac.lobi.sdk.receiver.AppInstallReceiver;
import com.kayac.lobi.sdk.utils.SDKBridge;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AdUtil {
    private static final String JAVASCIRIPT_CALL = "javascript: helper.bridge.call";
    private static final String METHOD_BACK = "back";
    private static final String METHOD_CHECK_INSTALLED = "check_installed";
    private static final String METHOD_CLICKED = "clicked";
    private static final String METHOD_COPY = "copy";
    private static final String METHOD_HIDE_BANNER = "hide_banner";
    public static final String METHOD_HISTORY_BACK = "history:back";
    private static final String METHOD_INSTALL = "install";
    private static final String METHOD_INSTALL_OR_LAUNCH = "install_or_launch";
    private static final String METHOD_OPEN_COUNTDOWN_DETAIL = "open_countdown_detail";
    private static final String METHOD_OPEN_URL = "open_url";
    private static final String METHOD_RESIZE_BANNER = "resize";
    private static final String METHOD_SHARE = "share";
    private static final String METHOD_SHOW_BANNER = "show_banner";
    private static final String METHOD_STATE_CHANGED = "state_changed";
    public static final String SCHEMA = "nakamapbridge";
    private static final String TAG = AdUtil.class.getSimpleName();

    public static String buildJavaScriptUrl(String methodName, Object... args) {
        StringBuilder builder = new StringBuilder(JAVASCIRIPT_CALL);
        builder.append("(\"").append(methodName).append("\"");
        for (Object arg : args) {
            String str = arg.toString();
            if (arg instanceof String) {
                str = "\"" + str + "\"";
            }
            builder.append(",");
            builder.append(str);
        }
        builder.append(")");
        return builder.toString();
    }

    public static boolean isClosed(String urlString) {
        Uri url = Uri.parse(urlString);
        if (SCHEMA.equals(url.getScheme())) {
            if (METHOD_BACK.equals(url.getHost())) {
                return true;
            }
        }
        return false;
    }

    public static boolean shouldOverrideUrlLoading(AdBaseActivity activity, WebView view, String urlString) {
        Log.i(TAG, "0" + urlString);
        Uri url = Uri.parse(urlString);
        if (view.getUrl() != null && SDKBridge.shouldMoveToAnotherActivity(activity, url)) {
            return true;
        }
        if (!SCHEMA.equals(url.getScheme())) {
            return false;
        }
        if (startActivityFromWebView(activity, url)) {
            return true;
        }
        Log.i(TAG, "schema " + urlString);
        String methodName = url.getHost();
        String packageName;
        String adId;
        if (METHOD_CHECK_INSTALLED.equals(methodName)) {
            Log.i(TAG, "ads");
            String jsonArray = url.getQueryParameter("ads");
            Log.i(TAG, "a " + jsonArray);
            JSONArray adList = null;
            try {
                adList = new JSONArray(jsonArray);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.i(TAG, "no");
            }
            if (adList == null) {
                return true;
            }
            Log.i(TAG, "input: " + jsonArray);
            JSONArray adResultList = new JSONArray();
            for (int i = 0; i < adList.length(); i++) {
                JSONObject ad = adList.optJSONObject(i);
                packageName = ad.optString(AuthorizedAppValue.JSON_KEY_PACKAGE);
                adId = ad.optString("ad_id");
                if (!(packageName == null || adId == null)) {
                    boolean isInstalled = isInstalled(packageName);
                    try {
                        JSONObject adResult = new JSONObject();
                        adResult.put("ad_id", adId);
                        adResult.put("is_installed", isInstalled);
                        adResultList.put(adResult);
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }
            }
            Log.i(TAG, "output: " + adResultList);
            Log.i(TAG, "" + buildJavaScriptUrl(methodName, adResultList));
            view.loadUrl(buildJavaScriptUrl(methodName, adResultList));
            return true;
        } else if (METHOD_INSTALL.equals(methodName)) {
            adId = url.getQueryParameter("ad_id");
            packageName = url.getQueryParameter(AuthorizedAppValue.JSON_KEY_PACKAGE);
            linkUrl = url.getQueryParameter(AuthorizedAppValue.JSON_KEY_LINKURL);
            startInstallation(adId, packageName, true);
            Uri targetUrl = Uri.parse(linkUrl);
            if (targetUrl.isRelative()) {
                Uri current = Uri.parse(view.getUrl());
                targetUrl = Uri.parse(current.getScheme() + "://" + current.getHost() + linkUrl + "?token=" + getUserToken());
            }
            ((Activity) view.getContext()).startActivity(new Intent("android.intent.action.VIEW", targetUrl));
            return true;
        } else if (METHOD_INSTALL_OR_LAUNCH.equals(methodName)) {
            packageName = url.getQueryParameter(AuthorizedAppValue.JSON_KEY_PACKAGE);
            linkUrl = url.getQueryParameter(AuthorizedAppValue.JSON_KEY_LINKURL);
            if (isInstalled(packageName)) {
                startApplication(view.getContext(), packageName);
            } else {
                ((Activity) view.getContext()).startActivity(new Intent("android.intent.action.VIEW", Uri.parse(linkUrl)));
            }
            return true;
        } else if (METHOD_CLICKED.equals(methodName)) {
            countUpClick(url.getQueryParameter("ad_id"));
            return true;
        } else if (METHOD_STATE_CHANGED.equals(methodName)) {
            if (activity == null) {
                return true;
            }
            activity.setBackableState(url.getQueryParameter("can_go_back").equals("true"), false);
            return true;
        } else if (METHOD_COPY.equals(methodName)) {
            TextUtil.copyText(activity, url.getQueryParameter("text"));
            return true;
        } else if (!METHOD_OPEN_URL.equals(methodName)) {
            return true;
        } else {
            String openUrl = url.getQueryParameter("url");
            if (openUrl == null) {
                return true;
            }
            activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(openUrl)));
            return true;
        }
    }

    public static void startApplication(Context context, String packageName) {
        try {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.LAUNCHER");
            intent.addFlags(65536);
            for (ResolveInfo info : context.getPackageManager().queryIntentActivities(intent, 0)) {
                if (info.activityInfo.packageName.equalsIgnoreCase(packageName)) {
                    launchComponent(context, info.activityInfo.packageName, info.activityInfo.name);
                    return;
                }
            }
            showInMarket(context, packageName);
        } catch (Exception e) {
            showInMarket(context, packageName);
        }
    }

    private static void launchComponent(Context context, String packageName, String name) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        intent.setComponent(new ComponentName(packageName, name));
        intent.setFlags(268435456);
        context.startActivity(intent);
    }

    private static void showInMarket(Context context, String packageName) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(getGooglePlayUrl(packageName)));
        intent.setFlags(268435456);
        context.startActivity(intent);
    }

    public static void checkNewEntries(AdNewAdValue t) {
        if (t == null) {
            Log.d(TAG, "no new ad value");
            return;
        }
        Log.d(TAG, "check ad new entries");
        boolean hasNewEntry = false;
        Iterator it = t.getEntries().iterator();
        while (it.hasNext()) {
            AdItemList types = (AdItemList) it.next();
            String type = types.getType();
            Log.d(TAG, "ad type: " + type);
            boolean isNew = false;
            Iterator it2 = types.getEntries().iterator();
            while (it2.hasNext()) {
                AdItem entry = (AdItem) it2.next();
                Log.d(TAG, "  ad id: " + entry.getAdId());
                isNew = true;
                String packageName = entry.getPackageName();
                if (TextUtils.isEmpty(packageName) && isInstalled(packageName)) {
                    Log.d(TAG, packageName + " is installed");
                    isNew = false;
                    continue;
                }
                if (isNew) {
                    break;
                }
            }
            if (isNew) {
                String key2 = null;
                if (type.equals(AdItemList.TYPE_RESERVATION)) {
                    key2 = AdVersion.NEW_RESERVATION_AVAILABLE;
                } else if (type.equals(AdItemList.TYPE_RECOMMEND)) {
                    key2 = AdVersion.NEW_GAME_RANKING_AVAILABLE;
                } else if (type.equals(AdItemList.TYPE_PERK)) {
                    key2 = AdVersion.NEW_PERK_AVAILABLE;
                } else if (type.equals(AdItemList.TYPE_PRIZE_GROUPS)) {
                    key2 = AdVersion.NEW_PRIZE_GROUPS_AVAILABLE;
                }
                if (key2 != null) {
                    Log.d(TAG, "new ads are arrived");
                    TransactionDatastore.setKKValue(AdVersion.KEY1, key2, Boolean.valueOf(true));
                    hasNewEntry = true;
                }
            } else {
                Log.d(TAG, "no new ads found");
            }
        }
        if (hasNewEntry) {
            TransactionDatastore.setKKValue(Version.KEY1, Version.NEW_MARK_ON_MENU, Boolean.TRUE);
        }
        TransactionDatastore.setKKValue(AdVersion.KEY1, AdVersion.LAST_FETCHED_AT, Long.valueOf(t.getEpoch()));
    }

    public static void checkAllInstallations(long timeLimit) {
        Log.d(TAG, "begin installation checking: time limit is " + timeLimit + " msec");
        ArrayList<AdWaitingAppValue> apps = TransactionDatastore.getAdWaitingApp(null);
        Log.d(TAG, "found " + apps.size() + " entries");
        long now = System.currentTimeMillis();
        Iterator it = apps.iterator();
        while (it.hasNext()) {
            AdWaitingAppValue app = (AdWaitingAppValue) it.next();
            Log.d(TAG, "duration: " + (now - app.getDate()) + " msec");
            if (timeLimit >= 0 && now - app.getDate() > timeLimit) {
                Log.d(TAG, "installation of " + app.getPackage() + " is timed out");
                AppInstallReceiver.stopInstallation(app.getAdId());
            } else if (isInstalled(app.getPackage())) {
                Log.d(TAG, "installation of " + app.getPackage() + " is completed");
                AppInstallReceiver.finishInstallation(app.getAdId(), app.getPackage(), app.getCountConversion());
            } else {
                Log.d(TAG, "installation of " + app.getPackage() + " is waited");
            }
        }
        Log.d(TAG, "end installation checking");
    }

    public static boolean isInstalled(String packageName) {
        try {
            SDKBridge.getContext().getPackageManager().getPackageInfo(packageName, 1);
            return true;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    public static boolean canRespondToImplicitIntent(String uriStr) {
        if (SDKBridge.getContext().getPackageManager().queryIntentActivities(new Intent("android.intent.action.VIEW", Uri.parse(uriStr)), 0).size() > 0) {
            return true;
        }
        return false;
    }

    public static String getGooglePlayUrl(String packageName) {
        return "market://details?id=" + packageName;
    }

    public static String getUserToken() {
        return AccountDatastore.getCurrentUser().getToken();
    }

    public static boolean startActivityFromWebView(Context context, Uri data) {
        if (SCHEMA.equals(data.getScheme())) {
            return IntentUtils.startActivity(context, Uri.parse(data.toString().replace(SCHEMA, IntentUtils.SCHEME_LOBI)));
        }
        return false;
    }

    public static void countUpClick(final String adId) {
        String token = getUserToken();
        Map<String, String> params = new HashMap();
        params.put("token", token);
        params.put("ad_id", adId);
        CoreAPI.postAdTrackingClick(params, new DefaultAPICallback<PostAdTrackingClick>(null) {
            public void onResponse(PostAdTrackingClick t) {
                if (t.success) {
                    Log.i(AdUtil.TAG, "click ok: " + adId);
                }
            }

            public void onError(int statusCode, String responseBody) {
                Log.i(AdUtil.TAG, "click ng(" + statusCode + ") " + responseBody);
            }

            public void onError(Throwable e) {
            }
        });
        Log.d(TAG, "click: " + adId);
    }

    public static void countUpConversion(final String adId) {
        Log.d(TAG, "getuuid");
        DeviceUUID.getUUID(SDKBridge.getContext(), new OnGetUUID() {
            public void onGetUUID(String uuid) {
                String token = AdUtil.getUserToken();
                Map<String, String> params = new HashMap();
                params.put("token", token);
                params.put("ad_id", adId);
                params.put("install_id", uuid);
                CoreAPI.postAdTrackingConversion(params, new DefaultAPICallback<PostAdTrackingConversion>(null) {
                    public void onResponse(PostAdTrackingConversion t) {
                        super.onResponse(t);
                        Log.d(AdUtil.TAG, "conversion result: " + adId);
                        if (t.success) {
                            Log.d(AdUtil.TAG, "conversion ok: " + adId);
                            AppInstallReceiver.stopInstallation(adId);
                        }
                    }

                    public void onError(int statusCode, String responseBody) {
                        Log.d(AdUtil.TAG, "conversion error: (" + statusCode + ") " + responseBody);
                        if (statusCode < 500) {
                            AppInstallReceiver.stopInstallation(adId);
                        }
                    }

                    public void onError(Throwable e) {
                    }
                });
                Log.d(AdUtil.TAG, "conversion: " + adId);
            }
        });
    }

    public static void startInstallation(String adId, String packageName, boolean countConversion) {
        AppInstallReceiver.startInstallation(adId, packageName, null, countConversion);
    }
}
