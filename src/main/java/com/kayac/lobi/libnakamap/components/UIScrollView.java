package com.kayac.lobi.libnakamap.components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class UIScrollView extends ScrollView {
    private OnScrollChanged mOnScrollChanged;

    public interface OnScrollChanged {
        void onScrollChanged(int i, int i2, int i3, int i4);
    }

    public UIScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnScrollChanged(OnScrollChanged onScrollChanged) {
        this.mOnScrollChanged = onScrollChanged;
    }

    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (this.mOnScrollChanged != null) {
            this.mOnScrollChanged.onScrollChanged(l, t, oldl, oldt);
        }
    }
}
