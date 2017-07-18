package com.kayac.lobi.sdk.activity.group;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.kayac.lobi.libnakamap.PathRoutedActivity;
import com.kayac.lobi.libnakamap.components.ActionBar;
import com.kayac.lobi.libnakamap.components.ActionBar.BackableContent;
import com.kayac.lobi.libnakamap.components.ActionBar.Button;
import com.kayac.lobi.libnakamap.components.ActionBar.MenuContent;
import com.kayac.lobi.libnakamap.components.MenuDrawer;
import com.kayac.lobi.libnakamap.components.PathRouter;
import com.kayac.lobi.libnakamap.components.SearchBox;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.datastore.AccountDatastoreAsync;
import com.kayac.lobi.libnakamap.datastore.DatastoreAsyncCallback;
import com.kayac.lobi.libnakamap.net.APIRes.GetDefaultUserContacts;
import com.kayac.lobi.libnakamap.net.APIRes.GetDefaultUserFollowers;
import com.kayac.lobi.libnakamap.net.CoreAPI;
import com.kayac.lobi.libnakamap.net.CoreAPI.DefaultAPICallback;
import com.kayac.lobi.libnakamap.utils.DebugAssert;
import com.kayac.lobi.libnakamap.utils.Log;
import com.kayac.lobi.libnakamap.utils.NakamapSDKDatastore.Key;
import com.kayac.lobi.libnakamap.value.LobiAccountContactValue;
import com.kayac.lobi.libnakamap.value.StartupConfigValue;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.sdk.R;
import com.kayac.lobi.sdk.activity.profile.ProfileActivity;
import com.kayac.lobi.sdk.view.LobiBannerView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserFollowerListActivity extends PathRoutedActivity {
    public static final String EXTRAS_FROM_MENU = "from_menu";
    public static final String EXTRAS_SHOW_CONTACT = "show_contact";
    public static final String EXTRAS_USER_VALUE = "user_value";
    public static final String PATH_USER_FOLLOWERS = "/user_followers";
    public static final String PATH_USER_FOLLOWS = "/user_follows";
    private ActionBar mActionBar;
    private TextView mCategoryCounterView;
    private LobiAccountContactListAdapter mContactAdapter;
    private ListView mContactListView;
    private DrawerLayout mDrawerLayout;
    private boolean mIsMe;
    private boolean mShowContact;
    private UserValue mTargetUser;

    private class OnGetUserContact extends DefaultAPICallback<GetDefaultUserContacts> {
        public OnGetUserContact(Activity activity) {
            super(activity);
        }

        public void onResponse(final GetDefaultUserContacts t) {
            super.onResponse(t);
            runOnUiThread(new Runnable() {
                public void run() {
                    UserFollowerListActivity.this.loadUserData(t.loginContacts);
                }
            });
        }

        public void onError(int statusCode, String responseBody) {
            super.onError(statusCode, responseBody);
            runOnUiThread(new Runnable() {
                public void run() {
                    UserFollowerListActivity.this.loadUserData(null);
                }
            });
        }

        public void onError(Throwable e) {
            super.onError(e);
            runOnUiThread(new Runnable() {
                public void run() {
                    UserFollowerListActivity.this.loadUserData(null);
                }
            });
        }
    }

    private class OnGetUserFollowers extends DefaultAPICallback<GetDefaultUserFollowers> {
        public OnGetUserFollowers(Activity activity) {
            super(activity);
        }

        public void onResponse(final GetDefaultUserFollowers t) {
            super.onResponse(t);
            runOnUiThread(new Runnable() {
                public void run() {
                    UserFollowerListActivity.this.loadUserData(t.loginFollowers);
                }
            });
        }

        public void onError(int statusCode, String responseBody) {
            super.onError(statusCode, responseBody);
            runOnUiThread(new Runnable() {
                public void run() {
                    UserFollowerListActivity.this.loadUserData(null);
                }
            });
        }

        public void onError(Throwable e) {
            super.onError(e);
            runOnUiThread(new Runnable() {
                public void run() {
                    UserFollowerListActivity.this.loadUserData(null);
                }
            });
        }
    }

    private class ToProfileListener implements OnItemClickListener {
        private ToProfileListener() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            if (position >= UserFollowerListActivity.this.mContactListView.getHeaderViewsCount()) {
                ProfileActivity.startProfile(AccountDatastore.getCurrentUser(), UserFollowerListActivity.this.mContactAdapter.getItem(position - UserFollowerListActivity.this.mContactListView.getHeaderViewsCount()).getAppUser());
            }
        }
    }

    public static void startFollowerList(UserValue userValue) {
        Bundle bundle = new Bundle();
        bundle.putString(PathRouter.PATH, PATH_USER_FOLLOWERS);
        bundle.putParcelable(EXTRAS_USER_VALUE, userValue);
        PathRouter.startPath(bundle);
    }

    @TargetApi(5)
    public static void startFollowerListFromMenu() {
        Bundle bundle = new Bundle();
        bundle.putString(PathRouter.PATH, PATH_USER_FOLLOWERS);
        bundle.putBoolean(EXTRAS_FROM_MENU, true);
        bundle.putParcelable(EXTRAS_USER_VALUE, AccountDatastore.getCurrentUser());
        PathRouter.startPath(bundle, 65536);
    }

    public static void startContactList(UserValue userValue) {
        Bundle bundle = new Bundle();
        bundle.putString(PathRouter.PATH, PATH_USER_FOLLOWS);
        bundle.putParcelable(EXTRAS_USER_VALUE, userValue);
        bundle.putBoolean(EXTRAS_SHOW_CONTACT, true);
        PathRouter.startPath(bundle);
    }

    @TargetApi(5)
    public static void startContactListFromMenu() {
        Bundle bundle = new Bundle();
        bundle.putString(PathRouter.PATH, PATH_USER_FOLLOWS);
        bundle.putBoolean(EXTRAS_FROM_MENU, true);
        bundle.putParcelable(EXTRAS_USER_VALUE, AccountDatastore.getCurrentUser());
        bundle.putBoolean(EXTRAS_SHOW_CONTACT, true);
        PathRouter.startPath(bundle, 65536);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lobi_group_user_contact_list_activity);
        Intent intent = getIntent();
        if (intent == null) {
            finish();
            return;
        }
        this.mTargetUser = (UserValue) intent.getParcelableExtra(EXTRAS_USER_VALUE);
        if (this.mTargetUser == null) {
            Log.e(UserFollowerListActivity.class.getSimpleName(), "user value is null");
            finish();
            return;
        }
        this.mIsMe = this.mTargetUser.equals(AccountDatastore.getCurrentUser());
        this.mShowContact = intent.getBooleanExtra(EXTRAS_SHOW_CONTACT, false);
        initActionBar();
        this.mContactListView = (ListView) findViewById(R.id.lobi_group_contacts_list);
        this.mContactListView.setVisibility(0);
        initListHeader();
        this.mContactAdapter = new LobiAccountContactListAdapter(this);
        this.mContactListView.setAdapter(this.mContactAdapter);
        this.mContactListView.setOnItemClickListener(new ToProfileListener());
        if (this.mShowContact && this.mIsMe) {
            ((SearchBox) findViewById(R.id.lobi_search_box)).getEditText().addTextChangedListener(new TextWatcher() {
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    UserFollowerListActivity.this.mContactAdapter.getFilter().filter(s.toString());
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void afterTextChanged(Editable s) {
                }
            });
        } else {
            ((SearchBox) findViewById(R.id.lobi_search_box)).setVisibility(8);
        }
        if (intent.getBooleanExtra(EXTRAS_FROM_MENU, false)) {
            this.mDrawerLayout = MenuDrawer.setMenuDrawer(this, (DrawerLayout) findViewById(R.id.drawer_layout), (ViewGroup) findViewById(R.id.content_frame));
        } else {
            MenuDrawer.disableMenuDrawer((DrawerLayout) findViewById(R.id.drawer_layout));
        }
    }

    private void initActionBar() {
        this.mActionBar = (ActionBar) findViewById(R.id.lobi_action_bar);
        DebugAssert.assertNotNull(this.mActionBar);
        if (getIntent().getBooleanExtra(EXTRAS_FROM_MENU, false)) {
            this.mActionBar.setContent(new MenuContent(this));
            MenuContent content = (MenuContent) this.mActionBar.getContent();
            DebugAssert.assertNotNull(content);
            content.setText(this.mShowContact ? R.string.lobi_profile_details_follow : R.string.lobi_profile_details_follower);
            content.setOnMenuButtonClickListener(new OnClickListener() {
                public void onClick(View v) {
                    UserFollowerListActivity.this.mDrawerLayout.openDrawer(8388611);
                }
            });
            Button actionBarButton = new Button(this);
            actionBarButton.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    UserFollowerListActivity.this.finish();
                }
            });
            actionBarButton.setIconImage(R.drawable.lobi_action_bar_close_selector);
            this.mActionBar.addActionBarButtonWithSeparator(actionBarButton);
            return;
        }
        this.mActionBar.setContent(new BackableContent(this));
        BackableContent content2 = (BackableContent) this.mActionBar.getContent();
        content2.setText(this.mShowContact ? R.string.lobi_profile_details_follow : R.string.lobi_profile_details_follower);
        content2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                UserFollowerListActivity.this.finish();
            }
        });
    }

    private void initListHeader() {
        LinearLayout header = (LinearLayout) getLayoutInflater().inflate(R.layout.lobi_follow_list_category_header, (ViewGroup) null);
        this.mCategoryCounterView = (TextView) header.findViewById(R.id.lobi_follow_list_category_header_count);
        this.mContactListView.addHeaderView(header, null, false);
        this.mContactListView.setHeaderDividersEnabled(false);
    }

    private void updateCategoryCounter(int count) {
        if (this.mCategoryCounterView != null) {
            this.mCategoryCounterView.setText(String.format("(%d)", new Object[]{Integer.valueOf(count)}));
        }
    }

    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if (intent != null) {
            if (this.mContactAdapter.getCount() == 0) {
                getUserFollowersFromServer(this.mTargetUser.getUid());
            }
            if (intent.getBooleanExtra(EXTRAS_FROM_MENU, false)) {
                MenuDrawer.setMenuItems(this.mDrawerLayout);
            }
        }
    }

    private void getUserFollowersFromServer(String userId) {
        startLoading();
        Map<String, String> params = new HashMap();
        params.put("token", AccountDatastore.getCurrentUser().getToken());
        params.put("uid", userId);
        params.put("login_filter", "1");
        if (this.mShowContact) {
            CoreAPI.getDefaultUserContacts(params, new OnGetUserContact(this));
        } else {
            CoreAPI.getDefaultUserFollowers(params, new OnGetUserFollowers(this));
        }
    }

    private void loadUserData(List<LobiAccountContactValue> followers) {
        stopLoading();
        if (followers != null && followers.size() != 0) {
            showEmptyView(0);
            List<LobiAccountContactValue> fixedContacts = new ArrayList(followers.size());
            fixedContacts.addAll(followers);
            updateCategoryCounter(fixedContacts.size());
            this.mContactAdapter.addItems(fixedContacts);
            this.mContactAdapter.notifyDataSetChanged();
        } else if (this.mContactAdapter.getCount() <= 0) {
            if (this.mIsMe) {
                AccountDatastoreAsync.getValue(Key.STARTUP_CONFIG, new DatastoreAsyncCallback<StartupConfigValue>() {
                    public void onResponse(final StartupConfigValue config) {
                        UserFollowerListActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                if (config != null) {
                                    if (((LobiBannerView) UserFollowerListActivity.this.findViewById(R.id.lobi_no_contact_banner_bannerview)).setBannerInfo(config.getCoreConfig().game, config.getCoreConfig().contactBanner)) {
                                        UserFollowerListActivity.this.showEmptyView(R.id.lobi_contacts_list_empty_banner_layout);
                                        return;
                                    }
                                }
                                UserFollowerListActivity.this.showEmptyView(R.id.lobi_contacts_list_empty_view_layout);
                            }
                        });
                    }
                });
            } else {
                showEmptyView(R.id.lobi_contacts_list_empty_view_layout);
            }
            updateCategoryCounter(0);
        }
    }

    private void showEmptyView(int id) {
        View noItemView = findViewById(R.id.lobi_contacts_list_empty_view_layout);
        View noItemBanner = findViewById(R.id.lobi_contacts_list_empty_banner_layout);
        if (id == 0) {
            noItemView.setVisibility(4);
            noItemBanner.setVisibility(4);
        } else if (id == noItemView.getId()) {
            noItemView.setVisibility(0);
            noItemBanner.setVisibility(4);
            noItemText = (TextView) findViewById(R.id.lobi_no_contact_textview);
            if (this.mShowContact) {
                noItemText.setText(this.mIsMe ? R.string.lobi_follow_list_no_item : R.string.lobi_follow_list_no_shown_item);
            } else {
                noItemText.setText(this.mIsMe ? R.string.lobi_follower_list_no_item : R.string.lobi_follower_list_no_shown_item);
            }
            if (this.mIsMe) {
                noItemText.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.lobi_icn_none_follower, 0, 0);
            }
        } else if (id == noItemBanner.getId()) {
            noItemView.setVisibility(4);
            noItemBanner.setVisibility(0);
            noItemText = (TextView) findViewById(R.id.lobi_no_contact_banner_textview);
            if (this.mShowContact) {
                noItemText.setText(R.string.lobi_follow_list_no_item_with_banner);
            } else {
                noItemText.setText(R.string.lobi_follower_list_no_item_with_banner);
            }
        }
    }
}
