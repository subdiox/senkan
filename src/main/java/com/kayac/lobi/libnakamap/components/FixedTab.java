package com.kayac.lobi.libnakamap.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.kayac.lobi.libnakamap.components.Styleable.Function;
import com.kayac.lobi.libnakamap.components.Styleable.Style;
import com.kayac.lobi.libnakamap.utils.DebugAssert;
import com.kayac.lobi.sdk.R;
import java.util.ArrayList;

public class FixedTab extends LinearLayout implements Styleable {
    private int mCurrentPosition = -1;
    private OnPositionChangeListener mOnPositionChangeListener = new OnPositionChangeListener() {
        public void onPositionChange(int pos) {
        }
    };

    public interface OnPositionChangeListener {
        void onPositionChange(int i);
    }

    public static final class Element extends LinearLayout implements Styleable {
        private int mIcon;

        public void setTitle(String title) {
            TextView t = (TextView) findViewById(R.id.lobi_fixed_tab_element_title);
            DebugAssert.assertNotNull(t);
            t.setText(title);
        }

        public void setIcon(int icon) {
            ImageView i = (ImageView) findViewById(R.id.lobi_fixed_tab_element_icon);
            DebugAssert.assertNotNull(i);
            this.mIcon = icon;
            i.setImageResource(this.mIcon);
        }

        public void selected(boolean selected) {
            int color;
            findViewById(R.id.lobi_fixed_tab_element_position).setVisibility(selected ? 0 : 4);
            TextView t = (TextView) findViewById(R.id.lobi_fixed_tab_element_title);
            DebugAssert.assertNotNull(t);
            if (selected) {
                color = getResources().getColor(R.color.lobi_text_black);
            } else {
                color = getResources().getColor(R.color.lobi_text_gray);
            }
            t.setTextColor(color);
            if (this.mIcon != -1) {
                ((ImageView) findViewById(R.id.lobi_fixed_tab_element_icon)).setSelected(selected);
            }
        }

        public void showNotice(int visible) {
            findViewById(R.id.lobi_fixed_tab_element_notice).setVisibility(visible);
        }

        public boolean isShowNotice() {
            return findViewById(R.id.lobi_fixed_tab_element_notice).getVisibility() == 0;
        }

        public void setStyle(Style style) {
            Function.setChildenStyleIter(style, this);
        }

        public Element(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.mIcon = -1;
            setLayoutParams(new LayoutParams(-1, -2));
            setOrientation(1);
            ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.lobi_fixed_tab_element, this, true);
            setBackgroundColor(Color.parseColor("#FFFFFF"));
        }

        public Element(Context context) {
            this(context, null);
        }
    }

    public void setPosition(int pos) {
        if (this.mCurrentPosition != pos) {
            this.mCurrentPosition = pos;
            this.mOnPositionChangeListener.onPositionChange(pos);
            int count = getChildCount();
            int i = 0;
            while (i < count) {
                View child = getChildAt(i);
                if (child instanceof Element) {
                    ((Element) child).selected(i == pos);
                }
                i++;
            }
        }
    }

    public int getCurrentPosition() {
        return this.mCurrentPosition;
    }

    public Element getElement(int position) {
        return (Element) getChildAt(position);
    }

    public ArrayList<Element> getElements() {
        int childCount = getChildCount();
        ArrayList<Element> childs = new ArrayList();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child instanceof Element) {
                childs.add((Element) child);
            }
        }
        return childs;
    }

    public void setStyle(Style style) {
        Function.setChildenStyleIter(style, this);
    }

    public FixedTab(Context context, AttributeSet attrs) {
        boolean z;
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.lobi_FixedTab);
        int resId = typedArray.getResourceId(R.styleable.lobi_FixedTab_lobi_titles, -1);
        if (resId != -1) {
            z = true;
        } else {
            z = false;
        }
        DebugAssert.assertTrue(z);
        String[] titles = getResources().getStringArray(resId);
        int iconsId = typedArray.getResourceId(R.styleable.lobi_FixedTab_lobi_icons, -1);
        TypedArray icons = null;
        if (iconsId != -1) {
            icons = getResources().obtainTypedArray(iconsId);
        }
        for (int i = 0; i < titles.length; i++) {
            String title = titles[i];
            Element e = new Element(getContext());
            e.setLayoutParams(new LayoutParams(0, -2, 1.0f));
            e.setTitle(title);
            if (icons != null) {
                e.setIcon(icons.getResourceId(i, 0));
            }
            final int n = i;
            e.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    FixedTab.this.setPosition(n);
                }
            });
            addView(e);
        }
    }

    public FixedTab(Context context) {
        super(context);
    }

    public void setOnPositionChangeListener(OnPositionChangeListener onPositionChangeListener) {
        this.mOnPositionChangeListener = onPositionChangeListener;
    }
}
