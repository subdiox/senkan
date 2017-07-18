package com.google.android.gms.wallet.firstparty;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;

public final class zzj extends zza {
    public static final Creator<zzj> CREATOR = new zzk();
    private final int mVersionCode;
    byte[] zzbPH;

    zzj() {
        this(1, new byte[0]);
    }

    zzj(int i, byte[] bArr) {
        this.mVersionCode = i;
        this.zzbPH = bArr;
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzk.zza(this, parcel, i);
    }
}
