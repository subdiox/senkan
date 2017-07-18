package com.kayac.lobi.libnakamap.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.kayac.lobi.libnakamap.utils.DebugAssert;
import com.kayac.lobi.sdk.R;

public class LobiFollowButton extends FrameLayout {
    public LobiFollowButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.lobi_follow_button, this, true);
    }

    public void setFollowingStatus(boolean isFollowing) {
        Context context = getContext();
        LinearLayout button = (LinearLayout) findViewById(R.id.lobi_follow_button);
        TextView label = (TextView) findViewById(R.id.lobi_follow_button_label);
        ImageView icon = (ImageView) findViewById(R.id.lobi_follow_button_icon);
        DebugAssert.assertNotNull(button);
        DebugAssert.assertNotNull(label);
        DebugAssert.assertNotNull(icon);
        button.setBackgroundResource(isFollowing ? R.drawable.lobi_button_s_green_selector : R.drawable.lobi_button_s_g_white_selector);
        label.setText(context.getString(isFollowing ? R.string.lobisdk_following : R.string.lobisdk_follow));
        label.setTextColor(getResources().getColor(isFollowing ? R.color.lobi_white_true : R.color.lobi_green_groups_visibility));
        icon.setImageResource(isFollowing ? R.drawable.lobi_icn_login_white : R.drawable.lobi_icn_login_green);
    }

    public void setOnClickListener(OnClickListener listener) {
        LinearLayout button = (LinearLayout) findViewById(R.id.lobi_follow_button);
        DebugAssert.assertNotNull(button);
        button.setOnClickListener(listener);
    }
}
