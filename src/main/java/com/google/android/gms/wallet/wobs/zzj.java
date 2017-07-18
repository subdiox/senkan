package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;

public final class zzj extends zza {
    public static final Creator<zzj> CREATOR = new zzk();
    String body;
    private final int mVersionCode;
    String zzbQG;

    zzj() {
        this.mVersionCode = 1;
    }

    zzj(int i, String str, String str2) {
        this.mVersionCode = i;
        this.zzbQG = str;
        this.body = str2;
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzk.zza(this, parcel, i);
    }
}
