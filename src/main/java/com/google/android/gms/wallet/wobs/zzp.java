package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;

public final class zzp extends zza {
    public static final Creator<zzp> CREATOR = new zzq();
    String body;
    private final int mVersionCode;
    String zzbQG;
    zzl zzbQK;
    zzn zzbQL;
    zzn zzbQM;

    zzp() {
        this.mVersionCode = 1;
    }

    zzp(int i, String str, String str2, zzl com_google_android_gms_wallet_wobs_zzl, zzn com_google_android_gms_wallet_wobs_zzn, zzn com_google_android_gms_wallet_wobs_zzn2) {
        this.mVersionCode = i;
        this.zzbQG = str;
        this.body = str2;
        this.zzbQK = com_google_android_gms_wallet_wobs_zzl;
        this.zzbQL = com_google_android_gms_wallet_wobs_zzn;
        this.zzbQM = com_google_android_gms_wallet_wobs_zzn2;
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzq.zza(this, parcel, i);
    }
}
