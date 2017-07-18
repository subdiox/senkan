package com.kayac.lobi.libnakamap.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.kayac.lobi.libnakamap.components.Styleable.Style;
import com.kayac.lobi.sdk.R;

public class SectionView extends LinearLayout implements Styleable {
    public SectionView(Context context) {
        super(context, null);
        ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.lobi_section_view_layout, this, true);
    }

    public SectionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayoutParams(new LayoutParams(-1, -2));
        ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.lobi_section_view_layout, this, true);
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.lobi_SectionView);
            int title = typedArray.getResourceId(R.styleable.lobi_SectionView_lobi_section_title, -1);
            boolean editButton = typedArray.getBoolean(R.styleable.lobi_SectionView_lobi_section_option_button, false);
            int editButtonImage = typedArray.getResourceId(R.styleable.lobi_SectionView_lobi_section_option_button_image, R.drawable.lobi_btn_section_edit);
            int optionText = typedArray.getResourceId(R.styleable.lobi_SectionView_lobi_section_option_text, -1);
            setTitle(title);
            setButton(editButton, editButtonImage);
            setOptionText(optionText);
            typedArray.recycle();
        }
    }

    public void setStyle(Style style) {
    }

    public void setTitleText(String title) {
        ((TextView) findViewById(R.id.lobi_section_view_text)).setText(title);
    }

    public void setImage(Integer image) {
        ImageView view = (ImageView) findViewById(R.id.lobi_section_view_sub_image);
        view.setVisibility(0);
        if (image != null) {
            view.setImageResource(image.intValue());
        } else {
            view.setImageBitmap(null);
        }
    }

    public TextView getTitleTextView() {
        return (TextView) findViewById(R.id.lobi_section_view_text);
    }

    public void setOptionViewVisible(boolean visible) {
        int i;
        int i2 = 0;
        View findViewById = findViewById(R.id.lobi_section_view_sub_image);
        if (visible) {
            i = 0;
        } else {
            i = 8;
        }
        findViewById.setVisibility(i);
        View findViewById2 = findViewById(R.id.lobi_section_view_sub_text);
        if (!visible) {
            i2 = 8;
        }
        findViewById2.setVisibility(i2);
    }

    private void setTitle(int id) {
        if (id > 0) {
            TextView title = (TextView) findViewById(R.id.lobi_section_view_text);
            if (title != null) {
                title.setText(id);
            }
        }
    }

    private void setButton(boolean visible, int resId) {
        ImageView view = (ImageView) findViewById(R.id.lobi_section_view_sub_image);
        view.setVisibility(visible ? 0 : 8);
        view.setImageResource(resId);
    }

    private void setOptionText(int text) {
        if (text > 0) {
            TextView view = (TextView) findViewById(R.id.lobi_section_view_sub_text);
            view.setVisibility(0);
            view.setText(text);
        }
    }

    public TextView getOptionTextView() {
        return (TextView) findViewById(R.id.lobi_section_view_sub_text);
    }
}
