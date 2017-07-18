package com.kayac.lobi.libnakamap.components;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ToggleButton;
import com.kayac.lobi.libnakamap.components.ToggleInterface.OnToggleStateChangedListener;
import com.kayac.lobi.sdk.R;

public class ToggleOnOffButton extends ToggleButton {
    private final ToggleOnOffButtonImpl mButtonImpl;

    public ToggleOnOffButton(Context context) {
        this(context, null);
    }

    public ToggleOnOffButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayoutParams(new LayoutParams(context.getResources().getDimensionPixelSize(R.dimen.lobi_toggle_on_off_width), -2));
        this.mButtonImpl = new ToggleOnOffButtonImpl(context, attrs);
    }

    public void setOn() {
        this.mButtonImpl.setOn();
    }

    public void setOff() {
        this.mButtonImpl.setOff();
    }

    public void setState(boolean state) {
        this.mButtonImpl.setState(state);
    }

    public void rollBack() {
        this.mButtonImpl.rollBack();
    }

    public boolean getState() {
        return this.mButtonImpl.getState();
    }

    public void alwaysOn() {
        this.mButtonImpl.alwaysOn();
    }

    public void position(int x, int y) {
        this.mButtonImpl.position(x, y);
    }

    public boolean isEnabled() {
        return this.mButtonImpl.getState();
    }

    public void setOnToggleStateChangedListener(OnToggleStateChangedListener listener) {
        this.mButtonImpl.setOnToggleStateChangedListener(listener);
    }

    public void setEnabled(boolean sharing) {
        this.mButtonImpl.setEnabled(sharing);
    }

    public boolean onTouchEvent(MotionEvent event) {
        invalidate();
        return this.mButtonImpl.onTouchEvent(event);
    }

    public void draw(Canvas canvas) {
        this.mButtonImpl.draw(canvas);
        if (this.mButtonImpl.isAnimating()) {
            invalidate();
        }
    }
}
