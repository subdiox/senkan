package com.kayac.lobi.libnakamap.components;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.EditText;

public class UIEditText extends EditText {
    protected OnTextChangedListener mOnTextChangedListener = null;

    public interface OnTextChangedListener {
        void onTextChanged(UIEditText uIEditText, CharSequence charSequence, int i, int i2, int i3);
    }

    public UIEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        allowEmoji();
    }

    public UIEditText(Context context) {
        super(context);
        allowEmoji();
    }

    private void allowEmoji() {
        Bundle bundle = getInputExtras(true);
        if (bundle != null) {
            bundle.putBoolean("allowEmoji", true);
        }
    }

    public OnTextChangedListener getOnTextChangedListener() {
        return this.mOnTextChangedListener;
    }

    public void setOnTextChangedListener(OnTextChangedListener listener) {
        this.mOnTextChangedListener = listener;
    }

    protected void onTextChanged(CharSequence text, int start, int before, int after) {
        super.onTextChanged(text, start, before, after);
        if (this.mOnTextChangedListener != null) {
            this.mOnTextChangedListener.onTextChanged(this, text, start, before, after);
        }
    }
}
