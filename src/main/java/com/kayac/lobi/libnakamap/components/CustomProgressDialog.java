package com.kayac.lobi.libnakamap.components;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;
import com.kayac.lobi.libnakamap.utils.DebugAssert;
import com.kayac.lobi.sdk.R;

public class CustomProgressDialog extends Dialog {
    public CustomProgressDialog(Context context) {
        super(context, R.style.lobi_custom_dialog);
        View view = ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.lobi_custom_progress_dialog, null, false);
        view.setClickable(false);
        setContentView(view);
        setCanceledOnTouchOutside(false);
        LayoutParams lp = getWindow().getAttributes();
        lp.width = -1;
        getWindow().setAttributes(lp);
    }

    public void setMessage(String message) {
        TextView t = (TextView) findViewById(R.id.lobi_custom_progress_dialog_message);
        DebugAssert.assertNotNull(t);
        t.setText(message);
    }

    public void dismiss() {
        try {
            super.dismiss();
        } catch (Exception e) {
        }
    }
}
