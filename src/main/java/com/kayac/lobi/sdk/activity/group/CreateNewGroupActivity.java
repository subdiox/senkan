package com.kayac.lobi.sdk.activity.group;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.kayac.lobi.libnakamap.PathRoutedActivity;
import com.kayac.lobi.libnakamap.collection.Pair;
import com.kayac.lobi.libnakamap.components.ActionBar;
import com.kayac.lobi.libnakamap.components.ActionBar.BackableContent;
import com.kayac.lobi.libnakamap.components.ActionBar.CheckButton;
import com.kayac.lobi.libnakamap.components.ActionBar.CheckButton.State;
import com.kayac.lobi.libnakamap.components.CustomDialog;
import com.kayac.lobi.libnakamap.components.CustomProgressDialog;
import com.kayac.lobi.libnakamap.components.ListRow;
import com.kayac.lobi.libnakamap.components.ListRow.TwoLine;
import com.kayac.lobi.libnakamap.components.PathRouter;
import com.kayac.lobi.libnakamap.components.ToggleInterface;
import com.kayac.lobi.libnakamap.components.ToggleInterface.OnToggleStateChangedListener;
import com.kayac.lobi.libnakamap.components.ToggleOnOffButton;
import com.kayac.lobi.libnakamap.components.UIEditText;
import com.kayac.lobi.libnakamap.components.UIEditText.OnTextChangedListener;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastore;
import com.kayac.lobi.libnakamap.net.APIRes.GetPublicGroupsTree;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupJoinWithGroupUid;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroups;
import com.kayac.lobi.libnakamap.net.APIRes.PostPublicGroups;
import com.kayac.lobi.libnakamap.net.CoreAPI;
import com.kayac.lobi.libnakamap.net.CoreAPI.DefaultAPICallback;
import com.kayac.lobi.libnakamap.utils.DebugAssert;
import com.kayac.lobi.libnakamap.utils.GroupValueUtils;
import com.kayac.lobi.libnakamap.utils.Log;
import com.kayac.lobi.libnakamap.utils.NakamapSDKDatastore.Key;
import com.kayac.lobi.libnakamap.value.CategoryValue;
import com.kayac.lobi.libnakamap.value.GroupDetailValue;
import com.kayac.lobi.libnakamap.value.GroupDetailValue.Builder;
import com.kayac.lobi.libnakamap.value.GroupInterface;
import com.kayac.lobi.libnakamap.value.PublicCategoryValue;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.sdk.R;
import com.kayac.lobi.sdk.chat.activity.ChatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateNewGroupActivity extends PathRoutedActivity {
    public static final String EXTRA_FROM_PUBLIC_GROUPS = "fromPublicGroups";
    public static final String EXTRA_SELECTED_CATEGORY = "selectedCategory";
    public static final String PATH_NEW = "/new";
    private static final String TAG = "create_group";
    private CheckButton mActionBarButton;
    private CategoriesAdapter mAdapter;
    private TextView mCategory;
    private String mCategoryID;
    private CustomDialog mDialog;
    private boolean mFixedCategory;
    private boolean mIsOn;
    private final DefaultAPICallback<PostGroupJoinWithGroupUid> mOnJoinedGroup = new DefaultAPICallback<PostGroupJoinWithGroupUid>(this) {
        public void onResponse(PostGroupJoinWithGroupUid t) {
            Builder builder = new Builder(GroupValueUtils.convertToGroupDetailValue(t.group));
            builder.setLastChatAt(System.currentTimeMillis());
            GroupDetailValue groupDetailValue = builder.build();
            CategoryValue categoryValue = TransactionDatastore.getCategory(CreateNewGroupActivity.this.isPublicGroup() ? "public" : CategoryValue.TYPE_PRIVATE, CreateNewGroupActivity.this.mUserValue.getUid());
            categoryValue.getGroupDetails().add(groupDetailValue);
            TransactionDatastore.setCategory(categoryValue, CreateNewGroupActivity.this.mUserValue.getUid());
            TransactionDatastore.setGroupDetail(groupDetailValue, CreateNewGroupActivity.this.mUserValue.getUid());
            final Bundle extras = new Bundle();
            extras.putString(PathRouter.PATH, ChatActivity.PATH_CHAT);
            extras.putString("gid", groupDetailValue.getUid());
            extras.putString(ChatActivity.EXTRAS_STREAM_HOST, groupDetailValue.getStreamHost());
            runOnUiThread(new Runnable() {
                public void run() {
                    PathRouter.startPath(extras);
                    CreateNewGroupActivity.this.mProgress.dismiss();
                    CreateNewGroupActivity.this.finish();
                }
            });
        }
    };
    private DefaultAPICallback<PostGroups> mOnPostGroups = new DefaultAPICallback<PostGroups>(this) {
        public void onResponse(PostGroups t) {
            Map<String, String> params = new HashMap();
            params.put("token", CreateNewGroupActivity.this.mUserValue.getToken());
            params.put("uid", t.uid);
            CreateNewGroupActivity.this.mOnJoinedGroup.setProgress(CreateNewGroupActivity.this.mProgress);
            CoreAPI.postGroupJoinWithGroupUidV2(params, CreateNewGroupActivity.this.mOnJoinedGroup);
        }

        public void onError(int statusCode, String responseBody) {
            Log.v(CreateNewGroupActivity.TAG, "onError" + statusCode + " : " + responseBody);
            super.onError(statusCode, responseBody);
            runOnUiThread(new Runnable() {
                public void run() {
                    CreateNewGroupActivity.this.mActionBarButton.setEnable();
                }
            });
        }

        public void onError(Throwable e) {
            super.onError(e);
            runOnUiThread(new Runnable() {
                public void run() {
                    CreateNewGroupActivity.this.mActionBarButton.setEnable();
                }
            });
        }
    };
    private final DefaultAPICallback<PostPublicGroups> mOnPostPublicGroups = new DefaultAPICallback<PostPublicGroups>(this) {
        public void onResponse(PostPublicGroups t) {
            UserValue userValue = AccountDatastore.getCurrentUser();
            Map<String, String> params = new HashMap();
            params.put("token", userValue.getToken());
            params.put("uid", t.uid);
            params.put("members_count", "1");
            CreateNewGroupActivity.this.mOnJoinedGroup.setProgress(CreateNewGroupActivity.this.mProgress);
            CoreAPI.postGroupJoinWithGroupUidV2(params, CreateNewGroupActivity.this.mOnJoinedGroup);
        }

        public void onError(int statusCode, String responseBody) {
            super.onError(statusCode, responseBody);
            runOnUiThread(new Runnable() {
                public void run() {
                    CreateNewGroupActivity.this.mActionBarButton.setEnable();
                }
            });
        }

        public void onError(Throwable e) {
            super.onError(e);
            runOnUiThread(new Runnable() {
                public void run() {
                    CreateNewGroupActivity.this.mActionBarButton.setEnable();
                }
            });
        }
    };
    private CustomProgressDialog mProgress;
    private PublicCategoryValue mSelectedPublicCategory;
    private final UserValue mUserValue = AccountDatastore.getCurrentUser();
    private final List<PublicCategoryValue> mViewedCategories = new ArrayList();

    public static final class CategoriesAdapter extends BaseAdapter {
        private final List<PublicCategoryValue> mData = new ArrayList();
        private final LayoutInflater mLayoutInflater;

        public CategoriesAdapter(Context context) {
            this.mLayoutInflater = LayoutInflater.from(context);
        }

        public int getCount() {
            return this.mData.size();
        }

        public PublicCategoryValue getItem(int position) {
            return (PublicCategoryValue) this.mData.get(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public void addAll(List<PublicCategoryValue> data) {
            this.mData.addAll(data);
            notifyDataSetChanged();
        }

        public void clearAll() {
            this.mData.clear();
            notifyDataSetChanged();
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = this.mLayoutInflater.inflate(R.layout.lobi_community_list_item_category, null);
                view.setTag(new ItemHolder(view));
            } else {
                view = convertView;
            }
            PublicCategoryValue value = getItem(position);
            ItemHolder holder = (ItemHolder) view.getTag();
            TwoLine twoLine = (TwoLine) holder.container.getContent(1);
            holder.container.getContent(0).setVisibility(8);
            twoLine.setText(0, value.getTitle());
            twoLine.setVisibility(1, 8);
            if (value.getItems().size() <= 0) {
                holder.container.getContent(2).setVisibility(8);
            } else if ("group".equals(((Pair) value.getItems().get(0)).first)) {
                holder.container.getContent(2).setVisibility(8);
            } else {
                holder.container.getContent(2).setVisibility(0);
            }
            return view;
        }
    }

    public static final class ItemHolder {
        public final ListRow container;
        final View view;

        public ItemHolder(View view) {
            this.view = view;
            this.container = (ListRow) view.findViewById(R.id.lobi_community_title_row);
        }
    }

    private final class OnGetCategoriesTree extends DefaultAPICallback<GetPublicGroupsTree> {
        public OnGetCategoriesTree(Context context) {
            super(context);
        }

        public void onResponse(final GetPublicGroupsTree t) {
            runOnUiThread(new Runnable() {
                public void run() {
                    Log.v(CreateNewGroupActivity.TAG, "loaded from server");
                    CreateNewGroupActivity.this.displayPublicCategories(t.publiCategory, true);
                }
            });
        }

        public void onError(int statusCode, String responseBody) {
        }

        public void onError(Throwable e) {
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lobi_group_create_new_group_activity);
        ActionBar actionBar = (ActionBar) findViewById(R.id.lobi_action_bar);
        DebugAssert.assertNotNull(actionBar);
        BackableContent content = (BackableContent) actionBar.getContent();
        content.setText(R.string.lobi_create_group);
        content.setOnBackButtonClickListener(new OnClickListener() {
            public void onClick(View v) {
                CreateNewGroupActivity.this.finish();
            }
        });
        Bundle extras = getIntent().getExtras();
        this.mActionBarButton = new CheckButton(this);
        this.mActionBarButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (State.STATE_ENABLE == CreateNewGroupActivity.this.mActionBarButton.getState()) {
                    CreateNewGroupActivity.this.createNewGroup();
                }
            }
        });
        actionBar.addActionBarButton(this.mActionBarButton);
        boolean fromPublicGroups = extras.getBoolean(EXTRA_FROM_PUBLIC_GROUPS, false);
        ListRow container = (ListRow) findViewById(R.id.lobi_group_create_new_group_publicity_settings);
        TwoLine line = (TwoLine) container.getContent(1);
        ToggleOnOffButton toggle = (ToggleOnOffButton) ((FrameLayout) container.getContent(2)).findViewById(R.id.lobi_list_row_content_toggle_on_off);
        int i = (((Boolean) TransactionDatastore.getValue(Key.PUBLIC_CHAT_ENABLED, Boolean.valueOf(true))).booleanValue() && ((Boolean) TransactionDatastore.getValue(Key.PRIVATE_CHAT_ENABLED, Boolean.valueOf(true))).booleanValue()) ? 0 : 8;
        container.setVisibility(i);
        line.setText(0, getString(R.string.lobi_make));
        line.setText(1, getString(R.string.lobi_make));
        toggle.setOnToggleStateChangedListener(new OnToggleStateChangedListener() {
            public void onToggle(ToggleInterface toggle, boolean isOn) {
                CreateNewGroupActivity.this.mIsOn = isOn;
                CreateNewGroupActivity.this.toggleCategoryAreaVisibility(isOn);
            }
        });
        toggle.setState(fromPublicGroups);
        toggleCategoryAreaVisibility(fromPublicGroups);
        this.mIsOn = fromPublicGroups;
        TwoLine twoLine = (TwoLine) ((ListRow) findViewById(R.id.lobi_group_create_new_group_public_group_category)).getContent(1);
        twoLine.setText(0, getString(R.string.lobi_category));
        TextView description = twoLine.getTextView(1);
        this.mCategory = description;
        description.setTextColor(getResources().getColor(R.color.lobi_gray));
        description.setText(getString(R.string.lobi_select_category));
        UIEditText name = (UIEditText) findViewById(R.id.lobi_group_create_new_group_name);
        name.setOnTextChangedListener(new OnTextChangedListener() {
            public void onTextChanged(UIEditText textInput, CharSequence text, int start, int before, int after) {
                if (text.length() <= 0) {
                    CreateNewGroupActivity.this.mActionBarButton.setDisable();
                } else if (!CreateNewGroupActivity.this.mIsOn) {
                    CreateNewGroupActivity.this.mActionBarButton.setEnable();
                } else if (CreateNewGroupActivity.this.mCategoryID == null) {
                    CreateNewGroupActivity.this.mActionBarButton.setDisable();
                } else {
                    CreateNewGroupActivity.this.mActionBarButton.setEnable();
                }
            }
        });
        if (name.getText().length() <= 0) {
            this.mActionBarButton.setDisable();
        } else {
            this.mActionBarButton.setEnable();
        }
        this.mAdapter = new CategoriesAdapter(this);
        setCategorySelector();
        setupDialog();
        this.mFixedCategory = false;
        loadCategoriesTree(null);
        if (extras.containsKey(EXTRA_SELECTED_CATEGORY)) {
            PublicCategoryValue categoryValue = (PublicCategoryValue) extras.getParcelable(EXTRA_SELECTED_CATEGORY);
            if ("category".equals(categoryValue.getType())) {
                selectCategory(categoryValue);
            }
        }
    }

    private void toggleCategoryAreaVisibility(boolean isOn) {
        View view = findViewById(R.id.lobi_group_create_new_group_public_group_category_area);
        if (isOn) {
            if (getStringInEditText(R.id.lobi_group_create_new_group_name).length() <= 0) {
                this.mActionBarButton.setDisable();
            } else if (this.mCategoryID == null) {
                this.mActionBarButton.setDisable();
            } else {
                this.mActionBarButton.setEnable();
            }
            if (this.mFixedCategory) {
                view.setVisibility(8);
                return;
            } else {
                view.setVisibility(0);
                return;
            }
        }
        if (getStringInEditText(R.id.lobi_group_create_new_group_name).length() <= 0) {
            this.mActionBarButton.setDisable();
        } else {
            this.mActionBarButton.setEnable();
        }
        view.setVisibility(8);
    }

    private void setupDialog() {
        View listView = setupListView();
        this.mDialog = new CustomDialog((Context) this, listView);
        this.mDialog.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode != 4 || event.getAction() != 1 || CreateNewGroupActivity.this.mViewedCategories.size() <= 1) {
                    return false;
                }
                CreateNewGroupActivity.this.mDialog.setTitle(CreateNewGroupActivity.this.getResources().getString(R.string.lobi_select_category));
                CreateNewGroupActivity.this.displayPublicCategories((PublicCategoryValue) CreateNewGroupActivity.this.mViewedCategories.get(CreateNewGroupActivity.this.mViewedCategories.size() - 1));
                CreateNewGroupActivity.this.mViewedCategories.remove(CreateNewGroupActivity.this.mViewedCategories.size() - 1);
                return true;
            }
        });
        listView.setAdapter(this.mAdapter);
        this.mDialog.findViewById(R.id.lobi_custom_dialog_title_area).setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                if (CreateNewGroupActivity.this.mViewedCategories != null && CreateNewGroupActivity.this.mViewedCategories.size() > 0) {
                    CreateNewGroupActivity.this.displayPublicCategories((PublicCategoryValue) CreateNewGroupActivity.this.mViewedCategories.get(CreateNewGroupActivity.this.mViewedCategories.size() - 1));
                    if (CreateNewGroupActivity.this.mViewedCategories.size() > 1) {
                        CreateNewGroupActivity.this.mDialog.setTitle(CreateNewGroupActivity.this.getResources().getString(R.string.lobi_select_category));
                        CreateNewGroupActivity.this.mViewedCategories.remove(CreateNewGroupActivity.this.mViewedCategories.size() - 1);
                    }
                }
            }
        });
    }

    private boolean isPublicGroup() {
        return this.mIsOn;
    }

    protected void createNewGroup() {
        this.mProgress = new CustomProgressDialog(this);
        this.mProgress.setMessage(getString(R.string.lobi_loading_loading));
        this.mProgress.show();
        String name = getStringInEditText(R.id.lobi_group_create_new_group_name);
        String description = getStringInEditText(R.id.lobi_group_create_new_group_description);
        if (isPublicGroup()) {
            Map<String, String> params = new HashMap();
            params.put("token", this.mUserValue.getToken());
            params.put("name", name);
            params.put("description", description);
            params.put("category", this.mCategoryID);
            this.mOnPostPublicGroups.setProgress(this.mProgress);
            CoreAPI.postPublicGroups(params, this.mOnPostPublicGroups);
            return;
        }
        params = new HashMap();
        params.put("token", this.mUserValue.getToken());
        params.put("name", name);
        params.put("description", description);
        this.mOnPostGroups.setProgress(this.mProgress);
        CoreAPI.postGroups(params, this.mOnPostGroups);
    }

    String getStringInEditText(int id) {
        return ((EditText) findViewById(id)).getText().toString();
    }

    private ListView setupListView() {
        ListView listView = new ListView(this);
        listView.setDivider(null);
        listView.setBackgroundColor(getResources().getColor(R.color.lobi_white));
        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                CreateNewGroupActivity.this.mViewedCategories.add(CreateNewGroupActivity.this.mSelectedPublicCategory);
                PublicCategoryValue publicCategoryValue = CreateNewGroupActivity.this.mAdapter.getItem(position);
                if (publicCategoryValue.getItems() == null || publicCategoryValue.getItems().size() <= 0) {
                    CreateNewGroupActivity.this.loadCategoriesTree(publicCategoryValue.getId());
                    return;
                }
                if (publicCategoryValue.getItems().size() > 0 && "category".equals(publicCategoryValue.getItems().get(0))) {
                    PublicCategoryValue secondValue = ((Pair) publicCategoryValue.getItems().get(0)).second;
                    if (secondValue.getItems() == null || secondValue.getItems().size() <= 0) {
                        CreateNewGroupActivity.this.loadCategoriesTree(publicCategoryValue.getId());
                    }
                }
                CreateNewGroupActivity.this.displayPublicCategories(publicCategoryValue);
            }
        });
        return listView;
    }

    protected void setCategorySelector() {
        Context context = this;
        ListRow listRow = (ListRow) findViewById(R.id.lobi_group_create_new_group_public_group_category);
        TwoLine twoLine = (TwoLine) listRow.getContent(1);
        twoLine.setText(0, getString(R.string.lobi_category));
        this.mCategory = twoLine.getTextView(1);
        listRow.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CreateNewGroupActivity.this.mDialog.hideTitle();
                CreateNewGroupActivity.this.mViewedCategories.clear();
                CreateNewGroupActivity.this.loadCategoriesTree(null);
                CreateNewGroupActivity.this.mDialog.showTitle();
                CreateNewGroupActivity.this.mDialog.show();
            }
        });
    }

    protected String getCategoryTitle() {
        String title = "";
        if (this.mSelectedPublicCategory.getName().equals(((PublicCategoryValue) this.mViewedCategories.get(this.mViewedCategories.size() - 1)).getName())) {
            title = "";
        } else {
            title = " / " + this.mSelectedPublicCategory.getName();
        }
        return ((PublicCategoryValue) this.mViewedCategories.get(this.mViewedCategories.size() - 1)).getName();
    }

    protected void displayPublicCategories(PublicCategoryValue publicCategoryValue) {
        displayPublicCategories(publicCategoryValue, false);
    }

    private boolean canAddGroup(PublicCategoryValue publicCategoryValue) {
        for (Pair<String, GroupInterface> item : publicCategoryValue.getItems()) {
            if ("category".equals(item.first) && canAddGroup(item.second)) {
                return true;
            }
        }
        return publicCategoryValue.getPermissions().addGroup;
    }

    protected void displayPublicCategories(PublicCategoryValue publicCategoryValue, boolean isFromNetwork) {
        if (publicCategoryValue != null) {
            Log.v(TAG, "displaying categories");
            this.mSelectedPublicCategory = publicCategoryValue;
            if (this.mViewedCategories.size() == 0) {
                this.mViewedCategories.add(publicCategoryValue);
            }
            Log.v("nakamap-sdk", "public group dialog: " + this.mDialog);
            Log.v("nakamap-sdk", "public group dialog: " + getCategoryTitle());
            if (this.mDialog != null) {
                this.mDialog.setTitle(getResources().getString(R.string.lobi_select_category));
            }
            this.mAdapter.clearAll();
            List<PublicCategoryValue> categories = new ArrayList();
            for (Pair<String, GroupInterface> item : publicCategoryValue.getItems()) {
                if ("category".equals(item.first)) {
                    PublicCategoryValue categoryValue = item.second;
                    if (canAddGroup(categoryValue)) {
                        categories.add(categoryValue);
                    }
                }
            }
            Log.v("nakamap-sdk", "category type: " + publicCategoryValue.getType());
            Log.v("nakamap-sdk", "category size: " + categories.size());
            if (categories.size() == 0) {
                selectCategory(publicCategoryValue);
                if (isFromNetwork && PublicCategoryValue.TYPE_ROOT.equals(publicCategoryValue.getType())) {
                    this.mFixedCategory = true;
                    toggleCategoryAreaVisibility(true);
                    return;
                }
                return;
            }
            this.mAdapter.addAll(categories);
        }
    }

    protected void loadCategoriesTree(String id) {
        Map<String, String> params = new HashMap();
        params.put("token", AccountDatastore.getCurrentUser().getToken());
        if (id != null) {
            params.put("category", id);
        }
        CoreAPI.getPublicGroupsTree(params, new OnGetCategoriesTree(this));
    }

    protected void selectCategory(PublicCategoryValue publicCategoryValue) {
        if (publicCategoryValue == null || !publicCategoryValue.getPermissions().addGroup) {
            this.mCategory.setText("");
            this.mCategoryID = null;
            this.mActionBarButton.setDisable();
        } else {
            this.mCategory.setText(publicCategoryValue.getName());
            this.mCategory.setTextColor(getResources().getColor(R.color.lobi_orange));
            this.mCategoryID = publicCategoryValue.getId();
            this.mActionBarButton.setEnable();
        }
        if (this.mDialog != null) {
            this.mDialog.cancel();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public void finish() {
        super.finish();
    }
}
