package com.kayac.lobi.sdk.activity.menu;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources.NotFoundException;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.kayac.lobi.libnakamap.PathRoutedActivity;
import com.kayac.lobi.libnakamap.collection.MutablePair;
import com.kayac.lobi.libnakamap.components.ActionBar;
import com.kayac.lobi.libnakamap.components.ActionBar.BackableContent;
import com.kayac.lobi.libnakamap.components.CustomCheckbox;
import com.kayac.lobi.libnakamap.components.CustomDialog;
import com.kayac.lobi.libnakamap.components.CustomProgressDialog;
import com.kayac.lobi.libnakamap.components.ImageLoaderCircleView;
import com.kayac.lobi.libnakamap.components.ListRow;
import com.kayac.lobi.libnakamap.components.ListRow.OneLine;
import com.kayac.lobi.libnakamap.components.PathRouter;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.functional.Functional;
import com.kayac.lobi.libnakamap.functional.Functional.Predicater;
import com.kayac.lobi.libnakamap.functional.Functional.Reducer;
import com.kayac.lobi.libnakamap.net.APIRes.GetDefaultUser;
import com.kayac.lobi.libnakamap.net.APIRes.GetMeBlockingUsers;
import com.kayac.lobi.libnakamap.net.APIRes.PostMeBlockingUsersRemove;
import com.kayac.lobi.libnakamap.net.CoreAPI;
import com.kayac.lobi.libnakamap.net.CoreAPI.DefaultAPICallback;
import com.kayac.lobi.libnakamap.utils.ModuleUtil;
import com.kayac.lobi.libnakamap.utils.NakamapBroadcastManager;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.sdk.R;
import com.kayac.lobi.sdk.Version;
import com.kayac.lobi.sdk.activity.setting.WebViewSetting;
import com.kayac.lobi.sdk.auth.AccountUtil;
import com.kayac.lobi.sdk.utils.SDKBridge;
import com.rekoo.libs.net.URLCons;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.apache.james.mime4j.field.ContentTypeField;

public class MenuActivity extends PathRoutedActivity {
    public static final String EXTRAS_FROM_CHAT_SDK = "EXTRAS_FROM_CHAT_SDK";
    public static final String PATH_MENU = "/menu";
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if ("profile_updated".equals(intent.getAction())) {
                MenuActivity.this.renderBlockUser(AccountDatastore.getCurrentUser());
            }
        }
    };
    private boolean mIsFromChatSdk;
    private View mLoginContainer;

    private static final class OnRemoveBlockingListener implements OnClickListener {
        private MenuActivity mActivity;
        private UserValue mUser;

        public static final class BlockingUserAdapter extends BaseAdapter {
            private final Context mContext;
            private List<MutablePair<UserValue, Boolean>> mItems = new ArrayList();

            public BlockingUserAdapter(Context context) {
                this.mContext = context;
            }

            public void setItems(List<MutablePair<UserValue, Boolean>> items) {
                this.mItems = items;
            }

            public List<MutablePair<UserValue, Boolean>> getItems() {
                return this.mItems;
            }

            public int getCount() {
                return this.mItems.size();
            }

            public Object getItem(int i) {
                return this.mItems.get(i);
            }

            public long getItemId(int position) {
                return (long) position;
            }

            public View getView(int i, View convertView, ViewGroup container) {
                ListRow view;
                if (convertView == null) {
                    view = OnRemoveBlockingListener.listRowBuilder(this.mContext);
                } else {
                    view = (ListRow) convertView;
                }
                MutablePair<UserValue, Boolean> item = (MutablePair) getItem(i);
                ((BlockingUserHolder) view.getTag()).bind(new BlockingUserValue(((UserValue) item.first).getIcon(), ((UserValue) item.first).getName(), ((Boolean) item.second).booleanValue()));
                return view;
            }
        }

        private static final class BlockingUserHolder {
            public final CustomCheckbox checkbox;
            public final ImageLoaderCircleView image;
            public final TextView text;

            public BlockingUserHolder(ImageLoaderCircleView image, TextView text, CustomCheckbox checkbox) {
                this.image = image;
                this.text = text;
                this.checkbox = checkbox;
            }

            public void bind(BlockingUserValue value) {
                this.image.loadImage(value.url);
                this.text.setText(value.text);
                this.checkbox.setChecked(value.checked);
            }
        }

        public static final class BlockingUserValue {
            public final boolean checked;
            public final String text;
            public final String url;

            public BlockingUserValue(String url, String text, boolean checked) {
                this.url = url;
                this.text = text;
                this.checked = checked;
            }
        }

        private OnRemoveBlockingListener() {
        }

        public void setActivity(MenuActivity activity) {
            this.mActivity = activity;
        }

        public void setUser(UserValue user) {
            this.mUser = user;
        }

        public void onClick(View v) {
            CustomProgressDialog progress = new CustomProgressDialog(this.mActivity);
            progress.setMessage(this.mActivity.getString(R.string.lobi_loading_loading));
            progress.show();
            Map<String, String> params = new HashMap();
            params.put("token", this.mUser.getToken());
            DefaultAPICallback<GetMeBlockingUsers> onGetMeBlockingUsers = new DefaultAPICallback<GetMeBlockingUsers>(this.mActivity) {
                public void onResponse(final GetMeBlockingUsers t) {
                    super.onResponse(t);
                    OnRemoveBlockingListener.this.mActivity.runOnUiThread(new Runnable() {
                        public void run() {
                            OnRemoveBlockingListener.this.showBlockingUserList(t);
                        }
                    });
                }
            };
            onGetMeBlockingUsers.setProgress(progress);
            CoreAPI.getMeBlockingUsers(params, onGetMeBlockingUsers);
        }

        private void showBlockingUserList(GetMeBlockingUsers t) {
            if (t.users.size() == 0) {
                Toast.makeText(this.mActivity, this.mActivity.getString(R.string.lobi_none_found), 0).show();
                return;
            }
            final View listView = new ListView(this.mActivity);
            listView.setDivider(this.mActivity.getResources().getDrawable(R.drawable.lobi_line_gray));
            BlockingUserAdapter adapter = new BlockingUserAdapter(this.mActivity);
            final List<MutablePair<UserValue, Boolean>> items = new ArrayList();
            for (UserValue user : t.users) {
                items.add(new MutablePair(user, Boolean.valueOf(true)));
            }
            adapter.setItems(items);
            adapter.notifyDataSetChanged();
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                    MutablePair<UserValue, Boolean> item = (MutablePair) ((BlockingUserAdapter) listView.getAdapter()).getItem(arg2);
                    item.second = Boolean.valueOf(!((Boolean) item.second).booleanValue());
                    ((BlockingUserHolder) arg1.getTag()).checkbox.setChecked(((Boolean) item.second).booleanValue());
                }
            });
            final CustomDialog dialog = new CustomDialog(this.mActivity, listView);
            dialog.setTitle(R.string.lobi_unblock);
            dialog.setPositiveButton(this.mActivity.getString(17039370), new OnClickListener() {
                public void onClick(View v) {
                    dialog.dismiss();
                    OnRemoveBlockingListener.this.updateBlockingUsers(items);
                }
            });
            dialog.show();
        }

        private void updateBlockingUsers(final List<MutablePair<UserValue, Boolean>> items) {
            CoreAPI.getExecutorService().execute(new Runnable() {
                public void run() {
                    final String uids = (String) Functional.reduce(Functional.filter(items, new Predicater<MutablePair<UserValue, Boolean>>() {
                        public boolean predicate(MutablePair<UserValue, Boolean> t) {
                            return !((Boolean) t.second).booleanValue();
                        }
                    }), "", new Reducer<MutablePair<UserValue, Boolean>, String>() {
                        public String reduce(String previusValue, MutablePair<UserValue, Boolean> currentValue) {
                            return previusValue + "," + ((UserValue) currentValue.first).getUid();
                        }
                    });
                    OnRemoveBlockingListener.this.mActivity.runOnUiThread(new Runnable() {
                        public void run() {
                            final CustomProgressDialog innerProgress = new CustomProgressDialog(OnRemoveBlockingListener.this.mActivity);
                            innerProgress.setMessage(OnRemoveBlockingListener.this.mActivity.getString(R.string.lobi_loading_loading));
                            innerProgress.show();
                            Map<String, String> params = new HashMap();
                            params.put("token", OnRemoveBlockingListener.this.mUser.getToken());
                            params.put("users", uids);
                            DefaultAPICallback<PostMeBlockingUsersRemove> callback = new DefaultAPICallback<PostMeBlockingUsersRemove>(OnRemoveBlockingListener.this.mActivity) {
                                public void onResponse(PostMeBlockingUsersRemove t) {
                                    OnRemoveBlockingListener.this.mActivity.runOnUiThread(new Runnable() {
                                        public void run() {
                                            innerProgress.dismiss();
                                        }
                                    });
                                }
                            };
                            callback.setProgress(innerProgress);
                            CoreAPI.postMeBlockingUsersRemove(params, callback);
                        }
                    });
                }
            });
        }

        private static final ListRow listRowBuilder(Context context) {
            ListRow row = (ListRow) LayoutInflater.from(context).inflate(R.layout.lobi_setting_dialog_row, null);
            Button b = (Button) row.getContent(2);
            b.setFocusable(false);
            b.setClickable(false);
            row.setTag(new BlockingUserHolder((ImageLoaderCircleView) row.getContent(0), (TextView) ((OneLine) row.getContent(1)).findViewById(R.id.lobi_line_0), (CustomCheckbox) row.getContent(2)));
            return row;
        }
    }

    public static void startSettingMenu(boolean isNoAnimation) {
        Bundle extras = new Bundle();
        extras.putString(PathRouter.PATH, PATH_MENU);
        if (isNoAnimation) {
            PathRouter.startPath(extras, 65536);
        } else {
            PathRouter.startPath(extras);
        }
    }

    public static void startSettingMenuFromChatSDK(boolean isNoAnimation) {
        Bundle extras = new Bundle();
        extras.putString(PathRouter.PATH, PATH_MENU);
        extras.putBoolean(EXTRAS_FROM_CHAT_SDK, true);
        if (isNoAnimation) {
            PathRouter.startPath(extras, 65536);
        } else {
            PathRouter.startPath(extras);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mIsFromChatSdk = getIntent().getBooleanExtra(EXTRAS_FROM_CHAT_SDK, false);
        setContentView(R.layout.lobisdk_menu_activity);
        UserValue currentUser = AccountDatastore.getCurrentUser();
        renderActionBar();
        renderBlockUser(currentUser);
        renderOthers();
        NakamapBroadcastManager broadcastManager = NakamapBroadcastManager.getInstance(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction("profile_updated");
        broadcastManager.registerReceiver(this.mBroadcastReceiver, filter);
    }

    protected void onResume() {
        super.onResume();
        checkAppLoginStatus();
    }

    protected void onDestroy() {
        super.onDestroy();
        NakamapBroadcastManager.getInstance(this).unregisterReceiver(this.mBroadcastReceiver);
    }

    public void finish() {
        super.finish();
    }

    private void renderBlockUser(UserValue user) {
        View container = findViewById(R.id.lobi_setting_general_block_user_list_area);
        if (this.mIsFromChatSdk) {
            container.setVisibility(0);
            ListRow listRow = (ListRow) findViewById(R.id.lobi_setting_general_block_user_list);
            ((OneLine) listRow.getContent(1)).setText(0, getString(R.string.lobi_blocking));
            OnRemoveBlockingListener listener = new OnRemoveBlockingListener();
            listener.setActivity(this);
            listener.setUser(user);
            listRow.findViewById(R.id.lobi_list_row_area).setOnClickListener(listener);
            return;
        }
        container.setVisibility(8);
    }

    private void renderOthers() {
        ListRow listRow;
        ListRow container = (ListRow) findViewById(R.id.lobi_menu_about);
        ((OneLine) container.getContent(1)).setText(0, getString(R.string.lobi_about_us));
        container.findViewById(R.id.lobi_list_row_area).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MenuActivity.this.showAboutUs(v);
            }
        });
        container = (ListRow) findViewById(R.id.lobi_setting_general_othre_terms_of_service);
        ((OneLine) container.getContent(1)).setText(0, getString(R.string.lobi_terms));
        container.findViewById(R.id.lobi_list_row_area).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(PathRouter.PATH, WebViewSetting.PATH_WEBVIEW_SETTINGS);
                bundle.putString("url", "http://lobi.co/terms");
                bundle.putString("actionBarTitle", MenuActivity.this.getString(R.string.lobi_terms));
                PathRouter.startPath(bundle);
            }
        });
        container = (ListRow) findViewById(R.id.lobi_setting_general_othre_act_on_sect);
        ((OneLine) container.getContent(1)).setText(0, getString(R.string.lobi_act_on_sect));
        container.findViewById(R.id.lobi_list_row_area).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(PathRouter.PATH, WebViewSetting.PATH_WEBVIEW_SETTINGS);
                bundle.putString("url", "http://nakamap.com/page/asct");
                bundle.putString("actionBarTitle", MenuActivity.this.getString(R.string.lobi_act_on_sect));
                PathRouter.startPath(bundle);
            }
        });
        View chatFaqContainer = findViewById(R.id.lobi_setting_general_help_faq_container);
        if (this.mIsFromChatSdk && ModuleUtil.chatIsAvailable()) {
            chatFaqContainer.setVisibility(0);
            listRow = (ListRow) findViewById(R.id.lobi_setting_general_help_faq);
            ((OneLine) listRow.getContent(1)).setText(0, getString(R.string.lobisdk_chat_faq));
            listRow.findViewById(R.id.lobi_list_row_area).setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString(PathRouter.PATH, WebViewSetting.PATH_WEBVIEW_SETTINGS);
                    bundle.putString("url", "http://lobi-faq.tumblr.com/tagged/sdk");
                    bundle.putString("actionBarTitle", MenuActivity.this.getString(R.string.lobisdk_chat_faq));
                    PathRouter.startPath(bundle);
                }
            });
        } else {
            chatFaqContainer.setVisibility(8);
        }
        View reqFaqContainer = findViewById(R.id.lobi_setting_general_help_rec_faq_container);
        if (this.mIsFromChatSdk || !ModuleUtil.recIsAvailable()) {
            reqFaqContainer.setVisibility(8);
        } else {
            listRow = (ListRow) findViewById(R.id.lobi_setting_general_help_rec_faq);
            ((OneLine) listRow.getContent(1)).setText(0, getString(R.string.lobisdk_rec_faq));
            listRow.findViewById(R.id.lobi_list_row_area).setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString(PathRouter.PATH, WebViewSetting.PATH_WEBVIEW_SETTINGS);
                    bundle.putString("url", "https://thanks.lobi.co/video/faq.html");
                    bundle.putString("actionBarTitle", MenuActivity.this.getString(R.string.lobisdk_rec_faq));
                    PathRouter.startPath(bundle);
                }
            });
        }
        container = (ListRow) findViewById(R.id.lobi_setting_general_othre_acknowledgments);
        ((OneLine) container.getContent(1)).setText(0, getString(R.string.lobi_acknowledgments));
        container.findViewById(R.id.lobi_list_row_area).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String content = null;
                try {
                    content = IOUtils.toString(MenuActivity.this.getResources().openRawResource(R.raw.lobi_license));
                } catch (NotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                if (content != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString(PathRouter.PATH, WebViewSetting.PATH_WEBVIEW_SETTINGS);
                    bundle.putString(URLCons.CONTENT, content);
                    bundle.putString("actionBarTitle", MenuActivity.this.getString(R.string.lobi_acknowledgments));
                    PathRouter.startPath(bundle);
                }
            }
        });
        View blogContainer = findViewById(R.id.lobi_menu_blog_container);
        if (this.mIsFromChatSdk) {
            blogContainer.setVisibility(8);
        } else {
            blogContainer.setVisibility(0);
            container = (ListRow) findViewById(R.id.lobi_menu_blog);
            ((OneLine) container.getContent(1)).setText(0, getString(R.string.lobi_information));
            container.findViewById(R.id.lobi_list_row_area).setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    MenuActivity.this.openUrl("http://blog.lobi.co");
                }
            });
        }
        container = (ListRow) findViewById(R.id.lobi_menu_contact_us);
        ((OneLine) container.getContent(1)).setText(0, getString(R.string.lobi_feedback));
        container.findViewById(R.id.lobi_list_row_area).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MenuActivity.this.startActivitySafe(MenuActivity.this.getStartMailClientIntent());
            }
        });
        this.mLoginContainer = findViewById(R.id.lobi_menu_bind_container);
        listRow = (ListRow) findViewById(R.id.lobi_menu_bind);
        ((OneLine) listRow.getContent(1)).setText(0, getString(R.string.lobisdk_login_lobi));
        listRow.findViewById(R.id.lobi_list_row_area).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                AccountUtil.bindToLobiAccount(5);
            }
        });
    }

    private void checkAppLoginStatus() {
        if (!this.mIsFromChatSdk) {
            UserValue currentUser = AccountDatastore.getCurrentUser();
            Map<String, String> params = new HashMap();
            params.put("token", currentUser.getToken());
            params.put("uid", currentUser.getUid());
            CoreAPI.getDefaultUser(params, new DefaultAPICallback<GetDefaultUser>(this) {
                public void onResponse(GetDefaultUser t) {
                    super.onResponse(t);
                    handleResponse(t.user);
                }

                public void onError(int statusCode, String responseBody) {
                    handleResponse(null);
                }

                public void onError(Throwable e) {
                    handleResponse(null);
                }

                private void handleResponse(final UserValue user) {
                    MenuActivity.this.mLoginContainer.post(new Runnable() {
                        public void run() {
                            MenuActivity.this.mLoginContainer.setVisibility(user == null ? 0 : 8);
                        }
                    });
                }
            });
        }
    }

    private void showAboutUs(View v) {
        final CustomDialog dialog = CustomDialog.createTextDialog(v.getContext(), getString(R.string.lobi_lobi_is_a_chat));
        dialog.setTitle(R.string.lobi_about_us);
        if (SDKBridge.checkIfNakamapNativeAppInstalled(v.getContext().getPackageManager())) {
            dialog.setPositiveButton(getString(R.string.lobi_ok), new OnClickListener() {
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        } else {
            dialog.setPositiveButton(getString(R.string.lobi_download), new OnClickListener() {
                public void onClick(View v) {
                    dialog.dismiss();
                    MenuActivity.this.openMarketWithReferrer();
                }
            });
            dialog.setNegativeButton(getString(R.string.lobi_cancel), new OnClickListener() {
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
        dialog.show();
    }

    private void openMarketWithReferrer() {
        openUrl(SDKBridge.createMarketUriWithReferer());
    }

    public void openUrl(String url) {
        startActivitySafe(new Intent("android.intent.action.VIEW", Uri.parse(url)));
    }

    private void startActivitySafe(Intent intent) {
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
        }
    }

    private void renderActionBar() {
        ((BackableContent) ((ActionBar) findViewById(R.id.lobi_action_bar)).getContent()).setOnBackButtonClickListener(new OnClickListener() {
            public void onClick(View v) {
                MenuActivity.this.finish();
            }
        });
    }

    public Intent getStartMailClientIntent() {
        String platform = Build.MANUFACTURER + " " + Build.MODEL + " " + Build.PRODUCT;
        String os = "Android " + VERSION.RELEASE;
        String uid = AccountDatastore.getCurrentUser().getUid();
        String title = getString(R.string.lobi_feedback);
        String message = getString(R.string.lobi_dear) + String.format("LobiCoreSDK: version %s\n", new Object[]{Version.versionName});
        if (ModuleUtil.chatIsAvailable()) {
            message = message + String.format("LobiChatSDK: version %s\n", new Object[]{ModuleUtil.chatVersionName()});
        }
        if (ModuleUtil.rankingIsAvailable()) {
            message = message + String.format("LobiRankingSDK: version %s\n", new Object[]{ModuleUtil.rankingVersionName()});
        }
        if (ModuleUtil.recIsAvailable()) {
            message = message + String.format("LobiRecSDK: version %s\n", new Object[]{ModuleUtil.recVersionName()});
        }
        message = message + String.format("platform: %s, os: %s, u:%s", new Object[]{platform, os, uid});
        Intent it = new Intent("android.intent.action.SEND");
        it.putExtra("android.intent.extra.EMAIL", new String[]{"info@lobi.co"});
        it.putExtra("android.intent.extra.TEXT", message);
        it.putExtra("android.intent.extra.SUBJECT", title);
        it.setType(ContentTypeField.TYPE_MESSAGE_RFC822);
        it.addCategory("android.intent.category.DEFAULT");
        return it;
    }
}
