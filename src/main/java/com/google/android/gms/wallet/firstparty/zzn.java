package com.google.android.gms.wallet.firstparty;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;

public final class zzn extends zza {
    public static final Creator<zzn> CREATOR = new zzo();
    private final int mVersionCode;
    String[] zzbPJ;
    byte[][] zzbPK;

    zzn() {
        this(1, new String[0], new byte[0][]);
    }

    zzn(int i, String[] strArr, byte[][] bArr) {
        this.mVersionCode = i;
        this.zzbPJ = strArr;
        this.zzbPK = bArr;
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzo.zza(this, parcel, i);
    }
}
