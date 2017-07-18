package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;

public final class zzb extends zza {
    public static final Creator<zzb> CREATOR = new zzc();
    String label;
    private final int mVersionCode;
    String value;

    zzb() {
        this.mVersionCode = 1;
    }

    zzb(int i, String str, String str2) {
        this.mVersionCode = i;
        this.label = str;
        this.value = str2;
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzc.zza(this, parcel, i);
    }
}
