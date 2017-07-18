package com.kayac.lobi.libnakamap.components;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.kayac.lobi.libnakamap.components.ToggleInterface.OnToggleStateChangedListener;
import com.kayac.lobi.libnakamap.utils.Log;
import com.kayac.lobi.sdk.R;

class ToggleOnOffButtonImpl extends LinearLayout implements ToggleInterface {
    private static final int ANIMATION_COUNT = 10;
    private static final int AUTO = 0;
    private static final int OFF = 3;
    private static final int ON = 2;
    private static final int STOP = -1;
    private static final int TOGGLE = 1;
    private int mActionAfter;
    private boolean mAllowToogleState;
    private int mAnimationCount = 0;
    private float mAnimationEnd;
    private float mAnimationStart;
    private Canvas mCanvas;
    private Bitmap mCanvasBitmap;
    private boolean mDown = false;
    private Rect mHitArea;
    private boolean mIsAnimating;
    private boolean mIsDragging;
    private boolean mIsEnabled;
    private float mLeftMargin;
    private OnToggleStateChangedListener mListener;
    private Bitmap mMaskBitmap;
    private float mMaskWidth;
    private Paint mPaint = new Paint(5);
    private float mPosition = 0.0f;
    private float mPositionStart;
    private int mPositionX = 0;
    private int mPositionY = 0;
    private float mPrevX;
    private float mStartX;
    private Bitmap mSwitchBitmap;
    private float mSwitchMoveWitdh = 0.0f;
    private float mSwitchMoveWitdhX = 0.0f;
    private int mSwitchWidth;
    private int mSwitchstate;
    private boolean mToggleState;
    private float mTopMargin;
    private float mVelocity;
    private Xfermode mXferMode = new PorterDuffXfermode(Mode.DST_IN);

    public void setOn() {
        setOn(false);
    }

    public void setOff() {
        setOff(false);
    }

    public void setOn(boolean toggleState) {
        this.mSwitchstate = 2;
        this.mActionAfter = 0;
        this.mToggleState = true;
        this.mAllowToogleState = toggleState;
    }

    public void setOff(boolean toggleState) {
        this.mSwitchstate = 3;
        this.mToggleState = false;
        this.mActionAfter = 0;
        this.mAllowToogleState = toggleState;
    }

    public void setState(boolean toggleState) {
        this.mSwitchstate = toggleState ? 2 : 3;
        this.mToggleState = toggleState;
        this.mActionAfter = -1;
        this.mAllowToogleState = false;
    }

    public void rollBack() {
        if (this.mToggleState) {
            setOff();
        } else {
            setOn();
        }
    }

    public boolean getState() {
        return this.mToggleState;
    }

    public void alwaysOn() {
        this.mSwitchstate = 2;
        this.mActionAfter = 2;
    }

    public int getButtonWidth() {
        return (int) this.mMaskWidth;
    }

    public ToggleOnOffButtonImpl(Context context, AttributeSet attrs) {
        boolean state = true;
        super(context, attrs);
        setLayoutParams(new LayoutParams(context.getResources().getDimensionPixelSize(R.dimen.lobi_toggle_on_off_width), -2));
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.lobi_ToggleOnOffButton);
        if (typedArray.getBoolean(R.styleable.lobi_ToggleOnOffButton_lobi_isOff, true)) {
            state = false;
        }
        typedArray.recycle();
        Resources res = context.getResources();
        this.mSwitchBitmap = BitmapFactory.decodeResource(res, R.drawable.lobi_switch);
        this.mMaskBitmap = BitmapFactory.decodeResource(res, R.drawable.lobi_switch_mask);
        this.mMaskWidth = (float) this.mMaskBitmap.getWidth();
        this.mLeftMargin = 0.0f;
        this.mSwitchWidth = this.mSwitchBitmap.getWidth();
        this.mSwitchMoveWitdh = ((float) this.mSwitchWidth) - this.mMaskWidth;
        this.mSwitchMoveWitdhX = this.mSwitchMoveWitdh;
        this.mPosition = 0.0f;
        Bitmap bitmap = Bitmap.createBitmap(this.mMaskBitmap.getWidth(), this.mMaskBitmap.getHeight(), Config.ARGB_4444);
        new Canvas(bitmap).drawBitmap(this.mMaskBitmap, this.mLeftMargin, this.mTopMargin, this.mPaint);
        this.mMaskBitmap.recycle();
        this.mMaskBitmap = bitmap;
        this.mCanvasBitmap = Bitmap.createBitmap(this.mMaskBitmap.getWidth(), this.mMaskBitmap.getHeight(), Config.ARGB_8888);
        this.mCanvas = new Canvas(this.mCanvasBitmap);
        this.mHitArea = new Rect(0, 0, this.mMaskBitmap.getWidth(), this.mMaskBitmap.getHeight());
        setState(state);
    }

    public boolean isEnabled() {
        return this.mIsEnabled;
    }

    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        this.mAllowToogleState = true;
        if (this.mDown) {
            this.mIsDragging = true;
            this.mSwitchstate = -1;
            this.mStartX = x;
            this.mPrevX = x;
            this.mPositionStart = this.mPosition;
            this.mVelocity = 0.0f;
            if (!this.mHitArea.contains((int) x, (int) event.getY())) {
                this.mIsDragging = false;
                return false;
            }
        }
        switch (event.getAction() & 255) {
            case 0:
                this.mDown = true;
                if (!this.mHitArea.contains((int) x, (int) event.getY())) {
                    return false;
                }
                break;
            case 1:
            case 3:
                this.mDown = false;
                if (this.mIsDragging) {
                    this.mAnimationStart = getPosition(x);
                    if (this.mVelocity < 0.0f) {
                        this.mAnimationEnd = 0.0f;
                    } else if (this.mVelocity > 0.0f) {
                        this.mAnimationEnd = this.mSwitchMoveWitdhX;
                    } else if (this.mAnimationStart > this.mSwitchMoveWitdh / 2.0f) {
                        this.mAnimationEnd = 0.0f;
                    } else {
                        this.mAnimationEnd = this.mSwitchMoveWitdhX;
                    }
                    this.mAnimationCount = 0;
                    this.mSwitchstate = 0;
                    break;
                }
                return false;
            case 2:
                this.mDown = false;
                if (this.mIsDragging) {
                    this.mPosition = getPosition(x);
                    break;
                }
                return false;
        }
        float velocity = x - this.mPrevX;
        if (Math.abs(velocity) > Math.abs(this.mVelocity)) {
            this.mVelocity = velocity;
        }
        this.mPrevX = x;
        return true;
    }

    private float getPosition(float x) {
        float position = (x - this.mStartX) + this.mPositionStart;
        if (position < 0.0f) {
            return 0.0f;
        }
        if (position > this.mSwitchMoveWitdhX) {
            return this.mSwitchMoveWitdhX;
        }
        return position;
    }

    public void position(int x, int y) {
        this.mPositionX = x;
        this.mPositionY = y;
        this.mHitArea.offsetTo(x, y);
    }

    public void draw(Canvas canvas) {
        float f;
        switch (this.mSwitchstate) {
            case 0:
                autoAnimate();
                break;
            case 1:
                if (this.mIsEnabled) {
                    f = this.mSwitchMoveWitdhX;
                } else {
                    f = 0.0f;
                }
                this.mAnimationEnd = f;
                this.mSwitchstate = this.mActionAfter;
                autoAnimate();
                break;
            case 2:
                this.mSwitchstate = this.mActionAfter;
                this.mAnimationEnd = 0.0f;
                this.mPosition = 0.0f;
                break;
            case 3:
                f = this.mSwitchMoveWitdhX;
                this.mAnimationEnd = f;
                this.mPosition = f;
                this.mSwitchstate = this.mActionAfter;
                break;
        }
        this.mCanvas.drawColor(0);
        this.mPaint.setXfermode(null);
        this.mCanvas.drawBitmap(this.mSwitchBitmap, (this.mPosition - this.mSwitchMoveWitdh) + this.mLeftMargin, this.mTopMargin, this.mPaint);
        this.mPaint.setXfermode(this.mXferMode);
        this.mCanvas.drawBitmap(this.mMaskBitmap, 0.0f, 0.0f, this.mPaint);
        this.mPaint.setXfermode(null);
        canvas.translate((float) this.mPositionX, (float) this.mPositionY);
        canvas.drawBitmap(this.mCanvasBitmap, 0.0f, 0.0f, this.mPaint);
        canvas.translate((float) (-this.mPositionX), (float) (-this.mPositionY));
    }

    private final void autoAnimate() {
        boolean z = true;
        int i = this.mAnimationCount + 1;
        this.mAnimationCount = i;
        if (i >= 10) {
            this.mPosition = this.mAnimationEnd;
            if (this.mAnimationEnd > 0.1f) {
                z = false;
            }
            this.mIsEnabled = z;
            this.mSwitchstate = -1;
            this.mIsAnimating = false;
            if (this.mListener != null && this.mAllowToogleState) {
                this.mListener.onToggle(this, this.mIsEnabled);
                return;
            }
            return;
        }
        float t = ((float) this.mAnimationCount) / 10.0f;
        t *= 2.0f - t;
        this.mPosition = (this.mAnimationEnd * t) + ((1.0f - t) * this.mAnimationStart);
        this.mIsAnimating = true;
    }

    public Canvas getCanvas() {
        return this.mCanvas;
    }

    public boolean isAnimating() {
        return this.mIsAnimating;
    }

    public void setOnToggleStateChangedListener(OnToggleStateChangedListener listener) {
        this.mListener = listener;
    }

    public void setEnabled(boolean sharing) {
        Log.i("view", "setEnabled");
        this.mAnimationEnd = sharing ? this.mSwitchMoveWitdh : 0.0f;
        this.mAnimationStart = this.mPosition;
        this.mAnimationCount = 0;
    }
}
