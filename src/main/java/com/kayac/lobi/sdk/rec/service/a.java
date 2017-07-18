package com.kayac.lobi.sdk.rec.service;

import com.kayac.lobi.sdk.LobiCoreAPI.APICallback;
import org.json.JSONObject;

class a implements APICallback {
    final /* synthetic */ int[] a;
    final /* synthetic */ JSONObject[] b;
    final /* synthetic */ MovieUploadService c;

    a(MovieUploadService movieUploadService, int[] iArr, JSONObject[] jSONObjectArr) {
        this.c = movieUploadService;
        this.a = iArr;
        this.b = jSONObjectArr;
    }

    public void onResult(int i, JSONObject jSONObject) {
        this.a[0] = i;
        this.b[0] = jSONObject;
    }
}
