package com.kayac.lobi.sdk.rec.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.Scopes;
import com.kayac.lobi.libnakamap.PathRoutedActivity;
import com.kayac.lobi.libnakamap.components.ActionBar;
import com.kayac.lobi.libnakamap.components.ActionBar.CloseButton;
import com.kayac.lobi.libnakamap.components.ActionBar.MenuButtonContent;
import com.kayac.lobi.libnakamap.components.ActionBar.TextButton;
import com.kayac.lobi.libnakamap.components.MenuDrawer;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.net.APIDef.GetRanking;
import com.kayac.lobi.libnakamap.net.APIDef.GetUserV3.RequestKey;
import com.kayac.lobi.libnakamap.net.APIUtil;
import com.kayac.lobi.libnakamap.net.APIUtil.Thanks;
import com.kayac.lobi.libnakamap.net.LobiWebViewClient;
import com.kayac.lobi.libnakamap.rec.LobiRecAPI;
import com.kayac.lobi.libnakamap.rec.a.b;
import com.kayac.lobi.libnakamap.utils.ImageUtils;
import com.kayac.lobi.libnakamap.utils.JSONUtil;
import com.kayac.lobi.libnakamap.utils.NakamapSDKDatastore.Key;
import com.kayac.lobi.libnakamap.utils.NotificationUtil.NotificationSDKType;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.sdk.LobiCore;
import com.kayac.lobi.sdk.LobiCoreAPI;
import com.kayac.lobi.sdk.rec.R;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RecPlayActivity extends PathRoutedActivity {
    public static final String EXTRA_CAN_GO_BACK_TO_ACTIVITY = "can_go_back_to_activity";
    public static final String EXTRA_CATEGORY = "extra_category";
    public static final String EXTRA_EVENT_FIELDS = "extra_event_fields";
    public static final String EXTRA_LETSPLAY = "extra_letsplay";
    public static final String EXTRA_META_JSON = "extra_meta_json";
    public static final String EXTRA_OPEN_FROM = "extra_open_from";
    public static final String EXTRA_ROWS = "extra_rows";
    public static final String EXTRA_SORT = "extra_sort";
    public static final String EXTRA_URI = "extra_uri";
    public static final String EXTRA_USER_EXID = "extra_user_exid";
    public static final String EXTRA_USER_UID = "extra_user_uid";
    public static final String EXTRA_VIDEO_UID = "extra_video_uid";
    public static final String PATH_PLAY_LIST_VIDEO = "/rec_playlist/video";
    private static final int REQUEST_GALLERY = 1001;
    private static final String TAG = RecPlayActivity.class.getSimpleName();
    private static final Map<String, String> URLs = new c();
    private static final b sLog = new b(TAG);
    private ActionBar mActionBar;
    private boolean mCanGoBackToActivity;
    private DrawerLayout mDrawerLayout;
    private String mEventFields;
    private boolean mLoadingModal;
    private TextButton mNotificationActionBarButton;
    private ProgressDialog mProgress = null;
    private boolean mShouldRefresh = false;
    private Uri mUri;
    private String mVideoUid;
    private WebView mWebView;
    private LobiWebViewClient mWebViewClient = new n(this);

    private boolean canGoBack() {
        this.mWebView = (WebView) findViewById(R.id.lobi_rec_play_content);
        return this.mWebView.canGoBack();
    }

    private Builder createVideoUriBuilder() {
        Builder builder = new Builder();
        builder.scheme("https");
        builder.authority(new Thanks().getAuthority());
        builder.appendQueryParameter("token", AccountDatastore.getCurrentUser().getToken());
        return builder;
    }

    private void goBack() {
        this.mWebView = (WebView) findViewById(R.id.lobi_rec_play_content);
        this.mWebView.goBack();
    }

    private void hideIndicator() {
        runOnUiThread(new t(this));
    }

    private static boolean isListPageUri(Uri uri) {
        return uri.getPath().equals("/videos") || uri.getPath().equals("/videos/top");
    }

    private void loadRequest() {
        Executors.newSingleThreadScheduledExecutor().execute(new h(this));
    }

    private static Map<String, String> parseMetaJson(JSONObject jSONObject) {
        Map<String, String> hashMap = new HashMap();
        Iterator keys = jSONObject.keys();
        while (keys.hasNext()) {
            String str = (String) keys.next();
            if (jSONObject.opt(str) instanceof String) {
                hashMap.put(str, jSONObject.optString(str));
            } else if (jSONObject.opt(str) instanceof JSONArray) {
                hashMap.put(str, TextUtils.join(" ", JSONUtil.toStringList(jSONObject.optJSONArray(str))));
            }
        }
        return hashMap;
    }

    private void setupActionBar() {
        this.mActionBar = (ActionBar) findViewById(R.id.lobi_action_bar);
        this.mNotificationActionBarButton = this.mActionBar.setupNotifications(NotificationSDKType.RecSDK);
        if (!this.mCanGoBackToActivity) {
            this.mActionBar.getNotifications(NotificationSDKType.RecSDK, null);
        }
        ((MenuButtonContent) this.mActionBar.getContent()).setOnMenuButtonClickListener(new o(this));
        View closeButton = new CloseButton(this);
        closeButton.setOnClickListener(new p(this));
        this.mActionBar.addActionBarButton(closeButton);
        updateActionbarContent(null);
    }

    private String setupVideoDetailUrl() {
        Builder createVideoUriBuilder = createVideoUriBuilder();
        createVideoUriBuilder.path(String.format("/video/%s", new Object[]{this.mVideoUid}));
        String uri = createVideoUriBuilder.build().toString();
        sLog.b("play.lobi.co: " + uri);
        return uri;
    }

    private String setupVideoListUrl() {
        Builder createVideoUriBuilder = createVideoUriBuilder();
        createVideoUriBuilder.path("/videos");
        Intent intent = getIntent();
        Object stringExtra = intent.getStringExtra(EXTRA_USER_UID);
        if (TextUtils.isEmpty(stringExtra)) {
            boolean z = false;
        } else {
            createVideoUriBuilder.appendQueryParameter("user", stringExtra);
            int i = 1;
        }
        Object stringExtra2 = intent.getStringExtra(EXTRA_USER_EXID);
        if (!TextUtils.isEmpty(stringExtra2)) {
            createVideoUriBuilder.appendQueryParameter("user_ex_id", stringExtra2);
            i = 1;
        }
        stringExtra2 = intent.getStringExtra(EXTRA_CATEGORY);
        if (!TextUtils.isEmpty(stringExtra2)) {
            createVideoUriBuilder.appendQueryParameter("category", stringExtra2);
            i = 1;
        }
        if (intent.getBooleanExtra(EXTRA_LETSPLAY, false)) {
            createVideoUriBuilder.appendQueryParameter("letsplay", "true");
            i = 1;
        }
        if (intent.getIntExtra(EXTRA_ROWS, 0) > 0) {
            createVideoUriBuilder.appendQueryParameter("rows", String.format("%d", new Object[]{Integer.valueOf(intent.getIntExtra(EXTRA_ROWS, 0))}));
            i = 1;
        }
        Object stringExtra3 = intent.getStringExtra(EXTRA_SORT);
        if (!TextUtils.isEmpty(stringExtra3)) {
            createVideoUriBuilder.appendQueryParameter("sort", stringExtra3);
            i = 1;
        }
        stringExtra3 = intent.getStringExtra(EXTRA_OPEN_FROM);
        if (!TextUtils.isEmpty(stringExtra3)) {
            createVideoUriBuilder.appendQueryParameter("open_from", stringExtra3);
            i = 1;
        }
        stringExtra2 = intent.getStringExtra(EXTRA_META_JSON);
        if (!TextUtils.isEmpty(stringExtra2)) {
            JSONObject jSONObject;
            try {
                jSONObject = new JSONObject(stringExtra2);
            } catch (JSONException e) {
                jSONObject = null;
            }
            if (jSONObject != null) {
                Map parseMetaJson = parseMetaJson(jSONObject);
                createVideoUriBuilder.appendQueryParameter("meta_fields", TextUtils.join(",", parseMetaJson.keySet()));
                for (Entry entry : parseMetaJson.entrySet()) {
                    createVideoUriBuilder.appendQueryParameter("meta__" + ((String) entry.getKey()), (String) entry.getValue());
                }
                i = 1;
            }
        }
        if (i == 0) {
            createVideoUriBuilder.path("/videos/top");
        }
        if (LobiRecAPI.isSecretMode()) {
            createVideoUriBuilder.appendQueryParameter(RequestKey.SECRET_MODE, "1");
        }
        String uri = createVideoUriBuilder.build().toString();
        sLog.b("play.lobi.co: " + uri);
        return uri;
    }

    private String setupVideoTopUrl() {
        Builder createVideoUriBuilder = createVideoUriBuilder();
        createVideoUriBuilder.path("/videos/top");
        createVideoUriBuilder.appendQueryParameter("event", this.mEventFields);
        if (LobiRecAPI.isSecretMode()) {
            createVideoUriBuilder.appendQueryParameter(RequestKey.SECRET_MODE, "1");
        }
        String uri = createVideoUriBuilder.build().toString();
        sLog.b("play.lobi.co: " + uri);
        return uri;
    }

    private void showAccuseVideoAlert(String str) {
        runOnUiThread(new w(this, str));
    }

    private void showChangeNameAlert() {
        CharSequence string = getString(R.string.lobirec_insert_name);
        CharSequence string2 = getString(17039360);
        CharSequence string3 = getString(17039370);
        View editText = new EditText(this);
        editText.setLayoutParams(new LayoutParams(-1, -2));
        editText.setMaxLines(1);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(string);
        builder.setView(editText);
        builder.setPositiveButton(string3, new d(this, editText));
        builder.setNegativeButton(string2, new g(this));
        builder.create().show();
    }

    private static void showErrorAlert(Activity activity) {
        activity.runOnUiThread(new u(activity));
    }

    private void showIndicator() {
    }

    private void signupIfNecessary() {
        UserValue currentUser = AccountDatastore.getCurrentUser();
        if (currentUser == null || currentUser.getToken().length() == 0) {
            showIndicator();
            LobiCoreAPI.signupWithBaseName(LobiCore.sharedInstance().getNewAccountBaseName(), new r(this));
            return;
        }
        runOnUiThread(new q(this));
    }

    private void updateActionbarContent(String str) {
        MenuButtonContent menuButtonContent = (MenuButtonContent) this.mActionBar.getContent();
        ImageView imageView = (ImageView) menuButtonContent.findViewById(R.id.lobi_action_bar_menu_button);
        TextView textView = (TextView) menuButtonContent.findViewById(R.id.lobi_action_bar_title);
        if (canGoBack() || this.mCanGoBackToActivity) {
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.lobi_icn_actionbar_back));
            this.mNotificationActionBarButton.setVisibility(8);
        } else {
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.lobi_icn_actionbar_menu));
            this.mNotificationActionBarButton.setVisibility(0);
        }
        if (str != null) {
            String replaceFirst = Pattern.compile("\\?.*").matcher(str).replaceFirst("");
            if (replaceFirst.equals(URLs.get(Scopes.PROFILE))) {
                textView.setText(R.string.lobirec_profile);
            } else if (replaceFirst.equals(URLs.get("list")) || replaceFirst.startsWith((String) URLs.get("video")) || replaceFirst.equals(URLs.get(GetRanking.ORIGIN_TOP))) {
                textView.setText(R.string.lobirec_video_list);
            } else if (replaceFirst.equals(URLs.get("help"))) {
                textView.setText(R.string.lobirec_help);
            } else {
                textView.setText(null);
            }
        }
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        Cursor query;
        InputStream inputStream;
        OutputStream outputStream;
        Throwable th;
        OutputStream outputStream2;
        Throwable th2;
        Cursor cursor = null;
        switch (i) {
            case REQUEST_GALLERY /*1001*/:
                if (i2 == -1) {
                    OutputStream outputStream3 = null;
                    InputStream inputStreamFromUri;
                    try {
                        query = getContentResolver().query(intent.getData(), new String[]{"_data"}, null, null, null);
                        try {
                            if (query.moveToFirst()) {
                                boolean z;
                                File file;
                                InputStream inputStream2;
                                OutputStream outputStream4;
                                String string = query.getString(0);
                                if (string == null) {
                                    inputStreamFromUri = ImageUtils.getInputStreamFromUri(this, intent.getData());
                                    if (inputStreamFromUri == null) {
                                        try {
                                            Toast.makeText(this, getString(R.string.lobirec_cant_open_image), 0).show();
                                            if (inputStreamFromUri != null) {
                                                try {
                                                    inputStreamFromUri.close();
                                                } catch (IOException e) {
                                                }
                                            }
                                            if (null != null) {
                                                try {
                                                    outputStream3.close();
                                                } catch (IOException e2) {
                                                }
                                            }
                                            if (query == null) {
                                                return;
                                            }
                                        } catch (FileNotFoundException e3) {
                                            inputStream = inputStreamFromUri;
                                            try {
                                                Toast.makeText(this, getString(R.string.lobirec_cant_open_image), 0).show();
                                                if (inputStream != null) {
                                                    try {
                                                        inputStream.close();
                                                    } catch (IOException e4) {
                                                    }
                                                }
                                                if (outputStream != null) {
                                                    try {
                                                        outputStream.close();
                                                    } catch (IOException e5) {
                                                    }
                                                }
                                                if (query != null) {
                                                    query.close();
                                                    return;
                                                }
                                                return;
                                            } catch (Throwable th3) {
                                                th = th3;
                                                inputStreamFromUri = inputStream;
                                                outputStream2 = outputStream;
                                                cursor = query;
                                                th2 = th;
                                                if (inputStreamFromUri != null) {
                                                    try {
                                                        inputStreamFromUri.close();
                                                    } catch (IOException e6) {
                                                    }
                                                }
                                                if (outputStream2 != null) {
                                                    try {
                                                        outputStream2.close();
                                                    } catch (IOException e7) {
                                                    }
                                                }
                                                if (cursor != null) {
                                                    cursor.close();
                                                }
                                                throw th2;
                                            }
                                        } catch (IOException e8) {
                                            try {
                                                Toast.makeText(this, getString(R.string.lobirec_cant_open_image), 0).show();
                                                if (inputStreamFromUri != null) {
                                                    try {
                                                        inputStreamFromUri.close();
                                                    } catch (IOException e9) {
                                                    }
                                                }
                                                if (outputStream != null) {
                                                    try {
                                                        outputStream.close();
                                                    } catch (IOException e10) {
                                                    }
                                                }
                                                if (query != null) {
                                                    query.close();
                                                    return;
                                                }
                                                return;
                                            } catch (Throwable th4) {
                                                th = th4;
                                                outputStream2 = outputStream;
                                                cursor = query;
                                                th2 = th;
                                                if (inputStreamFromUri != null) {
                                                    inputStreamFromUri.close();
                                                }
                                                if (outputStream2 != null) {
                                                    outputStream2.close();
                                                }
                                                if (cursor != null) {
                                                    cursor.close();
                                                }
                                                throw th2;
                                            }
                                        } catch (Throwable th42) {
                                            th = th42;
                                            outputStream2 = null;
                                            cursor = query;
                                            th2 = th;
                                            if (inputStreamFromUri != null) {
                                                inputStreamFromUri.close();
                                            }
                                            if (outputStream2 != null) {
                                                outputStream2.close();
                                            }
                                            if (cursor != null) {
                                                cursor.close();
                                            }
                                            throw th2;
                                        }
                                        query.close();
                                        return;
                                    }
                                    File file2 = new File(getCacheDir(), "lobi-ranking");
                                    if (!file2.isDirectory()) {
                                        file2.delete();
                                    }
                                    if (!file2.exists()) {
                                        file2.mkdir();
                                    }
                                    File file3 = new File(file2, UUID.randomUUID().toString());
                                    outputStream2 = new FileOutputStream(file3);
                                    try {
                                        byte[] bArr = new byte[4096];
                                        while (true) {
                                            int read = inputStreamFromUri.read(bArr);
                                            if (-1 != read) {
                                                outputStream2.write(bArr, 0, read);
                                            } else {
                                                inputStreamFromUri.close();
                                                try {
                                                    outputStream2.close();
                                                    z = true;
                                                    file = file3;
                                                    inputStream2 = null;
                                                    outputStream4 = null;
                                                } catch (FileNotFoundException e11) {
                                                    OutputStream outputStream5 = outputStream2;
                                                    inputStream = null;
                                                    outputStream = outputStream5;
                                                    Toast.makeText(this, getString(R.string.lobirec_cant_open_image), 0).show();
                                                    if (inputStream != null) {
                                                        inputStream.close();
                                                    }
                                                    if (outputStream != null) {
                                                        outputStream.close();
                                                    }
                                                    if (query != null) {
                                                        query.close();
                                                        return;
                                                    }
                                                    return;
                                                } catch (IOException e12) {
                                                    inputStreamFromUri = null;
                                                    outputStream = outputStream2;
                                                    Toast.makeText(this, getString(R.string.lobirec_cant_open_image), 0).show();
                                                    if (inputStreamFromUri != null) {
                                                        inputStreamFromUri.close();
                                                    }
                                                    if (outputStream != null) {
                                                        outputStream.close();
                                                    }
                                                    if (query != null) {
                                                        query.close();
                                                        return;
                                                    }
                                                    return;
                                                } catch (Throwable th32) {
                                                    th = th32;
                                                    inputStreamFromUri = null;
                                                    cursor = query;
                                                    th2 = th;
                                                    if (inputStreamFromUri != null) {
                                                        inputStreamFromUri.close();
                                                    }
                                                    if (outputStream2 != null) {
                                                        outputStream2.close();
                                                    }
                                                    if (cursor != null) {
                                                        cursor.close();
                                                    }
                                                    throw th2;
                                                }
                                            }
                                        }
                                    } catch (FileNotFoundException e13) {
                                        outputStream = outputStream2;
                                        inputStream = inputStreamFromUri;
                                        Toast.makeText(this, getString(R.string.lobirec_cant_open_image), 0).show();
                                        if (inputStream != null) {
                                            inputStream.close();
                                        }
                                        if (outputStream != null) {
                                            outputStream.close();
                                        }
                                        if (query != null) {
                                            query.close();
                                            return;
                                        }
                                        return;
                                    } catch (IOException e14) {
                                        outputStream = outputStream2;
                                        Toast.makeText(this, getString(R.string.lobirec_cant_open_image), 0).show();
                                        if (inputStreamFromUri != null) {
                                            inputStreamFromUri.close();
                                        }
                                        if (outputStream != null) {
                                            outputStream.close();
                                        }
                                        if (query != null) {
                                            query.close();
                                            return;
                                        }
                                        return;
                                    } catch (Throwable th5) {
                                        cursor = query;
                                        th2 = th5;
                                        if (inputStreamFromUri != null) {
                                            inputStreamFromUri.close();
                                        }
                                        if (outputStream2 != null) {
                                            outputStream2.close();
                                        }
                                        if (cursor != null) {
                                            cursor.close();
                                        }
                                        throw th2;
                                    }
                                }
                                file = new File(string);
                                outputStream4 = null;
                                inputStream2 = null;
                                z = false;
                                if (file.exists()) {
                                    showIndicator();
                                    LobiCoreAPI.updateUserIcon(file, new k(this, z, file));
                                } else {
                                    Toast.makeText(this, getString(R.string.lobirec_cant_open_image), 0).show();
                                    if (null != null) {
                                        try {
                                            inputStream2.close();
                                        } catch (IOException e15) {
                                        }
                                    }
                                    if (null != null) {
                                        try {
                                            outputStream4.close();
                                        } catch (IOException e16) {
                                        }
                                    }
                                    if (query != null) {
                                        query.close();
                                        return;
                                    }
                                    return;
                                }
                            }
                            outputStream2 = null;
                            inputStreamFromUri = null;
                            if (null != null) {
                                try {
                                    inputStreamFromUri.close();
                                } catch (IOException e17) {
                                }
                            }
                            if (null != null) {
                                try {
                                    outputStream2.close();
                                } catch (IOException e18) {
                                }
                            }
                            if (query != null) {
                                query.close();
                                return;
                            }
                            return;
                        } catch (FileNotFoundException e19) {
                            inputStream = null;
                            Toast.makeText(this, getString(R.string.lobirec_cant_open_image), 0).show();
                            if (inputStream != null) {
                                inputStream.close();
                            }
                            if (outputStream != null) {
                                outputStream.close();
                            }
                            if (query != null) {
                                query.close();
                                return;
                            }
                            return;
                        } catch (IOException e20) {
                            inputStreamFromUri = null;
                            Toast.makeText(this, getString(R.string.lobirec_cant_open_image), 0).show();
                            if (inputStreamFromUri != null) {
                                inputStreamFromUri.close();
                            }
                            if (outputStream != null) {
                                outputStream.close();
                            }
                            if (query != null) {
                                query.close();
                                return;
                            }
                            return;
                        } catch (Throwable th422) {
                            inputStreamFromUri = null;
                            Cursor cursor2 = query;
                            th2 = th422;
                            outputStream2 = null;
                            cursor = cursor2;
                            if (inputStreamFromUri != null) {
                                inputStreamFromUri.close();
                            }
                            if (outputStream2 != null) {
                                outputStream2.close();
                            }
                            if (cursor != null) {
                                cursor.close();
                            }
                            throw th2;
                        }
                    } catch (FileNotFoundException e21) {
                        query = null;
                        inputStream = null;
                        Toast.makeText(this, getString(R.string.lobirec_cant_open_image), 0).show();
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        if (outputStream != null) {
                            outputStream.close();
                        }
                        if (query != null) {
                            query.close();
                            return;
                        }
                        return;
                    } catch (IOException e22) {
                        query = null;
                        inputStreamFromUri = null;
                        Toast.makeText(this, getString(R.string.lobirec_cant_open_image), 0).show();
                        if (inputStreamFromUri != null) {
                            inputStreamFromUri.close();
                        }
                        if (outputStream != null) {
                            outputStream.close();
                        }
                        if (query != null) {
                            query.close();
                            return;
                        }
                        return;
                    } catch (Throwable th6) {
                        th2 = th6;
                        outputStream2 = null;
                        inputStreamFromUri = null;
                        if (inputStreamFromUri != null) {
                            inputStreamFromUri.close();
                        }
                        if (outputStream2 != null) {
                            outputStream2.close();
                        }
                        if (cursor != null) {
                            cursor.close();
                        }
                        throw th2;
                    }
                }
                return;
            default:
                return;
        }
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.lobi_rec_activity_play);
        Intent intent = getIntent();
        this.mUri = (Uri) intent.getParcelableExtra(EXTRA_URI);
        this.mVideoUid = intent.getStringExtra(EXTRA_VIDEO_UID);
        this.mCanGoBackToActivity = intent.getBooleanExtra(EXTRA_CAN_GO_BACK_TO_ACTIVITY, false);
        if (intent.hasExtra(EXTRA_EVENT_FIELDS)) {
            Object stringExtra = intent.getStringExtra(EXTRA_EVENT_FIELDS);
            AccountDatastore.setValue(Key.EVENT_FIELDS_CACHE, stringExtra);
            this.mEventFields = stringExtra;
        } else {
            this.mEventFields = null;
        }
        if (this.mCanGoBackToActivity) {
            MenuDrawer.disableMenuDrawer((DrawerLayout) findViewById(R.id.drawer_layout));
        } else {
            this.mDrawerLayout = MenuDrawer.setMenuDrawer(this, (DrawerLayout) findViewById(R.id.drawer_layout), (ViewGroup) findViewById(R.id.content_frame));
        }
        setupActionBar();
        this.mWebView = (WebView) findViewById(R.id.lobi_rec_play_content);
        this.mWebView.setWebViewClient(this.mWebViewClient);
        this.mWebView.getSettings().setJavaScriptEnabled(true);
        this.mWebView.setWebChromeClient(new WebChromeClient());
        this.mWebView.setScrollBarStyle(0);
        this.mWebView.getSettings().setAppCachePath(getCacheDir().getAbsolutePath());
        this.mWebView.getSettings().setAppCacheEnabled(true);
        this.mWebView.getSettings().setCacheMode(2);
        this.mWebView.getSettings().setUserAgentString(APIUtil.getUserAgent());
        signupIfNecessary();
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4) {
            return super.onKeyDown(i, keyEvent);
        }
        if (canGoBack()) {
            goBack();
        } else {
            finish();
        }
        return false;
    }

    public void onResume() {
        super.onResume();
        this.mLoadingModal = false;
        if (!this.mCanGoBackToActivity) {
            MenuDrawer.setMenuItems(this.mDrawerLayout);
        }
        if (this.mShouldRefresh) {
            this.mShouldRefresh = false;
            this.mWebView.reload();
        }
    }
}
