package com.kayac.lobi.libnakamap.components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import com.kayac.lobi.libnakamap.components.Styleable.Style;

public class CustomCheckbox extends CheckBox implements Styleable {
    public CustomCheckbox(Context context) {
        super(context);
        initCheckbox();
    }

    public CustomCheckbox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initCheckbox();
    }

    public CustomCheckbox(Context context, AttributeSet attrs) {
        super(context, attrs);
        initCheckbox();
    }

    private void initCheckbox() {
        setSaveEnabled(false);
    }

    public void setStyle(Style style) {
    }
}
