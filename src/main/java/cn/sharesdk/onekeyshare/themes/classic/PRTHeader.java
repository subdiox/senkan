package cn.sharesdk.onekeyshare.themes.classic;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.mob.tools.utils.R;

public class PRTHeader extends LinearLayout {
    private static final int DESIGN_AVATAR_PADDING = 24;
    private static final int DESIGN_AVATAR_WIDTH = 64;
    private static final int DESIGN_SCREEN_WIDTH = 720;
    private RotateImageView ivArrow;
    private ProgressBar pbRefreshing;
    private TextView tvHeader;

    public PRTHeader(Context context) {
        super(context);
        int[] size = R.getScreenSize(context);
        float ratio = ((float) (size[0] < size[1] ? size[0] : size[1])) / 720.0f;
        setOrientation(1);
        LinearLayout llInner = new LinearLayout(context);
        LayoutParams lp = new LayoutParams(-2, -2);
        lp.gravity = 1;
        addView(llInner, lp);
        this.ivArrow = new RotateImageView(context);
        int resId = R.getBitmapRes(context, "ssdk_oks_ptr_ptr");
        if (resId > 0) {
            this.ivArrow.setImageResource(resId);
        }
        int avatarWidth = (int) (64.0f * ratio);
        lp = new LayoutParams(avatarWidth, avatarWidth);
        lp.gravity = 16;
        int avataPadding = (int) (24.0f * ratio);
        lp.bottomMargin = avataPadding;
        lp.topMargin = avataPadding;
        llInner.addView(this.ivArrow, lp);
        this.pbRefreshing = new ProgressBar(context);
        this.pbRefreshing.setIndeterminateDrawable(context.getResources().getDrawable(R.getBitmapRes(context, "ssdk_oks_classic_progressbar")));
        llInner.addView(this.pbRefreshing, lp);
        this.pbRefreshing.setVisibility(8);
        this.tvHeader = new TextView(getContext());
        this.tvHeader.setTextSize(2, 18.0f);
        this.tvHeader.setPadding(avataPadding, 0, avataPadding, 0);
        this.tvHeader.setTextColor(-16139513);
        lp = new LayoutParams(-2, -2);
        lp.gravity = 16;
        llInner.addView(this.tvHeader, lp);
    }

    public void onPullDown(int percent) {
        if (percent > 100) {
            int degree = ((percent - 100) * 180) / 20;
            if (degree > 180) {
                degree = 180;
            }
            if (degree < 0) {
                degree = 0;
            }
            this.ivArrow.setRotation((float) degree);
        } else {
            this.ivArrow.setRotation(0.0f);
        }
        int resId;
        if (percent < 100) {
            resId = R.getStringRes(getContext(), "ssdk_oks_pull_to_refresh");
            if (resId > 0) {
                this.tvHeader.setText(resId);
                return;
            }
            return;
        }
        resId = R.getStringRes(getContext(), "ssdk_oks_release_to_refresh");
        if (resId > 0) {
            this.tvHeader.setText(resId);
        }
    }

    public void onRequest() {
        this.ivArrow.setVisibility(8);
        this.pbRefreshing.setVisibility(0);
        int resId = R.getStringRes(getContext(), "ssdk_oks_refreshing");
        if (resId > 0) {
            this.tvHeader.setText(resId);
        }
    }

    public void reverse() {
        this.pbRefreshing.setVisibility(8);
        this.ivArrow.setRotation(180.0f);
        this.ivArrow.setVisibility(0);
    }
}
