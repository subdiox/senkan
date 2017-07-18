package cn.sharesdk.onekeyshare.themes.classic.port;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import cn.sharesdk.onekeyshare.OnekeyShareThemeImpl;
import cn.sharesdk.onekeyshare.themes.classic.EditPage;
import cn.sharesdk.onekeyshare.themes.classic.XView;
import com.mob.tools.gui.AsyncImageView;
import com.mob.tools.utils.BitmapHelper;
import com.mob.tools.utils.R;
import java.io.File;

public class EditPagePort extends EditPage {
    private static final int DESIGN_BOTTOM_HEIGHT = 75;
    private static final int DESIGN_LEFT_PADDING = 40;
    private static final int DESIGN_REMOVE_THUMB_HEIGHT = 70;
    private static final int DESIGN_SCREEN_HEIGHT = 1280;
    private static final int DESIGN_THUMB_HEIGHT = 300;
    private static final int DESIGN_TITLE_HEIGHT = 96;

    public EditPagePort(OnekeyShareThemeImpl impl) {
        super(impl);
    }

    public void onCreate() {
        super.onCreate();
        float ratio = ((float) R.getScreenHeight(this.activity)) / 1280.0f;
        this.maxBodyHeight = 0;
        this.llPage = new LinearLayout(this.activity);
        this.llPage.setOrientation(1);
        this.activity.setContentView(this.llPage);
        this.rlTitle = new RelativeLayout(this.activity);
        this.rlTitle.setBackgroundColor(-1644052);
        this.llPage.addView(this.rlTitle, new LayoutParams(-1, (int) (96.0f * ratio)));
        initTitle(this.rlTitle, ratio);
        RelativeLayout rlBody = new RelativeLayout(this.activity);
        rlBody.setBackgroundColor(-1);
        this.llPage.addView(rlBody, new LayoutParams(-1, -2));
        initBody(rlBody, ratio);
        LinearLayout llShadow = new LinearLayout(this.activity);
        llShadow.setOrientation(1);
        rlBody.addView(llShadow, new RelativeLayout.LayoutParams(-1, -2));
        initShadow(llShadow, ratio);
        this.llBottom = new LinearLayout(this.activity);
        this.llBottom.setOrientation(1);
        this.llPage.addView(this.llBottom, new LayoutParams(-1, -2));
        initBottom(this.llBottom, ratio);
    }

    private void initTitle(RelativeLayout rlTitle, float ratio) {
        this.tvCancel = new TextView(this.activity);
        this.tvCancel.setTextColor(-12895429);
        this.tvCancel.setTextSize(2, 18.0f);
        this.tvCancel.setGravity(17);
        int resId = R.getStringRes(this.activity, "ssdk_oks_cancel");
        if (resId > 0) {
            this.tvCancel.setText(resId);
        }
        int padding = (int) (40.0f * ratio);
        this.tvCancel.setPadding(padding, 0, padding, 0);
        rlTitle.addView(this.tvCancel, new RelativeLayout.LayoutParams(-2, -1));
        this.tvCancel.setOnClickListener(this);
        TextView tvTitle = new TextView(this.activity);
        tvTitle.setTextColor(-12895429);
        tvTitle.setTextSize(2, 22.0f);
        tvTitle.setGravity(17);
        resId = R.getStringRes(this.activity, "ssdk_oks_multi_share");
        if (resId > 0) {
            tvTitle.setText(resId);
        }
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(-2, -1);
        lp.addRule(13);
        rlTitle.addView(tvTitle, lp);
        this.tvShare = new TextView(this.activity);
        this.tvShare.setTextColor(-37615);
        this.tvShare.setTextSize(2, 18.0f);
        this.tvShare.setGravity(17);
        resId = R.getStringRes(this.activity, "ssdk_oks_share");
        if (resId > 0) {
            this.tvShare.setText(resId);
        }
        this.tvShare.setPadding(padding, 0, padding, 0);
        lp = new RelativeLayout.LayoutParams(-2, -1);
        lp.addRule(11);
        rlTitle.addView(this.tvShare, lp);
        this.tvShare.setOnClickListener(this);
    }

    private void initBody(RelativeLayout rlBody, float ratio) {
        this.svContent = new ScrollView(this.activity);
        rlBody.addView(this.svContent, new RelativeLayout.LayoutParams(-1, -2));
        LinearLayout llContent = new LinearLayout(this.activity);
        llContent.setOrientation(1);
        this.svContent.addView(llContent, new FrameLayout.LayoutParams(-1, -2));
        this.etContent = new EditText(this.activity);
        int padding = (int) (40.0f * ratio);
        this.etContent.setPadding(padding, padding, padding, padding);
        this.etContent.setBackgroundDrawable(null);
        this.etContent.setTextColor(-12895429);
        this.etContent.setTextSize(2, 21.0f);
        this.etContent.setText(this.sp.getText());
        llContent.addView(this.etContent, new LayoutParams(-1, -2));
        this.etContent.addTextChangedListener(this);
        this.rlThumb = new RelativeLayout(this.activity);
        this.rlThumb.setBackgroundColor(-13553359);
        int thumbWidth = (int) (300.0f * ratio);
        int xWidth = (int) (70.0f * ratio);
        LayoutParams lp = new LayoutParams(thumbWidth, thumbWidth);
        lp.topMargin = padding;
        lp.bottomMargin = padding;
        lp.rightMargin = padding;
        lp.leftMargin = padding;
        llContent.addView(this.rlThumb, lp);
        this.aivThumb = new AsyncImageView(this.activity) {
            public void onImageGot(String url, Bitmap bm) {
                EditPagePort.this.thumb = bm;
                super.onImageGot(url, bm);
            }
        };
        this.aivThumb.setScaleToCropCenter(true);
        this.rlThumb.addView(this.aivThumb, new RelativeLayout.LayoutParams(thumbWidth, thumbWidth));
        this.aivThumb.setOnClickListener(this);
        initThumb(this.aivThumb);
        this.xvRemove = new XView(this.activity);
        this.xvRemove.setRatio(ratio);
        RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(xWidth, xWidth);
        rllp.addRule(10);
        rllp.addRule(11);
        this.rlThumb.addView(this.xvRemove, rllp);
        this.xvRemove.setOnClickListener(this);
    }

    private void initBottom(LinearLayout llBottom, float ratio) {
        LinearLayout llAt = new LinearLayout(this.activity);
        llAt.setPadding(0, 0, 0, 5);
        llAt.setBackgroundColor(-1);
        llBottom.addView(llAt, new LayoutParams(-1, (int) (75.0f * ratio)));
        this.tvAt = new TextView(this.activity);
        this.tvAt.setTextColor(-12895429);
        this.tvAt.setTextSize(2, 22.0f);
        this.tvAt.setGravity(80);
        this.tvAt.setText("@");
        int padding = (int) (40.0f * ratio);
        this.tvAt.setPadding(padding, 0, padding, 0);
        llAt.addView(this.tvAt, new LayoutParams(-2, -1));
        this.tvAt.setOnClickListener(this);
        if (isShowAtUserLayout(this.platform.getName())) {
            this.tvAt.setVisibility(0);
        } else {
            this.tvAt.setVisibility(4);
        }
        this.tvTextCouter = new TextView(this.activity);
        this.tvTextCouter.setTextColor(-12895429);
        this.tvTextCouter.setTextSize(2, 21.0f);
        this.tvTextCouter.setGravity(85);
        onTextChanged(this.etContent.getText(), 0, 0, 0);
        this.tvTextCouter.setPadding(padding, 0, padding, 0);
        LayoutParams lp = new LayoutParams(-2, -1);
        lp.weight = 1.0f;
        llAt.addView(this.tvTextCouter, lp);
        View v = new View(this.activity);
        v.setBackgroundColor(-3355444);
        llBottom.addView(v, new LayoutParams(-1, ratio > 1.0f ? (int) ratio : 1));
    }

    private void initShadow(LinearLayout llShadow, float ratio) {
        LayoutParams lp = new LayoutParams(-1, ratio > 1.0f ? (int) ratio : 1);
        View v = new View(this.activity);
        v.setBackgroundColor(687865856);
        llShadow.addView(v, lp);
        v = new View(this.activity);
        v.setBackgroundColor(335544320);
        llShadow.addView(v, lp);
        v = new View(this.activity);
        v.setBackgroundColor(117440512);
        llShadow.addView(v, lp);
    }

    private void initThumb(AsyncImageView aivThumb) {
        String imageUrl = this.sp.getImageUrl();
        String imagePath = this.sp.getImagePath();
        String[] imageArray = this.sp.getImageArray();
        Bitmap pic = null;
        this.rlThumb.setVisibility(0);
        if (!TextUtils.isEmpty(imagePath) && new File(imagePath).exists()) {
            try {
                pic = BitmapHelper.getBitmap(imagePath);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        if (pic != null) {
            this.thumb = pic;
            aivThumb.setBitmap(pic);
        } else if (imageArray != null && imageArray.length > 0 && !TextUtils.isEmpty(imageArray[0]) && new File(imageArray[0]).exists()) {
            try {
                pic = BitmapHelper.getBitmap(imagePath);
            } catch (Throwable e2) {
                e2.printStackTrace();
            }
        }
        if (pic != null) {
            this.thumb = pic;
            aivThumb.setBitmap(pic);
        } else if (pic != null || TextUtils.isEmpty(imageUrl)) {
            this.rlThumb.setVisibility(8);
        } else {
            aivThumb.execute(imageUrl, 0);
        }
    }
}
