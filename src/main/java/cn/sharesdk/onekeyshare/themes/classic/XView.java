package cn.sharesdk.onekeyshare.themes.classic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class XView extends View {
    private float ratio;

    public XView(Context context) {
        super(context);
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }

    protected void onDraw(Canvas canvas) {
        int width = getWidth() / 2;
        int height = getHeight() / 2;
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(-6250336);
        canvas.drawRect((float) width, 0.0f, (float) getWidth(), (float) height, paint);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(3.0f * this.ratio);
        paint.setColor(-1);
        float left = 8.0f * this.ratio;
        canvas.drawLine(((float) width) + left, left, ((float) getWidth()) - left, ((float) width) - left, paint);
        canvas.drawLine(((float) width) + left, ((float) width) - left, ((float) getWidth()) - left, left, paint);
    }
}
