package com.kayac.lobi.libnakamap.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.kayac.lobi.libnakamap.utils.ViewUtils;
import com.kayac.lobi.sdk.R;

public class SearchBox extends LinearLayout {
    protected UIEditText mEditText;
    protected String mHint;
    protected SearchBox searchBox;

    public SearchBox(Context context) {
        this(context, null);
    }

    public SearchBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.searchBox = this;
        setLayoutParams(new LayoutParams(-1, -2));
        setOrientation(0);
        ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.lobi_search_box, this, true);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.lobi_SearchBox);
        boolean optionalButton = typedArray.getBoolean(R.styleable.lobi_SearchBox_lobi_closeButton, false);
        ((LinearLayout) findViewById(R.id.lobi_search_back_area)).setBackgroundColor(-1);
        this.mEditText = (UIEditText) ViewUtils.findViewById(this, R.id.lobi_search_box_text_edit);
        this.mHint = typedArray.getString(R.styleable.lobi_SearchBox_lobi_searchHintText);
        if (!TextUtils.isEmpty(this.mHint)) {
            this.mEditText.setHint(this.mHint);
        }
        setCloseSearch(optionalButton);
    }

    public UIEditText getEditText() {
        return this.mEditText;
    }

    public void clear() {
        UIEditText editText = (UIEditText) ViewUtils.findViewById(this, R.id.lobi_search_box_text_edit);
        editText.setText("");
        editText.setHint(this.mHint);
    }

    protected void setCloseSearch(boolean enable) {
        FrameLayout close = (FrameLayout) findViewById(R.id.lobi_search_close);
        if (enable) {
            close.setVisibility(0);
            close.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    SearchBox.this.mEditText.setText("");
                }
            });
            return;
        }
        close.setVisibility(8);
    }
}
