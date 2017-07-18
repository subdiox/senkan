package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;

public final class zzg extends zza {
    public static final Creator<zzg> CREATOR = new zzh();
    private final int mVersionCode;
    String zzbOo;
    int zzbQB;
    String zzbQC;
    double zzbQD;
    long zzbQE;
    int zzbQF;

    zzg() {
        this.mVersionCode = 1;
        this.zzbQF = -1;
        this.zzbQB = -1;
        this.zzbQD = -1.0d;
    }

    zzg(int i, int i2, String str, double d, String str2, long j, int i3) {
        this.mVersionCode = i;
        this.zzbQB = i2;
        this.zzbQC = str;
        this.zzbQD = d;
        this.zzbOo = str2;
        this.zzbQE = j;
        this.zzbQF = i3;
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzh.zza(this, parcel, i);
    }
}
