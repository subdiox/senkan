package com.kayac.lobi.sdk.unity;

import com.kayac.lobi.libnakamap.contacts.SNSInterface;
import com.kayac.lobi.sdk.LobiCore;
import com.kayac.lobi.sdk.LobiCoreAPI;
import com.kayac.lobi.sdk.LobiCoreAPI.APICallback;
import com.kayac.lobi.sdk.utils.UnityUtils;
import java.io.File;
import org.json.JSONObject;

public class LobiCoreBridge {
    private static final String UNITY_EVENT_RECEIVER_GAME_OBJECT_NAME = "LobiEventReceiver";

    public static int isReady() {
        UnityUtils.checkSetup();
        return LobiCore.isSignedIn() ? 1 : 0;
    }

    public static void setAccountBaseName(String baseName) {
        UnityUtils.checkSetup();
        LobiCore.sharedInstance().setNewAccountBaseName(baseName);
    }

    public static void presentProfile() {
        UnityUtils.checkSetup();
        LobiCore.presentProfile();
    }

    public static void presentAdWall() {
        UnityUtils.checkSetup();
        LobiCore.presentAdWall();
    }

    public static void setupPopOverController(int x, int y, int direction) {
    }

    public static void setNavigationBarCustomColor(float r, float g, float b) {
    }

    public static void signupWithBaseName(final String gameObjectName, final String callbackMethodName, final String id, String accountBaseName) {
        UnityUtils.checkSetup();
        LobiCoreAPI.signupWithBaseName(accountBaseName, new APICallback() {
            public void onResult(int code, JSONObject response) {
                String message = "";
                if (code != 0) {
                    message = UnityUtils.errorMessage(id, code, response);
                } else {
                    message = UnityUtils.defaultSuccessMessage(id, code);
                }
                UnityUtils.unitySendMessage(gameObjectName, callbackMethodName, message);
            }
        });
    }

    public static void signupWithBaseName(final String gameObjectName, final String callbackMethodName, final String id, String accountBaseName, String encryptedExternalId, String encryptIv) {
        UnityUtils.checkSetup();
        LobiCoreAPI.signupWithBaseName(accountBaseName, encryptedExternalId, encryptIv, new APICallback() {
            public void onResult(int code, JSONObject response) {
                String message = "";
                if (code != 0) {
                    message = UnityUtils.errorMessage(id, code, response);
                } else {
                    message = UnityUtils.defaultSuccessMessage(id, code);
                }
                UnityUtils.unitySendMessage(gameObjectName, callbackMethodName, message);
            }
        });
    }

    public static void updateUserName(final String gameObjectName, final String callbackMethodName, final String id, String userName) {
        UnityUtils.checkSetup();
        LobiCoreAPI.updateUserName(userName, new APICallback() {
            public void onResult(int code, JSONObject response) {
                String message = "";
                if (code != 0) {
                    message = UnityUtils.errorMessage(id, code, response);
                } else {
                    message = UnityUtils.defaultSuccessMessage(id, code);
                }
                UnityUtils.unitySendMessage(gameObjectName, callbackMethodName, message);
            }
        });
    }

    public static void updateUserIcon(final String gameObjectName, final String callbackMethodName, final String id, String filePath) {
        UnityUtils.checkSetup();
        LobiCoreAPI.updateUserIcon(new File(filePath), new APICallback() {
            public void onResult(int code, JSONObject response) {
                String message = "";
                if (code != 0) {
                    message = UnityUtils.errorMessage(id, code, response);
                } else {
                    message = UnityUtils.defaultSuccessMessage(id, code);
                }
                UnityUtils.unitySendMessage(gameObjectName, callbackMethodName, message);
            }
        });
    }

    public static void updateUserCover(final String gameObjectName, final String callbackMethodName, final String id, String filePath) {
        UnityUtils.checkSetup();
        LobiCoreAPI.updateUserCover(new File(filePath), new APICallback() {
            public void onResult(int code, JSONObject response) {
                String message = "";
                if (code != 0) {
                    message = UnityUtils.errorMessage(id, code, response);
                } else {
                    message = UnityUtils.defaultSuccessMessage(id, code);
                }
                UnityUtils.unitySendMessage(gameObjectName, callbackMethodName, message);
            }
        });
    }

    public static void prepareExternalId(String encryptedExternalId, String encryptIv, String baseName) {
        UnityUtils.checkSetup();
        LobiCore.prepareExternalId(encryptedExternalId, encryptIv, baseName);
    }

    public static void enableStrictExid() {
        UnityUtils.checkSetup();
        LobiCore.enableStrictExid();
    }

    public static int getUseStrictExid() {
        UnityUtils.checkSetup();
        return LobiCore.isStrictExidEnabled() ? 1 : 0;
    }

    public static void updateEncryptedExternalId(final String gameObjectName, final String callbackMethodName, final String id, String encryptedExternalId, String encryptIv) {
        UnityUtils.checkSetup();
        LobiCoreAPI.updateEncryptedExternalId(encryptedExternalId, encryptIv, new APICallback() {
            public void onResult(int code, JSONObject response) {
                String message = "";
                if (code != 0) {
                    message = UnityUtils.errorMessage(id, code, response);
                } else {
                    message = UnityUtils.defaultSuccessMessage(id, code);
                }
                UnityUtils.unitySendMessage(gameObjectName, callbackMethodName, message);
            }
        });
    }

    public static int hasExIdUser() {
        return LobiCore.hasExidUser() ? 1 : 0;
    }

    public static void bindToLobiAccount() {
        LobiCore.bindToLobiAccount();
    }

    public static void isBoundWithLobiAccount() {
        LobiCoreAPI.isBoundWithLobiAccount(new APICallback() {
            public void onResult(int code, JSONObject response) {
                String message = "0";
                if (code == 0) {
                    message = response.optString(SNSInterface.SUCCESS, "0");
                }
                UnityUtils.unitySendMessage(LobiCoreBridge.UNITY_EVENT_RECEIVER_GAME_OBJECT_NAME, "IsBoundWithLobiAccount", message);
            }
        });
    }

    public static void setClientId(String clientId) {
        LobiCore.setClientId(clientId);
    }
}
