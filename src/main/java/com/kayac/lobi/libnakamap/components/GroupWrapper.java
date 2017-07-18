package com.kayac.lobi.libnakamap.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.kayac.lobi.libnakamap.components.Styleable.Function;
import com.kayac.lobi.libnakamap.components.Styleable.Style;
import com.kayac.lobi.sdk.R;
import java.util.List;

public class GroupWrapper extends LinearLayout implements Styleable {
    List<String> mData;
    int mElements;
    int mIndex;
    boolean mIsAnimate;
    int mLayout;

    public GroupWrapper(Context context) {
        this(context, null);
    }

    public GroupWrapper(Context context, AttributeSet attrs) {
        this(context, attrs, R.style.lobi_group_wrapper);
    }

    public void setStyle(Style style) {
        Function.setChildenStyleIter(style, this);
    }

    public GroupWrapper(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        this.mIndex = 0;
        initLayout(context, attrs, defStyle);
    }

    private void initLayout(Context context, AttributeSet attrs, int defStyle) {
        setOrientation(1);
        ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.lobi_group_wrapper, this, true);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.lobi_GroupWrapper, 0, defStyle);
        this.mLayout = a.getResourceId(R.styleable.lobi_GroupWrapper_lobi_group_wrapper_content_layout, R.layout.lobi_group_wrapper_content_layout);
        this.mElements = a.getInt(R.styleable.lobi_GroupWrapper_lobi_group_wrapper_elements_to_display, 0);
        if (this.mElements > 0) {
            generateLayout(this.mElements);
        }
        this.mIsAnimate = a.getBoolean(R.styleable.lobi_GroupWrapper_lobi_group_wrapper_animated, false);
    }

    private void generateLayout(int size) {
        int rows = (int) Math.ceil(Math.sqrt((double) size));
        ViewGroup holder = (ViewGroup) findViewById(R.id.lobi_group_wrapper_content);
        holder.removeViewAt(0);
        int i = 0;
        while (i < rows) {
            LinearLayout group = createLinearLayout();
            int columns = (i + 1) * rows <= size ? rows : size % rows;
            if (columns != 0) {
                for (int j = 0; j < columns; j++) {
                    group.addView(createContentView());
                }
                holder.addView(group);
                if (columns == rows) {
                    i++;
                } else {
                    return;
                }
            }
            return;
        }
    }

    private LinearLayout createLinearLayout() {
        LinearLayout view = new LinearLayout(getContext());
        view.setOrientation(0);
        view.setLayoutParams(new LayoutParams(-1, -2));
        return view;
    }

    private View createContentView() {
        View newContent = LayoutInflater.from(getContext()).inflate(this.mLayout, null, false);
        newContent.setLayoutParams(new LayoutParams(0, -1, 1.0f));
        return newContent;
    }

    private void displayData(int indexStart) {
        ViewGroup holder = (ViewGroup) findViewById(R.id.lobi_group_wrapper_content);
        Animation animFadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.lobi_icon_fade_in);
        for (int i = 0; i < holder.getChildCount(); i++) {
            ViewGroup ll = (ViewGroup) holder.getChildAt(i);
            for (int j = 0; j < ll.getChildCount(); j++) {
                int index = indexStart;
                if (index >= this.mData.size()) {
                    index = 0;
                    indexStart = 0;
                    this.mIndex = 0;
                }
                ImageLoaderSquareView image = (ImageLoaderSquareView) ll.getChildAt(j).findViewById(R.id.lobi_group_wrapper_content_image_loader);
                image.loadImage((String) this.mData.get(index));
                if (this.mIsAnimate) {
                    image.startAnimation(animFadeIn);
                }
                indexStart++;
                this.mIndex = index;
            }
        }
    }

    public void setData(List<String> data) {
        this.mData = data;
        if (this.mElements == 0) {
            generateLayout(this.mData.size());
        }
        displayData(this.mIndex);
    }

    public View getBase() {
        return findViewById(R.id.lobi_group_wrapper_base);
    }

    public void rotate() {
        if (this.mIsAnimate) {
            ViewGroup holder = (ViewGroup) findViewById(R.id.lobi_group_wrapper_content);
            Animation animFadeOut = AnimationUtils.loadAnimation(getContext(), R.anim.lobi_icon_fade_out);
            animFadeOut.setAnimationListener(new AnimationListener() {
                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    GroupWrapper.this.displayData(GroupWrapper.this.mIndex);
                }
            });
            holder.startAnimation(animFadeOut);
            return;
        }
        displayData(this.mIndex);
    }

    public void reset() {
        displayData(0);
    }

    public void setTitle(String title) {
        ((TextView) findViewById(R.id.lobi_group_wrapper_title)).setText(title);
    }
}
