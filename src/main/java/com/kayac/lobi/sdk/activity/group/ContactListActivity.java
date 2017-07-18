package com.kayac.lobi.sdk.activity.group;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import com.kayac.lobi.libnakamap.PathRoutedActivity;
import com.kayac.lobi.libnakamap.collection.MutablePair;
import com.kayac.lobi.libnakamap.collection.Pair;
import com.kayac.lobi.libnakamap.components.ActionBar;
import com.kayac.lobi.libnakamap.components.ActionBar.BackableContent;
import com.kayac.lobi.libnakamap.components.CustomDialog;
import com.kayac.lobi.libnakamap.components.CustomProgressDialog;
import com.kayac.lobi.libnakamap.components.ListRow;
import com.kayac.lobi.libnakamap.components.ListRow.IndexTarget;
import com.kayac.lobi.libnakamap.components.PathRouter;
import com.kayac.lobi.libnakamap.components.SearchBox;
import com.kayac.lobi.libnakamap.components.SectionView;
import com.kayac.lobi.libnakamap.datastore.AccountDDL.KKey.UpdateAt;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.UserContact.Order;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastore;
import com.kayac.lobi.libnakamap.net.APIDef.GetUsers.RequestKey;
import com.kayac.lobi.libnakamap.net.APIRes.GetMeChatFriends;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupMembers;
import com.kayac.lobi.libnakamap.net.CoreAPI;
import com.kayac.lobi.libnakamap.net.CoreAPI.DefaultAPICallback;
import com.kayac.lobi.libnakamap.utils.DebugAssert;
import com.kayac.lobi.libnakamap.utils.NakamapSDKDatastore.Key;
import com.kayac.lobi.libnakamap.value.GroupDetailValue;
import com.kayac.lobi.libnakamap.value.UserContactValue;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.sdk.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactListActivity extends PathRoutedActivity {
    public static final String EXTRA_GROUP_UID = "EXTRA_GROUP_UID";
    public static final String EXTRA_USER_UID = "EXTRA_USER_UID";
    public static final String PATH_CONTACTS = "/contacts";
    public static final String PATH_CONTACTS_FROM_NOTIFICATION = "//contacts";
    private static final IndexTarget<UserContactValue> mIndexTarget = new IndexTarget<UserContactValue>() {
        public String pickup(UserContactValue t) {
            return t.getName().toUpperCase();
        }
    };
    protected ContactListAdapter mAdapter;
    private View mFilterNotHitView;
    protected String mGroupUid;
    private View mHeader;
    private View mNotFoundView;
    private List<UserContactValue> mUserContacts;

    private static final class AddGroupListener implements OnItemClickListener {
        private ContactListActivity mActivity;
        private UserValue mCurrentUser;
        private GroupDetailValue mGroupDetail;
        private CustomDialog mInviteGroupDialog;
        private CustomProgressDialog mProgressDialog;

        private AddGroupListener() {
        }

        public void setActivity(ContactListActivity activity) {
            this.mActivity = activity;
        }

        public void setUser(UserValue user) {
            this.mCurrentUser = user;
        }

        public void setGroupDetail(GroupDetailValue groupDetail) {
            this.mGroupDetail = groupDetail;
        }

        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            final MutablePair<Pair<String, UserContactValue>, Boolean> contacts = (MutablePair) arg0.getItemAtPosition(arg2);
            this.mInviteGroupDialog = CustomDialog.createTextDialog(this.mActivity, this.mActivity.getString(R.string.lobi_add__string__to_this, new Object[]{((UserContactValue) ((Pair) contacts.first).second).getName()}));
            this.mInviteGroupDialog.setTitle(R.string.lobi_add_friend);
            this.mInviteGroupDialog.setNegativeButton(this.mActivity.getString(17039360), new OnClickListener() {
                public void onClick(View v) {
                    AddGroupListener.this.mInviteGroupDialog.dismiss();
                }
            });
            this.mInviteGroupDialog.setPositiveButton(this.mActivity.getString(17039370), new OnClickListener() {
                public void onClick(View v) {
                    AddGroupListener.this.mInviteGroupDialog.dismiss();
                    AddGroupListener.this.postGroupMember(contacts);
                }
            });
            this.mInviteGroupDialog.show();
        }

        private void postGroupMember(MutablePair<Pair<String, UserContactValue>, Boolean> contacts) {
            this.mProgressDialog = new CustomProgressDialog(this.mActivity);
            this.mProgressDialog.setMessage(this.mActivity.getString(R.string.lobi_loading_loading));
            this.mProgressDialog.show();
            Map<String, String> params = new HashMap();
            params.put("token", this.mCurrentUser.getToken());
            params.put("uid", this.mGroupDetail.getUid());
            params.put("users", ((UserContactValue) ((Pair) contacts.first).second).getUid());
            CoreAPI.postGroupMembers(params, new DefaultAPICallback<PostGroupMembers>(this.mActivity) {
                public void onResponse(PostGroupMembers t) {
                    AddGroupListener.this.mActivity.runOnUiThread(new Runnable() {
                        public void run() {
                            AddGroupListener.this.dialogDismiss();
                            Toast.makeText(AnonymousClass3.this.getContext(), R.string.lobi_added_to_group, 0).show();
                        }
                    });
                }

                public void onError(int statusCode, String responseBody) {
                    super.onError(statusCode, responseBody);
                    AddGroupListener.this.dialogDismiss();
                }

                public void onError(Throwable e) {
                    super.onError(e);
                    AddGroupListener.this.dialogDismiss();
                }
            });
        }

        private void dialogDismiss() {
            if (this.mProgressDialog != null) {
                this.mProgressDialog.dismiss();
            }
            if (this.mInviteGroupDialog != null) {
                this.mInviteGroupDialog.dismiss();
            }
        }
    }

    private class GetMeChatFriendsApiCallback extends DefaultAPICallback<GetMeChatFriends> {
        public GetMeChatFriendsApiCallback(Context context) {
            super(context);
        }

        public void onResponse(GetMeChatFriends getMeChatFriends) {
            super.onResponse(getMeChatFriends);
            List<UserContactValue> contacts = new ArrayList();
            for (UserValue user : getMeChatFriends.users) {
                contacts.add(new UserContactValue(user.getUid(), user.getName(), user.getDescription(), user.getIcon(), 0, 0, user.getContactedDate()));
            }
            if (contacts.size() <= 0) {
                updateContacts(null);
                return;
            }
            UserValue userValue = AccountDatastore.getCurrentUser();
            TransactionDatastore.setUserContacts(contacts, userValue.getUid());
            AccountDatastore.setKKValue(UpdateAt.KEY1, UpdateAt.GET_ME_CONTACTS, Long.valueOf(System.currentTimeMillis()));
            Order order = Order.NAME;
            order.setCaseInsensitive(true);
            order.setDesc(false);
            updateContacts(ListRow.appendIndexMapCheckable(TransactionDatastore.getUserContacts(userValue.getUid(), order), ContactListActivity.mIndexTarget));
        }

        public void onError(int statusCode, String responseBody) {
            super.onError(statusCode, responseBody);
            updateContacts(null);
        }

        public void onError(Throwable e) {
            super.onError(e);
            updateContacts(null);
        }

        private void updateContacts(final List<MutablePair<Pair<String, UserContactValue>, Boolean>> itemsWithIndex) {
            ContactListActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    ContactListActivity.this.onContactsLoaded(itemsWithIndex);
                }
            });
        }
    }

    public static void startContactsListFromChatGroupInfo(String userUid, String groupUid) {
        Bundle bundle = new Bundle();
        bundle.putString(PathRouter.PATH, PATH_CONTACTS);
        bundle.putString(EXTRA_GROUP_UID, groupUid);
        bundle.putString("EXTRA_USER_UID", userUid);
        PathRouter.startPath(bundle);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lobi_group_contact_list_activity);
        Log.v("lobi-sdk", "[contacts] onCreate");
        Bundle extras = getIntent().getExtras();
        DebugAssert.assertNotNull(extras);
        this.mGroupUid = extras.getString(EXTRA_GROUP_UID);
        initView();
    }

    private void initView() {
        UserValue userValue = AccountDatastore.getCurrentUser();
        this.mNotFoundView = findViewById(R.id.lobi_contacts_list_empty_view_layout);
        this.mFilterNotHitView = findViewById(R.id.lobi_contacts_list_not_hit_view_layout);
        setupListView(userValue);
        setupSearchBox(userValue);
        initActionBar(null, null, this.mGroupUid);
    }

    protected void onResume() {
        super.onResume();
        onResumeImpl();
    }

    private synchronized void onResumeImpl() {
        getUserContacts(AccountDatastore.getCurrentUser().getUid());
    }

    private void initActionBar(UserValue currentUser, String userUid, String groupUid) {
        ActionBar actionBar = (ActionBar) findViewById(R.id.lobi_action_bar);
        DebugAssert.assertNotNull(actionBar);
        ((BackableContent) actionBar.getContent()).setOnBackButtonClickListener(new OnClickListener() {
            public void onClick(View v) {
                ContactListActivity.this.finish();
            }
        });
    }

    private void setupListView(UserValue user) {
        ListView listView = (ListView) findViewById(R.id.lobi_group_contacts_list);
        listView.setOnItemClickListener(getOnItemClickListener(this.mGroupUid));
        listView.setRecyclerListener(this.mAdapter);
        this.mAdapter = new ContactListAdapter(this, false);
        this.mHeader = LayoutInflater.from(this).inflate(R.layout.lobi_contact_list_header, null);
        listView.addHeaderView(this.mHeader, null, false);
        listView.setHeaderDividersEnabled(false);
        listView.setAdapter(this.mAdapter);
    }

    private void setupSearchBox(final UserValue user) {
        final Order order = Order.NAME;
        order.setCaseInsensitive(true);
        order.setDesc(false);
        ((SearchBox) findViewById(R.id.lobi_search_box)).getEditText().addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<UserContactValue> items;
                boolean isNoHit;
                int i = 0;
                String filter = s.toString();
                if (TextUtils.isEmpty(s)) {
                    items = TransactionDatastore.getUserContacts(user.getUid(), order);
                } else {
                    items = TransactionDatastore.getUserContacts(user.getUid(), order, filter);
                }
                ContactListActivity.this.mAdapter.setItems(ListRow.appendIndexMapCheckable(items, ContactListActivity.mIndexTarget));
                ContactListActivity.this.mAdapter.notifyDataSetChanged();
                if (ContactListActivity.this.mAdapter.getCount() <= 0) {
                    isNoHit = true;
                } else {
                    isNoHit = false;
                }
                View access$200 = ContactListActivity.this.mFilterNotHitView;
                if (!isNoHit) {
                    i = 8;
                }
                access$200.setVisibility(i);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void getUserContacts(String uid) {
        if (System.currentTimeMillis() < 600000 + ((Long) AccountDatastore.getKKValue(UpdateAt.KEY1, UpdateAt.GET_ME_CONTACTS, Long.valueOf(-1))).longValue()) {
            Order order = Order.NAME;
            order.setCaseInsensitive(true);
            order.setDesc(false);
            this.mUserContacts = TransactionDatastore.getUserContacts(uid, order);
            if (this.mUserContacts != null && this.mUserContacts.size() > 0) {
                setAdapterItems(converToAdapterData(this.mUserContacts));
            }
        }
        loadContactFromServer();
    }

    private synchronized void setAdapterItems(List<MutablePair<Pair<String, UserContactValue>, Boolean>> data) {
        this.mAdapter.setItems(data);
        this.mAdapter.notifyDataSetChanged();
        ((SectionView) this.mHeader.findViewById(R.id.lobi_contact_list_header_edit)).setTitleText(getString(R.string.lobi_contacts__int_, new Object[]{Integer.valueOf(data.size())}));
    }

    private List<MutablePair<Pair<String, UserContactValue>, Boolean>> converToAdapterData(List<UserContactValue> contacts) {
        return ListRow.appendIndexMapCheckable(contacts, mIndexTarget);
    }

    private void onContactsLoaded(List<MutablePair<Pair<String, UserContactValue>, Boolean>> itemsWithIndex) {
        boolean isHavingItem;
        int i;
        int i2 = 0;
        if (itemsWithIndex == null || itemsWithIndex.size() <= 0) {
            isHavingItem = false;
        } else {
            isHavingItem = true;
        }
        ListView listView = (ListView) findViewById(R.id.lobi_group_contacts_list);
        listView.setEmptyView(null);
        listView.setVisibility(0);
        View findViewById = findViewById(R.id.lobi_search_box);
        if (isHavingItem) {
            i = 0;
        } else {
            i = 8;
        }
        findViewById.setVisibility(i);
        findViewById = this.mNotFoundView;
        if (isHavingItem) {
            i = 8;
        } else {
            i = 0;
        }
        findViewById.setVisibility(i);
        View findViewById2 = this.mHeader.findViewById(R.id.lobi_contact_list_header_edit);
        if (!isHavingItem) {
            i2 = 8;
        }
        findViewById2.setVisibility(i2);
        if (itemsWithIndex != null) {
            setAdapterItems(itemsWithIndex);
        }
    }

    private void loadContactFromServer() {
        String userExternalIds = (String) AccountDatastore.getValue(Key.CUSTOM_FRIEND_LIST_EXIDS);
        if (TextUtils.isEmpty(userExternalIds)) {
            onContactsLoaded(null);
            return;
        }
        GetMeChatFriendsApiCallback callback = new GetMeChatFriendsApiCallback(this);
        if (this.mAdapter.getCount() == 0) {
            CustomProgressDialog progress = new CustomProgressDialog(this);
            progress.setMessage(getString(R.string.lobi_loading_loading));
            progress.show();
            callback.setProgress(progress);
        }
        Map<String, String> params = new HashMap();
        params.put("token", AccountDatastore.getCurrentUser().getToken());
        params.put(RequestKey.TARGET_USER_EX_IDS, userExternalIds);
        CoreAPI.getMeChatFriends(params, callback);
    }

    private OnItemClickListener getOnItemClickListener(String groupUid) {
        AddGroupListener listener = new AddGroupListener();
        listener.setActivity(this);
        UserValue userValue = AccountDatastore.getCurrentUser();
        listener.setUser(userValue);
        listener.setGroupDetail(TransactionDatastore.getGroupDetail(groupUid, userValue.getUid()));
        return listener;
    }
}
