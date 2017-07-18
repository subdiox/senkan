package com.kayac.lobi.sdk.chat.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.EditText;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.kayac.lobi.libnakamap.PathRoutedActivity;
import com.kayac.lobi.libnakamap.components.ActionBar;
import com.kayac.lobi.libnakamap.components.ActionBar.BackableContent;
import com.kayac.lobi.libnakamap.components.ImageLoaderView;
import com.kayac.lobi.libnakamap.components.ListOverScroller;
import com.kayac.lobi.libnakamap.components.ListOverScroller.OnSpringBackListener;
import com.kayac.lobi.libnakamap.components.PullDownOverScrollComponent;
import com.kayac.lobi.libnakamap.components.SelectPictureMenuDialog;
import com.kayac.lobi.libnakamap.components.UIEditText;
import com.kayac.lobi.libnakamap.components.UIEditText.OnTextChangedListener;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.datastore.DatastoreAsyncCallback;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.KKey;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastore;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastoreAsync;
import com.kayac.lobi.libnakamap.net.APIDef.GetGroupChatReplies.RequestKey;
import com.kayac.lobi.libnakamap.net.APIRes.GetGroupChatReplies;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupChat;
import com.kayac.lobi.libnakamap.net.CoreAPI;
import com.kayac.lobi.libnakamap.net.CoreAPI.DefaultAPICallback;
import com.kayac.lobi.libnakamap.utils.ChatAdapterReplyBindView;
import com.kayac.lobi.libnakamap.utils.ChatEditUtils;
import com.kayac.lobi.libnakamap.utils.ChatListUtil.ChatListItemData;
import com.kayac.lobi.libnakamap.utils.DebugAssert;
import com.kayac.lobi.libnakamap.utils.ImageIntentManager;
import com.kayac.lobi.libnakamap.utils.Log;
import com.kayac.lobi.libnakamap.utils.NakamapBroadcastManager;
import com.kayac.lobi.libnakamap.utils.PictureUtil.Res;
import com.kayac.lobi.libnakamap.utils.SelectionManager;
import com.kayac.lobi.libnakamap.utils.TimeUtil;
import com.kayac.lobi.libnakamap.utils.ViewUtils;
import com.kayac.lobi.libnakamap.value.ChatValue;
import com.kayac.lobi.libnakamap.value.ChatValue.Replies;
import com.kayac.lobi.libnakamap.value.ChatValue.Replies.Builder;
import com.kayac.lobi.libnakamap.value.GroupDetailValue;
import com.kayac.lobi.libnakamap.value.GroupStreamValue;
import com.kayac.lobi.libnakamap.value.GroupValue;
import com.kayac.lobi.libnakamap.value.StampValue;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.sdk.R;
import com.kayac.lobi.sdk.activity.stamp.StampActivity;
import com.kayac.lobi.sdk.service.chat.GroupEventListener;
import com.kayac.lobi.sdk.service.chat.GroupEventManager;
import com.kayac.lobi.sdk.utils.SDKBridge;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatReplyActivity extends PathRoutedActivity {
    public static final String EXTRA_CHAT_REPLY_CHAT_SHOW_KEYBOARD = "chat_reply_show_keyboard";
    public static final String EXTRA_CHAT_REPLY_CHAT_UID = "chat_reply_chat_uid";
    public static final String EXTRA_CHAT_REPLY_FIRST_VIEW_ID = "chat_reply_first_view_id";
    public static final String EXTRA_CHAT_REPLY_GROUPDETAIL = "chat_reply_groupdetail";
    public static final String EXTRA_CHAT_REPLY_GROUP_JOIN_CONDITIONS = "chat_reply_group_join_conditions";
    public static final String EXTRA_CHAT_REPLY_TO = "chat_reply_to";
    public static final String PATH_CHAT_REPLY = "/grouplist/chat/reply";
    public static final String PATH_LIKE_RANKING_REPLY = "/like_ranking/reply";
    private static final String TAG = "group.stream";
    private BackableContent mActionBarContent;
    private ChatListAdapter mAdapter;
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Bundle extras = intent.getExtras();
            if (SelectPictureMenuDialog.ACTION_SELECTED.equals(action) && extras.getInt(SelectPictureMenuDialog.EXTRA_TYPE, -1) == R.id.lobi_select_picture_menu_detach_photo) {
                ((ChatEditPictureButton) ChatReplyActivity.this.findViewById(R.id.lobi_chat_edit_picture)).showButton();
            }
            if (!ChatReplyActivity.this.mIsPaused) {
                ChatReplyActivity.this.mImageIntentManager.onReceive(context, intent);
            }
        }
    };
    private int mExtraPadding = 0;
    private boolean mFirstView = true;
    private String mFirstViewChatId;
    private GroupDetailValue mGroupDetail;
    private final GroupEventListener mGroupEventListener = new GroupEventListener() {
        public void onMessage(GroupStreamValue message) {
            String event = message.getEvent();
            Log.v(ChatReplyActivity.TAG, "STREAMING onMessage: " + event);
            if ("chat".equals(event)) {
                TransactionDatastoreAsync.setKKValue(KKey.LAST_CHAT_AT, AccountDatastore.getCurrentUser().getUid() + ":" + ChatReplyActivity.this.mGroupDetail.getUid(), Long.valueOf(System.currentTimeMillis()), null);
                ChatValue chat = message.getChat();
                Bundle extras = ChatReplyActivity.this.getIntent().getExtras();
                DebugAssert.assertNotNull(extras);
                if (extras.getString(ChatReplyActivity.EXTRA_CHAT_REPLY_TO).equals(chat.getReplyTo())) {
                    ChatReplyActivity.this.mAdapter.addChat(chat);
                    ChatReplyActivity.this.onFetchReplies(chat.getReplies().getCount());
                    ChatReplyActivity.this.mListView.setSelectionFromTop(ChatReplyActivity.this.mListView.getCount(), 0);
                }
                Log.v(ChatReplyActivity.TAG, "STREAMING REPLy");
            } else if (GroupStreamValue.CHAT_DELETED.equals(event)) {
                ChatReplyActivity.this.mAdapter.removeChat(message.getId());
            }
        }
    };
    private final ImageIntentManager mImageIntentManager = new ImageIntentManager(this);
    private boolean mIsPaused;
    private ListView mListView;
    private TextView mNoReplyMessage;
    private final DefaultAPICallback<GetGroupChatReplies> mOnGetReplies = new DefaultAPICallback<GetGroupChatReplies>(this) {
        public void onResponse(final GetGroupChatReplies t) {
            ChatReplyActivity.this.startGroupStreaming();
            runOnUiThread(new Runnable() {
                public void run() {
                    if (ChatValue.USER_DELETED.equals(t.to.getType())) {
                        Toast.makeText(ChatReplyActivity.this, R.string.lobi_the_original, 0).show();
                        ChatReplyActivity.this.finish();
                        return;
                    }
                    Builder repliesBuilder = new Builder(t.to.getReplies());
                    repliesBuilder.setCount(t.chats.size());
                    Replies replies = repliesBuilder.build();
                    ChatValue.Builder chatBuilder = new ChatValue.Builder(t.to);
                    chatBuilder.setReplies(replies);
                    ChatReplyActivity.this.mAdapter.addChat(chatBuilder.build());
                    ChatReplyActivity.this.mAdapter.addChats(t.chats);
                    ChatReplyActivity.this.onFetchReplies(t.chats.size());
                    if (ChatReplyActivity.this.mFirstView) {
                        int toPosition = ChatReplyActivity.this.mListView.getCount() - 1;
                        if (ChatReplyActivity.this.mFirstViewChatId != null) {
                            toPosition = ChatReplyActivity.this.getListPositionFromChatId(ChatReplyActivity.this.mFirstViewChatId);
                        }
                        ChatReplyActivity.this.mListView.setSelection(toPosition);
                        ChatReplyActivity.this.mFirstView = false;
                    }
                    ChatReplyActivity.this.setActionBarContent();
                }
            });
        }

        public void onError(int statusCode, String responseBody) {
            super.onError(statusCode, responseBody);
            ChatReplyActivity.this.finish();
        }

        public void onError(Throwable e) {
            super.onError(e);
            ChatReplyActivity.this.finish();
        }
    };
    private final DefaultAPICallback<PostGroupChat> mOnPostChat = new DefaultAPICallback<PostGroupChat>(this) {
        public void onResponse(PostGroupChat t) {
            runOnUiThread(new Runnable() {
                public void run() {
                    ChatReplyActivity.this.mPostChatButton.setEnabled(true);
                    EditText editText = (EditText) ChatReplyActivity.this.findViewById(R.id.lobi_chat_edit);
                    ((InputMethodManager) ChatReplyActivity.this.getSystemService("input_method")).hideSoftInputFromWindow(editText.getWindowToken(), 0);
                    editText.setText("");
                    ((ChatEditPictureButton) ChatReplyActivity.this.findViewById(R.id.lobi_chat_edit_picture)).showButton();
                    ChatEditUtils.clearSelection();
                }
            });
        }

        public void onError(Throwable e) {
            enablePostButton();
            super.onError(e);
        }

        public void onError(int statusCode, String responseBody) {
            enablePostButton();
            super.onError(statusCode, responseBody);
        }

        private void enablePostButton() {
            runOnUiThread(new Runnable() {
                public void run() {
                    ChatReplyActivity.this.mPostChatButton.setEnabled(true);
                }
            });
        }
    };
    private View mPostChatButton;
    private PullDownOverScrollComponent mPullDownOverScroll;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setup();
        Bundle extras = getIntent().getExtras();
        DebugAssert.assertNotNull(extras);
        String chatUid = extras.getString(EXTRA_CHAT_REPLY_TO);
        this.mGroupDetail = (GroupDetailValue) extras.getParcelable(EXTRA_CHAT_REPLY_GROUPDETAIL);
        this.mFirstViewChatId = extras.getString(EXTRA_CHAT_REPLY_FIRST_VIEW_ID);
        setActionBar();
        prepareUI(this);
        this.mListView = (ListView) findViewById(R.id.lobi_chat_reply_list);
        setListView(chatUid);
        setChatEditComponents(chatUid);
        loadStamps();
        startGroupStreaming();
    }

    protected void setStampButton(int size) {
        View view = findViewById(R.id.lobi_chat_edit_start_stamp);
        if (view != null) {
            if (size > 0) {
                view.setVisibility(0);
                final Bundle extras = getIntent().getExtras();
                view.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        StampActivity.startStamp(TransactionDatastore.getGroupDetail(ChatReplyActivity.this.mGroupDetail.getUid(), AccountDatastore.getCurrentUser().getUid()), extras.getString(ChatReplyActivity.EXTRA_CHAT_REPLY_TO));
                    }
                });
                return;
            }
            view.setVisibility(8);
        }
    }

    protected void onPause() {
        super.onPause();
        this.mIsPaused = true;
        View focused = getCurrentFocus();
        if (focused != null) {
            ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(focused.getWindowToken(), 0);
        }
    }

    protected void onResume() {
        super.onResume();
        Bundle extras = getIntent().getExtras();
        DebugAssert.assertNotNull(extras);
        loadRepliesFromServer(extras.getString(EXTRA_CHAT_REPLY_TO));
        this.mIsPaused = false;
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        ChatEditPictureButton pictButton = (ChatEditPictureButton) findViewById(R.id.lobi_chat_edit_picture);
        ChatEditUtils.setSelectedPicture(getApplicationContext(), pictButton);
        if (this.mImageIntentManager.getFile() == null) {
            pictButton.setImageUri(null);
        }
        disableSendButton();
    }

    protected void onDestroy() {
        SelectionManager.getManager().deleteSelection();
        NakamapBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.mBroadcastReceiver);
        this.mImageIntentManager.onDestroy();
        GroupEventManager.getManager(getApplicationContext()).removeEventListener(this.mGroupEventListener);
        super.onDestroy();
    }

    private int getListPositionFromChatId(String chatUid) {
        int position = 0;
        for (int i = 0; i < this.mAdapter.getCount(); i++) {
            if (this.mAdapter.getItem(i).getId().equals(chatUid)) {
                position = i;
                break;
            }
        }
        return this.mListView.getHeaderViewsCount() + position;
    }

    private void prepareUI(Activity activity) {
        TransactionDatastoreAsync.getGroup(this.mGroupDetail.getUid(), AccountDatastore.getCurrentUser().getUid(), new DatastoreAsyncCallback<GroupValue>() {
            public void onResponse(GroupValue t) {
                ChatReplyActivity.this.displayUIWithGroupValue(t);
            }
        });
    }

    private void displayUIWithGroupValue(final GroupValue t) {
        runOnUiThread(new Runnable() {
            public void run() {
                ChatReplyActivity.this.setupWallPaper(t);
            }
        });
    }

    private void setupWallPaper(GroupValue t) {
        if (t != null) {
            updateBackground(t.getWallpaper());
        }
    }

    protected void loadStamps() {
        TransactionDatastoreAsync.getStamps(new DatastoreAsyncCallback<List<StampValue>>() {
            public void onResponse(final List<StampValue> t) {
                ChatReplyActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        ChatReplyActivity.this.setStampButton(t.size());
                    }
                });
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 20001 || requestCode == 20002) {
            Res res = this.mImageIntentManager.onActivityResult(requestCode, resultCode, data);
            if (resultCode == -1) {
                if (res == null || res.getRaw() == null || res.getThumb() == null) {
                    Toast.makeText(this, getString(R.string.lobi_sorry), 0).show();
                } else {
                    ChatEditPictureButton pictButton = (ChatEditPictureButton) findViewById(R.id.lobi_chat_edit_picture);
                    pictButton.setImageUri(Uri.fromFile(res.getThumb()));
                    pictButton.showThumbnail();
                }
            } else if (resultCode == 0) {
                Toast.makeText(this, getString(R.string.lobi_sorry), 0).show();
            } else {
                Toast.makeText(this, getString(R.string.lobi_sorry), 0).show();
            }
        }
        disableSendButton();
    }

    private void setup() {
        getWindow().setFormat(1);
        setContentView(R.layout.lobi_chat_chat_reply_activity);
        NakamapBroadcastManager manager = NakamapBroadcastManager.getInstance(getApplicationContext());
        IntentFilter filter = new IntentFilter();
        filter.addAction(SelectPictureMenuDialog.ACTION_SELECTED);
        manager.registerReceiver(this.mBroadcastReceiver, filter);
    }

    protected boolean getExtras() {
        Bundle extras = getIntent().getExtras();
        DebugAssert.assertNotNull(extras);
        this.mGroupDetail = (GroupDetailValue) extras.getParcelable(EXTRA_CHAT_REPLY_GROUPDETAIL);
        if (extras.getString(EXTRA_CHAT_REPLY_TO) == null || this.mGroupDetail == null) {
            return false;
        }
        return true;
    }

    private void startGroupStreaming() {
        String streamHost = this.mGroupDetail.getStreamHost();
        String groupUid = this.mGroupDetail.getUid();
        Log.v("nakamap [group.stream]", streamHost + " : " + groupUid);
        GroupEventManager manager = GroupEventManager.getManager(getApplicationContext());
        manager.addEventListener(this.mGroupEventListener);
        manager.startPolling(streamHost, groupUid);
    }

    protected void setActionBar() {
        ActionBar actionBar = (ActionBar) findViewById(R.id.lobi_action_bar);
        DebugAssert.assertNotNull(actionBar);
        this.mActionBarContent = (BackableContent) actionBar.getContent();
        this.mActionBarContent.setText("");
    }

    protected void setActionBarContent() {
        ChatListItemData rootChat = this.mAdapter.getItem(0);
        if (rootChat != null) {
            this.mActionBarContent.setText(((ChatValue) rootChat.getData()).getUser().getName());
            this.mActionBarContent.setOnBackButtonClickListener(new OnClickListener() {
                public void onClick(View v) {
                    ChatReplyActivity.this.finish();
                }
            });
        }
    }

    protected void setListView(final String chatUid) {
        final Activity activity = this;
        this.mAdapter = new ChatListAdapter(this, this.mGroupDetail, new ChatAdapterReplyBindView(this));
        this.mAdapter.setJoinConditions(getIntent().getExtras().getParcelableArrayList(EXTRA_CHAT_REPLY_GROUP_JOIN_CONDITIONS));
        this.mAdapter.setOrder(-1);
        this.mPullDownOverScroll = new PullDownOverScrollComponent(this);
        TransactionDatastoreAsync.getKKValue(KKey.LAST_CHAT_REPLY_REFRESH_AT, AccountDatastore.getCurrentUser().getUid() + ":" + this.mGroupDetail.getUid() + ":" + chatUid, new DatastoreAsyncCallback<Object>() {
            public void onResponse(final Object t) {
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        if (t != null) {
                            ChatReplyActivity.this.mPullDownOverScroll.getUpdateTextView().setText(ChatReplyActivity.this.getString(R.string.lobi_last, new Object[]{t.toString()}));
                            return;
                        }
                        ChatReplyActivity.this.mPullDownOverScroll.getUpdateTextView().setText("");
                    }
                });
            }
        });
        this.mPullDownOverScroll.getUpdateTextView().setText("");
        final View fakeBackground = findViewById(R.id.lobi_chat_reply_fake_background);
        this.mNoReplyMessage = (TextView) findViewById(R.id.lobi_chat_reply_no_replies);
        new ListOverScroller(this.mListView, this.mPullDownOverScroll).setOnSpringBackListener(new OnSpringBackListener() {
            public void onSpringBack() {
                ChatReplyActivity.this.onPullDownRefresh(chatUid);
                GroupEventManager.getManager(ChatReplyActivity.this.getApplicationContext()).stopPolling();
                ChatReplyActivity.this.loadRepliesFromServer(chatUid);
            }
        });
        this.mListView.setOnScrollListener(new OnScrollListener() {
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int header = Integer.MAX_VALUE;
                int first = Integer.MAX_VALUE;
                int second = Integer.MAX_VALUE;
                int count = view.getChildCount();
                for (int i = 0; i < count; i++) {
                    int top = view.getChildAt(i).getTop();
                    if (header > top) {
                        second = first;
                        first = header;
                        header = top;
                    } else if (first > top) {
                        second = first;
                        first = top;
                    } else if (second > top) {
                        second = top;
                    }
                }
                if (second != Integer.MAX_VALUE) {
                    fakeBackground.setVisibility(0);
                    ChatReplyActivity.this.syncPosition(second);
                } else if (ChatReplyActivity.this.mAdapter.getCount() == 1) {
                    fakeBackground.setVisibility(0);
                    ChatReplyActivity.this.syncPosition(first);
                }
            }

            public void onScrollStateChanged(AbsListView arg0, int arg1) {
            }
        });
        this.mListView.addHeaderView(this.mPullDownOverScroll);
        this.mListView.setAdapter(this.mAdapter);
        this.mListView.smoothScrollToPosition(this.mAdapter.getCount());
        this.mListView.setRecyclerListener(this.mAdapter);
        ViewUtils.hideOverscrollEdge(this.mListView);
    }

    private void onFetchReplies(int replies) {
        if (replies == 0) {
            View listItem = this.mAdapter.getView(0, null, this.mListView);
            listItem.measure(0, 0);
            syncPosition(listItem.getMeasuredHeight());
            this.mNoReplyMessage.setVisibility(0);
            return;
        }
        this.mNoReplyMessage.setVisibility(8);
    }

    private void onPullDownRefresh(String chatUid) {
        String shortTime = TimeUtil.getShortTime(System.currentTimeMillis());
        this.mPullDownOverScroll.getUpdateTextView().setText(getString(R.string.lobi_last, new Object[]{shortTime}));
        TransactionDatastoreAsync.setKKValue(KKey.LAST_CHAT_REFRESH_AT, AccountDatastore.getCurrentUser().getUid() + ":" + this.mGroupDetail.getUid() + ":" + chatUid, shortTime, null);
    }

    private void updateBackground(String path) {
        ImageLoaderView background = (ImageLoaderView) findViewById(R.id.lobi_chat_reply_background);
        if (TextUtils.isEmpty(path)) {
            background.setBackgroundResource(R.drawable.lobi_bg_light_repeat);
        } else {
            background.loadImage(path);
        }
    }

    protected void loadRepliesFromServer(String chatUid) {
        Map<String, String> params = new HashMap();
        params.put("token", AccountDatastore.getCurrentUser().getToken());
        params.put("uid", this.mGroupDetail.getUid());
        params.put(RequestKey.TO, chatUid);
        CoreAPI.getGroupChatReplies(params, this.mOnGetReplies);
    }

    protected void syncPosition(int second) {
        View fakeBackground = findViewById(R.id.lobi_chat_reply_fake_background);
        LayoutParams params = (LayoutParams) fakeBackground.getLayoutParams();
        if (this.mAdapter.getCount() == 1) {
            if (this.mExtraPadding == 0) {
                View listItem = this.mAdapter.getView(0, null, this.mListView);
                listItem.measure(0, 0);
                this.mExtraPadding = listItem.getMeasuredHeight();
            }
            params.topMargin = this.mExtraPadding + second;
            fakeBackground.setLayoutParams(params);
            return;
        }
        params.topMargin = second;
        fakeBackground.setLayoutParams(params);
    }

    protected void setChatEditComponents(String chatUid) {
        View view = findViewById(R.id.lobi_chat_edit_start_stamp);
        Bundle extras = getIntent().getExtras();
        DebugAssert.assertNotNull(extras);
        if (extras.getBoolean(EXTRA_CHAT_REPLY_CHAT_SHOW_KEYBOARD)) {
            showKeyboard();
        }
        ChatEditPictureButton pictButton = (ChatEditPictureButton) findViewById(R.id.lobi_chat_edit_picture);
        ((ChatEditPictureButton) findViewById(R.id.lobi_chat_edit_picture)).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                SDKBridge.startSelectPhotoFromChatReplyActivity(ChatReplyActivity.this, ChatReplyActivity.this.mImageIntentManager.getFile() != null);
            }
        });
        ChatEditUtils.stampButton(view, this.mGroupDetail, chatUid);
        setSendButton(chatUid);
    }

    public void showKeyboard() {
        UIEditText editText = (UIEditText) findViewById(R.id.lobi_chat_edit);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        ((InputMethodManager) getSystemService("input_method")).showSoftInput(editText, 2);
    }

    protected void setSendButton(final String chatUid) {
        View view = findViewById(R.id.lobi_chat_edit_post);
        view.setOnClickListener(new OnClickListener() {
            public void onClick(View editPost) {
                if (ChatReplyActivity.this.mGroupDetail.isPublic() && !ChatReplyActivity.this.mGroupDetail.getType().equals(GroupValue.MINE) && !ChatReplyActivity.this.mGroupDetail.getType().equals("invited")) {
                    return;
                }
                if (ChatEditUtils.getCountSelectedPictures() > 1) {
                    EditText editText = (EditText) ChatReplyActivity.this.findViewById(R.id.lobi_chat_edit);
                    ChatEditUtils.postMultiples(ChatReplyActivity.this.getApplicationContext(), ChatReplyActivity.this.mGroupDetail, editText.getText().toString(), chatUid);
                    ((InputMethodManager) ChatReplyActivity.this.getSystemService("input_method")).hideSoftInputFromWindow(editText.getWindowToken(), 0);
                    editText.setText("");
                    ((ChatEditPictureButton) ChatReplyActivity.this.findViewById(R.id.lobi_chat_edit_picture)).showButton();
                    return;
                }
                ChatReplyActivity.this.postChat(ChatReplyActivity.this.mGroupDetail, AccountDatastore.getCurrentUser());
                editPost.setEnabled(false);
            }
        });
        this.mPostChatButton = view;
        this.mPostChatButton.setEnabled(false);
        ((UIEditText) findViewById(R.id.lobi_chat_edit)).setOnTextChangedListener(new OnTextChangedListener() {
            public void onTextChanged(UIEditText textInput, CharSequence text, int start, int before, int after) {
                ChatReplyActivity.this.disableSendButton();
            }
        });
    }

    protected void disableSendButton() {
        if (((UIEditText) findViewById(R.id.lobi_chat_edit)).getText().length() == 0 && ChatEditUtils.getCountSelectedPictures() == 0 && this.mImageIntentManager.getFile() == null) {
            this.mPostChatButton.setEnabled(false);
        } else {
            this.mPostChatButton.setEnabled(true);
        }
    }

    protected void postChat(GroupDetailValue groupDetail, UserValue user) {
        String message = ((EditText) findViewById(R.id.lobi_chat_edit)).getText().toString();
        String gid = groupDetail.getUid();
        Bundle extras = getIntent().getExtras();
        DebugAssert.assertNotNull(extras);
        ChatEditUtils.postChat(this.mOnPostChat, message, gid, extras.getString(EXTRA_CHAT_REPLY_TO), this.mImageIntentManager.getFile());
        this.mImageIntentManager.setFile(null);
    }

    protected void setupPopupMenu() {
    }
}
