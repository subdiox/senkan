package com.kayac.lobi.sdk.chat.activity;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.kayac.lobi.libnakamap.components.FramedImageLoader;
import com.kayac.lobi.libnakamap.components.ImageLoaderView;
import com.kayac.lobi.libnakamap.utils.Log;
import com.kayac.lobi.libnakamap.utils.ViewUtils;
import com.kayac.lobi.libnakamap.value.UserValue;
import com.kayac.lobi.sdk.R;
import java.util.ArrayList;
import java.util.List;

public class ChatGroupInfoMembersLayout extends LinearLayout {
    private static final String TAG = "[members]";
    private final LinearLayout mContainer;
    private MemberLoader mMemberLoader;
    private final List<UserValue> mMembers;
    private String mMembersCount;
    private final TextView mNumUsers;

    public interface MemberLoader {
        void load(int i);
    }

    public ChatGroupInfoMembersLayout(Context context) {
        this(context, null);
    }

    public ChatGroupInfoMembersLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mMembers = new ArrayList();
        this.mMembersCount = "";
        LayoutInflater.from(context).inflate(R.layout.lobi_chat_group_info_members_layout, this);
        this.mNumUsers = (TextView) ViewUtils.findViewById(this, R.id.lobi_chat_group_info_members_layout_num_members);
        this.mContainer = (LinearLayout) ViewUtils.findViewById(this, R.id.lobi_chat_group_info_members_layout_container);
    }

    public void setNumberOfUsers(int numUsers) {
        this.mNumUsers.setText(String.valueOf(numUsers));
    }

    public void reload() {
        if (this.mMemberLoader != null) {
            int count = calculateMaximumMemberCount();
            Log.v(TAG, "count: " + count + " size: " + this.mMembers.size());
            if (this.mMembers.size() < count) {
                Log.v(TAG, "size < count");
                this.mMemberLoader.load(count);
                return;
            }
            Log.v(TAG, "size >= count");
            post(new Runnable() {
                public void run() {
                    ChatGroupInfoMembersLayout.this.render();
                }
            });
        }
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed && this.mMemberLoader != null) {
            int count = calculateMaximumMemberCount();
            Log.v(TAG, "count: " + count + " size: " + this.mMembers.size());
            if (this.mMembers.size() < count) {
                Log.v(TAG, "size < count");
                this.mMemberLoader.load(count);
                return;
            }
            Log.v(TAG, "size >= count");
            post(new Runnable() {
                public void run() {
                    ChatGroupInfoMembersLayout.this.render();
                }
            });
        }
    }

    private int calculateMaximumMemberCount() {
        Resources res = getContext().getResources();
        int width = this.mContainer.getRight();
        int leftMargin = res.getDimensionPixelSize(R.dimen.lobi_chat_group_info_members_left_margin);
        int rightMargin = res.getDimensionPixelSize(R.dimen.lobi_chat_group_info_members_right_margin);
        int iconSize = res.getDimensionPixelSize(R.dimen.lobi_chat_group_info_members_icon_size);
        return ((width - leftMargin) - rightMargin) / (res.getDimensionPixelOffset(R.dimen.lobi_chat_group_info_members_icon_margin) + iconSize);
    }

    public void setMemberLoader(MemberLoader loader) {
        this.mMemberLoader = loader;
    }

    public void setMembers(int membersCount, List<UserValue> members) {
        Log.v(TAG, "setMembers");
        this.mMembers.clear();
        this.mMembers.addAll(members);
        this.mMembersCount = String.valueOf(membersCount);
        render();
    }

    private void render() {
        Context context = getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        Resources res = context.getResources();
        int iconSize = res.getDimensionPixelSize(R.dimen.lobi_chat_group_info_members_icon_size);
        int iconMargin = res.getDimensionPixelSize(R.dimen.lobi_padding_middle);
        this.mContainer.removeAllViews();
        int count = Math.min(this.mMembers.size(), calculateMaximumMemberCount());
        for (int i = 0; i < count; i++) {
            FramedImageLoader loader = (FramedImageLoader) inflater.inflate(R.layout.lobi_chat_list_header_member_icon, null);
            ((ImageLoaderView) ViewUtils.findViewById(loader, R.id.lobi_chat_list_header_member_icon)).loadImage(((UserValue) this.mMembers.get(i)).getIcon());
            LayoutParams params = new LayoutParams(iconSize, iconSize);
            params.gravity = 16;
            params.rightMargin = iconMargin;
            this.mContainer.addView(loader, params);
        }
    }
}
