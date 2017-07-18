package com.kayac.lobi.sdk.auth;

import android.app.Activity;
import android.content.Context;
import com.google.android.gcm.GCMConstants;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastore;
import com.kayac.lobi.libnakamap.net.APIDef.PostOauthAccessToken.RequestKey;
import com.kayac.lobi.libnakamap.net.APIRes.PostOauthAccessToken;
import com.kayac.lobi.libnakamap.net.APISync;
import com.kayac.lobi.libnakamap.net.APISync.APISyncException;
import com.kayac.lobi.libnakamap.net.CoreAPI;
import com.kayac.lobi.libnakamap.net.CoreAPI.DefaultAPICallback;
import com.kayac.lobi.libnakamap.utils.Log;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.libnakamap.value.UserValue.Builder;
import com.kayac.lobi.sdk.LobiCore;
import com.kayac.lobi.sdk.LobiCoreAPI.APICallback;
import com.kayac.lobi.sdk.exception.NakamapIllegalStateException;
import com.kayac.lobi.sdk.net.NakamapApi;
import com.kayac.lobi.sdk.utils.SDKBridge;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class AccountUtil {
    private static final String INVALID_REQUEST = "invalid_request";
    private static final long REFRESH_INTERVAL_IN_MILISECONDS = 86400000;
    private static final String REFRESH_TOKEN_INVALID = "refresh_token invalid";

    public static class BindTrackingId {
        public static final int DEFAULT = 0;
        public static final int FOLLOW_BUTTON = 1;
        public static final int IN_GAME = 12;
        public static final int MENU_DRAWER_FOLLOW_LIST_ITEM = 7;
        public static final int MENU_DRAWER_LOGIN_BUTTON = 6;
        public static final int NOTIFICATION_LIST_ITEM = 3;
        public static final int NOTIFICATION_LOGIN_BUTTON = 4;
        public static final int PROFILE_LOGIN_BUTTON = 2;
        public static final int RANKING = 11;
        public static final int SETTING_ITEM = 5;
        public static final int VIDEO_ADD_COMMENT = 9;
        public static final int VIDEO_FOLLOW_BUTTON = 8;
        public static final int VIDEO_POST = 10;
    }

    public static void refreshToken() {
        Log.v("nakamap-sdk", "refreshToken");
        UserValue currentUser = AccountDatastore.getCurrentUser();
        String token = currentUser.getToken();
        Map<String, String> params = new HashMap();
        params.put(RequestKey.GRANT_TYPE, "refresh_token");
        params.put("refresh_token", token);
        try {
            PostOauthAccessToken t = APISync.postOauthAccessToken(params);
            Log.v("nakamap-sdk", "token: " + t.accessToken);
            AccountDatastore.setValue("lastRefreshedDate", Long.valueOf(System.currentTimeMillis()));
            Builder builder = new Builder(currentUser);
            builder.setToken(t.accessToken);
            UserValue userValue = builder.build();
            List<UserValue> users = new ArrayList();
            users.add(userValue);
            AccountDatastore.setUsers(users);
            AccountDatastore.setCurrentUser(userValue);
        } catch (APISyncException e) {
            int statusCode = e.getStatusCode();
            Log.v("nakamap-sdk", "onError " + statusCode);
            if (400 == statusCode) {
                try {
                    JSONObject jsonObject = new JSONObject(e.getResponseBody());
                    String error = jsonObject.optString(GCMConstants.EXTRA_ERROR);
                    String errorDescription = jsonObject.optString("error_description");
                    Log.v("nakamap-sdk", errorDescription);
                    if (INVALID_REQUEST.equals(error) && REFRESH_TOKEN_INVALID.equals(errorDescription)) {
                        NakamapApi.signupAgain();
                    }
                } catch (JSONException je) {
                    je.printStackTrace();
                }
            }
        }
    }

    public static boolean shouldRefreshToken() {
        if (!LobiCore.isSignedIn()) {
            return false;
        }
        Long lastRefreshedDate = (Long) TransactionDatastore.getValue("lastRefreshedDate", Long.valueOf(0));
        Log.v("refreshToken", "diff: " + (System.currentTimeMillis() - lastRefreshedDate.longValue()));
        if (System.currentTimeMillis() - lastRefreshedDate.longValue() <= 86400000) {
            return false;
        }
        Log.v("nakamap-sdk [refreshToken]", "refresh token!");
        TransactionDatastore.setValue("lastRefreshedDate", Long.valueOf(System.currentTimeMillis()));
        return true;
    }

    public static void refreshTokenIfNecessary(APICallback callback) {
        if (shouldRefreshToken()) {
            refreshToken();
        }
        callback.onResult(0, null);
    }

    public static void clearTokenRefreshedDate() {
        TransactionDatastore.deleteValue("lastRefreshedDate");
    }

    public static void requestCurrentUserBindingState(DefaultAPICallback<Boolean> callback) {
        if (LobiCore.isSignedIn()) {
            UserValue currentUser = AccountDatastore.getCurrentUser();
            Map<String, String> params = new HashMap();
            params.put("token", currentUser.getToken());
            CoreAPI.getMeBinded(params, callback);
            return;
        }
        callback.onError(new NakamapIllegalStateException("current user not set"));
    }

    public static void bindWithInstalledAppIfNecessary(final Context context, final int trackingId) {
        if (SDKBridge.checkIfNakamapNativeAppInstalled(context.getPackageManager())) {
            requestCurrentUserBindingState(new DefaultAPICallback<Boolean>(null) {
                public void onResponse(Boolean binded) {
                    if (binded == null || !binded.booleanValue()) {
                        SDKBridge.startBindingToNativeApp((Activity) context, trackingId);
                    }
                }
            });
        }
    }

    public static void bindToLobiAccount(int trackingId) {
        LobiCore.assertSetup();
        if (LobiCore.isSignedIn()) {
            SDKBridge.startBindingToNativeApp(LobiCore.sharedInstance().getContext(), trackingId);
        }
    }
}
