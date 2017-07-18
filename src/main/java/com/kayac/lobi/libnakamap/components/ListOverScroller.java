package com.kayac.lobi.libnakamap.components;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.AbsListView;
import android.widget.ListView;
import com.kayac.lobi.sdk.R;

public class ListOverScroller {
    private final Runnable mAnimationRunnable = new Runnable() {
        public void run() {
            ListOverScroller.this.mIsRunning = true;
            if (ListOverScroller.this.mOverScroller.computeScrollOffset()) {
                ListOverScroller.this.mVirtualScrollY = ListOverScroller.this.mOverScroller.getCurrY();
                ListOverScroller.this.setOverScrollHeaderHeight(ListOverScroller.this.mMinHeight - ListOverScroller.this.mOverScroller.getCurrY());
                ListOverScroller.this.mHandler.post(this);
                return;
            }
            ListOverScroller.this.cancelOverScrollAnimation();
        }
    };
    protected boolean mBinded = false;
    private View mBindedView;
    private int mBindedViewHeight;
    private Context mContext;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private final Interpolator mHeaderAnimationInterpolator = new AccelerateDecelerateInterpolator();
    private final int mHeightToRefresh;
    private boolean mIsRunning = false;
    private float mLastMotionY;
    private final ListView mListView;
    private final int mMaxHeight;
    private final int mMinHeight;
    private OnPullDownListener mOnPullDownListener;
    private OnSpringBackListener mOnSpringBackListener;
    private final OnTouchListener mOnTouchListener = new OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            float y = event.getY();
            if (ListOverScroller.this.mLastMotionY < 0.0f) {
                ListOverScroller.this.mLastMotionY = y;
            }
            int newY = Math.max(ListOverScroller.this.mVirtualScrollY + ((int) (ListOverScroller.this.mLastMotionY - y)), ListOverScroller.this.mMinHeight - ListOverScroller.this.mMaxHeight);
            ListOverScroller.this.mLastMotionY = y;
            switch (event.getAction() & 255) {
                case 0:
                    if (!ListOverScroller.this.mOverScroller.isFinished()) {
                        ListOverScroller.this.mOverScroller.abortAnimation();
                        break;
                    }
                    break;
                case 1:
                case 3:
                    ListOverScroller.this.overScrollBy(newY);
                    break;
                case 2:
                    ListOverScroller.this.cancelOverScrollAnimation();
                    if (newY < 0) {
                        ListOverScroller.this.setOverScrollHeaderHeight(ListOverScroller.this.mMinHeight - newY);
                        ListOverScroller.this.mVirtualScrollY = newY;
                        break;
                    }
                    ListOverScroller.this.setOverScrollHeaderHeight(ListOverScroller.this.mMinHeight);
                    ListOverScroller.this.mVirtualScrollY = 0;
                    break;
            }
            return v.onTouchEvent(event);
        }
    };
    private final View mOverScrollHeader;
    private final OverScroller mOverScroller;
    private int mVirtualScrollY;

    public interface OnPullDownListener {
        void onPullDown(int i);
    }

    public interface OnSpringBackListener {
        void onSpringBack();
    }

    public ListOverScroller(ListView listView, View overScrollHeader) {
        this.mListView = listView;
        this.mListView.setOnTouchListener(this.mOnTouchListener);
        this.mContext = listView.getContext();
        this.mOverScroller = new OverScroller(this.mContext);
        Resources res = this.mContext.getResources();
        this.mMinHeight = res.getDimensionPixelSize(R.dimen.lobi_list_over_scroller_min_height);
        this.mMaxHeight = res.getDimensionPixelSize(R.dimen.lobi_list_over_scroller_max_height);
        this.mHeightToRefresh = res.getDimensionPixelSize(R.dimen.lobi_list_over_scroller_height_to_refresh);
        this.mOverScrollHeader = overScrollHeader;
        setOverScrollHeaderHeight(this.mMinHeight);
        this.mLastMotionY = -1.0f;
    }

    protected void cancelOverScrollAnimation() {
        if (this.mIsRunning) {
            this.mIsRunning = false;
            this.mHandler.removeCallbacks(this.mAnimationRunnable);
        }
    }

    protected void setOverScrollHeaderHeight(int height) {
        int overScrollHeight;
        height = Math.min(height, this.mMaxHeight);
        if (height < this.mMinHeight) {
            overScrollHeight = this.mMinHeight;
        } else {
            overScrollHeight = height;
        }
        LayoutParams params = this.mOverScrollHeader.getLayoutParams();
        if (params == null) {
            params = new AbsListView.LayoutParams(-1, height);
        } else {
            params.height = overScrollHeight;
        }
        this.mOverScrollHeader.scrollTo(0, (int) (((float) this.mMaxHeight) * (1.0f - this.mHeaderAnimationInterpolator.getInterpolation(((float) height) / ((float) this.mMaxHeight)))));
        this.mOverScrollHeader.setLayoutParams(params);
        if (this.mOnPullDownListener != null) {
            this.mOnPullDownListener.onPullDown(overScrollHeight);
        }
    }

    protected void overScrollBy(int newScrollY) {
        if (newScrollY >= 0) {
            this.mVirtualScrollY = newScrollY;
            cancelOverScrollAnimation();
            setOverScrollHeaderHeight(this.mMinHeight - newScrollY);
        } else {
            this.mOverScroller.springBack(0, newScrollY, 0, 0, 0, 0);
            if (this.mListView.getFirstVisiblePosition() <= 0 && this.mOverScrollHeader.getLayoutParams().height >= this.mHeightToRefresh) {
                onSpringBack();
            }
        }
        if (!this.mIsRunning) {
            this.mHandler.post(this.mAnimationRunnable);
        }
    }

    protected void onSpringBack() {
        showView();
        if (this.mOnSpringBackListener != null) {
            this.mOnSpringBackListener.onSpringBack();
        }
    }

    public void setOnSpringBackListener(OnSpringBackListener listner) {
        this.mOnSpringBackListener = listner;
    }

    public void setOnPullDownListener(OnPullDownListener listner) {
        this.mOnPullDownListener = listner;
    }

    protected void showView() {
        if (this.mBindedView != null && !this.mBinded) {
            LayoutParams params = this.mBindedView.getLayoutParams();
            params.height = this.mBindedViewHeight;
            this.mBindedView.setLayoutParams(params);
            this.mBinded = true;
        }
    }

    public void bindHideView(View v, int height) {
        this.mBindedView = v;
        this.mBindedViewHeight = height;
    }
}
