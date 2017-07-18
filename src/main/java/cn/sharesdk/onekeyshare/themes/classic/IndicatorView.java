package cn.sharesdk.onekeyshare.themes.classic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class IndicatorView extends View {
    private static final int DESIGN_BOTTOM_HEIGHT = 52;
    private static final int DESIGN_INDICATOR_DISTANCE = 14;
    private static final int DESIGN_INDICATOR_RADIUS = 6;
    private int count;
    private int current;

    public IndicatorView(Context context) {
        super(context);
    }

    public void setScreenCount(int count) {
        this.count = count;
    }

    public void onScreenChange(int currentScreen, int lastScreen) {
        if (currentScreen != this.current) {
            this.current = currentScreen;
            postInvalidate();
        }
    }

    protected void onDraw(Canvas canvas) {
        if (this.count <= 1) {
            setVisibility(8);
            return;
        }
        float height = (float) getHeight();
        float radius = (6.0f * height) / 52.0f;
        float distance = (14.0f * height) / 52.0f;
        float left = (((float) getWidth()) - (((radius * 2.0f) * ((float) this.count)) + (((float) (this.count - 1)) * distance))) / 2.0f;
        float cy = height / 2.0f;
        canvas.drawColor(-1);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        for (int i = 0; i < this.count; i++) {
            if (i == this.current) {
                paint.setColor(-10653280);
            } else {
                paint.setColor(-5262921);
            }
            canvas.drawCircle(left + (((radius * 2.0f) + distance) * ((float) i)), cy, radius, paint);
        }
    }
}
