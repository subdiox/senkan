package com.google.android.gms.wallet.firstparty;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;

public final class zzl extends zza {
    public static final Creator<zzl> CREATOR = new zzm();
    private final int mVersionCode;
    int[] zzbPI;

    zzl() {
        this(1, null);
    }

    zzl(int i, int[] iArr) {
        this.mVersionCode = i;
        this.zzbPI = iArr;
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzm.zza(this, parcel, i);
    }
}
