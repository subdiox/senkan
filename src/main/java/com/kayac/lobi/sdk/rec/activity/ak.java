package com.kayac.lobi.sdk.rec.activity;

import android.content.Intent;

class ak implements Runnable {
    final /* synthetic */ aj a;

    ak(aj ajVar) {
        this.a = ajVar;
    }

    public void run() {
        Intent intent = new Intent(this.a.a, RecPlayActivity.class);
        intent.putExtra(RecPlayActivity.EXTRA_CAN_GO_BACK_TO_ACTIVITY, true);
        intent.putExtra(RecPlayActivity.EXTRA_SORT, "created");
        this.a.a.startActivity(intent);
    }
}
