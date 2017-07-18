package com.kayac.lobi.libnakamap.components;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.kayac.lobi.libnakamap.utils.Log;
import com.kayac.lobi.sdk.R;

public class LightBox {
    private static final String TAG = "[lightbox]";
    private final int mAnimationTime;
    private final android.widget.FrameLayout mClickLayer;
    private final int mConfigChangedAnimationTime;
    private final FrameLayout mContainer;
    private final Context mContext;
    private Animator mCurrentAnimator;
    private final OnClickListener mDismissOnClickListener = new OnClickListener() {
        public void onClick(View v) {
            LightBox.this.dismiss();
        }
    };
    private Animator mDissmissAnimator;
    private boolean mIsAnimating;
    private boolean mIsShowing;
    private OnContentLongClickListener mOnContentLongClickListener;
    private OnContentShowListener mOnContentShowListener;
    private final OnAnimatorEnd mOnDefaultAnimatorEnd = new OnAnimatorEnd() {
        public void onAnimatorEnd(Animator animator) {
            LightBox.this.mIsAnimating = false;
            LightBox.this.mCurrentAnimator = null;
            animator.setOnAnimatorEnd(null);
            if (LightBox.this.mOnContentShowListener != null) {
                LightBox.this.mOnContentShowListener.onContentShow(LightBox.this.mPopupContentView);
            }
        }
    };
    private final OnAnimatorEnd mOnDismissAnimatorEnd = new OnAnimatorEnd() {
        public void onAnimatorEnd(Animator animator) {
            LightBox.this.mWindowManager.removeView(LightBox.this.mContainer);
            LightBox.this.mContainer.removeAllViews();
            LightBox.this.mOriginalParent.removeView(LightBox.this.mPlaceHolder);
            LightBox.this.mOriginalLayoutParams.width = LightBox.this.mOriginalLayoutWidth;
            LightBox.this.mOriginalLayoutParams.height = LightBox.this.mOriginalLayoutHeight;
            LightBox.this.mPopupContentView.setLayoutParams(LightBox.this.mOriginalLayoutParams);
            LightBox.this.mOriginalParent.addView(LightBox.this.mPopupContentView, LightBox.this.mPopupViewIndex);
            LightBox.this.mIsAnimating = false;
            LightBox.this.mCurrentAnimator = null;
            ImageView content = LightBox.this.mPopupContentView;
            if (content instanceof ImageLoaderView) {
                ((ImageLoaderView) content).setClearBitmapOnDetach(true);
            }
            content.setScaleType(ScaleType.CENTER_CROP);
            LightBox.this.mPopupContentView = null;
            if (LightBox.this.mOnDismissListener != null) {
                LightBox.this.mOnDismissListener.onDismiss(content);
            }
            Log.v(LightBox.TAG, "layout: " + LightBox.this.mOriginalParent.getLayoutParams());
        }
    };
    private OnDismissListener mOnDismissListener;
    private int mOrientation;
    private int mOriginalLayoutHeight;
    private LayoutParams mOriginalLayoutParams;
    private int mOriginalLayoutWidth;
    private ViewGroup mOriginalParent;
    private final int mPadding;
    private final View mPlaceHolder;
    private ImageView mPopupContentView;
    private int mPopupViewIndex;
    private Window mWindow;
    private final WindowManager mWindowManager;

    private interface OnAnimatorEnd {
        void onAnimatorEnd(Animator animator);
    }

    private static class Animator implements Runnable {
        private final AnimatorParams mAnimationParams;
        private final FrameLayout mContainer;
        private final android.widget.FrameLayout.LayoutParams mLayoutParams;
        OnAnimatorEnd mOnAnimatorEnd;
        private final View mTarget;
        private long mTime;

        private Animator(FrameLayout container, View target, AnimatorParams params) {
            this.mTime = 0;
            this.mContainer = container;
            this.mTarget = target;
            this.mLayoutParams = new android.widget.FrameLayout.LayoutParams(0, 0);
            this.mLayoutParams.gravity = 7;
            this.mAnimationParams = params;
        }

        private void cancel() {
            this.mContainer.removeCallbacks(this);
        }

        private void start() {
            this.mTime = System.currentTimeMillis();
            this.mContainer.post(this);
        }

        private void setOnAnimatorEnd(OnAnimatorEnd animator) {
            this.mOnAnimatorEnd = animator;
        }

        public void run() {
            long time = System.currentTimeMillis() - this.mTime;
            float t = ((float) time) / ((float) this.mAnimationParams.duration);
            if (t >= 1.0f) {
                t = 1.0f;
            }
            t = this.mAnimationParams.interpolator.getInterpolation(t);
            float s = 1.0f - t;
            this.mLayoutParams.leftMargin = (int) ((((float) this.mAnimationParams.startRect.left) * s) + (((float) this.mAnimationParams.endRect.left) * t));
            this.mLayoutParams.topMargin = (int) ((((float) this.mAnimationParams.startRect.top) * s) + (((float) this.mAnimationParams.endRect.top) * t));
            this.mLayoutParams.width = (int) ((((float) this.mAnimationParams.startRect.width()) * s) + (((float) this.mAnimationParams.endRect.width()) * t));
            this.mLayoutParams.height = (int) ((((float) this.mAnimationParams.startRect.height()) * s) + (((float) this.mAnimationParams.endRect.height()) * t));
            this.mTarget.setLayoutParams(this.mLayoutParams);
            this.mTarget.invalidate();
            if (time >= ((long) this.mAnimationParams.duration)) {
                this.mContainer.removeCallbacks(this);
                if (this.mOnAnimatorEnd != null) {
                    this.mOnAnimatorEnd.onAnimatorEnd(this);
                    return;
                }
                return;
            }
            this.mContainer.post(this);
        }
    }

    private static class AnimatorParams {
        public final int duration;
        public final Rect endRect;
        public final Interpolator interpolator;
        public final Rect startRect;

        public AnimatorParams(Rect startRect, Rect endRect, int duration, Interpolator interpolator) {
            this.startRect = startRect;
            this.endRect = endRect;
            this.duration = duration;
            this.interpolator = interpolator;
        }
    }

    public static class FrameLayout extends android.widget.FrameLayout {
        private OnKeyListener mOnKeyListener;

        public FrameLayout(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public void setOnKeyListener(OnKeyListener l) {
            super.setOnKeyListener(l);
            this.mOnKeyListener = l;
        }

        public boolean dispatchKeyEvent(KeyEvent event) {
            if (this.mOnKeyListener == null || !this.mOnKeyListener.onKey(this, event.getKeyCode(), event)) {
                return super.dispatchKeyEvent(event);
            }
            return true;
        }
    }

    public interface OnContentLongClickListener {
        void onContentLongClick(View view);
    }

    public interface OnContentShowListener {
        void onContentShow(View view);
    }

    public interface OnDismissListener {
        void onDismiss(ImageView imageView);
    }

    public LightBox(Context context) {
        this.mContext = context;
        this.mWindowManager = (WindowManager) context.getSystemService("window");
        this.mContainer = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.lobi_light_box, null);
        this.mContainer.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == 1 && event.getKeyCode() == 4) {
                    LightBox.this.dismiss();
                }
                return false;
            }
        });
        this.mPlaceHolder = new View(context);
        this.mClickLayer = new android.widget.FrameLayout(context);
        this.mClickLayer.setLayoutParams(new android.widget.FrameLayout.LayoutParams(-1, -1));
        this.mClickLayer.setOnClickListener(this.mDismissOnClickListener);
        this.mClickLayer.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View v) {
                if (LightBox.this.mOnContentLongClickListener == null) {
                    return false;
                }
                LightBox.this.mOnContentLongClickListener.onContentLongClick(LightBox.this.mPopupContentView);
                return true;
            }
        });
        Resources resources = context.getResources();
        this.mPadding = resources.getDimensionPixelSize(R.dimen.lobi_light_box_padding);
        this.mAnimationTime = resources.getInteger(R.integer.lobi_light_box_animation_time);
        this.mConfigChangedAnimationTime = resources.getInteger(R.integer.lobi_light_box_configuration_changed_animation_time);
    }

    public void show(ImageView view, Window window) {
        if (view instanceof ImageLoaderView) {
            ((ImageLoaderView) view).setClearBitmapOnDetach(false);
        }
        Drawable drawable = view.getDrawable();
        if (!this.mIsAnimating && drawable != null) {
            this.mIsShowing = true;
            this.mIsAnimating = true;
            this.mOriginalParent = (ViewGroup) view.getParent();
            this.mOrientation = this.mContext.getResources().getConfiguration().orientation;
            this.mPopupViewIndex = this.mOriginalParent.indexOfChild(view);
            int frameWidth = view.getWidth();
            int frameHeight = view.getHeight();
            LayoutParams p = view.getLayoutParams();
            Log.v(TAG, "@show: " + p);
            this.mOriginalLayoutWidth = p.width;
            this.mOriginalLayoutHeight = p.height;
            p.width = frameWidth;
            p.height = frameHeight;
            this.mPlaceHolder.setId(view.getId());
            this.mPlaceHolder.setLayoutParams(p);
            this.mOriginalLayoutParams = p;
            view.setScaleType(ScaleType.FIT_CENTER);
            this.mPopupContentView = view;
            this.mContainer.removeAllViews();
            int[] position = new int[2];
            view.getLocationInWindow(position);
            Log.v(TAG, String.format("locationInWindow (%d, %d)", new Object[]{Integer.valueOf(position[0]), Integer.valueOf(position[1])}));
            LayoutParams layoutParams = new android.widget.FrameLayout.LayoutParams(frameWidth, frameHeight);
            Rect bounds = drawable.getBounds();
            int imageWidth = bounds.width();
            int imageHeight = bounds.height();
            int horizontalRatio = frameWidth / imageWidth;
            int verticalRatio = frameHeight / imageHeight;
            Rect rect = new Rect();
            window.getDecorView().getWindowVisibleDisplayFrame(rect);
            layoutParams.gravity = 51;
            if (horizontalRatio > verticalRatio) {
                layoutParams.leftMargin = position[0] - ((imageWidth - frameWidth) / 2);
                layoutParams.topMargin = position[1] - rect.top;
                layoutParams.width = imageWidth;
            } else {
                layoutParams.leftMargin = position[0];
                layoutParams.topMargin = (position[1] - rect.top) - ((imageHeight - frameHeight) / 2);
                layoutParams.height = imageHeight;
            }
            Interpolator interpolator = new DecelerateInterpolator();
            Rect rect2 = new Rect(layoutParams.leftMargin, layoutParams.topMargin, layoutParams.leftMargin + layoutParams.width, layoutParams.topMargin + layoutParams.height);
            Rect endRect = new Rect(this.mPadding, this.mPadding, rect.right - this.mPadding, (rect.bottom - this.mPadding) - rect.top);
            Animator animator = new Animator(this.mContainer, view, new AnimatorParams(rect2, endRect, this.mAnimationTime, interpolator));
            animator.setOnAnimatorEnd(this.mOnDefaultAnimatorEnd);
            this.mCurrentAnimator = animator;
            animator.start();
            this.mDissmissAnimator = new Animator(this.mContainer, view, new AnimatorParams(endRect, rect2, this.mAnimationTime, interpolator));
            this.mDissmissAnimator.setOnAnimatorEnd(this.mOnDismissAnimatorEnd);
            this.mOriginalParent.removeView(view);
            this.mOriginalParent.addView(this.mPlaceHolder, this.mPopupViewIndex);
            this.mContainer.addView(view, layoutParams);
            this.mContainer.addView(this.mClickLayer);
            this.mWindow = window;
            invokePopup(createWindowParams(view.getWindowToken()));
        }
    }

    private void invokePopup(WindowManager.LayoutParams p) {
        this.mWindowManager.addView(this.mContainer, p);
    }

    private WindowManager.LayoutParams createWindowParams(IBinder token) {
        WindowManager.LayoutParams p = new WindowManager.LayoutParams();
        p.gravity = 51;
        p.format = -3;
        p.packageName = this.mContext.getPackageName();
        p.token = token;
        return p;
    }

    public void dismiss() {
        if (isShowing()) {
            this.mIsShowing = false;
            this.mIsAnimating = true;
            this.mCurrentAnimator = this.mDissmissAnimator;
            this.mDissmissAnimator.start();
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        if (this.mPopupContentView != null && isShowing() && this.mOrientation != newConfig.orientation) {
            this.mOrientation = newConfig.orientation;
            if (this.mIsAnimating && this.mCurrentAnimator != null) {
                this.mCurrentAnimator.cancel();
            }
            this.mIsAnimating = true;
            View view = this.mPopupContentView;
            int[] position = new int[2];
            view.getLocationInWindow(position);
            android.widget.FrameLayout.LayoutParams p = new android.widget.FrameLayout.LayoutParams(view.getWidth(), view.getHeight());
            Rect rect = new Rect();
            this.mWindow.getDecorView().getWindowVisibleDisplayFrame(rect);
            Rect rect2 = new Rect(rect.top, rect.left, rect.bottom, rect.right - rect.top);
            p.gravity = 51;
            p.leftMargin = position[0];
            p.topMargin = position[1] - rect2.top;
            Animator animator = new Animator(this.mContainer, view, new AnimatorParams(new Rect(p.leftMargin, p.topMargin, p.leftMargin + view.getWidth(), p.topMargin + view.getHeight()), new Rect(this.mPadding, this.mPadding, rect2.right - this.mPadding, rect2.bottom - this.mPadding), this.mConfigChangedAnimationTime, new DecelerateInterpolator()));
            animator.setOnAnimatorEnd(this.mOnDefaultAnimatorEnd);
            this.mCurrentAnimator = animator;
            animator.start();
        }
    }

    public void setOnContentLongClickListener(OnContentLongClickListener listener) {
        this.mOnContentLongClickListener = listener;
    }

    public void setOnContentShowListener(OnContentShowListener listener) {
        this.mOnContentShowListener = listener;
    }

    public void setOnDismissListener(OnDismissListener listener) {
        this.mOnDismissListener = listener;
    }

    public boolean isShowing() {
        return this.mIsShowing;
    }
}
