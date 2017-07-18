package com.kayac.lobi.sdk.rec.activity;

import com.kayac.lobi.libnakamap.datastore.AccountDDL.KKey.UpdateAt;
import com.kayac.lobi.libnakamap.datastore.DatastoreAsyncCallback;

class bc implements DatastoreAsyncCallback<Long> {
    final /* synthetic */ RecPostVideoActivity a;

    bc(RecPostVideoActivity recPostVideoActivity) {
        this.a = recPostVideoActivity;
    }

    public void a(Long l) {
        if (System.currentTimeMillis() < l.longValue() + UpdateAt.GET_REC_INFO_CACHE) {
            this.a.loadRecSdkInfoFromDisk();
        } else {
            this.a.loadRecSdkInfoFromServer();
        }
    }

    public /* synthetic */ void onResponse(Object obj) {
        a((Long) obj);
    }
}
