package com.kayac.lobi.sdk.rec.activity;

import com.kayac.lobi.libnakamap.rec.c.f;

class ba implements Runnable {
    final /* synthetic */ RecPostVideoActivity a;

    ba(RecPostVideoActivity recPostVideoActivity) {
        this.a = recPostVideoActivity;
    }

    public void run() {
        String obj = this.a.mTitleEditText.d().toString();
        String obj2 = this.a.mDescriptionEditText.d().toString();
        this.a.mTryPost = true;
        f.a(this.a.mMovieFile.getAbsolutePath(), obj, obj2, this.a.mPostScore, this.a.mPostCategory, this.a.mPostMetadata, this.a.mShareWithFacebook, this.a.mShareWithTwitter, this.a.mShareWithYoutube, this.a.mShareWithNicovideo, this.a.mVideoUsingCamera.booleanValue(), this.a.mVideoUsingMic.booleanValue(), null, this.a.mSecretMode.booleanValue(), false, new bb(this));
    }
}
