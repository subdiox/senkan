package com.kayac.lobi.libnakamap.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import com.kayac.lobi.sdk.R;

public class RoundCornerOverlayView extends View {
    Bitmap clipBitmap;
    private final int mColor;
    private final float mRadius;
    Paint paint;

    public RoundCornerOverlayView(Context context) {
        this(context, null);
    }

    public RoundCornerOverlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.paint = new Paint();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.lobi_RoundCornerOverlayView);
        this.mRadius = (float) a.getDimensionPixelOffset(R.styleable.lobi_RoundCornerOverlayView_lobi_radius, 0);
        this.mColor = a.getColor(R.styleable.lobi_RoundCornerOverlayView_lobi_color, -1);
        a.recycle();
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.clipBitmap != null && !this.clipBitmap.isRecycled()) {
            canvas.drawBitmap(this.clipBitmap, 0.0f, 0.0f, this.paint);
        }
    }

    protected void onSizeChanged(int width, int height, int oldw, int oldh) {
        super.onSizeChanged(width, height, oldw, oldh);
        if (width != oldw || height != oldh) {
            if (this.clipBitmap != null) {
                this.clipBitmap.recycle();
                this.clipBitmap = null;
            }
            this.clipBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
            Canvas clipCanvas = new Canvas(this.clipBitmap);
            clipCanvas.drawColor(this.mColor);
            Paint paintClip = new Paint(1);
            paintClip.setXfermode(new PorterDuffXfermode(Mode.DST_OUT));
            paintClip.setStyle(Style.FILL);
            paintClip.setColor(-1);
            clipCanvas.drawRoundRect(new RectF(0.0f, 0.0f, (float) this.clipBitmap.getWidth(), (float) this.clipBitmap.getHeight()), this.mRadius, this.mRadius, paintClip);
        }
    }
}
