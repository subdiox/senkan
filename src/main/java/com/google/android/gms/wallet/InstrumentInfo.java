package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;

public final class InstrumentInfo extends zza {
    public static final Creator<InstrumentInfo> CREATOR = new zzj();
    private final int mVersionCode;
    private String zzbOr;
    private String zzbOs;

    InstrumentInfo(int i, String str, String str2) {
        this.mVersionCode = i;
        this.zzbOr = str;
        this.zzbOs = str2;
    }

    public String getInstrumentDetails() {
        return this.zzbOs;
    }

    public String getInstrumentType() {
        return this.zzbOr;
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzj.zza(this, parcel, i);
    }
}
