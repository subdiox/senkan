package com.kayac.lobi.libnakamap.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.kayac.lobi.sdk.R;

public class ChatOptionButton extends LinearLayout {
    private Drawable mDefaultDrawable;
    private String mDefaultText;
    private Drawable mHighlightDrawable;
    private final ImageView mIconImage;
    private final TextView mTitleText;

    public ChatOptionButton(Context context) {
        this(context, null);
    }

    public ChatOptionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        View parent = LayoutInflater.from(context).inflate(R.layout.lobi_chat_option_button, this, true);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.lobi_ChatOptionButton);
        this.mDefaultDrawable = array.getDrawable(R.styleable.lobi_ChatOptionButton_lobi_default_drawable);
        this.mHighlightDrawable = array.getDrawable(R.styleable.lobi_ChatOptionButton_lobi_highlight_drawable);
        this.mDefaultText = array.getString(R.styleable.lobi_ChatOptionButton_lobi_default_text);
        String textAlign = array.getString(R.styleable.lobi_ChatOptionButton_lobi_textAlign);
        array.recycle();
        this.mTitleText = (TextView) parent.findViewById(R.id.lobi_chat_option_buttion_text);
        if (this.mDefaultText != null) {
            this.mTitleText.setText(this.mDefaultText);
        }
        if (textAlign != null) {
            this.mTitleText.setGravity(getAlignId(textAlign));
        }
        this.mIconImage = (ImageView) parent.findViewById(R.id.lobi_chat_option_buttion_image);
        if (this.mDefaultDrawable != null) {
            this.mIconImage.setImageDrawable(this.mDefaultDrawable);
        }
        setIconHighlight(false);
        setTextCount(1);
    }

    private int getAlignId(String alignStr) {
        if (alignStr.endsWith("left")) {
            return 3;
        }
        if (alignStr.endsWith("right")) {
            return 5;
        }
        if (alignStr.endsWith("center")) {
            return 17;
        }
        throw new IllegalArgumentException("invalid param." + alignStr);
    }

    public void setIconHighlight(boolean highlighted) {
        Drawable toDraw = highlighted ? this.mHighlightDrawable : this.mDefaultDrawable;
        if (toDraw != null) {
            this.mIconImage.setImageDrawable(toDraw);
        }
    }

    public void setTextCount(int count) {
        String setString = this.mDefaultText;
        if (count > 0) {
            setString = String.valueOf(count);
        }
        if (this.mDefaultText != null) {
            this.mTitleText.setText(setString);
        }
    }
}
