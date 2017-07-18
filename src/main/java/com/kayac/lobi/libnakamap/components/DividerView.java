package com.kayac.lobi.libnakamap.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.kayac.lobi.sdk.R;

public class DividerView extends LinearLayout {
    public DividerView(Context context) {
        this(context, null);
    }

    public DividerView(Context context, AttributeSet attrs) {
        super(context);
        setLayoutParams(new LayoutParams(-1, -2));
        ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.lobi_divider_view_layout, this, true);
    }
}
