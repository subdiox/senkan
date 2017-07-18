package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;

public final class zzf extends zza {
    public static final Creator<zzf> CREATOR = new zzi();
    String label;
    private final int mVersionCode;
    String type;
    zzl zzbOI;
    zzg zzbQA;

    zzf() {
        this.mVersionCode = 1;
    }

    zzf(int i, String str, zzg com_google_android_gms_wallet_wobs_zzg, String str2, zzl com_google_android_gms_wallet_wobs_zzl) {
        this.mVersionCode = i;
        this.label = str;
        this.zzbQA = com_google_android_gms_wallet_wobs_zzg;
        this.type = str2;
        this.zzbOI = com_google_android_gms_wallet_wobs_zzl;
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzi.zza(this, parcel, i);
    }
}
