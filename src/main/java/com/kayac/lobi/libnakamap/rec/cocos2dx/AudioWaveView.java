package com.kayac.lobi.libnakamap.rec.cocos2dx;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class AudioWaveView extends View {
    private static final String TAG = AudioWaveView.class.getSimpleName();
    private int h = 120;
    private short[] mData = new short[4096];
    private int mReadCursor = 0;
    private int mWriteCursor = 0;
    private Paint paint = new Paint();
    private int w = 1024;

    public AudioWaveView(Context context) {
        super(context);
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        this.w = point.x;
        setAlpha(0.8f);
    }

    public void add(short[] sArr) {
        synchronized (this.mData) {
            for (short s : sArr) {
                this.mData[this.mWriteCursor] = s;
                this.mWriteCursor = (this.mWriteCursor + 1) % this.mData.length;
            }
        }
        post(new Runnable() {
            public void run() {
                AudioWaveView.this.invalidate();
            }
        });
    }

    protected void onDraw(Canvas canvas) {
        int i = 0;
        super.onDraw(canvas);
        this.paint.setColor(Color.rgb(0, 0, 0));
        this.paint.setAlpha(120);
        canvas.drawRect(0.0f, 0.0f, (float) this.w, (float) this.h, this.paint);
        this.paint.setColor(Color.rgb(255, 255, 255));
        this.paint.setAlpha(120);
        canvas.drawLine(0.0f, 0.0f, (float) this.w, 0.0f, this.paint);
        canvas.drawLine(0.0f, (float) (this.h / 2), (float) this.w, (float) (this.h / 2), this.paint);
        canvas.drawLine(0.0f, (float) this.h, (float) this.w, (float) this.h, this.paint);
        this.paint.setAlpha(80);
        canvas.drawLine(0.0f, (float) ((this.h * 1) / 4), (float) this.w, (float) ((this.h * 1) / 4), this.paint);
        canvas.drawLine(0.0f, (float) ((this.h * 3) / 4), (float) this.w, (float) ((this.h * 3) / 4), this.paint);
        double length = (1.0d * ((double) this.w)) / ((double) this.mData.length);
        this.paint.setAlpha(255);
        synchronized (this.mData) {
            float f = 0.0f;
            while (i < this.mData.length) {
                short s = this.mData[i];
                float f2 = (((float) s) * 1.0f) / 32767.0f;
                if (s == (short) 0) {
                    this.paint.setColor(Color.rgb(255, 0, 0));
                } else {
                    this.paint.setColor(Color.rgb(0, 255, 255));
                }
                float f3 = (float) (((double) (i + 1)) * length);
                float round = (float) Math.round(((((float) this.h) * 1.0f) / 2.0f) + ((f2 * ((float) this.h)) / 2.0f));
                f2 = (float) (((double) i) * length);
                if (i == 0) {
                    f = round;
                }
                canvas.drawLine(f2, f, f3, round, this.paint);
                i++;
                f = round;
            }
        }
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, 0, 0, this.w, this.h);
    }
}
