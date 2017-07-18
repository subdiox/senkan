package com.kayac.lobi.sdk.rec.activity;

import com.kayac.lobi.libnakamap.components.UIEditText;
import com.kayac.lobi.libnakamap.components.UIEditText.OnTextChangedListener;

class be implements OnTextChangedListener {
    final /* synthetic */ RecPostVideoActivity a;
    final /* synthetic */ a b;

    be(a aVar, RecPostVideoActivity recPostVideoActivity) {
        this.b = aVar;
        this.a = recPostVideoActivity;
    }

    public void onTextChanged(UIEditText uIEditText, CharSequence charSequence, int i, int i2, int i3) {
        this.b.a.updateTextCounters();
    }
}
