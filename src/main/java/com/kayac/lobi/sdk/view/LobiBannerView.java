package com.kayac.lobi.sdk.view;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.kayac.lobi.libnakamap.components.ImageLoaderView;
import com.kayac.lobi.libnakamap.net.CoreAPI;
import com.kayac.lobi.libnakamap.utils.ModuleUtil;
import com.kayac.lobi.libnakamap.value.ProfileValue;
import com.kayac.lobi.libnakamap.value.StartupConfigValue.CoreConfig.Banner;
import com.kayac.lobi.libnakamap.value.StartupConfigValue.CoreConfig.Game;
import com.kayac.lobi.sdk.R;

public class LobiBannerView extends FrameLayout {
    private TextView mAppNameText;
    private String mAppUrl;
    private ImageLoaderView mBackgroundImage;
    private TextView mDescriptionText;
    private ImageLoaderView mIconImage;
    private int mMaxWidth;
    private String mWebUrl;

    public LobiBannerView(Context context) {
        this(context, null);
    }

    public LobiBannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LobiBannerView(Context context, AttributeSet attrs, int defStyle) {
        View parent;
        super(context, attrs, defStyle);
        this.mMaxWidth = 0;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.lobi_LobiBannerView);
        boolean showTextOnly = a.getBoolean(R.styleable.lobi_LobiBannerView_lobi_showTextOnly, false);
        a.recycle();
        this.mMaxWidth = getResources().getDimensionPixelSize(R.dimen.lobi_lobi_banner_max_width);
        if (showTextOnly) {
            parent = LayoutInflater.from(context).inflate(R.layout.lobi_lobi_banner_text_only, this, true);
            this.mBackgroundImage = (ImageLoaderView) parent.findViewById(R.id.lobi_lobi_banner_background);
            this.mDescriptionText = (TextView) parent.findViewById(R.id.lobi_lobi_banner_description);
            this.mIconImage = null;
            this.mAppNameText = null;
        } else {
            parent = LayoutInflater.from(context).inflate(R.layout.lobi_lobi_banner, this, true);
            this.mBackgroundImage = (ImageLoaderView) parent.findViewById(R.id.lobi_lobi_banner_background);
            this.mIconImage = (ImageLoaderView) parent.findViewById(R.id.lobi_lobi_banner_icon);
            this.mAppNameText = (TextView) parent.findViewById(R.id.lobi_lobi_banner_app_name);
            this.mDescriptionText = (TextView) parent.findViewById(R.id.lobi_lobi_banner_description);
        }
        parent.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                try {
                    if (TextUtils.isEmpty(LobiBannerView.this.mAppUrl)) {
                        throw new ActivityNotFoundException();
                    }
                    Intent nativeAppIntent = new Intent("android.intent.action.VIEW");
                    nativeAppIntent.setData(Uri.parse(LobiBannerView.this.mAppUrl));
                    v.getContext().startActivity(nativeAppIntent);
                } catch (ActivityNotFoundException e) {
                    if (!TextUtils.isEmpty(LobiBannerView.this.mWebUrl)) {
                        Uri uri = Uri.parse(LobiBannerView.this.mWebUrl);
                        if (!uri.getAuthority().equals(CoreAPI.getEndpoint().getAuthority())) {
                            Intent webLobiIntent = new Intent("android.intent.action.VIEW");
                            webLobiIntent.setData(uri);
                            v.getContext().startActivity(webLobiIntent);
                        } else if (ModuleUtil.recIsAvailable()) {
                            ModuleUtil.recOpenLobiPlayActivity(uri);
                        }
                    }
                }
            }
        });
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        if (this.mMaxWidth > 0 && this.mMaxWidth < measuredWidth) {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(this.mMaxWidth, MeasureSpec.getMode(widthMeasureSpec));
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public boolean setBannerInfo(Game game, Banner banner) {
        if (game == null || banner == null) {
            return false;
        }
        String appName = game.appName;
        String icon = game.icon;
        String background = game.background;
        String description = banner.description;
        String webUrl = banner.webUrl;
        String appUrl = banner.appUrl;
        if (TextUtils.isEmpty(appName) || TextUtils.isEmpty(icon) || TextUtils.isEmpty(background) || TextUtils.isEmpty(description)) {
            return false;
        }
        if (TextUtils.isEmpty(webUrl) && TextUtils.isEmpty(appUrl)) {
            return false;
        }
        if (this.mIconImage == null) {
            this.mDescriptionText.setText(description);
            this.mBackgroundImage.loadImage(background);
        } else {
            this.mAppNameText.setText(appName);
            this.mIconImage.loadImage(icon);
            this.mBackgroundImage.loadImage(background);
            this.mDescriptionText.setText(description);
        }
        this.mWebUrl = webUrl;
        this.mAppUrl = appUrl;
        return true;
    }

    public boolean setBannerInfo(ProfileValue.Banner banner) {
        if (banner == null) {
            return false;
        }
        String icon = banner.icon;
        String background = banner.background;
        String description = banner.description;
        String webUrl = banner.webUrl;
        String appUrl = banner.appUrl;
        if (TextUtils.isEmpty(icon) || TextUtils.isEmpty(background) || TextUtils.isEmpty(description)) {
            return false;
        }
        if (TextUtils.isEmpty(webUrl) && TextUtils.isEmpty(appUrl)) {
            return false;
        }
        this.mAppNameText.setText(description);
        this.mAppNameText.setMaxLines(2);
        this.mAppNameText.setSingleLine(false);
        this.mAppNameText.setEllipsize(null);
        this.mIconImage.loadImage(icon);
        this.mBackgroundImage.loadImage(background);
        this.mDescriptionText.setVisibility(8);
        this.mWebUrl = webUrl;
        this.mAppUrl = appUrl;
        return true;
    }
}
