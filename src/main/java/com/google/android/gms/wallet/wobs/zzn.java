package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;

public final class zzn extends zza {
    public static final Creator<zzn> CREATOR = new zzo();
    String description;
    private final int mVersionCode;
    String zzbQJ;

    zzn() {
        this.mVersionCode = 1;
    }

    zzn(int i, String str, String str2) {
        this.mVersionCode = i;
        this.zzbQJ = str;
        this.description = str2;
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzo.zza(this, parcel, i);
    }
}
