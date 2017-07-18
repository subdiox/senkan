package com.rekoo.libs.platform.ui.floating;

import android.view.View;
import android.view.View.OnClickListener;
import java.util.Calendar;

public abstract class NoDuplicateClickListener implements OnClickListener {
    public static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;

    public abstract void onNoDulicateClick(View view);

    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - this.lastClickTime > 1000) {
            this.lastClickTime = currentTime;
            onNoDulicateClick(v);
        }
    }
}
