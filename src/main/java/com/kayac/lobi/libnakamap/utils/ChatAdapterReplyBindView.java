package com.kayac.lobi.libnakamap.utils;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import com.kayac.lobi.libnakamap.utils.ChatListUtil.ChatListItemData;
import com.kayac.lobi.libnakamap.utils.ChatListUtil.IViewModifier;
import com.kayac.lobi.libnakamap.value.ChatValue;
import com.kayac.lobi.libnakamap.value.GroupDetailValue;
import com.kayac.lobi.libnakamap.value.GroupValue.JoinCondition;
import com.kayac.lobi.sdk.chat.activity.ChatListAdapter.ChatViewType;
import com.kayac.lobi.sdk.chat.activity.ChatReplyActivity;
import com.kayac.lobi.sdk.utils.SDKBridge;
import java.util.List;

public class ChatAdapterReplyBindView implements IViewModifier {
    private final Context mContext;

    public ChatAdapterReplyBindView(Context context) {
        this.mContext = context;
    }

    public Bundle getGalleryBundle(ChatListItemData data) {
        return SDKBridge.getGalleryBundleForStandardView(data);
    }

    public void displayReadMark(View view, int visible) {
    }

    public void setReplyClickListener(final Context context, View view, OnClickListener replyButtonClick) {
        view.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (context instanceof ChatReplyActivity) {
                    context.showKeyboard();
                }
            }
        });
    }

    public void setRefers(ChatListItemData data, ChatValue chat, LinearLayout refersContainer, int chatMessageMaxWidth) {
    }

    public void addExtraMargin(View view) {
        view.setPadding(0, 40, 0, 40);
    }

    public int getViewType(int position, String type, boolean isLeft) {
        if (position != 0) {
            return ChatViewType.CHAT_REPLY.ordinal();
        }
        if (isLeft) {
            return ChatViewType.CHAT_LEFT.ordinal();
        }
        return ChatViewType.CHAT_RIGHT.ordinal();
    }

    public void setupChatView(View view, ChatListItemData data, String uid, String gid, GroupDetailValue groupDetail, boolean tymeFormat, List<JoinCondition> list) {
    }
}
