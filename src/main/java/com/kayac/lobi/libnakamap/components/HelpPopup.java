package com.kayac.lobi.libnakamap.components;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import com.kayac.lobi.libnakamap.components.Styleable.Style;

public class HelpPopup extends Dialog implements Styleable {
    public boolean mAuto;
    private Context mContext;
    protected Dialog mDialog;
    private int mDuration;
    private Handler mHandler;
    private OnClickListener mOnClickListener;
    private Runnable mRunnable;
    private boolean mTouch;
    protected View mView;

    public interface OnClickListener {
        void onClickEvent();
    }

    public HelpPopup(Context context) {
        this(context, null);
    }

    public HelpPopup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HelpPopup(Context context, AttributeSet attrs, int defStyle) {
        super(context, 16973840);
        this.mDialog = this;
        this.mDuration = 3000;
        this.mAuto = true;
        this.mTouch = true;
        this.mRunnable = new Runnable() {
            public void run() {
                HelpPopup.this.remove();
            }
        };
        this.mContext = context;
        requestWindowFeature(1);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == 1 && this.mTouch) {
            if (this.mOnClickListener != null) {
                this.mOnClickListener.onClickEvent();
            }
            remove();
        }
        return false;
    }

    public void show() {
        show(false);
    }

    public void show(boolean dimm) {
        super.show();
        LayoutParams lp = this.mDialog.getWindow().getAttributes();
        float dim = 0.0f;
        if (dimm) {
            dim = 0.5f;
        }
        lp.dimAmount = dim;
        lp.alpha = 1.0f;
        this.mDialog.getWindow().setAttributes(lp);
        this.mDialog.setCanceledOnTouchOutside(true);
        if (this.mAuto) {
            dissapear();
        }
    }

    public void setContentView(Drawable drawable) {
        ImageView image = new ImageView(this.mContext);
        image.setImageDrawable(drawable);
        super.setContentView(image);
    }

    public void setAuto(boolean auto) {
        this.mAuto = auto;
    }

    public void setTouchable(boolean isTouch) {
        this.mTouch = isTouch;
    }

    public void setDuration(int duration) {
        this.mDuration = duration;
    }

    protected void remove() {
        this.mDialog.dismiss();
    }

    protected void dissapear() {
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mHandler.postDelayed(this.mRunnable, (long) this.mDuration);
        this.mHandler = null;
    }

    public void setOnClickListener(OnClickListener listner) {
        this.mOnClickListener = listner;
    }

    public void setStyle(Style style) {
    }
}
