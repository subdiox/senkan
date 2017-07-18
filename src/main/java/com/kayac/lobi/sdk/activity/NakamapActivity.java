package com.kayac.lobi.sdk.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.kayac.lobi.libnakamap.cache.CacheManager;
import com.kayac.lobi.libnakamap.components.CustomDialog;
import com.kayac.lobi.libnakamap.components.CustomProgressDialog;
import com.kayac.lobi.libnakamap.components.PathRouter;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.net.APIDef.PostSignupFree.RequestKey;
import com.kayac.lobi.libnakamap.net.APIRes.GetMe;
import com.kayac.lobi.libnakamap.net.APIRes.PostSignupFree;
import com.kayac.lobi.libnakamap.net.CoreAPI;
import com.kayac.lobi.libnakamap.net.CoreAPI.DefaultAPICallback;
import com.kayac.lobi.libnakamap.net.security.HashUtils;
import com.kayac.lobi.libnakamap.utils.Log;
import com.kayac.lobi.libnakamap.utils.NakamapSDKDatastore;
import com.kayac.lobi.libnakamap.utils.NakamapSDKDatastore.Key;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.libnakamap.value.UserValue.Builder;
import com.kayac.lobi.sdk.LobiCore;
import com.kayac.lobi.sdk.R;
import com.kayac.lobi.sdk.Version;
import com.kayac.lobi.sdk.migration.datastore.UserMigrationHelper;
import com.kayac.lobi.sdk.net.NakamapApi;
import com.kayac.lobi.sdk.path.PathRouterConfig;
import java.util.HashMap;
import java.util.Map;

public final class NakamapActivity extends Activity {
    public static final String EXTRA_PENDING_GROUP_EX_ID = "pending_group_ex_id";
    public static final String EXTRA_PENDING_GROUP_NAME = "pending_group_name";
    public static final String EXTRA_PENDING_MESSAGE = "pending_message";
    public static final String EXTRA_TRIED_TO_OPEN_INVALID_GROUP_EXID_WITHOUT_NAME = "tried_to_open_invalid_group_exid_without_name";
    private static final String PREFIX_FOR_PENDING = "pending_";

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Boolean hasCreatedUser = (Boolean) AccountDatastore.getValue(Key.HAS_CREATED_USER, Boolean.FALSE);
        Log.v("nakamap-sdk", "hasCreatedUser: " + hasCreatedUser);
        if (!hasCreatedUser.booleanValue()) {
            createUser(this);
        } else if (getIntent().getBooleanExtra(EXTRA_TRIED_TO_OPEN_INVALID_GROUP_EXID_WITHOUT_NAME, false)) {
            showGroupWarning();
        } else {
            startProfileActivityWithPendingExtras();
            Boolean accepted = (Boolean) AccountDatastore.getValue(Key.HAS_ACCEPTED_TERMS_OF_USE, Boolean.FALSE);
            Log.v("nakamap-sdk", "accepted: " + accepted);
            if (accepted.booleanValue()) {
                updateMe();
            }
            finish();
        }
    }

    private void createUser(Activity activity) {
        LobiCore nakamap = LobiCore.sharedInstance();
        String installId = UserMigrationHelper.getInstallId();
        Map<String, String> params = new HashMap();
        params.put("client_id", nakamap.getClientId());
        params.put("install_id", installId);
        params.put(RequestKey.SIGNATURE, HashUtils.hmacSha1(new String(NakamapApi.SALT), installId));
        params.put("name", nakamap.getNewAccountBaseName());
        params.put("platform", "android");
        params.put("version", Version.versionName);
        CoreAPI.postSignupFree(params, new DefaultAPICallback<PostSignupFree>(activity) {
            public void onResponse(PostSignupFree t) {
                Log.v("nakamap-sdk", "onResponse: sign up free success!");
                Log.v("nakamap-sdk", "myuid:: " + t.user.getUid());
                NakamapActivity.this.saveAccessTokenAndShowGroupList(t.user);
                if (UserMigrationHelper.hasOldUserAccount()) {
                    UserMigrationHelper.deleteOldDatabase();
                }
                NakamapActivity.this.finish();
            }

            public void onError(int statusCode, String responseBody) {
                super.onError(statusCode, responseBody);
                NakamapActivity.this.finish();
            }

            public void onError(Throwable e) {
                super.onError(e);
                NakamapActivity.this.finish();
            }
        });
    }

    private void saveAccessTokenAndShowGroupList(UserValue user) {
        NakamapSDKDatastore.saveCurrentUser(user);
        startProfileActivityWithPendingExtras();
    }

    private void startProfileActivityWithPendingExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            extras = new Bundle();
        }
        for (String key : extras.keySet()) {
            if (!key.startsWith(PREFIX_FOR_PENDING)) {
                extras.remove(key);
            }
        }
        extras.putBoolean("EXTRA_FROM_MENU", true);
        extras.putString(PathRouter.PATH, PathRouterConfig.PATH_CHAT_SDK_DEFAULT);
        PathRouter.startPath(extras);
    }

    private void updateMe() {
        Log.v("nakamap-sdk", "updateMe: ");
        final UserValue currentUser = AccountDatastore.getCurrentUser();
        Map<String, String> params = new HashMap();
        params.put("token", currentUser.getToken());
        CustomProgressDialog dialog = new CustomProgressDialog(this);
        dialog.setMessage(getString(R.string.lobi_loading_loading));
        DefaultAPICallback<GetMe> callback = new DefaultAPICallback<GetMe>(null) {
            public void onResponse(GetMe t) {
                Builder builder = new Builder(t.me);
                builder.setToken(currentUser.getToken());
                AccountDatastore.setCurrentUser(builder.build());
                super.onResponse(t);
            }
        };
        callback.setProgress(dialog);
        CoreAPI.getMe(params, callback);
    }

    private void showGroupWarning() {
        final CustomDialog dialog = CustomDialog.createTextDialog(this, getString(R.string.lobisdk_error_group_with_specified_exid_does_not_exist));
        dialog.setPositiveButton(getString(R.string.lobi_ok), new OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setOnDismissListener(new OnDismissListener() {
            public void onDismiss(DialogInterface arg0) {
                PathRouter.startPath(PathRouterConfig.PATH_CHAT_SDK_DEFAULT);
                NakamapActivity.this.finish();
            }
        });
        dialog.show();
    }

    protected void onDestroy() {
        CacheManager.startSyncCache();
        super.onDestroy();
    }
}
