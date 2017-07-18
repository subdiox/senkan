package com.kayac.lobi.sdk.rec.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.internal.view.SupportMenu;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gcm.GCMConstants;
import com.kayac.lobi.libnakamap.PathRoutedActivity;
import com.kayac.lobi.libnakamap.components.ActionBar;
import com.kayac.lobi.libnakamap.components.ActionBar.BackableContent;
import com.kayac.lobi.libnakamap.components.UIEditText;
import com.kayac.lobi.libnakamap.datastore.AccountDDL.KKey.UpdateAt;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.datastore.AccountDatastoreAsync;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.KKey.Rec;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastore;
import com.kayac.lobi.libnakamap.rec.LobiRec;
import com.kayac.lobi.libnakamap.rec.LobiRecAPI;
import com.kayac.lobi.libnakamap.rec.a.b;
import com.kayac.lobi.libnakamap.rec.b.a.c;
import com.kayac.lobi.libnakamap.rec.c.f;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.sdk.LobiCore;
import com.kayac.lobi.sdk.LobiCoreAPI;
import com.kayac.lobi.sdk.auth.TermsOfUseFragment;
import com.kayac.lobi.sdk.rec.R;
import com.kayac.lobi.sdk.utils.ManifestMetaDataUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executors;
import org.json.JSONObject;

public class RecPostVideoActivity extends PathRoutedActivity implements c {
    public static final int DESCRIPTION_TEXT_COUNT_MAX = 1000;
    public static final String EXTRA_CAMERA = "EXTRA_CAMERA";
    public static final String EXTRA_CATEGORY = "EXTRA_CATEGORY";
    public static final String EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION";
    public static final String EXTRA_METADATA = "EXTRA_METADATA";
    public static final String EXTRA_MIC = "EXTRA_MIC";
    public static final String EXTRA_SCORE = "EXTRA_SCORE";
    public static final String EXTRA_TITLE = "EXTRA_TITLE";
    private static final int REQUEST_SNS = 1001;
    public static final String RESULT_EXTRA_SERVICE = "RESULT_EXTRA_SERVICE";
    public static final int TITLE_TEXT_COUNT_MAX = 40;
    private static final Boolean USE_DUMMY_FILE = Boolean.valueOf(false);
    private boolean mBindAfterPostingVideo = false;
    private a mDescriptionEditText;
    private boolean mLoadingModal = false;
    private File mMovieFile = null;
    private boolean mPostButtonEnable = false;
    private String mPostCategory = "";
    private String mPostMetadata = "";
    private long mPostScore = 0;
    private Boolean mSecretMode = Boolean.valueOf(false);
    private View mSendButton;
    private boolean mSending = false;
    private boolean mShareFacebookEnable = false;
    private boolean mShareNicovideoEnable = false;
    private boolean mShareTwitterEnable = false;
    private boolean mShareWithFacebook = false;
    private boolean mShareWithNicovideo = false;
    private boolean mShareWithTwitter = false;
    private boolean mShareWithYoutube = false;
    private boolean mShareYoutubeEnable = false;
    private a mTitleEditText;
    private boolean mTryPost = false;
    private Boolean mVideoUsingCamera = Boolean.valueOf(false);
    private Boolean mVideoUsingMic = Boolean.valueOf(false);

    private class a {
        final /* synthetic */ RecPostVideoActivity a;
        private int b;
        private boolean c;
        private UIEditText d;
        private TextView e;
        private int f = this.e.getTextColors().getDefaultColor();

        public a(RecPostVideoActivity recPostVideoActivity, int i, int i2, int i3, boolean z) {
            this.a = recPostVideoActivity;
            this.d = (UIEditText) recPostVideoActivity.findViewById(i);
            this.e = (TextView) recPostVideoActivity.findViewById(i2);
            this.b = i3;
            this.c = z;
            this.d.setOnTextChangedListener(new be(this, recPostVideoActivity));
        }

        public void a() {
            int length = this.b - this.d.getText().length();
            this.e.setText(String.valueOf(length));
            this.e.setTextColor(length < 0 ? SupportMenu.CATEGORY_MASK : this.f);
        }

        public void a(CharSequence charSequence) {
            this.d.setText(charSequence);
            this.a.updateTextCounters();
        }

        public boolean b() {
            return this.d.getText().length() > 0;
        }

        public boolean c() {
            return this.b < this.d.getText().length();
        }

        public Editable d() {
            return this.d.getText();
        }

        public void e() {
            ((InputMethodManager) this.a.getSystemService("input_method")).hideSoftInputFromWindow(this.d.getWindowToken(), 0);
        }
    }

    private void doIfTermsOfUseIsAccepted(Runnable runnable) {
        this.mLoadingModal = true;
        if (TermsOfUseFragment.hasAccepted().booleanValue()) {
            runnable.run();
            return;
        }
        Fragment termsOfUseFragment = new TermsOfUseFragment();
        termsOfUseFragment.setCallback(new aq(this, runnable));
        if (!isFinishing()) {
            FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
            beginTransaction.add(termsOfUseFragment, "terms");
            beginTransaction.commitAllowingStateLoss();
        }
    }

    private void finishWithError(int i) {
        showDialogFragmentAsync(new at(this, i), GCMConstants.EXTRA_ERROR);
    }

    private void handleRecInfoResponse(JSONObject jSONObject) {
        JSONObject optJSONObject = jSONObject.optJSONObject("sns");
        if (optJSONObject != null) {
            this.mPostButtonEnable = true;
            this.mShareTwitterEnable = optJSONObject.optString("twitter", "0").equals("1");
            this.mShareFacebookEnable = optJSONObject.optString("facebook", "0").equals("1");
            this.mShareYoutubeEnable = optJSONObject.optString("youtube", "0").equals("1");
            this.mShareNicovideoEnable = optJSONObject.optString("nicovideo", "0").equals("1");
        }
        runOnUiThread(new ac(this));
    }

    private void loadRecSdkInfo() {
        AccountDatastoreAsync.getKKValue(UpdateAt.KEY1, UpdateAt.GET_REC_INFO, Long.valueOf(-1), new bc(this));
    }

    private void loadRecSdkInfoFromDisk() {
        JSONObject jSONObject;
        try {
            jSONObject = new JSONObject((String) TransactionDatastore.getValue(Rec.REC_INFO));
        } catch (Throwable e) {
            b.a(e);
            jSONObject = null;
        }
        if (jSONObject != null) {
            handleRecInfoResponse(jSONObject);
            return;
        }
        TransactionDatastore.deleteValue(Rec.REC_INFO);
        AccountDatastore.deleteKKValue(UpdateAt.KEY1, UpdateAt.GET_REC_INFO);
    }

    private void loadRecSdkInfoFromServer() {
        f.f(new bd(this));
    }

    public static void open(Context context, String str, String str2, String str3, long j, Boolean bool, Boolean bool2) {
        Intent intent = new Intent(context, RecPostVideoActivity.class);
        intent.putExtra("EXTRA_TITLE", str);
        intent.putExtra(EXTRA_DESCRIPTION, str2);
        intent.putExtra(EXTRA_CATEGORY, str3);
        intent.putExtra(EXTRA_SCORE, j);
        intent.putExtra(EXTRA_CAMERA, bool);
        intent.putExtra(EXTRA_MIC, bool2);
        context.startActivity(intent);
    }

    public static void sendFinishBroadcast(Context context) {
        sendFinishBroadcast(context, false, false, false, false, false);
    }

    public static void sendFinishBroadcast(Context context, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        Intent intent = new Intent(LobiRec.ACTION_FINISH_POST_VIDEO_ACTIVITY);
        intent.putExtra(LobiRec.EXTRA_FINISH_POST_VIDEO_ACTIVITY_TRY_POST, z);
        intent.putExtra(LobiRec.EXTRA_FINISH_POST_VIDEO_ACTIVITY_TWITTER_SHARE, z2);
        intent.putExtra(LobiRec.EXTRA_FINISH_POST_VIDEO_ACTIVITY_FACEBOOK_SHARE, z3);
        intent.putExtra(LobiRec.EXTRA_FINISH_POST_VIDEO_ACTIVITY_YOUTUBE_SHARE, z4);
        intent.putExtra(LobiRec.EXTRA_FINISH_POST_VIDEO_ACTIVITY_NICOVIDEO_SHARE, z5);
        context.sendBroadcast(intent);
    }

    private void setDummyMovieFile() {
        File b;
        Throwable e;
        Throwable th;
        try {
            b = com.kayac.lobi.libnakamap.rec.b.a.a().b();
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(b);
                InputStream open = getAssets().open("himehyaku.mp4");
                byte[] bArr = new byte[1024];
                while (true) {
                    int read = open.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    fileOutputStream.write(bArr, 0, read);
                }
                fileOutputStream.close();
            } catch (IOException e2) {
                e = e2;
                b.a(e);
                onLoadVideo(b);
            } catch (com.kayac.lobi.libnakamap.rec.b.a.a e3) {
                e = e3;
                b.a(e);
                onLoadVideo(b);
            }
        } catch (Throwable e4) {
            th = e4;
            b = null;
            e = th;
            b.a(e);
            onLoadVideo(b);
        } catch (Throwable e42) {
            th = e42;
            b = null;
            e = th;
            b.a(e);
            onLoadVideo(b);
        }
        onLoadVideo(b);
    }

    private void setupActionBar() {
        ((BackableContent) ((ActionBar) findViewById(R.id.lobi_action_bar)).getContent()).setOnBackButtonClickListener(new ab(this));
    }

    private void setupFAQBtn() {
        findViewById(R.id.lobi_rec_btn_faq).setOnClickListener(new al(this));
    }

    private void setupMovieThumbnail(String str) {
        findViewById(R.id.lobi_rec_activity_post_video_thumbnail).setOnClickListener(new am(this, str));
        Executors.newSingleThreadExecutor().execute(new an(this, str));
    }

    private void setupSNSShare() {
        int i;
        int i2 = 1;
        this.mSendButton.setVisibility(this.mPostButtonEnable ? 0 : 8);
        View findViewById = findViewById(R.id.share_twitter_unit);
        if (this.mShareTwitterEnable) {
            findViewById.setVisibility(0);
            findViewById(R.id.share_web_twitter).setOnClickListener(new ad(this));
            i = 1;
        } else {
            findViewById.setVisibility(8);
            i = 0;
        }
        View findViewById2 = findViewById(R.id.share_facebook_unit);
        if (this.mShareFacebookEnable) {
            findViewById2.setVisibility(0);
            findViewById(R.id.share_web_facebook).setOnClickListener(new ae(this));
            i = 1;
        } else {
            findViewById2.setVisibility(8);
        }
        findViewById2 = findViewById(R.id.share_youtube_unit);
        if (this.mShareYoutubeEnable) {
            findViewById2.setVisibility(0);
            findViewById(R.id.share_web_youtube).setOnClickListener(new af(this));
            i = 1;
        } else {
            findViewById2.setVisibility(8);
        }
        findViewById2 = findViewById(R.id.share_nicovideo_unit);
        if (this.mShareNicovideoEnable) {
            findViewById2.setVisibility(0);
            findViewById(R.id.share_web_nicovideo).setOnClickListener(new ag(this));
        } else {
            findViewById2.setVisibility(8);
            i2 = i;
        }
        findViewById = findViewById(R.id.share_unit);
        if (i2 != 0) {
            findViewById.setVisibility(0);
        } else {
            findViewById.setVisibility(8);
        }
    }

    private void setupVideoListBtn() {
        findViewById(R.id.lobi_rec_btn_video_list).setOnClickListener(new aj(this));
    }

    private void setupVideoPostBtn() {
        this.mSendButton = findViewById(R.id.lobi_rec_btn_post_video);
        this.mSendButton.setOnClickListener(new ah(this));
    }

    private void showDialogFragmentAsync(DialogFragment dialogFragment, String str) {
        if (!isFinishing()) {
            FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
            beginTransaction.add((Fragment) dialogFragment, str);
            beginTransaction.commitAllowingStateLoss();
        }
    }

    private static void showErrorAlert(Activity activity) {
        activity.runOnUiThread(new aw(activity));
    }

    private void showSNSLogin(String str) {
        if (!this.mLoadingModal) {
            this.mLoadingModal = true;
            Intent intent = new Intent(this, RecSNSLoginActivity.class);
            intent.putExtra(RecSNSLoginActivity.EXTRA_SERVICE, str);
            startActivityForResult(intent, REQUEST_SNS);
        }
    }

    private void signupIfNecessary() {
        UserValue currentUser = AccountDatastore.getCurrentUser();
        if (currentUser == null || currentUser.getToken().length() == 0) {
            LobiCoreAPI.signupWithBaseName(LobiCore.sharedInstance().getNewAccountBaseName(), new av(this));
        } else {
            runOnUiThread(new ap(this));
        }
    }

    private void updateFacebookShareButton() {
        ImageView imageView = (ImageView) findViewById(R.id.share_web_facebook);
        Resources resources = getResources();
        if (this.mShareWithFacebook) {
            imageView.setImageDrawable(resources.getDrawable(R.drawable.lobi_icn_facebook_on));
        } else {
            imageView.setImageDrawable(resources.getDrawable(R.drawable.lobi_icn_facebook_off));
        }
    }

    private void updateNicovideoShareButton() {
        ImageView imageView = (ImageView) findViewById(R.id.share_web_nicovideo);
        Resources resources = getResources();
        if (this.mShareWithNicovideo) {
            imageView.setImageDrawable(resources.getDrawable(R.drawable.lobi_icn_nicovideo_on));
        } else {
            imageView.setImageDrawable(resources.getDrawable(R.drawable.lobi_icn_nicovideo_off));
        }
    }

    private void updatePermission(String str) {
        if ("twitter".equals(str)) {
            this.mShareWithTwitter = true;
            updateTwitterShareButton();
        } else if ("facebook".equals(str)) {
            this.mShareWithFacebook = true;
            updateFacebookShareButton();
        } else if ("google".equals(str)) {
            this.mShareWithYoutube = true;
            updateYoutubeShareButton();
        } else if ("nicovideo".equals(str)) {
            this.mShareWithNicovideo = true;
            updateNicovideoShareButton();
        }
    }

    private void updateStatusMe() {
        f.e(new ay(this));
    }

    private void updateTextCounters() {
        this.mTitleEditText.a();
        this.mDescriptionEditText.a();
    }

    private void updateThumbnail(String str, long j) {
        Executors.newSingleThreadExecutor().execute(new ar(this, str, j));
    }

    private void updateTwitterShareButton() {
        ImageView imageView = (ImageView) findViewById(R.id.share_web_twitter);
        Resources resources = getResources();
        if (this.mShareWithTwitter) {
            imageView.setImageDrawable(resources.getDrawable(R.drawable.lobi_icn_twitter_on));
        } else {
            imageView.setImageDrawable(resources.getDrawable(R.drawable.lobi_icn_twitter_off));
        }
    }

    private void updateYoutubeShareButton() {
        ImageView imageView = (ImageView) findViewById(R.id.share_web_youtube);
        Resources resources = getResources();
        if (this.mShareWithYoutube) {
            imageView.setImageDrawable(resources.getDrawable(R.drawable.lobi_icn_youtube_on));
        } else {
            imageView.setImageDrawable(resources.getDrawable(R.drawable.lobi_icn_youtube_off));
        }
    }

    private synchronized void uploadMovie() {
        if (com.kayac.lobi.libnakamap.rec.b.a.a().c(this.mMovieFile)) {
            runOnUiThread(new az(this));
        } else {
            this.mSending = true;
            Executors.newSingleThreadExecutor().execute(new ba(this));
        }
    }

    public void finish() {
        sendFinishBroadcast(this, this.mTryPost, this.mShareWithTwitter, this.mShareWithFacebook, this.mShareWithYoutube, this.mShareWithNicovideo);
        super.finish();
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        switch (i) {
            case REQUEST_SNS /*1001*/:
                this.mLoadingModal = false;
                if (i2 == -1) {
                    Object stringExtra = intent.getStringExtra(RESULT_EXTRA_SERVICE);
                    if (!TextUtils.isEmpty(stringExtra)) {
                        updatePermission(stringExtra);
                        return;
                    }
                    return;
                }
                return;
            default:
                return;
        }
    }

    protected void onCreate(Bundle bundle) {
        CharSequence charSequence;
        CharSequence charSequence2;
        super.onCreate(bundle);
        setContentView(R.layout.lobi_rec_activity_post_video);
        setupActionBar();
        setupVideoPostBtn();
        setupVideoListBtn();
        setupFAQBtn();
        setupSNSShare();
        this.mBindAfterPostingVideo = ManifestMetaDataUtils.getBoolean(this, ManifestMetaDataUtils.BIND_AFTER_POSTING_VIDEO, false);
        loadRecSdkInfo();
        if (USE_DUMMY_FILE.booleanValue()) {
            setDummyMovieFile();
        } else if (!com.kayac.lobi.libnakamap.rec.b.a.a().a((c) this)) {
            b.c("LobiRecSDK", "Aborting the video concat task.");
            finish();
            return;
        }
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String string = extras.getString("EXTRA_TITLE");
            String string2 = extras.getString(EXTRA_DESCRIPTION);
            this.mPostCategory = extras.getString(EXTRA_CATEGORY);
            this.mPostMetadata = extras.getString(EXTRA_METADATA);
            this.mPostScore = extras.getLong(EXTRA_SCORE);
            this.mVideoUsingCamera = Boolean.valueOf(extras.getBoolean(EXTRA_CAMERA));
            this.mVideoUsingMic = Boolean.valueOf(extras.getBoolean(EXTRA_MIC));
            this.mSecretMode = Boolean.valueOf(LobiRecAPI.isSecretMode());
            charSequence = string2;
            charSequence2 = string;
        } else {
            charSequence = null;
            charSequence2 = null;
        }
        this.mTitleEditText = new a(this, R.id.title, R.id.title_header_count, 40, true);
        this.mDescriptionEditText = new a(this, R.id.description, R.id.description_header_count, 1000, true);
        if (charSequence2 != null) {
            this.mTitleEditText.a(charSequence2);
        }
        if (charSequence != null) {
            this.mDescriptionEditText.a(charSequence);
        }
        signupIfNecessary();
    }

    protected void onDestroy() {
        com.kayac.lobi.libnakamap.rec.b.a.a().h();
        super.onDestroy();
    }

    public void onLoadVideo(File file) {
        if (file == null || !file.exists()) {
            finish();
            return;
        }
        setupMovieThumbnail(file.getAbsolutePath());
        this.mMovieFile = file;
    }

    public void onPause() {
        super.onPause();
        this.mTitleEditText.e();
        this.mDescriptionEditText.e();
    }

    public void onResume() {
        super.onResume();
        this.mLoadingModal = false;
    }
}
