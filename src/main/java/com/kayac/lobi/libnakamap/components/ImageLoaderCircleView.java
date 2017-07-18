package com.kayac.lobi.libnakamap.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import com.kayac.lobi.sdk.R;

public class ImageLoaderCircleView extends ImageLoaderView {
    public ImageLoaderCircleView(Context context) {
        this(context, null);
    }

    public ImageLoaderCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (VERSION.SDK_INT >= 11) {
            setLayerType(1, null);
        }
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.lobi_ImageLoaderCircleView);
        float borderWidth = a.getDimension(R.styleable.lobi_ImageLoaderCircleView_lobi_borderWidth, 0.0f);
        if (borderWidth != 0.0f) {
            setBackgroundPadding((int) borderWidth);
        }
        a.recycle();
    }

    public void loadImage(String url) {
        super.loadImage(url);
        setDrawDefault(false);
    }

    public void setImageDrawable(Drawable drawable) {
        if (drawable != null) {
            Bitmap sourceBitmap = ((BitmapDrawable) drawable).getBitmap().copy(Config.ARGB_8888, true);
            int oneSideSize = sourceBitmap.getWidth() < sourceBitmap.getHeight() ? sourceBitmap.getWidth() : sourceBitmap.getHeight();
            float radius = (float) (oneSideSize / 2);
            int startX = Math.round((float) ((sourceBitmap.getWidth() - oneSideSize) / 2));
            int startY = Math.round((float) ((sourceBitmap.getHeight() - oneSideSize) / 2));
            Bitmap clipBitmap = Bitmap.createBitmap(oneSideSize, oneSideSize, Config.ARGB_8888);
            Canvas clipCanvas = new Canvas(clipBitmap);
            clipCanvas.drawARGB(0, 0, 0, 0);
            clipCanvas.drawCircle(((float) startX) + radius, ((float) startY) + radius, radius, new Paint(1));
            Bitmap iconBitmap = Bitmap.createBitmap(oneSideSize, oneSideSize, Config.ARGB_8888);
            Canvas canvas = new Canvas(iconBitmap);
            Paint paint = new Paint();
            canvas.drawBitmap(clipBitmap, 0.0f, 0.0f, paint);
            paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
            canvas.drawBitmap(sourceBitmap, new Rect(startX, startY, startX + oneSideSize, startY + oneSideSize), new Rect(0, 0, oneSideSize, oneSideSize), paint);
            clipBitmap.recycle();
            super.setImageDrawable(new BitmapDrawable(getContext().getResources(), iconBitmap));
        }
    }

    public void setBackgroundPadding(int paddingSize) {
        if (paddingSize > 0) {
            setBackgroundResource(R.drawable.lobi_shape_oval_icon_background);
            setPadding(paddingSize, paddingSize, paddingSize, paddingSize);
            return;
        }
        setBackgroundResource(0);
        setPadding(0, 0, 0, 0);
    }
}
