package com.kayac.lobi.sdk.chat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.kayac.lobi.libnakamap.PathRoutedActivity;
import com.kayac.lobi.libnakamap.components.ActionBar;
import com.kayac.lobi.libnakamap.components.ActionBar.BackableContent;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastore;
import com.kayac.lobi.libnakamap.functional.Functional;
import com.kayac.lobi.libnakamap.functional.Functional.Predicater;
import com.kayac.lobi.libnakamap.net.APIRes.GetGroup;
import com.kayac.lobi.libnakamap.net.CoreAPI.DefaultAPICallback;
import com.kayac.lobi.libnakamap.utils.DebugAssert;
import com.kayac.lobi.libnakamap.utils.Log;
import com.kayac.lobi.libnakamap.value.GroupDetailValue;
import com.kayac.lobi.libnakamap.value.GroupStreamValue;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.sdk.R;
import com.kayac.lobi.sdk.service.chat.GroupEventListener;
import com.kayac.lobi.sdk.service.chat.GroupEventManager;
import java.util.List;

public class ChatMemberActivity extends PathRoutedActivity {
    private static final String CURSOR_LAST_PAGE = "0";
    public static final String PATH_CHAT_INFO_MEMBERS = "/grouplist/chat/info/members";
    protected static final String TAG = "[member]";
    private ChatMemberListAdapter mAdapter;
    private int mFooterHeight = 0;
    private final GroupEventListener mGroupEventListener = new GroupEventListener() {
        public void onMessage(final GroupStreamValue message) {
            ChatMemberActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    String event = message.getEvent();
                    if (GroupStreamValue.LOCATION.equals(event)) {
                        ChatMemberActivity.this.mAdapter.updateSharedLocation(message.getUser());
                    } else if ("part".equals(event) && ChatMemberActivity.this.mAdapter.removeUser(message.getUser())) {
                        ChatMemberActivity.access$006(ChatMemberActivity.this);
                    }
                }
            });
        }
    };
    private ListView mListView;
    private View mLoadingFooterView;
    private int mMembersCount = 0;
    private String mMembersNextCursor;
    private final OnScrollListener mOnScrollListener = new OnScrollListener() {
        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }

        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (firstVisibleItem + visibleItemCount != totalItemCount) {
                return;
            }
            if (!"0".equals(ChatMemberActivity.this.mMembersNextCursor) || ChatMemberActivity.this.mMembersCount == 0) {
                LayoutParams params = (LayoutParams) ChatMemberActivity.this.mLoadingFooterView.getLayoutParams();
                if (params.height == 1) {
                    params.height = ChatMemberActivity.this.mFooterHeight;
                    ChatMemberActivity.this.mLoadingFooterView.setLayoutParams(params);
                } else {
                    ChatMemberActivity.this.mFooterHeight = params.height;
                    Log.v(ChatMemberActivity.TAG, "footer parent: " + ChatMemberActivity.this.mLoadingFooterView.getParent());
                }
                ChatMemberActivity.this.mLoadingFooterView.setVisibility(0);
                ChatMemberActivity.this.mPagerLoader.loadNextPage();
            }
        }
    };
    private final ChatMemberPagerLoader mPagerLoader = new ChatMemberPagerLoader(new DefaultAPICallback<GetGroup>(this) {
        public void onResponse(final GetGroup t) {
            runOnUiThread(new Runnable() {
                public void run() {
                    ChatMemberActivity.this.mMembersCount = t.group.getMembersCount();
                    ChatMemberActivity.this.mMembersNextCursor = t.group.getMembersNextCursor();
                    ChatMemberActivity.this.mLoadingFooterView.setVisibility(8);
                    LayoutParams params = (LayoutParams) ChatMemberActivity.this.mLoadingFooterView.getLayoutParams();
                    params.height = 1;
                    ChatMemberActivity.this.mLoadingFooterView.setLayoutParams(params);
                    ChatMemberActivity.this.mAdapter.setIsPublicGroup(t.group.isPublic());
                    List<UserValue> members = t.group.getMembers();
                    Log.v("lobi-sdk", "[member] count: " + ChatMemberActivity.this.mMembersCount);
                    Log.v("lobi-sdk", "[member] size: " + members.size());
                    final UserValue leader = t.group.getOwner();
                    members = Functional.filter(members, new Predicater<UserValue>() {
                        public boolean predicate(UserValue t) {
                            return !leader.getUid().equals(t.getUid());
                        }
                    });
                    if (ChatMemberActivity.this.mAdapter.getCount() == 0) {
                        ChatMemberActivity.this.mAdapter.setLeader(leader);
                    }
                    ChatMemberActivity.this.mAdapter.addAll(members);
                }
            });
        }
    });

    static /* synthetic */ int access$006(ChatMemberActivity x0) {
        int i = x0.mMembersCount - 1;
        x0.mMembersCount = i;
        return i;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lobi_chat_members);
        Bundle extras = getIntent().getExtras();
        DebugAssert.assertNotNull(extras);
        final String gid = extras.getString("gid");
        GroupDetailValue groupDetail = TransactionDatastore.getGroupDetail(gid, AccountDatastore.getCurrentUser().getUid());
        ActionBar actionBar = (ActionBar) findViewById(R.id.lobi_action_bar);
        DebugAssert.assertNotNull(actionBar);
        BackableContent content = (BackableContent) actionBar.getContent();
        content.setText(groupDetail.getName());
        content.setOnBackButtonClickListener(new OnClickListener() {
            public void onClick(View v) {
                ChatMemberActivity.this.finish();
            }
        });
        this.mLoadingFooterView = LayoutInflater.from(this).inflate(R.layout.lobi_loading_footer, null);
        this.mLoadingFooterView.setVisibility(8);
        this.mLoadingFooterView.setBackgroundResource(R.drawable.lobi_bg_light_repeat);
        this.mAdapter = new ChatMemberListAdapter(this);
        this.mListView = (ListView) findViewById(R.id.lobi_chat_member_list);
        this.mListView.addFooterView(this.mLoadingFooterView);
        this.mListView.setAdapter(this.mAdapter);
        this.mListView.setOnScrollListener(this.mOnScrollListener);
        this.mListView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
                UserValue user = ChatMemberActivity.this.mAdapter.getItem(position);
                if (user != null) {
                    ChatSDKModuleBridge.startChatProfileFromGroup(user, gid);
                }
            }
        });
        this.mPagerLoader.setGid(gid);
        Log.d(TAG, "onCrate: load members");
        loadMembers();
        startContactStreaming();
    }

    private void startContactStreaming() {
        Intent intent = getIntent();
        String streamHost = intent.getStringExtra(ChatActivity.EXTRAS_STREAM_HOST);
        String groupUid = intent.getStringExtra("gid");
        GroupEventManager manager = GroupEventManager.getManager(getApplicationContext());
        manager.addEventListener(this.mGroupEventListener);
        manager.startPolling(streamHost, groupUid);
    }

    private void loadMembers() {
        this.mPagerLoader.loadNextPage();
    }

    protected void onDestroy() {
        GroupEventManager.getManager(getApplicationContext()).removeEventListener(this.mGroupEventListener);
        super.onDestroy();
    }

    public void finish() {
        super.finish();
    }
}
