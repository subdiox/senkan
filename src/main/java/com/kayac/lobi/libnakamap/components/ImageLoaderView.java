package com.kayac.lobi.libnakamap.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import com.kayac.lobi.libnakamap.net.ImageLoader;
import com.kayac.lobi.libnakamap.net.ImageLoader.LoaderTask;
import com.kayac.lobi.libnakamap.net.ImageLoader.OnImageLoaded;
import com.kayac.lobi.libnakamap.net.RecyclingBitmapDrawable;
import com.kayac.lobi.libnakamap.utils.DeviceUtil;
import com.kayac.lobi.libnakamap.utils.Log;
import com.kayac.lobi.sdk.R;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageLoaderView extends ImageView implements OnImageLoaded {
    public static final String TAG = "[loader]";
    private static Drawable sDefaultDrawable = new ColorDrawable(-2236963);
    private static final Pattern sImageSizePattern = Pattern.compile("_([1-9][0-9]*)");
    private boolean clearBitmapOnDetach;
    private int mBaseDrawableId;
    private int mBaseHeight;
    private int mBaseWidth;
    private Config mBitmapConfig;
    private Drawable mDefaultDrawable;
    private final SetImageDrawableHook mDefaultSetImageDrawable;
    private boolean mDrawDefault;
    private LoaderTask mLoaderTask;
    private final Object mLock;
    private final Rect mRect;
    private SetImageDrawableHook mSetImageDrawable;
    private String mUrl;
    private boolean mUseExtendedSize;

    public interface SetImageDrawableHook {
        void setImageDrawable(ImageLoaderView imageLoaderView, BitmapDrawable bitmapDrawable);
    }

    private static final class DefaultSetImageDrawableHook implements SetImageDrawableHook {
        private DefaultSetImageDrawableHook() {
        }

        public void setImageDrawable(ImageLoaderView view, BitmapDrawable bitmapDrawable) {
            view.setImageDrawable(bitmapDrawable);
        }
    }

    public ImageLoaderView(Context context) {
        this(context, null);
    }

    public ImageLoaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mLock = new Object();
        this.mRect = new Rect();
        this.mBitmapConfig = Config.ARGB_8888;
        this.mSetImageDrawable = null;
        this.mDefaultSetImageDrawable = new DefaultSetImageDrawableHook();
        this.clearBitmapOnDetach = true;
        if (attrs != null) {
            readAttributes(context, attrs);
        }
        if (this.mBaseDrawableId != 0) {
            this.mDefaultDrawable = context.getResources().getDrawable(this.mBaseDrawableId);
        } else {
            this.mDefaultDrawable = sDefaultDrawable;
        }
    }

    private void readAttributes(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.lobi_ImageLoaderView);
        this.mBaseDrawableId = typedArray.getResourceId(R.styleable.lobi_ImageLoaderView_lobi_baseDrawable, 0);
        this.mBaseWidth = typedArray.getDimensionPixelSize(R.styleable.lobi_ImageLoaderView_lobi_baseWidth, 0);
        this.mBaseHeight = typedArray.getDimensionPixelSize(R.styleable.lobi_ImageLoaderView_lobi_baseHeight, 0);
        this.mUseExtendedSize = typedArray.getBoolean(R.styleable.lobi_ImageLoaderView_lobi_useMaxExtendedSize, false);
        if (this.mUseExtendedSize) {
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            this.mBaseWidth = metrics.widthPixels;
            this.mBaseHeight = metrics.heightPixels;
        }
        switch (typedArray.getInt(R.styleable.lobi_ImageLoaderView_lobi_bitmapConfig, 0)) {
            case 1:
                this.mBitmapConfig = Config.RGB_565;
                break;
        }
        typedArray.recycle();
    }

    public void setMemoryCacheEnable(boolean b) {
    }

    public void loadImage(String url) {
        loadImage(url, this.mDefaultSetImageDrawable);
    }

    public void setBaseWidth(int width) {
        this.mBaseWidth = width;
    }

    public void setBaseHeigth(int height) {
        this.mBaseHeight = height;
    }

    public void loadImage(String url, SetImageDrawableHook setImageDrawable) {
        synchronized (this.mLock) {
            if (TextUtils.isEmpty(url)) {
                this.mDrawDefault = true;
            } else if (this.mLoaderTask == null || !url.equals(this.mLoaderTask.url)) {
                this.mSetImageDrawable = setImageDrawable;
                if (this.mBaseWidth == 0 || this.mBaseHeight == 0) {
                    DisplayMetrics metrics = getResources().getDisplayMetrics();
                    this.mBaseWidth = metrics.widthPixels;
                    this.mBaseHeight = metrics.heightPixels;
                }
                LoaderTask task = new LoaderTask(getContext(), url, false, this, this.mBaseWidth, this.mBaseHeight, this.mBitmapConfig);
                if (this.mLoaderTask != null) {
                    ImageLoader.cancel(this.mLoaderTask);
                }
                this.mUrl = url;
                this.mLoaderTask = task;
                this.mDrawDefault = true;
                ImageLoader.loadImage(task);
            }
        }
    }

    public void loadImage(String url, int size) {
        loadImage(url, size, this.mDefaultSetImageDrawable);
    }

    public void loadImage(String url, int size, SetImageDrawableHook setImageDrawable) {
        loadImage(getImageUrl(url, size), setImageDrawable);
    }

    public static String getImageUrl(String url, int size) {
        Matcher m = sImageSizePattern.matcher(url);
        if (m.find()) {
            return url.subSequence(0, m.start(1)) + String.valueOf(size) + url.substring(m.end(1));
        }
        return url;
    }

    public void recycleImageSafely() {
        Drawable drawable = getDrawable();
        setImageBitmap(null);
        if (drawable instanceof BitmapDrawable) {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
    }

    public void cancelLoadImage() {
        synchronized (this.mLock) {
            if (this.mLoaderTask != null) {
                ImageLoader.cancel(this.mLoaderTask);
                this.mLoaderTask = null;
            }
        }
    }

    public void setDrawDefault(boolean value) {
        synchronized (this.mLock) {
            this.mDrawDefault = value;
        }
    }

    public void setClearBitmapOnDetach(boolean clearBitmapOnDetach) {
        this.clearBitmapOnDetach = clearBitmapOnDetach;
    }

    public boolean getDrawDefault() {
        boolean value;
        synchronized (this.mLock) {
            value = this.mDrawDefault;
        }
        return value;
    }

    public void onImageLoaded(String url, BitmapDrawable bitmapDrawable) {
        synchronized (this.mLock) {
            boolean isEqual = TextUtils.equals(this.mUrl, url);
            this.mLoaderTask = null;
            this.mDrawDefault = !isEqual;
        }
        if (bitmapDrawable == null || bitmapDrawable == null || !isEqual) {
            loadImage(this.mUrl);
        } else if (this.mSetImageDrawable == null) {
            setImageDrawable(bitmapDrawable);
        } else {
            this.mSetImageDrawable.setImageDrawable(this, bitmapDrawable);
        }
    }

    protected void onDraw(Canvas canvas) {
        if (getDrawDefault()) {
            this.mDefaultDrawable.setBounds(this.mRect);
            this.mDefaultDrawable.draw(canvas);
            return;
        }
        Drawable drawable = getDrawable();
        if (drawable != null && (drawable instanceof BitmapDrawable)) {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            if (bitmap != null && bitmap.isRecycled()) {
                Log.v("[icon]", "bitmap recycled!");
                setImageDrawable(this.mDefaultDrawable);
                synchronized (this.mLock) {
                    if (this.mUrl != null) {
                        loadImage(this.mUrl);
                    }
                }
                return;
            }
        }
        super.onDraw(canvas);
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            this.mRect.set(0, 0, right - left, bottom - top);
        }
    }

    protected void onDetachedFromWindow() {
        if (!DeviceUtil.hasHoneycomb() && this.clearBitmapOnDetach) {
            setImageDrawable(null);
        }
        super.onDetachedFromWindow();
    }

    public void setImageDrawable(Drawable drawable) {
        Drawable previousDrawable = getDrawable();
        super.setImageDrawable(drawable);
        notifyDrawable(drawable, true);
        notifyDrawable(previousDrawable, false);
    }

    private static void notifyDrawable(Drawable drawable, boolean isDisplayed) {
        if (drawable instanceof RecyclingBitmapDrawable) {
            ((RecyclingBitmapDrawable) drawable).setIsDisplayed(isDisplayed);
        } else if (drawable instanceof LayerDrawable) {
            LayerDrawable layerDrawable = (LayerDrawable) drawable;
            int z = layerDrawable.getNumberOfLayers();
            for (int i = 0; i < z; i++) {
                notifyDrawable(layerDrawable.getDrawable(i), isDisplayed);
            }
        }
    }
}
