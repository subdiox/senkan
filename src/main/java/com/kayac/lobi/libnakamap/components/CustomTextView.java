package com.kayac.lobi.libnakamap.components;

import android.content.Context;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import com.kayac.lobi.libnakamap.utils.NakamapLinkify;
import com.kayac.lobi.libnakamap.utils.NakamapLinkify.ClickableURLSpan;

public class CustomTextView extends TextView {
    protected OnTextLinkClickedListener mOnTextLinkClickedListener = null;

    public interface OnTextLinkClickedListener {
        void onTextLinkClicked(CustomTextView customTextView, ClickableURLSpan clickableURLSpan, CharSequence charSequence);
    }

    public CustomTextView(Context context) {
        super(context);
    }

    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        NakamapLinkify.addLinks((TextView) this, getAutoLinkMask(), this.mOnTextLinkClickedListener);
        setMovementMethod(null);
    }

    public void setSuperText(CharSequence text, BufferType bufferType) {
        super.setText(text, bufferType);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnTextLinkClickedListener(OnTextLinkClickedListener listener) {
        this.mOnTextLinkClickedListener = listener;
        NakamapLinkify.addLinks((TextView) this, getAutoLinkMask(), listener);
    }

    public void removeTextLinkClickedListeners() {
        Spannable text = (Spannable) getText();
        ClickableURLSpan[] spans = (ClickableURLSpan[]) text.getSpans(0, text.length(), ClickableURLSpan.class);
        for (int i = spans.length - 1; i >= 0; i--) {
            ClickableURLSpan clickableURLSpan = spans[i];
            clickableURLSpan.setOnClickListener(null);
            text.removeSpan(clickableURLSpan);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        CharSequence text = getText();
        if (text instanceof Spannable) {
            Spannable buffer = (Spannable) text;
            int action = event.getAction();
            if (action == 1 || action == 0) {
                int x = (((int) event.getX()) - getTotalPaddingLeft()) + getScrollX();
                int y = (((int) event.getY()) - getTotalPaddingTop()) + getScrollY();
                Layout layout = getLayout();
                int off = layout.getOffsetForHorizontal(layout.getLineForVertical(y), (float) x);
                ClickableSpan[] link = (ClickableSpan[]) buffer.getSpans(off, off, ClickableSpan.class);
                if (link.length != 0) {
                    if (action == 1) {
                        link[0].onClick(widget);
                        return true;
                    } else if (action != 0) {
                        return true;
                    } else {
                        Selection.setSelection(buffer, buffer.getSpanStart(link[0]), buffer.getSpanEnd(link[0]));
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
