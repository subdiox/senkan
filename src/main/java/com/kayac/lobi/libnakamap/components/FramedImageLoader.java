package com.kayac.lobi.libnakamap.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.kayac.lobi.libnakamap.utils.DebugAssert;
import com.kayac.lobi.sdk.R;

public class FramedImageLoader extends FrameLayout {
    private final int mFrameHeight;
    private final int mFrameResourceId;
    private final int mFrameWidth;
    private ImageLoaderView mImageLoaderView;
    private final int mLoaderId;

    public FramedImageLoader(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.lobi_framed_image_loader, this, true);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.lobi_FramedImageLoader);
        this.mFrameResourceId = a.getResourceId(R.styleable.lobi_FramedImageLoader_lobi_frameResourceId, 0);
        this.mLoaderId = a.getResourceId(R.styleable.lobi_FramedImageLoader_lobi_loaderId, 0);
        this.mFrameWidth = a.getDimensionPixelSize(R.styleable.lobi_FramedImageLoader_lobi_frameWidth, 0);
        this.mFrameHeight = a.getDimensionPixelSize(R.styleable.lobi_FramedImageLoader_lobi_frameHeight, 0);
        a.recycle();
        View view = findViewById(R.id.lobi_framed_image_loader_loader);
        this.mImageLoaderView = (ImageLoaderView) view;
        if (this.mLoaderId != 0) {
            view.setId(this.mLoaderId);
        }
        setImageViewSize(this.mImageLoaderView);
        if (this.mFrameResourceId != 0) {
            ImageView imageView = (ImageView) findViewById(R.id.lobi_framed_image_loader_frame);
            setImageViewSize(imageView);
            imageView.setImageResource(this.mFrameResourceId);
        }
    }

    void setImageViewSize(ImageView imageView) {
        if (this.mFrameWidth > 0) {
            LayoutParams params = this.mImageLoaderView.getLayoutParams();
            params.width = this.mFrameWidth;
            params.height = this.mFrameHeight;
            this.mImageLoaderView.setScaleType(ScaleType.FIT_XY);
            this.mImageLoaderView.setLayoutParams(params);
        }
    }

    public ImageLoaderView getImageLoaderView() {
        return this.mImageLoaderView;
    }

    public void loadImage(String url) {
        this.mImageLoaderView.loadImage(url);
    }

    public void loadImage(String url, int size) {
        this.mImageLoaderView.loadImage(url, size);
    }

    public void setFrame(int resId) {
        ImageView imageView = (ImageView) findViewById(R.id.lobi_framed_image_loader_frame);
        DebugAssert.assertNotNull(imageView);
        imageView.setImageResource(resId);
    }

    public void setImageChangeButtonVisible(boolean visible) {
        ImageView imageView = (ImageView) findViewById(R.id.lobi_framed_image_loader_change_button);
        DebugAssert.assertNotNull(imageView);
        imageView.setVisibility(visible ? 0 : 8);
    }

    public View getImageChangeButton() {
        View view = findViewById(R.id.lobi_framed_image_loader_change_button);
        DebugAssert.assertNotNull(view);
        return view;
    }
}
