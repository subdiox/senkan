package com.kayac.lobi.libnakamap.components;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.kayac.lobi.libnakamap.PathRoutedActivity;
import com.kayac.lobi.libnakamap.components.ListRow.OneLine;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.datastore.AccountDatastoreAsync;
import com.kayac.lobi.libnakamap.datastore.DatastoreAsyncCallback;
import com.kayac.lobi.libnakamap.net.APIRes.GetDefaultUser;
import com.kayac.lobi.libnakamap.net.CoreAPI;
import com.kayac.lobi.libnakamap.net.CoreAPI.DefaultAPICallback;
import com.kayac.lobi.libnakamap.utils.DebugAssert;
import com.kayac.lobi.libnakamap.utils.ModuleUtil;
import com.kayac.lobi.libnakamap.utils.NakamapSDKDatastore.Key;
import com.kayac.lobi.libnakamap.value.NotificationValue;
import com.kayac.lobi.libnakamap.value.StartupConfigValue;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.sdk.R;
import com.kayac.lobi.sdk.activity.ad.AdBaseActivity;
import com.kayac.lobi.sdk.activity.ad.AdRecommendActivity;
import com.kayac.lobi.sdk.activity.group.UserFollowerListActivity;
import com.kayac.lobi.sdk.activity.menu.MenuActivity;
import com.kayac.lobi.sdk.activity.profile.ProfileActivity;
import com.kayac.lobi.sdk.auth.AccountUtil;
import com.kayac.lobi.sdk.view.LobiBannerView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MenuDrawer {
    private static final String PATH_RANKING_ACTIVITY = "/ranking";
    private static final String PATH_REC_ACTIVITY = "/rec_playlist";
    private static final String TAG = MenuDrawer.class.getName();

    public static class ContentFrame extends LinearLayout {
        public ContentFrame(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public ContentFrame(Context context) {
            super(context);
        }

        public void setOffset(float slideOffset) {
        }
    }

    public static class Item {
        public final int icon;
        public final int id;
        public boolean isNew;
        public final String label;

        public Item(int icon, String label, int id) {
            this.icon = icon;
            this.label = label;
            this.id = id;
            this.isNew = false;
        }

        public Item(String label, int id) {
            this(0, label, id);
        }

        public Item(String label) {
            this(0, label, 0);
        }
    }

    public static class ItemHolder {
        public int id;
        public final int type;

        ItemHolder(int type) {
            this.type = type;
        }
    }

    public static class MenuAdapter extends ArrayAdapter<Item> {
        private final LayoutInflater mLayoutInflater;
        private OnClickListener mListener;
        private UserValue mLobiAccount;
        private UserValue mUser;

        interface ItemType {
            public static final int COUNTDOWN_ITEM = 2;
            public static final int MENU_ITEM = 1;
            public static final int PROFILE_ITEM = 3;
            public static final int SECTION_HEADER = 0;
        }

        public MenuAdapter(Context context, OnClickListener listener) {
            super(context, 0);
            this.mLayoutInflater = LayoutInflater.from(context);
            this.mListener = listener;
        }

        public void setLobiAccount(UserValue user) {
            this.mLobiAccount = user;
            notifyDataSetChanged();
        }

        public int getViewTypeCount() {
            return 4;
        }

        public int getItemViewType(int position) {
            Item item = (Item) getItem(position);
            if (item.id == 0) {
                return 0;
            }
            if (item.id == R.id.lobi_popup_menu_profile) {
                return 3;
            }
            return 1;
        }

        public View getSectionHeaderView(int position, View convertView, ViewGroup parent) {
            LinearLayout row;
            if (convertView == null) {
                row = (LinearLayout) this.mLayoutInflater.inflate(R.layout.lobi_menu_drawer_section_header, null, false);
                row.setTag(new SectionHeaderHolder(row));
            } else {
                row = (LinearLayout) convertView;
            }
            ((SectionHeaderHolder) row.getTag()).label.setText(((Item) getItem(position)).label);
            return row;
        }

        public View getMenuItemView(int position, View convertView, ViewGroup parent) {
            ListRow row;
            if (convertView == null) {
                row = (ListRow) this.mLayoutInflater.inflate(R.layout.lobi_menu_drawer_item, null, false);
                row.setTag(new MenuItemHolder(row));
            } else {
                row = (ListRow) convertView;
            }
            Item item = (Item) getItem(position);
            MenuItemHolder holder = (MenuItemHolder) row.getTag();
            holder.icon.setBackgroundResource(item.icon);
            holder.label.setText(item.label);
            holder.id = item.id;
            row.setBackgroundResource(R.color.lobi_white_background);
            return row;
        }

        public View getProfileItemView(int position, View convertView, ViewGroup parent) {
            FrameLayout row;
            if (convertView == null) {
                row = (FrameLayout) this.mLayoutInflater.inflate(R.layout.lobi_menu_drawer_header, null, false);
                row.setTag(new ProfileItemHolder(row, this.mListener));
            } else {
                row = (FrameLayout) convertView;
            }
            if (this.mUser != null) {
                Item item = (Item) getItem(position);
                ProfileItemHolder holder = (ProfileItemHolder) row.getTag();
                holder.icon.loadImage(this.mUser.getIcon());
                holder.cover.loadImage(this.mUser.getCover());
                holder.label.setText(this.mUser.getName());
                if (this.mLobiAccount == null) {
                    holder.lobiAccountLabel.setText(R.string.lobisdk_has_not_logged_in);
                } else {
                    holder.lobiAccountLabel.setText(holder.lobiAccountLabel.getResources().getString(R.string.lobisdk_lobi_name_format, new Object[]{this.mLobiAccount.getName()}));
                }
                holder.id = item.id;
                row.setBackgroundResource(R.color.lobi_white_background);
            }
            return row;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            switch (getItemViewType(position)) {
                case 0:
                    return getSectionHeaderView(position, convertView, parent);
                case 1:
                    return getMenuItemView(position, convertView, parent);
                case 3:
                    return getProfileItemView(position, convertView, parent);
                default:
                    return null;
            }
        }

        public boolean isEnabled(int position) {
            if (getItemViewType(position) == 0) {
                return false;
            }
            return super.isEnabled(position);
        }

        public boolean areAllItemsEnabled() {
            return false;
        }

        public void refreshItems(UserValue user) {
            if (user != null) {
                this.mUser = user;
                notifyDataSetChanged();
            }
        }
    }

    private static class MenuDrawerPathRouter implements OnItemClickListener, OnClickListener {
        private static final int NOT_SELECTED_ID = -1;
        final DrawerLayout mDrawerLayout;
        private int mId = -1;

        public MenuDrawerPathRouter(DrawerLayout drawerLayout) {
            this.mDrawerLayout = drawerLayout;
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg3) {
            ItemHolder holder = (ItemHolder) view.getTag();
            setClickedItem(holder != null ? holder.id : view.getId());
            onItemClickImpl();
        }

        private void setClickedItem(int id) {
            this.mId = id;
        }

        public void onClick(View v) {
            onItemClick(null, v, -1, 0);
        }

        public void onItemClickImpl() {
            if (this.mId != -1) {
                int id = this.mId;
                this.mId = -1;
                if (id == R.id.lobi_popup_menu_profile) {
                    if (ProfileActivity.PATH_MY_PROFILE.equals(PathRouter.getLastPath())) {
                        this.mDrawerLayout.closeDrawers();
                        return;
                    }
                    PathRouter.removePathsGreaterThan("/");
                    ProfileActivity.startProfileFromMenu();
                } else if (id == R.id.lobi_popup_menu_setting) {
                    MenuActivity.startSettingMenu(true);
                } else if (id == R.id.lobi_popup_menu_ranking) {
                    if ("/ranking".equals(PathRouter.getLastPath())) {
                        this.mDrawerLayout.closeDrawers();
                        return;
                    }
                    PathRouter.removePathsGreaterThan("/");
                    PathRouter.startPath("/ranking", 65536);
                } else if (id == R.id.lobi_popup_menu_rec) {
                    if ("/rec_playlist".equals(PathRouter.getLastPath())) {
                        this.mDrawerLayout.closeDrawers();
                        return;
                    }
                    String eventFields = (String) AccountDatastore.getValue(Key.EVENT_FIELDS_CACHE);
                    PathRouter.removePathsGreaterThan("/");
                    ModuleUtil.recOpenLobiPlayActivityWithEventFields(eventFields);
                } else if (id == R.id.lobi_popup_menu_follow_list || id == R.id.lobi_popup_menu_follower_list) {
                    if (MenuDrawer.getMenuAdapter(this.mDrawerLayout).mLobiAccount == null) {
                        this.mDrawerLayout.closeDrawers();
                        Context context = this.mDrawerLayout.getContext();
                        if (context instanceof PathRoutedActivity) {
                            LoginEntranceDialog.start((PathRoutedActivity) context, NotificationValue.SHEME_AUTHORITY_LOGIN, 2, 7);
                        }
                    } else if (id == R.id.lobi_popup_menu_follow_list) {
                        if (UserFollowerListActivity.PATH_USER_FOLLOWS.equals(PathRouter.getLastPath())) {
                            this.mDrawerLayout.closeDrawers();
                            return;
                        }
                        PathRouter.removePathsGreaterThan("/");
                        UserFollowerListActivity.startContactListFromMenu();
                    } else if (UserFollowerListActivity.PATH_USER_FOLLOWERS.equals(PathRouter.getLastPath())) {
                        this.mDrawerLayout.closeDrawers();
                    } else {
                        PathRouter.removePathsGreaterThan("/");
                        UserFollowerListActivity.startFollowerListFromMenu();
                    }
                } else if (id != R.id.lobi_popup_menu_game_ranking) {
                    DebugAssert.fail();
                } else if (AdRecommendActivity.PATH_AD_RECOMMEND.equals(PathRouter.getLastPath())) {
                    this.mDrawerLayout.closeDrawers();
                } else {
                    AdBaseActivity.startFromMenu(AdRecommendActivity.PATH_AD_RECOMMEND);
                }
            }
        }
    }

    public static class MenuItemHolder extends ItemHolder {
        final ImageView icon;
        final TextView label;
        final ImageView newIcon;

        MenuItemHolder(ListRow row) {
            super(1);
            this.icon = (ImageView) row.getContent(0);
            this.label = (TextView) ((OneLine) row.getContent(1)).findViewById(R.id.lobi_line_0);
            this.newIcon = (ImageView) row.getContent(2);
            this.newIcon.setImageResource(R.drawable.lobi_icn_arrow_black);
        }
    }

    public static class ProfileItemHolder extends ItemHolder {
        final ImageLoaderView cover;
        final ImageLoaderView icon;
        final TextView label;
        final TextView lobiAccountLabel;

        ProfileItemHolder(View row, OnClickListener listener) {
            super(3);
            this.icon = (ImageLoaderView) row.findViewById(R.id.lobi_menu_drawer_user_icon);
            this.cover = (ImageLoaderView) row.findViewById(R.id.lobi_menu_drawer_user_cover);
            this.label = (TextView) row.findViewById(R.id.lobi_menu_drawer_user_name);
            this.lobiAccountLabel = (TextView) row.findViewById(R.id.lobi_menu_drawer_lobi_user_name);
        }
    }

    public static class SectionHeaderHolder extends ItemHolder {
        final TextView label;

        SectionHeaderHolder(View row) {
            super(0);
            this.label = (TextView) row.findViewById(R.id.lobi_section_view_text);
        }
    }

    public static ArrayList<Item> getOriginalItems(Context context, StartupConfigValue config) {
        ArrayList<Item> items = new ArrayList();
        if (context == null) {
            Log.e(TAG, "context null.");
        } else {
            Resources res = context.getResources();
            items.add(new Item(res.getString(R.string.lobi_profile), R.id.lobi_popup_menu_profile));
            items.add(new Item(res.getString(R.string.lobi_content_menu)));
            if (ModuleUtil.recIsAvailable() && config != null && config.getRecConfig().enabled) {
                items.add(new Item(R.drawable.lobi_icn_menu_rec, res.getString(R.string.lobi_sdk_menu_rec), R.id.lobi_popup_menu_rec));
            }
            if (ModuleUtil.rankingIsAvailable() && config != null && config.getRankingConfig().enabled) {
                items.add(new Item(R.drawable.lobi_icn_menu_ranking, res.getString(R.string.lobi_sdk_menu_ranking), R.id.lobi_popup_menu_ranking));
            }
            items.add(new Item(res.getString(R.string.lobi_user_menu)));
            items.add(new Item(R.drawable.lobi_icn_menu_follow, res.getString(R.string.lobisdk_menu_follow_list), R.id.lobi_popup_menu_follow_list));
            items.add(new Item(R.drawable.lobi_icn_menu_follower, res.getString(R.string.lobisdk_menu_follower_list), R.id.lobi_popup_menu_follower_list));
            items.add(new Item(R.drawable.lobi_icn_menu_setting, res.getString(R.string.lobi_settings), R.id.lobi_popup_menu_setting));
        }
        return items;
    }

    public static final DrawerLayout setMenuDrawer(final Activity activity, final DrawerLayout parent, final ViewGroup content) {
        ListView listView = (ListView) parent.findViewById(R.id.menu_drawer);
        MenuDrawerPathRouter listener = new MenuDrawerPathRouter(parent);
        parent.setDrawerListener(new DrawerListener() {
            public void onDrawerStateChanged(int state) {
            }

            public void onDrawerSlide(View drawerView, float slideOffset) {
                ((ContentFrame) content).setOffset(slideOffset);
            }

            public void onDrawerOpened(View drawerView) {
            }

            public void onDrawerClosed(View drawerView) {
                parent.setDrawerLockMode(0);
            }
        });
        listView.setFooterDividersEnabled(false);
        View footerView = LayoutInflater.from(activity).inflate(R.layout.lobi_menu_drawer_footer, null);
        listView.addFooterView(footerView, null, false);
        footerView.findViewById(R.id.lobi_menu_drawer_login_button).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                AccountUtil.bindToLobiAccount(6);
            }
        });
        final LobiBannerView lobiBanner = (LobiBannerView) footerView.findViewById(R.id.lobi_menu_drawer_lobi_banner);
        final MenuAdapter adapter = new MenuAdapter(activity, listener);
        listView.setAdapter(adapter);
        listView.setSelector(new ColorDrawable(0));
        listView.setOnItemClickListener(listener);
        AccountDatastoreAsync.getValue(Key.STARTUP_CONFIG, new DatastoreAsyncCallback<StartupConfigValue>() {
            public void onResponse(final StartupConfigValue config) {
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        Iterator it = MenuDrawer.getOriginalItems(activity, config).iterator();
                        while (it.hasNext()) {
                            adapter.add((Item) it.next());
                        }
                        adapter.notifyDataSetChanged();
                        if (config != null) {
                            if (lobiBanner.setBannerInfo(config.getCoreConfig().game, config.getCoreConfig().menuBanner)) {
                                lobiBanner.setVisibility(0);
                            } else {
                                lobiBanner.setVisibility(8);
                            }
                        }
                    }
                });
            }
        });
        return parent;
    }

    private static void loadLobiAccount(final ListView listView) {
        Context context = listView.getContext();
        UserValue currentUser = AccountDatastore.getCurrentUser();
        Map<String, String> params = new HashMap();
        params.put("token", currentUser.getToken());
        params.put("uid", currentUser.getUid());
        CoreAPI.getDefaultUser(params, new DefaultAPICallback<GetDefaultUser>(context) {
            private void set(final UserValue lobiAccount) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        listView.findViewById(R.id.lobi_menu_drawer_login_button).setVisibility(lobiAccount == null ? 0 : 8);
                        MenuDrawer.getMenuAdapter(listView).setLobiAccount(lobiAccount);
                    }
                });
            }

            public void onResponse(GetDefaultUser t) {
                set(t.user);
            }

            public void onError(int statusCode, String responseBody) {
                set(null);
            }

            public void onError(Throwable e) {
                set(null);
            }
        });
    }

    public static final void enableMenuDrawer(DrawerLayout parent) {
        parent.setDrawerLockMode(0);
    }

    public static final void disableMenuDrawer(DrawerLayout parent) {
        parent.setDrawerLockMode(1);
    }

    public static final void setMenuItems(DrawerLayout parent) {
        ListView listView = (ListView) parent.findViewById(R.id.menu_drawer);
        loadLobiAccount(listView);
        getMenuAdapter(listView).refreshItems(AccountDatastore.getCurrentUser());
    }

    private static MenuAdapter getMenuAdapter(DrawerLayout parent) {
        return getMenuAdapter((ListView) parent.findViewById(R.id.menu_drawer));
    }

    private static MenuAdapter getMenuAdapter(ListView listView) {
        ListAdapter adapter = listView.getAdapter();
        if (adapter instanceof HeaderViewListAdapter) {
            adapter = ((HeaderViewListAdapter) adapter).getWrappedAdapter();
        }
        return (MenuAdapter) adapter;
    }
}
