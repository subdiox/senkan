package com.kayac.lobi.sdk.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.kayac.lobi.sdk.R;

public class FriendStatusMarkView extends FrameLayout {
    private View mBackGround;
    private ImageView mIcon;
    private TextView mText;

    public FriendStatusMarkView(Context context) {
        this(context, null, 0);
    }

    public FriendStatusMarkView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FriendStatusMarkView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View parent = LayoutInflater.from(context).inflate(R.layout.lobi_friend_status_mark_view, this, true);
        this.mBackGround = parent.findViewById(R.id.lobi_friend_status_mark_background);
        this.mIcon = (ImageView) parent.findViewById(R.id.lobi_friend_status_mark_icon);
        this.mText = (TextView) parent.findViewById(R.id.lobi_friend_status_mark_text);
        setIsFriend(false);
    }

    public void setIsFriend(boolean isFriend) {
        this.mBackGround.setBackgroundResource(isFriend ? R.drawable.lobi_friend_status_on : R.drawable.lobi_friend_status_off);
        this.mIcon.setImageResource(isFriend ? R.drawable.lobi_icn_popup_friends : R.drawable.lobi_icn_popup_notfriends);
        this.mText.setText(isFriend ? R.string.lobi_you_are_friends : R.string.lobi_not_friend);
    }
}
