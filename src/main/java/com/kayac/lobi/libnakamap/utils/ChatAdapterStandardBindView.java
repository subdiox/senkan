package com.kayac.lobi.libnakamap.utils;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastore;
import com.kayac.lobi.libnakamap.utils.ChatListUtil.ChatItemNormalHolder;
import com.kayac.lobi.libnakamap.utils.ChatListUtil.ChatItemNormalHolder.ChatReplyLayoutHolder;
import com.kayac.lobi.libnakamap.utils.ChatListUtil.ChatListItemData;
import com.kayac.lobi.libnakamap.utils.ChatListUtil.IViewModifier;
import com.kayac.lobi.libnakamap.value.ChatReferValue;
import com.kayac.lobi.libnakamap.value.ChatValue;
import com.kayac.lobi.libnakamap.value.ChatValue.Replies;
import com.kayac.lobi.libnakamap.value.GroupDetailValue;
import com.kayac.lobi.libnakamap.value.GroupValue.JoinCondition;
import com.kayac.lobi.sdk.R;
import com.kayac.lobi.sdk.chat.activity.ChatActivity;
import com.kayac.lobi.sdk.chat.activity.ChatActivity.OnGroupJoinCallback;
import com.kayac.lobi.sdk.chat.activity.ChatListAdapter.ChatViewType;
import com.kayac.lobi.sdk.chat.activity.ChatReferLayout;
import com.kayac.lobi.sdk.chat.activity.ChatReplyLayout;
import com.kayac.lobi.sdk.utils.SDKBridge;
import java.util.List;

public class ChatAdapterStandardBindView implements IViewModifier {
    private final Context mContext;

    public ChatAdapterStandardBindView(Context context) {
        this.mContext = context;
    }

    public Bundle getGalleryBundle(ChatListItemData data) {
        return SDKBridge.getGalleryBundleForStandardView(data);
    }

    public void displayReadMark(View view, int visible) {
        view.setVisibility(visible);
    }

    public void setupChatView(View view, ChatListItemData data, String uid, String gid, GroupDetailValue groupDetail, boolean tymeFormat, List<JoinCondition> joinConditions) {
        ChatItemNormalHolder holder = (ChatItemNormalHolder) view.getTag();
        final ChatValue chatValue = (ChatValue) data.getData();
        Replies replies = chatValue.getReplies();
        int replyCount = replies.getChats().size() <= 2 ? replies.getChats().size() : 2;
        int visibleReplyCount = Math.min(2, replyCount);
        holder.replyContainer.setVisibility(replyCount > 0 ? 0 : 8);
        holder.replyOne.setVisibility(8);
        holder.replyTwo.setVisibility(8);
        for (int i = 0; i < replyCount; i++) {
            if (i < visibleReplyCount) {
                View replyView = holder.replyOne;
                ChatReplyLayoutHolder replyLayoutHolder = holder.replyLayoutHolderOne;
                if (i == 1) {
                    replyLayoutHolder = holder.replyLayoutHolderTwo;
                    replyView = holder.replyTwo;
                }
                replyView.setVisibility(0);
                final ChatValue reply = (ChatValue) replies.getChats().get(i);
                ChatReplyLayout replyLayout = new ChatReplyLayout(this.mContext, replyLayoutHolder, false, gid, (ChatValue) replies.getChats().get(i));
                replyLayout.initView();
                final ChatListItemData chatListItemData = data;
                final List<JoinCondition> list = joinConditions;
                replyLayout.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        final GroupDetailValue groupDetail = TransactionDatastore.getGroupDetail(chatListItemData.getGroupUid(), chatListItemData.getUserUid());
                        if (!ChatListUtil.checkToJoin(groupDetail)) {
                            ChatActivity.joinPublicGroup(ChatAdapterStandardBindView.this.mContext, groupDetail, list, true, new OnGroupJoinCallback() {
                                public void onJoinResult(boolean success) {
                                    GroupDetailValue newGroupDetailValue = TransactionDatastore.getGroupDetail(groupDetail.getUid(), AccountDatastore.getCurrentUser().getUid());
                                    if (success && newGroupDetailValue != null && chatValue != null) {
                                        ChatListUtil.goToRepliesFromChat(chatValue.getId(), newGroupDetailValue, false);
                                    }
                                }
                            });
                        } else if (reply != null) {
                            ChatListUtil.goToRepliesFromChat(chatValue.getId(), groupDetail, false);
                        }
                    }
                });
                replyLayout.setChatReply(reply, tymeFormat);
            }
        }
    }

    public void setReplyClickListener(Context context, View view, OnClickListener replyButtonClick) {
        view.setOnClickListener(replyButtonClick);
    }

    public void setRefers(ChatListItemData data, ChatValue chat, LinearLayout refersContainer, int chatMessageMaxWidth) {
        if (refersContainer != null) {
            List<ChatReferValue> refers = chat.getRefers();
            if (refers == null) {
                refersContainer.setVisibility(8);
                return;
            }
            int i;
            ChatReferLayout referLayout;
            refersContainer.setVisibility(0);
            int childCount = refersContainer.getChildCount();
            int refersCount = refers.size();
            for (i = childCount; i < refersCount; i++) {
                referLayout = new ChatReferLayout(this.mContext);
                LayoutParams params = new LayoutParams(-1, -2);
                if (i > 0) {
                    params.topMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.lobi_margin_middle);
                }
                refersContainer.addView(referLayout, params);
            }
            for (i = 0; i < refersCount; i++) {
                referLayout = (ChatReferLayout) refersContainer.getChildAt(i);
                referLayout.setChatRefer((ChatReferValue) refers.get(i));
                referLayout.setVisibility(0);
            }
            for (i = refersCount; i < childCount; i++) {
                refersContainer.getChildAt(i).setVisibility(8);
            }
        }
    }

    public void addExtraMargin(View view) {
    }

    public int getViewType(int position, String type, boolean isLeft) {
        if (!"shout".equals(type) && !"normal".equals(type)) {
            return ChatViewType.CHAT_SYSTEM.ordinal();
        }
        if (isLeft) {
            return ChatViewType.CHAT_LEFT.ordinal();
        }
        return ChatViewType.CHAT_RIGHT.ordinal();
    }
}
