package com.kayac.lobi.sdk.rec.activity;

import android.widget.Toast;
import com.kayac.lobi.sdk.rec.R;

class az implements Runnable {
    final /* synthetic */ RecPostVideoActivity a;

    az(RecPostVideoActivity recPostVideoActivity) {
        this.a = recPostVideoActivity;
    }

    public void run() {
        Toast.makeText(this.a, this.a.getString(R.string.lobirec_duplicated_video), 0).show();
    }
}
