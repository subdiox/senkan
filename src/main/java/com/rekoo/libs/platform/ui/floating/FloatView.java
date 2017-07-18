package com.rekoo.libs.platform.ui.floating;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import com.rekoo.libs.utils.ResUtils;
import java.util.Timer;
import java.util.TimerTask;

public class FloatView extends ImageView {
    private static final int FREQUENCY = 16;
    private static final int HIDE = 1;
    private static final int KEEP_TO_SIDE = 0;
    private static final int MOVE_SLOWLY = 2;
    private boolean canClick = true;
    private Context context;
    private int count;
    private int defaultResource;
    private float distance;
    private Handler handler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 2:
                    if (FloatView.this.j == FloatView.this.count + 1) {
                        FloatView.this.canClick = true;
                    }
                    FloatView.this.count = (int) ((((float) (FloatView.this.step * 2)) * Math.abs(FloatView.this.distance)) / ((float) FloatView.this.screenWidth));
                    if (FloatView.this.j <= FloatView.this.count) {
                        FloatView.this.windowManagerParams.x = (int) (FloatView.this.xStart - ((((float) FloatView.this.j) * FloatView.this.distance) / ((float) FloatView.this.count)));
                        FloatView.this.windowManager.updateViewLayout(FloatView.this.image, FloatView.this.windowManagerParams);
                        FloatView floatView = FloatView.this;
                        floatView.j = floatView.j + 1;
                        FloatView.this.handler.postDelayed(new Runnable() {
                            public void run() {
                                FloatView.this.handler.sendEmptyMessage(2);
                            }
                        }, 16);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    };
    private View image;
    private boolean isCancel;
    private boolean isHide;
    private boolean isMove = false;
    private boolean isRight = false;
    private boolean isSecondCancel;
    private boolean isTouch = false;
    private boolean isUp;
    private int j;
    private NoDuplicateClickListener mClickListener;
    private PreferebceManager mPreferenceManager = null;
    private float mTouchX;
    private float mTouchY;
    private float mX;
    private int screenWidth;
    private TimerTask secondTask;
    private Timer secondTimer;
    private int statusBarHeight;
    private int step;
    private Timer timer;
    private TimerTask timerTask;
    private WindowManager windowManager;
    private LayoutParams windowManagerParams;
    private float x;
    private float xStart;
    private float y;

    public FloatView(Context context, LayoutParams windowManagerParams, WindowManager windowManager) {
        super(context);
        this.context = context;
        this.image = this;
        this.windowManager = windowManager;
        this.defaultResource = ResUtils.getDrawable("float_image", context);
        this.windowManagerParams = windowManagerParams;
        this.isMove = false;
        if (context.getResources().getConfiguration().orientation == 2) {
            this.step = 20;
        } else if (context.getResources().getConfiguration().orientation == 1) {
            this.step = 12;
        }
        this.statusBarHeight = getStatusHeight(context);
        this.screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        this.mPreferenceManager = new PreferebceManager(context);
        windowManagerParams.type = 99;
        windowManagerParams.format = 1;
        windowManagerParams.flags = ((windowManagerParams.flags | 8) | 32) | 1024;
        windowManagerParams.gravity = 51;
        windowManagerParams.x = (int) this.mPreferenceManager.getFloatX();
        windowManagerParams.y = (int) this.mPreferenceManager.getFloatY();
        windowManagerParams.width = -2;
        windowManagerParams.height = -2;
        this.isRight = this.mPreferenceManager.isDisplayRight();
        setImageResource(this.defaultResource);
    }

    public boolean onTouchEvent(MotionEvent event) {
        this.isTouch = true;
        this.isUp = false;
        this.xStart = 0.0f;
        this.x = event.getRawX();
        this.y = event.getRawY();
        Log.i("tag", "currX" + this.x + "====currY" + this.y);
        switch (event.getAction()) {
            case 0:
                this.isMove = false;
                this.mTouchX = event.getX();
                this.mTouchY = event.getY();
                Log.i("tag", "startX" + this.mTouchX + "====startY" + this.mTouchY);
                break;
            case 1:
                this.isTouch = false;
                float halfScreen = (float) (this.screenWidth / 2);
                if (this.isMove) {
                    this.isUp = true;
                    this.mX = this.mTouchX;
                    this.isMove = false;
                    if (this.x <= halfScreen) {
                        this.xStart = this.x - this.mTouchX;
                        this.x = 0.0f;
                        this.isRight = false;
                    } else {
                        this.xStart = this.x;
                        this.x = (((float) this.screenWidth) + this.mTouchX) + ((float) this.image.getWidth());
                        this.isRight = true;
                    }
                    updateViewPosition();
                    this.mPreferenceManager.setFloatX(this.x);
                    this.mPreferenceManager.setFloatY(this.y - this.mTouchY);
                    this.mPreferenceManager.setDisplayRight(this.isRight);
                } else {
                    setImageResource(this.defaultResource);
                    if (this.mClickListener != null && this.canClick) {
                        this.mClickListener.onClick(this);
                    }
                }
                this.mTouchY = 0.0f;
                this.mTouchX = 0.0f;
                break;
            case 2:
                int xMove = Math.abs((int) (event.getX() - this.mTouchX));
                int yMove = Math.abs((int) (event.getY() - this.mTouchY));
                if (xMove > 5 || yMove > 5) {
                    this.isMove = true;
                    setImageResource(this.defaultResource);
                    updateViewPosition();
                    break;
                }
        }
        return true;
    }

    public boolean isHide() {
        return this.isHide;
    }

    public void setNoDuplicateClickListener(NoDuplicateClickListener l) {
        this.mClickListener = l;
    }

    private void updateViewPosition() {
        if (this.isUp) {
            this.canClick = false;
            this.distance = this.xStart - this.x;
            this.j = 0;
            this.windowManagerParams.y = (int) (this.y - this.mTouchY);
            this.handler.postDelayed(new Runnable() {
                public void run() {
                    FloatView.this.handler.sendEmptyMessage(2);
                }
            }, 16);
            return;
        }
        this.windowManagerParams.x = (int) (this.x - this.mTouchX);
        this.windowManagerParams.y = (int) (this.y - this.mTouchY);
        this.windowManager.updateViewLayout(this, this.windowManagerParams);
    }

    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            statusHeight = context.getResources().getDimensionPixelSize(Integer.parseInt(clazz.getField("status_bar_height").get(clazz.newInstance()).toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }
}
