package com.kayac.lobi.libnakamap.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.kayac.lobi.libnakamap.utils.ViewUtils;
import com.kayac.lobi.sdk.R;

public class PullDownOverScrollComponent extends FrameLayout {
    private final TextView mUpdateTextView = ((TextView) ViewUtils.findViewById(this, R.id.lobi_pull_down_over_scroll_update));

    public PullDownOverScrollComponent(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.lobi_pull_down_over_scroll_component, this);
    }

    public TextView getUpdateTextView() {
        return this.mUpdateTextView;
    }
}
