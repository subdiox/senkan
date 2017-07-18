package com.kayac.lobi.libnakamap.components;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.MediaStore.Images.Thumbnails;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.kayac.lobi.libnakamap.components.Styleable.Style;
import com.kayac.lobi.libnakamap.utils.DebugAssert;
import com.kayac.lobi.libnakamap.utils.GalleryUtil.ImageData;
import com.kayac.lobi.sdk.R;
import java.util.ArrayList;
import java.util.List;

public class HorizontalElementScroller extends LinearLayout implements Styleable {
    public static final String TYPE_GALLERY = "TYPE_GALLERY";
    private Context mContext;
    private List<View> mData;
    private int mElements;
    private int mElementsInPage;
    private List<ImageData> mImageData;
    private OnClickItemListener mOnClickItemListener;
    public OnSizeChangeListener mOnsizeChangeListener;
    private HorizontalPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    private int mWidthElment;
    private int mWidthLayout;

    private static final class GalleryHorizontalAdapter extends PagerAdapter {
        private List<List<ImageData>> data;
        private Context mContext;
        private OnClickItemListener mOnClickItemListener;

        public GalleryHorizontalAdapter(Context ctx, List<List<ImageData>> data, OnClickItemListener listener) {
            this.data = data;
            this.mContext = ctx;
            this.mOnClickItemListener = listener;
        }

        public int getCount() {
            return this.data.size();
        }

        public void clear() {
            this.data.clear();
            notifyDataSetChanged();
        }

        public Object instantiateItem(View collection, int position) {
            List<ImageData> imageData = (List) this.data.get(position);
            LinearLayout layout = new LinearLayout(this.mContext);
            layout.setLayoutParams(new LayoutParams(-1, -1));
            layout.removeAllViews();
            for (final ImageData img : imageData) {
                ThumbItem item = new ThumbItem(this.mContext);
                ImageLoaderView thumb = (ImageLoaderView) item.findViewById(R.id.lobi_chat_pick_preview_horizontall_scroller_item_image);
                Bitmap bitmap = Thumbnails.getThumbnail(this.mContext.getApplicationContext().getContentResolver(), (long) img.getId(), 1, null);
                thumb.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        if (GalleryHorizontalAdapter.this.mOnClickItemListener != null) {
                            GalleryHorizontalAdapter.this.mOnClickItemListener.onClickItem(img);
                        }
                    }
                });
                thumb.setImageBitmap(bitmap);
                layout.addView(item);
            }
            ((ViewPager) collection).addView(layout);
            return layout;
        }

        public void destroyItem(View collection, int position, Object view) {
            ((ViewPager) collection).removeView((View) view);
        }

        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public void startUpdate(View arg0) {
        }

        public void finishUpdate(View arg0) {
        }
    }

    private static final class HorizontalPagerAdapter extends PagerAdapter {
        private List<View> data;

        public HorizontalPagerAdapter(Context ctx, List<View> data) {
            this.data = data;
        }

        public int getCount() {
            return this.data.size();
        }

        public void clear() {
            for (View v : this.data) {
                ImageLoaderView thumb = (ImageLoaderView) v.findViewById(R.id.lobi_chat_pick_preview_horizontall_scroller_item_image);
                if (thumb != null) {
                    thumb.setClearBitmapOnDetach(true);
                }
            }
            this.data.clear();
            notifyDataSetChanged();
        }

        public Object instantiateItem(View collection, int position) {
            View view = (View) this.data.get(position);
            ((ViewPager) collection).addView(view);
            return view;
        }

        public void destroyItem(View collection, int position, Object view) {
            ((ViewPager) collection).removeView((View) view);
        }

        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public void startUpdate(View arg0) {
        }

        public void finishUpdate(View arg0) {
        }
    }

    public interface OnClickItemListener {
        void onClickItem(ImageData imageData);
    }

    public interface OnSizeChangeListener {
        void onSizeChange(int i, int i2, int i3, int i4);
    }

    public static final class ThumbItem extends FrameLayout implements Styleable {
        public void setStyle(Style style) {
        }

        public ThumbItem(Context context) {
            super(context, null);
            setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
            ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.lobi_chat_pick_preview_horizontall_scroller_item, this, true);
        }
    }

    public HorizontalElementScroller(Context context) {
        this(context, null);
    }

    public HorizontalElementScroller(Context context, AttributeSet attrs) {
        boolean z;
        super(context, attrs);
        this.mData = new ArrayList();
        this.mImageData = new ArrayList();
        this.mElements = 0;
        this.mElementsInPage = 0;
        this.mContext = context;
        setLayoutParams(new LayoutParams(-1, -2));
        setOrientation(0);
        inflateView(this.mContext);
        this.mViewPager = (ViewPager) findViewById(R.id.lobi_horizontal_view_pager);
        DebugAssert.assertNotNull(this.mViewPager);
        this.mWidthElment = this.mContext.getResources().getDimensionPixelSize(R.dimen.lobi_stamp_category_thumb_width);
        if (this.mWidthElment > 0) {
            z = true;
        } else {
            z = false;
        }
        DebugAssert.assertTrue(z);
    }

    public void setOnClickItemListener(OnClickItemListener listener) {
        this.mOnClickItemListener = listener;
    }

    protected void inflateView(Context context) {
        ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.lobi_horizontal_element_scroller, this, true);
    }

    public void setData(List<View> data) {
        this.mData = data;
        this.mElements = data.size();
    }

    public void setData(List<ImageData> data, String type) {
        this.mImageData = data;
        this.mElements = data.size();
    }

    public int getSize() {
        return this.mElements;
    }

    public void setCurrentPage(int index) {
        this.mViewPager.setCurrentItem(index);
    }

    public int getCurrentPage() {
        return this.mViewPager.getCurrentItem();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.mWidthLayout = getMeasuredWidth();
        this.mElementsInPage = (int) Math.floor((double) (this.mWidthLayout / this.mWidthElment));
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (this.mOnsizeChangeListener != null) {
            this.mOnsizeChangeListener.onSizeChange(w, h, oldw, oldh);
        }
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public int getElementsNumberInPage() {
        return this.mElementsInPage;
    }

    public void render() {
        int elements = this.mElementsInPage;
        this.mViewPager.setPageMargin(-(this.mWidthLayout - (this.mWidthElment * elements)));
        int pages = (int) Math.ceil((double) (this.mData.size() / elements));
        if (this.mData.size() % elements == 0) {
            pages--;
        }
        List<View> views = new ArrayList();
        int n = 0;
        for (int i = 0; i <= pages; i++) {
            LinearLayout layout = new LinearLayout(this.mContext);
            layout.setLayoutParams(new LayoutParams(-1, -1));
            layout.removeAllViews();
            for (int j = 0; j < elements; j++) {
                if (n < this.mData.size()) {
                    layout.addView((View) this.mData.get(n));
                    n++;
                }
            }
            views.add(layout);
        }
        this.mPagerAdapter = new HorizontalPagerAdapter(this.mContext, views);
        this.mViewPager.setAdapter(this.mPagerAdapter);
    }

    public void render(String type) {
        if (TYPE_GALLERY.equals(type)) {
            int elements = this.mElementsInPage;
            this.mViewPager.setPageMargin(-(this.mWidthLayout - (this.mWidthElment * elements)));
            int pages = (int) Math.ceil((double) (this.mImageData.size() / elements));
            if (this.mImageData.size() % elements == 0) {
                pages--;
            }
            List<List<ImageData>> imageData = new ArrayList();
            int n = 0;
            for (int i = 0; i <= pages; i++) {
                List<ImageData> img = new ArrayList();
                for (int j = 0; j < elements; j++) {
                    if (n < this.mImageData.size()) {
                        img.add(this.mImageData.get(n));
                        n++;
                    }
                }
                imageData.add(img);
            }
            this.mViewPager.setAdapter(new GalleryHorizontalAdapter(this.mContext, imageData, this.mOnClickItemListener));
        }
    }

    public void setStyle(Style style) {
    }

    public void clearView() {
        if (this.mPagerAdapter != null) {
            this.mData.clear();
            this.mPagerAdapter.clear();
            this.mPagerAdapter.notifyDataSetChanged();
            removeAllViews();
            inflateView(this.mContext);
            this.mViewPager = (ViewPager) findViewById(R.id.lobi_horizontal_view_pager);
            DebugAssert.assertNotNull(this.mViewPager);
        }
    }

    public void setOnSizeChangeListener(OnSizeChangeListener listener) {
        this.mOnsizeChangeListener = listener;
    }
}
