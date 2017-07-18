package com.kayac.lobi.libnakamap.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.kayac.lobi.sdk.R;

public class TextDialog extends CustomDialog {

    private static final class TextContent extends FrameLayout {
        public TextContent(Context context, String str) {
            super(context);
            ((TextView) ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.lobi_custom_dialog_content_text, this, true).findViewById(R.id.lobi_custom_dialog_content_text)).setText(str);
        }
    }

    public TextDialog(Context context, String str) {
        super(context, new TextContent(context, str));
    }
}
