package com.kayac.lobi.sdk.rec.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.VideoView;
import com.kayac.lobi.sdk.rec.R;

public class RecVideoPlayerActivity extends Activity {
    public static final String EXTRA_URI = "EXTRA_URI";
    private int mStopPosition = 0;
    private VideoView mVideoView = null;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setResult(1);
        overridePendingTransition(0, 0);
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
        setContentView(R.layout.lobi_rec_activity_video_player);
        this.mVideoView = (VideoView) findViewById(R.id.lobi_rec_play_video_player);
        this.mVideoView.post(new bj(this, string));
    }

    protected void onPause() {
        super.onPause();
        this.mVideoView.pause();
        this.mStopPosition = this.mVideoView.getCurrentPosition();
    }

    protected void onResume() {
        super.onResume();
        this.mVideoView.seekTo(this.mStopPosition);
    }
}
