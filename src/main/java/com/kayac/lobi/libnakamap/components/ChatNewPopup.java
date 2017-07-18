package com.kayac.lobi.libnakamap.components;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.kayac.lobi.libnakamap.value.ChatValue;
import com.kayac.lobi.sdk.R;
import com.kayac.lobi.sdk.utils.EmoticonUtil;
import java.util.ArrayList;

public class ChatNewPopup extends FrameLayout {
    private static final int DISPLAY_MSEC = 5000;
    private ArrayList<ChatValue> chats;
    private Animation mAnimFadeIn;
    private Animation mAnimFadeOut;
    private Animation mAnimMessageFadeIn;
    private Animation mAnimMessageFadeOut;
    private Handler mHandler;
    private boolean mIsDisplayed;
    private String mLatestChatId;
    private View mMessageBox;
    private TextView mName;
    private Runnable mNextTask;
    private TextView mText;

    public ChatNewPopup(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mIsDisplayed = false;
        this.mLatestChatId = "";
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mNextTask = new Runnable() {
            public void run() {
                ChatNewPopup.this.hideText();
            }
        };
        ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.lobi_chat_new_popup, this, true);
        this.mMessageBox = findViewById(R.id.lobi_chat_new_message);
        this.mName = (TextView) findViewById(R.id.lobi_chat_new_message_name);
        this.mText = (TextView) findViewById(R.id.lobi_chat_new_message_text);
        this.chats = new ArrayList();
        this.mAnimFadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.lobi_chat_new_popup_fade_in);
        this.mAnimFadeOut = AnimationUtils.loadAnimation(getContext(), R.anim.lobi_chat_new_popup_fade_out);
        this.mAnimFadeOut.setAnimationListener(new AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                ChatNewPopup.this.setVisibility(8);
            }
        });
        this.mAnimMessageFadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.lobi_chat_new_popup_message_fade_in);
        this.mAnimMessageFadeOut = AnimationUtils.loadAnimation(getContext(), R.anim.lobi_chat_new_popup_message_fade_out);
        this.mAnimMessageFadeOut.setAnimationListener(new AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                ChatNewPopup.this.mMessageBox.setVisibility(4);
                ChatNewPopup.this.next();
            }
        });
    }

    public ChatNewPopup(Context context) {
        this(context, null);
    }

    public void show(ChatValue chat) {
        String chatId = chat.getId();
        if (chatId.compareTo(this.mLatestChatId) > 0) {
            this.mLatestChatId = chatId;
            this.chats.add(chat);
            if (!this.mIsDisplayed) {
                this.mIsDisplayed = true;
                setVisibility(0);
                startAnimation(this.mAnimFadeIn);
                next();
            }
        }
    }

    private void hideText() {
        this.mMessageBox.startAnimation(this.mAnimMessageFadeOut);
    }

    private void next() {
        if (!this.mIsDisplayed) {
            return;
        }
        if (this.chats.size() == 0) {
            hide();
            return;
        }
        ChatValue chat = (ChatValue) this.chats.remove(0);
        this.mName.setText(EmoticonUtil.getEmoticonSpannedText(getContext(), chat.getUser().getName()));
        this.mText.setText(EmoticonUtil.getEmoticonSpannedText(getContext(), getMessage(chat)));
        this.mMessageBox.startAnimation(this.mAnimMessageFadeIn);
        this.mMessageBox.setVisibility(0);
        this.mHandler.removeCallbacks(this.mNextTask);
        this.mHandler.postDelayed(this.mNextTask, 5000);
    }

    public void hide() {
        this.chats.clear();
        if (this.mIsDisplayed) {
            this.mIsDisplayed = false;
            startAnimation(this.mAnimFadeOut);
        }
    }

    private final String getMessage(ChatValue chat) {
        boolean hasMessage;
        boolean hasImage = false;
        if (TextUtils.isEmpty(chat.getMessage())) {
            hasMessage = false;
        } else {
            hasMessage = true;
        }
        if (hasMessage) {
            return chat.getMessage();
        }
        boolean hasStamp;
        if (TextUtils.isEmpty(chat.getStampUid())) {
            hasStamp = false;
        } else {
            hasStamp = true;
        }
        if (hasStamp) {
            return getResources().getString(R.string.lobi_sent_stamp);
        }
        if (!TextUtils.isEmpty(chat.getImage()) || chat.getAssets().size() > 0) {
            hasImage = true;
        }
        if (hasImage) {
            return getResources().getString(R.string.lobi_sent_image);
        }
        return "";
    }
}
