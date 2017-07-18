package com.google.android.gms.wallet.firstparty;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;

public final class zzf extends zza {
    public static final Creator<zzf> CREATOR = new zzg();
    private final int mVersionCode;
    byte[] zzbPE;

    zzf() {
        this(1, new byte[0]);
    }

    zzf(int i, byte[] bArr) {
        this.mVersionCode = i;
        this.zzbPE = bArr;
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzg.zza(this, parcel, i);
    }
}
