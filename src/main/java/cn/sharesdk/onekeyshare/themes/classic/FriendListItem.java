package cn.sharesdk.onekeyshare.themes.classic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.ViewCompat;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import cn.sharesdk.onekeyshare.themes.classic.FriendAdapter.Following;
import com.mob.tools.gui.AsyncImageView;
import com.mob.tools.gui.BitmapProcessor;
import com.mob.tools.utils.R;

public class FriendListItem extends LinearLayout {
    private static final int DESIGN_AVATAR_PADDING = 24;
    private static final int DESIGN_AVATAR_WIDTH = 64;
    private static final int DESIGN_ITEM_HEIGHT = 96;
    private static final int DESIGN_ITEM_PADDING = 20;
    private AsyncImageView aivIcon;
    private Bitmap bmChd;
    private Bitmap bmUnch;
    private ImageView ivCheck;
    private TextView tvName;

    public FriendListItem(Context context, float ratio) {
        super(context);
        int itemPadding = (int) (20.0f * ratio);
        setPadding(itemPadding, 0, itemPadding, 0);
        setMinimumHeight((int) (96.0f * ratio));
        setBackgroundColor(-1);
        this.ivCheck = new ImageView(context);
        LayoutParams lp = new LayoutParams(-2, -2);
        lp.gravity = 16;
        addView(this.ivCheck, lp);
        this.aivIcon = new AsyncImageView(context);
        int avatarWidth = (int) (64.0f * ratio);
        lp = new LayoutParams(avatarWidth, avatarWidth);
        lp.gravity = 16;
        int avatarMargin = (int) (24.0f * ratio);
        lp.setMargins(avatarMargin, 0, avatarMargin, 0);
        addView(this.aivIcon, lp);
        this.tvName = new TextView(context);
        this.tvName.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        this.tvName.setTextSize(2, 18.0f);
        this.tvName.setSingleLine();
        lp = new LayoutParams(-2, -2);
        lp.gravity = 16;
        lp.weight = 1.0f;
        addView(this.tvName, lp);
        int resId = R.getBitmapRes(context, "ssdk_oks_classic_check_checked");
        if (resId > 0) {
            this.bmChd = BitmapFactory.decodeResource(context.getResources(), resId);
        }
        resId = R.getBitmapRes(getContext(), "ssdk_oks_classic_check_default");
        if (resId > 0) {
            this.bmUnch = BitmapFactory.decodeResource(context.getResources(), resId);
        }
    }

    public void update(Following following, boolean fling) {
        this.tvName.setText(following.screenName);
        this.ivCheck.setImageBitmap(following.checked ? this.bmChd : this.bmUnch);
        if (this.aivIcon == null) {
            return;
        }
        if (fling) {
            Bitmap bm = BitmapProcessor.getBitmapFromCache(following.icon);
            if (bm == null || bm.isRecycled()) {
                this.aivIcon.execute(null, 0);
                return;
            } else {
                this.aivIcon.setImageBitmap(bm);
                return;
            }
        }
        this.aivIcon.execute(following.icon, 0);
    }
}
