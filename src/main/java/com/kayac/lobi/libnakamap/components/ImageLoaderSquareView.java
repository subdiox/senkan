package com.kayac.lobi.libnakamap.components;

import android.content.Context;
import android.util.AttributeSet;

public class ImageLoaderSquareView extends ImageLoaderView {
    public ImageLoaderSquareView(Context context) {
        super(context);
    }

    public ImageLoaderSquareView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        setMeasuredDimension(width, width);
    }
}
