package com.kayac.lobi.sdk.chat.activity;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AbsListView.RecyclerListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;
import com.kayac.lobi.libnakamap.components.ImageLoaderView;
import com.kayac.lobi.libnakamap.components.LightBox;
import com.kayac.lobi.libnakamap.components.LightBox.OnContentLongClickListener;
import com.kayac.lobi.libnakamap.components.LightBox.OnContentShowListener;
import com.kayac.lobi.libnakamap.components.LightBox.OnDismissListener;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastore;
import com.kayac.lobi.libnakamap.utils.ChatListUtil;
import com.kayac.lobi.libnakamap.utils.ChatListUtil.ChatItemNormalHolder;
import com.kayac.lobi.libnakamap.utils.ChatListUtil.ChatItemReplyHolder;
import com.kayac.lobi.libnakamap.utils.ChatListUtil.ChatItemSystemMessageHolder;
import com.kayac.lobi.libnakamap.utils.ChatListUtil.ChatListItemData;
import com.kayac.lobi.libnakamap.utils.ChatListUtil.ChatViewHolder;
import com.kayac.lobi.libnakamap.utils.ChatListUtil.IViewModifier;
import com.kayac.lobi.libnakamap.utils.DebugAssert;
import com.kayac.lobi.libnakamap.utils.Log;
import com.kayac.lobi.libnakamap.utils.PictureUtil;
import com.kayac.lobi.libnakamap.value.ChatValue;
import com.kayac.lobi.libnakamap.value.ChatValue.Builder;
import com.kayac.lobi.libnakamap.value.GroupDetailValue;
import com.kayac.lobi.libnakamap.value.GroupValue.JoinCondition;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.sdk.R;
import com.kayac.lobi.sdk.utils.ManifestUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ChatListAdapter extends BaseAdapter implements OnScrollListener, RecyclerListener {
    private Comparator<ChatListItemData> mChatComparator = new Comparator<ChatListItemData>() {
        private final ChatValueComparator mComparatorImpl = new ChatValueComparator();

        public int compare(ChatListItemData data0, ChatListItemData data1) {
            return ChatListAdapter.this.mOrder * this.mComparatorImpl.compare(data0, data1);
        }
    };
    private final int mChatMessageMaxWidth;
    private final Context mContext;
    private final List<ChatListItemData> mData = new ArrayList();
    private int mFirstVisibleItem = 0;
    public final String mGid;
    public GroupDetailValue mGroupDetailValue;
    private final boolean mIsPublic;
    private List<JoinCondition> mJoinConditions;
    private final LightBox mLightBox;
    private OnShowLightBox mOnShowLightBox = new OnShowLightBox() {
        public void onShowLightBox(ImageView imageView) {
            ChatListAdapter.this.mLightBox.show(imageView, ((Activity) imageView.getContext()).getWindow());
        }
    };
    private int mOrder = 1;
    private final UserValue mUserValue;
    public IViewModifier mViewModifier;

    public interface OnShowLightBox {
        void onShowLightBox(ImageView imageView);
    }

    public enum ChatViewType {
        CHAT_LEFT(R.layout.lobi_chat_list_left_item, new ChatItemNormalHolder()),
        CHAT_RIGHT(R.layout.lobi_chat_list_right_item, new ChatItemNormalHolder()),
        CHAT_REPLY(R.layout.lobi_chat_reply_list_left_item, new ChatItemReplyHolder()),
        CHAT_SYSTEM(R.layout.lobi_chat_list_system_message, new ChatItemSystemMessageHolder());
        
        private final ChatViewHolder holder;
        private final int layout;

        private ChatViewType(int layout, ChatViewHolder holder) {
            this.layout = layout;
            this.holder = holder;
        }

        public int getLayout() {
            return this.layout;
        }

        public Object getHolder(View view) {
            return this.holder.build(view);
        }

        public void bind(Context context, ChatListAdapter adapter, View view, ChatListItemData chatListItemData, int position, List<JoinCondition> joinConditions) {
            this.holder.bind(context, adapter, view, chatListItemData, position, joinConditions);
        }
    }

    public interface OnReload {
        void onReload();

        void onReload(int i, ChatListItemData chatListItemData);
    }

    public ChatListAdapter(Context context, GroupDetailValue groupDetail, IViewModifier viewModifier) {
        this.mViewModifier = viewModifier;
        this.mContext = context;
        this.mLightBox = new LightBox(this.mContext);
        this.mGroupDetailValue = groupDetail;
        this.mGid = groupDetail.getUid();
        this.mIsPublic = groupDetail.isPublic();
        this.mUserValue = AccountDatastore.getCurrentUser();
        Resources resources = context.getResources();
        setupLightBox(this.mLightBox);
        this.mChatMessageMaxWidth = resources.getDisplayMetrics().widthPixels - resources.getDimensionPixelSize(R.dimen.lobi_chat_message_extra_room);
    }

    public boolean isEnabled(int position) {
        return false;
    }

    public void setOrder(int order) {
        this.mOrder = order;
    }

    public int getCount() {
        return this.mData.size();
    }

    public ChatListItemData getItem(int position) {
        return (ChatListItemData) this.mData.get(position);
    }

    public List<ChatListItemData> getData() {
        return this.mData;
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public int getItemViewType(int position) {
        boolean isLeft = false;
        ChatListItemData data = getItem(position);
        if (ChatListItemData.TYPE_CHAT.equals(data.getType())) {
            ChatValue chat = (ChatValue) data.getData();
            if (!TextUtils.equals(chat.getUser().getUid(), this.mUserValue.getUid())) {
                isLeft = true;
            }
            return this.mViewModifier.getViewType(position, chat.getType(), isLeft);
        } else if (ChatListItemData.TYPE_SYSTEM.equals(data.getType())) {
            return ChatViewType.CHAT_SYSTEM.ordinal();
        } else {
            return 0;
        }
    }

    public int getViewTypeCount() {
        return ChatViewType.values().length;
    }

    public View getView(int position, View convertView, ViewGroup container) {
        View view;
        ChatListItemData data = getItem(position);
        data.setOnShowLightBox(this.mOnShowLightBox);
        ChatViewType chatViewType = ChatViewType.values()[getItemViewType(position)];
        if (convertView == null) {
            view = LayoutInflater.from(this.mContext).inflate(chatViewType.getLayout(), null);
            view.setTag(chatViewType.getHolder(view));
        } else {
            view = convertView;
        }
        data.setIsNew(false);
        chatViewType.bind(this.mContext, this, view, data, position, this.mJoinConditions);
        return view;
    }

    public void onMovedToScrapHeap(View view) {
        if (view.getTag() instanceof ChatItemNormalHolder) {
            recycleImageSafely(((ChatItemNormalHolder) view.getTag()).picture);
        } else if (view.getTag() instanceof ChatItemReplyHolder) {
            recycleImageSafely(((ChatItemReplyHolder) view.getTag()).picture);
        }
    }

    private void recycleImageSafely(ImageLoaderView imageView) {
        ChatListItemData data = (ChatListItemData) imageView.getTag();
        if (data == null || data.getIsDispensable()) {
            imageView.recycleImageSafely();
        }
    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.mFirstVisibleItem = firstVisibleItem;
    }

    public void onScrollStateChanged(AbsListView arg0, int arg1) {
    }

    public final int getFirstVisibleItem() {
        return this.mFirstVisibleItem;
    }

    private void setupLightBox(LightBox lightbox) {
        lightbox.setOnDismissListener(new OnDismissListener() {
            public void onDismiss(ImageView v) {
                ((ChatListItemData) v.getTag()).setIsDispensable(true);
                ChatListAdapter.this.notifyDataSetChanged();
            }
        });
        if (ManifestUtil.hasWriteExternalStoragePermission(this.mContext)) {
            lightbox.setOnContentShowListener(new OnContentShowListener() {
                public void onContentShow(View content) {
                    Toast.makeText(content.getContext(), ChatListAdapter.this.mContext.getString(R.string.lobi_save_image_to_gallery), 0).show();
                }
            });
            lightbox.setOnContentLongClickListener(new OnContentLongClickListener() {
                public void onContentLongClick(View content) {
                    if (!PictureUtil.requestWritePermissionIfNotGranted((Activity) content.getContext())) {
                        ChatListUtil.savePhotoToGallery((Activity) ChatListAdapter.this.mContext, (ImageView) content);
                    }
                }
            });
        }
    }

    public void addChats(List<ChatValue> chats) {
        DebugAssert.assertNotNull(chats);
        for (ChatValue chat : chats) {
            addChat(chat);
        }
        notifyDataSetChanged();
    }

    public int removeChat(String id) {
        Builder chatValueBuilder = new Builder();
        chatValueBuilder.setId(id);
        String str = id;
        int pos = Collections.binarySearch(this.mData, new ChatListItemData(str, id + ChatListUtil.SUFFIX_CHAT, ChatListItemData.TYPE_CHAT, null, chatValueBuilder.build(), null, "", "", false, false, 0), this.mChatComparator);
        if (pos > -1) {
            String replyTo = ((ChatValue) ((ChatListItemData) this.mData.get(pos)).getData()).getReplyTo();
            this.mData.remove(pos);
            Log.d("chat", "remove pos: " + pos);
            if (!TextUtils.isEmpty(replyTo)) {
                Log.d("chat", "but reply");
                pos = -1;
            }
            notifyDataSetChanged();
        }
        return pos;
    }

    public void setChats(List<ChatValue> chats) {
        DebugAssert.assertNotNull(chats);
        this.mData.clear();
        for (ChatValue chat : chats) {
            if (!ChatValue.USER_DELETED.equals(chat.getType())) {
                this.mData.add(new ChatListItemData(chat.getId(), chat.getId() + ChatListUtil.SUFFIX_CHAT, ChatListItemData.TYPE_CHAT, this.mViewModifier, chat, this.mGroupDetailValue, this.mUserValue.getUid(), this.mGid, true, this.mIsPublic, this.mChatMessageMaxWidth));
            }
        }
        Collections.sort(this.mData, this.mChatComparator);
        notifyDataSetChanged();
    }

    public void clear() {
        this.mData.clear();
    }

    public void addChat(ChatValue chatValue) {
        if (!ChatValue.USER_DELETED.equals(chatValue.getType())) {
            ChatListItemData data = new ChatListItemData(chatValue.getId(), chatValue.getId() + ChatListUtil.SUFFIX_CHAT, ChatListItemData.TYPE_CHAT, this.mViewModifier, chatValue, this.mGroupDetailValue, this.mUserValue.getUid(), this.mGid, true, this.mIsPublic, this.mChatMessageMaxWidth);
            int pos = Collections.binarySearch(this.mData, data, this.mChatComparator);
            if (pos < 0) {
                this.mData.add(-(pos + 1), data);
                notifyDataSetChanged();
            } else {
                this.mData.set(pos, data);
                notifyDataSetChanged();
                ChatValue itemChatValue = (ChatValue) getItem(pos).getData();
                if (!(chatValue.getLikesCount() == itemChatValue.getLikesCount() && chatValue.getBoosCount() == itemChatValue.getBoosCount())) {
                    this.mData.set(pos, data);
                    notifyDataSetChanged();
                }
            }
            notifyDataSetChanged();
        }
    }

    public void addChatListItemData(List<ChatListItemData> data) {
        for (ChatListItemData chatItemData : data) {
            int pos = Collections.binarySearch(this.mData, chatItemData, this.mChatComparator);
            if (ChatListItemData.TYPE_CHAT.equals(chatItemData.getType())) {
                if (!ChatValue.USER_DELETED.equals(((ChatValue) chatItemData.getData()).getType())) {
                    relocateItem(pos, chatItemData);
                }
            } else {
                relocateItem(pos, chatItemData);
            }
        }
    }

    public void relocateItem(int pos, ChatListItemData chatItemData) {
        if (pos < 0) {
            this.mData.add(-(pos + 1), chatItemData);
        } else {
            this.mData.set(pos, chatItemData);
        }
    }

    public void setChatListItemData(int position, ChatListItemData item) {
        this.mData.set(position, item);
    }

    public List<ChatListItemData> convertToChatListItemData(List<ChatValue> chats) {
        List<ChatListItemData> items = new ArrayList();
        for (ChatValue chat : chats) {
            if (!ChatValue.USER_DELETED.equals(chat.getType())) {
                items.add(new ChatListItemData(chat.getId(), chat.getId() + ChatListUtil.SUFFIX_CHAT, ChatListItemData.TYPE_CHAT, this.mViewModifier, chat, this.mGroupDetailValue, this.mUserValue.getUid(), this.mGid, true, this.mIsPublic, this.mChatMessageMaxWidth));
            }
        }
        return items;
    }

    public void addChatOrReply(ChatValue chatValue) {
        if (!ChatValue.USER_DELETED.equals(chatValue.getType())) {
            String replyTo = chatValue.getReplyTo();
            boolean isReply = !TextUtils.isEmpty(replyTo);
            String id = isReply ? replyTo : chatValue.getId();
            int pos = Collections.binarySearch(this.mData, new ChatListItemData(id, id + ChatListUtil.SUFFIX_CHAT, ChatListItemData.TYPE_CHAT, this.mViewModifier, chatValue, this.mGroupDetailValue, this.mUserValue.getUid(), this.mGid, true, this.mIsPublic, this.mChatMessageMaxWidth), this.mChatComparator);
            if (!isReply && pos < 0) {
                ChatListItemData newChat = new ChatListItemData(chatValue.getId(), chatValue.getId() + ChatListUtil.SUFFIX_CHAT, ChatListItemData.TYPE_CHAT, this.mViewModifier, chatValue, this.mGroupDetailValue, this.mUserValue.getUid(), this.mGid, true, this.mIsPublic, this.mChatMessageMaxWidth);
                newChat.setIsNew(true);
                this.mData.add(-(pos + 1), newChat);
            } else if (!isReply || pos < 0) {
                Log.i("chat", "invalid data..");
            } else {
                Log.i("chat", "append reply");
                ChatListItemData parentChat = (ChatListItemData) this.mData.get(pos);
                ChatListUtil.addReply((ChatValue) parentChat.getData(), chatValue);
                this.mData.set(pos, parentChat);
            }
        }
    }

    public void setJoinConditions(List<JoinCondition> joinConditions) {
        this.mJoinConditions = joinConditions;
    }

    public List<JoinCondition> getJoinConditions() {
        return this.mJoinConditions;
    }

    public void refreshGroupDetailValue() {
        this.mGroupDetailValue = TransactionDatastore.getGroupDetail(this.mGid, this.mUserValue.getUid());
    }
}
