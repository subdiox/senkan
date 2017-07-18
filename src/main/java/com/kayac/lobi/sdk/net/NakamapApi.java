package com.kayac.lobi.sdk.net;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.google.android.gms.wallet.WalletConstants;
import com.kayac.lobi.libnakamap.contacts.SNSInterface;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.KKey.Chat;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastore;
import com.kayac.lobi.libnakamap.exception.NakamapException.CurrentUserNotSet;
import com.kayac.lobi.libnakamap.net.APIDef.PostGroups;
import com.kayac.lobi.libnakamap.net.APIDef.PostMeCover;
import com.kayac.lobi.libnakamap.net.APIDef.PostSignupFree.RequestKey;
import com.kayac.lobi.libnakamap.net.APIRes;
import com.kayac.lobi.libnakamap.net.APIRes.GetGroup;
import com.kayac.lobi.libnakamap.net.APIRes.GetStampUnlocked;
import com.kayac.lobi.libnakamap.net.APIRes.PostDefaultMeContacts;
import com.kayac.lobi.libnakamap.net.APIRes.PostDefaultMeContactsRemove;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupChat;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupExId;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupJoinWithExId;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupKickExId;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupMembersExId;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupPartExId;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupRemoveExId;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupTransferExId;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroups1on1s;
import com.kayac.lobi.libnakamap.net.APIRes.PostMeContactsExId;
import com.kayac.lobi.libnakamap.net.APIRes.PostMeContactsRemoveExId;
import com.kayac.lobi.libnakamap.net.APIRes.PostMeExId;
import com.kayac.lobi.libnakamap.net.APIRes.PostMeIcon;
import com.kayac.lobi.libnakamap.net.APIRes.PostMeProfile;
import com.kayac.lobi.libnakamap.net.APIRes.PostSignupFree;
import com.kayac.lobi.libnakamap.net.APIRes.PostStampUnlock;
import com.kayac.lobi.libnakamap.net.APISync;
import com.kayac.lobi.libnakamap.net.APISync.APISyncException;
import com.kayac.lobi.libnakamap.net.CoreAPI;
import com.kayac.lobi.libnakamap.net.CoreAPI.DefaultAPICallback;
import com.kayac.lobi.libnakamap.net.security.HashUtils;
import com.kayac.lobi.libnakamap.utils.Log;
import com.kayac.lobi.libnakamap.utils.NakamapSDKDatastore;
import com.kayac.lobi.libnakamap.utils.NakamapSDKDatastore.Key;
import com.kayac.lobi.libnakamap.value.ChatValue;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.libnakamap.value.UserValue.Builder;
import com.kayac.lobi.sdk.LobiCore;
import com.kayac.lobi.sdk.LobiCoreAPI.APICallback;
import com.kayac.lobi.sdk.Version;
import com.kayac.lobi.sdk.migration.datastore.UserMigrationHelper;
import com.rekoo.libs.config.Config;
import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class NakamapApi {
    public static final byte[] SALT = new byte[]{(byte) 50, (byte) 56, (byte) 53, (byte) 49, (byte) 50, (byte) 100, (byte) 54, (byte) 101, (byte) 57, (byte) 50, (byte) 48, (byte) 54, (byte) 52, (byte) 48, (byte) 56, (byte) 51, (byte) 54, (byte) 55, (byte) 49, (byte) 50, (byte) 102, (byte) 102, (byte) 51, (byte) 101, (byte) 100, (byte) 50, (byte) 98, (byte) 97, (byte) 49, (byte) 97, (byte) 54, (byte) 100, (byte) 97, (byte) 49, (byte) 50, (byte) 53, (byte) 49, (byte) 53, (byte) 49, (byte) 51};
    private static final Handler sHandler = new Handler(Looper.getMainLooper());

    public static final void init() {
    }

    private static void saveSignupResult(UserValue user, String encryptedExternalId, String iv) {
        if (LobiCore.isSignedIn() && !TextUtils.equals(user.getUid(), AccountDatastore.getCurrentUser().getUid())) {
            LobiCore.clear();
        }
        NakamapSDKDatastore.saveCurrentUser(user);
        if (!TextUtils.isEmpty(encryptedExternalId)) {
            AccountDatastore.setValue(Key.ENCRYPTED_EX_ID, encryptedExternalId);
        }
        if (!TextUtils.isEmpty(iv)) {
            AccountDatastore.setValue("iv", iv);
        }
        if (UserMigrationHelper.hasOldUserAccount()) {
            UserMigrationHelper.deleteOldDatabase();
        }
    }

    public static JSONObject signupWithBaseNameSync(String newAccountBaseName, String encryptedExternalId, String iv) throws APISyncException {
        LobiCore nakamap = LobiCore.sharedInstance();
        String installId = UserMigrationHelper.getInstallId();
        Map<String, String> params = new HashMap();
        params.put("client_id", nakamap.getClientId());
        params.put("install_id", installId);
        params.put(RequestKey.SIGNATURE, HashUtils.hmacSha1(new String(SALT), installId));
        params.put("name", newAccountBaseName);
        params.put("platform", "android");
        params.put("version", Version.versionName);
        if (!TextUtils.isEmpty(encryptedExternalId)) {
            params.put("encrypted_ex_id", encryptedExternalId);
            AccountDatastore.setValue(Key.ENCRYPTED_EX_ID, encryptedExternalId);
        }
        if (!TextUtils.isEmpty(iv)) {
            params.put("iv", iv);
            AccountDatastore.setValue("iv", iv);
        }
        PostSignupFree t = APISync.postSignupFree(params);
        saveSignupResult(t.user, encryptedExternalId, iv);
        Log.v("nakamap-sdk", "user: " + t.user.getName());
        JSONObject response = new JSONObject();
        try {
            response.put(SNSInterface.SUCCESS, "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static void signupWithBaseName(String newAccountBaseName, final String encryptedExternalId, final String iv, final APICallback callback) {
        LobiCore nakamap = LobiCore.sharedInstance();
        String installId = UserMigrationHelper.getInstallId();
        Map<String, String> params = new HashMap();
        params.put("client_id", nakamap.getClientId());
        params.put("install_id", installId);
        params.put(RequestKey.SIGNATURE, HashUtils.hmacSha1(new String(SALT), installId));
        params.put("name", newAccountBaseName);
        params.put("platform", "android");
        params.put("version", Version.versionName);
        if (!TextUtils.isEmpty(encryptedExternalId)) {
            params.put("encrypted_ex_id", encryptedExternalId);
            AccountDatastore.setValue(Key.ENCRYPTED_EX_ID, encryptedExternalId);
        }
        if (!TextUtils.isEmpty(iv)) {
            params.put("iv", iv);
            AccountDatastore.setValue("iv", iv);
        }
        CoreAPI.postSignupFree(params, new DefaultAPICallback<PostSignupFree>(null) {
            public void onResponse(PostSignupFree t) {
                NakamapApi.saveSignupResult(t.user, encryptedExternalId, iv);
                Log.v("nakamap-sdk", "user: " + t.user.getName());
                JSONObject response = new JSONObject();
                try {
                    response.put(SNSInterface.SUCCESS, "1");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callback.onResult(0, response);
            }

            public void onError(int statusCode, String responseBody) {
                NakamapApi.handleOnError(callback, statusCode, responseBody);
            }

            public void onError(Throwable e) {
                NakamapApi.handleOnError(callback);
            }
        });
    }

    public static final void updateUserIcon(File icon, final APICallback callback) {
        Map<String, String> params = new HashMap();
        params.put("token", accessToken());
        params.put("icon", icon.getAbsolutePath());
        params.put("force", "1");
        CoreAPI.postMeIcon(params, new DefaultAPICallback<PostMeIcon>(null) {
            public void onResponse(PostMeIcon t) {
                Builder builder = new Builder(AccountDatastore.getCurrentUser());
                builder.setIcon(t.icon);
                AccountDatastore.setUser(builder.build());
                JSONObject response = new JSONObject();
                try {
                    response.put(SNSInterface.SUCCESS, "1");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callback.onResult(0, response);
            }

            public void onError(int statusCode, String responseBody) {
                NakamapApi.handleOnError(callback, statusCode, responseBody);
            }

            public void onError(Throwable e) {
                e.printStackTrace();
                NakamapApi.handleOnError(callback);
            }
        });
    }

    public static void updateUserName(final String name, final APICallback callback) {
        Map<String, String> params = new HashMap();
        params.put("token", accessToken());
        params.put("name", name);
        CoreAPI.postMeProfile(params, new DefaultAPICallback<PostMeProfile>(null) {
            public void onResponse(PostMeProfile t) {
                if (t.success) {
                    Builder builder = new Builder(AccountDatastore.getCurrentUser());
                    builder.setName(name);
                    AccountDatastore.setUser(builder.build());
                }
                JSONObject response = new JSONObject();
                try {
                    response.put(SNSInterface.SUCCESS, t.success ? "1" : "0");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callback.onResult(0, response);
            }

            public void onError(int statusCode, String responseBody) {
                NakamapApi.handleOnError(callback, statusCode, responseBody);
            }

            public void onError(Throwable e) {
                e.printStackTrace();
                NakamapApi.handleOnError(callback);
            }
        });
    }

    public static final void updateUserCover(File cover, final APICallback callback) {
        Map<String, String> params = new HashMap();
        params.put("token", accessToken());
        params.put(PostMeCover.RequestKey.COVER, cover.getAbsolutePath());
        params.put("force", "1");
        CoreAPI.postMeCover(params, new DefaultAPICallback<APIRes.PostMeCover>(null) {
            public void onResponse(APIRes.PostMeCover t) {
                Builder builder = new Builder(AccountDatastore.getCurrentUser());
                builder.setCover(t.cover);
                AccountDatastore.setUser(builder.build());
                JSONObject response = new JSONObject();
                try {
                    response.put(SNSInterface.SUCCESS, "1");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callback.onResult(0, response);
            }

            public void onError(int statusCode, String responseBody) {
                NakamapApi.handleOnError(callback, statusCode, responseBody);
            }

            public void onError(Throwable e) {
                e.printStackTrace();
                NakamapApi.handleOnError(callback);
            }
        });
    }

    public static final void unlockStamp(String stampUid, final APICallback callback) {
        Map<String, String> params = new HashMap();
        params.put("token", accessToken());
        params.put("uid", stampUid);
        CoreAPI.postStampUnlock(params, new DefaultAPICallback<PostStampUnlock>(null) {
            public void onResponse(PostStampUnlock t) {
                NakamapApi.sHandler.post(new Runnable() {
                    public void run() {
                        JSONObject response = new JSONObject();
                        try {
                            response.put(SNSInterface.SUCCESS, "1");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.onResult(0, response);
                    }
                });
            }

            public void onError(int statusCode, String responseBody) {
                NakamapApi.handleOnError(callback, statusCode, responseBody);
            }

            public void onError(Throwable e) {
                NakamapApi.handleOnError(callback);
            }
        });
    }

    public static final void askStampUnlocked(String stampUid, final APICallback callback) {
        Map<String, String> params = new HashMap();
        params.put("token", accessToken());
        params.put("uid", stampUid);
        CoreAPI.getStampUnlocked(params, new DefaultAPICallback<GetStampUnlocked>(null) {
            public void onResponse(GetStampUnlocked t) {
                NakamapApi.sHandler.post(new Runnable() {
                    public void run() {
                        JSONObject response = new JSONObject();
                        try {
                            response.put("unlocked", "1");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.onResult(0, response);
                    }
                });
            }

            public void onError(int statusCode, String responseBody) {
                Log.v("nakamap-sdk", "onError (" + statusCode + "): " + responseBody);
                if (statusCode == WalletConstants.ERROR_CODE_INVALID_PARAMETERS) {
                    NakamapApi.sHandler.post(new Runnable() {
                        public void run() {
                            JSONObject response = new JSONObject();
                            try {
                                response.put("unlocked", "0");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            callback.onResult(0, response);
                        }
                    });
                } else {
                    NakamapApi.handleOnError(callback, statusCode, responseBody);
                }
            }

            public void onError(Throwable e) {
                NakamapApi.handleOnError(callback);
            }
        });
    }

    public static final void updateEncryptedExternalID(String encryptedExternalId, String iv, final APICallback callback) {
        Map<String, String> params = new HashMap();
        params.put("token", accessToken());
        params.put("encrypted_ex_id", encryptedExternalId);
        params.put("iv", iv);
        AccountDatastore.setValue(Key.ENCRYPTED_EX_ID, encryptedExternalId);
        AccountDatastore.setValue("iv", iv);
        CoreAPI.postMeExId(params, new DefaultAPICallback<PostMeExId>(null) {
            public void onResponse(final PostMeExId t) {
                NakamapApi.sHandler.post(new Runnable() {
                    public void run() {
                        String success = t.success ? "1" : "0";
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put(SNSInterface.SUCCESS, success);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.onResult(0, jsonObject);
                    }
                });
            }

            public void onError(int statusCode, String responseBody) {
                NakamapApi.handleOnError(callback, statusCode, responseBody);
            }

            public void onError(Throwable e) {
                NakamapApi.handleOnError(callback);
            }
        });
    }

    public static final void addFriendsWithExternalIDs(List<String> externalIds, final APICallback callback) {
        Map<String, String> params = new HashMap();
        params.put("token", accessToken());
        params.put("user_ex_ids", TextUtils.join(",", externalIds));
        CoreAPI.postMeContactsExId(params, new DefaultAPICallback<PostMeContactsExId>(null) {
            public void onResponse(final PostMeContactsExId t) {
                NakamapApi.sHandler.post(new Runnable() {
                    public void run() {
                        String success = t.success ? "1" : "0";
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put(SNSInterface.SUCCESS, success);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.onResult(0, jsonObject);
                    }
                });
            }

            public void onError(int statusCode, String responseBody) {
                NakamapApi.handleOnError(callback, statusCode, responseBody);
            }

            public void onError(Throwable e) {
                NakamapApi.handleOnError(callback);
            }
        });
    }

    public static final void removeFriendWithExternalID(String extenalId, final APICallback callback) {
        Map<String, String> params = new HashMap();
        params.put("token", accessToken());
        params.put("user_ex_id", extenalId);
        CoreAPI.postMeContactsRemoveExId(params, new DefaultAPICallback<PostMeContactsRemoveExId>(null) {
            public void onResponse(final PostMeContactsRemoveExId t) {
                NakamapApi.sHandler.post(new Runnable() {
                    public void run() {
                        String success = t.success ? "1" : "0";
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put(SNSInterface.SUCCESS, success);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.onResult(0, jsonObject);
                    }
                });
            }

            public void onError(int statusCode, String responseBody) {
                NakamapApi.handleOnError(callback, statusCode, responseBody);
            }

            public void onError(Throwable e) {
                NakamapApi.handleOnError(callback);
            }
        });
    }

    public static final void followLobiAccount(String userUid, final APICallback callback) {
        Map<String, String> params = new HashMap();
        params.put("token", accessToken());
        params.put("users", userUid);
        CoreAPI.postDefaultMeContacts(params, new DefaultAPICallback<PostDefaultMeContacts>(null) {
            public void onResponse(final PostDefaultMeContacts t) {
                NakamapApi.sHandler.post(new Runnable() {
                    public void run() {
                        String success = t.success ? "1" : "0";
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put(SNSInterface.SUCCESS, success);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.onResult(0, jsonObject);
                    }
                });
            }

            public void onError(int statusCode, String responseBody) {
                NakamapApi.handleOnError(callback, statusCode, responseBody);
            }

            public void onError(Throwable e) {
                NakamapApi.handleOnError(callback);
            }
        });
    }

    public static final void unfollowLobiAccount(String userUid, final APICallback callback) {
        Map<String, String> params = new HashMap();
        params.put("token", accessToken());
        params.put("user", userUid);
        CoreAPI.postDefaultMeContactsRemove(params, new DefaultAPICallback<PostDefaultMeContactsRemove>(null) {
            public void onResponse(final PostDefaultMeContactsRemove t) {
                NakamapApi.sHandler.post(new Runnable() {
                    public void run() {
                        String success = t.success ? "1" : "0";
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put(SNSInterface.SUCCESS, success);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.onResult(0, jsonObject);
                    }
                });
            }

            public void onError(int statusCode, String responseBody) {
                NakamapApi.handleOnError(callback, statusCode, responseBody);
            }

            public void onError(Throwable e) {
                NakamapApi.handleOnError(callback);
            }
        });
    }

    public static final void postChatWithGroupExternalId(String externalId, final String message, final File photo, final APICallback callback) {
        Map<String, String> params = new HashMap();
        params.put("token", accessToken());
        params.put("group_ex_id", externalId);
        CoreAPI.getGroupWithExIdV2(params, new DefaultAPICallback<GetGroup>(null) {
            public void onError(int statusCode, String responseBody) {
                NakamapApi.handleOnError(callback, statusCode, responseBody);
            }

            public void onError(Throwable e) {
                NakamapApi.handleOnError(callback);
            }

            public void onResponse(GetGroup t) {
                String groupUid = t.group.getUid();
                Map<String, String> params = new HashMap();
                params.put("uid", groupUid);
                params.put("token", NakamapApi.accessToken());
                if (!TextUtils.isEmpty(message)) {
                    params.put("message", message);
                }
                if (photo != null) {
                    params.put("image", photo.getAbsolutePath());
                }
                CoreAPI.postGroupChat(params, new DefaultAPICallback<PostGroupChat>(null) {
                    public void onResponse(PostGroupChat t) {
                        NakamapApi.sHandler.post(new Runnable() {
                            public void run() {
                                JSONObject jsonObject = new JSONObject();
                                try {
                                    jsonObject.put(SNSInterface.SUCCESS, "1");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                callback.onResult(0, jsonObject);
                            }
                        });
                    }

                    public void onError(int statusCode, String responseBody) {
                        NakamapApi.handleOnError(callback, statusCode, responseBody);
                    }

                    public void onError(Throwable e) {
                        NakamapApi.handleOnError(callback);
                    }
                });
            }
        });
    }

    public static final void makeGroupWithNameAndExternalIds(String name, List<String> userExtenalIds, final APICallback callback) {
        Map<String, String> params = new HashMap();
        params.put("token", accessToken());
        params.put("name", name);
        params.put(PostGroups.RequestKey.OPTION_MEMBER_USER_EX_IDS, TextUtils.join(",", userExtenalIds));
        CoreAPI.postGroups(params, new DefaultAPICallback<APIRes.PostGroups>(null) {
            public void onResponse(APIRes.PostGroups t) {
                NakamapApi.sHandler.post(new Runnable() {
                    public void run() {
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put(SNSInterface.SUCCESS, "1");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.onResult(0, jsonObject);
                    }
                });
            }

            public void onError(int statusCode, String responseBody) {
                NakamapApi.handleOnError(callback, statusCode, responseBody);
            }

            public void onError(Throwable e) {
                NakamapApi.handleOnError(callback);
            }
        });
    }

    public static void createGroupWithExternalID(String groupExternalID, String groupName, final APICallback callback) {
        Map<String, String> params = new HashMap();
        params.put("token", accessToken());
        params.put("name", groupName);
        params.put("group_ex_id", groupExternalID);
        CoreAPI.postGroups(params, new DefaultAPICallback<APIRes.PostGroups>(null) {
            public void onResponse(APIRes.PostGroups t) {
                NakamapApi.sHandler.post(new Runnable() {
                    public void run() {
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put(SNSInterface.SUCCESS, "1");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.onResult(0, jsonObject);
                    }
                });
            }

            public void onError(int statusCode, String responseBody) {
                NakamapApi.handleOnError(callback, statusCode, responseBody);
            }

            public void onError(Throwable e) {
                NakamapApi.handleOnError(callback);
            }
        });
    }

    public static final void deleteGroupWithExternalID(String groupExternalID, final APICallback callback) {
        Map<String, String> params = new HashMap();
        params.put("token", accessToken());
        params.put("group_ex_id", groupExternalID);
        CoreAPI.postGroupRemoveExId(params, new DefaultAPICallback<PostGroupRemoveExId>(null) {
            public void onResponse(final PostGroupRemoveExId t) {
                NakamapApi.sHandler.post(new Runnable() {
                    public void run() {
                        String success = t.success ? "1" : "0";
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put(SNSInterface.SUCCESS, success);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.onResult(0, jsonObject);
                    }
                });
            }

            public void onError(int statusCode, String responseBody) {
                NakamapApi.handleOnError(callback, statusCode, responseBody);
            }

            public void onError(Throwable e) {
                NakamapApi.handleOnError(callback);
            }
        });
    }

    public static final void joinGroupWithExternalID(String groupExternalID, String name, final APICallback callback) {
        Map<String, String> params = new HashMap();
        params.put("token", accessToken());
        params.put("name", name);
        params.put("group_ex_id", groupExternalID);
        CoreAPI.postGroupJoinWithExId(params, new DefaultAPICallback<PostGroupJoinWithExId>(null) {
            public void onResponse(PostGroupJoinWithExId t) {
                NakamapApi.sHandler.post(new Runnable() {
                    public void run() {
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put(SNSInterface.SUCCESS, "1");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.onResult(0, jsonObject);
                    }
                });
            }

            public void onError(int statusCode, String responseBody) {
                NakamapApi.handleOnError(callback, statusCode, responseBody);
            }

            public void onError(Throwable e) {
                NakamapApi.handleOnError(callback);
            }
        });
    }

    public static final void partGroupWithExternalID(String groupExternalID, final APICallback callback) {
        Map<String, String> params = new HashMap();
        params.put("token", accessToken());
        params.put("group_ex_id", groupExternalID);
        CoreAPI.postGroupPartExId(params, new DefaultAPICallback<PostGroupPartExId>(null) {
            public void onResponse(final PostGroupPartExId t) {
                NakamapApi.sHandler.post(new Runnable() {
                    public void run() {
                        String success = t.success ? "1" : "0";
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put(SNSInterface.SUCCESS, success);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.onResult(0, jsonObject);
                    }
                });
            }

            public void onError(int statusCode, String responseBody) {
                NakamapApi.handleOnError(callback, statusCode, responseBody);
            }

            public void onError(Throwable e) {
                NakamapApi.handleOnError(callback);
            }
        });
    }

    public static final void kickUserWithExternalID(String groupExternalID, String userExternalId, final APICallback callback) {
        Map<String, String> params = new HashMap();
        params.put("token", accessToken());
        params.put("group_ex_id", groupExternalID);
        params.put("user_ex_id", userExternalId);
        CoreAPI.postGroupKickExId(params, new DefaultAPICallback<PostGroupKickExId>(null) {
            public void onResponse(final PostGroupKickExId t) {
                NakamapApi.sHandler.post(new Runnable() {
                    public void run() {
                        String success = t.success ? "1" : "0";
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put(SNSInterface.SUCCESS, success);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.onResult(0, jsonObject);
                    }
                });
            }

            public void onError(int statusCode, String responseBody) {
                NakamapApi.handleOnError(callback, statusCode, responseBody);
            }

            public void onError(Throwable e) {
                NakamapApi.handleOnError(callback);
            }
        });
    }

    public static final void addUsersWithExternalIDs(String groupExternalID, List<String> userExternalIds, final APICallback callback) {
        Map<String, String> params = new HashMap();
        params.put("token", accessToken());
        params.put("group_ex_id", groupExternalID);
        params.put("user_ex_ids", TextUtils.join(",", userExternalIds));
        CoreAPI.postGroupMembersExId(params, new DefaultAPICallback<PostGroupMembersExId>(null) {
            public void onResponse(final PostGroupMembersExId t) {
                NakamapApi.sHandler.post(new Runnable() {
                    public void run() {
                        String success = t.success ? "1" : "0";
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put(SNSInterface.SUCCESS, success);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.onResult(0, jsonObject);
                    }
                });
            }

            public void onError(int statusCode, String responseBody) {
                NakamapApi.handleOnError(callback, statusCode, responseBody);
            }

            public void onError(Throwable e) {
                NakamapApi.handleOnError(callback);
            }
        });
    }

    public static final void updateGroupWithExternalID(String groupExId, String name, String memo, final APICallback callback) {
        Map<String, String> params = new HashMap();
        params.put("token", accessToken());
        params.put("group_ex_id", groupExId);
        params.put("name", name);
        params.put("description", memo);
        CoreAPI.postGroupExId(params, new DefaultAPICallback<PostGroupExId>(null) {
            public void onResponse(PostGroupExId t) {
                NakamapApi.sHandler.post(new Runnable() {
                    public void run() {
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put(SNSInterface.SUCCESS, "1");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.onResult(0, jsonObject);
                    }
                });
            }

            public void onError(int statusCode, String responseBody) {
                NakamapApi.handleOnError(callback, statusCode, responseBody);
            }

            public void onError(Throwable e) {
                NakamapApi.handleOnError(callback);
            }
        });
    }

    public static final void changeLeaderWithExternalID(String groupExternalID, String userExternalID, final APICallback callback) {
        Map<String, String> params = new HashMap();
        params.put("token", accessToken());
        params.put("group_ex_id", groupExternalID);
        params.put("user_ex_id", userExternalID);
        CoreAPI.postGroupTransferExId(params, new DefaultAPICallback<PostGroupTransferExId>(null) {
            public void onResponse(PostGroupTransferExId t) {
                NakamapApi.sHandler.post(new Runnable() {
                    public void run() {
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put(SNSInterface.SUCCESS, "1");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.onResult(0, jsonObject);
                    }
                });
            }

            public void onError(int statusCode, String responseBody) {
                NakamapApi.handleOnError(callback, statusCode, responseBody);
            }

            public void onError(Throwable e) {
                NakamapApi.handleOnError(callback);
            }
        });
    }

    public static final void getUnreadChatCountWithExternalID(String groupExternalID, final APICallback callback) {
        final UserValue currentUser = getCurrentUser();
        String token = currentUser != null ? currentUser.getToken() : "";
        Map<String, String> params = new HashMap();
        params.put("token", token);
        params.put("group_ex_id", groupExternalID);
        CoreAPI.getGroupWithExIdV2(params, new DefaultAPICallback<GetGroup>(null) {
            public void onResponse(GetGroup t) {
                super.onResponse(t);
                String groupUid = t.group.getUid();
                String lastChatId = (String) TransactionDatastore.getKKValue(Chat.LATEST_CHAT_ID, currentUser.getUid() + ":" + groupUid, Config.INIT_FAIL_NO_NETWORK);
                Log.v("lobi-sdk", "[unread] groupUid: " + groupUid);
                Log.v("lobi-sdk", "[unread] lastChatId: " + lastChatId);
                Comparator<ChatValue> comparator = new Comparator<ChatValue>() {
                    public int compare(ChatValue lhs, ChatValue rhs) {
                        String id0 = lhs.getId();
                        String id1 = rhs.getId();
                        int length0 = id0.length();
                        int length1 = id1.length();
                        if (length0 < length1) {
                            return 1;
                        }
                        if (length1 < length0) {
                            return -1;
                        }
                        return id1.compareTo(id0);
                    }
                };
                List<ChatValue> chats = t.group.getChats();
                Collections.sort(chats, comparator);
                ChatValue.Builder toFind = new ChatValue.Builder();
                toFind.setId(lastChatId);
                int index = Collections.binarySearch(chats, toFind.build(), comparator);
                if (index < 0) {
                    index = chats.size();
                }
                if (TextUtils.equals(lastChatId, Config.INIT_FAIL_NO_NETWORK)) {
                    index = -1;
                }
                JSONObject response = new JSONObject();
                try {
                    response.put("count", index);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callback.onResult(0, response);
            }

            public void onError(int statusCode, String responseBody) {
                NakamapApi.handleOnError(callback, statusCode, responseBody);
            }

            public void onError(Throwable e) {
                NakamapApi.handleOnError(callback);
            }
        });
    }

    private static void handleOnError(final APICallback callback, final int statusCode, final String responseBody) {
        sHandler.post(new Runnable() {
            public void run() {
                JSONObject error = null;
                try {
                    error = new JSONObject(responseBody);
                } catch (Exception e) {
                    callback.onResult(APICallback.RESPONSE_ERROR, null);
                }
                if (statusCode >= 500) {
                    callback.onResult(APICallback.RESPONSE_ERROR, null);
                } else {
                    callback.onResult(APICallback.FATAL_ERROR, error);
                }
            }
        });
    }

    private static void handleOnError(final APICallback callback) {
        sHandler.post(new Runnable() {
            public void run() {
                callback.onResult(100, null);
            }
        });
    }

    private static UserValue getCurrentUser() {
        try {
            return AccountDatastore.getCurrentUser();
        } catch (CurrentUserNotSet e) {
            return null;
        }
    }

    private static String accessToken() {
        String token = "";
        UserValue user = getCurrentUser();
        if (user != null) {
            return user.getToken();
        }
        return token;
    }

    public static void signupAgain() {
        String name = LobiCore.sharedInstance().getNewAccountBaseName();
        if (TextUtils.isEmpty(name)) {
            name = "Nakamap";
        }
        try {
            signupWithBaseNameSync(name, (String) AccountDatastore.getValue(Key.ENCRYPTED_EX_ID), (String) AccountDatastore.getValue("iv"));
            AccountDatastore.setValue("lastRefreshedDate", Long.valueOf(System.currentTimeMillis()));
        } catch (APISyncException e) {
        }
    }

    static String getHostApplicationVersion() {
        Context context = LobiCore.sharedInstance().getContext();
        String versionName = "";
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 1).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return versionName;
        }
    }

    public static final void create1on1GroupWithUserExternalId(String userExternalId, final APICallback callback) {
        Map<String, String> params = new HashMap();
        params.put("token", accessToken());
        params.put("user_ex_id", userExternalId);
        CoreAPI.postGroups1on1s(params, new DefaultAPICallback<PostGroups1on1s>(null) {
            public void onResponse(PostGroups1on1s t) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("uid", t.groupDetail.getUid());
                    jsonObject.put("name", t.groupDetail.getName());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callback.onResult(0, jsonObject);
            }

            public void onError(int statusCode, String responseBody) {
                NakamapApi.handleOnError(callback, statusCode, responseBody);
            }

            public void onError(Throwable e) {
                NakamapApi.handleOnError(callback);
            }
        });
    }
}
