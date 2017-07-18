package com.kayac.lobi.libnakamap.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.text.style.URLSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.kayac.lobi.libnakamap.components.ChatOptionButton;
import com.kayac.lobi.libnakamap.components.CustomDialog;
import com.kayac.lobi.libnakamap.components.CustomDialog.EditTextContent;
import com.kayac.lobi.libnakamap.components.CustomTextView;
import com.kayac.lobi.libnakamap.components.ImageLoaderCircleView;
import com.kayac.lobi.libnakamap.components.ImageLoaderView;
import com.kayac.lobi.libnakamap.components.ListPopupWindow;
import com.kayac.lobi.libnakamap.components.PathRouter;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.datastore.TransactionDatastore;
import com.kayac.lobi.libnakamap.net.APIDef.PostAccusations.RequestKey;
import com.kayac.lobi.libnakamap.net.APIRes.PostAccusations;
import com.kayac.lobi.libnakamap.net.APIRes.PostGroupChatRemove;
import com.kayac.lobi.libnakamap.net.CoreAPI;
import com.kayac.lobi.libnakamap.net.CoreAPI.DefaultAPICallback;
import com.kayac.lobi.libnakamap.value.AssetValue;
import com.kayac.lobi.libnakamap.value.ChatValue;
import com.kayac.lobi.libnakamap.value.ChatValue.Replies;
import com.kayac.lobi.libnakamap.value.GroupDetailValue;
import com.kayac.lobi.libnakamap.value.GroupValue;
import com.kayac.lobi.libnakamap.value.GroupValue.JoinCondition;
import com.kayac.lobi.libnakamap.value.GroupValue.JoinCondition.InstalledParams;
import com.kayac.lobi.libnakamap.value.GroupValue.JoinCondition.Params;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.sdk.R;
import com.kayac.lobi.sdk.activity.group.ContactListActivity;
import com.kayac.lobi.sdk.activity.invitation.InvitationWebViewActivity;
import com.kayac.lobi.sdk.activity.setting.WebViewSetting;
import com.kayac.lobi.sdk.chat.activity.ChatActivity;
import com.kayac.lobi.sdk.chat.activity.ChatActivity.OnGroupJoinCallback;
import com.kayac.lobi.sdk.chat.activity.ChatListAdapter;
import com.kayac.lobi.sdk.chat.activity.ChatListAdapter.OnShowLightBox;
import com.kayac.lobi.sdk.chat.activity.ChatReplyActivity;
import com.kayac.lobi.sdk.chat.activity.ChatSDKModuleBridge;
import com.kayac.lobi.sdk.utils.SDKBridge;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatListUtil {
    public static final int DATE_LONG = 1;
    public static final int DATE_SMALL = 0;
    public static final String SUFFIX_ADS = "a";
    public static final String SUFFIX_CHAT = "b";
    public static int iTimeTypeFormat = 0;
    public static final OnLongClickListener mOnBalloonLongClick = new OnLongClickListener() {
        public boolean onLongClick(View v) {
            ChatValue chatValue = (ChatValue) ((ChatListItemData) v.getTag()).getData();
            if (TextUtils.isEmpty(chatValue.getMessage())) {
                return false;
            }
            TextUtil.copyText(v.getContext(), chatValue.getMessage());
            return true;
        }
    };

    public interface IViewModifier {
        void addExtraMargin(View view);

        void displayReadMark(View view, int i);

        Bundle getGalleryBundle(ChatListItemData chatListItemData);

        int getViewType(int i, String str, boolean z);

        void setRefers(ChatListItemData chatListItemData, ChatValue chatValue, LinearLayout linearLayout, int i);

        void setReplyClickListener(Context context, View view, OnClickListener onClickListener);

        void setupChatView(View view, ChatListItemData chatListItemData, String str, String str2, GroupDetailValue groupDetailValue, boolean z, List<JoinCondition> list);
    }

    public interface ChatViewHolder {
        void bind(Context context, ChatListAdapter chatListAdapter, View view, ChatListItemData chatListItemData, int i, List<JoinCondition> list);

        Object build(View view);
    }

    public static final class ChatItemNormalHolder implements ChatViewHolder {
        public final CustomTextView chatMessage;
        public final LinearLayout chatOptionsContainer;
        public final LinearLayout dislikeContainer;
        public final TextView dislikeCountText;
        public final ImageView dislikeImage;
        public final ImageLoaderCircleView icon;
        public final LinearLayout likeContainer;
        public final TextView likeCountText;
        public final ImageView likeImage;
        public final FrameLayout messageBalloon;
        public final ImageView moreOptions;
        public final ImageLoaderView picture;
        public final TextView pictureCount;
        public final LinearLayout pictureDescription;
        public final FrameLayout pictureFrame;
        public final View readMark;
        public final LinearLayout refersContainer;
        public final LinearLayout replyButton;
        public final ImageView replyButtonImage;
        public final LinearLayout replyContainer;
        public final TextView replyCountText;
        public final ChatReplyLayoutHolder replyLayoutHolderOne;
        public final ChatReplyLayoutHolder replyLayoutHolderTwo;
        public final FrameLayout replyOne;
        public final ImageLoaderView replyPicture;
        public final FrameLayout replyPictureContainer;
        public final View replyPictureDescription;
        public final FrameLayout replyTwo;
        public final ImageView shoutIcon;
        public final TextView time;
        public final TextView userName;

        public static final class ChatReplyLayoutHolder {
            public final TextView date;
            public final ImageLoaderCircleView icon;
            public final TextView name;
            public final ImageLoaderView picture;
            public final FrameLayout pictureContainer;
            public final TextView pictureCount;
            public final View pictureDescription;
            public final CustomTextView replyMessage;

            public ChatReplyLayoutHolder(View view) {
                this.name = (TextView) ViewUtils.findViewById(view, R.id.lobi_chat_reply_name);
                this.date = (TextView) ViewUtils.findViewById(view, R.id.lobi_chat_reply_date);
                this.icon = (ImageLoaderCircleView) ViewUtils.findViewById(view, R.id.lobi_chat_reply_icon);
                this.pictureContainer = (FrameLayout) ViewUtils.findViewById(view, R.id.lobi_chat_reply_picture_container);
                this.picture = (ImageLoaderView) ViewUtils.findViewById(view, R.id.lobi_chat_reply_picture);
                this.pictureDescription = (View) ViewUtils.findViewById(view, R.id.lobi_chat_list_item_picture_description);
                this.replyMessage = (CustomTextView) ViewUtils.findViewById(view, R.id.lobi_chat_reply_message);
                this.pictureCount = (TextView) ViewUtils.findViewById(view, R.id.lobi_chat_list_item_picture_count);
            }
        }

        public ChatItemNormalHolder() {
            this.icon = null;
            this.userName = null;
            this.time = null;
            this.chatMessage = null;
            this.picture = null;
            this.pictureFrame = null;
            this.pictureDescription = null;
            this.pictureCount = null;
            this.messageBalloon = null;
            this.shoutIcon = null;
            this.likeCountText = null;
            this.dislikeCountText = null;
            this.likeImage = null;
            this.dislikeImage = null;
            this.likeContainer = null;
            this.dislikeContainer = null;
            this.moreOptions = null;
            this.replyContainer = null;
            this.replyOne = null;
            this.replyTwo = null;
            this.replyLayoutHolderOne = null;
            this.replyLayoutHolderTwo = null;
            this.replyCountText = null;
            this.replyButton = null;
            this.replyButtonImage = null;
            this.readMark = null;
            this.chatOptionsContainer = null;
            this.replyPictureContainer = null;
            this.replyPicture = null;
            this.replyPictureDescription = null;
            this.refersContainer = null;
        }

        public ChatItemNormalHolder(View view) {
            this.icon = (ImageLoaderCircleView) ViewUtils.findViewById(view, R.id.lobi_chat_list_item_icon);
            this.userName = (TextView) ViewUtils.findViewById(view, R.id.lobi_chat_list_item_user_name);
            this.time = (TextView) ViewUtils.findViewById(view, R.id.lobi_chat_list_item_time);
            this.chatMessage = (CustomTextView) ViewUtils.findViewById(view, R.id.lobi_chat_list_item_message);
            this.picture = (ImageLoaderView) ViewUtils.findViewById(view, R.id.lobi_chat_list_item_picture);
            this.pictureFrame = (FrameLayout) ViewUtils.findViewById(view, R.id.lobi_chat_list_item_picture_container);
            this.pictureDescription = (LinearLayout) ViewUtils.findViewById(view, R.id.lobi_chat_list_item_picture_description);
            this.pictureCount = (TextView) ViewUtils.findViewById(view, R.id.lobi_chat_list_item_picture_count);
            this.messageBalloon = (FrameLayout) ViewUtils.findViewById(view, R.id.lobi_chat_list_item_message_balloon);
            this.shoutIcon = (ImageView) ViewUtils.findViewById(view, R.id.lobi_chat_list_stamp_shout_ico);
            this.likeCountText = (TextView) ViewUtils.findViewById(view, R.id.lobi_chat_list_item_like_text);
            this.dislikeCountText = (TextView) ViewUtils.findViewById(view, R.id.lobi_chat_list_item_dislike_text);
            this.likeImage = (ImageView) ViewUtils.findViewById(view, R.id.lobi_chat_list_item_like_button);
            this.dislikeImage = (ImageView) ViewUtils.findViewById(view, R.id.lobi_chat_list_item_dislike_button);
            this.likeContainer = (LinearLayout) ViewUtils.findViewById(view, R.id.lobi_chat_list_item_like_container);
            this.dislikeContainer = (LinearLayout) ViewUtils.findViewById(view, R.id.lobi_chat_list_item_dislike_container);
            this.moreOptions = (ImageView) ViewUtils.findViewById(view, R.id.lobi_chat_list_more);
            this.replyContainer = (LinearLayout) ViewUtils.findViewById(view, R.id.lobi_chat_list_item_replies_container);
            this.replyOne = (FrameLayout) ViewUtils.findViewById(view, R.id.lobi_chat_list_item_replies_reply_one);
            this.replyTwo = (FrameLayout) ViewUtils.findViewById(view, R.id.lobi_chat_list_item_replies_reply_two);
            this.replyLayoutHolderOne = new ChatReplyLayoutHolder(this.replyOne);
            this.replyLayoutHolderTwo = new ChatReplyLayoutHolder(this.replyTwo);
            this.replyCountText = (TextView) ViewUtils.findViewById(view, R.id.lobi_chat_list_item_reply_text);
            this.replyButton = (LinearLayout) ViewUtils.findViewById(view, R.id.lobi_chat_list_item_reply_button);
            this.replyButtonImage = (ImageView) ViewUtils.findViewById(view, R.id.lobi_chat_list_item_reply_button_image);
            this.readMark = (View) ViewUtils.findViewById(view, R.id.lobi_chat_list_item_read_mark);
            this.chatOptionsContainer = (LinearLayout) ViewUtils.findViewById(view, R.id.lobi_chat_list_item_options_container);
            this.replyPictureContainer = (FrameLayout) ViewUtils.findViewById(view, R.id.lobi_chat_reply_picture_container);
            this.replyPicture = (ImageLoaderView) ViewUtils.findViewById(view, R.id.lobi_chat_reply_picture);
            this.replyPictureDescription = (View) ViewUtils.findViewById(view, R.id.lobi_chat_list_item_picture_description);
            this.refersContainer = (LinearLayout) ViewUtils.findViewById(view, R.id.lobi_chat_list_item_refers_container);
        }

        public ChatItemNormalHolder build(View view) {
            return new ChatItemNormalHolder(view);
        }

        public void bind(Context context, ChatListAdapter adapter, View view, ChatListItemData chatListItemData, int position, List<JoinCondition> joinConditions) {
            ChatItemNormalHolder holder = (ChatItemNormalHolder) view.getTag();
            ChatValue chatValue = (ChatValue) chatListItemData.getData();
            ChatReadMarkUtils.saveChatAtOnMemory(chatListItemData.getGroupUid(), chatValue.getCreatedDate());
            ChatListUtil.unreadMark(holder.readMark, chatListItemData.getIsWithReadMark(), chatListItemData.getViewModifier());
            final ListPopupWindow moreOptions = ChatListUtil.moreOptions(context, chatListItemData);
            holder.moreOptions.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    moreOptions.showAsDropDown(v);
                }
            });
            setupChatBackground(chatListItemData, holder.messageBalloon, holder.shoutIcon, !TextUtils.equals(chatValue.getUser().getUid(), chatListItemData.getUserUid()), "shout".equals(chatValue.getType()));
            ChatListUtil.setupBasicView(context, adapter, chatListItemData, holder.icon, holder.userName, holder.time);
            chatListItemData.getViewModifier().addExtraMargin(view);
            String imagetype = chatValue.getImageType();
            boolean isStamp = imagetype == null ? false : imagetype.equals("stamp");
            if (isStamp) {
                bindStampChat(context, chatListItemData, holder);
            } else {
                bindNormalChat(context, adapter, view, chatListItemData, position, joinConditions, holder, chatValue);
            }
        }

        public void bindStampChat(Context context, ChatListItemData chatListItemData, ChatItemNormalHolder holder) {
            holder.messageBalloon.setBackgroundColor(0);
            holder.messageBalloon.setOnClickListener(null);
            holder.chatOptionsContainer.setVisibility(8);
            holder.replyContainer.removeAllViews();
            holder.replyContainer.setVisibility(8);
            ChatListUtil.displayStamp(context, chatListItemData, holder.chatMessage, holder.picture, holder.pictureFrame, holder.pictureDescription);
        }

        public void bindNormalChat(Context context, ChatListAdapter adapter, View view, ChatListItemData chatListItemData, int position, List<JoinCondition> joinConditions, ChatItemNormalHolder holder, ChatValue chatValue) {
            final ChatValue chatValue2 = chatValue;
            final List<JoinCondition> list = joinConditions;
            OnClickListener ballonlistener = new OnClickListener() {
                public void onClick(View v) {
                    final ChatListItemData tag = (ChatListItemData) v.getTag();
                    GroupDetailValue groupDetail = TransactionDatastore.getGroupDetail(tag.getGroupUid(), tag.getUserUid());
                    if (!ChatListUtil.checkToJoin(groupDetail)) {
                        ChatActivity.joinPublicGroup(v.getContext(), tag.getGroupDetailValue(), list, true, new OnGroupJoinCallback() {
                            public void onJoinResult(boolean success) {
                                GroupDetailValue groupDetail = TransactionDatastore.getGroupDetail(tag.getGroupDetailValue().getUid(), AccountDatastore.getCurrentUser().getUid());
                                if (success && groupDetail != null && chatValue2 != null) {
                                    ChatListUtil.goToRepliesFromChat(chatValue2.getId(), groupDetail, false);
                                }
                            }
                        });
                    } else if (chatValue2 != null) {
                        ChatListUtil.goToRepliesFromChat(chatValue2.getId(), groupDetail, false);
                    }
                }
            };
            holder.messageBalloon.setOnLongClickListener(ChatListUtil.mOnBalloonLongClick);
            holder.messageBalloon.setOnClickListener(ballonlistener);
            holder.chatMessage.setOnClickListener(ballonlistener);
            holder.chatMessage.setOnTextLinkClickedListener(CustomTextViewUtil.getOnTextLinkClickedListener(InvitationWebViewActivity.PATH_INVITATION, " "));
            holder.chatOptionsContainer.setVisibility(0);
            ChatListUtil.setupChat(context, view, chatListItemData, holder.chatMessage, holder.picture, holder.pictureFrame, holder.pictureDescription, holder.pictureCount, holder.userName, joinConditions);
            chatListItemData.getViewModifier().setRefers(chatListItemData, chatValue, holder.refersContainer, chatListItemData.getChatMessageMaxWidth());
            for (int id : new int[]{R.id.lobi_chat_list_item_like_container, R.id.lobi_chat_list_item_bottom_options_separator_0, R.id.lobi_chat_list_item_dislike_container, R.id.lobi_chat_list_item_bottom_options_separator_1}) {
                holder.chatOptionsContainer.findViewById(id).setVisibility(4);
            }
            holder.likeContainer.setVisibility(4);
            holder.dislikeContainer.setVisibility(4);
            Replies replies = chatValue.getReplies();
            boolean isReplied = false;
            if (replies.getChats().size() > 0) {
                holder.replyCountText.setText(String.valueOf(replies.getCount()));
                for (ChatValue replyChatValue : replies.getChats()) {
                    if (TextUtils.equals(replyChatValue.getUser().getUid(), chatListItemData.getUserUid())) {
                        isReplied = true;
                    }
                }
            } else {
                holder.replyCountText.setText(R.string.lobi_chat_reply);
            }
            holder.replyButtonImage.setImageResource(isReplied ? R.drawable.lobi_icn_chat_reply_on : R.drawable.lobi_icn_chat_reply);
        }

        private static void setupChatBackground(ChatListItemData data, View messageBalloon, ImageView shoutIcon, boolean isLeft, boolean isShout) {
            int i = 0;
            messageBalloon.setVisibility(0);
            if (!isShout) {
                i = 8;
            }
            shoutIcon.setVisibility(i);
            i = isLeft ? isShout ? R.drawable.lobi_balloon_left_shout : R.drawable.lobi_balloon_left : isShout ? R.drawable.lobi_balloon_right_shout : R.drawable.lobi_balloon_right;
            messageBalloon.setBackgroundResource(i);
            messageBalloon.setTag(data);
            messageBalloon.setOnLongClickListener(new OnLongClickListener() {
                public boolean onLongClick(View v) {
                    ChatValue chatValue = (ChatValue) ((ChatListItemData) v.getTag()).getData();
                    if (TextUtils.isEmpty(chatValue.getMessage())) {
                        return false;
                    }
                    ((ClipboardManager) v.getContext().getSystemService("clipboard")).setText(chatValue.getMessage());
                    Toast.makeText(v.getContext(), v.getContext().getResources().getString(R.string.lobi_chat_copy_done), 0).show();
                    return true;
                }
            });
        }
    }

    public static final class ChatItemReplyHolder implements ChatViewHolder {
        public final CustomTextView chatMessage;
        public final LinearLayout dislikeContainer;
        public final TextView dislikeCountText;
        public final ImageView dislikeImage;
        public final ImageLoaderCircleView icon;
        public final LinearLayout likeContainer;
        public final TextView likeCountText;
        public final ImageView likeImage;
        public final FrameLayout messageBalloon;
        public final ImageView moreOptions;
        public final ImageLoaderView picture;
        public final TextView pictureCount;
        public final LinearLayout pictureDescription;
        public final FrameLayout pictureFrame;
        public final View readMark;
        public final TextView time;
        public final TextView userName;
        public final View view;

        public ChatItemReplyHolder() {
            this.view = null;
            this.icon = null;
            this.userName = null;
            this.time = null;
            this.chatMessage = null;
            this.picture = null;
            this.pictureFrame = null;
            this.pictureDescription = null;
            this.pictureCount = null;
            this.likeCountText = null;
            this.dislikeCountText = null;
            this.likeImage = null;
            this.dislikeImage = null;
            this.likeContainer = null;
            this.dislikeContainer = null;
            this.moreOptions = null;
            this.readMark = null;
            this.messageBalloon = null;
        }

        public ChatItemReplyHolder(View view) {
            this.view = view;
            this.icon = (ImageLoaderCircleView) ViewUtils.findViewById(view, R.id.lobi_chat_list_item_icon);
            this.userName = (TextView) ViewUtils.findViewById(view, R.id.lobi_chat_list_item_user_name);
            this.time = (TextView) ViewUtils.findViewById(view, R.id.lobi_chat_list_item_time);
            this.chatMessage = (CustomTextView) ViewUtils.findViewById(view, R.id.lobi_chat_list_item_message);
            this.picture = (ImageLoaderView) ViewUtils.findViewById(view, R.id.lobi_chat_list_item_picture);
            this.pictureFrame = (FrameLayout) ViewUtils.findViewById(view, R.id.lobi_chat_list_item_picture_container);
            this.pictureDescription = (LinearLayout) ViewUtils.findViewById(view, R.id.lobi_chat_list_item_picture_description);
            this.pictureCount = (TextView) ViewUtils.findViewById(view, R.id.lobi_chat_list_item_picture_count);
            this.likeCountText = (TextView) ViewUtils.findViewById(view, R.id.lobi_chat_list_item_like_text);
            this.dislikeCountText = (TextView) ViewUtils.findViewById(view, R.id.lobi_chat_list_item_dislike_text);
            this.likeImage = (ImageView) ViewUtils.findViewById(view, R.id.lobi_chat_list_item_like_button);
            this.dislikeImage = (ImageView) ViewUtils.findViewById(view, R.id.lobi_chat_list_item_dislike_button);
            this.likeContainer = (LinearLayout) ViewUtils.findViewById(view, R.id.lobi_chat_list_item_like_container);
            this.dislikeContainer = (LinearLayout) ViewUtils.findViewById(view, R.id.lobi_chat_list_item_dislike_container);
            this.moreOptions = (ImageView) ViewUtils.findViewById(view, R.id.lobi_chat_list_more);
            this.readMark = (View) ViewUtils.findViewById(view, R.id.lobi_chat_list_item_read_mark);
            this.messageBalloon = (FrameLayout) ViewUtils.findViewById(view, R.id.lobi_chat_list_item_message_balloon);
        }

        public Object build(View view) {
            return new ChatItemReplyHolder(view);
        }

        public void bind(Context context, ChatListAdapter adapter, View view, ChatListItemData chatListItemData, int position, List<JoinCondition> list) {
            ChatItemReplyHolder holder = (ChatItemReplyHolder) view.getTag();
            ChatValue chatValue = (ChatValue) chatListItemData.getData();
            final ListPopupWindow moreOptions = ChatListUtil.moreOptions(context, chatListItemData);
            holder.moreOptions.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    moreOptions.showAsDropDown(v);
                }
            });
            ChatListUtil.setupBasicView(context, adapter, chatListItemData, holder.icon, holder.userName, holder.time);
            String imagetype = chatValue.getImageType();
            boolean isStamp = imagetype == null ? false : imagetype.equals("stamp");
            if (isStamp) {
                holder.likeContainer.setVisibility(8);
                holder.dislikeContainer.setVisibility(8);
                holder.messageBalloon.setOnLongClickListener(null);
                ChatListUtil.displayStamp(context, chatListItemData, holder.chatMessage, holder.picture, holder.pictureFrame, holder.pictureDescription);
                return;
            }
            ChatListUtil.setupChat(context, view, chatListItemData, holder.chatMessage, holder.picture, holder.pictureFrame, holder.pictureDescription, holder.pictureCount, holder.userName, null);
            for (int id : new int[]{R.id.lobi_chat_list_item_like_container, R.id.lobi_chat_list_item_dislike_container}) {
                holder.view.findViewById(id).setVisibility(4);
            }
            holder.likeContainer.setVisibility(4);
            holder.dislikeContainer.setVisibility(4);
            holder.messageBalloon.setTag(chatListItemData);
            holder.messageBalloon.setOnLongClickListener(ChatListUtil.mOnBalloonLongClick);
            holder.chatMessage.setOnTextLinkClickedListener(CustomTextViewUtil.getOnTextLinkClickedListener(InvitationWebViewActivity.PATH_INVITATION, " "));
        }
    }

    public static final class ChatItemSystemMessageHolder implements ChatViewHolder {
        private final String TYPE_CREATED_GROUP;
        public final LinearLayout createdGroupContainer;
        public final Button createdGroupInvite;
        public final CustomTextView message;
        public final View readMark;
        public final TextView time;
        public final View view;

        public ChatItemSystemMessageHolder() {
            this.TYPE_CREATED_GROUP = ChatValue.SYSTEM_CREATED;
            this.view = null;
            this.time = null;
            this.message = null;
            this.createdGroupContainer = null;
            this.createdGroupInvite = null;
            this.readMark = null;
        }

        public ChatItemSystemMessageHolder(View view) {
            this.TYPE_CREATED_GROUP = ChatValue.SYSTEM_CREATED;
            this.view = view;
            this.time = (TextView) ViewUtils.findViewById(view, R.id.lobi_chat_list_system_message_time);
            this.message = (CustomTextView) ViewUtils.findViewById(view, R.id.lobi_chat_list_system_message_message);
            this.createdGroupContainer = (LinearLayout) ViewUtils.findViewById(view, R.id.lobi_chat_list_system_message_creted_group);
            this.createdGroupInvite = (Button) ViewUtils.findViewById(view, R.id.lobi_chat_list_system_message_invitation_button);
            this.readMark = (View) ViewUtils.findViewById(view, R.id.lobi_chat_list_item_read_mark);
        }

        public Object build(View view) {
            return new ChatItemSystemMessageHolder(view);
        }

        public void bind(Context context, ChatListAdapter adapter, View view, final ChatListItemData chatListItemData, int position, List<JoinCondition> list) {
            ChatItemSystemMessageHolder holder = (ChatItemSystemMessageHolder) view.getTag();
            ChatValue chat = (ChatValue) chatListItemData.getData();
            ChatListUtil.unreadMark(holder.readMark, chatListItemData.getIsWithReadMark(), chatListItemData.getViewModifier());
            holder.message.setText(ChatListUtil.getEmojiString(context, chatListItemData, chat.getMessage(), true));
            holder.time.setText(TimeUtil.getLongTime(chat.getCreatedDate()));
            if (ChatValue.SYSTEM_CREATED.equals(chat.getType())) {
                holder.createdGroupContainer.setVisibility(0);
                if (chatListItemData.getIsPublic()) {
                    holder.createdGroupInvite.setVisibility(8);
                    return;
                } else {
                    holder.createdGroupInvite.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            ContactListActivity.startContactsListFromChatGroupInfo(chatListItemData.getUserUid(), chatListItemData.getGroupUid());
                        }
                    });
                    return;
                }
            }
            holder.createdGroupContainer.setVisibility(8);
        }
    }

    public static final class ChatListItemData {
        public static final String TYPE_AD = "TYPE_AD";
        public static final String TYPE_CHAT = "TYPE_CHAT";
        public static final String TYPE_SYSTEM = "TYPE_SYSTEM";
        private int mChatMessageMaxWidth;
        private Object mData;
        private String mGid;
        private GroupDetailValue mGroupDetailValue;
        private String mId;
        private boolean mIsDispensable = true;
        private boolean mIsNew;
        private boolean mIsPublic;
        private boolean mIsWithReadMark = false;
        private OnShowLightBox mOnShowLightBox;
        private String mOrder;
        private String mType;
        private String mUid;
        private IViewModifier mViewModifier;

        public ChatListItemData(String id, String order, String type, IViewModifier viewModifier, Object data, GroupDetailValue groupDetailValue, String uid, String gid, boolean isNew, boolean isPublic, int chatMessageMaxWidth) {
            this.mId = id;
            this.mOrder = order;
            this.mType = type;
            this.mViewModifier = viewModifier;
            this.mData = data;
            this.mGroupDetailValue = groupDetailValue;
            this.mUid = uid;
            this.mGid = gid;
            this.mIsNew = isNew;
            this.mIsPublic = isPublic;
            this.mChatMessageMaxWidth = chatMessageMaxWidth;
        }

        public Object getData() {
            return this.mData;
        }

        public String getOrder() {
            return this.mOrder;
        }

        public boolean getIsNew() {
            return this.mIsNew;
        }

        public void setIsNew(boolean isNew) {
            this.mIsNew = isNew;
        }

        public String getGroupUid() {
            return this.mGid;
        }

        public boolean getIsWithReadMark() {
            return this.mIsWithReadMark;
        }

        public void setIsWithReadMark(boolean flag) {
            this.mIsWithReadMark = flag;
        }

        public String getUserUid() {
            return this.mUid;
        }

        public boolean getIsPublic() {
            return this.mIsPublic;
        }

        public GroupDetailValue getGroupDetailValue() {
            return this.mGroupDetailValue;
        }

        public void setOnShowLightBox(OnShowLightBox onShowDialog) {
            this.mOnShowLightBox = onShowDialog;
        }

        public OnShowLightBox getOnShowLightBox() {
            return this.mOnShowLightBox;
        }

        public void setIsDispensable(boolean flag) {
            this.mIsDispensable = flag;
        }

        public boolean getIsDispensable() {
            return this.mIsDispensable;
        }

        public String getType() {
            return this.mType;
        }

        public IViewModifier getViewModifier() {
            return this.mViewModifier;
        }

        public int getChatMessageMaxWidth() {
            return this.mChatMessageMaxWidth;
        }

        public String getId() {
            return this.mId;
        }

        public ChatListItemData clone() {
            return new ChatListItemData(getId(), getOrder(), getType(), getViewModifier(), getData(), getGroupDetailValue(), getUserUid(), getGroupUid(), getIsNew(), getIsPublic(), getChatMessageMaxWidth());
        }
    }

    public static void showTime(Context context, TextView textView, ChatListItemData tag, boolean isShort) {
        textView.setTag(tag);
        showTime(context, textView, (ChatValue) tag.getData(), isShort);
    }

    public static void showTime(Context context, TextView textView, ChatValue chatValue, boolean isShort) {
        if (isShort) {
            textView.setText(TimeUtil.getTimeSpan(context, chatValue.getCreatedDate()));
        } else {
            textView.setText(TimeUtil.getLongTime(chatValue.getCreatedDate()));
        }
    }

    public static void setLink(TextView view) {
        if (view.getUrls().length == 1) {
            final URLSpan link = view.getUrls()[0];
            view.setClickable(true);
            view.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString(PathRouter.PATH, WebViewSetting.PATH_WEBVIEW_SETTINGS);
                    bundle.putString("url", link.getURL());
                    bundle.putString("actionBarTitle", "test");
                    PathRouter.startPath(bundle);
                }
            });
            return;
        }
        view.setOnClickListener(null);
        view.setClickable(false);
    }

    public static CharSequence getEmojiString(Context context, ChatListItemData chatListItemData, String text, boolean addTrip) {
        return getEmojiString(context, (ChatValue) chatListItemData.getData(), text, addTrip, chatListItemData.getIsPublic());
    }

    public static CharSequence getEmojiString(Context context, ChatValue chatValue, String text, boolean addTrip, boolean isPublic) {
        if (!(chatValue == null || chatValue.getUser() == null || !addTrip)) {
            text = text + getTrip(chatValue.getUser().getUid(), isPublic);
        }
        return SDKBridge.getEmoticonSpannedText(context, text);
    }

    private static String getTrip(String userUid, boolean isPublic) {
        if (isPublic) {
            return " (" + userUid.substring(0, 5) + ")";
        }
        return "";
    }

    public static boolean checkValidChatType(String type) {
        return ChatListItemData.TYPE_CHAT.equals(type) || ChatListItemData.TYPE_SYSTEM.equals(type);
    }

    public static boolean checkJoinConditions(Context context, boolean showDialog, List<JoinCondition> conditions, String groupUid) {
        if (conditions == null) {
            Log.i("[community]", "not fetched yet");
            return true;
        }
        List<InstalledParams> notInstalledApps = getNotInstalledApps(conditions);
        if (notInstalledApps.size() == 0) {
            return false;
        }
        final CustomDialog dialog;
        if (TextUtils.isEmpty(((InstalledParams) notInstalledApps.get(0)).getPackageName())) {
            dialog = CustomDialog.createTextDialog(context, context.getString(R.string.lobi_download_to_join_not_supported_message));
            dialog.setPositiveButton(context.getString(R.string.lobi_ok), new OnClickListener() {
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
            return true;
        } else if (!showDialog) {
            return true;
        } else {
            dialog = CustomDialog.createTextDialog(context, context.getString(R.string.lobi_download_to_join_message));
            dialog.setPositiveButton(context.getString(R.string.lobi_download), new OnClickListener() {
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.setNegativeButton(context.getString(17039360), new OnClickListener() {
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
            return true;
        }
    }

    public static List<InstalledParams> getNeededApps(List<JoinCondition> conditions) {
        List<InstalledParams> neededPackages = new ArrayList();
        if (conditions != null) {
            for (JoinCondition cond : conditions) {
                Params params = cond.getParams();
                if (InstalledParams.TYPE.equals(cond.getType())) {
                    neededPackages.add((InstalledParams) params);
                }
            }
        }
        return neededPackages;
    }

    public static List<InstalledParams> getNotInstalledApps(List<JoinCondition> conditions) {
        List<InstalledParams> neededPackages = getNeededApps(conditions);
        Iterator<InstalledParams> iter = neededPackages.iterator();
        while (iter.hasNext()) {
            if (AdUtil.isInstalled(((InstalledParams) iter.next()).getPackageName())) {
                iter.remove();
            }
        }
        return neededPackages;
    }

    public static String getAnalysticToken(List<JoinCondition> conditions) {
        List<InstalledParams> neededPackages = getNeededApps(conditions);
        List<InstalledParams> notInstalledPackages = getNotInstalledApps(conditions);
        if (neededPackages.size() == 0) {
            return null;
        }
        String token;
        if (notInstalledPackages.size() == 0) {
            token = "INSTALLED";
        } else if (TextUtils.isEmpty(((InstalledParams) notInstalledPackages.get(0)).getPackageName())) {
            token = "NOT_SUPPORTED";
        } else {
            token = "NOT_INSTALLED";
        }
        return token + "-" + ((InstalledParams) neededPackages.get(0)).getAppUid();
    }

    public static boolean checkToJoin(GroupDetailValue groupDetail) {
        if (!groupDetail.isPublic() || groupDetail.getType().equals(GroupValue.MINE) || groupDetail.getType().equals("invited")) {
            return true;
        }
        return false;
    }

    public static void goToRepliesFromChat(String chatUid, GroupDetailValue groupDetail, boolean keyboard) {
        Bundle extras = new Bundle();
        extras.putBoolean(ChatReplyActivity.EXTRA_CHAT_REPLY_CHAT_SHOW_KEYBOARD, keyboard);
        extras.putString(PathRouter.PATH, ChatReplyActivity.PATH_CHAT_REPLY);
        goToReply(extras, chatUid, groupDetail);
    }

    public static void goToRepliesFromLikeRanking(String chatUid, GroupDetailValue groupDetail, String firstViewChatId) {
        Bundle extras = new Bundle();
        extras.putBoolean(ChatReplyActivity.EXTRA_CHAT_REPLY_CHAT_SHOW_KEYBOARD, false);
        extras.putString(PathRouter.PATH, ChatReplyActivity.PATH_LIKE_RANKING_REPLY);
        if (firstViewChatId != null) {
            extras.putString(ChatReplyActivity.EXTRA_CHAT_REPLY_FIRST_VIEW_ID, firstViewChatId);
        }
        goToReply(extras, chatUid, groupDetail);
    }

    private static void goToReply(Bundle bundle, String chatUid, GroupDetailValue groupDetail) {
        Bundle extras = new Bundle(bundle);
        extras.putString(ChatReplyActivity.EXTRA_CHAT_REPLY_TO, chatUid);
        extras.putParcelable(ChatReplyActivity.EXTRA_CHAT_REPLY_GROUPDETAIL, groupDetail);
        PathRouter.startPath(extras);
    }

    public static ListPopupWindow moreOptions(Context context, ChatListItemData data) {
        ChatValue chatValue = (ChatValue) data.getData();
        boolean isLeft = !TextUtils.equals(chatValue.getUser().getUid(), data.getUserUid());
        ListPopupWindow moreOptionsMenu = new ListPopupWindow(context);
        moreOptionsMenu.setLayoutId(R.layout.lobi_chat_options_popup_menu);
        moreOptionsMenu.getContentView().findViewById(R.id.lobi_chat_options_popup_like_dislike_container).setVisibility(8);
        moreOptionsMenu.forceWrapContent();
        if (isLeft) {
            reportChat(context, moreOptionsMenu, chatValue.getUser().getUid());
        } else {
            deleteChat(context, moreOptionsMenu, chatValue.getId(), data.getGroupUid());
        }
        return moreOptionsMenu;
    }

    private static void reportChat(final Context context, final ListPopupWindow moreOptionsMenu, final String userUid) {
        View moreOptionsView = moreOptionsMenu.getContentView();
        FrameLayout reportContainer = (FrameLayout) ViewUtils.findViewById(moreOptionsView, R.id.lobi_chat_options_popup_report_cancel_container);
        ((TextView) ViewUtils.findViewById(moreOptionsView, R.id.lobi_chat_options_popup_report_cancel_text)).setText(context.getString(R.string.lobi_accuse));
        reportContainer.setOnClickListener(new OnClickListener() {
            final DefaultAPICallback<PostAccusations> mCallback = new DefaultAPICallback<PostAccusations>(context) {
                public void onResponse(PostAccusations t) {
                    super.onResponse(t);
                    if (t.success) {
                        showToast(context.getString(R.string.lobi_reported));
                    }
                }

                void showToast(final String message) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(context, message, 0).show();
                        }
                    });
                }
            };

            public void onClick(View v) {
                moreOptionsMenu.dismiss();
                final View newContent = new EditTextContent(context, context.getString(R.string.lobi_please_enter_your_reason_of_accusing), null, false);
                final CustomDialog dialog = new CustomDialog(context, newContent);
                dialog.setTitle(context.getString(R.string.lobi_please_enter_your_reason_of_accusing));
                dialog.setPositiveButton(context.getString(R.string.lobi_ok), new OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                        v.setVisibility(8);
                        UserValue user = AccountDatastore.getCurrentUser();
                        Map<String, String> params = new HashMap();
                        params.put("token", user.getToken());
                        params.put("user", userUid);
                        params.put(RequestKey.REASON, newContent.getText().toString());
                        CoreAPI.postAccusations(params, AnonymousClass5.this.mCallback);
                    }
                });
                dialog.setNegativeButton(context.getString(R.string.lobi_cancel), new OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                        v.setVisibility(8);
                    }
                });
                dialog.show();
            }
        });
    }

    private static void deleteChat(final Context context, final ListPopupWindow moreOptionsMenu, final String chatUid, final String gid) {
        View moreOptionsView = moreOptionsMenu.getContentView();
        FrameLayout reportContainer = (FrameLayout) ViewUtils.findViewById(moreOptionsView, R.id.lobi_chat_options_popup_report_cancel_container);
        ((TextView) ViewUtils.findViewById(moreOptionsView, R.id.lobi_chat_options_popup_report_cancel_text)).setText(context.getString(R.string.lobi_delete));
        reportContainer.setOnClickListener(new OnClickListener() {
            final DefaultAPICallback<PostGroupChatRemove> callback = new DefaultAPICallback<PostGroupChatRemove>(context) {
            };

            public void onClick(View v) {
                moreOptionsMenu.dismiss();
                final CustomDialog dialog = CustomDialog.createTextDialog(context, context.getString(R.string.lobi_delete_this_chat));
                dialog.setPositiveButton(context.getString(R.string.lobi_delete), new OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                        UserValue user = AccountDatastore.getCurrentUser();
                        Map<String, String> params = new HashMap();
                        params.put("token", user.getToken());
                        params.put("uid", gid);
                        params.put("id", chatUid);
                        CoreAPI.postGroupChatRemove(params, AnonymousClass6.this.callback);
                    }
                });
                dialog.setNegativeButton(context.getString(R.string.lobi_cancel), new OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    public static void setupBasicView(Context context, final ChatListAdapter adapter, final ChatListItemData data, ImageLoaderCircleView icon, TextView userName, TextView time) {
        boolean z = true;
        ChatValue chat = (ChatValue) data.getData();
        icon.loadImage(chat.getUser().getIcon());
        icon.setTag(chat);
        icon.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                UserValue user = ((ChatValue) v.getTag()).getUser();
                if (user != null) {
                    ChatSDKModuleBridge.startChatProfileFromGroup(user, data.getGroupUid());
                }
            }
        });
        userName.setText(getEmojiString(context, data, chat.getUser().getName(), true));
        setLayoutParams(userName, -2, -2);
        if (iTimeTypeFormat != 0) {
            z = false;
        }
        showTime(context, time, data, z);
        time.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                int i;
                boolean isshort;
                if (ChatListUtil.iTimeTypeFormat == 0) {
                    i = 1;
                } else {
                    i = 0;
                }
                ChatListUtil.iTimeTypeFormat = i;
                TextView textView = (TextView) v;
                ChatListItemData tag = (ChatListItemData) v.getTag();
                if (ChatListUtil.iTimeTypeFormat == 0) {
                    isshort = true;
                } else {
                    isshort = false;
                }
                ChatListUtil.showTime(v.getContext(), textView, tag, isshort);
                adapter.notifyDataSetChanged();
            }
        });
    }

    public static void setLayoutParams(View view, int width, int height) {
        LayoutParams params = view.getLayoutParams();
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);
    }

    public static void setupChat(Context context, View view, ChatListItemData data, CustomTextView chatMessage, ImageLoaderView picture, FrameLayout pictureFrame, LinearLayout pictureDescription, TextView pictureCount, TextView userName, List<JoinCondition> joinConditions) {
        ChatValue chat = (ChatValue) data.getData();
        ChatListItemData pictureData = (ChatListItemData) picture.getTag();
        boolean isDispensable = pictureData != null ? pictureData.getIsDispensable() : true;
        if (isDispensable) {
            picture.setTag(data);
        }
        String image = chat.getImage();
        List<AssetValue> assets = chat.getAssets();
        if (assets.size() <= 0 && TextUtils.isEmpty(image)) {
            pictureFrame.setVisibility(8);
        } else if (isDispensable) {
            setDefaultImagePorperties(context, picture, pictureFrame, pictureDescription, pictureCount, assets.size());
            picture.loadImage(image.replace("_100.", "_raw."));
        }
        chatMessage.setVisibility(0);
        setChatMessage(context, chatMessage, data);
        data.getViewModifier().setupChatView(view, data, data.getUserUid(), data.getGroupUid(), data.getGroupDetailValue(), iTimeTypeFormat == 0, joinConditions);
    }

    public static void setChatMessage(Context context, CustomTextView chatMessage, ChatListItemData data) {
        setChatMessage(context, chatMessage, (ChatValue) data.getData(), data.getIsPublic());
    }

    public static void setChatMessage(Context context, CustomTextView chatMessage, ChatValue chatValue, boolean isPublicGroup) {
        CharSequence emojiMessage = getEmojiString(context, chatValue, chatValue.getMessage(), false, isPublicGroup);
        if (TextUtils.isEmpty(emojiMessage)) {
            chatMessage.setVisibility(8);
            return;
        }
        chatMessage.setVisibility(0);
        chatMessage.setText(emojiMessage);
    }

    public static void setDefaultImagePorperties(Context context, ImageLoaderView picture, FrameLayout pictureFrame, LinearLayout pictureDescription, TextView pictureCount, int assetsSize) {
        picture.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ChatListItemData data = (ChatListItemData) v.getTag();
                ChatValue chatValue = (ChatValue) data.getData();
                data.setIsDispensable(false);
                ImageView imageView = (ImageView) v;
                if (data.getOnShowLightBox() != null) {
                    data.getOnShowLightBox().onShowLightBox(imageView);
                }
            }
        });
        setLayoutPictureContent(context, picture, pictureFrame, pictureDescription, pictureCount, assetsSize);
    }

    public static void setLayoutPictureContent(Context context, ImageLoaderView picture, FrameLayout pictureFrame, LinearLayout pictureDescription, TextView pictureCount, int assetsSize) {
        pictureFrame.setVisibility(0);
        Resources resources = context.getResources();
        if (assetsSize > 1) {
            pictureDescription.setVisibility(0);
            pictureCount.setText(Integer.toString(assetsSize));
            if (assetsSize > 50) {
                pictureFrame.setBackgroundResource(R.drawable.lobi_chat_bg_media_03);
            } else if (assetsSize > 10) {
                pictureFrame.setBackgroundResource(R.drawable.lobi_chat_bg_media_02);
            } else {
                pictureFrame.setBackgroundResource(R.drawable.lobi_chat_bg_media_01);
            }
        } else {
            pictureDescription.setVisibility(8);
            pictureFrame.setBackgroundResource(R.drawable.lobi_chat_bg_media_00);
        }
        DisplayMetrics metrics = resources.getDisplayMetrics();
        int maxWidth = metrics.widthPixels;
        int maxHeight = metrics.heightPixels;
        picture.setBaseWidth(maxWidth);
        picture.setBaseHeigth(maxHeight);
        picture.setScaleType(ScaleType.CENTER_CROP);
        setLayoutParams(picture, resources.getDimensionPixelSize(R.dimen.lobi_chat_gallery_thumb_width), resources.getDimensionPixelSize(R.dimen.lobi_chat_gallery_thumb_height));
        setLayoutParams(pictureFrame, resources.getDimensionPixelSize(R.dimen.lobi_chat_gallery_thumb_frame_width), resources.getDimensionPixelSize(R.dimen.lobi_chat_gallery_thumb_frame_height));
        Log.v("nakamap", String.format("[picture] %d, %d - %d, %d", new Object[]{Integer.valueOf(pictureFrame.getLeft()), Integer.valueOf(pictureFrame.getTop()), Integer.valueOf(pictureFrame.getRight()), Integer.valueOf(pictureFrame.getBottom())}));
        picture.setAdjustViewBounds(false);
    }

    public static void addReply(ChatValue parent, ChatValue reply) {
        if (parent.getReplies() != null) {
            List<ChatValue> replies = parent.getReplies().getChats();
            boolean duplicated = true;
            if (replies.size() == 0) {
                duplicated = false;
            } else {
                ChatValue latestReply = (ChatValue) replies.get(replies.size() - 1);
                if (latestReply.getCreatedDate() <= reply.getCreatedDate() && !latestReply.getId().equals(reply.getId())) {
                    duplicated = false;
                }
            }
            if (!duplicated) {
                if (replies.size() >= 2) {
                    replies.remove(0);
                }
                replies.add(reply);
            }
        }
    }

    public static void displayStamp(Context context, ChatListItemData data, CustomTextView chatMessage, ImageLoaderView picture, FrameLayout pictureFrame, LinearLayout pictureDescription) {
        ChatValue chatValue = (ChatValue) data.getData();
        chatMessage.setOnClickListener(null);
        chatMessage.setVisibility(8);
        picture.setVisibility(0);
        ChatListItemData pictureData = (ChatListItemData) picture.getTag();
        if (pictureData != null ? pictureData.getIsDispensable() : true) {
            picture.setTag(data);
            setStampImagePorperties(context, picture, pictureFrame, pictureDescription);
            picture.loadImage(chatValue.getImage());
        }
    }

    public static void setStampImagePorperties(Context context, ImageLoaderView picture, FrameLayout pictureFrame, LinearLayout pictureDescription) {
        picture.setBaseWidth(0);
        picture.setBaseHeigth(0);
        pictureFrame.setVisibility(0);
        pictureDescription.setVisibility(8);
        picture.setOnLongClickListener(null);
        int stampSize = context.getResources().getDimensionPixelSize(R.dimen.lobi_stamp_chat_height);
        MarginLayoutParams params = (MarginLayoutParams) pictureFrame.getLayoutParams();
        MarginLayoutParams paramsPicture = (MarginLayoutParams) picture.getLayoutParams();
        params.width = stampSize;
        params.height = stampSize;
        paramsPicture.width = stampSize;
        paramsPicture.height = stampSize;
        picture.setAdjustViewBounds(true);
        pictureFrame.setLayoutParams(params);
        picture.setLayoutParams(paramsPicture);
        pictureFrame.setBackgroundColor(0);
    }

    public static void unreadMark(View view, boolean isWithReadMark, IViewModifier viewModifier) {
        viewModifier.displayReadMark(view, isWithReadMark ? 0 : 8);
    }

    public static boolean isReplySelf(ChatValue chatValue, String myUserId) {
        if (chatValue == null || myUserId == null) {
            Log.w("ChatListUtil", "chatValue and myUserId required.");
            return false;
        }
        Replies replies = chatValue.getReplies();
        if (replies.getChats().size() <= 0) {
            return false;
        }
        for (ChatValue replyChatValue : replies.getChats()) {
            if (TextUtils.equals(replyChatValue.getUser().getUid(), myUserId)) {
                return true;
            }
        }
        return false;
    }

    public static void setupChatOptionButton(ChatOptionButton button, int textCount, boolean isHighlight) {
        if (button == null) {
            throw new IllegalArgumentException("button is null param.");
        } else if (textCount < 0) {
            throw new IllegalArgumentException("textCount is minus value. -> " + textCount);
        } else {
            button.setTextCount(textCount);
            button.setIconHighlight(isHighlight);
        }
    }

    public static void savePhotoToGallery(final Activity activity, final ImageView imageView) {
        if (activity == null || imageView == null) {
            throw new IllegalArgumentException("activity and imageView required param.");
        }
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(new Runnable() {
            public void run() {
                GalleryUtil.saveImageToLocal(activity, ((BitmapDrawable) imageView.getDrawable()).getBitmap());
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(imageView.getContext(), activity.getString(R.string.lobi_saved_to), 0).show();
                    }
                });
                executor.shutdown();
            }
        });
    }
}
