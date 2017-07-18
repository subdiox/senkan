package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;

public final class zzl extends zza {
    public static final Creator<zzl> CREATOR = new zzm();
    private final int mVersionCode;
    long zzbQH;
    long zzbQI;

    zzl() {
        this.mVersionCode = 1;
    }

    zzl(int i, long j, long j2) {
        this.mVersionCode = i;
        this.zzbQH = j;
        this.zzbQI = j2;
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzm.zza(this, parcel, i);
    }
}
