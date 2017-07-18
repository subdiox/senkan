package com.kayac.lobi.sdk.activity.profile;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.kayac.lobi.libnakamap.PathRoutedActivity;
import com.kayac.lobi.libnakamap.components.ActionBar;
import com.kayac.lobi.libnakamap.components.ActionBar.BackableContent;
import com.kayac.lobi.libnakamap.components.ActionBar.Button;
import com.kayac.lobi.libnakamap.components.ActionBar.MenuContent;
import com.kayac.lobi.libnakamap.components.CustomDialog;
import com.kayac.lobi.libnakamap.components.CustomDialog.EditTextContent;
import com.kayac.lobi.libnakamap.components.CustomProgressDialog;
import com.kayac.lobi.libnakamap.components.CustomTextView;
import com.kayac.lobi.libnakamap.components.ImageLoaderCircleView;
import com.kayac.lobi.libnakamap.components.ImageLoaderView;
import com.kayac.lobi.libnakamap.components.LobiFollowButton;
import com.kayac.lobi.libnakamap.components.LobiFragment;
import com.kayac.lobi.libnakamap.components.LoginEntranceDialog;
import com.kayac.lobi.libnakamap.components.MenuDrawer;
import com.kayac.lobi.libnakamap.components.PathRouter;
import com.kayac.lobi.libnakamap.components.ProfileCover;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.datastore.AccountDatastoreAsync;
import com.kayac.lobi.libnakamap.datastore.DatastoreAsyncCallback;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastore;
import com.kayac.lobi.libnakamap.net.APIDef.PostAccusations.RequestKey;
import com.kayac.lobi.libnakamap.net.APIRes.GetDefaultUser;
import com.kayac.lobi.libnakamap.net.APIRes.GetUser;
import com.kayac.lobi.libnakamap.net.APIRes.GetUserV3;
import com.kayac.lobi.libnakamap.net.APIRes.PostAccusations;
import com.kayac.lobi.libnakamap.net.CoreAPI;
import com.kayac.lobi.libnakamap.net.CoreAPI.DefaultAPICallback;
import com.kayac.lobi.libnakamap.utils.CustomTextViewUtil;
import com.kayac.lobi.libnakamap.utils.DebugAssert;
import com.kayac.lobi.libnakamap.utils.Log;
import com.kayac.lobi.libnakamap.utils.ModuleUtil;
import com.kayac.lobi.libnakamap.utils.NakamapSDKDatastore.Key;
import com.kayac.lobi.libnakamap.value.NotificationValue;
import com.kayac.lobi.libnakamap.value.ProfileValue;
import com.kayac.lobi.libnakamap.value.StartupConfigValue;
import com.kayac.lobi.libnakamap.value.StartupConfigValue.CoreConfig.Banner;
import com.kayac.lobi.libnakamap.value.StartupConfigValue.CoreConfig.Game;
import com.kayac.lobi.libnakamap.value.UserContactValue;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.libnakamap.value.UserValue.Builder;
import com.kayac.lobi.sdk.LobiCoreAPI.APICallback;
import com.kayac.lobi.sdk.R;
import com.kayac.lobi.sdk.activity.group.UserFollowerListActivity;
import com.kayac.lobi.sdk.activity.invitation.InvitationWebViewActivity;
import com.kayac.lobi.sdk.auth.AccountUtil;
import com.kayac.lobi.sdk.net.NakamapApi;
import com.kayac.lobi.sdk.utils.EmoticonUtil;
import com.kayac.lobi.sdk.view.LobiBannerView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public class ProfileActivity extends PathRoutedActivity {
    public static final String EXTRA_CURRENT_USER = "EXTRA_CURRENT_USER";
    public static final String EXTRA_FROM_MENU = "EXTRA_FROM_MENU";
    public static final String EXTRA_TARGET_USER = "EXTRA_TARGET_USER";
    public static final String ON_ACCOUNT_CHANGE = "com.kayac.lobi.libnakamap.activity.group.ON_ACCOUNT_CHANGE";
    public static final String PATH_MY_PROFILE = "/my_profile";
    public static final String PATH_PROFILE = "/profile";
    public static final String PROFILE_UPDATED = "profile_updated";
    private ActionBar mActionBar;
    public Button mActionBarButtonTrash;
    private DrawerLayout mDrawerLayout;
    private boolean mFromMenu;
    protected boolean mIsSuicide;

    public static final class MainFragment extends LobiFragment {
        public static final String ARGS_CURRENT_USER = "ARGS_CURRENT_USER";
        public static final String ARGS_IS_ME = "ARGS_IS_ME";
        public static final String ARGS_TARGET_USER = "ARGS_TARGET_USER";
        private LobiFollowButton mFollowButton;
        private boolean mLobiUserFollowing = false;
        private String mLobiUserUid = null;

        public static final MainFragment newInstance(UserValue currentUser, UserValue targetUser, boolean isMe) {
            MainFragment frag = new MainFragment();
            Bundle args = new Bundle();
            args.putParcelable(ARGS_CURRENT_USER, currentUser);
            args.putParcelable(ARGS_TARGET_USER, targetUser);
            args.putBoolean(ARGS_IS_ME, isMe);
            frag.setArguments(args);
            return frag;
        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
            View v = inflater.inflate(R.layout.lobi_profile_profile_fragment, container, false);
            ProfileCover cover = (ProfileCover) v.findViewById(R.id.lobi_profile_profile_fragment_profile_cover);
            cover.setContentLayout(R.layout.lobi_profile_profile_cover_content);
            Bundle arguments = getArguments();
            UserValue targetUser = (UserValue) arguments.getParcelable(ARGS_TARGET_USER);
            DebugAssert.assertNotNull((ImageLoaderView) cover.findViewById(R.id.lobi_profile_cover_image));
            cover.findViewById(R.id.lobi_profile_container_image_area).setOnClickListener(null);
            setUserNameAndDesc(cover, targetUser, arguments.getBoolean(ARGS_IS_ME));
            AccountDatastoreAsync.getValue(Key.STARTUP_CONFIG, new DatastoreAsyncCallback<StartupConfigValue>() {
                public void onResponse(StartupConfigValue config) {
                    final Activity activity = MainFragment.this.getActivity();
                    if (config != null && activity != null) {
                        final Game game = config.getCoreConfig().game;
                        final Banner profileBanner = config.getCoreConfig().profileBanner;
                        activity.runOnUiThread(new Runnable() {
                            public void run() {
                                LobiBannerView lobiBanner = (LobiBannerView) activity.findViewById(R.id.lobi_profile_profile_cover_content_lobi_banner);
                                if (lobiBanner.setBannerInfo(game, profileBanner)) {
                                    lobiBanner.setVisibility(0);
                                } else {
                                    lobiBanner.setVisibility(8);
                                }
                            }
                        });
                    }
                }
            });
            return v;
        }

        public void onResume() {
            super.onResume();
        }

        public static UserContactValue getUserContactValue(UserValue userValue) {
            return new UserContactValue(userValue.getUid(), userValue.getName(), userValue.getDescription(), userValue.getIcon(), userValue.getContactsCount(), 1, userValue.getContactedDate());
        }

        public void setUserProfile(ProfileValue profile) {
            setUser(profile.getUserValue());
            ProfileCover cover = (ProfileCover) getView().findViewById(R.id.lobi_profile_profile_fragment_profile_cover);
            setUserNameAndDesc(cover, profile.getUserValue(), getArguments().getBoolean(ARGS_IS_ME));
            TextView videos = (TextView) cover.findViewById(R.id.lobi_profile_profile_cover_content_videos_count);
            if (ModuleUtil.recIsAvailable()) {
                videos.setText(getActivity().getString(R.string.lobisdk_videos, new Object[]{Integer.valueOf(profile.getPostedVideosCount())}));
                videos.setVisibility(0);
            } else {
                videos.setVisibility(4);
            }
            ViewGroup bannerContainer = (ViewGroup) getView().findViewById(R.id.lobi_profile_banner_section);
            bannerContainer.removeAllViews();
            for (ProfileValue.Banner banner : profile.getBanners()) {
                LobiBannerView bannerView = new LobiBannerView(bannerContainer.getContext());
                if (bannerView.setBannerInfo(banner)) {
                    bannerContainer.addView(bannerView);
                }
            }
            loadLobiAccount(profile.getUserValue(), getArguments().getBoolean(ARGS_IS_ME));
            UserValue currentUser = (UserValue) getArguments().getParcelable(ARGS_CURRENT_USER);
            for (String module : new String[]{ModuleUtil.PACKAGE_RANKING_SDK, ModuleUtil.PACKAGE_REC_SDK}) {
                ModuleUtil.setProfileView(module, profile, this, getActivity(), getArguments().getBoolean(ARGS_IS_ME), currentUser.getToken());
            }
        }

        private void loadLobiAccount(final UserValue user, final boolean isMe) {
            Map<String, String> params = new HashMap();
            params.put("token", AccountDatastore.getCurrentUser().getToken());
            params.put("uid", user.getUid());
            CoreAPI.getDefaultUser(params, new DefaultAPICallback<GetDefaultUser>(null) {
                public void onResponse(final GetDefaultUser t) {
                    MainFragment.this.getView().post(new Runnable() {
                        public void run() {
                            MainFragment.this.setupLobiAccountSection(user, t, isMe);
                        }
                    });
                }

                public void onError(int statusCode, String responseBody) {
                    MainFragment.this.getView().post(new Runnable() {
                        public void run() {
                            MainFragment.this.setupLobiAccountSection(user, null, isMe);
                        }
                    });
                }
            });
        }

        private void setupLobiAccountSection(UserValue user, GetDefaultUser res, boolean isMe) {
            LinearLayout section = (LinearLayout) getView().findViewById(R.id.lobi_profile_profile_lobi_account_section);
            ImageLoaderCircleView icon = (ImageLoaderCircleView) section.findViewById(R.id.lobi_profile_profile_lobi_account_icon);
            FrameLayout iconFrame = (FrameLayout) section.findViewById(R.id.lobi_profile_profile_lobi_account_icon_frame);
            TextView name = (TextView) section.findViewById(R.id.lobi_profile_profile_lobi_account_name);
            TextView status = (TextView) section.findViewById(R.id.lobi_profile_profile_lobi_account_status);
            LinearLayout friendSection = (LinearLayout) section.findViewById(R.id.lobi_profile_profile_lobi_account_friend_section);
            ViewGroup followersColumn = (ViewGroup) friendSection.findViewById(R.id.lobi_profile_profile_lobi_account_followers_column);
            ViewGroup contactsColumn = (ViewGroup) friendSection.findViewById(R.id.lobi_profile_profile_lobi_account_contacts_column);
            TextView followersCountText = (TextView) followersColumn.findViewById(R.id.lobi_profile_profile_lobi_account_followers_count);
            TextView contactsCountText = (TextView) contactsColumn.findViewById(R.id.lobi_profile_profile_lobi_account_contacts_count);
            this.mFollowButton = (LobiFollowButton) section.findViewById(R.id.lobi_profile_profile_lobi_account_follow);
            if (res == null) {
                friendSection.setVisibility(8);
                if (isMe) {
                    iconFrame.setVisibility(8);
                    OnClickListener anonymousClass3 = new OnClickListener() {
                        public void onClick(View v) {
                            AccountUtil.bindToLobiAccount(2);
                        }
                    };
                    section.setOnClickListener(anonymousClass3);
                    this.mFollowButton.setOnClickListener(anonymousClass3);
                    section.setVisibility(0);
                    return;
                }
                section.setVisibility(8);
                return;
            }
            UserValue lobiUser = res.user;
            long contactsCount = res.contactsCount;
            long followersCount = res.followersCount;
            this.mLobiUserFollowing = res.followingDate >= 0;
            this.mLobiUserUid = lobiUser.getUid();
            section.setVisibility(0);
            iconFrame.setVisibility(0);
            icon.loadImage(lobiUser.getIcon(), 64);
            name.setText(lobiUser.getName());
            status.setText(getString(R.string.lobisdk_has_logged_in));
            contactsCountText.setText(String.format("%d", new Object[]{Long.valueOf(contactsCount)}));
            followersCountText.setText(String.format("%d", new Object[]{Long.valueOf(followersCount)}));
            friendSection.setVisibility(0);
            setupFollowButton(isMe);
            section.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    MainFragment.this.openMainAccount(v.getContext(), MainFragment.this.mLobiUserUid);
                }
            });
            final UserValue userValue = user;
            contactsColumn.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    UserFollowerListActivity.startContactList(userValue);
                }
            });
            userValue = user;
            followersColumn.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    UserFollowerListActivity.startFollowerList(userValue);
                }
            });
        }

        private void setupFollowButton(boolean isMe) {
            DebugAssert.assertNotNull(this.mFollowButton);
            DebugAssert.assertNotNull(this.mLobiUserUid);
            if (isMe) {
                this.mFollowButton.setVisibility(8);
                return;
            }
            this.mFollowButton.setVisibility(0);
            this.mFollowButton.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    MainFragment.this.onClickFollowBtn();
                }
            });
            this.mFollowButton.setFollowingStatus(this.mLobiUserFollowing);
        }

        private void onClickFollowBtn() {
            AccountUtil.requestCurrentUserBindingState(new DefaultAPICallback<Boolean>(null) {
                public void onResponse(Boolean binded) {
                    final ProfileActivity activity = (ProfileActivity) MainFragment.this.getActivity();
                    if (binded == null || !binded.booleanValue()) {
                        if (activity != null) {
                            LoginEntranceDialog.start(activity, NotificationValue.SHEME_AUTHORITY_LOGIN, 1, 1);
                        }
                    } else if (MainFragment.this.mLobiUserFollowing) {
                        NakamapApi.unfollowLobiAccount(MainFragment.this.mLobiUserUid, new APICallback() {
                            public void onResult(int code, JSONObject response) {
                                if (code == 0) {
                                    MainFragment.this.mLobiUserFollowing = false;
                                    MainFragment.this.mFollowButton.setFollowingStatus(MainFragment.this.mLobiUserFollowing);
                                } else if (activity != null) {
                                    activity.showResponseError(response);
                                }
                            }
                        });
                    } else {
                        NakamapApi.followLobiAccount(MainFragment.this.mLobiUserUid, new APICallback() {
                            public void onResult(int code, JSONObject response) {
                                if (code == 0) {
                                    MainFragment.this.mLobiUserFollowing = true;
                                    MainFragment.this.mFollowButton.setFollowingStatus(MainFragment.this.mLobiUserFollowing);
                                } else if (activity != null) {
                                    activity.showResponseError(response);
                                }
                            }
                        });
                    }
                }

                public void onError(int statusCode, final String responseBody) {
                    MainFragment.this.mFollowButton.post(new Runnable() {
                        public void run() {
                            Toast.makeText(AnonymousClass8.this.getContext(), responseBody, 0).show();
                        }
                    });
                }

                public void onError(final Throwable e) {
                    MainFragment.this.mFollowButton.post(new Runnable() {
                        public void run() {
                            Toast.makeText(AnonymousClass8.this.getContext(), e.getMessage(), 0).show();
                        }
                    });
                }
            });
        }

        public void setUserNameAndDesc(ProfileCover cover, UserValue user, boolean isMe) {
            CharSequence charSequence;
            TextView editTextName = (TextView) cover.findViewById(R.id.lobi_profile_profile_cover_content_name);
            DebugAssert.assertNotNull(editTextName);
            editTextName.setText(EmoticonUtil.getEmoticonSpannedText(editTextName.getContext(), user.getName()));
            CustomTextView description = (CustomTextView) cover.findViewById(R.id.lobi_profile_profile_cover_content_description);
            DebugAssert.assertNotNull(description);
            description.setOnTextLinkClickedListener(CustomTextViewUtil.getOnTextLinkClickedListener(InvitationWebViewActivity.PATH_INVITATION, " "));
            if (TextUtils.isEmpty(user.getDescription())) {
                charSequence = "";
            } else {
                charSequence = EmoticonUtil.getEmoticonSpannedText(description.getContext(), user.getDescription());
            }
            description.setText(charSequence);
            if (isMe) {
                description.setHint(R.string.lobi_profile_you_can_input_profile_info);
            } else {
                description.setHint("");
            }
            cover.setEditButtonVisible(isMe);
        }

        public void setUser(UserValue user) {
            ProfileCover cover = (ProfileCover) getView().findViewById(R.id.lobi_profile_profile_fragment_profile_cover);
            cover.setCoverImage(user.getCover());
            cover.setIconImage(user.getIcon());
            Button actionBarButtonTrash = null;
            if (getActivity() != null) {
                actionBarButtonTrash = ((ProfileActivity) getActivity()).getActionBarButtonTrash();
            }
            if (actionBarButtonTrash != null) {
                actionBarButtonTrash.setOnClickListener(new OnClickListener() {
                    CustomDialog dialog;

                    public void onClick(View v) {
                        final List<String> items = new ArrayList();
                        final String doAccuse = MainFragment.this.getActivity().getString(R.string.lobi_accuse);
                        items.add(doAccuse);
                        this.dialog = CustomDialog.createMultiLineDialog(MainFragment.this.getActivity(), items, new OnItemClickListener() {
                            public void onItemClick(AdapterView<?> adapterView, View arg1, final int arg2, long arg3) {
                                MainFragment.this.getActivity().runOnUiThread(new Runnable() {
                                    public void run() {
                                        AnonymousClass9.this.dialog.dismiss();
                                        UserValue targetUser = (UserValue) MainFragment.this.getArguments().getParcelable(MainFragment.ARGS_TARGET_USER);
                                        if (((String) items.get(arg2)).equals(doAccuse)) {
                                            ProfileActivity.accuse(MainFragment.this.getActivity(), targetUser.getUid());
                                        } else {
                                            DebugAssert.fail();
                                        }
                                    }
                                });
                            }
                        });
                        this.dialog.setCanceledOnTouchOutside(true);
                        this.dialog.show();
                    }
                });
            }
        }

        private void openMainAccount(Context context, String uid) {
            Intent nativeAppIntent = new Intent("android.intent.action.VIEW");
            nativeAppIntent.setData(Uri.parse("lobi://user/" + uid));
            Intent webLobiIntent = new Intent("android.intent.action.VIEW");
            webLobiIntent.setData(Uri.parse("https://web.lobi.co/user/" + uid));
            try {
                context.startActivity(nativeAppIntent);
            } catch (ActivityNotFoundException e) {
                context.startActivity(webLobiIntent);
            }
        }
    }

    private static final class OnGetTargetUser extends DefaultAPICallback<GetUserV3> {
        private Context mContext;

        public OnGetTargetUser(Context context) {
            super(context);
            this.mContext = context;
        }

        public void onResponse(GetUserV3 t) {
            ProfileValue profileValue = t.profileValue;
            if (profileValue == null) {
                Toast.makeText(this.mContext, this.mContext.getString(R.string.lobi_none_found), 0).show();
                return;
            }
            UserValue userValue = profileValue.getUserValue();
            if (userValue == null) {
                Toast.makeText(this.mContext, this.mContext.getString(R.string.lobi_none_found), 0).show();
                return;
            }
            Bundle extras = new Bundle();
            extras.putString(PathRouter.PATH, ProfileActivity.PATH_PROFILE);
            extras.putParcelable(ProfileActivity.EXTRA_TARGET_USER, userValue);
            PathRouter.startPath(extras);
        }
    }

    private static final class OnGetUserV3 extends DefaultAPICallback<GetUserV3> {
        private final ProfileActivity mActivity;
        private CustomProgressDialog mProgressDialog;

        public OnGetUserV3(ProfileActivity context) {
            super(context);
            this.mActivity = context;
        }

        public void setProgressDialog(CustomProgressDialog progressDialog) {
            this.mProgressDialog = progressDialog;
            super.setProgress(progressDialog);
        }

        public void onResponse(final GetUserV3 t) {
            this.mActivity.runOnUiThread(new Runnable() {
                public void run() {
                    OnGetUserV3.this.mProgressDialog.dismiss();
                    MainFragment frag = (MainFragment) OnGetUserV3.this.mActivity.getSupportFragmentManager().findFragmentById(R.id.lobi_fragment);
                    if (frag != null) {
                        frag.setUserProfile(t.profileValue);
                    }
                }
            });
        }

        public void onError(int statusCode, String responseBody) {
            super.onError(statusCode, responseBody);
            this.mActivity.runOnUiThread(new Runnable() {
                public void run() {
                    OnGetUserV3.this.mActivity.finish();
                }
            });
        }

        public void onError(Throwable e) {
            super.onError(e);
            this.mActivity.runOnUiThread(new Runnable() {
                public void run() {
                    OnGetUserV3.this.mActivity.finish();
                }
            });
        }
    }

    private static Bundle setExtras(UserValue currentUser) {
        Bundle bundle = new Bundle();
        bundle.putString(PathRouter.PATH, PATH_PROFILE);
        bundle.putParcelable(EXTRA_CURRENT_USER, currentUser);
        return bundle;
    }

    public static void startProfile(UserValue currentUser, UserValue targetUser) {
        DebugAssert.assertNotNull(currentUser);
        Bundle bundle = setExtras(currentUser);
        bundle.putParcelable(EXTRA_TARGET_USER, targetUser);
        bundle.putBoolean("EXTRA_FROM_MENU", false);
        PathRouter.startPath(bundle);
    }

    public static void startProfile(UserValue currentUser, UserContactValue userContactValue) {
        DebugAssert.assertNotNull(currentUser);
        Bundle bundle = setExtras(currentUser);
        Builder builder = new Builder();
        builder.setUid(userContactValue.getUid());
        builder.setDefault(false);
        builder.setName(userContactValue.getName());
        builder.setDescription(userContactValue.getDescription());
        builder.setIcon(userContactValue.getIcon());
        builder.setContactsCount(userContactValue.getContactsCount());
        builder.setContactedDate(userContactValue.getContactedDate());
        builder.setLng(Float.NaN);
        builder.setLat(Float.NaN);
        bundle.putParcelable(EXTRA_TARGET_USER, builder.build());
        bundle.putBoolean("EXTRA_FROM_MENU", false);
        PathRouter.startPath(bundle);
    }

    public static void startProfileFromMenu() {
        UserValue user = AccountDatastore.getCurrentUser();
        Bundle bundle = new Bundle();
        bundle.putString(PathRouter.PATH, PATH_MY_PROFILE);
        bundle.putParcelable(EXTRA_CURRENT_USER, user);
        bundle.putParcelable(EXTRA_TARGET_USER, user);
        bundle.putBoolean("EXTRA_FROM_MENU", true);
        PathRouter.startPath(bundle, 65536);
    }

    public static final void startProfileWithUserUid(Context context, String userUid) {
        UserValue currentUser = AccountDatastore.getCurrentUser();
        Map<String, String> params = new HashMap();
        params.put("token", currentUser.getToken());
        params.put("uid", userUid);
        CoreAPI.getUserV3(params, new OnGetTargetUser(context));
    }

    private void initActionBar(boolean fromMenu, UserValue user, boolean isMe) {
        this.mFromMenu = fromMenu;
        this.mActionBar = (ActionBar) findViewById(R.id.lobi_action_bar);
        DebugAssert.assertNotNull(this.mActionBar);
        if (fromMenu) {
            this.mActionBar.setContent(new MenuContent(this));
            MenuContent content = (MenuContent) this.mActionBar.getContent();
            DebugAssert.assertNotNull(content);
            content.setText(user.getName());
            content.setOnMenuButtonClickListener(new OnClickListener() {
                public void onClick(View v) {
                    ProfileActivity.this.mDrawerLayout.openDrawer(8388611);
                }
            });
            Button actionBarButton = new Button(this);
            actionBarButton.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    ProfileActivity.this.finish();
                }
            });
            actionBarButton.setIconImage(R.drawable.lobi_action_bar_close_selector);
            this.mActionBar.addActionBarButtonWithSeparator(actionBarButton);
        } else {
            this.mActionBar.setContent(new BackableContent(this));
            BackableContent content2 = (BackableContent) this.mActionBar.getContent();
            content2.setText(user.getName());
            content2.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    ProfileActivity.this.finish();
                }
            });
        }
        if (!isMe) {
            this.mActionBarButtonTrash = new Button(this);
            this.mActionBarButtonTrash.setIconImage(R.drawable.lobi_action_bar_button_alert_selector);
            this.mActionBar.addActionBarButton(this.mActionBarButtonTrash);
        }
    }

    public Button getActionBarButtonTrash() {
        return this.mActionBarButtonTrash;
    }

    public void getUserImages(String userUid) {
        if (userUid != null) {
            UserValue current = AccountDatastore.getCurrentUser();
            Map<String, String> params = new HashMap();
            params.put("token", current.getToken());
            params.put("uid", userUid);
            CoreAPI.getUser(params, new DefaultAPICallback<GetUser>(this) {
                public void onResponse(final GetUser t) {
                    TransactionDatastore.setUser(t.user);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            MainFragment frag = (MainFragment) ProfileActivity.this.getSupportFragmentManager().findFragmentById(R.id.lobi_fragment);
                            if (frag != null) {
                                frag.setUser(t.user);
                            }
                        }
                    });
                }
            });
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        UserValue targetUser;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lobi_profile_profile_activity);
        Bundle extras = getIntent().getExtras();
        DebugAssert.assertNotNull(extras);
        UserValue extraCurrentUser = (UserValue) extras.getParcelable(EXTRA_CURRENT_USER);
        UserValue extraTargetUser = (UserValue) extras.getParcelable(EXTRA_TARGET_USER);
        UserValue currentUser = extraCurrentUser != null ? extraCurrentUser : AccountDatastore.getCurrentUser();
        if (extraTargetUser != null) {
            targetUser = extraTargetUser;
        } else {
            targetUser = currentUser;
        }
        boolean isMe = AccountDatastore.getUsers().contains(targetUser);
        boolean fromMenu = extras.getBoolean("EXTRA_FROM_MENU", false);
        DebugAssert.assertNotNull((ActionBar) findViewById(R.id.lobi_action_bar));
        initActionBar(fromMenu, targetUser, isMe);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.lobi_fragment, MainFragment.newInstance(currentUser, targetUser, isMe));
        ft.commit();
        if (fromMenu) {
            this.mDrawerLayout = MenuDrawer.setMenuDrawer(this, (DrawerLayout) findViewById(R.id.drawer_layout), (ViewGroup) findViewById(R.id.content_frame));
        } else {
            MenuDrawer.disableMenuDrawer((DrawerLayout) findViewById(R.id.drawer_layout));
        }
    }

    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if (intent != null) {
            UserValue targetUser;
            Log.v("lobi-sdk", "[picture] onResume");
            CustomProgressDialog progress = new CustomProgressDialog(this);
            progress.setMessage(getString(R.string.lobi_loading_loading));
            progress.show();
            UserValue extraCurrentUser = (UserValue) intent.getParcelableExtra(EXTRA_CURRENT_USER);
            UserValue extraTargetUser = (UserValue) intent.getParcelableExtra(EXTRA_TARGET_USER);
            UserValue currentUser = extraCurrentUser != null ? extraCurrentUser : AccountDatastore.getCurrentUser();
            if (extraTargetUser != null) {
                targetUser = extraTargetUser;
            } else {
                targetUser = currentUser;
            }
            boolean fromMenu = intent.getBooleanExtra("EXTRA_FROM_MENU", false);
            Map<String, String> params = new HashMap();
            params.put("token", currentUser.getToken());
            params.put("uid", targetUser.getUid());
            OnGetUserV3 callback = new OnGetUserV3(this);
            callback.setProgressDialog(progress);
            CoreAPI.getUserV3(params, callback);
            if (fromMenu) {
                MenuDrawer.setMenuItems(this.mDrawerLayout);
            }
        }
    }

    protected void onPause() {
        super.onPause();
        Log.v("lobi-sdk", "[picture] onPause");
    }

    protected void onStop() {
        super.onStop();
        if (getIntent().getBooleanExtra("EXTRA_FROM_MENU", false)) {
            this.mDrawerLayout.closeDrawers();
        }
    }

    public void finish() {
        super.finish();
        if (this.mFromMenu) {
            overridePendingTransition(0, 0);
        }
    }

    public static void accuse(final Context context, final String users) {
        if (context != null && !TextUtils.isEmpty(users)) {
            final View newContent = new EditTextContent(context, context.getString(R.string.lobi_please_enter_your_reason_of_accusing), null, false);
            final CustomDialog dialog = new CustomDialog(context, newContent);
            dialog.setTitle(context.getString(R.string.lobi_please_enter_your_reason_of_accusing));
            dialog.setNegativeButton(context.getString(17039360), new OnClickListener() {
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.setPositiveButton(context.getString(17039370), new OnClickListener() {
                public void onClick(View v) {
                    dialog.dismiss();
                    CustomProgressDialog progress = new CustomProgressDialog(context);
                    progress.setMessage(context.getString(R.string.lobi_loading_loading));
                    progress.show();
                    Map<String, String> params = new HashMap();
                    params.put("token", AccountDatastore.getCurrentUser().getToken());
                    params.put("user", users);
                    params.put(RequestKey.REASON, newContent.getText().toString());
                    DefaultAPICallback<PostAccusations> callback = new DefaultAPICallback<PostAccusations>(context) {
                        public void onResponse(PostAccusations t) {
                            super.onResponse(t);
                            if (t.success) {
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        Toast.makeText(context, context.getString(R.string.lobi_reported), 0).show();
                                    }
                                });
                            }
                        }
                    };
                    callback.setProgress(progress);
                    CoreAPI.postAccusations(params, callback);
                }
            });
            dialog.show();
        }
    }
}
