package com.kayac.lobi.sdk;

import com.google.android.gcm.GCMConstants;
import com.kayac.lobi.libnakamap.contacts.SNSInterface;
import com.kayac.lobi.libnakamap.net.CoreAPI.DefaultAPICallback;
import com.kayac.lobi.sdk.auth.AccountUtil;
import com.kayac.lobi.sdk.exception.NakamapIllegalStateException;
import com.kayac.lobi.sdk.net.NakamapApi;
import java.io.File;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LobiCoreAPI {

    public interface APICallback {
        public static final int FATAL_ERROR = 102;
        public static final int NETWORK_ERROR = 100;
        public static final int RESPONSE_ERROR = 101;
        public static final int SUCCESS = 0;

        void onResult(int i, JSONObject jSONObject);
    }

    private static native void updateEncryptedExternalIdNativeCallback(int i, String str);

    public static final boolean callbackErrorIfCurrentUserNotSet(APICallback callback) {
        if (LobiCore.isSignedIn()) {
            return false;
        }
        try {
            JSONObject obj = new JSONObject();
            obj.put(GCMConstants.EXTRA_ERROR, new JSONArray(new ArrayList<String>() {
                {
                    add("current user not set");
                }
            }));
            callback.onResult(APICallback.FATAL_ERROR, obj);
        } catch (JSONException e) {
            e.printStackTrace();
            callback.onResult(APICallback.FATAL_ERROR, null);
        }
        return true;
    }

    public static final void signupWithBaseName(String newAccountBaseName, APICallback callback) {
        LobiCore.assertSetup();
        LobiCore.sharedInstance();
        if (LobiCore.isStrictExidEnabled()) {
            throw new NakamapIllegalStateException("Lobi signing up requires encrypted exid & iv.");
        }
        NakamapApi.signupWithBaseName(newAccountBaseName, null, null, callback);
    }

    public static final void signupWithBaseName(String newAccountBaseName, String encryptedExternalId, String iv, APICallback callback) {
        LobiCore.assertSetup();
        NakamapApi.signupWithBaseName(newAccountBaseName, encryptedExternalId, iv, callback);
    }

    public static final void updateEncryptedExternalId(String encryptedExternalId, String iv, APICallback callback) {
        LobiCore.assertSetup();
        if (!callbackErrorIfCurrentUserNotSet(callback)) {
            NakamapApi.updateEncryptedExternalID(encryptedExternalId, iv, callback);
        }
    }

    public static final void updateEncryptedExternalId(String encryptedExternalId, String iv) {
        updateEncryptedExternalId(encryptedExternalId, iv, new APICallback() {
            public void onResult(int code, JSONObject response) {
                try {
                    LobiCoreAPI.updateEncryptedExternalIdNativeCallback(code, response.toString());
                } catch (UnsatisfiedLinkError e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static final void updateUserIcon(File icon, APICallback callback) {
        LobiCore.assertSetup();
        if (!callbackErrorIfCurrentUserNotSet(callback)) {
            NakamapApi.updateUserIcon(icon, callback);
        }
    }

    public static final void updateUserName(String name, APICallback callback) {
        LobiCore.assertSetup();
        if (!callbackErrorIfCurrentUserNotSet(callback)) {
            NakamapApi.updateUserName(name, callback);
        }
    }

    public static final void updateUserCover(File cover, APICallback callback) {
        LobiCore.assertSetup();
        if (!callbackErrorIfCurrentUserNotSet(callback)) {
            NakamapApi.updateUserCover(cover, callback);
        }
    }

    public static final void isBoundWithLobiAccount(final APICallback callback) {
        AccountUtil.requestCurrentUserBindingState(new DefaultAPICallback<Boolean>(LobiCore.sharedInstance().getContext()) {
            private void falseResult() {
                JSONObject response = new JSONObject();
                try {
                    response.put(SNSInterface.SUCCESS, "0");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callback.onResult(0, response);
            }

            public void onResponse(Boolean binded) {
                if (binded == null || !binded.booleanValue()) {
                    falseResult();
                    return;
                }
                JSONObject response = new JSONObject();
                try {
                    response.put(SNSInterface.SUCCESS, "1");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callback.onResult(0, response);
            }

            public void onError(int statusCode, String responseBody) {
                falseResult();
            }

            public void onError(Throwable e) {
                falseResult();
            }
        });
    }

    public static void setClientId(String clientId) {
        LobiCore.setClientId(clientId);
    }
}
