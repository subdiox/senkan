package com.kayac.lobi.sdk.chat.activity;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.kayac.lobi.libnakamap.components.FramedImageLoader;
import com.kayac.lobi.libnakamap.components.ImageLoaderCircleView;
import com.kayac.lobi.libnakamap.utils.ViewUtils;
import com.kayac.lobi.libnakamap.value.GroupValue;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.sdk.R;
import java.util.ArrayList;
import java.util.List;

public class ChatListHeaderComponent extends LinearLayout {
    private Context mContext;
    private final TextView mGroupDescription;
    private final FramedImageLoader mGroupIcon;
    private int mHeight;
    private LinearLayout mMainHolder;
    private final List<UserValue> mMembers;
    private final LinearLayout mMembersContainer;

    public ChatListHeaderComponent(Context context) {
        this(context, null);
    }

    public ChatListHeaderComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mMembers = new ArrayList();
        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.lobi_chat_list_header_layout, this);
        this.mGroupIcon = (FramedImageLoader) ViewUtils.findViewById(this, R.id.lobi_chat_list_header_group_icon);
        this.mGroupDescription = (TextView) ViewUtils.findViewById(this, R.id.lobi_chat_list_members_description);
        this.mMembersContainer = (LinearLayout) ViewUtils.findViewById(this, R.id.lobi_chat_list_members_container);
        this.mMainHolder = (LinearLayout) ViewUtils.findViewById(this, R.id.lobi_chat_header_holder);
    }

    public void setGroup(GroupValue value) {
        this.mGroupIcon.loadImage(value.getIcon(), 128);
        this.mGroupDescription.setText(value.getDescription().length() == 0 ? this.mContext.getString(R.string.lobi_key_grp_description) : value.getDescription());
        this.mMembers.clear();
        this.mMembers.addAll(value.getMembers());
        renderMembers();
    }

    public void renderMembers() {
        if (this.mMembers.size() != 0) {
            Context context = getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            Resources res = context.getResources();
            int count = Math.min(calculateMembersCount(res, res.getDisplayMetrics().widthPixels), this.mMembers.size());
            int iconSize = res.getDimensionPixelSize(R.dimen.lobi_chat_members_header_member_icon_size);
            int iconMargin = res.getDimensionPixelSize(R.dimen.lobi_chat_members_header_member_icon_margin);
            this.mMembersContainer.removeAllViews();
            for (int i = 0; i < count; i++) {
                ImageLoaderCircleView memberIcon = (ImageLoaderCircleView) inflater.inflate(R.layout.lobi_chat_list_header_member_icon, null);
                memberIcon.loadImage(((UserValue) this.mMembers.get(i)).getIcon());
                LayoutParams params = new LayoutParams(iconSize, iconSize);
                params.rightMargin = iconMargin;
                params.gravity = 80;
                this.mMembersContainer.addView(memberIcon, params);
            }
        }
    }

    public static int calculateMembersCount(Resources res, int size) {
        return (((size - res.getDimensionPixelSize(R.dimen.lobi_chat_members_header_group_icon_size)) - (res.getDimensionPixelSize(R.dimen.lobi_chat_members_header_margin) * 2)) - (res.getDimensionPixelSize(R.dimen.lobi_chat_members_header_padding) * 2)) / (res.getDimensionPixelSize(R.dimen.lobi_chat_members_header_member_icon_margin) + res.getDimensionPixelSize(R.dimen.lobi_chat_members_header_member_icon_size));
    }

    public void hide() {
        LayoutParams params = (LayoutParams) this.mMainHolder.getLayoutParams();
        this.mHeight = params.height;
        params.height = 0;
        this.mMainHolder.setLayoutParams(params);
    }

    public View getHideView() {
        return this.mMainHolder;
    }

    public int getHideHeight() {
        return this.mHeight;
    }

    public void setDescrription(String description) {
        this.mGroupDescription.setText(description);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mMembersContainer.removeAllViews();
        removeAllViews();
    }
}
