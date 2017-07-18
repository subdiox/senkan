package com.kayac.lobi.sdk.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import com.kayac.lobi.libnakamap.PathRoutedActivity;
import com.kayac.lobi.libnakamap.components.ActionBar;
import com.kayac.lobi.libnakamap.components.ActionBar.Button;
import com.kayac.lobi.libnakamap.components.PathRouter;
import com.kayac.lobi.libnakamap.contacts.SNSInterface;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.net.APIRes.GetSdkStartup;
import com.kayac.lobi.libnakamap.net.CoreAPI;
import com.kayac.lobi.libnakamap.net.CoreAPI.DefaultAPICallback;
import com.kayac.lobi.libnakamap.utils.ModuleUtil;
import com.kayac.lobi.libnakamap.utils.NakamapSDKDatastore;
import com.kayac.lobi.libnakamap.utils.NakamapSDKDatastore.Key;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.sdk.LobiCore;
import com.kayac.lobi.sdk.LobiCoreAPI.APICallback;
import com.kayac.lobi.sdk.R;
import com.kayac.lobi.sdk.auth.AccountUtil;
import com.kayac.lobi.sdk.auth.TermsOfUseFragment;
import com.kayac.lobi.sdk.auth.TermsOfUseFragment.Callback;
import com.kayac.lobi.sdk.net.NakamapApi;
import com.kayac.lobi.sdk.path.PathRouterConfig;
import com.kayac.lobi.sdk.utils.UrlUtils;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONObject;

public class RootActivity extends PathRoutedActivity {
    public static final String APP_LINK_CLICKED = "app_link_clicked";
    public static final String APP_LINK_DATA = "app_link_data";
    public static final String EXTRAS_TARGET_PATH = "target_path";
    public static final String ON_ACCEPT_TERMS_OF_USE = "onAcceptTermsOfUse";
    public static final String PATH_ROOT = "/";
    static ExecutorService sExecutor = Executors.newFixedThreadPool(4);
    private static boolean sStartupConfigUpdated = false;
    private boolean mLobiActivityStartedFlag = false;
    private boolean mLobiInitializedFlag = false;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lobi_root_activity);
        setupActionBar();
        PathRouter.removePathsGreaterThan("/");
        setupLobiCoreActivity();
    }

    public void onResume() {
        super.onResume();
        if (!this.mLobiActivityStartedFlag) {
            this.mLobiActivityStartedFlag = true;
        } else if (this.mLobiInitializedFlag) {
            finish();
        }
    }

    private void setupActionBar() {
        ActionBar actionBar = (ActionBar) findViewById(R.id.lobi_action_bar);
        Button actionBarButton = new Button(this);
        actionBarButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                RootActivity.this.finish();
            }
        });
        actionBarButton.setIconImage(R.drawable.lobi_action_bar_close_selector);
        actionBar.addActionBarButtonWithSeparator(actionBarButton);
    }

    public void setupLobiCoreActivity() {
        UserValue currentUser = AccountDatastore.optCurrentUser();
        if (currentUser == null) {
            createUser(this);
        } else if (AccountUtil.shouldRefreshToken()) {
            refreshToken();
        } else if (!showTermsOfUse()) {
            if (sStartupConfigUpdated) {
                startTargetPath();
                this.mLobiInitializedFlag = true;
                return;
            }
            updateStartupConfigs(currentUser);
        }
    }

    private void startTargetPath() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (!overrideRouting(this, intent)) {
            if (!ModuleUtil.chatIsAvailable() || !ModuleUtil.chatOverrideRouting(this, intent)) {
                if ((!ModuleUtil.rankingIsAvailable() || !ModuleUtil.rankingOverrideRouting(this, intent)) && extras != null) {
                    String targetPath = extras.getString(EXTRAS_TARGET_PATH);
                    if (targetPath != null) {
                        extras.putString(PathRouter.PATH, targetPath);
                        extras.remove(EXTRAS_TARGET_PATH);
                        PathRouter.startPath(extras);
                    }
                }
            }
        }
    }

    private void createUser(Activity activity) {
        LobiCore lobiCore = LobiCore.sharedInstance();
        startLoading();
        NakamapApi.signupWithBaseName(lobiCore.getNewAccountBaseName(), null, null, new APICallback() {
            public void onResult(int code, JSONObject response) {
                RootActivity.this.stopLoading();
                if (code == 0) {
                    RootActivity.this.setupLobiCoreActivity();
                    return;
                }
                RootActivity.this.showAPIError(response);
                RootActivity.this.finish();
            }
        });
    }

    private boolean overrideRouting(RootActivity activity, Intent intent) {
        if ("android.intent.action.VIEW".equals(intent.getAction())) {
            Uri uri = intent.getData();
            if (!("nakamapapp-" + LobiCore.sharedInstance().getClientId()).equals(uri.getScheme())) {
                return true;
            }
            if ("bind".equals(uri.getHost())) {
                if (!"finished".equals(uri.getLastPathSegment())) {
                    return true;
                }
                try {
                    Bundle query = UrlUtils.parseQuery(new URI(uri.toString()));
                    for (String key : query.keySet()) {
                        Log.v("nakamap-sdk", "/bind/finished " + key + " = " + query.get(key));
                    }
                    if (query.containsKey(SNSInterface.SUCCESS)) {
                        AccountUtil.requestCurrentUserBindingState(new DefaultAPICallback<Boolean>(activity) {
                            public void onResponse(Boolean binded) {
                                if (binded != null && binded.booleanValue()) {
                                    AccountDatastore.setValue("ssoBound", Boolean.TRUE);
                                }
                            }
                        });
                    }
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                finish();
                return true;
            }
        }
        return false;
    }

    private void refreshToken() {
        sExecutor.execute(new Runnable() {
            public void run() {
                AccountUtil.refreshToken();
                RootActivity.this.setupLobiCoreActivity();
            }
        });
    }

    private void saveAccessTokenAndShowGroupList(UserValue user) {
        NakamapSDKDatastore.saveCurrentUser(user);
    }

    public static void startActivity(String path) {
        startActivity(path, null);
    }

    public static void startActivity(String path, Bundle extras) {
        if (!TextUtils.isEmpty(path)) {
            if (extras == null) {
                extras = new Bundle();
            }
            extras.putString(PathRouter.PATH, "/");
            extras.putString(EXTRAS_TARGET_PATH, path);
            PathRouter.startPath(extras);
        }
    }

    public boolean showTermsOfUse() {
        if (!shouldShowTerms() || TermsOfUseFragment.hasAccepted().booleanValue()) {
            return false;
        }
        int frameLayoutId = R.id.lobi_sdk_terms_of_use;
        findViewById(frameLayoutId).setVisibility(0);
        TermsOfUseFragment fragment = new TermsOfUseFragment();
        fragment.setCallback(new Callback() {
            public void onAccept() {
                RootActivity.this.setupLobiCoreActivity();
            }

            public void onDismiss() {
                RootActivity.this.finish();
            }
        });
        try {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(frameLayoutId, fragment);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean shouldShowTerms() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return false;
        }
        String targetPath = extras.getString(EXTRAS_TARGET_PATH);
        if (targetPath == null) {
            return false;
        }
        if (TextUtils.equals(targetPath, PathRouterConfig.PATH_CHAT_SDK_DEFAULT)) {
            return true;
        }
        if (TextUtils.equals(targetPath, PathRouterConfig.PATH_RANKING_SDK_DEFAULT)) {
            return true;
        }
        if (TextUtils.equals(targetPath, PathRouterConfig.PATH_REC_SDK_DEFAULT)) {
            return true;
        }
        return false;
    }

    private void updateStartupConfigs(UserValue user) {
        startLoading();
        Map<String, String> params = new HashMap();
        params.put("token", user.getToken());
        params.put("fields", "game,contact_banner,menu_banner,profile_banner");
        CoreAPI.getSdkStartup(params, new DefaultAPICallback<GetSdkStartup>(null) {
            public void onResponse(GetSdkStartup t) {
                if (t.config != null) {
                    RootActivity.sStartupConfigUpdated = true;
                    AccountDatastore.setValue(Key.STARTUP_CONFIG, t.config);
                    RootActivity.this.setupLobiCoreActivity();
                    RootActivity.this.stopLoading();
                    return;
                }
                handleError();
            }

            public void onError(int statusCode, String responseBody) {
                super.onError(statusCode, responseBody);
                Log.e("nakamap-sdk", String.format("error : %d, %s", new Object[]{Integer.valueOf(statusCode), responseBody}));
                handleError();
            }

            public void onError(Throwable e) {
                super.onError(e);
                e.printStackTrace();
                handleError();
            }

            private void handleError() {
                AccountDatastore.deleteValue(Key.STARTUP_CONFIG);
                RootActivity.this.stopLoading();
                RootActivity.this.showNetworkError();
                RootActivity.this.finish();
            }
        });
    }
}
