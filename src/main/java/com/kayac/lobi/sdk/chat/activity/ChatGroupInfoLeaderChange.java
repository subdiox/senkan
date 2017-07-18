package com.kayac.lobi.sdk.chat.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AbsListView.RecyclerListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.kayac.lobi.libnakamap.PathRoutedActivity;
import com.kayac.lobi.libnakamap.components.ActionBar;
import com.kayac.lobi.libnakamap.components.ActionBar.BackableContent;
import com.kayac.lobi.libnakamap.components.ImageLoaderView;
import com.kayac.lobi.libnakamap.components.ListRow;
import com.kayac.lobi.libnakamap.components.ListRow.TwoLine;
import com.kayac.lobi.libnakamap.components.SearchBox;
import com.kayac.lobi.libnakamap.net.APIRes.GetGroup;
import com.kayac.lobi.libnakamap.net.CoreAPI.DefaultAPICallback;
import com.kayac.lobi.libnakamap.utils.DebugAssert;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.sdk.R;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ChatGroupInfoLeaderChange extends PathRoutedActivity {
    public static final String EXTRAS_GROUP_VALUE = "EXTRAS_GROUP_VALUE";
    public static final String PATH_CHANGE_LEADER = "/grouplist/chat/info/settings/change_leader";
    private Adapter mAdapter;
    private int mFooterHeight = 0;
    private final OnScrollListener mOnScrollListener = new OnScrollListener() {
        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }

        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (firstVisibleItem + visibleItemCount != totalItemCount) {
                return;
            }
            if (ChatGroupInfoLeaderChange.this.mAdapter.getCount() < ChatGroupInfoLeaderChange.this.mAdapter.getMembersCount() || ChatGroupInfoLeaderChange.this.mAdapter.getMembersCount() == 0) {
                LayoutParams params = (LayoutParams) view.getChildAt(totalItemCount - 1).getLayoutParams();
                if (params.height == 1) {
                    params.height = ChatGroupInfoLeaderChange.this.mFooterHeight;
                    view.getChildAt(totalItemCount - 1).setLayoutParams(params);
                } else {
                    ChatGroupInfoLeaderChange.this.mFooterHeight = params.height;
                }
                view.getChildAt(totalItemCount - 1).setVisibility(0);
                ChatGroupInfoLeaderChange.this.mPagerLoader.loadNextPage();
            }
        }
    };
    private final ChatMemberPagerLoader mPagerLoader = new ChatMemberPagerLoader(new DefaultAPICallback<GetGroup>(this) {
        public void onResponse(final GetGroup t) {
            runOnUiThread(new Runnable() {
                public void run() {
                    List<UserValue> members = t.group.getMembers();
                    ChatGroupInfoLeaderChange.this.mAdapter.setMemebersCount(t.group.getMembersCount());
                    ChatGroupInfoLeaderChange.this.mAdapter.addAll(members);
                }
            });
        }
    });

    public final class Adapter extends BaseAdapter implements RecyclerListener {
        private List<UserValue> mData = new ArrayList();
        private final LayoutInflater mLayoutInflater;
        private int mMembersCount;

        public Adapter(Context context) {
            this.mLayoutInflater = LayoutInflater.from(context);
        }

        public int getCount() {
            return this.mData.size();
        }

        public Object getItem(int position) {
            return Integer.valueOf(position);
        }

        public long getItemId(int arg0) {
            return (long) arg0;
        }

        public View getView(int position, View convertView, ViewGroup container) {
            ListRow view;
            if (convertView == null) {
                view = (ListRow) this.mLayoutInflater.inflate(R.layout.lobi_chat_group_info_leader_change_item, null);
                Builder builder = new Builder();
                builder.setIcon((ImageLoaderView) ((FrameLayout) view.getContent(0)).findViewById(R.id.lobi_list_row_content_image_loader_big_image_view));
                TwoLine twoLine = (TwoLine) view.getContent(1);
                builder.setName(twoLine.getTextView(0));
                builder.setDescription(twoLine.getTextView(1));
                view.setTag(builder.build());
            } else {
                view = (ListRow) convertView;
            }
            ItemHolder holder = (ItemHolder) view.getTag();
            UserValue user = (UserValue) this.mData.get(position);
            holder.icon.loadImage(user.getIcon());
            holder.name.setText(user.getName());
            holder.description.setText(user.getDescription());
            return view;
        }

        public void onMovedToScrapHeap(View arg0) {
        }

        public void addAll(Collection<UserValue> set) {
            this.mData.addAll(set);
            notifyDataSetChanged();
        }

        public void setMemebersCount(int members) {
            this.mMembersCount = members;
        }

        public int getMembersCount() {
            return this.mMembersCount;
        }
    }

    static final class ItemHolder {
        final TextView description;
        final ImageLoaderView icon;
        final TextView name;

        static final class Builder {
            TextView mDescription;
            ImageLoaderView mIcon;
            TextView mName;

            Builder() {
            }

            void setIcon(ImageLoaderView icon) {
                this.mIcon = icon;
            }

            void setName(TextView textView) {
                this.mName = textView;
            }

            void setDescription(TextView textView) {
                this.mDescription = textView;
            }

            public ItemHolder build() {
                return new ItemHolder(this.mIcon, this.mName, this.mDescription);
            }
        }

        ItemHolder(ImageLoaderView icon, TextView name, TextView description) {
            this.icon = icon;
            this.name = name;
            this.description = description;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lobi_chat_group_info_settings_change_leader_activity);
        this.mPagerLoader.setGid(getIntent().getExtras().getString("EXTRAS_GROUP_VALUE"));
        setupActionBar();
        setupListView();
        setupSearchBox();
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public void finish() {
        super.finish();
    }

    private void setupActionBar() {
        ActionBar actionBar = (ActionBar) findViewById(R.id.lobi_action_bar);
        DebugAssert.assertNotNull(actionBar);
        BackableContent content = (BackableContent) actionBar.getContent();
        content.setText("LEADER CHANGE");
        content.setOnBackButtonClickListener(new OnClickListener() {
            public void onClick(View v) {
                ChatGroupInfoLeaderChange.this.finish();
            }
        });
    }

    private void setupListView() {
        ListView listView = (ListView) findViewById(R.id.lobi_chat_group_info_change_leader);
        LayoutInflater inflater = LayoutInflater.from(this);
        listView.addHeaderView(inflater.inflate(R.layout.lobi_chat_group_info_leader_change_list_header, null));
        View view = inflater.inflate(R.layout.lobi_loading_footer, null);
        view.setVisibility(8);
        view.setBackgroundResource(R.drawable.lobi_bg_light_repeat);
        listView.addFooterView(view);
        listView.setOnScrollListener(this.mOnScrollListener);
        this.mAdapter = new Adapter(this);
        listView.setAdapter(this.mAdapter);
        loadMembers();
    }

    private void loadMembers() {
        this.mPagerLoader.loadNextPage();
    }

    private void setupSearchBox() {
        ((SearchBox) findViewById(R.id.lobi_search_box)).getEditText().addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String filter = s.toString();
            }

            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            public void afterTextChanged(Editable arg0) {
            }
        });
    }
}
