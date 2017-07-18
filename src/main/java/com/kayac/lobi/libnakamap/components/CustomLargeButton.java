package com.kayac.lobi.libnakamap.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import com.kayac.lobi.libnakamap.components.Styleable.Style;
import com.kayac.lobi.sdk.R;

public class CustomLargeButton extends FrameLayout implements Styleable {
    private static final int UNDEFINED_VALUE = -1;
    private final Context mContext;
    private boolean mIsOn;

    public CustomLargeButton(Context context) {
        this(context, null);
    }

    public CustomLargeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mIsOn = false;
        this.mContext = context;
        setLayoutParams(new LayoutParams(-2, -2));
        ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.lobi_custom_large_button_layout, this, true);
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.lobi_CustomLargeButton);
            int backgound = typedArray.getResourceId(R.styleable.lobi_CustomLargeButton_lobi_custom_large_button_background, -1);
            int textColor = typedArray.getResourceId(R.styleable.lobi_CustomLargeButton_lobi_custom_large_button_text_color, ViewCompat.MEASURED_STATE_MASK);
            int icon = typedArray.getResourceId(R.styleable.lobi_CustomLargeButton_lobi_custom_large_button_image, -1);
            int sizeId = typedArray.getResourceId(R.styleable.lobi_CustomLargeButton_lobi_custom_large_button_dp_size, -1);
            String text = typedArray.getString(R.styleable.lobi_CustomLargeButton_lobi_custom_large_button_text);
            setBackground(backgound);
            setTextView(textColor, text, sizeId);
            setIcon(icon);
            typedArray.recycle();
        }
    }

    public void setStyle(Style style) {
    }

    public void setBackground(int id) {
        if (id > 0) {
            ((FrameLayout) findViewById(R.id.lobi_custom_large_button_holder)).setBackgroundResource(id);
        }
    }

    public void setTextView(int color, String text) {
        setTextView(color, text, -1);
    }

    private void setTextView(int color, String text, int sizeId) {
        TextView textView = (TextView) findViewById(R.id.lobi_custom_large_button_text);
        textView.setTextColor(this.mContext.getResources().getColor(color));
        if (text != null) {
            textView.setText(text);
        }
        if (sizeId != -1) {
            textView.setTextSize(0, (float) getResources().getDimensionPixelSize(sizeId));
        }
    }

    public void setTextColor(int color) {
        ((TextView) findViewById(R.id.lobi_custom_large_button_text)).setTextColor(this.mContext.getResources().getColor(color));
    }

    public void setText(String text) {
        ((TextView) findViewById(R.id.lobi_custom_large_button_text)).setText(text);
    }

    public void setIcon(int id) {
        if (id > 0) {
            ((ImageView) findViewById(R.id.lobi_custom_large_button_image)).setImageResource(id);
        }
    }

    public boolean getIsOn() {
        return this.mIsOn;
    }

    public void switchONOff() {
        this.mIsOn = !this.mIsOn;
    }

    public void setOn() {
        this.mIsOn = true;
    }

    public void setOff() {
        this.mIsOn = false;
    }
}
