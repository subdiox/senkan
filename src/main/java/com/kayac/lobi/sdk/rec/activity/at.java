package com.kayac.lobi.sdk.rec.activity;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

class at extends DialogFragment {
    final /* synthetic */ int a;
    final /* synthetic */ RecPostVideoActivity b;

    at(RecPostVideoActivity recPostVideoActivity, int i) {
        this.b = recPostVideoActivity;
        this.a = i;
    }

    public Dialog onCreateDialog(Bundle bundle) {
        setCancelable(false);
        Builder builder = new Builder(getActivity());
        builder.setMessage(getString(this.a)).setPositiveButton(17039370, new au(this)).setCancelable(false);
        return builder.create();
    }
}
