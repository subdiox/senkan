package com.google.android.gms.wallet.firstparty;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;

public class zzq extends zza {
    public static final Creator<zzq> CREATOR = new zzr();
    final int mVersionCode;
    int zzbPM;
    Bundle zzbPN;
    String zzbPO;

    public zzq() {
        this.mVersionCode = 2;
        this.zzbPM = 0;
        this.zzbPN = new Bundle();
        this.zzbPO = "";
    }

    zzq(int i, int i2, Bundle bundle, String str) {
        this.mVersionCode = i;
        this.zzbPN = bundle;
        this.zzbPM = i2;
        this.zzbPO = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzr.zza(this, parcel, i);
    }
}
