package com.kayac.lobi.sdk.rec.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.VideoView;
import com.kayac.lobi.sdk.LobiCoreAPI.APICallback;
import com.kayac.lobi.sdk.rec.R;

public class RecVideoLoadingActivity extends Activity {
    public static final String EXTRA_URI = "EXTRA_URI";
    private VideoView mVideoView = null;

    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        finish();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            finish();
        }
        if (!extras.containsKey("EXTRA_URI")) {
            finish();
        }
        Object string = extras.getString("EXTRA_URI");
        if (TextUtils.isEmpty(string)) {
            finish();
        }
        setContentView(R.layout.lobi_rec_activity_video_loading);
        Intent intent = new Intent(this, RecVideoPlayerActivity.class);
        intent.putExtra("EXTRA_URI", string);
        startActivityForResult(intent, APICallback.RESPONSE_ERROR);
        overridePendingTransition(0, 0);
    }
}
