package com.kayac.lobi.sdk.chat.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import com.kayac.lobi.libnakamap.PathRoutedActivity;
import com.kayac.lobi.libnakamap.components.ActionBar;
import com.kayac.lobi.libnakamap.components.ActionBar.BackableContent;
import com.kayac.lobi.libnakamap.components.ActionBar.CheckButton;
import com.kayac.lobi.libnakamap.components.CustomDialog;
import com.kayac.lobi.libnakamap.components.CustomProgressDialog;
import com.kayac.lobi.libnakamap.components.ImageLoaderView;
import com.kayac.lobi.libnakamap.components.LightBox;
import com.kayac.lobi.libnakamap.components.LightBox.OnContentLongClickListener;
import com.kayac.lobi.libnakamap.components.LightBox.OnContentShowListener;
import com.kayac.lobi.libnakamap.components.LightBox.OnDismissListener;
import com.kayac.lobi.libnakamap.components.ListRow;
import com.kayac.lobi.libnakamap.components.ListRow.OneLine;
import com.kayac.lobi.libnakamap.components.ListRow.TwoLine;
import com.kayac.lobi.libnakamap.components.PathRouter;
import com.kayac.lobi.libnakamap.components.SectionView;
import com.kayac.lobi.libnakamap.components.SelectPictureMenuDialog;
import com.kayac.lobi.libnakamap.components.UIEditText;
import com.kayac.lobi.libnakamap.components.UIEditText.OnTextChangedListener;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.KKey;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.KKey.ChatGroupInfo;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastore;
import com.kayac.lobi.libnakamap.net.APIDef.PostGroupWallpaper.RequestKey;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroup;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupIcon;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupWallpaper;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupWallpaperRemove;
import com.kayac.lobi.libnakamap.net.CoreAPI;
import com.kayac.lobi.libnakamap.net.CoreAPI.DefaultAPICallback;
import com.kayac.lobi.libnakamap.utils.DebugAssert;
import com.kayac.lobi.libnakamap.utils.GalleryUtil;
import com.kayac.lobi.libnakamap.utils.ImageIntentManager;
import com.kayac.lobi.libnakamap.utils.NakamapBroadcastManager;
import com.kayac.lobi.libnakamap.utils.PictureUtil;
import com.kayac.lobi.libnakamap.utils.PictureUtil.Res;
import com.kayac.lobi.libnakamap.value.GroupDetailValue;
import com.kayac.lobi.libnakamap.value.GroupPermissionValue;
import com.kayac.lobi.libnakamap.value.GroupValue;
import com.kayac.lobi.libnakamap.value.GroupValue.Builder;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.sdk.R;
import com.kayac.lobi.sdk.utils.ManifestUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatGroupInfoSettingsActivity extends PathRoutedActivity {
    public static final String EXTRAS_GROUP_VALUE = "EXTRAS_GROUP_VALUE";
    public static final String PATH_GROUP_SETTINGS_FROM_CHAT = "/grouplist/chat/settings";
    public static final String PATH_GROUP_SETTINGS_FROM_INFO = "/grouplist/chat/info/settings";
    private static final int PICK_PICTURE = 20002;
    private static final int TAKE_PHOTO = 20001;
    private CheckButton mActionBarButton;
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Bundle bundle = intent.getExtras();
            if (SelectPictureMenuDialog.ACTION_SELECTED.equals(action)) {
                int type = bundle.getInt(SelectPictureMenuDialog.EXTRA_TYPE, -1);
                DebugAssert.assertTrue(type != -1);
                if (type == R.id.lobi_select_picture_menu_detach_photo) {
                    CustomProgressDialog progress = new CustomProgressDialog(ChatGroupInfoSettingsActivity.this);
                    progress.setMessage(ChatGroupInfoSettingsActivity.this.getString(R.string.lobi_loading_loading));
                    progress.show();
                    OnPostGroupWallpaperRemove callback = new OnPostGroupWallpaperRemove(ChatGroupInfoSettingsActivity.this);
                    callback.setProgress(progress);
                    String groupUid = (String) TransactionDatastore.getKKValue(ChatGroupInfo.KEY1, ChatGroupInfo.GROUP_UID, null);
                    callback.setGroupUid(groupUid);
                    UserValue userValue = AccountDatastore.getCurrentUser();
                    callback.setCurrentUser(userValue);
                    Map<String, String> params = new HashMap();
                    params.put("token", userValue.getToken());
                    params.put("uid", groupUid);
                    CoreAPI.postGroupWallpaperRemove(params, callback);
                    return;
                }
                ChatGroupInfoSettingsActivity.this.mImageIntentManager.onReceive(context, intent);
            }
        }
    };
    private String mDefaultDescription;
    private String mDefaultName;
    private final ImageIntentManager mImageIntentManager = new ImageIntentManager(this);

    private static final class OnPostGroupIcon extends DefaultAPICallback<PostGroupIcon> {
        private final ChatGroupInfoSettingsActivity mActivity;
        private String mGroupUid;
        private UserValue mUser;

        public OnPostGroupIcon(ChatGroupInfoSettingsActivity context) {
            super(context);
            this.mActivity = context;
        }

        public void setGroupUid(String groupUid) {
            this.mGroupUid = groupUid;
        }

        public void setCurrentUser(UserValue user) {
            this.mUser = user;
        }

        public void onResponse(PostGroupIcon t) {
            String userUid = this.mUser.getUid();
            Builder builder = new Builder(TransactionDatastore.getGroup(this.mGroupUid, userUid));
            builder.setIcon(t.groupDetail.getIcon());
            final GroupValue groupValue = builder.build();
            TransactionDatastore.setGroup(groupValue, userUid);
            super.onResponse(t);
            this.mActivity.runOnUiThread(new Runnable() {
                public void run() {
                    OnPostGroupIcon.this.mActivity.setGroupIconWallpaper(groupValue, OnPostGroupIcon.this.mUser, groupValue.getPermission());
                }
            });
            GroupDetailValue.Builder builder2 = new GroupDetailValue.Builder(TransactionDatastore.getGroupDetail(this.mGroupUid, userUid));
            builder2.setIcon(t.groupDetail.getIcon());
            TransactionDatastore.setGroupDetail(builder2.build(), userUid);
        }
    }

    private static final class OnPostGroupWallpaper extends DefaultAPICallback<PostGroupWallpaper> {
        private final ChatGroupInfoSettingsActivity mActivity;
        private String mGroupUid;
        private UserValue mUser;

        public OnPostGroupWallpaper(ChatGroupInfoSettingsActivity context) {
            super(context);
            this.mActivity = context;
        }

        public void setGroupUid(String groupUid) {
            this.mGroupUid = groupUid;
        }

        public void setCurrentUser(UserValue user) {
            this.mUser = user;
        }

        public void onResponse(PostGroupWallpaper t) {
            Builder builder = new Builder(TransactionDatastore.getGroup(this.mGroupUid, this.mUser.getUid()));
            builder.setWallpaper(t.url);
            final GroupValue groupValue = builder.build();
            TransactionDatastore.setGroup(groupValue, this.mUser.getUid());
            super.onResponse(t);
            this.mActivity.runOnUiThread(new Runnable() {
                public void run() {
                    OnPostGroupWallpaper.this.mActivity.setGroupIconWallpaper(groupValue, OnPostGroupWallpaper.this.mUser, groupValue.getPermission());
                }
            });
        }
    }

    private static final class OnPostGroupWallpaperRemove extends DefaultAPICallback<PostGroupWallpaperRemove> {
        private final ChatGroupInfoSettingsActivity mActivity;
        private String mGroupUid;
        private UserValue mUser;

        public OnPostGroupWallpaperRemove(ChatGroupInfoSettingsActivity activity) {
            super(activity);
            this.mActivity = activity;
        }

        public void setGroupUid(String groupUid) {
            this.mGroupUid = groupUid;
        }

        public void setCurrentUser(UserValue user) {
            this.mUser = user;
        }

        public void onResponse(PostGroupWallpaperRemove t) {
            super.onResponse(t);
            Builder builder = new Builder(TransactionDatastore.getGroup(this.mGroupUid, this.mUser.getUid()));
            builder.setWallpaper("");
            final GroupValue groupValue = builder.build();
            TransactionDatastore.setGroup(groupValue, this.mUser.getUid());
            this.mActivity.runOnUiThread(new Runnable() {
                public void run() {
                    OnPostGroupWallpaperRemove.this.mActivity.setGroupIconWallpaper(groupValue, OnPostGroupWallpaperRemove.this.mUser, groupValue.getPermission());
                    FrameLayout iconArea = (FrameLayout) ((ListRow) OnPostGroupWallpaperRemove.this.mActivity.findViewById(R.id.lobi_chat_group_info_settings_group_wallpaper)).getContent(0);
                    iconArea.setOnClickListener(null);
                    ((ImageLoaderView) iconArea.findViewById(R.id.lobi_list_row_content_image_loader_big_image_view)).setOnClickListener(null);
                    ((ImageView) iconArea.findViewById(R.id.lobi_list_row_content_image_loader_big_zoom_icon)).setVisibility(8);
                }
            });
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lobi_chat_group_info_settings_activity);
        GroupValue groupValue = (GroupValue) getIntent().getExtras().getParcelable("EXTRAS_GROUP_VALUE");
        GroupPermissionValue permission = groupValue.getPermission();
        this.mDefaultName = groupValue.getName();
        this.mDefaultDescription = groupValue.getDescription();
        UserValue userValue = AccountDatastore.getCurrentUser();
        setGroupIconWallpaper(groupValue, userValue, permission);
        setActionBar(groupValue, userValue);
        setGroupName(this.mDefaultName, permission.canUpdateName);
        setGroupDescription(this.mDefaultDescription, permission.canUpdateDescription);
        if (userValue.getUid().equals(((UserValue) groupValue.getMembers().get(0)).getUid())) {
            DebugAssert.assertNotNull(groupValue);
        } else {
            DebugAssert.assertNotNull(groupValue);
        }
    }

    protected void onResume() {
        super.onResume();
        NakamapBroadcastManager manager = NakamapBroadcastManager.getInstance(getApplicationContext());
        IntentFilter filter = new IntentFilter();
        filter.addAction(SelectPictureMenuDialog.ACTION_SELECTED);
        manager.registerReceiver(this.mBroadcastReceiver, filter);
    }

    protected void onPause() {
        super.onPause();
        NakamapBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.mBroadcastReceiver);
    }

    protected void onDestroy() {
        this.mImageIntentManager.onDestroy();
        super.onDestroy();
    }

    public void finish() {
        super.finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String token = (String) TransactionDatastore.getKKValue(ChatGroupInfo.KEY1, "TOKEN", null);
        DebugAssert.assertNotNull(token);
        String groupUid = (String) TransactionDatastore.getKKValue(ChatGroupInfo.KEY1, ChatGroupInfo.GROUP_UID, null);
        String userUid = (String) TransactionDatastore.getKKValue(ChatGroupInfo.KEY1, "USER_UID", null);
        DebugAssert.assertNotNull(groupUid);
        int photoType;
        int uploadSize;
        int uploadFormat;
        Res res;
        CustomProgressDialog progress;
        OnPostGroupIcon callback;
        Map<String, String> params;
        OnPostGroupWallpaper callback2;
        if (requestCode == 20001) {
            if (resultCode == -1) {
                Uri path = ImageIntentManager.retreivePictureOutputPath();
                photoType = ((Integer) TransactionDatastore.getKKValue(ChatGroupInfo.KEY1, "PICTURE_TYPE", Integer.valueOf(-1))).intValue();
                DebugAssert.assertTrue(photoType != -1);
                uploadSize = 0;
                uploadFormat = 0;
                switch (photoType) {
                    case 0:
                        uploadSize = 640;
                        uploadFormat = 0;
                        break;
                    case 1:
                        uploadSize = 640;
                        uploadFormat = 0;
                        break;
                    default:
                        DebugAssert.fail();
                        break;
                }
                res = PictureUtil.onActivityResultPictureTakenHelper(this, data, path, uploadSize, uploadFormat, -1);
                if (res.getRaw() == null) {
                    Toast.makeText(this, getString(R.string.lobi_sorry), 0).show();
                    return;
                }
                switch (photoType) {
                    case 0:
                        progress = new CustomProgressDialog(this);
                        progress.setMessage(getString(R.string.lobi_loading_loading));
                        progress.show();
                        callback = new OnPostGroupIcon(this);
                        callback.setProgress(progress);
                        callback.setGroupUid(groupUid);
                        callback.setCurrentUser(AccountDatastore.getUser(userUid));
                        params = new HashMap();
                        params.put("token", token);
                        params.put("uid", groupUid);
                        params.put("icon", res.getRaw().getAbsolutePath());
                        CoreAPI.postGroupIcon(params, callback);
                        return;
                    case 1:
                        progress = new CustomProgressDialog(this);
                        progress.setMessage(getString(R.string.lobi_loading_loading));
                        progress.show();
                        callback2 = new OnPostGroupWallpaper(this);
                        callback2.setProgress(progress);
                        callback2.setGroupUid(groupUid);
                        callback2.setCurrentUser(AccountDatastore.getUser(userUid));
                        params = new HashMap();
                        params.put("token", token);
                        params.put("uid", groupUid);
                        params.put(RequestKey.WALLPAPER, res.getRaw().getAbsolutePath());
                        CoreAPI.postGroupWallpaper(params, callback2);
                        return;
                    default:
                        DebugAssert.fail();
                        return;
                }
            } else if (resultCode == 0) {
                Toast.makeText(this, getString(R.string.lobi_sorry), 0).show();
            } else {
                Toast.makeText(this, getString(R.string.lobi_sorry), 0).show();
            }
        } else if (requestCode != 20002) {
        } else {
            if (resultCode == -1) {
                photoType = ((Integer) TransactionDatastore.getKKValue(ChatGroupInfo.KEY1, "PICTURE_TYPE", Integer.valueOf(-1))).intValue();
                DebugAssert.assertTrue(photoType != -1);
                uploadSize = 0;
                uploadFormat = 0;
                switch (photoType) {
                    case 0:
                        uploadSize = 640;
                        uploadFormat = 0;
                        break;
                    case 1:
                        uploadSize = 640;
                        uploadFormat = 0;
                        break;
                    default:
                        DebugAssert.fail();
                        break;
                }
                res = PictureUtil.onActivityResultPictureSelectedHelper(this, data, uploadSize, uploadFormat, -1);
                if (res.getRaw() == null) {
                    Toast.makeText(this, getString(R.string.lobi_sorry), 0).show();
                    return;
                }
                switch (photoType) {
                    case 0:
                        progress = new CustomProgressDialog(this);
                        progress.setMessage(getString(R.string.lobi_loading_loading));
                        progress.show();
                        callback = new OnPostGroupIcon(this);
                        callback.setProgress(progress);
                        callback.setGroupUid(groupUid);
                        callback.setCurrentUser(AccountDatastore.getUser(userUid));
                        params = new HashMap();
                        params.put("token", token);
                        params.put("uid", groupUid);
                        params.put("icon", res.getRaw().getAbsolutePath());
                        CoreAPI.postGroupIcon(params, callback);
                        return;
                    case 1:
                        progress = new CustomProgressDialog(this);
                        progress.setMessage(getString(R.string.lobi_loading_loading));
                        progress.show();
                        callback2 = new OnPostGroupWallpaper(this);
                        callback2.setProgress(progress);
                        callback2.setGroupUid(groupUid);
                        callback2.setCurrentUser(AccountDatastore.getUser(userUid));
                        params = new HashMap();
                        params.put("token", token);
                        params.put("uid", groupUid);
                        params.put(RequestKey.WALLPAPER, res.getRaw().getAbsolutePath());
                        CoreAPI.postGroupWallpaper(params, callback2);
                        return;
                    default:
                        DebugAssert.fail();
                        return;
                }
            } else if (resultCode == 0) {
                Toast.makeText(this, getString(R.string.lobi_sorry), 0).show();
            } else {
                Toast.makeText(this, getString(R.string.lobi_sorry), 0).show();
            }
        }
    }

    private void setActionBar(final GroupValue groupDetail, final UserValue currentUser) {
        ActionBar actionBar = (ActionBar) findViewById(R.id.lobi_action_bar);
        ((BackableContent) actionBar.getContent()).setOnBackButtonClickListener(new OnClickListener() {
            public void onClick(View v) {
                ChatGroupInfoSettingsActivity.this.finish();
            }
        });
        final ChatGroupInfoSettingsActivity context = this;
        this.mActionBarButton = new CheckButton(this);
        this.mActionBarButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String name = ((UIEditText) ChatGroupInfoSettingsActivity.this.findViewById(R.id.lobi_chat_group_info_settings_group_name)).getText().toString();
                String description = ((UIEditText) ChatGroupInfoSettingsActivity.this.findViewById(R.id.lobi_chat_group_info_settings_group_description)).getText().toString();
                if (ChatGroupInfoSettingsActivity.this.hasTextChanges(name, description)) {
                    ChatGroupInfoSettingsActivity.this.postChanges(name, description, groupDetail, currentUser, context);
                }
            }
        });
        actionBar.addActionBarButton(this.mActionBarButton);
    }

    private void setLeaderOptions(UserValue userValue, final String guid) {
        findViewById(R.id.lobi_chat_group_info_settings_group_description_leader_only_area).setVisibility(0);
        ((OneLine) ((ListRow) findViewById(R.id.lobi_chat_group_info_settings_group_description_leader_only)).getContent(1)).getTextView(0).setText(R.string.lobi_only_leader_can_change);
        findViewById(R.id.lobi_chat_group_info_settings_group_wallpaper_leader_only_area).setVisibility(0);
        ((OneLine) ((ListRow) findViewById(R.id.lobi_chat_group_info_settings_group_wallpaper_leader_only)).getContent(1)).getTextView(0).setText(R.string.lobi_only_leader_can_change);
        findViewById(R.id.lobi_chat_group_info_settings_group_change_leader_area).setVisibility(0);
        ListRow listRowLeader = (ListRow) findViewById(R.id.lobi_chat_group_info_settings_group_change_leader);
        TwoLine twoLine = (TwoLine) listRowLeader.getContent(1);
        twoLine.getTextView(0).setText(R.string.lobi_only_leader_can_change);
        twoLine.getTextView(1).setText(userValue.getName());
        ((ImageLoaderView) ((FrameLayout) listRowLeader.getContent(0)).findViewById(R.id.lobi_list_row_content_image_loader_big_image_view)).loadImage(userValue.getIcon());
        listRowLeader.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putString(PathRouter.PATH, ChatGroupInfoLeaderChange.PATH_CHANGE_LEADER);
                extras.putString("EXTRAS_GROUP_VALUE", guid);
                PathRouter.startPath(extras);
            }
        });
    }

    private void setGroupIconWallpaper(GroupValue groupValue, UserValue userValue, GroupPermissionValue permission) {
        setGroupIcon(groupValue, userValue, permission.canUpdateIcon);
        setGroupWallpaper(groupValue, userValue, permission.canUpdateWallpaper);
    }

    private void setGroupName(String name, boolean canUpdate) {
        UIEditText nameView = (UIEditText) findViewById(R.id.lobi_chat_group_info_settings_group_name);
        nameView.setText(name);
        OnTextChangedListener changeTextOnListener = new OnTextChangedListener() {
            public void onTextChanged(UIEditText textInput, CharSequence text, int start, int before, int after) {
                ChatGroupInfoSettingsActivity.this.updateConfirmButtonStatus();
            }
        };
        if (canUpdate) {
            nameView.setOnTextChangedListener(changeTextOnListener);
            nameView.setOnClickListener(null);
            return;
        }
        nameView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(ChatGroupInfoSettingsActivity.this, ChatGroupInfoSettingsActivity.this.getString(R.string.lobi_only_leader_can_change), 0).show();
            }
        });
        nameView.setFocusable(false);
    }

    private void setGroupDescription(String description, boolean canUpdate) {
        UIEditText descriptionView = (UIEditText) findViewById(R.id.lobi_chat_group_info_settings_group_description);
        if (!TextUtils.isEmpty(description)) {
            descriptionView.setText(description);
        }
        OnTextChangedListener changeTextOnListener = new OnTextChangedListener() {
            public void onTextChanged(UIEditText textInput, CharSequence text, int start, int before, int after) {
                ChatGroupInfoSettingsActivity.this.updateConfirmButtonStatus();
            }
        };
        if (canUpdate) {
            descriptionView.setOnTextChangedListener(changeTextOnListener);
            descriptionView.setOnClickListener(null);
            return;
        }
        descriptionView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(ChatGroupInfoSettingsActivity.this, ChatGroupInfoSettingsActivity.this.getString(R.string.lobi_only_leader_can_change), 0).show();
            }
        });
        descriptionView.setFocusable(false);
    }

    private void setGroupIcon(final GroupValue group, final UserValue userValue, boolean canUpdate) {
        SectionView section = (SectionView) findViewById(R.id.lobi_chat_group_info_settings_section_icon);
        section.getOptionTextView().setTextColor(getResources().getColor(R.color.lobi_gray));
        section.setImage(Integer.valueOf(R.drawable.lobi_icn_edit));
        section.setOptionViewVisible(true);
        ListRow listRow = (ListRow) findViewById(R.id.lobi_chat_group_info_settings_group_icon);
        ((ImageLoaderView) ((FrameLayout) listRow.getContent(0)).findViewById(R.id.lobi_list_row_content_image_loader_big_image_view)).loadImage(group.getIcon(), 128);
        ((OneLine) listRow.getContent(1)).setText(0, getString(R.string.lobi_change_setting));
        if (canUpdate) {
            listRow.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    CoreAPI.getExecutorService().execute(new Runnable() {
                        public void run() {
                            TransactionDatastore.setKKValue(ChatGroupInfo.KEY1, "PICTURE_TYPE", Integer.valueOf(0));
                            TransactionDatastore.setKKValue(ChatGroupInfo.KEY1, "TOKEN", userValue.getToken());
                            TransactionDatastore.setKKValue(ChatGroupInfo.KEY1, ChatGroupInfo.GROUP_UID, group.getUid());
                            TransactionDatastore.setKKValue(ChatGroupInfo.KEY1, "USER_UID", userValue.getUid());
                            ChatGroupInfoSettingsActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    SelectPictureMenuDialog.newInstance(KKey.ImageIntentManager.GROUP_ICON, R.string.lobi_update_icon, false, 0).show(ChatGroupInfoSettingsActivity.this.getSupportFragmentManager(), SelectPictureMenuDialog.class.getCanonicalName());
                                }
                            });
                        }
                    });
                }
            });
        } else {
            listRow.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    Toast.makeText(ChatGroupInfoSettingsActivity.this, ChatGroupInfoSettingsActivity.this.getString(R.string.lobi_only_leader_can_change), 0).show();
                }
            });
        }
    }

    private void setGroupWallpaper(final GroupValue group, final UserValue userValue, boolean canUpdate) {
        String string;
        SectionView section = (SectionView) findViewById(R.id.lobi_chat_group_info_settings_section_wallpaper);
        section.getOptionTextView().setTextColor(getResources().getColor(R.color.lobi_gray));
        section.setImage(Integer.valueOf(R.drawable.lobi_icn_edit));
        ListRow listRow = (ListRow) findViewById(R.id.lobi_chat_group_info_settings_group_wallpaper);
        FrameLayout iconArea = (FrameLayout) listRow.getContent(0);
        final LightBox lightBox = new LightBox(this);
        setupLightBox(lightBox);
        ImageLoaderView wallpaperView = (ImageLoaderView) iconArea.findViewById(R.id.lobi_list_row_content_image_loader_big_image_view);
        wallpaperView.loadImage(group.getWallpaper());
        if (!TextUtils.isEmpty(group.getWallpaper())) {
            ((ImageView) iconArea.findViewById(R.id.lobi_list_row_content_image_loader_big_zoom_icon)).setVisibility(0);
            wallpaperView.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    lightBox.show((ImageView) v, ((Activity) v.getContext()).getWindow());
                }
            });
        }
        OneLine oneLine = (OneLine) listRow.getContent(1);
        if (canUpdate) {
            string = getString(R.string.lobi_change_setting);
        } else {
            string = getString(R.string.lobi_only_leader_can_change);
        }
        oneLine.setText(0, string);
        if (canUpdate) {
            section.setOptionViewVisible(false);
            listRow.setOnClickListener(new OnClickListener() {
                CustomDialog dialog;

                public void onClick(View v) {
                    ChatGroupInfoSettingsActivity.this.changeWallpaper(group, userValue, !TextUtils.isEmpty(group.getWallpaper()));
                }
            });
            return;
        }
        listRow.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(ChatGroupInfoSettingsActivity.this, ChatGroupInfoSettingsActivity.this.getString(R.string.lobi_only_leader_can_change), 0).show();
            }
        });
    }

    private void updateConfirmButtonStatus() {
        if (hasTextChanges(((UIEditText) findViewById(R.id.lobi_chat_group_info_settings_group_name)).getText().toString(), ((UIEditText) findViewById(R.id.lobi_chat_group_info_settings_group_description)).getText().toString())) {
            this.mActionBarButton.setEnable();
        } else {
            this.mActionBarButton.setDisable();
        }
    }

    private boolean hasTextChanges(String name, String description) {
        if (TextUtils.equals(name, this.mDefaultName) && TextUtils.equals(description, this.mDefaultDescription)) {
            return false;
        }
        return true;
    }

    private void setupLightBox(LightBox lightbox) {
        final Context context = this;
        lightbox.setOnDismissListener(new OnDismissListener() {
            public void onDismiss(ImageView v) {
            }
        });
        if (ManifestUtil.hasWriteExternalStoragePermission(context)) {
            lightbox.setOnContentShowListener(new OnContentShowListener() {
                public void onContentShow(View content) {
                    Toast.makeText(content.getContext(), context.getString(R.string.lobi_save_image_to_gallery), 0).show();
                }
            });
            lightbox.setOnContentLongClickListener(new OnContentLongClickListener() {
                public void onContentLongClick(final View content) {
                    if (!PictureUtil.requestWritePermissionIfNotGranted((Activity) content.getContext())) {
                        final ExecutorService executor = Executors.newSingleThreadExecutor();
                        executor.submit(new Runnable() {
                            public void run() {
                                GalleryUtil.saveImageToLocal(context, ((BitmapDrawable) content.getDrawable()).getBitmap());
                                context.runOnUiThread(new Runnable() {
                                    public void run() {
                                        Toast.makeText(content.getContext(), context.getString(R.string.lobi_saved_to), 0).show();
                                    }
                                });
                                executor.shutdown();
                            }
                        });
                    }
                }
            });
        }
    }

    private void changeWallpaper(final GroupValue group, final UserValue currentUser, final boolean isSet) {
        CoreAPI.getExecutorService().execute(new Runnable() {
            public void run() {
                TransactionDatastore.setKKValue(ChatGroupInfo.KEY1, "PICTURE_TYPE", Integer.valueOf(1));
                TransactionDatastore.setKKValue(ChatGroupInfo.KEY1, "TOKEN", currentUser.getToken());
                TransactionDatastore.setKKValue(ChatGroupInfo.KEY1, ChatGroupInfo.GROUP_UID, group.getUid());
                TransactionDatastore.setKKValue(ChatGroupInfo.KEY1, "USER_UID", currentUser.getUid());
                ChatGroupInfoSettingsActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        SelectPictureMenuDialog.newInstance(KKey.ImageIntentManager.GROUP_WALLPAPER, R.string.lobi_update_wallpaper, isSet, R.string.lobi_restore_defaults).show(ChatGroupInfoSettingsActivity.this.getSupportFragmentManager(), SelectPictureMenuDialog.class.getCanonicalName());
                    }
                });
            }
        });
    }

    private void postChanges(String name, String description, GroupValue groupDetail, UserValue currentUser, Activity context) {
        this.mActionBarButton.setDisable();
        final CustomProgressDialog progress = new CustomProgressDialog(this);
        progress.setMessage(getString(R.string.lobi_loading_loading));
        progress.show();
        Map<String, String> params = new HashMap();
        params.put("token", currentUser.getToken());
        params.put("uid", groupDetail.getUid());
        params.put("name", name);
        params.put("description", description);
        final GroupValue groupValue = groupDetail;
        final UserValue userValue = currentUser;
        final Activity activity = context;
        DefaultAPICallback<PostGroup> callback = new DefaultAPICallback<PostGroup>(this) {
            public void onResponse(PostGroup t) {
                GroupDetailValue.Builder builder = new GroupDetailValue.Builder(TransactionDatastore.getGroupDetail(groupValue.getUid(), userValue.getUid()));
                builder.setName(t.groupDetail.getName());
                builder.setDescription(t.groupDetail.getDescription());
                TransactionDatastore.setGroupDetail(builder.build(), userValue.getUid());
                Builder builder2 = new Builder(TransactionDatastore.getGroup(groupValue.getUid(), userValue.getUid()));
                builder2.setName(t.groupDetail.getName());
                builder2.setDescription(t.groupDetail.getDescription());
                TransactionDatastore.setGroup(builder2.build(), userValue.getUid());
                runOnUiThread(new Runnable() {
                    public void run() {
                        progress.dismiss();
                        activity.finish();
                    }
                });
            }
        };
        callback.setProgress(progress);
        CoreAPI.postGroup(params, callback);
    }
}
