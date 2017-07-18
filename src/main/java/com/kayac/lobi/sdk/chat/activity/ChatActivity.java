package com.kayac.lobi.sdk.chat.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.Toast;
import com.kayac.lobi.libnakamap.PathRoutedActivity;
import com.kayac.lobi.libnakamap.components.ActionBar;
import com.kayac.lobi.libnakamap.components.ActionBar.BackableContent;
import com.kayac.lobi.libnakamap.components.ChatMegamenuFragment;
import com.kayac.lobi.libnakamap.components.ChatNewPopup;
import com.kayac.lobi.libnakamap.components.CustomDialog;
import com.kayac.lobi.libnakamap.components.CustomProgressDialog;
import com.kayac.lobi.libnakamap.components.DialogManager;
import com.kayac.lobi.libnakamap.components.ImageLoaderView;
import com.kayac.lobi.libnakamap.components.InputAreaMaskView;
import com.kayac.lobi.libnakamap.components.ListOverScroller;
import com.kayac.lobi.libnakamap.components.ListOverScroller.OnSpringBackListener;
import com.kayac.lobi.libnakamap.components.MegamenuFragment;
import com.kayac.lobi.libnakamap.components.PathRouter;
import com.kayac.lobi.libnakamap.components.PullDownOverScrollComponent;
import com.kayac.lobi.libnakamap.components.SdkChatMegamenuFragment;
import com.kayac.lobi.libnakamap.components.SelectPictureMenuDialog;
import com.kayac.lobi.libnakamap.datastore.AccountDDL.KKey.UpdateAt;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.datastore.DatastoreAsyncCallback;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.KKey;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.KKey.Chat;
import com.kayac.lobi.libnakamap.datastore.TransactionDDL.KKey.Hint;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastore;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastoreAsync;
import com.kayac.lobi.libnakamap.net.APIDef.PostGroupJoinWithGroupUidV2.RequestKey;
import com.kayac.lobi.libnakamap.net.APIRes.GetGroup;
import com.kayac.lobi.libnakamap.net.APIRes.GetGroupV2;
import com.kayac.lobi.libnakamap.net.APIRes.GetStamps;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupJoinWithExId;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupJoinWithGroupUid;
import com.kayac.lobi.libnakamap.net.APISync;
import com.kayac.lobi.libnakamap.net.APISync.APISyncException;
import com.kayac.lobi.libnakamap.net.CoreAPI;
import com.kayac.lobi.libnakamap.net.CoreAPI.DefaultAPICallback;
import com.kayac.lobi.libnakamap.utils.ChatAdapterStandardBindView;
import com.kayac.lobi.libnakamap.utils.ChatListUtil;
import com.kayac.lobi.libnakamap.utils.ChatListUtil.ChatListItemData;
import com.kayac.lobi.libnakamap.utils.ChatListUtil.IViewModifier;
import com.kayac.lobi.libnakamap.utils.ChatReadMarkUtils;
import com.kayac.lobi.libnakamap.utils.DebugAssert;
import com.kayac.lobi.libnakamap.utils.DefaultGroupNotFoundHandler;
import com.kayac.lobi.libnakamap.utils.DeviceUUID;
import com.kayac.lobi.libnakamap.utils.DeviceUUID.OnGetUUID;
import com.kayac.lobi.libnakamap.utils.GroupValueUtils;
import com.kayac.lobi.libnakamap.utils.ImageIntentManager;
import com.kayac.lobi.libnakamap.utils.IntentUtils;
import com.kayac.lobi.libnakamap.utils.Log;
import com.kayac.lobi.libnakamap.utils.NakamapBroadcastManager;
import com.kayac.lobi.libnakamap.utils.NotificationUtil.NotificationSDKType;
import com.kayac.lobi.libnakamap.utils.PictureUtil.Res;
import com.kayac.lobi.libnakamap.utils.TimeUtil;
import com.kayac.lobi.libnakamap.utils.TutorialUtil;
import com.kayac.lobi.libnakamap.utils.ViewUtils;
import com.kayac.lobi.libnakamap.utils.WeakRefUtil;
import com.kayac.lobi.libnakamap.value.ChatValue;
import com.kayac.lobi.libnakamap.value.GroupButtonHooksValue;
import com.kayac.lobi.libnakamap.value.GroupDetailValue;
import com.kayac.lobi.libnakamap.value.GroupPermissionValue;
import com.kayac.lobi.libnakamap.value.GroupStreamValue;
import com.kayac.lobi.libnakamap.value.GroupValue;
import com.kayac.lobi.libnakamap.value.GroupValue.Builder;
import com.kayac.lobi.libnakamap.value.GroupValue.JoinCondition;
import com.kayac.lobi.libnakamap.value.StampValue;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.sdk.LobiCore;
import com.kayac.lobi.sdk.R;
import com.kayac.lobi.sdk.activity.stamp.StampActivity;
import com.kayac.lobi.sdk.service.chat.GroupEventListener;
import com.kayac.lobi.sdk.service.chat.GroupEventManager;
import com.kayac.lobi.sdk.utils.SDKBridge;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ChatActivity extends PathRoutedActivity {
    public static final String EXTRAS_CATEGORY_ID = "EXTRA_CATEGORY_ID";
    public static final String EXTRAS_GID = "gid";
    public static final String EXTRAS_GROUP_BASENAME = "group_basename";
    public static final String EXTRAS_GROUP_EXID = "group_exid";
    public static final String EXTRAS_MESSAGE = "EXTRA_MESSAGE";
    public static final String EXTRAS_STREAM_HOST = "streamHost";
    public static final String EXTRAS_TARGET_JOIN_GROUP_EXID = "target_join_group_exid";
    public static final String EXTRAS_TARGET_JOIN_GROUP_NAME = "target_join_group_name";
    public static final String EXTRAS_TARGET_PEEK_GROUP_EXID = "target_peek_group_exid";
    private static final float ONE_LINE_CHAT_HEIGHT = 108.0f;
    public static final String PATH_CHAT = "/grouplist/chat";
    private static final String PROGRESS_DIALOG = "progressDialog";
    protected static final String TAG = "[chat]";
    private static final DialogManager sDialogManager = new DialogManager();
    private ActionBar mActionBar;
    private WeakReference<ChatListAdapter> mAdapterRef;
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (!ChatActivity.this.mIsPaused) {
                ChatActivity.this.mImageIntentManager.onReceive(context, intent);
            }
        }
    };
    private ChatNewPopup mChatNewPopup;
    private String mFromCategoryId;
    private String mGid;
    private final GroupEventListener mGroupEventListener = new GroupEventListener() {
        public void onMessage(GroupStreamValue message) {
            int savedListTop = 0;
            ListView listView = (ListView) WeakRefUtil.get(ChatActivity.this.mListViewRef);
            if (listView != null) {
                ChatListAdapter adapter = (ChatListAdapter) WeakRefUtil.get(ChatActivity.this.mAdapterRef);
                if (adapter != null) {
                    String event = message.getEvent();
                    int index = listView.getFirstVisiblePosition();
                    boolean isTop = false;
                    View firstVisibleView = listView.getChildAt(0);
                    if (firstVisibleView != null) {
                        savedListTop = firstVisibleView.getTop();
                    }
                    if (adapter.getFirstVisibleItem() == 0) {
                        index++;
                        if (savedListTop == 0) {
                            isTop = true;
                        }
                    }
                    if ("chat".equals(event)) {
                        ChatValue chat = message.getChat();
                        String replyTo = chat.getReplyTo();
                        adapter.addChatOrReply(chat);
                        adapter.notifyDataSetChanged();
                        if (TextUtils.isEmpty(replyTo)) {
                            Log.d("chat", "add chat");
                            index++;
                        } else {
                            Log.d("chat", "add reply");
                        }
                        String type = chat.getType();
                        if (ChatValue.SYSTEM_NAME_UPDATE.equals(type) || ChatValue.SYSTEM_MEMO_UPDATE.equals(type) || ChatValue.SYSTEM_ICON_UPDATE.equals(type) || ChatValue.SYSTEM_WALLPAPER_UPDATE.equals(type) || ChatValue.SYSTEM_WALLPAPER_REMOVED.equals(type)) {
                            ChatActivity.this.updateGroupChat();
                        }
                        if (!(ChatActivity.this.mUserValue.getUid().equals(chat.getUser().getUid()) || !TextUtils.isEmpty(replyTo) || type.startsWith("system.") || isTop)) {
                            ChatActivity.this.showNewChatPopup(chat);
                        }
                    } else if (GroupStreamValue.CHAT_DELETED.equals(event)) {
                        int pos = adapter.removeChat(message.getId());
                        if (pos >= 0 && pos < index) {
                            index--;
                        }
                    } else {
                        return;
                    }
                    Log.i("chat", "ind to " + index);
                    listView.setSelectionFromTop(index, savedListTop);
                    if (isTop) {
                        ChatActivity.this.scrollToTop();
                    }
                }
            }
        }
    };
    private WeakReference<ChatListHeaderComponent> mHeaderComponentRef;
    private final ImageIntentManager mImageIntentManager = new ImageIntentManager(this);
    private volatile boolean mIsDestroyed = false;
    private boolean mIsDisabledToSetReadMark = false;
    private volatile boolean mIsPaused = false;
    private InputAreaMaskView mJoinFrame;
    private String mLastBackgroundImagePath = null;
    private WeakReference<ListView> mListViewRef;
    private WeakReference<View> mLoadingFooterViewRef;
    private ChatMegamenuFragment mMegamenuFragment;
    private ArrayList<ChatValue> mNewChatStack = new ArrayList();
    private ChatsLoader mNewerChatsLoader;
    private ChatsLoader mOlderChatsLoader;
    private final DefaultAPICallback<GetGroupV2> mOnGetGroupChat = new DefaultAPICallback<GetGroupV2>(this) {
        public void onResponse(GetGroupV2 t) {
            ChatActivity.this.onFetchedGroup(t.group);
        }

        public void onError(int statusCode, String responseBody) {
            super.onError(statusCode, responseBody);
            ChatActivity.this.onFetchGroupFailed(statusCode);
            ChatActivity.this.hideLoader();
        }

        public void onError(Throwable e) {
            super.onError(e);
            ChatActivity.this.hideLoader();
        }
    };
    private final DefaultAPICallback<GetGroupV2> mOnGetNewerChats = new DefaultAPICallback<GetGroupV2>(this) {
        public void onResponse(final GetGroupV2 t) {
            if (!ChatActivity.this.mIsDestroyed) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        ChatActivity.this.hideLoader();
                        ChatListAdapter adapter = (ChatListAdapter) ChatActivity.this.mAdapterRef.get();
                        if (adapter != null) {
                            adapter.addChats(t.group.getChats());
                        }
                    }
                });
            }
        }

        public void onError(int statusCode, String responseBody) {
            super.onError(statusCode, responseBody);
            ChatActivity.this.onFetchGroupFailed(statusCode);
            ChatActivity.this.hideLoader();
        }

        public void onError(Throwable e) {
            super.onError(e);
            ChatActivity.this.hideLoader();
        }
    };
    private final DefaultAPICallback<GetGroupV2> mOnGetOlderChats = new DefaultAPICallback<GetGroupV2>(this) {
        public void onResponse(final GetGroupV2 t) {
            if (!ChatActivity.this.mIsDestroyed) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        ChatActivity.this.hideLoader();
                        ChatListAdapter adapter = (ChatListAdapter) ChatActivity.this.mAdapterRef.get();
                        if (adapter != null) {
                            adapter.addChats(t.group.getChats());
                            if (!ChatActivity.this.mIsDisabledToSetReadMark) {
                                ChatActivity.this.setupIsWithReadMark(adapter, ChatActivity.this.mGid);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        }

        public void onError(int statusCode, String responseBody) {
            super.onError(statusCode, responseBody);
            ChatActivity.this.onFetchGroupFailed(statusCode);
            ChatActivity.this.hideLoader();
        }

        public void onError(Throwable e) {
            super.onError(e);
            ChatActivity.this.hideLoader();
        }
    };
    private final DefaultAPICallback<GetStamps> mOnGetStamps = new DefaultAPICallback<GetStamps>(this) {
        public void onResponse(final GetStamps r) {
            runOnUiThread(new Runnable() {
                public void run() {
                    ChatActivity.this.stampDiskStore(r.items);
                }
            });
        }
    };
    private final OnScrollListener mOnScrollListener = new OnScrollListener() {
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            ChatListAdapter adapter = (ChatListAdapter) ChatActivity.this.mAdapterRef.get();
            if (adapter != null) {
                adapter.onScrollStateChanged(view, scrollState);
            }
        }

        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            ChatListAdapter adapter = (ChatListAdapter) ChatActivity.this.mAdapterRef.get();
            View loadingFooterView = (View) ChatActivity.this.mLoadingFooterViewRef.get();
            if (adapter != null && loadingFooterView != null) {
                if (firstVisibleItem == 0) {
                    ChatActivity.this.hideNewChatPopup();
                }
                adapter.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                if (firstVisibleItem + visibleItemCount == totalItemCount) {
                    int count = adapter.getCount();
                    Log.v(ChatActivity.TAG, "is loading: " + ChatActivity.this.mOlderChatsLoader.isLoading());
                    if (count > 0 && !ChatActivity.this.mOlderChatsLoader.isLoading()) {
                        ChatListItemData data = adapter.getItem(count - 1);
                        if (ChatListUtil.checkValidChatType(data.getType())) {
                            if (ChatActivity.this.mOlderChatsLoader.loadChats(((ChatValue) data.getData()).getId())) {
                                loadingFooterView.setVisibility(0);
                            } else {
                                loadingFooterView.setVisibility(8);
                            }
                        }
                    }
                }
            }
        }
    };
    private final DefaultAPICallback<GetGroupV2> mOnUpdateGroupChat = new DefaultAPICallback<GetGroupV2>(this) {
        public void onResponse(GetGroupV2 t) {
            ChatActivity.this.onFetchedGroup(t.group, false);
        }

        public void onError(int statusCode, String responseBody) {
            super.onError(statusCode, responseBody);
            ChatActivity.this.hideLoader();
        }

        public void onError(Throwable e) {
            super.onError(e);
            ChatActivity.this.hideLoader();
        }
    };
    private WeakReference<PullDownOverScrollComponent> mPullDownOverScrollRef;
    private int mStampCount;
    private String mStreamHost;
    private UserValue mUserValue;

    public interface OnGroupJoinCallback {
        void onJoinResult(boolean z);
    }

    public interface OnJoinGroupAPICallback {
        void onError();

        void onSuccess(GroupValue groupValue);
    }

    interface OnAgreeJoiningGroupCallback {
        void agreeJoiningGroup(String str);
    }

    static abstract class ChatsLoader extends DefaultAPICallback<GetGroupV2> {
        final String mGid;
        private boolean mIsLoading = false;
        final Object mLock = new Object();
        final DefaultAPICallback<GetGroupV2> mOnGetChats;
        final String mToken;

        abstract boolean loadChats(String str);

        ChatsLoader(String token, String gid, DefaultAPICallback<GetGroupV2> callback) {
            super(null);
            this.mToken = token;
            this.mGid = gid;
            this.mOnGetChats = callback;
        }

        boolean isLoading() {
            boolean loading;
            synchronized (this.mLock) {
                loading = this.mIsLoading;
            }
            return loading;
        }

        public void onResponse(GetGroupV2 t) {
            Log.d(ChatActivity.TAG, "load chats: " + t.group.getChats().size());
            setIsLoading(false);
            this.mOnGetChats.onResponse(t);
        }

        public void onError(int statusCode, String responseBody) {
            Log.w(ChatActivity.TAG, "onError: " + statusCode + " " + responseBody);
            setIsLoading(false);
            this.mOnGetChats.onError(statusCode, responseBody);
        }

        public void onError(Throwable e) {
            e.printStackTrace();
            setIsLoading(false);
            this.mOnGetChats.onError(e);
        }

        protected void loadChatsAsync(final Map<String, String> params) {
            CoreAPI.getExecutorService().execute(new Runnable() {
                public void run() {
                    ChatsLoader.this.loadChatsSync(params);
                }
            });
        }

        private void loadChatsSync(Map<String, String> params) {
            Builder builder;
            List<ChatValue> resultChats;
            APISyncException e;
            GetGroupV2 prevResult = null;
            String uid = (String) params.get("uid");
            boolean isOlderThan = params.containsKey("older_than");
            List<ChatValue> resultChats2 = null;
            Builder builder2 = null;
            while (true) {
                try {
                    params.put("uid", uid);
                    if (prevResult != null) {
                        List<ChatValue> chats = prevResult.group.getChats();
                        if (isOlderThan) {
                            params.put("older_than", ((ChatValue) chats.get(chats.size() - 1)).getId());
                        } else {
                            params.put("newer_than", ((ChatValue) chats.get(0)).getId());
                        }
                    }
                    GetGroupV2 group = APISync.getGroupV2(params);
                    if (builder2 == null) {
                        builder = new Builder(group.group);
                        try {
                            resultChats = new ArrayList();
                        } catch (APISyncException e2) {
                            e = e2;
                            resultChats = resultChats2;
                        }
                    } else {
                        resultChats = resultChats2;
                        builder = builder2;
                    }
                    try {
                        for (ChatValue chat : group.group.getChats()) {
                            if (!ChatValue.USER_DELETED.equals(chat.getType())) {
                                resultChats.add(chat);
                            }
                        }
                        if (resultChats.size() >= 30 || group.group.getChats().size() < 30) {
                            builder.setChats(resultChats);
                            onResponse(new GetGroupV2(builder.build()));
                        } else {
                            prevResult = group;
                            resultChats2 = resultChats;
                            builder2 = builder;
                        }
                    } catch (APISyncException e3) {
                        e = e3;
                    }
                } catch (APISyncException e4) {
                    e = e4;
                    resultChats = resultChats2;
                    builder = builder2;
                }
            }
            builder.setChats(resultChats);
            onResponse(new GetGroupV2(builder.build()));
            return;
            int statusCode = e.getStatusCode();
            if (statusCode >= 500 || statusCode < 400) {
                onError(e);
            } else {
                onError(statusCode, e.getResponseBody());
            }
        }

        public void setIsLoading(boolean isLoading) {
            synchronized (this.mLock) {
                this.mIsLoading = isLoading;
            }
        }
    }

    static final class NewerChatsLoader extends ChatsLoader {
        NewerChatsLoader(String token, String gid, DefaultAPICallback<GetGroupV2> callback) {
            super(token, gid, callback);
        }

        boolean loadChats(String id) {
            setIsLoading(true);
            Map<String, String> params = new HashMap();
            params.put("token", this.mToken);
            params.put("uid", this.mGid);
            params.put("newer_than", id);
            params.put("members_count", "1");
            loadChatsAsync(params);
            return true;
        }
    }

    static final class OlderChatsLoader extends ChatsLoader {
        private String mPrevId;

        OlderChatsLoader(String token, String gid, DefaultAPICallback<GetGroupV2> callback) {
            super(token, gid, callback);
        }

        boolean loadChats(String id) {
            boolean z = true;
            synchronized (this.mLock) {
                if (TextUtils.equals(this.mPrevId, id)) {
                    z = false;
                } else {
                    this.mPrevId = id;
                    setIsLoading(true);
                    Map<String, String> params = new HashMap();
                    params.put("token", this.mToken);
                    params.put("uid", this.mGid);
                    params.put("older_than", id);
                    params.put("members_count", "1");
                    loadChatsAsync(params);
                }
            }
            return z;
        }
    }

    public void setupIsWithReadMark(ChatListAdapter adapter, String guid) {
        long lastChatAt = ChatReadMarkUtils.getChatAt(this.mGid);
        int i = 0;
        while (i < adapter.getCount()) {
            ChatListItemData data = adapter.getItem(i);
            if (!ChatListItemData.TYPE_CHAT.equals(data.getType()) || ((ChatValue) data.getData()).getCreatedDate() > lastChatAt) {
                i++;
            } else {
                data.setIsWithReadMark(true);
                adapter.notifyDataSetChanged();
                return;
            }
        }
    }

    public void showNewChatPopup(ChatValue chat) {
        if (this.mIsPaused || this.mNewChatStack.size() > 0) {
            Log.i("popup", "stack " + chat.getMessage());
            this.mNewChatStack.add(chat);
            return;
        }
        this.mChatNewPopup.show(chat);
    }

    public void hideNewChatPopup() {
        this.mNewChatStack.clear();
        this.mChatNewPopup.hide();
    }

    private void scrollToTop() {
        ListView listView = (ListView) WeakRefUtil.get(this.mListViewRef);
        if (listView != null) {
            listView.smoothScrollToPosition(0);
        }
    }

    void hideLoader() {
        runOnUiThread(new Runnable() {
            public void run() {
                View loadingFooterView = (View) ChatActivity.this.mLoadingFooterViewRef.get();
                if (loadingFooterView != null) {
                    loadingFooterView.setVisibility(8);
                }
            }
        });
    }

    void onFetchedGroup(GroupValue group) {
        onFetchedGroup(group, true);
    }

    void onFetchedGroup(final GroupValue group, final boolean initChat) {
        TransactionDatastore.setGroup(group, this.mUserValue.getUid());
        if (group.getJoinConditions() != null) {
            ChatListAdapter adapter = (ChatListAdapter) this.mAdapterRef.get();
            if (adapter != null) {
                adapter.setJoinConditions(group.getJoinConditions());
            }
        }
        renderTextOfJoinButton(group.getGroupButtonHooksValue());
        if (!this.mIsDestroyed) {
            if (!GroupEventManager.getManager(getApplicationContext()).isPolling()) {
                startGroupStreaming(group.getStreamHost());
            }
            hideLoader();
            runOnUiThread(new Runnable() {
                public void run() {
                    ChatListHeaderComponent headerComponent = (ChatListHeaderComponent) ChatActivity.this.mHeaderComponentRef.get();
                    if (headerComponent != null) {
                        ChatActivity.this.updateBackground(group.getWallpaper());
                        headerComponent.setGroup(group);
                        if (initChat) {
                            ChatActivity.this.onFirstReceiveChats(group.getChats());
                        }
                        ChatActivity.this.setActionBarInfo(group.getName());
                    }
                }
            });
        }
    }

    void onFetchGroupFailed(int statusCode) {
        DefaultGroupNotFoundHandler.handleError(this, statusCode, this.mUserValue.getUid(), this.mGid);
    }

    protected void onFirstReceiveChats(List<ChatValue> chats) {
        Log.v(TAG, "onFirstReceiveChats");
        if (!this.mIsDestroyed) {
            ChatListAdapter adapter = (ChatListAdapter) this.mAdapterRef.get();
            if (adapter != null) {
                adapter.clear();
                adapter.setChats(chats);
                if (!this.mIsDisabledToSetReadMark) {
                    int index = -1;
                    long lastChatAt = ChatReadMarkUtils.getChatAt(this.mGid);
                    int i = 0;
                    while (i < adapter.getCount()) {
                        if (ChatListItemData.TYPE_CHAT.equals(adapter.getItem(i).getType()) && ((ChatValue) adapter.getItem(i).getData()).getCreatedDate() <= lastChatAt) {
                            index = i;
                            break;
                        }
                        i++;
                    }
                    if (index != -1 && index < adapter.getCount()) {
                        if (index == 0) {
                            this.mIsDisabledToSetReadMark = true;
                            return;
                        }
                        adapter.getItem(index).setIsWithReadMark(true);
                        adapter.notifyDataSetChanged();
                        ListView listView = (ListView) this.mListViewRef.get();
                        if (listView != null) {
                            Log.i("readmark", "INDX " + index);
                            listView.setSelectionFromTop(index + listView.getHeaderViewsCount(), (int) (getResources().getDisplayMetrics().density * ONE_LINE_CHAT_HEIGHT));
                        }
                    }
                }
            }
        }
    }

    public String getGuid() {
        return this.mGid;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Uri data = getIntent().getData();
        if (data != null) {
            this.mIsDestroyed = true;
            finish();
            IntentUtils.startGroup(this, data);
            return;
        }
        getWindow().setFormat(1);
        setContentView(R.layout.lobi_chat_chat_activity);
        this.mUserValue = AccountDatastore.getCurrentUser();
        this.mChatNewPopup = (ChatNewPopup) findViewById(R.id.lobi_chat_new);
        if (getExtras()) {
            Log.i(TAG, "guid " + this.mGid);
            setupActionBar();
            this.mMegamenuFragment = (ChatMegamenuFragment) MegamenuFragment.addMegamenuFragment(SdkChatMegamenuFragment.class, this, this.mGid, this.mActionBar);
            DebugAssert.assertNotNull(this.mMegamenuFragment);
            displayUIWithGroupDetailValue(true);
            loadStamps();
            NakamapBroadcastManager manager = NakamapBroadcastManager.getInstance(getApplicationContext());
            IntentFilter filter = new IntentFilter();
            filter.addAction(SelectPictureMenuDialog.ACTION_SELECTED);
            manager.registerReceiver(this.mBroadcastReceiver, filter);
            return;
        }
        finish();
    }

    protected void onResume() {
        super.onResume();
        displayUIWithGroupDetailValue(false);
        GroupValue group = TransactionDatastore.getGroup(this.mGid, this.mUserValue.getUid());
        if (group != null) {
            renderTextOfJoinButton(group.getGroupButtonHooksValue());
        }
        if (this.mAdapterRef.get() != null) {
            ((ChatListAdapter) this.mAdapterRef.get()).refreshGroupDetailValue();
        }
        this.mActionBar.getNotifications(NotificationSDKType.ChatSDK, null);
        this.mIsPaused = false;
        Iterator it = this.mNewChatStack.iterator();
        while (it.hasNext()) {
            this.mChatNewPopup.show((ChatValue) it.next());
        }
        this.mNewChatStack.clear();
    }

    protected void onStart() {
        super.onStart();
        if (this.mMegamenuFragment != null && this.mMegamenuFragment.isVisible()) {
            this.mMegamenuFragment.hide(false);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4 || this.mMegamenuFragment == null || !this.mMegamenuFragment.isVisible()) {
            return super.onKeyDown(keyCode, event);
        }
        this.mMegamenuFragment.hide(true);
        return false;
    }

    private boolean getExtras() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        DebugAssert.assertNotNull(extras);
        String gid = extras.getString("gid");
        if (gid == null) {
            Log.e(TAG, "gid == null finish!");
            return false;
        }
        this.mGid = gid;
        this.mStreamHost = intent.getStringExtra(EXTRAS_STREAM_HOST);
        if (this.mStreamHost != null) {
            startGroupStreaming(this.mStreamHost);
        }
        GroupDetailValue groupDetail = (GroupDetailValue) extras.getParcelable(SDKBridge.GROUPDETAILVALUE);
        if (groupDetail != null) {
            TransactionDatastore.setGroupDetail(groupDetail, this.mUserValue.getUid());
        }
        this.mFromCategoryId = extras.getString(EXTRAS_CATEGORY_ID);
        return true;
    }

    private void setupActionBar() {
        this.mActionBar = (ActionBar) findViewById(R.id.lobi_action_bar);
        DebugAssert.assertNotNull(this.mActionBar);
        ((BackableContent) this.mActionBar.getContent()).setOnBackButtonClickListener(new OnClickListener() {
            public void onClick(View v) {
                ChatActivity.this.finish();
            }
        });
    }

    private void setActionBarNotice() {
        DebugAssert.assertNotNull(this.mActionBar);
        this.mActionBar.setupNotifications(NotificationSDKType.ChatSDK);
    }

    private void setActionBarInfo(String name) {
        ((BackableContent) this.mActionBar.getContent()).setText(name);
    }

    private void setupHeaderComponent(GroupDetailValue groupDetail) {
        if (this.mHeaderComponentRef != null) {
            ChatListHeaderComponent headerComponent = (ChatListHeaderComponent) this.mHeaderComponentRef.get();
            if (headerComponent != null) {
                String description;
                if (groupDetail.getDescription().length() <= 0) {
                    description = getString(R.string.lobi_key_grp_description);
                } else {
                    description = groupDetail.getDescription();
                }
                headerComponent.setDescrription(description);
            }
        }
    }

    private void renderTextOfJoinButton(GroupButtonHooksValue hooks) {
        ChatListAdapter adapter = (ChatListAdapter) this.mAdapterRef.get();
        if (adapter != null) {
            this.mJoinFrame.updateState(adapter.getJoinConditions(), hooks);
            runOnUiThread(new Runnable() {
                public void run() {
                    ChatActivity.this.mJoinFrame.render();
                }
            });
        }
    }

    private void showChatEditFrame(GroupDetailValue groupDetail) {
        View editFrame = findViewById(R.id.lobi_chat_edit_frame);
        if (this.mJoinFrame != null && editFrame != null) {
            if (shouldShowJoinButton(groupDetail)) {
                this.mJoinFrame.setVisibility(0);
                editFrame.setVisibility(8);
                return;
            }
            this.mJoinFrame.setVisibility(8);
            editFrame.setVisibility(0);
        }
    }

    private void displayUIWithGroupDetailValue(boolean render) {
        if (!this.mIsDestroyed && this.mGid != null && this.mUserValue != null) {
            GroupDetailValue groupDetail = TransactionDatastore.getGroupDetail(this.mGid, this.mUserValue.getUid());
            if (groupDetail == null) {
                finish();
                return;
            }
            setupHeaderComponent(groupDetail);
            showChatEditFrame(groupDetail);
            GroupValue group = TransactionDatastore.getGroup(this.mGid, this.mUserValue.getUid());
            if (group != null) {
                updateBackground(group.getWallpaper());
            }
            if (render) {
                renderView(groupDetail);
            }
            if (this.mStreamHost != null && this.mAdapterRef != null) {
                loadNewerChats(this.mStreamHost);
            }
        }
    }

    private void renderView(GroupDetailValue groupDetail) {
        IViewModifier viewModifier = new ChatAdapterStandardBindView(this);
        final String gid = groupDetail.getUid();
        final Intent intent = getIntent();
        this.mActionBar = (ActionBar) findViewById(R.id.lobi_action_bar);
        DebugAssert.assertNotNull(this.mActionBar);
        BackableContent content = (BackableContent) this.mActionBar.getContent();
        content.setText(groupDetail.getName());
        content.setOnBackButtonClickListener(new OnClickListener() {
            public void onClick(View v) {
                ChatActivity.this.finish();
            }
        });
        setActionBarNotice();
        if (this.mMegamenuFragment == null || this.mActionBar == null) {
            throw new RuntimeException("Unexpected error. mMegamenuFragment or mActionBar is null.");
        }
        this.mMegamenuFragment.setMegamenuToActionBar(this, this.mActionBar);
        findViewById(R.id.lobi_chat_edit_picture).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SDKBridge.startChatPickPhotoFromChatActivity(ChatActivity.this, gid, false);
            }
        });
        findViewById(R.id.lobi_chat_edit_start_textview).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ChatActivity.this.startChatEditActivity(gid);
            }
        });
        ChatListHeaderComponent headerComponent = new ChatListHeaderComponent(this);
        headerComponent.hide();
        headerComponent.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putString("gid", gid);
                extras.putString(PathRouter.PATH, ChatGroupInfoActivity.PATH_CHAT_INFO);
                PathRouter.startPath(extras);
            }
        });
        this.mHeaderComponentRef = new WeakReference(headerComponent);
        final PullDownOverScrollComponent pullDownOverScroll = new PullDownOverScrollComponent(this);
        pullDownOverScroll.getUpdateTextView().setText("");
        TransactionDatastoreAsync.getKKValue(KKey.LAST_CHAT_REFRESH_AT, this.mUserValue.getUid(), new DatastoreAsyncCallback<Object>() {
            public void onResponse(Object t) {
                if (t != null) {
                    pullDownOverScroll.getUpdateTextView().setText(ChatActivity.this.getString(R.string.lobi_last, new Object[]{t.toString()}));
                }
            }
        });
        this.mPullDownOverScrollRef = new WeakReference(pullDownOverScroll);
        ChatListAdapter adapter = new ChatListAdapter(this, groupDetail, viewModifier);
        this.mAdapterRef = new WeakReference(adapter);
        ListView listView = (ListView) findViewById(R.id.lobi_chat_list);
        this.mListViewRef = new WeakReference(listView);
        listView.addHeaderView(pullDownOverScroll);
        listView.addHeaderView(headerComponent);
        ListOverScroller listOverScroller = new ListOverScroller(listView, pullDownOverScroll);
        listOverScroller.bindHideView(headerComponent.getHideView(), headerComponent.getHideHeight());
        listOverScroller.setOnSpringBackListener(new OnSpringBackListener() {
            public void onSpringBack() {
                ChatActivity.this.onPullDownRefresh();
                ChatActivity.this.loadNewerChats(intent.getStringExtra(ChatActivity.EXTRAS_STREAM_HOST));
            }
        });
        String token = this.mUserValue.getToken();
        this.mNewerChatsLoader = new NewerChatsLoader(token, gid, this.mOnGetNewerChats);
        this.mJoinFrame = (InputAreaMaskView) findViewById(R.id.lobi_chat_public_join_frame);
        View loadingFooterView = LayoutInflater.from(this).inflate(R.layout.lobi_chat_loading_footer, null);
        this.mLoadingFooterViewRef = new WeakReference(loadingFooterView);
        listView.addFooterView(loadingFooterView);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(this.mOnScrollListener);
        listView.setRecyclerListener(adapter);
        ViewUtils.hideOverscrollEdge(listView);
        initGroupChat();
        this.mOlderChatsLoader = new OlderChatsLoader(token, gid, this.mOnGetOlderChats);
        Log.v("nakamap-sdk", "group type: " + groupDetail.getType());
        Log.v("nakamap-sdk", "can join? " + groupDetail.getPermission().join);
        this.mJoinFrame.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ChatListAdapter adapter = (ChatListAdapter) ChatActivity.this.mAdapterRef.get();
                if (adapter != null) {
                    ChatActivity.joinPublicGroup(v.getContext(), TransactionDatastore.getGroupDetail(ChatActivity.this.mGid, ChatActivity.this.mUserValue.getUid()), adapter.getJoinConditions(), false, null);
                }
            }
        });
    }

    private String getDisplayedMembersCount() {
        Resources res = getResources();
        DisplayMetrics metrics = res.getDisplayMetrics();
        return String.valueOf(ChatListHeaderComponent.calculateMembersCount(res, Math.max(metrics.widthPixels, metrics.heightPixels)));
    }

    private void initGroupChat() {
        getGroup(this.mOnGetGroupChat);
    }

    private void updateGroupChat() {
        getGroup(this.mOnUpdateGroupChat);
    }

    private void getGroup(final DefaultAPICallback<GetGroupV2> callback) {
        DeviceUUID.getUUID(SDKBridge.getContext(), new OnGetUUID() {
            public void onGetUUID(String uuid) {
                Map<String, String> params = new HashMap();
                params.put("token", ChatActivity.this.mUserValue.getToken());
                params.put("uid", ChatActivity.this.mGid);
                params.put("members_count", ChatActivity.this.getDisplayedMembersCount());
                params.put("install_id", uuid);
                CoreAPI.getGroupV2(params, callback);
            }
        });
    }

    public void startChatEditActivity(String gid) {
        GroupDetailValue groupDetail = TransactionDatastore.getGroupDetail(gid, this.mUserValue.getUid());
        Bundle extras = new Bundle();
        extras.putString(PathRouter.PATH, ChatEditActivity.PATH_CHAT_EDIT);
        extras.putParcelable("EXTRA_GROUP_DETAIL_VALUE", groupDetail);
        extras.putInt(ChatEditActivity.EXTRA_STAMP_COUNT, this.mStampCount);
        extras.putString("EXTRA_MESSAGE", getIntent().getStringExtra("EXTRA_MESSAGE"));
        PathRouter.startPath(extras);
    }

    private boolean shouldShowJoinButton(GroupDetailValue groupDetail) {
        GroupPermissionValue permission = groupDetail.getPermission();
        return GroupValue.NOT_JOINED.equals(groupDetail.getType()) && permission.peek && permission.join;
    }

    private void loadNewerChats(String streamhost) {
        ChatListAdapter adapter = (ChatListAdapter) this.mAdapterRef.get();
        if (adapter != null) {
            if (adapter.getCount() > 0) {
                ChatListItemData data = adapter.getItem(0);
                if (ChatListUtil.checkValidChatType(data.getType())) {
                    this.mNewerChatsLoader.loadChats(((ChatValue) data.getData()).getId());
                }
            }
            String streamHost = streamhost;
            if (streamHost != null) {
                GroupEventManager manager = GroupEventManager.getManager(getApplicationContext());
                manager.stopPolling();
                manager.startPolling(streamHost, this.mGid);
            }
        }
    }

    private void onPullDownRefresh() {
        String shortTime = TimeUtil.getShortTime(System.currentTimeMillis());
        String pullDownText = getString(R.string.lobi_last, new Object[]{shortTime});
        PullDownOverScrollComponent pullDownOverScrollComponent = (PullDownOverScrollComponent) this.mPullDownOverScrollRef.get();
        if (pullDownOverScrollComponent != null) {
            pullDownOverScrollComponent.getUpdateTextView().setText(pullDownText);
            TransactionDatastoreAsync.setKKValue(KKey.LAST_CHAT_REFRESH_AT, this.mUserValue.getUid() + ":" + this.mGid, shortTime, null);
        }
    }

    public static void joinPublicGroup(final Context context, final GroupDetailValue oldGroupDetailValue, List<JoinCondition> conditions, boolean joinCoditionsDialog, final OnGroupJoinCallback resultCallback) {
        if (!ChatListUtil.checkJoinConditions(context, joinCoditionsDialog, conditions, oldGroupDetailValue.getUid())) {
            showJoiningGroupConfirmation(context, new OnAgreeJoiningGroupCallback() {
                public void agreeJoiningGroup(String uuid) {
                    final UserValue currentUser = AccountDatastore.getCurrentUser();
                    ChatActivity.joinGroupNormal(context, new OnJoinGroupAPICallback() {
                        public void onSuccess(GroupValue group) {
                            ChatActivity.dismissLoadingDialog(context);
                            GroupValueUtils.setGroupValueAndGroupDetailValue(group, oldGroupDetailValue, currentUser.getUid(), true);
                            ((ChatActivity) context).startGroupStreaming(group.getStreamHost());
                            ((ChatActivity) context).runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(context, context.getString(R.string.lobi_joined_bang), 0).show();
                                    ((ChatActivity) context).displayUIWithGroupDetailValue(false);
                                    ChatListAdapter adapter = (ChatListAdapter) ((ChatActivity) context).mAdapterRef.get();
                                    if (adapter != null) {
                                        adapter.refreshGroupDetailValue();
                                    }
                                    if (AccountDatastore.getCurrentUser().isDefault()) {
                                        String key = Hint.JOINED_PUBLIC_GROUP;
                                        if (!TutorialUtil.getIsShownForNewUser(key)) {
                                            TutorialUtil.setIsShown(key);
                                        }
                                    }
                                    if (resultCallback != null) {
                                        resultCallback.onJoinResult(true);
                                    }
                                }
                            });
                        }

                        public void onError() {
                            if (resultCallback != null) {
                                resultCallback.onJoinResult(false);
                            }
                            ChatActivity.dismissLoadingDialog(context);
                        }
                    }, currentUser.getToken(), oldGroupDetailValue.getUid(), uuid);
                    CustomProgressDialog dialog = new CustomProgressDialog(context);
                    dialog.setMessage(context.getString(R.string.lobi_loading_loading));
                    ChatActivity.sDialogManager.show(ChatActivity.PROGRESS_DIALOG, dialog);
                }
            });
        }
    }

    private static void dismissLoadingDialog(Context context) {
        if (context instanceof ChatActivity) {
            ((ChatActivity) context).runOnUiThread(new Runnable() {
                public void run() {
                    ChatActivity.sDialogManager.dismiss(ChatActivity.PROGRESS_DIALOG);
                }
            });
        }
    }

    private static void joinGroupNormal(Context context, final OnJoinGroupAPICallback callback, String token, String uid, String uuid) {
        Map<String, String> params = new HashMap();
        params.put("token", token);
        params.put("uid", uid);
        params.put("install_id", uuid);
        if (context instanceof ChatActivity) {
            String fromCategoryId = ((ChatActivity) context).mFromCategoryId;
            if (fromCategoryId != null) {
                params.put(RequestKey.FROM_CATEGORY_ID, fromCategoryId);
            }
        }
        CoreAPI.postGroupJoinWithGroupUidV2(params, new DefaultAPICallback<PostGroupJoinWithGroupUid>(context) {
            public void onResponse(PostGroupJoinWithGroupUid t) {
                if (callback != null && t != null) {
                    callback.onSuccess(t.group);
                }
            }

            public void onError(int statusCode, String responseBody) {
                super.onError(statusCode, responseBody);
                if (callback != null) {
                    callback.onError();
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (callback != null) {
                    callback.onError();
                }
            }
        });
    }

    private static void showJoiningGroupConfirmation(final Context context, final OnAgreeJoiningGroupCallback cb) {
        final CustomDialog customDialog = CustomDialog.createTextDialog(context, context.getString(R.string.lobi_join_this));
        customDialog.setTitle(context.getString(R.string.lobi_join_to_see_reply));
        customDialog.setNegativeButton(context.getString(R.string.lobi_cancel), new OnClickListener() {
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });
        customDialog.setPositiveButton(context.getString(R.string.lobi_ok), new OnClickListener() {
            public void onClick(View v) {
                customDialog.dismiss();
                DeviceUUID.getUUID(context, new OnGetUUID() {
                    public void onGetUUID(String uuid) {
                        if (cb != null) {
                            cb.agreeJoiningGroup(uuid);
                        }
                    }
                });
            }
        });
        customDialog.show();
    }

    private void startGroupStreaming(String streamHost) {
        Log.v(TAG, "startGroupStreaming: " + streamHost);
        String groupUid = getIntent().getStringExtra("gid");
        GroupEventManager manager = GroupEventManager.getManager(getApplicationContext());
        manager.addEventListener(this.mGroupEventListener);
        manager.startPolling(streamHost, groupUid);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 20001 || requestCode == 20002) {
            Res res = this.mImageIntentManager.onActivityResult(requestCode, resultCode, data);
            if (resultCode == -1) {
                if (res == null || res.getRaw() == null || res.getThumb() == null) {
                    Toast.makeText(this, getString(R.string.lobi_sorry), 0).show();
                    return;
                }
                GroupDetailValue groupDetail = TransactionDatastore.getGroupDetail(this.mGid, AccountDatastore.getCurrentUser().getUid());
                Bundle extras = new Bundle();
                extras.putString(PathRouter.PATH, ChatEditActivity.PATH_CHAT_EDIT);
                extras.putParcelable("EXTRA_GROUP_DETAIL_VALUE", groupDetail);
                extras.putString(ChatEditActivity.EXTRA_IMAGE_PATH, this.mImageIntentManager.getFile().getAbsolutePath());
                PathRouter.startPath(extras);
            } else if (resultCode == 0) {
                Toast.makeText(this, getString(R.string.lobi_sorry), 0).show();
            } else {
                Toast.makeText(this, getString(R.string.lobi_sorry), 0).show();
            }
        }
    }

    protected void onPause() {
        super.onPause();
        this.mIsPaused = true;
    }

    protected void onDestroy() {
        destroy();
        GroupEventManager.getManager(getApplicationContext()).removeEventListener(this.mGroupEventListener);
        NakamapBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.mBroadcastReceiver);
        ChatReadMarkUtils.saveChatAt(this.mGid);
        this.mImageIntentManager.onDestroy();
        sDialogManager.onDestroy();
        super.onDestroy();
    }

    private void destroy() {
        if (!this.mIsDestroyed) {
            TransactionDatastore.setKKValue(KKey.LAST_CHAT_AT, AccountDatastore.getCurrentUser().getUid() + ":" + this.mGid, Long.valueOf(System.currentTimeMillis()));
            ListView listView = null;
            if (this.mListViewRef != null) {
                listView = (ListView) this.mListViewRef.get();
                this.mListViewRef.clear();
            }
            if (listView != null) {
                listView.setAdapter(null);
                listView.setOnScrollListener(null);
                listView.setRecyclerListener(null);
                ViewUtils.cleanAllDescendantViews((ViewGroup) listView.getRootView());
            }
            if (this.mAdapterRef != null) {
                ChatListAdapter adapter = (ChatListAdapter) this.mAdapterRef.get();
                if (adapter != null) {
                    String userUid = AccountDatastore.getCurrentUser().getUid();
                    if (adapter.getCount() > 0 && ChatListItemData.TYPE_CHAT.equals(adapter.getItem(0).getType())) {
                        TransactionDatastore.setKKValue(Chat.LATEST_CHAT_ID, userUid + ":" + this.mGid, adapter.getItem(0).getId());
                    }
                }
                this.mAdapterRef.clear();
            }
            if (this.mHeaderComponentRef != null) {
                this.mHeaderComponentRef.clear();
            }
            if (this.mPullDownOverScrollRef != null) {
                this.mPullDownOverScrollRef.clear();
            }
            MegamenuFragment.removeMegamenuFragment(this);
        }
    }

    public void finish() {
        destroy();
        super.finish();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.mHeaderComponentRef != null) {
            ChatListHeaderComponent headerComponent = (ChatListHeaderComponent) this.mHeaderComponentRef.get();
            if (headerComponent != null) {
                headerComponent.renderMembers();
            }
        }
    }

    private void updateBackground(String path) {
        ImageLoaderView background = (ImageLoaderView) findViewById(R.id.lobi_chat_background);
        if (background != null) {
            if (TextUtils.isEmpty(path)) {
                background.recycleImageSafely();
                background.setImageBitmap(null);
            } else if (!path.equals(this.mLastBackgroundImagePath)) {
                this.mLastBackgroundImagePath = path;
                background.recycleImageSafely();
                background.loadImage(path);
            }
        }
    }

    protected void loadStampsFromServer() {
        Map<String, String> params = new HashMap();
        params.put("token", this.mUserValue.getToken());
        CoreAPI.getStamps(params, this.mOnGetStamps);
    }

    protected void setStampButton(int size) {
        View view = findViewById(R.id.lobi_chat_edit_start_stamp);
        if (view != null) {
            if (size > 0) {
                view.setVisibility(0);
                view.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        StampActivity.startStamp(TransactionDatastore.getGroupDetail(ChatActivity.this.mGid, AccountDatastore.getCurrentUser().getUid()));
                    }
                });
                return;
            }
            view.setVisibility(8);
        }
    }

    protected void loadStamps() {
        TransactionDatastoreAsync.getStamps(new DatastoreAsyncCallback<List<StampValue>>() {
            public void onResponse(final List<StampValue> t) {
                if (t.size() > 0) {
                    ChatActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            ChatActivity.this.setStampButton(t.size());
                            ChatActivity.this.mStampCount = t.size();
                        }
                    });
                } else {
                    ChatActivity.this.loadStampsFromServer();
                }
            }
        });
    }

    protected void stampDiskStore(final List<StampValue> stamps) {
        this.mStampCount = stamps.size();
        setStampButton(stamps.size());
        TransactionDatastoreAsync.deleteAllStamp(new DatastoreAsyncCallback<Void>() {
            public void onResponse(Void t) {
                AccountDatastore.setKKValue(UpdateAt.KEY1, UpdateAt.GET_STAMPS, Long.valueOf(System.currentTimeMillis()));
                int len = stamps.size();
                for (int i = 0; i < len; i++) {
                    TransactionDatastore.setStamp((StampValue) stamps.get(i), i);
                }
            }
        });
    }

    public void onBackPressed() {
        if (!SDKBridge.isChatTutorialFragmentForceVisible(this)) {
            super.onBackPressed();
        }
    }

    public static void startCreateChatActivity(String groupExId, String groupBasename) {
        Log.v("nakamap-sdk", "groupExId: " + groupExId);
        Log.v("nakamap-sdk", "groupName: " + groupBasename);
        Map<String, String> params = new HashMap();
        final UserValue currentUser = AccountDatastore.getCurrentUser();
        params.put("token", currentUser.getToken());
        params.put("group_ex_id", groupExId);
        if (groupBasename != null) {
            params.put("name", groupBasename);
        }
        CoreAPI.postGroupJoinWithExId(params, new DefaultAPICallback<PostGroupJoinWithExId>(null) {
            public void onResponse(PostGroupJoinWithExId t) {
                Log.v("nakamap-sdk", "joined: " + t.group.getName());
                TransactionDatastore.setGroupDetail(GroupValueUtils.convertToGroupDetailValue(t.group), currentUser.getUid());
                Bundle extras = new Bundle();
                extras.putString(PathRouter.PATH, ChatActivity.PATH_CHAT);
                extras.putString(ChatActivity.EXTRAS_STREAM_HOST, t.group.getStreamHost());
                extras.putString("gid", t.group.getUid());
                PathRouter.startPath(extras);
            }

            public void onError(int statusCode, String responseBody) {
                super.onError(statusCode, responseBody);
            }
        });
    }

    public static void startPeekChatActivity(String groupExId) {
        Map<String, String> params = new HashMap();
        final UserValue currentUser = AccountDatastore.getCurrentUser();
        params.put("token", currentUser.getToken());
        params.put("group_ex_id", groupExId);
        CoreAPI.getGroupWithExIdV2(params, new DefaultAPICallback<GetGroup>(LobiCore.sharedInstance().getContext()) {
            public void onResponse(GetGroup t) {
                TransactionDatastore.setGroupDetail(GroupValueUtils.convertToGroupDetailValue(t.group), currentUser.getUid());
                Bundle extras = new Bundle();
                extras.putString(PathRouter.PATH, ChatActivity.PATH_CHAT);
                extras.putString(ChatActivity.EXTRAS_STREAM_HOST, t.group.getStreamHost());
                extras.putString("gid", t.group.getUid());
                PathRouter.startPath(extras);
            }

            public void onError(int statusCode, String responseBody) {
                super.onError(statusCode, responseBody);
            }
        });
    }
}
