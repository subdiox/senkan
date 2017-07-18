package com.kayac.lobi.sdk.activity.profile;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import com.kayac.lobi.libnakamap.PathRoutedActivity;
import com.kayac.lobi.libnakamap.components.ActionBar;
import com.kayac.lobi.libnakamap.components.ActionBar.BackableContent;
import com.kayac.lobi.libnakamap.components.ActionBar.CheckButton;
import com.kayac.lobi.libnakamap.components.CustomDialog;
import com.kayac.lobi.libnakamap.components.CustomProgressDialog;
import com.kayac.lobi.libnakamap.components.ImageLoaderView;
import com.kayac.lobi.libnakamap.components.ListRow;
import com.kayac.lobi.libnakamap.components.ListRow.OneLine;
import com.kayac.lobi.libnakamap.components.SelectPictureMenuDialog;
import com.kayac.lobi.libnakamap.components.UIEditText;
import com.kayac.lobi.libnakamap.components.UIEditText.OnTextChangedListener;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.KKey;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.KKey.Profile;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastore;
import com.kayac.lobi.libnakamap.net.APIDef.PostMeCover.RequestKey;
import com.kayac.lobi.libnakamap.net.APIRes.GetUser;
import com.kayac.lobi.libnakamap.net.APIRes.PostMeCover;
import com.kayac.lobi.libnakamap.net.APIRes.PostMeIcon;
import com.kayac.lobi.libnakamap.net.APIRes.PostMeProfile;
import com.kayac.lobi.libnakamap.net.CoreAPI;
import com.kayac.lobi.libnakamap.net.CoreAPI.DefaultAPICallback;
import com.kayac.lobi.libnakamap.utils.DebugAssert;
import com.kayac.lobi.libnakamap.utils.ImageIntentManager;
import com.kayac.lobi.libnakamap.utils.Log;
import com.kayac.lobi.libnakamap.utils.ModuleUtil;
import com.kayac.lobi.libnakamap.utils.NakamapBroadcastManager;
import com.kayac.lobi.libnakamap.utils.PictureUtil;
import com.kayac.lobi.libnakamap.utils.PictureUtil.Res;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.libnakamap.value.UserValue.Builder;
import com.kayac.lobi.sdk.R;
import com.kayac.lobi.sdk.utils.EmoticonUtil;
import java.util.HashMap;
import java.util.Map;

public class ProfileEditActivity extends PathRoutedActivity {
    public static final String EXTRAS_TARGET_USER = "EXTRAS_TARGET_USER";
    public static final String PATH_PROFILE_EDIT = "/profile_edit";
    private static final int PICK_PICTURE = 20002;
    public static final String PROFILE_UPDATED = "profile_updated";
    private static final int TAKE_PHOTO = 20001;
    private boolean isUserImagesChanged = false;
    protected CheckButton mActionBarButtonConfirm;
    private ImageLoaderView mCoverImageView;
    protected String mEditingUserDescription = "";
    protected String mEditingUserName = "";
    private ListRow mIconEditRow;
    private ImageLoaderView mIconImageView;
    private final ImageIntentManager mImageIntentManager = new ImageIntentManager(this);
    private UserValue mTargetUser;

    private static final class OnPostMeCover extends DefaultAPICallback<PostMeCover> {
        private final ProfileEditActivity mActivity;
        private CustomProgressDialog mProgress;
        private String mToken;
        private String mUserUid;

        OnPostMeCover(ProfileEditActivity activity) {
            super(activity);
            this.mActivity = activity;
        }

        public void setProgress(CustomProgressDialog progress) {
            this.mProgress = progress;
            super.setProgress(progress);
        }

        public void setUserUid(String userUid) {
            this.mUserUid = userUid;
        }

        public void setToken(String token) {
            this.mToken = token;
        }

        public void onResponse(PostMeCover t) {
            this.mActivity.runOnUiThread(new Runnable() {
                public void run() {
                    OnPostMeCover.this.mProgress.dismiss();
                    OnPostMeCover.this.mActivity.updateUserImages(OnPostMeCover.this.mUserUid, OnPostMeCover.this.mToken);
                }
            });
        }
    }

    private static final class OnPostMeIcon extends DefaultAPICallback<PostMeIcon> {
        private final ProfileEditActivity mActivity;
        private CustomProgressDialog mProgress;
        private String mToken;
        private String mUserUid;

        OnPostMeIcon(ProfileEditActivity activity) {
            super(activity);
            this.mActivity = activity;
        }

        public void setProgress(CustomProgressDialog progress) {
            this.mProgress = progress;
            super.setProgress(progress);
        }

        public void setUserUid(String userUid) {
            this.mUserUid = userUid;
        }

        public void setToken(String token) {
            this.mToken = token;
        }

        public void onResponse(PostMeIcon t) {
            Builder builder = new Builder(AccountDatastore.getUser(this.mUserUid));
            builder.setIcon(t.icon);
            AccountDatastore.setUser(builder.build());
            NakamapBroadcastManager.getInstance(this.mActivity).sendBroadcast(new Intent("profile_updated"));
            this.mActivity.runOnUiThread(new Runnable() {
                public void run() {
                    OnPostMeIcon.this.mProgress.dismiss();
                    OnPostMeIcon.this.mActivity.updateUserImages(OnPostMeIcon.this.mUserUid, OnPostMeIcon.this.mToken);
                }
            });
        }
    }

    private static final class OnPostMeProfile extends DefaultAPICallback<PostMeProfile> {
        private final ProfileEditActivity mActivity;
        private String mDesc;
        private String mName;
        private CustomProgressDialog mProgress;

        OnPostMeProfile(ProfileEditActivity activity) {
            super(activity);
            this.mActivity = activity;
        }

        public void setProgress(CustomProgressDialog progress) {
            this.mProgress = progress;
            super.setProgress(progress);
        }

        public void setName(String name) {
            this.mName = name;
        }

        public void setDesc(String desc) {
            this.mDesc = desc;
        }

        public void onResponse(PostMeProfile t) {
            Builder builder = new Builder(AccountDatastore.getCurrentUser());
            if (this.mName != null) {
                builder.setName(this.mName);
            }
            if (this.mDesc != null) {
                builder.setDescription(this.mDesc);
            }
            AccountDatastore.setUser(builder.build());
            this.mActivity.runOnUiThread(new Runnable() {
                public void run() {
                    OnPostMeProfile.this.mProgress.dismiss();
                    OnPostMeProfile.this.mActivity.onFinishProfileEdit();
                }
            });
        }
    }

    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.lobi_profile_edit_activity);
        this.mTargetUser = (UserValue) getIntent().getExtras().getParcelable("EXTRAS_TARGET_USER");
        this.mCoverImageView = (ImageLoaderView) findViewById(R.id.lobi_profile_cover_image);
        this.mIconEditRow = (ListRow) findViewById(R.id.lobi_profile_edit_change_icon_row);
        this.mIconImageView = (ImageLoaderView) this.mIconEditRow.getContent(0);
        DebugAssert.assertNotNull(this.mCoverImageView);
        DebugAssert.assertNotNull(this.mIconEditRow);
        DebugAssert.assertNotNull(this.mIconImageView);
        setupLayoutActionBar(this.mTargetUser);
        setupLayoutCoverImage(this.mTargetUser);
        setupLayoutIcon(this.mTargetUser);
        setupLayoutUserName(this.mTargetUser);
        setupLayoutUserDescription(this.mTargetUser);
        updateConfirmButtonStatus();
        updateDisplay();
    }

    protected void onResume() {
        super.onResume();
        NakamapBroadcastManager manager = NakamapBroadcastManager.getInstance(getApplicationContext());
        IntentFilter filter = new IntentFilter();
        filter.addAction(SelectPictureMenuDialog.ACTION_SELECTED);
        manager.registerReceiver(this.mImageIntentManager, filter);
    }

    protected void onPause() {
        super.onPause();
        NakamapBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.mImageIntentManager);
    }

    protected void onDestroy() {
        this.mImageIntentManager.onDestroy();
        super.onDestroy();
    }

    public void onBackPressed() {
        if (!showCancelConfirmDialogIfNecessary()) {
            super.onBackPressed();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String userUid = (String) TransactionDatastore.getKKValue(Profile.KEY1, "USER_UID", null);
        DebugAssert.assertNotNull(userUid);
        String token = (String) TransactionDatastore.getKKValue(Profile.KEY1, "TOKEN", null);
        DebugAssert.assertNotNull(token);
        int photoType = ((Integer) TransactionDatastore.getKKValue(Profile.KEY1, "PICTURE_TYPE", Integer.valueOf(-1))).intValue();
        DebugAssert.assertTrue(photoType != -1);
        int uploadSize = PictureUtil.getUploadSize(photoType);
        int uploadFormat = PictureUtil.getUploadFormat(photoType);
        if (resultCode == -1) {
            Res res = null;
            if (requestCode == 20001) {
                Log.v("lobi-sdk", "[picture] onActivityResult take photo");
                res = PictureUtil.onActivityResultPictureTakenHelper(this, data, ImageIntentManager.retreivePictureOutputPath(), uploadSize, uploadFormat, -1);
            } else if (requestCode == 20002) {
                res = PictureUtil.onActivityResultPictureSelectedHelper(this, data, uploadSize, uploadFormat, -1);
            } else {
                DebugAssert.fail();
            }
            if (res.getRaw() != null) {
                switch (photoType) {
                    case 0:
                        startPostCoverAPI(userUid, token, res);
                        return;
                    case 1:
                        startPostIconAPI(userUid, token, res);
                        return;
                    default:
                        DebugAssert.fail();
                        return;
                }
            }
            showErrorToast();
        } else if (resultCode == 0) {
            showErrorToast();
        } else {
            showErrorToast();
        }
    }

    protected boolean isUserNameChanged() {
        return !TextUtils.equals(this.mEditingUserName, this.mTargetUser.getName());
    }

    protected boolean isUserDescriptionChanged() {
        return !TextUtils.equals(this.mEditingUserDescription, this.mTargetUser.getDescription());
    }

    protected void startUpdateProfile(UserValue targetUser) {
        Map<String, String> params = new HashMap();
        params.put("token", this.mTargetUser.getToken());
        boolean saveChanges = false;
        OnPostMeProfile callback = new OnPostMeProfile(this);
        if (isUserNameChanged()) {
            params.put("name", this.mEditingUserName);
            saveChanges = true;
            callback.setName(this.mEditingUserName);
        }
        if (isUserDescriptionChanged()) {
            params.put("description", this.mEditingUserDescription);
            saveChanges = true;
            callback.setDesc(this.mEditingUserDescription);
        }
        if (saveChanges) {
            CustomProgressDialog progress = new CustomProgressDialog(this);
            progress.setMessage(getString(R.string.lobi_loading_loading));
            progress.show();
            callback.setProgress(progress);
            CoreAPI.postMeProfile(params, callback);
            return;
        }
        onFinishProfileEdit();
    }

    protected void setupLayoutActionBar(UserValue targetUser) {
        ActionBar actionBar = (ActionBar) findViewById(R.id.lobi_action_bar);
        DebugAssert.assertNotNull(actionBar);
        this.mActionBarButtonConfirm = new CheckButton(this);
        this.mActionBarButtonConfirm.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ProfileEditActivity.this.startUpdateProfile(ProfileEditActivity.this.mTargetUser);
            }
        });
        actionBar.addActionBarButton(this.mActionBarButtonConfirm);
        BackableContent backContent = (BackableContent) actionBar.getContent();
        DebugAssert.assertNotNull(backContent);
        backContent.setOnBackButtonClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (!ProfileEditActivity.this.showCancelConfirmDialogIfNecessary()) {
                    ProfileEditActivity.this.finish();
                }
            }
        });
    }

    protected void onFinishProfileEdit() {
        finish();
    }

    private void showErrorToast() {
        Toast.makeText(this, getString(R.string.lobi_sorry), 0).show();
    }

    private void startPostIconAPI(String userUid, String token, Res res) {
        CustomProgressDialog progress = new CustomProgressDialog(this);
        progress.setMessage(getString(R.string.lobi_loading_loading));
        progress.show();
        OnPostMeIcon callback = new OnPostMeIcon(this);
        callback.setProgress(progress);
        callback.setUserUid(userUid);
        callback.setToken(token);
        Map<String, String> params = new HashMap();
        params.put("token", token);
        params.put("icon", res.getRaw().getAbsolutePath());
        CoreAPI.postMeIcon(params, callback);
    }

    private void startPostCoverAPI(String userUid, String token, Res res) {
        CustomProgressDialog progress = new CustomProgressDialog(this);
        progress.setMessage(getString(R.string.lobi_loading_loading));
        progress.show();
        OnPostMeCover callback = new OnPostMeCover(this);
        callback.setProgress(progress);
        callback.setUserUid(userUid);
        callback.setToken(token);
        Map<String, String> params = new HashMap();
        params.put("token", token);
        params.put(RequestKey.COVER, res.getRaw().getAbsolutePath());
        CoreAPI.postMeCover(params, callback);
    }

    private void setupLayoutCoverImage(final UserValue currentUserValue) {
        final FragmentActivity selfActivity = this;
        findViewById(R.id.lobi_profile_cover_change).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                TransactionDatastore.setKKValue(Profile.KEY1, "PICTURE_TYPE", Integer.valueOf(0));
                TransactionDatastore.setKKValue(Profile.KEY1, "TOKEN", currentUserValue.getToken());
                TransactionDatastore.setKKValue(Profile.KEY1, "USER_UID", currentUserValue.getUid());
                selfActivity.runOnUiThread(new Runnable() {
                    public void run() {
                        SelectPictureMenuDialog.newInstance(KKey.ImageIntentManager.PROFILE_COVER, R.string.lobi_update_cover, false, 0).show(selfActivity.getSupportFragmentManager(), SelectPictureMenuDialog.class.getCanonicalName());
                    }
                });
            }
        });
    }

    public void setupLayoutIcon(final UserValue currentUserValue) {
        View container = findViewById(R.id.lobi_profile_edit_change_icon_container);
        if (ModuleUtil.chatIsAvailable()) {
            container.setVisibility(8);
            return;
        }
        container.setVisibility(0);
        ListRow iconListRow = (ListRow) findViewById(R.id.lobi_profile_edit_change_icon_row);
        DebugAssert.assertNotNull(iconListRow);
        ((OneLine) iconListRow.getContent(1)).setText(0, getResources().getString(R.string.lobi_change_setting));
        final FragmentActivity selfActivity = this;
        iconListRow.findViewById(R.id.lobi_list_row_area).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                TransactionDatastore.setKKValue(Profile.KEY1, "PICTURE_TYPE", Integer.valueOf(1));
                TransactionDatastore.setKKValue(Profile.KEY1, "TOKEN", currentUserValue.getToken());
                TransactionDatastore.setKKValue(Profile.KEY1, "USER_UID", currentUserValue.getUid());
                selfActivity.runOnUiThread(new Runnable() {
                    public void run() {
                        SelectPictureMenuDialog.newInstance(KKey.ImageIntentManager.PROFILE_ICON, R.string.lobi_update_icon, false, 0).show(selfActivity.getSupportFragmentManager(), SelectPictureMenuDialog.class.getCanonicalName());
                    }
                });
            }
        });
    }

    private void setupLayoutUserDescription(UserValue currentUserValue) {
        this.mEditingUserDescription = currentUserValue.getDescription();
        UIEditText editTextDescription = (UIEditText) findViewById(R.id.lobi_profile_edit_user_description);
        DebugAssert.assertNotNull(editTextDescription);
        if (!TextUtils.isEmpty(currentUserValue.getDescription())) {
            editTextDescription.setText(EmoticonUtil.getEmoticonSpannedText(editTextDescription.getContext(), this.mEditingUserDescription));
        }
        editTextDescription.setOnTextChangedListener(new OnTextChangedListener() {
            public void onTextChanged(UIEditText textInput, CharSequence text, int start, int before, int after) {
                ProfileEditActivity.this.mEditingUserDescription = text.toString();
                ProfileEditActivity.this.updateConfirmButtonStatus();
            }
        });
    }

    protected void updateConfirmButtonStatus() {
        boolean isNameEntered;
        if (this.mEditingUserName.length() > 0) {
            isNameEntered = true;
        } else {
            isNameEntered = false;
        }
        boolean isTextChanged;
        if (isUserNameChanged() || isUserDescriptionChanged()) {
            isTextChanged = true;
        } else {
            isTextChanged = false;
        }
        if (isNameEntered && (isTextChanged || this.isUserImagesChanged)) {
            this.mActionBarButtonConfirm.setEnable();
        } else {
            this.mActionBarButtonConfirm.setDisable();
        }
    }

    private void setupLayoutUserName(UserValue currentUserValue) {
        View container = findViewById(R.id.lobi_profile_edit_change_name_container);
        this.mEditingUserName = currentUserValue.getName();
        CharSequence spannedName = EmoticonUtil.getEmoticonSpannedText(container.getContext(), this.mEditingUserName);
        if (ModuleUtil.chatIsAvailable()) {
            container.setVisibility(8);
            return;
        }
        container.setVisibility(0);
        UIEditText editTextName = (UIEditText) findViewById(R.id.lobi_profile_edit_user_name);
        DebugAssert.assertNotNull(editTextName);
        editTextName.setText(spannedName);
        editTextName.setOnTextChangedListener(new OnTextChangedListener() {
            public void onTextChanged(UIEditText textInput, CharSequence text, int start, int before, int after) {
                ProfileEditActivity.this.mEditingUserName = text.toString();
                ProfileEditActivity.this.updateConfirmButtonStatus();
            }
        });
    }

    private void updateDisplay() {
        this.mCoverImageView.loadImage(this.mTargetUser.getCover());
        this.mIconImageView.loadImage(this.mTargetUser.getIcon());
    }

    private void updateUserImages(String userUid, String token) {
        if (userUid != null && token != null) {
            Map<String, String> params = new HashMap();
            params.put("token", token);
            params.put("uid", userUid);
            CoreAPI.getUser(params, new DefaultAPICallback<GetUser>(this) {
                public void onResponse(GetUser t) {
                    Builder builder = new Builder(AccountDatastore.getCurrentUser());
                    builder.setCover(t.user.getCover());
                    builder.setIcon(t.user.getIcon());
                    UserValue newUser = builder.build();
                    AccountDatastore.setUser(newUser);
                    ProfileEditActivity.this.mTargetUser = newUser;
                    runOnUiThread(new Runnable() {
                        public void run() {
                            ProfileEditActivity.this.updateDisplay();
                            ProfileEditActivity.this.isUserImagesChanged = true;
                            ProfileEditActivity.this.updateConfirmButtonStatus();
                        }
                    });
                }
            });
        }
    }

    private boolean showCancelConfirmDialogIfNecessary() {
        if (!isUserNameChanged() && !isUserDescriptionChanged()) {
            return false;
        }
        final CustomDialog dialog = CustomDialog.createTextDialog(this, getString(R.string.lobisdk_confirm_cancel_profile_edit));
        dialog.setTitle(getString(R.string.lobi_edit_profile));
        dialog.setPositiveButton(getString(R.string.lobi_ok), new OnClickListener() {
            public void onClick(View v) {
                ProfileEditActivity.this.finish();
            }
        });
        dialog.setNegativeButton(getString(R.string.lobi_cancel), new OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        return true;
    }
}
