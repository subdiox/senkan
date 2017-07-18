package com.kayac.lobi.sdk.rec.activity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import com.kayac.lobi.libnakamap.rec.a.b;
import com.kayac.lobi.sdk.rec.R;

class ah implements OnClickListener {
    final /* synthetic */ RecPostVideoActivity a;

    ah(RecPostVideoActivity recPostVideoActivity) {
        this.a = recPostVideoActivity;
    }

    public void onClick(View view) {
        if (!this.a.mLoadingModal && this.a.mMovieFile != null) {
            if (this.a.mSending) {
                b.b("RecPostVideoActivity", "Sending...");
                return;
            }
            this.a.updateTextCounters();
            if (!this.a.mTitleEditText.b()) {
                Toast.makeText(this.a, this.a.getString(R.string.lobirec_no_title), 0).show();
            } else if (this.a.mTitleEditText.c()) {
                Toast.makeText(this.a, this.a.getString(R.string.lobirec_too_long_title), 0).show();
            } else if (this.a.mDescriptionEditText.c()) {
                Toast.makeText(this.a, this.a.getString(R.string.lobirec_too_long_description), 0).show();
            } else {
                this.a.doIfTermsOfUseIsAccepted(new ai(this));
            }
        }
    }
}
