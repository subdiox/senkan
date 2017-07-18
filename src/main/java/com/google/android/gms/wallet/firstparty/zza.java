package com.google.android.gms.wallet.firstparty;

import android.os.Parcel;
import android.os.Parcelable.Creator;

public class zza extends com.google.android.gms.common.internal.safeparcel.zza {
    public static final Creator<zza> CREATOR = new zzb();
    private final int mVersionCode;
    byte[] zzbPA;
    zzq zzbPB;
    byte[] zzbPz;

    zza(int i, byte[] bArr, byte[] bArr2, zzq com_google_android_gms_wallet_firstparty_zzq) {
        this.mVersionCode = i;
        this.zzbPz = bArr;
        this.zzbPA = bArr2;
        this.zzbPB = com_google_android_gms_wallet_firstparty_zzq;
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzb.zza(this, parcel, i);
    }
}
