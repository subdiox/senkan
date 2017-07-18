package com.kayac.lobi.libnakamap.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.kayac.lobi.libnakamap.components.UIScrollView.OnScrollChanged;
import com.kayac.lobi.libnakamap.datastore.AccountDatastore;
import com.kayac.lobi.libnakamap.utils.ViewUtils;
import com.kayac.lobi.sdk.R;
import com.kayac.lobi.sdk.activity.profile.ProfileEditActivity;

public class ProfileCover extends FrameLayout {
    private static final String TAG = "[profile]";
    private final Runnable mAnimationRunnable;
    private final LinearLayout mContainer;
    private final View mContainerImageArea;
    private View mContentView;
    private final ImageLoaderView mCoverImage;
    private final View mCoverImageContainer;
    private final int mCoverMaxHeight;
    private final int mCoverMinHeight;
    private final View mEditButton;
    private final View mIconContainer;
    private boolean mIsRunning;
    private float mLastMotionY;
    private final View mMarginBottom;
    private final OnScrollChanged mOnScrollChanged;
    private final OnTouchListener mOnTouchListener;
    private final OverScroller mOverScroller;
    private final ImageLoaderView mProfileIcon;
    private int mProfileIconTopOverRun;
    private final UIScrollView mScrollView;
    private int mVirtualScrollY;

    public ProfileCover(Context context) {
        this(context, null);
    }

    public ProfileCover(Context context, AttributeSet attrs) {
        this(context, attrs, R.style.lobi_profile_cover);
    }

    public ProfileCover(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mProfileIconTopOverRun = 0;
        this.mIsRunning = false;
        this.mOnScrollChanged = new OnScrollChanged() {
            public void onScrollChanged(int l, int t, int oldl, int oldt) {
                if (ProfileCover.this.mVirtualScrollY >= 0) {
                    Log.v(ProfileCover.TAG, "mVirtualScrollY: " + ProfileCover.this.mVirtualScrollY);
                    Log.v(ProfileCover.TAG, "onScrollChanged: " + l + " x " + t);
                    if (t >= 0 && oldt >= 0) {
                        ProfileCover.this.mVirtualScrollY = t;
                    }
                }
                ProfileCover.this.scrollCoverImage();
            }
        };
        this.mOnTouchListener = new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int deltaY;
                float y = event.getY();
                if (ProfileCover.this.mLastMotionY >= 0.0f) {
                    deltaY = (int) (ProfileCover.this.mLastMotionY - y);
                } else {
                    deltaY = 0;
                }
                int newY = Math.max(ProfileCover.this.mVirtualScrollY + deltaY, ProfileCover.this.mCoverMinHeight - ProfileCover.this.mCoverMaxHeight);
                ProfileCover.this.mLastMotionY = y;
                switch (event.getAction() & 255) {
                    case 1:
                    case 3:
                        ProfileCover.this.overScrollBy(newY);
                        ProfileCover.this.mLastMotionY = -1.0f;
                        break;
                    case 2:
                        ProfileCover.this.cancelOverScrollAnimation();
                        if (newY >= 0) {
                            ProfileCover.this.setCoverImageFrameHeight(ProfileCover.this.mCoverMinHeight);
                            ProfileCover.this.mVirtualScrollY = 0;
                            break;
                        }
                        ProfileCover.this.setCoverImageFrameHeight(ProfileCover.this.mCoverMinHeight - newY);
                        ProfileCover.this.mScrollView.scrollTo(0, 0);
                        ProfileCover.this.mVirtualScrollY = newY;
                        return false;
                }
                return v.onTouchEvent(event);
            }
        };
        this.mAnimationRunnable = new Runnable() {
            public void run() {
                if (!ProfileCover.this.mIsRunning) {
                    return;
                }
                if (ProfileCover.this.mOverScroller.computeScrollOffset()) {
                    ProfileCover.this.mVirtualScrollY = ProfileCover.this.mOverScroller.getCurrY();
                    ProfileCover.this.setCoverImageFrameHeight(ProfileCover.this.mCoverMinHeight - ProfileCover.this.mOverScroller.getCurrY());
                    ProfileCover.this.post(this);
                    return;
                }
                ProfileCover.this.cancelOverScrollAnimation();
            }
        };
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.lobi_ProfileCover, defStyle, R.style.lobi_profile_cover);
        this.mCoverMinHeight = a.getDimensionPixelSize(R.styleable.lobi_ProfileCover_lobi_coverImageMinHeight, 0);
        this.mCoverMaxHeight = a.getDimensionPixelSize(R.styleable.lobi_ProfileCover_lobi_coverImageMaxHeight, 0);
        this.mProfileIconTopOverRun = a.getDimensionPixelSize(R.styleable.lobi_ProfileCover_lobi_iconTopRunOver, 0);
        a.recycle();
        this.mVirtualScrollY = 0;
        this.mLastMotionY = -1.0f;
        View view = LayoutInflater.from(context).inflate(R.layout.lobi_profile_cover, this);
        this.mContainerImageArea = view.findViewById(R.id.lobi_profile_container_image_area);
        this.mCoverImage = (ImageLoaderView) view.findViewById(R.id.lobi_profile_cover_image);
        this.mCoverImageContainer = view.findViewById(R.id.lobi_profile_cover_container);
        this.mEditButton = view.findViewById(R.id.lobi_profile_cover_edit_button);
        this.mEditButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(PathRouter.PATH, ProfileEditActivity.PATH_PROFILE_EDIT);
                bundle.putParcelable("EXTRAS_TARGET_USER", AccountDatastore.getCurrentUser());
                PathRouter.startPath(bundle);
            }
        });
        this.mMarginBottom = view.findViewById(R.id.lobi_profile_cover_margin_bottom);
        this.mScrollView = (UIScrollView) view.findViewById(R.id.lobi_profile_cover_scroll_view);
        this.mScrollView.setOnScrollChanged(this.mOnScrollChanged);
        this.mScrollView.setOnTouchListener(this.mOnTouchListener);
        ViewUtils.hideOverscrollEdge(this.mScrollView);
        this.mContainer = (LinearLayout) view.findViewById(R.id.lobi_profile_container);
        this.mIconContainer = view.findViewById(R.id.lobi_profile_cover_icon_container);
        this.mProfileIcon = (ImageLoaderView) view.findViewById(R.id.lobi_profile_cover_icon);
        this.mOverScroller = new OverScroller(context);
        scrollCoverImage();
    }

    protected void cancelOverScrollAnimation() {
        if (this.mIsRunning) {
            this.mIsRunning = false;
            if (!this.mOverScroller.isFinished()) {
                this.mOverScroller.abortAnimation();
            }
            removeCallbacks(this.mAnimationRunnable);
        }
    }

    protected void setCoverImageFrameHeight(int height) {
        int coverImageAreaHeight;
        height = Math.min(height, this.mCoverMaxHeight);
        if (height < this.mCoverMinHeight) {
            coverImageAreaHeight = this.mCoverMinHeight;
        } else {
            coverImageAreaHeight = height;
        }
        LayoutParams params = this.mContainerImageArea.getLayoutParams();
        params.height = coverImageAreaHeight;
        this.mContainerImageArea.setLayoutParams(params);
        FrameLayout.LayoutParams params2 = (FrameLayout.LayoutParams) this.mIconContainer.getLayoutParams();
        params2.topMargin = coverImageAreaHeight - this.mProfileIconTopOverRun;
        this.mIconContainer.setLayoutParams(params2);
        scrollCoverImage();
    }

    protected void overScrollBy(int newScrollY) {
        if (newScrollY >= 0) {
            this.mVirtualScrollY = newScrollY;
            cancelOverScrollAnimation();
            setCoverImageFrameHeight(this.mCoverMinHeight - newScrollY);
            return;
        }
        this.mOverScroller.springBack(0, newScrollY, 0, 0, 0, 0);
        if (!this.mIsRunning) {
            this.mIsRunning = true;
            post(this.mAnimationRunnable);
        }
    }

    private void scrollCoverImage() {
        if (this.mVirtualScrollY > this.mCoverMinHeight) {
            Log.v(TAG, this.mVirtualScrollY + " > " + this.mCoverMinHeight);
            return;
        }
        this.mCoverImageContainer.scrollTo(0, ((this.mCoverMaxHeight - this.mCoverMinHeight) + this.mVirtualScrollY) >> 1);
    }

    public void setCoverImage(String url) {
        this.mCoverImage.loadImage(url);
    }

    public void resetCoverImage() {
        this.mCoverImage.setImageBitmap(null);
    }

    public void setIconImage(String url) {
        this.mProfileIcon.loadImage(url);
    }

    public void setIconImage(String url, int size) {
        this.mProfileIcon.loadImage(url, size);
    }

    public void restartScroll() {
        this.mVirtualScrollY = 1;
        this.mScrollView.scrollTo(0, this.mVirtualScrollY);
    }

    public void setContentLayout(int layoutId) {
        if (this.mContentView != null) {
            this.mContentView.setOnTouchListener(null);
            this.mContainer.removeView(this.mContentView);
        }
        this.mContentView = LayoutInflater.from(getContext()).inflate(layoutId, null);
        scrollCoverImage();
        this.mContainer.addView(this.mContentView, new FrameLayout.LayoutParams(-1, -1));
    }

    public View getContentView() {
        return this.mContentView;
    }

    public void setEditButtonVisible(boolean visible) {
        int i = 8;
        this.mEditButton.setVisibility(visible ? 0 : 8);
        View view = this.mMarginBottom;
        if (!visible) {
            i = 0;
        }
        view.setVisibility(i);
    }
}
